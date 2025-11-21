package com.jsdc.ybpt.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.mapper.DiseaseMutexMapper;
import com.jsdc.ybpt.mapper.FileInfoMapper;
import com.jsdc.ybpt.mapper.OpspDiseMapper;
import com.jsdc.ybpt.mapper.RegistInfoMapper;
import com.jsdc.ybpt.model.*;
import com.jsdc.ybpt.service.OpspDiseListBService;
import com.jsdc.ybpt.service.SysFileService;
import com.jsdc.ybpt.service.SysUserService;
import com.jsdc.ybpt.util.FastDfs.FastDfsParams;
import com.jsdc.ybpt.util.FastDfs.FastDfsUtil;
import com.jsdc.ybpt.vo.OpspCertDVo;
import com.jsdc.ybpt.vo.OpspDiseListBVo;
import com.jsdc.ybpt.vo.OpspRegDVo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * 医保门慢门特病种目录表
 */
@RestController
@RequestMapping("/opspDise")
public class OpspDiseListBController {

    @Autowired
    private OpspDiseListBService opspDiseListBService;

    @Autowired
    private OpspDiseMapper opspDiseMapper;

    @Autowired
    private RegistInfoMapper registInfoMapper;

    @Autowired
    private SysFileService sysFileService;

    @Autowired
    private DiseaseMutexMapper diseaseMutexMapper ;

    @Autowired
    private SysUserService sysUserService ;

    @Autowired
    private FileInfoMapper fileInfoMapper;

    @Autowired
    private FastDfsUtil fastDfsUtil;

    /**
     * 获取医保门慢门特病种目录
     *
     * @param
     * @return
     */
    @RequestMapping("/OpspDiseList")
    public ResultInfo medinsInfoBVoPage(@RequestBody OpspDiseListBVo opspDiseListBVo) {
        Page<OpspDiseListBVo> opspDiseListBVoPage = opspDiseListBService.OpspDiseList(opspDiseListBVo);
        return ResultInfo.success(opspDiseListBVoPage);
    }


    /**
     * 门慢门特新增
     * Author wzn
     * Date 2022/4/28 10:14
     */
    @RequestMapping("/add")
    public ResultInfo add(@RequestBody OpspDise opspDise) {
        String id = opspDiseListBService.add(opspDise);
        return ResultInfo.success(id);
    }

    /**
     * 新增校验  同一个人  未审核只能有一个
     * Author wzn
     * Date 2022/5/9 15:30
     */
    @RequestMapping("/checkAdd")
    public ResultInfo checkAdd(@RequestBody OpspDise opspDise) {
        opspDiseListBService.checkAdd(opspDise);
        return ResultInfo.success();
    }


    /**
     * 门特门慢登记列表
     * Author wzn
     * Date 2022/4/29 13:54
     */
    @RequestMapping("/selectList")
    public ResultInfo selectDiseList(@RequestBody OpspDise opspDise) {
        return ResultInfo.success(opspDiseListBService.selectDiseList(opspDise));
    }

    /**
    *门慢门特审核列表
    * Author wzn
    * Date 2022/5/12 15:29
    */
    @RequestMapping("/opspCheckList")
    public ResultInfo opspCheckList(@RequestBody OpspRegDVo opspRegDVo) {
        return ResultInfo.success(opspDiseListBService.opspCheckList(opspRegDVo));
    }


    /**
     * 门慢门特详情接口
     * Author wzn
     * Date 2022/5/5 10:12
     */
    @RequestMapping("/info")
    public ResultInfo info(String id) {
        OpspDise opspDise = opspDiseMapper.selectById(id);
        return ResultInfo.success(opspDise);
    }


    /**
     * 病种列表
     * Author wzn
     * Date 2022/5/5 13:53
     */
    @RequestMapping("/diseasesList")
    public ResultInfo diseasesList(String id) {
        QueryWrapper<RegistrationInformation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("opsp_id", id);
        List<RegistrationInformation> informationList = registInfoMapper.selectList(queryWrapper);
        return ResultInfo.success(informationList);
    }


    /**
     * 图片列表
     * Author wzn
     * Date 2022/5/5 16:28
     */
    @RequestMapping("/picList")
    public ResultInfo picList(String id) {
        return ResultInfo.success(sysFileService.picList(id));
    }

    /**
    *门慢特登记审核
    * Author wzn
    * Date 2022/5/12 17:15
    */
    @RequestMapping("/checkResult")
    public ResultInfo checkResult(@RequestBody OpspDise opspDise) {
       opspDiseListBService.checkResult(opspDise);
        return ResultInfo.success();
    }

    /**
    *门慢特撤销接口
    * Author wzn
    * Date 2022/5/17 11:07
    */
    @RequestMapping("/revoke")
    public ResultInfo revoke(String id) {
        opspDiseListBService.revoke(id);
        return ResultInfo.success();
    }

    /**
    *门慢特打印
    * Author wzn
    * Date 2022/5/20 10:16
    */
    @RequestMapping("/opspDisePrint")
    public ResultInfo opspDisePrint(String id) {
        return ResultInfo.success(opspDiseListBService.opspDisePrint(id));
    }


    /**
    *门慢特互斥校验
    * Author wzn
    * Date 2022/11/10 14:52
    */
    @RequestMapping("/mutuallyExclusiveOrNot")
    public ResultInfo mutuallyExclusiveOrNot(@RequestBody  OpspDise opspDise) {
       boolean ishc = false ;
       if(CollectionUtils.isNotEmpty(opspDise.getOpspDiseListBVos())){
           List<OpspDiseListBVo> opspDiseListBVoList =  opspDise.getOpspDiseListBVos() ;
           if(!ishc){
               //门慢特
               List<OpspDiseListBVo> opspDiseListBVoList1 = opspDiseListBService.mutuallyExclusiveOrNot2(opspDiseListBVoList.get(0).getCertNo()) ;
               List<OpspCertDVo> certDVoList = new ArrayList<>() ;
               if(CollectionUtils.isNotEmpty(opspDiseListBVoList1)){
                   for(OpspDiseListBVo od:opspDiseListBVoList1){
                       OpspCertDVo opspCertDVo = new OpspCertDVo() ;
                       QueryWrapper<DiseaseMutex> queryWrapper = new QueryWrapper<>() ;
                       queryWrapper.eq("dise_code",od.getOpsp_dise_code()) ;
                       queryWrapper.eq("insured_type",opspDiseListBVoList.get(0).getInsutype()) ;
                       queryWrapper.eq("type",1) ;
                       //门慢特 互斥数据
                       List<DiseaseMutex> diseaseMutexList = diseaseMutexMapper.selectList(queryWrapper) ;
                       if(CollectionUtils.isNotEmpty(diseaseMutexList)){
                           for(DiseaseMutex d:diseaseMutexList){
                               opspCertDVo.setOpsp_dise_code(d.getDise_mutex_code());
                               certDVoList.add(opspCertDVo) ;
                           }

                       }
                   }
               }

               //单病种
               List<OpspDiseListBVo> opspDiseListBVoList2 = opspDiseListBService.mutuallyExclusiveOrNot3(opspDiseListBVoList.get(0).getCertNo()) ;
               if(CollectionUtils.isNotEmpty(opspDiseListBVoList2)){
                   for(OpspDiseListBVo od:opspDiseListBVoList2){
                       OpspCertDVo opspCertDVo = new OpspCertDVo() ;
                       QueryWrapper<DiseaseMutex> queryWrapper = new QueryWrapper<>() ;
                       queryWrapper.eq("dise_code",od.getOpsp_dise_code()) ;
                       queryWrapper.eq("insured_type",opspDiseListBVoList.get(0).getInsutype()) ;
                       queryWrapper.eq("type",2) ;
                       //单病种 互斥数据
                       List<DiseaseMutex> diseaseMutexList = diseaseMutexMapper.selectList(queryWrapper) ;
                       if(CollectionUtils.isNotEmpty(diseaseMutexList)){
                           for(DiseaseMutex d:diseaseMutexList){
                               opspCertDVo.setOpsp_dise_code(d.getDise_mutex_code());
                               certDVoList.add(opspCertDVo) ;
                           }

                       }
                   }
               }


               //所有的互斥数据和页面选中的病种进行比对
               for(OpspDiseListBVo odl:opspDiseListBVoList){
                   for(OpspCertDVo c:certDVoList){
                       if(odl.getOpsp_dise_code().equals(c.getOpsp_dise_code())){
                           ishc = true ;
                       }
                   }
               }
           }
       }

        return ResultInfo.success(ishc);
    }



    /**
     *图片上传
     * Author wzn
     */
    @RequestMapping("/uploadPic")
    public ResultInfo uploadPic(List<MultipartFile> file,String id) {
        for(MultipartFile m:file){
            //清除文件
            List<FileInfo> fileInfos = fileInfoMapper.selectList(Wrappers.<FileInfo>lambdaQuery()
                    .eq(FileInfo::getBizId, id)
                    .eq(FileInfo::getBizType, "7")
            );
            for (FileInfo fileInfo : fileInfos) {
                fastDfsUtil.deleteFile(fileInfo);
            }
            //上传文件服务器
            FastDfsParams params = new FastDfsParams("opsp/pic", m, "7", id);
            params.setFileName(m.getOriginalFilename());
        }

        return ResultInfo.success();
    }



}
