/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



(function() {

    var review = function($scope,$state,DBService) {

    	console.log("more reviews called.");

    	DBService.getDemo().then(function(success) {

    		console.log("success in service")

    	}, function(error) {

    	});

    };

    review.$inject = ['$scope','$state','DBService'];

    angular.module('authApp').controller('reviews', review);

}());