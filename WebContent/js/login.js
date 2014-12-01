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
	   
	   if(status == 200 && response == "true")
	   {
		   window.location.replace("http://localhost:8080/home"); // everything was good should
		   //window.location.href="#page2";
	   }
	   else
	   {
		   document.getElementById('error').innerHTML="<font color=\"red\">Invalid Credentials</font>";
	   }
	   
	   //parse out json
	   
	}
	request.open("POST","http://localhost:8080/login",true);
	request.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	request.send(postData); 
	return;

	
}