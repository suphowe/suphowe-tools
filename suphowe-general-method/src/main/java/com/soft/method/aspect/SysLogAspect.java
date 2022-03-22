package com.soft.method.aspect;

import com.google.gson.Gson;
import com.soft.method.entity.ExceptionLog;
import com.soft.method.entity.SysLogInfo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
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
    
    /**
     * 声明切点
     */
    @Pointcut("execution(* com.soft.method.*.*(..))")
    public void point(){}

    /**
     * 通知方法会在目标方法调用之前执行
     */
    @Before("point())")
    public void before() {
//        logger.info("before ==> 通知方法会在目标方法调用之前执行");
    }

    /**
     * 通知方法会在目标方法返回或异常后调用
     */
    @After("point()")
    public void after() {
//        logger.info("After ==> 通知方法会在目标方法返回或异常后调用");
    }

    /**
     * 通知方法会在目标方法返回后调用
     * @param joinPoint 切点
     * @param result 返回结果
     */
    @AfterReturning(value = "point()", returning = "result")
    public void afterReturn(JoinPoint joinPoint, Object result) {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        SysLogInfo sysLogInfo = new SysLogInfo();
        try {
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
            logger.info("记录操作日志:{}", sysLogInfo);
//            logservice.insertSysLogInfo(sysLogInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通知方法会在目标方法抛出异常后调用
     */
    @AfterThrowing(pointcut = "point()", throwing = "throwable")
    public void afterThrowing(JoinPoint joinPoint, Throwable throwable) {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

        ExceptionLog exceptionLog = new ExceptionLog();
        try {
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
            exceptionLog.setParams(new Gson().toJson(requestMap));
            // 请求方法名
            exceptionLog.setMethod(methodName);
            // 异常名称
            exceptionLog.setExceptionName(throwable.getClass().getName());
            // 异常信息
            exceptionLog.setExceptionMessage(stackTraceToString(throwable.getClass().getName(), throwable.getMessage(), throwable.getStackTrace()));
            // 请求IP
            exceptionLog.setRequestIp(request.getRemoteAddr());
            // 操作URI
            exceptionLog.setRequestUri(request.getRequestURI());
            logger.info("记录操作异常日志:{}", exceptionLog);
//            logservice.insertExceptionLog(exceptionLog);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * 通知方法会将目标方法封装起来
     * 返回类型不能为int,int是无法匹配null的,会抛出异常
     * @param proceeding 切面
     */
    @Around("point()")
    public Object around(ProceedingJoinPoint proceeding) {
        //和JoinPoint一样，ProceedingJoinPoint也可以获取
        //连接点方法的实参
        Object[] args = proceeding.getArgs();
        //连接点方法的方法名0
        String methodName = proceeding.getSignature().getName();
        //连接点方法所在的对象
        Object targetObj = proceeding.getTarget();
        String targetClassName = targetObj.getClass().getName();

        Object result = null;
        try {
//            logger.info("前置通知==>参数:{}", args);
//            logger.info("前置通知==>执行文件:{}", targetClassName);
//            logger.info("前置通知==>执行方法:{}", methodName);
            //执行连接点的方法 获取返回值
            result = proceeding.proceed(args);
//            logger.info("返回通知==>返回结果:{}", result);
        } catch (Throwable e) {
            logger.info("异常通知==>", e);
        } finally {
            logger.info("最终通知==>执行结束");
        }
        return result;
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
    private String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
        StringBuffer stringBuffer = new StringBuffer();
        for (StackTraceElement stet : elements) {
            stringBuffer.append(stet + "\n");
        }
        String message = exceptionName + ":" + exceptionMessage + "\n\t" + stringBuffer.toString();
        return message;
    }

}
