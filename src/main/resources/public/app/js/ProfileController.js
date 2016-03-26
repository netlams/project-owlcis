(function () {
    var app = angular.module('authApp');

    /* Gets the Course JSON list */
    app.service('CompletedCourseService', function ($q, $http) {
        this.getDeptList = function () {
            var defer = $q.defer();
            $http.get('/api/completed-courses', {cache: 'true'})
                    .success(function (data) {
                        defer.resolve(data);
                    });
            return defer.promise;
        };
    });

    app.directive('profileCoursesMenu', function () {
        return {
            restrict: "E",
            template: "<form><div class='btn-group btn-group-justified' role='group'>"
                    + "<div class='btn-group' role='group'>"
                    + "<button class='btn btn-default' data-ng-click='addMode()'>Add Course</button>"
                    + "</div>"
                    + "<div class='btn-group' role='group'>"
                    + "<button class='btn btn-warning' data-ng-click='editMode()'>Edit Courses</button>"
                    + "</div>"
                    + "</div>"
                    + "<div id='menu-input' class='hidden form-group'>"
                    + "<div class='col-sm-3'>"
                    + "<select name='semester'>"
                    + "<option value='1'>Fall</option>"
                    + "<option value='2'>Spring</option>"
                    + "<option value='2'>Summer</option>"
                    + "</select>"
                    + "</div>"
                    + "<div class='col-sm-3'>"
                    + "<input type='number' value='2016' maxlength='4' minlength='4'>"
                    + "</div>"
                    + "<div class='col-sm-5'>"
                    + "<select name='course'><option value='1'>CIS 1001</option></select>"
                    + "</div>"
                    + "</div></form>",
            controller: function ($scope, $element, $attrs) {
                $scope.addMode = function () {
                    angular.element(document.querySelector('#menu-input')).toggleClass("hidden");
                };
                $scope.EditMode = function () {
                    var p = angular.element("<p />");
                    p.text("Appended");
                    $element.find("div").append(p);
                };
            }
        };
    });

    /* Profile Controller */
    app.controller('profileController', ['$scope', function ($scope) {

        }]);

    /* Completed Courses Controller */
    app.controller('completedCourseController', ['$scope', function ($scope) {
            $scope.courses = [{semester: 'Fall 2015', id: 'CIS 1001', name: 'Intro to Acad...'},
                {semester: 'Fall 2015', id: 'CIS 1056', name: 'Intro to Python...'},
                {semester: 'Fall 2015', id: 'CIS 1166', name: 'Math Concepts I'},
                {semester: 'Spring 2016', id: 'CIS 2166', name: 'Math Concepts II'},
                {semester: 'Spring 2016', id: 'CIS 2168', name: 'Data Structures'},
                {semester: 'Spring 2016', id: 'CIS 2107', name: 'Low-Level ...'}];
            $scope.tab = 'view';
            $scope.changeTab = function(tab) {
              $scope.tab = tab; 
              if (tab == 'view') {
//                  $("#courses").toggle("slow");
              }
            };
        }]);

}());