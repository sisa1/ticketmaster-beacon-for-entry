package com.ticketmaster.dao.myql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ticketmaster.bean.UserBean;
import com.ticketmaster.dao.UserDao;

public class UserDaoImpl extends MySqlDao implements UserDao {
	
	@Override
	public List<UserBean> getAllUsers() {
		Connection mySqlConnection = null;
		mySqlConnection = MySqlDao.getConnection();
		
		List<UserBean> userList = new ArrayList<UserBean>();
		try {
			mySqlConnection.setAutoCommit(false);
			String selectAllQuery = "SELECT * FROM beacon_servlet.users";
			PreparedStatement pStatement = mySqlConnection.prepareStatement(selectAllQuery);
			ResultSet rs = pStatement.executeQuery();
			
			// Iterate ResultSet and Initialize Camera list
			while(rs.next()) {
				UserBean userToAddToList;
				userToAddToList = new UserBean();
				userToAddToList.setId(Integer.parseInt(rs.getString("Id")));
				userToAddToList.setFirstName(rs.getString("FirstName"));
				userToAddToList.setLastName(rs.getString("LastName"));
				userToAddToList.setUsername(rs.getString("Username"));
				userList.add(userToAddToList);
			}
			
			pStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			userList = null;
		} finally {
			// close the connection even if get was unsuccessful
			MySqlDao.cleanup(mySqlConnection);
		}
		return userList;
	}
	
	public UserBean getUser(String username) {
		Connection mySqlConnection = null;
		mySqlConnection = MySqlDao.getConnection();
		
		UserBean userToReturn = null;
		try {
			mySqlConnection.setAutoCommit(false);
			String selectAllQuery = "SELECT ? FROM beacon_servlet.users";
			PreparedStatement pStatement = mySqlConnection.prepareStatement(selectAllQuery);
			pStatement.setString(1, username);
			
			ResultSet rs = pStatement.executeQuery();
			
			// Iterate ResultSet and Initialize Camera list
			while(rs.next()) {
				userToReturn = new UserBean();
				userToReturn.setId(Integer.parseInt(rs.getString("Id")));
				userToReturn.setFirstName(rs.getString("FirstName"));
				userToReturn.setLastName(rs.getString("LastName"));
				userToReturn.setUsername(rs.getString("Username"));
			}
			
			pStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			userToReturn = null;
		} finally {
			// close the connection even if get was unsuccessful
			MySqlDao.cleanup(mySqlConnection);
		}
		return userToReturn;
	}
	
	// Inserts a user into the database with the specified parameters
	@Override
	public UserBean createUser(int id, String firstName, String lastName, String username){
		UserBean tmp = null;
		Connection con = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "");
			stmt = con.createStatement();		
			stmt.executeUpdate("INSERT INTO beacon_servlet.users VALUES (" + id +", '" + firstName + "', '" + lastName + "', '" + username +  "')");
			// I should probably fetch the object from the data base to make sure it was added successfully
			tmp = new UserBean();
			tmp.setFirstName(firstName);
			tmp.setLastName(lastName);
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
	
	public UserBean readUser(int id) {
		Connection mySqlConnection = null;
		mySqlConnection = MySqlDao.getConnection();
		Statement stmt = null;
		
		UserBean userToReturn = null;
		try {
			mySqlConnection.setAutoCommit(false);
			stmt = mySqlConnection.createStatement();
			String selectQuery = "SELECT ? FROM beacon_servlet.users WHERE Id = " + id + ";";
			
			
			ResultSet rs = stmt.executeQuery(selectQuery);
			
			// Iterate ResultSet and Initialize Camera list
			while(rs.next()) {
				userToReturn = new UserBean();
				userToReturn.setId(Integer.parseInt(rs.getString("Id")));
				userToReturn.setFirstName(rs.getString("FirstName"));
				userToReturn.setLastName(rs.getString("LastName"));
				userToReturn.setUsername(rs.getString("Username"));
			}
			
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			userToReturn = null;
		} finally {
			// close the connection even if get was unsuccessful
			MySqlDao.cleanup(mySqlConnection);
		}
		return userToReturn;
	}	

	public UserBean updateUser(int id, UserBean userToUpDate){
		UserBean result = null;
		Connection con = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "");
			stmt = con.createStatement();		
			stmt.executeUpdate("UPDATE beacon_servlet.users SET FirstName = '"+ userToUpDate.getFirstName() 
					+ "', LastName = '" + userToUpDate.getLastName() + "', UserName = '" 
					+ userToUpDate.getUsername() +"' WHERE Id = " + id + ";");
			result = userToUpDate;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			cleanup(con, stmt);
		}
		return result;
	}
	
	public UserBean deleteUser(int id){
		Connection con = null;
		Statement stmt = null;
		UserBean result = null;
		UserBean tmp = null;
		try {
			tmp = readUser(id);
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "");
			stmt = con.createStatement();		
			stmt.executeUpdate("DELETE FROM beacon_servlet.users WHERE Id = " + id + ";");
			result = tmp;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			cleanup(con, stmt);
		}
		return result;		
	}
}
