package com.jsdc.ybpt.service.appropNotice;

import cn.hutool.core.annotation.Alias;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.poi.excel.sax.Excel03SaxReader;
import cn.hutool.poi.excel.sax.Excel07SaxReader;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsdc.ybpt.appropNotice.dto.AppropDocumentPageDTO;
import com.jsdc.ybpt.appropNotice.dto.AppropDocumentValidUploadDTO;
import com.jsdc.ybpt.appropNotice.entity.AppropDocument;
import com.jsdc.ybpt.appropNotice.entity.AppropNotice;
import com.jsdc.ybpt.appropNotice.enums.DocumentTypeEnum;
import com.jsdc.ybpt.mapper.appropNotice.AppropDocumentMapper;
import com.jsdc.ybpt.mapper.appropNotice.AppropNoticeMapper;
import com.jsdc.ybpt.model.FileInfo;
import com.jsdc.ybpt.model.SysRole;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model.SysUserRole;
import com.jsdc.ybpt.model_check.AdministrativeUnit;
import com.jsdc.ybpt.service.*;
import com.jsdc.ybpt.util.FastDfs.FastDfsParams;
import com.jsdc.ybpt.util.FastDfs.FastDfsUtil;
import com.jsdc.ybpt.util.FileUtils;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.util.exception.CustomException;
import com.jsdc.ybpt.vo.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AppropDocumentService extends ServiceImpl<AppropDocumentMapper, AppropDocument> {
    @Resource
    private AppropDocumentMapper appropDocumentMapper;
    @Resource
    private AppropNoticeMapper appropNoticeMapper;
    @Resource
    private AdministrativeUnitService administrativeUnitService;
    @Resource
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Resource
    private FileInfoService fileInfoService;

    @Resource
    private FastDfsUtil fastDfsUtil;

    @Value("${fastDfs_downurl}")
    private String dow_path;

    public Page<AppropDocument> getPage(AppropDocumentPageDTO appropDocumentPageDTO) {
        SysUser sysUser = sysUserService.getUser();
        AdministrativeUnit administrativeUnit = administrativeUnitService.getOne(Wrappers.<AdministrativeUnit>lambdaQuery()
                .eq(AdministrativeUnit::getAdmdvs, sysUser.getOrg_code())
                .eq(AdministrativeUnit::getIs_del, "0")
        );
        if(StringUtils.isNull(administrativeUnit)){
            throw new CustomException("机构信息不存在",-1);
        }
//        if(StringUtils.isEmpty(sysUser.getDept_code()))
//            return new Page<>();
        appropDocumentPageDTO.setPageNo(appropDocumentPageDTO.getPageNo()!=null?appropDocumentPageDTO.getPageNo():1);
        appropDocumentPageDTO.setPageSize(appropDocumentPageDTO.getPageSize()!=null?appropDocumentPageDTO.getPageSize():10);
        Page<AppropDocument> page = new Page<>(appropDocumentPageDTO.getPageNo(), appropDocumentPageDTO.getPageSize());

//        QueryWrapper<AppropDocument> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("is_del", "0");
//        queryWrapper.eq("tcq", administrativeUnit.getAdmdvs());
//        if (StringUtils.isNotNull(appropDocumentPageDTO.getYear())) {
//            queryWrapper.eq("year", appropDocumentPageDTO.getYear());
//        }
//        if (StringUtils.isNotNull(appropDocumentPageDTO.getMonth())) {
//            queryWrapper.eq("month", appropDocumentPageDTO.getMonth());
//        }
//        if(StringUtils.isEmpty(sysUser.getDept_code())){
//            queryWrapper.eq("create_user", sysUser.getId());
//        }else{
//            queryWrapper.eq("dept_code", sysUser.getDept_code());
//        }
//        if (StringUtils.isNotNull(appropDocumentPageDTO.getDocName())) {
//            queryWrapper.like("doc_name", appropDocumentPageDTO.getDocName());
//        }
//        if (StringUtils.isNotNull(appropDocumentPageDTO.getDocType())) {
//            queryWrapper.eq("doc_type", appropDocumentPageDTO.getDocType());
//        }
//        if (StringUtils.isNotNull(appropDocumentPageDTO.getBeginCreateTime())) {
//            queryWrapper.ge("to_char(create_time,'YYYY-MM-DD')", appropDocumentPageDTO.getBeginCreateTime());
//        }
//        if (StringUtils.isNotNull(appropDocumentPageDTO.getEndCreateTime())) {
//            queryWrapper.le("to_char(create_time,'YYYY-MM-DD')", appropDocumentPageDTO.getEndCreateTime());
//        }
//        if (StringUtils.isNotBlank(appropDocumentPageDTO.getStatus())) {
//            queryWrapper.eq("status", appropDocumentPageDTO.getStatus());
//        }
//        queryWrapper.orderByDesc("year");
//        queryWrapper.orderByDesc("month");
//        Page<AppropDocument> pageResult  = appropDocumentMapper.selectPage(page, queryWrapper);
//        pageResult.getRecords().stream().forEach(d->{
//            DocumentTypeEnum doc = DocumentTypeEnum.getEnumByCode(d.getDocType());
//            if(doc!=null){
//                d.setDocType(doc.getInfo());
//            }
//        });

        appropDocumentPageDTO.setTcq(administrativeUnit.getAdmdvs());
        if(!StringUtils.isEmpty(sysUser.getDept_code())){
            appropDocumentPageDTO.setDeptCode(sysUser.getDept_code());
        }
        appropDocumentPageDTO.setCreateUser(sysUser.getId());
        IPage<AppropDocument> docPage = appropDocumentMapper.pageQuery(page,appropDocumentPageDTO);
        Page<AppropDocument> pageResult = new Page<>();
        pageResult.setCurrent(page.getCurrent());
        pageResult.setSize(page.getSize());
        pageResult.setRecords(docPage.getRecords());
        pageResult.setTotal(docPage.getTotal());
        pageResult.getRecords().stream().forEach(d->{
            DocumentTypeEnum doc = DocumentTypeEnum.getEnumByCode(d.getDocType());
            if(doc!=null){
                d.setDocType(doc.getInfo());
            }
        });
        return pageResult;
    }

    //获取当前账号的文档权限
    public List<JSONObject> document(){
        List<JSONObject> list = new ArrayList<>();
        SysUser sysUser = sysUserService.getUser();
        log.info("document sysUser:{}", JSON.toJSONString(sysUser));
        List<SysUserRole> sysUserRoles = sysUserRoleService.list(new QueryWrapper<SysUserRole>().eq("user_id",sysUser.getId()));
        log.info("document sysUserRoles:{}", JSON.toJSONString(sysUserRoles));
        List<String> roleIds = Optional.ofNullable(sysUserRoles).orElse(Lists.newArrayList()).stream().map(s->s.getRole_id()).collect(Collectors.toList());
        log.info("document roleIds:{}", JSON.toJSONString(roleIds));
        if(CollectionUtils.isEmpty(roleIds))
            return list;
        List<SysRole> roles = sysRoleService.queryByRoleIds(roleIds);
        if(CollectionUtils.isEmpty(roles))
            return Lists.newArrayList();
        log.info("document roles:{}", JSON.toJSONString(roleIds));
        DocumentTypeEnum[] values = DocumentTypeEnum.values();
        for (DocumentTypeEnum doc : values) {
            List<SysRole> docRole = roles.stream().filter(r->doc.getCode().equals(r.getRole_symbol())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(docRole)){
                JSONObject obj = new JSONObject();
                obj.put("code",doc.getCode());
                obj.put("info",doc.getInfo());
                list.add(obj);
            }
        }
//        roles.forEach(r->{
//            DocumentTypeEnum doc = DocumentTypeEnum.getEnumByCode(r.getRole_symbol());
//            if(doc!=null){
//                JSONObject obj = new JSONObject();
//                obj.put("code",doc.getCode());
//                obj.put("info",doc.getInfo());
//                list.add(obj);
//            }
//        });
        return list;
    }

    public boolean validate(AppropDocumentValidUploadDTO appropDocumentValidUploadDTO){
        //权限校验
        List<JSONObject> docs = Optional.ofNullable(document()).orElse(Lists.newArrayList()).stream().filter(d->
                appropDocumentValidUploadDTO.getDocType().equals(d.getString("code"))).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(docs)){
            throw new CustomException("当前账号无上传权限!",-1);
        }
        //重复上传校验
//        AppropDocumentPageDTO appropDocumentPageDTO = new AppropDocumentPageDTO();
//        appropDocumentPageDTO.setYear(appropDocumentValidUploadDTO.getYear());
//        appropDocumentPageDTO.setMonth(appropDocumentValidUploadDTO.getMonth());
//        appropDocumentPageDTO.setDocType(appropDocumentValidUploadDTO.getDocType());
        SysUser sysUser = sysUserService.getUser();
        AdministrativeUnit administrativeUnit = administrativeUnitService.getOne(Wrappers.<AdministrativeUnit>lambdaQuery()
                .eq(AdministrativeUnit::getAdmdvs, sysUser.getOrg_code())
                .eq(AdministrativeUnit::getIs_del, "0")
        );
        QueryWrapper<AppropDocument> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("year", appropDocumentValidUploadDTO.getYear());
        queryWrapper.eq("month", appropDocumentValidUploadDTO.getMonth());
        queryWrapper.eq("doc_type", appropDocumentValidUploadDTO.getDocType());
        queryWrapper.eq("tcq",administrativeUnit.getAdmdvs());
        queryWrapper.eq("is_del", "0");
        List<AppropDocument> list = appropDocumentMapper.selectList(queryWrapper);
        if(!CollectionUtils.isEmpty(list))
            throw new CustomException("当前年月对应拨付文档已上传,不可再上传！",-1);
        return true;
    }

    public boolean save(MultipartFile file,AppropDocumentValidUploadDTO appropDocumentValidUploadDTO){
        validate(appropDocumentValidUploadDTO);
        SysUser sysUser = sysUserService.getUser();
        AdministrativeUnit administrativeUnit = administrativeUnitService.getOne(Wrappers.<AdministrativeUnit>lambdaQuery()
                .eq(AdministrativeUnit::getAdmdvs, sysUser.getOrg_code())
                .eq(AdministrativeUnit::getIs_del, "0")
        );
        if(StringUtils.isNull(administrativeUnit)){
            throw new CustomException("机构信息不存在",-1);
        }
        String fileName = file.getOriginalFilename();
        DocumentTypeEnum doc = DocumentTypeEnum.getEnumByCode(appropDocumentValidUploadDTO.getDocType());
        if(doc == null){
            throw new CustomException("文档类型错误",-1);
        }
//        if(!fileName.contains(doc.getInfo())){
//            throw new CustomException("上传文档与文档类型不匹配",-1);
//        }
        //表格格式校验
        List<List<String>> list = readerFile(file,doc.getHeadStartRowIndex(),doc.getHeadEndRowIndex());
        if(CollectionUtils.isEmpty(list))
            throw new CustomException("文件表头读取失败",-1);
        List<String> header = new ArrayList<String>(){
            {
            list.forEach(l->{
                addAll(l);
            });
            }
        };
        List<String> originalTableHeader = doc.getTableHeader();
        List<String> tableHeader = doc.getTableHeader().stream().filter(t->header.contains(t)).collect(Collectors.toList());
        if(tableHeader.size()!=originalTableHeader.size()||tableHeader.size()!=header.size())
            throw new CustomException("上传文档格式与模板文档格式不一致",-1);

        boolean result = false;
        AppropDocument appropDocument = new AppropDocument();
        appropDocument.setId(IdUtil.simpleUUID());
        appropDocument.setYear(appropDocumentValidUploadDTO.getYear());
        appropDocument.setMonth(appropDocumentValidUploadDTO.getMonth());
        appropDocument.setStatus("0");
        appropDocument.setDocType(appropDocumentValidUploadDTO.getDocType());
        appropDocument.setDocName(fileName);
        appropDocument.setTcq(administrativeUnit.getAdmdvs());
        if(StringUtils.isNotEmpty(sysUser.getDept_code())){
            appropDocument.setDeptCode(sysUser.getDept_code());
        }
        appropDocument.setCreateTime(new Date());
        appropDocument.setCreateUser(sysUser.getId());

        //todo 保存附件里的记录 步骤提前
        //1、读取附件内容 2、生成记录 3、保存

        //遵循原来的逻辑，但是这种操作逻辑很奇怪
        if(doc!=null){
            FastDfsParams params = null;
            params = new FastDfsParams(doc.getFilePath(), file, doc.getCode(), appropDocument.getId());
            params.setFileName(fileName);
            ResultInfo upload = fastDfsUtil.uploadFile(params);
            if(upload.getCode()!=-1)
                result = true;
        }
        if(result){
            result = appropDocumentMapper.insert(appropDocument)>0?true:false;
        }
        return result;
    }

    /**
     * @param file
     * @return java.util.List<T>
     * @description //TODO  读取文件
     * @author wangxiao
     * @date 2024/5/21
     */
    private List<List<String>> readerFile(MultipartFile file, int startRowIndex, int endRowIndex) {
        InputStream in = null;
        List<List<String>> rows = new ArrayList<>();
        try {
            in = file.getInputStream();
            String filename = file.getOriginalFilename();
            String fileType = filename.substring(filename.lastIndexOf(".")+1);
            List<List<Object>> rowsTemp = new ArrayList<>();
            //构建对象读取
            if(fileType.equals("xls")){
                Excel03SaxReader reader = new Excel03SaxReader(createRowHandler(rowsTemp,startRowIndex,endRowIndex));
                IOUtils.setByteArrayMaxOverride(400000000);
                reader.read(in, -1);
            }else{
                Excel07SaxReader reader = new Excel07SaxReader(createRowHandler(rowsTemp,startRowIndex,endRowIndex));
                IOUtils.setByteArrayMaxOverride(400000000);
                reader.read(in, -1);
            }
            for (List<Object> objects : rowsTemp) {
                List<String> list = objects.stream().filter(o->o!=null).map(o->o.toString()).collect(Collectors.toList());
                rows.add(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("读取文件异常,请检查文件！",-1);
        } finally {
            if(StringUtils.isNotNull(in)){
                IoUtil.close(in);
            }
        }
        return rows;
    }

    private RowHandler createRowHandler(List<List<Object>> rows, int startRowIndex,int endRowIndex) {
        return new RowHandler() {
            @Override
            public void handle(int sheetIndex, long rowIndex, List<Object> rowlist) {
                if(rowIndex >= startRowIndex && rowIndex<= endRowIndex && StringUtils.isNotEmpty(rowlist)){
                    rows.add(rowlist);
                }
            }
        };
    }

    public boolean publish(String id){
        SysUser sysUser = sysUserService.getUser();
        AppropDocument appropDocument = new AppropDocument();
        appropDocument.setStatus("1");
        appropDocument.setPubTime(new Date());
        appropDocument.setUpdateTime(appropDocument.getPubTime());
        appropDocument.setUpdateUser(sysUser.getId());

        LambdaUpdateWrapper<AppropDocument> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(AppropDocument::getStatus, "0");
        wrapper.eq(AppropDocument::getId, id);

        return appropDocumentMapper.update(appropDocument,wrapper)>0?true:false;
    }

    public boolean off(String id){
        AppropDocument doc = appropDocumentMapper.selectById(id);
        //校验当前文件是否已生成拨付数据
        QueryWrapper<AppropNotice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("year", doc.getYear());
        queryWrapper.eq("month", doc.getMonth());
        queryWrapper.eq("tcq", doc.getTcq());
        queryWrapper.eq("is_del", "0");
        List<AppropNotice> notices = appropNoticeMapper.selectList(queryWrapper);
        if(!CollectionUtils.isEmpty(notices)){
            throw new CustomException("当前文件已生成拨付数据，不能取消发布",-1);
        }
        SysUser sysUser = sysUserService.getUser();
        AppropDocument appropDocument = new AppropDocument();
        appropDocument.setStatus("0");
        appropDocument.setUpdateTime(new Date());
        appropDocument.setUpdateUser(sysUser.getId());

        LambdaUpdateWrapper<AppropDocument> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(AppropDocument::getStatus, "1");
        wrapper.eq(AppropDocument::getId, id);

        return appropDocumentMapper.update(appropDocument,wrapper)>0?true:false;
    }

    public boolean delete(String id){
        SysUser sysUser = sysUserService.getUser();
        AppropDocument appropDocument = new AppropDocument();
        appropDocument.setIsDel("1");
        appropDocument.setUpdateTime(new Date());
        appropDocument.setUpdateUser(sysUser.getId());

        LambdaUpdateWrapper<AppropDocument> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(AppropDocument::getStatus, "0");
        wrapper.eq(AppropDocument::getId, id);
        return appropDocumentMapper.update(appropDocument,wrapper)>0?true:false;
    }

    public List<AppropDocument> docList(AppropDocumentValidUploadDTO appropDocumentValidUploadDTO) {
        SysUser sysUser = sysUserService.getUser();
        AdministrativeUnit administrativeUnit = administrativeUnitService.getOne(Wrappers.<AdministrativeUnit>lambdaQuery()
                .eq(AdministrativeUnit::getAdmdvs, sysUser.getOrg_code())
                .eq(AdministrativeUnit::getIs_del, "0")
        );
        if(StringUtils.isNull(administrativeUnit)){
            throw new CustomException("机构信息不存在",-1);
        }
        QueryWrapper<AppropDocument> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        queryWrapper.eq("tcq", administrativeUnit.getAdmdvs());
        queryWrapper.eq("year", appropDocumentValidUploadDTO.getYear());
        queryWrapper.eq("month", appropDocumentValidUploadDTO.getMonth());
        queryWrapper.eq("doc_type", appropDocumentValidUploadDTO.getDocType());
        queryWrapper.eq("status","1");
        List<AppropDocument> list = appropDocumentMapper.selectList(queryWrapper);
        Optional.ofNullable(list).orElse(Lists.newArrayList()).stream().forEach(d->{
            DocumentTypeEnum doc = DocumentTypeEnum.getEnumByCode(d.getDocType());
            if(doc!=null){
                d.setDocType(doc.getInfo());
            }
        });
        return list;
    }

    public InputStream downLoad(String id){
        List<FileInfo> files = fileInfoService.getFileInfoByBizId(id,null);
        FileInfo file = Optional.ofNullable(files).orElse(null).get(0);
        InputStream fs = FileUtils.downLoadFile(dow_path,file);
        return fs;
    }
}
