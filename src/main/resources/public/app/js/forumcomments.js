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
        $http.get('/api/forumcom').then(function (value) {
            $scope.forumcom = value.data;
        });
        
        
        
        
        
        $scope.Formprocess = function () {

                $http.post('/api/fvc', $scope.Commentform.reply)
                        .then(function (response) {
                           console.log("Sending this  " + $scope.Commentform.reply);
                            console.log("Sending this  " + $scope.Commentform.reply);
                           // $window.location.href = '/#/forum';
                        }, function (response) {
                            console.log("Sending this  " + $scope.Commentform.reply);
                        });
            };
        
        
        
        

    };

    forumcomments.$inject = ['$scope','$state','DBService','$http','$window'];

    angular.module('authApp').controller('forumcomments', forumcomments);

}());
