import Vue from 'vue'
import Vuex from 'vuex'
import auth from '../services/auth'

Vue.use(Vuex)

export const store = new Vuex.Store({
  state: {
    token: localStorage.getItem('access_token') || null
  },
  getters: {
    AccessToken (state) {
      return state.token
    }
  },
  mutations: {
    retrieveToken (state, token) {
      state.token = token
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
    retrieveToken (context, userId) {
      const token = auth.getAccessToken(userId).data
      localStorage.setItem('access_token', token)
      context.commit('retrieveToken', token)
      return token
    }
  },
  modules: {
  }
})
