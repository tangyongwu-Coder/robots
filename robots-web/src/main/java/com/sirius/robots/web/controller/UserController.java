package com.sirius.robots.web.controller;

import com.sirius.robots.comm.req.UserLoginReqDTO;
import com.sirius.robots.comm.req.UserRegisterReqDTO;
import com.sirius.robots.comm.res.Result;
import com.sirius.robots.comm.util.LogUtil;
import com.sirius.robots.comm.util.VerifyParamUtil;
import com.sirius.robots.service.UserLoginService;
import com.sirius.robots.web.config.PathUrl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户控制层
 *
 * @author 孟星魂
 * @version 5.0 createTime: 2019/12/25
 */
@Slf4j
@Controller
@RequestMapping(value ="/user",consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private UserLoginService userLoginService;

    @ResponseBody
    @RequestMapping(value ="/login", method = RequestMethod.POST)
    public Result<String> login(@RequestBody UserLoginReqDTO reqDTO){
        LogUtil.updateLogId(null);
        log.info("用户登录,请求参数:{}",reqDTO);
        VerifyParamUtil.validateObject(reqDTO);
        userLoginService.login(reqDTO);
        long start = System.currentTimeMillis();
        log.info("用户登录-成功-耗时:{}ms,",System.currentTimeMillis()-start);
        return Result.ok("成功");
    }

    @ResponseBody
    @RequestMapping(value ="/register", method = RequestMethod.POST)
    public Result<String> register(@RequestBody UserRegisterReqDTO reqDTO){
        LogUtil.updateLogId(null);
        log.info("用户注册,请求参数:{}",reqDTO);
        VerifyParamUtil.validateObject(reqDTO);
        long start = System.currentTimeMillis();
        userLoginService.register(reqDTO);
        log.info("用户注册-成功-耗时:{}ms,",System.currentTimeMillis()-start);
        return Result.ok("成功");
    }



}