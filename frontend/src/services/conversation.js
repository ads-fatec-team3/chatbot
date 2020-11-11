const { api } = require('./auth')

module.exports = {
  getAllConversations: async () => {
    return api({
      method: 'get',
      url: '/conversations'
    }).then(resp => {
      return resp
    }).catch(e => {
      console.log(e)
    })
  },
  getConversation: async (conversationId) => {
    return api({
      method: 'get',
      url: `/conversations/${conversationId}`
    }).then(resp => {
      return resp
    }).catch(e => {
      console.log(e)
    })
  },
  newConversation: async (title) => {
    return api({
      method: 'post',
      url: '/conversations',
      data: { title }
    }).then(resp => {
      return resp
    }).catch(e => {
      console.log(e)
    })
  },
  updateConversation: async (title, conversationId) => {
    return api({
      method: 'put',
      url: `/conversations/${conversationId}`,
      data: { title }
    }).then(resp => {
      return resp
    }).catch(e => {
      console.log(e)
    })
  },
  deleteConversation: async (conversationId) => {
    return api({
      method: 'delete',
      url: `/conversations/${conversationId}`
    }).then(resp => {
      return resp
    }).catch(e => {
      console.log(e)
    })
  },
  insertConversationMember: async (memberId, conversationId) => {
    return api({
      method: 'put',
      url: `/conversations/${conversationId}/members/${memberId}/add`
    }).then(resp => {
      return resp
    }).catch(e => {
      console.log(e)
    })
  },
  deleteConversationMember: async (memberId, conversationId) => {
    return api({
      method: 'delete',
      url: `/conversations/${conversationId}/members/${memberId}/add`
    }).then(resp => {
      return resp
    }).catch(e => {
      console.log(e)
    })
  }
}
