

(function () {

    var firstController = function ($scope, $state, DBService) {

        //console.log("first controller called.");

        DBService.getDemo().then(function (success) {

            //console.log("success in service")

        }, function (error) {

        });

    };

    firstController.$inject = ['$scope', '$state', 'DBService'];

    angular.module('authApp').controller('firstController', firstController);


}());