package com.netty.server.controller;

import com.google.gson.Gson;
import com.netty.server.beans.Result;
import com.netty.server.constant.TransCodeConstants;
import com.netty.server.service.NettyMessageService;
import com.netty.server.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import java.util.HashMap;

/**
 * netty 消息控制器
 * @author suphowe
 */
@Controller
public class NettyMessageController {

    private static final Logger logger = LoggerFactory.getLogger(NettyMessageController.class);

    @Autowired
    NettyMessageService nettyMessageService;

    /**
     * 根据交易代码进行业务分类
     * @param message 业务数据
     * @return 处理结果
     */
    public String businessCategories(HashMap<String, Object> message) {
        String transcode = String.valueOf(message.get("transcode"));
        if (transcode.isEmpty() || TransCodeConstants.TRANSCODE.get(Integer.parseInt(transcode)) == null){
            logger.info("server cannot find transcode: {}", transcode);
            return new Gson().toJson(ResultUtil.success("cannot find transcode!"));
        }
        // 业务分类
        int transCategories = Integer.parseInt(transcode.substring(0, 4));
        logger.info("服务端业务分类: {}, 交易内容:{}", transcode, TransCodeConstants.TRANSCODE.get(transCategories));
        if (transCategories == 1000){
            return new Gson().toJson(ResultUtil.success(nettyMessageService.businessCategories(message)));
        }
        return "failed found trans code";
    }
}
