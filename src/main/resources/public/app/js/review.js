(function () {
    var app = angular.module('authApp');

    /* Gets the dept JSON list */
    app.service('CourseList', function ($q, $http) {
        this.getCourseList = function () {
            var defer = $q.defer();
            $http.get('/api/courselist', {cache: 'true'})
                    .success(function (data) {
                        defer.resolve(data);
                    });
            return defer.promise;
        };
    });



    /* Display JSON list */
    app.controller('review', ['$scope', '$state', '$http', '$window', 'CourseList',
        function ($scope, $state, $http, $window, CourseList) {
            $http.get('/api/viewreviews').then(function (value) {
                $scope.example2 = value.data;
            });
            $scope.Dataform = {};
            // get dept list
            CourseList.getCourseList()
                    .then(function (data) {
                        $scope.Dataform = {
                            selectedID: null,
                            course_ID: data,
                            err: null
                        };
                    });
            $scope.countlikes = 0;
            // process the form
            $scope.Formprocess = function () {

                $http.post('/api/viewreviews', $scope.Dataform.selectedID)
                        .then(function (response) {
                            $scope.example2 = response.data;
                            $scope.thumbsup = $scope.example2[0].thumbsup;

                        }, function (response) {
                            console.log("Sending this" + $scope.Dataform.selectedID);
                        });
            };

            $scope.json_object = {};

            $scope.myjson = {}
            $scope.like = function (reviewid,thumbsup_) {
               // $scope.thumbsup++;
               thumbsup_++;
                $scope.json_object = {
                    'reviewid': reviewid,
                    'thumbsUp': thumbsup_,
                };

            $scope.myjson = $scope.json_object;
            console.log($scope.json_object);

                $http.post('/api/incthumbsup', $scope.json_object)
                        .then(function (response) {
                            $scope.update_like_response = response.data;
                            console.log($scope.update_like_response);
                        }, function (response) {
                            console.log("Sending this to db thumbs up:",
                                    $scope.thumbsup, $scope.example2[0].reviewid);
                        });
                //console.log($scope.update_like_response);
            };
            
             $scope.json_object_down = {};

            $scope.myjson2 = {}
            $scope.dislike = function (reviewid,thumbsdown_) {
               // $scope.thumbsdown++;
               thumbsdown_++;
                $scope.json_object_down = {
                    'reviewid': reviewid,
                    'thumbsDown': thumbsdown_,
                    
                };

            $scope.myjson2 = $scope.json_object_down;
            console.log($scope.json_object_down);

                $http.post('/api/incthumbsdown', $scope.json_object_down)
                        .then(function (response) {
                            $scope.update_dislike_response = response.data;
                            console.log($scope.update_dislike_response);
                        }, function (response) {
                            console.log("Sending this to db thumbs down:",
                                    $scope.thumbsdown, $scope.example2[0].reviewid);
                        });
                //console.log($scope.update_dislike_response);
            };


        }]);

}());  