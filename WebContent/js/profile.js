
function getProfile(){
	var xhr = new XMLHttpRequest();
	xhr.onload = function ()
	{
		if(xhr.status == 200)
			{
				console.log("response: "+xhr.response);
				var response = JSON.parse(xhr.responseText);
				document.getElementById("profilePicture").src = "/profile/"+response["userid"]+".jpg";
				document.getElementById("profileName").innerHTML = response["firstname"] + " " + response["lastname"];
				document.getElementById("profileEmail").innerHTML = response["email"];
				document.getElementById("profileAboutMe").innerHTML = response["aboutme"];
				console.log("success!");
			}
	}
	
	xhr.open("POST", "http://localhost:8080/profile",false);
	xhr.send();
	
	
}