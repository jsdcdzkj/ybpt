package com.jsdc.ybpt.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.abnormal.CheckSuspicions;
import com.jsdc.ybpt.model.FileInfo;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.service.CheckSuspicionsService;
import com.jsdc.ybpt.service.FileInfoService;
import com.jsdc.ybpt.service.SysUserService;
import com.jsdc.ybpt.util.FastDfs.FastDfsParams;
import com.jsdc.ybpt.util.FastDfs.FastDfsUtil;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/checkSuspicions")
public class CheckSuspicionsController {

    @Autowired
    private CheckSuspicionsService checkSuspicionsService ;

    @Autowired
    private SysUserService sysUserService ;
    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private FastDfsUtil fastDfsUtil;
    @Value("${fastDfs_downurl}")
    private  String fastDfs_downurl;

    /**
    *稽查数据医保导入
    * Author wzn
    * Date 2022/11/15 16:53
    */


    @RequestMapping("/importData")
    public ResultInfo importData(MultipartFile file) {
        checkSuspicionsService.importData(file);
        return ResultInfo.success();
    }



    /**
    *稽查数据导出
    * Author wzn
    * Date 2022/11/15 16:54
    */
    @RequestMapping("/exportCheckSuspicionsData")
    public void exportSettleAbnormalData(HttpServletResponse response, String fixmedins_code, String upload_no, String status, String insu_admdvs_name) throws Exception{
        QueryWrapper qw = new QueryWrapper<CheckSuspicions>();
        qw.eq("upload_no",upload_no);
        SysUser sysUser = sysUserService.getUser();
        if("1".equals(sysUser.getUser_type())){//行政管理
            if(!StrUtil.hasEmpty(fixmedins_code)){
                qw.eq("fixmedins_code",fixmedins_code);
            }
        }else if("2".equals(sysUser.getUser_type()) || "3".equals(sysUser.getUser_type())){
            qw.eq("fixmedins_code",sysUser.getOrg_code());
        }
        if(!StrUtil.hasEmpty(status)){
            qw.eq("status",status);
        }
        if(!StrUtil.hasEmpty(insu_admdvs_name)){
            qw.eq("insu_admdvs_name",insu_admdvs_name);
        }
        List<CheckSuspicions> details = checkSuspicionsService.list(qw);

        if(CollectionUtils.isNotEmpty(details)){
            for(CheckSuspicions c:details){
                if("0".equals(c.getStatus())){
                    c.setStatus("待审核");
                }else if("1".equals(c.getStatus())){
                    c.setStatus("审核通过");
                }else if("2".equals(c.getStatus())){
                    c.setStatus("审核驳回");
                }

                if("1".equals(c.getGend())){
                    c.setGend("男");
                }else if("2".equals(c.getGend())){
                    c.setGend("女");
                }

                if("0".equals(c.getLmt_used_flag())){
                    c.setLmt_used_flag("否");
                }else if("1".equals(c.getLmt_used_flag())){
                    c.setLmt_used_flag("是");
                }

            }
        }
        // 通过工具类创建writer，默认创建xls格式
        BigExcelWriter writer = (BigExcelWriter) ExcelUtil.getBigWriter();
        writer.addHeaderAlias("rid", "Rid");
        writer.addHeaderAlias("psn_name", "人员姓名");
        writer.addHeaderAlias("cert_no", "身份证号");
        writer.addHeaderAlias("gend", "性别");
        writer.addHeaderAlias("age", "年龄");
        writer.addHeaderAlias("setl_time", "结算时间");
        writer.addHeaderAlias("fixmedins_name", "定点医药机构名称");
        writer.addHeaderAlias("fixmedins_code", "定点医药机构编号");
        writer.addHeaderAlias("genericNameOfTheDrug", "药品通用名名称");
        writer.addHeaderAlias("drugProvinceCode", "药品省编码");
        writer.addHeaderAlias("nationalDrugCode", "国家药品码");
        writer.addHeaderAlias("mdtrt_id", "就诊Id");
        writer.addHeaderAlias("setl_id", "结算Id");
        writer.addHeaderAlias("pric", "单价");
        writer.addHeaderAlias("cnt", "数量");
        writer.addHeaderAlias("det_item_fee_sumamt", "明细项目费用总额");
        writer.addHeaderAlias("psn_selfpay_amt", "个人自付比例");
        writer.addHeaderAlias("lmt_used_flag", "限制使用标志");
        writer.addHeaderAlias("med_type", "医疗类别");
        writer.addHeaderAlias("medfee_sumamt", "医疗费总额");
        writer.addHeaderAlias("hifp_pay", "统筹基金支出");
        writer.addHeaderAlias("pool_prop_selfpay", "基本医疗统筹支付比例");
        writer.addHeaderAlias("cvlserv_pay", "公务员医疗补助资金支出");
        writer.addHeaderAlias("hifes_pay", "补充医疗保险基金支出");
        writer.addHeaderAlias("hifmi_pay", "大病补充医疗保险基金支出");
        writer.addHeaderAlias("hifob_pay", "大额医疗补助基金支出");
        writer.addHeaderAlias("maf_pay", "医疗救助基金支出");
        writer.addHeaderAlias("acct_pay", "个人账户支出");
        writer.addHeaderAlias("cash_payamt", "现金支付金额");
        writer.addHeaderAlias("common_name_code", "通用名编码");
        writer.addHeaderAlias("lmt_usescp", "限制使用范围");
        writer.addHeaderAlias("reg", "REG");
        writer.addHeaderAlias("psn_type", "人员类别");
        writer.addHeaderAlias("timeOfPrescription", "处方时间");
        writer.addHeaderAlias("dept_code", "所属科室");
        writer.addHeaderAlias("inpatientWard", "病区");
        writer.addHeaderAlias("doctorSCode", "医生编码");
        writer.addHeaderAlias("nameOfDoctor", "医生姓名");
        writer.addHeaderAlias("admissionTime", "入院时间");
        writer.addHeaderAlias("numberOfAdmittedDiseases", "入院病种编码");
        writer.addHeaderAlias("admittingDiagnosis", "入院诊断");
        writer.addHeaderAlias("timeOfDischarge", "出院时间");
        writer.addHeaderAlias("numberOfDischargedDiseases", "出院病种编码");
        writer.addHeaderAlias("dischargeDiagnosis", "出院诊断");


        writer.addHeaderAlias("upload_no", "批次号");
        writer.addHeaderAlias("status", "审核状态");

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
     * 查询数据
     * @param pageNo
     * @param pageSize
     * @param org_code
     * @param upload_no
     * @param isUpload
     * @return
     */
    @RequestMapping("/getList")
    public ResultInfo getList(Integer pageNo,Integer pageSize,String org_code,String upload_no,String isUpload,String area,String status){
        Page page = new Page(pageNo,pageSize);
        QueryWrapper qw = new QueryWrapper<CheckSuspicions>();
        qw.eq("upload_no",upload_no);
        SysUser sysUser = sysUserService.getUser();
        if("1".equals(sysUser.getUser_type())){//行政管理
            if(!StrUtil.hasEmpty(org_code)){
                qw.eq("fixmedins_code",org_code);
            }
        }else if("2".equals(sysUser.getUser_type()) || "3".equals(sysUser.getUser_type())){
            qw.eq("fixmedins_code",sysUser.getOrg_code());
        }
        if(!StrUtil.hasEmpty(isUpload)){
            qw.eq("isUpload",isUpload);
        }
        if(!StrUtil.hasEmpty(area)){
            qw.eq("insu_admdvs_name",area);
        }
        if(!StrUtil.hasEmpty(status)){
            qw.eq("status",status);
        }
        Page pageInfo = checkSuspicionsService.page(page, qw);
        return ResultInfo.success(pageInfo);
    }

    /**
     * 举证审核
     * @throws Exception
     */
    @RequestMapping("/audit")
    public ResultInfo audit(@RequestBody CheckSuspicions checkSuspicions){
        return ResultInfo.success(checkSuspicions.updateById());
    }



    /**
     * 获取批次
     * @throws Exception
     */
    @RequestMapping("/getUploadNo")
    public ResultInfo getUploadNo(){
        QueryWrapper qw = new QueryWrapper<CheckSuspicions>();
        qw.groupBy("upload_no");
        qw.select("upload_no");
        qw.orderByDesc("upload_no");
        List<String> list = checkSuspicionsService.listObjs(qw);
        return ResultInfo.success(list);
    }

    /**
     * 查看
     * @throws Exception
     */
    @RequestMapping("/lookFileInfo")
    public ResultInfo look(String rid){
        Map map = checkSuspicionsService.lookFileInfo(rid);
        return ResultInfo.success(map);
    }





    @RequestMapping("/attachmentUploading")
    public ResultInfo attachmentUploading(@RequestParam(value = "picfile", required = false) List<MultipartFile> picfiles,
                                          @RequestParam(value = "rid", required = false) String rid,
                                          @RequestParam(value = "file", required = false) List<MultipartFile> files,
                                          @RequestParam(value = "describe", required = false) String describe,String oldFileIds) {
        CheckSuspicions checkSuspicions = checkSuspicionsService.getById(rid);
        //删除以前老文件
        List<FileInfo> fileInfos = fileInfoService.list(new QueryWrapper<FileInfo>().eq("bizType","1").eq("bizId",rid));
        if(fileInfos != null && fileInfos.size() > 0){
            //获取不需要删除的文件
            Map<String,String> map = new HashMap<>();
            String[] oldFileStr = null ;
            if(StringUtils.isNotEmpty(oldFileIds)){
                 oldFileStr = oldFileIds.split(",");
            }

            if(oldFileStr != null && oldFileStr.length > 0){
                for (String s : oldFileStr) {
                    StrUtil.isNotEmpty(s);
                    map.put(s,s);
                }
            }
            for (FileInfo fileInfo : fileInfos) {
                if(!map.containsKey(fileInfo.getId())){
                    ResultInfo resultInfo = fastDfsUtil.deleteFile(fileInfo);
                    System.out.println(resultInfo.getMsg());
                }
            }
        }
        if(picfiles != null && picfiles.size() > 0){
            for (MultipartFile file:picfiles) {
                if(file == null){
                    continue;
                }
                FastDfsParams params = new FastDfsParams("checkSuspicions",file,"1",checkSuspicions.getRid());
                ResultInfo resultInfo = fastDfsUtil.uploadPicFile(params);
                if(resultInfo.getCode() != 0){
                    return resultInfo;
                }
            }
        }
        if(files != null && files.size() > 0){
            for (MultipartFile file:files) {
                if(file == null){
                    continue;
                }
                FastDfsParams params = new FastDfsParams("checkSuspicions",file,"1",checkSuspicions.getRid());
                ResultInfo resultInfo = fastDfsUtil.uploadFile(params);
                if(resultInfo.getCode() != 0){
                    return resultInfo;
                }
            }
        }
        checkSuspicions.setIsUpload("1");
        checkSuspicions.setDescribe(describe);
        checkSuspicions.updateById();
        return ResultInfo.success();
    }



    @RequestMapping(value = "/testUpload")
    public String testUpload(){
        //文件地址
        File file = new File("F:\\itss资料\\成熟度一级要求.xlsx");
        //声明参数集合
        HashMap<String, Object> paramMap = new HashMap<>();
        //文件
        paramMap.put("file", file);
        //输出
        paramMap.put("output","json");
        //自定义路径
        paramMap.put("path","image");
        //场景
        paramMap.put("scene","file");
        paramMap.put("filename","123123.xlsx");
        //上传
        String result= HttpUtil.post("http://localhost:8080/upload", paramMap);
        //输出json结果
        System.out.println(result);
        return result;
    }


    /**
     *举证证据下载
     * Author wzn
     * Date 2022/11/16 9:35
     */
    @RequestMapping(value = "/downloadOfEvidence")
    public void downloadOfEvidence(HttpServletResponse response,String rid) {
        checkSuspicionsService.downloadOfEvidence(rid,fastDfs_downurl,response);
    }

}
