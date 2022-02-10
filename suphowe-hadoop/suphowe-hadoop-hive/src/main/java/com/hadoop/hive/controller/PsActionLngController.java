package com.hadoop.hive.controller;

import com.google.gson.Gson;
import com.hadoop.hive.entity.PsActionLng;
import com.hadoop.hive.service.PsActionLngService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 员工操作控制
 * @author suphowe
 */
@RestController
@Api(value = "员工操作控制")
@RequestMapping(value = "/psActionLng")
public class PsActionLngController {

    @Autowired
    private PsActionLngService psActionLngService;

    @GetMapping("/queryAll")
    @ApiOperation(value = "员工操作控制,查询全部记录")
    public String queryAll() {
        List<Map<String, Object>> list = psActionLngService.queryAll();
        return new Gson().toJson(list);
    }
}
