
function getProfile(){
	
	document.getElementById("profilePicture").src = "http://localhost:8080/Profile/"+sessionStorage.userID +".jpg";
	document.getElementById("profileName").innerHTML = sessionStorage.firstname + " " + sessionStorage.lastname;
	document.getElementById("profileEmail").innerHTML = sessionStorage.email;
	document.getElementById("profileAboutMe").innerHTML = sessionStorage.aboutme;
	console.log("success!");
}



