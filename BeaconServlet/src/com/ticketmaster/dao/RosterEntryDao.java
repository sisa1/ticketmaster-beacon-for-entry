package com.ticketmaster.dao;

import java.util.List;

import com.ticketmaster.bean.EventBean;
import com.ticketmaster.bean.RosterEntryBean;
import com.ticketmaster.bean.UserBean;

public interface RosterEntryDao {
	
	
	/** Standard - CRUD operation **/
	public RosterEntryBean createTicket(RosterEntryBean ticket);
	public RosterEntryBean readRoster(int id);
	public RosterEntryBean updateRoster(int id, RosterEntryBean ticket);
	public void deleteRoster(int id);
	
	public List<RosterEntryBean> getAllRosterEntries();
	public List<RosterEntryBean> getRosterForEvent(int eventId);
	public boolean setAttend(int eventId, String username);
	public boolean setUnAttend(int eventId, String username);
	
	public RosterEntryBean createRoster(String name);
	public RosterEntryBean readRoster(int eventId, int userId);
	public RosterEntryBean readRoster(int eventId, String username);
}
