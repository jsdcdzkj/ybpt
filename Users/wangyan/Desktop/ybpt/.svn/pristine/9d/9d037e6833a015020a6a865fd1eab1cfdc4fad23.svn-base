package com.jsdc.ybpt.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.poi.excel.sax.Excel07SaxReader;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.capitalSettlement.QsInfo;
import com.jsdc.ybpt.capitalSettlement.QsInfoDetails;
import com.jsdc.ybpt.capitalSettlement.QsOrgConfirmation;
import com.jsdc.ybpt.mapper.QsInfoDetailsMapper;
import com.jsdc.ybpt.mapper.QsInfoMapper;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.util.DateUtil;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.util.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class CapitalSettlementService extends BaseService<QsInfo> {

    @Autowired
    private QsInfoMapper qsInfoMapper ;
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private QsInfoDetailsMapper qsInfoDetailsMapper ;


    /**
     * 年度清算数据导入
     * Author wzn
     * Date 2023/1/5 14:41
     */
    @Transactional
    public void importData(QsInfo qsInfo, MultipartFile file) {
        QueryWrapper<QsInfo> queryWrapper = new QueryWrapper<>() ;
        queryWrapper.eq("admdvs", qsInfo.getAdmdvs());
        queryWrapper.eq("is_del","0");
        queryWrapper.eq("year",qsInfo.getYear());
        List<QsInfo> qsInfos = qsInfoMapper.selectList(queryWrapper) ;
        if(qsInfos.size()>0){
            throw new CustomException("本年度已存在该统筹区数据,请先删除！");
        }

        SysUser sysUser = sysUserService.getUser();
        qsInfo.setId(IdUtil.simpleUUID());
        qsInfo.setPub_status("1");
        qsInfo.setIs_del("0");
        qsInfo.setUpload_time(DateUtil.getDateFormat(new Date(), "yyyy-MM-dd HH:mm:ss"));
        qsInfo.setCreateUser(sysUser.getUsername());
        qsInfo.setCreateTime(new Date());
        qsInfo.insert();


        try {
            InputStream in = file.getInputStream();
            //构建对象读取
            Excel07SaxReader reader = new Excel07SaxReader(createRowHandler(sysUser.getUsername(), qsInfo.getId(),qsInfo.getYear(),qsInfo.getAdmdvs()));
            reader.read(in, -1);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private RowHandler createRowHandler(String sysUserName, String qs_info_id,String year,String admdvs) {
        return new RowHandler() {
            @Override
            public void handle(int sheetIndex, long rowIndex, List<Object> rowlist) {
                QsInfoDetails qsInfoDetails = null;

                if (rowIndex == 0) {
                    return;
                }
                int s = rowlist.size();
                for (int i = 0; i < rowlist.size(); i++) {
                    qsInfoDetails = new QsInfoDetails();
                    qsInfoDetails.setYear(year);
                    qsInfoDetails.setQs_info_id(qs_info_id);

                    qsInfoDetails.setType(sheetIndex+"");


                    if (null != rowlist.get(1)) {
                        qsInfoDetails.setBase_code(rowlist.get(1).toString());
                    } else {
                        qsInfoDetails.setBase_code("");
                    }

                    if (null != rowlist.get(2)) {
                        qsInfoDetails.setOrg_code(rowlist.get(2).toString());

                    } else {
                        qsInfoDetails.setOrg_code("");
                    }

                    if (null != rowlist.get(3)) {
                        qsInfoDetails.setOrg_name(rowlist.get(3).toString());

                    } else {
                        qsInfoDetails.setOrg_name("");
                    }

                    if(rowlist.size()>4){
                        if (null != rowlist.get(4) && !"".equals(rowlist.get(4))) {
                            BigDecimal db = new BigDecimal(rowlist.get(4).toString());
                            qsInfoDetails.setPayable_amount(db.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
                        } else {
                            qsInfoDetails.setPayable_amount("");
                        }
                    }

                    if(rowlist.size()>5){
                        if (null != rowlist.get(5) && !"".equals(rowlist.get(5))) {
                            BigDecimal db = new BigDecimal(rowlist.get(5).toString());
                            qsInfoDetails.setPaid_amount(db.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
                        } else {
                            qsInfoDetails.setPaid_amount("");
                        }
                    }

                    if(rowlist.size()>6){
                        if (null != rowlist.get(6) && !"".equals(rowlist.get(6))) {
                            BigDecimal db = new BigDecimal(rowlist.get(6).toString());
                            qsInfoDetails.setPayment_amont(db.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
                        } else {
                            qsInfoDetails.setPayment_amont("");
                        }
                    }

                    if(rowlist.size()>7){
                        if (null != rowlist.get(7) && !"".equals(rowlist.get(7))) {
                            BigDecimal db = new BigDecimal(rowlist.get(7).toString());
                            qsInfoDetails.setOrg_borne_amount(db.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
                        } else {
                            qsInfoDetails.setOrg_borne_amount("");
                        }
                    }


                    if(rowlist.size()>8){
                        if (null != rowlist.get(8) && !"".equals(rowlist.get(8))) {
                            BigDecimal db = new BigDecimal(rowlist.get(8).toString());
                            qsInfoDetails.setSum_amount(db.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
                        } else {
                            qsInfoDetails.setSum_amount("");
                        }
                    }


                    if (rowlist.size()>9 ) {
                        if(null != rowlist.get(9) && !"".equals(rowlist.get(9))){
                            System.out.println(rowlist.get(9));
                            BigDecimal db = new BigDecimal(rowlist.get(9).toString());
                            qsInfoDetails.setBudget_amount(db.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
                        }
                    } else {
                        qsInfoDetails.setBudget_amount("");
                    }


                    if (rowlist.size()>10 ) {
                        if(null != rowlist.get(10) && !"".equals(rowlist.get(10))){
                            BigDecimal db = new BigDecimal(rowlist.get(10).toString());
                            qsInfoDetails.setWithhold_payment(db.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
                        }
                    } else {
                        qsInfoDetails.setWithhold_payment("");
                    }


                    qsInfoDetails.setId(IdUtil.simpleUUID());
                    qsInfoDetails.setAdmdvs(admdvs);
                    qsInfoDetails.setCreateUser(sysUserName);
                    qsInfoDetails.setCreateTime(new Date());
                }

                if(StringUtils.isNotEmpty(qsInfoDetails.getOrg_code())){

                    qsInfoDetails.insert();
                }

            }
        };
    }



    public Page<QsInfo> getList(QsInfo qsInfo){
        SysUser sysUser = sysUserService.getUser() ;
        Page<QsInfo> page = new Page<>(qsInfo.getPageNo(), qsInfo.getPageSize());
        QueryWrapper<QsInfo> queryWrapper = new QueryWrapper<>() ;
        queryWrapper.eq("is_del","0");
        if(!"".equals(qsInfo.getYear()) && null !=qsInfo.getYear()){
            queryWrapper.eq("year",qsInfo.getYear());
        }

        queryWrapper.eq("admdvs",sysUser.getOrg_code());
        if(!"".equals(qsInfo.getPub_status()) && null !=qsInfo.getPub_status()){
            queryWrapper.like("pub_status",qsInfo.getPub_status());
        }
        queryWrapper.orderByDesc("upload_time");
        Page<QsInfo> fixmedinsBPage = qsInfoMapper.selectPage(page,queryWrapper) ;
        return fixmedinsBPage;
    }

    /**
    *资金清算明细
    * Author wzn
    * Date 2023/1/5 16:45
    */
    public Page<QsInfoDetails> detail(QsInfoDetails qsInfoDetails){
        Page<QsInfoDetails> page = new Page<>(qsInfoDetails.getPageNo(), qsInfoDetails.getPageSize());
        QueryWrapper<QsInfoDetails> queryWrapper = new QueryWrapper<>() ;
        queryWrapper.eq("qs_info_id",qsInfoDetails.getQs_info_id()) ;
        if(!"".equals(qsInfoDetails.getType()) && null !=qsInfoDetails.getType()){
            queryWrapper.eq("type",qsInfoDetails.getType());
        }
        if(!"".equals(qsInfoDetails.getOrg_code()) && null !=qsInfoDetails.getOrg_code()){
            queryWrapper.eq("org_code",qsInfoDetails.getOrg_code());
        }
        if(!"".equals(qsInfoDetails.getOrg_name()) && null !=qsInfoDetails.getOrg_name()){
            queryWrapper.like("org_name",qsInfoDetails.getOrg_name());
        }
        Page<QsInfoDetails> fixmedinsBPage = qsInfoDetailsMapper.selectPage(page,queryWrapper) ;
        return fixmedinsBPage;
    }


    /**
    *资金清算明细单条详情
    * Author wzn
    * Date 2023/1/6 8:39
    */
    public QsInfoDetails oneInfo(String id){
        QsInfoDetails qsInfoDetails = qsInfoDetailsMapper.selectById(id) ;
        return qsInfoDetails;
    }



    /**
    *生成确认书
    * Author wzn
    * Date 2023/1/7 9:14
    */
    public void createConfirming(String id){
        QsInfo qsInfo = qsInfoMapper.selectById(id) ;
        qsInfo.setPub_status("2");
        qsInfo.updateById() ;
        SysUser sysUser = sysUserService.getUser() ;
        QueryWrapper<QsInfoDetails> queryWrapper = new QueryWrapper<>() ;
        queryWrapper.eq("qs_info_id",id) ;
        queryWrapper.eq("type","0") ;
        List<QsInfoDetails> qsInfoDetailsList = qsInfoDetailsMapper.selectList(queryWrapper) ;
        if(CollectionUtil.isNotEmpty(qsInfoDetailsList)){
            QsOrgConfirmation qsOrgConfirmation = null ;
            for(QsInfoDetails q:qsInfoDetailsList){
                qsOrgConfirmation = new QsOrgConfirmation() ;
                qsOrgConfirmation.setId(IdUtil.simpleUUID());
                qsOrgConfirmation.setOrg_code(q.getOrg_code());
                qsOrgConfirmation.setOrg_name(q.getOrg_name());
                qsOrgConfirmation.setYear(qsInfo.getYear());
                qsOrgConfirmation.setAdmdvs(qsInfo.getAdmdvs());
                qsOrgConfirmation.setStatus("1");
                qsOrgConfirmation.setIs_del("0");
                qsOrgConfirmation.setCreateUser(sysUser.getUsername());
                qsOrgConfirmation.setCreateTime(new Date());
                qsOrgConfirmation.insert() ;
            }
        }
    }

}
