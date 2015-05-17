package com.logoocc.jsonparsedemo1.bean;

import javax.security.auth.PrivateCredentialPermission;

import android.R.integer;

public class User {

	/**
	 * @param args
	 */
	private int id;
	private String name;
	private String time;
    private String img;
    
	public User(int id, String name, String time) {
		
		this.id = id;
		this.name = name;
		this.time = time;
	
	}
	public User(int id, String name, String time, String img) {
		
		this.id = id;
		this.name = name;
		this.time = time;
		this.img = img;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	

}
