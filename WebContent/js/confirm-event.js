window.CodiqaControls && window.CodiqaControls.register('googlemaps', 'googlemapsjs3',{
              ready: start
});

var styles = [
              {
                  "featureType": "water",
                  "elementType": "geometry",
                  "stylers": [
                      {
                          "color": "#a2daf2"
                      }
                  ]
              },
              {
                  "featureType": "landscape.man_made",
                  "elementType": "geometry",
                  "stylers": [
                      {
                          "color": "#f7f1df"
                      }
                  ]
              },
              {
                  "featureType": "landscape.natural",
                  "elementType": "geometry",
                  "stylers": [
                      {
                          "color": "#d0e3b4"
                      }
                  ]
              },
              {
                  "featureType": "landscape.natural.terrain",
                  "elementType": "geometry",
                  "stylers": [
                      {
                          "visibility": "off"
                      }
                  ]
              },
              {
                  "featureType": "poi.park",
                  "elementType": "geometry",
                  "stylers": [
                      {
                          "color": "#bde6ab"
                      }
                  ]
              },
              {
                  "featureType": "poi",
                  "elementType": "labels",
                  "stylers": [
                      {
                          "visibility": "off"
                      }
                  ]
              },
              {
                  "featureType": "poi.medical",
                  "elementType": "geometry",
                  "stylers": [
                      {
                          "color": "#fbd3da"
                      }
                  ]
              },
              {
                  "featureType": "poi.business",
                  "stylers": [
                      {
                          "visibility": "off"
                      }
                  ]
              },
              {
                  "featureType": "road",
                  "elementType": "geometry.stroke",
                  "stylers": [
                      {
                          "visibility": "off"
                      }
                  ]
              },
              {
                  "featureType": "road",
                  "elementType": "labels",
                  "stylers": [
                      {
                          "visibility": "off"
                      }
                  ]
              },
              {
                  "featureType": "road.highway",
                  "elementType": "geometry.fill",
                  "stylers": [
                      {
                          "color": "#ffe15f"
                      }
                  ]
              },
              {
                  "featureType": "road.highway",
                  "elementType": "geometry.stroke",
                  "stylers": [
                      {
                          "color": "#efd151"
                      }
                  ]
              },
              {
                  "featureType": "road.arterial",
                  "elementType": "geometry.fill",
                  "stylers": [
                      {
                          "color": "#ffffff"
                      }
                  ]
              },
              {
                  "featureType": "road.local",
                  "elementType": "geometry.fill",
                  "stylers": [
                      {
                          "color": "black"
                      }
                  ]
              },
              {
                  "featureType": "transit.station.airport",
                  "elementType": "geometry.fill",
                  "stylers": [
                      {
                          "color": "#cfb2db"
                      }
                  ]
              }
          	];
          	



function start(control)
{
	console.log("confirm-event");
	/** initialize control **/	
    control.options = {
    	zoom: 10,
       	mapTypeId: google.maps.MapTypeId.ROADMAP,
 	   	zoomControl: false,
		streetViewControl: false,
		mapTypeControl: false
    };
    control.el = document.getElementById(control._id);
    control.map = new google.maps.Map(control.el, control.options);
	
	/** initalize map **/
	var mapOptions = {
		center: control.center,
	    zoom: 11,
	    mapTypeId: google.maps.MapTypeId.ROADMAP,
		styles: styles
	};
    control.map.setOptions(mapOptions);


	/** set center of map to current user location **/
	navigator.geolocation.getCurrentPosition(success,error);
	function success(position) {
		var position =  new google.maps.LatLng(position.coords.latitude,position.coords.longitude);
		control.center = position;
		control.map.setCenter(control.center);	
		new google.maps.Marker({
			position: position,
			map: control.map,
			icon: "img/maps/blue-dot.png"
		})
		
		
	}
	function error(msg) 
	{
		alert('error: ' + msg);
	}
}


function addEvent(){
	var eventAddress = document.getElementById("searchInput").value;
	var latitude;
	var longitude;
	var geocoder = new google.maps.Geocoder();
	geocoder.geocode( { 'address': eventAddress,'componentRestrictions':{'country':'US'}}, function(results, status) {

	if (status == google.maps.GeocoderStatus.OK)
	{
	    
		for(var i=0;i<results.length;i++)
		{
			console.log(results[i].formatted_address);
		}
		
		
		latitude = results[0].geometry.location.lat();
	    longitude = results[0].geometry.location.lng();
	
	    console.log("Event Title = " + localStorage.getItem("title"));
	    
	    
		var formData = new FormData();
		formData.append("eventTitle",localStorage.getItem("title"));
		formData.append("eventDescription",localStorage.getItem("description"));
		formData.append("eventDate",localStorage.getItem("date"));
		formData.append("eventTime",localStorage.getItem("time"));
		formData.append("latitude",latitude);
		formData.append("longitude",longitude);
		formData.append("file", file);
		
		var xhr = new XMLHttpRequest();
		xhr.onload = function ()
		{
			if(xhr.status == 200)
				{
					console.log("success!");
					alert("Successfully Added New Event " + Event["title"]);
					redirectTo("home");
				}
		}
		
		xhr.open("POST", "http://localhost:8080/confirm-event",false);
		xhr.send(formData);
			
	} 
	
	});
	
	
}




