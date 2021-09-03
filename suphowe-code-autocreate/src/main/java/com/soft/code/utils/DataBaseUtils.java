package com.soft.code.utils;


import com.soft.code.model.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库公共方法
 * @author suphowe
 */
public class DataBaseUtils {

    private static Connection connection;

    public static Connection getConnection() {
        return connection;
    }

    public static Connection initDb(DataBase dataBase) {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(dataBase.getUrl(), dataBase.getUsername(), dataBase.getPassword());
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
