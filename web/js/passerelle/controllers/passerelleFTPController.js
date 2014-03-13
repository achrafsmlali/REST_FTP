angular.module("passerelleFTP.passerelleFTP.Controllers.passerelleFTPController",['passerelleFTP.passerelleFTP.Services.passerelleFTPService'])
  .controller('dirsController', ['$rootScope', '$scope', '$log','MessagesService','passerelleFTPService',
    function ($rootScope, $scope, $log,MessagesService,passerelleFTPService) {
      $scope.dirs = [];
      $scope.currentPath = "";

      var loadDir = function (path) {
        return passerelleFTPService.getDirs(path, $rootScope.loggedUser).then(function (data) {
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
      function toBinaryString(data) {
            var ret = [];
            var len = data.length;
            var byte;
            for (var i = 0; i < len; i++) { 
                byte=( data.charCodeAt(i) & 0xFF )>>> 0;
                ret.push( String.fromCharCode(byte) );
            }

            return ret.join('');
        }
      var downloadFile = function(path) { 
        return passerelleFTPService.downloadFile(path, $rootScope.loggedUser).then(function(data) {
            var data = toBinaryString(data.data);
            data = "data:application/bytes;base64,"+btoa(data);
            document.location = data;
        });
      };

      loadDir($scope.currentPath);


      $scope.changeDir = function (dir) {
        if(dir.type == "directory")
            loadDir($scope.currentPath + "/" + dir.name + "/");
        else
            downloadFile($scope.currentPath + "/" + dir.name);
      };
    }
  ]);