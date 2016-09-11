package com.favorcollection.service;

import weibo4j.http.AccessToken;

public interface WeiboToken {
	public AccessToken getAccessToken(String admin);
	public void saveAccessToken(String user, AccessToken accessToken);
}
