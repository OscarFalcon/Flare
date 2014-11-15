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
	function error(msg) {
		alert('error: ' + msg);
	}
	
	
	var points = new Array();
	
	/** lets get marker locations dynamically from server **/
	var request = new XMLHttpRequest();
	request.onload = function() {
		var status = request.status;
		var response = request.responseText;
		console.log("map-POST: status = " + status);
		console.log("map-POST: response = " + response);
		
		var rows = response.split('#'); //protocol for obtaining each row of the results;
		
		for(var i=0;i < rows.length;i++)
		{
			var line = rows[i];
			console.log("row"+ i + ": " + line);
			
			points[i] = new Array(); 
			var columns = line.split('|');
			for(var j = 0; j < columns.length;j++)
			{
				console.log("column" + j + ": " + columns[j]);
				points[i].push(columns[j]);
				
			}
			
			
		}
		
		
		
	}
	request.open("POST","http://localhost:8080/map",false);
	request.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	request.send(); 
	
	
	
	
	
					
	/** place markers and infowindows on map 
	var points = [[29.531527,-98.562403,"hsa.jpg","Bowl Night!","Come join us for some fun!"]
	  				 ,[29.586706,-98.624847,"cs.gif","Hack-A-Thon","bring your own computer"] 
				   	 ];			  **/
	var latLng,marker,infowindow,contentString,markers = [],infowindows = [],circles = [];


	
	


  	for(var i = 0; i < points.length; i++)
  	{
  		
  		var myTitle = points[i][0];
  		var myDescription = points[i][1];
  		var myLat = points[i][2];
  		var myLong = points[i][3];
  		var myDate = points[i][4];
  		var myTime = points[i][5];
  		var myPicID = points[i][6];
  		
  		
  		
  		latLng = new google.maps.LatLng(myLat,myLong);
		marker = new google.maps.Marker({
			position:latLng,
			map: control.map,
			//animation:google.maps.Animation.BOUNCE,
			icon:'img/maps/orange.marker.png',
			title:points[i][3]
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
        setInterval(function() {
            for(var i=0;i<circles.length;i++){
				var mycircle = circles[i];
				var radius = mycircle.getRadius();
	            if ((radius > rMax) || (radius < rMin)) {
	                direction *= -1;
	            }
	            mycircle.setRadius(radius + direction * 10);	
			}
        }, 50);
		
		circle.bindTo('center', marker, 'position');
				
  		contentString = '<div class ="infoWindow">'+
        	'<div>'+	
			'<img src="http://localhost:8080/Profile/'+  myPicID + '.jpg" '+ 
			'width="42" height="42">' +
        	'<h3>' + myTitle + '</h3>'+			
			'<p>' + myDescription + '</p>' +
			'<input type="image" src="img/maps/next-arrow.png" width="20" height="20"/>' +
        	'</div>';

  		console.log(contentString);
  		
		infowindow = new google.maps.InfoWindow({
			content:contentString,
		});
		marker.info = infowindow;
	}
}//start function