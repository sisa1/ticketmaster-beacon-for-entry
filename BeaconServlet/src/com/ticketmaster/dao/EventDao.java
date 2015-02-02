package com.ticketmaster.dao;

import java.util.List;

import com.ticketmaster.bean.EventBean;

public interface EventDao {
	public List<EventBean> getAllEvents();
	public EventBean getEvent();
	public EventBean readEvent();
	public EventBean createEvent();
	public EventBean deleteEvent();
}
