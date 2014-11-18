package com.ticketmaster.api.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
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
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getResponse() {
		HelloBean bean = new HelloBean("test", 1);
		return Response.status(200).entity(bean).build();
	}
	
	
}