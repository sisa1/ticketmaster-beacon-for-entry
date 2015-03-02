package com.ticketmaster.bean;
import java.io.Serializable;
import java.util.Date;

public class EventTimeBean implements Serializable {
	private static final long serialVersionUID = 1L;
	int Id;
	Date StartTime;
	Date EndTime;
	
	
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
	
	public Date getStartTime() {
		return StartTime;
	}
	public void setStartTime(Date startTime) {
		StartTime = startTime;
	}
	
	public Date getEndTime() {
		return StartTime;
	}
	public void setEndTime(Date startTime) {
		StartTime = startTime;
	}	
}
