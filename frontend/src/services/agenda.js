const { api } = require('./auth')

module.exports = {
  getAgenda: async () => {
    return api({
      method: 'get',
      url: '/agenda'
    }).then(resp => {
      return resp
    }).catch(e => {
      console.log(e)
    })
  },
  getAgendaById: async (id) => {
    return api({
      method: 'get',
      url: `/agenda/${id}`
    }).then(resp => {
      return resp
    }).catch(e => {
      console.log(e)
    })
  },
  updateAgenda: async (data) => {
    return api({
      method: 'put',
      url: `/agenda/${data.id}`,
      data: data
    }).then(resp => {
      return resp
    }).catch(e => {
      console.log(e)
    })
  },
  newAgenda: async (data, ownerId) => {
    return api({
      method: 'post',
      url: `/agenda?ownerId=${ownerId}`,
      data: data
    }).then(resp => {
      return resp
    }).catch(e => {
      console.log(e)
    })
  },
  addMember: async (agenda, member) => {
    return api({
      method: 'put',
      url: `/agenda/${agenda}/members/${member}/add`
    }).then(resp => {
      return resp
    }).catch(e => {
      console.log(e)
    })
  }
}
