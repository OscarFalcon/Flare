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
		
		
		String mysql_string = "SELECT event.id,event.title,event.description,event.locationLat,event.locationLong"
							  + ",event.date,event.time,event.type,event.attending,event.checked_in,friends.friend_id from"
							  + " event INNER JOIN friends ON friends.friend_id = event.user_id WHERE friends.user_id = ?";
		
		ArrayList<Object[]> results;
		Object[] arguments = new Object[]{user_id};
		int[] result_types = new int[]{MySQL.INTEGER,MySQL.STRING,MySQL.STRING,MySQL.BIG_DECIMAL,MySQL.BIG_DECIMAL,MySQL.STRING,
									   MySQL.STRING,MySQL.STRING,MySQL.INTEGER,MySQL.INTEGER,MySQL.INTEGER};
		

		results = MySQL.executeQuery(mysql_string, arguments, result_types);
		
		if(results == null)
		{
			response.setStatus(503);	//503 Service Unavailable.
			return;
		}
		
		StringBuilder json = new StringBuilder("{ \"events\": [");
		
		int size = results.size();
		for(int i = 0; i < size;i++)
		{
			Object tmp[]; 
			String line = null;
			tmp = results.get(i);
			String eventTitle,eventDescription,eventDate,eventTime,eventType;
			BigDecimal locationLat,locationLog;
			int eventId,eventAttending,eventCheckedIn,friendId;
			
			
			eventId = (int) tmp[0];
			eventTitle = (String) tmp[1];
			eventDescription = (String) tmp[2];
			locationLat = (BigDecimal) tmp[3];
			locationLog = (BigDecimal) tmp[4];
			eventDate = (String) tmp[5];
			eventTime = (String) tmp[6];
			eventType = (String) tmp[7];
			eventAttending = (int) tmp[8];
			eventCheckedIn = (int) tmp[9];
			friendId = (int) tmp[10];
			 
			line  = "{ \"eventId\":" + "\"" + eventId + "\"," +
					"\"eventTitle\":" + "\""+ eventTitle + "\"," +
					"\"eventDescription\":" + "\"" + eventDescription + "\"," +
					"\"eventDate\":" + "\"" + eventDate + "\"," +
					"\"eventTime\":" + "\"" + eventTime +"\"," +
					"\"eventType\":" + "\"" + eventType + "\"," +
					"\"locationLat\":" +"\""+ locationLat + "\"," +
					"\"locationLog\":" +"\""+locationLog + "\"," + 
					"\"attending:\":" + "\""+eventAttending + "\"," +
					"\"checkedIn\":" + "\""+eventCheckedIn +"\"," +
					"\"friendId\":" + "\""+friendId + "\"}";
			if( (i + 1) != size)
			{
				line = line + ",";
			}
			
			json.append(line);
			
		}
		json.append("]}");
		System.out.println(json);
		
		writer.write(json.toString());
		writer.close();
		return;
		
	}

}
