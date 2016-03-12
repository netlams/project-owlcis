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
    /* only call this function one time! (first time when nonexistent cookies) */
    if (!getCookie("ROLE") && !getCookie("EMAIL") && !getCookie("FNAME")) {
        loadLoadingAnim();


        var profile = googleUser.getBasicProfile();
        var id_token = googleUser.getAuthResponse().id_token;

        console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
        console.log('Name: ' + profile.getName());
        console.log('Image URL: ' + profile.getImageUrl());
        console.log('Email: ' + profile.getEmail());
        console.log('Token: ' + id_token);

//        if (googleUser.isSignedIn()) {
        if (validateEmail(profile.getEmail())) {
//                alert("Good you're from temple.");
            // Communicate with backend to set session data

            logBackend(id_token);

            // countdown to redirect
//      var seconds = 5;
//      var repeat =  setInterval(function(){
//          document.getElementById("message").innerHTML = 'Redirecting you to homepage in ... ' + seconds;
//          seconds -= 1;
//          if (seconds == 0) { 
//              clearInterval(repeat);
////              redirectTo("index.html")
//          }
//      }, 1000);
        } else {
            alert("Sorry, OWLCIS only allows Temple students, alumnis, and faculty members."
                    + " \nYour Gmail: " + profile.getEmail() + " has not been accepted.");
            signOut();
        }
//        }
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

function showRole() {
    var role = getCookie("ROLE");
    alert("Your role is: " + getCookie("ROLE"));
}

function redirectTo(page) {
    window.location = page;
}

// Communicate with backend to set session data
function logBackend(id) {
    console.log("Communicating with backend")
    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'login');
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
//    xhr.onload = function () {
//        console.log(xhr.responseText);
//        redirectTo("/");
//    };
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 404) {
            console.log(xhr.responseText);
            alert("OWLCIS is unavailable at the moment. Sorry for the inconvenience.")
        }
        else if (xhr.readyState == 4 && xhr.status == 403) {
            console.log(xhr.responseText);
            alert("OWLCIS encounterd an error. Sorry for the inconvenience.")
        }
        else {
            console.log(xhr.responseText);
            redirectTo("/");
        }
    };
    xhr.send(id);
}

function loadLoadingAnim() {
    document.getElementById("app").style.opacity = "0.1";
    document.body.innerHTML += "<div class='cssload-loader'>OWLCIS</div>";
    return true;
}