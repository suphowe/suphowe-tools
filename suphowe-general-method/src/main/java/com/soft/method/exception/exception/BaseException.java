package com.soft.method.exception.exception;

import com.soft.method.exception.constant.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 异常基类
 * @author suphowe
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseException extends RuntimeException {
    private Integer code;
    private String message;

    public BaseException(Status status) {
        super(status.getMessage());
        this.code = status.getCode();
        this.message = status.getMessage();
    }

    public BaseException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
