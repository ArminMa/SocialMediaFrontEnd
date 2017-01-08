var helloWorld = 'helo';

var socket;

function openInExternalWindow(group) {
    alert("in open external window");
    window.open('/chat/chatbox/chat.jsf?groupid=' + group,'', "width=250, height=300");
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
        openInExternalWindow(received_msg.groupid);
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

function chatSocket(){
    alert("chatsocket function alert window!!!!");
    var host = "ws://localhost:5092/myapp";
    var wSocket = new WebSocket(host);
    // called when a message is received
    wSocket.onmessage = function(event) {
        alert("in on message in chat socket");
        alert("message: " + event.data.toString);
    };
    wSocket.onclose = function() {
        alert("Connection is closed...");
    };
    wSocket.onopen = function(){
        alert(" chat Socket is connected");
    };
    wSocket.onerror = function(){
        alert("Fel!");
    };

    this.socket = wSocket;

    this.sendMessage = function (group, message){
        alert("in chatsocket send message");
        var messagePojo = {
            groupid:group,
            message:message
        }
        alert(messagePojo.groupid + ", " + messagePojo.message);
        this.socket.send(JSON.stringify(messagePojo));
    }

}

function $_GET(param) {
    var vars = {};
    window.location.href.replace( location.hash, '' ).replace(
        /[?&]+([^=&]+)=?([^&]*)?/gi, // regexp
        function( m, key, value ) { // callback
            vars[key] = value !== undefined ? value : '';
        }
    );

    if ( param ) {
        return vars[param] ? vars[param] : null;
    }
    return vars;
}