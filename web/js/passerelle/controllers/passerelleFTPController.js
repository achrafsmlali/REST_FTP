angular.module("passerelleFTP.passerelleFTP.Controllers.passerelleFTPController", ['passerelleFTP.passerelleFTP.Services.passerelleFTPService'])
        .controller('dirsController', ['$rootScope', '$scope', '$log', 'MessagesService', 'passerelleFTPService',
          function($rootScope, $scope, $log, MessagesService, passerelleFTPService) {
            $scope.dirs = [];
            $scope.currentPath = "";

            var loadDir = function(path) {
              return passerelleFTPService.getDirs(path, $rootScope.loggedUser).then(function(data) {
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
              }, function(error) {
                if (error.status == 401) {
                  $rootScope.loggedUser = {};
                  MessagesService.error("Non connecté");
                } else {
                  MessagesService.error(error);
                }
              });
            };
            function toBinaryString(data) {
              var ret = [];
              var len = data.length;
              var byte;
              for (var i = 0; i < len; i++) {
                byte = (data.charCodeAt(i) & 0xFF) >>> 0;
                ret.push(String.fromCharCode(byte));
              }

              return ret.join('');
            }
            var downloadFile = function(path) {

              downloadURL(document.location.href.replace(document.location.hash, "") + "rest" + path);
              return;
              return passerelleFTPService.downloadFile(path, $rootScope.loggedUser).then(function(data) {
                var data = toBinaryString(data.data);
                data = "data:application/bytes;base64," + btoa(data);
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


            $scope.changeDir = function(dir) {
              if (dir.type == "directory")
                loadDir($scope.currentPath + "/" + dir.name + "/");
              else
                downloadFile($scope.currentPath + "/" + dir.name);
            };

            $scope.delete = function(dir) {
              return passerelleFTPService.delete($scope.currentPath + "/" + dir.name + "/", $rootScope.loggedUser).then(function(data) {
                $scope.current.listFile.splice($scope.current.listFile.indexOf(dir), 1);
              }, function(error) {
                if (error.status == 401) {
                  MessagesService.error("Non connecté");
                  $rootScope.loggedUser = {};
                } else {
                  MessagesService.error(error);
                }
              });
            };
            $scope.fileChange = function(input) {
              if (input.files.length > 0) {
                $scope.file = input.files[0];
              } else {
                $scope.file = null;
              }
            };
            $scope.upload = function() {
              if (!$scope.file)
                return;
              return passerelleFTPService.uploadFile($scope.currentPath + "/" + $scope.file.name, $scope.file, $rootScope.loggedUser).then(function(data) {
                $scope.file = null;
                $scope.f = null;
                loadDir($scope.currentPath);
              }, function(error) {
                if (error.status == 401) {
                  MessagesService.error("Non connecté");
                  $rootScope.loggedUser = {};
                } else {
                  MessagesService.error(error);
                }
              });
            };
            $scope.mkdir = function() {
              var name = prompt("Nom du dossier");
              return passerelleFTPService.mkdir($scope.currentPath + "/" + name, $rootScope.loggedUser).then(function(data) {
                loadDir($scope.currentPath);
              }, function(error) {
                if (error.status == 401) {
                  MessagesService.error("Non connecté");
                  $rootScope.loggedUser = {};
                } else {
                  MessagesService.error(error);
                }
              });
            };

          }
        ]).filter('bytesToSize', function() {
  return function(bytes) {
    var k = 1000;
    var sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB'];
    if (bytes == null)
      return '';

    if (bytes == 0)
      return '0 Bytes';
    var i = parseInt(Math.floor(Math.log(bytes) / Math.log(k)), 10);
    return (bytes / Math.pow(k, i)).toPrecision(3) + ' ' + sizes[i];
  };
});