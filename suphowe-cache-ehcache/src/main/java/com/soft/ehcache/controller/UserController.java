package com.soft.ehcache.controller;

import com.soft.ehcache.entity.User;
import com.soft.ehcache.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * ehcache测试
 * @author suphowe
 */
@Slf4j
@RestController
@Api(value = "ehcache测试")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 获取两次，查看日志验证缓存
     */
    @RequestMapping(value = "/getTwice", method = RequestMethod.POST)
    @ApiOperation(value = "获取两次,看日志验证缓存")
    public void getTwice() {
        // 模拟查询id为1的用户
        User user1 = userService.get(1L);
        log.debug("【user1】= {}", user1);

        // 再次查询
        User user2 = userService.get(1L);
        log.debug("【user2】= {}", user2);
        // 查看日志，只打印一次日志，证明缓存生效
    }

    /**
     * 先存，再查询，查看日志验证缓存
     */
    @RequestMapping(value = "/getAfterSave", method = RequestMethod.POST)
    @ApiOperation(value = "先存，再查询，查看日志验证缓存")
    public void getAfterSave() {
        userService.saveOrUpdate(new User(4L, "user4"));

        User user = userService.get(4L);
        log.debug("【user】= {}", user);
        // 查看日志，只打印保存用户的日志，查询是未触发查询日志，因此缓存生效
    }

    /**
     * 测试删除，查看redis是否存在缓存数据
     */
    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    @ApiOperation(value = "测试删除，查看redis是否存在缓存数据")
    public void deleteUser() {
        // 查询一次，使ehcache中存在缓存数据
        userService.get(1L);
        // 删除，查看ehcache是否存在缓存数据
        userService.delete(1L);
    }
}
