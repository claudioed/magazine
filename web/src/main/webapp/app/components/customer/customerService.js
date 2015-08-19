(function (angular) {
    'use strict';

    /* Services */
    angular.module('magazine.modules.Customer.services', []).
        service('CustomerService', ['$http',
            function ($http) {

                var urlCollections = '/api/customers';
                var urlBase = '/api/customer/';
                
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