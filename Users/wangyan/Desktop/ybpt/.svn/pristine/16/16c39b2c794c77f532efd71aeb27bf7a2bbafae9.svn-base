package com.jsdc.ybpt.controller;

import com.jsdc.ybpt.model.SysFile;
import com.jsdc.ybpt.service.CheckSuspicionsService;
import com.jsdc.ybpt.util.FileRepository;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@Controller
@RequestMapping("/common")
public class CommonController {
  @Autowired
  private FileRepository fileRepository;

  @Autowired
  private CheckSuspicionsService checkSuspicionsService ;

  @Value("${uploadPath}")
  private String uploadPath;

  @PostMapping("/testFeign")
  @ResponseBody
  public ResultInfo testFeign()throws Exception{
    System.out.println(getTomcatPort());
    return ResultInfo.success(getTomcatPort());
  }

  /**
   *	获取外部tomcat端口
   */
  public String getTomcatPort() throws Exception {
    MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
    Set<ObjectName> objectNames = beanServer.queryNames(new ObjectName("*:type=Connector,*"), Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));
    String port = objectNames.iterator().next().getKeyProperty("port");

    return port;
  }

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



}
