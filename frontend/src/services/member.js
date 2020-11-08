const { api } = require('./auth')

module.exports = {
  setToken: (token) => {
    api.defaults.headers.common.Authorization = 'Bearer ' + token
  },
  getAllMembers: () => {
    return api({
      method: 'get',
      url: '/members'
    }).then(resp => {
      return resp
    }).catch(e => {
      console.log(e)
    })
  },
  getMember: async (memberId) => {
    try {
      const resp = await api.get(`/members/${memberId}`)
      return resp
    } catch (err) {
      console.log(err)
    }
  },
  newMember: async (name) => {
    try {
      const resp = await api.post('/members', {
        name
      })
      return resp
    } catch (err) {
      console.log(err)
    }
  },
  updateMember: async (name, memberId) => {
    try {
      const resp = await api.put(`/members/${memberId}`, {
        name
      })
      return resp
    } catch (err) {
      console.log(err)
    }
  },
  deleteMember: async (memberId) => {
    try {
      const resp = await api.delete(`/members/${memberId}`)
      return resp
    } catch (err) {
      console.log(err)
    }
  }
}
