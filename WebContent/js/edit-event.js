var id = getUrlVars()["id"];

function getEditEvent(){
	console.log("getEditEvent" + sessionStorage["title"+id]);
	document.getElementById("eventpicture").src = "http://localhost:8080/Events/"+id+".jpg";
	document.getElementById("eventTitleField").value = sessionStorage["title"+id];
	document.getElementById("eventDescriptionField").value = sessionStorage["description"+id];
	document.getElementById("eventDateField").value = sessionStorage["date"+id];
	document.getElementById("eventTimeField").value = sessionStorage["time"+id];
}


function getUrlVars() {
    var vars = {};
    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
        vars[key] = value;
    });
    return vars;
}


function deleteEvent(){
	var xhr = new XMLHttpRequest();
	xhr.onload = function (){
		if(xhr.status == 200){
			console.log("deleteEvent status: "+xhr.responseText);
			if(xhr.responseText == "true"){
				alert("Successfully Deleted Event");
				redirectTo("created-events");
			} else {
				alert("Delete Event Error");
			}
		}
	}
	
	xhr.open("POST", window.location.href, false);
	xhr.send();
}



function editEvent(){
	var title = document.getElementById("eventTitleField").value;
	var description = document.getElementById("eventDescriptionField").value;
	var date = document.getElementById("eventDateField").value;
	var time = document.getElementById("eventTimeField").value;
	
	//Becareful with these!
	var picture = document.getElementById("myfile").files[0];
	var location = document.getElementById("locationField").value;
	
	//Data checks!
	
	
	var formData = new FormData();
	formData.append("title", title);
	formData.append("description", description);
	formData.append("date", date);
	formData.append("time", time);
	
	var request = new XMLHttpRequest();
	request.onload = function () {
		var status = request.status; // HTTP response status, e.g., 200 for "200 OK" (Request was successful)
		console.log("status: " + status);
		
		if(status == 200){
			alert("successfully edited event");
			window.location.replace("http://localhost:8080/created-events"); // everything was good should
			console.log("success");
		} else {
			alert("something went wrong!");
		}
	}
	
	request.open("POST", "http://localhost:8080/editEvent", false);
	request.send(formData);
	
}