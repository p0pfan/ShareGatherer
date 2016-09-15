package com.favorcollection.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.favorcollection.model.GatheredInfo;
import com.favorcollection.pojo.ShareShow;
import com.favorcollection.pojo.Weibouser;
import com.favorcollection.service.PostService;
import com.favorcollection.service.Impl.WeiboTokenImpl;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

import weibo4j.http.AccessToken;



@RestController
@RequestMapping("/share")
public class ShareController {
	public static Logger log = Logger.getLogger(ShareController.class);
	@Autowired
	private PostService postService;
	
	@Autowired
	private WeiboTokenImpl weiboTokenImpl;
	
	@RequestMapping(value = "/all" , method = RequestMethod.GET)
	public @ResponseBody List<GatheredInfo> showShares(){
		return postService.showShares();
	}
	
	/*
	 * fix bug in chinese code
	 * http://josh-persistence.iteye.com/blog/2085015
	 */
	@RequestMapping(value = "/weibo" , method = RequestMethod.GET,produces="application/json;charset=utf-8")
	public  @ResponseBody String getWeiboShare() throws UnsupportedEncodingException, ClientHandlerException, UniformInterfaceException{
		AccessToken accesstoken = weiboTokenImpl.getAccessToken("c_c1227@163.com");
		Client client = Client.create(); 
		WebResource webResource = client
				   .resource("https://api.weibo.com/2/comments/mentions.json");
		ClientResponse response = webResource
				.queryParam("access_token",accesstoken.getAccessToken())
				.queryParam("since_id","-1473900000")
				.queryParam("max_id","0")
				.queryParam("count","50")
				.queryParam("page","1")
				.queryParam("filter_by_author","0")
				.queryParam("filter_by_source","0")
				.get(ClientResponse.class);
		String xx = response.getEntity(String.class);
		JSONObject json = new JSONObject(xx);
		JSONArray ja = json.getJSONArray("comments");
		
		return ja.toString();
	}

}
