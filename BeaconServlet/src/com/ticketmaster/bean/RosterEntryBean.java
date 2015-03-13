package com.ticketmaster.bean;

import java.io.Serializable;

public class RosterEntryBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	int Id;
	EventBean Event;
	UserBean Visitor;
	boolean DidAttend;
	
	public RosterEntryBean(EventBean event, UserBean user, boolean attendStatus) {
		Event = event;
		Visitor = user;
		DidAttend = attendStatus;
	}
	
	public RosterEntryBean() {
		Event = null;
		Visitor = null;
		DidAttend = false;
	}
	
	public UserBean getVisitor() {
		return Visitor;
	}
	public void setVisitor(UserBean visitor) {
		Visitor = visitor;
	}
	public boolean isDidAttend() {
		return DidAttend;
	}
	public void setDidAttend(boolean didAttend) {
		DidAttend = didAttend;
	}
	public EventBean getEvent() {
		return Event;
	}
	public void setEvent(EventBean event) {
		Event = event;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}
}
