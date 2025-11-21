const data = [
  {
    path: '/index',
    name: 'index',
    redirect: 'index',
    component: '@/views/index/index',
    meta: {
      title: '首页',
      icon: 'home',
      affix: true,
    },
  },
  {
    path: '/personnelManagement',
    component: 'Layout',
    redirect: 'noRedirect',
    name: 'PersonnelManagement',
    meta: { title: '系统管理', icon: 'laptop-medical', permissions: ['admin'] },
    children: [
      {
        path: 'userManagement',
        name: 'UserManagement',
        component: '@/views/personnelManagement/userManagement/index',
        meta: { title: '用户管理', icon: 'file-medical', },
      },
      {
        path: 'roleManagement',
        name: 'RoleManagement',
        component: '@/views/personnelManagement/roleManagement/index',
        meta: { title: '角色管理', icon: 'file-medical', },
      },
      {
        path: 'menuManagement',
        name: 'MenuManagement',
        component: '@/views/personnelManagement/menuManagement/index',
        meta: { title: '菜单管理', icon: 'file-medical', badge: 'New' },
      },
    ],
  },
  {
    path: '/baseinfo',
    component: 'Layout',
    redirect: 'noRedirect',
    name: 'baseinfo',
    alwaysShow: true,
    meta: { title: '基础信息管理', icon: 'briefcase-medical' },
    children: [
      {
        path: 'hosipital',
        name: 'hosipital',
        component: '@/views/personaldaiyu/personalsbsp/mmmtdjsh/index',
        meta: {
          title: '医疗机构管理',
          icon: 'file-medical',
          permissions: ['admin', 'editor'],
        },
      },
      {
        path: 'keshimanagement',
        component: 'Layout',
        redirect: 'noRedirect',
        name: 'keshimanagement',
        meta: {
          title: '科室管理',
          icon: 'file-medical',
          permissions: ['admin'],
        },
      },
      {
        path: 'doctormanagement',
        component: '@/views/personaldaiyu/personalsbsp/mmmtdjsh/index',
        name: 'doctormanagement',
        meta: {
          title: '医师管理',
          icon: 'file-medical',
          permissions: ['admin'],
        },
      },
    ],
  },
  {
    path: '/personnelmanagement',
    component: 'Layout',
    redirect: 'noRedirect',
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
        component: 'Layout',
        meta: {
          title: '个人待遇申报审批管理',
          icon: 'folder',
          permissions: ['admin'],
        },
        children: [
          {
            path: 'mmmtdj',
            name: 'mmmtdj',
            component: '@/views/personaldaiyu/personalsbsp/mmmtdj/index',
            meta: {
              title: '门慢门特登记',
              icon: 'file-medical',
              permissions: ['admin'],
            },
          },
          {
            path: 'mmmtdjsh',
            name: 'mmmtdjsh',
            component: '@/views/personaldaiyu/personalsbsp/mmmtdjsh/index',
            meta: {
              title: '门慢门特登记审核',
              permissions: ['admin'],
              icon: 'file-medical',
            },
          },
          {
            path: 'zzzydj',
            name: 'zzzydj',
            component: '@/views/personaldaiyu/personalsbsp/zzzydj/index',
            meta: {
              title: '转诊转院登记',
              permissions: ['admin'],
              icon: 'file-medical',
            },
          },
          {
            path: 'zzzydjsh',
            name: 'zzzydjsh',
            component: '@/views/personaldaiyu/personalsbsp/zzzydjsh/index',
            meta: {
              title: '转诊转院登记审核',
              icon: 'file-medical',
              permissions: ['admin'],
            },
          },
          {
            path: 'ydjydj',
            name: 'ydjydj',
            component: '@/views/personaldaiyu/personalsbsp/ydjydj/index',
            meta: {
              title: '异地就医登记',
              icon: 'file-medical',
              permissions: ['admin'],
            },
          },
          {
            path: 'ydjydjsh',
            name: 'ydjydjsh',
            component: '@/views/personaldaiyu/personalsbsp/ydjydjsh/index',
            meta: {
              title: '异地就医登记审核',
              icon: 'file-medical',
              permissions: ['admin'],
            },
          },
        ],
      },
    ],
  },
  {
    path: '/duizhang',
    component: 'Layout',
    redirect: 'noRedirect',
    name: 'duizhang',
    meta: { title: '对账管理', icon: 'book-medical' },
    children: [
      {
        path: 'yldzmanagement',
        name: 'yldzmanagement',
        component: '@/views/accountcompare/hospitalaccount/index',
        meta: { title: '医疗机构对账管理', icon: 'file-medical', },
      },
      {
        path: 'dzmanagement',
        name: 'dzmanagement',
        component: '@/views/accountcompare/centeraccount/index',
        meta: { title: '中心端对账管理', icon: 'file-medical', },
      },
    ],
  },
]
module.exports = [
  {
    url: '/menu/navigate',
    type: 'post',
    response() {
      return { code: 200, msg: 'success', data: data }
    },
  },
]
