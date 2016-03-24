
(function () {
    var app = angular.module('authApp');
    /* Display JSON list */
    app.controller('PromiseCtrl', ['$scope', '$state', '$http', '$window',
        function ($scope, $state, $http, $window) {
            $http.get('/api/viewreviews').then(function (value) {
                $scope.example2 = value.data;
            });
        }]);
  
}());

  