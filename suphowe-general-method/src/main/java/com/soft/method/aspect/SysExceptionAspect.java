package com.soft.method.aspect;

import com.soft.method.beans.Result;
import com.soft.method.exception.ExceptionHandle;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 切面处理
 * @author suphowe
 */
@Aspect
@Component
public class SysExceptionAspect {

    private final static Logger logger = LoggerFactory.getLogger(SysExceptionAspect.class);

    @Resource
    private ExceptionHandle exceptionHandle;

    @Pointcut("execution(public * com.soft.method.controller.*.*(..))")
    public void log(){

    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //url
        logger.info("url={}",request.getRequestURL());
        //method
        logger.info("method={}",request.getMethod());
        //ip
        logger.info("id={}",request.getRemoteAddr());
        //class_method
        logger.info("class_method={}",joinPoint.getSignature().getDeclaringTypeName() + "," + joinPoint.getSignature().getName());
        //args[]
        logger.info("args={}",joinPoint.getArgs());
    }

    /**
     * 切面环绕
     */
    @Around("log()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Result result = null;
        try {

        } catch (Exception e) {
            return exceptionHandle.exceptionGet(e);
        }
        if(result == null){
            return proceedingJoinPoint.proceed();
        }else {
            return result;
        }
    }

    /**
     * 打印输出结果
     */
    @AfterReturning(pointcut = "log()",returning = "object")
    public void doAfterReturn(Object object){
        logger.info("response={}",object.toString());
    }
}
