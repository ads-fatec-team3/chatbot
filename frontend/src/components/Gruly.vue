<template>
  <div>
    <main id="scroll-messages">
      <div v-for="(message, index) in messages" :key="index" class="d-flex flex-column">

        <div v-if="message.sender === user" class="d-flex justify-end">
          <div class="message my-message">
            <div class="d-flex justify-around">
              <b class="message-header mr-4">Você</b>
            </div>
            <div>{{ message.text }}</div>
          </div>
        </div>

        <div v-else class="d-flex justify-start">
          <div class="message your-message">
            <div class="d-flex justify-around">
              <b class="message-header mr-4">Gruly</b>
            </div>
            <div>{{ message.text }}</div>
          </div>
        </div>
      </div>
    </main>

    <div class="d-flex flex-column justify-start message-input">
      <b>Mensagem para: Gruly</b>
      <div class="d-flex flex-row">
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
  </div>
</template>

<script>
import moment from 'moment'

export default {
  name: 'ChatTab',
  props: {
    messages: Array,
    user: Number,
    conversaName: String,
    sendGruly: Function
  },
  data () {
    return {
      message: null
    }
  },
  methods: {
    sendMessage: function () {
      this.$emit('sendGruly', this.message)
      this.message = null
    },
    scrollToEnd: function () {
      var scrollArea = this.$el.querySelector('#scroll-messages')
      scrollArea.scrollTop = scrollArea.scrollHeight + 20
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
  },
  mounted () {
    this.scrollToEnd()
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
  #scroll-messages {
    height: 70vh;
    overflow-y: auto;
  }

  #scroll-messages::-webkit-scrollbar {
    display: none;
  }

  #scroll-messages {
    -ms-overflow-style: none;
    scrollbar-width: none;
  }

  .message {
    border-radius: 15px;
    padding: 5px;
    margin: 10px;
    max-width: 250px;
    text-align: justify;
    text-justify: inter-word;
    font-size: 18px;
    word-wrap: break-word;
  }

  .my-message {
    background-color: lightgreen;
  }

  .your-message {
    background-color: lightgray;
  }
  #scroll-messages {
    overflow: scroll;
    height: 70vh;
    max-height: 70vh;
  }
  .message-header {
    font-size: 14px;
  }
</style>
