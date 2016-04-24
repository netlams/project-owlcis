/**
 * Global variables
 */
var backendError = false;
var auth2;

/* Initializes the Sign-In client */
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

$(function () {
  $('#google-signin-btn').tooltip()
})

/* Start Google Auth */
//initClient();

/* Create Google signin button */
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

/* Failure at Google signin */
function onFailure(error) {
    console.log(error);
}

/* Success at Google signin */
function onSignIn(googleUser) {

    var profile = googleUser.getBasicProfile();
    var id_token = googleUser.getAuthResponse().id_token;

    console.log('Name: ' + profile.getName());
    console.log('Email: ' + profile.getEmail());
    /* only call this function one time! (first time when nonexistent cookies) */
    if (!getCookie("ROLE") && !getCookie("EMAIL") && !getCookie("FNAME")) {
        if (validateEmail(profile.getEmail())) {
            // Communicate with backend to set session data

            logBackend(id_token);

        } else {
            loadLoginMsg('bad-email', profile.getEmail());
        }
    }
}

/* Sign out of OWLCIS */
function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
        console.log('User signed out.');
        window.location = "/signout";
    });
}

/* Check if email is from Temple */
function validateEmail(email) {
    var pattern = new RegExp(/\S\S*@temple.edu$/i); // check if email param is a valid temple email address
    return pattern.test(email);
}

/* Gets the value from Cookie */
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

/* Redirect to a URL */
function redirectTo(page) {
    window.location = page;
    document.location.reload(true);
}

// Communicate with backend to set session data
function logBackend(id) {
    loadLoadingAnim();

    console.log("Communicating with backend");
    var xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function () {
        console.log(xhr.responseText);
        if (xhr.readyState == 4 && xhr.status == 200) {
            // user logged in and session and cookie set
            setTimeout(function () {
                redirectTo("/");
            }, 200); // slight delay to make it look natural
        }
        else if (xhr.readyState == 4 && xhr.status == 202) {
            // no matching found, so added new user and session and cookie set
            loadLoginMsg('new-user');
        }
        else if (xhr.readyState == 4 && xhr.status == 403) {
            // Bad response
            if (!backendError) {
                loadLoginMsg(false);
            }
            backendError = true;
        }
        else if (xhr.readyState == 4 && (xhr.status == 404 || xhr.status == 500)) {
            // Bad response
            if (!backendError) {
                loadLoginMsg(false);
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

/* Start loading animation */
function loadLoadingAnim() {
    document.getElementById("app").style.opacity = "0.1";
    document.body.innerHTML += "<div id='cssload-loader'>OWLCIS is Loading</div>";
    return true;
}

/* Stop loading animation */
function stopLoadingAnim() {
    /* load for 1.5 seconds */
    setTimeout(function () {
        document.getElementById("app").style.opacity = "1.0";
        var elem = document.getElementById("cssload-loader");
        elem.parentNode.removeChild(elem);
    }, 500);
    return true;
}

function loadLoginMsg(msg, email) {
    var title, body, btn;
    if (msg == 'bad-email') {
        title = 'OWLCIS Login Failure';
        body = "OWLCIS only allows Temple students, alumnis, and faculty members with a valid university email account.\n"
                + "<span class='text-uppercase'>" + email + "</span>"
                + " has not been accepted.";
        btn = "<button type='button' class='btn btn-default' data-dismiss='modal' onClick='signOut();'>Close</button>"
                + "<a href='https://www.google.com/accounts/Logout?continue=https://appengine.google.com/_ah/logout?continue=http://owlcis.me'"
                + "type='button' class='btn btn-warning'>"
                + "Log out of Google"
                + "</a>";
    } else if (msg == 'new-user') {
        title = 'Welcome New User';
        body = 'Redirecting you to sign up';
        btn = "<button type='button' class='btn btn-default' data-dismiss='modal' onClick='redirectTo(\"/#signup\")'>Close</button>";
    } else {
        title = 'OWLCIS Login Failure';
        body = 'OWLCIS is unavailable at the moment. Please try again later.';
        btn = "<button type='button' class='btn btn-default' data-dismiss='modal' onClick='stopLoadingAnim()'>Close</button>";
    }

    /* Add a Bootstrap Modal */
    $(document.body)
            .prepend("<div class='modal fade' id='login-msg-modal' tabindex='-1' role='dialog' aria-labelledby='login-msg-label'>"
                    + "<div class='modal-dialog' role='document'>"
                    + "<div class='modal-content'>"
                    + "<div class='modal-header'>"
                    + "<button type='button' class='close' data-dismiss='modal' aria-label='Close'>"
                    + "<span aria-hidden='true'>&times;</span>"
                    + "</button>"
                    + "<h2 class='modal-title text-center' id='login-msg-label'>" + title + "</h2>"
                    + "</div>"
                    + "<div class='modal-body text-center'>"
                    + body
                    + "</div>"
                    + "<div class='modal-footer'>"
                    + btn
                    + "</div>"
                    + "</div>"
                    + "</div>"
                    + "</div>");
    /* Activate Modal */
    $('#login-msg-modal').modal();
}