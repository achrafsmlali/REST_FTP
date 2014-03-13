angular.module("passerelleFTP.passerelleFTP.Controllers.passerelleFTPController",['passerelleFTP.passerelleFTP.Services.passerelleFTPService'])
  .controller('dirsController', ['$rootScope', '$scope', '$log','MessagesService','passerelleFTPService',
    function ($rootScope, $scope, $log,MessagesService,passerelleFTPService) {
      $scope.dirs = [];
      $scope.currentPath = "";

      var loadDir = function (path) {
        return passerelleFTPService.getDirs(path).then(function (data) {
          $scope.current = data;
          data.listFile.push({
            creationDate: null,
            group: null,
            listFile: [],
            name: "..",
            owner: null,
            size: null,
            type: "directory"
          });
          $scope.search = "";
          $scope.currentPath = data.name;
        }, function (err) {
          MessagesService.error(err);
        });
      };

      loadDir($scope.currentPath);


      $scope.changeDir = function (dir) {
        loadDir($scope.currentPath + "/" + dir.name + "/");
      };
      $scope.delete = function(dir) {
          return passerelleFTPService.delete($scope.currentPath + "/" + dir.name + "/",$rootScope.loggedUser).then(function(data) {
              $scope.dirs.splice($scope.dirs.indexOf(dir),1);
         
          })
      }
    }
  ]);