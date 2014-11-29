package com.flare.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.flare.database.MySQL;

/**
 * Servlet implementation class loginServlet
 */
@WebServlet("/login")
public class loginServlet extends GeneralServlet {
	private static final long serialVersionUID = 1L;

	private static final int MAX_LOGIN_ATTEMPTS = 5;
	
	
    public loginServlet() {
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("loginServlet:doGet");
		doGet(request,response,"login");
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username;
		String password;
		Integer loginCount;
		HttpSession session;
		PrintWriter writer;
		
		
		System.out.println("loginServlet:POST");
		
		if(request == null)
		{
			System.out.println("null request");
			return;
		}

		session = request.getSession(false);
		if(session == null)
		{
			session = request.getSession(true);
			session.setAttribute("loginCount",0);
			session.setMaxInactiveInterval(60);// will log member out after 1 minute
			session.setAttribute("loggedIn",false);
		}			
		
		loginCount = (Integer) session.getAttribute("loginCount");
		
		username = (String) request.getParameter("username");
		password = (String) request.getParameter("password");
		
		if(username == null || password == null)
		{
			
			return;
		}
		
        if(loginCount == MAX_LOGIN_ATTEMPTS)
        {
        	System.out.println("max login attemps");
        	return;
        }
  		
		String mysql_string = "SELECT user_id FROM person WHERE username = ? && password = ?";
		Object arguments[] = new Object[2];
		int result_types[] = new int[1];
		ArrayList<Object[]> results;
		writer = response.getWriter();
		
		arguments[0] = username;
		arguments[1] = password;
		result_types[0] = MySQL.INTEGER;

		results = MySQL.executeQuery(mysql_string, arguments, result_types);
		if(results == null )
		{
			writer.write("error");
			writer.close();
			return;
		}
		if(results.size() != 0)
		{
			String id = Integer.toString((int)results.get(0)[0]);
			session.setAttribute("loginCount",0);
			session.setAttribute("loggedIn",true);
			session.setAttribute("username",username);
			session.setAttribute("user_id",id);
			writer.write("true");	
		}
		else
		{
			writer.write("false");
			session.setAttribute("loginCount",loginCount+1);
		
		}
		
		writer.close();
		return;
		
		
	}
	
	
	

}
