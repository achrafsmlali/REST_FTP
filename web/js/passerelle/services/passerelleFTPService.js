angular.module('passerelleFTP.passerelleFTP.Services.passerelleFTPService', ['base64'])
        .factory("passerelleFTPService", ['$base64', '$http', '$q', '$log',
          function($base64, $http, $q, $log) {
            var apiRootUrl = "rest";

            return {
              getDirs: function(path, user) {
                if (path == null)
                  path = "/";
                path = path.replace(/\/$/, '')
                if (path == "")
                  path = "/";
                var deferred = $q.defer();
                var encodedUserNameAndPassword = $base64.encode(user.username + ':' + user.password);
                $http.defaults.headers.common['Authorization'] = 'Basic ' + encodedUserNameAndPassword;
                $http.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
                $http.get(apiRootUrl + "/dir/" + path.replace(/\/$/, '') + "/json").then(function(data) {
                  if (data.status == 200) {
                    return deferred.resolve(data.data);
                  }
                  return deferred.reject(data);
                }, function(error) {
                  return deferred.reject(error);
                });
                return deferred.promise;
              },
              downloadFile: function(path, user) {
                var deferred = $q.defer();
                var encodedUserNameAndPassword = $base64.encode(user.username + ':' + user.password);
                $http.defaults.headers.common['Authorization'] = 'Basic ' + encodedUserNameAndPassword;
                $http.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
                $http.get(apiRootUrl + "/" + path.replace(/\/$/, '')).then(function(data) {
                  if (data.status == 200) {
                    return deferred.resolve(data.data);
                  }
                  return deferred.reject(data);
                }, function(error) {
                  return deferred.reject(error);
                });
                return deferred.promise;
              },
              uploadFile: function(path, file, user) {
                var deferred = $q.defer();
                var encodedUserNameAndPassword = $base64.encode(user.username + ':' + user.password);
                $http.defaults.headers.common['Authorization'] = 'Basic ' + encodedUserNameAndPassword;
                $http.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
                var formData = new FormData();
                formData.append('file', file);
                $http({method: 'PUT', url: apiRootUrl + "/" + path.replace(/\/$/, ''), data: formData, headers: {'Content-Type': undefined}, transformRequest: angular.identity}).then(function(data) {
                  if (data.status == 200) {
                    return deferred.resolve(data.data);
                  }
                  return deferred.reject(data);
                }, function(error) {
                  return deferred.reject(error);
                });
                return deferred.promise;
              },
              delete: function(path, user) {
                var deferred = $q.defer();
                var encodedUserNameAndPassword = $base64.encode(user.username + ':' + user.password);
                $http.defaults.headers.common['Authorization'] = 'Basic ' + encodedUserNameAndPassword;
                $http.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
                $http.delete(apiRootUrl + "/" + path.replace(/\/$/, '')).then(function(data) {
                  if (data.status == 200) {
                    return deferred.resolve(data.data);
                  }
                  return deferred.reject(data);
                }, function(error) {
                  return deferred.reject(error);
                });
                return deferred.promise;
              },
              mkdir: function(path, user) {
                var deferred = $q.defer();
                var encodedUserNameAndPassword = $base64.encode(user.username + ':' + user.password);
                $http.defaults.headers.common['Authorization'] = 'Basic ' + encodedUserNameAndPassword;
                $http.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
                $http.post(apiRootUrl + "/mkdir/" + path.replace(/\/$/, '')).then(function(data) {
                  if (data.status == 200) {
                    return deferred.resolve(data.data);
                  }
                  return deferred.reject(data);
                }, function(error) {
                  return deferred.reject(error);
                });
                return deferred.promise;
              }
            };
          }
        ]);