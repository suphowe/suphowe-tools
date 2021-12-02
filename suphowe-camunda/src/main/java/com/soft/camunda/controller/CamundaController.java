package com.soft.camunda.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * camunda 测试
 * @author suphowe
 */
@Slf4j
@RestController
@Api(value = "Camunda测试")
@RequestMapping(value = "/camunda")
public class CamundaController implements JavaDelegate {

    @Autowired
    private RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("经理审核");
    }
}
