const { api } = require('./auth')

module.exports = {
  getAllMessages: async () => {
    return api({
      method: 'get',
      url: '/messages'
    }).then(resp => {
      return resp
    }).catch(e => {
      console.log(e)
    })
  },
  getMember: async (messageId) => {
    return api({
      method: 'get',
      url: `/messages/${messageId}`
    }).then(resp => {
      return resp
    }).catch(e => {
      console.log(e)
    })
  },
  newMessage: async (senderId, conversationId, text) => {
    return api({
      method: 'post',
      url: '/messages',
      data: { text },
      params: { senderId, conversationId }
    }).then(resp => {
      return resp
    }).catch(e => {
      console.log(e)
    })
  },
  updateMessage: async (text, messageId) => {
    return api({
      method: 'put',
      url: `/messages/${messageId}`,
      data: { text }
    }).then(resp => {
      return resp
    }).catch(e => {
      console.log(e)
    })
  },
  deleteMessage: async (messageId) => {
    return api({
      method: 'delete',
      url: `/messages/${messageId}`
    }).then(resp => {
      return resp
    }).catch(e => {
      console.log(e)
    })
  }
}
