/*
 * @AUTHOR: Matthew de la Rosa
 * @DATE: Aug 2014
 * 
 * @DESCRIPTION:
 * 		DaoFactory produces My SQL implementation of DAOs
 * 		Return types are generic DAO
 */

package com.ticketmaster.dao;

import com.ticketmaster.dao.myql.RosterEntryDaoImpl;
import com.ticketmaster.dao.myql.ScanEntryDaoImpl;
import com.ticketmaster.dao.myql.UserDaoImpl;
import com.ticketmaster.dao.myql.EventDaoImpl;
import com.ticketmaster.dao.myql.EventTimeDaoImpl;
import com.ticketmaster.dao.myql.EventBeaconDaoImpl;

public class MySqlDaoFactory {

	public static UserDao getUserDAO() {
		return new UserDaoImpl();
	}
	
	public static EventDao getEventDAO(){
		return new EventDaoImpl();
	}
	
	public static RosterEntryDao getRosterEntryDAO(){
		return new RosterEntryDaoImpl();
	}
	
	public static EventTimeDao getEventTimeDAO(){
		return new EventTimeDaoImpl();
	}
	
	public static  ScanEntryDao getScanEntryDAO() {
		return new ScanEntryDaoImpl();
	}
	
	public static EventBeaconDao getEventBeaconDAO(){
		return new EventBeaconDaoImpl();
	}
}
