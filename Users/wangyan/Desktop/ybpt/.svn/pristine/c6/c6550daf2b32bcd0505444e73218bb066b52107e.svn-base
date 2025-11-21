package com.jsdc.ybpt.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jsdc.ybpt.model.FileInfo;
import com.jsdc.ybpt.price.declare.SbBedDeclaration;
import com.jsdc.ybpt.price.declare.SbBusinessProject;
import com.jsdc.ybpt.price.declare.SbCivilianMedical;
import com.jsdc.ybpt.price.declare.SbGovernmentMedical;
import com.jsdc.ybpt.service.FileInfoService;
import com.jsdc.ybpt.service.SbBedDeclarationService;
import com.jsdc.ybpt.service.declare.SbBusinessProjectService;
import com.jsdc.ybpt.service.declare.SbCivilianMedicalService;
import com.jsdc.ybpt.service.declare.SbGovernmentMedicalService;
import com.jsdc.ybpt.util.FastDfs.FastDfsParams;
import com.jsdc.ybpt.util.FastDfs.FastDfsUtil;
import com.jsdc.ybpt.util.ZipUtils;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName MainController
 * @Description TODO
 * @Author xujian
 * @Date 2022/4/20 17:29
 * @Version 1.0
 */
@RestController
@RequestMapping("/fileInfo")
public class FileInfoController {
    @Value("${fastDfs_downurl}")
    private String dow_path;
    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private FastDfsUtil fastDfsUtil;
    @Autowired
    private SbGovernmentMedicalService sbGovernmentMedicalService;
    @Autowired
    private SbCivilianMedicalService sbCivilianMedicalService;
    @Autowired
    private SbBusinessProjectService sbBusinessProjectService;

    @Autowired
    private SbBedDeclarationService sbBedDeclarationService ;


    /**
     * 上传价格申报明细文件
     */
    @RequestMapping("/uploadDetailFile")
    public ResultInfo uploadDetailFile(String fileInfoId, MultipartFile file) {
        //清除文件
        if (StrUtil.isNotEmpty(fileInfoId)) {
            FileInfo fileInfo = fileInfoService.getById(fileInfoId);
            fastDfsUtil.deleteFile(fileInfo);
        }
        //上传文件服务器
        if (file != null) {
            FastDfsParams params = new FastDfsParams("price_declaration/detail", file, "8", "");
            params.setFileName(file.getOriginalFilename());
            return fastDfsUtil.uploadFile(params);
        }
        return ResultInfo.success();
    }

    /**
     * 压缩下载价格申报明细文件
     */
    @RequestMapping("/downloadDetailFile")
    public void downloadDetailFile(HttpServletResponse response,String id,String natures,String type) {
        //压缩下载
        List<Map<String, Object>> sourceMapList = new ArrayList<Map<String, Object>>();
        List<FileInfo> fileInfoList = new ArrayList<>();
        List ids = new ArrayList();
        //申报类型 1.西药 2.中成药 3.中药饮片 4.市场调节价项目 5. 耗材
        // 6.自设项目 7.新增医疗服务 8.市管未定价项目 9.其他病房床位 10.单人间、套房床位
        //11 制剂申请
        if (natures.equals("营利性") || natures.equals("民办非营利")) {
            if (type.equals("4") || type.equals("7") || type.equals("8")) {
                List<SbCivilianMedical> sbCivilianMedical = sbCivilianMedicalService.list(Wrappers.<SbCivilianMedical>lambdaQuery()
                        .eq(SbCivilianMedical::getSb_apply_id, id)
                );
                ids = sbCivilianMedical.stream().map(SbCivilianMedical::getId).collect(Collectors.toList());
            }
            if (type.equals("6")) {
                List<SbBusinessProject> sbBusinessProject = sbBusinessProjectService.list(Wrappers.<SbBusinessProject>lambdaQuery()
                        .eq(SbBusinessProject::getApply_id, id)
                );
                ids = sbBusinessProject.stream().map(SbBusinessProject::getId).collect(Collectors.toList());
            }
        }
        if (natures.equals("政府非营利")) {
            if (type.equals("4") || type.equals("7") || type.equals("8")) {
                List<SbGovernmentMedical> sbGovernmentMedical = sbGovernmentMedicalService.list(Wrappers.<SbGovernmentMedical>lambdaQuery()
                        .eq(SbGovernmentMedical::getSb_apply_id, id)
                );
                ids = sbGovernmentMedical.stream().map(SbGovernmentMedical::getId).collect(Collectors.toList());
            }
        }

        if (type.equals("9") ||type.equals("10")) {
            QueryWrapper<SbBedDeclaration> queryWrapper = new QueryWrapper<>() ;
            queryWrapper.eq("apply_id",id) ;
            List<SbBedDeclaration> sbBedDeclarations = sbBedDeclarationService.list(queryWrapper) ;
            ids = sbBedDeclarations.stream().map(SbBedDeclaration::getId).collect(Collectors.toList());
        }
        LambdaQueryWrapper<FileInfo> lambdaQueryWrapper = Wrappers.<FileInfo>lambdaQuery();
        if(type.equals("11")){
            ids.add(id);
            //com.jsdc.ybpt.util.FastDfs.FastDfsParams bizType
            lambdaQueryWrapper.eq(FileInfo::getBizType , "25");
        }


        if(ids != null && ids.size() > 0){
            lambdaQueryWrapper.in(FileInfo::getBizId, ids);
            fileInfoList = fileInfoService.list(lambdaQueryWrapper);
            for (FileInfo x : fileInfoList) {
                sourceMapList.add(ZipUtils.getToZipUrlMap(dow_path + x.getFileUrl(), x.getFileName().substring(0, x.getFileName().lastIndexOf(".")), "附件下载"));
            }
        }
        String uuid = IdUtil.simpleUUID();
        String name = dow_path + "/" + uuid + ".zip";
        ServletOutputStream outputStream = null;

        if(sourceMapList != null && sourceMapList.size() > 0){
            try {
                outputStream = response.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ZipUtils.toZipHTTP(sourceMapList, outputStream);
        }
    }
}
