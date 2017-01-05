(function() {
  'use strict';

  var app = angular.module('app', ['simpleChat']);

    app.controller('Shell', Shell);

  function Shell() {

    var vm = this;

      vm.userLogdIn1 = userLogdIn1;
      var user2Name = userLogdIn2;

    vm.messages = [
      {
        'username': vm.userLogdIn1,
        'content': 'Hi!'
      },
      {
        'username': 'Elisa',
        'content': 'Whats up?'
      },
      {
        'username': 'Matt',
        'content': 'I found this nice AngularJS Directive'
      },
      {
        'username': 'Elisa',
        'content': 'Looks Great!'
      }
    ];

    vm.username = 'Matt';

    vm.sendMessage = function(message, username) {
      if(message && message !== '' && username) {
        vm.messages.push({
          'username': username,
          'content': message
        });
      }
    };

  }

})();
