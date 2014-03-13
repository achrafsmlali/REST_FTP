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
          
          downloadURL(document.location.href.replace(document.location.hash, "")+"rest"+path);
          return;
        return passerelleFTPService.downloadFile(path, $rootScope.loggedUser).then(function(data) {
            var data = toBinaryString(data.data);
            data = "data:application/bytes;base64,"+btoa(data);
            document.location = data;
        });
      };
      
      var downloadURL = function downloadURL(url) {
            var hiddenIFrameID = 'hiddenDownloader',
                iframe = document.getElementById(hiddenIFrameID);
            if (iframe === null) {
                iframe = document.createElement('iframe');
                iframe.id = hiddenIFrameID;
                iframe.style.display = 'none';
                document.body.appendChild(iframe);
            }
            iframe.src = url;
        };

      loadDir($scope.currentPath);


      $scope.changeDir = function (dir) {
        if(dir.type == "directory")
            loadDir($scope.currentPath + "/" + dir.name + "/");
        else
            downloadFile($scope.currentPath + "/" + dir.name);
      };
      
      $scope.delete = function(dir) {
        return passerelleFTPService.delete($scope.currentPath + "/" + dir.name + "/",$rootScope.loggedUser).then(function(data) {
            $scope.current.listFile.splice($scope.current.listFile.indexOf(dir),1);
        }, function (error) {
            alert(error);
        });
      }
    }
  ]);