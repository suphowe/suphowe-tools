package com.soft.token.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.soft.token.beans.User;
import com.soft.token.constant.SystemConstants;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;

/**
 * token服务类
 * @author suphowe
 */
@Service
public class TokenService {

    @Autowired
    StringEncryptor stringEncryptor;

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

        return JWT.create().withAudience(user.getId()).withIssuedAt(start).withExpiresAt(end)
                .sign(Algorithm.HMAC256(user.getPassword()));
    }

    /**
     * 建立token
     * @param user 用户
     * @return token
     */
    public HashMap<String, Object> getToken(String ipAddress, User user) {
        HashMap<String, Object> result = new HashMap<>(2);
        // 过期时间
        long currentTime = System.currentTimeMillis() + SystemConstants.TOKEN_EXPIRED_TIME * 60 * 1000;
        String token = stringEncryptor.encrypt(user.getId() + " " + currentTime + " " + ipAddress);
        result.put("token", token);
        return result;
    }

    /**
     * 解析
     * @param token 传入token
     * @return 解析信息
     */
    public String[] analyzeToken(String token) {
        String decryptInfo = stringEncryptor.decrypt(token);
        return decryptInfo.split(" ");
    }

    /**
     * 判断token是否过期 true 过期 false 未过期
     */
    public boolean judgeTokenExpired (long tokenEndTime) {
        long now = System.currentTimeMillis();
        return now - tokenEndTime >= 0;
    }

    /**
     * 判断是否为本人操作
     * @param request 请求
     * @param emplCode 员工编号
     * @return 判断结果
     */
    public boolean whetherMyselfOperate (HttpServletRequest request, String emplCode) {
        String token = request.getHeader("token") == null ? "" : request.getHeader("token");
        String[] user = analyzeToken(token);
        return user[0].equals(emplCode);
    }

}
