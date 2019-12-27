package com.sirius.robots.web.controller;

import com.sirius.robots.service.UserLoginService;
import com.sirius.robots.web.config.PathUrl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 路径控制
 *
 * @author 孟星魂
 * @version 5.0 createTime: 2018/4/16
 */
@Slf4j
@Controller
public class PathController {
    @Autowired
    private UserLoginService userLoginService;

    @RequestMapping(value ="/", method = RequestMethod.GET)
    public String index(){
        log.info("进入首页");
        return PathUrl.INDEX;
    }

    @RequestMapping(value ="/login", method = RequestMethod.GET)
    public String login(Model model){
        log.info("登陆");
        model.addAttribute("type", "login");
        return PathUrl.LOGIN;
    }
    @RequestMapping(value ="/register", method = RequestMethod.GET)
    public String register(Model model){
        log.info("注册");
        model.addAttribute("type", "register");
        return PathUrl.LOGIN;
    }

    @RequestMapping(value ="/forgot", method = RequestMethod.GET)
    public String forgot(Model model){
        log.info("忘记密码");
        model.addAttribute("type", "forgot");
        return PathUrl.LOGIN;
    }

    public Boolean validateToken(){
        return Boolean.TRUE;
    }
}
