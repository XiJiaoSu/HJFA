package com.cs2013.hjfa.utils;

public interface MResponseHandler {
	/**
	 * ���������֮ǰ,������ӽ�������ʾ
	 */
	void preResponse();

	/**
	 * ����������
	 * @param res
	 * @param code
	 */
    void onResponse(String res, int code);

    /**
     * �������
     * @param msg
     */
    void onError(String msg,int code,int resCode);

    /**
     * ��������������������ɹ�����ʾ�����preResponse�Ĳ���
     * @param code
     */
    void afterResponse(int code,int state);
	
    
    
    
}
