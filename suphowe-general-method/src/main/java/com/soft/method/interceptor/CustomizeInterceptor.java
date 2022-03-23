package com.soft.method.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 自定义拦截器
 * @author suphowe
 */
public class CustomizeInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(CustomizeInterceptor.class);

    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object object) throws Exception {
        logger.info("request uri : {}", request.getRequestURI());
        // 拦截器内部判断处理
        if (false) {
            returnJson(httpServletResponse);
        }
        return true;
    }

    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

    /**
     * 拦截器返回数据
     */
    private void returnJson(HttpServletResponse response){
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print("拦截器返回信息");
        } catch (IOException e){
            logger.error("拦截器输出流异常 ", e);
        } finally {
            if(writer != null){
                writer.close();
            }
        }
    }
}

