package com.soft.web.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 系统日志
 * @author suphowe
 */
@Data
public class SysLogInfo implements Serializable {

    private static final long serialVersionUID = 1965363497916938072L;
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
