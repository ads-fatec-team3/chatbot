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
          Agenda
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
          <div>
            <v-virtual-scroll
              id="messages"
              :items="messages"
              class="scroll-box"
              item-height="64"
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
                v-model="message"
                outlined
                rows="2"
                no-resize
                class="ml-2"
              ></v-textarea>
              <v-btn
                fab
                color="primary"
                class="ma-2"
                @click="sendMessage"
              >
                <v-icon>mdi-send</v-icon>
              </v-btn>
            </div>
          </div>
        </v-tab-item>

        <v-tab-item value="tab-conversas">
          <div>
            <div class="d-flex flex-row ma-2">
              <v-textarea
                v-model="searchMember"
                outlined
                rows="1"
                no-resize
                class="ml-2"
                placeholder="Quem você está procurando?"
              ></v-textarea>
              <v-btn
                fab
                color="primary"
                class="ml-2"
              >
                <v-icon>mdi-magnify</v-icon>
              </v-btn>
            </div>
            <v-virtual-scroll
              :items="conversas"
              item-height="64"
              class="scroll-box"
            >
              <template v-slot="{ item, index }">

                <v-list-item :key="index" @click="tab = 'tab-message'">
                  <v-list-item-action>
                    <v-btn
                      fab
                      small
                      depressed
                      color="blue darken-4"
                    >
                      <strong class="white--text">{{ item.id }}</strong>
                    </v-btn>
                  </v-list-item-action>

                  <v-list-item-content>
                    <v-list-item-title>
                      <strong>{{ arrayToString(item.members) }}</strong>
                    </v-list-item-title>
                  </v-list-item-content>

                </v-list-item>
                <v-divider></v-divider>

              </template>
            </v-virtual-scroll>
          </div>
        </v-tab-item>

        <v-tab-item value="tab-agenda">
          <div>
            <v-virtual-scroll
              :items="agenda"
              item-height="60"
              class="scroll-box"
            >
              <template v-slot="{ item, index }">

                <v-list-item :key="index">

                  <v-list-item-content>
                    <v-list-item-title class="d-flex flex-column">
                      <strong class="mb-1">{{ item.title|upperCase }}</strong>
                      <strong>{{ item.begins_at|formatDate }}</strong>
                    </v-list-item-title>
                  </v-list-item-content>

                </v-list-item>
                <v-divider></v-divider>

              </template>
            </v-virtual-scroll>
          </div>
        </v-tab-item>

      </v-tabs-items>
    </div>
  </v-app>
</template>

<script>
import axios from 'axios'
import moment from 'moment'

import Vue from 'vue'
import { mapGetters } from 'vuex'
export default Vue.extend({
  computed: {
    ...mapGetters({
      chatList: 'chat/chatList'
    })
  },
  name: 'Chat',
  data () {
    return {
      tab: null,
      message: null,
      messageGruly: null,
      searchMember: null,
      user: 1,
      otherUser: 2,
      messages: [
        { content: 'olá', dateTime: '2020-10-04 12:00:00', owner: 2 }
      ],
      messagesGruly: [],
      conversas: [
        { id: 1, members: ['João', 'Maria'], lastMessage: { content: 'olá', dateTime: '2020-10-04 12:00:00', owner: 2 } },
        { id: 2, members: ['José', 'Pedro'], lastMessage: { content: 'olá', dateTime: '2020-10-04 12:00:00', owner: 2 } },
        { id: 3, members: ['Carlos'], lastMessage: { content: 'olá', dateTime: '2020-10-04 12:00:00', owner: 2 } }
      ],
      agenda: []
    }
  },
  methods: {
    sendMessage: function () {
      if (this.message) {
        this.messages.push({
          content: this.message,
          owner: this.user
        })

        this.messages.push({
          content: this.message,
          owner: this.otherUser
        })

        this.message = null
        this.scrollToEnd()
      }
      console.log(this.chatList())
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
    arrayToString: function (array) {
      return array.join(', ')
    },
    loadAgenda: function () {
      axios.get('https://ads-fatec-team3.free.beeceptor.com/schedule').then(response => {
        this.agenda = response.data.schedule
        console.log(this.agenda)
      }).catch(response => {
        console.log('Deu ruim')
      })
    },
    loadGruly: function () {
      axios.get('http://127.0.0.1:5000').then(response => {
        this.messagesGruly.push({
          content: response.data.result, dateTime: '2020-10-04 12:00:00', owner: 'Gruly'
        })
      })
    }
  },

  mounted () {
    this.loadAgenda()
    this.loadGruly()
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
})
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
