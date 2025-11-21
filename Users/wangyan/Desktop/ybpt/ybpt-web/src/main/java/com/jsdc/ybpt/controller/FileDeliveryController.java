package com.jsdc.ybpt.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpRequest;
import com.jsdc.ybpt.model.FileDelivery;
import com.jsdc.ybpt.model.FileInfo;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.service.FileDeliveryService;
import com.jsdc.ybpt.service.SysUserService;
import com.jsdc.ybpt.util.FastDfs.FastDfsParams;
import com.jsdc.ybpt.util.FastDfs.FastDfsUtil;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/fileDelivery")
public class FileDeliveryController {
    @Autowired
    private FileDeliveryService fileDeliveryService;

    @Autowired
    private SysUserService sysUserService ;

    @Autowired
    private FastDfsUtil fastDfsUtil;

    @Value("${fastDfs_downurl}")
    private String fastDfs_downurl;

    @Value("${decompress_url}")
    private String decompress_url;





    /**
    *文件下发列表
    * Author wzn
    * Date 2023/6/3 14:43
    */
    @PostMapping("/fileDeliveryList")
    public ResultInfo fileDeliveryList(@RequestBody FileDelivery fileDelivery) {
        return ResultInfo.success(fileDeliveryService.fileDeliveryList(fileDelivery));
    }

    /**
    *修改
    * Author wzn
    * Date 2023/6/3 15:41
    */
    @PostMapping("/updateFileDelivery")
    public ResultInfo updateFileDelivery(@RequestBody FileDelivery fileDelivery) {
        fileDeliveryService.updateFileDelivery(fileDelivery) ;
        return ResultInfo.success();
    }


    /**
    *上传文件
    * Author wzn
    * Date 2023/6/5 15:04
    */
    @RequestMapping("/upload")
    public ResultInfo upload(MultipartFile file) {

        SysUser sysUser = sysUserService.getUser();

        //上传文件服务器
        FastDfsParams params = new FastDfsParams("fileDeliveryZip", file, "11", sysUser.getOrg_code());
        String uid = IdUtil.simpleUUID() ;
        params.setFileName(uid);
        ResultInfo resultInfo = fastDfsUtil.uploadFile(params);
        if(resultInfo.getCode() == 0 ){
            //调用解压文件服务
            FileInfo fileInfo = (FileInfo) resultInfo.getData();
            String zipPath = "E:\\files"+fileInfo.getFileUrl()  ;
            String url = decompress_url + "/decompression/init";
            Map<String, String> params2 = new HashMap<>();
            params2.put("zipPath",zipPath) ;
            params2.put("path","E:\\files\\file\\fileDelivery") ;
            String res = HttpRequest.post(URLUtil.encode(url)).formStr(params2).timeout(10000).execute().body();
            //调用新增接口
            fileDeliveryService.addFileDelivery() ;
        }
        return ResultInfo.success();
    }

}
