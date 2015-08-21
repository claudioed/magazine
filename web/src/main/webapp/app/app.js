
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
        }).state('deliveries',{
            url:'/deliveries',
            templateUrl:'app/components/delivery/view/deliveries.html',
            controller:'DeliveryListController'
        });

    }).run(function($state){
        $state.go('sales');
    });
})(angular);

