package com.ticketmaster.api.rest;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("rest/HelloRest")
public class HelloRest {
	
	private class HelloBean {
		String message;
		int num;
		public HelloBean(String message, int num) {
			this.message = message;
			this.num = num;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public int getNum() {
			return num;
		}
		public void setNum(int num) {
			this.num = num;
		}
	}
	
	@GET
	@Path("/{message}/{num}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getResponse(@PathParam("message")@DefaultValue("test")String message ,
								@PathParam("num") @DefaultValue("1") int num) {
		HelloBean bean = new HelloBean(message, num);
		return Response.status(200).entity(bean).build();
	}
	
	
}