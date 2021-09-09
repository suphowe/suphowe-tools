package com.soft.method.cron;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * cron表达式工具
 * @author suphowe
 */
public class CronUtil {

    private static final Logger logger = LoggerFactory.getLogger(CronUtil.class);

    /**
     * 构建Cron表达式
     * @param taskScheduleModel cron表达式模型
     * @return cron表达式
     */
    public static String createCronExpression(TaskScheduleModel taskScheduleModel) {
        StringBuilder cronExp = new StringBuilder();

        if (null == taskScheduleModel.getJobType()) {
            logger.info("执行周期未配置,{}", taskScheduleModel.getJobType());
            return "";
        }

        if (null != taskScheduleModel.getSecond()
                && null == taskScheduleModel.getMinute()
                && null == taskScheduleModel.getHour()) {
            //每隔几秒
            if (taskScheduleModel.getJobType() == 1) {
                cronExp.append("0/").append(taskScheduleModel.getSecond());
                cronExp.append(" ");
                cronExp.append("* ");
                cronExp.append("* ");
                cronExp.append("* ");
                cronExp.append("* ");
                cronExp.append("?");
            }
        }

        if (null == taskScheduleModel.getSecond()
                && null != taskScheduleModel.getMinute()
                && null == taskScheduleModel.getHour()) {
            //每隔分钟
            if (taskScheduleModel.getJobType() == 2) {
                cronExp.append("0 ");
                cronExp.append("0/").append(taskScheduleModel.getMinute());
                cronExp.append(" ");
                cronExp.append("* ");
                cronExp.append("* ");
                cronExp.append("* ");
                cronExp.append("?");
            }
        }

        if (null == taskScheduleModel.getSecond()
                && null == taskScheduleModel.getMinute()
                && null != taskScheduleModel.getHour()) {
            //每隔几小时
            if (taskScheduleModel.getJobType() == 3) {
                cronExp.append("0 ");
                cronExp.append("0 ");
                cronExp.append("0/").append(taskScheduleModel.getHour());
                cronExp.append(" ");
                cronExp.append("* ");
                cronExp.append("* ");
                cronExp.append("?");
            }

        }

        if (null != taskScheduleModel.getSecond()
                && null != taskScheduleModel.getMinute()
                && null != taskScheduleModel.getHour()) {
            //秒
            cronExp.append(taskScheduleModel.getSecond()).append(" ");
            //分
            cronExp.append(taskScheduleModel.getMinute()).append(" ");
            //小时
            cronExp.append(taskScheduleModel.getHour()).append(" ");
            //每天
            if (taskScheduleModel.getJobType() == 4) {
                cronExp.append("* ");//日
                cronExp.append("* ");//月
                cronExp.append("?");//周
            }

            //按每周
            else if (taskScheduleModel.getJobType() == 5) {
                //一个月中第几天
                cronExp.append("? ");
                //月份
                cronExp.append("* ");
                //周
                Integer[] weeks = taskScheduleModel.getDayOfWeeks();
                for (int i = 0; i < weeks.length; i++) {
                    if (i == 0) {
                        cronExp.append(weeks[i]);
                    } else {
                        cronExp.append(",").append(weeks[i]);
                    }
                }

            }

            //按每月
            else if (taskScheduleModel.getJobType() == 6) {
                //一个月中的哪几天
                Integer[] days = taskScheduleModel.getDayOfMonths();
                for (int i = 0; i < days.length; i++) {
                    if (i == 0) {
                        cronExp.append(days[i]);
                    } else {
                        cronExp.append(",").append(days[i]);
                    }
                }
                //月份
                cronExp.append(" * ");
                //周
                cronExp.append("?");
            }

        }
        return cronExp.toString();
    }

    /**
     * 生成计划的详细描述
     * @param taskScheduleModel cron表达式模型
     * @return 详细描述
     */
    public static String createDescription(TaskScheduleModel taskScheduleModel) {
        if (null == taskScheduleModel.getJobType()) {
            logger.info("执行周期未配置");
            return "";
        }
        StringBuilder description = new StringBuilder();
        //每*秒
        if (taskScheduleModel.getJobType() == 1) {
            if (null == taskScheduleModel.getSecond()){
                logger.info("配置的每*秒执行,秒数据不存在");
                return "";
            }
            description.append("每");
            description.append(taskScheduleModel.getSecond()).append("秒");
            description.append("执行一次");
        }
        //每*分钟
        else if (taskScheduleModel.getJobType() == 2) {
            if (null == taskScheduleModel.getMinute()){
                logger.info("配置的每*分钟执行,分钟数据不存在");
                return "";
            }
            description.append("每");
            description.append(taskScheduleModel.getMinute()).append("分");
            description.append("执行一次");
        }
        //每*小时
        else if (taskScheduleModel.getJobType() == 3) {
            if (null == taskScheduleModel.getHour()){
                logger.info("配置的每*小时执行,小时数据不存在");
                return "";
            }
            description.append("每");
            description.append(taskScheduleModel.getHour()).append("小时");
            description.append("执行一次");
        } else {
            if (null != taskScheduleModel.getSecond()
                    && null != taskScheduleModel.getMinute()
                    && null != taskScheduleModel.getHour()) {

                //按每天
                if (taskScheduleModel.getJobType() == 4) {
                    description.append("每天");
                    description.append(taskScheduleModel.getHour()).append("时");
                    description.append(taskScheduleModel.getMinute()).append("分");
                    description.append(taskScheduleModel.getSecond()).append("秒");
                    description.append("执行");
                }

                //按每周
                else if (taskScheduleModel.getJobType() == 5) {
                    if (taskScheduleModel.getDayOfWeeks() != null && taskScheduleModel.getDayOfWeeks().length > 0) {
                        description.append("每周的:");
                        for (int i : taskScheduleModel.getDayOfWeeks()) {
                            description.append("周".concat(String.valueOf(i)).concat(" "));
                        }
                        description.append(" ");
                    }
                    if (null != taskScheduleModel.getSecond()
                            && null != taskScheduleModel.getMinute()
                            && null != taskScheduleModel.getHour()) {
                        description.append(taskScheduleModel.getHour()).append("时");
                        description.append(taskScheduleModel.getMinute()).append("分");
                        description.append(taskScheduleModel.getSecond()).append("秒");
                    }
                    description.append("执行");
                }

                //按每月
                else if (taskScheduleModel.getJobType() == 6) {
                    //选择月份
                    if (taskScheduleModel.getDayOfMonths() != null && taskScheduleModel.getDayOfMonths().length > 0) {
                        description.append("每月的:");
                        for (int i : taskScheduleModel.getDayOfMonths()) {
                            description.append(String.valueOf(i).concat("号").concat(" "));
                        }
                        description.append(" ");
                    }
                    description.append(taskScheduleModel.getHour()).append("时");
                    description.append(taskScheduleModel.getMinute()).append("分");
                    description.append(taskScheduleModel.getSecond()).append("秒");
                    description.append("执行");
                }

            }
        }
        return description.toString();
    }

    //参考例子
    public static void main(String[] args) {
        //执行时间：每天的12时12分12秒 start
        TaskScheduleModel taskScheduleModel = new TaskScheduleModel();

        //按每秒
        taskScheduleModel.setJobType(1);
        taskScheduleModel.setSecond(30);
        String cronExp = createCronExpression(taskScheduleModel);
        logger.info("cron表达式:{}     描述:{}", cronExp, createDescription(taskScheduleModel));

        //按每分钟
        taskScheduleModel = new TaskScheduleModel();
        taskScheduleModel.setJobType(2);
        taskScheduleModel.setMinute(8);
        String cronExpp = createCronExpression(taskScheduleModel);
        logger.info("cron表达式:{}     描述:{}", cronExpp, createDescription(taskScheduleModel));

        //按每分钟
        taskScheduleModel = new TaskScheduleModel();
        taskScheduleModel.setJobType(3);
        taskScheduleModel.setHour(1);
        String cronExpph = createCronExpression(taskScheduleModel);
        logger.info("cron表达式:{}     描述:{}", cronExpph, createDescription(taskScheduleModel));

        //按每天
        taskScheduleModel = new TaskScheduleModel();
        taskScheduleModel.setJobType(4);
        //时
        Integer hour = 12;
        //分
        Integer minute = 12;
        //秒
        Integer second = 12;
        taskScheduleModel.setHour(hour);
        taskScheduleModel.setMinute(minute);
        taskScheduleModel.setSecond(second);
        String cropExp = createCronExpression(taskScheduleModel);
        logger.info("cron表达式:{}    描述:{}", cropExp, createDescription(taskScheduleModel));
        //执行时间：每天的12时12分12秒 end

        //每周的哪几天执行
        taskScheduleModel = new TaskScheduleModel();
        taskScheduleModel.setJobType(5);
        Integer[] dayOfWeeks = new Integer[3];
        dayOfWeeks[0] = 1;
        dayOfWeeks[1] = 2;
        dayOfWeeks[2] = 3;
        taskScheduleModel.setSecond(12);
        taskScheduleModel.setMinute(12);
        taskScheduleModel.setHour(12);
        taskScheduleModel.setDayOfWeeks(dayOfWeeks);
        cropExp = createCronExpression(taskScheduleModel);
        logger.info("cron表达式:{}    描述:{}", cropExp, createDescription(taskScheduleModel));

        //每月的哪几天执行
        taskScheduleModel = new TaskScheduleModel();
        taskScheduleModel.setJobType(6);
        Integer[] dayOfMonths = new Integer[3];
        dayOfMonths[0] = 1;
        dayOfMonths[1] = 21;
        dayOfMonths[2] = 13;
        taskScheduleModel.setSecond(12);
        taskScheduleModel.setMinute(12);
        taskScheduleModel.setHour(12);
        taskScheduleModel.setDayOfMonths(dayOfMonths);
        cropExp = createCronExpression(taskScheduleModel);
        logger.info("cron表达式:{}    描述:{}", cropExp, createDescription(taskScheduleModel));

    }

}
