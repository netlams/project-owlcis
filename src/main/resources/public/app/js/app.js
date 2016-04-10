/**
 * Created by dhruvin sheth
 */

var authApp = angular.module('authApp',['ngAnimate', 'ui.router', 'angular.filter']);

authApp.config(function($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/home');

    $stateProvider
        .state('first', {
            url: '/home',
            templateUrl: 'home.html',
            controller: 'firstController',
            
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
        }).state('reviews', {
            url: '/reviews',
            templateUrl: 'reviews.html',
        }).state('signup', {
            url: '/signup',
            templateUrl: 'signup.html'
        }).state('forum', {
            url: '/forum',
            templateUrl: 'forumhome.html',
            controller:'forumhome'
        }).state('post', {
            url: '/post',
            templateUrl: 'forumpost.html',
            controller:'forumpost'
        }).state('search', {
            url: '/search',
            templateUrl: 'forumsearch.html',
            controller:'forumsearch'
        }).state('profile', {
            url: '/profile',
            templateUrl: 'profile.html',
            controller:'profileController'
        }).state('review', {
            url: '/reviews',
            templateUrl: 'reviews.html',
            controller:'review'
        });
});

