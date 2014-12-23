package com.ticketmaster.dao.myql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlDao{
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
	
	protected static void cleanup(Connection c) {
		try {
			if(c != null) {
				c.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
