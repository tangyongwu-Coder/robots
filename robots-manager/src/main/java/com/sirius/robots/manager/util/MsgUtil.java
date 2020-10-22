package com.sirius.robots.manager.util;

import com.sirius.robots.comm.enums.msg.BaseMsgTypeEnum;
import com.sirius.robots.comm.enums.msg.MsgTypeEnum;
import com.sirius.robots.manager.model.MsgTypeBO;
import lombok.extern.slf4j.Slf4j;
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

    public static MsgTypeEnum isGroup(String content){
        for (MsgTypeEnum msgTypeEnum : MsgTypeEnum.values()) {
            String keyWord = keyWordEquals(msgTypeEnum, content);
            if(Objects.nonNull(keyWord)){
                return msgTypeEnum;
            }
        }
        return null;
    }
    public static MsgTypeBO getMsgType(String content){
        for (MsgTypeEnum msgTypeEnum : MsgTypeEnum.values()) {
            String keyWord = startWithKeyWord(msgTypeEnum, content);
            if(Objects.nonNull(keyWord)){
                return init(msgTypeEnum,null);
            }
            BaseMsgTypeEnum base = getBase(content, msgTypeEnum);
            if(Objects.nonNull(base)){
                return init(msgTypeEnum,base);
            }
        }
       return init(MsgTypeEnum.NULL,null);
    }

    private static MsgTypeBO init(MsgTypeEnum msgType,BaseMsgTypeEnum base){
        MsgTypeBO msgTypeBO = new MsgTypeBO();
        msgTypeBO.setBaseMsgTypeEnum(base);
        msgTypeBO.setMsgType(msgType);
        msgTypeBO.setIsGroup(Objects.isNull(base));
        return msgTypeBO;
    }
    public static BaseMsgTypeEnum getBase(String content,MsgTypeEnum msgTypeEnum,Boolean isStartWith){
        BaseMsgTypeEnum[] msgTypeEnums = msgTypeEnum.getMsgTypeEnums();
        if(Objects.isNull(msgTypeEnums)){
            return null;
        }
        BaseMsgTypeEnum baseMsgType = null;
        for (BaseMsgTypeEnum typeEnum : msgTypeEnums) {
            String keyWord;
            if(isStartWith){
                keyWord = startWithKeyWord(typeEnum, content);
            }else{
                keyWord = containsKeyWord(typeEnum, content);
            }
            if(Objects.isNull(keyWord)){
                continue;
            }
            if(Objects.isNull(baseMsgType)){
                baseMsgType = typeEnum;
            }else{
                baseMsgType = typeEnum.getOrder()<baseMsgType.getOrder() ? typeEnum : baseMsgType;
            }
        }
        return baseMsgType;
    }

    public static BaseMsgTypeEnum getBase(String content,MsgTypeEnum msgTypeEnum){
        return getBase(content,msgTypeEnum,Boolean.TRUE);
    }


    private static String keyWordEquals(BaseMsgTypeEnum msgTypeEnum,String msg){
        String keyWords = msgTypeEnum.getKeyWords();
        String[] keyWordList = getKeyWords(keyWords);
        for (String s : keyWordList) {
            if(msg.trim().equals(s)){
                return s;
            }
        }
        return null;
    }

    private static String startWithKeyWord(BaseMsgTypeEnum msgTypeEnum, String msg){
        String keyWords = msgTypeEnum.getKeyWords();
        String[] keyWordList = getKeyWords(keyWords);
        for (String s : keyWordList) {
            Boolean startsWith = msg.trim().startsWith(s);
            if(startsWith){
                return s;
            }
        }
        return null;
    }
    private static String containsKeyWord(BaseMsgTypeEnum msgTypeEnum, String msg){
        String keyWords = msgTypeEnum.getKeyWords();
        String[] keyWordList = getKeyWords(keyWords);
        for (String s : keyWordList) {
            Boolean contains = msg.trim().contains(s);
            if(contains){
                return s;
            }
        }
        return null;
    }
    public static String getMsg(String msg, BaseMsgTypeEnum msgTypeEnum){
        return removeStartWithKeyWord(msgTypeEnum, msg);
    }
    public static String removeStartWithKeyWord(BaseMsgTypeEnum msgTypeEnum, String msg){
        String keyWord = startWithKeyWord(msgTypeEnum, msg);
        if(Objects.isNull(keyWord)){
            return msg;
        }
        return msg.replaceFirst(keyWord,StringUtils.EMPTY).trim();
    }
    public static String removeContainsKeyWord(String msg,BaseMsgTypeEnum msgTypeEnum){
        String keyWord = containsKeyWord(msgTypeEnum, msg);
        if(Objects.isNull(keyWord)){
            return msg;
        }
        return msg.replaceFirst(keyWord,StringUtils.EMPTY).trim();
    }

    private static String[] getKeyWords(String keyWords){
        return keyWords.split(",");
    }

}