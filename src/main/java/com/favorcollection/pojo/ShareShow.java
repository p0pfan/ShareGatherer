package com.favorcollection.pojo;

import java.util.Date;

public class ShareShow {
	private Date createdAt;
	private long id;
	private String text;
	private ShareShow shareShow;
	private Weibouser weiboUser;
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public ShareShow getShareShow() {
		return shareShow;
	}
	public void setShareShow(ShareShow shareShow) {
		this.shareShow = shareShow;
	}
	public Weibouser getWeiboUser() {
		return weiboUser;
	}
	public void setWeiboUser(Weibouser weiboUser) {
		this.weiboUser = weiboUser;
	}
	
}
