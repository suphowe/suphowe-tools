package com.soft.oracle.controller;

import com.google.gson.Gson;
import com.soft.oracle.entity.MidEmail;
import com.soft.oracle.service.MidEmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * @author suphowe
 */
@RestController
@Api(value = "MID_EMAIL表操作")
@RequestMapping(value = "/midEmail")
public class MidEmailController {

    @Autowired
    private MidEmailService midEmailService;

    @GetMapping("/queryFirst100")
    @ApiOperation(value = "查询100条记录")
    public String queryAll() {
        List<MidEmail> list = midEmailService.queryFirst100();
        return new Gson().toJson(list);
    }
}
