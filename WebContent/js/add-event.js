

var fileInput;

function addEvent()
{
	var eventTitle = document.getElementById("textinput8").value;
	var eventDescription = document.getElementById("textarea1").value;
	var eventDate = document.getElementById("dateinput1").value;
	var eventTime = document.getElementById("timeinput1").value;
	var eventLocation = document.getElementById("locationinput1").value;
	
	console.log("addevent");
	
	if( eventTitle == '' || eventDescription == '' || eventDate == '' || eventTime == '' || eventLocation == '')
	{
		console.log("null values");
		return false;
		
	}
	
	if(fileInput == null)
	{
		console.log("Error: fileInput is null");
		return false;
	}
	
	var formData;
	var dataURL;
	var reader = new FileReader();
	reader.onload = function(e) {
		  dataURL = reader.result;
		  console.log("dataURL: " + dataURL);
		  
		  var byteString;
		  if (dataURL.split(',')[0].indexOf('base64') >= 0)
		  		byteString = atob(dataURL.split(',')[1]);
		  else
		        byteString = unescape(dataURL.split(',')[1]);
				
		  // separate out the mime component
		  var mimeString = dataURL.split(',')[0].split(':')[1].split(';')[0];

		  // write the bytes of the string to a typed array
		  var ia = new Uint8Array(byteString.length);
		  for (var i = 0; i < byteString.length; i++) 
		  {
		  		ia[i] = byteString.charCodeAt(i);
		  }
		  var blob = Blob([ia], {type:mimeString});
		  formData = new FormData();
		  formData.append("file", blob);
			  
	}
	reader.readAsDataURL(fileInput.files[0]);
	

	
	
	var request = new XMLHttpRequest();
	request.onload = function () {
 		var status = request.status; // HTTP response status, e.g., 200 for "200 OK"
 	    var response = request.responseText; // Returned data, e.g., an HTML document.
 	    console.log("status: " + status);
 		console.log("response: " + response);
 	    if(status == 200 && response == "true")
 	    {
			console.log("successfully uploaded image");
 		   
 	    }
 	    else
 	    {
			console.log("failed to upload image to server");
 	    }
	}
	request.open("POST","http://localhost:8080/events/add",true);
	request.setRequestHeader('Content-Type', 'multipart/form-data;boundary=applicationBoundary');
	request.send(formData);
	
}



function sendPOST()
{
	uploadData();
	//uploadFile();
	//window.location.replace("http://localhost:8080/home");
	
}




function uploadFile(eventTitle)
{
	
	var formData = new FormData();
	formData.append
	formData.append("file", document.getElementById("myfile").files[0]);
	formData.append("eventTitle","VALUE");
	formData.append("eventDescription","VALUE");
	formData.append("eventDate","VALUE");
	formData.append("eventTime","VALUE");
	formData.append("latitude","VALUE");
	formData.append("longitude","VALUE");
	
	
	
	
	var xhr = new XMLHttpRequest();
	xhr.open("POST", "http://localhost:8080/addEvent",false);
	xhr.send(formData);
	
}

function uploadData()
{
	var eventTitle = document.getElementById("textinput8").value;
	var eventDescription = document.getElementById("textarea1").value;
	var eventDate = document.getElementById("dateinput1").value;
	var eventTime = document.getElementById("timeinput1").value;
	var eventAddress = document.getElementById("locationinput1").value;
	
	console.log("uploadData");
	
	if( eventTitle == '' || eventDescription == '' || eventDate == '' || eventTime == '' || eventAddress == '')
	{
		console.log("null values");
		return false;
		
	}
	
	var latitude;
	var longitude;
	var geocoder = new google.maps.Geocoder();
	geocoder.geocode( { 'address': eventAddress}, function(results, status) {

	if (status == google.maps.GeocoderStatus.OK)
	{
	    latitude = results[0].geometry.location.lat();
	    longitude = results[0].geometry.location.lng();
	   
		/**var request = new XMLHttpRequest();
		request.onload = function ()
		{
			 if(status == 200 )
			 {
				 console.log("success");
			 }
		} **/
		
		var formData = new FormData();
		formData.append("eventTitle",eventTitle);
		formData.append("eventDescription",eventDescription);
		formData.append("eventDate",eventDate);
		formData.append("eventTime",eventTime);
		formData.append("latitude",latitude);
		formData.append("longitude",longitude);
		formData.append("file", document.getElementById("myfile").files[0]);
		
		var xhr = new XMLHttpRequest();
		xhr.onload = function ()
		{
			if(xhr.status == 200)
				{
					console.log("success!");
				}
		}
		
		xhr.open("POST", "http://localhost:8080/addEvent",false);
		xhr.send(formData);
		
		
		/**
		var postData = "eventTitle="+eventTitle +
						"&eventDescription="+ eventDescription +
						"&eventDate="+eventDate+
						"&eventTime="+eventTime+
						"&latitude="+latitude+
						"&longitude="+longitude;
						
		console.log(postData);
		
		request.open("POST","http://localhost:8080/addEvent",false);
		request.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		request.send(postData);**/
			
	} 
	
	});
		
}


function changeInput(input)
{
	console.log("changeInput");
	fileInput = input;
}