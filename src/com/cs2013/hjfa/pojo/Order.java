package com.cs2013.hjfa.pojo;


public class Order {

	private String id;//订单id
	private String name;
	private long orderTime;//下单时间
	private long confirmTime;//确定时间
	
	private String uid;//用户id
	private String sid;//座位id
	private int state;//预定状态,0 未确认,1确认
	
	private String description;//描述信息

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(long orderTime) {
		this.orderTime = orderTime;
	}

	public long getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(long confirmTime) {
		this.confirmTime = confirmTime;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", name=" + name + ", orderTime="
				+ orderTime + ", confirmTime=" + confirmTime + ", uid=" + uid
				+ ", sid=" + sid + ", state=" + state + ", description="
				+ description + "]";
	}
	
}
