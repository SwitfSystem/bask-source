import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import '@/styles/index.scss' // global css

import App from './App'
import store from './store'
import router from './router'

import '@/icons' // icon
import '@/permission' // permission control
import Dialog from './components/Dialog/install'
import Axios from 'axios'
Vue.use(Dialog, { store, router }) // 如果没有路由和Vuex，就不传
/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online ! ! !
 */
const { mockXHR } = require('../mock')
mockXHR()

// set ElementUI lang to EN
Vue.use(ElementUI, {
  size: 'small' // set element-ui default size
})
// 如果想要中文版 element-ui，按如下方式声明
// Vue.use(ElementUI)

Vue.config.productionTip = false
Vue.prototype.$axios = Axios
Axios.defaults.baseURL = 'https://api.zgxunge.com:8080'
new Vue({
  el: '#app',
  router,
  store,
  Axios,
  render: h => h(App)
})
