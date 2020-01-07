package com.sirius.robots.web.controller;

import com.sirius.robots.comm.bo.PageDTO;
import com.sirius.robots.comm.req.PageQueryReqDTO;
import com.sirius.robots.comm.res.Result;
import com.sirius.robots.comm.util.LogUtil;
import com.sirius.robots.dal.model.EnumTypeInfo;
import com.sirius.robots.service.EnumsService;
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
    public Result<List<EnumTypeInfo>> type(){
        LogUtil.updateLogId(null);
        log.info("获取枚举类型-开始");
        long start = System.currentTimeMillis();
        List<EnumTypeInfo> list = enumsService.queryAllType();
        log.info("获取枚举类型-成功-耗时:{}ms,",System.currentTimeMillis()-start);
        return Result.ok(list);

    }
    @ResponseBody
    @RequestMapping(value ="/page", method = RequestMethod.POST)
    public Result<PageDTO<Map<String,Object>>> page(@RequestBody PageQueryReqDTO reqDTO){
        LogUtil.updateLogId(null);
        log.info("获取枚举,请求参数:{}",reqDTO);
        long start = System.currentTimeMillis();
        List<Map<String, Object>> data = init(reqDTO.getPageDTO());
        log.info("获取枚举-成功-耗时:{}ms,",System.currentTimeMillis()-start);
        PageDTO<Map<String, Object>> pageInfo = new PageDTO<>();
        pageInfo.setPage(1);
        pageInfo.setPageSize(10);
        pageInfo.setCount(100L);
        pageInfo.setPages(10);
        pageInfo.setResult(data);
        return Result.ok(pageInfo);
    }

    private List<Map<String,Object>> init(PageDTO pageDTO){
        List<Map<String,Object>> list = new ArrayList<>();
        Integer page = pageDTO.getPage();
        Integer pageSize = pageDTO.getPageSize();
        for (int i = (page-1) * pageSize; i < pageSize *page; i++) {
            list.add(initMap(i));
        }

        return list;
    }

    private Map<String,Object> initMap(int i){
        Random random = new Random();
        Map<String,Object> map = new HashMap<>();
        map.put("idx",i);
        map.put("random",random.nextInt(5));
        map.put("name",random.nextInt(2));
        map.put("phone",random.nextInt(10));
        return map;
    }
}
