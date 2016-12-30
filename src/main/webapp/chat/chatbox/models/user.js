

// set up a mongoose model
module.exports = model('User',{

	"id": {type: String, index: {unique: true, dropDups: true}},
	"username":{type: String, index: {unique: true, dropDups: true}},
	"email":{type: String, index: {unique: true, dropDups: true}},
	"password":String,
    "startChat":String,
	"authorities":[{"id":String,"isLocked":Boolean,"authority":{"authority":String,"id":{ type: Number } }}],
	"index": {type: Number, index: {unique: true, dropDups: true}},
	"online":Boolean,
	"token": String

});


