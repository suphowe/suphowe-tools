package com.soft.token.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.soft.token.beans.User;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * token 下发
 * @author suphowe
 */
@Service
public class UserTokenService {

    /**
     * 获取用户token
     * @param user 用户名
     * @return token
     */
    public String getToken(User user) {
        Date start = new Date();
        //一小时有效时间
        long currentTime = System.currentTimeMillis() + 60* 60 * 1000;
        Date end = new Date(currentTime);

        String token = JWT.create().withAudience(user.getId()).withIssuedAt(start).withExpiresAt(end)
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }

}
