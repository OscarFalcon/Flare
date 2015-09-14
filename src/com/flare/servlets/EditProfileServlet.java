package com.flare.servlets;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
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
import com.flare.services.FlareUtils;

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
		final String SAVE_DIR = getServletContext().getRealPath("/") + "/Profile";
		final String SAVE_DIR_WORKSPACE = System.getProperty("user.home")
				+ File.separator + "git" +File.separator + "Flare" +File.separator + "WebContent" + File.separator + "Profile";
		
		
		Object arguments[] = new Object[5];
		System.out.println("EditProfileServlet:POST");
		
		if(request == null)
		{
			System.out.println("null request");
			return;
		}
		
		session = request.getSession();
		userID = (String) session.getAttribute("user_id");
		
		Part part = null;
		Part photoPart = null;
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
				System.out.println("contentType: " + part.getContentType());
				if(part.getContentType() == null) //data part of request
				{
					System.out.println("name: " + part.getName());
					InputStream in = part.getInputStream();
					String value = IOUtils.toString(in, "UTF-8");
					System.out.println("value: " + value);
					
					switch(part.getName())
					{
					case "email":
						arguments[0] = value;
						break;
					case "fullname":
						arguments[1] = value;
						break;
					case "aboutme":
						arguments[2] = value;
						break;
					case "password":
						arguments[3] = value;
						break;	
					
					}
					
				}
				else
				{
					photoPart = part;
					
				}
					
			}
			
		} catch(IOException e){
			e.printStackTrace();
		}
		
		arguments[4] = userID;
		
		String updateProfile = "UPDATE person SET email = ?, full_name = ?"
							+ ", aboutme = ?, password = ? WHERE user_id = ?";
		
		boolean result;
				
		result = MySQL.execute(updateProfile, arguments);
		if(result == false)
		{
			response.setStatus(400);
			return;
		}	
		if(photoPart == null) //This does not necessarily mean something went wrong, just that no photo needed to be uploaded.
		{
			System.out.println("Photo not updated!");
			response.setStatus(200);
			return;
			
		}
		
		InputStream in = null;
		BufferedImage image = null;
		OutputStream out = null;
		OutputStream out2 = null;
		try
		{
			in = photoPart.getInputStream();
			image = ImageIO.read(in);
			out = new FileOutputStream(SAVE_DIR + File.separator + userID + ".jpg");
			FlareUtils.saveImageAsJPEG(image,out,100);

			out2 = new FileOutputStream(SAVE_DIR_WORKSPACE + File.separator + userID+ ".jpg");
			FlareUtils.saveImageAsJPEG(image,out2,100);
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if( in != null)
			{
				in.close();
			}
			if(out != null)
			{
				out.close();
			}
			image.flush();
		}		
	}
	
}
