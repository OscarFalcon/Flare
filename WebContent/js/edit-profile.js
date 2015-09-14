/**
 * Edit Profile
 */


function loadEditProfile() {
	document.getElementById("navProfilePicture").src = "http://localhost:8080/Profile/"+sessionStorage.userID +".jpg";
	document.getElementById("navUsername").innerHTML = sessionStorage.username;

	document.getElementById("profilePicture").src = "http://localhost:8080/Profile/"+sessionStorage.userID +".jpg";
	document.getElementById("profileUsername").innerHTML = sessionStorage.username;
	document.getElementById("aboutmeField").value = sessionStorage.aboutme;
	document.getElementById("profileNameField").value = sessionStorage.fullname;
	document.getElementById("emailField").value = sessionStorage.email;
	

	
}



function editProfile()
{	
	var email = document.getElementById("emailField").value;
	var fullname = document.getElementById("profileNameField").value;
	var aboutme = document.getElementById("aboutmeField").value;
	
	
	//These we have to be careful with!
	var picture = document.getElementById("myfile").files[0];
	//var password = document.getElementById("passwordField").value;
	//var verify = document.getElementById("verifyField").value;
	var password = '';
	var verify = '';
	
	if (email == '' || aboutme == '' || fullname == '')
	{
		document.getElementById('error').innerHTML="<font color=\"red\">Missing information </font>";
		return false;
	} else if(password == '' || verify == ''){
		password = sessionStorage.password;
		verify = sessionStorage.password;
	}
	
	//if(password != verify )
	//{
	//	document.getElementById('error').innerHTML="<font color=\"red\">Passwords do not match</font>";
	//	return false;
	//}
	var url = "http://localhost:8080/editprofile";
	var async = false;
	
	
	var formData = new FormData();
	formData.append("email", email);
	formData.append("fullname", fullname);
	formData.append("aboutme",aboutme);
	formData.append("password", password);
	formData.append("profilepicture", picture);
	
	console.log(email);
	console.log(fullname);
	console.log(password);
	
	var request = new XMLHttpRequest();
	request.onload = function () {

	   var status = request.status; // HTTP response status, e.g., 200 for "200 OK" (Request was successful)
	   console.log("status: " + status);
	   
	   if(status == 200)
	   {
		   initUser(sessionStorage.username, password, sessionStorage.userID, email, fullname, aboutme);
		   window.location.replace("http://localhost:8080/profile"); // everything was good should
		   console.log("success");
	   }
	   else
	   {
		   document.getElementById('error').innerHTML="<font color=\"red\">Unable to update profile</font>";
	   }
	   
	}
	request.open("POST",url,async);
	request.send(formData); 
	
}
