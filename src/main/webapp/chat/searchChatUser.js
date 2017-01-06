var app = angular.module('lisnerApp', [ "angularfaces" ]);


app.controller('lisnerController', function($scope) {
    // This initializes the Angular Model with the values of the JSF
    // bean attributes
    initJSFScope($scope);
    var helloWorld = 'helo';
    var user1Name = userNamee;
    var password = passwooord;
    var user2Name = chatWithThisUserName;
    var socket;
    var host = "ws://localhost:5091/myapp";
    var wSocket = new WebSocket(host);
    $scope.socket = wSocket;

    // called when a message is received
    wSocket.onmessage = function(event) {
        var received_msg = event.data;
        alert(received_msg);
    };
    // called when socket closes
    wSocket.onclose = function() {
        alert("Connection is closed...");
    };

    wSocket.onopen = function(){
        alert(" Web Socket is connected, sending data");
    };
    wSocket.onerror = function(){
        alert("Fel!");
    };




    $scope.sendSocketMessage = function(username, password, user2name){
        alert("in sendSocketMessage, user to chat with = " + user2name);
        user2Name = user2name;
        var user = {
            username: username,
            password: password
        };

        var userPojo = JSON.stringify({ type: 'message', data: user });
        $scope.socket.send(userPojo);
    };

    $scope.openInExternalWindow = function()  {
        window.open('/chat/chatbox/chat.jsf','', "width=300, height=300") ;
    };

    function listenChat(){
        alert("in listen chat function");

    }


});


function sendSocketMessage2(username, password, user2name){
    alert("in sendSocketMessage2, user to chat with = " + user2name);
    user2Name = user2name;
    var user = {
        username: username,
        password: password
    };

    var userPojo = JSON.stringify({ type: 'message', data: user });
    $scope.socket.send(userPojo);
}




