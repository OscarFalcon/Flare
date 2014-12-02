function validateForm()
{
	
	var user = document.getElementById("usernameField").value;
	var pass = document.getElementById("passwordField").value;
	
	console.log("login.js: validate form");
	
	if(user == '')
	{
		document.getElementById('error').innerHTML="<font color=\"red\">Please Enter Username</font>";
		return false;
	}
 
	if(pass == '')
	{
		document.getElementById('error').innerHTML="<font color=\"red\">Please Enter Password</font>";
		return false;
	}
	
	
	var url = "localhost:/8080/login";
	var method = "POST";
	var postData;
	var async = true;

	postData = "username=" + user +
			   "&password=" + pass;

	
	
	var request = new XMLHttpRequest();
	request.onload = function () {

	   var status = request.status; // HTTP response status, e.g., 200 for "200 OK" (Request was successful)
	   var response = request.responseText; // Returned data, e.g., an HTML document.
	   console.log("status: " + status);
	   console.log("response: " + response);
	   
	   
	   // Converting JSON Object to Javascript Object
		var person = JSON.parse(response);
		
		// getting the userSession (initUser function)
		//var userSession = require('userSession');
		
		console.log("user email: " + person.user[0].eMail);
		console.log("first name: " + person.user[0].firstName);
		console.log("last name: " + person.user[0].lastName);
		console.log("about me: " + person.user[0].aboutMe);
		console.log("username: " + user);
		console.log("Password: " + pass);
		
		
		//set values for the user
		initUser(user, pass, person.user[0].eMail, person.user[0].firstName, person.user[0].lastName, person.user[0].aboutMe);
	   
	   if(status == 200 && response != false)
	   {
		   window.location.replace("http://localhost:8080/home"); // everything was good should
	   }
	   else
	   {
		   document.getElementById('error').innerHTML="<font color=\"red\">Invalid Credentials</font>";
	   }
	   	   
	}
	request.open("POST","http://localhost:8080/login",true);
	request.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	request.send(postData); 
	return;

	
}