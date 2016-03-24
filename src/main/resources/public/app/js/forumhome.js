/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



(function() {

    var forumhome = function($scope,$state,DBService) {

    	console.log("forum controller called.");

    	DBService.getDemo().then(function(success) {

    		console.log("success in service")

    	}, function(error) {

    	});

    };

    forumhome.$inject = ['$scope','$state','DBService'];

    angular.module('authApp').controller('forumhome', forumhome);

}());