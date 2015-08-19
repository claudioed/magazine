(function (angular) {
    'use strict';

    // Controllers
    angular.module('magazine.modules.Sale.controllers', []).

        controller('SaleCreateController',
        ['$scope', 'SaleService',
            function ($scope, SaleService) {

                $scope.resetForm = function () {
                    $scope.sale = null;
                };

                $scope.create = function (sale) {
                    SaleService.create(sale).then(
                        function (data) {
                            console.log("Success on create Sale!!!")
                        }, function (err) {
                            console.log("Error on create Sale!!!")
                        });
                };
            }]).

        controller('SaleListController',
        ['$scope', 'SaleService',
            function ($scope, SaleService) {
                SaleService.find().then(function (data) {
                    $scope.sales = data;
                }, function (err) {
                    console.log(err);
                });
            }]).

        controller('SaleDetailController',
        ['$scope', 'SaleService',
            function ($scope, SaleService) {
                $scope.findOne = function (id) {
                    SaleService.findOne(id).then(function (data) {
                        $scope.sale = data;
                    }, function (err) {
                        console.log(err);
                    });
                };

            }]);
})(angular);