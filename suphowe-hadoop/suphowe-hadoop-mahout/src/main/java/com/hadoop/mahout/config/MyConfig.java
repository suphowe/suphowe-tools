package com.hadoop.mahout.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
public class MyConfig {

    @Bean
    public DataModel getMySQLDataModel(){
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName("localhost");
        dataSource.setUser("root");
        dataSource.setPassword("root");
        //数据库名字
        dataSource.setDatabaseName("recommend");
        //参数1：mysql数据源信息，参数2：表名，参数3：用户列字段，参数4：商品列字段，参数5：偏好值字段，参数6：时间戳
        JDBCDataModel dataModel=new MySQLJDBCDataModel(dataSource,"user_pianhao_data1","uid","pid","val", "time");

        /**
         *  DataModel可基于数据也可基于文件
         *  文件汇总数据格式
         *  用户id::商品id::偏好分值::时间戳
         *  1::122::5::838985046
         *  1::185::5::838983525
         *  1::231::5::838983392
         *  .........
         */

        // File file = new File("E:\\initData.dat");
        // try {
        //   DataModel dataModel = new GroupLensDataModel(file);
        // } catch (IOException e) {
        //    e.printStackTrace();
        // }

        return dataModel;
    }
}