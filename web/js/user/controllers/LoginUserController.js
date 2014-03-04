angular.module("passerelleFTP.User.Controllers.Login",[])
  .controller('LoginController', ['$rootScope', '$scope', '$log','MessagesService',
    function ($rootScope, $scope, $log,MessagesService) {
      var user = {};
      $scope.user = user;

      $scope.login = function () {
        MessagesService.clear();
        $rootScope.loggedUser = {
          username: user.username,
          password: user.password
        };
      };
    }
  ]);