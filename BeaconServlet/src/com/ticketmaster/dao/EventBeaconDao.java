package com.ticketmaster.dao;

import com.ticketmaster.bean.EventBeaconBean;
import java.util.List;

public interface EventBeaconDao {
	/** Standard - CRUD operation **/
	public EventBeaconBean createEventBeacon(EventBeaconBean eventBeacon);
	public EventBeaconBean readEventBeacon(int id);
	public EventBeaconBean readEventBeacon(String UUID);
	public EventBeaconBean updateEventBeacon(int id, EventBeaconBean eventBeacon);
	public void deleteEventBeacon(int id);
	public List<EventBeaconBean> getBeaconsForEvent(int eventId);
	public List<EventBeaconBean> getAllEventBeacons();
}
