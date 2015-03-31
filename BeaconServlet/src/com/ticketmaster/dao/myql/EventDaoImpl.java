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
import com.ticketmaster.bean.UserBean;
import com.ticketmaster.dao.EventDao;

public class EventDaoImpl extends MySqlDao implements EventDao {
	
	/** CRUD OPERATIONS **/
	@Override
	public EventBean createEvent(EventBean event) {
		String insertEventQuery = "INSERT INTO events (`EventName`) VALUES (?)";
		Connection mySqlConnection = null;
		mySqlConnection = MySqlDao.getConnection();
		try {
			PreparedStatement pStatement = mySqlConnection.prepareStatement(insertEventQuery, Statement.RETURN_GENERATED_KEYS);
			pStatement.setString(1, event.getName());
			pStatement.executeUpdate();// INSERT INTO Camera
			
			//Get generated key
			int newId = -1;
			ResultSet generatedKeys = pStatement.getGeneratedKeys();
			if (generatedKeys.next()) {
				newId = (int) generatedKeys.getLong(1);
			}
			event.setId(newId);
			
			return event;
			
		} catch (SQLException e) {
			e.printStackTrace();
			event = null;
			
		} finally {
			MySqlDao.cleanup(mySqlConnection);
		}
		return event;
	}
	
	@Override
	public EventBean readEvent(int id){
		Connection mySqlConnection = null;
		mySqlConnection = MySqlDao.getConnection();
		
		EventBean eventToReturn = null;
		try {

			String readByIdQuery = "SELECT * FROM events WHERE EventId = ?";
			PreparedStatement pStatement = mySqlConnection.prepareStatement(readByIdQuery);
			pStatement.setInt(1, id);
			ResultSet rs = pStatement.executeQuery();
			
			// Iterate ResultSet and Initialize Camera list
			while(rs.next()) {
				eventToReturn = new EventBean();
				eventToReturn.setId(rs.getInt("EventId"));
				eventToReturn.setName((rs.getString("EventName")));
			}
			pStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			eventToReturn = null;
		} finally {
			// close the connection even if get was unsuccessful
			MySqlDao.cleanup(mySqlConnection);
		}
		return eventToReturn;
	}
	
	@Override
	public EventBean updateEvent(int id, EventBean eventToUpdate) {
		String updateQuery = "UPDATE events SET EventName = ? WHERE EventId=?";
		EventBean result = null;
		Connection mySqlConnection = null;
		Statement stmt = null;
		mySqlConnection = MySqlDao.getConnection();
		try {
			PreparedStatement pStatement = mySqlConnection.prepareStatement(updateQuery);
			pStatement.setString(1, eventToUpdate.getName());
			pStatement.setInt(2, id);
			pStatement.executeUpdate();
			result = eventToUpdate;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			cleanup(mySqlConnection, stmt);
		}
		return result;
	}
	
	@Override
	public void deleteEvent(int id) {
		Connection mySqlConnection = null;
		String deleteQuery = "DELETE FROM events WHERE EventId = ?";
		mySqlConnection = MySqlDao.getConnection();
		try {
			PreparedStatement pStatement = mySqlConnection.prepareStatement(deleteQuery);
			pStatement.setInt(1, id);
			pStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			cleanup(mySqlConnection);
		}
	}
	
	
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


}
