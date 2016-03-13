
(function() {

    var fifthController = function($scope,$state,DBService) {

    	console.log("fifth controller called.");

    	DBService.getDemo().then(function(success) {

    		console.log("success in service")

    	}, function(error) {

    	});
    };

    fifthController.$inject = ['$scope','$state','DBService'];

    angular.module('authApp').controller('fifthController', fifthController);

}());