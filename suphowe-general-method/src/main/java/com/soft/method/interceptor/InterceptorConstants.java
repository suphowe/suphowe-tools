package com.soft.method.interceptor;

import java.util.Arrays;
import java.util.List;

/**
 * 拦截器常量
 * @author suphowe
 */
public class InterceptorConstants {

    /**
     * 拦截器白名单
     */
    public static final List<String> URL_WHITELIST = Arrays.asList("/sys/getToken","/","/sys/getCaptcha",
            "/doc.html","/v3/*","/v2/*","/test/**",
            "/webjars/*","/webjars/*/*","/webjars/**","/swagger-ui/*","/swagger-ui/index.html",
            "/swagger-resources","/swagger-resources/**", "/favicon.ico", "/error",
            "/login");
}
