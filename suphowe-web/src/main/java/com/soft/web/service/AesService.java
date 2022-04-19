package com.soft.web.service;

import com.soft.web.entity.ResponseMsg;
import com.soft.web.util.AesUtils;
import com.soft.web.util.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * 接口数据加解密
 * @author suphowe
 */
@Service
public class AesService {

    private static final Logger logger = LoggerFactory.getLogger(AesService.class);

    @Value("${module.boots.response.aes.key}")
    private String key;

    /**
     * 获取加密数据
     * @param id 消息id
     * @param username 用户
     * @return 加密数据
     */
    public ResponseMsg getEncrypt(String id, String username) {
        // data 数据
        LinkedHashMap<String, Object> data = new LinkedHashMap<>(8);
        data.put("id", id);
        data.put("username", username);

        ResponseBody responseMsg = new ResponseBody("200", data);
        return responseMsg.createDataBody();
    }

    /**
     * 获取解密数据
     * @param message 加密消息
     * @return 解密数据
     */
    public ResponseMsg getDecrypt(String message) {
        ResponseBody responseMsg = new ResponseBody("200", AesUtils.decrypt(message, key));
        return responseMsg.createDataBody();
    }

}
