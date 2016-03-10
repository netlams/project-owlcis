(function () {

    var loginController = function ($scope, $state, DBService) {
        console.log("login controller called.");
        $scope.name = "John";
    };

    loginController.$inject = ['$scope', '$state', 'DBService'];

    angular.module('authApp').controller('loginController', loginController);

}());