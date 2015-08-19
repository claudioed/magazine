
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
        });

    }).run(function($state){
        $state.go('sales');
    });
})(angular);

