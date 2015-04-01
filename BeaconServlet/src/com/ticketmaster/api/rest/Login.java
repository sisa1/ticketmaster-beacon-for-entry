package com.ticketmaster.api.rest;

import java.io.Serializable;
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
	
	public class LoginResponse implements Serializable {
		int userId;
		String loginMessage;
		
		public LoginResponse(int usrId, String loginMsg) {
			userId = usrId;
			loginMessage = loginMsg;
		}
		
		public int getUserId() {
			return userId;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
		public String getLoginMessage() {
			return loginMessage;
		}
		public void setLoginMessage(String loginMessage) {
			this.loginMessage = loginMessage;
		}
		
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response getResponse(	@FormParam("username")@DefaultValue("") String strUsername ,
									@FormParam("password")@DefaultValue("") String strPassword) {
		
		UserBean selectedUser = null;
		
		try {
	        UserDao userDao = MySqlDaoFactory.getUserDAO();
	        selectedUser = userDao.getUser(strUsername);
	        
	        if(selectedUser == null) {
	        	LoginResponse responseObject = new LoginResponse(-1, "Invalid Login");
	        	return Response.status(200).entity(responseObject).build();
	        }
	        
	        if(selectedUser.getPassword().compareTo(strPassword) != 0) {
	        	LoginResponse responseObject = new LoginResponse(-1, ("Invalid Password"));
	        	return Response.status(200).entity(responseObject).build();
	        }
        } catch (Exception Ex) {
        	return Response.status(500).entity("Error getting user list").build();
        }
		
		LoginResponse responseObject = new LoginResponse(selectedUser.getId(), ("Welcome " + selectedUser.getFirstName() + "!"));
		return Response.status(200).entity(responseObject).build();
	}
}