package com.sirius.robots.web.exception;


import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.sirius.robots.comm.enums.ErrorCodeEnum;
import com.sirius.robots.comm.exception.RobotsServiceException;
import com.sirius.robots.comm.res.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.omg.CORBA.portable.ApplicationException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.IntStream;

/**
 * 异常统一处理
 *
 * @author 孟星魂
 * @version 5.0 createTime: 2019/12/25
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class RobotsExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public Result handleApplicationException(final ApplicationException ex) {
        log.error(ExceptionUtils.getStackTrace(ex));
        return Result.fail(ErrorCodeEnum.SYSTEM_ERROR);
    }


    @ExceptionHandler(RuntimeException.class)
    public Result handleRuntimeException(final RuntimeException ex) {
        log.error(ExceptionUtils.getStackTrace(ex));
        return Result.fail(ErrorCodeEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public Result handleUnsupportedOperationException(final UnsupportedOperationException ex) {
        log.error(ExceptionUtils.getStackTrace(ex));
        return Result.fail(ErrorCodeEnum.PARAMS_ERROR);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result validationError(MethodArgumentNotValidException e) {
        StringBuilder buf = new StringBuilder();
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        IntStream.range(0, errors.size()).forEach(index -> {
            if (index != 0) {
                buf.append(",");
            }
            FieldError error = errors.get(index);
            String message = error.getDefaultMessage();
            if (StringUtils.isNotBlank(message) && error.getRejectedValue() != null) {
                message = message.replace("{}", error.getRejectedValue().toString());
            }
            buf.append(error.getField()).append(":").append(message);
        });
        log.error("异常:{}", buf.toString());
        return Result.fail(ErrorCodeEnum.PARAMS_ERROR.getCode(), buf.toString());
    }


    @ExceptionHandler(BindException.class)
    public Result bindException(BindException e) {
        //获取异常信息
        StringBuilder msg = new StringBuilder();
        IntStream.range(0, e.getFieldErrorCount()).forEach(index -> {
            if (index != 0) {
                msg.append(",");
            }
            FieldError error = e.getFieldErrors().get(index);
            msg.append(error.getField()).append(":").append(error.getDefaultMessage());
        });
        log.error("异常:{}", msg);
        return Result.fail(ErrorCodeEnum.PARAMS_ERROR.getCode(), msg.toString());
    }


    //缺少参数异常
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result missingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("异常:{}", "缺少必要参数,参数名称为" + e.getParameterName());
        return Result.fail(ErrorCodeEnum.PARAMS_ERROR);
    }

    //缺少参数异常
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result missingServletRequestParameterException(HttpRequestMethodNotSupportedException e) {
        log.error("异常:请求方式 {} 不支持", e.getMethod());
        return Result.fail(ErrorCodeEnum.UNSUPPORTED_REQUEST);
    }

    //参数类型不匹配
    @ExceptionHandler({HttpMessageNotReadableException.class, TypeMismatchException.class})
    public Result messageException(Exception e) {
        if (e.getCause() != null && e.getCause() instanceof UnrecognizedPropertyException) {
            String propName = ((UnrecognizedPropertyException) e.getCause()).getPropertyName();
            log.error("无法识别的属性[{}]", propName);
        } else if (e.getCause() != null && e.getCause() instanceof InvalidFormatException) {
            InvalidFormatException cause = ((InvalidFormatException) e.getCause());
            String value = cause.getValue().toString();
            String targetCls = cause.getTargetType().getSimpleName();
            log.error("值[{}]不是有效的[{}]类型", value, targetCls);
        } else {
            log.error(ExceptionUtils.getStackTrace(e));
        }
        return Result.fail(ErrorCodeEnum.PARAMS_ERROR);
    }

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class, HttpMediaTypeNotAcceptableException.class})
    public Result badRequest(Exception e) {
        log.error(ExceptionUtils.getStackTrace(e));
        return Result.fail(ErrorCodeEnum.PARAMS_ERROR);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public Result illegalArgument(IllegalArgumentException e) {
        log.error(ExceptionUtils.getStackTrace(e));
        return Result.fail(ErrorCodeEnum.PARAMS_ERROR);
    }

    @ExceptionHandler({SocketException.class})
    public Result socketException(SocketException e) {
        log.error(ExceptionUtils.getStackTrace(e));
        return Result.fail(ErrorCodeEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler({TimeoutException.class, SocketTimeoutException.class})
    public Result socketTimeoutException(Exception e) {
        log.warn("请求超时, e:", e);
        return Result.fail(ErrorCodeEnum.OUT_SERVICE_TIME_OUT);
    }

    @ExceptionHandler(RobotsServiceException.class)
    public Result loanTradeServiceException(RobotsServiceException e) {
        log.error("业务异常，error:", e);
        return Result.fail(e);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public void noHandlerFoundException(NoHandlerFoundException e) {
        log.warn("请求了不存在的接口:{}", e.getRequestURL());
    }

    @ExceptionHandler(Throwable.class)
    public Result unknownError(Throwable e) {
        log.error("发生未知的异常, e:", e);
        return Result.fail(ErrorCodeEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public Result duplicateKeyException(DuplicateKeyException e) {
        log.error("处理数据库重复记录异常：{}", ExceptionUtils.getStackTrace(e));
        return Result.fail(ErrorCodeEnum.DATA_REPEAT);
    }
}
