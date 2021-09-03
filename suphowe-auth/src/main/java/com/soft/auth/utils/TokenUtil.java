package com.soft.auth.utils;

import com.auth0.jwt.JWT;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * token 工具类
 * @author suphowe
 */
public class TokenUtil {

    /**
     * 从token中取出用户id
     * @return 用户id
     */
    public static String getTokenUserId() {
        // 从 http 请求头中取出 token
        String token = getRequest().getHeader("token");
        String userId = JWT.decode(token).getAudience().get(0);
        return userId;
    }

    /**
     * 获取request
     * @return 请求request
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        return requestAttributes == null ? null : requestAttributes.getRequest();
    }

}
