(function (angular) {
    'use strict';

    // Services
    angular.module('magazine.services',
        [
            'magazine.modules.Sale.services',
            'magazine.modules.Delivery.services',
            'magazine.modules.Gathering.services',
            'magazine.modules.Product.services'
        ]);
})(angular);