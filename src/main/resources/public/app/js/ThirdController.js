
(function() {
    var app = angular.module('authApp');
 
  /* Display JSON list */
    app.controller('thirdController', ['$scope', '$state', '$http', '$window', 'CourseList',
        function ($scope, $state, $http, $window, CourseList) {
          $scope.semester = '';
          $scope.reviewText  = '';
          $scope.courseID = '';
          $scope.recElectiveID = '';
            $scope.starh = {
                name: ''
            };
            
            $scope.stare = {
                name: ''
            };
            
            $scope.starc = {
                name: ''
            };
            
           $scope.Dataform2 = {
                                'selectedID': null,
                                'courseID': {},
                                'err': null
          };

      
          $http.get('/api/courselist', {cache: 'true'})
            .success(function (data) {
                $scope.Dataform2.course_ID = data;
            });
  
          $scope.send_fields = function (){
            console.log('Send Fields');
            console.log('Semester',$scope.semester);
            console.log('Comment',$scope.reviewText);
            console.log('Helpfulness',$scope.helpfulness);
            console.log('easiness',$scope.easiness);
            console.log('clarity',$scope.clarity);
            console.log('course id',$scope.Dataform2.selectedID);
            console.log('elective', $scope.recElectiveID);
            $scope.json_object = { 
                                'userID':      41,
                                'courseID':    $scope.Dataform2.selectedID, 
                                'reviewText':  $scope.reviewText,
                                'semester':    $scope.semester, 
                                'helpfulness': $scope.helpfulness,
                                'easiness':   $scope.easiness,
                                'clarity':     $scope.clarity,
                                'hasRecElective': true,
                                'recElectiveID': $scope.recElectiveID,
                                'thumbsUp':    1,
                                'thumbsDown':  1
                              };
           
                $http.post('/api/coursereviews', $scope.json_object)
                        .then(function (response) {
                            $scope.example2 = response.data;
                            console.log($scope.example2);
                        }, function (response) {
                            
                        }); 
            
        };
            
        }
            
        ]);
  
  

}());


 