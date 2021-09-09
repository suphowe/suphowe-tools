package com.soft.method.exception;

import com.soft.method.beans.Result;
import com.soft.method.utils.ResultUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 记录异常
 * @author suphowe
 */
@ControllerAdvice
public class ExceptionHandle {

    /*---使用@ControllerAdvice,使Spring能加载该类,同时我们将所有捕获的异常统一返回结果Result这个实体---*/

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    /**
     * 判断错误是否是已定义的已知错误，不是则由未知错误代替，同时记录在log中
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result exceptionGet(Exception e) {
        if (e instanceof DescribeException) {
            DescribeException myException = (DescribeException) e;
            return ResultUtil.error(myException.getCode(), myException.getMessage());
        }

        logger.error("===>系统异常:", e);
        return ResultUtil.error(ExceptionEnum.UNKNOW_ERROR);
    }
}
