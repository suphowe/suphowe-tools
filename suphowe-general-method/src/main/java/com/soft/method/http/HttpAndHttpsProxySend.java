package com.soft.method.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.security.cert.X509Certificate;

/**
 * 通过代理发送http/https 请求
 * @author suphowe
 */
@Component
public class HttpAndHttpsProxySend {

    private static final Logger logger = LoggerFactory.getLogger(HttpAndHttpsProxySend.class);

    /**
     * 通过代理发送http/https请求
     * @param url 请求url
     * @param param 请求参数
     * @param proxy 代理地址
     * @param port 代理端口
     * @return 返回请求结果
     */
    public static String proxySend(String url, String param, String proxy, int port) {
        if (url.startsWith("https://")) {
            return httpsSend(url, param, proxy, port);
        } else {
            return httpSend(url, param, proxy, port);
        }
    }

    /**
     * 通过代理发送https请求
     * @param url 请求url
     * @param param 请求参数
     * @param proxy 代理地址
     * @param port 代理端口
     * @return 返回请求结果
     */
    private static String httpsSend(String url, String param, String proxy, int port) {
        HttpsURLConnection httpsConn;
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        BufferedReader reader = null;
        try {
            URL urlClient = new URL(url);
            logger.info("request url：{}", urlClient);

            SSLContext sc = SSLContext.getInstance("SSL");
            // 指定信任https
            sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
            //创建代理虽然是https也是Type.HTTP
            Proxy proxy1 = new Proxy(Type.HTTP, new InetSocketAddress(proxy, port));
            //设置代理
            httpsConn = (HttpsURLConnection) urlClient.openConnection(proxy1);

            httpsConn.setSSLSocketFactory(sc.getSocketFactory());
            httpsConn.setHostnameVerifier(new TrustAnyHostnameVerifier());
            //设置通用的请求属性
            httpsConn.setRequestProperty("accept", "*/*");
            httpsConn.setRequestProperty("connection", "Keep-Alive");
            httpsConn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            httpsConn.setDoOutput(true);
            httpsConn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(httpsConn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(httpsConn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            // 断开连接
            httpsConn.disconnect();
            logger.info("request result:/n {}", result);
            logger.info("return result:/n {}", httpsConn.getResponseMessage());
        } catch (Exception e) {
            logger.error("url exception,url:{} /n {}", url, e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                logger.error("close exception,url:{} /n {}", url, e);
            }
        }
        return result.toString();
    }

    /**
     * 通过代理发送https请求
     * @param url 请求url
     * @param param 请求参数
     * @param proxy 代理地址
     * @param port 代理端口
     * @return 返回请求结果
     */
    private static String httpSend(String url, String param, String proxy, int port) {
        HttpURLConnection httpConn;
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        BufferedReader reader = null;
        try {
            URL urlClient = new URL(url);
            logger.info("request url：{}", urlClient);
            //创建代理
            Proxy proxy1 = new Proxy(Type.HTTP, new InetSocketAddress(proxy, port));
            //设置代理
            httpConn = (HttpURLConnection) urlClient.openConnection(proxy1);
            // 设置通用的请求属性
            httpConn.setRequestProperty("accept", "*/*");
            httpConn.setRequestProperty("connection", "Keep-Alive");
            httpConn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(httpConn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(httpConn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            // 断开连接
            httpConn.disconnect();
            logger.info("request result:/n {}", result);
            logger.info("return result:/n {}", httpConn.getResponseMessage());
        } catch (Exception e) {
            logger.error("url exception,url:{} /n {}", url, e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                logger.error("close exception,url:{} /n {}", url, e);
            }
        }
        return result.toString();
    }

    private static class TrustAnyTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}
