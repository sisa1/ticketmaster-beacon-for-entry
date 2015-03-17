package com.ticketmaster.bean;

import java.io.Serializable;
import java.sql.Timestamp;

//Bean for User objects
public class ScanEntryBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	int userID;
	String username;
	int eventID;
	String errorMessage;
	Timestamp timeOfScan;
	boolean didAttend;
	
	public ScanEntryBean(int uId, String uName, int eId, String errMsg, Timestamp scan, boolean attended) {
		userID = uId;
		username = uName;
		eventID = eId;
		errorMessage = errMsg;
		timeOfScan = scan;
		didAttend  = attended;
	}
	
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getEventID() {
		return eventID;
	}
	public void setEventID(int eventID) {
		this.eventID = eventID;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public Timestamp getTimeOfScan() {
		return timeOfScan;
	}
	public void setTimeOfScan(Timestamp timeOfScan) {
		this.timeOfScan = timeOfScan;
	}

	public boolean isDidAttend() {
		return didAttend;
	}

	public void setDidAttend(boolean didAttend) {
		this.didAttend = didAttend;
	}
	
	
}