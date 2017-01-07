var helloWorld = 'helo';

var socket;

function openInExternalWindow() {
    window.open('/chat/chatbox/chat.jsf','', "width=250, height=300");
}

function chatConnectionSocketListener(userName, password){
    alert("chatConnectionSocketListener function alert window!!!!");
    alert("username:" + userName + ", password: "  + password);
    var host = "ws://localhost:5091/myapp";
    var wSocket = new WebSocket(host);
    // called when a message is received
    wSocket.onmessage = function(event) {
        alert("in on message");
        var received_msg = JSON.parse(event.data);
        alert("after tostring");
        alert("data received: " + received_msg.groupid);
    };
    // called when socket closes
    wSocket.onclose = function() {
        alert("Connection is closed...");
    };

    wSocket.onopen = function(){
        alert(" Web Socket is connected, sending data");
        //login user
        var myuser = {
            username:userName,
            password:password
        };
        alert(JSON.stringify(myuser));
        wSocket.send(JSON.stringify(myuser));
    };

    wSocket.onerror = function(){
        alert("Fel!");
    };

    this.socket = wSocket;

    this.login = function login(){

    }

    this.startChatWith = function (username){
        var myuser = {
            username: username
        }
        alert("in start chat with js");
        alert(JSON.stringify(myuser));
        this.socket.send(JSON.stringify(myuser));
        alert("after socket send username" + username);
    }
}