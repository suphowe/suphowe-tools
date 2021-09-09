package com.soft.method.cron;

import lombok.Data;
import lombok.ToString;

/**
 * cron表达式实体类
 * @author suphowe
 */
@Data
@ToString
public class TaskScheduleModel {

    /**
     * 所选作业类型:
     * 1  -> 每秒
     * 2  -> 每分钟
     * 3  -> 每小时
     * 4  -> 每天
     * 5  -> 每周
     * 6  -> 每月
     */
    Integer jobType;

    /**一周的哪几天*/
    Integer[] dayOfWeeks;

    /**一个月的哪几天*/
    Integer[] dayOfMonths;

    /**秒  */
    Integer second;

    /**分  */
    Integer minute;

    /**时  */
    Integer hour;
}
