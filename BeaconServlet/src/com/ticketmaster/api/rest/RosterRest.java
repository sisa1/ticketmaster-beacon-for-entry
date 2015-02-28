package com.ticketmaster.api.rest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ticketmaster.bean.EventBean;
import com.ticketmaster.bean.RosterEntryBean;
import com.ticketmaster.bean.UserBean;
import com.ticketmaster.dao.EventDao;
import com.ticketmaster.dao.MySqlDaoFactory;
import com.ticketmaster.dao.RosterEntryDao;
import com.ticketmaster.dao.UserDao;
import com.ticketmaster.dao.myql.MySqlDao;

@Path("rest/Roster")
public class RosterRest {
	
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
		
		if(eventId == 100) {
			String response = "Successfully Scanned ticket. Welcome to: Statically Programmed Event";
			return Response.status(200).entity(response).build();
		}
		
		boolean didSucceed = false;
		
		RosterEntryDao dao = MySqlDaoFactory.getRosterEntryDAO();
		didSucceed = dao.setAttend(eventId, username);
		
		
		if(didSucceed) {
			EventDao eventDao = MySqlDaoFactory.getEventDAO();
			EventBean currentEvent = eventDao.readEvent(eventId);
			String response = "Successfully Scanned ticket. Welcome to: " + currentEvent.getName();
			return Response.status(200).entity(response).build();
		} else {
			String response = "Invalid Ticket. ";
			
			
			
			
			
			
			Connection con = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://54.200.138.139:3306/beacon_servlet", "mysql_workbench", "dbadmin");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			/* TODO: get if ticket has already been scanned */
			int affectedRows = 0;
			boolean ticketWasScanned = false;
			try {				
				String selectAllQuery = "SELECT AttendedFlag FROM eventRoster WHERE (UserId = (SELECT UserId FROM users WHERE users.Username=?) and EventId = ?)";
				PreparedStatement pStatement = con.prepareStatement(selectAllQuery);
				pStatement.setString(1, username);
				pStatement.setInt(2, eventId);
				ResultSet rs = pStatement.executeQuery();
				while(rs.next()) {
					ticketWasScanned = rs.getBoolean("AttendedFlag");
				}
				pStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if(ticketWasScanned) {
				response += " Your ticket has already been scanned";
				
				
				String logQuery = "INSERT INTO analyticsRoster (userID, eventID, errorMsg) VALUES ((SELECT UserId FROM users WHERE users.Username=?), ?, ?)";
				//String logQuery = "INSERT INTO eventEntryScans (userID, eventID, scanID, errorMsg) VALUES (?, ?, ?, ?)";

				try {
					PreparedStatement pStatementLog = con.prepareStatement(logQuery);
					pStatementLog = con.prepareStatement(logQuery);
					pStatementLog.setString(1, username);
					pStatementLog.setInt(2, eventId);
					//pStatementLog.setInt(3, null);
					pStatementLog.setString(3, response);
					pStatementLog.executeUpdate();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			
			
			/* TODO: get if ticket is out of date *
			boolean ticketOutOfDate = false;
			try {				
				String selectAllQuery = "SELECT AttendedFlag FROM eventRoster WHERE (UserId = (SELECT UserId FROM users WHERE users.Username=?) and EventId = ?)";
				PreparedStatement pStatement = con.prepareStatement(selectAllQuery);
				pStatement.setString(1, username);
				pStatement.setInt(2, eventId);
				ResultSet rs = pStatement.executeQuery();
				while(rs.next()) {
					ticketWasScanned = rs.getBoolean("AttendedFlag");
				}
				pStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				// close the connection even if get was unsuccessful
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}*/
			
			
			
			
			
			
			
			
			
			
			
			
			return Response.status(200).entity(response).build();
		}
	}
	
}