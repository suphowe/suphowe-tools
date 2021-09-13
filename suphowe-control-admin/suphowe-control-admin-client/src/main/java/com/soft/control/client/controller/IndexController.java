package com.soft.control.client.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页
 * @author suphowe
 */
@RestController
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "this is a spring boot admin start";
    }

}
