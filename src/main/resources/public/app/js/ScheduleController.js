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
    
    /** Schedule List Controller */
    app.controller('ScheduleListController', function (DTOptionsBuilder, DTColumnBuilder) {
        var vm = this;
        vm.dtOptions = DTOptionsBuilder.fromSource('./api/profile/courses')
                .withBootstrap()
                .withDOM('frtip')
                .withPaginationType('simple_numbers')
                // Active Buttons extension
                .withButtons([
                    'columnsToggle',
                    'copy',
                    'print',
                    'pdf',
                    'excel'
                ]);

        vm.dtColumns = [
            DTColumnBuilder.newColumn('semester').withTitle('Semester').withClass('text-primary'),
            DTColumnBuilder.newColumn('courseID').withTitle('Course ID'),
            DTColumnBuilder.newColumn('courseTitle').withTitle('Name')
        ];
//        $("#schedule-table ul").hide();
    });

    /* Profile Controller */
    app.controller('scheduleController',
            ['$scope', '$http', '$filter', '$timeout', '$compile', '$templateCache', 'DegreeInfoService', 'DTOptionsBuilder', 'DTColumnBuilder', 'DTColumnDefBuilder',
                function ($scope, $http, $filter, $timeout, $compile, $templateCache, DegreeInfoService, DTOptionsBuilder, DTColumnBuilder, DTColumnDefBuilder) {
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
                    // dummy data
                    $scope.courses = [{semester: 'Fall 2015', id: 'CIS 1001', name: 'Intro to Acad...'},
                        {semester: 'Fall 2015', id: 'CIS 1056', name: 'Intro to Python...'},
                        {semester: 'Fall 2015', id: 'CIS 1166', name: 'Math Concepts I'},
                        {semester: 'Spring 2016', id: 'CIS 2166', name: 'Math Concepts II'},
                        {semester: 'Spring 2016', id: 'CIS 2168', name: 'Data Structures'},
                        {semester: 'Spring 2016', id: 'CIS 2107', name: 'Low-Level ...'}];

//            $scope.gg = function () {
//                window.open('data:application/vnd.ms-excel,' + $('#dvData').html());
//                //    e.preventDefault();
//            };

                    $templateCache.put('template', '<div><div><div><span>content is {{content}}</span></div></div></div>');

                    $scope.content = 'Hello, World!';

//            var el = $compile($templateCache.get('template'))($scope);
//            $('body').append(el);
//            $timeout(function () {
//                console.log(el.html());
//                $('body').dataTable({
//                    "dom": 'T<"clear">lfrtip',
//                    "tableTools": {
//                        "sSwfPath": "/swf/copy_csv_xls_pdf.swf"
//                    }
//                });
//            }, 300);   // wait for a short while

//            var vm = this;
//            vm.dtOptions = DTOptionsBuilder.fromSource('http://l-lin.github.io/angular-datatables/data.json')
//                    .withDOM('frtip')
//                    .withPaginationType('full_numbers')
//                    // Active Buttons extension
//                    .withButtons([
//                        'columnsToggle',
//                        'colvis',
//                        'copy',
//                        'print',
//                        'excel',
//                        {
//                            text: 'Some button',
//                            key: '1',
//                            action: function (e, dt, node, config) {
//                                alert('Button activated');
//                            }
//                        }
//                    ]);
//            vm.dtColumns = [
//                DTColumnBuilder.newColumn('id').withTitle('ID'),
//                DTColumnBuilder.newColumn('firstName').withTitle('First name'),
//                DTColumnBuilder.newColumn('lastName').withTitle('Last name')
//            ];
//  var vm = this;
//    vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
//        return $resource('data-sch.json').query().$promise;
//    }).withPaginationType('full_numbers');
//
//    vm.dtColumns = [
//        DTColumnBuilder.newColumn('id').withTitle('ID'),
//        DTColumnBuilder.newColumn('firstName').withTitle('First name'),
//        DTColumnBuilder.newColumn('lastName').withTitle('Last name').notVisible()
//    ];

                }]);
}());
