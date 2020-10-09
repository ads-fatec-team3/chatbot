<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png">
    <Hello msg="Melhor Chatbot da FATEC"/>
    <iframe class="chat" width="450" height="550" src="http://localhost:8081/chat" scrolling="no"></iframe>
  </div>
</template>

<script>
// @ is an alias to /src
import Hello from '@/components/Hello.vue'

export default {
  name: 'Home',
  components: {
    Hello
  }
}
</script>

<style>
  .chat {
    position: fixed;
    left: 20px;
    bottom: 20px;
  }
</style>
