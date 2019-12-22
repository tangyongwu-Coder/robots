package com.sirius.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Administrator on 2018/10/31 0031.
 */
@Slf4j
@Controller
public class UrlController {

    @RequestMapping(value ="/", method = RequestMethod.GET)
    public String index(){
        log.info("进入首页");
        return "index";
    }

    @RequestMapping(value ="/login", method = RequestMethod.GET)
    public String login(){
        log.info("登陆");
        return "user/login";
    }

    @RequestMapping(value ="/forgot", method = RequestMethod.GET)
    public String forgot(){
        log.info("忘记密码");
        return "user/forgot";
    }
    @RequestMapping(value ="/forgot2", method = RequestMethod.GET)
    public String forgot2(){
        log.info("忘记密码2");
        return "forgot2";
    }

    @RequestMapping(value ="/signUp", method = RequestMethod.GET)
    public String signUp(){
        log.info("注册");
        return "user/signUp";
    }
}
