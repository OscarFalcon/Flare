
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


