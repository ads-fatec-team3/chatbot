<template>
  <div>
    <v-virtual-scroll
      id="messages"
      :items="messages"
      class="scroll-box"
      item-height="64"
    >
      <template v-slot="{ item, index }">

        <v-list-item
          v-if="item.sender.id == user"
          :key="index"
          class="d-flex flex-row justify-end"
        >
          <strong class="message my-message">{{ item.text }}</strong>
        </v-list-item>

        <v-list-item
          v-else
          :key="index"
          class="d-flex flex-row justify-start">
          <strong class="message your-message">{{ item.text }}</strong>
        </v-list-item>

      </template>
    </v-virtual-scroll>
    <div class="d-flex flex-column justify-start message-input">
      <div>Mensagem para: {{ conversaId }}</div>
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
export default {
  name: 'ChatTab',
  props: {
    messages: Array,
    user: Number,
    conversaId: Number,
    send: Function
  },
  data () {
    return {
      message: null
    }
  },
  methods: {
    sendMessage: function () {
      this.$emit('send', this.message)
      this.message = null
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
