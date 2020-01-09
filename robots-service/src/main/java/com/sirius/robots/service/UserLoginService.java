package com.sirius.robots.service;

import com.sirius.robots.comm.enums.DeleteFlagEnum;
import com.sirius.robots.comm.enums.ErrorCodeEnum;
import com.sirius.robots.comm.enums.FlagEnum;
import com.sirius.robots.comm.exception.RobotsServiceException;
import com.sirius.robots.service.model.req.UserForgotReqDTO;
import com.sirius.robots.service.model.req.UserLoginReqDTO;
import com.sirius.robots.service.model.req.UserRegisterReqDTO;
import com.sirius.robots.comm.util.BeanMapperUtil;
import com.sirius.robots.comm.util.SystemConstant;
import com.sirius.robots.dal.model.UserInfo;
import com.sirius.robots.manager.UserManager;
import lombok.extern.slf4j.Slf4j;
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

    /**
     * 登录
     *
     * @param reqDTO    登录请求参数
     */
    public UserInfo login(UserLoginReqDTO reqDTO){
        String loginName = reqDTO.getLoginName();
        UserInfo userInfo = userManager.selectByLoginUser(loginName);
        if(Objects.isNull(userInfo)){
            throw new RobotsServiceException(ErrorCodeEnum.MSG_OUT_1002);
        }
        String passWord = reqDTO.getPassWord();
        String exictPassWord = userInfo.getPassWord();
        if(!passWord.equals(exictPassWord)){
            throw new RobotsServiceException(ErrorCodeEnum.MSG_OUT_1002);
        }
        return userInfo;
    }

    /**
     * 注册
     *
     * @param reqDTO    注册请求参数
     */
    public void register(UserRegisterReqDTO reqDTO){
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
            return;
        }
        userInfo = BeanMapperUtil.objConvert(reqDTO,UserInfo.class);
        userInfo.setDeleteFlag(DeleteFlagEnum.NORMAL.getCode());
        userInfo.setCreatedBy(SystemConstant.SYSTEM);
        userInfo.setRememberMe(FlagEnum.FALSE.getCodeStr());
        userInfo.setUseAble(FlagEnum.TRUE.getCodeStr());
        userInfo.setIsManager(FlagEnum.FALSE.getCodeStr());
        userManager.add(userInfo);

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

}