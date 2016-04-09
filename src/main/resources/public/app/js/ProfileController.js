(function () {
    var app = angular.module('authApp');

    /* Profile Controller */
    app.controller('profileController', ['$scope', '$http', '$filter',
        function ($scope, $http, $filter) {
            // Options
            $scope.profileOptions = {
                deptId: null,
                major: null,
                studentOrAlumniOptions: [{val: 'student', name: 'student'},
                    {val: 'alumni', name: 'alumni'}],
                availMajorOptions: [{shortname: 'CS', name: 'Computer Science'},
                    {shortname: 'CSM', name: 'Computer Science & Math'},
                    {shortname: 'IST', name: 'Information Science & Technology'}],
                availDegreeOptions: [{name: 'BS'},
                    {name: 'BA'},
                    {name: 'Master'},
                    {name: 'Ph.D'}],
                err: null,
                succ: null
            };
            // Fetch Profile info
            $http.get('/api/profile')
                    .success(function (response) {
                        $scope.profileData = response;
                        $scope.profileData.name = $scope.profileData.fname + " " + $scope.profileData.lname;
                        $scope.profileData.gradDate =
                                $filter('date')($scope.profileData.gradDate, "MM/dd/yyyy");  // for type="date" binding
                    })
                    .error(function (response) {
                        $scope.profileOptions.err = "Failed to get profile data. \n\
                                        Make sure you are logged in.";
                        console.log(response);
                        alert("error");
                    });
            // On submit
            $scope.processProfileForm = function () {
                $http.post('/api/profile', $scope.profileData)
                        .then(function (response) {
                            if (response.status == 200) {
                                $scope.profileOptions.succ = 'Successfully updated';
                            }
                        }, function (response) {
                            $scope.profileOptions.err = "Cannot update at the moment. \n\
                                        Make sure you are logged in.";
                            console.log("Error in processing form. "
                                    + response.data);
                            alert("error");
                        });
            };
        }]);

    /* Completed Courses Controller */
    app.controller('completedCourseController', ['$scope', '$http', '$filter', function ($scope, $http, $filter) {
            // holder for list to delete
            $scope.deleteCourseList = [];
            // Add Tab defaults
            $scope.addTabFormData = {
                year: parseInt($filter('date')(new Date(), 'yy-MM-dd').substring(0, 2)), // get the 2-digit year
                season: null,
                semester: null,
                courseID: null,
                err: null,
                succ: null
            };
            $scope.fetchProfileCourse = function () {
                // Fetch Profile info
                $http.get('/api/profile/courses')
                        .success(function (data) {
                            $scope.courses = data;
                        })
                        .error(function (response) {
                            console.log("Failed to get student course list" + response);
                            $scope.viewTabFormData = {
                                err: "Failed to get student course list"
                            };
                        });
            };
            $scope.fetchProfileCourse();
            // Fetch Add tab's form data (course list for selection list)
            $http.get('/api/courselist', {cache: 'true'})
                    .success(function (data) {
                        $scope.addTabFormOpt = {
                            availCourse: data,
                        };
                    })
                    .error(function (response) {
                        $scope.addTabFormOpt = {
                            availCourse: null,
                        };
                        $scope.addTabFormData.err = "Failed to get course list";
                        console.log("Failed to get course list" + response);
                    });
            // Add tab's form submit
            $scope.addCourse = function () {
                $scope.addTabFormData.semester = $scope.addTabFormData.season + $scope.addTabFormData.year;
                $http.post('/api/profile/add', $scope.addTabFormData)
                        .then(function (response) {
                            if (response.status == 201) {
                                $scope.addTabFormData.succ = "Successfully added";
                                $scope.addTabFormData.err = null;
                                $scope.fetchProfileCourse();
                            }
                        }, function (response) {
                            if (response.status == 400) {
                                $scope.addTabFormData.err = "Course already exist";
                                $scope.addTabFormData.succ = null;
                            }
                            else {
                            $scope.addTabFormData.err = "Failed to add";
                            $scope.addTabFormData.succ = null;
                            console.log("Error in processing form. "
                                    + response.data);
                            }
                        });
            };

            // default tab choosen
            $scope.tab = 'view';
            // change tab to ...
            $scope.changeTab = function (tab) {
                $scope.tab = tab;
            };
            // process the form submission
            $scope.processForm = function () {
                alert("Removing these courses: " + $scope.deleteCourseList);
                console.log($scope.deleteCourseList);
            };
        }]);

}());