package com.favorcollection.service.Impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.favorcollection.dao.AccessTokenDao;
import com.favorcollection.dao.impl.AccessTokenDaoImpl;
import com.favorcollection.model.AccessTokens;
import com.favorcollection.service.WeiboToken;
import com.favorcollection.util.AccessTokenUtil;


import weibo4j.http.AccessToken;

@Service
public class WeiboTokenImpl implements WeiboToken {
	private static Logger log = Logger.getLogger(WeiboTokenImpl.class);
	public static final String COLLECTION = "token";
	@Autowired
	private AccessTokenDaoImpl accessTokenImpl;
	
	@Override
	public AccessToken getAccessToken(String admin) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("admin", admin);
		AccessTokens at = accessTokenImpl.findOne(params, COLLECTION);
		log.info(at);
		log.info("get access token");
		if(at !=null ){
			
			String expireTime =at.getAccessToken().getExpireIn();
			long createTime = at.getCreateTime();
			if((System.currentTimeMillis()-createTime) < Long.valueOf(expireTime)){
				log.info("get accessToken from mongo");
				return at.getAccessToken();
			}else{
				log.info("update access from weibo");
				AccessToken acctks = AccessTokenUtil.getAccessToken();
				params.put("AccessToken", acctks);
				params.put("CeateTime", System.currentTimeMillis());
				accessTokenImpl.update(params, COLLECTION);
				return acctks;
			}
		}else{
			log.info("recapture access from weibo");
			
			AccessToken acctks = AccessTokenUtil.getAccessToken();
			saveAccessToken(admin, acctks);
			return acctks;
		}
		
		
	}

	@Override
	public void saveAccessToken(String user, AccessToken accessToken) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("admin", user);
		if(accessTokenImpl.findOne(params, COLLECTION)!=null){
			AccessToken acctks = AccessTokenUtil.getAccessToken();
			params.put("AccessToken", acctks);
			params.put("CeateTime", System.currentTimeMillis());
			accessTokenImpl.update(params, COLLECTION);
		}
		AccessTokens newATS =null;
		try {
			newATS = new AccessTokens();
			newATS.setAdmin(user);
			newATS.setAccessToken(accessToken);
			newATS.setCreateTime(System.currentTimeMillis());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		accessTokenImpl.insert(newATS, COLLECTION);
	}
	

	

}
