(function (angular) {
    'use strict';

    /* Services */
    angular.module('magazine.modules.Product.services', []).
        service('ProductService', ['$http',
            function ($http) {

                var serviceAddress = 'http://localhost:9004';
                var urlCollections = serviceAddress + '/api/products';
                var urlBase = serviceAddress + '/api/product/';

                this.find = function () {
                    return $http.get(urlCollections);
                };

                this.findOne = function (id) {
                    return $http.get(urlBase + id);
                };

                this.create = function (data) {
                    return $http.post(urlBase, data);
                };

                this.update = function (data) {
                    return $http.put(urlBase + data._id, data);
                };

                this.remove = function (data) {
                    return $http.delete(urlBase + '/id/' + data._id, data);
                };
            }
        ]);
})(angular);