package com.soft.method.smsverify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;

/**
 * 短信发送测试类
 * @author suphowe
 */
public class SmsVerifyTest {

    private static final Logger log = LoggerFactory.getLogger(SmsVerifyTest.class);

    private static String to = "13368497214";
    private static String smsContent = "【小陶科技】登录验证码：{" + SmsVerifyUtil.runNumber() + "}，如非本人操作，请忽略此短信。";


    /**
     * 验证码通知短信
     */
    public static void execute() {
        String tmpSmsContent = null;
        try {
            tmpSmsContent = URLEncoder.encode(smsContent, "UTF-8");
        } catch (Exception e) {
            log.error("exception:", e);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("accountSid").append("=").append(SmsVerifyConfig.ACCOUNT_SID);
        sb.append("&to").append("=").append(to);
        sb.append("&smsContent").append("=").append(tmpSmsContent);

        String body = sb.toString().concat(SmsVerifyHttpUtil.createCommonParam());

        // 提交请求
        String result = SmsVerifyHttpUtil.post(SmsVerifyConfig.BASE_URL, body);
        log.info("result:{}", result);
    }

    public static void main(String[] args) {
        execute();
    }
}
