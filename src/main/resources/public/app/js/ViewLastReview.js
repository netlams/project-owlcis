/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




(function() {

    var viewLastreview = function($scope,$state,DBService) {

    	
    	DBService.getDemo().then(function(success) {

    		console.log("success in service")

    	}, function(error) {

    	});

    };

    viewLastreview.$inject = ['$scope','$state','DBService'];

    angular.module('authApp').controller('viewLastreview', viewLastreview);

}());