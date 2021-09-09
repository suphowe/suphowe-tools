package com.soft.method.security;

/**
 * 认证算法
 *
 * @author suphowe
 */
public class CalcUtils {
    /**
     * response计算过程：
     * 1.  HA1=MD5(A1)=MD5(username:realm:password)
     *
     * 2. a. 如果 qop 值为“auth”或未指定 : HA2=MD5(A2)=MD5(method:uri)
     *    b. 如果 qop 值为“auth-int"     : HA2=MD5(A2)=MD5(method:uri:MD5(entity-body))
     *
     * 3. a.如果 qop 值为“auth”或“auth-int" : response=MD5(HA1:nonce:nc:cnonce:qop:HA2)
     *    b.如果 qop 未指定                 : response=MD5(HA1:nonce:HA2)
     *
     */


    /**
     * 通过账号密码和作用域计算HA1
     *
     * @param userName 用户名
     * @param realm 场所
     * @param passWord 密码
     * @return 加密后数据
     */
    public static String calcHA1(String userName, String realm, String passWord) {
        String str = userName + ":" + realm + ":" + passWord;
        return Md5Util.md5(str).toLowerCase();
    }

    /**
     * 通过请求方式计算HA2
     *
     * @param method <li>GET</li>
     *               <li>POST</li>
     * @param url    请求资源地址
     * @return 加密后数据
     */
    public static String calcHA2(String method, String url) {
        String str = method + ":" + url;
        return Md5Util.md5(str).toLowerCase();
    }

    /**
     * 计算最终的response值
     *
     * @param HA1
     * @param nonce
     * @param nc
     * @param cnonce
     * @param qop
     * @param HA2
     * @return
     */
    public static String calcResponse(String HA1, String nonce, String nc,
                                      String cnonce,
                                      String qop, String HA2) {
        String str = HA1 + ":" + nonce + ":" + nc + ":" + cnonce + ":" + qop + ":" + HA2;
        return Md5Util.md5(str).toLowerCase();
    }

    /**
     * 一次输入所有信息，计算response
     *
     * @param userName
     * @param realm
     * @param passWord
     * @param method
     * @param url
     * @param nonce
     * @param nc
     * @param cnonce
     * @param qop
     * @return
     */
    public static String calcResponse(String userName, String realm,
                                      String passWord, String method,
                                      String url, String nonce, String nc,
                                      String cnonce, String qop) {
        String HA1 = calcHA1(userName, realm, passWord);
        String HA2 = calcHA2(method, url);
        return calcResponse(HA1, nonce, nc, cnonce, qop, HA2).toLowerCase();
    }


}
