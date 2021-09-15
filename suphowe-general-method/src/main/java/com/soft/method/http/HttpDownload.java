package com.soft.method.http;

import com.soft.method.beans.AuthorizationDo;
import com.soft.method.constants.Constants;
import com.soft.method.security.CalcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


/**
 * http文件下载
 * @author suphowe
 */
public class HttpDownload {

    private static final Logger logger = LoggerFactory.getLogger(HttpDownload.class);

    @Autowired
    private final AuthorizationDo authorizationDo = new AuthorizationDo();

    /**
     * 链接url下载图片
     * @param urlList 下载链接
     */
    public static void downloadPicture(String urlList, String savePath, String saveName) {
        URL url = null;
        int imageNumber = 0;
        try {
            url = new URL(urlList);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());

            String imageName =  savePath + saveName;

            FileOutputStream fileOutputStream = new FileOutputStream(new File(imageName));
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int length;

            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            byte[] context=output.toByteArray();
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从网络Url中下载文件
     *
     * @param urlStr   请求url
     * @param fileName 文件名
     * @param savePath 文件保存文件夹名称
     * @param token    令牌
     */
    public static void downloadFromUrl(String urlStr, String fileName, String savePath, String token) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(3 * 1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            //如有安全令牌，添加安全令牌
            if (!token.isEmpty()) {
                conn.setRequestProperty("lfwywxqyh_token", token);
            }
            //得到输入流
            InputStream inputStream = conn.getInputStream();
            //获取自己数组
            byte[] getData = readInputStream(inputStream);

            //文件保存位置
            File saveDir = new File(savePath);
            if (!saveDir.exists()) {
                saveDir.mkdir();
            }
            File file = new File(saveDir + File.separator + fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(getData);
            if (fos != null) {
                fos.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            logger.info("Exception:", e);
        }
    }

    /**
     * 从网络Url中下载图片
     *
     * @param urlStr   请求url
     * @param fileName 文件名
     * @param savePath 文件保存文件夹名称
     * @param token    令牌
     * @param username 用户名
     * @param password 密码
     * @param width    图片宽度
     * @param height   图片高度
     */
    public boolean downLoadFromUrl(String headerAuthorization, String urlStr, String fileName, String savePath,
                                   String token, String username, String password, int width, int height) {
        boolean result = true;

        try {
            String login = username + ":" + password;
            login = login.replaceAll("@", "%40");

            urlStr = urlStr.replaceAll(username + ":" + password, login);
            logger.info("[图片下载URL]" + urlStr);
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(3 * 1000);
            //处理Authorization
            conn.setRequestProperty("Authorization", headerAuthorization);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            //如有安全令牌，添加安全令牌
//            if (token.length() > 0) {
//                conn.setRequestProperty("lfwywxqyh_token", token);
//            }

            int code = new Integer(conn.getResponseCode());
            String message = conn.getResponseMessage();
            logger.info("[图片下载URL]返回码" + code);
            logger.info("[图片下载URL]返回信息" + message);
            if (code == 401) {
                if (username.length() > 0 && password.length() > 0) {
                    List<String> headers = conn.getHeaderFields().get("WWW-Authenticate");
                    String header = null;
                    String newHeader = null;
                    for (String str : headers) {
                        if(str.indexOf("Basic") != -1){
                            String input = username + ":" + password;
                            String encoding = new sun.misc.BASE64Encoder().encode(input.getBytes());
                            newHeader = "Basic " + encoding;
                        }else if (str.indexOf("Digest") != -1) {
                            header = str;
                            newHeader = analysis(header, urlStr, username, password);
                        }
                        logger.info("[图片下载URL]Authorization:" + newHeader);
                        return downLoadFromUrl(newHeader, urlStr, fileName, savePath, token, username, password, width, height);
                    }

                } else {
                    return false;
                }
            }
            //得到输入流
            InputStream inputStream = conn.getInputStream();
            //获取自己数组
            byte[] getData = readInputStream(inputStream);

            //文件保存位置
            File saveDir = new File(savePath);
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }
            File file = new File(saveDir + File.separator + fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(getData);
            if (fos != null) {
                fos.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            logger.info("file download:[" + url + "] download success ,filename " + fileName);
            //修改图片大小
            resizeImage(saveDir + File.separator + fileName, saveDir + File.separator + fileName, width, height);
        } catch (Exception e) {
            result = false;
            logger.info("Exception:", e);
        }
        return result;
    }

    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream 输入流
     * @return 直接数组
     */
    private static byte[] readInputStream(InputStream inputStream) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (Exception e2) {
                logger.error("Exception:", e2);
            }
        }
        return bos.toByteArray();
    }

    /***
     * 	功能 :调整图片大小
     * @param srcImgPath 原图片路径
     * @param distImgPath  转换大小后图片路径
     * @param width   转换后图片宽度
     * @param height  转换后图片高度
     */
    private void resizeImage(String srcImgPath, String distImgPath, int width, int height) throws IOException {
        File srcFile = new File(srcImgPath);
        Image srcImg = ImageIO.read(srcFile);
        BufferedImage buffImg;
        buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        buffImg.getGraphics().drawImage(
                srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0,
                0, null);
        ImageIO.write(buffImg, "JPEG", new File(distImgPath));

    }

    /**
     * 拆分并解析原始Header
     *
     * @param originHeaderStr 原始header信息
     * @return header信息
     */
    private String analysis(String originHeaderStr, String url, String username, String password) {
        //自动设置uri

        this.authorizationDo.setUserAccount(username);
        this.authorizationDo.setUserPassWord(password);
        this.authorizationDo.setUri(url);

        originHeaderStr = originHeaderStr.substring(originHeaderStr.indexOf(" "));
        String[] items = originHeaderStr.replaceAll(" ", "")
                .replaceAll("\"", "").split(",");

        for (String str : items) {
            String type = str.substring(0, str.indexOf("="));
            String value = str.substring(str.indexOf("=") + 1);
            //完善实体
            switch (type) {
                case "qop":
                    this.authorizationDo.setQop(value);
                    break;
                case "nonce":
                    this.authorizationDo.setNonce(value);
                    break;
                case "realm":
                    this.authorizationDo.setRealm(value);
                    break;
                case "algorithm":
                    this.authorizationDo.setAlgorithm(value);
                    break;
                default:
                    break;
            }
        }
        //尝试写死cnonce值
        this.authorizationDo.setCnonce(Constants.CONST_CNONCE);
        //写死nc
        this.authorizationDo.setNc(Constants.CONST_NC);
        //计算并写入response
        this.authorizationDo.setResponse(CalcUtils
                .calcResponse(this.authorizationDo.getUserAccount(),
                        this.authorizationDo.getRealm(),
                        this.authorizationDo.getUserPassWord(),
                        Constants.CONST_METHOD_GET, this.authorizationDo.getUri(),
                        this.authorizationDo.getNonce(),
                        this.authorizationDo.getNc(),
                        this.authorizationDo.getCnonce(),
                        this.authorizationDo.getQop()));

        return this.authorizationDo.toString();
    }
}

