package com.flare.servlets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
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

@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
maxFileSize=1024*1024*10,      					// 10MB
maxRequestSize=1024*1024*50)   					// 50MB


@WebServlet("/editprofile")
public class EditProfileServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("EditProfileServlet:GET");		
		validate(request,response,"editprofile");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userID;
		HttpSession session;
		final String SAVE_DIR = System.getProperty("user.home") + "/git/Flare/WebContent/Profile";
		
		Object arguments[] = new Object[6];
		
		
		System.out.println("EditProfileServlet:POST");
		
		if(request == null)
		{
			System.out.println("null request");
			return;
		}
		
		session = request.getSession();
		userID = (String) session.getAttribute("user_id");
		
		Part part = null;
		Iterator<Part> parts = request.getParts().iterator();
		
		try{
			for(int i=0; i<5; i++)
			{
				if(parts.hasNext() == false)
				{
					System.out.println("Insufficient Data");
					response.setStatus(400);
					return;
				}
				part = parts.next();
				System.out.println("name: " + part.getName());
				InputStream in = part.getInputStream();
				String value = IOUtils.toString(in, "UTF-8");
				System.out.println("value: " + value);
				arguments[i] = value;
			}
			
		} catch(IOException e){
			e.printStackTrace();
		}
		
		arguments[5] = userID;
		
		String updateProfile = "UPDATE person SET email = ?, first_name = ?, last_name = ?"
							+ ", aboutme = ?, password = ? WHERE user_id = ?";
		
		boolean result;
				
		result = MySQL.execute(updateProfile, arguments);
		if(result == false)
		{
			response.setStatus(400);
			return;
		}
		
		if(!parts.hasNext())
		{
			response.setStatus(400);
			return;
		}
		
		part = parts.next();
		part.write(SAVE_DIR + File.separator + userID + ".jpg");
		return;
	}

}
