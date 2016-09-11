package com.favorcollection.service;

import com.favorcollection.pojo.UserInfo;

import weibo4j.http.AccessToken;

public interface UserService {
	public UserInfo showUser(AccessToken access); 
}
