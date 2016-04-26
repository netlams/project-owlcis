(function () {
    var app = angular.module('authApp');
    /* Gets the cookie */
    app.service('CookieService', function () {
        this.getCookie = function (cname) {
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
        };
    });
    /* Gets the dept JSON list */
    app.service('DeptService', function ($q, $http) {
        this.getDeptList = function () {
            var defer = $q.defer();
            $http.get('./api/depts', {cache: 'true'})
                    .success(function (data) {
                        defer.resolve(data);
                    });
            return defer.promise;
        };
    });
    /* Bootstrap Modal Builder Service */
    app.factory('ModalBuilderService', function () {
        var factory = {};
        factory.build = function (id, title, body, btn) {
            return "<div class='modal fade' id='" + id + "' tabindex='-1' role='dialog' aria-labelledby='msg-label'>"
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
                    + "</div>"
                    + "</div>";
        };
        return factory;
    });

    /* Login Controller */
    app.controller('loginController', ['$scope', '$state', 'CookieService',
        function ($scope, $state, CookieService) {
            $scope.memberNav = [{url: '#home', name: 'Home'},
                {url: '#profile', name: 'Profile'},
                {url: '#reviews', name: 'Course Reviews'},
                {url: '#schedule', name: 'Schedule'},
                {url: '#forum', name: 'Forum'}];
            $scope.advNav = [{url: '#home', name: 'Home'},
                {url: '#reviews', name: 'Course Reviews'},
                {url: '#forum', name: 'Forum'}];
            $scope.modNav = [{url: '#home', name: 'Home'},
                {url: '#reviews', name: 'Course Reviews'},
                {url: '#forum', name: 'Forum'},
                {url: '#moderator', name: 'Moderator'}];
            $scope.fname = CookieService.getCookie('FNAME');
            $scope.role = CookieService.getCookie('ROLE');
            $scope.email = CookieService.getCookie('EMAIL');
            $scope.navbar = [];
            $scope.loginStatus = false;
            $scope.checkLogin = function () {
                if ($scope.fname != null
                        && $scope.role != null
                        && $scope.email != null) {
                    if ($scope.role === 'member')
                        $scope.navbar = $scope.memberNav;
                    else if ($scope.role === 'moderator')
                        $scope.navbar = $scope.modNav;
                    else
                        $scope.navbar = $scope.advNav;
                    return true;
                } else {
                    return false;
                }
            };
            $scope.loginStatus = $scope.checkLogin();
        }]);
    /* Signup Controller */
    app.controller('signupController', ['$scope', '$state', '$http', '$window', 'DeptService', 'CookieService',
        'ModalBuilderService', '$filter', '$compile', '$interval',
        function ($scope, $state, $http, $window, DeptService, CookieService, ModalBuilderService, $filter, $compile, $interval) {
            $scope.foundEmail = CookieService.getCookie('EMAIL');
            $scope.formData = {
                deptId: null,
                major: null,
                availMajorOptions: [{shortname: 'CS', name: 'Computer Science'},
                    {shortname: 'CSM', name: 'Computer Science & Math'},
                    {shortname: 'IST', name: 'Information Science & Technology'}],
                availDeptOptions: null,
                availDegreeOptions: [{name: 'BS'}, {name: 'BA'}, {name: 'Master'}, {name: 'Ph.D.'}],
                err: null,
            };

            DeptService.getDeptList()
                    .then(function (data) {
                        $scope.formData.availDeptOptions = data;
                    });

            // activate modal msg before making load schedule call to API backend
            $scope.showMsg = function () {
                var id, title, body, btn;
                id = 'msg-modal';
                title = 'New User';
                body = "Welcome to OWLCIS. <i class='fa fa-smile-o' aria-hidden='true'></i> Get started " 
                        + " by visiting our Course Reviews page or Schedule page to build your schedule list. "
                        + "You can update your info anytime at the Profile page. ";
                btn = "<button class='btn btn-default' data-ng-click='msgConfirmed()' data-dismiss='modal'>Got it</button>";
                if ($('#' + id)) {
                    $('#' + id).remove(); // remove old modal if exists
                }
                $(document.body)
                        .prepend($compile(ModalBuilderService.build(id, title, body, btn))($scope));
                $('#' + id).modal(); // show
            };

            $scope.msgConfirmed = function () {
                setTimeout(function () {
                    $window.location.href = '/';
                }, 3000);
            };

            // process the form
            $scope.processForm = function () {
                $http.post('./signup', $scope.formData)
                        .then(function (response) {
                            console.log("Signup status: " + response.status);
                            console.log(response.data);
                            if (response.status == 203) {
                                $scope.formData.succ = 'Successfully signed up. Redirecting you in 3 seconds ...';
                                if ($scope.formData.role == "member") {
                                    setTimeout(function () {
                                        $scope.showMsg();
                                    }, 200);
                                } else {
                                    setTimeout(function () {
                                        $window.location.href = '/';
                                    }, 3000);
                                }


                            }
                        }, function (response) {
                            $scope.formData.err = "Cannot sign up at the moment.\n\
                                            Make sure you're loggged into Google and try again";
                            console.log("Error in processing form. "
                                    + response.data);
                        });
            };
        }]);
}());
