var event = [];


function getCreatedEvents(){
	var postData = "";
	if(friendID = getUrlVars()["friend_id"]){
		postData = "friendID="+friendID;
		console.log("friendID="+friendID);
	}
	var xhr = new XMLHttpRequest();
	xhr.onload = function () 
	{
		if(xhr.status == 200){
			console.log("response: " + xhr.responseText);
			var response = JSON.parse(xhr.responseText);
			var htmlCode = "";
			
			for (i=0; i<response.events.length; i++){
				var id = response.events[i].eventId;
				var title = response.events[i].eventTitle;
				var description = response.events[i].eventDescription;
				var date = response.events[i].eventDate;
				var time = response.events[i].eventTime;
				var attending = response.events[i].attending;
				event["title"+id] = title;
				event["description"+id]=description;
				event["date"+id]=date;
				event["time"+id]=time;
				event["attending"+id]=attending;
				console.log("Loop" + i + "array: "+event["title"+id]);
				htmlCode += ""+
					'<div data-role="content">' +
						'<h2 id="title'+id+'" style="text-align:center">' + title + '<h2>' +
						'<h3 style="text-align:center">'+date+" "+time+" "+
	                    	'<b style="font-size:15px">'+
	                        	response.events[i].attending + ' going'+ //This needs to be added
	                        '</b>'+
	                    '</h3>'+
						'<div style=" text-align:center" data-controltype="image">' +
							'<img style="max-width: 100%; width: 400px; height: auto; max-height: 300px;" src="http://localhost:8080/Events/'+id+'.jpg"' + 'onerror="if (this.src != \'/Events/error.jpg\') this.src=\'/Events/error.jpg\';">' +
						'</div>';
						if(!friendID){
							htmlCode += '<div style="text-align:center;"><button style="font-size:16px;" onclick="redirectTo(\'edit-event?id='+id+'\');">'+
			                    	'Edit' +
			                    '</button></div>';
						}
					htmlCode += '</div>' +
					'<hr style="height:3px; background-color:#ccc; border:0; margin-top:12px; margin-bottom:12px;">';
			}
			document.getElementById("events").innerHTML = htmlCode;
			console.log("success!");
		}
	}
	
	xhr.open("POST", "http://localhost:8080/created-events", true);
	xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xhr.send(postData);
}