window.CodiqaControls && window.CodiqaControls.register('googlemaps', 'googlemapsjs1',{
              ready: start
   });
   
function start(control){

console.log("map.js")
	
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
	
		
	/** lets get marker locations dynamically from server **/
	var latLng,marker,infowindow,contentString,markers = [],infowindows = [],circles = [];

	
	
	var request = new XMLHttpRequest();
	request.onload = function() {
		var status = request.status;
		var response = request.responseText;
		console.log("map-POST: status = " + status);
		console.log("map-POST: response = " + response);
		
		
		if(status != 200)
		{
			return;
		}
		var json = JSON.parse(response);
		
		for(var i = 0; i < json.events.length ; i++)
		{
			
			latLng = new google.maps.LatLng(json.events[i].locationLat,json.events[i].locationLog);
			marker = new google.maps.Marker({
				position:latLng,
				map: control.map,
				//animation:google.maps.Animation.BOUNCE,
				icon:'img/maps/orange.marker.png',
				title:json.events[i].eventTitle
			});
			markers.push(marker);
			
			google.maps.event.addListener(marker, 'click', (function(x) {
				var hide = 0;
				return function() {	
					if(hide)
			  			markers[x].info.close();
					else
						markers[x].info.open(control.map,markers[x]);
					hide = !hide;
				}
			})(i));
		
			var circle = new google.maps.Circle({
				map:control.map,
				center: control.center,
	            radius: 4000,
				fillColor: '#fff',
	            fillOpacity: .6,
	            strokeColor: '#313131',
	            strokeOpacity: .4,
	            strokeWeight: .8
			});
			circle.setMap(control.map);
			circles.push(circle);
			
		 	var direction = 1;
		    var rMin = 2000, rMax = 4000;
		    setInterval(function()
		    {
		       for(var i=0;i<circles.length;i++)
		       {
		    	   var mycircle = circles[i];
		    	   var radius = mycircle.getRadius();
		    	   
			       if ((radius > rMax) || (radius < rMin))
			       {
			    	   direction *= -1;
			       }
			       mycircle.setRadius(radius + direction * 10);	
		       }
		    }, 50);
		    circle.bindTo('center', marker, 'position');
			
		    contentString = '<div class ="infoWindow">'+
	      	'<div>'+	
				'<img src="http://localhost:8080/Events/'+  json.events[i].eventId + '.jpg" '+ 
				'width="42" height="42">' +
	      	'<h3>' + json.events[i].eventTitle + '</h3>'+			
				'<p>' + json.events[i].eventDescription + '</p>' +
				'<input type="image" src="img/maps/next-arrow.png" width="20" height="20"/>' +
	      	'</div>';
		    
		    console.log(contentString);
			
			infowindow = new google.maps.InfoWindow({
				content:contentString,
			});
			marker.info = infowindow;
		    
		    
		    
		
		}//for loop
		
	}
	request.open("POST","http://localhost:8080/map",false);
	request.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	request.send(); 
	
	

}//start function