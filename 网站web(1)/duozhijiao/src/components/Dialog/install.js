/*
 * @Author: fengzg
 * @Date: 2020-05-27 14:48:58
 * @LastEditors: fengzg
 * @LastEditTime: 2020-05-29 17:25:47
 * @FilePath: \vue-admin\src\components\Dialog\install.js
 * @Description: 全局弹框
 * @WebSite: https://www.epower.cn/
 */
import createDialog from './index.js'

/* eslint-disable */
export default {
  install(Vue, options = {}) {
    Vue.prototype.$dialog = createDialog.bind(this, Vue, options)
  },
}
