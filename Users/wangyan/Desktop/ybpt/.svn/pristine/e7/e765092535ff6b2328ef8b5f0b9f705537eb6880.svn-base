package com.jsdc.ybpt.util.FastDfs;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.resource.InputStreamResource;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.jsdc.ybpt.model.FileInfo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;
import java.util.HashMap;

/**
 * FastDfs文件服务器
 */
@Component
public class FastDfsUtil {
    @Value("${fastDfs_url}")
    private  String upload_url;





    /**
     * 像fastdfs发送请求
     *
     * @param url
     * @param params
     * @return
     */
    private JSONObject sendPost(String url, HashMap params) {
        String result = HttpUtil.post(upload_url + url, params);
        return JSONObject.parseObject(result);
    }

    /**
     * 上传图片
     * @param params
     * @return
     */
    public ResultInfo uploadPicFile(FastDfsParams params) {
        MultipartFile multipartFile = params.getFile();
        //文件名
        String fileName = multipartFile.getOriginalFilename();
        //后缀名
        String fileSuffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        HashMap<String, Object> paramMap = new HashMap<>();
        //文件
        InputStreamResource file = null;
        try {
            file = new InputStreamResource(multipartFile.getInputStream(),
                    multipartFile.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }
        paramMap.put("file", file);
        //输出
        paramMap.put("output", "json");
        //自定义路径
        paramMap.put("path", "picture/"+params.getUploadPath());
        //场景
        paramMap.put("scene", "picture");
        //文件名
        paramMap.put("filename", IdUtil.simpleUUID()+fileSuffix);
        //上传
        JSONObject result = sendPost(FastDfsUrl.UPLOAD_URL,paramMap);
        FileInfo fileInfo = new FileInfo();
        if("fail".equals(result.getString("status"))){
            return ResultInfo.error(result.getString("message"));
        }else{
            fileInfo.setId(IdUtil.simpleUUID());
            fileInfo.setFileName(fileName);
            fileInfo.setContentType(multipartFile.getContentType());
            fileInfo.setFileType("1");
            fileInfo.setFileSize(multipartFile.getSize()+"");
            fileInfo.setFileUrl(result.getString("path"));
            fileInfo.setUploadTime(DateUtil.formatDateTime(new Date()));
            fileInfo.setBizType(params.getBizType());
            fileInfo.setBizId(params.getBizId());
            fileInfo.setFileMd5(result.getString("md5"));
            fileInfo.insert();
        }
        return ResultInfo.success(fileInfo);
    }

    /**
     * 上传文件
     * @param params
     * @return
     */
    public ResultInfo uploadFile(FastDfsParams params) {
        MultipartFile multipartFile = params.getFile();
        //文件名
        String fileName = "";
        if(StrUtil.isNotEmpty(params.getFileName())){
            fileName = params.getFileName();
        }else{
            fileName = multipartFile.getOriginalFilename();
        }

        //后缀名
        String fileSuffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        HashMap<String, Object> paramMap = new HashMap<>();
        //文件
        InputStreamResource file = null;
        try {
            file = new InputStreamResource(multipartFile.getInputStream(),
                    multipartFile.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }
        paramMap.put("file", file);
        //输出
        paramMap.put("output", "json");
        //自定义路径
        paramMap.put("path", "file/"+params.getUploadPath());
        //场景
        paramMap.put("scene", "file");
        //文件名
        paramMap.put("filename", IdUtil.simpleUUID()+fileSuffix);

        //上传
        JSONObject result = sendPost(FastDfsUrl.UPLOAD_URL,paramMap);

        //url -> http://192.168.0.101:8080/file/notifyApply/detail/3e9ded785d744c6695439100f6b54ede.png?name=3e9ded785d744c6695439100f6b54ede.png&download=1
        FileInfo fileInfo = new FileInfo();
        if("fail".equals(result.getString("status"))){
            return ResultInfo.error(result.getString("message"));
        }else{
            fileInfo.setId(IdUtil.simpleUUID());
            fileInfo.setFileName(fileName);
            fileInfo.setContentType(multipartFile.getContentType());
            fileInfo.setFileType("2");
            fileInfo.setFileSize(multipartFile.getSize()+"");
            fileInfo.setFileUrl(result.getString("path"));
            fileInfo.setUploadTime(DateUtil.formatDateTime(new Date()));
            fileInfo.setBizType(params.getBizType());
            fileInfo.setBizId(params.getBizId());
            fileInfo.setFileMd5(result.getString("md5"));
            fileInfo.insert();
        }
        return ResultInfo.success(fileInfo);
    }

    /**
     * 上传文件
     * @param params
     * @return
     */
    public ResultInfo uploadFile2(FastDfsParams params) {
        MultipartFile multipartFile = params.getFile();
        //文件名
        String fileName = "";
        if(StrUtil.isNotEmpty(params.getFileName())){
            fileName = params.getFileName();
        }else{
            fileName = multipartFile.getName();
        }

        //后缀名
        String fileSuffix = multipartFile.getName().substring(multipartFile.getName().lastIndexOf("."));
        HashMap<String, Object> paramMap = new HashMap<>();
        //文件
        InputStreamResource file = null;
        try {
            file = new InputStreamResource(multipartFile.getInputStream(),
                    multipartFile.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        paramMap.put("file", file);
        //输出
        paramMap.put("output", "json");
        //自定义路径
        paramMap.put("path", "file/"+params.getUploadPath());
        //场景
        paramMap.put("scene", "file");
        //文件名
        paramMap.put("filename", IdUtil.simpleUUID()+fileSuffix);
        //上传
        JSONObject result = sendPost(FastDfsUrl.UPLOAD_URL,paramMap);
        FileInfo fileInfo = new FileInfo();
        if("fail".equals(result.getString("status"))){
            return ResultInfo.error(result.getString("message"));
        }else{
            fileInfo.setId(IdUtil.simpleUUID());
            fileInfo.setFileName(fileName);
            fileInfo.setContentType(multipartFile.getContentType());
            fileInfo.setFileType("2");
            fileInfo.setFileSize(multipartFile.getSize()+"");
            fileInfo.setFileUrl(result.getString("path"));
            fileInfo.setUploadTime(DateUtil.formatDateTime(new Date()));
            fileInfo.setBizType(params.getBizType());
            fileInfo.setBizId(params.getBizId());
            fileInfo.setFileMd5(result.getString("md5"));
            fileInfo.insert();
        }
        return ResultInfo.success(fileInfo);
    }


    /**
     * 删除文件
     * @param fileInfo
     * @return
     */
    public ResultInfo deleteFile(FileInfo fileInfo){
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("path",fileInfo.getFileUrl());
        JSONObject result = sendPost(FastDfsUrl.DELETE_URL,paramMap);
        if("fail".equals(result.getString("status"))){
            return ResultInfo.error(result.getString("message"));
        }else{
            fileInfo.deleteById();
            return ResultInfo.success(fileInfo);
        }
    }

    //将MultipartFile转化为File类的方法
    public static File MultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File toFile = null;
        if (!multipartFile.equals("") && !(multipartFile.getSize() <= 0)) {
            InputStream ins = multipartFile.getInputStream();
            toFile = new File(multipartFile.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
