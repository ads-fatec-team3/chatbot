import Vue from 'vue'
import Vuex from 'vuex'

const { getAccessToken } = require('../services/auth')

Vue.use(Vuex)

export const store = new Vuex.Store({
  state: {
    token: localStorage.getItem('access_token') || null,
    id: localStorage.getItem('user_id') || null,
    role: localStorage.getItem('user_role') || null
  },
  getters: {
    AccessToken (state) {
      return state.token
    }
  },
  mutations: {
    setCredentials (state, user) {
      state.token = user.token
      state.id = user.id
      state.role = user.role
    },
    destroyToken (state) {
      state.token = null
    }
  },
  actions: {
    destroyToken (context) {
      localStorage.removeItem('access_token')
      context.commit('destroyToken')
    },
    async setCredentials (context, { username, password }) {
      console.log(username)
      const resp = await getAccessToken(username, password)
      const { token, id, role } = resp.data
      localStorage.setItem('access_token', token)
      localStorage.setItem('user_id', id)
      localStorage.setItem('user_role', role)
      context.commit('setCredentials', { token, id, role })
      return token
    }
  },
  modules: {
  }
})
