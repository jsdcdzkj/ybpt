package com.jsdc.ybpt.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.mapper.FileInfoMapper;
import com.jsdc.ybpt.model.FileInfo;
import com.jsdc.ybpt.model.FixmedinsB;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model.UnfixedMechanism;
import com.jsdc.ybpt.price.declare.SbApply;
import com.jsdc.ybpt.price.declare.SbApplyVo;
import com.jsdc.ybpt.service.FixmedinsBService;
import com.jsdc.ybpt.service.SbWesternMedicineService;
import com.jsdc.ybpt.service.SysUserService;
import com.jsdc.ybpt.service.UnfixedMechanismService;
import com.jsdc.ybpt.service.declare.SbApplyService;
import com.jsdc.ybpt.util.FastDfs.FastDfsParams;
import com.jsdc.ybpt.util.FastDfs.FastDfsUtil;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.List;

/**
 * 申诉(SbApply)表控制层
 *
 * @author wangYan
 * @since 2023-02-01 16:58:15
 */
@RestController
@RequestMapping("sbApply")
public class SbApplyController {
    /**
     * 服务对象
     */
    @Resource
    private SbApplyService sbApplyService;
    @Autowired
    private SbWesternMedicineService sbWesternMedicineService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private FileInfoMapper fileInfoMapper;
    @Autowired
    private FastDfsUtil fastDfsUtil;
    @Autowired
    private FixmedinsBService fixmedinsBService;
    @Autowired
    private UnfixedMechanismService unfixedMechanismService;

    /**
     * 上传医疗凭证
     */
    @RequestMapping("/uploadLicence")
    public ResultInfo uploadLicence(MultipartFile file) {
        SysUser sysUser = sysUserService.getUser();
        //清除文件
        List<FileInfo> fileInfos = fileInfoMapper.selectList(Wrappers.<FileInfo>lambdaQuery()
                .eq(FileInfo::getBizId, sysUser.getOrg_code() + "-licence")
                .eq(FileInfo::getBizType, "4")
        );
        for (FileInfo fileInfo : fileInfos) {
            fastDfsUtil.deleteFile(fileInfo);
        }
        //上传文件服务器
        FastDfsParams params = new FastDfsParams("price_declaration/licence", file, "4", sysUser.getOrg_code() + "-licence");
        params.setFileName(file.getOriginalFilename());
        return fastDfsUtil.uploadFile(params);
    }

    @RequestMapping("/getLicence")
    public ResultInfo getLicence(String org_code) {
        SysUser sysUser = sysUserService.getUser();
        String code = "";
        if (StringUtils.isNotEmpty(org_code)) {
            code = org_code;
        } else {
            code = sysUser.getOrg_code();
        }
        List<FileInfo> fileInfo = fileInfoMapper.selectList(Wrappers.<FileInfo>lambdaQuery()
                .eq(FileInfo::getBizId, code + "-licence")
                .eq(FileInfo::getBizType, "4")
        );
        if (fileInfo == null || fileInfo.size() == 0) {
            return ResultInfo.success(new FileInfo());
        }
        return ResultInfo.success(fileInfo.get(fileInfo.size() - 1));
    }

//    /**
//     * 上传  价格申报-详情文件
//     */
//    @RequestMapping("/uploadPrice")
//    public ResultInfo uploadPrice(MultipartFile file) {
//        SysUser sysUser = sysUserService.getUser();
//        //清除文件
//        List<FileInfo> fileInfos = fileInfoMapper.selectList(Wrappers.<FileInfo>lambdaQuery()
//                .eq(FileInfo::getBizType, "8")
//        );
//        for (FileInfo fileInfo : fileInfos) {
//            fastDfsUtil.deleteFile(fileInfo);
//        }
//        //上传文件服务器
//        FastDfsParams params = new FastDfsParams("consumables", file, "8", sysUser.getId());
//        params.setFileName(file.getOriginalFilename());
//        return fastDfsUtil.uploadFile(params);
//    }
//
//    @RequestMapping("/getPrice")
//    public ResultInfo getPrice(Integer pageNo, Integer pageSize, String fileName) {
//        LambdaQueryWrapper<FileInfo> lambdaQueryWrapper = new LambdaQueryWrapper();
//        lambdaQueryWrapper.eq(FileInfo::getBizType, "8");
//        if (StringUtils.isNotEmpty(fileName)) {
//            lambdaQueryWrapper.like(FileInfo::getFileName, fileName);
//        }
//        Page<FileInfo> fileInfos = fileInfoMapper.selectPage(new Page(pageNo, pageSize), lambdaQueryWrapper);
//        for (FileInfo fileInfo : fileInfos.getRecords()) {
//            Integer size = Integer.valueOf(fileInfo.getFileSize()) / 1024 / 1024;
//            if (size == 0) {
//                fileInfo.setFileSize(Integer.valueOf(fileInfo.getFileSize()) / 1024 + "KB");
//            } else {
//                fileInfo.setFileSize(size + "MB");
//            }
//            SysUser sysUser = sysUserService.getById(fileInfo.getBizId());
//            if (sysUser != null) {
//                fileInfo.setFileMd5(sysUser.getUsername());
//            }
//        }
//        return ResultInfo.success(fileInfos);
//    }


    @RequestMapping("/getFixmedinsB")
    public ResultInfo getFixmedinsB() {
        SysUser sysUser = sysUserService.getUser();
        if ("7".equals(sysUser.getUser_type()) || "8".equals(sysUser.getUser_type())) {
            UnfixedMechanism unfixedMechanism = unfixedMechanismService.getOne(Wrappers.<UnfixedMechanism>lambdaQuery()
                    .eq(UnfixedMechanism::getFixmedins_code, sysUser.getOrg_code())
                    .eq(UnfixedMechanism::getIs_del, "0")
            );
            if (unfixedMechanism != null) {
                unfixedMechanism.setBiznet(unfixedMechanism.getManage_quality());
            }
            return ResultInfo.success(unfixedMechanism);
        } else {
            FixmedinsB fixmedinsB = fixmedinsBService.getOne(Wrappers.<FixmedinsB>lambdaQuery()
                    .eq(FixmedinsB::getFixmedins_code, sysUser.getOrg_code())
                    .eq(FixmedinsB::getIs_del, "0")
            );
            return ResultInfo.success(fixmedinsB);
        }
    }


    /**
     * 公立医疗机构实行市场调节价管理医疗服务项目价格明细表 提交
     *
     * @param sbApply 实体对象
     * @return 新增结果
     */
    @RequestMapping("/insertGovernmentMedical")
    public ResultInfo insertGovernmentMedical(List<MultipartFile> file, @RequestBody SbApply sbApply) {
        return sbApplyService.insertGovernmentMedical(sbApply);
    }

    @RequestMapping("/down")
    public void down(SbApplyVo vo, HttpServletRequest request, HttpServletResponse response) {
        sbApplyService.down(vo,request,response);
    }

    /**
     * 非公立医疗机构医疗服务项目自主定价明细表 提交
     *
     * @param sbApply 实体对象
     * @return 新增结果
     */
    @RequestMapping("/insertCivilianMedical")
    public ResultInfo insertCivilianMedical(@RequestBody SbApply sbApply) {
        return sbApplyService.insertCivilianMedical(sbApply);
    }

    /**
     * 自主定价明细表 提交
     *
     * @param sbApply 实体对象
     * @return 新增结果
     */
    @RequestMapping("/insertCivilianMaterial")
    public ResultInfo insertCivilianMaterial(@RequestBody SbApply sbApply) {
        return sbApplyService.insertCivilianMaterial(sbApply);
    }

    /**
     * 公立医疗机构实行市场调节价管理医疗服务项目价格明细表 查看
     *
     * @param id 申述主键
     * @return 新增结果
     */
    @RequestMapping("/lookStateHospital")
    public ResultInfo lookStateHospital(String id) {
        return sbApplyService.lookStateHospital(id);
    }


    /**
     * 分页查询所有数据
     *
     * @param pageNo
     * @param pageSize
     * @param sbApply  查询实体
     * @return 所有数据
     */
    @RequestMapping("/selectAll")
    public ResultInfo selectAll(Integer pageNo, Integer pageSize, SbApply sbApply) {
        return ResultInfo.success(this.sbApplyService.page(new Page(pageNo, pageSize), new QueryWrapper<>(sbApply)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @RequestMapping("/selectOne{id}")
    public ResultInfo selectOne(@PathVariable Serializable id) {
        return ResultInfo.success(this.sbApplyService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param sbApply 实体对象
     * @return 新增结果
     */
    @RequestMapping("/insert")
    public ResultInfo insert(@RequestBody SbApply sbApply) {
        return ResultInfo.success(this.sbApplyService.save(sbApply));
    }

    /**
     * 修改数据
     *
     * @param sbApply 实体对象
     * @return 修改结果
     */
    @RequestMapping("/update")
    public ResultInfo update(@RequestBody SbApply sbApply) {
        return ResultInfo.success(this.sbApplyService.updateById(sbApply));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @RequestMapping("/delete")
    public ResultInfo delete(@RequestParam("idList") List<Long> idList) {
        return ResultInfo.success(this.sbApplyService.removeByIds(idList));
    }
}

