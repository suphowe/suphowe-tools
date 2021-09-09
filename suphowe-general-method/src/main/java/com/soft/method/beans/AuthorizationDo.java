package com.soft.method.beans;


import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Authorization的Digest认证实体对象
 * @author suphowe
 */
@Component
public class AuthorizationDo implements Serializable {


    private static final long serialVersionUID = -646027413984796565L;
    /**
     * 域
     */
    private String realm;
    /**
     * 认证方式
     */
    private String qop;
    /**
     * 服务器生成的随机字符串
     */
    private String nonce;
    /**
     * 登录账号
     */
    private String userAccount;
    /**
     * 登录密码
     */
    private String userPassWord;
    /**
     * 客户端生成的随机码
     */
    private String cnonce;
    /**
     * 请求方式(枚举)
     * <li>GET</li>
     * <li>POST</li>
     */
    private String method;
    /**
     * 请求的资源地址
     */
    private String uri;
    /**
     * 请求次数,通常为:00000001
     */
    private String nc;
    /**
     * 加密方式
     */
    private String algorithm;

    /**
     * 计算出来的response值
     */
    private String response;

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getQop() {
        return qop;
    }

    public void setQop(String qop) {
        this.qop = qop;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPassWord() {
        return userPassWord;
    }

    public void setUserPassWord(String userPassWord) {
        this.userPassWord = userPassWord;
    }

    public String getCnonce() {
        return cnonce;
    }

    public void setCnonce(String cnonce) {
        this.cnonce = cnonce;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getNc() {
        return nc;
    }

    public void setNc(String nc) {
        this.nc = nc;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }


    @Override
    public String toString() {
        return "Digest " +
                "username=\"" + userAccount + "\", " +
                "realm=\"" + realm + "\", " +
                "nonce=\"" + nonce + "\", " +
                "uri=\"" + uri + "\", " +
                "algorithm=" + algorithm + ", " +
                "response=\"" + response + "\", " +
                "qop=" + qop + ", " +
                "nc=" + nc + ", " +
                "cnonce=\"" + cnonce + "\"";
    }
}
