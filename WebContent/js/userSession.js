
function initUser(username, password, userID, email, fullname, aboutme)
{
	sessionStorage.userID = userID;
	sessionStorage.username = username;
	sessionStorage.password = password;
	sessionStorage.email = email;
	sessionStorage.fullname = fullname;
	sessionStorage.aboutme = aboutme;
}
