package com.ticketmaster.dao;

import java.sql.Timestamp;
import java.util.List;

import com.ticketmaster.bean.ScanEntryBean;
import com.ticketmaster.bean.UserBean;

public interface ScanEntryDao {
	// Inserts a user into the database with the specified parameters
	public List<ScanEntryBean> getScansForEvent(int eventId);
	public List<ScanEntryBean> getScansForEventBeforeTime(int eventId, int secondsBefore);
}
