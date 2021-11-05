let sock;
let client;
let sessionId;

const username = "admin"
const password = "password"

// client.connect({}, function (frame) {
//     var url = client.ws._transport.url;
//     url = url.replace(
//         "ws://localhost:8080/stomp/",  "");
//     url = url.replace("/websocket", "");
//     url = url.replace(/^[0-9]+\//, "");
//     console.log("Your current session is: " + url);
//     sessionId = url;
//
//     client.subscribe(`/user/${sessionId}/queue/messages`, function (msgOut) {
//         console.log("!!!!!!")
//         //handle messages
//         let message_list = document.getElementById('message-list');
//         let message = document.createElement('li');
//
//         message.appendChild(document.createTextNode(JSON.parse(msgOut.body).message));
//         message_list.appendChild(message);
//
//     })
//
// })

function sendMessageAll() {

    let input = document.getElementById("message-input");
    let message = input.value;

    client.send('/app/sendAll', {}, JSON.stringify({message: message}));
}

function sendMessageMe() {

    let input = document.getElementById("message-input");
    let message = input.value;

    client.send('/app/sendMe', {}, JSON.stringify({message: message}));
}

function disconnect() {
    if (client !== null) {
        client.disconnect(function() {
            console.log("Client disconnected");
        });
        client = null;
    }
}

function connect() {
    sock = new SockJS("http://localhost:8080/stomp");
    client = Stomp.over(sock);

    client.connect({'username': username, 'password': password}, (frame) => {

        client.subscribe("/topic/messages", payload => {

            let message_list = document.getElementById('message-list');
            let message = document.createElement('li');

            message.appendChild(document.createTextNode(JSON.parse(payload.body).message));
            message_list.appendChild(message);
        });

        client.subscribe(`/user/${username}/queue/messages`, function (msgOut) {

            let message_list = document.getElementById('message-list');
            let message = document.createElement('li');

            message.appendChild(document.createTextNode(JSON.parse(msgOut.body).message));
            message_list.appendChild(message);

        })

    });
}