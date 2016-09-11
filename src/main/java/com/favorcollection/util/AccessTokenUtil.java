package com.favorcollection.util;

import java.io.OutputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import weibo4j.Oauth;
import weibo4j.http.AccessToken;
import weibo4j.model.WeiboException;
import weibo4j.util.WeiboConfig;

public class AccessTokenUtil {
	private static final Logger logger = Logger.getLogger(AccessToken.class);

	public static AccessToken getAccessToken(){
		Oauth oauth = new Oauth();
		String code = Weibo.getCode();
		AccessToken accessToken = null;
		try{
			accessToken = oauth.getAccessTokenByCode(code);
			
		} catch (WeiboException e) {
			if(401 == e.getStatusCode()){
				logger.info("Unable to get the access token.");
			}else{
				e.printStackTrace();
			}
		}
		logger.info(accessToken);
		return accessToken;
		
	}
	
 
	public static void main(String[] args) {
		getAccessToken();
	}

}
