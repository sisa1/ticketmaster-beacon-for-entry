package com.ticketmaster.dao;

import java.util.List;

import com.ticketmaster.bean.EventBean;

public interface EventDao {
	public List<EventBean> getAllEvents();
	public EventBean getEvent(String name);
	public EventBean readEvent(int id);
	public EventBean createEvent(int id, String name);
	//public EventBean deleteEvent(int id);
}
