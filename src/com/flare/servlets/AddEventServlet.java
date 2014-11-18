package com.flare.servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import com.flare.database.MySQL;


@WebServlet("/addEvent")

@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
maxFileSize=1024*1024*10,      					// 10MB
maxRequestSize=1024*1024*50)   					// 50MB





public class AddEventServlet extends BaseServlet
{
	
	private static final long serialVersionUID = 1L;

	private static final String SAVE_DIR = "/Users/falcon/Workspaces/Flare/Flare_WAR/WebContent/Events";
	
    
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
		PrintWriter writer = response.getWriter();
		String contentType = request.getContentType();
		HttpSession session = request.getSession();
		
		System.out.println("contentType = "+contentType);
		
		if(contentType.contains("multipart/form-data"))
		{
		
			getImage(request,response);
			return;
			
		}
		
		String eventTitle,eventDescription,eventDate,eventTime,longitude,latitude,user_id;
		
		eventTitle = (String) request.getParameter("eventTitle");
		eventDescription = (String) request.getParameter("eventDescription");
		eventDate = (String) request.getParameter("eventDate");
		eventTime = (String) request.getParameter("eventTime");
		longitude = (String) request.getParameter("longitude");
		latitude = (String) request.getParameter("latitude");
		user_id = (String) session.getAttribute("user_id");
		
		
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			 String paramName = names.nextElement();
			 System.out.println("parameter name: " + paramName);
			 System.out.println("Parameter value: "+(String)request.getParameter(paramName));
		 }
		
		
		if(eventTitle == null || eventDescription == null || eventDate == null || eventTime == null 
				|| longitude == null || latitude == null || user_id == null)
		{
			System.out.println("AddEventServlet: null parameter values");
			return;
			
		}
		 
		BigDecimal lat,log;
		
		lat = new BigDecimal(latitude);
		log = new BigDecimal(longitude);
		
		String mysql_string = "INSERT INTO event(title,description,locationLat,locationLong,date,time,user_id) VALUES(?,?,?,?,?,?,?)";
		Object arguments[] = new Object[]{eventTitle,eventDescription,lat,log,eventDate,eventTime,user_id};
				
		
		if(MySQL.execute(mysql_string, arguments) == false)
		{
			writer.write("error");
			
		}
		else
		{
			String lastIncrement = "SELECT LAST_INSERT_ID()";
			int result_types[] = new int[]{MySQL.INTEGER};
			Object[] arguments1 = new Object[0];
			ArrayList<Object[]> results;
			
			
			results = MySQL.executeQuery(lastIncrement, arguments1, result_types);
			if(results == null)
			{
				System.out.println("addEventServlet: null results");
				writer.close();
				return;
			}
			
			int eventID = (int) results.get(0)[0];
			System.out.println("eventID: " + eventID);
			session.setAttribute("eventID",Integer.toString(eventID));
			writer.write("true");
		}
		writer.close();
		return;
		
	
	}
	
	
	

    
    
    
    
    
	
	
	private void getImage(HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session = request.getSession();

		System.out.println("savePath = " + SAVE_DIR);
		
		// creates the save directory if it does not exists
        File fileSaveDir = new File(SAVE_DIR);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
        
        
        try {
        	for (Part part : request.getParts())
        	{
        		part.getSubmittedFileName();
			    String fileName = extractFileName(part);			  	    
			    String saveName = (String) session.getAttribute("eventID");  
			    part.write(SAVE_DIR + File.separator + saveName + getExtension(fileName));
			}
		} catch (IOException | ServletException e1)
        {
			e1.printStackTrace();
		}
        
        
	
		
		
		
		
	}
	
	private static String getFilename(Part part) {
	    for (String cd : part.getHeader("content-disposition").split(";")) {
	        if (cd.trim().startsWith("filename")) {
	            String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
	            return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
	        }
	    }
	    return null;
	}
	
	  private String extractFileName(Part part) {
	        String contentDisp = part.getHeader("content-disposition");
	        String[] items = contentDisp.split(";");
	        for (String s : items) {
	            if (s.trim().startsWith("filename")) {
	                return s.substring(s.indexOf("=") + 2, s.length()-1);
	            }
	        }
	        return "";
	    }
	
	  
	  private String getExtension(String fileName)
	  {
		  return fileName.substring(fileName.lastIndexOf('.'));
		  
	  }
	  
	  
	

}
