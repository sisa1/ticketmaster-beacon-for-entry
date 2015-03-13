package com.ticketmaster.api.rest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ticketmaster.bean.EventBean;
import com.ticketmaster.bean.RosterEntryBean;
import com.ticketmaster.bean.UserBean;
import com.ticketmaster.dao.EventDao;
import com.ticketmaster.dao.MySqlDaoFactory;
import com.ticketmaster.dao.RosterEntryDao;
import com.ticketmaster.dao.EventTimeDao;

@Path("rest/Roster")
public class RosterRest {
	private static Logger logger = LogManager.getLogManager().getLogger(RosterRest.class.getCanonicalName());
	
	/** CRUD OPERATIONS **/
	// CREATE OR UPDATE by form parameters
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response responseUpdateRoster(@FormParam("id") @DefaultValue("0") int id ,
										@FormParam("userId") int userId,
										@FormParam("eventId") int eventId,
										@FormParam("attendedFlag") boolean attendedFlag) {
		Response response = null;
		RosterEntryDao dao = MySqlDaoFactory.getRosterEntryDAO();
		
		RosterEntryBean rosterToUpdate = new RosterEntryBean();
		EventBean eventInRoster = MySqlDaoFactory.getEventDAO().readEvent(eventId);
		UserBean userInRoster = MySqlDaoFactory.getUserDAO().readUser(userId);
		rosterToUpdate.setEvent(eventInRoster);
		rosterToUpdate.setVisitor(userInRoster);
		rosterToUpdate.setDidAttend(attendedFlag);
		
		/* Id has been provided -> UPDATE the event */
		if(id > 0) {
			rosterToUpdate.setId(id);
			try {
				dao.updateRoster(id, rosterToUpdate);
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
				rosterToUpdate = dao.createTicket(rosterToUpdate);
			} catch (Exception e) {
				//Print stack trace and return a 500 status code
				logger.log(Level.SEVERE, "unexpecting error while creating event", e);
				e.printStackTrace();
				response = Response.status(500).build();
				return response;
			}
		}
		
		// Return updated event
		response = Response.status(200).entity(rosterToUpdate).build();
		return response;
	}
	
	// READ by ID
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response responseGetId(@PathParam("id") int id) {
		RosterEntryDao dao = MySqlDaoFactory.getRosterEntryDAO();
		RosterEntryBean rosterToReturn;
		Response response = null;
		try {
			rosterToReturn = dao.readRoster(id);
			response = Response.status(200).entity(rosterToReturn).build();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "unexpected error while getting event by ID");
			e.printStackTrace();
			response = Response.status(500).build();
			return response;
		}
		
		if(rosterToReturn == null) {
			response = Response.status(404).entity(Collections.emptyList()).build();
		}
		
		return response;
	}
	
	// DELETE by Id
	@DELETE
	public Response responseDeleteEvent(@FormParam("id") int id) {
		RosterEntryDao dao = MySqlDaoFactory.getRosterEntryDAO();
		
		try {
			dao.deleteRoster(id);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "unexpecting error while deleting event", e);
			e.printStackTrace();
			return Response.status(500).build();
		}
		
		// success, send no content 204 HTTP Response
		return Response.status(204).build();
	}
	
	/* No Parameter -> Get all events */
	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public List<RosterEntryBean> responseGetEventsWithRoster() {
		RosterEntryDao dao = MySqlDaoFactory.getRosterEntryDAO();
		List<RosterEntryBean> rosterEntries = null;
		try {
			rosterEntries = dao.getAllRosterEntries();
		} catch (Exception ex) {
			rosterEntries = null;
		}
		return rosterEntries;
	}
	
	// Event ID parameter -> Get roster for event
	@GET
	@Path("/Event/{eventId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<RosterEntryBean> responseGetUsersAtRoster(@PathParam("eventId") @DefaultValue("-1") int eventId) {
		List<RosterEntryBean> completeRoster = new ArrayList<RosterEntryBean>();
		RosterEntryDao dao = MySqlDaoFactory.getRosterEntryDAO();
		completeRoster = dao.getRosterForEvent(eventId);
		return completeRoster;
	}
	
	/* eventId, username parameter -> */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response setUserAttend(@FormParam("eventId") @DefaultValue("-1") int eventId,
								  @FormParam("username") @DefaultValue("") String username) {
		
		RosterEntryDao dao = MySqlDaoFactory.getRosterEntryDAO();
		EventTimeDao eventTimeDao = MySqlDaoFactory.getEventTimeDAO();
		int errorCode = 0;
		boolean didSucceed = false;
		RosterEntryBean entry = dao.readRoster(eventId, username);
			
		//Check for valid entry
		if(entry != null){ //ticket exists
			
			if(!entry.isDidAttend()){ //ticket has not been scanned yet
			
				if(eventTimeDao.compareScanTime(eventId)){ //check if the scan time is in duration of event
					
					didSucceed = true;
				}else{
					errorCode = 3;
				}
			}else{
				errorCode = 2;
			}		
		}else{
			errorCode = 1;
		}
		
		if(didSucceed) {
			EventDao eventDao = MySqlDaoFactory.getEventDAO();
			EventBean currentEvent = eventDao.readEvent(eventId);
			dao.setAttend(eventId, username);
			String response = "Successfully Scanned ticket. Welcome to: " + currentEvent.getName();
			return Response.status(200).entity(response).build();
		}else{

			String response = "Invalid Ticket. ";
			if(errorCode == 1){
				response += "Your ticket was not found.";
			}
			if(errorCode == 2){
				response += "Your ticket has already been scanned.";
			}
			if(errorCode == 3){
				response += "Event has already passed.";
			}
			
			Connection con = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://54.200.138.139:3306/beacon_servlet", "mysql_workbench", "dbadmin");

				String logQuery = "INSERT INTO eventEntryScans (userID, username, eventID, responseMessage) VALUES ((SELECT UserId FROM users WHERE users.Username=?), ?, ?, ?)";
				PreparedStatement pStatementLog = con.prepareStatement(logQuery);
				pStatementLog.setString(1, username);
				pStatementLog.setString(2, username);
				pStatementLog.setInt(3, eventId);
				pStatementLog.setString(4, response);
				pStatementLog.executeUpdate();
				
				pStatementLog.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				if(con != null){
					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			return Response.status(200).entity(response).build();			
		}
		
	}
	
}