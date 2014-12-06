

var photoURL;


function confirmEvent()
{
	localStorage.setItem("title", document.getElementById("titleInput").value);
	localStorage.setItem("description", document.getElementById("descriptionInput").value);
	localStorage.setItem("date", document.getElementById("dateInput").value);
	localStorage.setItem("time", document.getElementById("timeInput").value);
	
	
	var photo = document.getElementById("myfile").files[0];
	console.log("files choosen: " + document.getElementById("myfile").files.length);
	var fileReader = new FileReader();
	fileReader.onload = function(event)
	{
		console.log("onload");
	    localStorage.setItem("photo",event.target.result);
	    redirectTo("confirm-event");
	};
	fileReader.readAsDataURL(photo);
	
}

function changeInput(input)
{
	console.log("changeInput");
	fileInput = input;
}









