package com.ticketmaster.dao.myql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ticketmaster.bean.ScanEntryBean;
import com.ticketmaster.bean.UserBean;
import com.ticketmaster.dao.ScanEntryDao;
import com.ticketmaster.dao.UserDao;

public class ScanEntryDaoImpl extends MySqlDao implements ScanEntryDao {

	@Override
	public List<ScanEntryBean> getScansForEvent(int eventId) {
		
		Connection con = null;
		Statement stmt = null;
		con = MySqlDao.getConnection();
		
		List<ScanEntryBean> returnMe = new ArrayList<ScanEntryBean>();
		
		int userId;
		String username;
		String responseMessage;
		Timestamp timeScanned = null;
		
		try {
			stmt = con.createStatement();
			String query = "SELECT * FROM eventEntryScans WHERE EventId = " + eventId;
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				userId = rs.getInt("userID");
				username = rs.getString("username");
				eventId = rs.getInt("eventID");
				responseMessage = rs.getString("responseMessage");
				timeScanned = rs.getTimestamp("timeScanned");
				
				ScanEntryBean addMe = new ScanEntryBean(userId, username, eventId, responseMessage, timeScanned);
				returnMe.add(addMe);
			}
			stmt.close();
			
		} catch(Exception ex) {
			returnMe = null;
		}
		
		return returnMe;
	}
	
	public List<ScanEntryBean> getScansForEventBeforeTime(int eventId, int secondsBefore) {
		Connection con = null;
		Statement stmt = null;
		con = MySqlDao.getConnection();
		
		List<ScanEntryBean> returnMe = new ArrayList<ScanEntryBean>();
		
		int userId;
		String username;
		String responseMessage;
		Timestamp timeScanned = null;
		
		try {
			stmt = con.createStatement();
			String query = "SELECT * FROM eventEntryScans WHERE (EventId=? AND (UNIX_TIMESTAMP() - UNIX_TIMESTAMP(eventEntryScans.timeScanned))<?)";
			PreparedStatement pStatement = con.prepareStatement(query);
			pStatement.setInt(1, eventId);
			pStatement.setInt(2, secondsBefore);
			ResultSet rs = pStatement.executeQuery();
			
			while(rs.next()) {
				userId = rs.getInt("userID");
				username = rs.getString("username");
				eventId = rs.getInt("eventID");
				responseMessage = rs.getString("responseMessage");
				timeScanned = rs.getTimestamp("timeScanned");
				
				ScanEntryBean addMe = new ScanEntryBean(userId, username, eventId, responseMessage, timeScanned);
				returnMe.add(addMe);
			}
			stmt.close();
			
		} catch(Exception ex) {
			ex.printStackTrace();
			returnMe = null;
		} finally {
			MySqlDao.cleanup(con);
		}
		
		return returnMe;
	}
	
}
