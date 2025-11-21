package com.jsdc.ybpt.controller;


import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.model_query.AccountDetails;
import com.jsdc.ybpt.model_query.EmpPayInfo;
import com.jsdc.ybpt.model_query.ResidentPayinfo;
import com.jsdc.ybpt.model_query.StaffPayinfo;
import com.jsdc.ybpt.model_query.settlement.MzSettlement;
import com.jsdc.ybpt.model_query.settlement.MzSettlementDetails;
import com.jsdc.ybpt.model_query.settlement.PersonalSettlement;
import com.jsdc.ybpt.service.PersonInfoService;
import com.jsdc.ybpt.vo.PersonalSettlementVo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@RequestMapping("/personInfo")
public class PersonInfoController {
  @Autowired
  private PersonInfoService personInfoService;

  /**
   * 居民信息查询
   * @param pageNo
   * @param pageSize
   * @param certno
   * @param cardType
   * @return
   */
  @RequestMapping("/selectPersonInfo")
  public ResultInfo selectPersonInfo(Integer pageNo,Integer pageSize,String certno,String cardType,String psn_name,String psn_no,String mob){
    return ResultInfo.success(personInfoService.selectPersonInfo(pageNo,pageSize, certno, cardType, psn_name, psn_no, mob));
  }

  /**
   * 居民信息详情
   * @param cardno
   * @return
   */
  @RequestMapping("/getPersonInfoByCarno")
  public ResultInfo getPersonInfoByCarno(String cardno){
    return ResultInfo.success(personInfoService.getPersonInfoByCarno(cardno));
  }

  /**
   * 单位缴费明细
   * @param empPayInfo
   * @return
   */
  @RequestMapping("/getEmpInfo")
  public ResultInfo getEmpInfo(@RequestBody EmpPayInfo empPayInfo){
    return ResultInfo.success(personInfoService.getEmpInfo(empPayInfo));
  }

  /**
   * 职工缴费明细
   * @param staffPayinfo
   * @return
   */
  @RequestMapping("/getStaffPayinfo")
  public ResultInfo getStaffPayinfo(@RequestBody StaffPayinfo staffPayinfo){
    return ResultInfo.success(personInfoService.getStaffPayinfo(staffPayinfo));
  }

  /**
   * 居民缴费明细
   * @param residentPayinfo
   * @return
   */
  @RequestMapping("/getResidentPayinfo")
  public ResultInfo getResidentPayinfo(@RequestBody ResidentPayinfo residentPayinfo){
    return ResultInfo.success(personInfoService.getResidentPayinfo(residentPayinfo));
  }

  /**
   * 个人收入明细
   * @param accountDetails
   * @return
   */
  @RequestMapping("/getAccountDetails")
  public ResultInfo getAccountDetails(@RequestBody AccountDetails accountDetails){
    return ResultInfo.success(personInfoService.getAccountDetails(accountDetails));
  }

  @RequestMapping("/selectPersonalSettlement_page")
  public ResultInfo selectPersonalSettlement_page(@RequestBody PersonalSettlementVo personalSettlementVo){
    Page<PersonalSettlement> page = personInfoService.selectPersonalSettlement_page(personalSettlementVo);
    return ResultInfo.success(page);
  }
  @RequestMapping("/selectPersonalSettlement_excel")
  public void selectPersonalSettlement_excel(HttpServletResponse response, @RequestBody PersonalSettlementVo personalSettlementVo) throws Exception{
    List<PersonalSettlement> details = personInfoService.selectPersonalSettlement_excel(personalSettlementVo);
    // 通过工具类创建writer，默认创建xls格式
    ExcelWriter writer = ExcelUtil.getBigWriter();
    writer.addHeaderAlias("fixmedins_code", "机构编号");
    writer.addHeaderAlias("fixmedins_name", "机构名称");
    writer.addHeaderAlias("psn_name", "姓名");
    writer.addHeaderAlias("certno", "身份证号");
    writer.addHeaderAlias("med_type", "医疗类别");
    writer.addHeaderAlias("setl_type", "结算类别");
    writer.addHeaderAlias("setl_time", "结算时间");
    writer.addHeaderAlias("medfee_sumamt", "医疗费总额");
    writer.addHeaderAlias("hifp_pay", "统筹基金支出");
    writer.addHeaderAlias("hifob_pay", "大额医疗补助基金支出");
    writer.addHeaderAlias("cvlserv_pay", "公务员医疗补助资金支出");
    writer.addHeaderAlias("acct_pay", "个人账户支出");
    writer.addHeaderAlias("cash_payamt", "现金支付金额");
    writer.addHeaderAlias("ownpay_hosp_part", "医院垫付");
    writer.addHeaderAlias("maf_pay", "医疗救助基金支出");
    writer.addHeaderAlias("hifes_pay", "补充医疗保险基金支出");
    writer.addHeaderAlias("hifmi_pay", "大病补充医疗保险基金支出");
    writer.addHeaderAlias("othfund_pay", "其它基金支付");
    writer.addHeaderAlias("dise_no", "病种编号");
    writer.addHeaderAlias("dise_name", "病种名称");
    writer.addHeaderAlias("fulamt_ownpay_amt", "全自费金额");
    writer.addHeaderAlias("overlmt_selfpay", "超限价自费费用");
    writer.addHeaderAlias("preselfpay_amt", "先行自付金额");
    writer.addHeaderAlias("dedc_std", "起付标准");
    writer.addHeaderAlias("pay_loc", "支付地点类别");
    writer.addHeaderAlias("pool_prop_selfpay", "基本医疗统筹支付比例");
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


  @RequestMapping("/selectMzSettlement_page")
  public ResultInfo selectMzSettlement_page(@RequestBody PersonalSettlementVo personalSettlementVo){
    Page<MzSettlement> page = personInfoService.selectMzSettlement_page(personalSettlementVo);
    return ResultInfo.success(page);
  }
  @RequestMapping("/selectMzSettlement_excel")
  public void selectMzSettlement_excel(HttpServletResponse response, @RequestBody PersonalSettlementVo personalSettlementVo) throws Exception{
    List<MzSettlement> details = personInfoService.selectMzSettlement_excel(personalSettlementVo);
    // 通过工具类创建writer，默认创建xls格式
    ExcelWriter writer = ExcelUtil.getBigWriter();
    writer.setSheet("门诊结算信息");
    writer.addHeaderAlias("psn_name", "姓名");
    writer.addHeaderAlias("certno", "身份证号");
    writer.addHeaderAlias("year", "年度");
    writer.addHeaderAlias("insutype", "险种类型");
    writer.addHeaderAlias("med_type", "医疗类别");
    writer.addHeaderAlias("insu_admdvs", "医保区划");
    writer.addHeaderAlias("pay_loc", "支付地点类别");
    writer.addHeaderAlias("fixmedins_code", "定点医药机构代码");
    writer.addHeaderAlias("fixmedins_name", "定点医药机构名称");
    writer.addHeaderAlias("hosp_lv", "医药机等级");
    writer.addHeaderAlias("begndate", "就医开始时间");
    writer.addHeaderAlias("enddate", "就医结束时间");
    writer.addHeaderAlias("setl_time", "结算时间");
    writer.addHeaderAlias("medfee_sumamt", "医疗费总额");
    writer.addHeaderAlias("fulamt_ownpay_amt", "全自费金额");
    writer.addHeaderAlias("overlmt_selfpay", "超限价自付金额");
    writer.addHeaderAlias("preselfpay_amt", "先行自付金额");
    writer.addHeaderAlias("inscp_amt", "符合范围金额");
    writer.addHeaderAlias("dedc_std", "起付标准");
    writer.addHeaderAlias("crt_dedc", "本次起付线");
    writer.addHeaderAlias("act_pay_dedc", "实际支付起付线");
    writer.addHeaderAlias("hifp_pay", "基本医疗基金支出");
    writer.addHeaderAlias("pool_prop_selfpay", "基本医疗统筹支付比例");
    writer.addHeaderAlias("cvlserv_pay", "公务员医疗补助基金支出");
    writer.addHeaderAlias("hifmi_pay", "大病补充医疗保险基金支出");
    writer.addHeaderAlias("hifob_pay", "大额医疗补助基金支出");
    writer.addHeaderAlias("hifdm_pay", "伤残人员医疗保障基金支出");
    writer.addHeaderAlias("maf_pay", "医疗救助基金支出");
    writer.addHeaderAlias("othfund_pay", "其他基金支出");
    writer.addHeaderAlias("fund_pay_sumamt", "基金支付总额");
    writer.addHeaderAlias("psn_pay", "个人支付总额");
    writer.addHeaderAlias("refd_setl_flag", "退费结算标志");
    writer.addHeaderAlias("crter_name", "创建人");
    writer.addHeaderAlias("opter_name", "经办人");
    writer.addHeaderAlias("opt_time", "经办时间");
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

  @RequestMapping("/selectMzSettleDetails")
  public ResultInfo selectMzSettleDetails(String setlId){
    return ResultInfo.success(personInfoService.selectMzSettleDetails(setlId));
  }

  @RequestMapping("/exportMzSettleDetails")
  public void sexportMzSettleDetails(HttpServletResponse response,String setlId) throws Exception{
    List<MzSettlementDetails> details = personInfoService.selectMzSettleDetails(setlId);
    // 通过工具类创建writer，默认创建xls格式
    ExcelWriter writer = ExcelUtil.getBigWriter();
    writer.addHeaderAlias("fee_ocur_time", "费用发生时间");
    writer.addHeaderAlias("rx_drord_no", "处方/医嘱号");
    writer.addHeaderAlias("hilist_code", "医保目录编码");
    writer.addHeaderAlias("hilist_name", "医保目录名称");
    writer.addHeaderAlias("medins_list_name", "医疗机构目录名称");
    writer.addHeaderAlias("list_type", "目录类别");
    writer.addHeaderAlias("med_chrgitm_type", "医疗收费项目类别");
    writer.addHeaderAlias("pric", "单价");
    writer.addHeaderAlias("cnt", "数量");
    writer.addHeaderAlias("det_item_fee_sumamt", "明细项目费用总额");
    writer.addHeaderAlias("fulamt_ownpay_amt", "全自费金额");
    writer.addHeaderAlias("pric_uplmt_amt", "定价上限金额");
    writer.addHeaderAlias("overlmt_selfpay", "超限价自费费用");
    writer.addHeaderAlias("selfpay_prop", "自付比例");
    writer.addHeaderAlias("preselfpay_amt", "先行自付金额");
    writer.addHeaderAlias("reim_prop", "报销比例");
    writer.addHeaderAlias("inscp_amt", "符合范围金额");
    writer.addHeaderAlias("cvlserv_bedfee_amt", "公务员床位费金额");
    writer.addHeaderAlias("medins_disc_amt", "医院减免金额");
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
}
