package com.jsdc.ybpt.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jsdc.ybpt.mapper.AutonomousMedicalMapper;
import com.jsdc.ybpt.mapper.EmployingInfoMapper;
import com.jsdc.ybpt.model.FileInfo;
import com.jsdc.ybpt.model_check.CivilworkerInfo;
import com.jsdc.ybpt.model_check.EmployingInfo;
import com.jsdc.ybpt.model_check.OrganizationInfo;
import com.jsdc.ybpt.model_check.PersonSubscribeRecord;
import com.jsdc.ybpt.service.CivilworkerInfoService;
import com.jsdc.ybpt.service.EmpSubscribeRecordService;
import com.jsdc.ybpt.service.OrganizationInfoService;
import com.jsdc.ybpt.service.PersonSubscribeRecordService;
import com.jsdc.ybpt.service.notice.NoticeService;
import com.jsdc.ybpt.util.IdCardNumberMethod;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.vo.EmpSubscribeRecordVo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/personSubscribeRecord")
public class PersonSubscribeRecordController {
    @Autowired
    private PersonSubscribeRecordService service;
    @Autowired
    private EmpSubscribeRecordService empSubscribeRecordService;

    @Autowired
    private CivilworkerInfoService civilworkerInfoService;
    @Autowired
    private AutonomousMedicalMapper autonomousMedicalMapper;
    @Autowired
    private EmployingInfoMapper employingInfoMapper;
    @Autowired
    private OrganizationInfoService organizationInfoService;

    @Autowired
    private NoticeService noticeService;

    @RequestMapping("/toApplyPersonSubscribeRecord")
    public String toApplyPersonSubscribeRecord(Model model, @RequestParam(name = "cardId") String cardId) {
        model.addAttribute("decryptedCardId", cardId);
        Integer totalCount = this.noticeService.getTotalCount(cardId);
        model.addAttribute("totalCount", totalCount);
        return "applyPersonSubscribeRecord";
    }

    @RequestMapping("/apply")
    public String applyPersonSubscribeRecordPage() {
        return "applyPersonSubscribeRecord";
    }


    @RequestMapping("/psrList")
    public String psrList(@RequestParam(name = "cardId") String cardId, ModelMap model) {
        model.addAttribute("decryptedCardId",cardId);
        Integer totalCount = this.noticeService.getTotalCount(cardId);
        model.addAttribute("totalCount", totalCount);
        return "psrList";
    }

    /**
     * @Description: 条件查询预约记录列表 预约进度： 待体检:0 , 已体检:1，已过期:2， 已上传报告:3  预约记录列表
     * @param: [personSubscribeSchedule]
     * @return: com.jsdc.ybpt.vo.ResultInfo
     * @author: yc
     */
    @RequestMapping("/getPersonSubscribeRecordList/{type}")
    @ResponseBody
    public ResultInfo getPersonSubscribeRecordList(@PathVariable(name = "type") String type, @RequestParam(name = "cardId") String cardId) {
        List<PersonSubscribeRecord> personSubscribeRecordList = service.findPersonSubscribeRecordByCivilWorker(StringUtils.trim(cardId), type);
        List<PersonSubscribeRecord> collect = personSubscribeRecordList.stream().map(record -> {
            switch (record.getSched()) {
                case "0": {
                    record.setSched("待体检");
                    return record;
                }
                case "1": {
                    record.setSched("已体检");
                    return record;
                }
                case "2": {
                    record.setSched("已过期");
                    return record;
                }
                case "3": {
                    record.setSched("已上传报告");
                    return record;
                }
                case "4": {
                    record.setSched("已撤销");
                    return record;
                }
            }
            return record;
        }).collect(Collectors.toList());
        return ResultInfo.success(collect);
    }


    /**
     * @Description: 提交预约记录
     * @param: [pid, uid, wids[],year,money,apply_date,org_id,org_name]
     * @return: com.jsdc.ybpt.vo.ResultInfo
     * @author: yc
     */
    @PostMapping("/save")
    @ResponseBody
    public ResultInfo save(@RequestBody EmpSubscribeRecordVo vo) {
        if (StringUtils.isEmpty(vo.getYear())) {
            return ResultInfo.error("year补不能为空");
        }
        if (StringUtils.isEmpty(vo.getApply_date())) {
            return ResultInfo.error("applyDate不能为空");
        }
        if (StringUtils.isEmpty(vo.getPid())) {
            return ResultInfo.error("packInfoId不能为空");
        }
        if (StringUtils.isEmpty(vo.getOrg_name())) {
            return ResultInfo.error("org_name不能为空");
        }
        if (StringUtils.isEmpty(vo.getOrg_id())) {
            return ResultInfo.error("org_id名称不能为空");
        }

        if (!StringUtils.checkPhone(vo.getPhone())) {
            return ResultInfo.error("手机号非法");
        }

        if (!(IdCardNumberMethod.checkCardId(vo.getCardId()))) {
            return ResultInfo.error("身份证号有误");
        }

        CivilworkerInfo civilworkerInfo = this.civilworkerInfoService.selectQualifiedCivil(vo.getCardId());
        vo.setUid(civilworkerInfo.getEmp_code());

        //异地安置体检年度
        boolean flag = civilworkerInfoService.isRelocationYear(civilworkerInfo,vo.getYear());
        if(flag){
            return ResultInfo.error("此公务员在"+vo.getYear()+"年度申请已申请异地安置");
        }
        Integer count=this.autonomousMedicalMapper.findAutonomousMedicalCount(vo.getUid());
        if (count > 0) {
            return ResultInfo.error("单位已经申请了自主体检！");
        }

        LambdaQueryWrapper<EmployingInfo> employingInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        employingInfoLambdaQueryWrapper.eq(EmployingInfo::getEmp_no,vo.getUid()).eq(EmployingInfo::getIs_del,"0");
        EmployingInfo employingInfo=employingInfoMapper.selectOne(employingInfoLambdaQueryWrapper);
        if (org.springframework.util.StringUtils.isEmpty(employingInfo.getParentOrgCode())){
            return ResultInfo.error("本地区没有开通权限！");
        }
        // 查询个人预约记录，如果在该年已经提交，则不允许再次提交
        if (this.service.countPsrNum(vo) > 0) {
            return ResultInfo.error("已经在" + vo.getYear() + "年提交, 请勿重复提交");
        }

        OrganizationInfo organizationInfo = organizationInfoService.getEntityByOrgMedicalInsuranceNum(vo.getOrg_id());
        if(!employingInfo.getParentOrgCode().equals(organizationInfo.getOrg_code())){
            return ResultInfo.error("公务员和体检机构不在同一统筹区");
        }
        if (StringUtils.isNotEmpty(vo.getYear())){
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(new Date());
            int i = calendar.get(Calendar.YEAR);
            if (!(String.valueOf( i )).equals(vo.getYear())){
                return ResultInfo.error("选择年份必须和当前年份一致");
            }
        }

        return this.empSubscribeRecordService.saveEmpSubscribeRecordForCivilPersonally(vo);
    }

    @PostMapping("/backOut")
    @ResponseBody
    public ResultInfo backOut(@RequestParam(name = "personSubscribeRecordId") String personSubscribeRecordId,
                              @RequestParam(name = "cardId") String cardId) {
        CivilworkerInfo civilworkerInfo = this.civilworkerInfoService.selectQualifiedCivil(cardId);
        return this.service.backOutPersonSubscribeRecordPersonal(civilworkerInfo, personSubscribeRecordId);
    }


    /**
     * @param model
     * @param psrId
     * @return String
     * @Description 跳转到报告页面
     */
    @RequestMapping(value = "/report")
    public String report(Model model, @RequestParam String psrId, @RequestParam String cardId) {
        model.addAttribute("decryptedCardId", cardId);
        model.addAttribute("psrId", psrId);

        PersonSubscribeRecord psr = this.service.getById(psrId);

        FileInfo reportFile = null;
        if (psr.getSched() != null && "3".equals(psr.getSched())) {
            // reportFile = service.getPhysicalExaminationReportByPsrId(psrId);
            reportFile = service.getFileServerExaminationReportByPsrId(psrId);

        }
        if(null != reportFile){
            model.addAttribute("reportUrl", "https://tj.xzybzx.org.cn/file" + reportFile.getFileUrl());
            model.addAttribute("reportFileUrl", "https://tj.xzybzx.org.cn/file" + reportFile.getFileUrl());
            model.addAttribute("downloadFileUrl", "https://tj.xzybzx.org.cn/fileRead" + reportFile.getFileUrl()+"?download=0");
        }

        return "report";
    }

    @Deprecated //废弃
    @RequestMapping("/reportStream")
    public void downloadReport(@RequestParam String psrId, HttpServletResponse response) throws Exception {
        FileInfo reportFile = this.service.getPhysicalExaminationReportByPsrId(psrId);
        if (reportFile == null) {
            return;
        }

        // 设置响应头，告诉浏览器返回的是一个PDF文件
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=file.pdf");

        // 获取响应输出流
        OutputStream  outputStream = null;


        try {
            outputStream = response.getOutputStream();
            String urlStr = "https://tj.xzybzx.org.cn/file"+reportFile.getFileUrl();

            URL url = new URL(urlStr);
            HttpURLConnection httpUrl = (HttpURLConnection)url.openConnection();

            // 获取输入流
            BufferedInputStream  inputStream = new BufferedInputStream(httpUrl.getInputStream());

            // 将PDF文件内容写入响应流
            // 缓冲区大小，可以根据需要调整
            byte[] buffer = new byte[1024];
            int bytesRead;

            // 从BufferedInputStream读取字节，并写入响应输出流
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            // 关闭输入流
            inputStream.close();
        } catch (IOException e) {
            // 处理异常
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            // 关闭响应输出流
            if(null != outputStream) {
                outputStream.close();
            }
        }
    }

    @RequestMapping(value = "getReport")
    @ResponseBody
    public ResultInfo getReport(@RequestParam String psrId, @RequestParam String cardId) {
        PersonSubscribeRecord psr = this.service.getById(psrId);
        if (psr == null) {
            return ResultInfo.error("没有该预约记录");
        }

        FileInfo reportFile = null;
        if (psr.getSched() != null && "3".equals(psr.getSched())) {
           // reportFile = service.getPhysicalExaminationReportByPsrId(psrId);
            reportFile = service.getFileServerExaminationReportByPsrId(psrId);
        } else {
            return ResultInfo.error("预约状态错误");
        }

        if (reportFile == null) {
            return ResultInfo.error("体检报告为空");
        }

        return ResultInfo.success(reportFile.getFileUrl());
    }
}
