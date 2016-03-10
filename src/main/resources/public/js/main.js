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
    var profile = googleUser.getBasicProfile();
    var id_token = googleUser.getAuthResponse().id_token;

    console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
    console.log('Name: ' + profile.getName());
    console.log('Image URL: ' + profile.getImageUrl());
    console.log('Email: ' + profile.getEmail());
    console.log('Token: ' + id_token);

    if (googleUser.isSignedIn()) {
        if (validateEmail(profile.getEmail())) {
            alert("Good you're from temple.");
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
            alert("Not temple.");
        }
    }
}

function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
        console.log('User signed out.');
        document.getElementById("message").innerHTML = 'You have logged out';
        document.getElementById("gname").innerHTML = '';
        document.getElementById("gemail").innerHTML = '';
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
    return "";
}

function showRole() {
    var role = getCookie("role");
    alert("Your role is: " + getCookie("role"));
}

function redirectTo(page) {
    window.location = page;
}

// Communicate with backend to set session data
function logBackend(id) {
    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'login');
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function () {
        console.log(xhr.responseText);
    };
    xhr.send(id);
}
