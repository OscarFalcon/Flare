//Global Object

window.user = {};


function userSession()
{
	
	var xhr = new XMLHttpRequest();
	
	xhr.onload = new function()
	{
		var method = "POST";
		var URL = "http://localhost:8080/login"; 
		var Asynchronous = "true";
		
		if(xhr.status == 200)
		{
			console.log("response: "+xhr.response);
			var response = JSON.parse(xhr.responseText);
			
			document.getElementById("profileId").innerHTML = response["userid"];
			document.getElementById("profilePicture").src = "/profile/"+response["userid"]+".jpg";
			document.getElementById("profileFirstName").innerHTML = response["firstname"];
			document.getElementById("profileLastName").innerHTML = response["lastname"];
			document.getElementById("profileEmail").innerHTML = response["email"];
			document.getElementById("profileAboutMe").innerHTML = response["aboutme"];
			
			console.log("success!");
		}
		
		xhr.open( Method, URL, Asynchronous)
		xhr.send();
	
		
	}

}

function initUser(username, password, email, firstname, lastname, aboutme)
{
	sessionStorage.username = username;
	sessionStorage.password = password;
	sessionStorage.email = email;
	sessionStorage.firstname = firstname;
	sessionStorage.lastname = lastname;
	sessionStorage.aboutme = aboutme;
		
}

