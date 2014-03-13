<%--
 DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

 Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.

 Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 Other names may be trademarks of their respective owners.

 The contents of this file are subject to the terms of either the GNU
 General Public License Version 2 only ("GPL") or the Common
 Development and Distribution License("CDDL") (collectively, the
 "License"). You may not use this file except in compliance with the
 License. You can obtain a copy of the License at
 http://www.netbeans.org/cddl-gplv2.html
 or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 specific language governing permissions and limitations under the
 License.  When distributing the software, include this License Header
 Notice in each file and include the License file at
 nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 particular file as subject to the "Classpath" exception as provided
 by Oracle in the GPL Version 2 section of the License file that
 accompanied this code. If applicable, add the following below the
 License Header, with the fields enclosed by brackets [] replaced by
 your own identifying information:
 "Portions Copyrighted [year] [name of copyright owner]"

 Contributor(s):

 The Original Software is NetBeans. The Initial Developer of the Original
 Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
 Microsystems, Inc. All Rights Reserved.

 If you wish your version of this file to be governed by only the CDDL
 or only the GPL Version 2, indicate your decision by adding
 "[Contributor] elects to include this software in this distribution
 under the [CDDL or GPL Version 2] license." If you do not indicate a
 single choice of license, a recipient has the option to distribute
 your version of this file under either the CDDL, the GPL Version 2 or
 to extend the choice of license to its licensees as provided above.
 However, if you add GPL Version 2 code and therefore, elected the GPL
 Version 2 license, then the option applies only if the new code is
 made subject to such option by the copyright holder.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
  "http://www.w3.org/TR/html4/loose.dtd">

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=Edge" />
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/favicon.png" />
    <link rel="shortcut icon" type="image/png" href="${pageContext.request.contextPath}/favicon.png" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />

    <script type="text/javascript">
      // include angular loader, which allows the files to load in any order
      /*
       AngularJS v1.2.6
       (c) 2010-2014 Google, Inc. http://angularjs.org
       License: MIT
       */
      (function() {
        'use strict';
        function d(a) {
          return function() {
            var c = arguments[0], b, c = "[" + (a ? a + ":" : "") + c + "] http://errors.angularjs.org/1.2.6/" + (a ? a + "/" : "") + c;
            for (b = 1; b < arguments.length; b++)
              c = c + (1 == b ? "?" : "&") + "p" + (b - 1) + "=" + encodeURIComponent("function" == typeof arguments[b] ? arguments[b].toString().replace(/ \{[\s\S]*$/, "") : "undefined" == typeof arguments[b] ? "undefined" : "string" != typeof arguments[b] ? JSON.stringify(arguments[b]) : arguments[b]);
            return Error(c)
          }
        }
        (function(a) {
          var c = d("$injector"), b = d("ng");
          a = a.angular ||
                  (a.angular = {});
          a.$$minErr = a.$$minErr || d;
          return a.module || (a.module = function() {
            var a = {};
            return function(e, d, f) {
              if ("hasOwnProperty" === e)
                throw b("badname", "module");
              d && a.hasOwnProperty(e) && (a[e] = null);
              return a[e] || (a[e] = function() {
                function a(c, d, e) {
                  return function() {
                    b[e || "push"]([c, d, arguments]);
                    return g
                  }
                }
                if (!d)
                  throw c("nomod", e);
                var b = [], h = [], k = a("$injector", "invoke"), g = {_invokeQueue: b, _runBlocks: h, requires: d, name: e, provider: a("$provide", "provider"), factory: a("$provide", "factory"), service: a("$provide",
                          "service"), value: a("$provide", "value"), constant: a("$provide", "constant", "unshift"), animation: a("$animateProvider", "register"), filter: a("$filterProvider", "register"), controller: a("$controllerProvider", "register"), directive: a("$compileProvider", "directive"), config: k, run: function(a) {
                    h.push(a);
                    return this
                  }};
                f && k(f);
                return g
              }())
            }
          }())
        })(window)
      })(window);


      // include a third-party async loader library
      /*!
       * $script.js v1.3
       * https://github.com/ded/script.js
       * Copyright: @ded & @fat - Dustin Diaz, Jacob Thornton 2011
       * Follow our software http://twitter.com/dedfat
       * License: MIT
       */
      !function(a, b, c) {
        function t(a, c) {
          var e = b.createElement("script"), f = j;
          e.onload = e.onerror = e[o] = function() {
            e[m] && !/^c|loade/.test(e[m]) || f || (e.onload = e[o] = null, f = 1, c())
          }, e.async = 1, e.src = a, d.insertBefore(e, d.firstChild)
        }
        function q(a, b) {
          p(a, function(a) {
            return!b(a)
          })
        }
        var d = b.getElementsByTagName("head")[0], e = {}, f = {}, g = {}, h = {}, i = "string", j = !1, k = "push", l = "DOMContentLoaded", m = "readyState", n = "addEventListener", o = "onreadystatechange", p = function(a, b) {
          for (var c = 0, d = a.length; c < d; ++c)
            if (!b(a[c]))
              return j;
          return 1
        };
        !b[m] && b[n] && (b[n](l, function r() {
          b.removeEventListener(l, r, j), b[m] = "complete"
        }, j), b[m] = "loading");
        var s = function(a, b, d) {
          function o() {
            if (!--m) {
              e[l] = 1, j && j();
              for (var a in g)
                p(a.split("|"), n) && !q(g[a], n) && (g[a] = [])
            }
          }
          function n(a) {
            return a.call ? a() : e[a]
          }
          a = a[k] ? a : [a];
          var i = b && b.call, j = i ? b : d, l = i ? a.join("") : b, m = a.length;
          c(function() {
            q(a, function(a) {
              h[a] ? (l && (f[l] = 1), o()) : (h[a] = 1, l && (f[l] = 1), t(s.path ? s.path + a + ".js" : a, o))
            })
          }, 0);
          return s
        };
        s.get = t, s.ready = function(a, b, c) {
          a = a[k] ? a : [a];
          var d = [];
          !q(a, function(a) {
            e[a] || d[k](a)
          }) && p(a, function(a) {
            return e[a]
          }) ? b() : !function(a) {
            g[a] = g[a] || [], g[a][k](b), c && c(d)
          }(a.join("|"));
          return s
        };
        var u = a.$script;
        s.noConflict = function() {
          a.$script = u;
          return this
        }, typeof module != "undefined" && module.exports ? module.exports = s : a.$script = s
      }(this, document, setTimeout)


      // load all of the dependencies asynchronously.
      $script([
        '${pageContext.request.contextPath}/js/angular/angular.min.js',
        '${pageContext.request.contextPath}/js/angular/angular-resource.min.js',
        '${pageContext.request.contextPath}/js/angular/angular-route.min.js',
        '${pageContext.request.contextPath}/js/shared/controllers/MainController.js',
        '${pageContext.request.contextPath}/js/shared/controllers/MessagesController.js',
        '${pageContext.request.contextPath}/js/shared/directives/MessagesDirective.js',
        '${pageContext.request.contextPath}/js/shared/services/MessagesService.js',
        '${pageContext.request.contextPath}/js/user/controllers/LoginUserController.js',
        '${pageContext.request.contextPath}/js/user/controllers/LogoutUserController.js',
        '${pageContext.request.contextPath}/js/passerelle/services/passerelleFTPService.js',
        '${pageContext.request.contextPath}/js/passerelle/controllers/passerelleFTPController.js',
        '${pageContext.request.contextPath}/js/app.js',
        '${pageContext.request.contextPath}/js/angular-base64.min.js'
      ], function() {
        // when all is done, execute bootstrap angular application
        angular.bootstrap(document, ['passerelleFTP']);
      });

    </script>
    <style>
      html{-ms-touch-action:none;height:100%;width:100%;overflow-x:hidden;overflow-y:auto;position:relative}body{position:relative;font-family:Helvetica,Arial,sans-serif;background-color:#eee;color:#333;font-size:13pt;padding:0;height:100%;width:100%;font-weight:100;margin:0}header{position:absolute;width:100%;background:#373942;box-shadow:0 1px 3px rgba(0,0,0,.2);font-weight:700;height:42px;z-index:195}.menu_user{position:relative;float:right;color:#fff;margin-left:auto}.menu_user label{color:#fff;position:relative;display:block;line-height:34px;padding:0 27px;padding-left:30px;cursor:pointer}.menu_user input{visibility:hidden;width:0;float:right;margin:0}.menu_user .submenu{position:absolute;background:#373942;padding:7px;color:#fff;width:170px;overflow:auto;right:0}.menu_user .submenu ul{list-style:none;margin:0;padding:0}.menu_user a{color:#F3F3F3!important;font-size:14px;white-space:nowrap;display:block;line-height:25px}.menu_user label .icon{width:33px;height:33px;overflow:hidden;display:inline-block;float:left;border-radius:50%;margin-right:15px;box-shadow:0 0 #FFF,0 0 0 0 #eee,0 0 0 2px rgba(0,0,0,.2)}.menu_user label .icon img{width:100%}header .logo{padding:0 10px;float:left}header .logo img{height:42px}
    </style>
    <title ng-bind="$route.current.$$route.options.title">Passerelle FTP</title>
  </head>
<body>
  <div ng-view></div>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/css.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</body>
</html>