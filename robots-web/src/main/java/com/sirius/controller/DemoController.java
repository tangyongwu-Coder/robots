package com.sirius.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2018/4/16
 */
@Slf4j
@Controller
public class DemoController {


    @RequestMapping(value ="/demo", method = RequestMethod.GET)
    public String hello(){
        return "demo";
    }

    @RequestMapping(value ="/hello", method = RequestMethod.GET)
    public String home(){
        return "hello";
    }

    @RequestMapping(value ="/index", method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    @RequestMapping(value ="/home2", method = RequestMethod.GET)
    @ResponseBody
    public String home2(){
        return "你好，Spring Boot";
    }


    @RequestMapping(value ="/he", method = RequestMethod.GET)
    public ModelAndView he(){
        ModelAndView nv = new ModelAndView("/he");
        return nv;
    }
}
