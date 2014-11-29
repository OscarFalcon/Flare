package com.flare.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BaseServlet extends HttpServlet{

	
	private static final long serialVersionUID = 1L;

	protected void validate(HttpServletRequest request, HttpServletResponse response,String app) throws ServletException, IOException {
		HttpSession session;
		
		String filepath = getServletContext().getRealPath("/") + "/WEB-INF/" + app + ".html";

		System.out.println("baseServlet");
		System.out.println("requestURL: " + request.getRequestURL().toString());
		
		session = request.getSession(false);
		
		if(session == null)
		{
			System.out.println("no session established");
			response.sendRedirect("http://localhost:8080/login");
		}
		else if(session.getAttribute("loggedIn").equals("false"))
		{
			response.getWriter().write("NOT LOGGED IN");
			response.sendRedirect("http://localhost:8080/login");	
		}
		else
		{
			System.out.println("baseServlet: " + (String)session.getAttribute("username"));
			File file = new File(filepath);
			FileInputStream fis = new FileInputStream(file);
			byte[] data = new byte[(int)file.length()];
		    fis.read(data);
			fis.close();
			   
			String s = new String(data, "UTF-8");
			response.getWriter().write(s);
		}
		
		return;

	}

	
	
	
}
