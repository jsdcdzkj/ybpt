package com.jsdc.ybpt.controller_appropNotice;

import cn.hutool.core.io.IoUtil;
import com.jsdc.ybpt.appropNotice.dto.AppropDocumentPageDTO;
import com.jsdc.ybpt.appropNotice.dto.AppropDocumentValidUploadDTO;
import com.jsdc.ybpt.model.FileInfo;
import com.jsdc.ybpt.service.FileInfoService;
import com.jsdc.ybpt.service.appropNotice.AppropDocumentService;
import com.jsdc.ybpt.util.FileUtils;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

/**
 * @Description 拨付文档上传控制层
 * @Author hanch
 * @Date 2024/7/15
 */
@RestController
@RequestMapping("/appropDocument")
public class AppropDocumentController {


    @Autowired
    private AppropDocumentService appropDocumentService;

    @Resource
    private FileInfoService fileInfoService;

    @Value("${fastDfs_downurl}")
    private String dow_path;


    @PostMapping("/page")
    public ResultInfo getPage(@RequestBody AppropDocumentPageDTO appropDocumentPageDTO) {
        return ResultInfo.success(appropDocumentService.getPage(appropDocumentPageDTO));
    }

    @GetMapping("/authList")
    public ResultInfo document() {
        return ResultInfo.success(appropDocumentService.document());
    }

    @PostMapping("/validate")
    public ResultInfo validate(@RequestBody @Validated AppropDocumentValidUploadDTO appropDocumentValidUploadDTO) {
        return ResultInfo.success(appropDocumentService.validate(appropDocumentValidUploadDTO));
    }

    //新增
    @PostMapping("/save")
    public ResultInfo save(@RequestPart(value = "file",required = true) MultipartFile file,
                           @RequestPart(value = "param",required = true) AppropDocumentValidUploadDTO appropDocumentValidUploadDTO) {
        return ResultInfo.success(appropDocumentService.save(file,appropDocumentValidUploadDTO));
    }

    //发布
    @GetMapping("/publish")
    public ResultInfo publish(@RequestParam String id) {
        return ResultInfo.success(appropDocumentService.publish(id));
    }

    //取消发布
    @GetMapping("/off")
    public ResultInfo off(@RequestParam String id) {
        return ResultInfo.success(appropDocumentService.off(id));
    }

    //删除
    @GetMapping("/detete")
    public ResultInfo detete(@RequestParam String id) {
        return ResultInfo.success(appropDocumentService.delete(id));
    }

    //上传文档查询
    @PostMapping("/docList")
    public ResultInfo docList(@RequestBody @Validated AppropDocumentValidUploadDTO appropDocumentValidUploadDTO) {
        return ResultInfo.success(appropDocumentService.docList(appropDocumentValidUploadDTO));
    }

    //下载文件
    @GetMapping("/downLoadFile")
    public void downLoadFile(String id,
                             String bizType,
                             HttpServletRequest request, HttpServletResponse response) {
        InputStream fs = null;
        try {
            List<FileInfo> files = fileInfoService.getFileInfoByBizId(id,bizType);
            FileInfo file = CollectionUtils.isEmpty(files)?null:files.get(0);
            if(file==null)
                return;
            fs = FileUtils.downLoadFile(dow_path,file);
            if(fs!=null){
                    response.setCharacterEncoding("utf-8");
                    response.setContentType("multipart/form-data");
                    response.setHeader("Content-Disposition",
                            "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, file.getFileName()));
                    FileUtils.writeBytes(fs, response.getOutputStream());
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if(StringUtils.isNotNull(fs)){
                IoUtil.close(fs);
            }
        }
    }

    //预览文件
    @GetMapping("/previewFile")
    public void previewFile(String id,
                             String bizType,
                             HttpServletRequest request, HttpServletResponse response) {
        InputStream fs = null;
        try {
            List<FileInfo> files = fileInfoService.getFileInfoByBizId(id,bizType);
            FileInfo file = CollectionUtils.isEmpty(files)?null:files.get(0);
            if(file==null)
                return;
            fs = FileUtils.downLoadFile(dow_path,file);
            String fileType = file.getFileName().substring(file.getFileName().lastIndexOf(".")+1);
            String fileName = file.getFileName().substring(0,file.getFileName().lastIndexOf("."));
            if(fs!=null){
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/octet-stream");
                //response.setContentType("multipart/form-data");
                if(fileType.equals("xls")){
                    fileType = "xlsx";
                    FileUtils.convertXlsToXlsxNew(fs,response.getOutputStream());
                }else{
                    FileUtils.writeBytes(fs, response.getOutputStream());
                }
                response.setHeader("Content-Disposition",
                        "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, fileName+fileType));
                response.setHeader("Content-Type", "application/octet-stream");
                response.setHeader("Content-Transfer-Encoding", "binary");
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if(StringUtils.isNotNull(fs)){
                IoUtil.close(fs);
            }
        }
    }

    @GetMapping("/fileUrl")
    public ResultInfo previewFile(String id,
                            String bizType) {
        List<FileInfo> files = fileInfoService.getFileInfoByBizId(id,bizType);
        if(CollectionUtils.isEmpty(files))
            return ResultInfo.success("");
        return ResultInfo.success(files.get(0).getFileUrl());
    }
}
