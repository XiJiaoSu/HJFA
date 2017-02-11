package com.cs2013.hjfa.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class NetWorkResponse {
	private static final int ConnectionTimeout = 5000;// 连接超时时间
	private static final int SoTimeout = 6000;// 获取数据超时时间
	private int tos;

	private String url;
	private MResponseHandler responseHandler;
	private String entity="";
	private static final int WHAT_FINISH = 1000;// 表示请求成功
	private static final int WHAT_ERROE = -1000;// 表示请求失败

	private Handler handler = new Handler() {// 用于请求结束时的数据处理

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case WHAT_FINISH:
				responseHandler.afterResponse(msg.arg1,Constants.STATE_OK);
				break;
			case WHAT_ERROE:
				responseHandler.onError((String) msg.obj,msg.arg1,Constants.STATE_ERROR);
				break;
			default:
				break;
			}
		};
	};


	public NetWorkResponse(String url, String entity, int tos,MResponseHandler handler) {
		this.entity=entity;
		this.url=url;
		this.tos=tos;
		this.responseHandler=handler;
	}

	public void postResponse() {
		responseHandler.preResponse();
		new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (this) {
					HttpClient httpClient=null;
					try {
						String result = "";
						Looper.prepare();
						//实例化请求的客户端
						 httpClient=new DefaultHttpClient(getParams());
						//获取httpPost
						HttpPost httpPost=new HttpPost(url);
						//添加头信息
						httpPost.addHeader("Content-Type", "application/json");
						if (entity!=null&&!"".equals(entity)) {
							httpPost.setEntity(new StringEntity(entity));
						}
						//执行请求
						HttpResponse httpResponse=httpClient.execute(httpPost);
						if (httpResponse.getStatusLine().getStatusCode()==200) {
							result=EntityUtils.toString(httpResponse.getEntity());
						}
						if ("".equals(result)) {
							throw new BaseException("-5", "当前网络不稳定", "当前网络不稳定");
						}
						responseHandler.onResponse(result, tos);
						Message msg = handler.obtainMessage();
						msg.what = WHAT_FINISH;
						msg.arg1 = tos;
						msg.sendToTarget();// 将消息发送给Handler进行处理
					} catch (Exception e) {
						Log.e("Log", "Exception:"+e.getLocalizedMessage());
						BaseException baseException=null;
						if (e instanceof BaseException) {
							baseException=(BaseException) e;
						}else{
							Log.d("wuwu",e.getMessage());
							baseException=new BaseException("-4", "当前网络不稳定", "当前网络不稳定");
						}
						Message msg = handler.obtainMessage();
						msg.what = WHAT_ERROE;
						msg.arg1=tos;
						msg.obj = baseException.getCode()+":"+baseException.getFriend();
						msg.sendToTarget();// 将消息发送给Handler进行处理
					}finally{
						if (httpClient!=null) {
							httpClient.getConnectionManager().shutdown();
						}
						Looper.loop();
					}
				}
			}
		}).start();
	}

	/**
	 * 设置http请求的参数，如网络连接超时等
	 * @return
	 */
	protected HttpParams getParams() {
		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams
				.setConnectionTimeout(httpParams, ConnectionTimeout);// 设置连接超时
		HttpConnectionParams.setSoTimeout(httpParams, SoTimeout);// 设置获得数据超时
		return httpParams;
	}

	public Handler getHandler() {
		return handler;
	}
	
	
}
