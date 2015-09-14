function createAccount()
{
	var username=document.getElementById("userNameField").value;
	var email=document.getElementById("emailField").value;
	var password=document.getElementById("passwordField").value;
	var verify=document.getElementById("verifyPasswordField").value;
	var fullname=document.getElementById("fullNameField").value;



	console.log("createAccount");
	console.log("username:"+username+"email:"+email+"password:"+password+"full name:"+fullname);

	
	if(username == '' || email == '' || password == '' || verify == '' || fullname == '')
	{
		document.getElementById('error2').innerHTML="<font color=\"red\">Missing information </font>";
		return false;
	}
	
	if(password != verify )
	{
		document.getElementById('error2').innerHTML="<font color=\"red\">Passwords do not match</font>";
		return false;
	}
	
	var url = "http://localhost:8080/signup";
	var method = "POST";
	var postData;
	var async = true;
	
	postData = "username=" + username +
			   "&password=" + password + 
			   "&email=" + email + 
			   "&fullname=" + fullname;
			   
	var request = new XMLHttpRequest();
	request.onload = function () {

		var status = request.status; // HTTP response status, e.g., 200 for "200 OK"
		var response = request.responseText; // Returned data, e.g., an HTML document.
		console.log("status: " + status);
		console.log("response: " + response);
	   
		if(status == 200 && response == "true")
		{
			document.getElementById('error2').innerHTML="<font color=\"green\">Created Account!</font>";
			alert("Created Account!")
			document.getElementById('error2').innerHTML="<font color=\"green\">Redirecting to login page.. </font>";
			sleep(1000);
			window.location.replace("http://localhost:8080/login");
		   		  
		}
		else
		{
			document.getElementById('error2').innerHTML="<font color=\"red\">Failed to create account </font>";
		}
	   
	}
	request.open(method,url,async);
	request.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	request.send(postData); 
	return false;
	
}


function sleep(milliseconds) {
  var start = new Date().getTime();
  for (var i = 0; i < 1e7; i++) {
    if ((new Date().getTime() - start) > milliseconds){
      break;
    }
  }
}
