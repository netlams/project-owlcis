/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

(function() {

    var forumpost = function($scope,$state,DBService) {

    	console.log("forum post called.");

    	DBService.getDemo().then(function(success) {

    		console.log("success in service")

    	}, function(error) {

    	});

    };

    forumpost.$inject = ['$scope','$state','DBService'];

    angular.module('authApp').controller('forumpost', forumpost);

}());