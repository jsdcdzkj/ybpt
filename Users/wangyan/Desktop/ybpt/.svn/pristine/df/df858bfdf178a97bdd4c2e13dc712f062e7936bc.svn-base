package com.jsdc.ybpt.service.terminal;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.FileInfoMapper;
import com.jsdc.ybpt.mapper.terminal.TerminalInfoMapper;
import com.jsdc.ybpt.mapper.terminal.TerminalNetworkMapper;
import com.jsdc.ybpt.mapper.terminal.TerminalUserMapper;
import com.jsdc.ybpt.model.FileInfo;
import com.jsdc.ybpt.model.SysDict;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.service.SysDictService;
import com.jsdc.ybpt.service.SysUserRoleService;
import com.jsdc.ybpt.service.SysUserService;
import com.jsdc.ybpt.terminal.TerminalInfo;

import com.jsdc.ybpt.terminal.TerminalNetwork;
import com.jsdc.ybpt.terminal.TerminalUser;
import com.jsdc.ybpt.util.FastDfs.FastDfsParams;
import com.jsdc.ybpt.util.FastDfs.FastDfsUtil;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.util.exception.CustomException;
import com.jsdc.ybpt.vo.ResultInfo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TerminalInfoService extends BaseService<TerminalInfo> {
    private final SysUserService sysUserService;
    private final SysUserRoleService sysUserRoleService;
    private final SysDictService sysDictService;
    private final TerminalInfoMapper terminalInfoMapper;
    private final TerminalNetworkMapper terminalNetworkMapper;
    private final TerminalUserMapper terminalUserMapper;
    private final FileInfoMapper fileInfoMapper;
    private final FastDfsUtil fastDfsUtil;

    private Map<String, String> area_map;
    private Map<String, String> cred_lv_map;
    private Map<String, String> operator_map;

    @Autowired
    public TerminalInfoService(
            SysUserService sysUserService,
            SysUserRoleService sysUserRoleService,
            SysDictService sysDictService,
            TerminalInfoMapper terminalInfoMapper,
            TerminalNetworkMapper terminalNetworkMapper,
            TerminalUserMapper terminalUserMapper,
            FileInfoMapper fileInfoMapper,
            FastDfsUtil fastDfsUtil) {

        this.sysUserRoleService = sysUserRoleService;
        this.sysUserService = sysUserService;
        this.sysDictService = sysDictService;
        this.terminalInfoMapper = terminalInfoMapper;
        this.terminalNetworkMapper = terminalNetworkMapper;
        this.terminalUserMapper = terminalUserMapper;
        this.fileInfoMapper = fileInfoMapper;
        this.fastDfsUtil = fastDfsUtil;
    }

    @PostConstruct
    public void init() {
        // Initialize maps after the dependencies are injected
        this.area_map = sysDictService.select("admdvs-area").stream()
                .collect(Collectors.toMap(SysDict::getValue, SysDict::getLabel));
        this.cred_lv_map = sysDictService.select("LMTPRIC_HOSP_LV").stream()
                .collect(Collectors.toMap(SysDict::getValue, SysDict::getLabel));
        this.operator_map = sysDictService.select("operator").stream()
                .collect(Collectors.toMap(SysDict::getValue, SysDict::getLabel));
    }

    /**
     * 新增修改
     */
    public void addUpdateInfo(TerminalInfo bean){
        //唯一性校验
        boolean check = this.checkOnly(bean) ;
        if(!check){
            throw new CustomException("编码已存在，禁止重复添加") ;
        }
        SysUser sysUser =  sysUserService.getUser();
        bean.setCreateUser(sysUser.getUsername());
        bean.setOrg_code(sysUser.getOrg_code());
        bean.setCreateTime(new Date());
        bean.setIs_del("0");

        //是否提交 0保存 1提交
        if("1".equals(bean.getIs_submit())){
            // 审核状态 0.未提交 1.审核中 2.已审核 3.已驳回
            bean.setStatus(1);
            bean.setVerify_user(sysUser.getUsername());
        }else{
            bean.setStatus(0);
        }

        if(StringUtils.isNotEmpty(bean.getId())){
            bean.updateById();

            QueryWrapper<TerminalNetwork> networkQueryWrapper = new QueryWrapper<>() ;
            networkQueryWrapper.eq("terminal_info_id",bean.getId()) ;
            terminalNetworkMapper.delete(networkQueryWrapper);
            for (TerminalNetwork network : bean.getTerminalNetworks()) {
                network.setId(UUID.randomUUID().toString());
                network.setTerminal_info_id(bean.getId());
                network.insert();
            }
            for (TerminalNetwork network : bean.getTerminalNetworksOther()) {
                network.setId(UUID.randomUUID().toString());
                network.setTerminal_info_id(bean.getId());
                network.insert();
            }

            QueryWrapper<TerminalUser> userQueryWrapper = new QueryWrapper<>() ;
            userQueryWrapper.eq("terminal_info_id",bean.getId()) ;
            terminalUserMapper.delete(userQueryWrapper);
            for (TerminalUser user : bean.getTerminalUsers()) {
                user.setId(UUID.randomUUID().toString());
                user.setTerminal_info_id(bean.getId());
                user.insert();
            }
        }else {
            bean.setId(UUID.randomUUID().toString());
            bean.insert();
            for (TerminalNetwork network : bean.getTerminalNetworks()) {
                network.setId(UUID.randomUUID().toString());
                network.setTerminal_info_id(bean.getId());
                network.insert();
            }
            for (TerminalNetwork network : bean.getTerminalNetworksOther()) {
                network.setId(UUID.randomUUID().toString());
                network.setTerminal_info_id(bean.getId());
                network.insert();
            }

            for (TerminalUser user : bean.getTerminalUsers()) {
                user.setId(UUID.randomUUID().toString());
                user.setTerminal_info_id(bean.getId());
                user.insert();
            }
        }

        //清除文件
        QueryWrapper<FileInfo> fileInfoQueryWrapper = new QueryWrapper<>() ;
        fileInfoQueryWrapper.eq("bizType","34") ;
        fileInfoQueryWrapper.eq("bizId",bean.getId()) ;
        if(bean.getFileIds() != null){
            fileInfoQueryWrapper.notIn("id",bean.getFileIds()) ;
        }
        fileInfoMapper.delete(fileInfoQueryWrapper);
        //绑定文件
        if(bean.getFileIds() != null){
            for (String fileId : bean.getFileIds()) {
                FileInfo fileInfo = fileInfoMapper.selectById(fileId);
                if(null != fileInfo){
                    fileInfo.setBizId(bean.getId());
                    fileInfo.updateById();
                }
            }
        }

    }


    /**
     * 分页
     */
    public Page<TerminalInfo> selectPage(TerminalInfo bean){
        Page page = new Page<>(bean.getPageNo(), bean.getPageSize());
        SysUser sysUser =  sysUserService.getUser();
        if(!sysUser.getOrg_code().equals("320399")){
            bean.setOrg_code(sysUser.getOrg_code());
        }
        List<String> roleSymbol = sysUserRoleService.getRoleSymbolByUser(sysUser.getId());
        bean.setRoleSymbol(roleSymbol);
        Page<TerminalInfo> fixmedinsBPage = terminalInfoMapper.queryPage(page,bean) ;
        if(null != fixmedinsBPage && fixmedinsBPage.getRecords().size() > 0){
            for(TerminalInfo terminalInfo : fixmedinsBPage.getRecords()){
                getNetworkUser(terminalInfo);
            }
        }
        return fixmedinsBPage;
    }

    /**
     * 列表
     */
    public List<TerminalInfo> selectList(TerminalInfo bean){
        SysUser sysUser =  sysUserService.getUser();
        List<String> roleSymbol = sysUserRoleService.getRoleSymbolByUser(sysUser.getId());
        bean.setRoleSymbol(roleSymbol);
        List<TerminalInfo> terminalInfoList = terminalInfoMapper.queryList(bean) ;

        for (TerminalInfo terminalInfo : terminalInfoList) {

            getNetworkUser(terminalInfo);
        }

        return terminalInfoList;
    }

    /**
     * 详情
     */
    public TerminalInfo detail(String id){
        TerminalInfo terminalInfo = terminalInfoMapper.selectById(id);
        getNetworkUser(terminalInfo);
        return terminalInfo;
    }

    /**
     * 删除
     */
    public TerminalInfo delInfo(String id) {
        TerminalInfo terminalInfo = new TerminalInfo() ;
        terminalInfo.setId(id);
        terminalInfo.setIs_del("1");
        updateById(terminalInfo);

        QueryWrapper<TerminalNetwork> networkQueryWrapper = new QueryWrapper<>() ;
        networkQueryWrapper.eq("terminal_info_id",terminalInfo.getId()) ;
        terminalNetworkMapper.delete(networkQueryWrapper);

        QueryWrapper<TerminalUser> userQueryWrapper = new QueryWrapper<>() ;
        userQueryWrapper.eq("terminal_info_id",terminalInfo.getId()) ;
        terminalUserMapper.delete(userQueryWrapper);

        return terminalInfo;
    }

    /**
     * 审核
     */
    public TerminalInfo audit(String id, Integer status, String verify_reason) {
        SysUser sysUser =  sysUserService.getUser();
        TerminalInfo terminalInfo = new TerminalInfo();
        terminalInfo.setId(id);
        terminalInfo.setStatus(status);
        terminalInfo.setVerify_reason(verify_reason);
        terminalInfo.setVerify_user(sysUser.getUsername());
        updateById(terminalInfo);
        return terminalInfo;
    }

    /**
     * 文件上传
     */
    public ResultInfo upload(MultipartFile file, String id) {
        SysUser sysUser = sysUserService.getUser();
        //上传文件服务器
        FastDfsParams params = new FastDfsParams("terminal/orgData/" + sysUser.getOrg_code() + "/", file, "34", id);
        params.setFileName(file.getOriginalFilename());
        return fastDfsUtil.uploadFile(params);
    }

    /**
     * 清除文件
     */
    public ResultInfo removeUpload(String id) {
        //清除文件
        FileInfo fileInfo = fileInfoMapper.selectById(id);
        if (fileInfo != null) {
            fileInfoMapper.deleteById(id);
        }
        return fastDfsUtil.deleteFile(fileInfo);
    }

    private void getNetworkUser(TerminalInfo terminalInfo) {
        QueryWrapper<TerminalNetwork> networkQueryWrapper = new QueryWrapper<>();
        networkQueryWrapper.eq("terminal_info_id",terminalInfo.getId()) ;
        networkQueryWrapper.eq("type","1") ;
        List<TerminalNetwork> networks = terminalNetworkMapper.selectList(networkQueryWrapper);
        terminalInfo.setTerminalNetworks(networks);
        terminalInfo.setTerminalNetworkNumber(networks.size());

        QueryWrapper<TerminalNetwork> networkOtherQueryWrapper = new QueryWrapper<>() ;
        networkOtherQueryWrapper.eq("terminal_info_id",terminalInfo.getId()) ;
        networkOtherQueryWrapper.eq("type","2") ;
        List<TerminalNetwork> networksOther = terminalNetworkMapper.selectList(networkOtherQueryWrapper);
        terminalInfo.setTerminalNetworksOther(networksOther);
        terminalInfo.setTerminalNetworkOtherNumber(networksOther.size());

        QueryWrapper<TerminalUser> userQueryWrapper = new QueryWrapper<>() ;
        userQueryWrapper.eq("terminal_info_id",terminalInfo.getId()) ;
        List<TerminalUser> users = terminalUserMapper.selectList(userQueryWrapper);
        terminalInfo.setTerminalUsers(users);
        terminalInfo.setTerminalUserNumber(users.size());

        List<FileInfo> fileInfos = fileInfoMapper.selectList(Wrappers.<FileInfo>lambdaQuery().eq(FileInfo::getBizId, terminalInfo.getId()).eq(FileInfo::getBizType, "34"));
        if (CollectionUtils.isNotEmpty(fileInfos)) {
            List<FileInfo> fileList = getFileListMap(fileInfos);
            terminalInfo.setFiles(fileList);
            terminalInfo.setFileIds(fileInfos.stream().map(FileInfo::getId).collect(Collectors.toList()));
        }

        // 0.未提交 1.审核中 2.已审核 3.已驳回
        if(terminalInfo.getStatus() == 0){
            terminalInfo.setStatus_name("未提交");
        }else if(terminalInfo.getStatus() == 1){
            terminalInfo.setStatus_name("审核中");
        }else if(terminalInfo.getStatus() == 2){
            terminalInfo.setStatus_name("已审核");
        }else if(terminalInfo.getStatus() == 3){
            terminalInfo.setStatus_name("已驳回");
        }
        terminalInfo.setArea_name(area_map.get(terminalInfo.getArea()));
        terminalInfo.setCred_lv_name(cred_lv_map.get(terminalInfo.getCred_lv()));
        terminalInfo.setOperator_name(operator_map.get(terminalInfo.getOperator()));
    }

    @NotNull
    private List<FileInfo> getFileListMap(List<FileInfo> fileInfos) {
        List<FileInfo> fileList = new ArrayList<>();
        for (FileInfo fileInfo : fileInfos) {
            fileList.add(fileInfo);
        }
        return fileList;
    }


    public boolean checkOnly(TerminalInfo terminalInfo){
        boolean checkOnly = true ; //没有重复
        QueryWrapper<TerminalInfo> queryWrapper = new QueryWrapper<>() ;
        queryWrapper.eq("medical_code",terminalInfo.getMedical_code()) ;
        queryWrapper.eq("is_del","0") ;
        List<TerminalInfo> terminalInfos = terminalInfoMapper.selectList(queryWrapper) ;
        if(!terminalInfos.isEmpty()){
            checkOnly = false ;
            if(null!=terminalInfo.getId()){
                if(terminalInfo.getId().equals(terminalInfos.get(0).getId())){
                    checkOnly = true ;
                }
            }
        }
        return checkOnly ;
    }
}
