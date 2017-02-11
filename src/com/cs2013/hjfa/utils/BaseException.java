package com.cs2013.hjfa.utils;

/**
 * �Զ�����쳣��Ϣ
 * 
 * @author Wuchuang
 * 
 */
public class BaseException extends Exception {

	// �쳣����
	private String code;
	// �쳣��Ϣ
	private String message;
	// ��ʾ���û����쳣��Ϣ
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
