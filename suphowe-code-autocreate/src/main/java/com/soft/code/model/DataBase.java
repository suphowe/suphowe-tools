package com.soft.code.model;

import lombok.Data;
import lombok.ToString;

/**
 * 数据库配置
 * @author suphowe
 */
@Data
@ToString
public class DataBase {

    private String username;
    private String password;
    private String url;
}
