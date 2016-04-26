(function () {
    var app = angular.module('authApp');

    /* Degree Long-name Analyzer Service */
    app.service('DegreeInfoService', function () {
        /**
         * Pass in abbr. degree name and get its long name
         */
        this.getLongName = function (x) {
            if (x.toUpperCase() === "CSM")
                return "Computer Science & Math";
            else if (x.toUpperCase() === "IST")
                return "Information Science & Technology";
            else
                return "Computer Science";
        };
        /**
         * Pass in abbr. degree name and get its long name
         */
        this.getShortName = function (x) {
            if (x.toUpperCase() === "Computer Science & Math")
                return "CSM";
            else if (x.toUpperCase() === "Information Science & Technology")
                return "IST";
            else
                return "CS";
        };
        /**
         * Pass in abbr. degree name and get its advisor info 
         */
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

    /* Bootstrap Modal Builder Service */
    app.factory('ModalBuilderService', function () {
        var factory = {};
        factory.build = function (id, title, body, btn) {
            return "<div class='modal fade' id='" + id + "' tabindex='-1' role='dialog' aria-labelledby='msg-label'>"
                    + "<div class='modal-dialog' role='document'>"
                    + "<div class='modal-content'>"
                    + "<div class='modal-header'>"
                    + "<button type='button' class='close' data-dismiss='modal' aria-label='Close'>"
                    + "<span aria-hidden='true'>&times;</span>"
                    + "</button>"
                    + "<h2 class='modal-title text-center' id='login-msg-label'>" + title + "</h2>"
                    + "</div>"
                    + "<div class='modal-body text-center'>"
                    + body
                    + "</div>"
                    + "<div class='modal-footer'>"
                    + btn
                    + "</div>"
                    + "</div>"
                    + "</div>"
                    + "</div>"
                    + "</div>";
        };
        return factory;
    });

    /** Schedule List View Controller */
    app.controller('scheduleListViewCtrl', ['$scope', 'DTOptionsBuilder', 'DTColumnBuilder', 'DTColumnDefBuilder',
        function ($scope, DTOptionsBuilder, DTColumnBuilder, DTColumnDefBuilder) {
            var vm = this;
            // Table options
            vm.dtOptions = {
                ajax: './api/schedule',
                dom: 'Bfrtip',
                paginationType: 'simple_numbers',
                displayLength: 16,
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
            // reload options
            vm.reloadData = reloadData;
            vm.dtInstance = {};

            /**
             * Func to reload
             */
            function reloadData() {
                var resetPaging = false;
                vm.dtInstance.reloadData(null, resetPaging);
            }

            // listen for event
            $scope.$on('fetchReload', function (event, msg) {
                if (msg === "view") {
                    reloadData();
                }
                ;
            });
        }]);

    /** Schedule List Edit Controller */
    app.controller('scheduleListEditCtrl', ['$scope', '$http', '$filter', '$resource', 'DTOptionsBuilder', 'DTColumnBuilder', 'DTColumnDefBuilder', '$interval',
        function ($scope, $http, $filter, $resource, DTOptionsBuilder, DTColumnBuilder, DTColumnDefBuilder, $interval) {
            // global Datatable var
            var vm = this;
            // global timer control
            var resetMsgTimer;

            /**
             * Func to fetch schedules
             */
            $scope.fetchData = function () {
                // Edit Mode stuff
                vm.data = $resource('./api/schedule').query();
                vm.dtOptions = {
                    dom: 'frtip',
                    paginationType: 'simple_numbers',
                    displayLength: 106,
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

            /**
             * gets a schedule from ajax table data
             */
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

            /**
             * Func to add schedule
             */
            $scope.addSchedule = function () {
                $scope.formData.semester = $scope.formData.season + $scope.formData.year;
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
                if (angular.isDefined(resetMsgTimer))
                    return;
                resetMsgTimer = $interval(resetMsg, 5000);
            };

            /**
             * Func to remove a schedule
             */
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
                if (angular.isDefined(resetMsgTimer))
                    return;
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

            // event listener
            $scope.$on('fetchReload', function (event, msg) {
                if (msg === "edit") {
                    $scope.fetchData();
                }
                ;
            });
        }]);

    /* Profile Controller */
    app.controller('scheduleController',
            ['$scope', '$http', '$filter', '$compile', '$window', '$interval', 'DegreeInfoService', 'ModalBuilderService',
                function ($scope, $http, $filter, $compile, $window, $interval, DegreeInfoService, ModalBuilderService) {
                    // default tab choosen
                    $scope.tab = 'view';
                    // change tab to ...
                    $scope.changeTab = function (tab) {
                        $scope.tab = tab;
                        $scope.$broadcast('fetchReload', tab);
                    };

                    // initialize default values
                    $scope.profileData = {
                        degreeType: 'BS',
                        major: 'Computer Science',
                        advisor: "",
                        err: null,
                        succ: null
                    };
                    $scope.flowchartData = {
                        year: null,
                        degree: null
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

                                // for flowchart
                                $scope.flowchartData.year = parseInt($scope.profileData.gradDate.substring(6, 10)) - 4;
                                $scope.flowchartData.degree = DegreeInfoService.getShortName($scope.profileData.major) + "_"
                                        + $scope.profileData.degreeType;
                            })
                            .error(function (response) {
                                $scope.profileOptions.err = "Failed to get profile data. \n\
                                        Make sure you are logged in.";
                                console.log(response);
                            });

                    // activate modal msg before making reset call to API backend         
                    $scope.showResetSchedule = function () {
                        var id, title, body, btn;
                        id = 'reset-msg-modal';
                        title = 'Confirm';
                        body = "Are you sure you want to <em>reset/delete</em> your entire schedule list?";
                        btn = "<button class='btn btn-default' data-dismiss='modal'>Cancel</button>"
                                + "<button data-ng-click='resetSchedule()' id='reset' class='btn btn-danger'>Reset</button>";
                        if ($('#' + id)) {
                            $('#' + id).remove();// remove old modal if exists
                        }
                        $(document.body)
                                .prepend($compile(ModalBuilderService.build(id, title, body, btn))($scope));
                        $('#' + id).modal(); // show
                    };

                    // function to make reset call to API backend         
                    $scope.resetSchedule = function () {
                        $('#reset-msg-modal .modal-footer #reset').prop('disabled', true); // disable this button
                        var space = "";
                        var loading = $interval(function () {
                            $('#reset-msg-modal .modal-body').html("Resetting" + space);
                            space = space + " .";
                        }, 200);

                        $http.delete('./api/schedule/reset')
                                .success(function (response) {
                                    setTimeout(function () {
                                        $interval.cancel(loading);
                                        $('#reset-msg-modal .modal-body').html('Done');
                                        $('#reset-msg-modal').modal('hide');
                                        $scope.$broadcast('fetchReload', 'view'); // broadcast event to reload data
                                        $scope.$broadcast('fetchReload', 'edit'); // broadcast event to reload data
                                    }, 1000);
                                    setTimeout(function () {
                                        $('#reset-msg-modal').remove(); // clean up
                                    }, 1500);
                                })
                                .error(function (response) {
                                    $interval.cancel(loading);
                                    $('#reset-msg-modal .modal-body').html('Failed to reset.');
                                    console.log("Failed to reset. " + response);
                                });
                    };
                    
                    // activate modal msg before making load schedule call to API backend
                    $scope.showLoadSchedule = function () {
                        var id, title, body, btn;
                        id = 'loadsch-msg-modal';
                        title = 'Confirm';
                        body = "Sure, we could load your favorite degree's courses in. Just confirm a few details."
                                + "<hr><form><div class='form-group'>"
                                + "<label for='year'>Degree requirements vary by year. Which year did you declared, or like?  (try 2012)</label>"
                                + "<input type='number' id='year' class='form-control modal-small-input' data-ng-model='flowchartData.year' value='" 
                                + $scope.flowchartData.year + "'/>"
                                + "<label for='degree'>Which degree?</label> " 
                                + "<select class='form-control modal-select-tag' data-ng-model='flowchartData.degree'>"
                                + "<option value='CS_BS'>CS_BS</option>"
                                + "<option value='CS_BA'>CS_BA</option>"
                                + "<option value='IST_BS'>IST_BS</option>"
                                + "<option value='IST_BA'>IST_BA</option>"
                                + "<option value='CSM_BS'>CSM_BS</option>"
                                + "</select></div></form>";
                        btn = "<button class='btn btn-default' data-dismiss='modal'>Cancel</button>"
                                + "<button data-ng-click='loadSchedule()' id='load' class='btn btn-danger'>Ready, set, go</button>";
                        if ($('#' + id)) {
                            $('#' + id).remove(); // remove old modal if exists
                        }
                        $(document.body)
                                .prepend($compile(ModalBuilderService.build(id, title, body, btn))($scope));
                        $('#' + id).modal(); // show
                    };
                    
                    // function to make load schedule call to API backend
                    $scope.loadSchedule = function () {
                        $('#loadsch-msg-modal .modal-footer #load').prop('disabled', true); // disable this button
                        var space = "";
                        var loading = $interval(function () {
                            $('#loadsch-msg-modal .modal-body').html("Loading" + space);
                            space = space + " .";
                        }, 200);
                        
                        $http.post('./api/schedule/load', $scope.flowchartData)
                                .success(function (response) {
                                    setTimeout(function () {
                                        $interval.cancel(loading);
                                        $('#loadsch-msg-modal .modal-body').html('Done');
                                        $('#loadsch-msg-modal').modal('hide');
                                        $scope.$broadcast('fetchReload', 'view'); // broadcast event to reload data
                                        $scope.$broadcast('fetchReload', 'edit'); // broadcast event to reload data
                                    }, 1000);
                                    setTimeout(function () {
                                        $('#loadsch-msg-modal').remove(); // clean up
                                    }, 1500);
                                })
                                .error(function (response) {
                                    $interval.cancel(loading);
                                    $('#loadsch-msg-modal .modal-body').html('Failed to load schedule list.');
                                    console.log("Failed to load your selected degree. Try CS_BS, 2012 " + response);
                                });
                    };


                    // actives modal menu
                    $scope.showFlowchart = function () {
                        var id, title, body, btn;
                        id = 'fc-msg-modal';
                        title = 'Confirm';
                        body = "Alright, we're about to generate your very own custom flowchart <i class='fa fa-smile-o' aria-hidden='true'></i>"
                                + " for the degree requirements. But before we do, we need to confirm a few details. "
                                + "<hr><form><div class='form-group'>" 
                                + "<label for='year'>What year did you declared your major? (try 2012)</label>"
                                + "<input type='number' class='form-control modal-small-input' data-ng-model='flowchartData.year' value='" + $scope.flowchartData.year + "'/>"
                                + "<label for='degree'>Your degree is </label>"
                                + "<select class='form-control modal-select-tag' data-ng-model='flowchartData.degree'>"
                                + "<option value='CS_BS'>CS_BS</option>"
                                + "<option value='CS_BA'>CS_BA</option>"
                                + "<option value='IST_BS'>IST_BS</option>"
                                + "<option value='IST_BA'>IST_BA</option>"
                                + "<option value='CSM_BS'>CSM_BS</option>"
                                + "</select></div></form>";
                        btn = "<button class='btn btn-default' data-dismiss='modal'>Cancel</button>"
                                + "<button data-ng-click='genFlowchart()' class='btn btn-warning' data-dismiss='modal'>Generate</button>";
                        $(document.body)
                                .prepend($compile(ModalBuilderService.build(id, title, body, btn))($scope));
                        $('#' + id).modal();
                    };

                    // signal generate flowchart
                    $scope.genFlowchart = function () {
                        console.log("from gF() " + $scope.flowchartData.year + " " + $scope.flowchartData.degree);
                        setCookie('FC_YEAR', $scope.flowchartData.year, 1);
                        setCookie('FC_DEGREE', $scope.flowchartData.degree, 1);
                        $window.open("./dummy_flowchart.html", "_blank");
                    };
                    
                    function setCookie(cname, cvalue, exdays) {
                        var d = new Date();
                        d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
                        var expires = "expires=" + d.toUTCString();
                        document.cookie = cname + "=" + cvalue + "; " + expires;
                    }
                    ;
                }]);
}());
