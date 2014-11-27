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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.commons.io.IOUtils;
import com.flare.database.MySQL;


@WebServlet("/addEvent")

@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
maxFileSize=1024*1024*10,      					// 10MB
maxRequestSize=1024*1024*50)   					// 50MB





public class AddEventServlet extends BaseServlet
{
	
	private static final long serialVersionUID = 1L;

	
    
    public AddEventServlet() 
    {
        super();
    
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("CreateEventServlet:GET");		
		validate(request,response,"add-event");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
				
		HttpSession session = request.getSession();
		String user_id;
		final String SAVE_DIR = System.getProperty("user.home") + "/git/Flare/WebContent/Events";
		//final String SAVE_DIR = getServletContext().getContextPath();
		
		System.out.println(SAVE_DIR);
		
		Object arguments[] = new Object[7]; //Object arguments[] = new Object[]{eventTitle,eventDescription,lat,log,eventDate,eventTime,user_id};
		user_id = (String) session.getAttribute("user_id");

		Part part;
		Iterator<Part> parts = request.getParts().iterator();
		try{	
			
			for(int i = 0; i < 6; i++)
        	{
        		if(parts.hasNext() == false)
        		{
        			System.out.println("Insufficient data");
        			response.setStatus(400); //The request could not be understood by the server due to malformed syntax. The client SHOULD NOT repeat the request without modifications.
        			return;
        		}
        		part = parts.next();
        		System.out.println("name = " + part.getName());
    			InputStream in = part.getInputStream();
    			String value = IOUtils.toString(in, "UTF-8");
    			System.out.println("value :" + value);
        		arguments[i] = value;        		
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

		
		arguments[4] =lat;
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
		

		if(parts.hasNext() == false)
		{
			System.out.println("Insufficient data");
			response.setStatus(400);
			return;
		}
		part = parts.next();
		part.getSubmittedFileName();			  	    
		String saveName = (String) session.getAttribute("eventID"); 
		
        File fileSaveDir = new File(SAVE_DIR); 		
        if (!fileSaveDir.exists())
        {
            fileSaveDir.mkdir();
        }
		part.write(SAVE_DIR + File.separator + saveName + ".jpg");
		return; 
	}

}
