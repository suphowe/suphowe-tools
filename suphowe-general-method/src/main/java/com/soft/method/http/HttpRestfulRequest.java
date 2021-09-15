package com.soft.method.http;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

/**
 * HTTP Restful 请求
 * @author suphowe
 */
@Component
public class HttpRestfulRequest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRestfulRequest.class);

    /**
     * 文件上传（非JSON）
     *
     * @param url             上传url
     * @param requestParams   上传内容
     * @param filePathAndName 本地文件地址
     * @return 返回信息
     */
    public String requestUploadFile(String url, HashMap<String, Object> requestParams, String filePathAndName) {
        String result = null;
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            /* HttpPost */
            HttpPost httpPost = new HttpPost(url);
            // 建立多文件实例
            MultipartEntityBuilder multipartEntity = MultipartEntityBuilder.create();
            multipartEntity.setCharset(StandardCharsets.UTF_8);
            multipartEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            File postFile = new File(filePathAndName);
            FileBody filebody = new FileBody(new File(filePathAndName));
            // upload为请求后台的File upload;
            multipartEntity.addPart(postFile.getName(), filebody);
            for (String key : requestParams.keySet()) {
                String value = (String) requestParams.get(key);
                multipartEntity.addPart(key, new StringBody(value, ContentType.create("text/plain", StandardCharsets.UTF_8)));
            }
            // 设置实体
            HttpEntity reqEntity =  multipartEntity.build();
            httpPost.setEntity(reqEntity);
            /* HttpResponse */
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            try {
                HttpEntity httpEntity = httpResponse.getEntity();
                result = EntityUtils.toString(httpEntity, "utf-8");
                EntityUtils.consume(httpEntity);
            } finally {
                try {
                    if (httpResponse != null) {
                        httpResponse.close();
                    }
                } catch (IOException e) {
                    logger.info("####### error ####### url:{} ,requestParams:{} ,filePathAndName:{}", url, requestParams, filePathAndName);
                    logger.error("## release resouce error IOException ##", e);
                }
            }
        } catch (Exception e) {
            logger.info("####### error ####### url:{} ,requestParams:{} ,filePathAndName:{}", url, requestParams, filePathAndName);
            logger.error("## release resouce error ##", e);
        }
        return result;
    }

    /**
     * post的表单参数（非JSON）
     *
     * @param url           请求url
     * @param requestParams 请求参数
     * @return 返回信息
     */
    public String requestPostForm(String url, HashMap<String, Object> requestParams) {
        String result = null;
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            /* HttpPost */
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> params = new ArrayList<>();
            for (Entry<String, Object> en : requestParams.entrySet()) {
                String key = en.getKey();
                String value = (String) en.getValue();
                if (value != null) {
                    params.add(new BasicNameValuePair(key, value));
                }
            }
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            /* HttpResponse */
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            try {
                HttpEntity httpEntity = httpResponse.getEntity();
                result = EntityUtils.toString(httpEntity, "utf-8");
                EntityUtils.consume(httpEntity);
            } finally {
                try {
                    if (httpResponse != null) {
                        httpResponse.close();
                    }
                } catch (IOException e) {
                    logger.info("####### error ####### url:{} ,requestParams:{} ", url, requestParams);
                    logger.error("## release resouce error IOException ##", e);
                }
            }
        } catch (Exception e) {
            logger.info("####### error ####### url:{} ,requestParams:{} ", url, requestParams);
            logger.error("## release resouce error ##", e);
        }
        return result;
    }

    /**
     * POST请求（JSON）
     *
     * @param url      请求url
     * @param token    令牌
     * @param jsonBody 请求Json主体
     * @return 返回信息
     */
    public String requestPostJson(String url, String token, String jsonBody) {
        String result = "";
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Content-Type", "application/json");
            if (token != null && token.length() > 0) {
                httpPost.addHeader("token", token);
            }
            httpPost.setEntity(new StringEntity(jsonBody));
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
            response.close();
            httpClient.close();
        } catch (Exception e) {
            logger.info("####### error ####### url:{} ,token:{}, requestParams:{} ", url, token, jsonBody);
            logger.error("## release resouce error ##", e);
        }
        return result;
    }

    /**
     * GET 请求
     *
     * @param url   请求URL
     * @param token 令牌
     * @return 返回信息
     */
    public String requestGet(String url, String token) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Content-type", "application/json");
        httpGet.setHeader("DataEncoding", "UTF-8");
        if (token != null && token.length() > 0) {
            httpGet.setHeader("token", token);
        }
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000).setConnectionRequestTimeout(35000).setSocketTimeout(60000).build();
        httpGet.setConfig(requestConfig);

        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            return EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            logger.info("####### error ####### url:{} ,token:{}", url, token);
            logger.error("## release resouce error ClientProtocolException ##", e);
        } catch (IOException e) {
            logger.info("####### error ####### url:{} ,token:{}", url, token);
            logger.error("## release resouce error IOException ##", e);
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    logger.info("####### error ####### url:{} ,token:{}", url, token);
                    logger.error("## release resouce error IOException ##", e);
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    logger.info("####### error ####### url:{} ,token:{}", url, token);
                    logger.error("## release resouce error IOException ##", e);
                }
            }
        }
        return null;
    }

    /**
     * PUT 发送Json
     *
     * @param url      请求url
     * @param token    令牌
     * @param jsonBody json主体
     */
    public String requestPut(String url, String token, String jsonBody) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000).setConnectionRequestTimeout(35000).setSocketTimeout(60000).build();
        httpPut.setConfig(requestConfig);
        httpPut.setHeader("Content-type", "application/json");
        httpPut.setHeader("DataEncoding", "UTF-8");
        if (token != null && token.length() > 0) {
            httpPut.setHeader("token", token);
        }

        CloseableHttpResponse httpResponse = null;
        try {
            httpPut.setEntity(new StringEntity(jsonBody));
            httpResponse = httpClient.execute(httpPut);
            HttpEntity entity = httpResponse.getEntity();
            return EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            logger.info("####### error ####### url:{} ,token:{}, jsonBody:{}", url, token, jsonBody);
            logger.error("## release resouce error IOException ClientProtocolException ##", e);
        } catch (IOException e) {
            logger.info("####### error ####### url:{} ,token:{}, jsonBody:{}", url, token, jsonBody);
            logger.error("## release resouce error IOException IOException ##", e);
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    logger.info("####### error ####### url:{} ,token:{}, jsonBody:{}", url, token, jsonBody);
                    logger.error("## release resouce error IOException IOException ##", e);
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    logger.info("####### error ####### url:{} ,token:{}, jsonBody:{}", url, token, jsonBody);
                    logger.error("## release resouce error IOException IOException ##", e);
                }
            }
        }
        return null;
    }

    /**
     * DELETE 请求
     *
     * @param url   请求url
     * @param token 令牌
     */
    public String requestDelete(String url, String token) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000).setConnectionRequestTimeout(35000).setSocketTimeout(60000).build();
        httpDelete.setConfig(requestConfig);
        httpDelete.setHeader("Content-type", "application/json");
        httpDelete.setHeader("DataEncoding", "UTF-8");
        if (token != null && token.length() > 0) {
            httpDelete.setHeader("token", token);
        }

        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpDelete);
            HttpEntity entity = httpResponse.getEntity();
            return EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            logger.info("####### error ####### url:{} ,token:{}", url, token);
            logger.error("## release resouce error IOException ClientProtocolException ##", e);
        } catch (IOException e) {
            logger.info("####### error ####### url:{} ,token:{}", url, token);
            logger.error("## release resouce error IOException IOException ##", e);
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    logger.info("####### error ####### url:{} ,token:{}", url, token);
                    logger.error("## release resouce error IOException IOException ##", e);
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    logger.info("####### error ####### url:{} ,token:{}", url, token);
                    logger.error("## release resouce error IOException IOException ##", e);
                }
            }
        }
        return null;
    }

    /**
     * 获取数据
     * @param request 请求
     * @return 数据
     */
    public static String readAsChars(HttpServletRequest request) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            br = request.getReader();
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            br.close();
        } catch (IOException e) {
            logger.info("IOException:", e);
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    logger.info("IOException:", e);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 获取数据
     * @param request 请求
     * @return 数据
     */
    public static String readAsChars2(HttpServletRequest request) {
        InputStream is = null;
        StringBuilder sb = new StringBuilder();
        try {
            is = request.getInputStream();
            byte[] b = new byte[4096];
            for (int n; (n = is.read(b)) != -1; ) {
                sb.append(new String(b, 0, n));
            }
        } catch (IOException e) {
            logger.info("IOException:", e);
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.info("IOException:", e);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 二进制获取数据
     * @param request 请求
     * @return 数据
     */
    public static byte[] readAsBytes(HttpServletRequest request) {
        int len = request.getContentLength();
        byte[] buffer = new byte[len];
        ServletInputStream in = null;

        try {
            in = request.getInputStream();
            in.read(buffer, 0, len);
            in.close();
        } catch (IOException e) {
            logger.info("Exception:", e);
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.info("IOException:", e);
                }
            }
        }
        return buffer;
    }
}
