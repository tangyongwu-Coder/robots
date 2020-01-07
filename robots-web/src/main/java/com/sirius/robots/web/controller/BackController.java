package com.sirius.robots.web.controller;

import com.sirius.robots.web.config.PathUrl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;

/**
 * 后台页面管理
 *
 * @author 孟星魂
 * @version 5.0 createTime: 2020/1/3
 */
@Slf4j
@Controller
public class BackController {

    @RequestMapping(value ="/back/index", method = RequestMethod.GET)
    public String index(){
        return PathUrl.BACK_INDEX;
    }

    @RequestMapping(value ="/back/enums", method = RequestMethod.GET)
    public String enums(){
        return PathUrl.BACK_ENUMS;
    }


}