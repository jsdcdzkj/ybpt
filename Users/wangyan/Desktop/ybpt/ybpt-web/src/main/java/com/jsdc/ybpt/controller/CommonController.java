package com.jsdc.ybpt.controller;

import com.jsdc.ybpt.model.SysFile;
import com.jsdc.ybpt.service.CommonService;
import com.jsdc.ybpt.util.FileRepository;
import com.jsdc.ybpt.util.FileUtils;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.vo.NatDataDicAVo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.UUID;


@Controller
@RequestMapping("/common")
public class CommonController {
  @Autowired
  private FileRepository fileRepository;

  @Autowired
  private CommonService commonService ;

  @Value("${uploadPath}")
  private String uploadPath;

  @Value("${printPath}")
  private String printPath;

  @Value("${reflAppyEvtCPrintPath}")
  private String reflAppyEvtCPrintPath;

  @Value("${sbApplyPath}")
  private String sbApplyPath;

  @RequestMapping("/upload")
  @ResponseBody
  public ResultInfo multipleCommentImageUpload(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "file", required = false) List<MultipartFile> files,@RequestParam(value = "id", required = false) String id) {
    response.setContentType("text/html;charset=utf-8");
    for (MultipartFile file:files) {
      String fileName = file.getOriginalFilename(); //获取文件名
      String file_name = UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";
      String file_path = uploadPath;
      try {
        uploadFile(file.getBytes(),file_path,file_name) ;
        SysFile sysFile = new SysFile() ;
        sysFile.setOldFileName(fileName);
        sysFile.setNewFileName(file_name);
        sysFile.setAssociationId(id);
        sysFile.setFilePath(file_path+"/"+file_name);
        sysFile.insert() ;
//        fileRepository.storePhotoByExt(file, file_path);
      } catch (IOException e) {
        e.printStackTrace();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return ResultInfo.success();
  }

  public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {

    File targetFile = new File(filePath);
    if (!targetFile.exists()) {
      targetFile.mkdirs();
    }
    FileOutputStream out = new FileOutputStream(filePath + "/" + fileName);
    out.write(file);
    out.flush();
    out.close();
  }



  @RequestMapping("/readImg")
  public void loadPhoto(HttpServletResponse response,String filePath) {

    //将一个图片加载到内存
    BufferedImage image = null;
    try {
      image = ImageIO.read(new FileInputStream(uploadPath+"/"+filePath));
    } catch (IOException e) {
      throw new RuntimeException("把图片加载到内存失败", e);
    }

    // 将图片输出给浏览器
    response.setContentType("image/png");//声明返回的是png格式的图片。
    try {
      OutputStream os = response.getOutputStream();
      ImageIO.write(image, "png", os);
      //不用关闭这个流，spring会自动帮我们关闭这个流。
    } catch (IOException e) {
      throw new RuntimeException("响应图片失败，服务器发生异常!", e);
    }
  }


  //下载本地Excel模板
  @GetMapping("/exceldownload")
  public void exceldownload(String fileName, Boolean delete, String type,HttpServletRequest request, HttpServletResponse response, String name)
          throws Exception {
    // 本地资源路径
    String localPath = "";
    if (StringUtils.isNotEmpty(type) && ("reflAppyEvtC").equals(type)) {
      localPath = reflAppyEvtCPrintPath;
    } else if(StringUtils.isNotEmpty(type) && ("sbApply").equals(type)){
      localPath = sbApplyPath;
    }else {
      localPath = printPath;
    }
    // 文件路径
    String downloadPath = localPath + "/"+fileName;
    response.setCharacterEncoding("utf-8");
    response.setContentType("multipart/form-data");
    response.setHeader("Content-Disposition",
            "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, name));
    FileUtils.writeBytes(downloadPath, response.getOutputStream());
    if (delete) {
      FileUtils.deleteFile(downloadPath);
    }
  }



  /**
  *根据类型查询下拉数据
  * Author wzn
  * Date 2022/6/13 16:59
  */
  @PostMapping("/upData")
  @ResponseBody
  public ResultInfo upData(String dic_type_code) {
    List<NatDataDicAVo> diagListBVos = commonService.selectDicList(dic_type_code) ;
    return ResultInfo.success(diagListBVos);
  }



}
