package com.ticketmaster.dao;

import java.util.List;

import com.ticketmaster.bean.EventBean;
import com.ticketmaster.bean.UserBean;

public interface EventDao {
	
	/** Standard - CRUD operation **/
	public EventBean createEvent(EventBean event);
	public EventBean readEvent(int id);
	public EventBean updateEvent(int id, EventBean event);
	public void deleteEvent(int id);
	
	
	public List<EventBean> getAllEvents();
	public EventBean getEvent(String name);
	public EventBean createEvent(String name);
}
