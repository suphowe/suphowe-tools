package com.soft.method.controller;

import com.soft.method.service.SigmaService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
/**
 *  6sigma 数据生成
 * @author suphowe
 **/
@RestController
@RequestMapping("/6sigma")
@Api(value = "6sigma 数据生成")
public class SigmaController {

    @Autowired
    SigmaService sigmaService;

    @RequestMapping(value = "/insertSigmaData", method = RequestMethod.GET)
    public String insertSigmaData(HttpServletRequest request,
                                  int rows, String beginDate, String endDate,
                                  float max, float min, String upTime, String lowTime){
        sigmaService.insertSigmaData(rows, beginDate, endDate, max, min, upTime, lowTime);
        return "success!";
    }

}
