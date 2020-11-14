<template>
  <v-form>
    <v-container>
      <v-row>
        <div class='text-h1'>Login</div>
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
        <v-btn primary large block @click='submitLogin'>Login</v-btn>
      </v-row>
    </v-container>
  </v-form>
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
      this.$store.dispatch('retrieveToken', {
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
