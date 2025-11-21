import request from '@/utils/request'
import {baseURL} from "@/config/setting.config";
import axios from "axios";
export function getPage(data) {
  return request({
    url: '/advice/getPage',
    method: 'post',
    data,
  })
}

export function doDelete(data) {
  return request({
    url: '/advice/delete',
    method: 'post',
    data,
  })
}

export function insert(data) {
  return request({
    url: '/advice/insert',
    method: 'post',
    data,
  })
}

// export function tcmWord(data) {
//   return request({
//     url: '/drugPrice/tcmWord?id='+data,
//     method: 'post',
//   })
// }


export function tcmWord(data) {
  return request({
    url: '/drugPrice/tcmWord',
    method: 'get',
    params: {
      id: data
    },
    responseType: 'blob'
  })
}



