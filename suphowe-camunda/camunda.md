# 网站资源
http://localhost:8080/  
用户名:howe  
密码:123456  
参考 https://www.cnblogs.com/suphowe/p/15632079.html  
# 核心表概念
```
ACT_ID_GROUP 组
ACT_ID_USER 账号
ACT_ID_MEMBERSHIP 归属关系
ACT_RU_AUTHORIZATION 资源、服务与账户、组之间的对应关系
ACT_GE_PROPERTY 系统运行时属性
ACT_GE_BYTEARRAY 字节数据
ACT_RE_DEPLOYMENT发布
ACT_RU_EXECUTION执行路径
ACT_RU_JOBruntime一个调度的单位
ACT_RU_JOBDEF 作业定义
ACT_RE_PROCDEF 流程定义，区分版本
ACT_RU_TASK一个工作流中的任务
ACT_RU_VARIABLE workflow中跨越节点的数据
ACT_RU_METER_LOG作业调度中的详细日志
ACT_HI_PROCINST 流程实例
ACT_HI_ACTINST活动实例
ACT_HI_TASKINST任务实例
ACT_HI_VARINST  变量实例
ACT_HI_DETAIL   变量的详细信息，比如a proc下，有个a1 act，之下有个a11 task，之下有个a111变量，这个变量和ACT_GE_BYTEARRAY的对应关系，类型等信息
ACT_HI_COMMENT 对某个task的评论
ACT_HI_OP_LOG 详细操作日志，比如某个人接了某个任务
```