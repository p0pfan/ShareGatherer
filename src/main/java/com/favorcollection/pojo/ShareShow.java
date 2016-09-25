package com.favorcollection.pojo;


public class ShareShow {
	private String createdAt;
	private long id;
	private String text;
	private ShareShow shareShow;
	private User user;
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
