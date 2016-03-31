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
    app.controller('PromiseCtrl', ['$scope', '$state', '$http', '$window', 'CourseList',
        function ($scope, $state, $http, $window, CourseList) {
            $http.get('/api/viewreviews').then(function (value) {
                $scope.example2 = value.data;
            });
            $scope.Dataform = {};
            // get dept list
            CourseList.getCourseList()
                    .then(function (data) {
                        $scope.Dataform = {
                            selectedID: null,
                            course_ID: data,
                            err: null
                        };
                    });

            // process the form
            $scope.Formprocess = function () {

                $http.post('/api/viewreviews', $scope.Dataform.selectedID)
                        .then(function (response) {
                            $scope.example2 = response.data;
                        }, function (response) {
                            console.log("Sending this" + $scope.Dataform.selectedID);
                        });
            };
            
             $scope.send_fields = function (){
            console.log('Send Fields');
            console.log($scope.semester);
            console.log($scope.comment);
            console.log($scope.selected_course);
            $scope.json_object = { 
                                'userID': 1,
                                'courseID': 'CIS 1001', 
                                'reviewText': 'Very nice profesor, but a lot of labs',
                                'semester': 'Fall', 
                                'helpfulness': 2,
                                'esasiness': 2,
                                'clarity':2,
                                'thumbsUp': 1,
                                'thumbsDown': 1
                              };
            $scope.send_fields = function ($http) {

                $http.post('/api/viewreviews', $scope.Dataform.selectedID)
                        .then(function (response) {
                            $scope.example2 = response.data;
                        }, function (response) {
                            console.log("Sending this" + $scope.Dataform.selectedID);
                        });                  
            };
            
        };
            
        }]);

}());  