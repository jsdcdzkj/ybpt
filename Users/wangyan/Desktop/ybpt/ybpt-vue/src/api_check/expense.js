import request from '@/utils/request_civil'
import axios from 'axios'
import { baseURL_civil } from '@/config/setting.config'
export const getPageList = (params) =>
  request({
    url: 'expenseDetail/getPageList',
    method: 'get',
    params,
  })

export const getDetail = (params) =>
  request({
    url: 'expenseDetail/getDetail',
    method: 'get',
    params,
  })

export const downLoad = (params) =>
  request({
    url: 'expense/downLoad',
    method: 'get',
    params,
  })
export const getPageList2 = (params) =>
  request({
    url: 'expense/getPageList',
    method: 'get',
    params,
  })
export const expenseDetail = (params) =>
  request({
    url: 'expenseDetail/getList',
    method: 'get',
    params,
  })
export const saveExpense = (data) =>
  request({
    url: 'expense/saveExpense',
    method: 'post',
    data,
  })
export const saveOrUpdate = (data) =>
  request({
    url: 'expenseDetail/saveOrUpdate',
    method: 'post',
    data,
  })

export const uploadFile = (data) => {
  const instance = axios.create({ baseURL: baseURL_civil })
  return instance.post('expense/upload', data, {
    headers: {
      'Content-Type': 'multipart/form-data',
      accessToken: localStorage.getItem('accessToken'),
    },
  })
}
//
export const expenseDetailExport = (data) =>
  request.post('expenseDetail/Export', data, { responseType: 'blob' })

export function expenseDetailExportYY(params) {
  return request({
    url: '/expense/yyExport',
    method: 'get',
    responseType: 'blob',
    headers: {
      'Content-Type': 'application/x-download',
    },
    params,
  })
}
// /expense/getDict

export const getDict = (params) =>
  request({
    url: 'expense/getDict',
    method: 'get',
    params,
  })

export const uploadFileBatch = (data) => {
  const instance = axios.create({ baseURL: baseURL_civil })
  return instance.post('expense/uploadFile', data, {
    headers: {
      'Content-Type': 'multipart/form-data',
      accessToken: localStorage.getItem('accessToken'),
    },
  })
}
