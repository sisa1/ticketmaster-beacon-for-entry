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
			String selectAllQuery = "SELECT * FROM beacon_servlet.events";
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
			String selectQuery = "SELECT ? FROM beacon_servlet.events WHERE EventName = " + name + ";";
			
			
			ResultSet rs = stmt.executeQuery(selectQuery);
			
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
			String selectQuery = "SELECT ? FROM beacon_servlet.events WHERE EventId = " + id + ";";
			
			
			ResultSet rs = stmt.executeQuery(selectQuery);
			
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
	public EventBean createEvent(int id, String name){
		EventBean tmp = null;
		Connection con = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "");
			stmt = con.createStatement();		
			stmt.executeUpdate("INSERT INTO beacon_servlet.events VALUES (" + id +", '" + name +  "')");
			// I should probably fetch the object from the data base to make sure it was added successfully
			tmp = new EventBean();
			tmp.setName(name);
			tmp.setId(id);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			cleanup(con, stmt);		
		}
		return tmp;	
	}
	/*public EventBean deleteEvent(int id){
		
	}*/

}
