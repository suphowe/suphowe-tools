package com.soft.web.system;

import com.google.gson.Gson;
import com.soft.web.annotate.SysLog;
import com.soft.web.entity.SysExLogInfo;
import com.soft.web.entity.SysLogInfo;
import com.soft.web.service.ThreadService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Aop 切面编程
 * @author suphowe
 */
@Aspect
@Component
public class SysLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(SysLogAspect.class);

    @Autowired
    ThreadService threadService;

    /**
     * 设置操作异常切入点记录异常日志 扫描所有controller包下操作
     */
    @Pointcut("execution(* com.soft.web.controller..*.*(..))")
    public void exceptionLogPointCut() {
    }

    /**
     * 声明切点,通过自定义注解切入日志
     */
    @Pointcut("@annotation(com.soft.web.annotate.SysLog)")
    public void sysLogPointCut() {
    }

    /**
     * 通知方法会在目标方法返回后调用
     * @param joinPoint 切点
     * @param result 返回结果
     */
    @AfterReturning(value = "sysLogPointCut()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        try {
            // 获取RequestAttributes
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            // 从获取RequestAttributes中获取HttpServletRequest的信息
            HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
            SysLogInfo sysLogInfo = new SysLogInfo();
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
            // 获取操作
            SysLog sysLog = method.getAnnotation(SysLog.class);
            if (sysLog != null) {
                String module = sysLog.module();
                String funcType = sysLog.funcType();
                String funcDesc = sysLog.funcDesc();
                sysLogInfo.setModule(module);
                sysLogInfo.setFuncType(funcType);
                sysLogInfo.setFuncDesc(funcDesc);
            }
            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            // 获取请求的方法名
            String methodName = method.getName();
            methodName = className + "." + methodName;
            // 请求方法
            sysLogInfo.setMethod(methodName);

            // 请求的参数
            HashMap<String, Object> requestMap = getRequestMap(request.getParameterMap());
            // 请求参数
            sysLogInfo.setParams(new Gson().toJson(requestMap));
            // 返回结果
            sysLogInfo.setResult(new Gson().toJson(result));
            // 请求IP
            sysLogInfo.setRequestIp(request.getRemoteAddr());
            // 请求URI
            sysLogInfo.setRequestUri(request.getRequestURI());
            // 请求时间
            sysLogInfo.setCreateTime(LocalDateTime.now().toString());
            logger.info("记录操作日志:{}", sysLogInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通知方法会在目标方法抛出异常后调用
     */
    @AfterThrowing(pointcut = "exceptionLogPointCut()", throwing = "throwable")
    public void afterThrowing(JoinPoint joinPoint, Throwable throwable) {
        try {
            // 获取RequestAttributes
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            // 从获取RequestAttributes中获取HttpServletRequest的信息
            HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

            SysExLogInfo sysExLogInfo = new SysExLogInfo();
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            // 获取请求的方法名
            String methodName = method.getName();
            methodName = className + "." + methodName;
            // 请求的参数
            HashMap<String, Object> requestMap = getRequestMap(request.getParameterMap());
            // 请求参数
            sysExLogInfo.setParams(new Gson().toJson(requestMap));
            // 请求方法名
            sysExLogInfo.setMethod(methodName);
            // 异常名称
            sysExLogInfo.setExceptionName(throwable.getClass().getName());
            // 异常信息
            sysExLogInfo.setExceptionMessage(stackTraceToString(throwable.getClass().getName(), throwable.getMessage(), throwable.getStackTrace()));
            // 请求IP
            sysExLogInfo.setRequestIp(request.getRemoteAddr());
            // 操作URI
            sysExLogInfo.setRequestUri(request.getRequestURI());
            // 请求时间
            sysExLogInfo.setCreateTime(LocalDateTime.now().toString());
            logger.info("记录操作异常日志:{}", sysExLogInfo);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * 转换request 请求参数
     * @param paramMap request获取的参数数组
     */
    private HashMap<String, Object> getRequestMap(Map<String, String[]> paramMap) {
        HashMap<String, Object> result = new HashMap<>(16);
        for (String key : paramMap.keySet()) {
            result.put(key, paramMap.get(key)[0]);
        }
        return result;
    }

    /**
     * 转换异常信息为字符串
     *
     * @param exceptionName    异常名称
     * @param exceptionMessage 异常信息
     * @param elements         堆栈信息
     */
    public String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
        StringBuilder strBuilder = new StringBuilder();
        for (StackTraceElement stet : elements) {
            strBuilder.append(stet);
            strBuilder.append("\n");
        }
        String message = exceptionName + ":" + exceptionMessage + "\n\t" + strBuilder.toString();
        if (message.length() > 200) {
            message = message.substring(0, 200);
        }
        return message;
    }
}
