package com.flare.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flare.services.FlareService;

/**
 * Servlet implementation class CreateEventsServlet
 */
@WebServlet("/created-events")
public class CreatedEventsServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreatedEventsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("CreatedEventsServlet:GET");		
		validate(request,response,"created-events");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("CreatedEventsServlet: doPost");
		/**
		 * Load the events onto the feed!
		 */
		String json = FlareService.getCreatedEvents(Integer.parseInt( (String) request.getSession().getAttribute("user_id")));
		if(json == null)
			json = "{}";
		
		System.out.println("JSON: " + json);
		PrintWriter writer = response.getWriter();
		writer.write(json);
	}

}
