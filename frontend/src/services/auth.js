const axios = require('axios')
const router = require('../router/index.js')

const axiosInstance = axios.create({
  baseURL: `${process.env.VUE_APP_SERVER_URL || 'localhost:8080'}/api`
})

axiosInstance.interceptors.response.use(
  resp => {
    return resp
  },
  error => {
    if (error.response.status === 401) {
      router.default.push({ name: 'login' })
    }
    return Promise.reject(error)
  }
)

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
