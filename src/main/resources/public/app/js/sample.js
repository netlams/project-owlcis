/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


(function () {
    var app = angular.module('authApp');
//angular.module('myApp', [])
app.controller('myCtrl', [ '$scope' , '$http' , '$state' , '$window',function ($scope, $http, $state, $window) {
    $scope.hello = {name: "Boaz"};
    $scope.newName = "";
    $scope.sendPost = function() {
        var data = $.param({
            json: JSON.stringify({
                name: $scope.newName
            })
        });
        $http.post("/echo/json", data).success(function(data, status) {
            $scope.hello = data;
        });
    };                   
}]);


}());  