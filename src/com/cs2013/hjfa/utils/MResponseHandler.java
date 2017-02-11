package com.cs2013.hjfa.utils;

public interface MResponseHandler {
	/**
	 * 请求服务器之前,可以添加进度条显示
	 */
	void preResponse();

	/**
	 * 返回请求结果
	 * @param res
	 * @param code
	 */
    void onResponse(String res, int code);

    /**
     * 请求出错
     * @param msg
     */
    void onError(String msg,int code,int resCode);

    /**
     * 请求结束，可以添加请求成功的提示与结束preResponse的操作
     * @param code
     */
    void afterResponse(int code,int state);
	
    
    
    
}
