package com.jt.common;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RsaUtils {
	
	/*
	 * 生成RSA密钥对
	 */
	public static Map<String, Object> createKeyPair(int keySize) {
        KeyPairGenerator keyGen = null;
        try {
            keyGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            log.error("初始化密钥工具异常", e);
            return null;
        }
        keyGen.initialize(keySize, new SecureRandom());
        KeyPair key = keyGen.generateKeyPair();
        PublicKey publicKey = key.getPublic();
        PrivateKey privateKey = key.getPrivate();
        Map<String,Object> map = new HashMap<>();
        map.put("publicKey", publicKey);
        map.put("privateKey", privateKey);
        map.put("publicKeyBase64", Base64.encodeBase64String(publicKey.getEncoded()));
        map.put("privateKeyBase64", Base64.encodeBase64String(privateKey.getEncoded()));
        return map;
    }
	
	/*
	 *	获取公钥的Base64字符串
	 */
	public static String getBase64PublicKeyString(PublicKey publicKey) {
        return Base64.encodeBase64URLSafeString(publicKey.getEncoded()).trim();
    }
	
	/*
	 * 	获取私钥的Base64字符串
	 */
	public static String getBase64PrivateKeyString(PrivateKey privateKey) {
        return Base64.encodeBase64URLSafeString(privateKey.getEncoded()).trim();
    }

	/*
	 * 	获取公钥
	 */
	public static PublicKey getPublicKey(String publicKeyBase64) throws Exception {
        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyBase64));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(pubKeySpec);
        return publicKey;
    }
	
	/*
	 * 	获取私钥
	 */
	public static PrivateKey getPrivateKey(String privateKeyBase64) throws Exception {
        PKCS8EncodedKeySpec priKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyBase64));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey priKey = keyFactory.generatePrivate(priKeySpec);
        return priKey;
    }

	/*
	 * 用私钥对数据进行签名 方法一
	 */
	public static byte[] sign(byte[] data, PrivateKey privateKey)
            throws Exception {
        Signature signature = Signature.getInstance("MD5WithRSA");
        signature.initSign(privateKey);
        signature.update(data);
        return signature.sign();
    }
	
	/*
	 * 用私钥对数据进行签名 方法二
	 */
	public static String sign(String data, PrivateKey privateKey)
            throws Exception {
        return Base64.encodeBase64URLSafeString(sign(data.getBytes(), privateKey)).trim();
    }
	
	/*
	 * 使用公钥校验签名 方法一
	 */
	public static boolean verify(byte[] data, byte[] sign, PublicKey publicKey)
            throws Exception {
        Signature signature = Signature.getInstance("MD5WithRSA");
        signature.initVerify(publicKey);
        signature.update(data);
        return signature.verify(sign);
    }
	
	/*
	 * 使用公钥校验签名 方法二
	 */
	public static boolean verify(String data, String sign, PublicKey publicKey)
            throws Exception {
        return verify(data.getBytes(), Base64.decodeBase64(sign), publicKey);
    }
	
	/*
	 * 获取参与签名的参数的字符串。参数拼接的顺序由 TreeMap 决定
	 */
	public static String getSourceSignData(TreeMap<String, String> paramsMap) {
        StringBuilder paramsBuilder = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> paramEntry : paramsMap.entrySet()) {
            if(!first){
                paramsBuilder.append("&");
            }else {
                first = false;
            }

            paramsBuilder.append(paramEntry.getKey()).append("=").append(paramEntry.getValue());
        }

        return paramsBuilder.toString();
    }

	/*
	 * 用私钥解密  前端通过JSEncrypt用公钥加密的数据
	 */
	public static String decryptBase64ToString(String encryptData, String privateKey) throws Exception {
		PrivateKey rk = getPrivateKey(privateKey);
		//通过Cipher对象解密
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, rk);
		byte[] res = cipher.doFinal(Base64.decodeBase64(encryptData));
		return new String(res);
	}

}
