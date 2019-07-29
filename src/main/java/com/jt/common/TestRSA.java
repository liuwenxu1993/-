package com.jt.common;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.util.DigestUtils;

public class TestRSA {
	/*
	 * 设计流程：
     * 1、创建密钥对
     * 2、获取参与签名的数据
     * 3、获取参与签名的数据的摘要（MD5值）
     * 4、使用私钥对摘要进行数字签名
     * 5、使用公钥验证签名
	 */
	public static void main(String[] args) throws Exception{
		//1.获取密钥对
		Map<String, Object> keyPair = RsaUtils.createKeyPair(1024);
		String publicKeyBase64 = keyPair.get("publicKeyBase64").toString();
        String privateKeyBase64 = keyPair.get("privateKeyBase64").toString();

        PublicKey pk = (PublicKey) keyPair.get("publicKey");
        PrivateKey rk = (PrivateKey) keyPair.get("privateKey");
        
        System.out.println("publicKeyBase64:"+publicKeyBase64);
        System.out.println("privateKeyBase64:"+privateKeyBase64);
        
		//2.获取需要签名的数据
		TreeMap<String, String> map = new TreeMap<>();
		map.put("token", "token");
		map.put("name", "张三");
		map.put("age", "25");
		map.put("gender", "male");
		map.put("phone","18665379224");
		
		String signData = RsaUtils.getSourceSignData(map);
		
		//3.获取数据摘要(MD5) 利用Spring framework中的DigestUtils工具类
		String digest = DigestUtils.md5DigestAsHex(signData.getBytes());
		System.out.println("数据摘要digest为:"+digest);
		
		//4.使用私钥对摘要进行签名
		PrivateKey privateKey = RsaUtils.getPrivateKey(privateKeyBase64);
		String sign = RsaUtils.sign(digest, privateKey);
		System.out.println("签名sign1为:"+sign);
		
		String sign2 = RsaUtils.sign(digest, rk);
		System.out.println("签名sign2为:"+sign);
		
		//5.使用公钥对签名进行验证
		PublicKey publicKey = RsaUtils.getPublicKey(publicKeyBase64);
		boolean result = RsaUtils.verify(digest, sign, publicKey);
		System.out.println("验证的签名结果1为:"+result);
		
		boolean result2 = RsaUtils.verify(digest, sign2, pk);
		System.out.println("验证的签名结果2为:"+result2);
	}
}
