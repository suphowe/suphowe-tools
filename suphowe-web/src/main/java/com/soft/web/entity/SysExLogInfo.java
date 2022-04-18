package com.soft.web.entity;

import lombok.Data;

/**
 * 异常日志
 * @author suphowe
 */
@Data
public class SysExLogInfo {

    int id;
    String params;
    String method;
    String exceptionName;
    String exceptionMessage;
    String requestIp;
    String requestUri;
    int checkStatus;
    String createTime;
}
