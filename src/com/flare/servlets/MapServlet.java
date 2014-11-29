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

import java.math.BigDecimal;

import com.flare.database.MySQL;
import com.flare.services.FlareService;
/**
 * Servlet implementation class MapServlet
 */
@WebServlet("/map")
public class MapServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MapServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("MapServlet:GET");		
		validate(request,response,"map");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		final HttpSession session = request.getSession();
		final String user_id = (String) session.getAttribute("user_id");
		final PrintWriter writer = response.getWriter();
		
		response.setContentType("application/json");
		
		System.out.println("MapServlet:POST");
		
		if(user_id == null)
		{
			System.out.println("MapServlet:POST: username is null");
			return;
		}
		String json = FlareService.getEvents(Integer.parseInt(user_id));
		
		System.out.println(json);
		writer.write(json.toString());
		writer.close();
		return;
		
	}

}
