

function getProfile(){
	var xhr = new XMLHttpRequest();
	xhr.onload = function ()
	{
		if(xhr.status == 200)
			{
				console.log("response: "+xhr.response);
				document.getElementById("")
				console.log("success!");
			}
	}
	
	xhr.open("POST", "http://localhost:8080/profile",false);
	xhr.send();
}