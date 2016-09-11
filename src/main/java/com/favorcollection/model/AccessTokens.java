package com.favorcollection.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import weibo4j.http.AccessToken;
import weibo4j.model.WeiboException;

@Document(collection = "token")
public class AccessTokens {
	@Id
	private String id;
	
	private String admin;
	private AccessToken accessToken;
	private long createTime;
	
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	public AccessToken getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(AccessToken accessToken) {
		this.accessToken = accessToken;
	}
	public AccessTokens() throws Exception {
		super();
		accessToken =new AccessToken();
	}
	
	
}
