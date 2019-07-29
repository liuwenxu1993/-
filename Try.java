package com.yj;

import java.util.Map;

import org.jsoup.nodes.Document;

public class Try {
	public static void main(String[] args) {
		
		Map<String, String> cookies = WebLogin.login();
		cookies.put("TMOOC-SESSION", "C2173D49804D4F52BA5A317739ED4CB5");
		cookies.put("tedu.local.language", "zh-CN");
		Document doc =VideoUrls.getVideoUrl(cookies); 
		System.out.println(doc);
		
	}

}
