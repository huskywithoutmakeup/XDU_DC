var http = require('http');
var server = http.createServer(function (request, response){
      response.write('Hello from server1');
      response.end();
});
server.listen(2021);
console.log("running at http://127.0.0.1:2021");

