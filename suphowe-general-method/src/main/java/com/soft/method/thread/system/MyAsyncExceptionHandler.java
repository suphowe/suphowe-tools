package com.soft.method.thread.system;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

/**
 * 线程池异常捕获
 * @author suphowe
 */
@Slf4j
public class MyAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
        log.error("ExceptionMessage:{}", throwable.getMessage());
        log.error("MethodName:{}", method.getName());
        for (Object param : objects) {
            log.error("Parameter:{}", param);
        }
    }
}
