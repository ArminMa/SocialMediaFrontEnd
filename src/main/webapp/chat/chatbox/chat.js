var app = angular.module('anchat', [ "angularfaces" ]);
app.factory('UserService', function() {
    return {
        name : 'anonymous'
    };
});

app.controller('ChatController', function($scope , UserService) {
    // This initializes the Angular Model with the values of the JSF
    // bean attributes
    initJSFScope($scope);

    var group = $_GET('groupid');
    var newConnection = true;
    var user1Name = userLogdIn1;
    var passwordd = passworrdd;
    var user2Name = false;
    $scope.inputText = "";
    $scope.myTextarea= "";

    var host = "ws://localhost:5092/myapp";
    var wSocket = new WebSocket(host);
    // called when a message is received
    wSocket.onmessage = function(event) {
        var wuut = event.data;
        addMessage3(wuut);
        $scope.$apply();
    };
    wSocket.onclose = function() {
        alert("chat socket Connection is closed...");
    };
    wSocket.onopen = function(){
        var messagePojo = {
            sendername:userLogdIn1,
            message:"",
            groupid:group
        }
        wSocket.send(JSON.stringify(messagePojo));
    };
    wSocket.onerror = function(){
        alert("Error chat socket!");
    };

    this.socket = wSocket;

    $scope.setUser1Name = function(newValue, oldValue) {if ( newValue != oldValue) {this.user1Name = newValue;}};
    $scope.setUser2Name = function(newValue, oldValue) {if ( newValue != oldValue) {this.user2Name = newValue;}};
    $scope.$watch('chatBean.user1Name', $scope.setUser1Name);
    $scope.$watch('chatBean.user2Name', $scope.setUser2Name);

    $scope.buttonClict = function() {
        var groupid = $_GET('groupid');
        var message = $scope.inputText;
        var messagePojo = {
            groupid:group,
            message:message,
            sendername:userLogdIn1
        }
        wSocket.send(JSON.stringify(messagePojo));
    };

    function addMessage3(message) {
        $scope.myTextarea += message + '\n';
    }
});


