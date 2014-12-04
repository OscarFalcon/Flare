function recoverAccount()
{
	var username = document.getElementById("userName").value;
	var email = document.getElementById("userEmail").value;
	var password = document.getElementById("newPassword").value;
	
	console.log("recover-account.js: recoverAccount");

	
	console.log("USERNAME: " + userName);
	console.log("EMAIL: " + userEmail);
	console.log("PASSWORD: " + password);
	
	
	var url = "http://localhost:8080/RecoverAccount";
	var method = "POST";
	var async = true;

	if(username == '' || email == '')
	{
		document.getElementById('error').innerHTML="<font color=\"red\">Please Enter valid username and email</font>";
		return false;
	}
	
	var postData;
	postData = "userName=" + username +
			   "&userEmail=" + email +
			   "&newPassword=" + password;
	
	var request = new XMLHttpRequest();
	request.onload = function () {

		var status = request.status; // HTTP response status, e.g., 200 for "200 OK"
		var response = request.responseText; // Returned data, e.g., an HTML document.
		console.log("status: " + status);
		console.log("response: " + response);
	   
		if(status == 200 && response != "false")
		{
			console.log("GOT A RESPONSE");
			document.getElementById('error').innerHTML="<font color=\"green\">Account Reset</font>";
			alert("Updated Account")
			document.getElementById('error').innerHTML="<font color=\"green\">Redirecting to login page.. </font>";
			window.location.replace("http://localhost:8080/login");
		   		  
		}
		else
		{
			document.getElementById('error').innerHTML="<font color=\"red\">Failed to enter correct e-mail address </font>";
		}
	   
	}
	request.open(method,url,async);
	request.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	request.send(postData); 
	return;
	
}


