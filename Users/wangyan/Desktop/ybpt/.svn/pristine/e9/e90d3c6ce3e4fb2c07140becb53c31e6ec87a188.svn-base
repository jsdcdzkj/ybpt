package com.jsdc.ybpt.service;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.DiseaseMutexMapper;
import com.jsdc.ybpt.mapper.OpspDiseListBMapper;
import com.jsdc.ybpt.mapper.OpspDiseMapper;
import com.jsdc.ybpt.mapper.RegistInfoMapper;
import com.jsdc.ybpt.model.*;
import com.jsdc.ybpt.util.IdCardNumberMethod;
import com.jsdc.ybpt.util.exception.CustomException;
import com.jsdc.ybpt.vo.OpspDiseListBVo;
import com.jsdc.ybpt.vo.OpspRegDVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
public class OpspDiseListBService extends BaseService<BizReconciliation> {

    @Autowired
    private OpspDiseListBMapper opspDiseListBMapper;

    @Autowired
    private OpspDiseMapper opspDiseMapper;

    @Autowired
    private RegistInfoMapper registInfoMapper ;
    @Autowired
    private SysDictService sysDictService;

    @Autowired
    private SysUserService sysUserService ;

    @Value("${printPath}")
    private String printPath;

    @Autowired
    private DiseaseMutexMapper diseaseMutexMapper ;


    /**
     * @return
     */
    @DS("reflowData")
    public Page<OpspDiseListBVo> OpspDiseList(OpspDiseListBVo opspDiseListBVo) {
        //统计回流库数据
        Page<OpspDiseListBVo> page = new Page<>(opspDiseListBVo.getPageNo(), opspDiseListBVo.getPageSize());
        Page<OpspDiseListBVo> opspDiseListBVoPage = opspDiseListBMapper.OpspDiseList(page, opspDiseListBVo);
        return opspDiseListBVoPage;
    }

    /**
     * 门慢门特新增
     * Author wzn
     * Date 2022/4/28 10:25
     */
    public String add(OpspDise opspDise) {
        SysUser sysUser = sysUserService.getUser() ;
        if("310".equals(opspDise.getInsutype())){
            opspDise.setInsutypeName("职工基本医疗保险");
        }else if("390".equals(opspDise.getInsutype())){
            opspDise.setInsutypeName("城乡居民基本医疗保险");
        }

        //查询统筹区
        HashMap<String, String> tcqMap = new HashMap();
        List<SysDict> tcq = sysDictService.list(new QueryWrapper<SysDict>().eq("dict_type", "ADMDVS").eq("is_del", "0"));
        tcq.forEach(x -> tcqMap.put(x.getValue(), x.getLabel()));
        //子表新增数据
        if (opspDise.getInformationList().size() != 0 && null != opspDise.getInformationList()) {
            for (RegistrationInformation r : opspDise.getInformationList()) {
                opspDise.setInsu_admdvs_name(tcqMap.get(opspDise.getInsu_admdvs()));
                opspDise.setCreateTime(new Date());
                opspDise.setCreateUser(sysUser.getOrg_code());
                opspDise.setId("");
                opspDise.setIs_del("0");
                opspDise.setApprovalStatus("0");
                opspDise.insert();
                r.setOpsp_id(opspDise.getId());
                r.insert();
            }
        }
        return opspDise.getId();
    }


    /**
    *门特门慢登记列表
    * Author wzn
    * Date 2022/4/29 13:45
    */
    public Page<OpspDise> selectDiseList(OpspDise opspDise){
        SysUser sysUser = sysUserService.getUser() ;
        opspDise.setCreateUser(sysUser.getOrg_code());
        opspDise.setMedins_code(sysUser.getOrg_code());
        Page<OpspDise> page = new Page<>(opspDise.getPageNo(), opspDise.getPageSize());
        return opspDiseMapper.selectDiseList(page,opspDise) ;
    }

    /**
    *门慢门特审核列表
    * Author wzn
    * Date 2022/5/12 15:28
    */
    public Page<OpspDise> opspCheckList(OpspRegDVo opspRegDVo){
        Page<OpspDise> page = new Page<>(opspRegDVo.getPageNo(), opspRegDVo.getPageSize());
        return opspDiseMapper.opspCheckList(page,opspRegDVo) ;
    }

    @DS("reflowData")
    public List<OpspDise> queryAssociation(String certNo,String opsp_dise_code) {
        //统计回流库数据
        List<OpspDise> opspDiseList = opspDiseMapper.queryAssociation(certNo,opsp_dise_code);
        return opspDiseList;
    }

    @DS("reflowData")
    public List<OpspDiseListBVo> selectOpspREGByCertNo(String certNo) {
        //统计回流库数据
        List<OpspDiseListBVo> opspDiseList = opspDiseMapper.selectOpspREGByCertNo(certNo);
        return opspDiseList;
    }


    public List<OpspDise> getList(){
        QueryWrapper<OpspDise> queryWrapper = new QueryWrapper<>() ;
        queryWrapper.eq("approvalStatus","0") ;//审核状态 0未审核
        queryWrapper.eq("is_del","0") ;//0  未删除
        queryWrapper.isNull("associationId") ;
        List<OpspDise> opspDiseList = opspDiseMapper.selectList(queryWrapper) ;
        return opspDiseList ;
    }

    public void updateOpdise(OpspDise opspDise){
        opspDiseMapper.updateById(opspDise) ;
    }


    /**
    *新增校验  同一个人  未审核只能有一个
    * Author wzn
    * Date 2022/5/9 15:30
    */
    public void checkAdd(OpspDise opspDise){
        //查此人是否有未审核的数据
        QueryWrapper<OpspDise> queryWrapper = new QueryWrapper<>() ;
        queryWrapper.eq("certno",opspDise.getCertno()) ;
        queryWrapper.eq("approvalStatus","0") ;
        Map<String,String> map = new HashMap<>() ;
        List<RegistrationInformation> registrationInformationList = opspDise.getInformationList() ;
        for(RegistrationInformation r:registrationInformationList){
            map.put(r.getOpsp_dise_code(),r.getOpsp_dise_name()) ;
        }
        List<OpspDise> opspDiseList = opspDiseMapper.selectList(queryWrapper) ;
        if(opspDiseList.size()>0){
            for(OpspDise o:opspDiseList){
                QueryWrapper<RegistrationInformation> queryWrapper1  = new QueryWrapper<>() ;
                queryWrapper1.eq("opsp_id", o.getId()) ;
                List<RegistrationInformation> registrationInformations = registInfoMapper.selectList(queryWrapper1) ;
                String name = map.get(registrationInformations.get(0).getOpsp_dise_code()) ;
                if(name != "" && name != null){
                    throw new CustomException("当前申请人有未完成的业务，请等待业务结束");
                }
            }
        }
    }

    /**
    *门慢特登记审核接口
    * Author wzn
    * Date 2022/5/12 17:08
    */
    public void checkResult(OpspDise opspDise){
        if("0".equals(opspDise.getCheckRusult())){
            opspDise.setApprovalStatus("1");
        }else if("1".equals(opspDise.getCheckRusult())){
            opspDise.setApprovalStatus("3");
        }
        opspDiseMapper.updateById(opspDise) ;
    }


    /**
    *门慢特登记撤销接口
    * Author wzn
    * Date 2022/5/17 11:06
    */
    public void revoke(String id){
        OpspDise opspDise = new OpspDise() ;
        opspDise.setApprovalStatus("2");
        opspDise.setIs_del("1");
        opspDise.setId(id);
        opspDiseMapper.updateById(opspDise) ;
    }


    /**
    *门慢特打印导出Excel
    * Author wzn
    * Date 2022/5/20 10:00
    */
    public String opspDisePrint(String id) {
        OpspDise opspDise = opspDiseMapper.selectById(id);
        String file_name = "" ;
        int age = IdCardNumberMethod.getAgeForIdcard(opspDise.getCertno()) ;
        if(opspDise.getGend().equals("1")){
            opspDise.setGend("男");
        }else {
            opspDise.setGend("女");
        }
        QueryWrapper<RegistrationInformation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("opsp_id", id);
        List<RegistrationInformation> informationList = registInfoMapper.selectList(queryWrapper);

//        TemplateExportParams params = new TemplateExportParams("print/徐州市基本医疗保险门特、门慢、两病评估表2022.5.18.xls");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("opspDise", opspDise);
        map.put("age", age);
        map.put("opspDiseList", informationList.get(0));
//        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        File savefile = new File(printPath+"/"+opspDise.getCertno());
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        FileOutputStream fos = null;
        try {
             file_name = UUID.randomUUID().toString().replaceAll("-", "") + ".xls";
            fos = new FileOutputStream(printPath+"/"+file_name);
//            workbook.write(fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file_name ;
    }


    /**
    *先查页面登记的病种列表是否存在互斥
    * Author wzn
    * Date 2022/11/10 11:18
    */
    public void mutuallyExclusiveOrNot(List<OpspDiseListBVo> opspDiseListBVoList,boolean ishc){
        //先查页面登记的病种列表是否存在互斥
        for(OpspDiseListBVo o:opspDiseListBVoList){
            QueryWrapper<DiseaseMutex> queryWrapper = new QueryWrapper<>() ;
            queryWrapper.eq("dise_code",o.getOpsp_dise_code()) ;
            queryWrapper.eq("insured_type",o.getInsutype()) ;
            List<DiseaseMutex> diseaseMutexList = diseaseMutexMapper.selectList(queryWrapper) ;
            for(DiseaseMutex d:diseaseMutexList){
                for(OpspDiseListBVo op:opspDiseListBVoList){
                    if(d.getDise_mutex_code().equals(op.getOpsp_dise_code())){
                        ishc = true ;
                    }
                }
            }
        }


    }


    /**
    *查以往门慢特登记审核通过的是否存在互斥
    * Author wzn
    * Date 2022/11/10 14:28
    */
    public List<OpspDiseListBVo> mutuallyExclusiveOrNot2(String certNo){
        //已经登记的门慢特  互斥信息
        List<OpspDiseListBVo> opspDiseListBVos = opspDiseMapper.selectOpspREGByCertNo(certNo) ;
        return opspDiseListBVos ;
    }


    public List<OpspDiseListBVo> mutuallyExclusiveOrNot3(String certNo){

        //已经登记的病种
        List<OpspDiseListBVo> opspDiseListBVos2 = opspDiseMapper.selectBydiseByCertNo(certNo) ;

        return opspDiseListBVos2 ;
    }

}
