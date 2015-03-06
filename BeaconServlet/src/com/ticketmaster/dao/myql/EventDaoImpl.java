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
import com.ticketmaster.dao.EventDao;

public class EventDaoImpl extends MySqlDao implements EventDao {
	public List<EventBean> getAllEvents(){
		Connection con = null;
		List<EventBean> result = new ArrayList<EventBean>();
		con = MySqlDao.getConnection();
		
		try {
			String selectAllQuery = "SELECT * FROM events";
			PreparedStatement pStatement = con.prepareStatement(selectAllQuery);
			ResultSet rs = pStatement.executeQuery();
			
			// Iterate ResultSet and Initialize Camera list
			while(rs.next()) {
				EventBean eventToAddToList;
				eventToAddToList = new EventBean();
				eventToAddToList.setId(Integer.parseInt(rs.getString("EventId")));
				eventToAddToList.setName(rs.getString("EventName"));
				result.add(eventToAddToList);
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
	public EventBean getEvent(String name){
		Connection mySqlConnection = null;
		mySqlConnection = MySqlDao.getConnection();
		Statement stmt = null;
		EventBean result = null;
		
		try {
			mySqlConnection.setAutoCommit(false);
			stmt = mySqlConnection.createStatement();
			String selectQuery = "SELECT * FROM events WHERE EventName = ?";
			
			PreparedStatement pStatement = mySqlConnection.prepareStatement(selectQuery);
			pStatement.setString(1, name);
			ResultSet rs = pStatement.executeQuery();
			
			// Iterate ResultSet and Initialize Camera list
			while(rs.next()) {
				result = new EventBean();
				result.setId(Integer.parseInt(rs.getString("EventId")));
				result.setName(rs.getString("EventName"));
			}
			
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			result = null;
		} finally {
			// close the connection even if get was unsuccessful
			MySqlDao.cleanup(mySqlConnection);
		}
		return result;
		
	}
	public EventBean readEvent(int id){
		Connection mySqlConnection = null;
		mySqlConnection = MySqlDao.getConnection();
		Statement stmt = null;
		EventBean result = null;
		
		try {
			mySqlConnection.setAutoCommit(false);
			stmt = mySqlConnection.createStatement();
			String selectQuery = "SELECT * FROM events WHERE EventId = ?";
			
			PreparedStatement pStatement = mySqlConnection.prepareStatement(selectQuery);
			pStatement.setInt(1, id);
			ResultSet rs = pStatement.executeQuery();
			
			// Iterate ResultSet and Initialize Camera list
			while(rs.next()) {
				result = new EventBean();
				result.setId(Integer.parseInt(rs.getString("EventId")));
				result.setName(rs.getString("EventName"));

			}
			
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			result = null;
		} finally {
			// close the connection even if get was unsuccessful
			MySqlDao.cleanup(mySqlConnection);
		}
		return result;		
	}
	public EventBean createEvent(String name){
		EventBean result = null;
		Connection con = null;
		Statement stmt = null;
		con = MySqlDao.getConnection();
		try {

			stmt = con.createStatement();	
			stmt.executeUpdate("INSERT INTO events (EventName) VALUES ('" + name +  "')", 1);
			ResultSet rs = stmt.getGeneratedKeys();
			
			Statement stmt2 = con.createStatement();
			while(rs.next()){
				ResultSet rs2 = stmt2.executeQuery("SELECT * FROM events WHERE EventId = " + rs.getInt(1));
				while(rs2.next()){
					result = new EventBean();
					result.setId(rs2.getInt("EventId"));
					result.setName(rs2.getString("EventName"));
				}
			}
			
			stmt2.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			cleanup(con, stmt);		
		}
		return result;	
	}
	/*public EventBean deleteEvent(int id){
		
	}*/

}
