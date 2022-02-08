package com.soft.clickhouse.controller;

import com.google.gson.Gson;
import com.soft.clickhouse.entity.DmHrEmployeeInfoDetailf;
import com.soft.clickhouse.service.DmHrEmployeeInfoDetailfService;
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
@Api(value = "XXX")
@RequestMapping(value = "/DmHrEmployeeInfoDetailf")
public class DmHrEmployeeInfoDetailfController {

    @Autowired
    private DmHrEmployeeInfoDetailfService dmHrEmployeeInfoDetailfService;

    @GetMapping("/queryFirst100")
    @ApiOperation(value = "查询100条记录")
    public String queryAll() {
        List<DmHrEmployeeInfoDetailf> list = dmHrEmployeeInfoDetailfService.queryFirst100();
        return new Gson().toJson(list);
    }
}
