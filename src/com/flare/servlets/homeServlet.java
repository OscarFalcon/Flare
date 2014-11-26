package com.flare.servlets;

import java.io.IOException;

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
		HttpSession session;
		String action;

		System.out.println("homeServlet: doPost");
		
		
		
		session = request.getSession(false);
		if(session == null)
		{
			System.out.println("no session established");
			response.sendRedirect("http://localhost:8080/login");
			return;
		}
		
		
		else if((boolean)session.getAttribute("loggedIn"))
		{
			action = request.getParameter("action");
			if(action == null)
			{
				System.out.println("homeServlet-doPost: ERROR - action is null");
				return;
			}
			switch(action)
			{
				case "createAccount":
					createAccount(request,response);
			}
			
			
		}
		
	
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
