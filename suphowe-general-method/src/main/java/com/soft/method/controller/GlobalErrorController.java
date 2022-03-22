package com.soft.method.controller;

import com.soft.method.beans.Result;
import com.soft.method.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理
 * @author suphowe
 */
@RestController
public class GlobalErrorController implements ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(GlobalErrorController.class);

    private static final String ERROR_PATH = "/error";

    @ResponseBody
    @RequestMapping(value  = ERROR_PATH)
    public Result error(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        logger.error("exception code :{}", statusCode);
        // logger.error("exception message:{}", exception.getMessage());
        // logger.error("exception :{}", exception.toString());
        return ResultUtil.back( statusCode, exception);
    }
}
