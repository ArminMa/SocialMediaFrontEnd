var app = angular.module('lisnerApp', [ "angularfaces" ]);

app.controller('lisnerController', function($scope) {
    // This initializes the Angular Model with the values of the JSF
    // bean attributes
    initJSFScope($scope);
    var helloWorld = 'helo';
    var user1Name = userLogdIn1;
    var userPassword = currentUserPassword;
    var chatSocketConnection = new chatConnectionSocketListener(user1Name, userPassword);

    $scope.startChatWithUser = function (hey) {
        alert("in searchChatUser.js startChatWithUser");
        alert(hey);
        chatSocketConnection.startChatWith(hey);
    }
});