package com.flare.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flare.database.MySQL;

/**
 * Servlet implementation class ProfileServlet
 */
@WebServlet("/profile")
public class ProfileServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ProfileServlet:GET");		
		validate(request,response,"profile");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user_id = (String) request.getSession().getAttribute("user_id");
		String sql = "SELECT username,email,first_name,last_name,aboutme FROM person WHERE user_id = ?";
		String username,email,firstname,lastname,aboutme;
		
		Object[] arguments = new Object[1];
		arguments[0] = user_id;
		int[] result_types = {MySQL.STRING,MySQL.STRING,MySQL.STRING,MySQL.STRING,MySQL.STRING};
		ArrayList<Object[]> results;
		
		
		results = MySQL.executeQuery(sql, arguments, result_types);
		if(results == null)
		{
			response.setStatus(400);
			return;
		}
		
		username = (String) results.get(0)[0]; 
		email = (String) results.get(0)[1];
		firstname = (String) results.get(0)[2];
		lastname = (String) results.get(0)[3];
		aboutme = (String) results.get(0)[4];
		
		String json = "{ \"userid\": " + "\"" + user_id + "\"," +
				"\"username\": " + "\"" + username + "\"," + 
				" \"firstname\": " + "\"" + firstname + "\"," + 
				" \"lastname\": " + "\"" + lastname + "\"," +
				" \"email\": " + "\"" + email + "\"," + 
				" \"aboutme\": " + "\"" + aboutme + "\" }";
		
		System.out.println("JSON: "+ json);
		
		PrintWriter writer = response.getWriter();
		writer.write(json);
	}

}
