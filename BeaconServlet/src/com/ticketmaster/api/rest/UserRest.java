package com.ticketmaster.api.rest;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ticketmaster.bean.UserBean;
import com.ticketmaster.dao.MySqlDaoFactory;
import com.ticketmaster.dao.UserDao;

@Path("rest/User")
public class UserRest {
	private static Logger logger = LogManager.getLogManager().getLogger(UserRest.class.getCanonicalName());
	
	//Return list of all users (no path parameter)
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserBean> responseGetAll() {
		UserDao dao = MySqlDaoFactory.getUserDAO();
		List<UserBean> users = null;
		try {
			users = dao.getAllUsers();
		} catch (Exception ex) {
			users = null;
		}
		return users;
	}
	
	//Return user at {id}
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response responseGetUserAtID(@PathParam("id") @DefaultValue("0") int id) {
		UserDao dao = MySqlDaoFactory.getUserDAO();
		UserBean userToReturn = null;
		Response response = null;
		
		try {
			userToReturn = dao.readUser(id);
			response = Response.status(200).entity(userToReturn).build();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "unexpected error while getting user by ID");
			e.printStackTrace();
			response = Response.status(500).build();
			return response;
		}
		
		if(userToReturn == null) {
			response = Response.status(404).entity(Collections.emptyList()).build();
		}
		return response;
	}
	
	// Update or create a camera
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response responseUpdateCam(	@FormParam("id") @DefaultValue("0") int id ,
										@FormParam("firstName") String firstName,
										@FormParam("lastName") String lastName,
										@FormParam("username") String username,
										@FormParam("password") String password) {
		Response response = null;
		UserDao dao = MySqlDaoFactory.getUserDAO();
		
		UserBean userToUpdate = new UserBean();
		
		userToUpdate.setFirstName(firstName);
		userToUpdate.setLastName(lastName);
		userToUpdate.setUsername(username);
		userToUpdate.setPassword(password);
		
		/* Id has been provided -> UPDATE the user */
		if(id > 0) {
			userToUpdate.setId(id);
			try {
				dao.updateUser(id, userToUpdate);
			} catch (Exception ex) {
				ex.printStackTrace();
				userToUpdate = null;
			}
			
		/* No valid Id has been provided -> CREATE a camera*/
		} else {
			try {
				userToUpdate = dao.createUser(userToUpdate);
			} catch (Exception e) {
				//Print stack trace and return a 500 status code
				logger.log(Level.SEVERE, "unexpecting error while creating user", e);
				e.printStackTrace();
				response = Response.status(500).build();
				return response;
			}
		}
		
		// Return updated camera
		response = Response.status(200).entity(userToUpdate).build();
		return response;
	}
	
	@DELETE
	public Response responseDeleteCam(@FormParam("id") int id) {
		UserDao dao = MySqlDaoFactory.getUserDAO();
		
		try {
			dao.deleteUser(id);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "unexpecting error while deleting user", e);
			e.printStackTrace();
			return Response.status(500).build();
		}
		
		// success, send no content 204 HTTP Response
		return Response.status(204).build();
	}	
}