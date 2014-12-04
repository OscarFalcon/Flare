var fileInput;
var file;
var newEvent = [];


function confirmEvent()
{
	newEvent["title"] = document.getElementById("titleInput").value;
	newEvent["description"] = document.getElementById("descriptionInput").value;
	newEvent["date"] = document.getElementById("dateInput").value;
	newEvent["time"] = document.getElementById("timeInput").value;
	localStorage.setItem("title", document.getElementById("titleInput").value);
	localStorage.setItem("description", document.getElementById("descriptionInput").value);
	localStorage.setItem("date", document.getElementById("dateInput").value);
	localStorage.setItem("time", document.getElementById("timeInput").value);
	
	
	alert("EventTitle - addEvent.html:" + newEvent["title"]);	
	file = document.getElementById("myfile").files[0]
	//var eventAddress = document.getElementById("locationinput").value;

	console.log("uploadData");
	
	if( newEvent["title"] == '' || newEvent["description"] == '' || newEvent["date"] == '' || newEvent["time"] == '')
	{
		console.log("null values");
		return false;
	} else {
		redirectTo("confirm-event");
	}
}

function changeInput(input)
{
	console.log("changeInput");
	fileInput = input;
}




