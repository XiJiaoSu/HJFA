package com.cs2013.hjfa.pojo.json;



public class JsonObject<T> {

	private int code=-1;
	private String msg;
	
	private T result;

	public int getCode() {
		return code;
	}

	public JsonObject<T> setCode(int code) {
		this.code = code;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getResult() {
		return  result;
	}

	public JsonObject<T> setResult(T result) {
		this.result = result;
		return this;
	}

	@Override
	public String toString() {
		return "JsonObject [code=" + code + ", msg=" + msg + ", result="
				+ result + "]";
	}

}
