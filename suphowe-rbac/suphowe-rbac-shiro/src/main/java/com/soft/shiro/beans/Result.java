package com.soft.shiro.beans;

import com.soft.shiro.common.IResultCode;
import com.soft.shiro.common.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一API对象返回
 * @author suphowe
 */
@Data
@NoArgsConstructor
public class Result<T> {
    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 状态
     */
    private boolean status;

    /**
     * 返回数据
     */
    private T data;

    public Result(Integer code, String message, boolean status, T data) {
        this.code = code;
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public Result(IResultCode resultCode, boolean status, T data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.status = status;
        this.data = data;
    }

    public Result(IResultCode resultCode, boolean status) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.status = status;
        this.data = null;
    }

    public static <T> Result success() {
        return new Result<>(ResultCode.OK, true);
    }

    public static <T> Result message(String message) {
        return new Result<>(ResultCode.OK.getCode(), message, true, null);
    }

    public static <T> Result success(T data) {
        return new Result<>(ResultCode.OK, true, data);
    }

    public static <T> Result fail() {
        return new Result<>(ResultCode.ERROR, false);
    }

    public static <T> Result fail(IResultCode resultCode) {
        return new Result<>(resultCode, false);
    }

    public static <T> Result fail(Integer code, String message) {
        return new Result<>(code, message, false, null);
    }

    public static <T> Result fail(IResultCode resultCode, T data) {
        return new Result<>(resultCode, false, data);
    }

    public static <T> Result fail(Integer code, String message, T data) {
        return new Result<>(code, message, false, data);
    }
}
