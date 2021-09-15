package com.soft.method.exception.exception;

import com.soft.method.exception.constant.Status;
import lombok.Getter;

/**
 * 页面异常
 * @author suphowe
 */
@Getter
public class PageException extends BaseException {

    public PageException(Status status) {
        super(status);
    }

    public PageException(Integer code, String message) {
        super(code, message);
    }
}
