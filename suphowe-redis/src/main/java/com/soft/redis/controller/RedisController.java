package com.soft.redis.controller;

import com.google.gson.Gson;
import com.soft.redis.service.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * redis测试
 * @author suphowe
 **/
@RestController
@RequestMapping("/redis")
@Api(value = "Redis测试")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @RequestMapping(value = "/redisPoolTest", method = RequestMethod.POST)
    @ApiOperation(value = "redis连接池测试,放入时间")
    public void redisPoolTest() {
        redisService.redisPoolTest();
    }

    @RequestMapping(value = "/redisProduct", method = RequestMethod.POST)
    @ApiOperation(value = "redis队列测试,生产者")
    public String redisProducer(String key, String value){
        HashMap<String, Object> result = redisService.redisProducer(key, value);
        return new Gson().toJson(result);
    }

    @RequestMapping(value = "/redisSetHeatValue", method = RequestMethod.POST)
    @ApiOperation(value = "redis设置过期时间")
    public String redisSetHeatValue(String key, String value, String time){
        HashMap<String, Object> result = redisService.redisSetHeatValue(key, value, time);
        return new Gson().toJson(result);
    }

    @RequestMapping(value = "/redisGetKeyValue", method = RequestMethod.POST)
    @ApiOperation(value = "redis模糊查询所有的键值")
    public String redisGetKeyValue(String key){
        HashMap<String, Object> result = redisService.redisGetKeyValue(key);
        return new Gson().toJson(result);
    }

    @RequestMapping(value = "/redisGet", method = RequestMethod.POST)
    @ApiOperation(value = "redis获取Key对应的所有数据")
    public String redisGet(String key){
        HashMap<String, Object> result = redisService.redisGet(key);
        return new Gson().toJson(result);
    }

    @RequestMapping(value = "/deleteValue", method = RequestMethod.POST)
    @ApiOperation(value = "模糊删除")
    public String deleteValue(String key){
        HashMap<String, Object> result = redisService.deleteValue(key);
        return new Gson().toJson(result);
    }

    @RequestMapping(value = "/flushAll", method = RequestMethod.POST)
    @ApiOperation(value = "清空数据")
    public String flushAll(){
        HashMap<String, Object> result = redisService.flushAll();
        return new Gson().toJson(result);
    }
}
