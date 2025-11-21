package com.jsdc.ybpt.terminal;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 医疗机构终端信息-用户
 */
@Data
@Entity(name = "terminal_user")
@TableName("terminal_user")
public class TerminalUser extends Model<TerminalInfo> {
    @TableId
    @Id
    private String id;
    //终端信息id
    private String terminal_info_id;

    //姓名
    private String name;
    //手机号
    private String phone_number;
    //备注
    private String Remarks;
    //身份证号
    private String ID_number;

}
