package com.ticketmaster.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ticketmaster.bean.UserBean;
import com.ticketmaster.dao.MySqlDaoFactory;
import com.ticketmaster.dao.UserDao;

/**
 * Servlet implementation class HelloWorld
 */
@WebServlet("/HelloWorld")
public class HelloWorld extends HttpServlet {
    private static final long serialVersionUID = 1L;
        
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelloWorld() {
        super();
        // TODO Auto-generated constructor stub
    }
 
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html");
        PrintWriter printWriter  = response.getWriter();
        printWriter.println("<h1>Hello World! Test Commit</h1>");
        printWriter.println("<h3>Shelby was here :)</h3>");
        printWriter.println("<h2>Matt committed again with T2 Server </h2>");
        
        try {
	        UserDao userDao = MySqlDaoFactory.getUserDAO();
	        List<UserBean> userList = userDao.getAllUsers();
	        for(int i = 0; i < userList.size(); i++) {
	        	printWriter.println("<br>" + userList.get(i).getUsername());
	        }
        } catch (Exception Ex) {
        	printWriter.println("<h2>Error retrieving all users</h2>");
        }
    }
 
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }
 
}