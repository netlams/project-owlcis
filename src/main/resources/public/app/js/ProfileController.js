(function () {
    var app = angular.module('authApp');

    /* Gets the Course JSON list */
    app.service('CompletedCourseService', function ($q, $http) {
        this.getDeptList = function () {
            var defer = $q.defer();
            $http.get('/api/completed-courses', {cache: 'true'})
                    .success(function (data) {
                        defer.resolve(data);
                    });
            return defer.promise;
        };
    });

    /* Profile Controller */
    app.controller('profileController', ['$scope', function ($scope) {
            $scope.formData = {
                deptId: null,
                major: null,
                studentOrAlumniOptions: [{val: '1', name: 'student'},
                    {val: '0', name: 'alumni'}],
                availMajorOptions: [{shortname: 'CS', name: 'Computer Science'},
                    {shortname: 'CSM', name: 'Computer Science & Math'},
                    {shortname: 'IST', name: 'Information Science & Technology'}],
                availDegreeOptions: [{name: 'BS'}, {name: 'BA'}],
                err: null,
            };
        }]);

    /* Completed Courses Controller */
    app.controller('completedCourseController', ['$scope', function ($scope) {
            $scope.deleteCourseList = [];
            // dummy data
            $scope.courses = [{semester: 'Fall 2015', id: 'CIS 1001', name: 'Intro to Acad...'},
                {semester: 'Fall 2015', id: 'CIS 1056', name: 'Intro to Python...'},
                {semester: 'Fall 2015', id: 'CIS 1166', name: 'Math Concepts I'},
                {semester: 'Spring 2016', id: 'CIS 2166', name: 'Math Concepts II'},
                {semester: 'Spring 2016', id: 'CIS 2168', name: 'Data Structures'},
                {semester: 'Spring 2016', id: 'CIS 2107', name: 'Low-Level ...'}];
            // default tab choosen
            $scope.tab = 'view';
            // change tab to ...
            $scope.changeTab = function (tab) {
                $scope.tab = tab;
            };
            // process the form submission
            $scope.processForm = function() {
                alert("Removing these courses: " + $scope.deleteCourseList );
                console.log($scope.deleteCourseList);
            };
        }]);

}());