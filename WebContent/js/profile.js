
function getProfile(){
	document.getElementById("profilePicture").src = "http://localhost:8080/Profile/"+sessionStorage.userID +".jpg";
	document.getElementById("profileName").innerHTML = sessionStorage.firstname + " " + sessionStorage.lastname;
	document.getElementById("profileEmail").innerHTML = sessionStorage.email;
	document.getElementById("profileAboutMe").innerHTML = sessionStorage.aboutme;
	console.log("success!");
}


function logout()
{
	var xhr = new XMLHttpRequest();
	xhr.onload = function ()
	{
		if(xhr.status == 200)
			{
				redirectTo("login");
			}
	};
	xhr.open("POST", "http://localhost:8080/logout",false);
	xhr.send();
	
}


