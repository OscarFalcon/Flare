/**
 * Edit Profile
 */

function editProfile()
{
	var picture = document.getElementById("profilepicture");
	
	if(picture != null)
		picture = document.getElementById("profilepicture").files[0];
	else
		document.getElementById('error').innerHTML="<font color =\"red\">Unable to get Profile Picture</font>";
	
	var email = document.getElementById("emailField");
	
	if(email != null)
		email =	document.getElementById("emailField").value;
	else
		document.getElementById('error').innerHTML="<font color =\"red\">Unable to get email</font>";
	
	var firstname = document.getElementById("firstnameField");
	
	if(firstname != null)
		firstname = document.getElementById("firstnameField").value;
	else
		document.getElementById('error').innerHTML="<font color =\"red\">Unable to get first name</font>";
	
	var lastname = document.getElementById("lastnameField")
	
	if(lastname != null)
		lastname = document.getElementById("lastnameField").value;
	else
		document.getElementById('error').innerHTML="<font color =\"red\">Unable to get last name</font>";
	
	var aboutme = document.getElementById("aboutmeField");
	
	if(aboutme != null)
		aboutme = document.getElementById("aboutmeField").value;
	else
		document.getElementById('error').innerHTML="<font color =\"red\">Unable to get about me info";
	
	var password = document.getElementById("passwordField");
	
	if(password != null)
		password = document.getElementById("passwordField").value;
	else
		document.getElementById('error').innerHTML="<font color =\"red\">Unable to get password</font>";
	
	var formData = new formData();
	formData.append("email", email);
	formData.append("firstName", firstname);
	formData.append("lastName", lastname);
	formData.append("aboutme",aboutme);
	formData.append("password", password);
	formData.append("profilepicture", picture);
	
	console.log("login.js: edit profile");
	
	
	var url = "localhost:/8080/editprofile";
	var async = true;
	
	var request = new XMLHttpRequest();
	request.onload = function () {

	   var status = request.status; // HTTP response status, e.g., 200 for "200 OK" (Request was successful)
	   var response = request.responseText; // Returned data, e.g., an HTML document.
	   console.log("status: " + status);
	   console.log("response: " + response);
	   
	   if(status == 200)
	   {
		   window.location.replace("http://localhost:8080/editprofile"); // everything was good should
		   console.log("success");
	   }
	   else
	   {
		   document.getElementById('error').innerHTML="<font color=\"red\">Unable to update profile</font>";
	   }
	   
	}
	request.open("POST",url,async);
	request.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	request.send(formData); 
	return;

	
}