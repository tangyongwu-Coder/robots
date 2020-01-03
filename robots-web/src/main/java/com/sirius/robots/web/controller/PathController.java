package com.sirius.robots.web.controller;

import com.sirius.robots.web.config.PathUrl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 路径控制
 *
 * @author 孟星魂
 * @version 5.0 createTime: 2018/4/16
 */
@Slf4j
@Controller
public class PathController {

    @RequestMapping(value ="/", method = RequestMethod.GET)
    public String index(Model model){
        log.info("进入首页");
        return PathUrl.INDEX;
    }

    @RequestMapping(value ="/index", method = RequestMethod.GET)
    public String index2(Model model){
        return index(model);
    }

    @RequestMapping(value ="/login", method = RequestMethod.GET)
    public String login(){
        log.info("登陆");
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
    @RequestMapping(value ="/out", method = RequestMethod.GET)
    public String out(HttpServletRequest request){
        log.info("退出");
        HttpSession session = request.getSession();
        session.removeAttribute("userInfo");
        return PathUrl.LOGIN;
    }
    @RequestMapping(value ="/account", method = RequestMethod.GET)
    public String userInfo(){
        log.info("用户信息");
        return PathUrl.ACCOUNT;
    }



    public Boolean validateToken(){
        return Boolean.TRUE;
    }
}
