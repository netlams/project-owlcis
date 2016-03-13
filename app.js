/**
 * Created by dhruvin sheth
 */

var authApp = angular.module('authApp',['ui.router']);

authApp.config(function($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/first');

    $stateProvider

        .state('first', {
            url: '/first',
            templateUrl: 'home.html',
            controller: 'firstController'
        }).state('second', {
            url: '/second',
            templateUrl: 'user.html',
            controller:'secondController'
        }).state('third', {
            url: '/third',
            templateUrl: 'course.html',
            controller:'thirdController'
        }).state('fourth', {
            url: '/fourth',
            templateUrl: 'schedule.html',
            controller:'fourthController'
        }).state('fifth', {
            url: '/fifth',
            templateUrl: 'review.html',
            controller:'fifthcontroller'
        }).state('signup', {
            url: '/signup',
            templateUrl: 'signup.html'
        });

});