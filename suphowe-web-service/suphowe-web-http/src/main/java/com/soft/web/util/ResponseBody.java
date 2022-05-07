package com.soft.web.util;

import com.soft.web.entity.ResponseMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;

/**
 * 返回数据
 * @author suphowe
 */
public class ResponseBody {

    private static final Logger logger = LoggerFactory.getLogger(ResponseBody.class);

    String code;
    Object object;

    public ResponseBody(String code) {
        this.code = code;
    }

    public ResponseBody(String code, Object object) {
        this.code = code;
        this.object = object;
    }

    public ResponseMsg createNullDataBody() {
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setCode(code);
        responseMsg.setMsgEn(RESPONSE_MSG_EN.get(code));
        responseMsg.setMsgCn(RESPONSE_MSG_CN.get(code));
        logger.info("返回数据:\r\n{}", responseMsg);
        return responseMsg;
    }

    public ResponseMsg createDataBody() {
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setCode(code);
        responseMsg.setMsgEn(RESPONSE_MSG_EN.get(code));
        responseMsg.setMsgCn(RESPONSE_MSG_CN.get(code));
        if (object != null) {
            responseMsg.setData(object);
        }
        logger.info("返回数据:\r\n{}", responseMsg);
        return responseMsg;
    }


    public static LinkedHashMap<Object, Object> RESPONSE_MSG_EN = new LinkedHashMap<>();
    static {
        RESPONSE_MSG_EN.put("100", "Continue");
        RESPONSE_MSG_EN.put("101", "SwitchingProtocols");
        RESPONSE_MSG_EN.put("200", "OK");
        RESPONSE_MSG_EN.put("201", "Created");
        RESPONSE_MSG_EN.put("202", "Accepted");
        RESPONSE_MSG_EN.put("203", "Non-AuthoritativeInformation");
        RESPONSE_MSG_EN.put("204", "No Content");
        RESPONSE_MSG_EN.put("205", "Reset Content");
        RESPONSE_MSG_EN.put("206", "Partial Content");
        RESPONSE_MSG_EN.put("300", "Multiple Choices");
        RESPONSE_MSG_EN.put("301", "Moved Permanently");
        RESPONSE_MSG_EN.put("302", "Found");
        RESPONSE_MSG_EN.put("303", "See Other");
        RESPONSE_MSG_EN.put("304", "Not Modified");
        RESPONSE_MSG_EN.put("305", "Use Proxy");
        RESPONSE_MSG_EN.put("307", "TemporaryRedirect");
        RESPONSE_MSG_EN.put("400", "Bad request");
        RESPONSE_MSG_EN.put("401", "Unauthorized");
        RESPONSE_MSG_EN.put("401.1", "Logon failed");
        RESPONSE_MSG_EN.put("401.2", "Logon failed due to server configuration");
        RESPONSE_MSG_EN.put("401.3", "Unauthorized due to ACL on resource");
        RESPONSE_MSG_EN.put("401.4", "Authorization failed by filter");
        RESPONSE_MSG_EN.put("401.5", "Authorization failed by ISAPI/CGI application");
        RESPONSE_MSG_EN.put("402", "Payment Required");
        RESPONSE_MSG_EN.put("403", "Forbidden");
        RESPONSE_MSG_EN.put("403.1", "Execute access forbidden");
        RESPONSE_MSG_EN.put("403.2", "Read access forbidden");
        RESPONSE_MSG_EN.put("403.3", "Write access forbidden");
        RESPONSE_MSG_EN.put("403.4", "SSL required");
        RESPONSE_MSG_EN.put("403.5", "SSL 128 required");
        RESPONSE_MSG_EN.put("403.6", "IP address rejected");
        RESPONSE_MSG_EN.put("403.7", "Client certificate required");
        RESPONSE_MSG_EN.put("403.8", "Site access denied");
        RESPONSE_MSG_EN.put("403.9", "Too many users");
        RESPONSE_MSG_EN.put("403.10", "Invalid configuration");
        RESPONSE_MSG_EN.put("403.11", "Password change");
        RESPONSE_MSG_EN.put("403.12", "Mapper denied access");
        RESPONSE_MSG_EN.put("403.13", "Client certificate revoked");
        RESPONSE_MSG_EN.put("403.14", "Directory listing denied");
        RESPONSE_MSG_EN.put("403.15", "Client Access Licenses exceeded");
        RESPONSE_MSG_EN.put("403.16", "Client certificate untrusted or invalid");
        RESPONSE_MSG_EN.put("403.17", "Client certificate has expired or is not yet valid");
        RESPONSE_MSG_EN.put("404", "Not found");
        RESPONSE_MSG_EN.put("404.1", "Site not found");
        RESPONSE_MSG_EN.put("405", "Method not allowed");
        RESPONSE_MSG_EN.put("406", "Not acceptable");
        RESPONSE_MSG_EN.put("407", "Proxy authentication required");
        RESPONSE_MSG_EN.put("408", "Request Time-out");
        RESPONSE_MSG_EN.put("409", "Conflict");
        RESPONSE_MSG_EN.put("410", "Gone");
        RESPONSE_MSG_EN.put("411", "Length Required");
        RESPONSE_MSG_EN.put("412", "Precondition Failed");
        RESPONSE_MSG_EN.put("413", "Request Entity TooLarge");
        RESPONSE_MSG_EN.put("414", "Request-URI TooLarge");
        RESPONSE_MSG_EN.put("415", "Unsupported MediaType");
        RESPONSE_MSG_EN.put("416", "Requested range not satisfiable");
        RESPONSE_MSG_EN.put("417", "ExpectationFailed");
        RESPONSE_MSG_EN.put("500", "Internal server error");
        RESPONSE_MSG_EN.put("500.11", "Server Close");
        RESPONSE_MSG_EN.put("500.12", "Application restarting");
        RESPONSE_MSG_EN.put("500.13", "Server too busy");
        RESPONSE_MSG_EN.put("500.14", "Server Invalid");
        RESPONSE_MSG_EN.put("500.15", "Requests for Global.asa not allowed");
        RESPONSE_MSG_EN.put("500.100", "asp error");
        RESPONSE_MSG_EN.put("501", "Not implemented");
        RESPONSE_MSG_EN.put("502", "Bad gateway");
        RESPONSE_MSG_EN.put("503", "ServiceUnavailable");
        RESPONSE_MSG_EN.put("504", "Gateway Time-out");
        RESPONSE_MSG_EN.put("505", "HTTP Version not supported");
    }

    public static LinkedHashMap<Object, Object> RESPONSE_MSG_CN = new LinkedHashMap<>();
    static {
        RESPONSE_MSG_CN.put("100", "继续");
        RESPONSE_MSG_CN.put("101", "切换协议");
        RESPONSE_MSG_CN.put("200", "成功");
        RESPONSE_MSG_CN.put("201", "已创建");
        RESPONSE_MSG_CN.put("202", "已接受");
        RESPONSE_MSG_CN.put("203", "非权威性信息");
        RESPONSE_MSG_CN.put("204", "无内容");
        RESPONSE_MSG_CN.put("205", "重置内容");
        RESPONSE_MSG_CN.put("206", "部分内容");
        RESPONSE_MSG_CN.put("300", "多种选择");
        RESPONSE_MSG_CN.put("301", "永久移动");
        RESPONSE_MSG_CN.put("302", "临时移动");
        RESPONSE_MSG_CN.put("303", "查看其他位置");
        RESPONSE_MSG_CN.put("304", "未修改");
        RESPONSE_MSG_CN.put("305", "使用代理");
        RESPONSE_MSG_CN.put("307", "临时重定向");
        RESPONSE_MSG_CN.put("400", "服务器不理解请求的语法");
        RESPONSE_MSG_CN.put("401", "未授权");
        RESPONSE_MSG_CN.put("401.1", "未授权：登录失败");
        RESPONSE_MSG_CN.put("401.2", "未授权：服务器配置问题导致登录失败");
        RESPONSE_MSG_CN.put("401.3", "未授权：ACL 禁止访问资源");
        RESPONSE_MSG_CN.put("401.4", "未授权：授权被筛选器拒绝");
        RESPONSE_MSG_CN.put("401.5", "未授权：ISAPI 或 CGI 授权失败");
        RESPONSE_MSG_CN.put("402", "请求要求身份验证");
        RESPONSE_MSG_CN.put("403", "服务器拒绝请求");
        RESPONSE_MSG_CN.put("403.1", "禁止访问：禁止可执行访问");
        RESPONSE_MSG_CN.put("403.2", "禁止访问：禁止读访问");
        RESPONSE_MSG_CN.put("403.3", "禁止访问：禁止写访问");
        RESPONSE_MSG_CN.put("403.4", "禁止访问：要求 SSL");
        RESPONSE_MSG_CN.put("403.5", "禁止访问：要求 SSL 128");
        RESPONSE_MSG_CN.put("403.6", "禁止访问：IP 地址被拒绝");
        RESPONSE_MSG_CN.put("403.7", "禁止访问：要求客户证书");
        RESPONSE_MSG_CN.put("403.8", "禁止访问：禁止站点访问");
        RESPONSE_MSG_CN.put("403.9", "禁止访问：连接的用户过多");
        RESPONSE_MSG_CN.put("403.10", "禁止访问：配置无效");
        RESPONSE_MSG_CN.put("403.11", "禁止访问：密码更改");
        RESPONSE_MSG_CN.put("403.12", "禁止访问：映射器拒绝访问");
        RESPONSE_MSG_CN.put("403.13", "禁止访问：客户证书已被吊销");
        RESPONSE_MSG_CN.put("403.14", "禁止访问：目录列表被拒绝");
        RESPONSE_MSG_CN.put("403.15", "禁止访问：客户访问许可过多");
        RESPONSE_MSG_CN.put("403.16", "禁止访问：客户证书不可信或者无效");
        RESPONSE_MSG_CN.put("403.17", "禁止访问：客户证书已经到期或者尚未生效");
        RESPONSE_MSG_CN.put("404", "无法找到资源");
        RESPONSE_MSG_CN.put("404.1", "无法找到 Web 站点");
        RESPONSE_MSG_CN.put("405", "资源被禁止");
        RESPONSE_MSG_CN.put("406", "无法接受");
        RESPONSE_MSG_CN.put("407", "要求代理身份验证");
        RESPONSE_MSG_CN.put("408", "请求超时");
        RESPONSE_MSG_CN.put("409", "请求发生冲突");
        RESPONSE_MSG_CN.put("410", "永远不可用");
        RESPONSE_MSG_CN.put("411", "服务器不接受不含有效内容长度标头字段的请求");
        RESPONSE_MSG_CN.put("412", "服务器未满足请求者在请求中设置的其中一个前提条件");
        RESPONSE_MSG_CN.put("413", "服务器无法处理请求，因为请求实体过大，超出服务器的处理能力");
        RESPONSE_MSG_CN.put("414", "请求 - URI 太长");
        RESPONSE_MSG_CN.put("415", "请求的格式不受请求页面的支持");
        RESPONSE_MSG_CN.put("416", "页面无法提供请求的范围");
        RESPONSE_MSG_CN.put("417", "服务器未满足”期望”请求标头字段的要求");
        RESPONSE_MSG_CN.put("500", "内部服务器错误");
        RESPONSE_MSG_CN.put("500.11", "服务器关闭");
        RESPONSE_MSG_CN.put("500.12", "应用程序重新启动");
        RESPONSE_MSG_CN.put("500.13", "服务器太忙");
        RESPONSE_MSG_CN.put("500.14", "应用程序无效");
        RESPONSE_MSG_CN.put("500.15", "不允许请求 Global.asa");
        RESPONSE_MSG_CN.put("500.100", "内部服务器错误 - ASP 错误");
        RESPONSE_MSG_CN.put("501", "尚未实施");
        RESPONSE_MSG_CN.put("502", "错误网关");
        RESPONSE_MSG_CN.put("503", "服务不可用");
        RESPONSE_MSG_CN.put("504", "网关超时");
        RESPONSE_MSG_CN.put("505", "HTTP 版本不受支持");
    }
}
