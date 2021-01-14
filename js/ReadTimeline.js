require('dotenv').config();
var axios = require('axios');

const Bearer = process.env.BEARER;
const Cookie = process.env.COOKIE;

//Placeholder id: 939091

var config = {
  method: 'get',
  url: 'https://api.twitter.com/2/users/822215679726100480/tweets?max_results=100',
  headers: { 
    'Authorization': Bearer, 
    'Cookie': Cookie
  }
};


//Run Timeline Request
axios(config)
.then(function (response) {
  console.log(JSON.stringify(response.data));
})
.catch(function (error) {
  console.log(error);
});



