<template>
  <v-app style="margin-top: -50px">
    <v-form>
      <v-container>
        <v-row align="center" justify="center">
          <div>
            <img alt="Vue logo" src="../assets/logo.png" width="250">
            <div class="text-h5">Efetuar Login</div>
          </div>
          <v-col cols='10' sm='6' md='3'>
            <v-text-field label='Usuário' v-model='username'></v-text-field>
          </v-col>
          <v-col cols='10' sm='6' md='3'>
            <v-text-field label='Senha' :type="value ? 'password' : 'text'" v-model='password' value="" name="password" :append-icon="value ? 'mdi-eye' : 'mdi-eye-off'" @click:append="() => (value = !value)"></v-text-field>
          </v-col>
          <v-col cols='10' sm='6' md='3'>
            <v-btn color="primary" x-medium block class="ma-2 pa-2" @click="submitLogin">Entrar</v-btn>
          </v-col>
        </v-row>
      </v-container>
    </v-form>
  </v-app>
</template>

<script>
export default {
  name: 'Login',
  data () {
    return {
      username: null,
      password: null,
      value: String
    }
  },
  methods: {
    submitLogin: function () {
      this.$store.dispatch('setCredentials', {
        username: this.username,
        password: this.password
      }).then(x => {
        this.$router.push({ name: 'chat' })
      }).catch(x => {
        console.log(x)
      })
    }
  }
}
</script>
