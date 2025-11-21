package com.jsdc.ybpt.service;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.hutool.core.util.IdUtil;
import cn.hutool.poi.excel.sax.Excel07SaxReader;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.infoAssessment.InfoAssessment;
import com.jsdc.ybpt.mapper.BankMapper;
import com.jsdc.ybpt.mapper.InfoAssessmentMapper;
import com.jsdc.ybpt.model.Bank;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.price.declare.*;
import com.jsdc.ybpt.priceBackUp.SbWesternMedicineBackUp;
import com.jsdc.ybpt.util.DateUtil;
import com.jsdc.ybpt.util.FileUtils;
import com.jsdc.ybpt.util.exception.CustomException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.*;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
public class InfoAssessmentService extends BaseService<InfoAssessment> {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private InfoAssessmentMapper infoAssessmentMapper;

    /**
     *
     *修改考核信息
     * @author wzn
     * @date 2024/10/28 10:50
     */
    public void updateInfoAssessment(InfoAssessment infoAssessment) {
        SysUser sysUser = sysUserService.getUser();
        infoAssessment.setUpdateUser(sysUser.getUsername());
        infoAssessment.setUpdateTime(new Date());
        infoAssessment.updateById();
    }


    /**
     *
     *列表接口
     * @author wzn
     * @date 2024/10/28 10:51
     */
    public Page<InfoAssessment> selectList(InfoAssessment infoAssessment) {

        SysUser sysUser = sysUserService.getUser();
        Page<InfoAssessment> page = new Page<>(infoAssessment.getPageNo(), infoAssessment.getPageSize());
        QueryWrapper<InfoAssessment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");

        if(!"1".equals(sysUser.getUser_type())){
            queryWrapper.eq("fixmedins_code", sysUser.getOrg_code());
        }

        if (StringUtils.isNotEmpty(infoAssessment.getType())) {
            queryWrapper.like("type", infoAssessment.getType());
        }

        if (StringUtils.isNotEmpty(infoAssessment.getFixmedins_code())) {
            queryWrapper.like("fixmedins_code", infoAssessment.getFixmedins_code());
        }

        if (StringUtils.isNotEmpty(infoAssessment.getFixmedins_name())) {
            queryWrapper.like("fixmedins_name", infoAssessment.getFixmedins_name());
        }

        if (StringUtils.isNotEmpty(infoAssessment.getAggrement_lv())) {
            queryWrapper.apply(" LOWER(aggrement_lv) like LOWER('%"+ infoAssessment.getAggrement_lv()+"%')");
        }

        if (StringUtils.isNotEmpty(infoAssessment.getAdmdvs())) {
            queryWrapper.like("admdvs", infoAssessment.getAdmdvs());
        }

        if (StringUtils.isNotEmpty(infoAssessment.getStartTime())) {
            queryWrapper.apply(" month >= '"+infoAssessment.getStartTime()+"'");
        }

        if (StringUtils.isNotEmpty(infoAssessment.getEndTime())) {
            queryWrapper.apply(" month <= '"+infoAssessment.getEndTime()+"'");
        }


        Page<InfoAssessment> bankPage = infoAssessmentMapper.selectPage(page, queryWrapper);
        return bankPage;
    }

    public List<InfoAssessment> selectAllList(InfoAssessment infoAssessment) {
        SysUser sysUser = sysUserService.getUser();
        QueryWrapper<InfoAssessment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");

        if(!"1".equals(sysUser.getUser_type())){
            queryWrapper.eq("fixmedins_code", sysUser.getOrg_code());
        }

        if (StringUtils.isNotEmpty(infoAssessment.getType())) {
            queryWrapper.like("type", infoAssessment.getType());
        }

        if (StringUtils.isNotEmpty(infoAssessment.getFixmedins_code())) {
            queryWrapper.like("fixmedins_code", infoAssessment.getFixmedins_code());
        }

        if (StringUtils.isNotEmpty(infoAssessment.getFixmedins_name())) {
            queryWrapper.like("fixmedins_name", infoAssessment.getFixmedins_name());
        }

        if (StringUtils.isNotEmpty(infoAssessment.getAggrement_lv())) {
            queryWrapper.like("aggrement_lv", infoAssessment.getAggrement_lv());
        }

        if (StringUtils.isNotEmpty(infoAssessment.getAdmdvs())) {
            queryWrapper.like("admdvs", infoAssessment.getAdmdvs());
        }

        if (StringUtils.isNotEmpty(infoAssessment.getStartTime())) {
            queryWrapper.apply(" month >="+infoAssessment.getStartTime());
        }

        if (StringUtils.isNotEmpty(infoAssessment.getEndTime())) {
            queryWrapper.apply(" month <="+infoAssessment.getEndTime());
        }


        List<InfoAssessment> list = infoAssessmentMapper.selectList( queryWrapper);
        return list;
    }

    /**
     *
     *删除考核
     * @author wzn
     * @date 2024/10/28 11:27
     */
    public void delInfoAssessment(String id) {
        InfoAssessment infoAssessment = new InfoAssessment();
        infoAssessment.setId(id);
        infoAssessment.setIs_del("1");
        infoAssessmentMapper.updateById(infoAssessment) ;
    }


@Transactional
    public void importData(MultipartFile file) {

        SysUser sysUser = sysUserService.getUser();
        try {
            InputStream in = file.getInputStream();
            //构建对象读取
            Excel07SaxReader reader = new Excel07SaxReader(createRowHandler(sysUser));
            reader.read(in, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private RowHandler createRowHandler(SysUser sysUser) {
        return new RowHandler() {
            @Override
            public void handle(int sheetIndex, long rowIndex, List<Object> rowlist) {
                InfoAssessment infoAssessment = null;

                if (rowIndex < 2) {
                    return;
                }


                for (int i = 0; i < rowlist.size(); i++) {
                if (rowlist.size() > 0) {
                    if (null == rowlist.get(0)) {
                        throw new RuntimeException("第" + (rowIndex + 1) + "行类型,不能为空！");
                    }
                }

                if (rowlist.size() > 1) {
                    if (null == rowlist.get(1)) {
                        throw new RuntimeException("第" + (rowIndex + 1) + "行机构编码,不能为空！");
                    }
                }

                if (rowlist.size() > 2) {
                    if (null == rowlist.get(2)) {
                        throw new RuntimeException("第" + (rowIndex + 1) + "行机构名称,不能为空！");
                    }
                }

                if (rowlist.size() > 3) {
                    if (null == rowlist.get(3)) {
                        throw new RuntimeException("第" + (rowIndex + 1) + "行机构级别,不能为空！");
                    }
                }

                if (rowlist.size() > 4) {
                    if (null == rowlist.get(4)) {
                        throw new RuntimeException("第" + (rowIndex + 1) + "行地区,不能为空！");
                    }
                }


                if (rowlist.size() > 5) {
                    if (null != rowlist.get(5)) {
                        boolean vaild = InfoAssessmentService.isValidDate(rowlist.get(5) + "");
                        if (!vaild) {
                            throw new RuntimeException("第" + (rowIndex + 1) + "行月份,格式有误！");
                        }
                    } else {
                        throw new RuntimeException("第" + (rowIndex + 1) + "行月份,不能为空！");
                    }
                }

                if (rowlist.size() > 6) {
//                    if (null == rowlist.get(6)) {
//                        throw new RuntimeException("第" + (rowIndex + 1) + "行医保码结算率,不能为空！");
//                    }

                }

            }

                for (int i = 0; i < rowlist.size(); i++) {

                    infoAssessment =  new InfoAssessment();
                    infoAssessment.setId(IdUtil.simpleUUID());


                    if(rowlist.size()>0){
                        if (null != rowlist.get(0)) {
                            infoAssessment.setType(rowlist.get(0)+"");
                        }
                    }

                    if(rowlist.size()>1){
                        if (null != rowlist.get(1)) {
                            infoAssessment.setFixmedins_code(rowlist.get(1)+"");
                        }
                    }

                    if(rowlist.size()>2){
                        if (null != rowlist.get(2)) {
                            infoAssessment.setFixmedins_name(rowlist.get(2)+"");
                        }
                    }

                    if(rowlist.size()>3){
                        if (null != rowlist.get(3)) {
                            infoAssessment.setAggrement_lv(rowlist.get(3)+"");
                        }
                    }

                    if(rowlist.size()>4){
                        if (null != rowlist.get(4)) {
                            infoAssessment.setAdmdvs(rowlist.get(4)+"");
                        }
                    }


                    if(rowlist.size()>5){
                        if (null != rowlist.get(5)) {
                            infoAssessment.setMonth(rowlist.get(5)+"");


                        }
                    }

                    if(rowlist.size()>6){
                        if (null != rowlist.get(6) ) {
                            infoAssessment.setMedicareCodeSettlementRate(rowlist.get(6)+"");
                        }

                    }


                    if(rowlist.size()>7){
                        if (null != rowlist.get(7)) {
                            infoAssessment.setMedicareCodeMoney(rowlist.get(7)+"");
                        } else {
                            infoAssessment.setMedicareCodeMoney("");
                        }
                    }

                    if(rowlist.size()>8){
                        if (null!= rowlist.get(8)) {
                            infoAssessment.setApplyCondition(rowlist.get(8)+"");
                        } else {
                            infoAssessment.setApplyCondition("");
                        }
                    }

                    if(rowlist.size()>9){
                        if (null != rowlist.get(9)) {
                            infoAssessment.setApplyMoney(rowlist.get(9)+"");
                        } else {
                            infoAssessment.setApplyMoney(" ");
                        }
                    }

                    if(rowlist.size()>10){
                        if (null != rowlist.get(10)) {
                            infoAssessment.setMobilePaymentMoney(rowlist.get(10)+"");
                        } else {
                            infoAssessment.setMobilePaymentMoney("");
                        }
                    }

                    if(rowlist.size()>11){
                        if (null != rowlist.get(11)) {
                            infoAssessment.setElectronicPrescriptionMoney(rowlist.get(11)+"");
                        } else {
                            infoAssessment.setElectronicPrescriptionMoney("");
                        }
                    }


                    if(rowlist.size()>12){
                        if (null != rowlist.get(12)) {
                            infoAssessment.setNationBillingPhysician(rowlist.get(12)+"");
                        } else {
                            infoAssessment.setNationBillingPhysician("");
                        }
                    }

                    if(rowlist.size()>13){
                        if (null != rowlist.get(13)) {
                            infoAssessment.setNationOperation(rowlist.get(13)+"");
                        } else {
                            infoAssessment.setNationOperation("");
                        }
                    }

                    if(rowlist.size()>14){
                        if (null != rowlist.get(14)) {
                            infoAssessment.setWesternMedicine(rowlist.get(14)+"");
                        } else {
                            infoAssessment.setWesternMedicine("");
                        }
                    }

                    if(rowlist.size()>15){
                        if (null != rowlist.get(15)) {
                            infoAssessment.setChinesePiece(rowlist.get(15)+"");
                        } else {
                            infoAssessment.setChinesePiece("");
                        }
                    }

                    if(rowlist.size()>16){
                        if (null != rowlist.get(16)) {
                            infoAssessment.setSelfPreparation(rowlist.get(16)+"");
                        } else {
                            infoAssessment.setSelfPreparation("");
                        }
                    }

                    if(rowlist.size()>17){
                        if (null != rowlist.get(17)) {
                            infoAssessment.setMedicalConsumables(rowlist.get(17)+"");
                        } else {
                            infoAssessment.setMedicalConsumables("");
                        }
                    }

                    if(rowlist.size()>18){
                        if (null != rowlist.get(18)) {
                            infoAssessment.setMedicalServiceItem(rowlist.get(18)+"");
                        } else {
                            infoAssessment.setMedicalServiceItem("");
                        }
                    }


                    if(rowlist.size()>19){
                        if (null != rowlist.get(19)) {
                            infoAssessment.setNationMoney(rowlist.get(19)+"");
                        } else {
                            infoAssessment.setNationMoney("");
                        }
                    }

                    if(rowlist.size()>20){
                        if (null != rowlist.get(20)) {
                            infoAssessment.setTraceableCodeMoney(rowlist.get(20)+"");
                        } else {
                            infoAssessment.setTraceableCodeMoney("");
                        }
                    }

                    if(rowlist.size()>21){
                        if (null != rowlist.get(21)) {
                            infoAssessment.setUploadPortMoney(rowlist.get(21)+"");
                        } else {
                            infoAssessment.setUploadPortMoney("");
                        }
                    }

                    if(rowlist.size()>22){
                        if (null != rowlist.get(22)) {
                            infoAssessment.setWorkPilotMoney(rowlist.get(22)+"");
                        } else {
                            infoAssessment.setWorkPilotMoney("");
                        }
                    }

                    if(rowlist.size()>23){
                        if (null != rowlist.get(23)) {
                            infoAssessment.setRemark(rowlist.get(23)+"");
                        } else {
                            infoAssessment.setRemark("");
                        }
                    }


                }


                if(null != infoAssessment){
                    infoAssessment.setIs_del("0");
                    infoAssessment.setCreateTime(new Date());
                    infoAssessment.setCreateUser(sysUser.getId());
                    infoAssessment.insert() ;
                }
            }
        };
    }





    public void down(InfoAssessment infoAssessment,HttpServletResponse response) {
        String file_name = "";
        Map<String, Object> data = new HashMap<String, Object>();
        TemplateExportParams params = new TemplateExportParams();

        List<InfoAssessment> infoAssessments = selectAllList(infoAssessment) ;

        data.put("mapList", infoAssessments);
        params = new TemplateExportParams("templates/信息化标准化考核奖惩模板.xlsx");



        Workbook workbook = ExcelExportUtil.exportExcel(params, data);

        // 在这里可以添加数据到工作簿...
        // 例如：workbook.createSheet("Sheet1");

        // 设置响应头，告诉浏览器这是个附件，需要下载
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=信息化标准化考核奖惩.xlsx");

        // 获取响应的输出流
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 将工作簿写入输出流
        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 关闭工作簿和输出流
        try {
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public static boolean isValidDate(String dateStr) {
        // 定义日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        try {
            // 尝试将字符串解析为YearMonth对象
            YearMonth yearMonth = YearMonth.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            // 如果抛出异常，说明日期格式不正确
            return false;
        }

        // 解析成功，返回true
        return true;
    }

}
