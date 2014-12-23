package com.ticketmaster.api.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ticketmaster.bean.UserBean;
import com.ticketmaster.dao.MySqlDaoFactory;
import com.ticketmaster.dao.UserDao;

@Path("rest/User")
public class UserRest {
	
	// No Parameter -> Get all
	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserBean> responseGetAll() {
		UserDao dao = MySqlDaoFactory.getUserDAO();
		List<UserBean> users = null;
		try {
			users = dao.getAllUsers();
		} catch (Exception ex) {
			users = null;
		}
		if(users == null) {
			
		}
		return users;
	}
	
	// Integer Parameter -> Get at ID
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public UserBean responseGetId(@PathParam("id") int id) {
		return null;
	}
}