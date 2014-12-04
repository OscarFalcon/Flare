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
 * Servlet implementation class DiscoverServlet
 */
@WebServlet("/discover")
public class DiscoverServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DiscoverServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("DiscoverServlet:GET");		
		validate(request,response,"discover");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		if(request.getParameter("userID") == null || request.getParameter("friendID") == null){	
			String json = FlareService.getAllUsers(Integer.parseInt((String)request.getSession().getAttribute("user_id")));
			System.out.println(json);
			writer.write(json);
			writer.close();
			return;
		}else {
			String responseString = FlareService.addFriend(request.getParameter("userID"), request.getParameter("friendID"));
			writer.write(responseString);
			writer.close();
		}
	}

}
