

(function() {

    var thirdController = function($scope,$state,DBService) {

    	console.log("third controller called.");

    	DBService.getDemo().then(function(success) {

    		console.log("success in service")

    	}, function(error) {

    	});

    };

    thirdController.$inject = ['$scope','$state','DBService'];

    angular.module('authApp').controller('thirdController', thirdController);

}());