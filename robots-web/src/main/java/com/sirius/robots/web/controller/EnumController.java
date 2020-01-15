package com.sirius.robots.web.controller;

import com.sirius.robots.comm.bo.PageDTO;
import com.sirius.robots.comm.res.Result;
import com.sirius.robots.comm.util.LogUtil;
import com.sirius.robots.comm.util.VerifyParamUtil;
import com.sirius.robots.dal.model.EnumsInfo;
import com.sirius.robots.service.EnumsService;
import com.sirius.robots.service.model.req.EnumsOneReqDTO;
import com.sirius.robots.service.model.req.EnumsQueryReqDTO;
import com.sirius.robots.service.model.req.EnumsReqDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/1/6
 */
@Slf4j
@Controller
@RequestMapping(value ="/enums",consumes = MediaType.APPLICATION_JSON_VALUE)
public class EnumController {

    @Autowired
    private EnumsService enumsService;

    @ResponseBody
    @RequestMapping(value ="/type", method = RequestMethod.POST)
    public Result<List<EnumsInfo>> type(@RequestBody EnumsQueryReqDTO reqDTO){
        LogUtil.updateLogId(null);
        log.info("获取枚举-开始,请求参数:{}",reqDTO);
        long start = System.currentTimeMillis();
        List<EnumsInfo> list = enumsService.queryEnum(reqDTO);
        log.info("获取枚举-成功-耗时:{}ms,",System.currentTimeMillis()-start);
        return Result.ok(list);

    }
    @ResponseBody
    @RequestMapping(value ="/page", method = RequestMethod.POST)
    public Result<PageDTO<EnumsInfo>> page(@RequestBody EnumsQueryReqDTO reqDTO){
        LogUtil.updateLogId(null);
        log.info("获取枚举分页-开始,请求参数:{}",reqDTO);
        long start = System.currentTimeMillis();
        PageDTO<EnumsInfo> data = enumsService.queryPage(reqDTO);
        log.info("获取枚举分页-成功-耗时:{}ms,",System.currentTimeMillis()-start);
        return Result.ok(data);
    }

    @ResponseBody
    @RequestMapping(value ="/edit", method = RequestMethod.POST)
    public Result<String> edit(@RequestBody EnumsReqDTO reqDTO){
        LogUtil.updateLogId(null);
        log.info("枚举新增/修改-开始,请求参数:{}",reqDTO);
        VerifyParamUtil.validateObject(reqDTO);
        enumsService.edit(reqDTO);
        long start = System.currentTimeMillis();
        log.info("枚举新增/修改-成功-耗时:{}ms,",System.currentTimeMillis()-start);
        return Result.ok("成功");
    }

    @ResponseBody
    @RequestMapping(value ="/editOne", method = RequestMethod.POST)
    public Result<String> editOne(@RequestBody EnumsOneReqDTO reqDTO){
        LogUtil.updateLogId(null);
        log.info("枚举修改-开始,请求参数:{}",reqDTO);
        VerifyParamUtil.validateObject(reqDTO);
        enumsService.editOne(reqDTO);
        long start = System.currentTimeMillis();
        log.info("枚举修改-成功-耗时:{}ms,",System.currentTimeMillis()-start);
        return Result.ok("成功");
    }
}
