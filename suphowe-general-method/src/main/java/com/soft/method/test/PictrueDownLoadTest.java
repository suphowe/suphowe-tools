package com.soft.method.test;

import com.soft.method.http.HttpDownload;

/**
 * 图片下载测试
 * @author suphowe
 */
public class PictrueDownLoadTest {

    public static void main(String[] args) throws Exception {
        String url = "http://static.xkcoding.com/spring-boot-demo/ureport2/035330.png";
        String fileName = url.replace("http://static.xkcoding.com/spring-boot-demo/ureport2/", "");
        String savePath = "c:/test/";
        HttpDownload.downloadPicture(url, savePath, fileName);
    }
}
