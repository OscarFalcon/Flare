function recoverAccount()
{
	
	var url = "http://localhost:8080/recoverAccount";
	var method = "POST";
	var postData;
	var async = true;
	
	postData = "email=" + email;
			   
	var request = new XMLHttpRequest();
	request.onload = function () {

		var status = request.status; // HTTP response status, e.g., 200 for "200 OK"
		var response = request.responseText; // Returned data, e.g., an HTML document.
		console.log("status: " + status);
		console.log("response: " + response);
	   
		if(status == 200 && response == "true")
		{
			document.getElementById('error').innerHTML="<font color=\"green\">Reset Account!</font>";
			alert("Updated Account")
			document.getElementById('error').innerHTML="<font color=\"green\">Redirecting to login page.. </font>";
			sleep(1000);
			window.location.replace("http://localhost:8080/recoverAccount");
		   		  
		}
		else
		{
			document.getElementById('error').innerHTML="<font color=\"red\">Failed to enter correct e-mail address </font>";
		}
	   
	}
	request.open(method,url,async);
	request.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	request.send(postData); 
	return false;
	
}


