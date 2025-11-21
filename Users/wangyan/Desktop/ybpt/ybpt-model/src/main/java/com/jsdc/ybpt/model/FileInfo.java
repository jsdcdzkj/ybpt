package com.jsdc.ybpt.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity(name = "file_info")
@TableName("file_info")
public class FileInfo extends Model {
    @TableId
    @Id
    private String id;//主键ID
    private String fileName;//文件名称
    private String contentType;//文件类型
    private String fileType;//文件分类  1：图片 2：文件
    private String fileSize;//文件大小
    private String fileUrl;//文件地址
    private String uploadTime;//上传时间
    private String bizType;//业务类型  1.商品稽核类型 2.考核 3.清算 4.医疗凭证 5.价格申报-pdf 6.耗材文件上传,7门慢门特图片上传8:价格申报-详情文件 10.通知附件.11 中医价格
    // 13 公务员体检 14 考核单 15 机构填写上传 16 保证金文件  20网签模板 17 考核任务申诉 28公务员医疗补助人员体检费用填报明细 34.终端
    private String bizId;//业务数据ID
    private String fileMd5;//fastDfs 文件md5 用于删除查看等操作
}
