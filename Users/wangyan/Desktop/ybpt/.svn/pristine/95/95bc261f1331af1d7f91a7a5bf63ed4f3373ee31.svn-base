import request from '@/utils/request_civil'

export function getPackInfoRatio(data) {
    return request({
        // 获取套餐占比
        url: '/statistic/packageChart/getRatio',
        method: 'post',
        params: data,
    })
}

// 用人单位套餐比
export function getPackageChart(data) {
    return request({
        url: '/statistic/getPhysicalRatio',
        method: 'post',
        params: data,
    })
}

// 用人单位
export function getPackageChartYearList() {
    return request({
        // 获取套餐年份
        url: '/statistic/packageChart/getYearList',
        method: 'post',
    })
}



//体检占比 全部用人单位
export function getEmploying() {
    return request({
        url: '/statistic/getEmploying',
        method: 'post',
    })
}
//体检占比
export function getPhysicalRatio(data) {
    return request({
        url: '/statistic/getEmployingInfoPhysicalRatioCheckedByOrg',
        method: 'post',
        params: data,
    })
}

//组织占比
export function getOrganization(data) {
    return request({
        url: '/statistic/getOrganization',
        method: 'post',
        params: data,
    })
}

//服务排行
export function getRanking(data) {
    return request({
        url: '/statistic/getRanking',
        method: 'post',
        params: data,
    })
}

// 服务排行获取年份
export function getRankingYear() {
    return request({
        // 获取年份
        url: '/statistic/getRankingYear',
        method: 'get',
    })
}

// 体检机构 获取体检占比年份
export function getYearListPhysicalRatioCheckedByOrg() {
    return request({
        // 获取年份
        url: '/statistic/yearListPhysicalRatioCheckedByOrg',
        method: 'get',
    })
}

// 体检机构 获取参与过此机构的行政单位的信息
export function getEmployingInfoListCheckedByOrg() {
    return request({
        // 获取年份
        url: '/statistic/getEmployingInfoListCheckedByOrg',
        method: 'get',
    })
}


export function getEmployingInfoPhysicalRatioCheckedByOrg(data) {
    return request({
        url: '/statistic/getEmployingInfoPhysicalRatioCheckedByOrg',
        method: 'post',
        params: data,
    })
}


export function eChat() {
    return request({
        url: '/statistic/eChat',
        method: 'post',
        params: '',
    })
}

export function eChatYB() {
    return request({
        url: '/statistic/eChatYB',
        method: 'post',
        params: '',
    })
}
