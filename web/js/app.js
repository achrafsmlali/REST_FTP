'use strict';

angular.module('passerelleFTP', [
  'ngRoute',
  'passerelleFTP.Shared.Directives.Messages',
  'passerelleFTP.Shared.Controllers.Messages',
  'passerelleFTP.User.Controllers.Login',
  'passerelleFTP.User.Controllers.Logout',
  'passerelleFTP.passerelleFTP.Services.passerelleFTPService',
  'passerelleFTP.passerelleFTP.Controllers.passerelleFTPController'
]).config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.when('/login', {
      templateUrl: 'partials/login.html',
      controller: 'LoginController',
      options: {
        name: 'login',
        connected: false,
        unconnected: true,
        title: 'Login'
      }
    });

    $routeProvider.when('/', {
      templateUrl: 'partials/dirs.html',
      controller: 'dirsController',
      options: {
        name: 'dirs',
        connected: true,
        unconnected: false,
        title: 'Explorer'
      }
    });

    $routeProvider.when('/404', {
      templateUrl: 'partials/404.html',
      options: {
        name: '404',
        connected: true,
        unconnected: true,
        title: 'Erreur 404'
      }
    });

    $routeProvider.otherwise({
      redirectTo: '/404',
      options: {}
    });
  }
]).run(['$rootScope', '$location', '$route', '$window', '$log',
  function($rootScope, $location, $route, $window, $log) {
    $rootScope.loggedUser = {};
    var prev_page = null;

    $rootScope.$watch('loggedUser', function (value) {
      if ($rootScope.loggedUser.username == null && $route.current != null && !$route.current.$$route.options.unconnected) {
        // no logged user, we should be going to #login
        if ($route.current.$$route.options.username == "login") {
          // already going to #login, no redirect needed
        } else {
          prev_page = $location.path();
          // not going to #login, we should redirect now
          $location.path("/login");
        }
      } else if ($route.current != null && $route.current.$$route.options.connected == false && $rootScope.loggedUser.username != null) {
        if (prev_page != null && prev_page != '/login') {
          $location.path(prev_page);
        } else {
          $location.path("/");
        }
      }
    });

    // register listener to watch route changes
    $rootScope.$on("$routeChangeStart", function(event, next, current) {
      if ($rootScope.loggedUser.username == null && (next.$$route == null || (next.$$route != null && !next.$$route.options.unconnected))) {
        // no logged user, we should be going to #login
        if (next.$$route && next.$$route.options.username == "login") {
          // already going to #login, no redirect needed
        } else {
          prev_page = $location.path();
          if(next.redirectTo != null) {
            next.redirectTo = "/login";
          }
          // not going to #login, we should redirect now
          $location.path("/login");
        }
      } else if (next.$$route != null && next.$$route.options.connected == false && $rootScope.loggedUser.username != null) {
        if (prev_page != null && prev_page != '/login') {
          $location.path(prev_page);
        } else {
          $location.path("/");
        }
      }
    });
  }
]);