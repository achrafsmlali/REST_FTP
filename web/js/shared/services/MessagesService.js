'use strict';
angular.module('passerelleFTP.Shared.Services.Messages', [])
  .factory("MessagesService", [
    function () {
      var errors = [];
      var messages = [];
      var warnings = [];

      var closeMessage = function (message) {
        if(errors.indexOf(message)>=0) {
          errors.splice(errors.indexOf(message), 1);
        } else if(messages.indexOf(message)>=0) {
          messages.splice(messages.indexOf(message), 1);
        } else if(warnings.indexOf(message)>=0) {
          warnings.splice(warnings.indexOf(message), 1);
        }
      };
      var getMessageLocalized = function (m) {
        return m;
      };
      function error (m, err) {
        errors.push(getMessageLocalized(m));
      }
      function message (m, mess) {
        messages.push(getMessageLocalized(m));
      }
      function warning (m, wra) {
        warnings.push(getMessageLocalized(m));
      }
      function clean () {
        errors.splice(0,errors.length);
        messages.splice(0,messages.length);
        warnings.splice(0,warnings.length);
      }
      return {
        error: error,
        message: message,
        warning: warning,
        clean: clean,
        clear: clean,
        errors: errors,
        messages: messages,
        warnings: warnings,
        close: closeMessage
      };
    }
  ]);