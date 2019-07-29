package com.jt.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.RsaUtils;
import com.jt.pojo.User;

@Controller
public class UserController {

	private Map<String, Object> keyPair= RsaUtils.createKeyPair(1024);//单例对象
	
	@RequestMapping("/user/register")
	public String register(Map<String, Object> paramMap) {
		//获取密钥对
		String pk = keyPair.get("publicKeyBase64").toString();
		paramMap.put("pk", pk);
		return "register";
	}
	
	@RequestMapping("/user/submit")
	@ResponseBody
	public String submit(User user) {
		String pwd = user.getPwd();
		System.out.println("加密的pwd:"+pwd);
		try {
			String password = RsaUtils.decryptBase64ToString(pwd,keyPair.get("privateKeyBase64").toString());
			System.out.println("解密的password:"+password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "成功注册";
	}

	
}
