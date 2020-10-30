package com.sirius.robots.web.controller;

import com.google.common.base.Throwables;
import com.sirius.robots.comm.enums.ErrorCodeEnum;
import com.sirius.robots.comm.exception.RobotsServiceException;
import com.sirius.robots.comm.util.LocalThreadUtils;
import com.sirius.robots.service.model.req.*;
import com.sirius.robots.comm.res.Result;
import com.sirius.robots.comm.util.LogUtil;
import com.sirius.robots.comm.util.VerifyParamUtil;
import com.sirius.robots.dal.model.UserInfo;
import com.sirius.robots.service.UserLoginService;
import com.sirius.robots.service.model.res.UserResDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


/**
 * 用户控制层
 *
 * @author 孟星魂
 * @version 5.0 createTime: 2019/12/25
 */
@Slf4j
@RestController
@RequestMapping(value ="/user",consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private UserLoginService userLoginService;

    @ResponseBody
    @RequestMapping(value ="/login", method = RequestMethod.POST)
    public Result<UserResDTO> login(@RequestBody UserLoginReqDTO reqDTO){
        try{
            LogUtil.updateLogId(null);
            log.info("用户登录,请求参数:{}",reqDTO);
            VerifyParamUtil.validateObject(reqDTO);
            long start = System.currentTimeMillis();
            UserResDTO userInfo = userLoginService.login(reqDTO);
            log.info("用户登录-成功-耗时:{}ms,",System.currentTimeMillis()-start);
            return Result.ok(userInfo);
        }catch (RobotsServiceException e){
            return Result.fail(e);
        }catch (Exception e){
            return Result.fail(ErrorCodeEnum.SYSTEM_ERROR);
        }

    }

    @ResponseBody
    @RequestMapping(value ="/register", method = RequestMethod.POST)
    public Result<UserInfo> register(@RequestBody UserRegisterReqDTO reqDTO){
        try{
            LogUtil.updateLogId(null);
            log.info("用户注册,请求参数:{}",reqDTO);
            VerifyParamUtil.validateObject(reqDTO);
            long start = System.currentTimeMillis();
            UserInfo register = userLoginService.register(reqDTO);
            log.info("用户注册-成功-耗时:{}ms,",System.currentTimeMillis()-start);
            return Result.ok(register);
        }catch (RobotsServiceException e){
            return Result.fail(e);
        }catch (Exception e){
            return Result.fail(ErrorCodeEnum.SYSTEM_ERROR);
        }
    }

    @ResponseBody
    @RequestMapping(value ="/forgot", method = RequestMethod.POST)
    public Result<String> forgot(@RequestBody UserForgotReqDTO reqDTO){
        try{
            LogUtil.updateLogId(null);
            log.info("用户找回密码,请求参数:{}",reqDTO);
            VerifyParamUtil.validateObject(reqDTO);
            long start = System.currentTimeMillis();
            userLoginService.forgot(reqDTO);
            log.info("用户找回密码-成功-耗时:{}ms,",System.currentTimeMillis()-start);
            return Result.ok("成功");
        }catch (RobotsServiceException e){
            return Result.fail(e);
        }catch (Exception e){
            return Result.fail(ErrorCodeEnum.SYSTEM_ERROR);
        }

    }

    @ResponseBody
    @RequestMapping(value ="/sendCode", method = RequestMethod.POST)
    public Result<Boolean> sendCode(@RequestBody SendCodeReqDTO reqDTO){
        try{
            LogUtil.updateLogId(null);
            log.info("发送验证码,请求参数:{}",reqDTO);
            VerifyParamUtil.validateObject(reqDTO);
            long start = System.currentTimeMillis();
            userLoginService.sendCode(reqDTO);
            log.info("发送验证码-成功-耗时:{}ms",System.currentTimeMillis()-start);
            return Result.ok(Boolean.TRUE);
        }catch (RobotsServiceException e){
            return Result.fail(e);
        }catch (Exception e){
            return Result.fail(ErrorCodeEnum.SYSTEM_ERROR);
        }

    }

    @ResponseBody
    @RequestMapping(value ="/refreshToken", method = RequestMethod.POST)
    public Result<Boolean> refreshToken(){
        try{
            LogUtil.updateLogId(null);
            log.info("刷新TOKEN,请求参数:{}");
            long start = System.currentTimeMillis();
            String token = LocalThreadUtils.getToken();
            userLoginService.refreshToken(token);
            log.info("刷新TOKEN-成功-耗时:{}ms",System.currentTimeMillis()-start);
            return Result.ok(Boolean.TRUE);
        }catch (RobotsServiceException e){
            return Result.fail(e);
        }catch (Exception e){
            return Result.fail(ErrorCodeEnum.SYSTEM_ERROR);
        }

    }

    @ResponseBody
    @RequestMapping(value = "/validateToken", method = RequestMethod.POST)
    public Result<String> validateToken(@RequestBody ValidateTokenReqBO reqBO) {

        try {
            LogUtil.updateLogId(null);
            log.info("校验当前token开始,参数:{}", reqBO);
            Long startTime = System.currentTimeMillis();
            userLoginService.validateToken(reqBO.getUserId(),reqBO.getSystemType(), reqBO.getToken());
            log.info("校验当前token结束,耗时:{}", (System.currentTimeMillis() - startTime));
            return Result.ok("成功");
        } catch (RobotsServiceException e) {
            return Result.fail(e);
        } catch (Exception e) {
            log.error("校验当前token结束,cause:{}", Throwables.getStackTraceAsString(e));
            return Result.fail(ErrorCodeEnum.SYSTEM_ERROR);
        }
    }


}