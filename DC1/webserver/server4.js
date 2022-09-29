var http = require('http');
var server = http.createServer(function (request, response){
      response.write('Hello from server4');
      response.end();
});
server.listen(2024);
console.log("running at http://127.0.0.1:2024");

