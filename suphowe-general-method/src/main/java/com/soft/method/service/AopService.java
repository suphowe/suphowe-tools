package com.soft.method.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 切面测试
 * @author suphowe
 */
@Service
public class AopService {

    private static final Logger logger = LoggerFactory.getLogger(AopService.class);

    public String methodOne(String msg){
        logger.info("==>接收信息:" + msg);
        return "return methodOne";
    }
}
