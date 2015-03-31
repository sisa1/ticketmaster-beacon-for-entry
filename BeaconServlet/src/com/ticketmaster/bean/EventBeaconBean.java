package com.ticketmaster.bean;

import java.io.Serializable;

public class EventBeaconBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int Id;
	private String Name;
	private int EventId;
	private String UUID;
	private int Major;
	private int Minor;
	
	public EventBeaconBean(int id, String name, int eventId, String uuid, int major, int minor){
		Id = id;
		Name = name;
		EventId = eventId;
		UUID = uuid;
		Major = major;
		Minor = minor;
	}
	
	public EventBeaconBean(){
		Id = -1;
		Name = null;
		EventId = 0;
		UUID = null;
		Major = 0;
		Minor = 0;
	}
	
	public int getId() {
		return Id;
	}
	public void setId(int beaconId) {
		Id = beaconId;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getEventId() {
		return EventId;
	}
	public void setEventId(int eventId) {
		EventId = eventId;
	}
	public String getUUID() {
		return UUID;
	}
	public void setUUID(String uUID) {
		UUID = uUID;
	}
	public int getMajor() {
		return Major;
	}
	public void setMajor(int major) {
		Major = major;
	}
	public int getMinor() {
		return Minor;
	}
	public void setMinor(int minor) {
		Minor = minor;
	}
	
}
