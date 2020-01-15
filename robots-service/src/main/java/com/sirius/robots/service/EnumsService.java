package com.sirius.robots.service;

import com.sirius.robots.comm.bo.PageDTO;
import com.sirius.robots.comm.constants.ServiceConstants;
import com.sirius.robots.comm.enums.DeleteFlagEnum;
import com.sirius.robots.comm.enums.ErrorCodeEnum;
import com.sirius.robots.comm.enums.StatusEnum;
import com.sirius.robots.comm.exception.RobotsServiceException;
import com.sirius.robots.comm.util.BeanMapperUtil;
import com.sirius.robots.comm.util.EditListUtil;
import com.sirius.robots.dal.model.EnumsInfo;
import com.sirius.robots.manager.EnumsManager;
import com.sirius.robots.service.model.bo.EnumsBO;
import com.sirius.robots.service.model.req.EnumsOneReqDTO;
import com.sirius.robots.service.model.req.EnumsQueryReqDTO;
import com.sirius.robots.service.model.req.EnumsReqDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 枚举服务
 *
 * @author 孟星魂
 * @version 5.0 createTime: 2020/1/7
 */
@Slf4j
@Service
public class EnumsService {

    @Autowired
    private EnumsManager enumsManager;

    /**
     * 查询所有枚举类型
     *
     * @return 枚举类型集合
     */
    public List<EnumsInfo> queryEnum(EnumsQueryReqDTO reqDTO){
        EnumsInfo query = BeanMapperUtil.objConvert(reqDTO, EnumsInfo.class);
        query.setEnumStatus(StatusEnum.NORMAL.getCode());
        query.setDeleteFlag(DeleteFlagEnum.NORMAL.getCode());
       return enumsManager.selectBySelective(query);
    }

    /**
     * 查询所有枚举类型
     *
     * @return 枚举类型集合
     */
    public PageDTO<EnumsInfo> queryPage(EnumsQueryReqDTO reqDTO){
        EnumsInfo query = BeanMapperUtil.objConvert(reqDTO, EnumsInfo.class);
        query.setDeleteFlag(DeleteFlagEnum.NORMAL.getCode());
        return enumsManager.selectByPage(query,reqDTO);
    }
    /**
     * 修改枚举
     *
     * @param reqDTO    枚举请求对象
     */
    public void editOne(EnumsOneReqDTO reqDTO){
        EnumsInfo existOne = enumsManager.selectByPrimaryKey(reqDTO.getId());
        if(Objects.isNull(existOne)){
            throw new RobotsServiceException(ErrorCodeEnum.DATA_NOT_EXITS);
        }
        BeanMapperUtil.copyNoNull(reqDTO,existOne);
        enumsManager.edit(existOne);
    }
    /**
     * 新增/修改枚举
     *
     * @param reqDTO    枚举请求对象
     */
    public void edit(EnumsReqDTO reqDTO){
        String enumType = reqDTO.getEnumType();
        List<EnumsBO> enums = reqDTO.getEnums();
        List<EnumsInfo> records = BeanMapperUtil.mapList(enums, EnumsInfo.class);
        for (EnumsInfo record : records) {
            record.setEnumType(enumType);
            record.setEnumStatus(StatusEnum.NORMAL.getCode());
            record.setDeleteFlag(DeleteFlagEnum.NORMAL.getCode());
        }

        EnumsInfo query = new EnumsInfo();
        query.setEnumType(enumType);
        List<EnumsInfo> existList = enumsManager.selectBySelective(query);
        Map<String, List<EnumsInfo>> map = EditListUtil.editSeparate(
                existList, records, "enumType,enumKey", ServiceConstants.SYSTEM_NAME);
        enumsManager.editConfig(map);
    }
}