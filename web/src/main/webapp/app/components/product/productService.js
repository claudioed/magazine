(function (angular) {
    'use strict';

    /* Services */
    angular.module('magazine.modules.Product.services', []).
        service('ProductService', ['$http',
            function ($http) {

                var urlCollections = '/api/products';
                var urlBase = '/api/product/';

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
                    return $http.put(urlBase + '/id/' + data._id, data);
                };

                this.remove = function (data) {
                    return $http.delete(urlBase + '/id/' + data._id, data);
                };
            }
        ]);
})(angular);