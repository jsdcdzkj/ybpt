package com.jsdc.ybpt.service;


import cn.hutool.core.date.TimeInterval;
import cn.hutool.poi.excel.sax.Excel07SaxReader;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.jsdc.ybpt.abnormal.CheckSuspicions;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.FileInfoMapper;
import com.jsdc.ybpt.model.FileInfo;
import com.jsdc.ybpt.util.DateUtil;
import com.jsdc.ybpt.util.ListGroupUtil;
import com.jsdc.ybpt.util.ZipUtils;
import com.jsdc.ybpt.util.exception.CustomException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class CheckSuspicionsService extends BaseService<CheckSuspicions> {

    @Autowired
    private ExcuteService excuteService;

    @Autowired
    private FileInfoMapper fileInfoMapper;


    private static final org.slf4j.Logger log = LoggerFactory.getLogger(MedicalCareAbnormalService.class);


    /**
     * excel大文件导入  -医保
     * Author wzn
     * Date 2022/9/29 10:04
     */
    public List<CheckSuspicions> importData(MultipartFile file) {
        List<CheckSuspicions> checkSuspicionsList = new ArrayList<>();
        try {
            InputStream in = file.getInputStream();
            StringBuilder sb = null;
            TimeInterval timer = cn.hutool.core.date.DateUtil.timer();
            //ExcelUtil快速读取
//            ExcelUtil.readBySax(in, 0, createRowHandler(settleAbnormalList));
            //构建对象读取
            Excel07SaxReader reader = new Excel07SaxReader(createRowHandler(checkSuspicionsList));
            reader.read(in, 0);

            System.out.println("解析EXCEL用时：" + timer.intervalRestart());
            List<List<CheckSuspicions>> lists = ListGroupUtil.groupListByQuantity(checkSuspicionsList, 100);
            if (CollectionUtils.isEmpty(lists)) {
                throw new CustomException("未能成功解析Excel!");
            }
            System.out.println("List分组用时：" + timer.intervalRestart());
            for (int i = 0; i < lists.size(); i++) {
                sb = new StringBuilder();
                sb.append("INSERT  all into check_suspicions (\"RID\", \"PSN_NAME\", \"CERT_NO\", \"GEND\", \"AGE\", \"SETL_TIME\", \"FIXMEDINS_NAME\", \"FIXMEDINS_CODE\", \"GENERICNAMEOFTHEDRUG\", \"DRUGPROVINCECODE\", \"NATIONALDRUGCODE\", \"MDTRT_ID\", \"SETL_ID\", \"PRIC\", \"CNT\", \"DET_ITEM_FEE_SUMAMT\", \"PSN_SELFPAY_AMT\", \"LMT_USED_FLAG\", \"MED_TYPE\", \"MEDFEE_SUMAMT\", \"HIFP_PAY\", \"POOL_PROP_SELFPAY\", \"CVLSERV_PAY\", \"HIFES_PAY\", \"HIFMI_PAY\", \"HIFOB_PAY\", \"MAF_PAY\", \"ACCT_PAY\", \"CASH_PAYAMT\", \"INSU_ADMDVS_NAME\", \"COMMON_NAME_CODE\", \"LMT_USESCP\", \"REG\",PSN_TYPE,TIMEOFPRESCRIPTION,DEPT_CODE,INPATIENTWARD,DOCTORSCODE,NAMEOFDOCTOR,ADMISSIONTIME,NUMBEROFADMITTEDDISEASES,ADMITTINGDIAGNOSIS,TIMEOFDISCHARGE,NUMBEROFDISCHARGEDDISEASES,DISCHARGEDIAGNOSIS, \"STATUS\",UPLOAD_NO, \"UPLOAD_TIME\" ) VALUES  (");
                for (int k = 0; k < lists.get(i).size(); k++) {
                    if (k > 0) {
                        sb.append(" INTO check_suspicions (\"RID\", \"PSN_NAME\", \"CERT_NO\", \"GEND\", \"AGE\", \"SETL_TIME\", \"FIXMEDINS_NAME\", \"FIXMEDINS_CODE\", \"GENERICNAMEOFTHEDRUG\", \"DRUGPROVINCECODE\", \"NATIONALDRUGCODE\", \"MDTRT_ID\", \"SETL_ID\", \"PRIC\", \"CNT\", \"DET_ITEM_FEE_SUMAMT\", \"PSN_SELFPAY_AMT\", \"LMT_USED_FLAG\", \"MED_TYPE\", \"MEDFEE_SUMAMT\", \"HIFP_PAY\", \"POOL_PROP_SELFPAY\", \"CVLSERV_PAY\", \"HIFES_PAY\", \"HIFMI_PAY\", \"HIFOB_PAY\", \"MAF_PAY\", \"ACCT_PAY\", \"CASH_PAYAMT\", \"INSU_ADMDVS_NAME\", \"COMMON_NAME_CODE\", \"LMT_USESCP\", \"REG\",PSN_TYPE,TIMEOFPRESCRIPTION,DEPT_CODE,INPATIENTWARD,DOCTORSCODE,NAMEOFDOCTOR,ADMISSIONTIME,NUMBEROFADMITTEDDISEASES,ADMITTINGDIAGNOSIS,TIMEOFDISCHARGE,NUMBEROFDISCHARGEDDISEASES,DISCHARGEDIAGNOSIS, \"STATUS\",UPLOAD_NO, \"UPLOAD_TIME\" ) VALUES  (");
                    }
                    sb.append("'" + lists.get(i).get(k).getRid() + "', ");
                    sb.append("'" + lists.get(i).get(k).getPsn_name() + "', ");
                    sb.append("'" + lists.get(i).get(k).getCert_no() + "', ");
                    sb.append("'" + lists.get(i).get(k).getGend() + "', ");
                    sb.append("'" + lists.get(i).get(k).getAge() + "', ");
                    sb.append("'" + lists.get(i).get(k).getSetl_time() + "', ");
                    sb.append("'" + lists.get(i).get(k).getFixmedins_name() + "', ");
                    sb.append("'" + lists.get(i).get(k).getFixmedins_code() + "', ");
                    sb.append("'" + lists.get(i).get(k).getGenericNameOfTheDrug() + "', ");
                    sb.append("'" + lists.get(i).get(k).getDrugProvinceCode() + "', ");
                    sb.append("'" + lists.get(i).get(k).getNationalDrugCode() + "', ");
                    sb.append("'" + lists.get(i).get(k).getMdtrt_id() + "', ");
                    sb.append("'" + lists.get(i).get(k).getSetl_id() + "', ");
                    sb.append("'" + lists.get(i).get(k).getPric() + "', ");
                    sb.append("'" + lists.get(i).get(k).getCnt() + "', ");
                    sb.append("'" + lists.get(i).get(k).getDet_item_fee_sumamt() + "', ");
                    sb.append("'" + lists.get(i).get(k).getPsn_selfpay_amt() + "', ");
                    sb.append("'" + lists.get(i).get(k).getLmt_used_flag() + "', ");
                    sb.append("'" + lists.get(i).get(k).getMed_type() + "', ");
                    sb.append("'" + lists.get(i).get(k).getMedfee_sumamt() + "', ");
                    sb.append("'" + lists.get(i).get(k).getHifp_pay() + "', ");
                    sb.append("'" + lists.get(i).get(k).getPool_prop_selfpay() + "', ");
                    sb.append("'" + lists.get(i).get(k).getCvlserv_pay() + "', ");
                    sb.append("'" + lists.get(i).get(k).getHifes_pay() + "', ");
                    sb.append("'" + lists.get(i).get(k).getHifmi_pay() + "', ");
                    sb.append("'" + lists.get(i).get(k).getHifob_pay() + "', ");
                    sb.append("'" + lists.get(i).get(k).getMaf_pay() + "', ");
                    sb.append("'" + lists.get(i).get(k).getAcct_pay() + "', ");
                    sb.append("'" + lists.get(i).get(k).getCash_payamt() + "', ");
                    sb.append("'" + lists.get(i).get(k).getInsu_admdvs_name() + "', ");
                    sb.append("'" + lists.get(i).get(k).getCommon_name_code() + "', ");
                    sb.append("'" + lists.get(i).get(k).getLmt_usescp() + "', ");
                    sb.append("'" + lists.get(i).get(k).getReg() + "', ");
                    sb.append("'" + lists.get(i).get(k).getPsn_type() + "', ");
                    sb.append("'" + lists.get(i).get(k).getTimeOfPrescription() + "', ");
                    sb.append("'" + lists.get(i).get(k).getDept_code() + "', ");
                    sb.append("'" + lists.get(i).get(k).getInpatientWard() + "', ");
                    sb.append("'" + lists.get(i).get(k).getDoctorSCode() + "', ");
                    sb.append("'" + lists.get(i).get(k).getNameOfDoctor() + "', ");
                    sb.append("'" + lists.get(i).get(k).getAdmissionTime() + "', ");
                    sb.append("'" + lists.get(i).get(k).getNumberOfAdmittedDiseases() + "', ");
                    sb.append("'" + lists.get(i).get(k).getDischargeDiagnosis() + "', ");
                    sb.append("'" + lists.get(i).get(k).getTimeOfDischarge() + "', ");
                    sb.append("'" + lists.get(i).get(k).getNumberOfDischargedDiseases() + "', ");
                    sb.append("'" + lists.get(i).get(k).getDischargeDiagnosis() + "', ");
                    sb.append("'" + lists.get(i).get(k).getStatus() + "', ");
                    sb.append("'" + lists.get(i).get(k).getUpload_no() + "', ");
                    sb.append("'" + lists.get(i).get(k).getUpload_time() + "') ");

                }
                sb.append("select 1 from dual");
                excuteService.executeAsync3(sb.toString());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return checkSuspicionsList;
    }


    private RowHandler createRowHandler(List<CheckSuspicions> list) {
        return new RowHandler() {
            @Override
            public void handle(int sheetIndex, long rowIndex, List<Object> rowlist) {
                if (rowIndex == 0) {
                    return;
                }
                CheckSuspicions checkSuspicions = null;
                int s = rowlist.size();
                for (int i = 0; i < rowlist.size(); i++) {
                    checkSuspicions = new CheckSuspicions();
                    if (null != rowlist.get(0)) {
                        checkSuspicions.setRid(rowlist.get(0).toString());
                    }
                    if (null != rowlist.get(1)) {
                        checkSuspicions.setPsn_name(rowlist.get(1).toString());
                    }
                    if (null != rowlist.get(2)) {
                        checkSuspicions.setCert_no(rowlist.get(2).toString());

                    }
                    if (null != rowlist.get(3)) {
                        checkSuspicions.setGend(rowlist.get(3).toString());

                    }
                    if (null != rowlist.get(4)) {
                        checkSuspicions.setAge(rowlist.get(4).toString());
                    }

                    if (null != rowlist.get(5)) {
                        checkSuspicions.setSetl_time(rowlist.get(5).toString());
                    }
                    if (null != rowlist.get(6)) {
                        checkSuspicions.setFixmedins_name(rowlist.get(6).toString());
                    }
                    if (null != rowlist.get(7)) {
                        checkSuspicions.setFixmedins_code(rowlist.get(7).toString());
                    }
                    if (null != rowlist.get(8)) {
                        checkSuspicions.setGenericNameOfTheDrug(rowlist.get(8).toString());
                    }
                    if (null != rowlist.get(9)) {
                        checkSuspicions.setDrugProvinceCode(rowlist.get(9).toString());
                    }

                    if (null != rowlist.get(10)) {
                        checkSuspicions.setNationalDrugCode(rowlist.get(10).toString());
                    }

                    if (null != rowlist.get(11)) {
                        checkSuspicions.setMdtrt_id(rowlist.get(11).toString());
                    }
                    if (null != rowlist.get(12)) {
                        checkSuspicions.setSetl_id(rowlist.get(12).toString());
                    }
                    if (null != rowlist.get(13)) {
                        checkSuspicions.setPric(rowlist.get(13).toString());
                    }
                    if (null != rowlist.get(14)) {
                        checkSuspicions.setCnt(rowlist.get(14).toString());
                    }
                    if (null != rowlist.get(15)) {
                        checkSuspicions.setDet_item_fee_sumamt(rowlist.get(15).toString());
                    }
                    if (null != rowlist.get(16)) {
                        checkSuspicions.setPsn_selfpay_amt(rowlist.get(16).toString());
                    }
                    if (null != rowlist.get(17)) {
                        checkSuspicions.setLmt_used_flag(rowlist.get(17).toString());
                    }
                    if (null != rowlist.get(18)) {
                        checkSuspicions.setMed_type(rowlist.get(18).toString());
                    }
                    if (null != rowlist.get(19)) {
                        checkSuspicions.setMedfee_sumamt(rowlist.get(19).toString());
                    }
                    if (null != rowlist.get(20)) {
                        checkSuspicions.setHifp_pay(rowlist.get(20).toString());
                    }
                    if (null != rowlist.get(21)) {
                        checkSuspicions.setPool_prop_selfpay(rowlist.get(21).toString());
                    }
                    if (null != rowlist.get(22)) {
                        checkSuspicions.setCvlserv_pay(rowlist.get(22).toString());
                    }
                    if (null != rowlist.get(23)) {
                        checkSuspicions.setHifes_pay(rowlist.get(23).toString());
                    }
                    if (null != rowlist.get(24)) {
                        checkSuspicions.setHifmi_pay(rowlist.get(24).toString());
                    }
                    if (null != rowlist.get(25)) {
                        checkSuspicions.setHifob_pay(rowlist.get(25).toString());
                    }
                    if (null != rowlist.get(26)) {
                        checkSuspicions.setMaf_pay(rowlist.get(26).toString());
                    }
                    if (null != rowlist.get(27)) {
                        checkSuspicions.setAcct_pay(rowlist.get(27).toString());
                    }
                    if (null != rowlist.get(28)) {
                        checkSuspicions.setCash_payamt(rowlist.get(28).toString());
                    }
                    if (null != rowlist.get(29)) {
                        checkSuspicions.setInsu_admdvs_name(rowlist.get(29).toString());
                    }
                    if (null != rowlist.get(30)) {
                        checkSuspicions.setCommon_name_code(rowlist.get(30).toString());
                    }
                    if (null != rowlist.get(31)) {
                        checkSuspicions.setLmt_usescp(rowlist.get(31).toString());
                    }
                    if (null != rowlist.get(32)) {
                        checkSuspicions.setReg(rowlist.get(32).toString());
                    }
                    if (null != rowlist.get(33)) {
                        checkSuspicions.setPsn_type(rowlist.get(33).toString());
                    }
                    if (null != rowlist.get(34)) {
                        checkSuspicions.setTimeOfPrescription(rowlist.get(34).toString());
                    }
                    if (null != rowlist.get(35)) {
                        checkSuspicions.setDept_code(rowlist.get(35).toString());
                    }
                    if (null != rowlist.get(36)) {
                        checkSuspicions.setInpatientWard(rowlist.get(36).toString());
                    }
                    if (null != rowlist.get(37)) {
                        checkSuspicions.setDoctorSCode(rowlist.get(37).toString());
                    }
                    if (null != rowlist.get(38)) {
                        checkSuspicions.setNameOfDoctor(rowlist.get(38).toString());
                    }
                    if (null != rowlist.get(39)) {
                        checkSuspicions.setAdmissionTime(rowlist.get(39).toString());
                    }
                    if (null != rowlist.get(40)) {
                        checkSuspicions.setNumberOfAdmittedDiseases(rowlist.get(40).toString());
                    }
                    if (null != rowlist.get(41)) {
                        checkSuspicions.setAdmittingDiagnosis(rowlist.get(41).toString());
                    }
                    if (null != rowlist.get(42)) {
                        checkSuspicions.setTimeOfDischarge(rowlist.get(42).toString());
                    }

                    if (null != rowlist.get(43)) {
                        checkSuspicions.setNumberOfDischargedDiseases(rowlist.get(43).toString());
                    }

                    if (null != rowlist.get(44)) {
                        checkSuspicions.setDischargeDiagnosis(rowlist.get(44).toString());
                    }


                    checkSuspicions.setStatus("0");
                    checkSuspicions.setIsUpload("0");
                    checkSuspicions.setUpload_no(DateUtil.getDateFormat(new Date(), "yyyyMMdd"));
                    checkSuspicions.setUpload_time(DateUtil.getDateFormat(new Date(), "yyyy-MM-dd HH:mm:ss"));

                }
                list.add(checkSuspicions);
            }
        };
    }


    public void downloadOfEvidence(String rid,String fileURL, HttpServletResponse response) {
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //查询举证信息
        QueryWrapper<FileInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bizId", rid);
        List<FileInfo> fileInfoList = fileInfoMapper.selectList(queryWrapper);


        List<Map<String, Object>> sourceMapList = new ArrayList<Map<String, Object>>();

        if (null != fileInfoList) {
            for (FileInfo f : fileInfoList) {
                if (null != f) {
                    sourceMapList.add(ZipUtils.getToZipUrlMap(fileURL + f.getFileUrl(), f.getFileName().substring(0, f.getFileName().lastIndexOf(".")), "举证证据"));
                }

            }
        }

        if (CollectionUtils.isEmpty(fileInfoList)) {
            throw new CustomException("无举证证据！");
        }

        ZipUtils.toZipHTTP(sourceMapList, outputStream);
    }


    public Map lookFileInfo(String rid) {
        //查询举证信息
        Map map = new HashMap();

        LambdaQueryWrapper<FileInfo> picQuery = new LambdaQueryWrapper<>();
        picQuery.eq(FileInfo::getBizType, "1");
        picQuery.eq(FileInfo::getBizId, rid);
        picQuery.eq(FileInfo::getFileType, "1");
        List<FileInfo> picList = fileInfoMapper.selectList(picQuery);
        map.put("picList", picList);

        LambdaQueryWrapper<FileInfo> docQuery = new LambdaQueryWrapper<>();
        docQuery.eq(FileInfo::getBizType, "1");
        docQuery.eq(FileInfo::getBizId, rid);
        docQuery.eq(FileInfo::getFileType, "2");
        List<FileInfo> docList = fileInfoMapper.selectList(docQuery);
        map.put("docList", docList);
        return map;
    }
}
