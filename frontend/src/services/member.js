const { api } = require('./auth')

module.exports = {
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
  getMemberData: (id) => {
    return api({
      method: 'get',
      url: `/members/${id}`
    }).then(resp => {
      return resp
    }).catch(e => {
      console.log(e)
    })
  },
  getMember: async (memberId) => {
    return api({
      method: 'get',
      url: `/members/${memberId}`
    }).then(resp => {
      return resp
    }).catch(e => {
      console.log(e)
    })
  },
  newMember: async (name) => {
    return api({
      method: 'post',
      url: '/members',
      data: { name }
    }).then(resp => {
      return resp
    }).catch(e => {
      console.log(e)
    })
  },
  updateMember: async (name, memberId) => {
    return api({
      method: 'put',
      url: `/members/${memberId}`,
      data: { name }
    }).then(resp => {
      return resp
    }).catch(e => {
      console.log(e)
    })
  },
  deleteMember: async (memberId) => {
    return api({
      method: 'delete',
      url: `/members/${memberId}`
    }).then(resp => {
      return resp
    }).catch(e => {
      console.log(e)
    })
  }
}
