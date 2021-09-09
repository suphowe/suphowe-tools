package com.soft.method.smsverify;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * 短信发送网络工具类
 * @author suphowe
 */
public class SmsVerifyHttpUtil {

    private static final Logger log = LoggerFactory.getLogger(SmsVerifyHttpUtil.class);

    /**
     * 构造通用参数timestamp、sig和respDataType
     *
     * @return 构造参数
     */
    public static String createCommonParam() {
        // 时间戳
        long timestamp = System.currentTimeMillis();
        // 签名
        String sig = DigestUtils.md5Hex(SmsVerifyConfig.ACCOUNT_SID + SmsVerifyConfig.AUTH_TOKEN + timestamp);

        return "&timestamp=" + timestamp + "&sig=" + sig + "&respDataType=" + SmsVerifyConfig.RESP_DATA_TYPE;
    }


    /**
     * post请求
     *
     * @param url  访问url
     * @param body 要post的数据
     * @return 发送返回结果
     */
    public static String post(String url, String body) {
        log.info("post url:{}", url);
        log.info("post body:{}", body);
        StringBuilder result = new StringBuilder();
        try {
            OutputStreamWriter out = null;
            BufferedReader in = null;
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();

            // 设置连接参数
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(20000);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // 提交数据
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(body);
            out.flush();

            // 读取返回数据
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line = "";
            // 读第一行不加换行符
            boolean firstLine = true;
            while ((line = in.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                } else {
                    result.append(System.lineSeparator());
                }
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
