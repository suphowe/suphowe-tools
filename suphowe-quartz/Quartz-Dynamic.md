# 动态quartz配置

## 表结构
```
CREATE TABLE `sys_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(255) DEFAULT NULL COMMENT '任务名',
  `description` varchar(255) DEFAULT NULL COMMENT '任务描述',
  `cron_expression` varchar(255) DEFAULT NULL COMMENT 'cron表达式',
  `bean_class` varchar(255) DEFAULT NULL COMMENT '任务执行时调用哪个类的方法 包名+类名',
  `job_status` varchar(255) DEFAULT NULL COMMENT '任务状态',
  `job_group` varchar(255) DEFAULT NULL COMMENT '任务分组',
  `create_user` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;
```
## quartz.properties
```
# 固定前缀org.quartz
org.quartz.scheduler.instanceName = DefaultQuartzScheduler
org.quartz.scheduler.instanceId = AUTO 
org.quartz.scheduler.rmi.export = false
org.quartz.scheduler.rmi.proxy = false
org.quartz.scheduler.wrapJobExecutionInUserTransaction = false
```

## 业务流程
```
QuartzController IQuartzService QuartzServiceImpl QuartzDao
```

## 所有的任务都需要继承 Job
```
/**
 * 基础任务调度接口
 * @author suphowe
 */
public interface QuartzBaseTaskJob extends Job {

    @Override
    void execute(JobExecutionContext context);
}
```

## 接口字段说明
| 字段 | 描述 | 类型 |
| ------ | ------ | ------ |
| jobName | 任务名称 | String |
| description | 任务描述 | String |
| cronExpression | cron表达式 | String |
| beanClassName | 任务执行时调用哪个类的方法 包名+类名 | String |
| jobGroup | 任务分组 | String |
| createUser | 创建者 | String |
| updateUser | 修改者 | String |