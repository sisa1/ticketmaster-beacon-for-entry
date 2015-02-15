/*package com.ticketmaster.api.rest;

import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ticketmaster.bean.EventBean;
import com.ticketmaster.dao.EventDao;
import com.ticketmaster.dao.MySqlDaoFactory;

@Path("rest/Event")
public class EventRest {
	
	// No Parameter -> Get all
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<EventBean> responseGetAll() {
		EventDao dao = MySqlDaoFactory.getEventDAO();
		List<EventBean> events = null;
		try {
			events = dao.getAllEvents();
		} catch (Exception ex) {
			events = null;
		}
		return events;
	}
	/*
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response responseInsertEvent(@FormParam("eventId") @DefaultValue("-1") int eventId,
										 @FormParam("eventName") @DefaultValue("") String eventName) {
		EventDao dao = MySqlDaoFactory.getEventDAO();
		try {
			EventBean result = dao.createEvent(eventId, eventName);
			return Response.status(200).entity(result).build();
		} catch(Exception ex) {
			return Response.status(500).entity("Event Dao Exception: " + ex.getMessage()).build();
		}
	}
	*/
	/* Integer Parameter -> Get at ID
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public UserBean responseGetId(@PathParam("id") int id) {
		return null;
	}*
	
}*/