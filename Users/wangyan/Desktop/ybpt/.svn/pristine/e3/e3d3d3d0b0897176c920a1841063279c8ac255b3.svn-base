import router from '@/router'
import store from '@/store'
import VabProgress from 'nprogress'
import 'nprogress/nprogress.css'
import getPageTitle from '@/utils/pageTitle'
import {
  authentication,
  loginInterception,
  progressBar,
  recordRoute,
  routesWhiteList,
} from '@/config'
import {
    getAccessToken,
    removeAccessToken,
    setAccessToken,
} from '@/utils/accessToken'
VabProgress.configure({
  easing: 'ease',
  speed: 500,
  trickleSpeed: 200,
  showSpinner: false,
})

router.beforeResolve(async (to, from, next) => {
    if('/login?type=1' == to.fullPath){
        localStorage.clear();
    }
    if (progressBar) VabProgress.start()

    // let hasToken = store.getters['user/accessToken']
    let hasToken = getAccessToken()
    console.log(hasToken,'====>是否有token')

    if (!loginInterception) hasToken = true
    if (hasToken) {
        if (to.path === '/login') {
            next({ path: '/' })
            if (progressBar) VabProgress.done()
        } else {
            const hasPermissions =
                store.getters['user/permissions'] &&
                store.getters['user/permissions'].length > 0
            if (hasPermissions) {
                console.log('window.top2')
                if (null != window.top2) {
                    window.top2()
                }

                next()
            } else {
                try {
                    let permissions
                    if (!loginInterception) {
                        //settings.js loginInterception为false时，创建虚拟权限
                        await store.dispatch('user/setPermissions', ['admin'])
                        permissions = ['admin']
                    } else {
                        permissions = await store.dispatch('user/getUserInfo')
                    }

                    let accessRoutes = []
                    if (authentication === 'intelligence') {
                        accessRoutes = await store.dispatch('routes/setRoutes', permissions)
                    } else if (authentication === 'all') {
                        accessRoutes = await store.dispatch('routes/setAllRoutes')
                    }
                    router.addRoutes(accessRoutes)
                    next({ ...to, replace: true })
                } catch {
                    await store.dispatch('user/resetAccessToken')
                    if (progressBar) VabProgress.done()
                }
            }
        }
    } else {
        if (routesWhiteList.indexOf(to.path) !== -1) {
            next()
        } else {
            if (recordRoute) {
                next(`/login?redirect=${to.path}`)
            } else {
                next('/login')
            }
            console.log('=====》无token去往login页面==》结束')

            if (progressBar) VabProgress.done()
        }
    }
    document.title = getPageTitle(to.meta.title)

})
router.afterEach(() => {
  if (progressBar) VabProgress.done()
})
