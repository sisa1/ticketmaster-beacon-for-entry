package com.ticketmaster.dao.myql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.ticketmaster.bean.EventBeaconBean;
import com.ticketmaster.bean.EventBean;
import com.ticketmaster.bean.ScanEntryBean;
import com.ticketmaster.dao.EventBeaconDao;


public class EventBeaconDaoImpl implements EventBeaconDao {
	/** Standard - CRUD operation **/
	public EventBeaconBean createEventBeacon(EventBeaconBean eventBeacon) {
		String insertQuery = "INSERT INTO eventBeacons (`BeaconName`, `EventId`, `UUID`, `Major`, `Minor`) VALUES (?, ?, ?, ?, ?)";
		Connection mySqlConnection = null;
		mySqlConnection = MySqlDao.getConnection();
		try {
			PreparedStatement pStatement = mySqlConnection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
			pStatement.setString(1, eventBeacon.getName());
			pStatement.setInt(2, eventBeacon.getEventId());
			pStatement.setString(3, eventBeacon.getUUID());
			pStatement.setInt(4, eventBeacon.getMajor());
			pStatement.setInt(5, eventBeacon.getMinor());
			pStatement.executeUpdate();// INSERT INTO Camera
			
			//Get generated key
			int newId = -1;
			ResultSet generatedKeys = pStatement.getGeneratedKeys();
			if (generatedKeys.next()) {
				newId = (int) generatedKeys.getLong(1);
			}
			eventBeacon.setId(newId);
			
			return eventBeacon;
			
		} catch (SQLException e) {
			e.printStackTrace();
			eventBeacon = null;
			
		} finally {
			MySqlDao.cleanup(mySqlConnection);
		}
		return eventBeacon;	
	}
	
	//read by BeaconId
	public EventBeaconBean readEventBeacon(int id) {
		Connection mySqlConnection = null;
		mySqlConnection = MySqlDao.getConnection();
		
		EventBeaconBean result = null;
		try {

			String readByIdQuery = "SELECT * FROM eventBeacons WHERE BeaconId = ?";
			PreparedStatement pStatement = mySqlConnection.prepareStatement(readByIdQuery);
			pStatement.setInt(1, id);
			ResultSet rs = pStatement.executeQuery();
			
			// Iterate ResultSet and Initialize Camera list
			while(rs.next()) {
				result = new EventBeaconBean();
				result.setId(rs.getInt("BeaconId"));
				result.setName(rs.getString("BeaconName"));
				result.setEventId(rs.getInt("EventId"));
				result.setUUID(rs.getString("UUID"));
				result.setMajor(rs.getInt("Major"));
				result.setMinor(rs.getInt("Minor"));
			}
			pStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			result = null;
		} finally {
			// close the connection even if get was unsuccessful
			MySqlDao.cleanup(mySqlConnection);
		}
		return result;
	}
	
	public EventBeaconBean updateEventBeacon(int id, EventBeaconBean eventBeacon) {
		String updateQuery = "UPDATE eventBeacons SET BeaconName = ?, EventId = ?, UUID = ?, Major = ?, Minor = ?  WHERE BeaconId=?";
		EventBeaconBean result = null;
		Connection mySqlConnection = null;
		mySqlConnection = MySqlDao.getConnection();
		try {
			PreparedStatement pStatement = mySqlConnection.prepareStatement(updateQuery);
			pStatement.setString(1, eventBeacon.getName());
			pStatement.setInt(2, eventBeacon.getEventId());
			pStatement.setString(3, eventBeacon.getUUID());
			pStatement.setInt(4, eventBeacon.getMajor());
			pStatement.setInt(5, eventBeacon.getMinor());
			pStatement.setInt(6, id);
			pStatement.executeUpdate();
			result = eventBeacon;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			MySqlDao.cleanup(mySqlConnection);
		}
		return result;
	}
	
	public void deleteEventBeacon(int id) {
		Connection mySqlConnection = null;
		String deleteQuery = "DELETE FROM eventBeacons WHERE BeaconId = ?";
		mySqlConnection = MySqlDao.getConnection();
		try {
			PreparedStatement pStatement = mySqlConnection.prepareStatement(deleteQuery);
			pStatement.setInt(1, id);
			pStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			MySqlDao.cleanup(mySqlConnection);
		}	
	}
	
	public EventBeaconBean readEventBeacon(String UUID){
		Connection mySqlConnection = null;
		mySqlConnection = MySqlDao.getConnection();
		
		EventBeaconBean result = null;
		try {

			String readByIdQuery = "SELECT * FROM eventBeacons WHERE UUID = ?";
			PreparedStatement pStatement = mySqlConnection.prepareStatement(readByIdQuery);
			pStatement.setString(1, UUID);
			ResultSet rs = pStatement.executeQuery();
			
			// Iterate ResultSet and Initialize Camera list
			while(rs.next()) {
				result = new EventBeaconBean();
				result.setId(rs.getInt("BeaconId"));
				result.setName(rs.getString("BeaconName"));
				result.setEventId(rs.getInt("EventId"));
				result.setUUID(rs.getString("UUID"));
				result.setMajor(rs.getInt("Major"));
				result.setMinor(rs.getInt("Minor"));
			}
			pStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			result = null;
		} finally {
			// close the connection even if get was unsuccessful
			MySqlDao.cleanup(mySqlConnection);
		}
		return result;
	}
	
	public List<EventBeaconBean> getBeaconsForEvent(int eventId){
		Connection con = null;
		Statement stmt = null;
		con = MySqlDao.getConnection();
		
		List<EventBeaconBean> returnMe = new ArrayList<EventBeaconBean>();
		
		int BeaconId;
		String BeaconName;
		int EventId;
		String UUID;
		int Major;
		int Minor;
	
		try {
			stmt = con.createStatement();
			String query = "SELECT * FROM eventBeacons WHERE EventId = " + eventId;
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				BeaconId = rs.getInt("BeaconId");
				BeaconName = rs.getString("BeaconName");
				EventId = rs.getInt("EventID");
				UUID = rs.getString("UUID");
				Major = rs.getInt("Major");
				Minor = rs.getInt("Minor");
							
				EventBeaconBean addMe = new EventBeaconBean(BeaconId, BeaconName, EventId, UUID, Major, Minor);
				returnMe.add(addMe);
			}
			stmt.close();
			
		} catch(Exception ex) {
			returnMe = null;
		}
		
		return returnMe;
	}
	
	public List<EventBeaconBean> getAllEventBeacons(){
		Connection con = null;
		Statement stmt = null;
		con = MySqlDao.getConnection();
		
		List<EventBeaconBean> returnMe = new ArrayList<EventBeaconBean>();
		
		int BeaconId;
		String BeaconName;
		int EventId;
		String UUID;
		int Major;
		int Minor;
	
		try {
			stmt = con.createStatement();
			String query = "SELECT * FROM eventBeacons";
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				BeaconId = rs.getInt("BeaconId");
				BeaconName = rs.getString("BeaconName");
				EventId = rs.getInt("EventID");
				UUID = rs.getString("UUID");
				Major = rs.getInt("Major");
				Minor = rs.getInt("Minor");
							
				EventBeaconBean addMe = new EventBeaconBean(BeaconId, BeaconName, EventId, UUID, Major, Minor);
				returnMe.add(addMe);
			}
			stmt.close();
			
		} catch(Exception ex) {
			returnMe = null;
		}
		
		return returnMe;

	}
}
