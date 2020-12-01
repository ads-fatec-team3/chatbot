<template>
  <v-app>
    <div style="height: 100vh;">
      <v-tabs
        v-model="tab"
        background-color="primary"
        centered
        icons-and-text
      >
        <v-tab  v-if="hasPermission()" href="#tab-gruly">
          Gruly
          <v-icon>mdi-robot</v-icon>
        </v-tab>

        <v-tab v-if="tab=='tab-chat'" href="#tab-chat">
          Conversas
          <v-icon>mdi-chat</v-icon>
        </v-tab>

        <v-tab v-if="tab!='tab-chat'" href="#tab-conversas">
          Conversas
          <v-icon>mdi-view-list</v-icon>
        </v-tab>

        <v-tab href="#tab-agenda">
          Atividades
          <v-icon>mdi-alarm</v-icon>
        </v-tab>
      </v-tabs>

      <v-tabs-items v-model="tab">
        <v-tab-item v-if="hasPermission()" value="tab-gruly">
          <Gruly
            :messages="messagesGruly"
            :user="user"
            :conversaName="conversaName"
            @sendGruly="sendMessageGruly"
          />
        </v-tab-item>

        <v-tab-item value="tab-chat">
          <ChatTab v-if="conversaId"
          :messages="messages"
          :user="user"
          :conversaName="conversaName" @send="send"
          @handleChangeTab="goToConversations"/>
          <div v-else>
            <div class="text-h4 mt-4">Selecione uma conversa!</div>
          </div>
        </v-tab-item>

        <v-tab-item value="tab-conversas">
          <ConversasTab
            :conversas="conversas"
            @handleChangeTab="goToChat"
            :members="members"
            @SearchConversa="SearchConversa"
            :activeDialogConversas="activeDialogConversas"
            @handleSearchConversa="SearchConversa"
            @handleActiveDialog="activeDialogConversas = !activeDialogConversas"
            @handleCreateConversa="createConversa"/>
        </v-tab-item>

        <v-tab-item value="tab-agenda">
          <AgendaTab
            :agendas="agendas"
            :members="members"
            :activeDialogAgenda="activeDialogAgenda"
            @handleSearchAgenda="SearchAgenda"
            @handleActiveDialog="activeDialogAgenda = !activeDialogAgenda"
            @handleCreateAgenda="createAgenda"
            @handleUpdateAgenda="updateAgenda"
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
import Gruly from '@/components/Gruly.vue'

import serviceConversation from '@/services/conversation.js'
import serviceMember from '@/services/member.js'
import serviceAgenda from '@/services/agenda.js'

export default {
  name: 'Chat',
  components: {
    ChatTab,
    ConversasTab,
    AgendaTab,
    Gruly
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
      agendas: [],
      connected: false,
      activeDialogAgenda: false,
      activeDialogConversas: false,
      members: []
    }
  },
  methods: {
    updateAgenda: function () {
      this.loadAgendas()
    },
    goToConversations: function () {
      this.tab = 'tab-conversas'
    },
    hasPermission: function () {
      if (this.$store.state.role === 'ROLE_STUDENT' || this.$store.state.role === 'ROLE_INSTRUCTOR') {
        return true
      }
      return false
    },
    connect: function () {
      this.socket = new SockJS(`${process.env.VUE_APP_SERVER_URL}/chat`)
      this.stompClient = Stomp.over(this.socket)
      this.stompClient.connect(
        { token: this.token },
        frame => {
          this.connected = true
          this.stompClient.subscribe('/topic/active', response => {
            this.connected = true
          })

          this.stompClient.subscribe('/topic/chat/messages', async response => {
            const message = JSON.parse(response.body)
            const sender = await serviceMember.getMember(message.sender)
            this.messages.push({
              text: message.text,
              sender: sender.data,
              timestamp: message.timestamp
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
    sendMessageGruly: function (message) {
      if (message) {
        this.messagesGruly.push({
          text: message,
          sender: this.user
        })

        axios.post('http://127.0.0.1:5000/send-message', {
          message: message
        }).then(response => {
          this.messagesGruly.push({
            text: response.data.result,
            sender: 'Gruly'
          })
        })
      }
    },
    scrollToEnd: function () {
      var scrollArea = this.$el.querySelector('#scroll-messages')
      scrollArea.scrollTop = scrollArea.scrollHeight
    },
    loadConversas: async function () {
      const resp = await serviceMember.getMemberData(this.$store.state.id)
      this.conversas = resp.data.conversations
    },
    loadAgendas: async function () {
      const resp = await serviceMember.getMemberData(this.$store.state.id)
      this.agendas = resp.data.agendas
    },
    createAgenda: async function (data, members) {
      const resp = await serviceAgenda.newAgenda(data, this.$store.state.id)
      if (resp.status === 200) {
        for (const member of members) {
          await serviceAgenda.addMember(resp.data.id, member)
        }
        this.loadAgendas()
        this.activeDialogAgenda = false
      }
    },
    createConversa: async function (data) {
      const resp = await serviceConversation.newConversation(data.title)
      if (resp.status === 200) {
        for (const memberId of data.selectedMembers) {
          await serviceConversation.insertConversationMember(memberId, resp.data.id)
        }
        this.loadConversas()
        this.activeDialogConversas = false
      }
    },
    SearchConversa: async function (search) {
      await this.loadConversas()
      this.conversas = this.conversas.filter((conversa) => {
        return conversa.title.toUpperCase().includes(search.toUpperCase())
      })
    },
    SearchAgenda: async function (search) {
      await this.loadAgendas()
      this.agendas = this.agendas.filter((agenda) => {
        return agenda.title.toUpperCase().includes(search.toUpperCase())
      })
    },
    loadGruly: function () {
      if (this.hasPermission()) {
        axios.get('http://127.0.0.1:5000').then(response => {
          this.messagesGruly.push({
            text: response.data.result,
            sender: 'Gruly'
          })
        })
      }
    },
    goToChat: async function (conversaId) {
      console.log(conversaId)
      const resp = await serviceConversation.getConversation(conversaId)
      console.log(resp)
      this.messages = resp.data.messages
      this.tab = 'tab-chat'
      console.log(resp.data)
      this.conversaName = resp.data.title
      this.conversaId = resp.data.id
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
      this.loadAgendas()
      this.loadGruly()
    }
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
