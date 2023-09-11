import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/* Router Modules */
// import componentsRouter from './modules/components'
// import chartsRouter from './modules/charts'
// import tableRouter from './modules/table'
// import nestedRouter from './modules/nested'

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
    noCache: true                if set true, the page will no be cached(default is false)
    affix: true                  if set true, the tag will affix in the tags-view
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
    path: '/',
    component: () => import('@/views/dashboard/index'),
    hidden: true
  },
  {
    path: '/register',
    component: () => import('@/views/register/index'),
    hidden: true
  },
  {
    path: '/adminLogin',
    component: () => import('@/views/adminLogin/index'),
    hidden: true
  },
  {
    path: '/auth-redirect',
    component: () => import('@/views/login/auth-redirect'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/error-page/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/error-page/401'),
    hidden: true
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
        meta: { title: '用户信息' }
      },
      {
        path: 'lockInfo',
        name: 'LockInfo',
        component: () => import('@/views/user/lock/index.vue'),
        meta: { title: '用户锁定' }
      }
    ]
  },
  {
    path: '/proxy',
    component: Layout,
    name: 'Example',
    alwaysShow: true,
    meta: { title: '代理管理', icon: 'el-icon-s-help', roles: ['admin', 'editor'] },
    redirect: '/proxy/infoList',
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
      },
      {
        path: 'active',
        name: 'ActiveList',
        component: () => import('@/views/proxy/active/index.vue'),
        meta: { title: '激活设备' }
      }
    ]
  },
  {
    path: '/finance',
    component: Layout,
    redirect: '/finance/withdraw',
    name: 'Example',
    alwaysShow: true,
    meta: { title: '财务管理', icon: 'el-icon-s-help', roles: ['admin', 'editor'] },
    children: [
      // {
      //   path: 'withdrawDetail',
      //   name: 'FinanceWithdrawDetail',
      //   component: () => import('@/views/finance/withdraw/detail.vue'),
      //   meta: { title: '提现', roles: ['editor'] }
      // },
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
        meta: { title: '佣金明细', roles: ['admin', 'editor'] }
      }
    ]
  },
  {
    path: '/equipment',
    component: Layout,
    name: 'Example',
    alwaysShow: true,
    redirect: '/equipment/infoList',
    meta: { title: '设备管理', icon: 'el-icon-s-help', roles: ['admin'] },
    children: [
      {
        path: 'infoList',
        name: 'EquipmentInfoList',
        component: () => import('@/views/equipment/infoList/index.vue'),
        meta: { title: '设备信息' }
      },
      {
        path: 'fakeList',
        name: 'EquipmentFakeList',
        component: () => import('@/views/equipment/fakeList/index.vue'),
        meta: { title: '挂机明细' }
      },
      {
        path: 'update',
        name: 'EquipmentUpdate',
        component: () => import('@/views/equipment/update/index.vue'),
        meta: { title: '更新' }
      },
      {
        path: 'limitIp',
        name: 'EquipmentLimitIp',
        component: () => import('@/views/equipment/limitIp/index.vue'),
        meta: { title: '限制ip' }
      },
      {
        path: 'notice',
        name: 'EquipmentNotice',
        component: () => import('@/views/equipment/notice/index.vue'),
        meta: { title: '公告列表' }
      }
    ]
  },
  {
    path: '/task',
    component: Layout,
    redirect: '/task/infoList',
    name: 'Example',
    alwaysShow: true,
    meta: { title: '任务管理', icon: 'el-icon-s-help', roles: ['admin'] },
    children: [
      {
        path: 'infoList',
        name: 'TaskInfoList',
        component: () => import('@/views/task/infoList/index.vue'),
        meta: { title: '营销任务' }
      },
      {
        path: 'notice',
        name: 'TaskList',
        component: () => import('@/views/task/list/index.vue'),
        meta: { title: '任务列表' }
      },
      {
        path: 'studio',
        name: 'Taskstudio',
        component: () => import('@/views/task/studio/index.vue'),
        meta: { title: '直播间营销' }
      }
    ]
  },
  {
    path: '/material',
    component: Layout,
    name: 'Example',
    redirect: '/material/infoList',
    alwaysShow: true,
    meta: { title: '素材管理', icon: 'el-icon-s-help', roles: ['admin'] },
    children: [
      {
        path: 'infoList',
        name: 'MaterialInfoList',
        component: () => import('@/views/material/infoList/index.vue'),
        meta: { title: '素材列表' }
      }
    ]
  },
  {
    path: '/roles',
    component: Layout,
    name: 'Example',
    alwaysShow: true,
    redirect: '/roles/infoList',
    meta: { title: '角色组管理', icon: 'el-icon-s-help', roles: ['admin'] },
    children: [
      {
        path: 'infoList',
        name: 'MaterialInfoList',
        component: () => import('@/views/roles/infoList/index.vue'),
        meta: { title: '角色组列表' }
      }
    ]
  }
  // {
  //   path: '/logs',
  //   component: Layout,
  //   name: 'Example',
  //   alwaysShow: true,
  //   redirect: '/logs/infoList',
  //   meta: { title: '日志管理', icon: 'el-icon-s-help', roles: ['admin'] },
  //   children: [
  //     {
  //       path: 'infoList',
  //       name: 'LogsInfoList',
  //       component: () => import('@/views/logs/infoList/index.vue'),
  //       meta: { title: '日志列表' }
  //     }
  //   ]
  // }
]

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
 */
export const asyncRoutes = [
  // {
  //   path: '/permission',
  //   component: Layout,
  //   redirect: '/permission/page',
  //   alwaysShow: true, // will always show the root menu
  //   name: 'Permission',
  //   hidden: true,
  //   meta: {
  //     title: 'Permission',
  //     icon: 'lock',
  //     roles: ['admin', 'editor'] // you can set roles in root nav
  //   },
  //   children: [
  //     {
  //       path: 'page',
  //       component: () => import('@/views/permission/page'),
  //       name: 'PagePermission',
  //       meta: {
  //         title: 'Page Permission',
  //         roles: ['admin'] // or you can only set roles in sub nav
  //       }
  //     },
  //     {
  //       path: 'directive',
  //       component: () => import('@/views/permission/directive'),
  //       name: 'DirectivePermission',
  //       meta: {
  //         title: 'Directive Permission'
  //         // if do not set roles, means: this page does not require permission
  //       }
  //     },
  //     {
  //       path: 'role',
  //       component: () => import('@/views/permission/role'),
  //       name: 'RolePermission',
  //       meta: {
  //         title: 'Role Permission',
  //         roles: ['admin']
  //       }
  //     }
  //   ]
  // // },
  // {
  //   path: '/',
  //   component: Layout,
  //   redirect: '/login',
  //   meta: { roles: ['admin'] },
  //   children: [
  //     {
  //       path: 'dashboard',
  //       component: () => import('@/views/dashboard/index'),
  //       name: 'Dashboard',
  //       meta: { title: 'Dashboard', icon: 'dashboard', affix: true, roles: ['admin'] }
  //     }
  //   ]
  // },
  {
    path: '/error',
    component: Layout,
    redirect: 'noRedirect',
    name: 'ErrorPages',
    hidden: true,
    meta: {
      title: 'Error Pages',
      icon: '404'
    },
    children: [
      {
        path: '401',
        component: () => import('@/views/error-page/401'),
        name: 'Page401',
        meta: { title: '401', noCache: true }
      },
      {
        path: '404',
        component: () => import('@/views/error-page/404'),
        name: 'Page404',
        meta: { title: '404', noCache: true }
      }
    ]
  },
  {
    path: '/user',
    component: Layout,
    name: 'Example',
    alwaysShow: true,
    meta: { title: '用户管理', icon: 'el-icon-s-help', roles: ['admin', 'editor'] },
    redirect: '/user/infoList',
    children: [
      {
        path: 'userInfoList',
        name: 'UserInfoList',
        component: () => import('@/views/user/infoList/index.vue'),
        meta: { title: '用户信息', roles: ['admin', 'editor'] }
      }
    ]
  },
  {
    path: '/proxy',
    component: Layout,
    name: 'Example',
    alwaysShow: true,
    meta: { title: '代理管理', icon: 'el-icon-s-help', roles: ['admin', 'editor'] },
    redirect: '/proxy/infoList',
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
    redirect: '/finance/withdraw',
    name: 'Example',
    alwaysShow: true,
    meta: { title: '财务管理', icon: 'el-icon-s-help', roles: ['admin', 'editor'] },
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
        meta: { title: '佣金明细', roles: ['admin', 'editor'] }
      }
    ]
  },
  {
    path: '/equipment',
    component: Layout,
    name: 'Example',
    alwaysShow: true,
    redirect: '/equipment/infoList',
    meta: { title: '设备管理', icon: 'el-icon-s-help', roles: ['admin'] },
    children: [
      {
        path: 'infoList',
        name: 'EquipmentInfoList',
        component: () => import('@/views/equipment/infoList/index.vue'),
        meta: { title: '设备信息' }
      },
      {
        path: 'update',
        name: 'EquipmentUpdate',
        component: () => import('@/views/equipment/update/index.vue'),
        meta: { title: '更新' }
      },
      {
        path: 'limitIp',
        name: 'EquipmentLimitIp',
        component: () => import('@/views/equipment/limitIp/index.vue'),
        meta: { title: '限制ip' }
      },
      {
        path: 'notice',
        name: 'EquipmentNotice',
        component: () => import('@/views/equipment/notice/index.vue'),
        meta: { title: '公告列表' }
      }
    ]
  },
  {
    path: '/task',
    component: Layout,
    redirect: '/task/infoList',
    name: 'Example',
    alwaysShow: true,
    meta: { title: '任务管理', icon: 'el-icon-s-help', roles: ['admin'] },
    children: [
      {
        path: 'infoList',
        name: 'TaskInfoList',
        component: () => import('@/views/task/infoList/index.vue'),
        meta: { title: '营销任务' }
      },
      {
        path: 'notice',
        name: 'TaskList',
        component: () => import('@/views/task/list/index.vue'),
        meta: { title: '任务列表' }
      },
      {
        path: 'studio',
        name: 'Taskstudio',
        component: () => import('@/views/task/studio/index.vue'),
        meta: { title: '直播间营销' }
      }
    ]
  },
  {
    path: '/material',
    component: Layout,
    name: 'Example',
    redirect: '/material/infoList',
    alwaysShow: true,
    meta: { title: '素材管理', icon: 'el-icon-s-help', roles: ['admin'] },
    children: [
      {
        path: 'infoList',
        name: 'MaterialInfoList',
        component: () => import('@/views/material/infoList/index.vue'),
        meta: { title: '素材列表' }
      }
    ]
  },
  {
    path: '/roles',
    component: Layout,
    name: 'Example',
    alwaysShow: true,
    redirect: '/roles/infoList',
    meta: { title: '角色组管理', icon: 'el-icon-s-help', roles: ['admin'] },
    children: [
      {
        path: 'infoList',
        name: 'MaterialInfoList',
        component: () => import('@/views/roles/infoList/index.vue'),
        meta: { title: '角色组列表' }
      }
    ]
  },
  {
    path: '/logs',
    component: Layout,
    name: 'Example',
    alwaysShow: true,
    redirect: '/logs/infoList',
    meta: { title: '日志管理', icon: 'el-icon-s-help', roles: ['admin'] },
    children: [
      {
        path: 'infoList',
        name: 'LogsInfoList',
        component: () => import('@/views/logs/infoList/index.vue'),
        meta: { title: '日志列表' }
      }
    ]
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
