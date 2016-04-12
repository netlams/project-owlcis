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
            $http.get('./api/profile')
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
                    });
            // On submit
            $scope.processProfileForm = function () {
                $http.post('./api/profile', $scope.profileData)
                        .then(function (response) {
                            if (response.status == 200) {
                                $scope.profileOptions.succ = 'Successfully updated';
                            }
                        }, function (response) {
                            $scope.profileOptions.err = "Cannot update at the moment. \n\
                                        Make sure you are logged in.";
                            console.log("Error in processing form. "
                                    + response.data);
                        });
            };
        }]);

    /* Completed Courses Controller */
    app.controller('completedCourseController', ['$scope', '$http', '$filter', function ($scope, $http, $filter) {
            // default tab choosen
            $scope.tab = 'view';
            // change tab to ...
            $scope.changeTab = function (tab) {
                $scope.tab = tab;
            };
            // holder for list to delete
            $scope.deleteCourseList = [];
            // Map to mark course as 'about to be deleted'
            $scope.deleteListCheckMap = new Map();
            // Delete Tab defaults
            $scope.delTabFormData = {
                err: null,
                succ: null
            };
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
                $http.get('./api/profile/courses')
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
            $scope.fetchProfileCourse(); // fetch at page load
            // Fetch Add tab's form data (course list for selection list)
            $http.get('./api/courselist', {cache: 'true'})
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
                $http.post('./api/profile/add', $scope.addTabFormData)
                        .then(function (response) {
                            if (response.status == 201) {
                                $scope.addTabFormData.err = null;
                                $scope.addTabFormData.succ = "Successfully added "
                                        + $scope.addTabFormData.courseID;
                                $scope.fetchProfileCourse();
                            }
                        }, function (response) {
                            if (response.status == 400) {
                                $scope.addTabFormData.succ = null;
                                $scope.addTabFormData.err = "Course already exist";
                            }
                            else {
                                $scope.addTabFormData.err = "Failed to add";
                                $scope.addTabFormData.succ = null;
                                console.log("Error in processing form. "
                                        + response.data);
                            }
                        });
            };
            // Delete Tab's form submit
            $scope.deleteCourse = function () {
                var completed = false;
                for (var i = 0; i < $scope.deleteCourseList.length; i++) {
                    $http.post('/api/profile/delete', $scope.deleteCourseList[i])
                            .then(function (response) {
                                if (response.status == 200) {
                                    $scope.delTabFormData.err = null;
                                    $scope.delTabFormData.succ = "Successfully deleted from your history";
                                    completed = true;
                                }
                            }, function (response) {
                                $scope.delTabFormData.succ = null;
                                $scope.delTabFormData.err = "Could not delete. Make sure you are logged in.";
                                console.log("Error in processing form. "
                                        + response.data);
                            });
                }

                var timer = setInterval(function () {
                    if (completed) {
                        $scope.courses = [];
                        $scope.fetchProfileCourse();
                        $scope.resetDelete();
                        clearInterval(timer);
                    }
                }, 2000);
                // give about 3 seconds to finish delete req
            };
            // temporary add courses to list, before submitting request
            $scope.addToDeleteList = function (c, s) {
                $scope.deleteCourseList.push({courseID: c, semester: s});
                $scope.deleteListCheckMap.set(c, s)
            };
            // reset delete list
            $scope.resetDelete = function () {
                $scope.deleteListCheckMap = new Map();
                $scope.deleteCourseList = [];
            };
        }]);

}());
