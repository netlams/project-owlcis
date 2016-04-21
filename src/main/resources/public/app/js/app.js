/**
 * Created by dhruvin sheth
 */

var authApp = angular.module('authApp', ['ngAnimate', 'ngResource', 'ngSanitize', 'ui.router', 'ui.router', 'angular.filter', 'datatables', 'datatables.buttons', 'datatables.bootstrap']);

authApp.config(function ($stateProvider, $urlRouterProvider, $compileProvider) {

        $compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|ftp|mailto|chrome-extension):/);

        $urlRouterProvider.otherwise('/home');

        $stateProvider.state('first', {
            url: '/home',
            templateUrl: 'home.html',
            controller: 'firstController',
        }).state('second', {
            url: '/user',
            templateUrl: 'user.html',
            controller: 'secondController'
        }).state('third', {
            url: '/course',
            templateUrl: 'course.html',
            controller: 'thirdController'
        }).state('schedule', {
            url: '/schedule',
            templateUrl: 'schedule.html',
            controller: 'scheduleController'
        }).state('reviews', {
            url: '/reviews',
            templateUrl: 'reviews.html',
        }).state('signup', {
            url: '/signup',
            templateUrl: 'signup.html',
            controller: 'signupController'
        }).state('forum', {
            url: '/forum',
            templateUrl: 'forumhome.html',
            controller: 'forumhome'
        }).state('post', {
            url: '/post',
            templateUrl: 'forumpost.html',
            controller: 'forumpost'
        }).state('comments', {
            url: '/comments',
            templateUrl: 'forumcomments.html',
            controller: 'forumcomments'
        }).state('profile', {
            url: '/profile',
            templateUrl: 'profile.html',
            controller: 'profileController'
        }).state('review', {
            url: '/reviews',
            templateUrl: 'reviews.html',
            controller: 'review'
        }).state('moderator', {
            url: '/moderator',
            templateUrl: 'moderator.html',
            controller: 'delete'
        });
});
