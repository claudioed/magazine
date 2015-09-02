
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
            url:'/edit-product/:productId',
            templateUrl:'app/components/product/view/edit-product.html',
            controller:'ProductEditController'
        }).state('gatherings',{
            url:'/gatherings',
            templateUrl:'app/components/gathering/view/gatherings.html',
            controller:'GatheringListController'
        }).state('deliveries',{
            url:'/deliveries',
            templateUrl:'app/components/delivery/view/deliveries.html',
            controller:'DeliveryListController'
        }).state('delivery-detail',{
            url:'/delivery/:id',
            templateUrl:'app/components/delivery/view/delivery.html',
            controller:'DeliveryDetailController'
        });

    }).run(function($state){
        $state.go('sales');
    });
})(angular);

