package com.sirius.robots.manager.util;

import com.sirius.robots.comm.enums.MsgTypeEnum;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/15
 */
@Slf4j
public class MsgUtil {

    public static Pattern NUMBER_EXPRESS_PATTERN = Pattern.compile("(^(-?\\d+)(\\.\\d+)?$)");
    /**
     * 处理long类型
     *
     * @param val   值
     * @return      long值
     */
    public static Integer isNumber(String val){
        if (StringUtils.isEmpty(val)){
            return null;
        }
        if (!NUMBER_EXPRESS_PATTERN.matcher(val).matches()){
            return null;
        }
        return Integer.parseInt(val);
    }

    public static MsgTypeEnum getMsgType(WxMpXmlMessage wxMessage){
        String content = wxMessage.getContent();
        MsgTypeEnum msgType = null;
        for (MsgTypeEnum msgTypeEnum : MsgTypeEnum.values()) {
            String keyWord = validateKeyWord(msgTypeEnum, content);
            if(Objects.isNull(keyWord)){
                continue;
            }
            if(Objects.isNull(msgType)){
                msgType = msgTypeEnum;
            }else{
                msgType = msgTypeEnum.getOrder()<msgType.getOrder() ? msgTypeEnum : msgType;
            }
        }
       return Objects.isNull(msgType) ? MsgTypeEnum.NULL : msgType;
    }

    private static String validateKeyWord(MsgTypeEnum msgTypeEnum,String msg){
        String keyWords = msgTypeEnum.getKeyWords();
        String[] keyWordList = getKeyWords(keyWords);
        for (String s : keyWordList) {
            Boolean contains = msg.trim().startsWith(s);
            if(contains){
                return s;
            }
        }
        return null;
    }

    public static String removeKeyWord(MsgTypeEnum msgTypeEnum,String msg){
        String keyWord = validateKeyWord(msgTypeEnum, msg);
        if(Objects.isNull(keyWord)){
            return msg;
        }
        return msg.replaceAll(keyWord,StringUtils.EMPTY).trim();
    }

    private static String[] getKeyWords(String keyWords){
        return keyWords.split(",");
    }

}