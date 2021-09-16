package com.netty.server.service;

import com.netty.server.constant.TransCodeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * netty 消息处理类
 * @author suphowe
 */
@Service
public class NettyMessageService {

    private static final Logger logger = LoggerFactory.getLogger(NettyMessageService.class);

    /**
     * 消息类业务分类
     * @param message 接收消息
     * @return 处理结果
     */
    public HashMap<String, Object> businessCategories(HashMap<String, Object> message) {
        int transcode = (Integer) message.get("transcode");
        logger.info("trans code: {}, trans message:{}", transcode, TransCodeConstants.TRANSCODE.get(transcode));
        logger.info("service get client message:{}", message);
        return message;
    }
}
