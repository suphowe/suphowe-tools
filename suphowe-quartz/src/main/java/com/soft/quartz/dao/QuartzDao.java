package com.soft.quartz.dao;

import com.soft.quartz.entity.SysTask;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 动态定时任务数据库操作
 * @author suphowe
 */
@Mapper
public interface QuartzDao {

    @Insert("insert into sys_task (job_name, description, cron_expression, bean_class, job_status, job_group, create_user, create_time)" +
            " values (#{jobName},#{description},#{cronExpression},#{beanClass},#{jobStatus},#{jobGroup},#{createUser},now())")
    int insertSysTask(SysTask sysTask);

    @Select("select count(*) from sys_task where job_name=#{jobName}")
    int selectCountByJobName(SysTask sysTask);

    @Update("<script>" +
            "update sys_task  " +
            "<set>" +
            "   <if test='description!=null and description!=&quot;&quot;'> description=#{description}, </if>" +
            "   <if test='cronExpression!=null and cronExpression!=&quot;&quot;'> cron_expression=#{cronExpression}, </if>" +
            "   <if test='beanClass!=null and beanClass!=&quot;&quot;'> bean_class=#{beanClass}, </if>" +
            "   <if test='jobStatus!=null and jobStatus!=&quot;&quot;'> job_status=#{jobStatus}, </if>" +
            "   <if test='jobGroup!=null and jobGroup!=&quot;&quot;'> job_group=#{jobGroup}, </if>" +
            "   <if test='updateUser!=null and updateUser!=&quot;&quot;'> update_user=#{updateUser}, </if>" +
            "   <if test='updateTime!=null and updateTime!=&quot;&quot;'> update_time=#{updateTime}, </if>" +
            "</set>" +
            " where job_name=#{jobName}" +
            "</script>")
    int updateSysTask(SysTask sysTask);

    @Select("<script>" +
            "select id,job_name jobName,description,cron_expression cronExpression,bean_class beanClass, " +
            "job_status jobStatus,job_group jobGroup,create_user createUser,create_time createTime,update_user updateUser," +
            "update_time updateTime from sys_task " +
            "<where>" +
            "   <if test='jobName!=null and jobName!=&quot;&quot;'>" +
            "       and job_name=#{jobName}" +
            "   </if>" +
            "   <if test='jobStatus!=null and jobStatus!=&quot;&quot;'>" +
            "       and job_status=#{jobStatus}" +
            "   </if>" +
            "</where>" +
            "</script>"
    )
    List<SysTask> selectSysTask(SysTask sysTask);

    @Delete("<script>"
            + "delete from sys_task"
            + "<where>"
            + "<if test='jobName!=null and jobName!=&quot;&quot;'>"
            + "   and job_name=#{jobName}"
            + "</if>"
            + "</where>"
            + "</script>"
    )
    int deleteSysTaskByJobName(SysTask sysTask);

    @Update("<script>" +
            "update sys_task " +
            "<set>" +
            "   <if test='jobStatus!=null and jobStatus!=&quot;&quot;'> job_status=#{jobStatus}, </if>" +
            "   <if test='updateUser!=null and updateUser!=&quot;&quot;'> update_user=#{updateUser}, </if>" +
            "   <if test='updateTime!=null and updateTime!=&quot;&quot;'> update_time=#{updateTime}, </if>" +
            "</set>" +
            "</script>")
    int updateAllSysTaskStatus(SysTask sysTask);
}
