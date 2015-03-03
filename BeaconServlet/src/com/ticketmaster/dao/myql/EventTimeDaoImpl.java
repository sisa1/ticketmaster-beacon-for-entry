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
import java.util.List;

public class EventTimeDaoImpl extends MySqlDao implements EventTimeDao {
	public EventTimeBean getEventTime(int eventId){
		return null;
	}
	
	//checks if the current time is within the duration of the specified event. 
	public boolean compareScanTime(int eventId){
		Date currentTime = new Date();
		Date startTime = null;
		Date endTime = null;
		Connection con = null;
		Statement stmt = null;
		boolean result = false;
		con = MySqlDao.getConnection();
		
		try {
			stmt = con.createStatement();
			String query = "SELECT * FROM eventTimes WHERE EventId =" + eventId + "";
			ResultSet rs =stmt.executeQuery(query);
			

			while(rs.next()) {
				startTime = rs.getDate("StartTime");
				endTime = rs.getDate("EndTime");
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
		if(currentTime.compareTo(startTime) >= 0){
			if(currentTime.compareTo(endTime) <= 0){
				result = true;
			}
		}
		
		return result;
	}

}
