(function (angular) {
    'use strict';

    // Controllers
    angular.module('magazine.modules.Product.controllers', []).

        controller('ProductEditController',
        ['$scope', 'ProductService',
            function ($scope, ProductService) {

                $scope.resetForm = function () {
                    $scope.product = null;
                };

                $scope.create = function (product) {
                    ProductService.create(product).then(
                        function (data) {
                            console.log("Success on create Product!!!")
                        }, function (err) {
                            console.log("Error on create Product!!!")
                        });
                };
            }]).

        controller('ProductListController',
        ['$scope', 'ProductService',
            function ($scope, ProductService) {
                ProductService.find().then(function (data) {
                    $scope.products = data;
                }, function (err) {
                    console.log(err);
                });
            }]).

        controller('ProductDetailController',
        ['$scope', 'ProductService',
            function ($scope, ProductService) {
                $scope.findOne = function (id) {
                    ProductService.findOne(id).then(function (data) {
                        $scope.product = data;
                    }, function (err) {
                        console.log(err);
                    });
                };

            }]);
})(angular);