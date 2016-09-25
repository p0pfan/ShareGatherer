package com.favorcollection.pojo;

public class User {
	private long id;
	private String username;
	private String Created_at;
	private String avatar;
	private String gender;
	private String location;
	
	
	public User(){
		this.id = 10000;
		this.username = "Anonymous";
	}
	
	public User(long id, String username, String created_at, String avatar) {
		super();
		this.id = id;
		this.username = username;
		Created_at = created_at;
		this.avatar = avatar;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCreated_at() {
		return Created_at;
	}
	public void setCreated_at(String created_at) {
		Created_at = created_at;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
}
