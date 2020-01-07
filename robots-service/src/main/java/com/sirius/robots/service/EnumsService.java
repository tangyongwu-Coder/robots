package com.sirius.robots.service;

import com.sirius.robots.comm.enums.DeleteFlagEnum;
import com.sirius.robots.comm.enums.StatusEnum;
import com.sirius.robots.dal.model.EnumTypeInfo;
import com.sirius.robots.manager.EnumTypeManager;
import com.sirius.robots.manager.EnumsManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/1/7
 */
@Slf4j
@Service
public class EnumsService {

    @Autowired
    private EnumsManager enumsManager;

    @Autowired
    private EnumTypeManager enumTypeManager;


    public List<EnumTypeInfo> queryAllType(){
        EnumTypeInfo query = new EnumTypeInfo();
        query.setEnumStatus(StatusEnum.NORMAL.getCode());
        query.setDeleteFlag(DeleteFlagEnum.NORMAL.getCode());
       return enumTypeManager.selectBySelective(query);
    }
}