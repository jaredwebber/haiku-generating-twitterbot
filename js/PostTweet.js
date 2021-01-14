require('dotenv').config();

var Twit = require('twit');
var fs = require('fs');
//Base message if reading file fails
var tweetContent = "error";

const ConsumerKey = process.env.CONSUMER_KEY;
const ConsumerSecret = process.env.CONSUMER_SECRET;
const AccessToken = process.env.ACCESS_TOKEN;
const AccessSecret = process.env.ACCESS_TOKEN_SECRET;

//Read the file written in java processing
fs.readFile('txt/tweet.txt', 'utf8', function(err, data) {
    if (err) throw err;
    tweetContent = data;
});

//Setup Twit library permissions
var T = new Twit({
    consumer_key:ConsumerKey,
    consumer_secret:ConsumerSecret,
    access_token:AccessToken,
    access_token_secret:AccessSecret
});

//Twit authentication
T.get('account/verify_credentials', {
    include_entities: false,
    skip_status: true,
    include_email: false
}, onAuthenticated)

function onAuthenticated(err){
    if (err) {
        console.log(err)
    } else {
    console.log('Authentication successful.')
}}

//Tweet when authenticated
function onAuthenticated(err){
    sendTweet()
}

function sendTweet(){
    //content will be set to "null" if no new tweets found
    if(tweetContent!="null"){
        T.post('statuses/update', { status:tweetContent})
    }
}

