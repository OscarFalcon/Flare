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
		String firstname;
		String lastname;
		String email;
		String aboutme;
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
			session.setMaxInactiveInterval(120);// will log member out after 2 minute
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
  		
		String mysql_string = "SELECT user_id, email, first_name, last_name, aboutme FROM person WHERE username = ? && password = ?";
		Object arguments[] = new Object[2];
		int result_types[] = new int[5];
		ArrayList<Object[]> results;
		writer = response.getWriter();
		
		arguments[0] = username;
		arguments[1] = password;
		result_types[0] = MySQL.INTEGER;
		result_types[1] = MySQL.STRING;
		result_types[2] = MySQL.STRING;
		result_types[3] = MySQL.STRING;
		result_types[4] = MySQL.STRING;

		results = MySQL.executeQuery(mysql_string, arguments, result_types);
		
		if(results == null )
		{
			writer.write("false");
			writer.close();
			return;
		}
		
		// Creating JSON Object
		String json = "{ \"user\": [";


		if(results.size() != 0)
		{
			String id = Integer.toString((int)results.get(0)[0]);
			email = (String) results.get(0)[1];
			firstname = (String) results.get(0)[2];
			lastname = (String) results.get(0)[3];
			aboutme = (String) results.get(0)[4];
			
			session.setAttribute("loginCount",0);
			session.setAttribute("loggedIn",true);
			session.setAttribute("username",username);
			session.setAttribute("password", password);
			session.setAttribute("user_id",id);
			session.setAttribute("firstname", firstname);
			session.setAttribute("lastname", lastname);
			session.setAttribute("email", email);
			session.setAttribute("aboutme", aboutme);
			

			// Populating JSON Object
			json += "{ \"userID\":" + "\"" + id + "\"," +
					    	"\"firstName\":" + "\""+ firstname + "\"," +
						    "\"lastName\":" + "\"" + lastname + "\"," +
						    "\"eMail\":" + "\"" + email + "\"," +
		    				"\"aboutMe\":" + "\"" + aboutme +"\"" + "}]}";

			
			System.out.println("JSON: " + json );
			
			// write out json object
			writer.write(json);	
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
