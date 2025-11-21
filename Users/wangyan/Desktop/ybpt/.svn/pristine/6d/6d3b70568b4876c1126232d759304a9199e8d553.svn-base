package com.jsdc.ybpt.service.eval;

import cn.hutool.core.util.IdUtil;
import cn.hutool.poi.excel.sax.Excel07SaxReader;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.agreementsignModel.NetTagFile;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.eval_.*;
import com.jsdc.ybpt.mapper.FileInfoMapper;
import com.jsdc.ybpt.mapper.SysUserMapper;
import com.jsdc.ybpt.mapper.eval.*;
import com.jsdc.ybpt.model.FileInfo;
import com.jsdc.ybpt.service.SysUserService;
import com.jsdc.ybpt.util.FastDfs.FastDfsParams;
import com.jsdc.ybpt.util.FastDfs.FastDfsUtil;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.util.exception.CustomException;
import com.jsdc.ybpt.vo.EvalDesignVo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class EvalDesignService extends BaseService<EvalDesign> {
    @Autowired
    private EvalDesignMapper evalDesignMapper;
    @Autowired
    private EvalDesignCategoryMapper evalDesignCategoryMapper;
    @Autowired
    private EvalCategoryStardardMapper evalCategoryStardardMapper;
    @Autowired
    private EvalStardardMethodMapper evalStardardMethodMapper;
    @Autowired
    private EvalMethodInfoMapper evalMethodInfoMapper;
    @Autowired
    private FileInfoMapper fileInfoMapper;
    @Autowired
    private EvalTaskManageMapper evalTaskManageMapper;
    @Autowired
    private FastDfsUtil fastDfsUtil;
    @Autowired
    private SysUserService sysUserService;
    @Value("${uploadtjreportPath}")
    private String uploadtjreportPath;
    @Value("${fastDfs_url}")
    private String fastDfsUrl;
    public Page<EvalDesign> pageList(EvalDesignVo vo) {
        LambdaQueryWrapper<EvalDesign> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(vo.getTitle()), EvalDesign::getTitle, vo.getTitle())
                .eq(StringUtils.isNotEmpty(vo.getOrgType()), EvalDesign::getOrgType, vo.getOrgType())
                .eq(StringUtils.isNotEmpty(vo.getAggrementLv()), EvalDesign::getAggrementLv, vo.getAggrementLv())
                .eq(StringUtils.isNotEmpty(vo.getYear()), EvalDesign::getYear, vo.getYear())
                .eq(StringUtils.isNotEmpty(vo.getCategory()), EvalDesign::getCategory, vo.getCategory())
                .eq(StringUtils.isNotEmpty(vo.getNatures()), EvalDesign::getNatures, vo.getNatures())
                .eq(StringUtils.isNotEmpty(vo.getSpirit()), EvalDesign::getSpirit, vo.getSpirit())
                .eq(EvalDesign::getIsDel, "0").orderByDesc(EvalDesign::getCreateTime);
        Page<EvalDesign> page = this.page(new Page<EvalDesign>(vo.getPageNo(), vo.getPageSize()), wrapper);
        page.getRecords().forEach(a->{
            LambdaQueryWrapper<EvalTaskManage> wrapper1=new LambdaQueryWrapper<>();
            wrapper1.eq(EvalTaskManage::getAssessmentId,a.getId());
            Long aLong=evalTaskManageMapper.selectCount(wrapper1);
            a.setIsFlag(aLong);
        });
        return page;
    }


    public EvalDesign getEntity(String id) {
        //总  1
        EvalDesign design = evalDesignMapper.selectById(id);
        //查询 大标题 2
        LambdaQueryWrapper<EvalDesignCategory> edcWrapper = new LambdaQueryWrapper<>();
        edcWrapper.eq(EvalDesignCategory::getDesignId, design.getId()).orderByAsc(EvalDesignCategory::getCreateTime);
        List<EvalDesignCategory> evalDesignCategories = evalDesignCategoryMapper.selectList(edcWrapper);
        design.setEvalDesignCategorys(evalDesignCategories);
        //大标题下面的评价项目 3
        design.getEvalDesignCategorys().forEach(evalDesignCategory -> {
            LambdaQueryWrapper<EvalCategoryStardard> ecsWrapper = new LambdaQueryWrapper<>();
            ecsWrapper.eq(EvalCategoryStardard::getCategoryId, evalDesignCategory.getId()).orderByAsc(EvalCategoryStardard::getCreateTime);
            List<EvalCategoryStardard> evalCategoryStardards = evalCategoryStardardMapper.selectList(ecsWrapper);
            evalDesignCategory.setEvalCategoryStardards(evalCategoryStardards);
            //评价项目下的评分办法 4
            evalDesignCategory.getEvalCategoryStardards().forEach(evalCategoryStardard -> {
                LambdaQueryWrapper<EvalStardardMethod> esmWrapper = new LambdaQueryWrapper<>();
                esmWrapper.eq(EvalStardardMethod::getStardardId, evalCategoryStardard.getId()).orderByAsc(EvalStardardMethod::getCreateTime);
                List<EvalStardardMethod> evalStardardMethods = evalStardardMethodMapper.selectList(esmWrapper);
                evalCategoryStardard.setEvalStardardMethods(evalStardardMethods);
                //评分方法 计算方式 5
                evalCategoryStardard.getEvalStardardMethods().forEach(evalStardardMethod -> {
                    LambdaQueryWrapper<EvalMethodInfo> emiWrapper = new LambdaQueryWrapper<>();
                    emiWrapper.eq(EvalMethodInfo::getMethodId, evalStardardMethod.getId()).orderByAsc(EvalMethodInfo::getCreateTime);
                    List<EvalMethodInfo> evalMethodInfos = evalMethodInfoMapper.selectList(emiWrapper);
                    evalStardardMethod.setEvalMethodInfos(evalMethodInfos);
                });
            });
        });
        return design;
    }

    public ResultInfo del(String id) {
        EvalDesign evalDesign = new EvalDesignVo();
        evalDesign.setId(id);
        evalDesign.setIsDel("1");
        updateById(evalDesign);
        return ResultInfo.success();
    }

    public ResultInfo copyEntity(String id) {
        //总  1
        EvalDesign design = evalDesignMapper.selectById(id);
        String id1 = IdUtil.simpleUUID();
        //查询 大标题 2
        LambdaQueryWrapper<EvalDesignCategory> edcWrapper = new LambdaQueryWrapper<>();
        edcWrapper.eq(EvalDesignCategory::getDesignId, design.getId());
        List<EvalDesignCategory> evalDesignCategories = evalDesignCategoryMapper.selectList(edcWrapper);
        design.setEvalDesignCategorys(evalDesignCategories);
        //大标题下面的评价项目 3
        design.getEvalDesignCategorys().forEach(evalDesignCategory -> {
            String id2 = IdUtil.simpleUUID();
            LambdaQueryWrapper<EvalCategoryStardard> ecsWrapper = new LambdaQueryWrapper<>();
            ecsWrapper.eq(EvalCategoryStardard::getCategoryId, evalDesignCategory.getId());
            List<EvalCategoryStardard> evalCategoryStardards = evalCategoryStardardMapper.selectList(ecsWrapper);
            evalDesignCategory.setEvalCategoryStardards(evalCategoryStardards);
            //评价项目下的评分办法 4
            evalDesignCategory.getEvalCategoryStardards().forEach(evalCategoryStardard -> {
                String id3 = IdUtil.simpleUUID();
                LambdaQueryWrapper<EvalStardardMethod> esmWrapper = new LambdaQueryWrapper<>();
                esmWrapper.eq(EvalStardardMethod::getStardardId, evalCategoryStardard.getId());
                List<EvalStardardMethod> evalStardardMethods = evalStardardMethodMapper.selectList(esmWrapper);
                evalCategoryStardard.setEvalStardardMethods(evalStardardMethods);
                //评分方法 计算方式 5
                evalCategoryStardard.getEvalStardardMethods().forEach(evalStardardMethod -> {
                    String id4 = IdUtil.simpleUUID();
                    LambdaQueryWrapper<EvalMethodInfo> emiWrapper = new LambdaQueryWrapper<>();
                    emiWrapper.eq(EvalMethodInfo::getMethodId, evalStardardMethod.getId());
                    List<EvalMethodInfo> evalMethodInfos = evalMethodInfoMapper.selectList(emiWrapper);
                    for (EvalMethodInfo info : evalMethodInfos) {
                        info.setId(IdUtil.simpleUUID());
                        info.setMethodId(id4);
                        evalMethodInfoMapper.insert(info);
                        //复制第五层
                    }
                    //复制第四层
                    evalStardardMethod.setId(id4);
                    evalStardardMethod.setStardardId(id3);
                    evalStardardMethodMapper.insert(evalStardardMethod);
                });
                //复制第三层
                evalCategoryStardard.setId(id3);
                evalCategoryStardard.setCategoryId(id2);
                evalCategoryStardardMapper.insert(evalCategoryStardard);
            });
            //复制第二层
            evalDesignCategory.setId(id2);
            evalDesignCategory.setDesignId(id1);
            evalDesignCategoryMapper.insert(evalDesignCategory);
        });
        //复制第一层
        design.setId(id1);
        evalDesignMapper.insert(design);
        return ResultInfo.success();
    }

    public ResultInfo saveOrUploadEval(EvalDesignVo vo) {
        if (StringUtils.isEmpty(vo.getId())) {
            LambdaQueryWrapper<EvalDesign> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(EvalDesign::getYear, vo.getYear())
                    .eq(!StringUtils.isEmpty(vo.getCategory()),EvalDesign::getCategory, vo.getCategory())
                    .eq(EvalDesign::getAggrementLv, vo.getAggrementLv())
                    .eq(!StringUtils.isEmpty(vo.getNatures()),EvalDesign::getNatures, vo.getNatures())
                    .eq(EvalDesign::getOrgType, vo.getOrgType())
                    .eq(!StringUtils.isEmpty(vo.getSpirit()),EvalDesign::getSpirit, vo.getSpirit()).eq(EvalDesign::getIsDel,0);
            List<EvalDesign> entitys=this.evalDesignMapper.selectList(wrapper);
            if (entitys.size()>0){
               return ResultInfo.error("当年同一类型，请勿重复添加");
            }
            vo.setId(IdUtil.simpleUUID());
            vo.setIsDel("0");
            vo.setCreateTime(new Date());
            vo.setCreateUser(sysUserService.getUser().getId());
            if (vo.getEvalDesignCategorys().size() > 0) {
                Calendar cal3=Calendar.getInstance();
                for (int l = 0; l < vo.getEvalDesignCategorys().size(); l++) {
                    cal3.add(Calendar.SECOND,l);
                    EvalDesignCategory a = vo.getEvalDesignCategorys().get(l);
                    a.setId(IdUtil.simpleUUID());
                    a.setCreateTime(cal3.getTime());
                    a.setDesignId(vo.getId());
                    vo.setPiont(vo.getPiont()+Integer.parseInt(a.getScore()));
                    evalDesignCategoryMapper.insert(a);
                    if (a.getEvalCategoryStardards().size() > 0) {
                        Calendar cal2=Calendar.getInstance();
                        for (int k = 0; k < a.getEvalCategoryStardards().size(); k++) {
                            cal2.add(Calendar.SECOND,k);
                            EvalCategoryStardard b = a.getEvalCategoryStardards().get(k);
                            b.setId(IdUtil.simpleUUID());
                            b.setCreateTime(cal2.getTime());
                            b.setCategoryId(a.getId());
                            evalCategoryStardardMapper.insert(b);
                            if (b.getEvalStardardMethods().size() > 0) {
                                Calendar cal=Calendar.getInstance();
                                for (int i = 0; i < b.getEvalStardardMethods().size(); i++) {
                                    cal.add(Calendar.SECOND,i);
                                    EvalStardardMethod c= b.getEvalStardardMethods().get(i);
                                    c.setId(IdUtil.simpleUUID());
                                    c.setCreateTime(cal.getTime());
                                    c.setStardardId(b.getId());
                                    evalStardardMethodMapper.insert(c);
                                    if (c.getEvalMethodInfos().size() > 0) {
                                        Calendar cal1=Calendar.getInstance();
                                        for (int j = 0; j < c.getEvalMethodInfos().size(); j++) {
                                            cal1.add(Calendar.SECOND,j);
                                            EvalMethodInfo d = c.getEvalMethodInfos().get(j);
                                            d.setId(IdUtil.simpleUUID());
                                            d.setCreateTime(cal1.getTime());
                                            d.setMethodId(c.getId());
                                            evalMethodInfoMapper.insert(d);
                                        }
                                    }
                                }
                            }
                        }
                    }

                }

            }
            vo.setScore(vo.getPiont()+"");
            save(vo);
            return ResultInfo.success("添加成功");
        } else {
            LambdaQueryWrapper<EvalDesignCategory> edcWrapper = new LambdaQueryWrapper<>();
            edcWrapper.eq(EvalDesignCategory::getDesignId, vo.getId());
            evalDesignCategoryMapper.delete(edcWrapper);
            if (vo.getEvalDesignCategorys().size() > 0) {
                Calendar cal3=Calendar.getInstance();
                for (int l = 0; l < vo.getEvalDesignCategorys().size(); l++) {
                    cal3.add(Calendar.SECOND,l);
                    EvalDesignCategory a = vo.getEvalDesignCategorys().get(l);
                    if (StringUtils.isEmpty(a.getId())) {
                        a.setId(IdUtil.simpleUUID());
                    }
                    a.setCreateTime(cal3.getTime());
                    a.setDesignId(vo.getId());
                    vo.setPiont(vo.getPiont()+Integer.parseInt(a.getScore()));
                    evalDesignCategoryMapper.insert(a);
                    LambdaQueryWrapper<EvalCategoryStardard> ecsWrapper = new LambdaQueryWrapper<>();
                    ecsWrapper.eq(EvalCategoryStardard::getCategoryId, a.getId());
                    evalCategoryStardardMapper.delete(ecsWrapper);
                    if (a.getEvalCategoryStardards().size() > 0) {
                        Calendar cal2=Calendar.getInstance();
                        for (int k = 0; k < a.getEvalCategoryStardards().size(); k++) {
                            cal2.add(Calendar.SECOND,k);
                            EvalCategoryStardard b = a.getEvalCategoryStardards().get(k);
                            if (StringUtils.isEmpty(b.getId())) {
                                b.setId(IdUtil.simpleUUID());
                            }
                            b.setCreateTime(cal2.getTime());
                            b.setCategoryId(a.getId());
                            evalCategoryStardardMapper.insert(b);
                            LambdaQueryWrapper<EvalStardardMethod> esmWrapper = new LambdaQueryWrapper<>();
                            esmWrapper.eq(EvalStardardMethod::getStardardId, b.getId());
                            evalStardardMethodMapper.delete(esmWrapper);
                            if (b.getEvalStardardMethods().size() > 0) {
                                Calendar cal=Calendar.getInstance();
                                for (int i = 0; i < b.getEvalStardardMethods().size(); i++) {
                                    cal.add(Calendar.SECOND,i);
                                    EvalStardardMethod c= b.getEvalStardardMethods().get(i);
                                    if (StringUtils.isEmpty(c.getId())) {
                                        c.setId(IdUtil.simpleUUID());
                                    }
                                    c.setStardardId(b.getId());
                                    c.setCreateTime(cal.getTime());
                                    evalStardardMethodMapper.insert(c);
                                    LambdaQueryWrapper<EvalMethodInfo> emiWrapper = new LambdaQueryWrapper<>();
                                    emiWrapper.eq(EvalMethodInfo::getMethodId, c.getId());
                                    evalMethodInfoMapper.delete(emiWrapper);
                                    if (c.getEvalMethodInfos().size() > 0) {
                                        Calendar cal1=Calendar.getInstance();
                                        for (int j = 0; j < c.getEvalMethodInfos().size(); j++) {
                                            cal1.add(Calendar.SECOND,j);
                                            EvalMethodInfo d = c.getEvalMethodInfos().get(j);
                                            if (StringUtils.isEmpty(d.getId())) {
                                                d.setId(IdUtil.simpleUUID());
                                            }
                                            d.setCreateTime(cal1.getTime());
                                            d.setMethodId(c.getId());
                                            evalMethodInfoMapper.insert(d);
                                        }
                                    }
                                }
                            }


                        }

                    }


                }

            }
            vo.setScore(vo.getPiont()+"");
            updateById(vo);
            return ResultInfo.success("修改成功");
        }

    }


    public ResultInfo uploadDesignFile(List<MultipartFile> file, String id,String year) {

        for (MultipartFile m : file) {
//            //清除文件
//            List<FileInfo> fileInfos = fileInfoMapper.selectList(Wrappers.<FileInfo>lambdaQuery()
//                    .eq(FileInfo::getBizId, id)
//                    .eq(FileInfo::getBizType, "14")
//            );
//            for (FileInfo fileInfo : fileInfos) {
//                fastDfsUtil.deleteFile(fileInfo);
//            }
            //上传文件服务器
            FastDfsParams params = new FastDfsParams("eval/"+year+"/template", m, "14", id);
            params.setFileName(m.getOriginalFilename());
            ResultInfo resultInfo = fastDfsUtil.uploadFile(params);
            if (resultInfo.getCode() != 0) {
                throw new CustomException(resultInfo.getMsg());
            }
            return resultInfo;
        }
        return ResultInfo.success();
    }


    public List<Map<String, String>> getDesignFile(String id) {
        QueryWrapper<FileInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<FileInfo> fileInfos = fileInfoMapper.selectList(queryWrapper);
        List<Map<String, String>> mapList = new ArrayList<>();
        if (fileInfos.size() != 0 && fileInfos != null) {
            for (FileInfo s : fileInfos) {
                Map<String, String> map = new HashMap<>();
                map.put("name", s.getFileName());
                map.put("url", uploadtjreportPath + s.getFileUrl());
                map.put("downLoad",fastDfsUrl+s.getFileUrl());
                mapList.add(map);
            }

        }
        return mapList;
    }

    public  ResultInfo getTaskEntity(String id){
        LambdaQueryWrapper<EvalTaskManage> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(EvalTaskManage::getAssessmentId,id);
        Long aLong=evalTaskManageMapper.selectCount(wrapper);
        if (aLong==0){
            return ResultInfo.success();
        }else{
            return ResultInfo.error("");
        }
    }
}

