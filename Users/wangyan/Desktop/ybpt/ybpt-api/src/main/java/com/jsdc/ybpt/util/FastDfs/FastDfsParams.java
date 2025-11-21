package com.jsdc.ybpt.util.FastDfs;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FastDfsParams {
    private String uploadPath;
    private MultipartFile file;
    /**
     * 业务类型 1.商品稽核类型 2.考核 3.清算 4.医疗凭证 5.价格申报-pdf 6.耗材文件上传 7.门慢门特图片上传 8:价格申报-详情文件 9.集采缺货-采购 10.通知附件11.下发文件 12.药品告知手续 16机构文件  17 保证金文件
     * 13 保证金文件 14考核单  20 网签补充协议模板 21 网签补充协议生成的word 22网签补充协议生成的pdf  23 网签生成的word  24网签生成的pdf
     * 25 制剂告知申请附件 26制剂自主定价告知受理书 27 医疗机构制剂定价测算表 28 医保拨付通知发生数文件 29 医保拨付通知应结算文件 30 医保拨付通知居民大病保险实际支付文件
     * 31 医保拨付通知月结算文件 32 医保拨付通知DRG文件 33 拨付分析数据模板 34.终端 35.通用下载功能
     */
    private String bizType;
    private String bizId;//业务数据ID
    private String fileName;//文件名


    public FastDfsParams(String uploadPath, MultipartFile file, String bizType, String bizId) {
        this.uploadPath = uploadPath;
        this.file = file;
        this.bizType = bizType;
        this.bizId = bizId;
    }
}
