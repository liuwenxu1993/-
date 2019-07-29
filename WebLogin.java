package com.yj;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

public class WebLogin {
	private static String loginName="13264748717";
	private static String password="6fa16cc2a42459486b88f37c8b3e755d";
	private static String loginUrl="http://uc.tmooc.cn/login";
	
	public static Map<String, String> login(){
		Map<String, String> data = new HashMap<String,String>();
		data.put("loginName", loginName);
		data.put("password", password);
		data.put("accountType", "0");
		Connection con = Jsoup.connect(loginUrl);
		con.header("User-Agent", 
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36")
		.ignoreContentType(true).method(Method.POST)
		.cookie("TMOOC-SESSION", "C2173D49804D4F52BA5A317739ED4CB5")
		.data(data);
		Integer code;
		try {
			Response res = con.execute();
			code = res.statusCode();
			System.out.println(code);
			if(code!=200)throw new Exception("登录失败");
			System.out.println(res.body());
			return res.cookies();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		
	}
}
