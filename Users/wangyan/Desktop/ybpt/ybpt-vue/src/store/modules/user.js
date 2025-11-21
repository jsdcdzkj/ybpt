
import Vue from 'vue'
import { getUserInfo, login, logout } from '@/api/user'
import {
  getAccessToken,
  removeAccessToken,
  setAccessToken,
} from '@/utils/accessToken'
import { resetRouter } from '@/router'
import { title, tokenName } from '@/config'

const state = {
  accessToken: getAccessToken(),
  username: '',
  avatar: '',
  permissions: [],
  msgNumber: 0
}
const getters = {
  accessToken: (state) => state.accessToken,
  username: (state) => state.username,
  avatar: (state) => state.avatar,
  permissions: (state) => state.permissions,
}
const mutations = {
  setAccessToken(state, accessToken) {
    state.accessToken = accessToken
    setAccessToken(accessToken)
  },
  setUsername(state, username) {
    state.username = username
  },
  setAvatar(state, avatar) {
    state.avatar = avatar
  },
  setPermissions(state, permissions) {
    state.permissions = permissions
  },
}
const actions = {
  setPermissions({ commit }, permissions) {
    commit('setPermissions', permissions)
  },
  async login({ commit }, userInfo) {
    const  res  = await login(userInfo)
    console.log(res);
    if (res.code==0){
      const data=res.data;
      const accessToken = data[tokenName]
      const user = data["user"];
      const roles = data["roles"];
      localStorage.setItem("userinfo", JSON.stringify(user))
      localStorage.setItem("roles", JSON.stringify(roles))
      if (accessToken) {
        commit('setAccessToken', accessToken)
        const hour = new Date().getHours()
        const thisTime =
            hour < 8
                ? '早上好'
                : hour <= 11
                ? '上午好'
                : hour <= 13
                    ? '中午好'
                    : hour < 18
                        ? '下午好'
                        : '晚上好'
        Vue.prototype.$baseNotify(`欢迎登录${title}`, `${thisTime}！`)
      } else {
        Vue.prototype.$baseMessage(
            `登录接口异常，未正确返回${tokenName}...`,
            'error'
        )
      }
    }else{
      userInfo.password='';
      Vue.prototype.$baseMessage(res.msg, 'error')
    }


  },
  async getUserInfo({ commit, state }) {
    const { data } = await getUserInfo(state.accessToken)
    if (!data) {
      Vue.prototype.$baseMessage('验证失败，请重新登录...', 'error')
      return false
    }
    let { permissions, username, avatar } = data
    if (permissions && username && Array.isArray(permissions)) {
      commit('setPermissions', permissions)
      commit('setUsername', username)
      commit('setAvatar', avatar)
      return permissions
    } else {
      Vue.prototype.$baseMessage('用户信息接口异常', 'error')
      return false
    }
  },
  async logout({ dispatch }) {
    await logout(state.accessToken)
    await dispatch('resetAccessToken')
    await resetRouter()
  },
  resetAccessToken({ commit }) {
    commit('setPermissions', [])
    commit('setAccessToken', '')
    removeAccessToken()
  },
}
export default { state, getters, mutations, actions }
