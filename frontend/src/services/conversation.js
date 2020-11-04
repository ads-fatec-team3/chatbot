const axios = require('axios')

const api = axios.create({
  baseURL: 'localhost:8080/api'
})
module.exports = {
  setToken: (token) => {
    api.defaults.headers.common.Authorization = 'Bearer ' + token
  },
  getAllConversations: async () => {
    try {
      const resp = await api.get('/conversations')
      return resp
    } catch (err) {
      console.log(err)
    }
  },
  getConversation: async (conversationId) => {
    try {
      const resp = await api.get(`/conversations/${conversationId}`)
      return resp
    } catch (err) {
      console.log(err)
    }
  },
  newConversation: async (title) => {
    try {
      const resp = await api.post('/conversations', {
        title
      })
      return resp
    } catch (err) {
      console.log(err)
    }
  },
  updateConversation: async (title, conversationId) => {
    try {
      const resp = await api.put(`/conversations/${conversationId}`, {
        title
      })
      return resp
    } catch (err) {
      console.log(err)
    }
  },
  deleteConversation: async (conversationId) => {
    try {
      const resp = await api.delete(`/conversations/${conversationId}`)
      return resp
    } catch (err) {
      console.log(err)
    }
  },
  insertConversationMember: async (memberId, conversationId) => {
    try {
      const resp = await api.put(`/conversations/${conversationId}/members/${memberId}/add`)
      return resp
    } catch (err) {
      console.log(err)
    }
  },
  deleteConversationMember: async (memberId, conversationId) => {
    try {
      const resp = await api.delete(`/conversations/${conversationId}/members/${memberId}/add`)
      return resp
    } catch (err) {
      console.log(err)
    }
  }
}
