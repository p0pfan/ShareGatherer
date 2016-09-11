package com.favorcollection.pojo;

public class EmailSource {
	private String from;
	private String subject;
	private String content;
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "EmailSource [from=" + from + ", subject=" + subject
				+ ", content=" + content + "]";
	}
	

}
