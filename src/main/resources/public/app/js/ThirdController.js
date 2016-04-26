
(function () {
    var app = angular.module('authApp');

    /* Display JSON list */
    app.controller('thirdController', ['$scope', '$state', '$http', '$window', 'CourseList', '$filter',
        function ($scope, $state, $http, $window, CourseList, $filter) {
            $scope.semester = '';
            $scope.reviewText = '';
            $scope.courseID = '';
            $scope.recElectiveID = '';
            $scope.year = parseInt($filter('date')(new Date(), 'yy-MM-dd').substring(0, 2)); // get the 2-digit year
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

            $scope.send_fields = function () {
                console.log('Send Fields');
                console.log('userID', $scope.userID);
                console.log('Semester', $scope.semester);
                console.log('Comment', $scope.reviewText);
                console.log('Helpfulness', $scope.helpfulness);
                console.log('easiness', $scope.easiness);
                console.log('clarity', $scope.clarity);
                console.log('course id', $scope.Dataform2.selectedID);
                console.log('elective', $scope.recElectiveID);
                $scope.json_object = {
                    'userID': $scope.userID,
                    'courseID': $scope.Dataform2.selectedID,
                    'reviewText': $scope.reviewText,
                    'semester': $scope.season + $scope.year,
                    'helpfulness': $scope.helpfulness,
                    'easiness': $scope.easiness,
                    'clarity': $scope.clarity,
                    'hasRecElective': true,
                    'recElectiveID': $scope.recElectiveID,
                    'thumbsUp': 1,
                    'thumbsDown': 1
                };


                $http.post('/api/coursereviews', $scope.json_object)
                        .then(function (response) {
                            //success portion
                            $scope.message = "<b>Course Review successfully Added.</b>";
                            $scope.example2 = response.data;
                            console.log($scope.json_object);
                            console.log($scope.example2);
                        }, function (response) {
                            //so this is for error
                            // $.notify("Error","error");
                            $scope.message = "<b>Error: Review Can not be added.</b>";
                            console.log($scope.json_object);

                        });

            };

        }

    ]);



}());


 