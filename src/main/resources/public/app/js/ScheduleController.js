(function () {
    var app = angular.module('authApp');

    /* Degree Long-name Service */
    app.service('DegreeInfoService', function () {
        this.getLongName = function (x) {
            if (x.toUpperCase() === "CSM")
                return "Computer Science & Math";
            else if (x.toUpperCase() === "IST")
                return "Information Science & Technology";
            else
                return "Computer Science";
        };
        this.getAdvising = function (x) {
            if (x.toUpperCase() === "CSM") {
                return {
                    name: 'Anthony Hughes',
                    email: 'hughes@temple.edu',
                    office: '344 SERC',
                    link: 'https://cis.temple.edu/drupal/user/141',
                    img: 'https://cis.temple.edu/sites/default/files/styles/portrait-large/public/user_pictures/picture-141-1435090532.jpg?itok=CfXQRzBx'
                };
            }
            else if (x.toUpperCase() === "IST")
                return {
                    name: 'Wendy Urban',
                    email: 'wurban@temple.edu',
                    office: '352 SERC',
                    link: 'https://cis.temple.edu/drupal/user/62',
                    img: 'https://cis.temple.edu/sites/default/files/styles/portrait-large/public/user_pictures/picture-62-1361970368.jpg?itok=LtwR0Ays'
                };
            else
                return {
                    name: 'Sally Kyvernitis',
                    email: 'sallyk@temple.edu',
                    office: '330 SERC',
                    link: 'https://cis.temple.edu/drupal/user/100',
                    img: 'https://cis.temple.edu/sites/default/files/styles/portrait-large/public/user_pictures/picture-100-1381599137.jpg?itok=xzs8-T3J'
                };
        };
    });

    /** Schedule List View Controller */
    app.controller('scheduleListViewCtrl', ['$scope', 'DTOptionsBuilder', 'DTColumnBuilder', 'DTColumnDefBuilder',
        function ($scope, DTOptionsBuilder, DTColumnBuilder, DTColumnDefBuilder) {
            var vm = this;
            $scope.loadData = function () {
                // Table options
                vm.dtOptions = {
                    ajax: './api/schedule',
                    dom: 'Bfrti',
                    paginationType: 'simple_numbers',
                    displayLength: 10,
                    aaSorting: [],
                    buttons: [
                        'columnsToggle', 'print', 'pdf', 'excel'
                    ]
                };
                // Column options 
                vm.dtColumns = [
                    DTColumnBuilder.newColumn('semester').withTitle('Semester').withClass('text-primary'),
                    DTColumnBuilder.newColumn('courseID').withTitle('Course ID'),
                    DTColumnBuilder.newColumn('courseTitle').withTitle('Name')
                ];
            };
            // run this
            $scope.loadData();
            
            $scope.$on('someEvent', function(event, msg) {
                console.log("view ctrl called");
            });
        }]);

    /** Schedule List Edit Controller */
    app.controller('scheduleListEditCtrl', ['$scope', '$http', '$filter', '$resource', 'DTOptionsBuilder', 'DTColumnBuilder', 'DTColumnDefBuilder', '$interval',
        function ($scope, $http, $filter, $resource, DTOptionsBuilder, DTColumnBuilder, DTColumnDefBuilder, $interval) {
            // global Datatable var
            var vm = this;
            // global timer control
            var resetMsgTimer;
            $scope.fetchData = function () {
                // Edit Mode stuff
                vm.data = $resource('./api/schedule').query();
                vm.dtOptions = {
                    dom: 'frti',
                    paginationType: 'simple_numbers',
                    displayLength: 10,
                    aaSorting: []
                };
                vm.dtColumnDefs = [
                    DTColumnDefBuilder.newColumnDef(0),
                    DTColumnDefBuilder.newColumnDef(1),
                    DTColumnDefBuilder.newColumnDef(2),
                ];
            };
            // run this
            $scope.fetchData();

            // Form defaults
            $scope.formData = {
                year: parseInt($filter('date')(new Date(), 'yy-MM-dd').substring(0, 2)), // get the 2-digit year
                season: null,
                semester: null,
                courseID: null,
                err: null,
                succ: null
            };

            function getCourseFromData(i) {
                return {
                    year: parseInt(vm.data[i].semester.substring(2, 4)),
                    season: vm.data[i].semester.substring(0, 2),
                    semester: vm.data[i].semester,
                    courseID: vm.data[i].courseID,
                    err: null,
                    succ: null
                };
            }

            // fetch and load list of add-able course names
            $http.get('./api/courselist', {cache: 'true'})
                    .success(function (data) {
                        $scope.availCourse = data;
                    })
                    .error(function (response) {
                        $scope.availCourse = null;
                        $scope.addTabFormData.err = "Failed to get course list";
                        console.log("Failed to get course list" + response);
                    });

            $scope.addSchedule = function () {
                $scope.formData.semester = $scope.formData.season +  $scope.formData.year;
                $http.post('./api/schedule/add', $scope.formData)
                        .then(function (response) {
                            if (response.status == 201) {
                                $scope.formData.err = null;
                                $scope.formData.succ = "Successfully added "
                                        + $scope.formData.courseID;
                                $scope.fetchData();
                            }
                        }, function (response) {
                            if (response.status == 400) {
                                $scope.formData.succ = null;
                                $scope.formData.err = "Course already exist";
                            }
                            else {
                                $scope.formData.err = "Failed to add";
                                $scope.formData.succ = null;
                                console.log("Error in processing form. "
                                        + response.data);
                            }
                        });
                       
                // set the timer to reset any alert messages
                if (angular.isDefined(resetMsgTimer)) return;
                resetMsgTimer = $interval(resetMsg, 5000); 
            };

            $scope.removeSchedule = function (i) {
                var removal = getCourseFromData(i);
                $http.post('./api/schedule/remove', removal)
                        .then(function (response) {
                            if (response.status == 200) {
                                $scope.formData.err = null;
                                $scope.formData.succ = "Deleted " + removal.courseID;
                                  vm.data.splice(i, 1);
                            }
                        }, function (response) {
                                $scope.formData.err = "Failed to add";
                                $scope.formData.succ = null;
                                console.log("Error in processing form. "
                                        + response.data);
                        });
                
                // set the timer to reset any alert messages
                if (angular.isDefined(resetMsgTimer)) return;
                resetMsgTimer = $interval(resetMsg, 5000);
            };
            
            /**
             * reset the alert messages
             */  
            resetMsg = function () {
                $scope.formData.succ = null;
                $scope.formData.err = null;
                $interval.cancel(resetMsgTimer);
                resetMsgTimer = undefined;
            };
        }]);

    /* Profile Controller */
    app.controller('scheduleController',
            ['$scope', '$http', '$filter', 'DegreeInfoService',
                function ($scope, $http, $filter, DegreeInfoService) {
                    // default tab choosen
                    $scope.tab = 'view';
                    // change tab to ...
                    $scope.changeTab = function (tab) {
                        $scope.tab = tab;
                    };

                    // initialize default values
                    $scope.profileData = {
                        degreeType: 'BS',
                        major: 'Computer Science',
                        advisor: "",
                        err: null,
                        succ: null
                    };
                    // Fetch Profile info
                    $http.get('./api/profile')
                            .success(function (response) {
                                $scope.profileData = response;
                                $scope.profileData.name = $scope.profileData.fname + " " + $scope.profileData.lname;
                                $scope.profileData.gradDate =
                                        $filter('date')($scope.profileData.gradDate, "MM/dd/yyyy");  // for type="date" binding
                                // get advisor info
                                $scope.profileData.advisor = DegreeInfoService.getAdvising($scope.profileData.major);
                                // get degree name
                                $scope.profileData.major = DegreeInfoService.getLongName($scope.profileData.major);
                            })
                            .error(function (response) {
                                $scope.profileOptions.err = "Failed to get profile data. \n\
                                        Make sure you are logged in.";
                                console.log(response);
                            });
                }]);
}());
