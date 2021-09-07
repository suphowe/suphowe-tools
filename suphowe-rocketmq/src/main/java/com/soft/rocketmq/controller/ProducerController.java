package com.soft.rocketmq.controller;

import com.soft.rocketmq.service.ProducerService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * rocketmq 测试
 * @author suphowe
 */
@Controller
@RequestMapping("/rocketmq")
@Api(value = "rocketmq测试")
public class ProducerController {

    @Autowired
    ProducerService producerService;

    @RequestMapping("/sendMsg")
    public String sendMsg(String message) throws Exception {
        return producerService.sendMsg(message);
    }
}
