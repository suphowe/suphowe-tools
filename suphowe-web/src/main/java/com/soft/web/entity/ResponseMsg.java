package com.soft.web.entity;

import lombok.Data;

/**
 * 返回数据配置
 * @author suphowe
 */
@Data
public class ResponseMsg {

    int code;
    String message;
    Object data;
}
