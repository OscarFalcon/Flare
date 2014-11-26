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
		
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("user_id");
		PrintWriter writer = response.getWriter();
		
		System.out.println("MapServlet:POST");
		System.out.println("MapServlet:POST: user_id = " + user_id);
		
		if(user_id == null)
		{
			System.out.println("MapServlet:POST: username is null");
			return;
		}
		String mysql_string = "SELECT title,description,locationLat,locationLong,date,time,id FROM event WHERE user_id = ?";
		ArrayList<Object[]> results;
		Object[] arguments = new Object[]{user_id};
		int[] result_types = new int[]{MySQL.STRING,MySQL.STRING,MySQL.BIG_DECIMAL,
				MySQL.BIG_DECIMAL,MySQL.STRING,MySQL.STRING,MySQL.INTEGER};
		
		
		
		results = MySQL.executeQuery(mysql_string, arguments, result_types);
		if(results == null)
		{
			writer.write("error");
			writer.close();
			return;
		}
		String responseString = "";
		
		for(int i = 0; i < results.size();i++)
		{
			Object tmp[];
			String line; 
			
			tmp = results.get(i);
			line = (String)tmp[0] + "|" + (String)tmp[1] + "|" +
					((BigDecimal)tmp[2]).toString() + "|" + ((BigDecimal)tmp[3]).toString() + "|" +
					(String)tmp[4] + "|" + (String)tmp[5] + "|" + (int)tmp[6];
			
			responseString = responseString + "#" + line;
			
		}
		if(responseString.length() > 0)
			responseString = responseString.substring(1);
		
		
		System.out.println(responseString);
		writer.write(responseString);
		writer.close();
		return;
		
	}

}
