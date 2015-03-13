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
	
	/** CRUD OPERATIONS **/
	@Override
	public RosterEntryBean createTicket(RosterEntryBean ticket) {
		String insertBeanQuery = "INSERT INTO eventRoster (`UserId`, `EventId`, `AttendedFlag`) VALUES (?, ?, 0)";
		Connection mySqlConnection = null;
		mySqlConnection = MySqlDao.getConnection();
		RosterEntryBean result = null;
		try {
			PreparedStatement pStatement = mySqlConnection.prepareStatement(insertBeanQuery, Statement.RETURN_GENERATED_KEYS);
			pStatement.setInt(1, ticket.getVisitor().getId());
			pStatement.setInt(2, ticket.getEvent().getId());
			pStatement.executeUpdate();
			
			//Get generated key
			int newId = -1;
			ResultSet generatedKeys = pStatement.getGeneratedKeys();
			if (generatedKeys.next()) {
				newId = (int) generatedKeys.getLong(1);
			}
			
			//result.setId(newId);
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			result = null;
		} finally {
			MySqlDao.cleanup(mySqlConnection);
		}
		return result;
	}
	
	public RosterEntryBean readRoster(int id) {
		Connection mySqlConnection = null;
		mySqlConnection = MySqlDao.getConnection();
		Statement stmt = null;
		
		RosterEntryBean rosterToReturn = null;
		try {
			stmt = mySqlConnection.createStatement();
			String readByIdQuery = "SELECT * FROM eventRoster, users, events WHERE EntryId=? AND eventRoster.UserId=users.UserId AND eventRoster.EventId=events.EventId";
			PreparedStatement pStatement = mySqlConnection.prepareStatement(readByIdQuery);
			pStatement.setInt(1, id);
			ResultSet rs = pStatement.executeQuery();
			
			EventBean eventToReturn = null;
			UserBean userToReturn = null;
			
			// Iterate ResultSet and Initialize Roster Entry Bean
			while(rs.next()) {
				userToReturn = new UserBean();
				userToReturn.setId(rs.getInt("UserId"));
				userToReturn.setFirstName(rs.getString("FirstName"));
				userToReturn.setLastName(rs.getString("LastName"));
				userToReturn.setUsername(rs.getString("Username"));
				
				eventToReturn = new EventBean();
				eventToReturn.setId(rs.getInt("EventId"));
				eventToReturn.setName((rs.getString("EventName")));
				
				rosterToReturn = new RosterEntryBean();
				rosterToReturn.setId(id);
				rosterToReturn.setVisitor(userToReturn);
				rosterToReturn.setEvent(eventToReturn);
				rosterToReturn.setDidAttend(rs.getBoolean("AttendedFlag"));
			}
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			rosterToReturn = null;
		} finally {
			// close the connection even if get was unsuccessful
			MySqlDao.cleanup(mySqlConnection);
		}
		return rosterToReturn;
	}

	@Override
	public RosterEntryBean updateRoster(int id, RosterEntryBean ticketToUpdate) {
		String updateQuery = "UPDATE eventRoster SET UserId=?, EventId=?, AttendedFlag=? WHERE EntryId=?";
		RosterEntryBean result = null;
		Connection mySqlConnection = null;
		Statement stmt = null;
		mySqlConnection = MySqlDao.getConnection();
		try {
			PreparedStatement pStatement = mySqlConnection.prepareStatement(updateQuery);
			pStatement.setInt(1, ticketToUpdate.getVisitor().getId());
			pStatement.setInt(2, ticketToUpdate.getEvent().getId());
			pStatement.setBoolean(3, ticketToUpdate.isDidAttend());
			pStatement.setInt(4, id);
			pStatement.executeUpdate();
			result = ticketToUpdate;
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally{
			cleanup(mySqlConnection, stmt);
		}
		return result;
	}
	
	public void deleteRoster(int id) {
		Connection mySqlConnection = null;
		Statement stmt = null;
		String deleteQuery = "DELETE FROM eventRoster WHERE EntryId=?";
		mySqlConnection = MySqlDao.getConnection();
		try {
			PreparedStatement pStatement = mySqlConnection.prepareStatement(deleteQuery);
			pStatement.setInt(1, id);
			pStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			cleanup(mySqlConnection, stmt);
		}
	}
	
	public List<RosterEntryBean> getAllRosterEntries() {
		Connection con = null;
		List<RosterEntryBean> result = new ArrayList<RosterEntryBean>();
		con = MySqlDao.getConnection();
		
		try {
			String selectAllQuery = "SELECT * FROM eventRoster, users, events WHERE eventRoster.UserId=users.UserId AND eventRoster.EventId=events.EventId";
			PreparedStatement pStatement = con.prepareStatement(selectAllQuery);
			ResultSet rs = pStatement.executeQuery();
			
			// Iterate ResultSet and Initialize Camera list
			while(rs.next()) {
				EventBean eventToAddToEntry = new EventBean();
				eventToAddToEntry.setId(Integer.parseInt(rs.getString("EventId")));
				eventToAddToEntry.setName(rs.getString("EventName"));
				
				UserBean userToAddToEntry = new UserBean();
				userToAddToEntry.setId(Integer.parseInt(rs.getString("UserId")));
				userToAddToEntry.setFirstName(rs.getString("FirstName"));
				userToAddToEntry.setLastName(rs.getString("LastName"));
				userToAddToEntry.setUsername(rs.getString("Username"));
				
				boolean userAttendedEntry = rs.getBoolean("AttendedFlag");
				
				RosterEntryBean entry = new RosterEntryBean(eventToAddToEntry, userToAddToEntry, userAttendedEntry);
				entry.setId(rs.getInt("EntryId"));
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
		Connection con = null;
		List<RosterEntryBean> result = new ArrayList<RosterEntryBean>();
		con = MySqlDao.getConnection();
		
		try {
			String selectAllQuery = "SELECT * FROM eventRoster, users, events WHERE eventRoster.UserId=users.UserId AND eventRoster.EventId=events.EventId AND events.EventId = ?";
			PreparedStatement pStatement = con.prepareStatement(selectAllQuery);
			pStatement.setInt(1, eventId);
			ResultSet rs = pStatement.executeQuery();
			
			// Iterate ResultSet and Initialize Camera list
			while(rs.next()) {
				EventBean eventToAddToEntry = new EventBean();
				eventToAddToEntry.setId(Integer.parseInt(rs.getString("EventId")));
				eventToAddToEntry.setName(rs.getString("EventName"));
				
				UserBean userToAddToEntry = new UserBean();
				userToAddToEntry.setId(Integer.parseInt(rs.getString("UserId")));
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
	
	public boolean setAttend(int eventId, String username) {
		//UPDATE `beacon_servlet`.`eventRoster` SET `AttendedFlag`='1' WHERE `Id`='2';
		Connection con = null;
		int affectedRows = 0;
		con = MySqlDao.getConnection();
		try {
			String selectAllQuery = "UPDATE eventRoster SET AttendedFlag=1 WHERE AttendedFlag=0 AND (UserId = (SELECT UserId FROM users WHERE users.Username=?) and EventId = ?)";
			PreparedStatement pStatement = con.prepareStatement(selectAllQuery);
			pStatement.setString(1, username);
			pStatement.setInt(2, eventId);
			affectedRows = pStatement.executeUpdate();
			//affectedRows = pStatement.getUpdateCount();
			pStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// close the connection even if get was unsuccessful
			MySqlDao.cleanup(con);
		}
		
		if(affectedRows > 0) {
			return true;
		}
	
		return false;
	}
	
	// CRUD
	public RosterEntryBean createRoster(String name) {
		/*EventBean tmp = null;
		Connection con = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/beacon_servlet", "root", "");
			stmt = con.createStatement();		
			stmt.executeUpdate("INSERT INTO events VALUES (" + id +", '" + name +  "')");
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
		return tmp;	*/
		return null;
	}
	
	// Returns a roster entry with the specified event id and user id
	public RosterEntryBean readRoster(int eventId, int userId){
		RosterEntryBean result = null;
		Connection con = null;
		con = MySqlDao.getConnection();
		
		try {
			String query = "SELECT * FROM eventRoster, users, events WHERE events.EventId = ? AND users.UserId = ? AND events.EventId = eventRoster.EventId AND users.UserId = eventRoster.UserId";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, eventId);
			stmt.setInt(2, userId);
			ResultSet rs =stmt.executeQuery();
			
			while(rs.next()) {
				EventBean eventToAddToEntry = new EventBean();
				eventToAddToEntry.setId(Integer.parseInt(rs.getString("EventId")));
				eventToAddToEntry.setName(rs.getString("EventName"));
				
				UserBean userToAddToEntry = new UserBean();
				userToAddToEntry.setId(Integer.parseInt(rs.getString("UserId")));
				userToAddToEntry.setFirstName(rs.getString("FirstName"));
				userToAddToEntry.setLastName(rs.getString("LastName"));
				userToAddToEntry.setUsername(rs.getString("Username"));
				
				boolean userAttendedEntry = rs.getBoolean("AttendedFlag");
				result = new RosterEntryBean(eventToAddToEntry, userToAddToEntry, userAttendedEntry);
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
	
	// Returns a roster entry with specified event id and username
	public RosterEntryBean readRoster(int eventId, String username){
		RosterEntryBean result = null;
		Connection con = null;
		con = MySqlDao.getConnection();
		
		try {
			String query = "SELECT * FROM eventRoster, users, events WHERE events.EventId=? AND users.Username=? AND eventRoster.EventId=events.EventId AND eventRoster.UserId=users.UserId";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, eventId);
			stmt.setString(2, username);
			ResultSet rs =stmt.executeQuery();
			
			while(rs.next()) {
				EventBean eventToAddToEntry = new EventBean();
				eventToAddToEntry.setId(Integer.parseInt(rs.getString("EventId")));
				eventToAddToEntry.setName(rs.getString("EventName"));
				
				UserBean userToAddToEntry = new UserBean();
				userToAddToEntry.setId(Integer.parseInt(rs.getString("UserId")));
				userToAddToEntry.setFirstName(rs.getString("FirstName"));
				userToAddToEntry.setLastName(rs.getString("LastName"));
				userToAddToEntry.setUsername(rs.getString("Username"));
				
				boolean userAttendedEntry = rs.getBoolean("AttendedFlag");
				result = new RosterEntryBean(eventToAddToEntry, userToAddToEntry, userAttendedEntry);
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
}
