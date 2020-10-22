package com.sirius.robots.comm.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * 验证类正则表达式
 *
 * @author 孟星魂
 * @version 5.0 createTime: 2019/12/27
 */
public class ValidatePattenUtil {

    /** 邮箱校验 */
    public static final String EMAIL_REGEX ="^[a-z0-9]+([._\\\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$";
    /** 身份证格式校验 */
    public static final String ID_CARD_REGEX = "(\\d{15})|(\\d{17}[0-9|X|x])$";
    /** 密码格式校验 */
    public static final String PASS_WORD_REGEX = "^(?![^a-zA-Z]+$)(?!\\D+$).{8,}$";
    /** 姓名格式校验 */
    public static final String NAME_REGEX = "^[\u0391-\uFFE5-\\·]{1,32}$";

    /** 手机号码格式校验 */
    public static final String MOBILE_REGEX = "([1]\\d{10}$)";

    /** 用户名格式校验 */
    public static final String USER_NAME_REGEX =  "[a-zA-Z]{1}[a-zA-Z0-9_]{6,15}";




    public static Boolean validate(String patternStr,String val){
        if(StringUtils.isEmpty(val)){
            return Boolean.FALSE;
        }
        Pattern pattern = Pattern.compile(patternStr);
        if (!pattern.matcher(val).matches()){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
