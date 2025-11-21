import Vue from 'vue'
import App from './App'
import store from './store'
import router from './router'
import './plugins'
import '@/layouts/export'
import api from './common/index'
import plTable from 'pl-table'
import 'pl-table/themes/index.css'
import '@/styles/scrollbar.css'
//
//
// // if (process.env.NODE_ENV === 'production') {
// //   const { mockXHR } = require('@/utils/static')
// //   mockXHR()
// // }
Vue.use(plTable);
Vue.config.productionTip = false

Vue.prototype.$api = api.commonUrl ;

new Vue({
  el: '#vue-medical',
  router,
  store,
  render: (h) => h(App),
})
