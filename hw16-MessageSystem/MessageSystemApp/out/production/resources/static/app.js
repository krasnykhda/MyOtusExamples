let stompClient = null;

const connect = () => {
    stompClient = Stomp.over(new SockJS('/websocket'));
    stompClient.connect({}, (frame) => {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/response', (message) => {
            $("#clients").html("");
            let clients = JSON.parse(message.body);
            for (var i = 0; i < clients.length; i++) {
                showClient(clients[i]);
            }
        });
        clientsList();
    });
}
const clientsList = () => stompClient.send("/app/clients", {}, {})
function getTime() {
      const stompClient = Stomp.over(new SockJS('/gs-guide-websocket'));
      stompClient.connect({}, (frame) => {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/currentTime', (timeMsg) => document.getElementById("currentTime").innerHTML = timeMsg.body);
      });
    }
const createClient = () => stompClient.send("/app/createClient", {}, JSON.stringify({
    'name': $("#clientName").val(),
    'adress':{'street':$("#clientStreet").val(),'clientId':""},
    'phones':[{'clientId':"",'number':$("#clientPhoneNumber").val()}]

}))

const showClient = (client) => {
$("#clients").append("<tr><td>" + client.name + "</td>"
+"<td>" + client.adress.street + "</td>"+
"<td>" + client.phones[0].number + "</td>"+"</tr>");


}
window.onload = function () {
   connect();
};

$(function () {
    $("form").on('submit', (event) => {
        event.preventDefault();
    });
    $("#create").click(createClient);

});
