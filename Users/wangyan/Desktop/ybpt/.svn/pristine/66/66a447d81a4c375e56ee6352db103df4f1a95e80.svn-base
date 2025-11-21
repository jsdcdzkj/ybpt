package com.jsdc.ybpt.service;

import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.util.IdUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.sax.Excel07SaxReader;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.jsdc.ybpt.abnormal.MedicalCareAbnormal;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.MedicalCareAbnormalMapper;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.util.DateUtil;
import com.jsdc.ybpt.util.ListGroupUtil;
import com.jsdc.ybpt.util.exception.CustomException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MedicalCareAbnormalService extends BaseService<MedicalCareAbnormal> {
    @Autowired
    private MedicalCareAbnormalMapper medicalCareAbnormalMapper;
    @Autowired
    private ExcuteService excuteService;

    @Autowired
    private SysUserService sysUserService ;

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(MedicalCareAbnormalService.class);


    /**
     * excel大文件导入  -医保
     * Author wzn
     * Date 2022/9/29 10:04
     */
    public List<MedicalCareAbnormal> importData(MultipartFile file) {
        List<MedicalCareAbnormal> medicalCareAbnormalList = new ArrayList<>();
        SysUser sysUser = sysUserService.getUser();
        try {
            InputStream in = file.getInputStream();
            StringBuilder sb = null;
            TimeInterval timer = cn.hutool.core.date.DateUtil.timer();
            //ExcelUtil快速读取
//            ExcelUtil.readBySax(in, 0, createRowHandler(settleAbnormalList));
            //构建对象读取
            Excel07SaxReader reader = new Excel07SaxReader(createRowHandler(medicalCareAbnormalList,sysUser.getUsername()));
            reader.read(in, 0);

            System.out.println("解析EXCEL用时：" + timer.intervalRestart());
            List<List<MedicalCareAbnormal>> lists = ListGroupUtil.groupListByQuantity(medicalCareAbnormalList, 100);
            if (CollectionUtils.isEmpty(lists)) {
                throw new CustomException("未能成功解析Excel!");
            }
            System.out.println("List分组用时：" + timer.intervalRestart());
            for (int i = 0; i < lists.size(); i++) {
                sb = new StringBuilder();
                sb.append("INSERT  all into MEDICAL_CARE_ABNORMAL (id,if_upload,upload_no,upload_time,table_name,org_code,org_name,area,settle_time,mdtrt_id,account_seria_number,setl_id,nurse_code,nurse_name,insured_persons_no ) VALUES  (");
                for (int k = 0; k < lists.get(i).size(); k++) {
                    if (k > 0) {
                        sb.append(" INTO MEDICAL_CARE_ABNORMAL (id,if_upload,upload_no,upload_time,table_name,org_code,org_name,area,settle_time,mdtrt_id,account_seria_number,setl_id,nurse_code,nurse_name,insured_persons_no ) VALUES  (");
                    }
                    sb.append("'" + lists.get(i).get(k).getId() + "', ");
                    sb.append("'" + lists.get(i).get(k).getIf_upload() + "', ");
                    sb.append("'" + lists.get(i).get(k).getUpload_no() + "', ");
                    sb.append("'" + lists.get(i).get(k).getUpload_time() + "', ");
                    sb.append("'" + lists.get(i).get(k).getTable_name() + "', ");
                    sb.append("'" + lists.get(i).get(k).getOrg_code() + "', ");
                    sb.append("'" + lists.get(i).get(k).getOrg_name() + "', ");
                    sb.append("'" + lists.get(i).get(k).getArea() + "', ");
                    sb.append("'" + lists.get(i).get(k).getSettle_time() + "', ");
                    sb.append("'" + lists.get(i).get(k).getMdtrt_id() + "', ");
                    sb.append("'" + lists.get(i).get(k).getAccount_seria_number() + "', ");
                    sb.append("'" + lists.get(i).get(k).getSetl_id() + "', ");
                    sb.append("'" + lists.get(i).get(k).getNurse_code() + "', ");
                    sb.append("'" + lists.get(i).get(k).getNurse_name() + "', ");
                    sb.append("'" + lists.get(i).get(k).getInsured_persons_no() + "') ");

                }
                sb.append("select 1 from dual");
                excuteService.executeAsync2(sb.toString());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return medicalCareAbnormalList;
    }


    /**
     * 医药机构大文件导入
     * Author wzn
     * Date 2022/9/29 15:06
     */
    public List<MedicalCareAbnormal> importData2(MultipartFile file) {
        List<MedicalCareAbnormal> medicalCareAbnormalList = new ArrayList<>();
        try {
            InputStream in = file.getInputStream();
            ExcelUtil.readBySax(in, 0, createRowHandler2(medicalCareAbnormalList));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return medicalCareAbnormalList;
    }

    private RowHandler createRowHandler(List<MedicalCareAbnormal> list,String userName) {
        return new RowHandler() {
            @Override
            public void handle(int sheetIndex, long rowIndex, List<Object> rowlist) {
                if (rowIndex == 0) {
                    return;
                }
                MedicalCareAbnormal medicalCareAbnormal = null;
                int s = rowlist.size();
                for (int i = 0; i < rowlist.size(); i++) {
                    medicalCareAbnormal = new MedicalCareAbnormal();
                    medicalCareAbnormal.setId(IdUtil.simpleUUID());
                    if (null != rowlist.get(0)) {
                        medicalCareAbnormal.setTable_name(rowlist.get(0).toString());
                    } else {
                        medicalCareAbnormal.setTable_name("");
                    }

                    if (null != rowlist.get(1)) {
                        medicalCareAbnormal.setOrg_code(rowlist.get(1).toString());
                    } else {
                        medicalCareAbnormal.setOrg_code("");
                    }

                    if (null != rowlist.get(2)) {
                        medicalCareAbnormal.setOrg_name(rowlist.get(2).toString());

                    } else {
                        medicalCareAbnormal.setOrg_name("");
                    }

                    if (null != rowlist.get(3)) {
                        medicalCareAbnormal.setArea(rowlist.get(3).toString());

                    } else {
                        medicalCareAbnormal.setArea("");
                    }

                    if (null != rowlist.get(4)) {
                        medicalCareAbnormal.setSettle_time(rowlist.get(4).toString());
                    } else {
                        medicalCareAbnormal.setSettle_time("");
                    }

                    if (null != rowlist.get(5)) {
                        medicalCareAbnormal.setMdtrt_id(rowlist.get(5).toString());
                    } else {
                        medicalCareAbnormal.setMdtrt_id("");
                    }

                    if (null != rowlist.get(6)) {
                        medicalCareAbnormal.setAccount_seria_number(rowlist.get(6).toString());
                    } else {
                        medicalCareAbnormal.setAccount_seria_number("");
                    }

                    if (null != rowlist.get(7)) {
                        medicalCareAbnormal.setSetl_id(rowlist.get(7).toString());
                    } else {
                        medicalCareAbnormal.setSetl_id("");
                    }

                    if (null != rowlist.get(8)) {
                        medicalCareAbnormal.setNurse_code(rowlist.get(8).toString());
                    } else {
                        medicalCareAbnormal.setNurse_code("");
                    }

                    if (null != rowlist.get(9)) {
                        medicalCareAbnormal.setNurse_name(rowlist.get(9).toString());
                    } else {
                        medicalCareAbnormal.setNurse_name("");
                    }

                    if (s > 10 && null != rowlist.get(10)) {
                        medicalCareAbnormal.setContent((rowlist.get(10).toString()));
                    } else {
                        medicalCareAbnormal.setContent("");
                    }

                    if (s > 11 && null != rowlist.get(11)) {
                        medicalCareAbnormal.setAppeal_reason((rowlist.get(11).toString()));
                    } else {
                        medicalCareAbnormal.setAppeal_reason("");
                    }

                    if (s > 12 && null != rowlist.get(12)) {
                        medicalCareAbnormal.setInsured_persons_no((rowlist.get(12).toString()));
                    } else {
                        medicalCareAbnormal.setInsured_persons_no("");
                    }


                    medicalCareAbnormal.setIf_upload("0");
                    medicalCareAbnormal.setUpload_no(DateUtil.getDateFormat(new Date(), "yyyyMMdd"));
                    medicalCareAbnormal.setUpload_time(DateUtil.getDateFormat(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    medicalCareAbnormal.setCreateUser(userName);
                    medicalCareAbnormal.setCreateTime(new Date());
                }
                list.add(medicalCareAbnormal);
            }
        };
    }


    /**
     * 机构修改后上传
     * Author wzn
     * Date 2022/9/29 12:22
     */
    private RowHandler createRowHandler2(List<MedicalCareAbnormal> list) {
        return new RowHandler() {
            @Override
            public void handle(int sheetIndex, long rowIndex, List<Object> rowlist) {
                MedicalCareAbnormal medicalCareAbnormal = null;
                if (rowIndex == 0) {
                    return;
                }
                for (int i = 0; i < rowlist.size(); i++) {
                    medicalCareAbnormal = new MedicalCareAbnormal();
                    medicalCareAbnormal.setId(rowlist.get(0).toString());
                    if (rowlist.size() > 11 && null != rowlist.get(11)) {
                        medicalCareAbnormal.setContent(rowlist.get(11).toString());
                    }
                    if (rowlist.size() > 12 && null != rowlist.get(12)) {
                        medicalCareAbnormal.setAppeal_reason((rowlist.get(12).toString()));
                    }


                }
                MedicalCareAbnormal medicalCareAbnormal1 = null;
                if (!"".equals(medicalCareAbnormal.getId())) {
                    medicalCareAbnormal1 = medicalCareAbnormal.selectById();
                    if (null != medicalCareAbnormal1) {
                        MedicalCareAbnormal medicalCareAbnormal2 = new MedicalCareAbnormal();
                        medicalCareAbnormal2.setId(medicalCareAbnormal1.getId());
                        if (null != medicalCareAbnormal.getContent() && !"".equals(medicalCareAbnormal.getContent())) {
                            medicalCareAbnormal2.setContent(medicalCareAbnormal.getContent());
                        }
                        if (null != medicalCareAbnormal.getAppeal_reason() && !"".equals(medicalCareAbnormal.getAppeal_reason())) {
                            medicalCareAbnormal2.setAppeal_reason(medicalCareAbnormal.getAppeal_reason());
                        }
                        if (null != medicalCareAbnormal2.getContent() || null != medicalCareAbnormal2.getAppeal_reason()) {
                            medicalCareAbnormal2.setIf_upload("1");
                            medicalCareAbnormal2.setUpdate_time(DateUtil.getDateFormat(new Date(), "yyyy-MM-dd HH:mm:ss"));
                            medicalCareAbnormalMapper.updateById(medicalCareAbnormal2);
                        }


                    }
                }


            }
        };
    }


}
