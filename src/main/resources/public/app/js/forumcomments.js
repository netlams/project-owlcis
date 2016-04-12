/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

(function () {

    var forumcomments = function($scope,$state,DBService,$http,$window) {

       $http.get('/api/forum').then(function (value) {
            $scope.forumQues = value.data;
        });

    };

    forumcomments.$inject = ['$scope','$state','DBService','$http','$window'];

    angular.module('authApp').controller('forumcomments', forumcomments);

}());
