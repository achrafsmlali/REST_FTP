angular.module('passerelleFTP.passerelleFTP.Services.passerelleFTPService', ["ngResource"])
  .factory("passerelleFTPService", ['$resource', '$http', '$q', '$log',
    function ($resource, $http, $q, $log) {
      var apiRootUrl = "rest";

      var dir = $resource(apiRootUrl + "/dir/:path/json", null, {
          "get": {
            method: "get"
          }
      });

      return {
        getDirs: function (path) {
            if(path == null) path = "/";
            path = path.replace(/\/$/, '')
          if(path == "") path = "/";
          var deferred = $q.defer();
          $http.get(apiRootUrl + "/dir/"+path.replace(/\/$/, '')+"/json").then(function (data) {
            if(data.status == 200) {
              return deferred.resolve(data.data);
            }
            return deferred.reject(data);
          });
          return deferred.promise;
        }
      };
    }
  ]);