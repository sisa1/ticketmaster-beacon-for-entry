package com.ticketmaster.api.rest;

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
import com.ticketmaster.dao.MySqlDaoFactory;
import com.ticketmaster.dao.RosterEntryDao;
import com.ticketmaster.dao.UserDao;

@Path("rest/Roster")
public class RosterRest {
	
	/* No Parameter -> Get all events
	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public List<EventBean> responseGetEventsWithRoster() {
		return null;
	}*/
	
	// Event ID parameter -> Get roster for event
	@GET
	@Path("/Event/{eventId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<RosterEntryBean> responseGetUsersAtRoster(@PathParam("eventId") @DefaultValue("-1") int eventId) {
		
		if(eventId == 9) {
			List<RosterEntryBean> completeRoster = new ArrayList<RosterEntryBean>();
			
			UserBean usr1 = new UserBean(1, "Obama", "Doe", "Odoe", "pass");
			UserBean usr2 = new UserBean(2, "Obama", "Senpai", "Jdoe", "pass");
			UserBean usr3 = new UserBean(3, "user", "nane", "user", "pass");
			UserBean usr4 = new UserBean(4, "TheLastPerson", "ToEnter", "lastty", "pass");
			
			EventBean evt1 = new EventBean(9, "Love Potion Number 9");
			
			RosterEntryBean entry1 = new RosterEntryBean(evt1, usr1, true);
			RosterEntryBean entry2 = new RosterEntryBean(evt1, usr2, false);
			RosterEntryBean entry3 = new RosterEntryBean(evt1, usr3, true);
			RosterEntryBean entry4 = new RosterEntryBean(evt1, usr4, true);
						
			completeRoster.add(entry1);
			completeRoster.add(entry2);
			completeRoster.add(entry3);
			completeRoster.add(entry4);
			
			return completeRoster;
		}
		
		List<RosterEntryBean> completeRoster = new ArrayList<RosterEntryBean>();
		RosterEntryDao dao = MySqlDaoFactory.getRosterEntryDAO();
		completeRoster = dao.getRosterForEvent(eventId);
		return completeRoster;
	}
	
	//eventId, username parameter -> 
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response setUserAttend(@FormParam("eventId") @DefaultValue("-1") int eventId,
								  @FormParam("username") @DefaultValue("") String username) {
		boolean didFindUser = false;
		
		try {
	        UserDao userDao = MySqlDaoFactory.getUserDAO();
	        List<UserBean> userList = userDao.getAllUsers();
	        for(int i = 0; i < userList.size(); i++) {
	        	if(userList.get(i).getUsername().compareTo(username) == 0) {
	        		didFindUser = true;
	        	}
	        }
        } catch (Exception Ex) {
        	return Response.status(500).entity("Error getting user list").build();
        }
		
		if(didFindUser && eventId < 10) {
			String response = "welcome to event " + eventId;
			return Response.status(200).entity(response).build();
		}
		
		return Response.status(200).entity("Invalid user, or no ticket?").build();
	}
	
}