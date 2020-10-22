package com.sirius.robots.manager.util;

import com.sirius.robots.comm.constants.ServiceConstants;
import com.sirius.robots.comm.enums.msg.BaseMsgTypeEnum;
import com.sirius.robots.comm.enums.msg.MsgTypeEnum;
import com.sirius.robots.dal.model.MsgAuthInfo;
import com.sirius.robots.manager.model.WxUserBO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/19
 */
@Slf4j
public class HelpUtil {



    public static String getHelp(WxUserBO userBO){
        StringBuilder sb  = new StringBuilder();
        getMsgType(sb,userBO);
        return sb.toString();
    }
    public static String getNull(WxUserBO userBO){
        StringBuilder sb  = new StringBuilder();
        sb.append(ServiceConstants.MSG_NULL_RES).append("\n");
        getMsgType(sb,userBO);
        return sb.toString();
    }

    public static String success(MsgTypeEnum msgTypeEnum,WxUserBO userBO){
        StringBuilder sb  = new StringBuilder();
        sb.append(msgTypeEnum.getMsg()+"成功！").append("\n");
        getMsgType(sb,userBO);
        return sb.toString();
    }
    public static String getGroup(MsgTypeEnum msgTypeEnum,WxUserBO userBO){
        StringBuilder sb  = new StringBuilder();
        sb.append("进入"+msgTypeEnum.getMsg()+"=>>").append("\n");
        getBaseMsgType(sb,msgTypeEnum,userBO);
        return sb.toString();
    }
    public static String noGroupOut(WxUserBO userBO){
        StringBuilder sb  = new StringBuilder();
        sb.append(ServiceConstants.NO_GROUP_OUT).append("\n");
        getMsgType(sb,userBO);
        return sb.toString();
    }


    public static String getGroupRepeat(MsgAuthInfo currGroup,MsgTypeEnum msgTypeBO){
        StringBuilder sb  = new StringBuilder();
        sb.append("当前已经处在<").append(MsgTypeEnum.explain(currGroup.getMsgType()).getMsg())
                .append(">中,请退出后再重新进入<").append(msgTypeBO.getMsg()).append(">");
        return sb.toString();
    }

    public static String getBaseMsgByType(MsgTypeEnum msgTypeEnum,WxUserBO userBO){
        StringBuilder sb  = new StringBuilder();
        sb.append("您好,您已进入").append(msgTypeEnum.getMsg()).append("环节").append("\n");
        getBaseMsgType(sb,msgTypeEnum,userBO);
        return sb.toString();
    }

    public static String getUnknown(MsgTypeEnum msgTypeEnum,WxUserBO userBO){
        StringBuilder sb  = new StringBuilder();
        sb.append(ServiceConstants.MSG_UNKNOWN).append("\n");
        getBaseMsgType(sb,msgTypeEnum,userBO);
        return sb.toString();
    }


    private static void getBaseMsgType(StringBuilder sb,MsgTypeEnum msgTypeEnum,WxUserBO userBO){
        sb.append("您可以说：\n");
        BaseMsgTypeEnum[] values = msgTypeEnum.getMsgTypeEnums();
        for (BaseMsgTypeEnum value : values) {
            if(MsgTypeEnum.NULL.equals(value)){
                continue;
            }
            if(!userBO.getIsManager()&&value.getManager()){
                continue;
            }
            sb.append(value.getMsg()).append("-->").append(value.getKeyWords()).append("\n");
        }
    }


    private static void getMsgType(StringBuilder sb,WxUserBO userBO){
        sb.append("目前支持的分组功能(关键字匹配)：\n");
        MsgTypeEnum[] values = MsgTypeEnum.values();
        for (MsgTypeEnum value : values) {
            if(MsgTypeEnum.NULL.equals(value)){
                continue;
            }
            if(!userBO.getIsManager()&&value.getManager()){
                continue;
            }
            sb.append(value.getMsg()).append("-->").append(value.getKeyWords()).append("\n");
        }
    }
}