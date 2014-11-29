

var fileInput;



function sendPOST()
{
	uploadData();
	//window.location.replace("http://localhost:8080/home");
	
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
	geocoder.geocode( { 'address': eventAddress,'componentRestrictions':{'country':'US'}}, function(results, status) {

	if (status == google.maps.GeocoderStatus.OK)
	{
	    latitude = results[0].geometry.location.lat();
	    longitude = results[0].geometry.location.lng();
	
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
			
	} 
	
	});
		
}


function changeInput(input)
{
	console.log("changeInput");
	fileInput = input;
}