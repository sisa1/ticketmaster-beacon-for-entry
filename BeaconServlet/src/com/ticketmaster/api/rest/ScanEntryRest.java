package com.ticketmaster.api.rest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ticketmaster.bean.EventBean;
import com.ticketmaster.bean.RosterEntryBean;
import com.ticketmaster.bean.ScanEntryBean;
import com.ticketmaster.bean.UserBean;
import com.ticketmaster.dao.EventDao;
import com.ticketmaster.dao.MySqlDaoFactory;
import com.ticketmaster.dao.RosterEntryDao;
import com.ticketmaster.dao.ScanEntryDao;
import com.ticketmaster.dao.UserDao;
import com.ticketmaster.dao.myql.MySqlDao;
import com.ticketmaster.dao.EventTimeDao;

@Path("rest/ScanEntry")
public class ScanEntryRest {
	
	/* Event ID parameter -> Get roster for event*/
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ScanEntryBean> responseGetLiveTicketsRoster(@QueryParam("eventId") @DefaultValue("-1") int eventId,
															  @QueryParam("timePassed")  @DefaultValue("-1") int timePassedSec,
															  @QueryParam("onlyErrors")  @DefaultValue("true") boolean showOnlyErrors) {
		List<ScanEntryBean> completeRoster = new ArrayList<ScanEntryBean>();
		ScanEntryDao dao = MySqlDaoFactory.getScanEntryDAO();
		
		if(timePassedSec > 0) {
			completeRoster = dao.getScansForEventBeforeTime(eventId, timePassedSec);
		} else {
			completeRoster = dao.getScansForEvent(eventId);
		}
		if(showOnlyErrors) {
			for(int i = 0; i < completeRoster.size(); i++) {
				//completeRoster.get(i) iterate through errors only
			}
		}
		
		return completeRoster;
	}
	
}