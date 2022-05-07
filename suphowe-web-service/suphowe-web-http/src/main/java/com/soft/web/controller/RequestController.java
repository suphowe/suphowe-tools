package com.soft.web.controller;

import com.soft.web.service.RequestService;
import com.soft.web.annotate.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 请求测试
 * @author suphowe
 */
@Api(tags = "Web请求", description = "请求数据处理")
@RestController
@RequestMapping("/request")
public class RequestController {

    @Autowired
    RequestService requestService;

    @ApiOperation(value = "请求去重")
    @PostMapping("/dedup")
    @SysLog(module = "请求数据处理", funcType = "request", funcDesc = "数据去重")
    public Object dedup(@RequestBody String requestData) {
        return requestService.dedup(requestData);
    }
}
