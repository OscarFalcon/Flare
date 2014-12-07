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
 * Servlet implementation class FriendsServlet
 */
@WebServlet("/friends")
public class FriendsServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
   
    public FriendsServlet() 
    {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("FriendsServlet:GET");		
		validate(request,response,"friends");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("DOPOST:FriendsServlet - userID: "+request.getParameter("userID")+ "friendID: "+request.getParameter("friendID"));
		PrintWriter writer = response.getWriter();
		//get Users friends
		if(request.getParameter("userID") == null && request.getParameter("friendID") == null){	
			String json = FlareService.getFriends(Integer.parseInt((String)request.getSession().getAttribute("user_id")));
			System.out.println(json);
			writer.write(json);
		} else if(request.getParameter("friendID") != null){ // get friends friends
			String json = FlareService.getFriends(Integer.parseInt((String)request.getParameter("friendID")));
			System.out.println(json);
			writer.write(json);
		}else { //delete users friend
			String responseString = FlareService.deleteFriend(request.getParameter("userID"), request.getParameter("friendID"));
			System.out.println("response string from delete friend:"+ responseString);
			writer.write(responseString);
		}
		writer.close();
		return;
	}

}
