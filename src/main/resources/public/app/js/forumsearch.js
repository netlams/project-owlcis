/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

(function() {

    var forumsearch = function($scope,$state,DBService,$http,$window) {

    	console.log("forum search called.");

    	DBService.getDemo().then(function(response) {

    		console.log("success in service")
           
    	}, function(error) {

    	});

    };

    forumsearch.$inject = ['$scope','$state','DBService','$http','$window'];

    angular.module('authApp').controller('forumsearch', forumsearch);

}());