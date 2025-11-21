package com.jsdc.ybpt.terminal;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.jsdc.ybpt.model.FileInfo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 医疗机构终端信息
 */
@Data
@Entity(name = "terminal_info")
@TableName("terminal_info")
public class TerminalInfo extends Model<TerminalInfo> {
    @TableId
    @Id
    private String id;
    //机构编码
    private String org_code;
    //机构名称
    private String org_name;
    //统筹区
    private String area;
    //统筹区 转义 admdvs-area
    @Transient
    @TableField(exist = false)
    private String area_name;
    //医保编码
    private String medical_code;
    //机构地址
    private String address;
    //机构联系人
    private String contacts;
    //机构联系电话
    private String contact_number;
    //医疗等级  1 一级 2	二级 3	三级 9	未定级
    private String cred_lv;
    //医疗等级 转义 LMTPRIC_HOSP_LV
    @Transient
    @TableField(exist = false)
    private String cred_lv_name;
    //运营商
    private String operator;
    //运营商 转义 operator
    @Transient
    @TableField(exist = false)
    private String operator_name;
    // 审核状态 0.未提交 1.审核中 2.已审核 3.已驳回
    private Integer status;
    //状态 转义
    @Transient
    @TableField(exist = false)
    private String status_name;
    //审核时间
    private String verify_time;
    //审核意见
    private String verify_reason;
    //审核人
    private String verify_user;


    //是否专网专线 0 否 1 是
    private String is_network_line;
    //存在哪些专线
    private String dedicated_line;
    //访问的服务
    private String services_accessed;

    //哑终端数量
    private String dumb_number;

    //使用的杀毒软件
    private String antivirus;

    @Transient
    @TableField(exist = false)
    private Integer pageNo = 1;
    @Transient
    @TableField(exist = false)
    private Integer pageSize = 10;
    //开始时间
    @Transient
    @TableField(exist = false)
    private String start_time;
    //结束时间
    @Transient
    @TableField(exist = false)
    private String end_time;

    //访问医保网络的终端
    @Transient
    @TableField(exist = false)
    private List<TerminalNetwork> terminalNetworks;
    //访问医保网络的终端数量
    @Transient
    @TableField(exist = false)
    private Integer terminalNetworkNumber = 0;
    //同时访问医保网和其他网络的终端
    @Transient
    @TableField(exist = false)
    private List<TerminalNetwork> terminalNetworksOther;
    //同时访问医保网和其他网络的终端数量
    @Transient
    @TableField(exist = false)
    private Integer terminalNetworkOtherNumber = 0;
    @Transient
    @TableField(exist = false)
    //医疗机构终端信息-用户
    private List<TerminalUser> terminalUsers;
    //开通账号数量
    @Transient
    @TableField(exist = false)
    private Integer terminalUserNumber = 0;

    //是否提交 0保存 1提交
    private String is_submit = "0";

    //上传文件
    @Transient
    @TableField(exist = false)
    private List<FileInfo> files;

    @Transient
    @TableField(exist = false)
    private List<String> fileIds;
    @Transient
    @TableField(exist = false)
    List<String> roleSymbol;

    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志
}
