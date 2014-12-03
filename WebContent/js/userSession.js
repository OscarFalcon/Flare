
function initUser(username, password, userID, email, firstname, lastname, aboutme)
{
	console.log("initUser: userSession")
	sessionStorage.userID = userID;
	sessionStorage.username = username;
	sessionStorage.password = password;
	sessionStorage.email = email;
	sessionStorage.firstname = firstname;
	sessionStorage.lastname = lastname;
	sessionStorage.aboutme = aboutme;
}



function saveEvent(eventTitle,eventDescription,eventDate,eventTime)
{
	sessionStorage.eventTitle = document.getElementById("titleInput").value;
	sessionStorage.eventDescription = document.getElementById("descriptionInput").value;
	sessionStorage.eventDate = eventDate = document.getElementById("dateInput").value;
	sessionStorage.eventTime = eventTime = document.getElementById("timeInput").value;
	
	
	console.log(sessionStorage.eventTitle);
	console.log(sessionStorage.eventDescription);
	console.log(sessionStorage.eventDate);
	console.log(sessionStorage.eventTime);
	
	dfgdfg
	
}