var stompClient = null;
let id = document.getElementById("id").value;
console.log(id);
var chatRoomId = 1;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/ws/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        //sendto 경로와 일치
        stompClient.subscribe('/topic/chatroom/'+chatRoomId, function (chatMesgDTO) {
            console.log( chatMesgDTO);
            showGreeting(JSON.parse(chatMesgDTO.body));
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");

    window.location.href = '/';
}

function send() {
    // @MessageMapping() 경로임
    stompClient.send("/app/mesg/"+ chatRoomId, {}, JSON.stringify({
        'mesgContent': $("#mesgContent").val(),
        'id': id,
        'chatRoomId': chatRoomId
    }));
}


function showGreeting(chatMesgDTO) {
    var messageContainer = document.getElementById("message-container");
    var messageElement = document.createElement("div");

    messageElement.textContent = "[" + chatMesgDTO.id + "] " + chatMesgDTO.mesgContent;

    if(chatMesgDTO.id==id){
        messageElement.className = "flex mb-2 w-full";
        messageElement.innerHTML = `
        <div class="flex mb-2 min-w-[97%] relative" style="justify-content: flex-end;">
            <div class="bg-[#4F886D] p-2 rounded-lg max-w-[80%] relative" style="text-align: right;">
                <p class="text-gray-100 mb-1" style="text-align: right;">${id}:</p>
                <p class="text-white-50 font-semibold mb-1">${chatMesgDTO.mesgContent }</p>
            </div>
        </div>`;
    } else {
        messageElement.className = "flex mb-2 w-full";
        messageElement.innerHTML = `
        <div class="flex mb-2 w-full relative">
            <div class="bg-[#F6F6F6] p-2 rounded-lg ml-7 max-w-[80%] relative" style="text-align: left;">
                <p class="text-gray-100 mb-1">${id}:</p>
                <p class="text-white-50 font-semibold mb-1">${chatMesgDTO.mesgContent }</p>
            </div>
        </div>`;
    }

    messageContainer.appendChild(messageElement);

}

$(document).ready(function () {
    connect();
    $("#disconnect").click(function() { disconnect(); });
    $("#send-button").click(function() { send(); });
});