(function (angular) {
    'use strict';

    // Controllers
    angular.module('magazine.modules.Customer.controllers', []).

        controller('CustomerCreateController',
        ['$scope', 'CustomerService',
            function ($scope, CustomerService) {

                $scope.resetForm = function () {
                    $scope.customer = null;
                };

                $scope.create = function (customer) {
                    CustomerService.create(customer).then(
                        function (data) {
                            console.log("Success on create Customer!!!")
                        }, function (err) {
                            console.log("Error on create Customer!!!")
                        });
                };
            }]).

        controller('CustomerListController',
        ['$scope', 'CustomerService',
            function ($scope, CustomerService) {
                CustomerService.find().then(function (data) {
                    $scope.customers = data;
                }, function (err) {
                    console.log(err);
                });
            }]).

        controller('CustomerDetailController',
        ['$scope', 'CustomerService',
            function ($scope, CustomerService) {
                $scope.findOne = function (id) {
                    CustomerService.findOne(id).then(function (data) {
                        $scope.customer = data;
                    }, function (err) {
                        console.log(err);
                    });
                };

            }]);
})(angular);