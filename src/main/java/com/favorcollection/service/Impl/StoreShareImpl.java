package com.favorcollection.service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.favorcollection.pojo.ShareShow;
import com.favorcollection.pojo.User;
import com.favorcollection.service.StoreShare;
import com.favorcollection.util.HttpRequest;

import weibo4j.http.AccessToken;

@Service
public class StoreShareImpl implements StoreShare {
	private static final String MENTION = "https://api.weibo.com/2/statuses/mentions.json";
	
	@Autowired
	private WeiboTokenImpl weiboTokenImpl;
	@Override
	public Object storeAllShareFromWeibo() {
		AccessToken accesstoken = weiboTokenImpl.getAccessToken("c_c1227@163.com");
		Map<String,Object> params = new HashMap<>();
		params.put("access_token",accesstoken.getAccessToken());
		params.put("since_id",1474128000);
		params.put("max_id",0);
		params.put("count",50);
		params.put("page",1);
		params.put("filter_by_author",0);
		params.put("filter_by_source",0);
		String entityStr = HttpRequest.doGet(MENTION, params);
		JSONObject json = new JSONObject(entityStr);
		if((String)json.get("status") == "falied"){
			return entityStr;
		}
		JSONObject jo = json.getJSONObject("message");
		JSONArray ja = jo.getJSONArray("statuses");
		System.out.println(ja.length());
		return ja.toString();
		
//		List<ShareShow> shows = new ArrayList<>();
//		for(int i = 0; i < ja.length(); i++){
//			//TODO 分析每一个@的人是否在网站注册过了
//			/*
//			 * undone
//			 */
//			ShareShow ss = new ShareShow();
//			ShareShow retweet = new ShareShow(); 
//			User user = new User();
//			ss.setUser(user);
//			JSONObject acomment = (JSONObject) ja.get(i);
//			ss.setCreatedAt((String)(acomment.get("created_at")));
//			ss.setId((long)(acomment.get("id")));
//			ss.setText((String)(acomment.get("text")));
//			JSONObject source = (JSONObject)acomment.get("status");
//			retweet.setCreatedAt((String)(source.get("created_at"))); 
//			retweet.setId((long)(source.get("id")));
//			retweet.setText((String)(source.get("text")));
//			ss.setShareShow(retweet);
//			shows.add(ss);
//		}
		
//		return shows;

	}
	public static void main(String[] args) {
		new StoreShareImpl().storeAllShareFromWeibo();
	}

}
