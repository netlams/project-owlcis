/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
(function () {
    var app = angular.module('authApp');

    /* Gets the dept JSON list */
    app.service('CourseCount', function ($q, $http) {
        this.getCourseCount = function () {
            var defer = $q.defer();
            $http.get('/api/coursecount', {cache: 'true'})
                    .success(function (data) {
                        defer.resolve(data);
                    });
            return defer.promise;
        };
    });


}());  