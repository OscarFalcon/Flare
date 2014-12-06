package com.flare.servlets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;

import com.flare.database.MySQL;

/**
 * Servlet implementation class ConfirmEventServlet
 */
@WebServlet("/confirm-event")

@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
maxFileSize=1024*1024*10,      					// 10MB
maxRequestSize=1024*1024*50)   					// 50MB


public class ConfirmEventServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    
    public ConfirmEventServlet()
    {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		validate(request,response,"confirm-event");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String user_id;
		//final String SAVE_DIR = System.getProperty("user.home") + "/git/Flare/WebContent/Events";
		String path = getServletContext().getRealPath("/");
		System.out.println("servlet path" + path);
		final String SAVE_DIR = getServletContext().getRealPath("/") + "/Events";
		
		
		System.out.println(request.getContentType());
		
		Object arguments[] = new Object[7]; //Object arguments[] = new Object[]{eventTitle,eventDescription,lat,log,eventDate,eventTime,user_id};
		user_id = (String) session.getAttribute("user_id");

		Part part;
		Part photoPart = null;
		Iterator<Part> parts = request.getParts().iterator();
		try{	
			
			for(int i = 0; i < 7; i++)
        	{
        		if(parts.hasNext() == false)
        		{
        			System.out.println("Insufficient data: VALUES" + i);
        			response.setStatus(400); //The request could not be understood by the server due to malformed syntax. The client SHOULD NOT repeat the request without modifications.
        			return;
        		}
        		part = parts.next();
        		System.out.println("ContentType" + part.getContentType());
        		if(part.getContentType() == null) //data part of request
				{
					System.out.println("name: " + part.getName());
					InputStream in = part.getInputStream();
					String value = IOUtils.toString(in, "UTF-8");
					System.out.println("value: " + value);
					
					switch(part.getName())
					{
					case "eventTitle":
						arguments[0] = value;
						break;
					case "eventDescription":
						arguments[1] = value;
						break;
					case "eventDate":
						arguments[2] = value;
						break;
					case "eventTime":
						arguments[3] = value;
						break;
					case "latitude":
						arguments[4] = value;
						break;	
					case "longitude":
						arguments[5] = value;
						break;
					case "file":
						photoPart = part;
						break;
					}
					
				}
				else
				{
					photoPart = part;
					
				}       		
        	}
		}
		catch(IOException e)
        {
			e.printStackTrace();
        }
        	
		arguments[6] =  user_id;
			
		if(arguments[0] == null || arguments[1] == null || arguments[2] == null || arguments[3] == null 
				|| arguments[4] == null || arguments[5] == null || arguments[6] == null)
		{
			System.out.println("AddEventServlet: null parameter values");
			return;
			
		}
			 
		BigDecimal lat,log;
		String locationLat,locationLog;
		locationLat = (String)arguments[4];
		locationLog = (String)arguments[5];
		locationLat = locationLat.substring(0,locationLat.indexOf('.')+6);
		locationLog = locationLog.substring(0,locationLat.indexOf('.')+6);
		lat = new BigDecimal(locationLat);
		log = new BigDecimal(locationLog);
		lat.setScale(5);
		log.setScale(5);
		System.out.println("locationLat === " + locationLat);
		System.out.println("locationLog === " + locationLog);
		arguments[4] = lat;
		arguments[5] = log;
		
		
		String mysql_string = "INSERT INTO event(title,description,date,time,locationLat,locationLong,user_id) VALUES(?,?,?,?,?,?,?)";
					
		if(MySQL.execute(mysql_string, arguments) == false)
		{
			response.setStatus(503); //Service Unavailable
			return;
		}	
			
		String lastIncrement = "SELECT LAST_INSERT_ID()";
		int result_types[] = new int[]{MySQL.INTEGER};
		Object[] arguments1 = new Object[0];
		ArrayList<Object[]> results;
			
			
		results = MySQL.executeQuery(lastIncrement, arguments1, result_types);
		if(results == null)
		{
			System.out.println("addEventServlet: last increment failed");
			response.setStatus(503);
			return;
		}
			
		int eventID = (int) results.get(0)[0];
		System.out.println("eventID: " + eventID);
		session.setAttribute("eventID",Integer.toString(eventID));
	
		
		if(photoPart == null) //This does not necessarily mean something went wrong, just that no photo needed to be uploaded.
		{
			System.out.println("Photo not updated!");
			response.setStatus(200);
			return;
			
		} else {
			System.out.println("writing photo!");
			String saveName = (String) session.getAttribute("eventID");
			photoPart.write(SAVE_DIR + File.separator + saveName + ".jpg");
			System.out.println("Photo has been written!");
			return;
		}
		
		
		
		
		 
		
        
        
        
        
        
        
        
        
	}

}
