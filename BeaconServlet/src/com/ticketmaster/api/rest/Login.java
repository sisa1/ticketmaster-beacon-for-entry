package com.ticketmaster.api.rest;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("rest/Login")
public class Login {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response getResponse(	@FormParam("username")@DefaultValue("") String strUsername ,
									@FormParam("password")@DefaultValue("") String strPassword) {
		if(strUsername.compareTo("Username") == 0 && strPassword.compareTo("Password") == 0) {
			String apiKey = strUsername.toLowerCase()+strPassword.toLowerCase();
			return Response.status(200).entity(apiKey).build();
		}	
		return Response.status(200).entity("Invalid Login").build();
	}
}