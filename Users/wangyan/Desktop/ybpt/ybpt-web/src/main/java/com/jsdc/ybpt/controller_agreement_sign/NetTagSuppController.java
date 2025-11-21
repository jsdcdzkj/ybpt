package com.jsdc.ybpt.controller_agreement_sign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.agreementsignModel.NetTagFile;
import com.jsdc.ybpt.agreementsignModel.NetTagMechanism;
import com.jsdc.ybpt.agreementsignModel.NetTagSupp;
import com.jsdc.ybpt.mapper.agreementsignMapper.NetTagFileMapper;
import com.jsdc.ybpt.model.FileInfo;
import com.jsdc.ybpt.service.FixmedinsBService;
import com.jsdc.ybpt.service.MedicalOrgService;
import com.jsdc.ybpt.service.agreementsignService.NetTagFileService;
import com.jsdc.ybpt.service.agreementsignService.NetTagSuppService;
import com.jsdc.ybpt.util.FastDfs.FastDfsParams;
import com.jsdc.ybpt.util.FastDfs.FastDfsUtil;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 协议网签-补充协议表
 */
@RestController
@RequestMapping("/tagsupp")
public class NetTagSuppController {


    @Autowired
    private NetTagSuppService netTagSuppService;
    @Autowired
    private MedicalOrgService medicalOrgService;
    @Autowired
    private FixmedinsBService fixmedinsBService;
    @Autowired
    private NetTagFileMapper netTagFileService;
    @Value("${netTagFilePath}")
    private String netTagFilePath;
    @Autowired
    private NetTagFileService fileService;

    @Value("${netTagNginxPath}")
    private String nginxPath;

    @Autowired
    private FastDfsUtil fastDfsUtil;

    /**
     * 补充协议管理-补充协议列表查询
     */
    @RequestMapping("/selectNetTagSupp")
    public ResultInfo selectNetTagSupp(@RequestBody NetTagSupp netTagSupp) {
        Page<NetTagSupp> pages = netTagSuppService.getAllNetTagMechanism(netTagSupp.getPageNo(), netTagSupp.getPageSize(), netTagSupp);

        return ResultInfo.success(pages);
    }

    /**
     * 补充协议管理-补充协议列表查询
     */
    @RequestMapping("/getList")
    public ResultInfo getList(@RequestBody NetTagSupp netTagSupp) {
        List<NetTagSupp> list = netTagSuppService.getList(netTagSupp);
        return ResultInfo.success(list);
    }

    /**
     * 补充协议管理-补充协议新增 修改
     */
    @RequestMapping(value = "/insertNetTagSupp", method = RequestMethod.POST)
    public ResultInfo insertNetTagSupp(@RequestBody NetTagSupp netTagSupp) {
        netTagSuppService.editNetTagSupp(netTagSupp);
        return ResultInfo.success();
    }


    @RequestMapping("/upload")
    @ResponseBody
    public ResultInfo multipleCommentImageUpload(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "file", required = false) List<MultipartFile> files) {
        response.setContentType("text/html;charset=utf-8");
        String id = UUID.randomUUID().toString();
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename(); //获取文件名
            int index = fileName.lastIndexOf(".");
            String fileSuffix = fileName.substring(index);
            String file_name = UUID.randomUUID().toString() + fileSuffix;
            System.out.println(file);
            String file_path = netTagFilePath + "model";
            try {
                //上传文件服务器
                FastDfsParams params = new FastDfsParams("netTagFile/model", file, "20", id);
                params.setFileName(file.getOriginalFilename());
                ResultInfo<FileInfo>  resultInfo = fastDfsUtil.uploadFile(params);

                //uploadFile(file.getBytes(), file_path, file_name);
                NetTagFile netTagFile = new NetTagFile();
                netTagFile.setOldFileName(fileName);
                netTagFile.setNewFileName(file_name);
                netTagFile.setIs_del("0");
                netTagFile.setId(id);
                netTagFile.setFilePath(resultInfo.getData().getFileUrl());
                netTagFile.setCreateTime(new Date());
                netTagFileService.insert(netTagFile);
                id = netTagFile.getId();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return ResultInfo.success(id);
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


    /**
     * 补充协议管理-补充协议查看
     */
    @RequestMapping("/getOneNetTagSupp")
    public ResultInfo getOneNetTagSupp(@RequestBody NetTagSupp netTagSupp) {
        return ResultInfo.success(netTagSuppService.getOneNetTagSupp(netTagSupp.getId()));
    }


    /**
     * 补充协议列表-补充协议列表 查询
     */
    @RequestMapping("/getAllNetTagMechanism")
    public ResultInfo getAllNetTagMechanism(@RequestBody NetTagMechanism netTagMechanism) {
        Page<NetTagMechanism> pages = netTagSuppService.getAllNetTagMechanism(netTagMechanism.getPageNo(), netTagMechanism.getPageSize(), netTagMechanism);
        pages.getRecords().forEach(x -> {
            NetTagSupp supp = netTagSuppService.getById(x.getAgreement_id());
            if(null != supp){
                Date invalidDate = supp.getInvalid_date();
                x.setInvalid_date(invalidDate);
            }

        });
        return ResultInfo.success(pages);

    }


//    /**
//     * 补充协议列表-补充协议发起
//     */
//    @RequestMapping("/initiate")
//    public ResultInfo initiate(NetTagSupp netTagSupp) {
//        return netTagSuppService.initiate(netTagSupp);
//    }


    /**
     * 补充协议列表-补充协议解约
     * 根据ID查询解约
     */
    @RequestMapping("/termination")
    public ResultInfo termination(Integer id) {
        return ResultInfo.success(netTagSuppService.termination(id));
    }


    /**
     * 根据文件ID获取文件信息
     */
    @RequestMapping("/getFileInfo")
    public ResultInfo getFileInfo(@RequestBody NetTagFile netTagFile) {

        System.out.println(netTagFile.getId());
        return ResultInfo.success(fileService.picList(netTagFile.getId()));
    }


    /**
     * 根据文件ID获取文件信息
     */
    @RequestMapping("/getFileUrl")
    public ResultInfo getFileUrl(@RequestBody NetTagFile netTagFile) {
        System.out.println(netTagFile.getId());
        return ResultInfo.success(fileService.getFile(netTagFile.getId()));
    }

    /**
     * 获取医药机构端补充协议列表
     *
     * @param netTagMechanism
     * @return
     */
    @RequestMapping("/getMechanismList")
    public ResultInfo getMechanismList(@RequestBody NetTagMechanism netTagMechanism) {
        Page<NetTagMechanism> pages = netTagSuppService.mechanismReplenish(netTagMechanism.getPageNo(), netTagMechanism.getPageSize(), netTagMechanism);
        return ResultInfo.success(pages);
    }

    /**
     * 医药机构端获取根据ID获取信息
     *
     * @param netTagMechanism
     * @return
     */
    @RequestMapping("/getInfoById")
    public ResultInfo getInfoById(@RequestBody NetTagMechanism netTagMechanism) {
        return ResultInfo.success(netTagSuppService.getInfoById(netTagMechanism.getId()));
    }

    /**
     * 医药机构端确认按钮
     *
     * @param netTagMechanism
     * @return
     */
    @RequestMapping("/mechanismSure")
    public ResultInfo mechanismSure(@RequestBody NetTagMechanism netTagMechanism) {
        return netTagSuppService.mechanismSure(netTagMechanism.getId());
    }

    /**
     * validateApply 网签申请 验证是否可以申请
     */
    @RequestMapping("/validateApply")
    public ResultInfo validateApply() {
        return netTagSuppService.validateApply();
    }





}
