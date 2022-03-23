package com.soft.token.controller;

import cn.hutool.json.JSONObject;
import com.soft.token.beans.User;
import com.soft.token.service.UserService;
import com.soft.token.service.TokenService;
import com.soft.token.system.UserLoginToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * token测试
 * @author suphowe
 **/
@CrossOrigin
@RestController
@RequestMapping("/token")
@Api(value = "token测试")
public class TokenController {

    private static final Logger logger = LoggerFactory.getLogger(TokenController.class);

    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

    @GetMapping("/login")
    @ApiOperation(value = "登陆测试")
    public Object login(String userId, String password, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        User userForBase = new User();
        userForBase.setId(userId);
        userForBase.setPassword(password);
        userForBase.setUsername(userService.findUserById(userId).getUsername());

        if (!userForBase.getPassword().equals(password)) {
            jsonObject.put("message", "登录失败,密码错误");
        } else {
            String token = tokenService.getToken(userForBase);
            jsonObject.put("token", token);

            Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        return jsonObject;
    }

    /**
     * 这个请求需要验证token才能访问
     */
    @UserLoginToken
    @GetMapping("/getMessage")
    @ApiOperation(value = "验证token")
    public String getMessage(HttpServletRequest httpServletRequest) {
        // 取出token中带的用户id 进行操作
        logger.info(userService.getTokenUserId(httpServletRequest));
        return "你已通过验证";
    }



}
