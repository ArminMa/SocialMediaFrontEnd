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
    addMessage3('testMessage1 for som text');
    addMessage3('more messages comming in');

    var asjgdk = UserService.name;

    // // open connection
    // var connection = new WebSocket('ws://127.0.0.1:1337');
    var newConnection = true;
    var user1Name = userLogdIn1;
    var user2Name = false;

    $scope.setUser1Name = function(newValue, oldValue) {if ( newValue != oldValue) {this.user1Name = newValue;}};
    $scope.setUser2Name = function(newValue, oldValue) {if ( newValue != oldValue) {this.user2Name = newValue;}};
    $scope.$watch('chatBean.user1Name', $scope.setUser1Name);
    $scope.$watch('chatBean.user2Name', $scope.setUser2Name);

    $scope.buttonClict = function() {
        addMessage3('testMessage1 for som text');
        addMessage3('more messages comming in');
        addMessage3(this.message);
    };

    $scope.inputTextValue = function(val) {
        if(val == '\n'){
            addMessage3(this.message);
        }else{
            this.message =  this.message + val;
        }
        addMessage3('test inputTextValue');
    };

    function addMessage2( message) {
        this.text = this.text + '\n' + $scope.inputValue;
        $scope.messageBord = this.text;
    }

    function addMessage3( message) {
        if(this.text == undefined){
            this.text = '@'
        }
        this.text = this.text + '\n' + message;
        $scope.messageBord = text;
        $scope.inputValue = '';
    }

    $scope.inputText = "";
    $scope.myTextarea= "";
    $scope.sendMessage = function () {
        if(this.user1Name == undefined || this.user1Name == 'undefined'){
            this.user1Name = userLogdIn1;
        }
        awesome();
        $scope.myTextarea += this.user1Name + ' @ ' + $scope.inputText + '\n';
        $scope.inputText = '';
    }
});


