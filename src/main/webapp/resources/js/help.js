var helloWorld = 'helo';

var socket;

function openInExternalWindow(group) {
    window.open('/chat/chatbox/chat.jsf?groupid=' + group,'', "width=250, height=300");
}

function chatConnectionSocketListener(userName, password){
    var host = "ws://localhost:5091/myapp";
    var wSocket = new WebSocket(host);
    // called when a message is received
    wSocket.onmessage = function(event) {
        var received_msg = JSON.parse(event.data);
        openInExternalWindow(received_msg.groupid);
    };
    // called when socket closes
    wSocket.onclose = function() {
        alert("Connection is closed...");
    };

    wSocket.onopen = function(){
        //login user
        var myuser = {
            username:userName,
            password:password
        };
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
        this.socket.send(JSON.stringify(myuser));
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