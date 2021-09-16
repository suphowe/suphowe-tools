package com.soft.quartz.entity;

import lombok.Data;

/**
 * 实体类
 * @author suphowe
 */
@Data
public class SysTask {

    private int id;
    private String jobName;
    private String description;
    private String cronExpression;
    private String beanClass;
    private String jobStatus;
    private String jobGroup;
    private String createUser;
    private String createTime;
    private String updateUser;
    private String updateTime;
}
