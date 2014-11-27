
function getEvents(){
	var xhr = new XMLHttpRequest();
	xhr.onload = function () 
	{
		if(xhr.status == 200){
			console.log("response: " + xhr.responseText);
			var response = JSON.parse(xhr.responseText);
			var size = response["size"];
			
			var htmlCode = "";
			
			for (i=0; i<size; i++){
				console.log("Loop" + i);
				htmlCode += ""+
					"<div data-role=\"content\">" +
						"<h2 style=\"text-align:center\">" + response["eventtitle"+i] + "<h2>" +
					"<div style=\" text-align:center\" data-controltype=\"image\">" +
			            "<img style=\"max-width: 100%; width: 400px; height: auto; max-height: 300px;\" src=\"" + "Event/"+response["eventid" + i]+".jpg" + "\">" +
			        "</div>" +
			        "<h3 style=\" text-align:center\"><img class=\"round\" style=\"width: 50px; height: 50px\" src=\"img/stock2.jpg\">By <a href=\"#profile\">UTSA</a> on " + response["eventdate"+i] + response["eventtime"+i] +
	         "&nbsp;&nbsp;<b style=\"font-size:15px\">553 going</b></h3>" +
	       	"<a data-role=\"button\" href=\"#feed\" data-icon=\"check\" data-mini=\"true\" data-iconpos=\"right\">check in</a>" + 
	        "<div data-role=\"collapsible-set\">" + 
	            "<div data-role=\"collapsible\">" +
	                "<h3>Details</h3>" + 
					"<div data-controltype=\"htmlblock\" style=\"text-align:center\">" +
					"<a href=\"http://twitter.com/share\" class=\"twitter-share-button\" data-count=\"vertical\" data-via=\"YourSite\">Share</a>" +
					"<p>" + response["eventdescription"+i] + "</p></div></div></div></div>"+
					"<hr style=\"height:3px; background-color:#ccc; border:0; margin-top:12px; margin-bottom:12px;\">";
			}
			
			document.getElementById("events").innerHTML = htmlCode;
			
			console.log("success!");
		}
		
	}
	
	xhr.open("POST", "http://localhost:8080/home", false);
	xhr.send();
}