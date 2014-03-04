angular.module("passerelleFTP.User.Controllers.Logout",[])
  .controller('LogoutController', ['$rootScope','$location','MessagesService',
    function ($rootScope,$location,MessagesService) {
      MessagesService.clear();
      $rootScope.loggedUser = {};
      $location.path("/login");
    }
  ]);