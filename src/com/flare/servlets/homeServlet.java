package com.flare.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flare.database.MySQL;
import com.flare.services.FlareService;


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
		String json = FlareService.getEvents(Integer.parseInt( (String) request.getSession().getAttribute("user_id")));
		
		
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
