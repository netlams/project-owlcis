/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

(function() {

    var forumpost = function($scope,$state,DBService,$http,$window) {

    	console.log("forum post called.");

    	DBService.getDemo().then(function(response) {
                    
    		//console.log("success in service")
                //window.alert(response.data);
                  $scope.Dataform;
            // get dept list
            /*
            CourseList.getCourseList()
                    .then(function (data) {
                        $scope.Dataform = {
                            selectedID: null,
                            course_ID: data,
                            err: null
                        };
                    });
*/
            // process the form
            $scope.Formprocess = function () {

                $http.post('/api/fv', $scope.Dataform.f)
                        .then(function (response) {
                            //window.alert("hello");
                            //console.log(response.data);
                            
                            //$scope.example2 = response.data;
                        }, function (response) {
                            console.log("Sending this  " + $scope.Dataform.f);
                        });
            };
                
                
                
                
    	
        }, function(error) {

    	});
    };
        
        
        
        
        
    

    forumpost.$inject = ['$scope','$state','DBService','$http','$window'];

    angular.module('authApp').controller('forumpost', forumpost);

}());