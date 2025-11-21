package com.jsdc.ybpt.service.agreementsignService;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.agreementsignModel.NetTagRegister;
import com.jsdc.ybpt.agreementsignModel.NetTagRegisterLog;
import com.jsdc.ybpt.mapper.FixmedinsBMapper;
import com.jsdc.ybpt.model.FixmedinsB;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.service.FixmedinsBService;
import com.jsdc.ybpt.service.SysUserService;
import com.jsdc.ybpt.util.FileUtils;
import com.jsdc.ybpt.vo.ResultInfo;
import com.jsdc.ybpt.vo.agreementsignVo.FixmedinsBVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.nio.ch.Net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * @projectName: ybpt
 * @className: NetTagSealService
 * @author: wp
 * @description:
 * @date: 2022/9/8 11:09
 */
@Service
public class NetTagSealService {
    @Autowired
    private SysUserService userService;
    @Autowired
    private FddService fddService;
    @Autowired
    private NetTagRegisterService registerService;
    @Autowired
    private SignService signService;
    @Autowired
    private FixmedinsBMapper fixmedinsBMapper;
    @Value("${uploadtjreportPath}")
    private String uploadtjreportPath;

    private static final Logger log = LoggerFactory.getLogger(NetTagSealService.class);

    /**
     * 用户注册 返回认证接口
     * @param type
     * @return
     */
    public ResultInfo authenticationbak(String type, MultipartFile file) throws Exception {
        SysUser sysUser = userService.getUser();
        String orgCode = sysUser.getOrg_code();
        JSONObject regeditResult = signService.regedit(orgCode, type);
        if("1".equals(regeditResult.getString("code"))){
            String customerId = regeditResult.getString("data");

            JSONObject result = new JSONObject();
            //企业
            if("2".equals(type)){
                result = signService.authenticationCompany(customerId);

            }else{
                //个人
                result = signService.authenticationPersonal(customerId);
            }
            if("1".equals(result.getString("code"))){
                NetTagRegister register = registerService.getOne(Wrappers.<NetTagRegister>lambdaQuery().eq(NetTagRegister::getUser_id, orgCode).eq(NetTagRegister::getIs_del, "0"));
                if(null == register){
                    NetTagRegister re = new NetTagRegister();
                    String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                    re.setId(uuid);
                    re.setUser_type(type);
                    re.setUser_id(orgCode);
                    re.setIs_regist("0");
                    re.setIs_upload_seal("0");
                    re.setCreateTime(new Date());
                    re.setUpdateTime(new Date());
                    re.setIs_del("0");
                    re.setCustomerId(customerId);
                    if("2".equals(type)){
                        String fileName = saveFile(file);
                        re.setFile_path(fileName);
                    }
                    re.setTransaction_no(JSON.parseObject(result.getString("data")).getString("transactionNo"));
                    registerService.save(re);
                }
                String base64Str = JSON.parseObject(result.getString("data")).getString("url");
                String url = new String(Base64.getDecoder().decode(base64Str));
                return ResultInfo.success(url);
            }
        }else{
            return ResultInfo.error("注册用户失败");
        }
        return ResultInfo.error("认证失败");
    }

    /**
     * 企业用户注册、认证
     * @return
     * @throws Exception
     */
    public ResultInfo companyAuthentication() throws Exception {
        SysUser sysUser = userService.getUser();
        String orgCode = sysUser.getOrg_code();
        NetTagRegister r = registerService.getOne(Wrappers.<NetTagRegister>lambdaQuery().eq(NetTagRegister::getUser_id, orgCode).eq(NetTagRegister::getIs_del, "0"));
        String registerResult = "";
        if(null == r){
            //用户注册
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            JSONObject regeditResult = signService.regedit(orgCode+uuid, "2");
            if("1".equals(regeditResult.getString("code"))){
                String customerId = regeditResult.getString("data");

                JSONObject result = new JSONObject();
                //企业认证
                result = signService.authenticationCompany(customerId);

                if("1".equals(result.getString("code"))){
                    NetTagRegister re = new NetTagRegister();

                    re.setId(uuid);
                    re.setUser_id(orgCode);
                    re.setIs_regist("0");
                    re.setIs_upload_seal("0");
                    re.setCreateTime(new Date());
                    re.setUpdateTime(new Date());
                    re.setIs_del("0");
                    re.setRegister_status("0");
                    re.setCustomerId(customerId);
                    re.setCompany_customer_id(customerId);
                    re.setCompany_transaction_no(JSON.parseObject(result.getString("data")).getString("transactionNo"));
                    //re.setCompany_seal_content(register.getCompany_seal_content());

                    String base64Str = JSON.parseObject(result.getString("data")).getString("url");
                    String url = new String(Base64.getDecoder().decode(base64Str));
                    re.setCompany_authentication_url(url);
                    registerService.save(re);
                    return ResultInfo.success(url);
                }
            }else{
                return ResultInfo.error("注册用户失败");
            }
        }else{
            if(StringUtils.isNotEmpty(r.getCompany_authentication_url()) && StringUtils.equals(r.getRegister_status(), "0")){
                return ResultInfo.success(r.getCompany_authentication_url());
            }else{
                String companyCustomerId = r.getCompany_customer_id();
                //企业认证
                JSONObject result = signService.authenticationCompany(companyCustomerId);
                if("1".equals(result.getString("code"))){
                    r.setCompany_transaction_no(JSON.parseObject(result.getString("data")).getString("transactionNo"));
                    //r.setCompany_seal_content(register.getCompany_seal_content());

                    String base64Str = JSON.parseObject(result.getString("data")).getString("url");
                    String url = new String(Base64.getDecoder().decode(base64Str));
                    r.setCompany_authentication_url(url);
                    registerService.updateById(r);
                    return ResultInfo.success(url);
                }
            }

        }
        return ResultInfo.error("认证失败");
    }

    /**
     * 个人用户注册、认证
     * @return
     */
    public ResultInfo personalAuthentication(){
        SysUser user = userService.getUser();
        String orgCode = user.getOrg_code();
        Optional<NetTagRegister> o = Optional.ofNullable(registerService.getOne(Wrappers.<NetTagRegister>lambdaQuery().eq(NetTagRegister::getUser_id, orgCode).eq(NetTagRegister::getIs_del, "0")));
        if(o.isPresent()){
            NetTagRegister register = o.get();
            //注册 账号为对应的部门编+uuid
            String uuid = IdUtil.simpleUUID();
            JSONObject regeditResult = signService.regedit(orgCode+uuid, "1");
            if("1".equals(regeditResult.getString("code"))) {
                String customerId = regeditResult.getString("data");

                //认证
                JSONObject result = signService.authenticationPersonal(customerId);
                if("1".equals(result.getString("code"))){
                    register.setPersonal_customer_id(customerId);
                    register.setP_transaction_no(JSON.parseObject(result.getString("data")).getString("transactionNo"));
                    register.setUpdateTime(new Date());
                    registerService.updateById(register);
                    String base64Str = JSON.parseObject(result.getString("data")).getString("url");
                    String url = new String(Base64.getDecoder().decode(base64Str));
                    return ResultInfo.success(url);
                }
            }
        }
        return ResultInfo.error("个人认证失败");
    }

    /**
     * 变更
     * @return
     */
    public ResultInfo changeCompanyInfo(){
        SysUser user = userService.getUser();
        String orgCode = user.getOrg_code();
        Optional<NetTagRegister> o = Optional.ofNullable(registerService.getOne(Wrappers.<NetTagRegister>lambdaQuery().eq(NetTagRegister::getUser_id, orgCode).eq(NetTagRegister::getIs_del, "0")));
        if(o.isPresent()){
            NetTagRegister register = o.get();
            String companyCustomerId = register.getCompany_customer_id();
            String personalCustomerId = register.getPersonal_customer_id();
            NetTagRegister re = new NetTagRegister();
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            re.setId(uuid);
            re.setUser_id(orgCode);
            re.setIs_regist("0");
            re.setIs_upload_seal("0");
            re.setCreateTime(new Date());
            re.setUpdateTime(new Date());
            re.setIs_del("0");
            re.setRegister_status("0");
            re.setCompany_customer_id(companyCustomerId);

            register.setIs_del("1");
            registerService.updateById(register);
//            registerService.save(re);
            return ResultInfo.success();

        }

        return ResultInfo.error("变更失败");
    }


    /**
     * 授权自动签章
     * @return
     */
    public ResultInfo authAutoSign(){
        SysUser user = userService.getUser();
        String orgCode = user.getOrg_code();
        Optional<NetTagRegister> o = Optional.ofNullable(registerService.getOne(Wrappers.<NetTagRegister>lambdaQuery().eq(NetTagRegister::getUser_id, orgCode).eq(NetTagRegister::getIs_del, "0")));
        if(o.isPresent()){
            NetTagRegister register = o.get();
            String transactionId = register.getId();
            String result = signService.autoSignAuthorize(register.getCompany_customer_id(), transactionId);
            return ResultInfo.success(result);
        }
        return ResultInfo.error("当前用户尚未注册");
    }

    private String saveFile(MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename(); //获取文件名
        int index = fileName.lastIndexOf(".");
        String fileSuffix = fileName.substring(index);
        String file_name = UUID.randomUUID().toString().replaceAll("-", "") + fileSuffix;
        uploadFile(file.getBytes(), uploadtjreportPath, file_name);
        return file_name;
    }

    public void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
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
     * 认证完成回调
     * @param json
     * @return
     */
    public String authenticationNotify(String json){
        try {
            log.error("认证完成回调json:"+json) ;
        }catch (Exception e) {
            e.printStackTrace();
        }

        String[] params = json.split("&");
        JSONObject jsonObject = new JSONObject();
        for(String p : params){
            if(StringUtils.isNoneBlank(p) && p.contains("=")){
                String[] s = p.split("=");

                jsonObject.put(s[0], s.length > 1 ? s[1]:"");
            }
        }
        String customerId = jsonObject.getString("customerId");
        LambdaQueryWrapper<NetTagRegister> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(NetTagRegister::getIs_del, "0");
        wrapper.and(w ->
                    w.eq(NetTagRegister::getCompany_customer_id, customerId)
                            .or()
                            .eq(NetTagRegister::getPersonal_customer_id, customerId)
                );

        NetTagRegister netTagRegister = registerService.getOne(wrapper);

        try {
            NetTagRegisterLog netTagRegisterLog = new NetTagRegisterLog() ;
            netTagRegisterLog.setId(IdUtil.simpleUUID());
            if(StringUtils.isNotBlank(netTagRegister.getUser_type())){
                netTagRegisterLog.setUser_type(netTagRegister.getUser_type());
            }

            if(StringUtils.isNotBlank(netTagRegister.getCompany_transaction_no())){
                netTagRegisterLog.setCompany_transaction_no(netTagRegister.getCompany_transaction_no());
            }
            if(StringUtils.isNotBlank(netTagRegister.getPersonal_transaction_no())){
                netTagRegisterLog.setPersonal_transaction_no(netTagRegister.getPersonal_transaction_no());
            }
            if(StringUtils.isNotBlank(json)){
                netTagRegisterLog.setJsonString(json);
            }
            netTagRegisterLog.insert() ;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if("2".equals(jsonObject.getString("authenticationType"))){
            if("4".equals(jsonObject.getString("status"))){
                log.error("customerId:"+customerId) ;
                log.error("company_transaction_no:"+netTagRegister.getCompany_transaction_no()) ;
                JSONObject res = signService.applyCertificate(customerId, netTagRegister.getCompany_transaction_no());
                log.error("证书申请返回:"+res.toJSONString()) ;
                netTagRegister.setRegister_result("applyCertificate: "+res.toString());
                if("1".equals(res.getString("code"))){
                    JSONObject result = signService.companyCertInfo(netTagRegister.getCompany_transaction_no());
                    log.error("查询企业实名认证信息:"+result.toJSONString()) ;
                    if(StringUtils.equals(result.getString("code"), "1")){
                        JSONObject company = result.getJSONObject("data").getJSONObject("company");
                        String base64 = signService.customSeal(customerId, company.getString("companyName"));
                        JSONObject uploadResult = signService.uploadSeal(customerId, base64);
                        log.error("上传印章:"+uploadResult.toJSONString()) ;
                        if(StringUtils.equals(uploadResult.getString("code"), "1")){
                            String signatureId = uploadResult.getJSONObject("data").getString("signature_id");
                            netTagRegister.setCompany_seal_id(signatureId);
                        }
                        netTagRegister.setRegister_status("1");
                        netTagRegister.setUpdateTime(new Date());
                        registerService.updateById(netTagRegister);
                    }
                }else {
                    log.error("证书申请接口返回状态有误") ;
                }
            }
        }else if("1".equals(jsonObject.getString("authenticationType"))){
            if("2".equals(jsonObject.getString("status"))){
                log.error("customerId:"+customerId) ;
                log.error("company_transaction_no:"+netTagRegister.getP_transaction_no()) ;
                JSONObject res = signService.applyCertificate(customerId, netTagRegister.getP_transaction_no());
                log.error("证书申请返回:"+res.toJSONString()) ;
                netTagRegister.setRegister_result("applyCertificate: "+res.toString());
                if("1".equals(res.getString("code"))){
                    netTagRegister.setRegister_status("3");
                    netTagRegister.setUpdateTime(new Date());
                    registerService.updateById(netTagRegister);
                }else {
                    log.error("证书申请接口返回状态有误") ;

                }
            }else{
                netTagRegister.setPersonal_customer_id(null);
                netTagRegister.setUpdateTime(new Date());
                registerService.updateById(netTagRegister);
            }
        }

        return "200";
    }

    /**
    *法大大回调失败,企业认证
    * Author wzn
    * Date 2023/11/6 15:00
    */
    public void manualOperationCom(String customerId){
        LambdaQueryWrapper<NetTagRegister> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(NetTagRegister::getIs_del, "0");
        wrapper.and(w ->
                w.eq(NetTagRegister::getCompany_customer_id, customerId)
                        .or()
                        .eq(NetTagRegister::getPersonal_customer_id, customerId)
        );
        NetTagRegister netTagRegister = registerService.getOne(wrapper);
        JSONObject res = signService.applyCertificate(customerId, netTagRegister.getCompany_transaction_no());
        log.error("证书申请返回:"+res.toJSONString()) ;
        netTagRegister.setRegister_result("applyCertificate: "+res.toString());
        if("1".equals(res.getString("code"))){
            JSONObject result = signService.companyCertInfo(netTagRegister.getCompany_transaction_no());
            log.error("查询企业实名认证信息:"+result.toJSONString()) ;
            if(StringUtils.equals(result.getString("code"), "1")){
                JSONObject company = result.getJSONObject("data").getJSONObject("company");
                String base64 = signService.customSeal(customerId, company.getString("companyName"));
                JSONObject uploadResult = signService.uploadSeal(customerId, base64);
                log.error("上传印章:"+uploadResult.toJSONString()) ;
                if(StringUtils.equals(uploadResult.getString("code"), "1")){
                    String signatureId = uploadResult.getJSONObject("data").getString("signature_id");
                    netTagRegister.setCompany_seal_id(signatureId);
                }
                netTagRegister.setRegister_status("1");
                netTagRegister.setUpdateTime(new Date());
                registerService.updateById(netTagRegister);
            }
        }else {
            log.error("证书申请接口返回状态有误") ;
        }
    }



    /**
     *法大大回调失败,个人认证
     * Author wzn
     * Date 2023/11/6 15:00
     */
    public void manualOperationPer(String customerId){
        LambdaQueryWrapper<NetTagRegister> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(NetTagRegister::getIs_del, "0");
        wrapper.and(w ->
                w.eq(NetTagRegister::getCompany_customer_id, customerId)
                        .or()
                        .eq(NetTagRegister::getPersonal_customer_id, customerId)
        );
        NetTagRegister netTagRegister = registerService.getOne(wrapper);

        JSONObject res = signService.applyCertificate(customerId, netTagRegister.getP_transaction_no());
        log.error("证书申请返回:"+res.toJSONString()) ;
        netTagRegister.setRegister_result("applyCertificate: "+res.toString());
        if("1".equals(res.getString("code"))){
            netTagRegister.setRegister_status("3");
            netTagRegister.setUpdateTime(new Date());
            registerService.updateById(netTagRegister);
        }else {
            log.error("证书申请接口返回状态有误") ;

        }
    }

    /**
     * 查询当前用户是否认证完成
     * @return
     */
    public ResultInfo checkAuthentication(){
        SysUser sysUser = userService.getUser();
        String orgCode = sysUser.getOrg_code();
        NetTagRegister register = registerService.getOne(Wrappers.<NetTagRegister>lambdaQuery().eq(NetTagRegister::getUser_id, orgCode).eq(NetTagRegister::getIs_del, "0"));
        if(null == register){
            return ResultInfo.success(0);
        }
        return ResultInfo.success(Integer.parseInt(register.getRegister_status()));
    }

    public ResultInfo getOrgPage(FixmedinsBVo vo){
        Page page = new Page(vo.getPageIndex(), vo.getPageSize());
        LambdaQueryWrapper<FixmedinsB> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(vo.getFixmedins_code()), FixmedinsB::getFixmedins_code, vo.getFixmedins_code());
        wrapper.like(StringUtils.isNotEmpty(vo.getFixmedins_name()), FixmedinsB::getFixmedins_name, vo.getFixmedins_name());
        wrapper.eq(FixmedinsB::getIs_del, "0");
        return ResultInfo.success(fixmedinsBMapper.selectPage(page, wrapper));
    }

    public ResultInfo getRegisterInfo(){
        SysUser sysUser = userService.getUser();
        String orgCode = sysUser.getOrg_code();
        NetTagRegister register = registerService.getOne(Wrappers.<NetTagRegister>lambdaQuery().eq(NetTagRegister::getUser_id, orgCode).eq(NetTagRegister::getIs_del, "0"));
        if(null == register){
            return ResultInfo.error("注册用户不存在");
        }
        return ResultInfo.success(register);
    }
}
