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
//            get coursecount data and store it locall  
            $http.get('/api/coursecount')
                    .success(function(data){
                        var n = [];
                        for (var i=0;i<data.length;i++){
                            n.push=data[i].course_id;
                        }
                        console.log(n);
                        $scope.countt=data;
                    })
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
            
    
            
        }]);
 

}());  