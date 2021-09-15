package com.soft.method.exception.exception;

import com.soft.method.exception.constant.Status;
import lombok.Getter;

/**
 * JSON异常
 * @author suphowe
 */
@Getter
public class JsonException extends BaseException {

    public JsonException(Status status) {
        super(status);
    }

    public JsonException(Integer code, String message) {
        super(code, message);
    }
}
