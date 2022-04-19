package com.soft.web.controller;

import com.soft.web.entity.ResponseMsg;
import com.soft.web.entity.SubmitForm;
import com.soft.web.util.ResponseBody;
import io.swagger.annotations.Api;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 提交验证
 * @author suphowe
 */
@Api(tags = "提交验证")
@RestController
@RequestMapping("/valid")
public class ValidController {

    @RequestMapping("/form")
    public ResponseMsg validForm(@Valid SubmitForm submitForm, BindingResult result){
        if(result.hasErrors()){
            return new ResponseBody("400").createNullDataBody();
        }
        return new ResponseBody("200").createNullDataBody();
    }
}
