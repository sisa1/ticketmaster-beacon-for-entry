package com.ticketmaster.bean;

import java.io.Serializable;

//Bean for User objects
public class UserBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	int Id;
	String FirstName;
	String LastName;
	String Username;
	String Password;
	
	//Constructor for userBean
	public UserBean (int aID, String aFirstName, String aLastName, String aUsername, String aPassword){
		Id = aID;
		FirstName = aFirstName;
		LastName = aLastName;
		Username = aUsername;
		Password = aPassword;			
	}

	public UserBean() {
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}
	
	public int getId(){
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}
	
	public String getFirstName(){
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
}