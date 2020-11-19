<template>
  <v-app>
    <div style="height: 100vh;">
      <v-tabs
        v-model="tab"
        background-color="primary"
        centered
        icons-and-text
      >
        <v-tab href="#tab-gruly">
          Gruly
          <v-icon>mdi-robot</v-icon>
        </v-tab>

        <v-tab href="#tab-chat">
          Chat
          <v-icon>mdi-chat</v-icon>
        </v-tab>

        <v-tab href="#tab-conversas">
          Conversas
          <v-icon>mdi-view-list</v-icon>
        </v-tab>

        <v-tab href="#tab-agenda">
          Atividades
          <v-icon>mdi-alarm</v-icon>
        </v-tab>
      </v-tabs>

      <v-tabs-items v-model="tab">
        <v-tab-item value="tab-gruly">
          <div>
            <v-virtual-scroll
              id="messages"
              :items="messagesGruly"
              class="scroll-box"
              item-height="110"
            >
              <template v-slot="{ item, index }">

                <v-list-item
                  v-if="item.owner == user"
                  :key="index"
                  class="d-flex flex-row justify-end"
                >
                  <strong class="message my-message">{{ item.content }}</strong>
                </v-list-item>

                <v-list-item
                  v-else
                  :key="index"
                  class="d-flex flex-row justify-start">
                  <strong class="message your-message">{{ item.content }}</strong>
                </v-list-item>

              </template>
            </v-virtual-scroll>
            <div class="d-flex flex-row message-input">
              <v-textarea
                v-model="messageGruly"
                outlined
                rows="2"
                no-resize
                class="ml-2"
              ></v-textarea>
              <v-btn
                fab
                color="primary"
                class="ma-2"
                @click="sendMessageGruly"
              >
                <v-icon>mdi-send</v-icon>
              </v-btn>
            </div>
          </div>
        </v-tab-item>

        <v-tab-item value="tab-chat">
          <ChatTab v-if="conversaId" :messages="messages" :user="user" :conversaId="conversaId" @send="send"/>
          <div v-else>
            <div class="text-h4 mt-4">Selecione uma conversa!</div>
          </div>
        </v-tab-item>

        <v-tab-item value="tab-conversas">
          <ConversasTab :conversas="conversas" @handleChangeTab="goToChat" />
        </v-tab-item>

        <v-tab-item value="tab-agenda">
          <AgendaTab
            :agenda="agenda"
            :members="members"
            :activeDialogAgenda="activeDialogAgenda"
            @handleActiveDialog="activeDialogAgenda = !activeDialogAgenda"
            @handleCreateAgenda="createAgenda"
          />
        </v-tab-item>

      </v-tabs-items>
    </div>
  </v-app>
</template>

<script>
import axios from 'axios'
import moment from 'moment'

import SockJS from 'sockjs-client'
import Stomp from 'webstomp-client'

import ChatTab from '@/components/ChatTab.vue'
import ConversasTab from '@/components/ConversasTab.vue'
import AgendaTab from '@/components/AgendaTab.vue'

import serviceMember from '@/services/member.js'
import serviceConversation from '@/services/conversation.js'
import serviceAgenda from '@/services/agenda.js'

export default {
  name: 'Chat',
  components: {
    ChatTab,
    ConversasTab,
    AgendaTab
  },
  data () {
    return {
      tab: 'tab-conversas',
      message: null,
      messageGruly: null,
      user: null,
      token: null,
      conversaId: null,
      messages: [],
      messagesGruly: [],
      conversas: [],
      agenda: [],
      connected: false,
      activeDialogAgenda: false,
      members: []
    }
  },
  methods: {
    connect: function () {
      this.socket = new SockJS(`${process.env.VUE_APP_SERVER_URL}/chat`)
      this.stompClient = Stomp.over(this.socket)
      this.stompClient.connect(
        { token: this.token },
        frame => {
          this.connected = true
          this.stompClient.subscribe('/topic/active', response => {
            // this.connected = this.arrayStringToArrayObj(JSON.parse(response.body))
            this.connected = true
          })

          this.stompClient.subscribe('/topic/chat/messages', response => {
            const message = JSON.parse(response.body)
            console.log(message)
            this.messages.push({
              text: message.text,
              sender: {
                id: message.sender
              }
            })
          })
        },
        error => {
          this.connected = false
          console.log(error)
        }
      )
    },
    send: function (content) {
      const message = {
        sender: this.user,
        text: content,
        type: 'CHAT',
        chatId: this.conversaId,
        recipient: this.conversaId
      }
      this.stompClient.send('/app/chat/messages', JSON.stringify(message), { sender: this.user, token: this.token })

      // Para atualizar no front, talvez remover depois
      this.messages.push({
        text: content,
        sender: {
          id: this.user
        }
      })
    },
    selectUser: function () {
      this.user = parseInt(this.$store.state.id)
      this.token = this.$store.state.token
      this.connect()
    },
    arrayStringToArrayObj: function (array) {
      const arrayObj = []
      var self = this
      array.forEach((element, index, originalArray) => {
        if (element !== self.user) {
          arrayObj.push({ name: element })
        }
      })
      return arrayObj
    },
    sendMessageGruly: function () {
      if (this.messageGruly) {
        this.messagesGruly.push({
          content: this.messageGruly,
          owner: this.user
        })

        axios.post('http://127.0.0.1:5000/send-message', {
          message: this.messageGruly
        }).then(response => {
          this.messagesGruly.push({
            content: response.data.result, dateTime: '2020-10-04 12:00:00', owner: 'Gruly'
          })
        })

        this.messageGruly = null
      }
    },
    scrollToEnd: function () {
      var scrollArea = this.$el.querySelector('#messages')
      scrollArea.scrollTop = scrollArea.scrollHeight
    },
    loadConversas: async function () {
      const resp = await serviceConversation.getAllConversations()
      this.conversas = resp.data
    },
    loadAgenda: async function () {
      const resp = await serviceAgenda.getAgenda()
      this.agenda = resp.data
    },
    createAgenda: async function (data) {
      const resp = await serviceAgenda.newAgenda(data, 1)
      if (resp.status === 201) {
        this.loadAgenda()
        this.activeDialogAgenda = false
      }
    },
    loadGruly: function () {
      axios.get('http://127.0.0.1:5000').then(response => {
        this.messagesGruly.push({
          content: response.data.result, dateTime: '2020-10-04 12:00:00', owner: 'Gruly'
        })
      })
    },
    goToChat: async function (conversaId) {
      const resp = await serviceConversation.getConversation(conversaId)
      console.log(resp)
      this.messages = resp.data.messages
      this.tab = 'tab-chat'
      this.conversaId = conversaId
    },
    loadMembers: async function () {
      const resp = await serviceMember.getAllMembers()
      const members = []
      for (let i = 0; i < resp.data.length; i++) {
        members.push({
          text: resp.data[i].username,
          value: resp.data[i].id
        })
      }
      this.members = members
    }
  },

  mounted () {
    if (!this.$store.state.token) {
      this.$router.push({ name: 'login' })
    } else {
      this.selectUser()
      this.loadMembers()
      this.loadConversas()
      this.loadAgenda()
    }
    // this.loadGruly()
  },

  filters: {
    formatDate: function (value) {
      if (value) {
        return moment(String(value)).format('DD/MM/YYYY hh:mm')
      }
    },
    upperCase: function (value) {
      if (value) {
        return value.toUpperCase()
      }
    }
  }
}
</script>

<style>
  .message-input {
    position: fixed;
    bottom: 0px;
    width: 100vw;
    background-color: white;
  }
  .scroll-box {
    height: 70vh;
    overflow-y: auto;
  }

  /* Hide scrollbar for Chrome, Safari and Opera */
  .scroll-box::-webkit-scrollbar {
    display: none;
  }

  /* Hide scrollbar for IE, Edge and Firefox */
  .scroll-box {
    -ms-overflow-style: none;  /* IE and Edge */
    scrollbar-width: none;  /* Firefox */
  }

  .message {
    border-radius: 15px;
    padding: 15px;
    margin: 15px;
    max-width: 300px;
  }

  .my-message {
    background-color: lightgreen;
  }

  .your-message {
    background-color: lightgray;
  }
</style>
