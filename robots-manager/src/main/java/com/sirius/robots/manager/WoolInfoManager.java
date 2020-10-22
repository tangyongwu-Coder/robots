package com.sirius.robots.manager;

import com.github.pagehelper.PageHelper;
import com.sirius.robots.comm.bo.PageDTO;
import com.sirius.robots.comm.constants.ServiceConstants;
import com.sirius.robots.comm.enums.*;
import com.sirius.robots.comm.enums.msg.MsgTypeEnum;
import com.sirius.robots.comm.enums.msg.WoolMsgTypeEnum;
import com.sirius.robots.comm.exception.RobotsServiceException;
import com.sirius.robots.comm.util.DateUtils;
import com.sirius.robots.dal.mapper.WoolInfoMapper;
import com.sirius.robots.dal.model.WoolInfo;
import com.sirius.robots.manager.model.WxUserBO;
import com.sirius.robots.manager.util.HelpUtil;
import com.sirius.robots.manager.util.MsgUtil;
import com.sirius.robots.manager.util.WoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/15
 */
@Slf4j
@Component
public class WoolInfoManager {

    @Autowired
    private WoolInfoMapper woolInfoMapper;

    public String addWool(String msg, WoolMsgTypeEnum msgType, WxUserBO userBO){
        Integer userId = userBO.getUserId();
        String woolMsg = MsgUtil.getMsg(msg, msgType);
        //首先查询线报是否存在
        WoolInfo woolInfo = queryByWoolMsg(woolMsg,userId);
        Boolean isManager = userBO.getIsManager();
        if(Objects.isNull(woolInfo)){
            woolInfo = WoolUtil.getWool(woolMsg);
            if(Objects.isNull(woolInfo.getNextTime())){
                String unknown = HelpUtil.getUnknown(MsgTypeEnum.WOOL, userBO);
                throw new RobotsServiceException(MsgErrorEnum.UN_KNOWN.getCode(),unknown);
            }
            woolInfo.setChannelType(isManager?ChannelTypeEnum.SYSTEM.getCode() : ChannelTypeEnum.USER.getCode());
            woolInfo.setCreatedBy(ServiceConstants.SYSTEM_NAME);
            woolInfo.setWoolStatus(StatusEnum.NORMAL.getCode());
            woolInfo.setDeleteFlag(DeleteFlagEnum.NORMAL.getCode());
            woolInfo.setCountUserId(userId);
            woolInfoMapper.insert(woolInfo);

        }else{
            if(DeleteFlagEnum.DELETE.getCode().equals(woolInfo.getDeleteFlag())
                    ||StatusEnum.DISABLE.getCode().equals(woolInfo.getWoolStatus())){
                woolInfo.setDeleteFlag(DeleteFlagEnum.NORMAL.getCode());
                woolInfo.setWoolStatus(StatusEnum.NORMAL.getCode());
                woolInfo.setUpdatedBy(ServiceConstants.SYSTEM_NAME);
                woolInfoMapper.updateByPrimaryKeySelective(woolInfo);
            }
        }
        return getRes(woolInfo,msgType,isManager);

    }

    public String deleteWool(String msg,WoolMsgTypeEnum msgType,WxUserBO userBO){
        WoolInfo woolInfo = getWool(msg,msgType,userBO);
        if(Objects.nonNull(woolInfo)){
            woolInfo.setUpdatedBy(ServiceConstants.SYSTEM_NAME);
            woolInfoMapper.delete(woolInfo);
        }
        return msgType.getMsg()+"成功。";
    }
    public String disableWool(String msg,WoolMsgTypeEnum msgType,WxUserBO userBO){
        if(!userBO.getIsManager()){
            throw new RobotsServiceException(MsgErrorEnum.USER_NOT_PERMISSION);
        }
        WoolInfo woolInfo = getWool(msg,msgType,userBO);
        if(Objects.nonNull(woolInfo)){
            woolInfo.setUpdatedBy(ServiceConstants.SYSTEM_NAME);
            woolInfo.setWoolStatus(StatusEnum.DISABLE.getCode());
            woolInfoMapper.updateByPrimaryKeySelective(woolInfo);
        }
        return msgType.getMsg()+"成功。";
    }
    public String enableWool(String msg,WoolMsgTypeEnum msgType,WxUserBO userBO){
        if(!userBO.getIsManager()){
            throw new RobotsServiceException(MsgErrorEnum.USER_NOT_PERMISSION);
        }
        WoolInfo woolInfo = getWool(msg,msgType,userBO);
        if(Objects.nonNull(woolInfo)){
            woolInfo.setUpdatedBy(ServiceConstants.SYSTEM_NAME);
            woolInfo.setWoolStatus(StatusEnum.NORMAL.getCode());
            woolInfoMapper.updateByPrimaryKeySelective(woolInfo);
        }
        return msgType.getMsg()+"成功。";
    }

    private WoolInfo getWool(String msg,WoolMsgTypeEnum msgType,WxUserBO userBO){
        String woolMsg = MsgUtil.getMsg(msg, msgType);
        Integer id = MsgUtil.isNumber(woolMsg);
        Integer userId = userBO.getUserId();
        WoolInfo woolInfo = null;
        if(Objects.nonNull(id)){
            woolInfo = woolInfoMapper.selectByPrimaryKey(id);
            if(!userBO.getIsManager()&&!userId.equals(woolInfo.getCountUserId())){
                log.error("不能操作非本人线报msg:{},user:{}",msg,userBO.getUserId());
                throw new RobotsServiceException(MsgErrorEnum.USER_NOT_PERMISSION);
            }
        }else{
            woolInfo = queryByWoolMsg(woolMsg,userId);
        }
        return woolInfo;
    }
    private WoolInfo queryByWoolMsg(String woolMsg,Integer userId){
        WoolInfo query = new WoolInfo();
        query.setWoolMsg(woolMsg);
        query.setCountUserId(userId);
        List<WoolInfo> woolInfos = woolInfoMapper.selectBySelective(query);
        if(CollectionUtils.isEmpty(woolInfos)){
            return null;
        }
        return woolInfos.get(0);
    }

    public String queryWool(String msg,WoolMsgTypeEnum msgType,WxUserBO userBO){
        Integer userId = userBO.getUserId();
        String woolMsg = MsgUtil.getMsg(msg, msgType);
        WoolInfo query = new WoolInfo();
        query.setWoolMsg(woolMsg);
        query.setCountUserId(userId);
        query.setDeleteFlag(DeleteFlagEnum.NORMAL.getCode());
        Boolean isManager = userBO.getIsManager();
        if(!isManager){
            query.setWoolStatus(StatusEnum.NORMAL.getCode());
        }
        PageDTO pageDTO = new PageDTO();
        pageDTO.setPage(1);
        pageDTO.setPageSize(10);
        PageDTO<WoolInfo> page = pageBySelective(query, pageDTO);
        if (Objects.isNull(page) || CollectionUtils.isEmpty(page.getResult())) {
            throw new RobotsServiceException(MsgErrorEnum.RESULT_NULL);
        }
        List<WoolInfo> result = page.getResult();
       return listToString(result,isManager);
    }

    public String queryTodayWool(String msg,WoolMsgTypeEnum msgType,WxUserBO userBO){
        Integer userId = userBO.getUserId();
        String woolMsg = MsgUtil.getMsg(msg, msgType);
        WoolInfo query = new WoolInfo();
        query.setWoolMsg(woolMsg);
        query.setCountUserId(userId);
        query.setDeleteFlag(DeleteFlagEnum.NORMAL.getCode());
        Boolean isManager = userBO.getIsManager();
        if(!isManager){
            query.setWoolStatus(StatusEnum.NORMAL.getCode());
        }
        query.setNextTime(DateUtils.getCurrentDate(DateUtils.dayPattern));
        List<WoolInfo> list = woolInfoMapper.selectBySelective(query);
        if (CollectionUtils.isEmpty(list)) {
            throw new RobotsServiceException(MsgErrorEnum.RESULT_NULL);
        }
        return listToString(list,isManager);
    }

    private String getRes(WoolInfo woolInfo,WoolMsgTypeEnum msgType,Boolean isManager){
        StringBuilder sb = new StringBuilder();
        sb.append("线报录入成功!").append("\n");
        String woolStr = toString(woolInfo,isManager);
        sb.append(woolStr).append("\n");
        return sb.toString();
    }
    private String listToString(List<WoolInfo> list,Boolean isManager){
        StringBuilder sb = new StringBuilder();
        for (WoolInfo woolInfo : list) {
            String s = toString(woolInfo,isManager);
            sb.append(s).append("\n");
        }
        return sb.toString();
    }
    private String toString(WoolInfo woolInfo,Boolean isManager){
        StringBuilder sb = new StringBuilder();
        String channelType = woolInfo.getChannelType();
        if(ChannelTypeEnum.SYSTEM.getCode().equals(channelType)){
            sb.append("(系统)").append("线报编号：").append(woolInfo.getId()).append("\n");
        }else{
            sb.append("线报编号：").append(woolInfo.getId()).append("\n");
        }
        if(isManager){
            sb.append("线报状态：").append(StatusEnum.explain(woolInfo.getWoolStatus())).append("\n");
        }
        sb.append("线报：").append(woolInfo.getWoolMsg()).append("\n");
        sb.append("线报时间：").append(woolInfo.getWoolDate()).append("\n");
        sb.append("下次时间：").append(DateUtils.format(woolInfo.getNextTime(),DateUtils.allPattern));
        return sb.toString();
    }

    private PageDTO<WoolInfo> pageBySelective(WoolInfo query, PageDTO pageDTO){
        pageDTO.checkPage();
        PageHelper.startPage(pageDTO.getPage(), pageDTO.getPageSize());
        List<WoolInfo> list = woolInfoMapper.selectBySelective(query);
        return new PageDTO<>(list);
    }








}