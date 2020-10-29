const axios = require('axios')

const api = axios.create({
  baseURL: 'localhost:8080/api'
})
module.exports = {
  getAllMembers: async () => {
    try {
      const resp = await api.get('/members')
      return resp
    } catch (err) {
      console.log(err)
    }
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
