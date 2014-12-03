
function initUser(username, password, userID, email, firstname, lastname, aboutme)
{
	sessionStorage.userID = userID;
	sessionStorage.username = username;
	sessionStorage.password = password;
	sessionStorage.email = email;
	sessionStorage.firstname = firstname;
	sessionStorage.lastname = lastname;
	sessionStorage.aboutme = aboutme;
}
