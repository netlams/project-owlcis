
(function() {

    var fourthController = function($scope,$state,DBService) {

        console.log("fourth controller called.");

        DBService.getDemo().then(function(success) {

    		console.log("success in service")

    	}, function(error) {

    	});

    };

    fourthController.$inject = ['$scope','$state','DBService'];

    angular.module('authApp').controller('fourthController', fourthController);

}());