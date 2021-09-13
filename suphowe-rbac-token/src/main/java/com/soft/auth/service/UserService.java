package com.soft.auth.service;

import com.auth0.jwt.JWT;
import com.soft.auth.beans.User;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户实现类
 * @author suphowe
 */
@Service
public class UserService {

    /**
     * 获取用户信息
     * @param userId 用户id
     * @return 用户信息
     */
    public User findUserById(String userId) {
        User user = new User();
        user.setId("admin");
        user.setPassword("111111");
        return user;
    }

    /**
     * 从token中取出用户id
     * @return 用户id
     */
    public String getTokenUserId(HttpServletRequest httpServletRequest) {
        // 从 http 请求头中取出 token
        String token = getRequest().getHeader("token");
        String userId = JWT.decode(token).getAudience().get(0);
        return userId;
    }

    /**
     * 获取request
     * @return 请求request
     */
    private static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        return requestAttributes == null ? null : requestAttributes.getRequest();
    }
}
