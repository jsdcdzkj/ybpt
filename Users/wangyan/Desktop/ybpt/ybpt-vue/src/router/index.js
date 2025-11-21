import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from '@/layouts'
import EmptyLayout from '@/layouts/EmptyLayout'
import {publicPath, routerMode} from '@/config'


Vue.use(VueRouter)
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true,
  },
  {
    path: '/register',
    component: () => import('@/views/register/index'),
    hidden: true,
  },
  {
    path: '/401',
    name: '401',
    component: () => import('@/views/401'),
    hidden: true,
  },
  {
    path: '/404',
    name: '404',
    component: () => import('@/views/404'),
    hidden: true,
  },
  {
    path: '/assess',
    component: Layout,
    redirect: 'noRedirect',
    //alwaysShow: true,
    name: 'assessment',
    children: [
      {
        path: '/assess/filling/:id',
        name: 'filling',
        component: () => import('@/views/assessment/assessManage/filling'),
        meta: {
          title: '考核填报',
          icon: 'file-medical',
        },
        hidden: true,
      },
    ],
  },
  // {
  // 	alwaysShow: true,
	// path: "/assessManage",
  // 	component: Layout,
  // 	name: "assessManage",
  // 	redirect: "noRedirect",
	// meta: {
	// 	icon: "file-medical",
	// 	title: "考核管理"
	// },
  // 	children: [{
  // 		redirect: null,
  // 		path: "/assessManage/index",
  // 		component: () => import('@/views/assess/index'),
  // 		meta: {icon: "folder", title: "首页"},
  // 	},{
	// 	redirect: null,
	// 	path: "/assessManage/criterion",
	// 	component: () => import('@/views/assess/criterion'),
	// 	meta: {icon: "folder", title: "考核单设计"},
	// },{
	// 	redirect: null,
	// 	path: "/assessManage/task",
	// 	component: () => import('@/views/assess/task'),
	// 	meta: {icon: "folder", title: "考核任务"},
	// },{
	// 	redirect: null,
	// 	path: "/assessManage/assess",
	// 	component: () => import('@/views/assess/assess'),
	// 	meta: {icon: "folder", title: "绩效考核"},
	// },{
	// 	redirect: null,
	// 	path: "/assessManage/assessBack",
	// 	component: () => import('@/views/assess/assessBack'),
	// 	meta: {icon: "folder", title: "考核反馈"},
	// },{
	// 	redirect: null,
	// 	path: "/assessManage/history",
	// 	component: () => import('@/views/assess/history'),
	// 	meta: {icon: "folder", title: "历史考核"},
	// },{
	// 	redirect: null,
	// 	path: "/assessManage/equationSet",
	// 	component: () => import('@/views/assess/equationSet'),
	// 	meta: {icon: "folder", title: "公式配置"},
	// }]
  // }
]

export const asyncRoutes = [
  {
    path: '/',
    component: Layout,
    redirect: 'index',
    children: [
      {
        path: 'index',
        name: 'Index',
        component: () => import('@/views/index/index'),
        meta: {
          title: '首页',
          icon: 'clinic-medical',
          affix: true,
        },
      },
    ],
  },
  {
    path: '/baseinfo',
    component: Layout,
    redirect: 'noRedirect',
    //alwaysShow: true,
    name: 'baseinfo',
    meta: {
      title: '基础信息管理',
      icon: 'briefcase-medical',
      permissions: ['admin'],
    },
    children: [
      {
        path: 'hosipital',
        name: 'hosipital',
        component: () => import('@/views/baseinfomanagement/hosipitalmanagement/index'),
        meta: {
          title: '',
          icon: 'file-medical',
        },
      },
      {
        path: 'keshimanagement',
        name: 'keshimanagement',
        component: () => import('@/views/baseinfomanagement/keshimanagement/index'),
        meta: {
          title: '科室管理',
          icon: 'file-medical',
        },
      },
    ],
  },
  {
    path: '/personnelmanagement',
    component: EmptyLayout,
    redirect: 'noRedirect',
    alwaysShow: true,
    name: 'personnelmanagement',
    meta: {
      title: '个人待遇管理',
      icon: 'user-nurse',
      permissions: ['admin'],
    },
    children: [
      {
        path: 'personalshenpi',
        name: 'personalshenpi',
        component: Layout,
        meta: {
          title: '个人待遇申报审批管理',
          icon: 'folder',
        },
        children: [
          {
            path: 'myicon',
            name: 'myicon',
            component: () =>
              import('@/views/vab/icon/index'),
            meta: {
              title: '我的图标',
              icon: 'file-medical',
            },
          },
          {
            path: 'mmmtdj',
            name: 'mmmtdj',
            component: () =>
              import('@/views/personaldaiyu/personalsbsp/mmmtdj/index'),
            meta: {
              title: '门慢门特登记',
              icon: 'file-medical',
            },
          },
          {
            path: 'mmmtdjsh',
            name: 'mmmtdjsh',
            component: () => import('@/views/personaldaiyu/personalsbsp/mmmtdjsh/index'),
            meta: {
              title: '门慢门特登记审核',
              icon: 'file-medical',
            },
          },
          {
            path: 'zzzydj',
            name: 'zzzydj',
            component: () => import('@/views/personaldaiyu/personalsbsp/zzzydj/index'),
            meta: {
              title: '转诊转院登记',
              icon: 'file-medical',
            },
          },
          {
            path: 'zzzydjsh',
            name: 'zzzydjsh',
            component: () => import('@/views/personaldaiyu/personalsbsp/zzzydjsh/index'),
            meta: {
              title: '转诊转院登记审核',
              icon: 'file-medical',
            },
          },
          {
            path: 'ydjydj',
            name: 'ydjydj',
            component: () => import('@/views/personaldaiyu/personalsbsp/ydjydj/index'),
            meta: {
              title: '异地就医登记',
              icon: 'file-medical',
            },
          },
          {
            path: 'ydjydjsh',
            name: 'ydjydjsh',
            component: () => import('@/views/personaldaiyu/personalsbsp/ydjydjsh/index'),
            meta: {
              title: '异地就医登记审核',
              icon: 'file-medical',
            },
          },
        ],
      },
    ],
  },
  {
    path: '/duizhang',
    component: Layout,
    redirect: 'noRedirect',
    alwaysShow: true,
    name: 'duizhang',
    meta: {
      title: '对账管理',
      icon: 'book-medical',
      permissions: ['admin'],
    },
    children: [
      {
        path: 'yldzmanagement',
        name: 'yldzmanagement',
        component: () => import('@/views/accountcompare/hospitalaccount/index'),
        meta: {
          title: '医疗机构对账管理',
          icon: 'file-medical',
        },

      },
      {
        path: 'dzmanagement',
        name: 'dzmanagement',
        component: () => import('@/views/accountcompare/centeraccount/index'),
        meta: {
          title: '中心端对账管理',
          icon: 'file-medical',
        },

      },
    ],
  },
  {
    path: '/sysmanagement',
    component: Layout,
    redirect: 'noRedirect',
    //alwaysShow: true,
    name: 'sysmanagement',
    meta: {
      title: '系统管理',
      icon: 'laptop-medical',
      permissions: ['admin'],
    },
    children: [
      {
        path: 'usernamangement',
        name: 'usernamangement',
        component: () => import('@/views/personnelManagement/userManagement/index'),
        meta: {
          title: '用户管理',
          icon: 'file-medical',
        },
      },
      {
        path: 'rolemanagement',
        name: 'rolemanagement',
        component: () => import('@/views/personnelManagement/roleManagement/index'),
        meta: {
          title: '角色管理',
          icon: 'file-medical',
        },
      },
      {
        path: 'menumanagement',
        name: 'menumanagement',
        component: () => import('@/views/personnelManagement/menuManagement/index'),
        meta: {
          title: '菜单管理',
          icon: 'file-medical',
        },
      },
    ],
  },
  {
    path: '*',
    redirect: '/404',
    hidden: true,
  },
]

const router = new VueRouter({
  base: publicPath,
  mode: routerMode,
  scrollBehavior: () => ({
    y: 0,
  }),
  routes: constantRoutes,
})
//注释的地方是允许路由重复点击，如果你觉得框架路由跳转规范太过严格可选择放开
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location, onResolve, onReject) {
  if (onResolve || onReject)
    return originalPush.call(this, location, onResolve, onReject)
  return originalPush.call(this, location).catch((err) => err)
}
export function resetRouter() {
  router.matcher = new VueRouter({
    base: publicPath,
    mode: routerMode,
    scrollBehavior: () => ({
      y: 0,
    }),
    routes: constantRoutes,
  }).matcher
}

export default router
