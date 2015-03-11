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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ticketmaster.bean.EventBean;
import com.ticketmaster.dao.EventDao;
import com.ticketmaster.dao.MySqlDaoFactory;

@Path("rest/Event")
public class EventRest {
	private static Logger logger = LogManager.getLogManager().getLogger(EventRest.class.getCanonicalName());
	
	/** CRUD OPERATIONS **/
	
	// CREATE OR UPDATE by form parameters
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response responseUpdateEvent(@FormParam("id") @DefaultValue("0") int id ,
										@FormParam("eventName") String eventName) {
		Response response = null;
		EventDao dao = MySqlDaoFactory.getEventDAO();
		
		EventBean eventToUpdate = new EventBean();
		eventToUpdate.setName(eventName);
		
		/* Id has been provided -> UPDATE the event */
		if(id > 0) {
			eventToUpdate.setId(id);
			try {
				dao.updateEvent(id, eventToUpdate);
			} catch (Exception e) {
				//Print stack trace and return a 500 status code
				logger.log(Level.SEVERE, "unexpecting error while creating event", e);
				e.printStackTrace();
				response = Response.status(500).build();
				return response;
			}
			
		/* No valid Id has been provided -> CREATE an event*/
		} else {
			try {
				eventToUpdate = dao.createEvent(eventToUpdate);
			} catch (Exception e) {
				//Print stack trace and return a 500 status code
				logger.log(Level.SEVERE, "unexpecting error while creating event", e);
				e.printStackTrace();
				response = Response.status(500).build();
				return response;
			}
		}
		
		// Return updated event
		response = Response.status(200).entity(eventToUpdate).build();
		return response;
	}
	
	// READ by ID
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response responseGetId(@PathParam("id") int id) {
		EventDao dao = MySqlDaoFactory.getEventDAO();
		EventBean eventToReturn;
		Response response = null;
		try {
			eventToReturn = dao.readEvent(id);
			response = Response.status(200).entity(eventToReturn).build();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "unexpected error while getting event by ID");
			e.printStackTrace();
			response = Response.status(500).build();
			return response;
		}
		
		if(eventToReturn == null) {
			response = Response.status(404).entity(Collections.emptyList()).build();
		}
		
		return response;
	}
	
	// DELETE by Id
	@DELETE
	public Response responseDeleteEvent(@FormParam("id") int id) {
		EventDao dao = MySqlDaoFactory.getEventDAO();
		
		try {
			dao.deleteEvent(id);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "unexpecting error while deleting event", e);
			e.printStackTrace();
			return Response.status(500).build();
		}
		
		// success, send no content 204 HTTP Response
		return Response.status(204).build();
	}
	
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
	
}