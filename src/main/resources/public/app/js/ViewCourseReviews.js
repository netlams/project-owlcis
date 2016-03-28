(function () {
    var app = angular.module('authApp');
   
     /* Gets the dept JSON list */
    app.service('CourseList', function ($q, $http) {
        this.getCourseList = function () {
            var defer = $q.defer();
            $http.get('/api/courselist', {cache: 'true'})
                    .success(function (data) {
                        defer.resolve(data);
                    });
            return defer.promise;
        };
    });
    
   
    
    /* Display JSON list */
    app.controller('PromiseCtrl', ['$scope', '$state', '$http', '$window','CourseList',
        function ($scope, $state, $http, $window , CourseList) {
            $http.get('/api/viewreviews').then(function (value) {
                $scope.example2 = value.data;
            });
         // $http.get('/api/courselist').then(function(value) {
             // $scope.courseID = value.data;
          //  });
        
            $scope.Dataform = {};
            // get dept list
            CourseList.getCourseList()
                    .then(function (data) {
                        $scope.Dataform ={
                         
                           selectedID: null,
                            
                           course_ID:data, 
                           err: null
                       };
                         });

            // process the form
            $scope.Formprocess = function () {
               
                $http.post( '/api/viewreviews', $scope.Dataform.selectedID)
                        .then(function (response) {
                            console.log("the sel: " +  $scope.Dataform.selectedID);
                             console.log("Sending this" + $scope.Dataform.selectedID);
//                             console.log("data: " + response.data[0].comment_text);
                             $scope.example2 = response.data;
//                            console.log("status: " + response.status);
//                    $window.alert(response.data);
//                            console.log(response.data);
                            if (response.status == 203) {
//                                alert("Successfull");
                                //$window.location.href = '/reviews';
                                ;
                            }
                        }, function (response) {
                            console.log("Sending this" + $scope.Dataform.selectedID);
                               //$window.alert(response.data);
//                            $scope.Dataform.err = "Cannot sign up at the moment.\n\
//                                            Make sure you're loggged into Google and try again";
//                            console.log("Error in processing form. "
//                                    + response.data);
                        });
            };
        }]);
        
        
        
        
        
        
        
        
        
        
        
 
  
/*app.controller('CourselistCtrl', ['$scope', '$state', '$http', '$window', 
        function ($scope, $state, $http, $window) {
    
    $http.get('/api/courselist').then(function(value) {
        $scope.courseID = value.data;
                //value.status;
        
       // $scope.redirect = function(){
            //window.location = "/api/courselist";
           // window.location = "#/signup";
        //}
        
    });*/
                    
    
   
                
}());  