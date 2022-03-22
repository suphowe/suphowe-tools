package com.soft.method.entity;

import lombok.Data;

/**
 * 系统日志
 * @author suphowe
 */
@Data
public class SysLogInfo {

    int id;
    String module;
    String funcType;
    String funcDesc;
    String method;
    String params;
    String result;
    String requestIp;
    String requestUri;
    int checkStatus;
    String createTime;
}
