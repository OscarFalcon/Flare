function loadDiscover(){
	var xhr = new XMLHttpRequest();
	xhr.onload = function (){
		if(xhr.status == 200){
			console.log("response: " + xhr.responseText);
			var response = JSON.parse(xhr.responseText);
			
			var htmlCode = "";
			for (i=0; i<response.friends.length; i++){
				console.log("Loop" + i);
				htmlCode += ""+
				'<div data-role="content">'+
					'<div style="" data-controltype="image">'+
						'<img class="round" style=" float: left; width: 75px; height: 75px;" src="http://localhost:8080/Profile/'+ response.friends[i].friend_id+'.jpg" onerror="if (this.src != \'/Profile/error.jpg\') this.src=\'/Profile/error.jpg\';">'+
						'<h2>'+ response.friends[i].firstName+ ' ' + response.friends[i].lastName + '</h2></br>'+
						response.friends[i].username +'<button style="float:right;font-size:16px;" type="button" onclick="addFriend('+response.friends[i].friend_id+');">Follow</button>'+
						'<br>'+'<div style="text-align:center;">'+response.friends[i].aboutMe + '</div>'+
					'</div>'+
				'</div>' +
				'<hr style="height:3px; background-color:#ccc; border:0; margin-top:12px; margin-bottom:12px;">';
			}
			
			document.getElementById("results").innerHTML = htmlCode;
			console.log("success!");
		}	
	}
	
	xhr.open("POST", "http://localhost:8080/discover", false);
	xhr.send();
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