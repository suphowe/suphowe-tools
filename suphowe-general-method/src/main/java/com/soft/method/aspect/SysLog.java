package com.soft.method.aspect;

import java.lang.annotation.*;

/**
 * 自定义操作日志注解
 * @author suphowe
 */
@Target(ElementType.METHOD) //注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
@Documented
public @interface SysLog {
    // 操作模块
    String module() default "";
    // 操作类型
    String funcType() default "";
    // 操作说明
    String funcDesc() default "";
}
