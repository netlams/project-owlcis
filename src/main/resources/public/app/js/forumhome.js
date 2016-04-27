/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


(function () {


    var forumhome = function ($scope, $state, $http, $window) {

        $scope.forum_s;
        $http.get('/api/fs').then(function (value) {
            $scope.forum = value.data;
            console.log(value)
        });

        $http.get('/api/forum').then(function (value) {
            $scope.forumQues = value.data;
            console.log(value)
        });
        $http.get('/api/forumcom').then(function (value) {
            $scope.forumcom = value.data;
        });
        $http.get('/api/forumComQ').then(function (value) {
            $scope.CommentQues = value.data;
        });

        $scope.forumsearch = function () {
            $http.post('/api/fs', $scope.forum_s.search)
                    .then(function (response) {
                        $scope.forum = response.data;

                    }, function (response) {
                    });

        };

        $scope.like = function (post_id, ques) {

            $scope.postid = post_id;
            $scope.ques = ques;
            $scope.json_object = {
                'post_id': post_id,
                'ques': ques,
            };
            $scope.myjson = $scope.json_object;
            $http.post('/api/forumcom', $scope.myjson)
                    .then(function (response) {

                    }, function (response) {
                        console.log("Sending this  " + $scope.postid);
                    });

        };


    };

    forumhome.$inject = ['$scope', '$state', '$http', '$window'];

    angular.module('authApp').controller('forumhome', forumhome);

}());
