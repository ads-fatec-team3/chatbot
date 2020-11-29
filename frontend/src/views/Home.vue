<template>
  <v-app>
    <div class="home">
      <img alt="Vue logo" src="../assets/logo.png">
      <Hello msg="Melhor Chatbot da FATEC"/>
      <div>
        <iframe v-if="active" class="chat" width="450" height="80%" src="/chat" scrolling="no"></iframe>
        <v-btn
          v-else
          color="primary"
          dark
          absolute
          bottom
          left
          width="450"
          height="50"
          @click="active = !active"
        >
          <div class="text-h5">
            Fale com a autoescola
            <v-icon large>mdi-message-text</v-icon>
          </div>
        </v-btn>
      </div>
    </div>
  </v-app>
</template>

<script>
import Hello from '@/components/Hello.vue'

export default {
  name: 'Home',
  components: {
    Hello
  },
  data () {
    return {
      active: false
    }
  }
}
</script>

<style>
  .chat {
    position: fixed;
    left: 20px;
    bottom: 20px;
    -webkit-box-shadow: -5px -4px 44px 0px rgba(0,0,0,0.75);
    -moz-box-shadow: -5px -4px 44px 0px rgba(0,0,0,0.75);
    box-shadow: -5px -4px 44px 0px rgba(0,0,0,0.75);
    border-radius: 15px;
  }
</style>
