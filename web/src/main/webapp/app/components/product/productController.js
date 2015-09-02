(function (angular) {
    'use strict';

    // Controllers
    angular.module('magazine.modules.Product.controllers', []).

        controller('ProductEditController',
        ['$scope', '$stateParams','ProductService',
            function ($scope,$stateParams, ProductService) {

                var id = $stateParams.productId;
                ProductService.findOne(id).then(function (result) {
                    $scope.product = result.data;
                    console.log($scope.product);
                }, function (err) {
                    console.log(err);
                });
                
                $scope.resetForm = function () {
                    $scope.product.name = null;
                    $scope.product.price = null;
                };

                $scope.edit = function (productForm) {
                    ProductService.update($scope.product).then(
                        function (data) {
                            console.log("Success on update Product!!!")
                        }, function (err) {
                            console.log("Error on update Product!!!")
                        });
                };
            }]).

        controller('ProductListController',
        ['$scope', 'ProductService',
            function ($scope, ProductService) {
                ProductService.find().then(function (data) {
                    $scope.products = data.data;
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