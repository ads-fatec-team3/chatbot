const axios = require('axios')

const api = axios.create({
  baseURL: 'localhost:8080/api'
})
module.exports = {
  getAllMessages: async () => {
    try {
      const resp = await api.get('/messages')
      return resp
    } catch (err) {
      console.log(err)
    }
  },
  getMember: async (messageId) => {
    try {
      const resp = await api.get(`/messages/${messageId}`)
      return resp
    } catch (err) {
      console.log(err)
    }
  },
  newMessage: async (senderId, conversationId, text) => {
    try {
      const resp = await api.post('/messages', {
        params: { senderId, conversationId },
        text
      })
      return resp
    } catch (err) {
      console.log(err)
    }
  },
  updateMessage: async (text, messageId) => {
    try {
      const resp = await api.put(`/messages/${messageId}`, {
        text
      })
      return resp
    } catch (err) {
      console.log(err)
    }
  },
  deleteMessage: async (messageId) => {
    try {
      const resp = await api.delete(`/messages/${messageId}`)
      return resp
    } catch (err) {
      console.log(err)
    }
  }
}
