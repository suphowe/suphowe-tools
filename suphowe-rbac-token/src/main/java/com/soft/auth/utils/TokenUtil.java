package com.soft.auth.utils;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.soft.auth.constant.Constants;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.UUID;

/**
 * token 工具类
 * @author suphowe
 */
public class TokenUtil {

    /**
     * 创建token
     */
    public String createToken() {
        String str = UUID.randomUUID().toString();
        StringBuilder token = new StringBuilder();
        try {
            token.append(Constants.TOKEN_PREFIX).append(str);
            boolean notEmpty = StrUtil.isNotEmpty(token.toString());
            if (notEmpty) {
                return token.toString();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }


    /**
     * 检验token
     */
    public boolean checkToken(HttpServletRequest request, String serverToken) throws Exception {
        String token = request.getHeader(Constants.TOKEN_NAME);
        // header中不存在token
        if (StrUtil.isBlank(token)) {
            token = request.getParameter(Constants.TOKEN_NAME);
            // parameter中也不存在token
            if (StrUtil.isBlank(token)) {
                return false;
            }
        }
        return Objects.equals(serverToken, token);
    }

}
