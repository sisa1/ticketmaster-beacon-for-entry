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

import java.sql.Timestamp;

import com.ticketmaster.bean.EventBean;
import com.ticketmaster.bean.EventTimeBean;
import com.ticketmaster.dao.EventDao;
import com.ticketmaster.dao.EventTimeDao;
import com.ticketmaster.dao.MySqlDaoFactory;

@Path("rest/EventTime")

public class EventTimeRest {
	private static Logger logger = LogManager.getLogManager().getLogger(EventRest.class.getCanonicalName());
	
	// Create or update event time.
	// takes a string of JDBC timestamp format (yyyy-mm-dd hh:mm:ss[.f...])
	// if operation is 0, update. else create new eventtime.
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response responseUpdateEvent(@FormParam("id") @DefaultValue("0") int id ,
										@FormParam("startTime") String startTime,
										@FormParam("endTime") String endTime,
										@FormParam("operation") int operation) {
		Response response = null;
		EventTimeDao dao = MySqlDaoFactory.getEventTimeDAO();
		EventTimeBean result;
	
		/* Id has been provided -> UPDATE the event */
		if(operation == 0) {
			try {
				Timestamp start = Timestamp.valueOf(startTime);
				Timestamp end = Timestamp.valueOf(endTime);
				result = dao.updateEventTime(id, start, end);
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
				Timestamp start = Timestamp.valueOf(startTime);
				Timestamp end = Timestamp.valueOf(endTime);
				result = dao.createEventTime(id, start, end);
			} catch (Exception e) {
				//Print stack trace and return a 500 status code
				logger.log(Level.SEVERE, "unexpecting error while creating event", e);
				e.printStackTrace();
				response = Response.status(500).build();
				return response;
			}
		}
		
		// Return updated event
		response = Response.status(200).entity(result).build();
		return response;
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response responseGetId(@PathParam("id") int id) {
		EventTimeDao dao = MySqlDaoFactory.getEventTimeDAO();
		EventTimeBean eventToReturn;
		Response response = null;
		try {
			eventToReturn = dao.readEventTime(id);
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
		EventTimeDao dao = MySqlDaoFactory.getEventTimeDAO();
		
		try {
			dao.deleteEventTime(id);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "unexpecting error while deleting event", e);
			e.printStackTrace();
			return Response.status(500).build();
		}
		
		// success, send no content 204 HTTP Response
		return Response.status(204).build();
	}

}
