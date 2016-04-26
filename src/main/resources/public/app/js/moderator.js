(function () {
    var app = angular.module('authApp');


    app.controller('delete', ['$scope', '$state', '$http', '$window',
        function ($scope, $state, $http, $window) {

            $http.get('/api/fs').then(function (value) {
                $scope.posts = value.data;
            });
            $http.get('/api/viewallreviews').then(function (value) {
                $scope.reviews = value.data;
                console.log(value);
            });

            $scope.deleteReview = function (y) {
                $http.post('/api/review/delete', y.comment_text)
                        .then(function (response) {
                            console.log(y.comment_text);
                        }, function (response) {
                        });
            };

            $scope.deletePost = function (x) {
                $http.post('/api/post/delete', x.search_keywords)
                        .then(function (response) {
                            console.log(x.search_keywords);
                        }, function (response) {
                        });
            };
        }]);


    /*   var moderator = function ($scope, $state, DBService, $http, $window) {
     
     $scope.forum_s;
     $http.get('/api/fs').then(function (value) {
     $scope.posts = value.data;
     });
     $http.get('/api/viewallreviews').then(function (value) {
     $scope.reviews = value.data;
     console.log(value);
     });
     
     $scope.Delete = function () {
     console.log("delete " + $('#f').val());
     };
     };
     
     moderator.$inject = ['$scope', '$state', 'DBService', '$http', '$window'];
     
     angular.module('authApp').controller('moderator', moderator);
     */
}());


