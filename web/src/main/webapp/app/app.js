
(function (angular) {
    'use strict';

    angular.module('magazine', [
        'ui.router',
        'magazine.controllers',
        'magazine.services'
    ]).config(function($stateProvider,$httpProvider){

        $stateProvider.state('sales',{
            url:'/sales',
            templateUrl:'app/components/sale/view/sales.html',
            controller:'SaleListController'
        }).state('create-delivery',{
            url:'/create-delivery',
            templateUrl:'app/components/delivery/view/delivery.html',
            controller:'DeliveryCreateController'
        }).state('products',{
            url:'/products',
            templateUrl:'app/components/product/view/products.html',
            controller:'ProductListController'
        }).state('edit-product',{
            url:'/edit-product',
            templateUrl:'app/components/product/view/product.html',
            controller:'ProductEditController'
        }).state('gatherings',{
            url:'/gatherings',
            templateUrl:'app/components/gathering/view/gatherings.html',
            controller:'GatheringListController'
        }).state('deliveries',{
            url:'/deliveries',
            templateUrl:'app/components/delivery/view/deliveries.html',
            controller:'DeliveryListController'
        });

    }).run(function($state){
        $state.go('sales');
    });
})(angular);

