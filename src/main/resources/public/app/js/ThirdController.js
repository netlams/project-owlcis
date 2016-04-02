
(function() {
    var app = angular.module('authApp');
 
  /* Display JSON list */
    app.controller('thirdController', ['$scope', '$state', '$http', '$window', 'CourseList',
        function ($scope, $state, $http, $window, CourseList) {
          $scope.semester = '';
          $scope.comment  = '';
          $scope.course_id = '';
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
                                'course_ID': {},
                                'err': null
          };

      
          $http.get('/api/courselist', {cache: 'true'})
            .success(function (data) {
                $scope.Dataform2.course_ID = data;
            });
  
          $scope.send_fields = function (){
            console.log('Send Fields');
            console.log($scope.semester);
            console.log($scope.comment);
            console.log($scope.starh.name);
            console.log($scope.stare.name);
            console.log($scope.starc.name);
            console.log($scope.Dataform2.selectedID);
            $scope.json_object = { 
                                'userID':      41,
                                'courseID':    $scope.Dataform2.selectedID, 
                                'reviewText':  $scope.comment,
                                'semester':    $scope.semester, 
                                'helpfulness': $scope.starh.name,
                                'easiness':   $scope.stare.name,
                                'clarity':     $scope.starc.name,
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


 