package com.soft.token.ecc;

import com.soft.token.constant.EccConstant;
import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import org.apache.commons.codec.binary.Base64;
import sun.security.ec.ECPrivateKeyImpl;
import sun.security.ec.ECPublicKeyImpl;

import javax.crypto.Cipher;
import javax.crypto.NullCipher;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.*;
import java.util.HashMap;

/**
 * ecc 算法
 * (实现有问题)
 * @author suphowe
 */
public class EccAlgorithm {

    public static void main(String[] args) throws Exception {
        byte[] privateKey = initPublicKey();
        byte[] publicKey = initPrivateKey();
        System.out.println("privateKey：" + privateKey);
        System.out.println("publicKey：" + publicKey);

        String info = sign("这是一个签名", privateKey);
        System.out.println("info:" + info);
//        String dec = HexBin.encode(decrypt(HexBin.decode(info), publicKey));
//        System.out.println("dec:" + dec);
//        System.out.println(sign("这是一个签名", initPrivateKey()));
    }

    /**
     * 公钥初始化
     */
    private static byte[] initPublicKey() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(EccConstant.ALGORITHM);
            keyPairGenerator.initialize(EccConstant.ECC_KEY_SIZE);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            ECPublicKey publicKey = (ECPublicKey) keyPair.getPublic();
            return publicKey.getEncoded();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 私钥初始化
     */
    private static byte[] initPrivateKey() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(EccConstant.ALGORITHM);
            keyPairGenerator.initialize(EccConstant.ECC_KEY_SIZE);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            ECPrivateKey privateKey = (ECPrivateKey) keyPair.getPrivate();
            return privateKey.getEncoded();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String sign(String data, byte[] privateKey) {
        try {
            //1.初始化密钥
//            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(EccConstant.ALGORITHM);
//            keyPairGenerator.initialize(EccConstant.ECC_KEY_SIZE);
//            KeyPair keyPair = keyPairGenerator.generateKeyPair();
//            ECPrivateKey ecPrivateKey = (ECPrivateKey)keyPair.getPrivate();

            //2.执行签名
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey);
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            PrivateKey signKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Signature signature = Signature.getInstance("SHA1withECDSA");
            signature.initSign(signKey);
            signature.update(data.getBytes());
            byte[] res = signature.sign();
            return HexBin.encode(res);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void jdkECDSA(String data){
        try {
            //1.初始化密钥
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(EccConstant.ALGORITHM);
            keyPairGenerator.initialize(EccConstant.ECC_KEY_SIZE);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            ECPublicKey ecPublicKey = (ECPublicKey)keyPair.getPublic();
            ECPrivateKey ecPrivateKey = (ECPrivateKey)keyPair.getPrivate();


            //2.执行签名
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(ecPrivateKey.getEncoded());

            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Signature signature = Signature.getInstance("SHA1withECDSA");
            signature.initSign(privateKey);
            signature.update(data.getBytes());
            byte[] res = signature.sign();
            System.out.println("签名："+ HexBin.encode(res));

            //3.验证签名
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(ecPublicKey.getEncoded());
            keyFactory = KeyFactory.getInstance("EC");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            signature = Signature.getInstance("SHA1withECDSA");
            signature.initVerify(publicKey);
            signature.update(data.getBytes());
            boolean bool = signature.verify(res);
            System.out.println("验证："+bool);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解密<br>
     * 用私钥解密
     */
    public static byte[] decrypt(byte[] data, String key) throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);

        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(EccConstant.ALGORITHM);

        ECPrivateKey priKey = (ECPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);

        ECPrivateKeySpec ecPrivateKeySpec = new ECPrivateKeySpec(priKey.getS(), priKey.getParams());

        // 对数据解密
        // TODO Chipher不支持EC算法 未能实现
        Cipher cipher = new NullCipher();
        // Cipher.getInstance(ALGORITHM, keyFactory.getProvider());
        cipher.init(Cipher.DECRYPT_MODE, priKey, ecPrivateKeySpec.getParams());

        return cipher.doFinal(data);
    }

    /**
     * 加密<br>
     * 用公钥加密
     */
    public static byte[] encrypt(byte[] data, String privateKey)throws Exception {
        // 对公钥解密
        byte[] keyBytes = decryptBASE64(privateKey);
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(EccConstant.ALGORITHM);

        ECPublicKey pubKey = (ECPublicKey) keyFactory.generatePublic(x509KeySpec);
        ECPublicKeySpec ecPublicKeySpec = new ECPublicKeySpec(pubKey.getW(), pubKey.getParams());

        // 对数据加密
        // TODO Chipher不支持EC算法 未能实现
        Cipher cipher = new NullCipher();
        // Cipher.getInstance(ALGORITHM, keyFactory.getProvider());
        cipher.init(Cipher.ENCRYPT_MODE, pubKey, ecPublicKeySpec.getParams());

        return cipher.doFinal(data);
    }

    /**
     * 取得私钥
     */
    public static String getPrivateKey(HashMap<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(EccConstant.ECC_PRIVATE_KEY);
        return encryptBASE64(key.getEncoded());
    }

    /**
     * 取得公钥
     */
    public static String getPublicKey(HashMap<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(EccConstant.ECC_PUBLIC_KEY);
        return encryptBASE64(key.getEncoded());
    }

    /**
     * 初始化密钥
     */
    public static HashMap<String, Object> initKey() throws Exception {
        BigInteger x = new BigInteger("2fe13c0537bbc11acaa07d793de4e6d5e5c94eee8", 16);
        BigInteger y = new BigInteger("289070fb05d38ff58321f2e800536d538ccdaa3d9", 16);
        ECPoint g = new ECPoint(x, y);

        // the order of generator
        BigInteger n = new BigInteger("5846006549323611672814741753598448348329118574063", 10);
        // the cofactor
        int h = 2;
        int m = 163;
        int[] ks = { 7, 6, 3 };
        ECFieldF2m ecField = new ECFieldF2m(m, ks);
        // y^2+xy=x^3+x^2+1
        BigInteger a = new BigInteger("1", 2);
        BigInteger b = new BigInteger("1", 2);

        EllipticCurve ellipticCurve = new EllipticCurve(ecField, a, b);
        ECParameterSpec ecParameterSpec = new ECParameterSpec(ellipticCurve, g, n, h);
        // 公钥
        ECPublicKey publicKey = new ECPublicKeyImpl(g, ecParameterSpec);
        BigInteger s = new BigInteger("1234006549323611672814741753598448348329118574063", 10);
        // 私钥
        ECPrivateKey privateKey = new ECPrivateKeyImpl(s, ecParameterSpec);
        HashMap<String, Object> keyMap = new HashMap<>(4);
        keyMap.put(EccConstant.ECC_PUBLIC_KEY, publicKey);
        keyMap.put(EccConstant.ECC_PRIVATE_KEY, privateKey);
        return keyMap;
    }

    public static byte[] decryptBASE64(String data) {
        return Base64.decodeBase64(data);
    }

    public static String encryptBASE64(byte[] data) {
        return new String(Base64.encodeBase64(data));
    }

}
