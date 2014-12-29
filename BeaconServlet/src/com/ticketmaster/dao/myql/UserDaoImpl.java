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
	
	// Inserts a user into the database with the specified parameters
	@Override
	public UserBean createUser(int id,String firstName, String lastName, String username, String password){
		UserBean tmp = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "");
			Statement stmt = con.createStatement();		
			stmt.executeUpdate("INSERT INTO user VALUES (" + id +", " + firstName + ", " + lastName + ", " + username + ", " + password + ")");
			tmp = new UserBean(id, firstName, lastName, username, password);
			stmt.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return tmp;
	}

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
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			userList = null;
		} finally {
			// close the connection even if get was unsuccessful
			MySqlDao.cleanup(mySqlConnection);
		}
		return userList;
		
		/* FOR USE WITH LOCALHOST
		List<UserBean> li = new ArrayList<UserBean>();
		UserBean u1 = new UserBean(0, "fname", "lname", "user", "pass");
		UserBean u2 = new UserBean(1, "john", "doe", "jdoe", "pass");
		UserBean u3 = new UserBean(2, "adam", "apple", "apple", "pass");
		UserBean u4 = new UserBean(3, "eve", "steve", "eva", "pass");
		li.add(u1);
		li.add(u2);
		li.add(u3);
		li.add(u4);
		return li;
		*/
	}
}
