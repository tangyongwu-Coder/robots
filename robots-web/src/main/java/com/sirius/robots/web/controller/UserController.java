package com.sirius.robots.web.controller;

import com.sirius.robots.service.model.req.UserForgotReqDTO;
import com.sirius.robots.service.model.req.UserLoginReqDTO;
import com.sirius.robots.service.model.req.UserRegisterReqDTO;
import com.sirius.robots.comm.res.Result;
import com.sirius.robots.comm.util.LogUtil;
import com.sirius.robots.comm.util.VerifyParamUtil;
import com.sirius.robots.dal.model.UserInfo;
import com.sirius.robots.service.UserLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
    public Result<String> login(@RequestBody UserLoginReqDTO reqDTO,HttpServletRequest request){
        LogUtil.updateLogId(null);
        log.info("用户登录,请求参数:{}",reqDTO);
        VerifyParamUtil.validateObject(reqDTO);
        UserInfo userInfo = userLoginService.login(reqDTO);
        //获取session并将userName存入session对象
        HttpSession session = request.getSession();
        session.setAttribute("userInfo", userInfo);
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

    @ResponseBody
    @RequestMapping(value ="/forgot", method = RequestMethod.POST)
    public Result<String> forgot(@RequestBody UserForgotReqDTO reqDTO){
        LogUtil.updateLogId(null);
        log.info("用户找回密码,请求参数:{}",reqDTO);
        VerifyParamUtil.validateObject(reqDTO);
        long start = System.currentTimeMillis();
        userLoginService.forgot(reqDTO);
        log.info("用户找回密码-成功-耗时:{}ms,",System.currentTimeMillis()-start);
        return Result.ok("成功");
    }

}