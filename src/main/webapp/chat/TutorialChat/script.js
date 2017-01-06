var app = angular.module('AngularChat', [ "angularfaces" ]).controller(
    'ChatController', function($scope) {
        // This initializes the Angular Model with the values of the JSF
        // bean attributes
        initJSFScope($scope);

        var MessageObject = function () {

            return {
                'type': {type: String},
                'sender': {type: String},
                'receiver': {type: String},
                'theMessage': {type: String},
                'date': {type: Date}
            };
        };

        var UserObject = function () {

            return {
                'type': {type: String},
                'id': {type: String, index: {unique: true, dropDups: true}},
                'username': {type: String, index: {unique: true, dropDups: true}},
                'email': {type: String, index: {unique: true, dropDups: true}},
                'password': String,
                'startChat': String,
                'authorities': [{id: String, isLocked: Boolean, authority: {authority: String, id: {type: Number}}}],
                'index': {type: Number, index: {unique: true, dropDups: true}},
                'online': Boolean,
                'token': String
            };
        };

        var User1 = new UserObject();
        var sender = new UserObject();
        var newConnection = true;
        var user1Name = userLogdIn1;
        var user2Name = false;
        var user1AsJson = user1AsJson1;
        var user2AsJson;
        $scope.setUser1 = function(newValue, oldValue) {if ( newValue != oldValue) {
            this.user1AsJson = newValue;}

            try {
                var json = JSON.parse(this.user1AsJson.data);
            } catch (e) {
                console.log('This doesn\'t look like a valid JSON: ', message.data);
                return;
            }

            sender.data.id = json.data.id;
            sender.data.username = json.data.username;
            sender.data.email = json.data.email;
            sender.data.password = json.data.password;
            sender.data.token = json.data.token;

        };
        $scope.setUser2 = function(newValue, oldValue) {if ( newValue != oldValue) {this.user2AsJson = newValue;}};
        $scope.setUser1Name = function(newValue, oldValue) {if ( newValue != oldValue) {this.user1Name = newValue;}};
        $scope.setUser2Name = function(newValue, oldValue) {if ( newValue != oldValue) {this.user2Name = newValue;}};
        $scope.$watch('chatBean.user1Name', $scope.setUser1Name);
        $scope.$watch('chatBean.user2Name', $scope.setUser2Name);
        $scope.$watch('chatBean.user1AsJson', $scope.setUser1);
        $scope.$watch('chatBean.user2AsJson', $scope.setUser2);


        //DOM Element
        var usernameInputEl = user1Name;
        var usernameListEl = document.querySelector("#userList");
        var articleEl = document.querySelector('article');
        var messageBoardEl = articleEl.querySelector('#message-board');
        var messageInputEl = articleEl.querySelector('#message');
        var sendBtnEl = articleEl.querySelector('#send');
        var chatToLabelEl = articleEl.querySelector('#destination');

// All btn, to chat to all people in the room
        var chatToAllEl = document.querySelector('#all');

// current chat destination
        var destination = 'all';

//Chat room that holds every conversation
        var chatRoom = {
            'all': []
        };

//socket object.
        var socket = new WebSocket('ws://127.0.0.1:1337');
        // if user is running mozilla then use it's built-in WebSocket
        window.WebSocket = window.WebSocket || window.MozWebSocket;

        // if browser doesn't support WebSocket, just show some notification and exit
        //TODO inform the user that ther is no chat
        if (!window.WebSocket) {

            //text: 'Sorry, but your browser doesn support WebSockets.'

            return;
        }

        socket.onerror = function (error) {
            // notification that that there is some problems with the conenction...
            // 'Sorry, but there\'s some problem with your connection or the server is down.';
        };

        socket.onclose = socketOnClose;
        window.onclose = socketOnClose;

        socket.onopen = function (e) {
            usernameInputEl.disabled = true;
            connectBtnEl.disabled = true;
            disconnectBtnEl.disabled = false;
        };


        socket.onmessage = function (message) {

            try {
                var json = JSON.parse(message.data);
            } catch (e) {
                console.log('This doesn\'t look like a valid JSON: ', message.data);
                return;
            }

            var fn;

            if(json.type === 'newUser'){
                var data = json.data.author + '@ ' + json.data.text ;
                newUser()
            }
            else if(json.type === 'removeUser') fn = removeUser;
            else if(json.type === 'message') fn = getMessage;



            fn.apply(null, data);
        };









        function socketOnClose(e) {
            usernameInputEl.disabled = false;
            connectBtnEl.disabled = false;
            disconnectBtnEl.disabled = true;
            usernameInputEl.value = '';
            messageBoardEl.innerHTML = '';
            chatToLabelEl.innerHTML = 'All';
            usernameListEl.innerHTML = '';
        }

        function newUser() {
            //if there is no users, return from the function
            if(arguments.length == 1 && arguments[0] == "") return;
            var usernameList = arguments;

            //Loop through all online users
            //foreach user, create a list with username as it's id and content
            //when the list is clicked, change the chat target to that user
            var documentFragment = document.createDocumentFragment();
            for(var i = 0; i < usernameList.length; i++) {
                var username = usernameList[i];
                var liEl = document.createElement("li");
                liEl.id = username;
                liEl.textContent = username;
                if(username != usernameInputEl.value) {
                    //we can chat to everyone in the chat room
                    //except our self
                    liEl.onclick = chatToFn(username);
                    liEl.classList.add('hoverable');
                }
                documentFragment.appendChild(liEl);
            }
            usernameListEl.appendChild(documentFragment);
        }

        function getMessage(sender, message, destination) {
            //destination
            destination = destination || sender;

            //if the current chat is the same as the incoming message destination
            //then add it to the conversation
            //else notify the user that there is an incoming message
            if(destination == destination) {
                var newChatEl = createNewChat(sender, message);
                messageBoardEl.appendChild(newChatEl);
            } else {
                var toEl = usernameListEl.querySelector('#' + destination);
                addCountMessage(toEl);
            }

            //push the incoming message to the conversation
            if(chatRoom[destination]) chatRoom[destination].push(newChatEl);
            else chatRoom[destination] = [newChatEl];
        }

        function removeUser(removedUsername) {
            //remove the user from the username list
            usernameListEl.querySelector('#' + removedUsername).remove();
        }

        /**
         * Return a conversation element.
         * example:
         * <div>
         *      <span>Andi</span>        <!-- Sender -->
         *      <span>Hello World</span> <!-- Message -->
         * </div>
         *
         * */
        function createNewChat(sender, message) {
            var newChatDivEl = document.createElement('div');
            var senderEl = document.createElement('span');
            var messageEl = document.createElement('span');

            if(sender == usernameInputEl.value) sender = 'me';

            senderEl.textContent = sender;
            messageEl.textContent = message;

            newChatDivEl.appendChild(senderEl);
            newChatDivEl.appendChild(messageEl);
            return newChatDivEl;
        }

        function addCountMessage(toEl) {
            var countEl = toEl.querySelector('.count');
            if(countEl) {
                var count = countEl.textContent;
                count = +count;
                countEl.textContent = count + 1;
            } else {
                var countEl = document.createElement('span');
                countEl.classList.add('count');
                countEl.textContent = '1';
                toEl.appendChild(countEl);
            }
        }

        sendBtnEl.onclick = loginToChat;
        chatToAllEl.onclick = chatToFn('all');

        function sendMessage() {
            var message = messageInputEl.value;
            if(message == '') return;


            var messageObject = new MessageObject();

            messageObject.date = (new Date()).getTime();
            messageObject.sender = usernameInputEl;
            messageObject.reciver = chatToLabelEl;
            messageObject.theMessage = message;
            messageObject.type = "message";

            //TODO send a socket message with the following JSON format new MessageObject()
            //  sender: {type: JSON UserPojo},
            //  receiver:{type: JSON UserPojo},
            //  theMessage:{type: String},
            //  date:Date

            socket.send(JSON.stringify(messageObject) );
            messageInputEl.value = '';


            var sender = usernameInputEl.value;
            //also put our conversation in the message board
            getMessage(sender, message, destination);
            //scroll to the bottom to see the newest message
            messageBoardEl.scrollTop = messageBoardEl.scrollHeight;
        }

        function chatToFn(username) {
            return function(e) {
                //remove the notification of how many new messages
                var countEl = usernameListEl.querySelector('#' + username + '>.count');
                if(countEl) countEl.remove();

                chatToLabelEl.textContent = username;
                destination = username;
                messageBoardEl.innerHTML = '';

                //Show all conversation from the username we are chatting to
                var conversationList = chatRoom[destination];
                if(!conversationList) return;
                var df = document.createDocumentFragment();
                conversationList.forEach(function (conversation) {
                    df.appendChild(conversation);
                });
                messageBoardEl.appendChild(df);
            }

        }

    });



