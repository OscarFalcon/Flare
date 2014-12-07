
/**
	Given a file input, reads in the data as an image and sets the 
	source attribute of the img element to that of the newly read 
	in image.
**/

function setPhotoHTML(input,imgElementID)
{
	console.log("setPhotoHTML");
	
	if (!input.files || !input.files[0])
	{
		console.log("bad input file")
		return false;
	}
		
	if(imgElementID == null )
	{
		console.log("html element is null");
		return false;
	}
	var imgElement = document.getElementById(imgElementID);
		
	var reader = new FileReader();
	reader.onload = function(e) {
		  var dataURL = reader.result;
		  imgElement.src = dataURL;
		}

	reader.readAsDataURL(input.files[0]); 
	return true;
}

function getUrlVars() {
    var vars = {};
    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
        vars[key] = value;
    });
    return vars;
}

function redirectTo(contextRoot)
{
	window.location.href = "http://localhost:8080/"+contextRoot;
	return false;
}


function addFriend(friend_id){
	var user_id=sessionStorage.getItem("userID");
	var postData;
	postData="userID="+user_id+
	"&friendID="+friend_id;
	
	var request = new XMLHttpRequest();
	request.onload = function () {

	   var status = request.status; // HTTP response status, e.g., 200 for "200 OK" (Request was successful)
	   var response = request.responseText; // Returned data, e.g., an HTML document.
	   console.log("status: " + status);
	   console.log("response: " + response);
	   
	   if(status == 200 && response != "false")
	   {
		   alert("successfully followed friend!");
	   }
	   else {
		   alert("already following");
	   }
	}
	request.open("POST","http://localhost:8080/discover",true);
	request.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	request.send(postData); 
	return;
	
}

