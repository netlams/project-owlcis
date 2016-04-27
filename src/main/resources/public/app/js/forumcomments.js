/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

(function () {

    var forumcomments = function($scope,$state,$http,$window) {

       $http.get('/api/forum').then(function (value) {
            $scope.forumQues = value.data;
        });
        $http.get('/api/forumcom').then(function (value) {
            $scope.forumcom = value.data;
        });
        
        $http.get('/api/forumComQ').then(function (value) {
            $scope.CommentQues = value.data;
        });
        
           //window.alert($scope.CommentQues.data);
        
        
        $scope.Formprocess = function () {

                $http.post('/api/fvc', $scope.Commentform.reply)
                        .then(function (response) {
                            $window.location.href = '/#/forum';
                           //console.log("Sending this  " + $scope.Commentform.reply);
                            //console.log("Sending this  " + $scope.Commentform.reply);
                           // $window.location.href = '/#/forum';
                        }, function (response) {
                            console.log("Sending this  " + $scope.Commentform.reply);
                        });
            };
            $scope.like = function (post_id) {
              
                $scope.postid = post_id;
               
//                   window.alert($scope.postid);
                   
                $http.post('/api/forumcom', $scope.postid)
                    .then(function (response) {
//                        window.alert($scope.postid);
                       
                        $scope.forumcom = response.data;
                     
                    }, function (response) {
                        console.log("Sending this  " + $scope.postid);
                    });
                    
                    
                    

             
            };
            
        
        
        
        

    };

    forumcomments.$inject = ['$scope','$state','$http','$window'];

    angular.module('authApp').controller('forumcomments', forumcomments);

}());
