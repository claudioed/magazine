(function (angular) {
    'use strict';

    /* Services */
    angular.module('magazine.modules.Gathering.services', []).
        service('GatheringService', ['$http',
            function ($http) {

                var urlCollections = '/api/gatherings';
                var urlBase = '/api/gathering/';
                
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