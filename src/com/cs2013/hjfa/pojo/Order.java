package com.cs2013.hjfa.pojo;


public class Order {

	private String id;//����id
	private String name;
	private long orderTime;//�µ�ʱ��
	private long confirmTime;//ȷ��ʱ��
	
	private String uid;//�û�id
	private String sid;//��λid
	private int state;//Ԥ��״̬,0 δȷ��,1ȷ��
	
	private String description;//������Ϣ

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
