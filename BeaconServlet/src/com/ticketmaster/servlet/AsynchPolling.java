package com.ticketmaster.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ticketmaster.bean.RosterEntryBean;
import com.ticketmaster.dao.MySqlDaoFactory;
import com.ticketmaster.dao.RosterEntryDao;

@WebServlet(urlPatterns = {"/asynchPolling"})
public class AsynchPolling extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	RosterEntryBean[] existingUsers = new RosterEntryBean[10];

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	// get event id from request
    	int eventId = Integer.parseInt(request.getParameter("eventId"));
    	String htmlMessage = "";
        ServletContext sc = request.getServletContext();
        sc.setAttribute("entries", null);	//clear
        
        try {
        	List<RosterEntryBean> roster = new ArrayList<RosterEntryBean>();
    		RosterEntryDao rosterDAO = MySqlDaoFactory.getRosterEntryDAO();
    		roster = rosterDAO.getRosterForEvent(eventId);
    		
    		// Print all data in the roster list
	        for(int i = 0; i < roster.size(); i++) {
	        	
	        	// check the array of preexisting RosterEntryBeans
	        		// that user already exists
	        	if(roster.get(i).getVisitor().getId() == existingUsers[i].getVisitor().getId())
	        	{
	        		// check to see if attended needs to be updated
	        		if(roster.get(i).isDidAttend() != existingUsers[i].isDidAttend())
	        			existingUsers[i].setDidAttend(roster.get(i).isDidAttend());
	        	}
	        		// note new user
	        	else
	        		existingUsers[i] = roster.get(i);
	        	
	        	// create final htmlMessage to send as response
	        	htmlMessage = "<div id=entry" + existingUsers[i].getVisitor().getUsername() + ">" +
	        				  "<br/>Visitor:" + 
	        				  "<br/>&nbsp;&nbsp;&nbsp;&nbsp; ID: " + existingUsers[i].getVisitor().getId() +
	        				  "<br/>&nbsp;&nbsp;&nbsp;&nbsp; First Name: " + existingUsers[i].getVisitor().getFirstName() +
	        				  "<br/>&nbsp;&nbsp;&nbsp;&nbsp; Last Name: " + existingUsers[i].getVisitor().getLastName() +
	        				  "<br/>&nbsp;&nbsp;&nbsp;&nbsp; Username: " + existingUsers[i].getVisitor().getUsername() +
	        				  "<br/>&nbsp;&nbsp;&nbsp;&nbsp; Visitor ID: " + existingUsers[i].getVisitor().getFirstName() +
	        				  "<br/>Attended?" +
	        				  "<br/>&nbsp;&nbsp;&nbsp;&nbsp; Status: " + existingUsers[i].isDidAttend() +
	        				  "<br/>Event:" +
	        				  "<br/>&nbsp;&nbsp;&nbsp;&nbsp; ID: " + existingUsers[i].getEvent().getId() +
	        				  "<br/>&nbsp;&nbsp;&nbsp;&nbsp; Name: " + existingUsers[i].getEvent().getName() +
	        				  "</div>";
	        	
	        	// for each htmlMessage, append it to the response, "entries"
	        	if (sc.getAttribute("entries") == null) {
	                sc.setAttribute("entries", htmlMessage);
	            } else {
	                String currentMessages = (String) sc.getAttribute("entries");
	                sc.setAttribute("entries", htmlMessage + currentMessages);
	            }
	        	
	        }
	        
        } catch (Exception Ex) {
        	sc.setAttribute("entries", "<h2>Error retrieving all users</h2>");
        }
        
        response.sendRedirect("Polling.jsp");
    }
}