package com.jsdc.ybpt.service.agreementsignService;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.agreementsignModel.NetTagAgreement;
import com.jsdc.ybpt.agreementsignModel.NetTagAgreementStatusMember;
import com.jsdc.ybpt.agreementsignModel.NetTagFile;
import com.jsdc.ybpt.agreementsignModel.NetTagMechanism;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.base.BaseServiceNoTransaction;
import com.jsdc.ybpt.mapper.FixmedinsBMapper;
import com.jsdc.ybpt.mapper.agreementsignMapper.NetTagAgreementMapper;
import com.jsdc.ybpt.mapper.agreementsignMapper.NetTagAgreementStatusMemberMapper;
import com.jsdc.ybpt.mapper.agreementsignMapper.NetTagFileMapper;
import com.jsdc.ybpt.mapper.agreementsignMapper.NetTagMechanismMapper;
import com.jsdc.ybpt.model.FileInfo;
import com.jsdc.ybpt.model.FixmedinsB;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.service.MedicalOrgService;
import com.jsdc.ybpt.service.OrganizationInfoService;
import com.jsdc.ybpt.service.SysUserService;
import com.jsdc.ybpt.util.FastDfs.FastDfsParams;
import com.jsdc.ybpt.util.FastDfs.FastDfsUtil;
import com.jsdc.ybpt.util.POIMergeDocUtil;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 网签协议表
 */
@Service
public class NetTagAgreementService extends BaseServiceNoTransaction<NetTagAgreement> {

    @Autowired
    private NetTagAgreementMapper netTagAgreementMapper;

    @Autowired
    private NetTagMechanismMapper netTagMechanismMapper;

    @Autowired
    private NetTagFileMapper netTagFileMapper;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private OrganizationInfoService organizationInfoService;

    @Autowired
    private NetTagAgreementStatusMemberMapper statusMemberMapper;

    @Autowired
    private MedicalOrgService medicalOrgService;

    @Autowired
    private FixmedinsBMapper fixmedinsBMapper;

    @Value("${netTagFilePath}")
    private String netTagFilePath;


    @Value("${netTagNginxPath}")
    private String nginxPath;

    @Value("${signPdfPath}")
    private String fastDfs_downurl;

    @Value("${fastDfs_downurl}")
    private String fastDfs_url;



    @Autowired
    private FastDfsUtil fastDfsUtil;
    /**
     * 根据协议编号查询协议信息
     * @param netTagAgreement
     * @return
     */
    public Page<NetTagAgreement> selectByAgreementNo(int pageIndex, int pageSize,NetTagAgreement netTagAgreement) {

        Page<NetTagAgreement> pages = netTagAgreementMapper.selectPage(new Page<>(pageIndex, pageSize), Wrappers.<NetTagAgreement>lambdaQuery()
                //协议类型
                .eq(null != netTagAgreement.getCategory_id(), NetTagAgreement::getCategory_id, netTagAgreement.getCategory_id())
                //协议等级
                .eq(StringUtils.isNotEmpty(netTagAgreement.getNet_grade_id()), NetTagAgreement::getNet_grade_id, netTagAgreement.getNet_grade_id())
                //协议年份
                .eq(StringUtils.isNotEmpty(netTagAgreement.getYear()), NetTagAgreement::getYear, netTagAgreement.getYear())
                .eq(NetTagAgreement::getIs_del, "0")
                .orderByDesc(NetTagAgreement::getCreateTime)
        );

        for(NetTagAgreement agreement : pages.getRecords()){
            agreement.setNetTagFile(netTagFileMapper.selectById(agreement.getFile_id()));
            if( 2 == agreement.getStatus()){
                List<NetTagAgreementStatusMember> statusMembers = statusMemberMapper.selectList(Wrappers.<NetTagAgreementStatusMember>lambdaQuery().eq(NetTagAgreementStatusMember::getNet_tag_agreement_id, agreement.getId()));
                agreement.setOrg_ids(statusMembers.stream().map(NetTagAgreementStatusMember::getOrg_id).collect(Collectors.toList()));
            }
        }

        return pages;
    }

    public List<NetTagAgreement> getList(NetTagAgreement netTagAgreement) {

        List<NetTagAgreement> list = netTagAgreementMapper.selectList(Wrappers.<NetTagAgreement>lambdaQuery()
                //协议类型
                .eq(null != netTagAgreement.getCategory_id(), NetTagAgreement::getCategory_id, netTagAgreement.getCategory_id())
                //协议等级
                .eq(null != netTagAgreement.getNet_grade_id(), NetTagAgreement::getNet_grade_id, netTagAgreement.getNet_grade_id())
                //协议年份
                .eq(null != netTagAgreement.getYear(), NetTagAgreement::getYear, netTagAgreement.getYear())
                .eq(NetTagAgreement::getIs_del, "0")
        );

        for(NetTagAgreement agreement : list){
            agreement.setNetTagFile(netTagFileMapper.selectById(agreement.getFile_id()));
            if( 2 == agreement.getStatus()){
                List<NetTagAgreementStatusMember> statusMembers = statusMemberMapper.selectList(Wrappers.<NetTagAgreementStatusMember>lambdaQuery().eq(NetTagAgreementStatusMember::getNet_tag_agreement_id, agreement.getId()));
                agreement.setOrg_ids(statusMembers.stream().map(NetTagAgreementStatusMember::getOrg_id).collect(Collectors.toList()));
            }
        }
        return list;
    }

    /**
     * 根据协议编号查询协议信息
     * @param netTagAgreement
     * @return
     */
    public NetTagAgreement selectById(NetTagAgreement netTagAgreement) {
        NetTagAgreement agreement = netTagAgreementMapper.selectById(netTagAgreement.getId());
        agreement.setNetTagFile(netTagFileMapper.selectById(agreement.getFile_id()));
        return agreement;
    }


    /**
     * 新增协议信息
     */
    public ResultInfo insertNetTagAgreement(NetTagAgreement netTagAgreement)  {

        // 判断文件是否存在
        if (null == netTagAgreement.getFile_id() || netTagFileMapper.selectById(netTagAgreement.getFile_id()) == null) {
            return ResultInfo.error("文件不存在");
        }
        //判断等级+年份是否存在
        netTagAgreementMapper.selectList(Wrappers.<NetTagAgreement>lambdaQuery().eq(null != netTagAgreement.getNet_grade_id(), NetTagAgreement::getNet_grade_id, netTagAgreement.getNet_grade_id())
                .eq(NetTagAgreement::getYear, netTagAgreement.getYear())
                .eq(null != netTagAgreement.getCategory_id(), NetTagAgreement::getCategory_id, netTagAgreement.getCategory_id())
                .eq(NetTagAgreement::getIs_del, "0")).stream().findFirst().ifPresent(agreement -> {
            throw new RuntimeException("该年份,协议等级已存在模版!");
        });

        NetTagFile netTagFile = netTagFileMapper.selectById(netTagAgreement.getFile_id());

        Map<String, String> data = new HashMap<>();
        data.put("party_b_name", netTagAgreement.getParty_b_name());
        data.put("party_b_address", netTagAgreement.getParty_b_address());
        data.put("party_b_code", netTagAgreement.getParty_b_code());
        try {
            //判断路径是否存在
            if(!StringUtils.isEmpty(netTagFilePath)){
                File file = new File(netTagFilePath);
                if(!file.exists()){
                    file.mkdirs();
                }
            }
            //String destDocx = netTagFilePath + netTagFile.getOldFileName();
            String destDocx = netTagFilePath + netTagFile.getNewFileName();
            String pdfPath = netTagFilePath + IdUtil.simpleUUID()+".pdf";

            //从文件服务器拉取文件到本地临时用
            NetTagSuppService.download(fastDfs_url+netTagFile.getFilePath(),netTagFilePath+"model/"+ netTagFile.getNewFileName()) ;

            POIMergeDocUtil.word2RedDocument(netTagFilePath+"model/"+ netTagFile.getNewFileName(), data, destDocx, pdfPath);
            NetTagFile netTagFile1 = new NetTagFile();
            String wordId = UUID.randomUUID().toString().replaceAll("-", "");

            MultipartFile multipartFile = NetTagSuppService.convertToMultipartFile(destDocx);
            MultipartFile pdfFile = NetTagSuppService.convertToMultipartFile(pdfPath);

            //上传文件服务器
            FastDfsParams params = new FastDfsParams("netTagFile", multipartFile, "23", wordId);

            params.setFileName(multipartFile.getName());
            ResultInfo<FileInfo>  resultInfo = fastDfsUtil.uploadFile2(params);

            netTagFile1.setId(wordId);
            //截取文件名后缀改为.docx
            netTagFile1.setOldFileName(destDocx.substring(0, destDocx.lastIndexOf(".")) + ".docx");
            netTagFile1.setNewFileName(UUID.randomUUID().toString().replaceAll("-", "") + ".docx");
            netTagFile1.setFilePath(resultInfo.getData().getFileUrl());
            netTagFile1.setAssociationId("");
            netTagFile1.setCreateTime(new Date());
            netTagFile1.setUpdateTime(new Date());
            netTagFile1.setIs_del("0");
            netTagFileMapper.insert(netTagFile1);

            NetTagFile netTagFile2 = new NetTagFile();
            String pdfId = UUID.randomUUID().toString().replaceAll("-", "");

            //上传PDF
            FastDfsParams params2 = new FastDfsParams("netTagFile", pdfFile, "22", pdfId);
            params2.setFileName(pdfFile.getName());
            ResultInfo<FileInfo>  resultInfo2 = fastDfsUtil.uploadFile2(params2);

            netTagFile2.setId(pdfId);
            //截取文件名后缀改为.pdf
            netTagFile2.setOldFileName(destDocx.substring(0, destDocx.lastIndexOf(".")) + ".pdf");
            netTagFile2.setNewFileName(UUID.randomUUID().toString().replaceAll("-", "") + ".pdf");
            netTagFile2.setFilePath(resultInfo2.getData().getFileUrl());
            netTagFile2.setAssociationId("");
            netTagFile2.setCreateTime(new Date());
            netTagFile2.setUpdateTime(new Date());
            netTagFile2.setIs_del("0");
            netTagFileMapper.insert(netTagFile2);

            //保存协议信息wordFile, pdfFile
            netTagAgreement.setFile_word(wordId);
            netTagAgreement.setFile_pdf(pdfId);

            netTagAgreement.setIs_del("0");
            netTagAgreement.setCreateTime(new Date());
            //设置初始协议状态 1已下线
            netTagAgreement.setStatus(1);
            //协议年份处理 yyyy
//            netTagAgreement.setYear(netTagAgreement.getYear().substring(0, 4));

            netTagAgreement.setId(UUID.randomUUID().toString());
            netTagAgreementMapper.insert(netTagAgreement);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResultInfo.success("新增成功");
    }


    public ResultInfo updateNetTagAg(NetTagAgreement netTagAgreement){
        SysUser sysUser = sysUserService.getUser();
        // 判断文件是否存在
        if (null == netTagAgreement.getFile_id() || netTagFileMapper.selectById(netTagAgreement.getFile_id()) == null) {
            return ResultInfo.error("文件不存在");
        }
        NetTagAgreement tempNetTag = netTagAgreementMapper.selectById(netTagAgreement.getId());
        if (!tempNetTag.getFile_id().equals(netTagAgreement.getFile_id()) ){
            NetTagFile netTagFile = netTagFileMapper.selectById(netTagAgreement.getFile_id());

            Map<String, String> data = new HashMap<>();
            data.put("party_b_name", netTagAgreement.getParty_b_name() == null ? "" : netTagAgreement.getParty_b_name());
            data.put("party_b_address", netTagAgreement.getParty_b_address() == null ? "" : netTagAgreement.getParty_b_address());
            data.put("party_b_code", netTagAgreement.getParty_b_code() == null ? "" : netTagAgreement.getParty_b_code());
            try {
                //判断路径是否存在
                if(!StringUtils.isEmpty(netTagFilePath)){
                    File file = new File(netTagFilePath);
                    if(!file.exists()){
                        file.mkdirs();
                    }
                }
                //String destDocx = netTagFilePath + netTagFile.getOldFileName();
                String destDocx = netTagFilePath + netTagFile.getNewFileName();
                String pdfPath = netTagFilePath + IdUtil.simpleUUID()+".pdf";

                //从文件服务器拉取文件到本地临时用
                NetTagSuppService.download(fastDfs_url+netTagFile.getFilePath(),netTagFilePath+"model/"+ netTagFile.getNewFileName()) ;

                POIMergeDocUtil.word2RedDocument(netTagFilePath+"model/"+ netTagFile.getNewFileName(), data, destDocx, pdfPath);



                NetTagFile netTagFile1 = new NetTagFile();
                String wordId = UUID.randomUUID().toString().replaceAll("-", "");


                MultipartFile multipartFile = NetTagSuppService.convertToMultipartFile(destDocx);
                MultipartFile pdfFile = NetTagSuppService.convertToMultipartFile(pdfPath);

                //上传文件服务器
                FastDfsParams params = new FastDfsParams("netTagFile", multipartFile, "23", wordId);

                params.setFileName(multipartFile.getName());
                ResultInfo<FileInfo>  resultInfo = fastDfsUtil.uploadFile2(params);

                netTagFile1.setId(wordId);
                //截取文件名后缀改为.docx
                netTagFile1.setOldFileName(destDocx.substring(0, destDocx.lastIndexOf(".")) + ".docx");
                netTagFile1.setNewFileName(UUID.randomUUID().toString().replaceAll("-", "") + ".docx");
                netTagFile1.setFilePath(resultInfo.getData().getFileUrl());
                netTagFile1.setAssociationId("");
                netTagFile1.setCreateTime(new Date());
                netTagFile1.setUpdateTime(new Date());
                netTagFile1.setIs_del("0");
                netTagFileMapper.insert(netTagFile1);

                NetTagFile netTagFile2 = new NetTagFile();
                String pdfId = UUID.randomUUID().toString().replaceAll("-", "");
                netTagFile2.setId(pdfId);

                //上传PDF
                FastDfsParams params2 = new FastDfsParams("netTagFile", pdfFile, "22", pdfId);
                params2.setFileName(pdfFile.getName());
                ResultInfo<FileInfo>  resultInfo2 = fastDfsUtil.uploadFile2(params2);

                //截取文件名后缀改为.pdf
                netTagFile2.setOldFileName(destDocx.substring(0, destDocx.lastIndexOf(".")) + ".pdf");
                netTagFile2.setNewFileName(UUID.randomUUID().toString().replaceAll("-", "") + ".pdf");
                netTagFile2.setFilePath(resultInfo2.getData().getFileUrl());
                netTagFile2.setAssociationId("");
                netTagFile2.setCreateTime(new Date());
                netTagFile2.setUpdateTime(new Date());
                netTagFile2.setIs_del("0");
                netTagFileMapper.insert(netTagFile2);

                //保存协议信息wordFile, pdfFile
                netTagAgreement.setFile_word(wordId);
                netTagAgreement.setFile_pdf(pdfId);


                netTagAgreement.setUpdateTime(new Date());
//                //设置初始协议状态 1已下线
//                netTagAgreement.setStatus(1);
                //协议年份处理 yyyy
//                netTagAgreement.setYear(netTagAgreement.getYear().substring(0, 4));


                netTagAgreementMapper.updateById(netTagAgreement);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

        netTagAgreement.setUpdateTime(new Date());
        netTagAgreement.setUpdateUser(sysUser.getId());
        netTagAgreementMapper.updateById(netTagAgreement);

        return ResultInfo.success();
    }

    /**
     * 更新申请
     */
    public int updateNetTagAgreement(NetTagAgreement netTagAgreement) {
        netTagMechanismMapper.delete(Wrappers.<NetTagMechanism>lambdaQuery()
                .eq(NetTagMechanism::getAgreement_id, netTagAgreement.getId()));
        NetTagMechanism netTagMechanism = new NetTagMechanism();
        netTagMechanism.setAgreement_id(netTagAgreement.getId());
        netTagMechanism.setIs_del("0");
        netTagMechanism.setCreateTime(new Date());
        netTagMechanism.setCreateUser(netTagAgreement.getCreateUser());
        netTagMechanismMapper.insert(netTagMechanism);

        return netTagAgreementMapper.updateById(netTagAgreement);
    }

    /**
     * 删除申请
     */
    public int deleteNetTagAgreement(NetTagAgreement netTagAgreement) {
        netTagAgreementMapper.deleteById(netTagAgreement.getId());

        return netTagMechanismMapper.delete(Wrappers.<NetTagMechanism>lambdaQuery()
                .eq(NetTagMechanism::getAgreement_id, netTagAgreement.getId()));
    }

    /**
     * 根据当前登录人拿到甲方信息
     * @return
     */
    public Map<String, Object> selectNetTagAgreementByLogin() {
        Map<String, Object> map = new HashMap<>();
        SysUser user = sysUserService.getUser();
        map.put("user", user);
        String code = user.getOrg_code();
        //调用医疗编码数据
        map.put("medicalDept", medicalOrgService.selectByMedicalOrgId(code));
        return map;
    }


    /**
     * 根据ID查询
     */
    public NetTagAgreement getOneInfo(String id){
        NetTagAgreement netTagAgreement =  netTagAgreementMapper.selectById(id);
        if (StringUtils.isNotEmpty(netTagAgreement.getFile_pdf())){
            NetTagFile netTagFile = netTagFileMapper.selectById(netTagAgreement.getFile_pdf());
            String path = netTagFile.getFilePath();
            //String tempPath = path.substring(9);

            netTagAgreement.setPdf_path(fastDfs_downurl + path+"?download=0");
        }
        return netTagAgreement;
    }

    /**
     * 修改协议状态
     * @param netTagAgreement
     * @return
     */
    public ResultInfo updateNetTagAgreementStatus(NetTagAgreement netTagAgreement) {
        if (StringUtils.isBlank(netTagAgreement.getId()) || null == netTagAgreementMapper.selectById(netTagAgreement.getId())){
            return ResultInfo.error("协议不存在");
        }
        NetTagAgreement oldAgreement = netTagAgreementMapper.selectById(netTagAgreement.getId());
        if(netTagAgreement.getStatus() == 0){
//            List<NetTagAgreement> isList = netTagAgreementMapper.selectList(Wrappers.<NetTagAgreement>lambdaQuery()
//                    .eq(NetTagAgreement::getYear, oldAgreement.getYear())
//                    .eq(NetTagAgreement::getIs_del, "0")
//                    .eq(NetTagAgreement::getStatus, 0)
//                    .eq(NetTagAgreement::getNet_grade_id, oldAgreement.getNet_grade_id())
//            );
//            if (isList.size() > 0){
//                return ResultInfo.error("当前等级已有协议上线");
//            }
        } else if (netTagAgreement.getStatus() == 2){
            if (CollectionUtils.isEmpty(netTagAgreement.getOrg_ids())){
                return ResultInfo.error("部分下线机构不能为空");
            }
            statusMemberMapper.delete(Wrappers.<NetTagAgreementStatusMember>lambdaQuery()
                    .eq(NetTagAgreementStatusMember::getNet_tag_agreement_id, netTagAgreement.getId()));
            for (String orgId : netTagAgreement.getOrg_ids()) {
                // 得到机构信息
                FixmedinsB fixmedinsB = fixmedinsBMapper.selectOne(Wrappers.<FixmedinsB>lambdaQuery().eq(FixmedinsB::getIs_del, "0").eq(FixmedinsB::getFixmedins_code, orgId));
                // 保存机构和模版关联
                NetTagAgreementStatusMember netTagAgreementStatusMember = new NetTagAgreementStatusMember();
                netTagAgreementStatusMember.setId(IdUtil.simpleUUID());
                netTagAgreementStatusMember.setNet_tag_agreement_id(netTagAgreement.getId());
                netTagAgreementStatusMember.setOrg_id(orgId);
                netTagAgreementStatusMember.setOrg_name(fixmedinsB.getFixmedins_name());
                netTagAgreementStatusMember.setOrg_code(fixmedinsB.getFixmedins_code());
                netTagAgreementStatusMember.setIs_del("0");
                netTagAgreementStatusMember.setCreateUser(sysUserService.getUser().getId());
                netTagAgreementStatusMember.setCreateTime(new Date());
                statusMemberMapper.insert(netTagAgreementStatusMember);
            }
        }

        netTagAgreementMapper.updateById(netTagAgreement);
        return ResultInfo.success();
    }

    /**
     * 查询回流库机构
     * @return
     */
    public ResultInfo selectHlkList(NetTagAgreement netTagAgreement) {
        if(null == netTagAgreement.getCategory_id()){
            return ResultInfo.error("数据加载失败");
        }
        //机构类别type(1-医疗机构 2-零售药店)
//        if(1 == netTagAgreement.getCategory_id()){
//            return ResultInfo.success(medicalOrgService.selectByMedicalLevel(netTagAgreement));
//        }

        return ResultInfo.success(fixmedinsBMapper.selectList(Wrappers.<FixmedinsB>lambdaQuery().eq(FixmedinsB::getAggrement_lv, netTagAgreement.getNet_grade_id())));
    }
}
