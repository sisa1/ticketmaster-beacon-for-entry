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
	public EventTimeBean getEventTime(int eventId){
		return null;
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
