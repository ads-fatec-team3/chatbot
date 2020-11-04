const axios = require('axios')

const api = axios.create({
  baseURL: 'localhost:8080/api'
})

module.exports = {
  getAccessToken: async (userId) => {
    try {
      const resp = await api.post('/auth', {
        params: { userId }
      })
      return resp
    } catch (err) {
      console.log(err)
    }
  }
}
