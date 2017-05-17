package com.cs2013.hjfa.pojo;

public class Seat {

	private String id;
	private String name;
	private String state;
	private String pid;
	private String description;
	private String level;
	
	private Library library;
	
	public Library getLibrary() {
		return library;
	}
	public void setLibrary(Library library) {
		this.library = library;
	}
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	@Override
	public String toString() {
		return "Seat [id=" + id + ", name=" + name + ", state=" + state
				+ ", pid=" + pid + ", description=" + description + ", level="
				+ level + ", library=" + library + "]";
	}
	
}
