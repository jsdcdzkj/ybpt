package com.jsdc.ybpt.service.eval;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.poi.excel.sax.Excel07SaxReader;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.eval_.*;
import com.jsdc.ybpt.mapper.FileInfoMapper;
import com.jsdc.ybpt.mapper.FixmedinsBMapper;
import com.jsdc.ybpt.mapper.MedinsDeptBMapper;
import com.jsdc.ybpt.mapper.SysUserMapper;
import com.jsdc.ybpt.mapper.eval.*;
import com.jsdc.ybpt.model.*;
import com.jsdc.ybpt.service.SysDictService;
import com.jsdc.ybpt.service.SysUserService;
import com.jsdc.ybpt.util.FastDfs.FastDfsParams;
import com.jsdc.ybpt.util.FastDfs.FastDfsUtil;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.util.exception.CustomException;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * (EvalOrgDetail)表服务接口
 *
 * @author wangyan
 * @since 2023-11-17 10:44:44
 */
@Service
public class EvalOrgDetailService extends BaseService<EvalOrgDetail> {

    @Autowired
    private SysUserService sysUserService ;

    @Autowired
    private FileInfoMapper fileInfoMapper ;

    @Autowired
    private FastDfsUtil fastDfsUtil;

    @Autowired
    private EvalTaskManageMapper evalTaskManageMapper ;

    @Autowired
    private EvalStardardUserMapper evalStardardUserMapper ;

    @Autowired
    private EvalEarnestMoneyMapper evalEarnestMoneyMapper ;

    @Autowired
    private FixmedinsBMapper fixmedinsBMapper ;

    @Autowired
    private EvalOrgTaskMapper evalOrgTaskMapper ;


    @Autowired
    private EvalDesignMapper evalDesignMapper ;

    @Autowired
    private MedinsDeptBMapper medinsDeptBMapper ;


    @Autowired
    private SysUserMapper sysUserMapper ;

    @Autowired
    private EvalFormulaConfigService evalFormulaConfigService ;

    @Autowired
    private EvalCategoryStardardMapper evalCategoryStardardMapper ;

    @Autowired
    private EvalOrgDetailMapper evalOrgDetailMapper ;


    @Autowired
    private EvalMethodInfoMapper evalMethodInfoMapper ;


    @Autowired
    private EvalStardardMethodMapper evalStardardMethodMapper ;

    @Autowired
    private SysDictService sysDictService ;



    /**
    *根据科室获取所有负责人接口
    * Author wzn
    * Date 2023/11/17 14:20
    */
    public List<SysUser> getUserList(String deptCode){
        SysUser sysUser = sysUserService.getUser() ;
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>() ;
        queryWrapper.eq("org_code",sysUser.getOrg_code()) ;
        queryWrapper.eq("dept_code",deptCode) ;
        queryWrapper.eq("is_del","0") ;
        queryWrapper.eq("user_type","1") ;
        return  sysUserService.list(queryWrapper) ;
    }

    /**
    *考核任务管理新增接口
    * Author wzn
    * Date 2023/11/17 15:07
    */
    @Transactional
    public void addTaskManage(EvalTaskManage evalTaskManage){

        SysUser sysUser = sysUserService.getUser();
        evalTaskManage.setAdmdvs(sysUser.getOrg_code());
        evalTaskManage.setCreateUser(sysUser.getUsername());
        evalTaskManage.setCreateTime(new Date());
        evalTaskManage.setIsDel("0");
        evalTaskManage.setStatus("0");
        evalTaskManage.setEarnestMoneyShow("0");
        evalTaskManage.setMoneyUpload("0");

        //
        EvalDesign evalDesign = new EvalDesign() ;
        evalDesign.setId(evalTaskManage.getAssessmentId());
        EvalDesign evalDesign1 = evalDesign.selectById() ;

        evalTaskManage.setCategory(evalDesign1.getCategory());
        evalTaskManage.setAggrementLv(evalDesign1.getAggrementLv());
        if(StringUtils.isNotEmpty(evalDesign1.getNatures())){
            evalTaskManage.setNatures(evalDesign1.getNatures());
        }

        if(StringUtils.isNotEmpty(evalDesign1.getSpirit())){
            evalTaskManage.setSpirit(evalDesign1.getSpirit());
        }

        evalTaskManage.insert();

        //插子表数据  前端封装 考核任务指标ID、 负责人 数组对象
        if(CollectionUtil.isNotEmpty(evalTaskManage.getEvalStardardUserList())){
            for(EvalStardardUser e:evalTaskManage.getEvalStardardUserList()){
                e.setTaskManageId(evalTaskManage.getId());
                e.insert() ;
            }
        }

    }



    /**
     *考核任务管理修改接口
     * Author wzn
     * Date 2023/11/17 15:07
     */
    @Transactional
    public void updTaskManage(EvalTaskManage evalTaskManage){

        SysUser sysUser = sysUserService.getUser();
        evalTaskManage.setCreateUser(sysUser.getUsername());
        evalTaskManage.setCreateTime(new Date());
        evalTaskManage.setIsDel("0");
        evalTaskManage.updateById();

        //先删子表数据
        QueryWrapper<EvalStardardUser> queryWrapper = new QueryWrapper<>() ;
        queryWrapper.eq("taskManageId",evalTaskManage.getId()) ;
        evalStardardUserMapper.delete(queryWrapper) ;

        //插子表数据  前端封装 考核任务指标ID、 负责人 数组对象
        if(CollectionUtil.isNotEmpty(evalTaskManage.getEvalStardardUserList())){
            for(EvalStardardUser e:evalTaskManage.getEvalStardardUserList()){
                e.setTaskManageId(evalTaskManage.getId());
                e.setDesignId(evalTaskManage.getAssessmentId());
                e.insert() ;
            }
        }

    }

    /**
    *机构上传接口
    * Author wzn
    * Date 2023/11/20 9:30
    */
    @Transactional
    public void uploadOrgFile(List<MultipartFile> file, String id) {

        for(MultipartFile m:file){
            //清除文件
            List<FileInfo> fileInfos = fileInfoMapper.selectList(Wrappers.<FileInfo>lambdaQuery()
                    .eq(FileInfo::getBizId, id)
                    .eq(FileInfo::getBizType, "16")
            );
            for (FileInfo fileInfo : fileInfos) {
                fastDfsUtil.deleteFile(fileInfo);
//                fileInfo.deleteById() ;
            }

            EvalTaskManage evalTaskManage = new EvalTaskManage() ;
            evalTaskManage.setId(id);
            EvalTaskManage evalTaskManage1 = evalTaskManage.selectById() ;

            //删掉原先的数据
            EvalEarnestMoney evalEarnestMoney  = new EvalEarnestMoney() ;
            QueryWrapper<EvalEarnestMoney> queryWrapper = new QueryWrapper<>() ;
            queryWrapper.eq("taskManageId",id) ;
            evalEarnestMoney.delete(queryWrapper) ;

            //导入文件
            try {
                InputStream in = m.getInputStream();
                //构建对象读取



                Excel07SaxReader reader = new Excel07SaxReader(createRowHandler(id));
                reader.read(in, -1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(!"0".equals(evalTaskManage1.getStatus())){
                this.publishTaskMange(id) ;
            }

            //上传文件服务器

            FastDfsParams params = new FastDfsParams("eval/"+evalTaskManage1.getYear()+"/org", m, "16", id);
            params.setFileName(m.getOriginalFilename());
            ResultInfo resultInfo = fastDfsUtil.uploadFile(params);
            if (resultInfo.getCode() != 0) {
                throw new CustomException(resultInfo.getMsg()) ;
            }

        }
    }



    private RowHandler createRowHandler(String id) {

        return new RowHandler() {
            @Override
            public void handle(int sheetIndex, long rowIndex, List<Object> rowlist) {
                EvalEarnestMoney evalEarnestMoney  = null;
                if (rowIndex == 0) {
                    return;
                }
                int s = rowlist.size();
                for (int i = 0; i < rowlist.size(); i++) {
                    evalEarnestMoney = new EvalEarnestMoney();
                    evalEarnestMoney.setId(IdUtil.simpleUUID());

                    if (null != rowlist.get(0)) {
                        evalEarnestMoney.setOrgCode(rowlist.get(0).toString());
                    } else {
                        throw new CustomException("请完善机构编码！");
                    }

//                    if (null != rowlist.get(1)) {
//                        evalEarnestMoney.setEarnestMoney(rowlist.get(1).toString());
//
//                    } else {
//                        throw new CustomException("请完善保证金！");
//                    }
                }

//                if(!isNumeric(rowlist.get(1).toString())){
//                    throw new CustomException("保证金只能为数字！");
//                }
                if(StringUtils.isNotEmpty(evalEarnestMoney.getOrgCode())){
                    QueryWrapper<FixmedinsB> fixmedinsBQueryWrapper = new QueryWrapper<>() ;
                    fixmedinsBQueryWrapper.eq("fixmedins_code",evalEarnestMoney.getOrgCode()) ;
                    FixmedinsB fixmedinsB = fixmedinsBMapper.selectOne(fixmedinsBQueryWrapper) ;
                    if(null != fixmedinsB){
                        evalEarnestMoney.setTaskManageId(id);
                        evalEarnestMoney.insert();
                    }else {
                        throw new CustomException(evalEarnestMoney.getOrgCode()+"未能匹配到该机构信息,请先维护机构信息");
                    }

                }

            }
        };
    }





    public void uploadMoneyFile(List<MultipartFile> file, String id) {

        for(MultipartFile m:file){
            //清除文件
            List<FileInfo> fileInfos = fileInfoMapper.selectList(Wrappers.<FileInfo>lambdaQuery()
                    .eq(FileInfo::getBizId, id)
                    .eq(FileInfo::getBizType, "17")
            );
            for (FileInfo fileInfo : fileInfos) {
                fastDfsUtil.deleteFile(fileInfo);
            }
            //上传文件服务器
            EvalTaskManage evalTaskManage = new EvalTaskManage() ;
            evalTaskManage.setId(id);
            EvalTaskManage evalTaskManage1 = evalTaskManage.selectById() ;
            FastDfsParams params = new FastDfsParams("eval/"+evalTaskManage1.getYear()+"/money", m, "17", id);
            params.setFileName(m.getOriginalFilename());
            ResultInfo resultInfo = fastDfsUtil.uploadFile(params);
            if (resultInfo.getCode() != 0) {
                throw new CustomException(resultInfo.getMsg()) ;
            }



            //导入文件
            try {
                InputStream in = m.getInputStream();
                //构建对象读取
                Excel07SaxReader reader = new Excel07SaxReader(createRowHandler3(id));
                reader.read(in, -1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //算总保证金金额
            BigDecimal staffSumEarnestMoney = new BigDecimal(0);
            BigDecimal residentSumEarnestMoney = new BigDecimal(0);
            BigDecimal medEarnestMoney = new BigDecimal(0);

            QueryWrapper<EvalEarnestMoney> queryWrapper1 = new QueryWrapper<>() ;
            queryWrapper1.eq("taskManageId",id) ;
            List<EvalEarnestMoney> evalEarnestMonies = evalEarnestMoneyMapper.selectList(queryWrapper1) ;
            if(CollectionUtil.isNotEmpty(evalEarnestMonies)){
                for(EvalEarnestMoney e:evalEarnestMonies){
                    if(StringUtils.isEmpty(e.getStaffEarnestMoney())){
                        e.setStaffEarnestMoney("0.00") ;
                    }

                    if(StringUtils.isEmpty(e.getResidentEarnestMoney())){
                        e.setResidentEarnestMoney("0.00") ;
                    }

                    if(StringUtils.isEmpty(e.getMedEarnestMoney())){
                        e.setMedEarnestMoney("0.00") ;
                    }

                    //更新机构保证金
                    EvalOrgTask evalOrgTask = new EvalOrgTask() ;
                    evalOrgTask.setMarginStaff(e.getStaffEarnestMoney()) ;
                    evalOrgTask.setMarginResident(e.getResidentEarnestMoney()) ;
                    evalOrgTask.setMarginMedical(e.getMedEarnestMoney()) ;

                    QueryWrapper<EvalOrgTask> queryWrapper = new QueryWrapper<>() ;
                    queryWrapper.eq("taskManageId",id) ;
                    queryWrapper.eq("orgCode",e.getOrgCode()) ;
                    evalOrgTaskMapper.update(evalOrgTask,queryWrapper) ;

                    BigDecimal earnestMoney = new BigDecimal(e.getStaffEarnestMoney());
                    BigDecimal money2 = new BigDecimal(e.getResidentEarnestMoney());
                    BigDecimal money3 = new BigDecimal(e.getMedEarnestMoney());

                    staffSumEarnestMoney = staffSumEarnestMoney.add(earnestMoney) ;
                    residentSumEarnestMoney = residentSumEarnestMoney.add(money2) ;
                    medEarnestMoney = medEarnestMoney.add(money3) ;
                }
                //更新总保证金
                evalTaskManage1.setStaffSumEarnestMoney(staffSumEarnestMoney.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                evalTaskManage1.setResidentSumEarnestMoney(residentSumEarnestMoney.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                evalTaskManage1.setMedSumEarnestMoney(medEarnestMoney.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                evalTaskManage1.setMoneyUpload("1");
                evalTaskManage1.updateById() ;



            }

        }
    }


    private RowHandler createRowHandler3(String id) {

        return new RowHandler() {
            @Override
            public void handle(int sheetIndex, long rowIndex, List<Object> rowlist) {
                EvalEarnestMoney evalEarnestMoney  = null;
                if (rowIndex == 0) {
                    return;
                }
                int s = rowlist.size();
                    evalEarnestMoney = new EvalEarnestMoney();

                    if (null != rowlist.get(0)) {
                        evalEarnestMoney.setOrgCode(rowlist.get(0).toString());
                    } else {
                        throw new CustomException("请完善机构编码！");
                    }

//                    evalEarnestMoney.setMedEarnestMoney(new BigDecimal(rowlist.get(3).toString()).toString());

                if(StringUtils.isNotEmpty(rowlist.get(1)+"") && null !=rowlist.get(1) ){
                    evalEarnestMoney.setStaffEarnestMoney(new BigDecimal(rowlist.get(1).toString()).toString());
                    if(!isNumeric(evalEarnestMoney.getStaffEarnestMoney())){
                        throw new CustomException("职工保证金只能为数字！");
                    }
                }


                if(StringUtils.isNotEmpty(rowlist.get(2)+"") && null !=rowlist.get(2) ){
                    evalEarnestMoney.setResidentEarnestMoney(new BigDecimal(rowlist.get(2).toString()).toString());
                    if(!isNumeric(evalEarnestMoney.getResidentEarnestMoney())){
                        throw new CustomException("居民保证金只能为数字！");
                    }

                }

                if(StringUtils.isNotEmpty(rowlist.get(3)+"") && null !=rowlist.get(3) ){
                    evalEarnestMoney.setMedEarnestMoney(new BigDecimal(rowlist.get(3).toString()).toString());
                    if(!isNumeric(evalEarnestMoney.getMedEarnestMoney())){
                        throw new CustomException("医疗救助保证金只能为数字！");
                    }

                }



                if(StringUtils.isNotEmpty(evalEarnestMoney.getOrgCode())){
                    QueryWrapper<FixmedinsB> fixmedinsBQueryWrapper = new QueryWrapper<>() ;
                    fixmedinsBQueryWrapper.eq("fixmedins_code",evalEarnestMoney.getOrgCode()) ;
                    FixmedinsB fixmedinsB = fixmedinsBMapper.selectOne(fixmedinsBQueryWrapper) ;
                    if(null == fixmedinsB){
                        throw new CustomException(evalEarnestMoney.getOrgCode()+"未能匹配到该机构信息,请先维护机构信息");
                    }else {
                        QueryWrapper<EvalEarnestMoney> queryWrapper = new QueryWrapper<>() ;
                        queryWrapper.eq("taskManageId",id) ;
                        queryWrapper.eq("orgCode",evalEarnestMoney.getOrgCode()) ;
                        EvalEarnestMoney evalEarnestMoney1 = evalEarnestMoneyMapper.selectOne(queryWrapper) ;

                        if(null != evalEarnestMoney1){
                            if(StringUtils.isNotEmpty(evalEarnestMoney.getStaffEarnestMoney())){
                                evalEarnestMoney1.setStaffEarnestMoney(new BigDecimal(evalEarnestMoney.getStaffEarnestMoney()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());

                            }
                            if(StringUtils.isNotEmpty(evalEarnestMoney.getResidentEarnestMoney())){
                                evalEarnestMoney1.setResidentEarnestMoney(new BigDecimal(evalEarnestMoney.getResidentEarnestMoney()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());

                            }
                            if(StringUtils.isNotEmpty(evalEarnestMoney.getMedEarnestMoney())){
                                evalEarnestMoney1.setMedEarnestMoney(new BigDecimal(evalEarnestMoney.getMedEarnestMoney()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                            }
                            evalEarnestMoney1.updateById();
                        }

                    }

                }

            }
        };
    }

    public static boolean isNumeric(String str){

        Pattern pattern = Pattern.compile("[0-9]*\\.?[0-9]+");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
    *保证金状态修改接口
    * Author wzn
    * Date 2023/11/20 10:31
    */
    public void updateMoneyShow(EvalTaskManage e){
        EvalTaskManage evalTaskManage = new EvalTaskManage() ;
        evalTaskManage.setId(e.getId());
        evalTaskManage.setEarnestMoneyShow(e.getEarnestMoneyShow());



        evalTaskManage.updateById() ;
    }


    public void updatecsStatus(String id){
        EvalTaskManage evalTaskManage = new EvalTaskManage() ;
        evalTaskManage.setStatus("4");
        evalTaskManage.setId(id);
        evalTaskManage.updateById() ;

        //修改下发机构表状态
        QueryWrapper<EvalOrgTask> queryWrapper1 = new QueryWrapper<>() ;
        queryWrapper1.eq("taskManageId",id) ;
        queryWrapper1.eq("isDel","0") ;
        EvalOrgTask evalOrgTask1 = new EvalOrgTask() ;
        evalOrgTask1.setStatus("2");
        evalOrgTaskMapper.update(evalOrgTask1,queryWrapper1) ;
    }


    /**
    * 所有没有指标的机构
    * Author wzn    
    * Date 2023/12/7 10:20
    */
    public ArrayList<Map<String,String>> zbList(String id){
        //根据设计单ID 查所有方法
        EvalTaskManage evalTaskManage1  = evalTaskManageMapper.selectById(id) ;
        List<EvalStardardMethod> evalStardardMethodList = evalCategoryStardardMapper.getMethod(evalTaskManage1.getAssessmentId()) ;
        ArrayList<Map<String,String>> mapArrayList = new ArrayList<Map<String, String>>() ;
        for(EvalStardardMethod e:evalStardardMethodList){
            //查该办法下  所有机构的指标值
            QueryWrapper<EvalOrgDetail> queryWrapper =new QueryWrapper<>() ;
            queryWrapper.eq("taskManageId",evalTaskManage1.getId()) ;
            queryWrapper.eq("evalStardardMethodId",e.getId()) ;
            List<EvalOrgDetail> objects = evalOrgDetailMapper.selectList(queryWrapper) ;
            if(CollectionUtil.isNotEmpty(objects)){

                for(EvalOrgDetail eo:objects){
                    if(StringUtils.isEmpty(eo.getTargetScore())){
                        Map<String,String> map = new HashMap<>() ;
                        EvalCategoryStardard evalCategoryStardard = evalCategoryStardardMapper.selectById(eo.getEvalStardardId()) ;

                        map.put("orgCode",eo.getOrgCode()) ;
                        if(null != evalCategoryStardard){
                            map.put("title",evalCategoryStardard.getTitle()) ;
                        }
                        EvalStardardMethod evalStardardMethod = evalStardardMethodMapper.selectById(eo.getEvalStardardMethodId()) ;

                        if(null != evalStardardMethod){
                            map.put("name",evalStardardMethod.getName()) ;
                        }

                        if(StringUtils.isEmpty(eo.getTargetScore())){
                            mapArrayList.add(map) ;
                        }


                    }
                }

            }

        }
        return mapArrayList ;
    }


    /**
    *结束初审  计算指标分数
    * Author wzn
    * Date 2023/11/22 17:30
    */
    @Transactional
    public void updateStatus(String id){

        //------------计算分数
        //根据设计单ID 查所有方法
        EvalTaskManage evalTaskManage1  = evalTaskManageMapper.selectById(id) ;
        List<EvalStardardMethod> evalStardardMethodList = evalCategoryStardardMapper.getMethod(evalTaskManage1.getAssessmentId()) ;
        for(EvalStardardMethod e:evalStardardMethodList){
            //todo 1 按照排名升序
            boolean ascendingOrder = false;
            if("1".equals(e.getType()) || "2".equals(e.getType())){

                 if("1".equals(e.getType())){
                     ascendingOrder = true;
                 }else if("2".equals(e.getType())){
                     ascendingOrder = false;
                 }
                //查该办法下所有的比例
                QueryWrapper<EvalMethodInfo> qw = new QueryWrapper<>() ;
                qw.eq("methodId",e.getId()) ;
                List<EvalMethodInfo> configurations = evalMethodInfoMapper.selectList(qw) ;



                //查该办法下  所有机构的指标值
                QueryWrapper<EvalOrgDetail> queryWrapper =new QueryWrapper<>() ;
                queryWrapper.eq("taskManageId",evalTaskManage1.getId()) ;
                queryWrapper.eq("evalStardardMethodId",e.getId()) ;
                    List<EvalOrgDetail> objects = evalOrgDetailMapper.selectList(queryWrapper) ;


                // 根据分数排序
                if (ascendingOrder) {
                    objects.sort(Comparator.comparing(EvalOrgDetail::getTargetScore));
                } else {


                    objects.sort((o1, o2) -> o2.getTargetScore().compareTo(o1.getTargetScore()));
                }

                // 计算分数并输出
                int totalObjects = objects.size();
                for (int i = 0; i < totalObjects; ) {
                    BigDecimal currentScore = new BigDecimal(objects.get(i).getTargetScore());
                    int count = 0; // 计算并列数量
                    while (i + count < totalObjects && new BigDecimal(objects.get(i + count).getTargetScore()).compareTo(currentScore) == 0) {
                        count++;
                    }
                    BigDecimal rank = BigDecimal.valueOf(i).divide(BigDecimal.valueOf(totalObjects), 2, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));
                    BigDecimal nextRank = BigDecimal.valueOf(i + count).divide(BigDecimal.valueOf(totalObjects), 2, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));

                    String score = getScoreFromConfigurations(rank, nextRank, configurations);
                    for (int j = i; j < i + count; j++) {
                        System.out.println("Org: " + objects.get(j).getId() + ", Score: " + score);

                        if(StringUtils.isNotEmpty(objects.get(j).getScore())){
                            objects.get(j).setScore(objects.get(j).getScore());
                        }else {
                            objects.get(j).setScore(String.valueOf(score));
                        }
                        objects.get(j).updateById() ;
                    }
                    i += count;
                }


            }

            //2 按照比例   3按照数值
            if("3".equals(e.getType()) || "4".equals(e.getType())){
                //查该办法下所有的比例
                QueryWrapper<EvalMethodInfo> qw = new QueryWrapper<>() ;
                qw.eq("methodId",e.getId()) ;
                List<EvalMethodInfo> evalMethodInfoList = evalMethodInfoMapper.selectList(qw) ;
                //查该办法下  所有机构的指标值
                QueryWrapper<EvalOrgDetail> queryWrapper =new QueryWrapper<>() ;
                queryWrapper.eq("taskManageId",evalTaskManage1.getId()) ;
                queryWrapper.eq("evalStardardMethodId",e.getId()) ;
                List<EvalOrgDetail> evalOrgDetailList = evalOrgDetailMapper.selectList(queryWrapper) ;
                if(CollectionUtil.isNotEmpty(evalOrgDetailList)){



                    for(EvalOrgDetail eo:evalOrgDetailList){

                        String score = this.getsc(evalMethodInfoList,eo.getTargetScore()) ;
                        //更改分数

                        if(StringUtils.isEmpty(eo.getScore())){
                            eo.setScore(score);
                            eo.updateById() ;
                        }

                    }

                }
            }
        }




    }

    private static String getScoreFromConfigurations(BigDecimal currentRank, BigDecimal nextRank, List<EvalMethodInfo> configurations) {
        for (EvalMethodInfo config : configurations) {
            if (currentRank.compareTo(new BigDecimal(config.getEndRate())) < 0 && nextRank.compareTo(new BigDecimal(config.getStartRate())) >= 0) {
                return config.getScore();
            }
        }
        return "0"; // 默认情况，可以根据需求修改
    }
    /**
    *判断指标在哪个区间内得分
    * Author wzn
    * Date 2023/11/29 10:22
    */
    public String getsc(List<EvalMethodInfo> evalMethodInfoList,String targetScore){
        String score = "0";
        for(EvalMethodInfo e:evalMethodInfoList){
            int comparison = new BigDecimal(targetScore).compareTo(new BigDecimal(e.getStartRate())) ;
            int comparison2 = new BigDecimal(targetScore).compareTo(new BigDecimal(e.getEndRate())) ;
            if(comparison>=0 && comparison2 <0){
                score =  e.getScore() ;
            }
//                        if(Double.valueOf(targetScore)>= Double.valueOf() && Double.valueOf(targetScore)<Double.valueOf(e.getEndRate())){
//                            score =  e.getScore() ;
//                        }
        }
        return score ;
    }



    /**
    *考核任务管理列表接口
    * Author wzn
    * Date 2023/11/20 10:45
    */
    public Page<EvalTaskManage> selectList(EvalTaskManage evalTaskManage) {
        Page<EvalTaskManage> page = new Page<>(evalTaskManage.getPageNo(), evalTaskManage.getPageSize());
        QueryWrapper<EvalTaskManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isDel", "0");
        SysUser sysUser = sysUserService.getUser() ;
        //任务名称
        if (!"".equals(evalTaskManage.getTaskName()) && null != evalTaskManage.getTaskName()) {
            queryWrapper.like("taskName", evalTaskManage.getTaskName());
        }
        //机构类型
        if (StringUtils.isNotEmpty(evalTaskManage.getOrgType())) {
            queryWrapper.eq("orgType", evalTaskManage.getOrgType());
        }
        //机构类别
        if (StringUtils.isNotEmpty(evalTaskManage.getCategory())) {
            queryWrapper.eq("category", evalTaskManage.getCategory());
        }
        //协议等级
        if (StringUtils.isNotEmpty(evalTaskManage.getAggrementLv())) {
            queryWrapper.eq("aggrementLv", evalTaskManage.getAggrementLv());
        }

        //考核年度
        if (StringUtils.isNotEmpty(evalTaskManage.getYear())) {
            queryWrapper.eq("year", evalTaskManage.getYear());
        }
        //经营性质
        if (StringUtils.isNotEmpty(evalTaskManage.getNatures())) {
            queryWrapper.eq("natures", evalTaskManage.getNatures());
        }

        //精神专科
        if (StringUtils.isNotEmpty(evalTaskManage.getSpirit())) {
            queryWrapper.eq("spirit", evalTaskManage.getSpirit());
        }
        //权限 根据统筹区
        if ("1".equals(sysUser.getUser_type()) && !"320399".equals(sysUser.getOrg_code())) {
            queryWrapper.eq("admdvs",sysUserService.getUser().getOrg_code()) ;
        }
//统筹区
        if (!"".equals(evalTaskManage.getAdmdvs()) && null != evalTaskManage.getAdmdvs()) {
            queryWrapper.eq("admdvs", evalTaskManage.getAdmdvs());
        }
        queryWrapper.orderByDesc("createTime") ;
        Page<EvalTaskManage> evalTaskManagePage = evalTaskManageMapper.selectPage(page, queryWrapper);
        if(CollectionUtil.isNotEmpty(evalTaskManagePage.getRecords())){
            for(EvalTaskManage e:evalTaskManagePage.getRecords()){
                //1:机构 2：药店
                if("1".equals(e.getOrgType())){
                    e.setOrgTypeName("医疗机构");
                }else if("2".equals(e.getOrgType())){
                    e.setOrgTypeName("零售药店");
                }

                //类别1.门诊 2.住院
                if("1".equals(e.getCategory())){
                    e.setCategoryName("门诊");
                }else if("2".equals(e.getCategory())){
                    e.setCategoryName("住院");
                }

                //协议等级 "1", "一级"  "2", "二级"  "3", "三级"  "4", "A级别"   "5", "B级别"   "6", "C级别"   "9", "未定级"
                if("1".equals(e.getAggrementLv())){
                    e.setAggrementLvName("一级");
                }else if("2".equals(e.getAggrementLv())){
                    e.setAggrementLvName("二级");
                }else if("3".equals(e.getAggrementLv())){
                    e.setAggrementLvName("三级");
                }else if("4".equals(e.getAggrementLv())){
                    e.setAggrementLvName("A级");
                }else if("5".equals(e.getAggrementLv())){
                    e.setAggrementLvName("B级");
                }else if("6".equals(e.getAggrementLv())){
                    e.setAggrementLvName("C级");
                }else if("9".equals(e.getAggrementLv())){
                    e.setAggrementLvName("未定级");
                }

                // 经营性质 1:公立 2:私立
                if("1".equals(e.getNatures())){
                    e.setNaturesName("公立");
                }else if("2".equals(e.getNatures())){
                    e.setNaturesName("私立");
                }

                //精神专科 0：非精神专科  1精神专科
                if("0".equals(e.getSpirit())){
                    e.setSpiritName("非精神专科");
                }else if("1".equals(e.getSpirit())){
                    e.setSpiritName("精神专科");
                }

                //状态 //0未发布   2初审开始-4复审开始 6待计算保证金 7完成
                if("0".equals(e.getStatus())){
                    e.setStatusName("未发布");
                }else if("2".equals(e.getStatus())){
                    e.setStatusName("初审开始");
                }else if("3".equals(e.getStatus())){
                    e.setStatusName("结束初审");
                } else if("4".equals(e.getStatus())){
                    e.setStatusName("复审开始");
                }else if("6".equals(e.getStatus())){
                    e.setStatusName("待计算保证金");
                }else if("7".equals(e.getStatus())){
                    e.setStatusName("完成");
                }



                if(StringUtils.isNotEmpty(e.getAdmdvs())){
                    SysDict sysDict = new SysDict() ;
                    sysDict.setDict_type("admdvs-area");
                    sysDict.setValue(e.getAdmdvs());
                    SysDict sysDict1 = sysDictService.selectByValue(sysDict) ;
                    if(null != sysDict1){
                        e.setAdmdvsName(sysDict1.getLabel());
                    }
                }
            }
        }
        return evalTaskManagePage;
    }





    /**
    *考核任务发布
    * Author wzn
    * Date 2023/11/20 13:52
    */

    public void publishTaskMange(String id) {
        EvalTaskManage khTaskManage = evalTaskManageMapper.selectById(id);
        if (null != khTaskManage) {
            SysUser sysUser = sysUserService.getUser();
            if (!"1".equals(sysUser.getUser_type())) {
                throw new CustomException("仅限管理员发布考核任务！");
            }

            EvalDesign evalDesign = new EvalDesign();
            evalDesign = evalDesign.selectById(khTaskManage.getAssessmentId());

            //查该考核任务下的所有保证金列表
            QueryWrapper<EvalEarnestMoney> queryWrapper = new QueryWrapper<>() ;
            queryWrapper.eq("taskManageId",id) ;
            List<EvalEarnestMoney> evalEarnestMonies = evalEarnestMoneyMapper.selectList(queryWrapper) ;
            if(CollectionUtil.isNotEmpty(evalEarnestMonies)){
                    //已发布的  进行比较
                    QueryWrapper<EvalOrgTask> queryWrapper1 = new QueryWrapper<>() ;
                    queryWrapper1.eq("isDel","0") ;
                      queryWrapper1.eq("taskManageId",id) ;
                    List<EvalOrgTask> evalOrgTaskList = evalOrgTaskMapper.selectList(queryWrapper1) ;
                    //旧数据和新数据比较
                if(CollectionUtil.isNotEmpty(evalOrgTaskList)){
                    for(EvalOrgTask eo:evalOrgTaskList){
                        QueryWrapper<EvalEarnestMoney> queryWrapper2 = new QueryWrapper<>() ;
                        queryWrapper2.eq("taskManageId",id) ;
                        queryWrapper2.eq("orgCode",eo.getOrgCode()) ;
                        EvalEarnestMoney evalEarnestMoney = evalEarnestMoneyMapper.selectOne(queryWrapper2) ;
                        //新上传的保证金列表没有此数据  则删除
                        if(null == evalEarnestMoney ){
                            eo.deleteById() ;
                            //删除子表
                            QueryWrapper<EvalOrgDetail> queryWrapper3 = new QueryWrapper<>() ;
                            queryWrapper3.eq("orgCode",eo.getOrgCode()) ;
                            queryWrapper3.eq("evalOrgTaskId",eo.getId()) ;
                            evalOrgDetailMapper.delete(queryWrapper3) ;

                        }else {
                            //更新保证金金额
//                            eo.setMargin(evalEarnestMoney.getEarnestMoney());
                            eo.updateById() ;
                        }
                    }
                }

                    //新数据和旧数据比较
                    for(EvalEarnestMoney e:evalEarnestMonies){
                        QueryWrapper<EvalOrgTask> queryWrapper2 = new QueryWrapper<>() ;
                        queryWrapper2.eq("taskManageId",e.getTaskManageId()) ;
                        queryWrapper2.eq("orgCode",e.getOrgCode()) ;
                        EvalOrgTask evalOrgTask = evalOrgTaskMapper.selectOne(queryWrapper2) ;
                        if(null == evalOrgTask){
                            //新增数据
                            QueryWrapper<FixmedinsB> fixmedinsBQueryWrapper = new QueryWrapper<>() ;
                            fixmedinsBQueryWrapper.eq("fixmedins_code",e.getOrgCode()) ;
                            FixmedinsB fixmedinsB = fixmedinsBMapper.selectOne(fixmedinsBQueryWrapper) ;
                            if(null != fixmedinsB){
                                //下发数据
                                EvalOrgTask evalOrgTask2 = new EvalOrgTask() ;
                                evalOrgTask2.setTaskManageId(id);
                                evalOrgTask2.setTaskName(khTaskManage.getTaskName());
                                evalOrgTask2.setDesignId(khTaskManage.getAssessmentId());
                                evalOrgTask2.setOrgType(khTaskManage.getOrgType());
                                evalOrgTask2.setCategory(khTaskManage.getCategory());
                                evalOrgTask2.setNatures(khTaskManage.getNatures());
                                evalOrgTask2.setAggrementLv(khTaskManage.getAggrementLv());
                                evalOrgTask2.setYear(khTaskManage.getYear());
                                evalOrgTask2.setOrgName(fixmedinsB.getFixmedins_name());
                                evalOrgTask2.setSpirit(khTaskManage.getSpirit());
                                evalOrgTask2.setOrgCode(e.getOrgCode());
                                evalOrgTask2.setStatus("0");
                                evalOrgTask2.setAdmdvs(khTaskManage.getAdmdvs());
                                evalOrgTask2.setExpirationTime(khTaskManage.getExpirationTime());
//                                evalOrgTask2.setMargin(e.getEarnestMoney());
                                evalOrgTask2.setCreateUser(sysUser.getUsername());
                                evalOrgTask2.setCreateTime(new Date());
                                evalOrgTask2.setIsDel("0");
                                evalOrgTask2.setTotalScore(evalDesign.getScore());
                                evalOrgTask2.insert() ;

                                //机构考核详情表数据生成
                                //指标ID


                                List<EvalStardardMethod> evalStardardMethodList = evalCategoryStardardMapper.getMedAndStar(khTaskManage.getAssessmentId()) ;
                                for(EvalStardardMethod es:evalStardardMethodList){
                                    EvalOrgDetail evalOrgDetail = new EvalOrgDetail() ;
                                    evalOrgDetail.setId(IdUtil.simpleUUID());
                                    evalOrgDetail.setStatus("1");
                                    evalOrgDetail.setEvalOrgTaskId(evalOrgTask2.getId());
                                    evalOrgDetail.setEvalStardardId(es.getStardardId());
                                    evalOrgDetail.setEvalStardardMethodId(es.getId());
                                    evalOrgDetail.setOrgCode(evalOrgTask2.getOrgCode());
                                    evalOrgDetail.setTaskManageId(evalOrgTask2.getTaskManageId());
                                    evalOrgDetail.insert() ;
                                }
                            }
                        }

                    }


                khTaskManage.setStatus("2");
                khTaskManage.updateById() ;
            }else {
                throw new CustomException("请先上传机构信息！");
            }


        }
    }


    /**
    *获取保证金文件路径
    * Author wzn
    * Date 2023/11/23 14:28
    */
    public FileInfo getFile(String id){
        QueryWrapper<FileInfo> queryWrapper = new QueryWrapper<>() ;
        queryWrapper.eq("bizType","17") ;
        queryWrapper.eq("bizId",id) ;
        FileInfo fileInfo = fileInfoMapper.selectOne(queryWrapper) ;
        if(null == fileInfo){
            throw new CustomException("请先上传保证金文件！") ;
        }
        return fileInfo ;
    }

    
    /**
    *获取机构文件路径
    * Author wzn
    * Date 2023/12/12 11:43
    */
    public FileInfo getFile2(String id){
        QueryWrapper<FileInfo> queryWrapper = new QueryWrapper<>() ;
        queryWrapper.eq("bizType","16") ;
        queryWrapper.eq("bizId",id) ;
        FileInfo fileInfo = fileInfoMapper.selectOne(queryWrapper) ;
        if(null == fileInfo){
            throw new CustomException("请先上传机构文件！") ;
        }
        return fileInfo ;
    }

    /**
    *考核任务删除
    * Author wzn
    * Date 2023/11/23 15:06
    */
    public void del(String id){


        EvalTaskManage evalTaskManage = new EvalTaskManage() ;
        evalTaskManage.setId(id);
        evalTaskManage.setIsDel("1");
        evalTaskManage.updateById() ;
    }



    /**
     *复审结束 计算平均分和指标值
     * Author wzn
     * Date 2023/11/23 15:52
     */
    public void calculateScore(String id){
        QueryWrapper<EvalOrgTask> queryWrapper = new QueryWrapper<>() ;
        queryWrapper.eq("isDel","0") ;
        queryWrapper.eq("taskManageId",id) ;
        List<EvalOrgTask> evalOrgTaskList = evalOrgTaskMapper.selectList(queryWrapper) ;
        for(EvalOrgTask e:evalOrgTaskList){
            //计算总分
            EvalOrgDetail evalOrgDetail = evalCategoryStardardMapper.getSum(e.getId()) ;
            if(null ==evalOrgDetail ){
                throw new CustomException(e.getOrgCode()+"该机构分数为空！") ;
            }
            e.setScore(evalOrgDetail.getScore());
            e.updateById() ;
        }

        //平均分
        EvalOrgTask evalOrgTask = evalOrgTaskMapper.averageScore(id) ;

        for(EvalOrgTask e:evalOrgTaskList){
            e.setAverageScore(evalOrgTask.getScore());
            BigDecimal av = new BigDecimal(evalOrgTask.getScore());
            BigDecimal score = new BigDecimal( e.getScore());
            //指数
            BigDecimal exponent = score.divide(av,2, RoundingMode.HALF_UP) ;
            e.setOrgRate(String.valueOf(exponent));
            e.updateById() ;
        }
        //更改状态为 待计算保证金
        EvalTaskManage evalTaskManage = new EvalTaskManage() ;
        evalTaskManage.setId(id);
        evalTaskManage.setStatus("6");
        evalTaskManage.updateById() ;

        //修改下发机构表状态
        QueryWrapper<EvalOrgTask> queryWrapper1 = new QueryWrapper<>() ;
        queryWrapper1.eq("taskManageId",id) ;
        queryWrapper1.eq("isDel","0") ;
        EvalOrgTask evalOrgTask1 = new EvalOrgTask() ;
        evalOrgTask1.setStatus("3");
        evalOrgTaskMapper.update(evalOrgTask1,queryWrapper1) ;
    }


    /**
    *考核单下拉接口
    * Author wzn
    * Date 2023/11/23 18:44
    */
    public List<EvalDesign> designList(EvalDesign evalDesign){
        QueryWrapper<EvalDesign> queryWrapper = new QueryWrapper<>() ;
        queryWrapper.eq("isDel","0") ;
        if(StringUtils.isNotEmpty(evalDesign.getOrgType())){
            queryWrapper.eq("orgType",evalDesign.getOrgType()) ;
        }
        if(StringUtils.isNotEmpty(evalDesign.getYear())){
            queryWrapper.eq("year",evalDesign.getYear()) ;
        }

        List<EvalDesign> evalDesignList = evalDesignMapper.selectList(queryWrapper) ;
        return evalDesignList ;
    }


    /**
    *机构下拉数据
    * Author wzn
    * Date 2023/11/24 14:06
    */
    public List<MedinsDeptB> getMedinsDeptB(){
        QueryWrapper<MedinsDeptB> queryWrapper = new QueryWrapper<>() ;
        queryWrapper.eq("vali_flag","1") ;
        queryWrapper.eq("org_code",sysUserService.getUser().getOrg_code()) ;

        List<MedinsDeptB> medinsDeptBList = medinsDeptBMapper.selectList(queryWrapper) ;
        return medinsDeptBList ;
    }

    /**
    *详情接口
    * Author wzn
    * Date 2023/11/25 10:24
    */
    public EvalTaskManage info(String id){
        EvalTaskManage evalTaskManage = new EvalTaskManage() ;
        evalTaskManage.setId(id);
        EvalTaskManage evalTaskManage1 = evalTaskManage.selectById() ;
         if("1".equals(evalTaskManage1.getOrgType())){
             evalTaskManage1.setOrgTypeName("医疗机构");
         }else if("2".equals(evalTaskManage1.getOrgType())){
             evalTaskManage1.setOrgTypeName("零售药店");
         }
        EvalDesign evalDesign = new EvalDesign() ;
        evalDesign.setId(evalTaskManage1.getAssessmentId());
        EvalDesign evalDesign1 =evalDesign.selectById() ;

        evalTaskManage1.setAssessmentName(evalDesign1.getTitle());
        return evalTaskManage1 ;
    }



    /**
    *获取负责人接口
    * Author wzn
    * Date 2023/11/25 11:06
    */
    public List<EvalStardardUser> getUser(String id){
            QueryWrapper<EvalStardardUser> queryWrapper = new QueryWrapper<>() ;
            queryWrapper.eq("taskManageId",id) ;
        List<EvalStardardUser> evalStardardUserList = evalStardardUserMapper.selectList(queryWrapper) ;
        for(EvalStardardUser e:evalStardardUserList){

            SysUser sysUser = sysUserMapper.selectById( e.getUserId()) ;
            if(null != sysUser){
                e.setUserName(sysUser.getName());
            }
        }
        return evalStardardUserList ;
    }


    /**
    *计算保证金
    * Author wzn
    * Date 2023/11/25 15:05
    */
    @Transactional
    public ResultInfo calculate(String id){
        EvalTaskManage evalTaskManage1 = evalTaskManageMapper.selectById(id) ;
        if(null != evalTaskManage1){
           if("0".equals(evalTaskManage1.getMoneyUpload())){
                throw new CustomException("请先上传保证金文件！") ;
           }
        }

        ResultInfo resultInfo = evalFormulaConfigService.genEarnestMoney(id) ;


        //更改状态
        EvalTaskManage evalTaskManage = new EvalTaskManage() ;
        evalTaskManage.setId(id);
        evalTaskManage.setStatus("7");
        evalTaskManage.updateById() ;

        //修改下发机构表状态
        QueryWrapper<EvalOrgTask> queryWrapper1 = new QueryWrapper<>() ;
        queryWrapper1.eq("taskManageId",id) ;
        queryWrapper1.eq("isDel","0") ;
        EvalOrgTask evalOrgTask1 = new EvalOrgTask() ;
        evalOrgTask1.setStatus("4");
        evalOrgTaskMapper.update(evalOrgTask1,queryWrapper1) ;
       return  resultInfo;
    }


    /**
    *结束初审时  没有分数的机构列表
    * Author wzn
    * Date 2023/11/27 9:28
    */
    public Page<EvalOrgTaskVo> noScore(EvalOrgTaskVo evalOrgTaskVo){
        Page<EvalOrgTaskVo> page = new Page<>( evalOrgTaskVo.getPageNo(),evalOrgTaskVo.getPageSize());
        Page<EvalOrgTaskVo> evalOrgTaskVoPage = evalOrgTaskMapper.noScore(page,evalOrgTaskVo) ;
        if(CollectionUtil.isNotEmpty(evalOrgTaskVoPage.getRecords())){
            for(EvalOrgTaskVo e:evalOrgTaskVoPage.getRecords()){
                QueryWrapper<EvalStardardUser> queryWrapper = new QueryWrapper<>() ;
                queryWrapper.eq("stardardId",e.getEvalStardardId()) ;
                queryWrapper.eq("taskManageId",evalOrgTaskVo.getTaskManageId()) ;
                EvalStardardUser evalStardardUser =  evalStardardUserMapper.selectOne(queryWrapper) ;
                if(null != evalStardardUser){
                    SysUser sysUser = sysUserMapper.selectById(evalStardardUser.getUserId()) ;
                    if(null != sysUser){
                        e.setName(sysUser.getName());
                    }

                }
            }
        }
        return evalOrgTaskVoPage ;
    }




    /**
    *考核任务下拉数据接口
    * Author wzn
    * Date 2023/11/30 9:41
    */
    public List<EvalTaskManage> taskManageDropDown(){
        QueryWrapper<EvalTaskManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isDel", "0");

        //权限 根据统筹区
        queryWrapper.eq("admdvs",sysUserService.getUser().getOrg_code()) ;
        queryWrapper.orderByDesc("createTime") ;
        List<EvalTaskManage> evalTaskManageList = evalTaskManageMapper.selectList(queryWrapper);
        return evalTaskManageList ;
    }




    public void uploadPzFile(List<MultipartFile> file, String id) {

        for(MultipartFile m:file){

            //导入文件
            try {
                InputStream in = m.getInputStream();
                //构建对象读取
                Excel07SaxReader reader = new Excel07SaxReader(createRowHandler2(id));
                reader.read(in, -1);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }


    private RowHandler createRowHandler2(String id) {

        return new RowHandler() {
            @Override
            public void handle(int sheetIndex, long rowIndex, List<Object> rowlist) {
                String staffProportion   = null;
                String residentProportion   = null;
                String medicalTreatmentProportion   = null;
                if (rowIndex == 0) {
                    return;
                }
                int s = rowlist.size();
                for (int i = 0; i < rowlist.size(); i++) {
                    if (null != rowlist.get(0)) {
                        staffProportion = rowlist.get(0).toString() ;
                    } else {
                        throw new CustomException("请完善职机构编码！");
                    }

                    if (null != rowlist.get(1)) {
                        staffProportion = rowlist.get(1).toString() ;
                    } else {
                        throw new CustomException("请完善职工占比！");
                    }

                    if(!isNumeric(rowlist.get(1).toString())){
                        throw new CustomException("职工占比只能为数字！");
                    }
                    if (null != rowlist.get(2)) {
                        residentProportion = rowlist.get(2).toString() ;

                    } else {
                        throw new CustomException("请完善居民占比！");
                    }

                    if(!isNumeric(rowlist.get(2).toString())){
                        throw new CustomException("居民占比只能为数字！");
                    }
                    if (null != rowlist.get(3)) {
                        medicalTreatmentProportion = rowlist.get(3).toString() ;

                    } else {
                        throw new CustomException("请完善医疗占比！");
                    }

                    if(!isNumeric(rowlist.get(3).toString())){
                        throw new CustomException("医疗占比只能为数字！");
                    }
                }

                QueryWrapper<EvalOrgTask> queryWrapper = new QueryWrapper<>() ;
                queryWrapper.eq("taskManageId",id) ;
                queryWrapper.eq("orgCode",rowlist.get(0).toString()) ;
                queryWrapper.eq("isDel","0") ;
                EvalOrgTask evalOrgTask = new EvalOrgTask() ;

                evalOrgTask.setStaffProportion(staffProportion);
                evalOrgTask.setResidentProportion(residentProportion);
                evalOrgTask.setMedicalTreatmentProportion(medicalTreatmentProportion);
                evalOrgTaskMapper.update(evalOrgTask,queryWrapper) ;
            }
        };
    }


    /**
    *结束初审的时候最后算总分
    * Author wzn
    * Date 2023/12/6 15:54
    */
    public void startFinish(String id){
        EvalTaskManage evalTaskManage = new EvalTaskManage() ;
        evalTaskManage.setId(id);
        evalTaskManage.setStatus("3");
        //evalTaskManage.setStatus("4");
        evalTaskManage.updateById() ;
        //修改下发机构表状态
        QueryWrapper<EvalOrgTask> queryWrapper1 = new QueryWrapper<>() ;
        queryWrapper1.eq("taskManageId",id) ;
        queryWrapper1.eq("isDel","0") ;
        EvalOrgTask evalOrgTask = new EvalOrgTask() ;
        evalOrgTask.setStatus("5");
        evalOrgTaskMapper.update(evalOrgTask,queryWrapper1) ;


        //-------------算总分
        QueryWrapper<EvalOrgTask> queryWrapper = new QueryWrapper<>() ;
        queryWrapper.eq("isDel","0") ;
        queryWrapper.eq("taskManageId",id) ;
        List<EvalOrgTask> evalOrgTaskList = evalOrgTaskMapper.selectList(queryWrapper) ;
        for(EvalOrgTask e:evalOrgTaskList){
            //计算总分
            EvalOrgDetail evalOrgDetail = evalCategoryStardardMapper.getSum(e.getId()) ;
            if(null ==evalOrgDetail ){
                throw new CustomException(e.getOrgCode()+"该机构分数为空！") ;
            }
            e.setScore(evalOrgDetail.getScore());
            e.updateById() ;
        }
    }
}
