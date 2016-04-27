/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

(function () {

    var forumpost = function ($scope, $state, $http, $window) {

        $scope.forum_s;
        $http.get('/api/fs').then(function (value) {
            $scope.forum = value.data;
        });

        $http.get('/api/forum').then(function (value) {
            $scope.forumQues = value.data;
        });

        $scope.Formprocess = function () {

            $http.post('/api/fv', $scope.Dataform.f)
                    .then(function (response) {
                        $window.location.href = '/#/forum';
                    }, function (response) {
                        console.log("Sending this  " + $scope.Dataform.f);
                    });
        };

        $scope.like = function (post_id) {

            $scope.postid = post_id;

//                   window.alert($scope.postid);

            $http.post('/api/forumcom', $scope.postid)
                    .then(function (response) {
//                        window.alert($scope.postid);

                        $scope.forumcom = response.data;

                    }, function (response) {
                        console.log("Sending this  " + $scope.postid);
                    });


        };

    };

    forumpost.$inject = ['$scope', '$state', '$http', '$window'];

    angular.module('authApp').controller('forumpost', forumpost);

}());
