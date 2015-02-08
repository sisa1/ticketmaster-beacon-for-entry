package com.ticketmaster.bean;

public class EventBean {
	int Id;
	String Name;
	
	public EventBean(){
		Id = 0;
		Name = null;
	}
	
	public EventBean(int aId, String aName){
		Id = aId;
		Name = aName;
	}
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	
	
}