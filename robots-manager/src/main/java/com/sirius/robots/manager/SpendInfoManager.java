package com.sirius.robots.manager;

import com.sirius.robots.comm.constants.ServiceConstants;
import com.sirius.robots.comm.enums.DeleteFlagEnum;
import com.sirius.robots.comm.enums.MsgErrorEnum;
import com.sirius.robots.comm.enums.StatusEnum;
import com.sirius.robots.comm.exception.RobotsServiceException;
import com.sirius.robots.comm.util.DateUtils;
import com.sirius.robots.dal.mapper.SpendInfoMapper;
import com.sirius.robots.dal.model.SpendInfo;
import com.sirius.robots.manager.model.WxUserBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/20
 */
@Slf4j
@Component
public class SpendInfoManager {

    @Autowired
    private SpendInfoMapper spendInfoMapper;

    public List<SpendInfo> addSpend(SpendInfo spendInfo,WxUserBO userBO){
        Boolean isMultiple = spendInfo.getIsMultiple();
        String countDate = DateUtils.getCurrent(DateUtils.datePattern);
        if(isMultiple){
            List<SpendInfo> list = spendInfo.getList();
            for (SpendInfo info : list) {
                info.setCountDate(countDate);
                initSpendOne(info,userBO);
            }
            spendInfoMapper.insertBatch(list);
        }else{
            initSpendOne(spendInfo,userBO);
            spendInfoMapper.insert(spendInfo);
        }
        return queryByType(spendInfo.getSpendType(),countDate,userBO);
    }

    private void initSpendOne(SpendInfo spendInfo,WxUserBO userBO){
        spendInfo.setStatus(StatusEnum.NORMAL.getCode());
        spendInfo.setUserId(userBO.getUserId());
        spendInfo.setFamilyId(userBO.getFamilyId());
        spendInfo.setDeleteFlag(DeleteFlagEnum.NORMAL.getCode());
        spendInfo.setCreatedBy(ServiceConstants.SYSTEM_NAME);
    }

    public SpendInfo delete(SpendInfo spendInfo,WxUserBO userBO){
        Integer userId = userBO.getUserId();
        SpendInfo spendInfo1 = spendInfoMapper.selectByPrimaryKey(spendInfo.getId());
        if(Objects.isNull(spendInfo1)){
            log.error("无信息:{},user:{}",spendInfo.getRemark(),userBO.getUserId());
            throw new RobotsServiceException(MsgErrorEnum.DELETE_NULL);
        }
        if(!userBO.getIsManager()&&!userId.equals(spendInfo1.getUserId())){
            log.error("不能操作非本人信息:{},user:{}",spendInfo.getRemark(),userBO.getUserId());
            throw new RobotsServiceException(MsgErrorEnum.USER_NOT_PERMISSION);
        }
        return spendInfo1;
    }

    public List<SpendInfo> queryByType(String spendType,String countDate,WxUserBO userBO){
        SpendInfo query = new SpendInfo();
        query.setSpendType(spendType);
        query.setFamilyId(userBO.getFamilyId());
        query.setDeleteFlag(DeleteFlagEnum.NORMAL.getCode());
        query.setStatus(StatusEnum.NORMAL.getCode());
        if(StringUtils.isEmpty(countDate)){
            countDate = DateUtils.getCurrent(DateUtils.datePattern);
        }
        query.setCountDate(countDate);
        return spendInfoMapper.selectBySelective(query);
    }
    public List<SpendInfo> queryDate(WxUserBO userBO){
        SpendInfo query = new SpendInfo();
        query.setFamilyId(userBO.getFamilyId());
        query.setDeleteFlag(DeleteFlagEnum.NORMAL.getCode());
        query.setStatus(StatusEnum.NORMAL.getCode());
        return spendInfoMapper.selectBySelective(query);

    }

}