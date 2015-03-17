package com.ticketmaster.bean;

import java.util.List;

public class EventBean {
	int Id;
	String Name;
	List<EventBeaconBean> BeaconGates;
	
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

	public List<EventBeaconBean> getBeaconGates() {
		return BeaconGates;
	}

	public void setBeaconGates(List<EventBeaconBean> beaconGates) {
		BeaconGates = beaconGates;
	}
	
	
}