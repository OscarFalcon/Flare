var id = getUrlVars()["id"];

function getEditEvent(){
	console.log("getEditEvent" + event["title"+id]);
	document.getElementById("eventpicture").src = "http://localhost:8080/Events/"+id+".jpg";
	document.getElementById("eventTitleField").value = event["title"+id];
	document.getElementById("eventDescriptionField").value = event["description"+id];
	document.getElementById("eventDateField").value = event["date"+id];
	document.getElementById("eventTimeField").value = event["time"+id];
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