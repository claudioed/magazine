(function (angular) {
    'use strict';

    // Controllers
    angular.module('magazine.controllers',
        [
            'magazine.modules.Sale.controllers',
            'magazine.modules.Delivery.controllers',
            'magazine.modules.Gathering.controllers',
            'magazine.modules.Product.controllers'
        ]);
})(angular);