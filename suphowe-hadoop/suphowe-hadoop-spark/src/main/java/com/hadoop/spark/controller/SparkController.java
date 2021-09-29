package com.hadoop.spark.controller;

import com.hadoop.spark.service.SparkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * spark 测试
 * @author suphowe
 */
@RestController
public class SparkController {

    @Autowired
    private SparkService sparkService;

    @RequestMapping("/demo/top10")
    public Map<String, Object> calculateTopTen() {
        return sparkService.calculateTopTen();
    }

    @RequestMapping("/demo/exercise")
    public void exercise() {
        sparkService.sparkExerciseDemo();
    }

    @RequestMapping("/demo/stream")
    public void streamingDemo() throws InterruptedException {
        sparkService.sparkStreaming();
    }
}
