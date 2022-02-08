const http = require('http');
var callr = require('callr');
var api = new callr.api(callr.apiKeyAuth('00255e258e223e4045cb69c9f3e1dc26e5d7f6802bd27e04e90122887f7076a84137b42f42f1ccb7a503bb0915ff0c99cfc7'));
var options = {
    cdr_field: 'userData',
    cli: 'BLOCKED',
    
};

var minutes = 5, the_interval = minutes * 60 * 1000;
setInterval(function() {
  http.get('http://backendme.com/Harsh/get_all_data.php', (resp) => {
  let data = '';

  resp.on('data', (chunk) => {
    data += chunk;
  });

  resp.on('end', () => {
    if(data!="failed"){
    var jsonObject = JSON.parse(data);
    for (var i=0; i<jsonObject.length; i++){
      var target = {
          number: jsonObject[i]['conntact_number'],
          timeout: 30
        };
       var messages = [131, 132, 'TTS|TTS_EN-GB_SERENA|Hello' + jsonObject[i]['first_name'] + jsonObject[i]['message'] + jsonObject[i]['event_date'] + 'Press 1 to confirm and 2 to deny. Thanks.'];
       api.call('calls.broadcast_1', target, messages, options).success(function(result) {});
       console.log(jsonObject[i]['conntact_number']);
    }
  }else{
        console.log("No numbers in queue")

  }
  });

}).on("error", (err) => {
  console.log("Error: " + err.message);
});
}, the_interval);

