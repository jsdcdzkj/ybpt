package com.jsdc.ybpt.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.common.BizLog;
import com.jsdc.ybpt.mapper.FileInfoMapper;
import com.jsdc.ybpt.model.Consumables;
import com.jsdc.ybpt.model.FileInfo;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.service.ConsumablesService;
import com.jsdc.ybpt.service.SysUserService;
import com.jsdc.ybpt.util.FastDfs.FastDfsParams;
import com.jsdc.ybpt.util.FastDfs.FastDfsUtil;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@RequestMapping("/consumables")
public class ConsumablesController {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ConsumablesService consumablesService;

    @Autowired
    private FileInfoMapper fileInfoMapper;
    @Autowired
    private FastDfsUtil fastDfsUtil;


    /**
     * 新增耗材接口
     * Author wzn
     * Date 2022/6/30 15:02
     */
    @PostMapping("/addConsumables")
    public ResultInfo addConsumables(@RequestBody Consumables consumables) {
        consumablesService.addConsumables(consumables);
        return ResultInfo.success();
    }

    /**
     * 上传医疗凭证
     */
    @RequestMapping("/fileUpload")
    public ResultInfo fileUpload(MultipartFile file) {
        SysUser sysUser = sysUserService.getUser();
        //清除文件
//        List<FileInfo> fileInfos = fileInfoMapper.selectList(Wrappers.<FileInfo>lambdaQuery()
//                .eq(FileInfo::getBizType, "6")
//        );
//        for (FileInfo fileInfo : fileInfos) {
//            fastDfsUtil.deleteFile(fileInfo);
//        }
        //上传文件服务器
        FastDfsParams params = new FastDfsParams("consumables", file, "6", sysUser.getId());
        params.setFileName(file.getOriginalFilename());
        return fastDfsUtil.uploadFile(params);
    }

    @BizLog(operatType="3",modelName="耗材管理-耗材文件",memo="文件删除操作")
    @RequestMapping("/fileDel")
    public ResultInfo fileDel(String id) {
        //清除文件
        FileInfo fileInfo = fileInfoMapper.selectById(id);
        fastDfsUtil.deleteFile(fileInfo);
        return ResultInfo.success();
    }


    @RequestMapping("/getFile")
    public ResultInfo getFile(Integer pageNo, Integer pageSize, String fileName) {
        LambdaQueryWrapper<FileInfo> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(FileInfo::getBizType, "6");
        if (StringUtils.isNotEmpty(fileName)) {
            lambdaQueryWrapper.like(FileInfo::getFileName, fileName);
        }
        lambdaQueryWrapper.orderByDesc(FileInfo::getUploadTime);
        Page<FileInfo> fileInfos = fileInfoMapper.selectPage(new Page(pageNo, pageSize), lambdaQueryWrapper);
        for (FileInfo fileInfo : fileInfos.getRecords()) {
            Integer size = Integer.valueOf(fileInfo.getFileSize()) / 1024 / 1024;
            if (size == 0) {
                fileInfo.setFileSize(Integer.valueOf(fileInfo.getFileSize()) / 1024 + "KB");
            } else {
                fileInfo.setFileSize(size + "MB");
            }
            SysUser sysUser = sysUserService.getById(fileInfo.getBizId());
            if (sysUser != null) {
                fileInfo.setFileMd5(sysUser.getUsername());
            }
        }
        return ResultInfo.success(fileInfos);
    }


    /**
     * 耗材修改
     * Author wzn
     * Date 2022/6/30 15:02
     */
    @PostMapping("/updateConsumables")
    public ResultInfo updateConsumables(@RequestBody Consumables consumables) {
        consumablesService.updateConsumables(consumables);
        return ResultInfo.success();
    }

    /**
     * 删除
     * Author wzn
     * Date 2022/7/1 9:40
     */
    @PostMapping("/delConsumables")
    public ResultInfo delCiviWorkerInfo(@RequestBody Consumables consumables) {
        consumables.setIs_del("1");
        consumablesService.updateConsumables(consumables);
        return ResultInfo.success();
    }

    /**
     * 耗材列表接口
     * Author wzn
     * Date 2022/6/30 9:24
     */

    @PostMapping("/selectList")
    public ResultInfo selectList(@RequestBody Consumables consumables) {
        Page<Consumables> page = consumablesService.selectList(consumables);
        return ResultInfo.success(page);
    }


    @RequestMapping("/excel")
    public void excel( Consumables consumables, HttpServletResponse response) throws Exception {
        SysUser sysUser = sysUserService.getUser();
        QueryWrapper lambda = new QueryWrapper<Consumables>();
        if (!"".equals(consumables.getMcs_code()) && null != consumables.getMcs_code()) {
            lambda.eq("mcs_code", consumables.getMcs_code());
        }
        if (!"".equals(consumables.getStatus()) && null != consumables.getStatus()) {
            lambda.eq("status", consumables.getStatus());
        }

        if ("2".equals(sysUser.getUser_type()) || "3".equals(sysUser.getUser_type())) {
            lambda.eq("fixmedins_no", sysUser.getOrg_code());
        }
        lambda.orderByDesc("createTime");
        lambda.eq("is_del", "0");
        List<Consumables> list = consumablesService.list(lambda);

        if (null != list && list.size() > 0) {
            for (Consumables c : list) {
                if (!"".equals(c.getStatus())) {
                    if ("0".equals(c.getStatus())) {
                        c.setStatus("待确认");
                    } else if ("1".equals(c.getStatus())) {
                        c.setStatus("已确认");
                    } else if ("2".equals(c.getStatus())) {
                        c.setStatus("已处理");
                    }
                }

            }
        }
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.setOnlyAlias(true);
        writer.addHeaderAlias("fixmedins_no", "定点机构编码");
        writer.addHeaderAlias("medins_name", "定点机构名称");
        writer.addHeaderAlias("mcs_code", "耗材国家编码");
        writer.addHeaderAlias("reg_fil_prod_name", "注册备案产品名称");
        writer.addHeaderAlias("reg_fil_no", "注册备案号");
        writer.addHeaderAlias("matl", "材质");
        writer.addHeaderAlias("characteristics", "特征");
        writer.addHeaderAlias("spec", "规格");
        writer.addHeaderAlias("mol", "型号");
        writer.addHeaderAlias("mcs_entp", "耗材企业");
        writer.addHeaderAlias("product_num", "产品编号");
        writer.addHeaderAlias("status", "状态");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        //out为OutputStream，需要写出到的目标流
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        // 关闭writer，释放内存
        writer.close();
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }

    @RequestMapping("/consumables9001")
    public ResultInfo consumables9001(Consumables c) {
        JSONObject json = new JSONObject();
        json.set("jyCode", "9001");
        return ResultInfo.success(consumablesService.getResultInfo(c, json));
    }

    @RequestMapping("/consumables9002")
    public ResultInfo consumables9002(Consumables c) {
        JSONObject json = new JSONObject();
        json.set("jyCode", "9002");
        return ResultInfo.success(consumablesService.getResultInfo(c, json));
    }

    @RequestMapping("/consumables9003")
    public ResultInfo consumables9003(Consumables c) {
        JSONObject json = new JSONObject();
        json.set("jyCode", "9003");
        return ResultInfo.success(consumablesService.getResultInfo(c, json));
    }

}
