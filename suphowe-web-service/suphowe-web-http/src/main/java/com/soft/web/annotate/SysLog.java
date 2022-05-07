package com.soft.web.annotate;

import java.lang.annotation.*;

/**
 * 自定义操作日志注解
 * @author suphowe
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    // 操作模块
    String module() default "";
    // 操作类型
    String funcType() default "";
    // 操作说明
    String funcDesc() default "";
}
