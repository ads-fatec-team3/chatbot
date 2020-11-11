const axios = require('axios')

const axiosInstance = axios.create({
  baseURL: 'http://localhost:8080/api'
})

module.exports = {
  api: (data) => {
    const body = data.data
    const method = data.method
    const url = data.url
    const options = {
      method,
      url,
      headers: {
        Authorization: 'Bearer ' + localStorage.getItem('access_token')
      }
    }
    if (method.toLowerCase() !== 'get') {
      options.data = body
    } else {
      options.params = body
    }
    return axiosInstance(options)
  },
  getAccessToken: (username, password) => {
    return axiosInstance({
      method: 'post',
      url: '/auth/signin',
      data: { username, password }
    }).then(resp => {
      console.log('deu bom')
      return resp
    }).catch(e => {
      console.log(e)
      console.log('getError')
      return 'Abacate'
    })
  }
}
