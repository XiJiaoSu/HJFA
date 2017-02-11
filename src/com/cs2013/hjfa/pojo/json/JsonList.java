package com.cs2013.hjfa.pojo.json;

import java.util.ArrayList;
import java.util.List;


public class JsonList<T> {

	private int code=-1;
	private String msg;
	
	private List<T> result=null;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<T> getResult() {
		if (result==null) {
			result=new ArrayList<T>();
		}
		return result;
	}

	public void setResult(List<T> list) {
		this.result = list;
	}
	
}
