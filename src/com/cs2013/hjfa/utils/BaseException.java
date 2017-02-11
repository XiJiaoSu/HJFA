package com.cs2013.hjfa.utils;

/**
 * 自定义的异常信息
 * 
 * @author Wuchuang
 * 
 */
public class BaseException extends Exception {

	// 异常代码
	private String code;
	// 异常信息
	private String message;
	// 显示给用户的异常信息
	private String friend;

	public BaseException(String code, String message, String friend) {
		super(message);
		this.code = code;
		this.message = message;
		this.friend = friend;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFriend() {
		return friend;
	}

	public void setFriend(String friend) {
		this.friend = friend;
	}
}
