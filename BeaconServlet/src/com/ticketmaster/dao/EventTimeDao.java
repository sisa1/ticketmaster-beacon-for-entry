package com.ticketmaster.dao;
import com.ticketmaster.bean.EventTimeBean;
import java.util.Date;
import java.sql.Timestamp;

public interface EventTimeDao {
	// CRUD operations
	EventTimeBean readEventTime(int eventId);
	EventTimeBean createEventTime(int eventId, Timestamp startTime, Timestamp endTime);
	void deleteEventTime(int eventId);
	EventTimeBean updateEventTime(int eventId, Timestamp startTime, Timestamp endTime);
	
	// Compare current time with start and end times of a given event
	boolean compareScanTime(int eventId);
	

}
