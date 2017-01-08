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
    alert("in chatboxcontroller");

    var group = $_GET('groupid');
    alert("groupID = " + group);
    var newConnection = true;
    var user1Name = userLogdIn1;
    var passwordd = passworrdd;
    var user2Name = false;
    $scope.inputText = "";
    $scope.myTextarea= "";

    alert("creating chat socket!!!!");
    var host = "ws://localhost:5092/myapp";
    var wSocket = new WebSocket(host);
    // called when a message is received
    wSocket.onmessage = function(event) {
        alert("in on message in chat socket");
        alert("message: " + event.data.toString);
        addMessage3(event.data.toString);
    };
    wSocket.onclose = function() {
        alert("chat socket Connection is closed...");
    };
    wSocket.onopen = function(){
        alert(" chat Socket is connected");
    };
    wSocket.onerror = function(){
        alert("Fel chat socket!");
    };

    this.socket = wSocket;

    this.socket.sendMessage = function (group, message){
        alert("in chatsocket send message");
        var messagePojo = {
            groupid:group,
            message:message
        }
        alert(messagePojo.groupid + ", " + messagePojo.message);
        this.socket.send(JSON.stringify(messagePojo));
    }

    $scope.setUser1Name = function(newValue, oldValue) {if ( newValue != oldValue) {this.user1Name = newValue;}};
    $scope.setUser2Name = function(newValue, oldValue) {if ( newValue != oldValue) {this.user2Name = newValue;}};
    $scope.$watch('chatBean.user1Name', $scope.setUser1Name);
    $scope.$watch('chatBean.user2Name', $scope.setUser2Name);

    $scope.buttonClict = function() {
        alert("in buttonclick");
        addMessage3($scope.inputText);
        wSocket.send($scope.inputText);
        alert("after sendmessage");
    };

    function addMessage3( message) {
        alert("in add message");
        $scope.myTextarea += $scope.inputText + '\n';
        alert("addmessage3 done");
    }
});


