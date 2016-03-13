(function () {
    var app = angular.module('authApp');

    /* Gets the cookie */
    app.service('CookieService', function () {
        this.getCookie = function getCookie(cname) {
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
    });

    app.controller('loginController', ['$scope', '$state', 'CookieService', function ($scope, $state, CookieService) {
            $scope.fname = CookieService.getCookie('FNAME');
            $scope.role = CookieService.getCookie('ROLE');
            $scope.loginStatus = false;
            $scope.checkLogin = function () {
                if ($scope.fname != null && $scope.role != null)
                    return true;
                else
                    return false
            }

            $scope.loginStatus = $scope.checkLogin();
        }]);

    app.controller('signupController', ['$scope', '$state', '$http', '$window', function ($scope, $state, $http, $window) {
            $scope.formData = {};
            // process the form
            $scope.processForm = function () {
                $http.post('/signup', $scope.formData)
                        .then(function (response) {
                            console.log(response.status);
                            console.log(response.data);
                            if (response.status == 203) {
                                alert("Successfully Added");
                                $window.location.href = '/';
                            }
                            else {
                                alert("error :((");
                                $window.location.href = '/';
                            }
                        }, function (response) {
                            ;
                        });
            };
        }]);

}());