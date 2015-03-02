package com.ticketmaster.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ticketmaster.bean.UserBean;
import com.ticketmaster.dao.MySqlDaoFactory;
import com.ticketmaster.dao.UserDao;

@WebServlet(urlPatterns = {"/asynchPolling"})
public class AsynchPolling extends HttpServlet {
	private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String message = request.getParameter("message");
        //String htmlMessage = "<p><b>" + name + "</b><br/>" + message + "</p>";
    	
    	String htmlMessage = "";
        ServletContext sc = request.getServletContext();
        
        try {
	        UserDao userDao = MySqlDaoFactory.getUserDAO();
	        List<UserBean> userList = userDao.getAllUsers();
	        for(int i = 0; i < userList.size(); i++) {
	        	htmlMessage = "<br/>" + userList.get(i).getUsername();
	        	
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