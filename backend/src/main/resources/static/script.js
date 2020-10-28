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
  }
}

function connect() {
  username = $("#from").val();

  if (username == null || username === "") {
    alert('Please input a nickname!');
    return;
  }

  $.post('/rest/user-connect', { username: username },

    function(remoteAddr, status, xhr) {
      var socket = new SockJS('/chat');

      stompClient = Stomp.over(socket);
      stompClient.connect({
        username: username
      }, function() {
        stompClient.subscribe('/topic/broadcast', function(output) {
          showMessage(createTextNode(JSON.parse(output.body)));
        });

        stompClient.subscribe('/topic/active', function() {
          updateUsers(username);
        });

        stompClient.subscribe('/queue/messages', function(output) {
          showMessage(createTextNode(JSON.parse(output.body)));
        });

        sendConnection('connected to server');
        setConnected(true);

      }, function(err) {
        alert(err);
      });
    }).done(function() {}).fail(function(jqxhr, settings, ex) {
      console.log('failed [ ' + ex + ' ]');
    }
  );
}

function disconnect() {
  if (stompClient != null) {
    $.post('/rest/user-disconnect', { username: username },

      function() {
        sendConnection(' disconnected from server');

        stompClient.disconnect(function() {
          console.log('disconnected...');
          setConnected(false);
        });
      }).done(function(param) {
        console.log('param done [ ' + param + ' ]');
      }).fail(function(jqxhr, settings, ex) {
        console.log('failed [ ' + ex + ' ]');
      }
    );
  }
}

function sendConnection(message) {
  sendBroadcast({
    'sender': 'server',
    'type': 'CHAT',
    'text': username + message
  });

  updateUsers(username);
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

function setSelectedUser(username) {
  $("#selectedUser").html(username);

  if ($("#selectedUser").html() == "") {
    $("#divSelectedUser").hide();
  } else {
    $("#divSelectedUser").show();
  }
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
      if (userList.length == 0) {
        activeUserSpan.html('<p><i>No active users found.</i></p>');
      }

      activeUserSpan.html('<p class="text-muted">click on user to begin chat</p>');

      userList.forEach(function(user) {
        if(user != username)
          activeUserUL.html(activeUserUL.html() + createUserNode(user));
      })
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