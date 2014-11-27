/**
 * Edit Profile
 */

function editProfile()
{
	
	var picture = document.getElementById("myfile").files[0];
	var email = document.getElementById("emailField").value;
	var firstname = document.getElementById("firstnameField").value;
	var lastname = document.getElementById("lastnameField").value;
	var aboutme = document.getElementById("aboutmeField").value;	
	var password = document.getElementById("passwordField").value;
	
	var url = "http://localhost:8080/editprofile";
	var async = false;
	
	
	var formData = new FormData();
	formData.append("email", email);
	formData.append("firstName", firstname);
	formData.append("lastName", lastname);
	formData.append("aboutme",aboutme);
	formData.append("password", password);
	formData.append("profilepicture", picture);
	
	console.log(email);
	console.log(firstname);
	console.log(password);
	
	var request = new XMLHttpRequest();
	request.onload = function () {

	   var status = request.status; // HTTP response status, e.g., 200 for "200 OK" (Request was successful)
	   console.log("status: " + status);
	   
	   if(status == 200)
	   {
		   window.location.replace("http://localhost:8080/editprofile"); // everything was good should
		   console.log("success");
	   }
	   else
	   {
		   //document.getElementById('error').innerHTML="<font color=\"red\">Unable to update profile</font>";
	   }
	   
	}
	request.open("POST",url,async);
	request.send(formData); 
	

	
}