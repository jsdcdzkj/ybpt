package com.jsdc.ybpt.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.abnormal.MedicalCareAbnormal;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.service.MedicalCareAbnormalService;
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
@RequestMapping("/medicalAbnormal")
public class MedicalAbnormalController {
    @Autowired
    private MedicalCareAbnormalService medicalCareAbnormalService;
    @Autowired
    private SysUserService sysUserService;


    /**
     * 查询数据
     * @param pageNo
     * @param pageSize
     * @param org_code
     * @param upload_no
     * @param if_upload
     * @return
     */
    @RequestMapping("/getMedicalCareAbnormalList")
    public ResultInfo getMedicalCareAbnormalList(Integer pageNo,Integer pageSize,String org_code,String upload_no,String if_upload,String area){
        Page page = new Page(pageNo,pageSize);
        QueryWrapper qw = new QueryWrapper<MedicalCareAbnormal>();
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
        Page pageinfo = medicalCareAbnormalService.page(page, qw);
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
    public void exportSettleAbnormalData(HttpServletResponse response, String org_code, String upload_no, String if_upload, String area) throws Exception{
        QueryWrapper qw = new QueryWrapper<MedicalCareAbnormal>();
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
        List<MedicalCareAbnormal> details = medicalCareAbnormalService.list(qw);
        // 通过工具类创建writer，默认创建xls格式
        BigExcelWriter writer = (BigExcelWriter) ExcelUtil.getBigWriter();
        writer.addHeaderAlias("id", "id");
        writer.addHeaderAlias("table_name", "表名");
        writer.addHeaderAlias("org_code", "机构编码");
        writer.addHeaderAlias("org_name", "机构名称");
        writer.addHeaderAlias("area", "行政区");
        writer.addHeaderAlias("settle_time", "费用发生时间");
        writer.addHeaderAlias("mdtrt_id", "就诊id");
        writer.addHeaderAlias("account_seria_number", "记账流水号");
        writer.addHeaderAlias("setl_id", "结算id");
        writer.addHeaderAlias("nurse_code", "医疗人员编码");
        writer.addHeaderAlias("nurse_name", "医疗人员姓名");
        writer.addHeaderAlias("content", "整改后编码");
        writer.addHeaderAlias("appeal_reason", "备注");
        writer.addHeaderAlias("insured_persons_no", "参保人编号");

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
        QueryWrapper qw = new QueryWrapper<MedicalCareAbnormal>();
        qw.groupBy("upload_no");
        qw.select("upload_no");
        qw.orderByDesc("upload_no");
        List<String> list = medicalCareAbnormalService.listObjs(qw);
        return ResultInfo.success(list);
    }




    /**
    *医保导入-医疗人员
    * Author wzn
    * Date 2022/10/20 14:42
    */
    @RequestMapping("/importData")
    public ResultInfo importData(MultipartFile file) {
        medicalCareAbnormalService.importData(file);
        return ResultInfo.success();
    }



    /**
     *医药机构导入-医疗人员
     * Author wzn
     * Date 2022/9/29 15:07
     */
    @RequestMapping("/importData2")
    public ResultInfo importData2(MultipartFile file) {
        List<MedicalCareAbnormal> settleAbnormalList = medicalCareAbnormalService.importData2(file);
        return ResultInfo.success();
    }
}
