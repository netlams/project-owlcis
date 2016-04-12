(function () {
    var app = angular.module('authApp');

    /* Profile Controller */
    app.controller('scheduleController', ['$scope', '$http', '$filter',
        function ($scope, $http, $filter) {
            $scope.defaultData = {
              degreeType: 'BS',
              major: 'Computer Science',
              advisor: 'Sally Kyvernitis',
              advisorEmail: 'sallyk@temple.edu',
              err: null,
              succ: null
            };
            // dummy data
            $scope.courses = [{semester: 'Fall 2015', id: 'CIS 1001', name: 'Intro to Acad...'},
                {semester: 'Fall 2015', id: 'CIS 1056', name: 'Intro to Python...'},
                {semester: 'Fall 2015', id: 'CIS 1166', name: 'Math Concepts I'},
                {semester: 'Spring 2016', id: 'CIS 2166', name: 'Math Concepts II'},
                {semester: 'Spring 2016', id: 'CIS 2168', name: 'Data Structures'},
                {semester: 'Spring 2016', id: 'CIS 2107', name: 'Low-Level ...'}];
        }]);
}());
