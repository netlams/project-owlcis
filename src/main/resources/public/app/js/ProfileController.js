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
    app.controller('completedCourseController', ['$scope', '$http', function ($scope, $http) {
            // holder for list to delete
            $scope.deleteCourseList = [];
            // Fetch Profile info
            $http.get('/api/profile/courses')
                    .success(function (response) {
                        $scope.courses = response;
                        console.log(response);
                    })
                    .error(function (response) {
                        console.log(response);
                        alert("error");
                    });
            // dummy data
//            $scope.courses = [{semester: 'Fall 2015', id: 'CIS 1001', name: 'Intro to Acad...'},
//                {semester: 'Fall 2015', id: 'CIS 1056', name: 'Intro to Python...'},
//                {semester: 'Fall 2015', id: 'CIS 1166', name: 'Math Concepts I'},
//                {semester: 'Spring 2016', id: 'CIS 2166', name: 'Math Concepts II'},
//                {semester: 'Spring 2016', id: 'CIS 2168', name: 'Data Structures'},
//                {semester: 'Spring 2016', id: 'CIS 2107', name: 'Low-Level ...'}];
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