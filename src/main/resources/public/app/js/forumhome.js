/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


(function () {


    var forumhome = function ($scope, $state, DBService, $http, $window) {

        $scope.forum_s;
        $http.get('/api/fs').then(function (value) {
            $scope.forum = value.data;
            console.log(value)
        });

        $http.get('/api/forum').then(function (value) {
            $scope.forumQues = value.data;
            console.log(value)
        });

        $scope.forumsearch = function () {
            $http.post('/api/fs', $scope.forum_s.search)
                    .then(function (response) {
                        //window.alert("hello");
                        //console.log(response.data);
                        //console.log($scope.forum_s.search);
                        $scope.forum = response.data;
                    }, function (response) {
                        console.log("Sending this  " + $scope.forum_s.search);
                    });

        };

    };

    forumhome.$inject = ['$scope', '$state', 'DBService', '$http', '$window'];

    angular.module('authApp').controller('forumhome', forumhome);

}());
