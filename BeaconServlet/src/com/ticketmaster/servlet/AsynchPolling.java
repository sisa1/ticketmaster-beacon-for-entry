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
import com.ticketmaster.dao.EventDao;

@WebServlet(urlPatterns = {"/asynchPolling"})
public class AsynchPolling extends HttpServlet {
	private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	int eventId = Integer.parseInt(request.getParameter("eventId"));
    	String htmlMessage = "";
        ServletContext sc = request.getServletContext();
        
        try {
        	List<RosterEntryBean> roster = new ArrayList<RosterEntryBean>();
    		RosterEntryDao rosterDAO = MySqlDaoFactory.getRosterEntryDAO();
    		roster = rosterDAO.getRosterForEvent(eventId);
//	        UserDao userDao = MySqlDaoFactory.getUserDAO();
//	        List<UserBean> userList = userDao.getAllUsers();
	        for(int i = 0; i < roster.size(); i++) {
	        	htmlMessage = "<br/>" + roster.get(i).getEvent().getName();
	        	
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