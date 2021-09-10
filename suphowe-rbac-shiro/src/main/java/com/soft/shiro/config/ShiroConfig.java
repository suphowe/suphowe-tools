package com.soft.shiro.config;

import com.soft.shiro.constants.ShiroConstants;
import com.soft.shiro.system.CustomRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Shiro配置类
 * @author suphowe
 */
@Configuration
public class ShiroConfig {

    /**
     * 开启shiro的注解
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAPC = new DefaultAdvisorAutoProxyCreator();
        defaultAAPC.setProxyTargetClass(true);
        return defaultAAPC;
    }

    /**
     * 将自己的验证方式加入容器
     */
    @Bean
    public CustomRealm myShiroRealm() {
        return new CustomRealm();
    }

    /**
     * 设置session过期时间
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        // 设置session过期时间3600s
        sessionManager.setGlobalSessionTimeout(ShiroConstants.EXPIRATION);
        return sessionManager;
    }

    /**
     * 权限管理，配置主要是Realm的管理认证
     */
    @Bean
    public SessionsSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm
        securityManager.setRealm(myShiroRealm());
        // 注入session管理器
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    /**
     * Filter工厂，拦截器,设置对应的过滤条件和跳转条件
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // shiro的核心安全接口，必须属性
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 身份验证失败,则跳转到登录页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 权限验证失败，则跳转到指定页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index");
        // shiro连续约束配置,即过滤的定义
        shiroFilterFactoryBean.setFilterChainDefinitionMap(getAuthcUri());
        return shiroFilterFactoryBean;
    }


    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 过滤链定义，从上向下顺序执行，一般将/**放在最为下边
     * authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问
     */
    public static HashMap<String, String> getAuthcUri(){
        LinkedHashMap<String, String> whiteListMap = new LinkedHashMap<>(4);
        whiteListMap.put("/logout", "logout");
        whiteListMap.put("/index", "anon");
        // swagger
        whiteListMap.put("/swagger-ui.html", "anon");
        whiteListMap.put("/swagger-resources/**", "anon");
        //对所有用户认证
        whiteListMap.put("/**", "authc");
        return whiteListMap;
    }
}
