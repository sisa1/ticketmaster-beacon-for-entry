package com.ticketmaster.bean;
import java.io.Serializable;
import java.sql.Timestamp;

public class EventTimeBean implements Serializable {
	private static final long serialVersionUID = 1L;
	int Id;
	Timestamp StartTime;
	Timestamp EndTime;
	
	
	public EventTimeBean(){
		Id = 0;
		StartTime = null;
		EndTime = null;
	}
	
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	
	public Timestamp getStartTime() {
		return StartTime;
	}
	public void setStartTime(Timestamp startTime) {
		StartTime = startTime;
	}
	
	public Timestamp getEndTime() {
		return StartTime;
	}
	public void setEndTime(Timestamp startTime) {
		StartTime = startTime;
	}	
}
