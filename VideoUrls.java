package com.yj;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class VideoUrls {
	private static String url="http://uc.tmooc.cn/video/findVideoList";
	private static String courseId="75686FFBDA7A45CE859EE457870494D6";
	
	public static Document getVideoUrl(Map<String, String> cookies) {
		System.out.println(cookies);
		HashMap<String, String> data = new HashMap<String,String>();
		data.put("courseId", courseId);
		Connection con = Jsoup.connect(url).header("User-Agent", 
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36")
		.ignoreContentType(true).method(Method.POST)
		.cookies(cookies).data(data);
		try {
			Response res = con.execute();
			if(res.statusCode()==200) {
				Document doc = Jsoup.parse(res.body());
				return doc;
			}
			return null;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	

}
