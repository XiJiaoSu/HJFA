package com.cs2013.hjfa.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class User {
	private String id;
	private String username;
	private String password;
	private Integer age;
	private String email;// 邮箱
	private long birth;// 生日
	private String phone;// 手机号
	private String stuId;// 学号

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirth() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.birth);
	}

	public void setBirth(long birth) {
		
		this.birth = birth;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStuId() {
		return stuId;
	}

	public void setStuId(String stuId) {
		this.stuId = stuId;
	}

}
