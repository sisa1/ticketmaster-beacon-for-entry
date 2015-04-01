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
	
	/** CRUD OPERATIONS **/
	// CREATE
	@Override
	public UserBean createUser(UserBean user) {
		String insertCamQuery = "INSERT INTO users (`UserId`, `FirstName`, `LastName`, `Username`, `Password`) VALUES (?, ?, ?, ?, ?)";
		Connection mySqlConnection = null;
		mySqlConnection = MySqlDao.getConnection();
		try {
			PreparedStatement pStatement = mySqlConnection.prepareStatement(insertCamQuery, Statement.RETURN_GENERATED_KEYS);
			pStatement.setInt(1, user.getId());
			pStatement.setString(2, user.getFirstName());
			pStatement.setString(3, user.getLastName());
			pStatement.setString(4, user.getUsername());
			pStatement.setString(5, user.getPassword());
			pStatement.executeUpdate();// INSERT INTO Camera
			
			//Get generated key
			int newId = -1;
			ResultSet generatedKeys = pStatement.getGeneratedKeys();
			if (generatedKeys.next()) {
				newId = (int) generatedKeys.getLong(1);
			}
			user.setId(newId);
			
			return user;
			
		} catch (SQLException e) {
			e.printStackTrace();
			user = null;
			
		} finally {
			MySqlDao.cleanup(mySqlConnection);
		}
		return user;
	}
	
	//READ
	@Override
	public UserBean readUser(int id) {
		Connection mySqlConnection = null;
		mySqlConnection = MySqlDao.getConnection();
		Statement stmt = null;
		
		UserBean userToReturn = null;
		try {
			stmt = mySqlConnection.createStatement();
			String readByIdQuery = "SELECT * FROM users WHERE UserId = ?";
			PreparedStatement pStatement = mySqlConnection.prepareStatement(readByIdQuery);
			pStatement.setInt(1, id);
			ResultSet rs = pStatement.executeQuery();
			
			// Iterate ResultSet and Initialize Camera list
			while(rs.next()) {
				userToReturn = new UserBean();
				userToReturn.setId(Integer.parseInt(rs.getString("UserId")));
				userToReturn.setFirstName(rs.getString("FirstName"));
				userToReturn.setLastName(rs.getString("LastName"));
				userToReturn.setUsername(rs.getString("Username"));
				userToReturn.setPassword(rs.getString("Password"));
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
	
	//UPDATE
	@Override
	public UserBean updateUser(int id, UserBean userToUpdate){
		String updateQuery = "UPDATE users SET FirstName = ?, LastName=?, Username=?, Password=? WHERE UserId=?";
		UserBean result = null;
		Connection mySqlConnection = null;
		Statement stmt = null;
		mySqlConnection = MySqlDao.getConnection();
		try {
			PreparedStatement pStatement = mySqlConnection.prepareStatement(updateQuery);
			pStatement.setString(1, userToUpdate.getFirstName());
			pStatement.setString(2, userToUpdate.getLastName());			
			pStatement.setString(3, userToUpdate.getUsername());
			pStatement.setString(4, userToUpdate.getPassword());
			pStatement.setInt(5, id);
			pStatement.executeUpdate();
			result = userToUpdate;
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally{
			cleanup(mySqlConnection, stmt);
		}
		return result;
	}
	
	//DELETE
	@Override
	public UserBean deleteUser(int id){
		Connection con = null;
		Statement stmt = null;
		UserBean result = null;
		UserBean tmp = null;
		con = MySqlDao.getConnection();
		try {
			tmp = readUser(id);
			stmt = con.createStatement();		
			stmt.executeUpdate("DELETE FROM users WHERE UserId = " + id + ";");
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
	
	
	
	/** OTHER METHODS **/
	@Override
	public List<UserBean> getAllUsers() {
		Connection mySqlConnection = null;
		mySqlConnection = MySqlDao.getConnection();
		
		List<UserBean> userList = new ArrayList<UserBean>();
		try {
			mySqlConnection.setAutoCommit(false);
			String selectAllQuery = "SELECT * FROM users";
			PreparedStatement pStatement = mySqlConnection.prepareStatement(selectAllQuery);
			ResultSet rs = pStatement.executeQuery();
			
			// Iterate ResultSet and Initialize Camera list
			while(rs.next()) {
				UserBean userToAddToList;
				userToAddToList = new UserBean();
				userToAddToList.setId(Integer.parseInt(rs.getString("UserId")));
				userToAddToList.setFirstName(rs.getString("FirstName"));
				userToAddToList.setLastName(rs.getString("LastName"));
				userToAddToList.setUsername(rs.getString("Username"));
				userToAddToList.setPassword(rs.getString("Password"));
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
	
	@Override
	public UserBean getUser(String username) {
		Connection mySqlConnection = null;
		mySqlConnection = MySqlDao.getConnection();
		
		UserBean userToReturn = null;
		try {
			mySqlConnection.setAutoCommit(false);
			String selectAllQuery = "SELECT * FROM users WHERE Username = ?";
			PreparedStatement pStatement = mySqlConnection.prepareStatement(selectAllQuery);
			pStatement.setString(1, username);
			
			ResultSet rs = pStatement.executeQuery();
			
			// Iterate ResultSet and Initialize Camera list
			while(rs.next()) {
				userToReturn = new UserBean();
				userToReturn.setId(Integer.parseInt(rs.getString("UserId")));
				userToReturn.setFirstName(rs.getString("FirstName"));
				userToReturn.setLastName(rs.getString("LastName"));
				userToReturn.setUsername(rs.getString("Username"));
				userToReturn.setPassword(rs.getString("Password"));
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
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/beacon_servlet", "root", "");
			stmt = con.createStatement();		
			stmt.executeUpdate("INSERT INTO users VALUES (" + id +", '" + firstName + "', '" + lastName + "', '" + username +  "')");
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
}
