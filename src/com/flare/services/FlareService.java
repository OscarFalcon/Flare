package com.flare.services;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.flare.database.MySQL;

public class FlareService
{



public static String getEvents(int user_id)
{
	String mysql_string = "SELECT event.id,event.title,event.description,event.locationLat,"
			  + "event.locationLong,event.date,event.time,event.type,event.attending,"
			  + "event.checked_in,friends.friend_id,person.username from event INNER JOIN"
			  + " friends ON friends.friend_id = event.user_id INNER JOIN person ON "
			  + "friends.friend_id = person.user_id WHERE friends.user_id = ?";

	ArrayList<Object[]> results;
	Object[] arguments = new Object[]{user_id};
	int[] result_types = new int[]{MySQL.INTEGER,MySQL.STRING,MySQL.STRING,MySQL.BIG_DECIMAL,MySQL.BIG_DECIMAL,MySQL.STRING,
						   MySQL.STRING,MySQL.STRING,MySQL.INTEGER,MySQL.INTEGER,MySQL.INTEGER,MySQL.STRING};
	
	
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
	String eventTitle,eventDescription,eventDate,eventTime,eventType,friendUsername;
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
	friendUsername = (String) tmp[11];
	
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
		"\"friendId\":" + "\""+friendId + "\"," +
		"\"friendUsername\":" +"\""+ friendUsername + "\""
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



}
