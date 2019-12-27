package com.sirius.robots.comm.res;

import com.sirius.robots.comm.enums.BaseErrorEnum;
import com.sirius.robots.comm.exception.RobotsServiceException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 返回结果
 *
 * @author 孟星魂
 * @version 5.0 createTime: 2019/12/25
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 7605700909920384168L;

    private Boolean success;
    private T data;
    private String errorCode;
    private String errorMsg;

    public Result(T data) {
        this.success = true;
        this.data = data;
    }

    public Result(String errorCode, String errorMsg) {
        this.success = false;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public static <T> Result<T> ok(T content) {
        return new Result<>(content);
    }

    public static <T> Result<T> fail(String errorCode, String errorMsg) {
        return new Result<>(errorCode, errorMsg);
    }

    public static <T> Result<T> fail(BaseErrorEnum baseErrorEnum) {
        return new Result<>(baseErrorEnum.getCode(), baseErrorEnum.getMsg());
    }

    public static <T> Result<T> fail(RobotsServiceException e) {
        return new Result<>(e.getCode(), e.getResMessage());
    }
}
