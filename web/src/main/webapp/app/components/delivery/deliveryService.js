(function (angular) {
    'use strict';

    /* Services */
    angular.module('magazine.modules.Delivery.services', []).
        service('DeliveryService', ['$http',
            function ($http) {

                var serviceAddress = 'http://localhost:9007';
                var urlCollections = serviceAddress + '/api/deliveries';
                var urlBase = serviceAddress +'/api/delivery/';

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