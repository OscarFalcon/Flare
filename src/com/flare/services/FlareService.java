package com.flare.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import com.flare.database.MySQL;

public class FlareService
{	
	

public static String deleteFriend(String userID, String friendID){
	String status = "false";
	String mysql_string = "DELETE from friends where user_id = ? and friend_id = ?"; 
	Object[] arguments = new Object[]{userID, friendID};

	if(MySQL.execute(mysql_string, arguments)){
		status = "true";
		System.out.println("Deleted friend: "+friendID);
	}
	else 
		System.out.println("SQL execute failed");
	
	return status;
}

public static String addFriend(String userID, String friendID){
	String status = "false";
	//check if friend already exists
	String mysql_string  = "SELECT * from friends where user_id = ? and friend_id = ?";
	Object[] arguments = new Object[]{userID, friendID};
	int[] result_types = new int[]{MySQL.INTEGER, MySQL.INTEGER};
	ArrayList<Object[]> results;
	
	results = MySQL.executeQuery(mysql_string, arguments, result_types);
	if(results.isEmpty()){
		//add friend to user
		mysql_string = "INSERT into friends(user_id, friend_id) VALUES(?,?)";
		arguments = new Object[]{userID, friendID};
		
		if(MySQL.execute(mysql_string, arguments)){
			status="true";
			System.out.println("Added friend "+friendID);
		}
	}
	
	return status;
}
	
public static String deleteEvent(String event_id){
	String mysql_string = "DELETE from event where id=?";
	Object[] arguments = new Object[]{event_id};
	
	if(MySQL.execute(mysql_string, arguments))
		return "true";
	else 
		return "false";
}

public static String getCreatedEvents(int user_id){
	String mysql_string = "SELECT event.id,event.title,event.description,"
			  + "event.date,event.time,event.type,event.attending,"
			  + "event.checked_in from event WHERE user_id = ?";
	ArrayList<Object[]> results;
	Object[] arguments = new Object[]{user_id};
	int[] result_types = new int[]{MySQL.INTEGER,MySQL.STRING,MySQL.STRING,MySQL.STRING,MySQL.STRING,MySQL.STRING,MySQL.INTEGER,MySQL.INTEGER};
	
	results = MySQL.executeQuery(mysql_string, arguments, result_types);
	
	if(results.isEmpty())
	{
		return null;
	}
	
	StringBuilder json = new StringBuilder("{ \"events\": [");
	
	int size = results.size();
	for(int i = 0; i < size;i++)
	{
		Object tmp[]; 
		String line = null;
		tmp = results.get(i);
		String eventTitle,eventDescription,eventDate,eventTime,eventType;
		int eventId,eventAttending,eventCheckedIn;
	
		eventId = (int) tmp[0];
		eventTitle = (String) tmp[1];
		eventDescription = (String) tmp[2];
		eventDate = (String) tmp[3];
		eventTime = (String) tmp[4];
		eventType = (String) tmp[5];
		eventAttending = (int) tmp[6];
		eventCheckedIn = (int) tmp[7];

	
		line  = "{ \"eventId\":" + "\"" + eventId + "\"," +
				"\"eventTitle\":" + "\""+ eventTitle + "\"," +
				"\"eventDescription\":" + "\"" + eventDescription + "\"," +
				"\"eventDate\":" + "\"" + eventDate + "\"," +
				"\"eventTime\":" + "\"" + eventTime +"\"," +
				"\"eventType\":" + "\"" + eventType + "\"," +
				"\"attending\":" + "\""+eventAttending + "\"," +
				"\"checkedIn\":" + "\""+eventCheckedIn +"\"" +
				"}";
			if( (i + 1) != size)
			{
			line = line + ",";
			}
			
			json.append(line);	
		}
		json.append("]}");
		return json.toString();
}
	
	
public static String getEvents(int user_id)
{
	String mysql_string = "SELECT event.id,event.title,event.description,event.locationLat," +
			"event.locationLong,event.date,event.time,event.type,event.attending," + 
			"event.checked_in,friends.friend_id,person.username,attending_event.user_id " + 
			"FROM event INNER JOIN friends ON friends.friend_id = event.user_id " +
			"INNER JOIN person ON friends.friend_id = person.user_id LEFT JOIN attending_event ON " +
			"event.id = attending_event.event_id AND attending_event.user_id = ? WHERE friends.user_id = ?";
			
			
			

	ArrayList<Object[]> results;
	Object[] arguments = new Object[]{user_id,user_id};
	int[] result_types = new int[]{MySQL.INTEGER,MySQL.STRING,MySQL.STRING,MySQL.BIG_DECIMAL,MySQL.BIG_DECIMAL,MySQL.STRING,
						   MySQL.STRING,MySQL.STRING,MySQL.INTEGER,MySQL.INTEGER,MySQL.INTEGER,MySQL.STRING,MySQL.INTEGER};
	
	
	results = MySQL.executeQuery(mysql_string, arguments, result_types);
	
	if(results == null)
	{
		return null;
	}
	
	StringBuilder json = new StringBuilder("{ \"events\": [");
	
	int size = results.size();
	for(int i = 0; i < size;i++)
	{
		Object tmp[]; 
		String line = null;
		tmp = results.get(i);
		String eventTitle,eventDescription,eventDate,eventTime,eventType,friendUsername,amIattending;
		BigDecimal locationLat,locationLog;
		int eventId,eventAttending,eventCheckedIn,friendId,amIattendingId;
	
	
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
		friendUsername = (String) tmp[11];
		amIattendingId = (int) tmp[12];
		
		if(amIattendingId == 0)
			amIattending = "no";
		else
			amIattending = "yes";
		
		
		
		line  = "{ \"eventId\":" + "\"" + eventId + "\"," +
			"\"eventTitle\":" + "\""+ eventTitle + "\"," +
			"\"eventDescription\":" + "\"" + eventDescription + "\"," +
			"\"eventDate\":" + "\"" + eventDate + "\"," +
			"\"eventTime\":" + "\"" + eventTime +"\"," +
			"\"eventType\":" + "\"" + eventType + "\"," +
			"\"locationLat\":" +"\""+ locationLat + "\"," +
			"\"locationLog\":" +"\""+locationLog + "\"," + 
			"\"attending\":" + "\""+eventAttending + "\"," +
			"\"checkedIn\":" + "\""+eventCheckedIn +"\"," +
			"\"friendId\":" + "\""+friendId + "\"," +
			"\"friendUsername\":" +"\""+ friendUsername + "\","+
			"\"amIattending\": " +"\""+amIattending +"\""
			+ "}";
		
		if( (i + 1) != size)
		{
		line = line + ",";
		}
		
		json.append(line);	
	}
	json.append("]}");
	return json.toString();
}

public static String getFriends(int user_id)
{
	String mysql = "SELECT person.user_id, person.username, person.email,person.full_name,"
				   + "person.aboutme FROM friends INNER JOIN person ON person.user_id = friends.friend_id "
				   + "WHERE friends.user_id = ?";
	Object[] arguments = {user_id};
	int[] result_types = {MySQL.STRING,MySQL.STRING,MySQL.STRING,MySQL.STRING,MySQL.STRING};
	
	ArrayList<Object[]> results = MySQL.executeQuery(mysql, arguments, result_types);
	
	if(results == null)
	{
		return null;
	}
	
	String friend_id, username,email,fullName,aboutMe;
	
	StringBuilder json = new StringBuilder("{\"friends\":[");
	int size = results.size();
	for(int i = 0; i < size; i++)
	{
		Object[] row = results.get(i);
		friend_id = (String) row[0];
		username = (String) row[1];
		email = (String) row[2];
		fullName = (String) row[3];
		aboutMe = (String) row[4];
		
		String line = "{\"friend_id\":" + "\""+friend_id+"\"," +
				"\"username\":" + "\""+username + "\"," +
				"\"email\":" + "\""+email + "\"," +
				"\"fullName\":" + "\""+fullName +"\"," +
				"\"aboutMe\":" + "\"" + aboutMe  + "\"" + 
				"}";
		
		if( i + 1 != size)
		{
			line += ",";
		}
		
		json.append(line);
	}
	json.append("]}");	
	return json.toString();
}


public static String getAllUsers(int user_id)
{
	String mysql = "SELECT user_id, username, email,full_name,"
				   + "aboutme FROM person where user_id != ?";
	Object[] arguments = {user_id};
	int[] result_types = {MySQL.STRING,MySQL.STRING,MySQL.STRING,MySQL.STRING,MySQL.STRING};
	
	ArrayList<Object[]> results = MySQL.executeQuery(mysql, arguments, result_types);
	
	if(results == null)
	{
		return null;
	}
	
	String friend_id, username,email,fullName,aboutMe;
	
	StringBuilder json = new StringBuilder("{\"friends\":[");
	int size = results.size();
	for(int i = 0; i < size; i++)
	{
		Object[] row = results.get(i);
		friend_id = (String) row[0];
		username = (String) row[1];
		email = (String) row[2];
		fullName = (String) row[3];
		aboutMe = (String) row[4];
		
		String line = "{\"friend_id\":" + "\""+friend_id+"\"," +
				"\"username\":" + "\""+username + "\"," +
				"\"email\":" + "\""+email + "\"," +
				"\"firstName\":" + "\""+fullName +"\"," +
				"\"aboutMe\":" + "\"" + aboutMe  + "\"" + 
				"}";
		
		if( i + 1 != size)
		{
			line += ",";
		}
		
		json.append(line);
	}
	json.append("]}");	
	return json.toString();
}






}
