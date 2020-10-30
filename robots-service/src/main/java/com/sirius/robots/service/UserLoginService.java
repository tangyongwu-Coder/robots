package com.sirius.robots.service;

import com.sirius.robots.comm.constants.HeaderConstants;
import com.sirius.robots.comm.constants.ServiceConstants;
import com.sirius.robots.comm.constants.SystemConstants;
import com.sirius.robots.comm.enums.*;
import com.sirius.robots.comm.exception.RobotsServiceException;
import com.sirius.robots.comm.util.*;
import com.sirius.robots.manager.helps.RedisCacheHelper;
import com.sirius.robots.comm.bo.LoginInfoTokenBO;
import com.sirius.robots.manager.util.EmailUtil;
import com.sirius.robots.service.model.req.SendCodeReqDTO;
import com.sirius.robots.service.model.req.UserForgotReqDTO;
import com.sirius.robots.service.model.req.UserLoginReqDTO;
import com.sirius.robots.service.model.req.UserRegisterReqDTO;
import com.sirius.robots.dal.model.UserInfo;
import com.sirius.robots.manager.UserManager;
import com.sirius.robots.service.model.res.UserResDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 用户登录服务
 *
 * @author 孟星魂
 * @version 5.0 createTime: 2019/12/26
 */
@Slf4j
@Service
public class UserLoginService {

    @Autowired
    private UserManager userManager;

    @Autowired
    private RedisCacheHelper helper;

    @Autowired
    private EmailUtil emailUtil;
    /**
     * 登录
     *
     * @param reqDTO    登录请求参数
     */
    public UserResDTO login(UserLoginReqDTO reqDTO){
        String loginName = reqDTO.getLoginName();
        UserInfo userInfo = userManager.selectByLoginUser(loginName);
        if(Objects.isNull(userInfo)){
            throw new RobotsServiceException(ErrorCodeEnum.MSG_OUT_1002);
        }
        String passWord = reqDTO.getPassWord();
        String existPassWord = userInfo.getPassWord();
        if(!passWord.equals(existPassWord)){
            throw new RobotsServiceException(ErrorCodeEnum.MSG_OUT_1002);
        }
        return handleLoginEvent(userInfo);
    }

    public void sendCode(SendCodeReqDTO reqDTO){
        String codeType = reqDTO.getCodeType();
        String redisKey = getCodeKey(codeType,reqDTO.getValue());
        String data = helper.queryString(redisKey);
        if (Objects.nonNull(data)) {
            log.debug("已经发送,还未消费,不要重复发送verify Code: {}",data);
            return;
        }
        EmailTypeEnum emailTypeEnum = EmailTypeEnum.explain(codeType);
        if(Objects.isNull(emailTypeEnum)){
            throw new RobotsServiceException(ErrorCodeEnum.MSG_OUT_1003);
        }
        try{
            String verifyCode = RandomUtil.generateRandomNum(6);
            log.info("verify Code: {}",verifyCode);
            emailUtil.sendSimpleMail(reqDTO.getValue(),verifyCode,emailTypeEnum);
            //10分钟有效
            helper.insertString(redisKey,verifyCode,600L);
        }catch (Exception e){
            log.error("发送邮件通知,异常：{}",e);
            throw new RobotsServiceException(ErrorCodeEnum.MSG_OUT_1004);
        }
    }


    public String refreshToken(String token){
        try {
            LoginInfoTokenBO loginInfoTokenBO = TokenEncodeUtil.decodeToken(token);
            loginInfoTokenBO.setTokenType("LOGIN");
            return TokenEncodeUtil.encodeToken(loginInfoTokenBO);
        } catch (Exception e) {
            throw new RobotsServiceException(ErrorCodeEnum.SYSTEM_ERROR);
        }
    }

    /**
     * 登录验证
     *
     * @param userId  用户id
     * @param type    系统类型
     * @param token   令牌
     * @return 本次操作code对应apiPath
     */
    public void validateToken(Long userId, String type, String token) {
        log.info("登录验证开始：{},token：{}", userId, token);
        UserResDTO resBO = helper.queryObject(SystemConstants.buildLoginUserRedisKey(userId, type), UserResDTO.class);
        if (resBO == null) {
            log.info("从redis未获取登陆信息, 用户id:{},系统类型:{}", userId, type);
            throw new RobotsServiceException(ErrorCodeEnum.AUTH0001);
        }
        boolean flag = checkToken(resBO.getToken(), type, userId, token);
        if (flag) {
            log.info("登录用户信息:{}", resBO);
            LocalThreadUtils.updateLocalThreadProperties(HeaderConstants.HEADER_USER, resBO.getId() + StringUtils.EMPTY);
            LocalThreadUtils.updateLocalThreadProperties(HeaderConstants.HEADER_SYSTEM_TYPE, type);
            LocalThreadUtils.updateToken(type, resBO.getToken());
        } else {
            log.info("验证token无效");
            throw new RobotsServiceException(ErrorCodeEnum.AUTH0001);
        }
    }
    /**
     * @param oldToken 缓存内的token
     * @param type     系统类型
     * @param userId   用户ID
     * @param token    token
     * @return
     */
    private boolean checkToken(String oldToken, String type, Long userId, String token) {
        LoginInfoTokenBO loginInfoTokenBO = TokenEncodeUtil.decodeToken(token);
        log.info("根据token反解析用户信息,{}", loginInfoTokenBO);
        if (!userId.equals(loginInfoTokenBO.getUserId())) {
            log.info("验证操作员与传入的不一致,{}", loginInfoTokenBO.getUserId());
            throw new RobotsServiceException(ErrorCodeEnum.AUTH0001);
        }
        if (token.equals(oldToken)) {
            //保持会话
            String key = SystemConstants.buildLoginUserRedisKey(userId,type);
            helper.expire(key, 120L);
            log.debug("保持会话key:{},过期时间剩余:{}", key, helper.getExpire(key));
            return true;
        }
        return false;
    }


    /**
     * 登录完成后处理
     *
     * @param userInfo 登录结果
     * @return 登录处理结果
     */
    private UserResDTO handleLoginEvent(UserInfo userInfo) {
        UserResDTO resBO = BeanMapperUtil.objConvert(userInfo,UserResDTO.class);
        String token = createLoginToken(resBO);
        Long id = resBO.getId();
        String systemType = LocalThreadUtils.getLocalThreadProperties(HeaderConstants.HEADER_SYSTEM_TYPE);
        log.info("登录userId:{},token:{}", id, token);
        resBO.setToken(token);
        String refreshToken = createRefreshToken(resBO);

        resBO.setToken(refreshToken);
        String key = SystemConstants.buildLoginUserRedisKey(id,systemType);
        String refreshTokenKey = ServiceConstants.LOGIN_USER_REFRESH_REDIS_KEY +id+systemType;
        //刷新token的缓存 14小时
        helper.insertObject(refreshTokenKey, refreshToken, 14 * 60L * 60L);
        log.info("登录userId:{},redis KEY:{}", id, key);
        Long expire = helper.getExpire(key);
        helper.insertObject(key, resBO, 120 * 60L);
        Long expire2 = helper.getExpire(key);
        log.debug("登录userId:{},当前过期时间expire:{},之后过期时间expire:{}", id, expire, expire2);
        return resBO;
    }

    /**
     * @param operatorInfo 登录用户信息
     * @return 生产Token
     */
    private String createLoginToken(UserResDTO operatorInfo) {
        return createToken(operatorInfo,"LOGIN");
    }
    /**
     * @param operatorInfo 登录用户信息
     * @return 生产Token
     */
    private String createRefreshToken(UserResDTO operatorInfo) {
        return createToken(operatorInfo,"REFRESH");
    }
    /**
     * @param operatorInfo 登录用户信息
     * @return 生产Token
     */
    private String createToken(UserResDTO operatorInfo,String tokenType) {
        try {
            String token = LogUtil.getLogId();
            LoginInfoTokenBO loginInfoTokenBO = new LoginInfoTokenBO();
            loginInfoTokenBO.setUsername(operatorInfo.getLoginName());
            loginInfoTokenBO.setUserId(operatorInfo.getId());
            loginInfoTokenBO.setTokenType(tokenType);
            String systemType = LocalThreadUtils.getLocalThreadProperties(HeaderConstants.HEADER_SYSTEM_TYPE);
            loginInfoTokenBO.setSystemType(systemType);
            loginInfoTokenBO.setToken(token);
            return TokenEncodeUtil.encodeToken(loginInfoTokenBO);
        } catch (Exception e) {
            throw new RobotsServiceException(ErrorCodeEnum.SYSTEM_ERROR);
        }
    }

    private String getCodeKey(String codeType,String value){
        return ServiceConstants.REDIS_CHECK_SEND + value + codeType;
    }

    /**
     * 注册
     *
     * @param reqDTO    注册请求参数
     */
    public UserInfo register(UserRegisterReqDTO reqDTO){
        validateCode(reqDTO.getCode(),reqDTO.getEmail(),EmailTypeEnum.REGISTER);
        validatePassWord(reqDTO);
        String loginName = reqDTO.getLoginName();
        UserInfo userInfo = userManager.selectByLoginUser(loginName);
        if(Objects.nonNull(userInfo)){
            if(DeleteFlagEnum.NORMAL.getCode().equals(userInfo.getDeleteFlag())){
                throw new RobotsServiceException(ErrorCodeEnum.MSG_OUT_1000);
            }
            //直接更新
            userInfo.setEmail(reqDTO.getEmail());
            userInfo.setPassWord(reqDTO.getPassWord());
            userInfo.setUpdatedBy(userInfo.getId()+"");
            userManager.edit(userInfo);
            return userInfo;
        }
        userInfo = BeanMapperUtil.objConvert(reqDTO,UserInfo.class);
        userInfo.setDeleteFlag(DeleteFlagEnum.NORMAL.getCode());
        userInfo.setCreatedBy(ServiceConstants.SYSTEM_NAME);
        userInfo.setRememberMe(FlagEnum.FALSE.getCodeStr());
        userInfo.setUseAble(FlagEnum.TRUE.getCodeStr());
        userInfo.setIsManager(FlagEnum.FALSE.getCodeStr());
        userManager.add(userInfo);
        //清除验证码
        clearCode(reqDTO.getEmail(),EmailTypeEnum.REGISTER);
        return userInfo;
    }

    private void clearCode(String email,EmailTypeEnum codeType){
        String redisKey = getCodeKey(codeType.getCode(),email);
        helper.deleteKey(redisKey);
    }

    /**
     * 找回密码
     *
     * @param reqDTO    注册请求参数
     */
    public void forgot(UserForgotReqDTO reqDTO){


    }

    /**
     * 验证密码
     *
     * @param reqDTO  注册请求参数
     */
    private void validatePassWord(UserRegisterReqDTO reqDTO){
        String passWord = reqDTO.getPassWord();
        String passWord2 = reqDTO.getPassWord2();
        if(!passWord.equals(passWord2)){
            throw new RobotsServiceException(ErrorCodeEnum.MSG_OUT_1001);
        }
    }

    /**
     * 验证验证码
     *
     */
    private void validateCode( String code,String email,EmailTypeEnum codeType){
        String redisKey = getCodeKey(codeType.getCode(),email);
        String data = helper.queryString(redisKey);
        if(Objects.isNull(data)){
            throw new RobotsServiceException(ErrorCodeEnum.MSG_OUT_1005);
        }
        if(!data.equals(code)){
            throw new RobotsServiceException(ErrorCodeEnum.MSG_OUT_1006);
        }
    }


}