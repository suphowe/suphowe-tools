package com.soft.token.utils;

import com.soft.auth.beans.SecretKey;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.thymeleaf.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * 非对称加密
 * 生成公钥和私钥
 * @author suphowe
 */
public class OpenSslUtil {

    private static final String FILE_URI = "C:/test";

    /**
     * 生成密钥文件
     * @param curid 加密文件名称
     * @param upCompany 生成文件夹
     * @return 密钥
     */
    public SecretKey doSecretKey(String curid, String upCompany){
        String outputPath = FILE_URI + "/" + upCompany + "/";
        File file = new File(outputPath);
        if(!file.exists()){
            file.mkdirs();
        }

        String osName = System.getProperty("os.name");
        String privateKey = outputPath + "/" + curid + ".pfx";
        String publicKey = outputPath + "/" + curid + ".cer";
        String pkcs8PrivateKey = outputPath + "/pkcs8" + curid + ".pfx";

        //获取OpenSSL安装路径
        String exePath = "";
        if(StringUtils.contains(osName, "Windows")){
            exePath = "D:/Program Files/OpenSSL-Win64/bin/openssl.exe";
        }else {
            //linux下openssl的安装路径
            exePath = "/usr/bin/openssl";
        }

        doProcess(exePath + " genrsa -out " + privateKey + " 1024");
        doProcess(exePath + " rsa -in " + privateKey + " -pubout -out " + publicKey);
        doProcess(exePath + " pkcs8 -topk8 -inform PEM -outform DER -in " + privateKey + " -out " + pkcs8PrivateKey + " -nocrypt");
        return getSecretKey(upCompany, publicKey, pkcs8PrivateKey);
    }

    /**
     * 获取生成的公钥
     * @param upCompany 文件夹
     * @param publicKeyPath 公钥地址
     * @param pkcs8PrivateKeyPath 私钥
     * @return 公钥
     */
    public SecretKey getSecretKey(String upCompany, String publicKeyPath, String pkcs8PrivateKeyPath){
        SecretKey secretKey = new SecretKey();
        try {
            FileUtils.readFileToString(new File(pkcs8PrivateKeyPath), "utf-8");
            String publicKeyString = FileUtils.readFileToString(new File(publicKeyPath), "utf-8");
            publicKeyString = StringUtils.replace(publicKeyString, "-----BEGIN PUBLIC KEY-----", "");
            publicKeyString = StringUtils.replace(publicKeyString, "-----END PUBLIC KEY-----", "");
            secretKey.setPublicKey(publicKeyString);
            byte[] b = FileUtils.readFileToByteArray(new File(pkcs8PrivateKeyPath));
            secretKey.setPrivateKey(Base64.encodeBase64String(b));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return secretKey;
    }

    /**
     * 终端执行openssl命令
     * @param command 命令
     * @return 执行结果
     */
    private boolean doProcess(String command) {
        BufferedReader br = null;
        try {
            Process p = Runtime.getRuntime().exec(command);
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";
            while((line = br.readLine()) != null){
                System.out.println(line);
            }
            p.waitFor();
            if(p.exitValue() == 0){
                System.out.println("程序运行正常");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
