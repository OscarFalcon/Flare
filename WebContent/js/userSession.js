
function initUser(username, password, email, firstname, lastname, aboutme)
{
	console.log("initUser: userSession")
	sessionStorage.username = username;
	sessionStorage.password = password;
	sessionStorage.email = email;
	sessionStorage.firstname = firstname;
	sessionStorage.lastname = lastname;
	sessionStorage.aboutme = aboutme;
		
}

