package com.jsdc.ybpt.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.abnormal.SettleAbnormal;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.service.SettleAbnormalService;
import com.jsdc.ybpt.service.SysUserService;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("settleAbnormal")
public class SettleAbnormalController {
    @Autowired
    private SettleAbnormalService settleAbnormalService ;


    @Autowired
    private SysUserService sysUserService;

    /**
    *医保导入
    * Author wzn
    * Date 2022/9/29 15:06
    */
    @RequestMapping("/importData")
    public ResultInfo importData(MultipartFile file) {
        settleAbnormalService.importData(file);
//        List<SettleAbnormal> settleAbnormalList = settleAbnormalService.importData(file);
        return ResultInfo.success();
    }




    /**
    *医药机构导入
    * Author wzn
    * Date 2022/9/29 15:07
    */
    @RequestMapping("/importData2")
    public ResultInfo importData2(MultipartFile file) {
        List<SettleAbnormal> settleAbnormalList = settleAbnormalService.importData2(file);
        return ResultInfo.success();
    }


    /**
     * 查询数据
     * @param pageNo
     * @param pageSize
     * @param org_code
     * @param upload_no
     * @param if_upload
     * @return
     */
    @RequestMapping("/getSettleAbnormalList")
    public ResultInfo getSettleAbnormalList(Integer pageNo,Integer pageSize,String org_code,String upload_no,String if_upload,String area){
        Page page = new Page(pageNo,pageSize);
        QueryWrapper qw = new QueryWrapper<SettleAbnormal>();
        qw.eq("upload_no",upload_no);
        SysUser sysUser = sysUserService.getUser();
        if("1".equals(sysUser.getUser_type())){//行政管理
            if(!StrUtil.hasEmpty(org_code)){
                qw.eq("org_code",org_code);
            }
        }else if("2".equals(sysUser.getUser_type()) || "3".equals(sysUser.getUser_type())){
            qw.eq("org_code",sysUser.getOrg_code());
        }
        if(!StrUtil.hasEmpty(if_upload)){
            qw.eq("if_upload",if_upload);
        }
        if(!StrUtil.hasEmpty(area)){
            qw.eq("area",area);
        }
        Page pageinfo = settleAbnormalService.page(page, qw);
        return ResultInfo.success(pageinfo);
    }


    /**
     * 导出徐州异常数据
     * @param response
     * @param org_code 机构编码
     * @param upload_no 上传批次
     * @throws Exception
     */
    @RequestMapping("/exportSettleAbnormalData")
    public void exportSettleAbnormalData(HttpServletResponse response,String org_code,String upload_no,String if_upload,String area) throws Exception{
        QueryWrapper qw = new QueryWrapper<SettleAbnormal>();
        qw.eq("upload_no",upload_no);
        SysUser sysUser = sysUserService.getUser();
        if("1".equals(sysUser.getUser_type())){//行政管理
            if(!StrUtil.hasEmpty(org_code)){
                qw.eq("org_code",org_code);
            }
        }else if("2".equals(sysUser.getUser_type()) || "3".equals(sysUser.getUser_type())){
            qw.eq("org_code",sysUser.getOrg_code());
        }
        if(!StrUtil.hasEmpty(if_upload)){
            qw.eq("if_upload",if_upload);
        }
        if(!StrUtil.hasEmpty(area)){
            qw.eq("area",area);
        }
        List<SettleAbnormal> details = settleAbnormalService.list(qw);
        // 通过工具类创建writer，默认创建xls格式
        BigExcelWriter writer = (BigExcelWriter) ExcelUtil.getBigWriter();
        writer.addHeaderAlias("id", "id");
        writer.addHeaderAlias("rid", "RID");
        writer.addHeaderAlias("org_code", "机构编码");
        writer.addHeaderAlias("org_name", "机构名称");
        writer.addHeaderAlias("area", "行政区");
        writer.addHeaderAlias("carno", "身份证号");
        writer.addHeaderAlias("name", "人员姓名");
        writer.addHeaderAlias("settle_time", "费用发生时间");
        writer.addHeaderAlias("settle_type", "结算类别");
        writer.addHeaderAlias("med_type", "医疗类别");
        writer.addHeaderAlias("catalogue_type", "目录大类");
        writer.addHeaderAlias("catalogue_code", "目录编码");
        writer.addHeaderAlias("catalogue_code_new", "修改后编码");
        writer.addHeaderAlias("reason_one", "违规原因1");
        writer.addHeaderAlias("reason_two", "违规原因2");
        writer.addHeaderAlias("reason_three", "违规原因3");
        writer.addHeaderAlias("reason_four", "违规原因4");
        writer.addHeaderAlias("reason_five", "申诉原因");
        writer.addHeaderAlias("insured_persons_no", "参保人编号");
        writer.addHeaderAlias("mdtrt_id", "就诊id");
        writer.addHeaderAlias("setl_id", "结算id");
        writer.addHeaderAlias("project_name", "项目名称");
        //只导出定义字段
        writer.setOnlyAlias(true) ;
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(details, true);
        //out为OutputStream，需要写出到的目标流
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        // 关闭writer，释放内存
        writer.close();
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }
    /**
     * 获取批次
     * @throws Exception
     */
    @RequestMapping("/getUploadNo")
    public ResultInfo getUploadNo(){
        QueryWrapper qw = new QueryWrapper<SettleAbnormal>();
        qw.groupBy("upload_no");
        qw.select("upload_no");
        qw.orderByDesc("upload_no");
        List<String> list = settleAbnormalService.listObjs(qw);
        return ResultInfo.success(list);
    }


}
