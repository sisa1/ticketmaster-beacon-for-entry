package com.ticketmaster.api.rest;

import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ticketmaster.bean.UserBean;
import com.ticketmaster.dao.MySqlDaoFactory;
import com.ticketmaster.dao.UserDao;

@Path("rest/Login")
public class Login {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response getResponse(	@FormParam("username")@DefaultValue("") String strUsername ,
									@FormParam("password")@DefaultValue("") String strPassword) {
		
		UserBean userBean = MySqlDaoFactory.getUserDAO().getUser(strUsername);
		boolean didFindUser = false;
		
		try {
	        UserDao userDao = MySqlDaoFactory.getUserDAO();
	        List<UserBean> userList = userDao.getAllUsers();
	        for(int i = 0; i < userList.size(); i++) {
	        	if(userList.get(i).getUsername().compareTo(strUsername) == 0) {
	        		didFindUser = true;
	        	}
	        }
        } catch (Exception Ex) {
        	return Response.status(500).entity("Error getting user list").build();
        }
		
		if(didFindUser) { // DANGEROUS - MUST CHANGE: PLAIN TXT PASS PASSING
			String apiKey = strUsername.toLowerCase()+strPassword.toLowerCase();
			return Response.status(200).entity(apiKey).build();
		}
		
		return Response.status(200).entity("Invalid Login").build();
	}
}