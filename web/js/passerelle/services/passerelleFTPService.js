angular.module('passerelleFTP.passerelleFTP.Services.passerelleFTPService', ['base64'])
  .factory("passerelleFTPService", ['$base64', '$http', '$q', '$log',
    function ($base64, $http, $q, $log) {
      var apiRootUrl = "rest";

      return {
        getDirs: function (path, user) {
          if(path == null) path = "/";
            path = path.replace(/\/$/, '')
          if(path == "") path = "/";
          var deferred = $q.defer();
          var encodedUserNameAndPassword = $base64.encode(user.username + ':' + user.password);
          $http.defaults.headers.common['Authorization'] = 'Basic ' + encodedUserNameAndPassword;
          $http.get(apiRootUrl + "/dir/"+path.replace(/\/$/, '')+"/json").then(function (data) {
            if(data.status == 200) {
              return deferred.resolve(data.data);
            }
            return deferred.reject(data);
          });
          return deferred.promise;
        },
        downloadFile: function (path, user) {
          var deferred = $q.defer();
          var encodedUserNameAndPassword = $base64.encode(user.username + ':' + user.password);
          $http.defaults.headers.common['Authorization'] = 'Basic ' + encodedUserNameAndPassword;
          $http.get(apiRootUrl + "/"+path.replace(/\/$/, '')).then(function (data) {
            if(data.status == 200) {
              return deferred.resolve(data.data);
            }
            return deferred.reject(data);
          });
          return deferred.promise;
        },
        delete: function (path, user) {
          var deferred = $q.defer();
          var encodedUserNameAndPassword = $base64.encode(user.username + ':' + user.password);
          $http.defaults.headers.common['Authorization'] = 'Basic ' + encodedUserNameAndPassword;
          $http.delete(apiRootUrl + "/"+path.replace(/\/$/, '')).then(function (data) {
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