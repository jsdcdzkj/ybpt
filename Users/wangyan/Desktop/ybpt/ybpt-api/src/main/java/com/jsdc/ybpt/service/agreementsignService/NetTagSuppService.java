package com.jsdc.ybpt.service.agreementsignService;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.agreementsignModel.*;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.FixmedinsBMapper;
import com.jsdc.ybpt.mapper.agreementsignMapper.NetTagFileMapper;
import com.jsdc.ybpt.mapper.agreementsignMapper.NetTagMechanismMapper;
import com.jsdc.ybpt.mapper.agreementsignMapper.NetTagSuppMapper;
import com.jsdc.ybpt.model.FileInfo;
import com.jsdc.ybpt.model.FixmedinsB;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.service.MedicalOrgService;
import com.jsdc.ybpt.service.SysUserService;
import com.jsdc.ybpt.util.FastDfs.FastDfsParams;
import com.jsdc.ybpt.util.FastDfs.FastDfsUtil;
import com.jsdc.ybpt.util.POIMergeDocUtil;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.vo.ResultInfo;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class NetTagSuppService extends BaseService<NetTagSupp> {

    public static final int cache = 10 * 1024;
    @Autowired
    private NetTagSuppMapper netTagSuppMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private NetTagMechanismMapper netTagMechanismMapper;
    @Autowired
    private NetTagFileMapper netTagFileMapper;
    @Autowired
    private NetTagRegisterService registerService;
    @Autowired
    private FddService fddService;
    @Autowired
    private SignService signService;

    @Autowired
    private NetTagMechanismService netTagMechanismService ;

    @Value("${netTagNginxPath}")
    private String nginxPath;


    @Value("${netTagFilePath}")
    private String netTagFilePath;


    @Value("${signPdfPath}")
    private String fastDfs_downurl;

    @Autowired
    private MedicalOrgService medicalOrgService;
    @Autowired
    private FixmedinsBMapper fixmedinsBMapper;
    @Autowired
    private NetTagLogService logService;

    @Autowired
    private FastDfsUtil fastDfsUtil;

    @Value("${fastDfs_downurl}")
    private String fastDfs_url;
    /**
     * 补充协议列表-补充协议列表 查询
     */
    public Page<NetTagMechanism> getAllNetTagMechanism(Integer pageIndex, Integer pageSize, NetTagMechanism netTagMechanism) {
        boolean flag = true;
        //获取当前登录用户
        SysUser sysUser = sysUserService.getUser();
        Page<NetTagMechanism> page;
        LambdaQueryWrapper<NetTagMechanism> lambda = new LambdaQueryWrapper<>();
        //获取当前登录用户的编码
        //user_type 账号类型 1:行政管理单位 2:医疗机构 3:零售药店 4：用人单位 5：体检机构 6银行
        if (sysUser.getUser_type().equals("2") || sysUser.getUser_type().equals("3")) {
            lambda.eq(NetTagMechanism::getMedical_code, sysUser.getOrg_code());
        }else{
            if(StringUtils.isEmpty(netTagMechanism.getAdmdvs())){
                lambda.eq(NetTagMechanism::getAdmdvs, sysUser.getOrg_code());
            }else{
                lambda.eq(NetTagMechanism::getAdmdvs, netTagMechanism.getAdmdvs());
            }
        }
        lambda.eq(NetTagMechanism::getType, "1");
        if (null != netTagMechanism) {
            //补充协议名称
            if (null != netTagMechanism.getStatus()) {
                switch (netTagMechanism.getStatus()){
                    case 0:
                        lambda.eq(NetTagMechanism::getStatus, 0);
                        lambda.eq(NetTagMechanism::getHas_sign, 2);
                        break;
                    case 1:
                        lambda.eq(NetTagMechanism::getStatus, 1);
                        break;
                    case 2:
                        lambda.eq(NetTagMechanism::getStatus, 2);
                        break;
                    case 3:
                        lambda.eq(NetTagMechanism::getStatus, 3);
                        break;
                    case 4:
                        lambda.eq(NetTagMechanism::getStatus, 4);
                        break;
                    case 5:
                        lambda.eq(NetTagMechanism::getStatus, 5);
                        break;

                }
            }else{
                lambda.ne(NetTagMechanism::getStatus, "0");
            }
            //创建时间
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (StringUtils.isNotEmpty(netTagMechanism.getStart_time())) {
                System.out.println(netTagMechanism.getStart_time());
                Date time = null;
                try {
                    time = simpleDateFormat.parse(netTagMechanism.getStart_time());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                lambda.ge(NetTagMechanism::getCreateTime, time);
            }
            if (StringUtils.isNotEmpty(netTagMechanism.getEnd_time())) {
                System.out.println(netTagMechanism.getEnd_time());
                Date time = null;
                try {
                    time = simpleDateFormat.parse(netTagMechanism.getEnd_time());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                lambda.le(NetTagMechanism::getCreateTime, time);
            }
            //年份
            if (StringUtils.isNotEmpty(netTagMechanism.getYear())) {
                lambda.eq(NetTagMechanism::getYear, netTagMechanism.getYear());
            }
            //国家编码
            if (StringUtils.isNotEmpty(netTagMechanism.getMechanism_code())){
                if (netTagMechanism.getMechanism_code().contains("%")){
                    lambda.like(NetTagMechanism::getMechanism_code, "/"+netTagMechanism.getMechanism_code()+"ESCAPE /");
                }else {
                    lambda.like(NetTagMechanism::getMechanism_code,netTagMechanism.getMechanism_code());
                }

            }
            if (StringUtils.isNotEmpty(netTagMechanism.getTitle())){
                List<String> ids = new ArrayList<>();
                if (!netTagMechanism.getTitle().contains("%")){
                    QueryWrapper<NetTagSupp> queryWrapper = new QueryWrapper<>();
                    queryWrapper.like("title",netTagMechanism.getTitle());
                    queryWrapper.eq("is_del","0");
                    List<NetTagSupp> netTagSupps = netTagSuppMapper.selectList(queryWrapper);


                    for (NetTagSupp temp : netTagSupps){
                        ids.add(temp.getId());
                    }
                }

                if (CollectionUtils.isEmpty(ids)){
                    flag = false;
                }else {
                    lambda.in(NetTagMechanism::getAgreement_id, ids);
                }
            }
        }
        lambda.eq(NetTagMechanism::getIs_del, "0");
        lambda.orderByDesc(NetTagMechanism::getCreateTime);

        if (!flag){
            lambda = new LambdaQueryWrapper<>();
            lambda.eq(NetTagMechanism::getIs_del, "-1");
        }

        page = netTagMechanismMapper.selectPage(new Page<>(pageIndex, pageSize), lambda);



        List<NetTagMechanism> list = page.getRecords();
        for (NetTagMechanism temp : list){

            if (StringUtils.isNotEmpty(temp.getAgreement_id())){
                NetTagSupp netTagSupp = netTagSuppMapper.selectById(temp.getAgreement_id());
                if (null != netTagSupp){
                    temp.setTitle(netTagSupp.getTitle());
                }
            }

            //回流库获取数据
            if ( StringUtils.isNotEmpty(temp.getMechanism_code())){
                //获取医疗机构信息
                QueryWrapper<FixmedinsB> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("is_del","0");
                queryWrapper.eq("fixmedins_code",temp.getMechanism_code());
                List<FixmedinsB> list1 = fixmedinsBMapper.selectList(queryWrapper);
                if (!CollectionUtils.isEmpty(list1)){
                    FixmedinsB fixmedinsB = list1.get(0);
                    //机构名称
                    temp.setFixmedins_name(fixmedinsB.getFixmedins_name());
                    //联系人
                    temp.setDept_resper_name(fixmedinsB.getLegrep_name());
                    //联系人电话
                    temp.setDept_resper_tel(fixmedinsB.getLegrep_mobile());
//                //法人
//                temp.setLegrep_name(fixmedinsB.getUscc());
                }
            }
            String operateUserId = temp.getOperate_user();
            if(StringUtils.isNotEmpty(operateUserId)){
                SysUser user = sysUserService.getById(operateUserId);
                temp.setOperate_user_name(user.getName());
            }
        }
        return page;

    }


//    /**
//     * 补充协议列表-补充协议发起
//     */
//    public ResultInfo initiate(NetTagSupp netTagSupp) {
//        //逻辑：发起补充协议时需要选择医药机构，可以重复选择补充协议，但是一个补充协议只能发送一个医药机构
//        if (!CollectionUtils.isEmpty(netTagSupp.getMedical())) {
//            //获取当前登录用户
//            SysUser sysUser = sysUserService.getUser();
//            for (int i = 0; i < netTagSupp.getMedical().size(); i++) {
//                String mechanism_code = netTagSupp.getMedical().get(i);
//                QueryWrapper<NetTagMechanism> queryWrapper = new QueryWrapper<>();
//                queryWrapper.eq("is_del", "0");
//                queryWrapper.eq("mechanism_code", mechanism_code);
//                queryWrapper.eq("type", "1");
//                Long count = netTagMechanismMapper.selectCount(queryWrapper);
//                //如果count>0证明，此补充协议以及绑定过此医药机构，不需要重复绑定 如果等于0 则需要添加数据
//                if (count == 0) {
//                    NetTagMechanism netTagMechanism = new NetTagMechanism();
//                    netTagMechanism.setMechanism_code(mechanism_code);
//                    netTagMechanism.setMedical_code(sysUser.getOrg_code());
////                    netTagMechanism.setAgreement_id(netTagSupp.getId());
//                    netTagMechanism.setType("1");
//                    netTagMechanism.setCreateTime(new Date());
//                    netTagMechanism.setCreateUser(sysUser.getId());
//                    netTagMechanism.setIs_del("0");
//                    netTagMechanism.setStatus(0);
//
//                    //判断是否是医药机构还是零售药店
//                    MedicalDept medicalDept = medicalOrgService.selectByMedicalOrgId(mechanism_code);
//                    String type = medicalDept.getCred_lv_type();
//                    netTagMechanism.setMedical_type(type);
//
//
//
//
//                    //获取文件Id
//                    NetTagSupp netTagSupp1 = netTagSuppMapper.selectById(netTagMechanism.getAgreement_id());
//                    netTagSupp1.setParty_b_name(medicalDept.getFixmedins_name());
//                    netTagSupp1.setParty_b_address(medicalDept.getAddr());
//                    netTagSupp1.setParty_b_code(medicalDept.getFixmedins_code());
//
//                    List<String> pdfId = wordToPdf(netTagSupp1.getFile_id(),netTagSupp1);
//                    netTagMechanism.setPdf_id(pdfId.get(1));
//                    netTagMechanismMapper.insert(netTagMechanism);
//                }
//            }
//
//        } else {
//            ResultInfo.error("请选择医药机构!");
//        }
//        return ResultInfo.success();
//    }

    /**
     * 补充协议列表-补充协议解约
     * 根据ID查询解约
     */
    public ResultInfo termination(Integer id) {
        SysUser sysUser = sysUserService.getUser();
        NetTagMechanism netTagMechanism = netTagMechanismMapper.selectById(id);
        //已确认状态的可以解约
        if (2 == netTagMechanism.getStatus()) {
            netTagMechanism.setStatus(0);
            netTagMechanism.setRescind(new Date());
            netTagMechanismMapper.updateById(netTagMechanism);
            NetTagLog log = new NetTagLog(sysUser.getId(), "补充协议解约", "解约成功");
            logService.save(log);
        } else {
            ResultInfo.error("只能对医药机构确认的补充协议操作!");
        }
        return ResultInfo.success();
    }


    /**
     * 补充协议管理-补充协议列表查询
     */
    public Page<NetTagSupp> getAllNetTagMechanism(Integer pageIndex, Integer pageSize, NetTagSupp netTagSupp) {
        //获取当前登录用户
        SysUser sysUser = sysUserService.getUser();
        Page<NetTagSupp> page;
        LambdaQueryWrapper<NetTagSupp> lambda = new LambdaQueryWrapper();
        //获取当前登录用户的编码
        lambda.eq(NetTagSupp::getMedical_code, sysUser.getOrg_code());
        lambda.eq(NetTagSupp::getIs_del, "0");
        lambda.orderByDesc(NetTagSupp::getCreateTime);
        page = netTagSuppMapper.selectPage(new Page<>(pageIndex, pageSize), lambda);
        List<NetTagSupp> list = page.getRecords();
        for (NetTagSupp temp : list) {
            temp.setMedicalName(sysUser.getOrg_name());
            String user = temp.getCreateUser();
            temp.setCreateName(sysUser.selectById(user).getName());
        }
        return page;
    }

    /**
     * 补充协议管理-补充协议列表查询
     */
    public List<NetTagSupp> getList(NetTagSupp netTagSupp) {
        LambdaQueryWrapper<NetTagSupp> lambda = new LambdaQueryWrapper();
        lambda.eq(NetTagSupp::getIs_del, "0");
        lambda.orderByDesc(NetTagSupp::getCreateTime);
        List<NetTagSupp> list = netTagSuppMapper.selectList(lambda);

        return list;
    }

    /**
     * 补充协议管理-补充协议查看
     */
    public NetTagSupp getOneNetTagSupp(String id) {
        NetTagSupp netTagSupp = netTagSuppMapper.selectById(id);
        if (StringUtils.isNotEmpty(netTagSupp.getFile_pdf())) {
            NetTagFile netTagFile = netTagFileMapper.selectById(netTagSupp.getFile_pdf());
            String path = netTagFile.getFilePath();
            //String tempPath = path.substring(9);

            netTagSupp.setPdf_path(fastDfs_downurl + path+"?download=0");
        }
        return netTagSupp;
    }


    /**
     * 补充协议管理-补充协议新增 修改
     */
    public ResultInfo editNetTagSupp(NetTagSupp netTagSupp) {
        SysUser sysUser = sysUserService.getUser();
        if (StringUtils.isEmpty(netTagSupp.getId())) {
            netTagSupp.setCreateTime(new Date());
            netTagSupp.setCreateUser(sysUser.getId());
            netTagSupp.setIs_del("0");
            netTagSupp.setMedical_code(sysUser.getOrg_code());
            netTagSupp.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            if (StringUtils.isNotEmpty(netTagSupp.getFile_id())) {
                netTagSupp.setParty_a_name(sysUser.getOrg_name());
                netTagSupp.setParty_a_address(netTagSupp.getAddress());
                List<String> list = wordToPdf(netTagSupp.getFile_id(), netTagSupp);
                netTagSupp.setFile_word(list.get(0));
                netTagSupp.setFile_pdf(list.get(1));
            }
            netTagSuppMapper.insert(netTagSupp);

        } else {
            netTagSupp.setUpdateTime(new Date());
            netTagSupp.setUpdateUser(sysUser.getId());
            NetTagSupp tempNet = netTagSuppMapper.selectById(netTagSupp.getId());
            if (!tempNet.getFile_id().equals(netTagSupp.getFile_id()) || !tempNet.getTitle().equals(netTagSupp.getTitle())) {
                netTagSupp.setParty_a_name(sysUser.getOrg_name());
                netTagSupp.setParty_a_address(netTagSupp.getAddress());
                List<String> list = wordToPdf(netTagSupp.getFile_id(), netTagSupp);
                netTagSupp.setFile_word(list.get(0));
                netTagSupp.setFile_pdf(list.get(1));
            }
            netTagSuppMapper.updateById(netTagSupp);

        }
        return ResultInfo.success();
    }


    public List<String> wordToPdf(String file_id, NetTagSupp netTagSupp) {

        List<String> list = new ArrayList<>();
        NetTagFile netTagFile = netTagFileMapper.selectById(file_id);


        Map<String, String> data = new HashMap<>();
//        data.put("title", netTagSupp.getTitle() == null ? "" : netTagSupp.getTitle());
//        data.put("party_a_name", netTagSupp.getParty_a_name() == null ? "" : netTagSupp.getParty_a_name());
//        data.put("party_a_address", netTagSupp.getParty_a_address() == null ? "" : netTagSupp.getParty_a_address());
        data.put("party_b_name", netTagSupp.getParty_b_name() == null ? "" : netTagSupp.getParty_b_name());
        data.put("party_b_address", netTagSupp.getParty_b_address() == null ? "" : netTagSupp.getParty_b_address());
        data.put("party_b_code", netTagSupp.getParty_b_code() == null ? "" : netTagSupp.getParty_b_code());

        try {
            //判断路径是否存在
            if (!StringUtils.isEmpty(netTagFilePath)) {
                File file = new File(netTagFilePath);
                if (!file.exists()) {
                    file.mkdirs();
                }
            }
//            String destDocx = netTagFilePath + netTagFile.getOldFileName();
            String destDocx = netTagFilePath + netTagFile.getNewFileName();
            String pdfPath = netTagFilePath + IdUtil.simpleUUID()+".pdf";
            //从文件服务器拉取文件到本地临时用
            download(fastDfs_url+netTagFile.getFilePath(),netTagFilePath+"model/"+ netTagFile.getNewFileName()) ;

            POIMergeDocUtil.word2RedDocument(netTagFilePath+"model/"+ netTagFile.getNewFileName(), data, destDocx, pdfPath);
            NetTagFile netTagFile1 = new NetTagFile();
            String wordId = UUID.randomUUID().toString().replaceAll("-", "");
            netTagFile1.setId(wordId);


            MultipartFile multipartFile = convertToMultipartFile(destDocx);
            MultipartFile pdfFile = convertToMultipartFile(pdfPath);

            //上传文件服务器
            FastDfsParams params = new FastDfsParams("netTagFile", multipartFile, "21", wordId);

            params.setFileName(multipartFile.getName());
            ResultInfo<FileInfo>  resultInfo = fastDfsUtil.uploadFile2(params);

            //截取文件名后缀改为.docx
            netTagFile1.setOldFileName(destDocx.substring(0, destDocx.lastIndexOf(".")) + ".docx");
            netTagFile1.setNewFileName(UUID.randomUUID().toString().replaceAll("-", "") + ".docx");
            netTagFile1.setFilePath(resultInfo.getData().getFileUrl());
            netTagFile1.setAssociationId("");
            netTagFile1.setCreateTime(new Date());
            netTagFile1.setUpdateTime(new Date());
            netTagFile1.setIs_del("0");
            netTagFileMapper.insert(netTagFile1);
            list.add(netTagFile1.getId());



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
            list.add(netTagFile2.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }


    /**
     * 机构端获取补充协议列表
     * @param pageIndex
     * @param pageSize
     * @param netTagMechanism
     * @return
     */
    public  Page<NetTagMechanism> mechanismReplenish(Integer pageIndex, Integer pageSize, NetTagMechanism netTagMechanism){
        boolean flag = true;
        //获取当前登录用户
        SysUser sysUser = sysUserService.getUser();
        Page<NetTagMechanism> page;
        LambdaQueryWrapper<NetTagMechanism> lambda = new LambdaQueryWrapper<>();
        //获取当前登录用户的编码
        lambda.eq(NetTagMechanism::getMechanism_code, sysUser.getOrg_code());
        lambda.eq(NetTagMechanism::getType, "1");
        if (null != netTagMechanism) {
            //补充协议状态
            if (null != netTagMechanism.getStatus()) {
                lambda.eq(NetTagMechanism::getStatus, netTagMechanism.getStatus());
            }
            //标题
            if (StringUtils.isNotEmpty(netTagMechanism.getTitle())){
                QueryWrapper<NetTagSupp> queryWrapper = new QueryWrapper<>();
                queryWrapper.like("title",netTagMechanism.getTitle());
                queryWrapper.eq("is_del","0");
                List<NetTagSupp> netTagSupps = netTagSuppMapper.selectList(queryWrapper);
                List<String> ids = new ArrayList<>();

                for (NetTagSupp temp : netTagSupps){
                    ids.add(temp.getId());
                }
                if (CollectionUtils.isEmpty(ids)){
                    flag = false;
                }else {
                    lambda.in(NetTagMechanism::getAgreement_id, ids);
                }


            }
        }
        lambda.eq(NetTagMechanism::getIs_del, "0");
        lambda.orderByDesc(NetTagMechanism::getCreateTime);

        if (!flag){
            lambda = new LambdaQueryWrapper<>();
            lambda.eq(NetTagMechanism::getIs_del, "-1");
        }

        page = netTagMechanismMapper.selectPage(new Page<>(pageIndex, pageSize), lambda);


        List<NetTagMechanism> list = page.getRecords();
        for (NetTagMechanism temp: list){
            temp.setFixmedins_name(sysUser.getOrg_name());
            String id = temp.getAgreement_id();

            NetTagSupp netTagSupp = netTagSuppMapper.selectById(id);
            if (null != netTagSupp){
                temp.setInvalid_date(netTagSupp.getInvalid_date());
                temp.setTitle(netTagSupp.getTitle());
            }
            String operateUserId = temp.getOperate_user();
            if(StringUtils.isNotEmpty(operateUserId)){
                SysUser user = sysUserService.getById(operateUserId);
                temp.setOperate_user_name(user.getName());
            }

        }


        return page;
    }

    /**
     * 医药机构端获取根据ID获取信息
     * @param id
     * @return
     */
    public NetTagMechanism getInfoById(String id){
        NetTagMechanism netTagMechanism = netTagMechanismMapper.selectById(id);
        if(StringUtils.isEmpty(netTagMechanism.getReview_url())){
            String fileId = netTagMechanism.getPdf_id();
            NetTagFile file = netTagFileMapper.selectById(fileId);
            String path = file.getFilePath();
            //String tempPath = file.getFilePath().substring(9);
            //tempPath = nginxPath+tempPath;
            netTagMechanism.setReview_url(fastDfs_downurl + path+"?download=0");
        }
        return netTagMechanism;
    }

    /**
     * 医药机构确认补充协议按钮
     * @param id
     * @return
     */
    public ResultInfo mechanismSure(String id){
        SysUser sysUser = sysUserService.getUser();
        NetTagMechanism netTagMechanism = netTagMechanismMapper.selectById(id);
        //todo 盖乙方的章 并替换合同查看地址以及合同下载地址

        NetTagRegister register = registerService.getOne(Wrappers.<NetTagRegister>lambdaQuery().eq(NetTagRegister::getUser_id, sysUser.getOrg_code()).eq(NetTagRegister::getIs_del, 0));

        if(null == register){
            return ResultInfo.error("请先实名认证");
        }
        NetTagFile file = netTagFileMapper.selectById(netTagMechanism.getPdf_id());
        if (null != file) {
            String contractId = signService.uploadContract(file.getFilePath(), "");
            if (StringUtils.isNotEmpty(contractId)) {
                netTagMechanism.setContract_id(contractId);
                netTagMechanism.setOperate_user(sysUser.getId());
                netTagMechanismMapper.updateById(netTagMechanism);
            }else{
                return ResultInfo.error("上传合同失败");
            }
        }
        JSONObject object = signService.authSign(register.getCompany_customer_id(), netTagMechanism.getContract_id(), "", "0", "乙      方", "2");
        String result = (String) object.get("result");
        if (result.equals("success")) {
            String url = signService.manualSign(register.getPersonal_customer_id(), netTagMechanism.getId(), netTagMechanism.getContract_id(), "", "2");
            NetTagLog log = new NetTagLog(sysUser.getId(), "补充协议确认", "确认成功");
            logService.save(log);
            return ResultInfo.success(url);
        }else {
            String msg = (String) object.get("msg");
            return ResultInfo.error(msg);
        }
    }




    public ResultInfo mechanismSure3(String id,String mechanism_code){
        //SysUser sysUser = sysUserService.getUser();

        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>() ;
        queryWrapper.eq("is_del","0") ;
        queryWrapper.eq("org_code",mechanism_code) ;
        SysUser sysUser   = sysUserService.getOne(queryWrapper) ;

        NetTagMechanism netTagMechanism = netTagMechanismMapper.selectById(id);
        //todo 盖乙方的章 并替换合同查看地址以及合同下载地址

        NetTagRegister register = registerService.getOne(Wrappers.<NetTagRegister>lambdaQuery().eq(NetTagRegister::getUser_id, sysUser.getOrg_code()).eq(NetTagRegister::getIs_del, 0));

        if(null == register){
            return ResultInfo.error("请先实名认证");
        }
        NetTagFile file = netTagFileMapper.selectById(netTagMechanism.getPdf_id());
        if (null != file) {
            String contractId = signService.uploadContract(file.getFilePath(), "");
            if (StringUtils.isNotEmpty(contractId)) {
                netTagMechanism.setContract_id(contractId);
                netTagMechanism.setOperate_user(sysUser.getId());
                netTagMechanismMapper.updateById(netTagMechanism);
            }else{
                return ResultInfo.error("上传合同失败");
            }
        }
        JSONObject object = signService.authSign(register.getCompany_customer_id(), netTagMechanism.getContract_id(), "", "0", "乙      方", "2");
        String result = (String) object.get("result");
        if (result.equals("success")) {
            String url = signService.manualSign(register.getPersonal_customer_id(), netTagMechanism.getId(), netTagMechanism.getContract_id(), "", "2");
            NetTagLog log = new NetTagLog(sysUser.getId(), "补充协议确认", "确认成功");
            logService.save(log);
            return ResultInfo.success(url);
        }else {
            String msg = (String) object.get("msg");
            return ResultInfo.error(msg);
        }
    }



    /**
     * validateApply 网签申请 验证是否可以申请
     */
    public ResultInfo validateApply() {
        SysUser sysUser = sysUserService.getUser();

        // 1.判断今年是否已经申请过
        List<NetTagMechanism> list = netTagMechanismMapper.selectList(Wrappers.<NetTagMechanism>lambdaQuery()
                .eq(NetTagMechanism::getYear, DateUtil.thisYear() + "")
                .eq(NetTagMechanism::getMechanism_code, sysUser.getOrg_code())
                .eq(NetTagMechanism::getType, "0")
                .eq(NetTagMechanism::getIs_del, "0"));
        for (NetTagMechanism netTagMechanism : list){
            if(netTagMechanism.getStatus() != 2 && netTagMechanism.getStatus() != 4){
                return ResultInfo.error("您今年已经申请过网签服务，不能重复申请");
            }
        }
        return ResultInfo.success();
    }


    /**
     * 根据url下载文件，保存到filepath中
     *
     * @param url 文件的url
     * @param diskUrl 本地存储路径
     * @return
     */
    public static void download(String url, String diskUrl) {

        try {
            HttpClient client = HttpClients.createDefault();
            HttpGet httpget = new HttpGet(url);
            // 加入Referer,防止防盗链
            httpget.setHeader("Referer", url);
            HttpResponse response = client.execute(httpget);
            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();

            File file = new File(diskUrl);
            file.getParentFile().mkdirs();
            FileOutputStream fileout = new FileOutputStream(file);
            byte[] buffer = new byte[cache];
            int ch = 0;
            while ((ch = is.read(buffer)) != -1) {
                fileout.write(buffer, 0, ch);
            }
            is.close();
            fileout.flush();
            fileout.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static MultipartFile convertToMultipartFile(String filePath) throws IOException {
        // 创建一个File对象，将文件路径作为参数传入
        File file = new File(filePath);

        // 创建一个文件输入流，将File对象作为参数传入
        FileInputStream inputStream = new FileInputStream(file);

        // 创建一个MockMultipartFile对象，将文件名、文件字节流、文件类型作为参数传入
        MultipartFile multipartFile = new MockMultipartFile(file.getName(), inputStream);

        return multipartFile;
    }
}
