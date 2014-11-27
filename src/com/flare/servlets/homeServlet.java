package com.flare.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.flare.database.MySQL;

@WebServlet("/home")
public class homeServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
   
    public homeServlet()
    {
        super();
    }

	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("homeServlet:GET");		
		validate(request,response,"home");

	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("homeServlet: doPost");
		
		
		/**
		 * Load the events onto the feed!
		 */
		
		String eventid, eventTitle, eventDescription, eventDate, eventTime,userID;
		String sql = "SELECT id,title,description,date,time,user_id FROM event WHERE 1";
		String json;
		
		System.out.println("HIT SERVER");
		
		int[] result_types = {MySQL.INTEGER,MySQL.STRING,MySQL.STRING,MySQL.STRING,MySQL.STRING,MySQL.INTEGER};
		ArrayList<Object[]> results;
		System.out.println("TEST");
		results = MySQL.executeQuery(sql, null, result_types);
		if(results == null){
			response.setStatus(400);
			return;
		}
		json = "{ \"size\": " + "\"" + results.size() + "\"";
		for(int i=0; i < results.size(); i++){
			eventid = (String) results.get(i)[0].toString();
			eventTitle = (String) results.get(i)[1];
			eventDescription = (String) results.get(i)[2];
			eventDate = (String) results.get(i)[3];
			eventTime = (String) results.get(i)[4];
			userID = (String) results.get(i)[5].toString();
			
			json += "," + "\"eventid"+i+"\": " + "\"" + eventid + "\"," +
					"\"eventtitle"+i+"\": " + "\"" + eventTitle + "\"," +
					"\"eventdescription"+i+"\": " + "\"" + eventDescription + "\"," +
					"\"eventdate"+i+"\": " + "\"" + eventDate + "\"," +
					"\"eventtime"+i+"\": " + "\"" + eventTime + "\"," +
					"\"eventuserid"+i+"\": " + "\"" + userID + "\"";
		}	
		json += "}";
		
		System.out.println("JSON: " + json);
		PrintWriter writer = response.getWriter();
		writer.write(json);
		
		
		
	}
	
	
	
	
	
	
	
	
	
	private void createAccount(HttpServletRequest request, HttpServletResponse response){
		String username;
		String email;
		String password;
		Object[] arguments;
		
		username = request.getParameter("username");
		password = request.getParameter("password");
		email	=  request.getParameter("email");
		
		
		if(username == null || password == null || email == null)
		{
			System.out.println("homeServlet:createAccount : null values");
			return;
		}
		
		
		
		String execute_query = "INSERT INTO person(username,password,object) VALUES (?,?,?)";
		
		arguments = new Object[3];
		arguments[0] = (username);
		arguments[1] = (password);
		
		if(MySQL.execute(execute_query, arguments) == false)
		{
			System.out.println("failed to add user to databse");
			return;
		}
		
		
	}
	
	
	
	
	
	

}
