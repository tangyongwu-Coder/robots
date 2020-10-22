package com.sirius.robots.manager.util;

import com.sirius.robots.comm.enums.MsgErrorEnum;
import com.sirius.robots.comm.enums.msg.BaseMsgTypeEnum;
import com.sirius.robots.comm.enums.msg.MsgTypeEnum;
import com.sirius.robots.comm.enums.msg.SpendMsgTypeEnum;
import com.sirius.robots.comm.exception.RobotsServiceException;
import com.sirius.robots.comm.util.BigDecimalUtil;
import com.sirius.robots.dal.model.SpendInfo;
import com.sirius.robots.manager.model.WxUserBO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/20
 */
@Slf4j
public class SpendUtil {


    public static void main(String[] args) {
        //数字结尾
        Pattern pattern = Pattern.compile("^[\u0391-\uFFE5](.*?)\\d$");
        Matcher matcher = pattern.matcher("555");
        if(matcher.find()) {
            System.out.println(matcher.group());
        }
        System.out.println("无");
    }


    public static Boolean validate(String msg){
        //汉字开头数字结尾
        Pattern pattern = Pattern.compile("^[\u0391-\uFFE5](.*?)\\d$");
        Matcher matcher = pattern.matcher(msg);
        if(!matcher.find()) {
             return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
    public static BaseMsgTypeEnum getMsgType(String msg, MsgTypeEnum msgTypeEnum){
        BaseMsgTypeEnum base = MsgUtil.getBase(msg,msgTypeEnum,Boolean.FALSE);
        if(Objects.isNull(base)){
            return SpendMsgTypeEnum.INCOME;
        }
        return base;
    }

    public static SpendInfo getSpend(String msg, BaseMsgTypeEnum baseMsgTypeEnum,WxUserBO userBO){
        SpendInfo spendInfo  = new SpendInfo();
        if(SpendMsgTypeEnum.INCOME.getCode().equals(baseMsgTypeEnum.getCode())
                || SpendMsgTypeEnum.PAY.getCode().equals(baseMsgTypeEnum.getCode())){
            getMsgs(msg,spendInfo,baseMsgTypeEnum,userBO);
            return spendInfo;
        }else if(SpendMsgTypeEnum.QUERY.getCode().equals(baseMsgTypeEnum.getCode())
                || SpendMsgTypeEnum.COUNT.getCode().equals(baseMsgTypeEnum.getCode())){
            //判断是否带日期,不带日期查当日
            String newMsg = MsgUtil.removeContainsKeyWord(msg, baseMsgTypeEnum);
            spendInfo.setCountDate(newMsg);
        }

        return spendInfo;
    }


    private static void getMsgOne(String msg,SpendInfo spendInfo, BaseMsgTypeEnum baseMsgTypeEnum,WxUserBO userBO){
        //汉字开头数字结尾
        Boolean validate = SpendUtil.validate(msg);
        if(!validate) {
            String unknown = HelpUtil.getUnknown(MsgTypeEnum.SPEND, userBO);
            throw new RobotsServiceException(MsgErrorEnum.UN_KNOWN.getCode(),unknown);
        }
        //招行余额5526
        //招行还款2444
        //建行+2222
        //平安还款-4444
        //支付宝411
        //花呗-6666
        spendInfo.setSpendType(baseMsgTypeEnum.getCode());
        spendInfo.setRemark(msg);
        String amt = Pattern.compile("[^0-9]").matcher(msg).replaceAll("").trim();
        spendInfo.setAmt(BigDecimalUtil.getAmt(new BigDecimal(amt)));
        String newMsg = msg.replaceAll(amt, StringUtils.EMPTY);
        newMsg = MsgUtil.removeContainsKeyWord(newMsg, baseMsgTypeEnum);
        spendInfo.setChannel(newMsg);
    }
    private static void getMsgs(String msg,SpendInfo spendInfo, BaseMsgTypeEnum baseMsgTypeEnum,WxUserBO userBO){
        String[] msgs = null;
        if(msg.contains("\n")){
            msgs = msg.split("\\n");
        }
        if(msg.contains(",")){
            msgs = msg.split(",");

        }
        if(msg.contains(";")){
            msgs = msg.split(";");
        }
        if(msg.contains("，")){
            msgs = msg.split("，");
        }
        if(msg.contains("；")){
            msgs = msg.split("；");
        }
        spendInfo.setIsMultiple(Boolean.FALSE);
        if(Objects.isNull(msgs)){
            getMsgOne(msg,spendInfo,baseMsgTypeEnum,userBO);
            return;
        }
        List<SpendInfo> list = new ArrayList<>();
        for (String s : msgs) {
            SpendInfo spendInfo2 = new SpendInfo();
            getMsgOne(s,spendInfo2,baseMsgTypeEnum,userBO);
            list.add(spendInfo2);
        }
        spendInfo.setIsMultiple(Boolean.TRUE);
        spendInfo.setList(list);
    }

}