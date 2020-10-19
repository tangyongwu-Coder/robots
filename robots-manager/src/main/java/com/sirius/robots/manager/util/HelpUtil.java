package com.sirius.robots.manager.util;

import com.sirius.robots.comm.constants.ServiceConstants;
import com.sirius.robots.comm.enums.MsgTypeEnum;
import com.sirius.robots.manager.model.WxUserBO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/19
 */
@Slf4j
public class HelpUtil {



    public static String getHelp1(WxUserBO userBO){
        StringBuilder sb  = new StringBuilder();
        getHelp(sb,userBO);
        return sb.toString();
    }
    public static String getHelp2(WxUserBO userBO){
        StringBuilder sb  = new StringBuilder();
        sb.append(ServiceConstants.MSG_NULL_RES).append("\n");
        getHelp(sb,userBO);
        return sb.toString();
    }

    private static void getHelp(StringBuilder sb,WxUserBO userBO){
        sb.append("目前支持的关键字有：\n");
        MsgTypeEnum[] values = MsgTypeEnum.values();
        for (MsgTypeEnum value : values) {
            if(MsgTypeEnum.NULL.equals(value)){
                continue;
            }
            if(!userBO.getIsManager()&&value.getManager()){
                continue;
            }
            sb.append(value.getKeyWords()).append("-->").append(value.getMsg()).append("\n");
        }
    }
}