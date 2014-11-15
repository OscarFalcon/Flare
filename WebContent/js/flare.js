/** load home page maps **/
window.CodiqaControls && window.CodiqaControls.register('googlemaps', 'googlemapsjs2',{
              ready: init
   });
function init(control){
   control.options = {
       zoom: 10,
       mapTypeId: google.maps.MapTypeId.ROADMAP,
	   zoomControl: true
	   
   };

   control.el = document.getElementById(control._id);
   control.map = new google.maps.Map(control.el, control.options);
 
   var geocoder = new google.maps.Geocoder();
   geocoder.geocode({
       'address': 'San Antonio, TX'
   }, function(results, status) {
       if (status == google.maps.GeocoderStatus.OK) {
           var marker = new google.maps.Marker({
               map: control.map,
               position: results[0].geometry.location,
               animation:google.maps.Animation.BOUNCE
           });
           control.center = results[0].geometry.location;
           control.map.setCenter(control.center);
       }
   });
}
   
/**  ##########################      MAP VIEW CODE          #####################################**/
window.CodiqaControls && window.CodiqaControls.register('googlemaps', 'googlemapsjs1',{
              ready: start
   });
   
function start(control){
	console.log("init");
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
	
	
	
	
					
	/** place markers and infowindows on map **/
	var points = [[29.531527,-98.562403,"hsa.jpg","Bowl Night!","Come join us for some fun!"]
	  				 ,[29.586706,-98.624847,"cs.gif","Hack-A-Thon","bring your own computer"]
				   	 ];			  
	var latLng,marker,infowindow,contentString,markers = [],infowindows = [],circles = [];


	
	


  	for(var i = 0; i < points.length; i++)
  	{
  		latLng = new google.maps.LatLng(points[i][0],points[i][1]);
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
		
		
		
		
				
  		contentString = 
			'<div class ="infoWindow">'+
        		'<div>'+	
				'<img src="img/'+points[i][2]+'"'+
				'width="70" height="70">' +
        		'<h3 class = "header2">' + points[i][3] + '</h3>'+			
				'<p>' + points[i][4] + '</p>' +
				'<input type="image" src="img/maps/next-arrow.png" width="20" height="20"/>' +
        	'</div>';

		infowindow = new google.maps.InfoWindow({
			content:contentString,
		});
		marker.info = infowindow;
	}
}//start function







function validateForm()
{
	
	var user = document.getElementById("usernameField").value;
	var pass = document.getElementById("passwordField").value;
	
	console.log("validate form");
	
	if(user == '')
	{
		document.getElementById('error').innerHTML="<font color=\"red\">Please Enter Username</font>";
		return false;
	}
 
	if(pass == '')
	{
		document.getElementById('error').innerHTML="<font color=\"red\">Please Enter Password</font>";
		return false;
	}
	
	
	var url = "localhost:/8080/login";
	var method = "POST";
	var postData;
	var async = true;

	postData = "username=" + user +
			   "&password=" + pass;
	
	var request = new XMLHttpRequest();
	request.onload = function () {

	   var status = request.status; // HTTP response status, e.g., 200 for "200 OK"
	   var response = request.responseText; // Returned data, e.g., an HTML document.
	   console.log("status: " + status);
	   console.log("response: " + response);
	   
	   if(status == 200 && response == "true")
	   {
		   window.location.replace("http://localhost:8080/home#page2");
		   //window.location.href="#page2";
	   }
	   else
	   {
		   document.getElementById('error').innerHTML="<font color=\"red\">Invalid Credentials</font>";
	   }
	   
	}
	request.open("POST","http://localhost:8080/login",true);
	request.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	request.send(postData); 
	return;

	
}

function createAccount()
{
	var username=document.createAccount.usernameField.value;
	var email=document.createAccount.emailField.value;
	var password=document.createAccount.passwordField.value;
	var verify=document.createAccount.verifyPasswordField.value;
	
	if(username == '')
	{
		document.getElementById('error1').innerHTML="<font color=\"red\">Please Enter valid Username</font>";
		return false;
	}
	
	document.getElementById('createLogin').reset();
	window.location.href="#page2"
}

function uploadpic(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#profilepicture')
                .attr('src', e.target.result)
                .width(200)
                .height(200);
        };

        reader.readAsDataURL(input.files[0]);
    }
}




