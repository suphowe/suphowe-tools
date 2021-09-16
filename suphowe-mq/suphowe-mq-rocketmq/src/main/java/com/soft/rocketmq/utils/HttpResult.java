package com.soft.rocketmq.utils;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 服务端返回结果API
 *
 * @author suphowe
 */
public class HttpResult {

    /**
     * 返回Json类型数据
     *
     * @param code HTTP响应状态码
     * @return json数据
     */
    public static String ok(int code) {
        HashMap<String, Object> result = new HashMap<>(4);
        result.put("code", code);
        result.put("message", HttpResult.CODE_MSG_ZH.get(code));
        return new Gson().toJson(result);
    }

    /**
     * 返回Json类型数据
     *
     * @param code HTTP响应状态码
     * @param data 返回数据
     * @return json数据
     */
    public static String returnJsonData(int code, Object data) {
        HashMap<String, Object> result = new HashMap<>(4);
        result.put("code", code);
        result.put("message", HttpResult.CODE_MSG_ZH.get(code));
        result.put("data", data);
        return new Gson().toJson(result);
    }

    /**
     * 返回Json类型数据
     *
     * @param code HTTP响应状态码
     * @return json数据
     */
    public static String returnJsonData(int code) {
        HashMap<String, Object> result = new HashMap<>(4);
        result.put("code", code);
        result.put("message", HttpResult.CODE_MSG_ZH.get(code));
        return new Gson().toJson(result);
    }

    /**
     * 返回Json类型数据
     *
     * @param code HTTP响应状态码
     * @param linkedHashMap HTTP响应字典
     * @return json数据
     */
    public static String backOut(int code, LinkedHashMap<Object, Object> linkedHashMap) {
        HashMap<String, Object> result = new HashMap<>(4);
        result.put("Code", code);
        result.put("Message", linkedHashMap.get(code));
        return new Gson().toJson(result);
    }

    /**
     * 返回Json类型数据
     *
     * @param code HTTP响应状态码
     * @param linkedHashMap HTTP响应字典
     * @param returnResult 需要返回结果
     * @return json数据
     */
    public static String backOut(int code, LinkedHashMap<Object, Object> linkedHashMap, Object returnResult) {
        HashMap<String, Object> result = new HashMap<>(4);
        result.put("Code", code);
        result.put("Message", linkedHashMap.get(code));
        result.put("Data", returnResult);
        return new Gson().toJson(result);
    }

    /**
     * HTTP响应状态码与响应信息映射关系
     */
    public static LinkedHashMap<Object, Object> CODE_MSG_EN = new LinkedHashMap<>();
    static {
        CODE_MSG_EN.put(100, "Continue");
        CODE_MSG_EN.put(200, "Success");
        //请求无效
        CODE_MSG_EN.put(400, "Data Format Error");
        //服务器内部错误
        CODE_MSG_EN.put(500, "Internel Server Error");
        //错误网关
        CODE_MSG_EN.put(502, "Bad Gateway");
        //服务器维护
        CODE_MSG_EN.put(50201, "Service Unavailable");
    }

    /**
     * HTTP响应状态码与响应信息映射关系
     */
    public static LinkedHashMap<Object, Object> CODE_MSG_ZH = new LinkedHashMap<>();
    static {
        CODE_MSG_ZH.put(100, "继续");
        CODE_MSG_ZH.put(101, "切换协议");
        CODE_MSG_ZH.put(110, "重新启动标记答复");
        CODE_MSG_ZH.put(120, "服务已就绪,等待开始");
        CODE_MSG_ZH.put(125, "数据连接已打开,正在开始传输");
        CODE_MSG_ZH.put(150, "文件状态正常,准备打开数据连接");
        CODE_MSG_ZH.put(200, "成功");
        CODE_MSG_ZH.put(201, "已创建");
        CODE_MSG_ZH.put(202, "已接受");
        CODE_MSG_ZH.put(203, "非权威性信息");
        CODE_MSG_ZH.put(204, "无内容");
        CODE_MSG_ZH.put(205, "重置内容");
        CODE_MSG_ZH.put(206, "部分内容");
        CODE_MSG_ZH.put(220, "服务就绪,可以执行新用户的请求");
        CODE_MSG_ZH.put(221, "服务关闭控制连接.如果适当,请注销");
        CODE_MSG_ZH.put(225, "数据连接打开,没有进行中的传输");
        CODE_MSG_ZH.put(226, "关闭数据连接");
        CODE_MSG_ZH.put(227, "进入被动模式");
        CODE_MSG_ZH.put(230, "用户已登录,继续进行");
        CODE_MSG_ZH.put(250, "请求的文件操作正确,已完成");
        CODE_MSG_ZH.put(257, "已创建 PATHNAME");

        CODE_MSG_ZH.put(302, "对象已移动");
        CODE_MSG_ZH.put(304, "未修改");
        CODE_MSG_ZH.put(307, "临时重定向");
        CODE_MSG_ZH.put(331, "用户名正确,需要密码");
        CODE_MSG_ZH.put(332, "需要登录帐户");
        CODE_MSG_ZH.put(350, "请求的文件操作正在等待进一步的信息");

        CODE_MSG_ZH.put(400, "错误的请求");

        CODE_MSG_ZH.put(401, "未授权:访问被拒绝");
        CODE_MSG_ZH.put(4011, "未授权:登陆失败");
        CODE_MSG_ZH.put(4012, "未授权:服务器配置问题导致登录失败");
        CODE_MSG_ZH.put(4013, "未授权:由于 ACL 对资源的限制而未获得授权");
        CODE_MSG_ZH.put(4014, "未授权:筛选器授权失败");
        CODE_MSG_ZH.put(4015, "未授权:ISAPI/CGI 应用程序授权失败");
        CODE_MSG_ZH.put(4017, "未授权:访问被 Web 服务器上的 URL 授权策略拒绝");

        CODE_MSG_ZH.put(403, "禁止访问");
        CODE_MSG_ZH.put(4031, "禁止访问：禁止可执行访问");
        CODE_MSG_ZH.put(4032, "禁止访问：禁止读访问");
        CODE_MSG_ZH.put(4033, "禁止访问：禁止写访问");
        CODE_MSG_ZH.put(4034, "禁止访问：要求 SSL");
        CODE_MSG_ZH.put(4035, "禁止访问：要求 SSL 128");
        CODE_MSG_ZH.put(4036, "禁止访问：IP 地址被拒绝");
        CODE_MSG_ZH.put(4037, "禁止访问：要求客户证书");
        CODE_MSG_ZH.put(4038, "禁止访问：禁止站点访问");
        CODE_MSG_ZH.put(4039, "禁止访问：连接的用户过多");
        CODE_MSG_ZH.put(40310, "禁止访问：配置无效");
        CODE_MSG_ZH.put(40311, "禁止访问：密码更改");
        CODE_MSG_ZH.put(40312, "禁止访问：映射器拒绝访问");
        CODE_MSG_ZH.put(40313, "禁止访问：客户证书已被吊销");
        CODE_MSG_ZH.put(40315, "禁止访问：客户访问许可过多");
        CODE_MSG_ZH.put(40316, "禁止访问：客户证书不可信或者无效");
        CODE_MSG_ZH.put(40317, "禁止访问：客户证书已经到期或者尚未生效");
        CODE_MSG_ZH.put(40318, "禁止访问：在当前的应用程序池中不能执行所请求的 URL");
        CODE_MSG_ZH.put(40319, "禁止访问：不能为这个应用程序池中的客户端执行 CGI");
        CODE_MSG_ZH.put(40320, "禁止访问：Passport 登录失败");

        CODE_MSG_ZH.put(404, "无法找到文件");
        CODE_MSG_ZH.put(4041, "无法在所请求的端口上访问 Web 站点");
        CODE_MSG_ZH.put(4042, "Web 服务扩展锁定策略阻止本请求");
        CODE_MSG_ZH.put(4043, "MIME 映射策略阻止本请求");

        CODE_MSG_ZH.put(405, "用来访问本页面的 HTTP 谓词不被允许(方法不被允许)");
        CODE_MSG_ZH.put(406, "客户端浏览器不接受所请求页面的 MIME 类型");
        CODE_MSG_ZH.put(407, "要求代理身份验证");
        CODE_MSG_ZH.put(410, "永远不可用");
        CODE_MSG_ZH.put(412, "先决条件失败");
        CODE_MSG_ZH.put(413, "请求实体太大");
        CODE_MSG_ZH.put(414, "请求 URI 太长");
        CODE_MSG_ZH.put(415, "不支持的媒体类型");
        CODE_MSG_ZH.put(416, "所请求的范围无法满足");
        CODE_MSG_ZH.put(417, "执行失败");
        CODE_MSG_ZH.put(421, "服务不可用,正在关闭控制连接.如果服务确定它必须关闭,将向任何命令发送这一应答");
        CODE_MSG_ZH.put(423, "锁定的错误");
        CODE_MSG_ZH.put(425, "无法打开数据连接");
        CODE_MSG_ZH.put(426, "连接关闭,无法取得目录列表");
        CODE_MSG_ZH.put(450, "未执行请求的文件操作,文件不可用");
        CODE_MSG_ZH.put(451, "请求的操作异常终止,正在处理本地错误");
        CODE_MSG_ZH.put(452, "未执行请求的操作,系统存储空间不够");

        CODE_MSG_ZH.put(500, "内部服务器错误");
        CODE_MSG_ZH.put(50010, "内部服务器错误 ASP 错误");
        CODE_MSG_ZH.put(50011, "服务器关闭");
        CODE_MSG_ZH.put(50012, "应用程序正忙于在 Web 服务器上重新启动");
        CODE_MSG_ZH.put(50013, "Web 服务器太忙");
        CODE_MSG_ZH.put(50014, "应用程序无效");
        CODE_MSG_ZH.put(50015, "不允许请求 global.asa");
        CODE_MSG_ZH.put(50016, "UNC 授权凭据不正确");
        CODE_MSG_ZH.put(50018, "URL 授权存储不能打开");
        CODE_MSG_ZH.put(500100, "内部 ASP 错误");

        CODE_MSG_ZH.put(501, "在参数中有语法错误");

        CODE_MSG_ZH.put(502, "Web 服务器用作网关或代理服务器时收到了无效响应");
        CODE_MSG_ZH.put(5022, "CGI 应用程序出错");

        CODE_MSG_ZH.put(503, "服务不可用");
        CODE_MSG_ZH.put(504, "网关超时");
        CODE_MSG_ZH.put(505, "HTTP 版本不受支持");

        CODE_MSG_ZH.put(530, "未登录");
        CODE_MSG_ZH.put(532, "存储文件需要帐户");
        CODE_MSG_ZH.put(550, "未执行请求的操作,文件不可用");
        CODE_MSG_ZH.put(551, "请求的操作异常终止,未知的页面类型");
        CODE_MSG_ZH.put(552, "请求的文件操作异常终止,超出存储分配");
        CODE_MSG_ZH.put(553, "未执行请求的操作,不允许的文件名");

    }

    /**
     * HTTP响应状态码与响应信息映射关系
     */
    public static LinkedHashMap<Object, Object> QUARTZ_CODE_MSG = new LinkedHashMap<>();
    static {
        QUARTZ_CODE_MSG.put(200, "成功");
        QUARTZ_CODE_MSG.put(412, "请求数据存在空值");
        QUARTZ_CODE_MSG.put(413, "任务已存在");
        QUARTZ_CODE_MSG.put(414, "未找到数据");
        QUARTZ_CODE_MSG.put(500, "失败");
    }
}