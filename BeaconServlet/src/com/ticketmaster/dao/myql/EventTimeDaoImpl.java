package com.ticketmaster.dao.myql;

import com.ticketmaster.bean.EventTimeBean;
import com.ticketmaster.dao.EventTimeDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

public class EventTimeDaoImpl extends MySqlDao implements EventTimeDao {
	public EventTimeBean readEventTime(int eventId){
		Connection con = null;
		EventTimeBean result = null;
		Statement stmt = null;
		con = MySqlDao.getConnection();
		try {
			stmt = con.createStatement();
			String query = "SELECT * FROM eventTimes WHERE EventId = " + eventId;
			ResultSet rs =stmt.executeQuery(query);
			result = new EventTimeBean();
			result.setId(eventId);
			
			while(rs.next()) {
				result.setStartTime(rs.getTimestamp("StartTime"));
				result.setEndTime(rs.getTimestamp("EndTime"));
			}
			
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// close the connection even if get was unsuccessful
			MySqlDao.cleanup(con);
		}					
		return result;
	}
	
	public EventTimeBean createEventTime(int eventId, Timestamp startTime, Timestamp endTime){
		EventTimeBean result = null;
		Connection con = null;
		Statement stmt = null;
		con = MySqlDao.getConnection();
		try {
			stmt = con.createStatement();
			String query = "INSERT INTO eventTimes VALUES (" + eventId + ", " + startTime.toString() + ", " + endTime.toString() +")";
			stmt.executeUpdate(query);
			stmt.close();
			result = new EventTimeBean();
			result.setId(eventId);
			result.setStartTime(startTime);
			result.setEndTime(endTime);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// close the connection even if get was unsuccessful
			MySqlDao.cleanup(con);
		}	
		
		return result;
	}
	
	public EventTimeBean updateEventTime(int eventId, Timestamp startTime, Timestamp endTime){
		EventTimeBean result = null;
		Connection con = null;
		Statement stmt = null;
		con = MySqlDao.getConnection();
		try {
			stmt = con.createStatement();
			String query = "UPDATE eventTimes SET StartTime = '" + startTime.toString() + "',  EndTime = '" + endTime.toString() + "' WHERE EventId = " + eventId;
			stmt.executeUpdate(query);
			stmt.close();
			result = new EventTimeBean();
			result.setId(eventId);
			result.setStartTime(startTime);
			result.setEndTime(endTime);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// close the connection even if get was unsuccessful
			MySqlDao.cleanup(con);
		}	
		
		return result;
	}	
	
	public void deleteEventTime(int eventId){
		EventTimeBean result = null;
		Connection con = null;
		Statement stmt = null;
		con = MySqlDao.getConnection();
		try {
			stmt = con.createStatement();
			String query = "DELETE FROM eventTimes WHERE EventId = " + eventId;
			stmt.executeUpdate(query);
			stmt.close();

			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// close the connection even if get was unsuccessful
			MySqlDao.cleanup(con);
		}		
		return;
	}	
	
	//checks if the current time is within the duration of the specified event. 
	public boolean compareScanTime(int eventId){
		Date tmp = new Date();
		Timestamp currentTime = new Timestamp(tmp.getTime());
		//System.out.println(eventId);
		//System.out.println("Current Time: " + currentTime.toString());
		Timestamp startTime = null;
		Timestamp endTime = null;
		Connection con = null;
		Statement stmt = null;
		boolean result = false;
		con = MySqlDao.getConnection();
		
		try {
			stmt = con.createStatement();
			String query = "SELECT * FROM eventTimes WHERE EventId = " + eventId;
			ResultSet rs =stmt.executeQuery(query);
			
			while(rs.next()) {
				startTime = rs.getTimestamp("StartTime");
				endTime = rs.getTimestamp("EndTime");
			}
			
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			result = false;
		} finally {
			// close the connection even if get was unsuccessful
			MySqlDao.cleanup(con);
		}

		//Check the time here
		if(startTime == null){
			return result;
		}
		if(endTime == null){
			return result;
		}
		
		//System.out.println("End Time: " + endTime.toString());
		//System.out.println("Start Time: " + startTime.toString());
		
		if(currentTime.compareTo(startTime) >= 0){
			if(currentTime.compareTo(endTime) <= 0){
				result = true;
			}
		}
		
		return result;
	}

}
