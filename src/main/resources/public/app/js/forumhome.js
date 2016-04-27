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

        $scope.forumsearch = function () {
            $http.post('/api/fs', $scope.forum_s.search)
                    .then(function (response) {
//                        window.alert($scope.forum_s.search);

                        $scope.forum = response.data;


                    }, function (response) {
                    });

        };

        $scope.like = function (post_id, ques) {

            $scope.postid = post_id;
            $scope.ques = ques;
            //window.alert($scope.ques);
            $scope.json_object = {
                'post_id': post_id,
                'ques': ques,
            };
            $scope.myjson = $scope.json_object;
            // console.log($scope.json_object);

            window.alert($scope.json_object);
            //$http.post('/api/forumcom', $scope.postid,$scope.ques)
            $http.post('/api/forumcom', $scope.myjson)
                    .then(function (response) {
//                        window.alert($scope.postid);
                        window.alert($scope.myjson);
                        //$scope.forumcom = response.data;

                    }, function (response) {
                        console.log("Sending this  " + $scope.postid);
                    });



        };


    };

    forumhome.$inject = ['$scope', '$state', '$http', '$window'];

    angular.module('authApp').controller('forumhome', forumhome);

}());
