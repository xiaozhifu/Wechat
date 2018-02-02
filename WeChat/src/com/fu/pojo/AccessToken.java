package com.fu.pojo;

import java.io.Serializable;
import java.util.Date;

public class AccessToken implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String token;
	private int expirsIn;
	private long getTokenTime;
	
	public long getGetTokenTime() {
		return getTokenTime;
	}
	public void setGetTokenTime() {
		this.getTokenTime = new Date().getTime();
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getExpirsIn() {
		return expirsIn;
	}
	public void setExpirsIn(int expirsIn) {
		this.expirsIn = expirsIn;
	}
	
	@Override
	public String toString() {
		return "AccessToken [token=" + token + ", expirsIn=" + expirsIn + ", getTokenTime=" + getTokenTime + "]";
	}
}
