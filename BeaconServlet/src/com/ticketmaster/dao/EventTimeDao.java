package com.ticketmaster.dao;
import com.ticketmaster.bean.EventTimeBean;
import java.util.Date;

public interface EventTimeDao {
	EventTimeBean getEventTime(int eventId);
	boolean compareScanTime(int eventId);

}
