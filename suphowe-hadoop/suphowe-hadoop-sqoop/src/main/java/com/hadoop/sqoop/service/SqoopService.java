package com.hadoop.sqoop.service;

import com.hadoop.sqoop.bean.SqoopBean;

/**
 * 数据传输接口
 * @author suphowe
 */
public interface SqoopService {

    /**
     * 从数据库到hdfs
     * @param jdbc 数据库jdbc
     * @param driver 数据库驱动
     * @param username 数据库用户名
     * @param password 数据库密码
     * @param table 数据库表
     * @param m 数据长度
     * @param targetdir 目标文件夹
     * @param putlocation HDFS服务地址
     * @throws Exception 异常
     * @return 返回0正确，返回1错误
     */
    SqoopBean mysql2Hdfs(String jdbc, String driver, String username, String password, String table, int m, String targetdir, String putlocation) throws Exception;

    /**
     * mysql到hbase
     * @param jdbc 数据库jdbc
     * @param driver 数据库驱动
     * @param username 数据库用户名
     * @param password 数据库密码
     * @param mysqlTable 数据库表
     * @param hbaseTableName hbase表名
     * @param columnFamily 列族
     * @param rowkey 行key
     * @param m 数据长度
     * @throws Exception 异常
     * @return 返回0正确，返回1错误
     */
    SqoopBean mysql2Hbase( String jdbc, String driver, String username, String password,String mysqlTable, String hbaseTableName, String columnFamily, String rowkey, int m) throws Exception;

}
