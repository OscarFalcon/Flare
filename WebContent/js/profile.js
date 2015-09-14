var id = getUrlVars()["friend_id"];

function getProfile(){
	document.getElementById("navProfilePicture").src = "http://localhost:8080/Profile/"+sessionStorage.userID +".jpg";
	document.getElementById("navUsername").innerHTML = sessionStorage.username;

	if(id){
		console.log("friend Profile = " + id );
		var postData;
		postData="friendID="+id;
		var request = new XMLHttpRequest();
		request.onload = function () {
			console.log("response: " + request.responseText);
			var response = JSON.parse(request.responseText);
			document.getElementById("friendsButton").setAttribute("onclick", "redirectTo('friends?friend_id="+id+"');");
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
	console.log("MyProfile");
	document.getElementById("profilePicture").src = "http://localhost:8080/Profile/"+sessionStorage.userID +".jpg";
	document.getElementById("profilePicture").setAttribute("onerror","if (this.src != '/Profile/error.jpg') this.src='/Profile/error.jpg';")
	document.getElementById("profileUsername").innerHTML = sessionStorage.username;
	document.getElementById("profileName").innerHTML = sessionStorage.fullname;
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

