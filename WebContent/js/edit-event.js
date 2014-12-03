var id = getUrlVars()["id"];

function getEditEvent(){
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
