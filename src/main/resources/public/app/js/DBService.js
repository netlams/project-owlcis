

(function(){

    var DBService = function($http) {
        var factory = {};

        factory.getDemo = function() {

            var response = $http({
                method: 'GET',
                url: "http://jsonplaceholder.typicode.com/users/1"
            }).success(function(data, status, headers, config) {
                return data;

            }).error(function(err, status, headers, config) {
                return err;
            });
            return response; 
        }

        return factory;

    };

    DBService.$inject = ['$http'];

    angular.module('authApp').factory('DBService', DBService);

}());