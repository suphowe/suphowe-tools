package com.hadoop.sqoop.controller;

import com.hadoop.sqoop.bean.SqoopBean;
import com.hadoop.sqoop.service.SqoopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * sqoop 测试控制类
 * @author suphowe
 */
@RestController
public class SqoopController {

    @Autowired
    private SqoopService sqoopService;

    @RequestMapping("/hi")
    public String get() {
        return "你好";
    }

    /**
     * 数据从mysql到hdfs
     */
    @PostMapping("/mysql2hdfs")
    @ResponseBody
    public SqoopBean sqoopTransform(String jdbc, String driver, String username, String password, String table, int m, String targetdir, String putlocation) throws Exception {
        return sqoopService.mysql2Hdfs(jdbc, driver, username, password, table, m, targetdir, putlocation);
        //返回0正确，返回1错误
    }

    /**
     * 数据从mysql到hbase
     */
    @PostMapping("/mysql2hbase")
    @ResponseBody
    public SqoopBean transformMysql2Hbase(String jdbc, String driver, String username, String password, String mysqlTable, String hbaseTableName, String columnFamily, String rowkey, int m) throws Exception {
        return sqoopService.mysql2Hbase(jdbc, driver, username, password, mysqlTable, hbaseTableName, columnFamily, rowkey, m);
    }
}

