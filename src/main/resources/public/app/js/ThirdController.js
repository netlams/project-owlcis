
(function() {
    var app = angular.module('authApp');
 
  /* Display JSON list */
    app.controller('thirdController', ['$scope', '$state', '$http', '$window', 'CourseList',
        function ($scope, $state, $http, $window, CourseList) {
          $scope.semester = '';
          $scope.comment  = '';
          $scope.course_id = '';
      
            $scope.starh = {
                name: ''
            };
            
            $scope.stare = {
                name: ''
            };
            
            $scope.starc = {
                name: ''
            };
  
          $scope.send_fields = function (){
            console.log('Send Fields');
            console.log($scope.semester);
            console.log($scope.comment);
            console.log($scope.starh.name);
            console.log($scope.stare.name);
            console.log($scope.starc.name);
            console.log($scope.selected_course);
            $scope.json_object = { 
                                'userID': 20,
                                'courseID': 'CIS 1001 TEST', 
                                'reviewText': $scope.comment,
                                'semester': $scope.semester, 
                                'helpfulness': $scope.starh.name,
                                'esasiness': 2,
                                'clarity':2,
                                'thumbsUp': 1,
                                'thumbsDown': 1
                              };
           

            /*    $http.post('/api/coursereviews', $scope.json_object)
                        .then(function (response) {
                            $scope.example2 = response.data;
                            console.log($scope.example2);
                        }, function (response) {
                            
                        });      */            
     
            
        };
            
        }
            
        ]);
  
  

}());


 