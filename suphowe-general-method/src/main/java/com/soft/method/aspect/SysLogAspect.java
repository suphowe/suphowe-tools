package com.soft.method.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
     */
    @AfterReturning("point()")
    public void afterReturing() {
//        logger.info("AfterReturning ==> 通知方法会在目标方法返回后调用");
    }

    /**
     * 通知方法会在目标方法抛出异常后调用
     */
    @AfterThrowing("point()")
    public void afterThrowing() {
//        logger.info("AfterThrowing ==> 通知方法会在目标方法抛出异常后调用");
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

}
