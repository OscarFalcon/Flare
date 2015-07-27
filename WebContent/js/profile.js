var id = getUrlVars()["friend_id"];

function getProfile(){
	if(id){
		console.log("parameterExists!! = " + id );
		var postData;
		postData="friendID="+id;
		var request = new XMLHttpRequest();
		request.onload = function () {
			console.log("response: " + request.responseText);
			var response = JSON.parse(request.responseText);
			document.getElementById("friendsButton").setAttribute("onclick", "redirectTo('friends?friend_id="+id+"');");
			document.getElementById("createdEventsButton").setAttribute("onclick", "redirectTo('created-events?friend_id="+id+"');")
			document.getElementById("logoutButton").remove();
			document.getElementById("editProfileButton").remove();
			document.getElementById("profilePicture").src = "http://localhost:8080/Profile/"+id +".jpg";
			document.getElementById("profilePicture").setAttribute("onerror","if (this.src != '/Profile/error.jpg') this.src='/Profile/error.jpg';")
			document.getElementById("profileName").innerHTML = response.firstname + " " + response.lastname;
			document.getElementById("profileEmail").innerHTML = response.email;
			document.getElementById("profileAboutMe").innerHTML = response.aboutme;
			//var htmlCode = "";
		};
		request.open("POST","http://localhost:8080/profile",true);
		request.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		request.send(postData); 
		return;
	}
	console.log("parameter Does not exist");
	document.getElementById("profilePicture").src = "http://localhost:8080/Profile/"+sessionStorage.userID +".jpg";
	document.getElementById("profilePicture").setAttribute("onerror","if (this.src != '/Profile/error.jpg') this.src='/Profile/error.jpg';")
	document.getElementById("profileNa   me").innerHTML = sessionStorage.firstname + " " + sessionStorage.lastname;
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

