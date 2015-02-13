package com.ticketmaster.dao.myql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ticketmaster.bean.EventBean;
import com.ticketmaster.bean.RosterEntryBean;
import com.ticketmaster.bean.UserBean;
import com.ticketmaster.dao.RosterEntryDao;

public class RosterEntryDaoImpl extends MySqlDao implements RosterEntryDao {
	
	public List<RosterEntryBean> getAllRosterEntries() {
		return null;
	}
	public List<RosterEntryBean> getRosterForEvent(int eventId) {
		return null;
	}
	
	// CRUD
	public EventBean createRoster(String name) {
		return null;
	}
	
	public EventBean readRoster(int id) {
		return null;
	}
	
	public EventBean updateRoster(int id, UserBean userBean, EventBean eventBean) {
		return null;
	}
	
	public void deleteRoster(int id) {
		
	}
}
