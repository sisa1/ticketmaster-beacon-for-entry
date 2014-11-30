package com.ticketmaster.mysqldao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlDAO{
	
	// method to get connection to Mysql server
	protected static Connection getConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "");
			return con;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		
	}
	
	// Bean for User objects
	public static class userBean{
		int ID;
		String FirstName;
		String LastName;
		String Username;
		String Password;
		
		int getID(){
			return ID;
		}
		
		String getFirstName(){
			return FirstName;
		}
		
		//Constructor for userBean
		public userBean(int aID, String aFirstName, String aLastName, String aUsername, String aPassword){
			ID = aID;
			FirstName = aFirstName;
			LastName = aLastName;
			Username = aUsername;
			Password = aPassword;			
		}
	}
	
	// Returns a bean of the user that has the specified ID
	static userBean readUser(int id){
		int ID = 0;
		String FirstName = null;
		String LastName = null;
		String Username = null;
		String Password = null;
		userBean tmp = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "");
			Statement stmt = con.createStatement();				
			ResultSet rs = stmt.executeQuery("SELECT * FROM user WHERE id = " + id);
			if(rs.next()){
				ID = rs.getInt("id");
				FirstName = rs.getString("firstName");
				LastName = rs.getString("lastName");
				Username = rs.getString("username");
				Password = rs.getString("password");
			}
			else{
				// user not found in database
				return null;
			}		
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		tmp = new userBean(ID, FirstName, LastName, Username, Password);
		return tmp;
	}
	
	// Inserts a user into the database with the specified parameters
	static userBean createUser(int id,String firstName, String lastName, String username, String password){
		userBean tmp = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "");
			Statement stmt = con.createStatement();		
			stmt.executeUpdate("INSERT INTO user VALUES (" + id +", " + firstName + ", " + lastName + ", " + username + ", " + password + ")");
			tmp = new userBean(id, firstName, lastName, username, password);
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
	
}
