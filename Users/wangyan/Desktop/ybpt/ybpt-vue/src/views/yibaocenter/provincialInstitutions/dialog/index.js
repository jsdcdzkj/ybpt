import { createDialog } from '@/utils/dialog'
import Add from './Add.vue'
import Vue from 'vue'

export const createAddDialog = (props, callback = null) =>
  createDialog(Vue.extend(Add), props, callback)
