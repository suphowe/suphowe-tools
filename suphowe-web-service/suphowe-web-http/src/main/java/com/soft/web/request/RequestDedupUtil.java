package com.soft.web.request;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

/**
 * 请求去重
 * @author suphowe
 */
public class RequestDedupUtil {

    /**
     * 请求数据(MD5加密)
     * @param requestJsonStr 请求的参数，这里通常是Json String
     * @param excludeKeys 请求参数里面要去除哪些字段再求摘要
     * @return 去除参数的MD5摘要
     */
    public static String dedupParam(String requestJsonStr, String... excludeKeys) {
        TreeMap<String, Object> paramTreeMap = JSON.parseObject(requestJsonStr, TreeMap.class);
        // 去除摘要字段
        if (excludeKeys != null) {
            List<String> dedupExcludeKeys = Arrays.asList(excludeKeys);
            if (!dedupExcludeKeys.isEmpty()) {
                for (String dedupExcludeKey : dedupExcludeKeys) {
                    paramTreeMap.remove(dedupExcludeKey);
                }
            }
        }
        String paramTreeMapJson = JSON.toJSONString(paramTreeMap);
        return jdkMd5(paramTreeMapJson);
    }

    /**
     * 数据MD5加密
     * @param data 数据
     * @return 加密数据
     */
    private static String jdkMd5(String data) {
        if (StrUtil.hasBlank(data)) {
            return "";
        }
        String res = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] mdBytes = messageDigest.digest(data.getBytes());
            res = DatatypeConverter.printHexBinary(mdBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
