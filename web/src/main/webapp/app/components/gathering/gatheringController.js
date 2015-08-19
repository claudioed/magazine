(function (angular) {
    'use strict';

    // Controllers
    angular.module('magazine.modules.Gathering.controllers', []).

        controller('GatheringCreateController',
        ['$scope', 'GatheringService',
            function ($scope, GatheringService) {

                $scope.resetForm = function () {
                    $scope.sale = null;
                };

                $scope.create = function (gathering) {
                    GatheringService.create(gathering).then(
                        function (data) {
                            console.log("Success on create Gathering!!!")
                        }, function (err) {
                            console.log("Error on create Gathering!!!")
                        });
                };
            }]).

        controller('GatheringListController',
        ['$scope', 'GatheringService',
            function ($scope, GatheringService) {
                GatheringService.find().then(function (data) {
                    $scope.gatherings = data;
                }, function (err) {
                    console.log(err);
                });
            }]).

        controller('GatheringDetailController',
        ['$scope', 'GatheringService',
            function ($scope, GatheringService) {
                $scope.findOne = function (id) {
                    GatheringService.findOne(id).then(function (data) {
                        $scope.gathering = data;
                    }, function (err) {
                        console.log(err);
                    });
                };

            }]);
})(angular);