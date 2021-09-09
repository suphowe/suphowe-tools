package com.soft.method.http;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * HTTP/HTTPS
 *
 * @author suphowe
 */
public class HttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * HTTP/HTTPS 请求发送
     *
     * @param url         请求url
     * @param sendData    需要发送数据
     * @param urlencode   数据编码格式
     * @param connTimeOut 链接超时时间
     * @param readTimeOut 读取超时时间
     * @param contentType 请求头部  固定输入"application/x-www-form-urlencoded;charset="+urlencode
     * @param header      输入null
     * @return 服务器返回字符串
     * @author mryin
     */
    public String sendAndRcvHttpPostBase(String url, String sendData, String urlencode, int connTimeOut,
                                         int readTimeOut, String contentType, Map<String, String> header) {
        String result = "";
        BufferedReader in = null;
        DataOutputStream out = null;
        int code = 999;
        HttpsURLConnection httpsConn = null;
        HttpURLConnection httpConn = null;
        try {
            URL connUrl = new URL(url);
            if (url.startsWith("https://")) {
                httpsConn = (HttpsURLConnection) connUrl.openConnection();
                TrustManager[] trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            @Override
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return null;
                            }

                            @Override
                            public void checkClientTrusted(
                                    java.security.cert.X509Certificate[] certs, String authType) {
                            }

                            @Override
                            public void checkServerTrusted(
                                    java.security.cert.X509Certificate[] certs, String authType) {
                            }
                        }
                };
                SSLContext sc = SSLContext.getInstance("TLS");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                httpsConn.setSSLSocketFactory(sc.getSocketFactory());
                HostnameVerifier hv = new HostnameVerifier() {
                    @Override
                    public boolean verify(String urlHostName, SSLSession session) {
                        return true;
                    }
                };
                httpsConn.setHostnameVerifier(hv);
                httpsConn.setRequestProperty("Accept-Charset", urlencode);
                httpsConn.setRequestProperty("User-Agent", "java HttpsURLConnection");
                if (header != null) {
                    for (String key : header.keySet()) {
                        httpsConn.setRequestProperty(key, (String) header.get(key));
                    }
                }
                httpsConn.setRequestMethod("POST");
                httpsConn.setUseCaches(false);
                httpsConn.setRequestProperty("Content-Type", contentType);
                httpsConn.setConnectTimeout(connTimeOut);
                httpsConn.setReadTimeout(readTimeOut);
                httpsConn.setDoInput(true);
                httpsConn.setInstanceFollowRedirects(true);
                if (sendData != null) {
                    httpsConn.setDoOutput(true);
                    // 获取URLConnection对象对应的输出流
                    out = new DataOutputStream(httpsConn.getOutputStream());
                    // 发送请求参数
                    out.write(sendData.getBytes(urlencode));
                    // flush输出流的缓冲
                    out.flush();
                    out.close();
                }
                // 取得该连接的输入流，以读取响应内容
                in = new BufferedReader(new InputStreamReader(httpsConn.getInputStream(), urlencode));
                code = httpsConn.getResponseCode();
            } else {
                httpConn = (HttpURLConnection) connUrl.openConnection();
                httpConn.setRequestProperty("Accept-Charset", urlencode);
                httpConn.setRequestProperty("user-agent", "java HttpURLConnection");
                if (header != null) {
                    for (String key : header.keySet()) {
                        httpConn.setRequestProperty(key, (String) header.get(key));
                    }
                }
                httpConn.setRequestMethod("POST");
                httpConn.setUseCaches(false);
                httpConn.setRequestProperty("Content-Type", contentType);
                httpConn.setConnectTimeout(connTimeOut);
                httpConn.setReadTimeout(readTimeOut);
                httpConn.setDoInput(true);
                httpConn.setInstanceFollowRedirects(true);
                if (sendData != null) {
                    httpConn.setDoOutput(true);
                    // 获取URLConnection对象对应的输出流
                    out = new DataOutputStream(httpConn.getOutputStream());
                    // 发送请求参数
                    out.write(sendData.getBytes(urlencode));
                    // flush输出流的缓冲
                    out.flush();
                    out.close();
                }
                // 取得该连接的输入流，以读取响应内容
                in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), urlencode));
                code = httpConn.getResponseCode();
            }
            if (HttpURLConnection.HTTP_OK == code) {
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
            } else {
                result = "";
                throw new Exception("发生异常：" + code);
            }
        } catch (IOException e) {
            logger.error("[HTTP/HTTPS 请求发送]失败:", e);
            result = "";
        } catch (Exception e) {
            logger.error("[HTTP/HTTPS 请求发送]失败:", e);
            result = "";
        } finally {
            logger.error("[HTTP/HTTPS 请求发送]请求地址:{}", url);
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    logger.error("[HTTP/HTTPS 关闭请求]失败:", e);
                }
            }
            if (httpConn != null) {
                httpConn.disconnect();
            }
            if (httpsConn != null) {
                httpsConn.disconnect();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("[HTTP/HTTPS 关闭请求]失败:", e);
                }
            }
        }
        return result;
    }

    /**
     * GET方式获取信息
     *
     * @param httpurl 请求URL
     * @return
     */
    public String doGet(String httpurl) {
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        // 返回结果字符串
        String result = null;
        try {
            // 创建远程url连接对象
            URL url = new URL(httpurl);
            // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接方式：get
            connection.setRequestMethod("GET");
            // 设置连接主机服务器的超时时间：15000毫秒
            connection.setConnectTimeout(15000);
            // 设置读取远程返回的数据时间：60000毫秒
            connection.setReadTimeout(60000);
            // 发送请求
            connection.connect();
            // 通过connection连接，获取输入流
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                // 封装输入流is，并指定字符集
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                // 存放数据
                StringBuffer sbf = new StringBuffer();
                String temp = null;
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
            }
        } catch (MalformedURLException e) {
            logger.error("[数据发送doGet]失败:", e);
        } catch (IOException e) {
            logger.error("[数据发送doGet]失败:", e);
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    logger.error("[数据发送doPost]失败:", e);
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error("[数据发送doPost]失败:", e);
                }
            }
            connection.disconnect();// 关闭远程连接
        }
        return result;
    }

    /**
     * POST发送json消息
     *
     * @param uri  请求URL
     * @param json json数据
     * @return 返回结果
     */
    public String doPost(String uri, String json) {
        byte[] postMsg = json.getBytes();
        if (postMsg.length < 1) {
            return "";
        }
        HttpURLConnection connection = null;
        InputStream is = null;
        OutputStream os = null;
        BufferedReader br = null;
        String result = null;
        try {
            URL url = new URL(uri);
            // 通过远程url连接对象打开连接
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接请求方式
            connection.setRequestMethod("POST");
            // 设置连接主机服务器超时时间：15000毫秒
            connection.setConnectTimeout(5000);
            // 设置读取主机服务器返回数据超时时间：60000毫秒
            connection.setReadTimeout(10000);

            // 默认值为：false，当向远程服务器传送数据/写数据时，需要设置为true
            connection.setDoOutput(true);
            // 默认值为：true，当前向远程服务读取数据时，设置为true，该参数可有可无
            connection.setDoInput(true);
            // 设置传入参数的格式:请求参数应该是 name1=value1&name2=value2 的形式。
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
//            connection.setRequestProperty("Host", host);
            // 设置鉴权信息：Authorization: Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0
            connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
            connection.setRequestProperty("Accept-Language", "zh-CN,en");
//            connection.setRequestProperty("Content-Length", param.length);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestProperty("Connection", "Keep-Alive");
            // 通过连接对象获取一个输出流
            os = connection.getOutputStream();
            // 通过输出流对象将参数写出去/传输出去,它是通过字节数组写出的
            os.write(postMsg);
            // 通过连接对象获取一个输入流，向远程读取
            if (connection.getResponseCode() == 200) {

                is = connection.getInputStream();
                // 对输入流对象进行包装:charset根据工作项目组的要求来设置
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                StringBuffer sbf = new StringBuffer();
                String temp = null;
                // 循环遍历一行一行读取数据
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
            }
        } catch (MalformedURLException e) {
            logger.error("[数据发送doPost]失败:", e);
        } catch (IOException e) {
            logger.error("[数据发送doPost]失败:", e);
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    logger.error("[数据发送doPost]失败:", e);
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    logger.error("[数据发送doPost]失败:", e);
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error("[数据发送doPost]失败:", e);
                }
            }
            // 断开与远程地址url的连接
            connection.disconnect();
        }
        return result;
    }

    /**
     * POST发送Json消息
     *
     * @param uri  请求uri
     * @param json json消息
     * @return
     */
    public String post(String uri, String json) {
        BufferedReader reader = null;
        try {
            // 创建连接
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            // 设置请求方式
            connection.setRequestMethod("POST");
            // 设置接收数据的格式
            // connection.setRequestProperty("Accept", "application/json");
            // 设置发送数据的格式
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();
            //一定要用BufferedReader 来接收响应， 使用字节来接收响应的方法是接收不到内容的
            // utf-8编码
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            out.append(json);
            out.flush();
            out.close();
            // 读取响应
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            String res = "";
            while ((line = reader.readLine()) != null) {
                res += line;
            }
            reader.close();
            return res;
            //如果一定要使用如下方式接收响应数据， 则响应必须为: response.getWriter().print(StringUtils.join("{\"errCode\":\"1\",\"errMsg\":\"", message, "\"}")); 来返回
            /*
             // 获取长度
             int length = (int) connection.getContentLength();
             if (length != -1) {
             byte[] data = new byte[length];
             byte[] temp = new byte[512];
             int readLen = 0;
             int destPos = 0;
             while ((readLen = is.read(temp)) > 0) {
             System.arraycopy(temp, 0, data, destPos, readLen);
             destPos += readLen;
             }
             String result = new String(data, "UTF-8"); // utf-8编码
             System.out.println(result);
             return result;
             }
             return res;
             */
        } catch (IOException e) {
            logger.error("[数据发送post]失败:", e);
        }
        // 自定义错误信息
        return "error";
    }

    public String sendJsonObject(String url, Object jsonObject, String encoding) throws ParseException, IOException {
        String body = "";

        //创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        //创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);

        //装填参数
        StringEntity s = new StringEntity(jsonObject.toString(), "utf-8");
        s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
                "application/json"));
        //设置参数到请求对象中
        httpPost.setEntity(s);
        System.out.println("请求地址：" + url);
//        System.out.println("请求参数："+nvps.toString());

        //设置header信息
        //指定报文头【Content-type】、【User-Agent】
//        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpPost);
        //获取结果实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity, encoding);
        }
        EntityUtils.consume(entity);
        //释放链接
        response.close();
        return body;
    }

    /**
     * Post发送form表单数据
     */
    public String postForm(String uri, List<BasicNameValuePair> parames) {
        String result = "";
        try {
            CloseableHttpClient client = null;
            CloseableHttpResponse response = null;
            try {
                HttpPost httpPost = new HttpPost(uri);
                httpPost.setEntity(new UrlEncodedFormEntity(parames, "UTF-8"));

                client = HttpClients.createDefault();
                response = client.execute(httpPost);
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity);
            } catch (Exception e) {
                logger.error("[数据发送postForm]失败:", e);
            } finally {
                if (response != null) {
                    response.close();
                }
                if (client != null) {
                    client.close();
                }
            }
        } catch (Exception e) {
            logger.error("[数据发送postForm]失败:", e);
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        LinkedHashMap<String,Object> map = new LinkedHashMap<>(8);
        map.put("ID", 1);
        map.put("IP", "192.168.200.75");
        map.put("event", "PTZ");
        map.put("user", "onvif");
        map.put("passwd", "tongli654321");

        LinkedHashMap<String,Object> param = new LinkedHashMap<>(8);
//        param.put("goto", "2");
//        param.put("profileToken", "Profile_1");
        param.put("goto", "move_relative");
        param.put("pan_value", "0.0");
        param.put("tilt_value", "0.4");
        param.put("zoom_value", "0.0");
        param.put("profileToken", "Profile_1");
        map.put("parameters", param);
        String jsonMsg = new Gson().toJson(map);
        System.out.println("#############################################################");

        System.out.println(jsonMsg);
        System.out.println("#############################################################");

        HttpUtil hp = new HttpUtil();
        String ret = hp.doPost("http://192.168.200.112:8070", jsonMsg);
        System.out.println(ret);
        /*
		LinkedHashMap<String,Object> upload_map = new LinkedHashMap<String,Object>();
		upload_map.put("event", "status");

		List<HashMap<String,Object>> parameters_list = new ArrayList<HashMap<String,Object>>();
		for(int i=0;i<18;i++) {
			LinkedHashMap<String,Object> msg_map = new LinkedHashMap<String,Object>();
			msg_map.put("ID", i+1);
			msg_map.put("IP", "192.168.200.64");
			msg_map.put("deviceType", "IPC");
			msg_map.put("deviceID", "DS-2DE5230W-A20180704CCCHC33419213W");
			msg_map.put("passwd", "tongli654321");
			msg_map.put("user", "onvif");
			msg_map.put("profileTokens", "");

			parameters_list.add(msg_map);

		}
		upload_map.put("parameters", parameters_list);
		String msg = new Gson().toJson(upload_map);

		String msg = "{\"event\":\"status\",\"parameters\":[{\"ID\":1,\"IP\":\"192.168.200.1\",\"deviceType\":\"IPC\",\"deviceID\":\"1-2DE5230W-A20180704CCCHC33419213W\",\"passwd\":\"tongli654321\",\"user\":\"onvif\",\"profileTokens\":\"\"},{\"ID\":2,\"IP\":\"192.168.200.85\",\"deviceType\":\"NVR\",\"deviceID\":\"1620180709CCRRC34263369WCVU\",\"passwd\":\"tongli654321\",\"user\":\"nvr\",\"profileTokens\":[\"ProfileToken033\",\"ProfileToken002\"]},{\"ID\":3,\"IP\":\"192.168.200.2\",\"deviceType\":\"IPC\",\"deviceID\":\"2-2DE5230W-A20180704CCCHC33419213W\",\"passwd\":\"tongli654321\",\"user\":\"onvif\",\"profileTokens\":\"\"},{\"ID\":4,\"IP\":\"192.168.200.8\",\"deviceType\":\"IPC\",\"deviceID\":\"8-2DE5230W-A20180704CCCHC33419213W\",\"passwd\":\"tongli654321\",\"user\":\"onvif\",\"profileTokens\":\"\"},{\"ID\":5,\"IP\":\"192.168.200.75\",\"deviceType\":\"IPC\",\"deviceID\":\"DS-2DE5230W-A20180704CCCHC33419210W\",\"passwd\":\"tongli654321\",\"user\":\"onvif\",\"profileTokens\":\"\"},{\"ID\":6,\"IP\":\"192.168.200.64\",\"deviceType\":\"IPC\",\"deviceID\":\"DS-2DE5230W-A20180704CCCHC33419213W\",\"passwd\":\"tongli654321\",\"user\":\"onvif\",\"profileTokens\":\"\"}]}";
		String msg = "{\"event\":\"status\",\"parameters\":[{\"ID\":1,\"IP\":\"192.168.200.1\",\"deviceType\":\"IPC\",\"deviceID\":\"1-2DE5230W-A20180704CCCHC33419213W\",\"passwd\":\"tongli654321\",\"user\":\"onvif\",\"profileTokens\":\"\"},{\"ID\":2,\"IP\":\"192.168.200.85\",\"deviceType\":\"NVR\",\"deviceID\":\"1620180709CCRRC34263369WCVU\",\"passwd\":\"tongli654321\",\"user\":\"nvr\",\"profileTokens\":[\"ProfileToken033\",\"ProfileToken002\"]},{\"ID\":3,\"IP\":\"192.168.200.2\",\"deviceType\":\"IPC\",\"deviceID\":\"2-2DE5230W-A20180704CCCHC33419213W\",\"passwd\":\"tongli654321\",\"user\":\"onvif\",\"profileTokens\":\"\"},{\"ID\":4,\"IP\":\"192.168.200.8\",\"deviceType\":\"IPC\",\"deviceID\":\"8-2DE5230W-A20180704CCCHC33419213W\",\"passwd\":\"tongli654321\",\"user\":\"onvif\",\"profileTokens\":\"\"},{\"ID\":5,\"IP\":\"192.168.200.75\",\"deviceType\":\"IPC\",\"deviceID\":\"DS-2DE5230W-A20180704CCCHC33419210W\",\"passwd\":\"tongli654321\",\"user\":\"onvif\",\"profileTokens\":\"\"},{\"ID\":6,\"IP\":\"192.168.200.64\",\"deviceType\":\"IPC\",\"deviceID\":\"DS-2DE5230W-A20180704CCCHC33419213W\",\"passwd\":\"tongli654321\",\"user\":\"onvif\",\"profileTokens\":\"\"},{\"ID\":7,\"IP\":\"192.168.200.1\",\"deviceType\":\"IPC\",\"deviceID\":\"1-2DE5230W-A20180704CCCHC33419213W\",\"passwd\":\"tongli654321\",\"user\":\"onvif\",\"profileTokens\":\"\"},{\"ID\":8,\"IP\":\"192.168.200.85\",\"deviceType\":\"NVR\",\"deviceID\":\"1620180709CCRRC34263369WCVU\",\"passwd\":\"tongli654321\",\"user\":\"nvr\",\"profileTokens\":[\"ProfileToken033\",\"ProfileToken002\"]},{\"ID\":9,\"IP\":\"192.168.200.2\",\"deviceType\":\"IPC\",\"deviceID\":\"2-2DE5230W-A20180704CCCHC33419213W\",\"passwd\":\"tongli654321\",\"user\":\"onvif\",\"profileTokens\":\"\"},{\"ID\":10,\"IP\":\"192.168.200.8\",\"deviceType\":\"IPC\",\"deviceID\":\"8-2DE5230W-A20180704CCCHC33419213W\",\"passwd\":\"tongli654321\",\"user\":\"onvif\",\"profileTokens\":\"\"},{\"ID\":11,\"IP\":\"192.168.200.75\",\"deviceType\":\"IPC\",\"deviceID\":\"DS-2DE5230W-A20180704CCCHC33419210W\",\"passwd\":\"tongli654321\",\"user\":\"onvif\",\"profileTokens\":\"\"},{\"ID\":12,\"IP\":\"192.168.200.64\",\"deviceType\":\"IPC\",\"deviceID\":\"DS-2DE5230W-A20180704CCCHC33419213W\",\"passwd\":\"tongli654321\",\"user\":\"onvif\",\"profileTokens\":\"\"},{\"ID\":13,\"IP\":\"192.168.200.1\",\"deviceType\":\"IPC\",\"deviceID\":\"1-2DE5230W-A20180704CCCHC33419213W\",\"passwd\":\"tongli654321\",\"user\":\"onvif\",\"profileTokens\":\"\"},{\"ID\":14,\"IP\":\"192.168.200.85\",\"deviceType\":\"NVR\",\"deviceID\":\"1620180709CCRRC34263369WCVU\",\"passwd\":\"tongli654321\",\"user\":\"nvr\",\"profileTokens\":[\"ProfileToken033\",\"ProfileToken002\"]},{\"ID\":15,\"IP\":\"192.168.200.2\",\"deviceType\":\"IPC\",\"deviceID\":\"2-2DE5230W-A20180704CCCHC33419213W\",\"passwd\":\"tongli654321\",\"user\":\"onvif\",\"profileTokens\":\"\"},{\"ID\":16,\"IP\":\"192.168.200.8\",\"deviceType\":\"IPC\",\"deviceID\":\"8-2DE5230W-A20180704CCCHC33419213W\",\"passwd\":\"tongli654321\",\"user\":\"onvif\",\"profileTokens\":\"\"},{\"ID\":17,\"IP\":\"192.168.200.75\",\"deviceType\":\"IPC\",\"deviceID\":\"DS-2DE5230W-A20180704CCCHC33419210W\",\"passwd\":\"tongli654321\",\"user\":\"onvif\",\"profileTokens\":\"\"},{\"ID\":18,\"IP\":\"192.168.200.64\",\"deviceType\":\"IPC\",\"deviceID\":\"DS-2DE5230W-A20180704CCCHC33419213W\",\"passwd\":\"tongli654321\",\"user\":\"onvif\",\"profileTokens\":\"\"}]}";
		System.out.println(msg);
		System.out.println(msg.length());

		String url = "http://192.168.201.110:8070";
		String host = "192.168.201.110";

        String url = "http://192.168.200.112:8090/ewserver/get_event_list";
        String url = "http://127.0.0.1:8081/ewserver/putStreamResult?detectionData=";
        String url = "http://192.168.200.112:8080/ewserver/login";


		String json = "page=1&length=10";
        String json = "{\"data\":{\"IP\":\"192.168.200.75\",\"stream_status\":true,\"time_stamp\":\"Tue Jun  2 15:15:23 2020\\n\",\"token\":\"Profile_1\"}}";
        HttpUtil httpUtil = new HttpUtil();
        String ret = "";
		while(true) {
			Thread.sleep(10000);
			ret = hpu.post(url, json);
        ret = hpu.postForm(url, json);
        System.out.println(ret);
        JSONObject statusJson = JSONUtil.parseObj(ret);
        JSONObject status = JSONUtil.parseObj(statusJson.get("status"));
        String code = status.get("code").toString();
        String desc = (String) status.get("desc");
        System.out.println(code);
        System.out.println(desc);

        String result = httpUtil.postForm(url, HttpUtil.getFormData());
        System.out.println("#############################################################");
        System.out.println(result);


		}
		*/
    }

    /**
     * 创建Form表单
     *
     * @return 组装报文
     */
    public static List<BasicNameValuePair> getFormData() {
        // 创建一个提交数据的容器
        List<BasicNameValuePair> parames = new ArrayList<>();
        parames.add(new BasicNameValuePair("userid", "frontal"));
        parames.add(new BasicNameValuePair("password", "111111"));
        return parames;
    }

}
