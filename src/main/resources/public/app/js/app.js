/**
 * Created by dhruvin sheth
 */

var authApp = angular.module('authApp',['ui.router']);

authApp.config(function($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/home');

    $stateProvider

        .state('first', {
            url: '/home',
            templateUrl: 'home.html',
            controller: 'firstController'
        }).state('second', {
            url: '/user',
            templateUrl: 'user.html',
            controller:'secondController'
        }).state('third', {
            url: '/course',
            templateUrl: 'course.html',
            controller:'thirdController'
        }).state('fourth', {
            url: '/schedule',
            templateUrl: 'schedule.html',
            controller:'fourthController'
        }).state('signup', {
            url: '/signup',
            templateUrl: 'signup.html'
        });

});