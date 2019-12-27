package com.sirius.robots.comm.util;

import com.sirius.robots.comm.enums.ErrorCodeEnum;
import com.sirius.robots.comm.exception.RobotsServiceException;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Objects;
import java.util.Set;

/**
 * 参数校验
 *
 * @author 孟星魂
 * @version 5.0 createTime: 2019/12/26
 */
public class VerifyParamUtil {

    private final static ValidatorFactory FACTORY = Validation.buildDefaultValidatorFactory();


    /**
     * 校验对象属性
     * @param object 需要校验的对象
     */
    public static void validateObject(Object object) {
        Validator validator = FACTORY.getValidator();
        Set<ConstraintViolation<Object>> violations = validator.validate(object);
        if (violations.size() == 0) {return;}
        throw new RobotsServiceException(ErrorCodeEnum.PARAMS_ERROR.getCode(),violations.iterator().next().getMessage());
    }

    /**
     * 按分组校验对象属性
     * @param object 对象
     * @param groups 分组class
     */
    public static void validateObject(Object object,Class<?>... groups) {
        Validator validator = FACTORY.getValidator();
        Set<ConstraintViolation<Object>> violations = validator.validate(object,groups);
        if (violations.size() == 0) {return;}
        throw new RobotsServiceException(ErrorCodeEnum.PARAMS_ERROR.getCode(),violations.iterator().next().getMessage());
    }

    /**
     * 校验参数是否非空
     *
     * @param object  待验证对象
     * @param message 提示消息
     */
    public static void validateNull(Object object,String message){
        if (Objects.isNull(object)) {
            throw new RobotsServiceException(ErrorCodeEnum.PARAMS_ERROR.getCode(),message);
        }
    }

    /**
     * 校验参数是否非空
     *
     * @param string  待验证对象
     * @param message 提示消息
     */
    public static void validateNull(String string,String message) {
        if (StringUtils.isEmpty(string)) {
            throw new RobotsServiceException(ErrorCodeEnum.PARAMS_ERROR.getCode(),message);
        }
    }
}
