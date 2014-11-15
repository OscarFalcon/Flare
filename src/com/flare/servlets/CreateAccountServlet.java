package com.flare.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flare.database.MySQL;



/**
 * Servlet implementation class CreateAccountServlet
 */
@WebServlet("/signup")
public class CreateAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateAccountServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filepath = "/Users/falcon/Workspaces/Flare/Flare_WAR/WebContent/WEB-INF/create-account.html";
		File file = new File(filepath);
		FileInputStream fis = new FileInputStream(file);
		byte[] data = new byte[(int)file.length()];
	    fis.read(data);
		fis.close();
		   
		String s = new String(data, "UTF-8");
		response.getWriter().write(s);
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username,password,email,firstname,lastname;
		PrintWriter writer = response.getWriter();
		
		System.out.println("CreateAccountservlet:POST");
		
		username = (String) request.getParameter("username");
		password = (String) request.getParameter("password");
		email    = (String) request.getParameter("email");
		firstname = (String)request.getParameter("firstname");
		lastname = (String)request.getParameter("lastname");
		
		
		if(username == null || password == null || email == null || firstname == null || lastname == null)
		{
			System.out.println("null parameters");
			return;
		}
		
		String mysql_string = "INSERT INTO person(username,email,password,first_name,last_name) VALUES(?,?,?,?,?)";
		Object arguments[] = new Object
				[5];
		arguments[0] = username;
		arguments[1] = email;
		arguments[2] = password;
		arguments[3] = firstname;
		arguments[4] = lastname;
		
		if(MySQL.execute(mysql_string, arguments) == false)
		{
			writer.write("false");
			
		}
		else
		{
			writer.write("true");
		}
		writer.close();
		return;
			
	}

}
