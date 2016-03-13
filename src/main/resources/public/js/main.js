var backendError = false;
var auth2;

/**
 * Initializes the Sign-In client.
 */
var initClient = function () {
    gapi.load('auth2', function () {
        /**
         * Retrieve the singleton for the GoogleAuth library and set up the
         * client.
         */
        auth2 = gapi.auth2.init({
            client_id: '221190717599-nkm4ci5r8eipdiqjbn8n0rr2m4tnvb78.apps.googleusercontent.com'
        });

    });
};

initClient();


function renderGButton() {
    gapi.signin2.render('google-signin-btn', {
        'scope': 'profile email',
        'width': 180,
        'height': 32,
        'longtitle': true,
        'theme': 'dark',
        'onsuccess': onSignIn,
        'onfailure': onFailure
    });
}

function onFailure(error) {
    console.log(error);
}

function onSignIn(googleUser) {
    
        googleUser = auth2.currentUser.get();
        var profile = googleUser.getBasicProfile();
        var id_token = googleUser.getAuthResponse().id_token;

        console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
        console.log('Name: ' + profile.getName());
        console.log('Email: ' + profile.getEmail());
    /* only call this function one time! (first time when nonexistent cookies) */
    if (!getCookie("ROLE") && !getCookie("EMAIL") && !getCookie("FNAME")) {
        if (validateEmail(profile.getEmail())) {
            // Communicate with backend to set session data

            logBackend(id_token);

        } else {
            alert("Sorry, OWLCIS only allows Temple students, alumnis, and faculty members."
                    + " \nYour Gmail: " + profile.getEmail() + " has not been accepted.");
            signOut();
        }
    }
}

function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
        console.log('User signed out.');
        redirectTo("signout");
    });
}

function validateEmail(email) {
    var pattern = new RegExp(/\S\S*@temple.edu$/i); // check if email param is a valid temple email address
    return pattern.test(email);
}

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ')
            c = c.substring(1);
        if (c.indexOf(name) == 0)
            return c.substring(name.length, c.length);
    }
    return null;
}

function redirectTo(page) {
    window.location = page;
}

// Communicate with backend to set session data
function logBackend(id) {
    loadLoadingAnim();

    console.log("Communicating with backend")
    var xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function () {
        console.log(xhr.responseText);
        if (xhr.readyState == 4 && xhr.status == 200) {
            // user logged in and session and cookie set
            redirectTo("/");
        }
        else if (xhr.readyState == 4 && xhr.status == 202) {
            // no matching found, so added new user and session and cookie set
            alert("New User! Redirecting you to complete signup.");
//            redirectTo("/");
            redirectTo("/#signup");
            document.location.reload(true);
        }
        else if (xhr.readyState == 4 && xhr.status == 403) {
            // Bad response
            if (!backendError) {
                alert("OWLCIS encounterd an error with your login credentials. Please make sure to use Temple email.");
                stopLoadingAnim();
            }
            backendError = true;
        }
        else if (xhr.readyState == 4 && (xhr.status == 404 || xhr.status == 500)) {
            // Bad response
            if (!backendError) {
                alert("OWLCIS is unavailable at the moment. Please try again later.");
                stopLoadingAnim();
            }
            backendError = true;
        }
        else {
            ;
        }
    };
    xhr.open('POST', 'login');
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send(id);
}

function loadLoadingAnim() {
    document.getElementById("app").style.opacity = "0.1";
    document.body.innerHTML += "<div id='cssload-loader'>OWLCIS is Loading</div>";
    return true;
}

function stopLoadingAnim() {
    document.getElementById("app").style.opacity = "1.0";
    var elem = document.getElementById("cssload-loader");
    elem.parentNode.removeChild(elem);
    return true;
}