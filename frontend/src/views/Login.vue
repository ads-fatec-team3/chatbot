<template>
  <v-app>
    <v-form>
      <v-container>
        <v-row align="center" justify="center">
          <div>
            <img alt="Vue logo" src="../assets/logo.png" width="200">
            <div class="text-h3">Login</div>
          </div>
          <v-col cols='12' sm='6' md='3'>
            <v-text-field label='Username' v-model='username'></v-text-field>
          </v-col>

          <v-col cols='12' sm='6' md='3'>
            <v-text-field
              label='Password'
              type='password'
              v-model='password'
            ></v-text-field>
          </v-col>
          <v-btn color="primary" x-large class="ma-2 pa-2" @click='submitLogin'>Login</v-btn>
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
      password: null
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
