package com.netty.server.utils;

import com.netty.server.beans.ExceptionEnum;
import com.netty.server.beans.Result;

import java.util.HashMap;

/**
 * 返回结果
 * @author suphowe
 */
public class ResultUtil {

    /**
     * 返回成功，传入返回体具体出參
     */
    public static HashMap<String, Object> success(Object object){
        HashMap<String, Object> result = new HashMap<>(4);
        result.put("code", 200);
        result.put("msg", "success");
        result.put("data", object);
        return result;
    }

    /**
     * 自定义错误信息
     */
    public static Result error(Integer code,String msg){
        Result result = new Result();
        result.setStatus(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    /**
     * 返回异常信息，在已知的范围内
     */
    public static Result error(ExceptionEnum exceptionEnum){
        Result result = new Result();
        result.setStatus(exceptionEnum.getCode());
        result.setMsg(exceptionEnum.getMsg());
        result.setData(null);
        return result;
    }
}
