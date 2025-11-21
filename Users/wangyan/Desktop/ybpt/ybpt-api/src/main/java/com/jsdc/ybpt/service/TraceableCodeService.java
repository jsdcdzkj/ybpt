package com.jsdc.ybpt.service;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.sax.Excel07SaxReader;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.assessment.KHManage;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.infoAssessment.InfoAssessment;
import com.jsdc.ybpt.mapper.InfoAssessmentMapper;
import com.jsdc.ybpt.mapper.TraceableCodeMapper;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.traceableCode.TraceableCode;
import com.jsdc.ybpt.vo.AssessmentVo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TraceableCodeService extends BaseService<TraceableCode> {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private TraceableCodeMapper traceableCodeMapper;

    /**
     *
     *修改追溯码
     * @author wzn
     * @date 2024/10/28 10:50
     */
    public void updateTraceableCode(TraceableCode traceableCode) {
        SysUser sysUser = sysUserService.getUser();
        traceableCode.setUpdateUser(sysUser.getUsername());
        traceableCode.setUpdateTime(new Date());
        traceableCode.updateById();
    }


    /**
     *
     *列表接口
     * @author wzn
     * @date 2024/10/28 10:51
     */
    public Page<TraceableCode> selectList(TraceableCode traceableCode) {

        SysUser sysUser = sysUserService.getUser();
        Page<TraceableCode> page = new Page<>(traceableCode.getPageNo(), traceableCode.getPageSize());
        QueryWrapper<TraceableCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");

        if(!"1".equals(sysUser.getUser_type())){
            queryWrapper.eq("fixmedins_code", sysUser.getOrg_code());
        }

        if (StringUtils.isNotEmpty(traceableCode.getFixmedins_code())) {
            queryWrapper.like("fixmedins_code", traceableCode.getFixmedins_code());
        }

        if (StringUtils.isNotEmpty(traceableCode.getFixmedins_name())) {
            queryWrapper.like("fixmedins_name", traceableCode.getFixmedins_name());
        }

        if (StringUtils.isNotEmpty(traceableCode.getBatchSerialNumber())) {
            queryWrapper.eq("batchSerialNumber", traceableCode.getBatchSerialNumber());
        }

        if (StringUtils.isNotEmpty(traceableCode.getSalesSerialNumber())) {
            queryWrapper.eq("salesSerialNumber", traceableCode.getSalesSerialNumber());
        }

        if (StringUtils.isNotEmpty(traceableCode.getMdtrt_id())) {
            queryWrapper.eq("mdtrt_id", traceableCode.getMdtrt_id());
        }

        if (StringUtils.isNotEmpty(traceableCode.getDrugTracingCode())) {
            queryWrapper.eq("drugTracingCode", traceableCode.getDrugTracingCode());
        }



        if (StringUtils.isNotEmpty(traceableCode.getStartTime())) {
            queryWrapper.apply(" SUBSTR(opt_time,0,10) >= '"+traceableCode.getStartTime()+"'");
        }

        if (StringUtils.isNotEmpty(traceableCode.getEndTime())) {
            queryWrapper.apply(" SUBSTR(opt_time,0,10) <= '"+traceableCode.getEndTime()+"'");
        }


        Page<TraceableCode> bankPage = traceableCodeMapper.selectPage(page, queryWrapper);
        return bankPage;
    }

    public List<TraceableCode> selectAllList(TraceableCode traceableCode) {
        SysUser sysUser = sysUserService.getUser();
        QueryWrapper<TraceableCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        if(!"1".equals(sysUser.getUser_type())){
            queryWrapper.eq("fixmedins_code", sysUser.getOrg_code());
        }

        if (StringUtils.isNotEmpty(traceableCode.getFixmedins_code())) {
            queryWrapper.like("fixmedins_code", traceableCode.getFixmedins_code());
        }

        if (StringUtils.isNotEmpty(traceableCode.getFixmedins_name())) {
            queryWrapper.like("fixmedins_name", traceableCode.getFixmedins_name());
        }

        if (StringUtils.isNotEmpty(traceableCode.getBatchSerialNumber())) {
            queryWrapper.eq("batchSerialNumber", traceableCode.getBatchSerialNumber());
        }

        if (StringUtils.isNotEmpty(traceableCode.getMdtrt_id())) {
            queryWrapper.eq("mdtrt_id", traceableCode.getMdtrt_id());
        }

        if (StringUtils.isNotEmpty(traceableCode.getDrugTracingCode())) {
            queryWrapper.eq("drugTracingCode", traceableCode.getDrugTracingCode());
        }



        if (StringUtils.isNotEmpty(traceableCode.getStartTime())) {
            queryWrapper.apply(" opt_time >= '"+traceableCode.getStartTime()+"'");
        }

        if (StringUtils.isNotEmpty(traceableCode.getEndTime())) {
            queryWrapper.apply(" opt_time <= '"+traceableCode.getEndTime()+"'");
        }

        List<TraceableCode> list = traceableCodeMapper.selectList( queryWrapper);
        return list;
    }

    /**
     *
     *删除追溯码重复
     * @author wzn
     * @date 2024/10/28 11:27
     */
    public void delTraceableCode(String id) {
        TraceableCode traceableCode = new TraceableCode();
        traceableCode.setId(id);
        traceableCode.setIs_del("1");
        traceableCodeMapper.updateById(traceableCode) ;
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
                TraceableCode traceableCode = null;

                if (rowIndex < 1) {
                    return;
                }



                for (int i = 0; i < rowlist.size(); i++) {


                if (rowlist.size() > 1) {
                    if (null == rowlist.get(1)) {
                        throw new RuntimeException("第" + (rowIndex + 1) + "行机构编码,不能为空！");
                    }
                }


            }

                for (int i = 0; i < rowlist.size(); i++) {

                    traceableCode =  new TraceableCode();
                    traceableCode.setId(IdUtil.simpleUUID());


                    if(rowlist.size()>0){
                        if (null != rowlist.get(0)) {
                            traceableCode.setTraceTheSerialNumber(rowlist.get(0)+"");
                        }
                    }

                    if(rowlist.size()>1){
                        if (null != rowlist.get(1)) {
                            traceableCode.setFixmedins_code(rowlist.get(1)+"");
                        }
                    }

                    if(rowlist.size()>2){
                        if (null != rowlist.get(2)) {
                            traceableCode.setFixmedins_name(rowlist.get(2)+"");
                        }
                    }

                    if(rowlist.size()>3){
                        if (null != rowlist.get(3)) {
                            traceableCode.setMed_list_codg(rowlist.get(3)+"");
                        }
                    }

                    if(rowlist.size()>4){
                        if (null != rowlist.get(4)) {
                            traceableCode.setMedins_list_codg(rowlist.get(4)+"");
                        }
                    }


                    if(rowlist.size()>5){
                        if (null != rowlist.get(5)) {
                            traceableCode.setMedins_list_name(rowlist.get(5)+"");
                        }
                    }

                    if(rowlist.size()>6){
                        if (null != rowlist.get(6) ) {
                            traceableCode.setBatchSerialNumber(rowlist.get(6)+"");
                        }

                    }

                    if(rowlist.size()>7){
                        if (null != rowlist.get(7)) {
                            traceableCode.setSalesSerialNumber(rowlist.get(7)+"");
                        }
                    }

                    if(rowlist.size()>8){
                        if (null!= rowlist.get(8)) {
                            traceableCode.setMdtrt_id(rowlist.get(8)+"");
                        }
                    }

                    if(rowlist.size()>9){
                        if (null != rowlist.get(9)) {
                            traceableCode.setSettlementType(rowlist.get(9)+"");
                        }
                    }

                    if(rowlist.size()>10){
                        if (null != rowlist.get(10)) {
                            traceableCode.setAccount_seria_number(rowlist.get(10)+"");
                        }
                    }

                    if(rowlist.size()>11){
                        if (null != rowlist.get(11)) {
                            traceableCode.setDrugTracingCode(rowlist.get(11)+"");
                        }
                    }


                    if(rowlist.size()>12){
                        if (null != rowlist.get(12)) {
                            traceableCode.setOpter_id(rowlist.get(12)+"");
                        }
                    }

                    if(rowlist.size()>13){
                        if (null != rowlist.get(13)) {
                            traceableCode.setOpter_name(rowlist.get(13)+"");
                        }
                    }

                    if(rowlist.size()>14){
                        if (null != rowlist.get(14)) {
                            traceableCode.setOpt_time(rowlist.get(14)+"");
                        }
                    }

                    if(rowlist.size()>15){
                        if (null != rowlist.get(15)) {
                            traceableCode.setOptins_no(rowlist.get(15)+"");
                        }
                    }

                    if(rowlist.size()>16){
                        if (null != rowlist.get(16)) {
                            traceableCode.setPoolarea_no(rowlist.get(16)+"");
                        }
                    }

                    if(rowlist.size()>17){
                        if (null != rowlist.get(17)) {
                            traceableCode.setDismantlingMark(rowlist.get(17)+"");
                        }
                    }

                    if(rowlist.size()>18){
                        if (null != rowlist.get(18)) {
                            traceableCode.setPsn_no(rowlist.get(18)+"");
                        }
                    }


                    if(rowlist.size()>19){
                        if (null != rowlist.get(19)) {
                            traceableCode.setPsn_cert_type(rowlist.get(19)+"");
                        }
                    }

                    if(rowlist.size()>20){
                        if (null != rowlist.get(20)) {
                            traceableCode.setCertno(rowlist.get(20)+"");
                        }
                    }
                    if(rowlist.size()>21){
                        if (null != rowlist.get(21)) {
                            traceableCode.setName(rowlist.get(21)+"");
                        }
                    }



                }

                if(null != traceableCode){
                    traceableCode.setIs_del("0");
                    traceableCode.setCreateTime(new Date());
                    traceableCode.setCreateUser(sysUser.getId());
                    traceableCode.insert() ;
                }
            }
        };
    }




    /**
     * 导出
     *
     * @return
     */


}
