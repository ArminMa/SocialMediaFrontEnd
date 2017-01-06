var helloWorld = 'helo';

var socket;

function awesome() {
    alert("awesome function alert window!!!!");
    var host = "ws://localhost:5091/myapp";
    var wSocket = new WebSocket(host);

    // called when a message is received
    wSocket.onmessage = function(event) {
        var received_msg = event.data;
        //öppna openInExternalWindow med gruppid

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

    this.socket = wSocket;

    this.sendMessage = function(message){
        alert("in loginToChat");
        alert(message);
        this.socket.send(message);
    }

}

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

    this.socket = wSocket;

    this.startChatWith = function (username){
        alert("in start chat with js");
        alert("username " + username);
        this.socket.send(username);
        alert("after socket send username" + username);
    }
}