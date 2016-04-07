/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

(function () {

    var app = angular.module('authApp');

     app.controller('LastReview', ['$scope', '$state', '$http', '$window', 'CourseList',
        function ($scope, $state, $http, $window, CourseList) {
            $http.get('/api/viewlastreviews').then(function (value) {
                $scope.example2 = value.data;
            });
           
        }]);
 

}());  