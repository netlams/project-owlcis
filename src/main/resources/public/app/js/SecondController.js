
(function() {

    var secondController = function($scope,$state,DBService) {

    	console.log("second controller called.");

    	DBService.getDemo().then(function(success) {

    		console.log("success in service")

    	}, function(error) {

    	});
    };

    secondController.$inject = ['$scope','$state','DBService'];

    angular.module('authApp').controller('secondController', secondController);

}());