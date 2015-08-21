(function (angular) {
    'use strict';

    // Services
    angular.module('magazine.services',
        [
            'magazine.modules.Sale.services',
            'magazine.modules.Delivery.services'
        ]);
})(angular);