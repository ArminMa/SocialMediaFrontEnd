var app = angular.module('AngularChat', [ "angularfaces" ]).controller(
    'ChatController', function($scope) {
        // This initializes the Angular Model with the values of the JSF
        // bean attributes
        initJSFScope($scope);

        // open connection
        var connection = new WebSocket('ws://127.0.0.1:1337');

        // for better performance - to avoid searching in DOM
        var content = $('#content');
        var input = $('#input');
        var status = $('#status');

        // my color assigned by the server
        var myColor = false;
        // my name sent to the server
        var newConnection = true;
        var user1Name = userLogdIn1;
        var user2Name = false;
        var user1AsJson;
        var user2AsJson;
        $scope.setUser1 = function(newValue, oldValue) {if ( newValue != oldValue) {this.user1AsJson = newValue;}};
        $scope.setUser2 = function(newValue, oldValue) {if ( newValue != oldValue) {this.user2AsJson = newValue;}};
        $scope.setUser1Name = function(newValue, oldValue) {if ( newValue != oldValue) {this.user1Name = newValue;}};
        $scope.setUser2Name = function(newValue, oldValue) {if ( newValue != oldValue) {this.user2Name = newValue;}};
        $scope.$watch('chatBean.user1Name', $scope.setUser1Name);
        $scope.$watch('chatBean.user2Name', $scope.setUser2Name);
        $scope.$watch('chatBean.user1AsJson', $scope.setUser1);
        $scope.$watch('chatBean.user2AsJson', $scope.setUser2);

        // if user is running mozilla then use it's built-in WebSocket
        window.WebSocket = window.WebSocket || window.MozWebSocket;

        // if browser doesn't support WebSocket, just show some notification and exit
        if (!window.WebSocket) {
            content.html($('<p>', { text: 'Sorry, but your browser doesn\'t '
            + 'support WebSockets.'} ));
            input.hide();
            $('span').hide();
            return;
        }


        connection.onerror = function (error) {
            // just in there were some problems with conenction...
            content.html($('<p>', { text: 'Sorry, but there\'s some problem with your '
            + 'connection or the server is down.' } ));
        };

        // most important part - incoming messages
        connection.onmessage = function (message) {
            // try to parse JSON message. Because we know that the server always returns
            // JSON this should work without any problem but we should make sure that
            // the massage is not chunked or otherwise damaged.
            try {
                var json = JSON.parse(message.data);
            } catch (e) {
                console.log('This doesn\'t look like a valid JSON: ', message.data);
                return;
            }

            // NOTE: if you're not sure about the JSON structure
            // check the server source code above
            if (json.type === 'color') { // first response from the server with user's color
                myColor = json.data;
                status.text(user1Name + ': ').css('color', myColor);
                input.removeAttr('disabled').focus();
                // from now user can start sending messages
            } else if (json.type === 'history') { // entire message history
                var index = json.data.length -1 ;
                var i = 0;
                // insert every single message to the chat window
                for (var i=0; i < json.data.length; i++) {
                    addMessage(json.data[i].author, json.data[i].text,
                        json.data[i].color, new Date(json.data[i].time));
                }
            } else if (json.type === 'message') { // it's a single message
                input.removeAttr('disabled'); // let the user write another message
                addMessage(json.data.author, json.data.text,
                    json.data.color, new Date(json.data.time));
            } else {
                console.log('Hmm..., I\'ve never seen JSON like this: ', json);
            }
        };

        /**
         * Send mesage when user presses Enter key
         */
        input.keydown(function(e) {
            if (e.keyCode === 13) {
                var msg = $(this).val();
                if (!msg) {
                    return;
                }else if(newConnection == true){
                    connection.send(user1Name);
                    $(this).val('');
                    newConnection = false;
                    connection.send(msg);
                } else{
                    // send the message as an ordinary text
                    connection.send(msg);
                    $(this).val('');
                    // disable the input field to make the user wait until server
                    // sends back response
                    input.attr('disabled', 'disabled');

                    // // we know that the first message sent from a user their name
                    // if (username === false) {
                    //     username = msg;
                    // }
                }
                // send the message as an ordinary text

            }
        });

        /**
         * This method is optional. If the server wasn't able to respond to the
         * in 3 seconds then show some error message to notify the user that
         * something is wrong.
         */
        setInterval(function() {
            if (connection.readyState !== 1) {
                status.text('Error');
                input.attr('disabled', 'disabled').val('Unable to comminucate '
                    + 'with the WebSocket server.');
            }
        }, 3000);

        /**
         * Add message to the chat window
         */
        function addMessage(author, message, color, dt) {
            content.prepend('<p><span style="color:' + color + '">' + author + '</span> @ ' +
                + (dt.getHours() < 10 ? '0' + dt.getHours() : dt.getHours()) + ':'
                + (dt.getMinutes() < 10 ? '0' + dt.getMinutes() : dt.getMinutes())
                + ': ' + message + '</p>');
        }
    });
