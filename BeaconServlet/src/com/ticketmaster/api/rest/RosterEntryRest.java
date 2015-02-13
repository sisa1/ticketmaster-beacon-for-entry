package com.ticketmaster.api.rest;

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
import com.ticketmaster.bean.RosterEntryBean;
import com.ticketmaster.dao.EventDao;
import com.ticketmaster.dao.MySqlDaoFactory;
import com.ticketmaster.dao.RosterEntryDao;

@Path("rest/RosterEntry")
public class RosterEntryRest {
	
	// No Parameter -> Get all
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<RosterEntryBean> responseGetAll() {
		RosterEntryDao dao = MySqlDaoFactory.getRosterEntryDAO();
		List<RosterEntryBean> rosterEntries = null;
		try {
			rosterEntries = dao.getAllRosterEntries();
		} catch (Exception ex) {
			rosterEntries = null;
		}
		return rosterEntries;
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response responseInsertRosterEntry(@FormParam("eventId") @DefaultValue("-1") int eventId,
											  @FormParam("eventName") @DefaultValue("") String eventName) {
		EventDao dao = MySqlDaoFactory.getEventDAO();
		try {
			EventBean result = dao.createEvent(eventId, eventName);
			return Response.status(200).entity(result).build();
		} catch(Exception ex) {
			return Response.status(500).entity("Event Dao Exception: " + ex.getMessage()).build();
		}
	}
}