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
		Connection con = null;
		List<RosterEntryBean> result = new ArrayList<RosterEntryBean>();
		con = MySqlDao.getConnection();
		
		try {
			String selectAllQuery = "SELECT * FROM beacon_servlet.eventRoster, beacon_servlet.users, beacon_servlet.events WHERE beacon_servlet.eventRoster.UserId=beacon_servlet.users.Id AND beacon_servlet.eventRoster.EventId=beacon_servlet.events.EventId";
			PreparedStatement pStatement = con.prepareStatement(selectAllQuery);
			ResultSet rs = pStatement.executeQuery();
			
			// Iterate ResultSet and Initialize Camera list
			while(rs.next()) {
				EventBean eventToAddToEntry = new EventBean();
				eventToAddToEntry.setId(Integer.parseInt(rs.getString("EventId")));
				eventToAddToEntry.setName(rs.getString("EventName"));
				
				UserBean userToAddToEntry = new UserBean();
				userToAddToEntry.setId(Integer.parseInt(rs.getString("Id")));
				userToAddToEntry.setFirstName(rs.getString("FirstName"));
				userToAddToEntry.setLastName(rs.getString("LastName"));
				userToAddToEntry.setUsername(rs.getString("Username"));
				
				boolean userAttendedEntry = rs.getBoolean("AttendedFlag");
				
				RosterEntryBean entry = new RosterEntryBean(eventToAddToEntry, userToAddToEntry, userAttendedEntry);
				result.add(entry);
			}
			
			pStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			result = null;
		} finally {
			// close the connection even if get was unsuccessful
			MySqlDao.cleanup(con);
		}
		return result;
	}
	
	public List<RosterEntryBean> getRosterForEvent(int eventId) {
		return null;
	}
	
	public boolean setAttend(int eventId, String username) {
		//UPDATE `beacon_servlet`.`eventRoster` SET `AttendedFlag`='1' WHERE `Id`='2';
		Connection con = null;
		con = MySqlDao.getConnection();
		ResultSet rs = null;
		try {
			String selectAllQuery = "UPDATE beacon_servlet.eventRoster SET AttendedFlag=0 WHERE(UserId = (SELECT Id FROM beacon_servlet.users WHERE beacon_servlet.users.Username=?) and eventId = ?)";
			PreparedStatement pStatement = con.prepareStatement(selectAllQuery);
			pStatement.setString(1, username);
			pStatement.setInt(2, eventId);
			rs = pStatement.executeQuery();
			pStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// close the connection even if get was unsuccessful
			MySqlDao.cleanup(con);
		}
		
		try {
			if(rs != null && rs.getRow() != 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
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
