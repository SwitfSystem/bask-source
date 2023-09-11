const Mock = require('mockjs')

const data = Mock.mock({
  'items|30': [{
    id: '@id',
    userName: `小明`,
    userID:'@natural(1,60)',
    phone:'@natural(1,100000000000000)',
    userType: '普通用户',
    invitationCode:'10086',
    superiorId:'20',
    regTime: '@datetime',
    balance: '100',
    role:1
  }]
})

module.exports = [
  {
    url: '/vue-admin-template/table/list',
    type: 'get',
    response: config => {
      const items = data.items
      return {
        code: 20000,
        data: {
          total: items.length,
          items: items
        }
      }
    }
  }
]
