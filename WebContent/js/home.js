
function getEvents(){
	var xhr = new XMLHttpRequest();
	xhr.onload = function () 
	{
		if(xhr.status == 200)
		{
			console.log("response: " + xhr.responseText);
			response = JSON.parse(xhr.responseText);
			
			var htmlCode = "";
			
			for (var i=0; i<response.events.length; i++)
			{
				console.log("attending" + response.events[i].attending);
				
				var attendingButton = "";
				if(response.events[i].amIattending == "yes")
				{
					attendingButton = '<button id="'+response.events[i].eventId +'" type="button" class="attendingEventButton" onclick="processRequest(this.id)">Going!</button>';
				}
				else
				{
					attendingButton = '<button id="'+response.events[i].eventId +'" type="button" class="notAttendingEventButton" onclick="processRequest(this.id)">Attend Event</button>';
				}
				
				
				
				htmlCode += ""+
					'<div data-role="content">' +
						'<h2 style="text-align:center">' + response.events[i].eventTitle + '<h2>' +
						'<div style=" text-align:center" data-controltype="image">' +
							'<img style="max-width: 100%; width: 400px; height: auto; max-height: 300px;" src="http://localhost:8080/Events/'+response.events[i].eventId+'.jpg"' + 'onerror="if (this.src != \'/Events/error.jpg\') this.src=\'/Events/error.jpg\';">' +
						'</div>' +
						'<h3 style=" text-align:center">' + 
							'<img class="round" style="width: 50px; height: 50px" src="http://localhost:8080/Profile/' + response.events[i].friendId + '.jpg">By <a onclick="redirectTo(\'profile?friend_id='+response.events[i].friendId+'\');">' + response.events[i].friendUsername+'</a> on ' + response.events[i].eventDate + response.events[i].eventTime +
							'<br></br>' + 
							'<b id ="checkInLabel' +response.events[i].eventId + '" style="font-size:15px">Attending: ' +  response.events[i].attending  + '</b>' + 
						'</h3>' +
						'<div style="text-align:center">' +
							attendingButton +
						'</div>' + 
						'<h3 style="text-align:center"><b>Details</b></h3>' + 
						'<div data-controltype="htmlblock" style="text-align:center">' +
							'<p>' + response.events[i].eventDescription + '</p>' +
						'</div>' +
					'</div>' +
					'<hr style="height:3px; background-color:#ccc; border:0; margin-top:12px; margin-bottom:12px;">';
			}
			
			document.getElementById("events").innerHTML = htmlCode;
			console.log("success!");
		}
		
	};
	
	xhr.open("POST", "http://localhost:8080/home", false);
	xhr.send();
}

function checkIn(eventId)
{
	var labelId = "checkInLabel" + eventId;
	console.log(eventId);
	
	var postData = "action=goingToEvent&"+
		"eventID="+eventId;
	
	var request = new XMLHttpRequest();
	request.onload = function()
	{
		var status = request.status;
		if(status == 200)
		{
			var content = document.getElementById(labelId).textContent;
			var attending = parseInt(content.split(" ")[1]);
			attending++;
			document.getElementById(labelId).textContent = "Attending: " + attending;
			document.getElementById(eventId).className = "attendingEventButton"; 
			document.getElementById(eventId).textContent = "Going!";
		}
	};
	request.open("POST","http://localhost:8080/AttendingEvent",true);
	request.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	request.send(postData);
}

function checkOut(eventId)
{
	var labelId = "checkInLabel" + eventId;
	console.log(eventId);
	
	var postData = "action=notGoingToEvent&"+
		"eventID="+eventId;
	
	var request = new XMLHttpRequest();
	request.onload = function()
	{
		var status = request.status;
		if(status == 200)
		{
			var content = document.getElementById(labelId).textContent;
			var attending = parseInt(content.split(" ")[1]);
			attending--;
			document.getElementById(labelId).textContent = "Attending: " + attending;
			document.getElementById(eventId).className = "notAttendingEventButton";
			document.getElementById(eventId).textContent = "Attend Event";
			
			
			
		}
	};
	request.open("POST","http://localhost:8080/AttendingEvent",true);
	request.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	request.send(postData);
}

function processRequest(eventId)
{
	if(document.getElementById(eventId).textContent == "Attend Event")
	{
		checkIn(eventId);
		console.log("checkIn action");
	}
	else
	{
		console.log("checkOut action");
		checkOut(eventId);
	}
	
}


