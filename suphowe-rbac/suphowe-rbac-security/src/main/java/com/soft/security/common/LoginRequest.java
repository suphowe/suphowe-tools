package com.soft.security.common;

import lombok.Data;

/**
 * 登录请求参数
 * @author suphowe
 */
@Data
public class LoginRequest {

    /**
     * 用户名或邮箱或手机号
     */
    private String usernameOrEmailOrPhone;

    /**
     * 密码
     */
    private String password;

    /**
     * 记住我
     */
    private Boolean rememberMe = false;

}
