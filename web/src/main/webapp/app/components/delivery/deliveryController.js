(function (angular) {
    'use strict';

    // Controllers
    angular.module('magazine.modules.Delivery.controllers', []).

        controller('DeliveryCreateController',
        ['$scope', 'DeliveryService',
            function ($scope, DeliveryService) {

                $scope.resetForm = function () {
                    $scope.delivery = null;
                };

                $scope.create = function (delivery) {
                    DeliveryService.create(delivery).then(
                        function (data) {
                            console.log("Success on create Delivery!!!")
                        }, function (err) {
                            console.log("Error on create Delivery!!!")
                        });
                };
            }]).

        controller('DeliveryListController',
        ['$scope', 'DeliveryService',
            function ($scope, DeliveryService) {
                DeliveryService.find().then(function (data) {
                    $scope.deliveries = data;
                }, function (err) {
                    console.log(err);
                });
            }]).

        controller('DeliveryDetailController',
        ['$scope', 'DeliveryService',
            function ($scope, DeliveryService) {
                $scope.findOne = function (id) {
                    DeliveryService.findOne(id).then(function (data) {
                        $scope.delivery = data;
                    }, function (err) {
                        console.log(err);
                    });
                };

            }]);
})(angular);