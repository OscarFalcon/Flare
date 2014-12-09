var selection = [];
var value;
$(document).keypress(function(e) {
    if(e.which == 13) {
    	sessionStorage.value = document.getElementById("searchinput2").value;
    	redirectTo('discover');
    }
});
function loadDiscover(){
	var xhr = new XMLHttpRequest();
	xhr.onload = function (){
		if(xhr.status == 200){
			console.log("response: " + xhr.responseText);
			var response = JSON.parse(xhr.responseText);
			
			
						
			var htmlCode = "";
			for (i=0; i<response.friends.length; i++){
			//	selection.push(response.friends[i].friend_id);
				selection.push(response.friends[i].firstName + " " + response.friends[i].lastName);
				selection.push(response.friends[i].username);
				$("#searchinput2").autocomplete( { source: selection });

				
				
				console.log("value is " + sessionStorage.value);
				if(sessionStorage.value == response.friends[i].username || sessionStorage.value == response.friends[i].firstName + " " + response.friends[i].lastName)
				
				{
					htmlCode += ""+
					'<div data-role="content">'+
						'<div style="" data-controltype="image">'+
							'<img class="round" style=" float: left; width: 75px; height: 75px;" src="http://localhost:8080/Profile/'+ response.friends[i].friend_id+'.jpg" onerror="if (this.src != \'/Profile/error.jpg\') this.src=\'/Profile/error.jpg\';">'+
							'<a style="font-size:20px;" onclick="redirectTo(\'profile?friend_id='+response.friends[i].friend_id+'\');">'+ response.friends[i].firstName+ ' ' + response.friends[i].lastName + '</a></br>'+
							response.friends[i].username +'<button style="float:right;font-size:16px;" type="button" onclick="addFriend('+response.friends[i].friend_id+');">Follow</button>'+
							'<br>'+'<div style="text-align:center;">'+response.friends[i].aboutMe + '</div>'+
						'</div>'+
					'</div>' +
					'<hr style="height:3px; background-color:#ccc; border:0; margin-top:12px; margin-bottom:12px;">';


				}

			}
			
			
			document.getElementById("results").innerHTML = htmlCode;

			console.log("success!");
		}	
	}
	xhr.open("POST", "http://localhost:8080/discover", false);
	xhr.send();
}

