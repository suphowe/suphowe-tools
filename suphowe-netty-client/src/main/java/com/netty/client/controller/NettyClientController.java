package com.netty.client.controller;

import com.netty.client.service.NettyClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * netty 客户端http信息接收
 * @author suphowe
 */
@Controller
@CrossOrigin
public class NettyClientController {

    @Autowired
    NettyClientService nettyClientService;

    /**
     * 获取数据
     */
    @ResponseBody
    @RequestMapping(value = "/sendMsg", method = RequestMethod.POST)
    public void sendMsg(String user, String targetUser, int transcode, String data){
        nettyClientService.sendMessage(user, targetUser, transcode, data);
    }
}
