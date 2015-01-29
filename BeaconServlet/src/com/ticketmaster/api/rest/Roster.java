package com.ticketmaster.api.rest;

import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ticketmaster.bean.EventBean;
import com.ticketmaster.bean.RosterEntryBean;

@Path("rest/Roster")
public class Roster {
	
	// No Parameter -> Get all events
	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public List<EventBean> responseGetEventsWithRoster() {
		return null;
	}
	
	// Event ID parameter -> Get roster for event
	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public List<RosterEntryBean> responseGetUsersAtRoster(@FormParam("eventId") @DefaultValue("-1") int eventId) {
		return null;
	}
	
	//eventId, username parameter -> 
	@POST
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public RosterEntryBean setUserAttend(@FormParam("eventId") @DefaultValue("-1") int eventId,
										 @FormParam("username") @DefaultValue("") String username) {
		return null;
	}
}