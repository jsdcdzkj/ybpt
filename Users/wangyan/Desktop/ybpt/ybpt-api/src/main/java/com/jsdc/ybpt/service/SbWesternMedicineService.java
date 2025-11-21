package com.jsdc.ybpt.service;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.poi.excel.sax.Excel07SaxReader;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.*;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.price.drug.SbChinesePatentMedicine;
import com.jsdc.ybpt.price.drug.SbWesternMedicine;
import com.jsdc.ybpt.price.zlproject.SbZlProject;
import com.jsdc.ybpt.priceBackUp.SbChinesePatentMedicineBackUp;
import com.jsdc.ybpt.priceBackUp.SbWesternMedicineBackUp;
import com.jsdc.ybpt.priceBackUp.project.SbProjectBackUp;
import com.jsdc.ybpt.util.DateUtil;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.util.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SbWesternMedicineService extends BaseService<SbWesternMedicine> {

    @Autowired
    private SysUserService sysUserService ;

    @Autowired
    private SbWesternMedicineMapper sbWesternMedicineMapper ;

    @Autowired
    private SbChinesePatentMedicineMapper sbChinesePatentMedicineMapper ;

    @Autowired
    private SbZlProjectMapper sbZlProjectMapper ;

    @Autowired
    private SbBloodConstituentMapper sbBloodConstituentMapper ;

    @Autowired
    private SbNonProjectMapper sbNonProjectMapper ;

    @Autowired
    private SbWesternMedicineBackUpMapper sbWesternMedicineBackUpMapper ;

    @Autowired
    private SbChinesePatentMedicineBackUpMapper sbChinesePatentMedicineBackUpMapper ;

    @Autowired
    private SbProjectBackUpMapper sbProjectBackUpMapper ;

    /**
    *西药数据导入
    * Author wzn
    * Date 2023/2/1 11:26
    */
    public void importData(MultipartFile file,String year) {

        SysUser sysUser = sysUserService.getUser();
        try {
            InputStream in = file.getInputStream();
            //构建对象读取
            Excel07SaxReader reader = new Excel07SaxReader(createRowHandler(sysUser.getUsername(),year));
            reader.read(in, 0);
            String nowTime = DateUtil.getDateFormat(new Date(), "yyyy-MM-dd");
            //比对当前时间和生效时间
            if(nowTime.equals(year)){
                timing() ;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private RowHandler createRowHandler(String sysUserName,String year) {
        return new RowHandler() {
            @Override
            public void handle(int sheetIndex, long rowIndex, List<Object> rowlist) {
                SbWesternMedicineBackUp sbWesternMedicineBackUp = null;

                if (rowIndex ==1) {
                   if(!rowlist.get(0).toString().contains("西药")){
                      throw new CustomException("请上传西药模板！");
                   }
                }
                if (rowIndex <3) {
                    return;
                }
                for (int i = 0; i < rowlist.size(); i++) {
                    sbWesternMedicineBackUp = new SbWesternMedicineBackUp();
                    sbWesternMedicineBackUp.setYear(year);
                    sbWesternMedicineBackUp.setIs_take_effect("0");




                    if (null != rowlist.get(1)) {
                        sbWesternMedicineBackUp.setSortingCodeNumber(rowlist.get(1).toString());
                    } else {
                        sbWesternMedicineBackUp.setSortingCodeNumber("");
                    }

                    if (null != rowlist.get(2)) {
                        sbWesternMedicineBackUp.setCommon_name_code(rowlist.get(2).toString());

                    } else {
                        sbWesternMedicineBackUp.setCommon_name_code("");
                    }

                    if (null != rowlist.get(3)) {
                        sbWesternMedicineBackUp.setDrugNames(rowlist.get(3).toString());

                    } else {
                        sbWesternMedicineBackUp.setDrugNames("");
                    }

                    if(rowlist.size()>4){
                        if (null != rowlist.get(4) && !"".equals(rowlist.get(4))) {
                            sbWesternMedicineBackUp.setPaymentCategory(rowlist.get(4).toString());
                        } else {
                            sbWesternMedicineBackUp.setPaymentCategory("");
                        }
                    }

                    if(rowlist.size()>5){
                        if (null != rowlist.get(5) && !"".equals(rowlist.get(5))) {
                            sbWesternMedicineBackUp.setDosageForm(rowlist.get(5).toString());
                        } else {
                            sbWesternMedicineBackUp.setDosageForm("");
                        }
                    }

                    if(rowlist.size()>6){
                        if (null != rowlist.get(6) && !"".equals(rowlist.get(6))) {
                            sbWesternMedicineBackUp.setProductNameCoding(rowlist.get(6).toString());
                        } else {
                            sbWesternMedicineBackUp.setProductNameCoding("");
                        }
                    }

                    if(rowlist.size()>7){
                        if (null != rowlist.get(7) && !"".equals(rowlist.get(7))) {
                            sbWesternMedicineBackUp.setRegisteredName(rowlist.get(7).toString());
                        } else {
                            sbWesternMedicineBackUp.setRegisteredName("");
                        }
                    }


                    if(rowlist.size()>8){
                        if (null != rowlist.get(8) && !"".equals(rowlist.get(8))) {
                            sbWesternMedicineBackUp.setProductName(rowlist.get(8).toString());
                        } else {
                            sbWesternMedicineBackUp.setProductName("");
                        }
                    }


                    if (rowlist.size()>9 ) {
                        if(null != rowlist.get(9) && !"".equals(rowlist.get(9))){
                            sbWesternMedicineBackUp.setActualDosageForm(rowlist.get(9).toString());
                        }else {
                            sbWesternMedicineBackUp.setActualDosageForm("");
                        }
                    }

                    if (rowlist.size()>10 ) {
                        if(null != rowlist.get(10) && !"".equals(rowlist.get(10))){
                            sbWesternMedicineBackUp.setActualSpecification(rowlist.get(10).toString());
                        }else {
                            sbWesternMedicineBackUp.setActualSpecification("");
                        }
                    }

                    if (rowlist.size()>11 ) {
                        if(null != rowlist.get(11) && !"".equals(rowlist.get(11))){
                            sbWesternMedicineBackUp.setPackagingMaterial(rowlist.get(11).toString());
                        }else {
                            sbWesternMedicineBackUp.setPackagingMaterial("");
                        }
                    }

                    if (rowlist.size()>12 ) {
                        if(null != rowlist.get(12) && !"".equals(rowlist.get(12))){
                            sbWesternMedicineBackUp.setMinimumPackingQuantity(rowlist.get(12).toString());
                        }else {
                            sbWesternMedicineBackUp.setMinimumPackingQuantity("");
                        }
                    }


                    if (rowlist.size()>13 ) {
                        if(null != rowlist.get(13) && !"".equals(rowlist.get(13))){
                            sbWesternMedicineBackUp.setUnit(rowlist.get(13).toString());
                        }else {
                            sbWesternMedicineBackUp.setUnit("");
                        }
                    }



                    if (rowlist.size()>14 ) {
                        if(null != rowlist.get(14) && !"".equals(rowlist.get(14))){
                            sbWesternMedicineBackUp.setValorize(rowlist.get(14).toString());
                        }else {
                            sbWesternMedicineBackUp.setValorize("");
                        }
                    }



                    if (rowlist.size()>15 ) {
                        if(null != rowlist.get(15) && !"".equals(rowlist.get(15))){
                            sbWesternMedicineBackUp.setPurchaseCeilingPrice(rowlist.get(15).toString());
                        }else {
                            sbWesternMedicineBackUp.setPurchaseCeilingPrice("");
                        }
                    }


                    if (rowlist.size()>16 ) {
                        if(null != rowlist.get(16) && !"".equals(rowlist.get(16))){
                            sbWesternMedicineBackUp.setPaymentCriteria(rowlist.get(16).toString());
                        }else {
                            sbWesternMedicineBackUp.setPaymentCriteria("");
                        }
                    }


                    if (rowlist.size()>17 ) {
                        if(null != rowlist.get(17) && !"".equals(rowlist.get(17))){
                            sbWesternMedicineBackUp.setApprovalNumber(rowlist.get(17).toString());
                        }else {
                            sbWesternMedicineBackUp.setApprovalNumber("");
                        }
                    }

                    if (rowlist.size()>18 ) {
                        if(null != rowlist.get(18) && !"".equals(rowlist.get(18))){
                            sbWesternMedicineBackUp.setMedicineEnterprise(rowlist.get(18).toString());
                        }else {
                            sbWesternMedicineBackUp.setMedicineEnterprise("");
                        }
                    }

                    if (rowlist.size()>19 ) {
                        if(null != rowlist.get(19) && !"".equals(rowlist.get(19))){
                            sbWesternMedicineBackUp.setLimitPayment(rowlist.get(19).toString());
                        }else {
                            sbWesternMedicineBackUp.setLimitPayment("");
                        }
                    }


                    if (rowlist.size()>20 ) {
                        if(null != rowlist.get(20) && !"".equals(rowlist.get(20))){
                            sbWesternMedicineBackUp.setSerialNumber(rowlist.get(20).toString());
                        }else {
                            sbWesternMedicineBackUp.setSerialNumber("");
                        }
                    }

                    if (rowlist.size()>21 ) {
                        if(null != rowlist.get(21) && !"".equals(rowlist.get(21))){
                            sbWesternMedicineBackUp.setBidDeclarationNumber(rowlist.get(21).toString());
                        }else {
                            sbWesternMedicineBackUp.setBidDeclarationNumber("");
                        }
                    }

                    if (rowlist.size()>22 ) {
                        if(null != rowlist.get(22) && !"".equals(rowlist.get(22))){
                            sbWesternMedicineBackUp.setRemark(rowlist.get(22).toString());
                        }else {
                            sbWesternMedicineBackUp.setRemark("");
                        }
                    }


                    if (rowlist.size()>23 ) {
                        if(null != rowlist.get(23) && !"".equals(rowlist.get(23))){
                            sbWesternMedicineBackUp.setNationalDrugCode(rowlist.get(23).toString());
                        }else {
                            sbWesternMedicineBackUp.setNationalDrugCode("");
                        }
                    }


                    if (rowlist.size()>24 ) {
                        if(null != rowlist.get(24) && !"".equals(rowlist.get(24))){
                            sbWesternMedicineBackUp.setChangeType(rowlist.get(24).toString());
                        }else {
                            sbWesternMedicineBackUp.setChangeType("");
                        }
                    }


                    if (rowlist.size()>25 ) {
                        if(null != rowlist.get(25) && !"".equals(rowlist.get(25))){
                            sbWesternMedicineBackUp.setReasonsForChange(rowlist.get(25).toString());
                        }else {
                            sbWesternMedicineBackUp.setReasonsForChange("");
                        }
                    }


                }

                if(StringUtils.isNotEmpty(sbWesternMedicineBackUp.getSortingCodeNumber())){
                    if(StringUtils.isEmpty(sbWesternMedicineBackUp.getChangeType())){
                        throw new CustomException("请完善变更类型！");
                    }
                    sbWesternMedicineBackUp.insert() ;
                }



            }
        };
    }

    /**
    *西药定时任务  往目录库刷新数据
    * Author wzn
    * Date 2023/3/1 14:11
    */
    public void timing(){
        String nowTime = DateUtil.getDateFormat(new Date(), "yyyy-MM-dd");
        QueryWrapper queryWrapper = new QueryWrapper() ;
        queryWrapper.isNull("is_del") ;
        List<SbWesternMedicineBackUp> sbWesternMedicineBackUps = sbWesternMedicineBackUpMapper.selectList(queryWrapper) ;
       if(CollectionUtil.isNotEmpty(sbWesternMedicineBackUps)){
           for(SbWesternMedicineBackUp s:sbWesternMedicineBackUps){
                if(nowTime.equals(s.getYear())){
                                    if(StringUtils.isNotEmpty(s.getChangeType())){
                    if("0".equals(s.getChangeType())){
                        SbWesternMedicine sbWesternMedicine  = new SbWesternMedicine() ;
                        BeanUtil.copyProperties(s,sbWesternMedicine);
                        sbWesternMedicine.setId(IdUtil.simpleUUID());
                        sbWesternMedicine.setCreateUser("定时任务");
                        sbWesternMedicine.setCreateTime(new Date());
                        sbWesternMedicine.setIs_del("0");
                        QueryWrapper<SbWesternMedicine> queryWrapper2 = new QueryWrapper<>() ;
                        queryWrapper2.eq("nationalDrugCode",sbWesternMedicine.getNationalDrugCode()) ;
                        Long cout = sbWesternMedicineMapper.selectCount(queryWrapper2);
                        if(cout == 0 ){
                            sbWesternMedicine.insert() ;
                        }
                        s.setIs_del("1") ;
                        s.setIs_take_effect("1");
                        s.updateById() ;
                    }else if("1".equals(s.getChangeType())){
                        QueryWrapper<SbWesternMedicine> queryWrapper2 = new QueryWrapper<>() ;
                        queryWrapper2.eq("nationalDrugCode",s.getNationalDrugCode());
                        SbWesternMedicine sbWesternMedicine1 = sbWesternMedicineMapper.selectOne(queryWrapper2) ;
                        if(null != sbWesternMedicine1){
                            //往备份表插数据
                            SbWesternMedicineBackUp sbWesternMedicineBackUp = new SbWesternMedicineBackUp() ;
                            BeanUtil.copyProperties(sbWesternMedicine1,sbWesternMedicineBackUp);
                            sbWesternMedicineBackUp.setId(IdUtil.simpleUUID());
                            sbWesternMedicineBackUp.setIs_del("0");
                            sbWesternMedicineBackUp.setBatch(DateUtil.getDateFormat(new Date(),"yyyyMMdd"));
                            sbWesternMedicineBackUp.insert() ;


                            //更新目录库数据
                            sbWesternMedicine1.setSortingCodeNumber(s.getSortingCodeNumber()) ;
                            sbWesternMedicine1.setCommon_name_code(s.getCommon_name_code()) ;
                            sbWesternMedicine1.setDrugNames(s.getDrugNames()) ;
                            sbWesternMedicine1.setPaymentCategory(s.getPaymentCategory()) ;
                            sbWesternMedicine1.setDosageForm(s.getDosageForm()) ;
                            sbWesternMedicine1.setProductNameCoding(s.getProductNameCoding()) ;
                            sbWesternMedicine1.setRegisteredName(s.getRegisteredName()) ;
                            sbWesternMedicine1.setProductName(s.getProductName()) ;
                            sbWesternMedicine1.setActualDosageForm(s.getActualDosageForm()) ;
                            sbWesternMedicine1.setActualSpecification(s.getActualSpecification()) ;
                            sbWesternMedicine1.setPackagingMaterial(s.getPackagingMaterial()) ;
                            sbWesternMedicine1.setMinimumPackingQuantity(s.getMinimumPackingQuantity()) ;
                            sbWesternMedicine1.setUnit(s.getUnit()) ;
                            sbWesternMedicine1.setValorize(s.getValorize()) ;
                            sbWesternMedicine1.setPurchaseCeilingPrice(s.getPurchaseCeilingPrice()) ;
                            sbWesternMedicine1.setPaymentCriteria(s.getPaymentCriteria()) ;
                            sbWesternMedicine1.setApprovalNumber(s.getApprovalNumber()) ;
                            sbWesternMedicine1.setMedicineEnterprise(s.getMedicineEnterprise()) ;
                            sbWesternMedicine1.setLimitPayment(s.getLimitPayment()) ;
                            sbWesternMedicine1.setSerialNumber(s.getSerialNumber()) ;
                            sbWesternMedicine1.setBidDeclarationNumber(s.getBidDeclarationNumber()) ;
                            sbWesternMedicine1.setRemark(s.getRemark()) ;
                            sbWesternMedicine1.setNationalDrugCode(s.getNationalDrugCode()) ;
                            sbWesternMedicine1.setReasonsForChange(s.getReasonsForChange()) ;
                            sbWesternMedicine1.setUpdateTime(new Date());
                            sbWesternMedicine1.updateById() ;
                        }
                        //定时任务数据
                        s.setIs_del("1") ;
                        s.setIs_take_effect("1");
                        s.updateById() ;

                    }else if("2".equals(s.getChangeType())){
                        QueryWrapper<SbWesternMedicine> queryWrapper2 = new QueryWrapper<>() ;
                        queryWrapper2.eq("nationalDrugCode",s.getNationalDrugCode());
                        SbWesternMedicine sbWesternMedicine1 = sbWesternMedicineMapper.selectOne(queryWrapper2) ;
                        if(null != sbWesternMedicine1){
                            //往备份表插数据
                            SbWesternMedicineBackUp sbWesternMedicineBackUp = new SbWesternMedicineBackUp() ;
                            BeanUtil.copyProperties(sbWesternMedicine1,sbWesternMedicineBackUp);
                            sbWesternMedicineBackUp.setId(IdUtil.simpleUUID());
                            sbWesternMedicineBackUp.setBatch(DateUtil.getDateFormat(new Date(),"yyyyMMdd"));
                            sbWesternMedicineBackUp.setIs_del("0");
                            sbWesternMedicineBackUp.insert() ;

                            //更新目录库数据
                            sbWesternMedicine1.deleteById() ;

                        }
                        //定时任务数据
                        s.setIs_del("1") ;
                        s.setIs_take_effect("1");
                        s.updateById() ;
                    }
                }
                }
           }
       }

    }



    /**
     *中成药定时任务  往目录库刷新数据
     * Author wzn
     * Date 2023/3/1 14:11
     */
    public void chinTiming(){
        String nowTime = DateUtil.getDateFormat(new Date(), "yyyy-MM-dd");
        QueryWrapper queryWrapper = new QueryWrapper() ;
        queryWrapper.isNull("is_del") ;
        List<SbChinesePatentMedicineBackUp> sbChinesePatentMedicineBackUps = sbChinesePatentMedicineBackUpMapper.selectList(queryWrapper) ;
        if(CollectionUtil.isNotEmpty(sbChinesePatentMedicineBackUps)){
            for(SbChinesePatentMedicineBackUp s:sbChinesePatentMedicineBackUps){
                if(nowTime.equals(s.getYear())){
                    if(StringUtils.isNotEmpty(s.getChangeType())){
                        if("0".equals(s.getChangeType())){
                            SbChinesePatentMedicine sbChinesePatentMedicine  = new SbChinesePatentMedicine() ;
                            BeanUtil.copyProperties(s,sbChinesePatentMedicine);
                            sbChinesePatentMedicine.setId(IdUtil.simpleUUID());
                            sbChinesePatentMedicine.setCreateUser("定时任务");
                            sbChinesePatentMedicine.setCreateTime(new Date());
                            sbChinesePatentMedicine.setIs_del("0");
                            QueryWrapper<SbChinesePatentMedicine> queryWrapper2 = new QueryWrapper<>() ;
                            queryWrapper2.eq("nationalDrugCode",sbChinesePatentMedicine.getNationalDrugCode()) ;
                            Long cout = sbChinesePatentMedicineMapper.selectCount(queryWrapper2);
                            if(cout == 0 ){
                                sbChinesePatentMedicine.insert() ;
                            }
                            s.setIs_del("1") ;
                            s.setIs_take_effect("1");
                            s.updateById() ;
                        }else if("1".equals(s.getChangeType())){
                            QueryWrapper<SbChinesePatentMedicine> queryWrapper2 = new QueryWrapper<>() ;
                            queryWrapper2.eq("nationalDrugCode",s.getNationalDrugCode());
                            SbChinesePatentMedicine sbChinesePatentMedicine = sbChinesePatentMedicineMapper.selectOne(queryWrapper2) ;
                            if(null != sbChinesePatentMedicine){
                                //往备份表插数据
                                SbChinesePatentMedicineBackUp sbChinesePatentMedicineBackUp = new SbChinesePatentMedicineBackUp() ;
                                BeanUtil.copyProperties(sbChinesePatentMedicine,sbChinesePatentMedicineBackUp);
                                sbChinesePatentMedicineBackUp.setId(IdUtil.simpleUUID());
                                sbChinesePatentMedicineBackUp.setIs_del("0");
                                sbChinesePatentMedicineBackUp.setBatch(DateUtil.getDateFormat(new Date(),"yyyyMMdd"));
                                sbChinesePatentMedicineBackUp.insert() ;


                                //更新目录库数据
                                sbChinesePatentMedicine.setSortingCodeNumber(s.getSortingCodeNumber()) ;
                                sbChinesePatentMedicine.setCommon_name_code(s.getCommon_name_code()) ;
                                sbChinesePatentMedicine.setDrugNames(s.getDrugNames()) ;
                                sbChinesePatentMedicine.setPaymentCategory(s.getPaymentCategory()) ;
                                sbChinesePatentMedicine.setProductNameCoding(s.getProductNameCoding()) ;
                                sbChinesePatentMedicine.setRegisteredName(s.getRegisteredName()) ;
                                sbChinesePatentMedicine.setProductName(s.getProductName()) ;
                                sbChinesePatentMedicine.setActualDosageForm(s.getActualDosageForm()) ;
                                sbChinesePatentMedicine.setActualSpecification(s.getActualSpecification()) ;
                                sbChinesePatentMedicine.setPackagingMaterial(s.getPackagingMaterial()) ;
                                sbChinesePatentMedicine.setMinimumPackingQuantity(s.getMinimumPackingQuantity()) ;
                                sbChinesePatentMedicine.setUnit(s.getUnit()) ;
                                sbChinesePatentMedicine.setValorize(s.getValorize()) ;
                                sbChinesePatentMedicine.setPurchaseCeilingPrice(s.getPurchaseCeilingPrice()) ;
                                sbChinesePatentMedicine.setPaymentCriteria(s.getPaymentCriteria()) ;
                                sbChinesePatentMedicine.setApprovalNumber(s.getApprovalNumber()) ;
                                sbChinesePatentMedicine.setMedicineEnterprise(s.getMedicineEnterprise()) ;
                                sbChinesePatentMedicine.setLimitPayment(s.getLimitPayment()) ;
                                sbChinesePatentMedicine.setSerialNumber(s.getSerialNumber()) ;
                                sbChinesePatentMedicine.setBidDeclarationNumber(s.getBidDeclarationNumber()) ;
                                sbChinesePatentMedicine.setRemark(s.getRemark()) ;
                                sbChinesePatentMedicine.setNationalDrugCode(s.getNationalDrugCode()) ;
                                sbChinesePatentMedicine.setReasonsForChange(s.getReasonsForChange()) ;
                                sbChinesePatentMedicine.setUpdateTime(new Date());
                                sbChinesePatentMedicine.updateById() ;
                            }
                            //定时任务数据
                            s.setIs_del("1") ;
                            s.setIs_take_effect("1");
                            s.updateById() ;

                        }else if("2".equals(s.getChangeType())){
                            QueryWrapper<SbChinesePatentMedicine> queryWrapper2 = new QueryWrapper<>() ;
                            queryWrapper2.eq("nationalDrugCode",s.getNationalDrugCode());
                            SbChinesePatentMedicine sbChinesePatentMedicine = sbChinesePatentMedicineMapper.selectOne(queryWrapper2) ;
                            if(null != sbChinesePatentMedicine){
                                //往备份表插数据
                                SbChinesePatentMedicineBackUp sbChinesePatentMedicineBackUp = new SbChinesePatentMedicineBackUp() ;
                                BeanUtil.copyProperties(sbChinesePatentMedicine,sbChinesePatentMedicineBackUp);
                                sbChinesePatentMedicineBackUp.setId(IdUtil.simpleUUID());
                                sbChinesePatentMedicineBackUp.setBatch(DateUtil.getDateFormat(new Date(),"yyyyMMdd"));
                                sbChinesePatentMedicineBackUp.setIs_del("0");
                                sbChinesePatentMedicineBackUp.insert() ;

                                //更新目录库数据
                                sbChinesePatentMedicine.deleteById() ;

                            }
                            //定时任务数据
                            s.setIs_del("1") ;
                            s.setIs_take_effect("1");
                            s.updateById() ;
                        }
                    }
                }
            }
        }

    }



    /**
    *西药列表已生效接口
    * Author wzn
    * Date 2023/2/1 14:26
    */
    public Page<SbWesternMedicine> selectList(SbWesternMedicine sbWesternMedicine) {
        Page<SbWesternMedicine> page = new Page<>(sbWesternMedicine.getPageNo(), sbWesternMedicine.getPageSize());
        QueryWrapper<SbWesternMedicine> queryWrapper = new QueryWrapper<>();
        if (!"".equals(sbWesternMedicine.getDrugNames()) && null != sbWesternMedicine.getDrugNames()) {
            queryWrapper.like("drugNames", sbWesternMedicine.getDrugNames());
        }
        if (!"".equals(sbWesternMedicine.getNationalDrugCode()) && null != sbWesternMedicine.getNationalDrugCode()) {
            queryWrapper.eq("nationalDrugCode", sbWesternMedicine.getNationalDrugCode());
        }
        Page<SbWesternMedicine> sbWesternMedicinePage = sbWesternMedicineMapper.selectPage(page, queryWrapper);
        return sbWesternMedicinePage;
    }


    /**
     *西药列表定时任务待生效接口
     * Author wzn
     * Date 2023/2/1 14:26
     */
    public Page<SbWesternMedicineBackUp> selectBackUpList(SbWesternMedicine sbWesternMedicine) {
        Page<SbWesternMedicineBackUp> page = new Page<>(sbWesternMedicine.getPageNo(), sbWesternMedicine.getPageSize());
        QueryWrapper<SbWesternMedicineBackUp> queryWrapper = new QueryWrapper<>();
        if (!"".equals(sbWesternMedicine.getDrugNames()) && null != sbWesternMedicine.getDrugNames()) {
            queryWrapper.like("drugNames", sbWesternMedicine.getDrugNames());
        }
        if (!"".equals(sbWesternMedicine.getNationalDrugCode()) && null != sbWesternMedicine.getNationalDrugCode()) {
            queryWrapper.eq("nationalDrugCode", sbWesternMedicine.getNationalDrugCode());
        }
        queryWrapper.isNull("is_del") ;
        Page<SbWesternMedicineBackUp> sbWesternMedicinePage = sbWesternMedicineBackUpMapper.selectPage(page, queryWrapper);
        return sbWesternMedicinePage;
    }


    /**
    *中成药导入
    * Author wzn
    * Date 2023/2/1 14:56
    */
    public void patentMedicineimportData(MultipartFile file,String year) {

        SysUser sysUser = sysUserService.getUser();
        try {
            InputStream in = file.getInputStream();
            //构建对象读取
            Excel07SaxReader reader = new Excel07SaxReader(patentHandler(sysUser.getUsername(),year));
            reader.read(in, 0);
            String nowTime = DateUtil.getDateFormat(new Date(), "yyyy-MM-dd");
            if(nowTime.equals(year)){
                chinTiming() ;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private RowHandler patentHandler(String sysUserName,String year) {
        return new RowHandler() {
            @Override
            public void handle(int sheetIndex, long rowIndex, List<Object> rowlist) {
                SbChinesePatentMedicineBackUp sbChinesePatentMedicineBackUp = null;

                if (rowIndex ==1) {
                    if(!rowlist.get(0).toString().contains("中成药")){
                        throw new CustomException("请上传中成药模板！");
                    }
                }
                if (rowIndex <3) {
                    return;
                }
                for (int i = 0; i < rowlist.size(); i++) {
                    sbChinesePatentMedicineBackUp = new SbChinesePatentMedicineBackUp();
                    sbChinesePatentMedicineBackUp.setYear(year);
                    sbChinesePatentMedicineBackUp.setIs_take_effect("0");

                    if (null != rowlist.get(1)) {
                        sbChinesePatentMedicineBackUp.setSortingCodeNumber(rowlist.get(1).toString());
                    } else {
                        sbChinesePatentMedicineBackUp.setSortingCodeNumber("");
                    }

                    if (null != rowlist.get(2)) {
                        sbChinesePatentMedicineBackUp.setCommon_name_code(rowlist.get(2).toString());

                    } else {
                        sbChinesePatentMedicineBackUp.setCommon_name_code("");
                    }

                    if (null != rowlist.get(3)) {
                        sbChinesePatentMedicineBackUp.setDrugNames(rowlist.get(3).toString());

                    } else {
                        sbChinesePatentMedicineBackUp.setDrugNames("");
                    }

                    if(rowlist.size()>4){
                        if (null != rowlist.get(4) && !"".equals(rowlist.get(4))) {
                            sbChinesePatentMedicineBackUp.setPaymentCategory(rowlist.get(4).toString());
                        } else {
                            sbChinesePatentMedicineBackUp.setPaymentCategory("");
                        }
                    }



                    if(rowlist.size()>5){
                        if (null != rowlist.get(5) && !"".equals(rowlist.get(5))) {
                            sbChinesePatentMedicineBackUp.setProductNameCoding(rowlist.get(5).toString());
                        } else {
                            sbChinesePatentMedicineBackUp.setProductNameCoding("");
                        }
                    }

                    if(rowlist.size()>6){
                        if (null != rowlist.get(6) && !"".equals(rowlist.get(6))) {
                            sbChinesePatentMedicineBackUp.setRegisteredName(rowlist.get(6).toString());
                        } else {
                            sbChinesePatentMedicineBackUp.setRegisteredName("");
                        }
                    }


                    if(rowlist.size()>7){
                        if (null != rowlist.get(7) && !"".equals(rowlist.get(7))) {
                            sbChinesePatentMedicineBackUp.setProductName(rowlist.get(7).toString());
                        } else {
                            sbChinesePatentMedicineBackUp.setProductName("");
                        }
                    }


                    if (rowlist.size()>8 ) {
                        if(null != rowlist.get(8) && !"".equals(rowlist.get(8))){
                            sbChinesePatentMedicineBackUp.setActualDosageForm(rowlist.get(8).toString());
                        }else {
                            sbChinesePatentMedicineBackUp.setActualDosageForm("");
                        }
                    }

                    if (rowlist.size()>9 ) {
                        if(null != rowlist.get(9) && !"".equals(rowlist.get(9))){
                            sbChinesePatentMedicineBackUp.setActualSpecification(rowlist.get(9).toString());
                        }else {
                            sbChinesePatentMedicineBackUp.setActualSpecification("");
                        }
                    }

                    if (rowlist.size()>10 ) {
                        if(null != rowlist.get(10) && !"".equals(rowlist.get(10))){
                            sbChinesePatentMedicineBackUp.setPackagingMaterial(rowlist.get(10).toString());
                        }else {
                            sbChinesePatentMedicineBackUp.setPackagingMaterial("");
                        }
                    }

                    if (rowlist.size()>11 ) {
                        if(null != rowlist.get(11) && !"".equals(rowlist.get(11))){
                            sbChinesePatentMedicineBackUp.setMinimumPackingQuantity(rowlist.get(11).toString());
                        }else {
                            sbChinesePatentMedicineBackUp.setMinimumPackingQuantity("");
                        }
                    }


                    if (rowlist.size()>12 ) {
                        if(null != rowlist.get(12) && !"".equals(rowlist.get(12))){
                            sbChinesePatentMedicineBackUp.setUnit(rowlist.get(12).toString());
                        }else {
                            sbChinesePatentMedicineBackUp.setUnit("");
                        }
                    }



                    if (rowlist.size()>13 ) {
                        if(null != rowlist.get(13) && !"".equals(rowlist.get(13))){
                            sbChinesePatentMedicineBackUp.setValorize(rowlist.get(13).toString());
                        }else {
                            sbChinesePatentMedicineBackUp.setValorize("");
                        }
                    }



                    if (rowlist.size()>14 ) {
                        if(null != rowlist.get(14) && !"".equals(rowlist.get(14))){
                            sbChinesePatentMedicineBackUp.setPurchaseCeilingPrice(rowlist.get(14).toString());
                        }else {
                            sbChinesePatentMedicineBackUp.setPurchaseCeilingPrice("");
                        }
                    }


                    if (rowlist.size()>15 ) {
                        if(null != rowlist.get(15) && !"".equals(rowlist.get(15))){
                            sbChinesePatentMedicineBackUp.setPaymentCriteria(rowlist.get(15).toString());
                        }else {
                            sbChinesePatentMedicineBackUp.setPaymentCriteria("");
                        }
                    }


                    if (rowlist.size()>16 ) {
                        if(null != rowlist.get(16) && !"".equals(rowlist.get(16))){
                            sbChinesePatentMedicineBackUp.setApprovalNumber(rowlist.get(16).toString());
                        }else {
                            sbChinesePatentMedicineBackUp.setApprovalNumber("");
                        }
                    }

                    if (rowlist.size()>17 ) {
                        if(null != rowlist.get(17) && !"".equals(rowlist.get(17))){
                            sbChinesePatentMedicineBackUp.setMedicineEnterprise(rowlist.get(17).toString());
                        }else {
                            sbChinesePatentMedicineBackUp.setMedicineEnterprise("");
                        }
                    }

                    if (rowlist.size()>18 ) {
                        if(null != rowlist.get(18) && !"".equals(rowlist.get(18))){
                            sbChinesePatentMedicineBackUp.setLimitPayment(rowlist.get(18).toString());
                        }else {
                            sbChinesePatentMedicineBackUp.setLimitPayment("");
                        }
                    }


                    if (rowlist.size()>19 ) {
                        if(null != rowlist.get(19) && !"".equals(rowlist.get(19))){
                            sbChinesePatentMedicineBackUp.setSerialNumber(rowlist.get(19).toString());
                        }else {
                            sbChinesePatentMedicineBackUp.setSerialNumber("");
                        }
                    }

                    if (rowlist.size()>20 ) {
                        if(null != rowlist.get(20) && !"".equals(rowlist.get(20))){
                            sbChinesePatentMedicineBackUp.setBidDeclarationNumber(rowlist.get(20).toString());
                        }else {
                            sbChinesePatentMedicineBackUp.setBidDeclarationNumber("");
                        }
                    }

                    if (rowlist.size()>21 ) {
                        if(null != rowlist.get(21) && !"".equals(rowlist.get(21))){
                            sbChinesePatentMedicineBackUp.setRemark(rowlist.get(21).toString());
                        }else {
                            sbChinesePatentMedicineBackUp.setRemark("");
                        }
                    }


                    if (rowlist.size()>22 ) {
                        if(null != rowlist.get(22) && !"".equals(rowlist.get(22))){
                            sbChinesePatentMedicineBackUp.setNationalDrugCode(rowlist.get(22).toString());
                        }else {
                            sbChinesePatentMedicineBackUp.setNationalDrugCode("");
                        }
                    }


                    if (rowlist.size()>23 ) {
                        if(null != rowlist.get(23) && !"".equals(rowlist.get(23))){
                            sbChinesePatentMedicineBackUp.setChangeType(rowlist.get(23).toString());
                        }else {
                            sbChinesePatentMedicineBackUp.setChangeType("");
                        }
                    }


                    if (rowlist.size()>24 ) {
                        if(null != rowlist.get(24) && !"".equals(rowlist.get(24))){
                            sbChinesePatentMedicineBackUp.setReasonsForChange(rowlist.get(24).toString());
                        }else {
                            sbChinesePatentMedicineBackUp.setReasonsForChange("");
                        }
                    }


                }
                if(StringUtils.isNotEmpty(sbChinesePatentMedicineBackUp.getSortingCodeNumber())){
                    if(StringUtils.isEmpty(sbChinesePatentMedicineBackUp.getChangeType())){
                        throw new CustomException("请完善变更类型！");
                    }
                    sbChinesePatentMedicineBackUp.insert() ;

                }


            }
        };
    }



    /**
    *中成药列表
    * Author wzn
    * Date 2023/2/1 15:10
    */
    public Page<SbChinesePatentMedicine> chinesePatentMedicineList(SbChinesePatentMedicine sbChinesePatentMedicine) {
        Page<SbChinesePatentMedicine> page = new Page<>(sbChinesePatentMedicine.getPageNo(), sbChinesePatentMedicine.getPageSize());
        QueryWrapper<SbChinesePatentMedicine> queryWrapper = new QueryWrapper<>();
        if (!"".equals(sbChinesePatentMedicine.getDrugNames()) && null != sbChinesePatentMedicine.getDrugNames()) {
            queryWrapper.like("drugNames", sbChinesePatentMedicine.getDrugNames());
        }
        if (!"".equals(sbChinesePatentMedicine.getNationalDrugCode()) && null != sbChinesePatentMedicine.getNationalDrugCode()) {
            queryWrapper.eq("nationalDrugCode", sbChinesePatentMedicine.getNationalDrugCode());
        }
        Page<SbChinesePatentMedicine> sbChinesePatentMedicinePage = sbChinesePatentMedicineMapper.selectPage(page, queryWrapper);
        return sbChinesePatentMedicinePage;
    }

    /**
     *中成药列表
     * Author wzn
     * Date 2023/2/1 15:10
     */
    public Page<SbChinesePatentMedicineBackUp> chinesePatentBackUpMedicineList(SbChinesePatentMedicine sbChinesePatentMedicine) {
        Page<SbChinesePatentMedicineBackUp> page = new Page<>(sbChinesePatentMedicine.getPageNo(), sbChinesePatentMedicine.getPageSize());
        QueryWrapper<SbChinesePatentMedicineBackUp> queryWrapper = new QueryWrapper<>();
        if (!"".equals(sbChinesePatentMedicine.getDrugNames()) && null != sbChinesePatentMedicine.getDrugNames()) {
            queryWrapper.like("drugNames", sbChinesePatentMedicine.getDrugNames());
        }
        if (!"".equals(sbChinesePatentMedicine.getNationalDrugCode()) && null != sbChinesePatentMedicine.getNationalDrugCode()) {
            queryWrapper.eq("nationalDrugCode", sbChinesePatentMedicine.getNationalDrugCode());
        }
        queryWrapper.isNull("is_del") ;
        Page<SbChinesePatentMedicineBackUp> sbChinesePatentMedicinePage = sbChinesePatentMedicineBackUpMapper.selectPage(page, queryWrapper);
        return sbChinesePatentMedicinePage;
    }





    /**
    *诊疗项目导入
    * Author wzn
    * Date 2023/2/2 9:12
    */
    public void projectimportData(MultipartFile file,String year) {

        SysUser sysUser = sysUserService.getUser();
        try {
            InputStream in = file.getInputStream();
            //构建对象读取
            Excel07SaxReader reader = new Excel07SaxReader(projectHandler(sysUser.getUsername(),year));
            reader.read(in, -1);
            String nowTime = DateUtil.getDateFormat(new Date(), "yyyy-MM-dd");
            if(nowTime.equals(year)){
                projectTiming() ;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private RowHandler projectHandler(String sysUserName,String year) {
        return new RowHandler() {
            @Override
            public void handle(int sheetIndex, long rowIndex, List<Object> rowlist) {
                SbProjectBackUp sbProjectBackUp = null;

                if (rowIndex ==0) {
                    if(!rowlist.get(0).toString().contains("诊疗项目")){
                        throw new CustomException("请上传诊疗项目模板！");
                    }
                }

                    if (rowIndex <2) {
                        return;
                    }
                    for (int i = 0; i < rowlist.size(); i++) {
                        sbProjectBackUp = new SbProjectBackUp();
                        sbProjectBackUp.setYear(year);
                        sbProjectBackUp.setIs_take_effect("0");


                        if (null != rowlist.get(1)) {
                            sbProjectBackUp.setProvincialProjectCode(rowlist.get(1).toString());
                        } else {
                            sbProjectBackUp.setProvincialProjectCode("");
                        }

                        if (null != rowlist.get(2)) {
                            sbProjectBackUp.setDirectoryCoding(rowlist.get(2).toString());
                        } else {
                            sbProjectBackUp.setDirectoryCoding("");
                        }

                        if (null != rowlist.get(3)) {
                            sbProjectBackUp.setDirectoryName(rowlist.get(3).toString());
                        } else {
                            sbProjectBackUp.setDirectoryName("");
                        }

                        if (null != rowlist.get(4)) {
                            sbProjectBackUp.setChargeUnit(rowlist.get(4).toString());

                        } else {
                            sbProjectBackUp.setChargeUnit("");
                        }

                        if(rowlist.size()>5){
                            if (null != rowlist.get(5) && !"".equals(rowlist.get(5))) {
                                sbProjectBackUp.setProjectConnotation(rowlist.get(5).toString());
                            } else {
                                sbProjectBackUp.setProjectConnotation("");
                            }
                        }

                        if(rowlist.size()>6){
                            if (null != rowlist.get(6) && !"".equals(rowlist.get(6))) {
                                sbProjectBackUp.setExcludedContent(rowlist.get(6).toString());
                            } else {
                                sbProjectBackUp.setExcludedContent("");
                            }
                        }

                        if(rowlist.size()>7){
                            if (null != rowlist.get(7) && !"".equals(rowlist.get(7))) {
                                sbProjectBackUp.setExplain(rowlist.get(7).toString());
                            } else {
                                sbProjectBackUp.setExplain("");
                            }
                        }

                        if(rowlist.size()>8){
                            if (null != rowlist.get(8) && !"".equals(rowlist.get(8))) {
                                sbProjectBackUp.setChargeType(rowlist.get(8).toString());
                            } else {
                                sbProjectBackUp.setChargeType("");
                            }
                        }


                        if(rowlist.size()>9){
                            if (null != rowlist.get(9) && !"".equals(rowlist.get(9))) {
                                sbProjectBackUp.setLevelOfChargeItem(rowlist.get(9).toString());
                            } else {
                                sbProjectBackUp.setLevelOfChargeItem("");
                            }
                        }


                        if (rowlist.size()>10 ) {
                            if(null != rowlist.get(10) && !"".equals(rowlist.get(10))){
                                sbProjectBackUp.setRestrictedUseSign(rowlist.get(10).toString());
                            }else {
                                sbProjectBackUp.setRestrictedUseSign("");
                            }
                        }

                        if (rowlist.size()>11 ) {
                            if(null != rowlist.get(11) && !"".equals(rowlist.get(11))){
                                sbProjectBackUp.setNonChildOne(rowlist.get(11).toString());
                            }else {
                                sbProjectBackUp.setNonChildOne("");
                            }
                        }

                        if (rowlist.size()>12 ) {
                            if(null != rowlist.get(12) && !"".equals(rowlist.get(12))){
                                sbProjectBackUp.setNonChildTwo(rowlist.get(12).toString());
                            }else {
                                sbProjectBackUp.setNonChildTwo("");
                            }
                        }


                        if (rowlist.size()>13 ) {
                            if(null != rowlist.get(13) && !"".equals(rowlist.get(13))){
                                sbProjectBackUp.setNonChildThree(rowlist.get(13).toString());
                            }else {
                                sbProjectBackUp.setNonChildThree("");
                            }
                        }



                        if (rowlist.size()>14 ) {
                            if(null != rowlist.get(14) && !"".equals(rowlist.get(14))){
                                sbProjectBackUp.setChildOne(rowlist.get(14).toString());
                            }else {
                                sbProjectBackUp.setChildOne("");
                            }
                        }



                        if (rowlist.size()>15 ) {
                            if(null != rowlist.get(15) && !"".equals(rowlist.get(15))){
                                sbProjectBackUp.setChildTwo(rowlist.get(15).toString());
                            }else {
                                sbProjectBackUp.setChildTwo("");
                            }
                        }


                        if (rowlist.size()>16 ) {
                            if(null != rowlist.get(16) && !"".equals(rowlist.get(16))){
                                sbProjectBackUp.setChildThree(rowlist.get(16).toString());
                            }else {
                                sbProjectBackUp.setChildThree("");
                            }
                        }


                        if (rowlist.size()>17 ) {
                            if(null != rowlist.get(17) && !"".equals(rowlist.get(17))){
                                sbProjectBackUp.setDisabledSoldier(rowlist.get(17).toString());
                            }else {
                                sbProjectBackUp.setDisabledSoldier("");
                            }
                        }

                        if (rowlist.size()>18 ) {
                            if(null != rowlist.get(18) && !"".equals(rowlist.get(18))){
                                sbProjectBackUp.setChangeCategory(rowlist.get(18).toString());
                            }else {
                                sbProjectBackUp.setChangeCategory("");
                            }
                        }

                        if (rowlist.size()>19 ) {
                            if(null != rowlist.get(19) && !"".equals(rowlist.get(19))){
                                sbProjectBackUp.setContentOfChange(rowlist.get(19).toString());
                            }else {
                                sbProjectBackUp.setContentOfChange("");
                            }
                        }
                        if (rowlist.size()>20 ) {
                            if(null != rowlist.get(20) && !"".equals(rowlist.get(20))){
                                sbProjectBackUp.setAddType(rowlist.get(20).toString());
                            }else {
                                sbProjectBackUp.setAddType("");
                            }
                        }

                    }


                if(StringUtils.isNotEmpty(sbProjectBackUp.getProvincialProjectCode())){
                    if(StringUtils.isEmpty(sbProjectBackUp.getChangeCategory())){
                        throw new CustomException("请完善变更类型！");
                    }
                    sbProjectBackUp.insert() ;
                }

            }
        };
    }


    /**
     *诊疗项目定时任务  往目录库刷新数据
     * Author wzn
     * Date 2023/3/1 14:11
     */
    public void projectTiming(){
        String nowTime = DateUtil.getDateFormat(new Date(), "yyyy-MM-dd");
        QueryWrapper queryWrapper = new QueryWrapper() ;
        queryWrapper.isNull("is_del") ;
        List<SbProjectBackUp> sbProjectBackUps = sbProjectBackUpMapper.selectList(queryWrapper) ;
        if(CollectionUtil.isNotEmpty(sbProjectBackUps)){
            for(SbProjectBackUp s:sbProjectBackUps){
                if(nowTime.equals(s.getYear())){
                    if(StringUtils.isNotEmpty(s.getChangeCategory())){
                        if("0".equals(s.getChangeCategory())){
                            SbZlProject sbZlProject  = new SbZlProject() ;
                            BeanUtil.copyProperties(s,sbZlProject);
                            sbZlProject.setId(IdUtil.simpleUUID());
                            sbZlProject.setCreateUser("定时任务");
                            sbZlProject.setCreateTime(new Date());
                            sbZlProject.setIs_del("0");
                            QueryWrapper<SbZlProject> queryWrapper2 = new QueryWrapper<>() ;
                            queryWrapper2.eq("provincialProjectCode",sbZlProject.getProvincialProjectCode()) ;
                            Long cout = sbZlProjectMapper.selectCount(queryWrapper2);
                            if(cout == 0 ){
                                sbZlProject.insert() ;
                            }
                            s.setIs_del("1") ;
                            s.setIs_take_effect("1");
                            s.updateById() ;
                        }else if("1".equals(s.getChangeCategory())){
                            QueryWrapper<SbZlProject> queryWrapper2 = new QueryWrapper<>() ;
                            queryWrapper2.eq("provincialProjectCode",s.getProvincialProjectCode());
                            SbZlProject sbZlProject = sbZlProjectMapper.selectOne(queryWrapper2) ;
                            if(null != sbZlProject){
                                //往备份表插数据
                                SbProjectBackUp sbProjectBackUp = new SbProjectBackUp() ;
                                BeanUtil.copyProperties(sbZlProject,sbProjectBackUp);
                                sbProjectBackUp.setId(IdUtil.simpleUUID());
                                sbProjectBackUp.setIs_del("0");
                                sbProjectBackUp.setBatch(DateUtil.getDateFormat(new Date(),"yyyyMMdd"));
                                sbProjectBackUp.insert() ;


                                //更新目录库数据
                                sbZlProject.setProvincialProjectCode(s.getProvincialProjectCode()) ;
                                sbZlProject.setDirectoryCoding(s.getDirectoryCoding()) ;
                                sbZlProject.setDirectoryName(s.getDirectoryName()) ;
                                sbZlProject.setChargeUnit(s.getChargeUnit()) ;
                                sbZlProject.setProjectConnotation(s.getProjectConnotation()) ;
                                sbZlProject.setExcludedContent(s.getExcludedContent()) ;
                                sbZlProject.setExplain(s.getExplain()) ;
                                sbZlProject.setChargeType(s.getChargeType()) ;
                                sbZlProject.setLevelOfChargeItem(s.getLevelOfChargeItem()) ;
                                sbZlProject.setRestrictedUseSign(s.getRestrictedUseSign()) ;
                                sbZlProject.setNonChildOne(s.getNonChildOne()) ;
                                sbZlProject.setNonChildTwo(s.getNonChildTwo()) ;
                                sbZlProject.setNonChildThree(s.getNonChildThree()) ;
                                sbZlProject.setChildOne(s.getChildOne()) ;
                                sbZlProject.setChildTwo(s.getChildTwo()) ;
                                sbZlProject.setChildThree(s.getChildThree()) ;
                                sbZlProject.setDisabledSoldier(s.getDisabledSoldier()) ;
                                sbZlProject.setContentOfChange(s.getContentOfChange()) ;
                                sbZlProject.setIs_autonomy(s.getIs_autonomy()) ;
                                sbZlProject.setAddType(s.getAddType()); ;
                                sbZlProject.setUpdateTime(new Date());
                                sbZlProject.updateById() ;
                            }
                            //定时任务数据
                            s.setIs_del("1") ;
                            s.setIs_take_effect("1");
                            s.updateById() ;

                        }else if("2".equals(s.getChangeCategory())){
                            QueryWrapper<SbZlProject> queryWrapper2 = new QueryWrapper<>() ;
                            queryWrapper2.eq("provincialProjectCode",s.getProvincialProjectCode());
                            SbZlProject sbZlProject = sbZlProjectMapper.selectOne(queryWrapper2) ;
                            if(null != sbZlProject){
                                //往备份表插数据
                                SbProjectBackUp sbProjectBackUp = new SbProjectBackUp() ;
                                BeanUtil.copyProperties(sbZlProject,sbProjectBackUp);
                                sbProjectBackUp.setId(IdUtil.simpleUUID());
                                sbProjectBackUp.setBatch(DateUtil.getDateFormat(new Date(),"yyyyMMdd"));
                                sbProjectBackUp.setIs_del("0");
                                sbProjectBackUp.insert() ;

                                //更新目录库数据
                                sbZlProject.deleteById() ;

                            }
                            //定时任务数据
                            s.setIs_del("1") ;
                            s.setIs_take_effect("1");
                            s.updateById() ;
                        }
                    }
                }
            }
        }

    }


    /**
    *诊疗列表接口
    * Author wzn
    * Date 2023/2/2 14:05
    */
    public Page<SbZlProject> zlProjectMedicineList(SbZlProject zlProject) {
        Page<SbZlProject> page = new Page<>(zlProject.getPageNo(), zlProject.getPageSize());
        QueryWrapper<SbZlProject> queryWrapper = new QueryWrapper<>();
        if (!"".equals(zlProject.getDirectoryName()) && null != zlProject.getDirectoryName()) {
            queryWrapper.like("directoryName", zlProject.getDirectoryName());
        }
        if (!"".equals(zlProject.getProvincialProjectCode()) && null != zlProject.getProvincialProjectCode()) {
            queryWrapper.eq("provincialProjectCode", zlProject.getProvincialProjectCode());
        }
        if (!"".equals(zlProject.getAddType()) && null != zlProject.getAddType() && "1".equals(zlProject.getAddType())) {
            queryWrapper.eq("addType", "1");
        }
        if (!"".equals(zlProject.getAddType()) && null != zlProject.getAddType() && "0".equals(zlProject.getAddType())) {
            queryWrapper.and(
                    x -> x.ne("addType", "1")
                            .or().isNull("addType")
            );
        }

        Page<SbZlProject> sbZlProjectPage = sbZlProjectMapper.selectPage(page, queryWrapper);
        return sbZlProjectPage;
    }

    /**
     *诊疗列表定时任务待生效数据接口
     * Author wzn
     * Date 2023/2/2 14:05
     */
    public Page<SbProjectBackUp> zlProjectBackUpMedicineList(SbZlProject zlProject) {
        Page<SbProjectBackUp> page = new Page<>(zlProject.getPageNo(), zlProject.getPageSize());
        QueryWrapper<SbProjectBackUp> queryWrapper = new QueryWrapper<>();
        if (!"".equals(zlProject.getDirectoryName()) && null != zlProject.getDirectoryName()) {
            queryWrapper.like("directoryName", zlProject.getDirectoryName());
        }
        if (!"".equals(zlProject.getProvincialProjectCode()) && null != zlProject.getProvincialProjectCode()) {
            queryWrapper.eq("provincialProjectCode", zlProject.getProvincialProjectCode());
        }
        if (!"".equals(zlProject.getAddType()) && null != zlProject.getAddType() && "1".equals(zlProject.getAddType())) {
            queryWrapper.eq("addType", "1");
        }
        if (!"".equals(zlProject.getAddType()) && null != zlProject.getAddType() && "0".equals(zlProject.getAddType())) {
            queryWrapper.and(
                    x -> x.ne("addType", "1")
                            .or().isNull("addType")
            );
        }
        queryWrapper.isNull("is_del") ;
        Page<SbProjectBackUp> sbZlProjectPage = sbProjectBackUpMapper.selectPage(page, queryWrapper);
        return sbZlProjectPage;
    }



}
