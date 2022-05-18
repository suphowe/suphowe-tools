package com.soft.method.controller;

import com.google.gson.Gson;
import com.soft.method.excel.EasyExcelUtil;
import com.soft.method.service.EasyExcelService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * EasyExcel 测试
 * @author suphowe
 */
@RestController
@RequestMapping("/easyExcel")
@Api(value = "数据上传")
@CrossOrigin
@Slf4j
public class EasyExcelController {

    @Autowired
    EasyExcelService easyExcelService;

    @ResponseBody
    @RequestMapping(value = "/getImportExcel", method = RequestMethod.POST)
    public String getImportExcel(@RequestParam MultipartFile excelFile, HttpServletRequest request, HttpServletResponse response) throws IOException {
        HashMap<String, Object> result = new HashMap<>(4);
        response.setHeader("Access-Control-Allow-Origin", "*");
        String name = excelFile.getOriginalFilename();
        List<Object> list = new ArrayList<>();
        if (!name.endsWith(".xls") && !name.endsWith(".xlsx")) {
            System.out.println("文件不是excel类型");
            result.put("filecondition", "文件类型错误");
        } else {
            String path = "C:\\servers\\apache-tomcat-9.0.50\\webapps\\test\\";
            String newFile = path+ File.separator+name;
            File uploadSaveFile = new File(newFile);
            if(uploadSaveFile.exists()){
                uploadSaveFile.delete();
            }
            excelFile.transferTo(new File(newFile));
            list = EasyExcelUtil.readMoreThan1000Row(newFile, true);
            easyExcelService.importDatabase(list);
        }
        result.put("code", 200);
        result.put("result", list);
        return new Gson().toJson(result);
    }

    @RequestMapping(value = "/writeTemplateExcel", method = RequestMethod.GET)
    public void writeTemplateExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HashMap<String, Object> other = new HashMap(4);
        other.put("unitName", "CAF");
        other.put("date", "2022-05-17");
        other.put("writer", "作者");

        List<HashMap<String, Object>> data = new ArrayList<>();
        HashMap<String, Object> data1 = new HashMap(16);
        data1.put("id", 1);
        data1.put("name", "howe");
        data1.put("sex", "M");
        data1.put("tel", "1336829xxx");
        data1.put("addr", "CQ");
        data1.put("age", 30);
        data1.put("height", 180);
        data1.put("weight", 70);
        data.add(data1);

        HashMap<String, Object> data2 = new HashMap(16);
        data2.put("id", 2);
        data2.put("name", "howe");
        data2.put("sex", "M");
        data2.put("tel", "1336829xxx");
        data2.put("addr", "CQ");
        data2.put("age", 30);
        data2.put("height", 180);
        data2.put("weight", 70);
        data.add(data2);

        EasyExcelUtil.writeTemplateExcel(response, "templates/excel/Excel填充模板.xlsx", data, other);
    }
}
