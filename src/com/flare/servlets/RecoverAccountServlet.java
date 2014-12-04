package com.flare.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.flare.database.MySQL;


@WebServlet("/RecoverAccount")
public class RecoverAccountServlet extends GeneralServlet {
	private static final long serialVersionUID = 1L;
	private static final int MAX_RESET_ATTEMPTS = 5;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecoverAccountServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response,"recover-account");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username;
		String email;
		String id;
		String password;
		
	//	Integer resetAccountCount;
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
		//	session.setAttribute("resetAccountCount",0);
		//	session.setAttribute("resetAccount",false);
		}			
		
		//resetAccountCount = (Integer) session.getAttribute("resetAccountCount");

		username = (String) request.getParameter("userName");
		email = (String) request.getParameter("userEmail");
		password = (String) request.getParameter("newPassword");
		
		
		System.out.println("username: " + username);
		System.out.println("email: " + email);
		System.out.println("Password: " + password);
		
		
		
		if(username == null || email == null)
		{
			System.out.println("username or email does not exist");
			return;
		}
		
       // if(resetAccountCount >= MAX_RESET_ATTEMPTS)
        //{
        	//System.out.println("Error: You have reached the max number tries to reset account");
        	//System.out.println("Contact: mjpric3@gmail.com");
        	//return;
        //}
  		
		String mysql_string = "SELECT user_id FROM person WHERE username = ? && email = ?";
		Object arguments[] = new Object[2];
		int result_types[] = new int[1];
		ArrayList<Object[]> results;
		writer = response.getWriter();
		
		arguments[0] = username;
		arguments[1] = email;
		result_types[0] = MySQL.INTEGER;

		results = MySQL.executeQuery(mysql_string, arguments, result_types);
		
		if(results == null )
		{
			writer.write("false");
			writer.close();
			return;
		}

		// Create new password
		if(results.size() != 0)
		{
			id = results.get(0)[0].toString();
			Object passArguments[] = new Object[2];
			
			String newPass = "UPDATE person SET password = ? WHERE user_id = ?";
			passArguments[0] = id;
			passArguments[1] = password;
			boolean result = false;
		
			result = MySQL.execute(newPass, passArguments);
			
			if(result == false)
			{
				System.out.println("Failed to reset password");
				response.setStatus(400);
				return;
			}
			else{
				System.out.println("Account Recovered");
			}
			
		
		}
			
		else
		{
			writer.write("false");
			//session.setAttribute("resetAccountCount",resetAccountCount+1);
		
		}
		
		writer.close();
		return;
	}
	
}
