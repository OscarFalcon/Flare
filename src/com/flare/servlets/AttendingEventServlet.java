package com.flare.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flare.database.MySQL;

/**
 * Servlet implementation class AttendingEventServlet
 */
@WebServlet("/AttendingEvent")
public class AttendingEventServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AttendingEventServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action;
		
		
		action = (String) request.getParameter("action");
		
		switch(action)
		{
			case "goingToEvent":
				goingToEvent(request,response);
				break;
			case "notGoingToEvent":
				notGoingToEvent(request,response);
				break;
			case "getPeopleAttending":
				getPeopleAttending();
				break;
		}
		
	}
	
	private void goingToEvent(HttpServletRequest request, HttpServletResponse response)
	{
		System.out.println("goingToEvent");
		
		String eventId = request.getParameter("eventID");
		String userId = (String) request.getSession().getAttribute("user_id");
		
		if(eventId == null || userId == null)
		{
			System.out.println("null eventId or userId");
			return;
		}
		
		String mysql = "INSERT INTO attending_event(user_id,event_id) VALUES(?,?)";
		Object arguments[] = {userId,eventId};
		MySQL.execute(mysql, arguments);
		
		mysql = "UPDATE event SET attending = attending + 1  WHERE id = ?";
		Object [] arguments2 = {eventId};
		MySQL.execute(mysql, arguments2);
		
	}
	
	private void notGoingToEvent(HttpServletRequest request, HttpServletResponse response)
	{
		System.out.println("notGoingToEvent");
		
		String eventId = request.getParameter("eventID");
		String userId = (String) request.getSession().getAttribute("user_id");
		
		if(eventId == null || userId == null)
		{
			System.out.println("null eventId or userId");
			return;
		}
		
		String mysql = "DELETE FROM attending_event WHERE user_id = ? AND event_id = ?";
		Object arguments[] = {userId,eventId};
		MySQL.execute(mysql, arguments);
		
		mysql = "UPDATE event SET attending = attending - 1  WHERE id = ?";
		Object [] arguments2 = {eventId};
		MySQL.execute(mysql, arguments2);
		
		
	}
	
	
	
	private void getPeopleAttending()
	{
		
	}
	
	
	
	

}
