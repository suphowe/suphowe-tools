package com.soft.security.exception;

import com.soft.security.common.BaseException;
import com.soft.security.common.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 全局异常
 * @author suphowe
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SecurityException extends BaseException {

    public SecurityException(Status status) {
        super(status);
    }

    public SecurityException(Status status, Object data) {
        super(status, data);
    }

    public SecurityException(Integer code, String message) {
        super(code, message);
    }

    public SecurityException(Integer code, String message, Object data) {
        super(code, message, data);
    }
}
