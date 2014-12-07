
function getEvents(){
	var xhr = new XMLHttpRequest();
	xhr.onload = function () 
	{
		if(xhr.status == 200){
			console.log("response: " + xhr.responseText);
			var response = JSON.parse(xhr.responseText);
			
			var htmlCode = "";
		
			
			
			for (i=0; i<response.events.length; i++){
				console.log("Loop" + i);
				htmlCode += ""+
					'<div data-role="content">' +
						'<h2 style="text-align:center">' + response.events[i].eventTitle + '<h2>' +
						'<div style=" text-align:center" data-controltype="image">' +
							'<img style="max-width: 100%; width: 400px; height: auto; max-height: 300px;" src="http://localhost:8080/Events/'+response.events[i].eventId+'.jpg"' + 'onerror="if (this.src != \'/Events/error.jpg\') this.src=\'/Events/error.jpg\';">' +
						'</div>' +
						'<h3 style=" text-align:center">' + 
							'<img class="round" style="width: 50px; height: 50px" src="http://localhost:8080/Profile/' + response.events[i].friendId + '.jpg">By <a onclick="redirectTo(\'profile?friend_id='+response.events[i].friendId+'\');">' + response.events[i].friendUsername+'</a> on ' + response.events[i].eventDate + response.events[i].eventTime +
							'<b style="font-size:15px"> 553 going </b>' + 
						'</h3>' +
						'<div style="text-align:center"><button type="button" style="font-size:16px;">Check In</button></div>' + 
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
		
	}
	
	xhr.open("POST", "http://localhost:8080/home", false);
	xhr.send();
}