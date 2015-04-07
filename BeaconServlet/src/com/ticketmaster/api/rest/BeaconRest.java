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

import com.ticketmaster.bean.EventBeaconBean;
import com.ticketmaster.bean.EventBean;
import com.ticketmaster.dao.EventBeaconDao;
import com.ticketmaster.dao.EventDao;
import com.ticketmaster.dao.MySqlDaoFactory;

@Path("rest/Beacon")
public class BeaconRest {
	private static Logger logger = LogManager.getLogManager().getLogger(BeaconRest.class.getCanonicalName());
	
	// CREATE OR UPDATE by form parameters
		@POST
		@Produces(MediaType.APPLICATION_JSON)
		public Response responseUpdateEvent(@FormParam("id") @DefaultValue("0") int id ,
											@FormParam("BeaconName") String beaconName,
											@FormParam("EventID") int eventId,
											@FormParam("UUID") String UUID,
											@FormParam("Major") int major,
											@FormParam("Minor") int minor) {
			Response response = null;
			EventBeaconDao dao = MySqlDaoFactory.getEventBeaconDAO();
			
			EventBeaconBean beaconToUpdate = new EventBeaconBean();
			beaconToUpdate.setName(beaconName);
			beaconToUpdate.setEventId(eventId);
			beaconToUpdate.setMajor(major);
			beaconToUpdate.setUUID(UUID);
			beaconToUpdate.setMinor(minor);
			
			/* Id has been provided -> UPDATE the beacon */
			if(id > 0) {
				beaconToUpdate.setId(id);
				try {
					dao.updateEventBeacon(id, beaconToUpdate);
				} catch (Exception e) {
					//Print stack trace and return a 500 status code
					logger.log(Level.SEVERE, "unexpecting error while creating event", e);
					e.printStackTrace();
					response = Response.status(500).build();
					return response;
				}
				
			/* No valid Id has been provided -> CREATE an beacon*/
			} else {
				try {
					beaconToUpdate = dao.createEventBeacon(beaconToUpdate);
				} catch (Exception e) {
					//Print stack trace and return a 500 status code
					logger.log(Level.SEVERE, "unexpecting error while creating event", e);
					e.printStackTrace();
					response = Response.status(500).build();
					return response;
				}
			}
			
			// Return updated event
			response = Response.status(200).entity(beaconToUpdate).build();
			return response;
		}
		
		// READ by ID
		@GET
		@Path("/{id}")
		@Produces(MediaType.APPLICATION_JSON)
		public Response responseGetId(@PathParam("id") int id) {
			EventBeaconDao dao = MySqlDaoFactory.getEventBeaconDAO();
			EventBeaconBean beaconToReturn;
			Response response = null;
			try {
				beaconToReturn = dao.readEventBeacon(id);
				response = Response.status(200).entity(beaconToReturn).build();
			} catch (Exception e) {
				logger.log(Level.SEVERE, "unexpected error while getting event by ID");
				e.printStackTrace();
				response = Response.status(500).build();
				return response;
			}
			
			if(beaconToReturn == null) {
				response = Response.status(404).entity(Collections.emptyList()).build();
			}
			
			return response;
		}
		
		/* READ by UUID
		@GET
		@Path("/{uuid}")
		@Produces(MediaType.APPLICATION_JSON)
		public Response responseGetUUID(@PathParam("uuid") String uuid) {
			EventBeaconDao dao = MySqlDaoFactory.getEventBeaconDAO();
			EventBeaconBean beaconToReturn;
			Response response = null;
			try {
				beaconToReturn = dao.readEventBeacon(uuid);
				response = Response.status(200).entity(beaconToReturn).build();
			} catch (Exception e) {
				logger.log(Level.SEVERE, "unexpected error while getting event by UUID");
				e.printStackTrace();
				response = Response.status(500).build();
				return response;
			}
			
			if(beaconToReturn == null) {
				response = Response.status(404).entity(Collections.emptyList()).build();
			}
			
			return response;
		}*/
		
		// GET ALL BEACONS FOR EVENT
		/* Not sure if this works
		@GET
		@Path("/{eventId}")
		@Produces(MediaType.APPLICATION_JSON)
		public Response responseGetEventId(@PathParam("eventId") int eventId) {
			EventBeaconDao dao = MySqlDaoFactory.getEventBeaconDAO();
			List<EventBeaconBean> beaconToReturn;
			Response response = null;
			try {
				beaconToReturn = dao.getBeaconsForEvent(eventId);
				response = Response.status(200).entity(beaconToReturn).build();
			} catch (Exception e) {
				logger.log(Level.SEVERE, "unexpected error while getting event by ID");
				e.printStackTrace();
				response = Response.status(500).build();
				return response;
			}
			
			if(beaconToReturn == null) {
				response = Response.status(404).entity(Collections.emptyList()).build();
			}
			
			return response;
		}
		*/
		
		// DELETE by Id
		@DELETE
		public Response responseDeleteEvent(@FormParam("id") int id) {
			EventBeaconDao dao = MySqlDaoFactory.getEventBeaconDAO();
			
			try {
				dao.deleteEventBeacon(id);
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
		public List<EventBeaconBean> responseGetAll() {
			EventBeaconDao dao = MySqlDaoFactory.getEventBeaconDAO();
			List<EventBeaconBean> beacons = null;
			try {
				beacons = dao.getAllEventBeacons();
			} catch (Exception ex) {
				beacons = null;
			}
			return beacons;
		}
		

}
