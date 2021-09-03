package com.soft.code.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.soft.code.model.ResultBean;
import com.soft.code.model.TableClass;
import com.soft.code.service.GenerateCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 代码生成控制
 * @author suphowe
 */
@Controller
public class GenerateCodeController {

    @Autowired
    GenerateCodeService generateCodeService;

    @RequestMapping("/codebuild")
    public String codebuild(){
        return "index.html";
    }

    @PostMapping("/generateCode")
    @ResponseBody
    public ResultBean generateCode(@RequestBody Map<String, Object> map) {
        String tableString = new Gson().toJson(map.get("tableData"));
        List<TableClass> tableClassList = JSONObject.parseArray(tableString, TableClass.class);
        String savePath = (String) map.get("savePath");
        return generateCodeService.generateCode(tableClassList, savePath);
    }
}
