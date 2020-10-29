/**
 * Conectar com stompClient.connect, enviando o username no payload
 * Subscribe nos canais:
 * - /topic/broadcast => mensagens para todos
 * - /topic/active => dados dos usuários ativos
 * - /queue/messages => mensagens para um usuários específico
 * 
 * Sempre que um usuário conectar ou desconectar, atualiza a lista de usuários ativos
 */

var stompClient = null;
var selectedUser = null;
var users = null;
var username = $("#from").val();

function setConnected(connected) {
  $("#from").prop("disabled", connected);
  $("#connect").prop("disabled", connected);
  $("#disconnect").prop("disabled", !connected);

  if (connected) {
    $("#users").show();
    $("#sendmessage").show();
  } else {
    $("#users").hide();
    $("#sendmessage").hide();
    users = [];
  }
}

function connect() {
  username = $("#from").val();

  if (username == null || username === "") {
    alert('Please input a nickname!');
    return;
  }

  var socket = new SockJS('/chat');

  stompClient = Stomp.over(socket);
  stompClient.connect({ username: username },
  function(response) {
    stompClient.subscribe('/topic/broadcast', function(response) {
      showMessage(createTextNode(JSON.parse(response.body)));
    });

    stompClient.subscribe('/topic/active', function(response) {
      updateActiveUsers(JSON.parse(response.body));
    });

    stompClient.subscribe('/user/queue/messages', function(response) {
      showMessage(createTextNode(JSON.parse(response.body)));
    });

    setConnected(true);
  })
}

function disconnect() {
  if (stompClient != null) {
    stompClient.disconnect(function() {
      console.log('disconnected...');
      setConnected(false);
      updateSelectedUser()
    });
  }
}

function sendBroadcast(message) {
  stompClient.send("/app/broadcast", {}, JSON.stringify(message));
}

function send() {
  var text = $("#message").val();

  if (selectedUser == null) {
    alert('Please select a user');
    return;
  }

  var message = {
    'sender': username,
    'text': text,
    'type': 'CHAT',
    'recipient': selectedUser
  }

  stompClient.send("/app/chat", { 'sender' : username }, JSON.stringify(message));

  $("#message").val("");
}

function createTextNode(messageObj) {
  var classAlert = 'alert-info';
  var fromTo = messageObj.sender;
  var addTo = fromTo;

  if (username == messageObj.sender) {
    fromTo = messageObj.recipient;
    addTo = 'to: ' + fromTo;
  }

  if (username != messageObj.sender && messageObj.sender != "server") {
    classAlert = "alert-warning";
  }

  if (messageObj.sender != "server") {
    addTo = '<a href="javascript:void(0)" onclick="setSelectedUser(\'' + fromTo + '\')">' + addTo + '</a>'
  }

  return '<div class="row alert ' + classAlert + '"><div class="col-md-8">' + messageObj.text + '</div>'
      + '<div class="col-md-4 text-right"><small>[ <b>' + addTo + '</b> ' + messageObj.timestamp + ' ]</small>'
      + '</div></div>';
}

function showMessage(message) {
  $("#content").html($("#content").html() + message);
  $("#clear").show();
}

function clearMessages() {
  $("#content").html("");
  $("#clear").hide();
}

function updateSelectedUser() {
  if(users.filter(user => user === selectedUser).length == 0) {
    $("#selectedUser").html('')
    selectedUser = null
  }
}

function setSelectedUser(user) {
  selectedUser = user;
  $("#selectedUser").html(selectedUser);

  if ($("#selectedUser").html() == "") {
    $("#divSelectedUser").hide();
  } else {
    $("#divSelectedUser").show();
  }
}

function updateActiveUsers(activeUsers) {
  var activeUserSpan = $("#active-users-span");
  var activeUserUL = $("#active-users");
  
  activeUserUL.html('');

  users = activeUsers.filter(user => user != username)

  if (users.length == 0) {
    activeUserSpan.html('<p><i>No active users found</i></p>');
  } else {
    activeUserSpan.html('<p class="text-muted">click on user to begin chat</p>');
  }

  users.forEach(function(user) {
    activeUserUL.html(activeUserUL.html() + createUserNode(user));
  })

  updateSelectedUser()
}

function updateUsers(username) {          
  var activeUserSpan = $("#active-users-span");
  var activeUserUL = $("#active-users");

  activeUserUL.html('');

  $.ajax({
    type: 'GET',
    url: '/rest/active-users',
    dataType: 'json',
    success: function(userList) {
      userList = userList.filter(user => user != username)

      if (userList.length == 0) {
        activeUserSpan.html('<p><i>No active users found.</i></p>');
      } else  {
        activeUserSpan.html('<p class="text-muted">click on user to begin chat</p>');
      }

      userList.forEach(function(user) {
        activeUserUL.html(activeUserUL.html() + createUserNode(user));
      })

      updateSelectedUser()
    },
    error: function(XMLHttpRequest, textStatus, errorThrown) {
      alert("error occurred", errorThrown);
    }
  });
}
  
function createUserNode(username) {
  return '<li class="list-group-item">' + '<a class="active-user" href="javascript:void(0)" '
        + 'onclick="setSelectedUser(\'' + username + '\')">' + username + '</a></li>';
}