import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/register',
    component: () => import('@/views/register/index'),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '首页', icon: 'dashboard' }
    }]
  },
  {
    path: '/user',
    component: Layout,
    name: 'Example',
    alwaysShow: true,
    meta: { title: '用户管理', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'userInfoList',
        name: 'UserInfoList',
        component: () => import('@/views/user/infoList/index.vue'),
        meta: { title: '下级用户' }
      },
      {
        path: 'fakeList',
        name: 'EquipmentFakeList',
        component: () => import('@/views/user/fakeList/index.vue'),
        meta: { title: '挂机明细' }
      }
    ]
  },
  {
    path: '/proxy',
    component: Layout,
    name: 'Example',
    alwaysShow: true,
    meta: { title: '代理管理', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'infoList',
        name: 'ProxyInfoList',
        component: () => import('@/views/proxy/infoList/index.vue'),
        meta: { title: '代理信息' }
      },
      {
        path: 'codeList',
        name: 'ProxyCodeList',
        component: () => import('@/views/proxy/code/index.vue'),
        meta: { title: '激活码列表' }
      }
    ]
  },
  {
    path: '/finance',
    component: Layout,
    name: 'Example',
    alwaysShow: true,
    meta: { title: '财务管理', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'withdrawDetail',
        name: 'FinanceWithdrawDetail',
        component: () => import('@/views/finance/withdraw/detail.vue'),
        meta: { title: '提现', roles: ['editor'] }
      },
      {
        path: 'withdraw',
        name: 'FinanceWithdraw',
        component: () => import('@/views/finance/withdraw/index.vue'),
        meta: { title: '提现列表' }
      },
      {
        path: 'commission',
        name: 'FinanceCommission',
        component: () => import('@/views/finance/commission/index.vue'),
        meta: { title: '佣金明细' }
      }
    ]
  },
  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },
  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
