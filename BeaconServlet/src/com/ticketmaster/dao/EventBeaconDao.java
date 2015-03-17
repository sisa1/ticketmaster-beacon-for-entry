package com.ticketmaster.dao;

import com.ticketmaster.bean.EventBeaconBean;

public interface EventBeaconDao {
	/** Standard - CRUD operation **/
	public EventBeaconBean createEventBeacon(EventBeaconBean eventBeacon);
	public EventBeaconBean readEventBeacon(int id);
	public EventBeaconBean updateEventBeacon(int id, EventBeaconBean eventBeacon);
	public void deleteEventBeacon(int id);
}
