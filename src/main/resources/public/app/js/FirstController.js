

(function() {

    var firstController = function($scope,$state,DBService) {

    	//console.log("first controller called.");

    	DBService.getDemo().then(function(success) {

    		//console.log("success in service")

    	}, function(error) {

    	});

    };

    firstController.$inject = ['$scope','$state','DBService'];

    angular.module('authApp').controller('firstController', firstController);
    
      /* Gets the dept JSON list */
    app.service('CourseCount', function ($q, $http) {
        this.getCourseCount = function () {
            var defer = $q.defer();
            $http.get('/api/coursecount', {cache: 'true'})
                    .success(function (data) {
                        defer.resolve(data);
                    });
            return defer.first;
        };
    });

}());