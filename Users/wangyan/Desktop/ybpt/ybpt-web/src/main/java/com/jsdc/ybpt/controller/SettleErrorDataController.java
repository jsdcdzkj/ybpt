package com.jsdc.ybpt.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.abnormal.SettleErrorData;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.service.SettleErrorDataService;
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
@RequestMapping("settleErrorData")
public class SettleErrorDataController {
    @Autowired
    private SettleErrorDataService settleErrorDataService ;


    @Autowired
    private SysUserService sysUserService;

    /**
    * 导入
    * Author wy
    */
    @RequestMapping("/importData")
    public ResultInfo importData(MultipartFile file) {
        return ResultInfo.success(settleErrorDataService.importData(file));
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
    @RequestMapping("/getSettleErrorDataList")
    public ResultInfo getSettleErrorDataList(Integer pageNo,Integer pageSize,String org_code,String upload_no,String if_upload,String area){
        Page page = new Page(pageNo,pageSize);
        QueryWrapper qw = new QueryWrapper<SettleErrorData>();
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

        Page pageinfo = settleErrorDataService.page(page, qw);
        return ResultInfo.success(pageinfo);
    }


    /**
     * 导出徐州异常数据
     * @param response
     * @param org_code 机构编码
     * @param upload_no 上传批次
     * @throws Exception
     */
    @RequestMapping("/exportSettleErrorDataData")
    public void exportSettleErrorDataData(HttpServletResponse response,String org_code,String upload_no,String if_upload,String area) throws Exception{
        QueryWrapper qw = new QueryWrapper<SettleErrorData>();
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
        List<SettleErrorData> details = settleErrorDataService.list(qw);
        // 通过工具类创建writer，默认创建xls格式
        BigExcelWriter writer = (BigExcelWriter) ExcelUtil.getBigWriter();
        writer.addHeaderAlias("serial_number", "流水号");
        writer.addHeaderAlias("org_code", "机构编码");
        writer.addHeaderAlias("org_name", "机构名称");
        writer.addHeaderAlias("quantity", "数量");
        writer.addHeaderAlias("price", "单价");
        writer.addHeaderAlias("fixmedins_code", "国家码");
        writer.addHeaderAlias("drug_name", "药品名称");
        writer.addHeaderAlias("upload_time", "上传时间");
        writer.addHeaderAlias("upload_no", "上传批次");
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
        QueryWrapper qw = new QueryWrapper<SettleErrorData>();
        qw.groupBy("upload_no");
        qw.select("upload_no");
        qw.orderByDesc("upload_no");
        List<String> list = settleErrorDataService.listObjs(qw);
        return ResultInfo.success(list);
    }


}
