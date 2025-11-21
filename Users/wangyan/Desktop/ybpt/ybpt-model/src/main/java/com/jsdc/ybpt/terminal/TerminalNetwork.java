package com.jsdc.ybpt.terminal;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 医疗机构终端信息-网络
 */
@Data
@Entity(name = "terminal_network")
@TableName("terminal_network")
public class TerminalNetwork extends Model<TerminalInfo> {
    @TableId
    @Id
    private String id;
    //1.访问医保网络的终端 2.同时访问医保网和其他网络的终端
    private String type = "1";
    //终端信息id
    private String terminal_info_id;
    //设备名称
    private String device_name;
    //IP地址
    private String id_address;
    //MAC地址
    private String mac_address;
    //操作系统
    private String operating_system;
    //运行业务
    private String business;
    //备注
    private String Remarks;

}
