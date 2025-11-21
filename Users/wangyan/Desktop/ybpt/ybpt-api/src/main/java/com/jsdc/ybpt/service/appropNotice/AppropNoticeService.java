package com.jsdc.ybpt.service.appropNotice;

import cn.hutool.core.annotation.Alias;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.sax.Excel03SaxReader;
import cn.hutool.poi.excel.sax.Excel07SaxReader;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsdc.ybpt.appropNotice.dto.*;
import com.jsdc.ybpt.appropNotice.entity.*;
import com.jsdc.ybpt.appropNotice.enums.DocumentTypeEnum;
import com.jsdc.ybpt.appropNotice.enums.InsutypeEnum;
import com.jsdc.ybpt.appropNotice.enums.TcqEnum;
import com.jsdc.ybpt.appropNotice.vo.*;
import com.jsdc.ybpt.mapper.appropNotice.AppropNoticeMapper;
import com.jsdc.ybpt.model.*;
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
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 拨付通知(APPROP_NOTICE)业务实现类
 *
 * @author wangxiao
 * @date 2024-05-17 16:36:33
 */
@Service
@Slf4j
public class AppropNoticeService extends ServiceImpl<AppropNoticeMapper, AppropNotice> {
    @Resource
    private AppropNoticeMapper appropNoticeMapper;

    @Resource
    private SysUserService sysUserService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Resource
    private AppropNoticeOccurService appropNoticeOccurService;

    @Resource
    private AppropNoticeSettleService appropNoticeSettleService;

    @Resource
    private AppropNoticeJmdbbxsjzfService appropNoticeJmdbbxsjzfService;

    @Resource
    private AppropNoticeMonthSettleService appropNoticeMonthSettleService;

    @Resource
    private AppropNoticeDrgService appropNoticeDrgService;

    @Resource
    private AppropNoticeSummaryService appropNoticeSummaryService;

    @Resource
    private AppropNoticeDataDetailService appropNoticeDataDetailService;

    @Resource
    private SysDictService sysDictService;

    @Resource
    private FastDfsUtil fastDfsUtil;

    @Resource
    private FixmedinsBService fixmedinsBService;

    @Resource
    private AdministrativeUnitService administrativeUnitService;

    @Resource
    private AppropNoticeSummaryAnalyseService appropNoticeSummaryAnalyseService;

    @Resource
    private AppropNoticeDetailAnalyseService appropNoticeDetailAnalyseService;

    @Resource
    private AppropNoticeSendService appropNoticeSendService;

    @Resource
    private FileInfoService fileInfoService;

    @Resource
    private SysRoleService sysRoleService;

    private final static String AUTH_GENERATE = "generateNotice";

    @Value("${fastDfs_downurl}")
    private String dow_path;

    private final static String sumStr = "合计";


    /**
     * @param appropNoticePageDTO
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.jsdc.ybpt.appropNotice.entity.AppropNotice>
     * @description //TODO  分页查询
     * @author wangxiao
     * @date 2024/5/21
     */
    public Page<AppropNotice> getPage(AppropNoticePageDTO appropNoticePageDTO) {
        SysUser sysUser = sysUserService.getUser();
        String tcq = "";
        if("1".equals(sysUser.getUser_type())){
            //市直单位
            AdministrativeUnit administrativeUnit = administrativeUnitService.getOne(Wrappers.<AdministrativeUnit>lambdaQuery()
                    .eq(AdministrativeUnit::getAdmdvs, sysUser.getOrg_code())
                    .eq(AdministrativeUnit::getIs_del, "0")
            );
            if(StringUtils.isNull(administrativeUnit)){
                throw new CustomException("机构信息不存在",-1);
            }
            tcq = administrativeUnit.getAdmdvs();
        }else{
            //定点医疗机构
            FixmedinsB fixmedinsB = fixmedinsBService.getOne(Wrappers.<FixmedinsB>lambdaQuery()
                    .eq(FixmedinsB::getFixmedins_code, sysUser.getOrg_code())
                    .eq(FixmedinsB::getIs_del, "0")
            );
            if(StringUtils.isNull(fixmedinsB)){
                throw new CustomException("机构信息不存在",-1);
            }
            tcq = fixmedinsB.getFix_blng_admdvs();
        }
        Page<AppropNotice> page = new Page<>(appropNoticePageDTO.getPageNo(), appropNoticePageDTO.getPageSize());
        QueryWrapper<AppropNotice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        queryWrapper.eq("tcq", tcq);
        if (StringUtils.isNotNull(appropNoticePageDTO.getYear())) {
            queryWrapper.eq("year", appropNoticePageDTO.getYear());
        }
        if (StringUtils.isNotNull(appropNoticePageDTO.getMonth())) {
            queryWrapper.eq("month", appropNoticePageDTO.getMonth());
        }
        if (StringUtils.isNotNull(appropNoticePageDTO.getBeginCreateTime())) {
            queryWrapper.ge("to_char(create_time,'YYYY-MM-DD')", appropNoticePageDTO.getBeginCreateTime());
        }
        if (StringUtils.isNotNull(appropNoticePageDTO.getEndCreateTime())) {
            queryWrapper.le("to_char(create_time,'YYYY-MM-DD')", appropNoticePageDTO.getEndCreateTime());
        }
        if (StringUtils.isNotNull(appropNoticePageDTO.getBeginSendTime())) {
            queryWrapper.ge("to_char(send_time,'YYYY-MM-DD')", appropNoticePageDTO.getBeginSendTime());
        }
        if (StringUtils.isNotNull(appropNoticePageDTO.getEndSendTime())) {
            queryWrapper.le("to_char(send_time,'YYYY-MM-DD')", appropNoticePageDTO.getEndSendTime());
        }
        if (StringUtils.isNotBlank(appropNoticePageDTO.getStatus())) {
            queryWrapper.eq("status", appropNoticePageDTO.getStatus());
        }
        queryWrapper.orderByDesc("year");
        queryWrapper.orderByDesc("month");
        Page<AppropNotice> appropNoticePage  = appropNoticeMapper.selectPage(page, queryWrapper);
        return appropNoticePage;
    }

    public IPage<AppropNotice> getPageByOrgCode(AppropNoticePageDTO appropNoticePageDTO) {
        SysUser sysUser = sysUserService.getUser();
        //定点医疗机构
        FixmedinsB fixmedinsB = fixmedinsBService.getOne(Wrappers.<FixmedinsB>lambdaQuery()
                .eq(FixmedinsB::getFixmedins_code, sysUser.getOrg_code())
                .eq(FixmedinsB::getIs_del, "0")
        );
        if(StringUtils.isNull(fixmedinsB)){
            throw new CustomException("机构信息不存在",-1);
        }
        String orgCode = fixmedinsB.getFixmedins_code();
        appropNoticePageDTO.setOrgCode(orgCode);
        Page<AppropNotice> page = new Page<>(appropNoticePageDTO.getPageNo(), appropNoticePageDTO.getPageSize());
        IPage<AppropNotice> appropNoticePage = appropNoticeMapper.getPageByOrgCode(page, appropNoticePageDTO);
        return appropNoticePage;
    }

    /**
     * @param appropNoticeValidUploadDTO
     * @return boolean
     * @description //TODO  上传校验
     * @author wangxiao
     * @date 2024/5/21
     */
    public ResultInfo validUpload(AppropNoticeValidUploadDTO appropNoticeValidUploadDTO) {
        SysUser sysUser = sysUserService.getUser();
        String tcq = "";
        if("1".equals(sysUser.getUser_type())){
            //市直单位
            AdministrativeUnit administrativeUnit = administrativeUnitService.getOne(Wrappers.<AdministrativeUnit>lambdaQuery()
                    .eq(AdministrativeUnit::getAdmdvs, sysUser.getOrg_code())
                    .eq(AdministrativeUnit::getIs_del, "0")
            );
            if(StringUtils.isNull(administrativeUnit)){
                throw new CustomException("机构信息不存在",-1);
            }
            tcq = administrativeUnit.getAdmdvs();
        }else{
            //定点医疗机构
            FixmedinsB fixmedinsB = fixmedinsBService.getOne(Wrappers.<FixmedinsB>lambdaQuery()
                    .eq(FixmedinsB::getFixmedins_code, sysUser.getOrg_code())
                    .eq(FixmedinsB::getIs_del, "0")
            );
            if(StringUtils.isNull(fixmedinsB)){
                throw new CustomException("机构信息不存在",-1);
            }
            tcq = fixmedinsB.getFix_blng_admdvs();
        }
//        QueryWrapper<AppropNotice> queryWrapper1 = new QueryWrapper<>();
//        queryWrapper1.eq("year", appropNoticeValidUploadDTO.getYear());
//        queryWrapper1.eq("tcq", tcq);
//        queryWrapper1.eq("is_del", "0");
//        List<AppropNotice> appropNotices = appropNoticeMapper.selectList(queryWrapper1);
//        if(CollectionUtils.isEmpty(appropNotices)){
//            return ResultInfo.success();
//        }

        //不能上传过去年的
        QueryWrapper<AppropNotice> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.gt("year", appropNoticeValidUploadDTO.getYear());
        queryWrapper1.eq("tcq", tcq);
        queryWrapper1.eq("is_del", "0");
        List<AppropNotice> appropNotices = appropNoticeMapper.selectList(queryWrapper1);
        if(!CollectionUtils.isEmpty(appropNotices)){
            return ResultInfo.error("不可以上传过去年月的文件");
        }
        QueryWrapper<AppropNotice> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("year", appropNoticeValidUploadDTO.getYear());
        queryWrapper2.eq("month", appropNoticeValidUploadDTO.getMonth());
        queryWrapper2.eq("tcq", tcq);
        queryWrapper2.eq("is_del", "0");
        AppropNotice appropNotice = appropNoticeMapper.selectOne(queryWrapper2);
        if (StringUtils.isNotNull(appropNotice)) {
            return ResultInfo.error("当前年月拨付数据已生成，不可再生成！");
        }
        Integer month = appropNoticeValidUploadDTO.getMonth();
        List<Integer> beforeMonths = new ArrayList<>();
        if(month > 1){
            for (int i = 1; i < month; i++) {
                beforeMonths.add(i);
            }
            QueryWrapper<AppropNotice> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("year", appropNoticeValidUploadDTO.getYear());
            queryWrapper3.in("month", beforeMonths);
            queryWrapper3.eq("tcq", tcq);
            queryWrapper3.eq("is_del", "0");
            queryWrapper3.eq("status", "1");
            List<AppropNotice> appropNotices1 = appropNoticeMapper.selectList(queryWrapper3);
            if(StringUtils.isEmpty(appropNotices1) || appropNotices1.size() != beforeMonths.size()){
                return ResultInfo.error("当前年月之前的月份拨付数据未发送,不可添加生成该月份拨付数据");
            }
        }
        return ResultInfo.success();
    }

    public ResultInfo uploadTemplate(MultipartFile file) {
        FastDfsParams params = null;
        params = new FastDfsParams("approp_notice/template", file, "33", null);
        params.setFileName(file.getOriginalFilename());
        return fastDfsUtil.uploadFile(params);
    }

    public ResultInfo getTempAddress() {
        FileInfo fileInfo = fileInfoService.getOne(new QueryWrapper<FileInfo>().eq("biztype", "33"));
        if(StringUtils.isNull(fileInfo)){
            throw new CustomException("模板文件不存在",-1);
        }
        return ResultInfo.success(fileInfo.getFileUrl());
    }

    /**
     * @param appropNoticePreviewDTO
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @description //TODO  上传数据预览
     * @author wangxiao
     * @date 2024/5/23
     */
    @Transactional
    public AppropNoticePreviewVO preview(MultipartFile occurFile,
                                         MultipartFile settleFile,
                                         MultipartFile jmdbbxsjzfFile,
                                         MultipartFile monthSettleFile,
                                         MultipartFile drgFile,
                                         AppropNoticePreviewDTO appropNoticePreviewDTO) {
        AppropNoticePreviewVO bean = BeanUtil.toBean(appropNoticePreviewDTO, AppropNoticePreviewVO.class);
        SysUser sysUser = sysUserService.getUser();
        Map<String, Object> map = readFileData(occurFile,settleFile,jmdbbxsjzfFile,monthSettleFile,drgFile,appropNoticePreviewDTO, sysUser);
        List<AppropNoticeSummary> appropNoticeSummaries = new ArrayList<>();
        List<AppropNoticeSummary> zgSummaries = dealZgSummary(map, appropNoticePreviewDTO, sysUser);
        if (StringUtils.isNotEmpty(zgSummaries)) {
            List<AppropNoticeSummary> originalZgSummaries = BeanUtil.copyToList(zgSummaries, AppropNoticeSummary.class);
            bean.setZgSummaries(originalZgSummaries);
            try {
                for (AppropNoticeSummary zgSummary : zgSummaries) {
                    Field[] fields = zgSummary.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        Class<?> type = field.getType();
                        if(type == BigDecimal.class){
                            Object val = field.get(zgSummary);
                            if(StringUtils.isNotNull(val)){
                                field.set(zgSummary, NumberUtil.div(val.toString(), "10000").setScale(6,RoundingMode.HALF_UP));
                            }
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new CustomException("数据转换成万元异常："+e.getMessage(),-1);
            }
            appropNoticeSummaries.addAll(zgSummaries);
            bean.setConvertZgSummaries(zgSummaries);
        }
        List<AppropNoticeSummary> jmSummaries = dealJmSummary(map, appropNoticePreviewDTO, sysUser);
        if (StringUtils.isNotEmpty(jmSummaries)) {
            List<AppropNoticeSummary> originalJmSummaries = BeanUtil.copyToList(jmSummaries, AppropNoticeSummary.class);
            bean.setJmSummaries(originalJmSummaries);
            try {
                for (AppropNoticeSummary jmSummary : jmSummaries) {
                    Field[] fields = jmSummary.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        Class<?> type = field.getType();
                        if(type == BigDecimal.class){
                            Object val = field.get(jmSummary);
                            if(StringUtils.isNotNull(val)){
                                field.set(jmSummary, NumberUtil.div(val.toString(), "10000").setScale(6,RoundingMode.HALF_UP));
                            }
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new CustomException("数据转换成万元异常："+e.getMessage(),-1);
            }
            appropNoticeSummaries.addAll(jmSummaries);
            bean.setConvertJmSummaries(jmSummaries);
        }
        if (StringUtils.isNotEmpty(map)) {
            List<AppropNoticeOccur> occurData = (List<AppropNoticeOccur>) map.get("occurData");
            if (StringUtils.isNotNull(occurData)) {
                bean.setOccurData(occurData);
            }
            List<AppropNoticeSettle> settleData = (List<AppropNoticeSettle>) map.get("settleData");
            if (StringUtils.isNotNull(settleData)) {
                bean.setSettleData(settleData);
            }
            List<AppropNoticeJmdbbxsjzf> jmdbbxsjzfData = (List<AppropNoticeJmdbbxsjzf>) map.get("jmdbbxsjzfData");
            if (StringUtils.isNotNull(jmdbbxsjzfData)) {
                bean.setJmdbbxsjzfData(jmdbbxsjzfData);
            }
            List<AppropNoticeMonthSettle> monthSettleData = (List<AppropNoticeMonthSettle>) map.get("monthSettleData");
            if (StringUtils.isNotNull(monthSettleData)) {
                bean.setMonthSettleData(monthSettleData);
            }
            List<AppropNoticeDrg> drgData = (List<AppropNoticeDrg>) map.get("drgData");
            if (StringUtils.isNotNull(drgData)) {
                bean.setDrgData(drgData);
            }
        }
        List<AppropNoticeDataDetail> appropNoticeDataDetails = new ArrayList<>();
        List<AppropNoticeDataDetail> zgDataDetails = dealZgDataDetail(map, appropNoticePreviewDTO, sysUser,null,null);
        if (StringUtils.isNotEmpty(zgDataDetails)) {
            appropNoticeDataDetails.addAll(zgDataDetails);
        }
        List<AppropNoticeDataDetail> jmDataDetails = dealJmDataDetail(map, appropNoticePreviewDTO, sysUser,null,null);
        if (StringUtils.isNotEmpty(jmDataDetails)) {
            if (StringUtils.isEmpty(appropNoticeDataDetails)) {
                appropNoticeDataDetails.addAll(jmDataDetails);
            } else {
                try {
                    for (AppropNoticeDataDetail detail : appropNoticeDataDetails) {
                        List<AppropNoticeDataDetail> filterJmDataDetail = jmDataDetails.stream().filter(x -> x.getOrgCode().equals(detail.getOrgCode())).collect(Collectors.toList());
                        if (StringUtils.isNotEmpty(filterJmDataDetail)) {
                            AppropNoticeDataDetail jmDataDetail = filterJmDataDetail.get(0);
                            Field[] declaredFields = detail.getClass().getDeclaredFields();
                            for (Field declaredField : declaredFields) {
                                declaredField.setAccessible(true);
                                Field field = jmDataDetail.getClass().getDeclaredField(declaredField.getName());
                                field.setAccessible(true);
                                Object val = field.get(jmDataDetail);
                                if(StringUtils.isNotNull(val)){
                                    declaredField.set(detail,field.get(jmDataDetail));
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new CustomException("居民数据明细赋值异常："+e.getMessage(),-1);
                }
            }
        }
        if (StringUtils.isNotEmpty(appropNoticeDataDetails)) {
            List<AppropNoticeDataDetail> originalDataDetails = BeanUtil.copyToList(appropNoticeDataDetails, AppropNoticeDataDetail.class);
            bean.setDataDetail(originalDataDetails);
            try {
                for (AppropNoticeDataDetail record : appropNoticeDataDetails) {
                    Field[] fields = record.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        Class<?> type = field.getType();
                        if(type == BigDecimal.class){
                            Object val = field.get(record);
                            if(StringUtils.isNotNull(val)){
                                field.set(record, NumberUtil.div(val.toString(), "10000").setScale(6,RoundingMode.HALF_UP));
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new CustomException("数据转换成万元异常："+e.getMessage(),-1);
            }
            bean.setConvertDataDetail(appropNoticeDataDetails);
        }
        return bean;
    }

    /**
     * @param appropNoticePreviewDTO
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @description //TODO  上传数据预览
     * @author wangxiao
     * @date 2024/5/23
     */
    @Transactional
    public AppropNoticePreviewVO previewNew(AppropNoticePreviewDTO appropNoticePreviewDTO){
        AppropNoticePreviewVO bean = BeanUtil.toBean(appropNoticePreviewDTO, AppropNoticePreviewVO.class);
        SysUser sysUser = sysUserService.getUser();
        Map<String, Object> map = readFileData(appropNoticePreviewDTO.getOccurFileId(),appropNoticePreviewDTO.getSettleFileId(),
                appropNoticePreviewDTO.getJmdbbxsjzfFileId(),appropNoticePreviewDTO.getMonthSettleFileId(),appropNoticePreviewDTO.getDrgFileId(),appropNoticePreviewDTO, sysUser);
        List<AppropNoticeSummary> appropNoticeSummaries = new ArrayList<>();
        List<AppropNoticeSummary> zgSummaries = dealZgSummary(map, appropNoticePreviewDTO, sysUser);
        if (StringUtils.isNotEmpty(zgSummaries)) {
            List<AppropNoticeSummary> originalZgSummaries = BeanUtil.copyToList(zgSummaries, AppropNoticeSummary.class);
            bean.setZgSummaries(originalZgSummaries);
            try {
                for (AppropNoticeSummary zgSummary : zgSummaries) {
                    Field[] fields = zgSummary.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        Class<?> type = field.getType();
                        if(type == BigDecimal.class){
                            Object val = field.get(zgSummary);
                            if(StringUtils.isNotNull(val)){
                                field.set(zgSummary, NumberUtil.div(val.toString(), "10000").setScale(6,RoundingMode.HALF_UP));
                            }
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new CustomException("数据转换成万元异常："+e.getMessage(),-1);
            }
            appropNoticeSummaries.addAll(zgSummaries);
            bean.setConvertZgSummaries(zgSummaries);
        }
        List<AppropNoticeSummary> jmSummaries = dealJmSummary(map, appropNoticePreviewDTO, sysUser);
        if (StringUtils.isNotEmpty(jmSummaries)) {
            List<AppropNoticeSummary> originalJmSummaries = BeanUtil.copyToList(jmSummaries, AppropNoticeSummary.class);
            bean.setJmSummaries(originalJmSummaries);
            try {
                for (AppropNoticeSummary jmSummary : jmSummaries) {
                    Field[] fields = jmSummary.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        Class<?> type = field.getType();
                        if(type == BigDecimal.class){
                            Object val = field.get(jmSummary);
                            if(StringUtils.isNotNull(val)){
                                field.set(jmSummary, NumberUtil.div(val.toString(), "10000").setScale(6,RoundingMode.HALF_UP));
                            }
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new CustomException("数据转换成万元异常："+e.getMessage(),-1);
            }
            appropNoticeSummaries.addAll(jmSummaries);
            bean.setConvertJmSummaries(jmSummaries);
        }

        Set<String> orgCodeSet = new HashSet<>();
        if (StringUtils.isNotEmpty(map)) {
            List<AppropNoticeOccur> occurData = (List<AppropNoticeOccur>) map.get("occurData");
            if (StringUtils.isNotNull(occurData)) {
                //bean.setOccurData(occurData);
                Set<String> orgCode = occurData.stream().filter(j->j.getOrgCode()!=null).map(AppropNoticeOccur::getOrgCode).collect(Collectors.toSet());
                if (StringUtils.isNotEmpty(orgCode)) {
                    orgCodeSet.addAll(orgCode);
                }
            }
            List<AppropNoticeSettle> settleData = (List<AppropNoticeSettle>) map.get("settleData");
            if (StringUtils.isNotNull(settleData)) {
                //bean.setSettleData(settleData);
                Set<String> orgCode = settleData.stream().filter(j->j.getOrgCode()!=null).map(AppropNoticeSettle::getOrgCode).collect(Collectors.toSet());
                if (StringUtils.isNotEmpty(orgCode)) {
                    orgCodeSet.addAll(orgCode);
                }
            }
            List<AppropNoticeJmdbbxsjzf> jmdbbxsjzfData = (List<AppropNoticeJmdbbxsjzf>) map.get("jmdbbxsjzfData");
            if (StringUtils.isNotNull(jmdbbxsjzfData)) {
                //bean.setJmdbbxsjzfData(jmdbbxsjzfData);
                Set<String> orgCode = jmdbbxsjzfData.stream().filter(j->j.getOrgCode()!=null).map(AppropNoticeJmdbbxsjzf::getOrgCode).collect(Collectors.toSet());
                if (StringUtils.isNotEmpty(orgCode)) {
                    orgCodeSet.addAll(orgCode);
                }
            }
            List<AppropNoticeMonthSettle> monthSettleData = (List<AppropNoticeMonthSettle>) map.get("monthSettleData");
            if (StringUtils.isNotNull(monthSettleData)) {
                //bean.setMonthSettleData(monthSettleData);
                Set<String> orgCode = monthSettleData.stream().filter(j->j.getOrgCode()!=null).map(AppropNoticeMonthSettle::getOrgCode).collect(Collectors.toSet());
                if (StringUtils.isNotEmpty(orgCode)) {
                    orgCodeSet.addAll(orgCode);
                }
            }
            List<AppropNoticeDrg> drgData = (List<AppropNoticeDrg>) map.get("drgData");
            if (StringUtils.isNotNull(drgData)) {
                //bean.setDrgData(drgData);
                Set<String> orgCode = drgData.stream().filter(j->j.getOrgCode()!=null).map(AppropNoticeDrg::getOrgCode).collect(Collectors.toSet());
                if (StringUtils.isNotEmpty(orgCode)) {
                    orgCodeSet.addAll(orgCode);
                }
            }
        }

        List<List<String>> orgCodeLists = new ArrayList<>();
        List<String> orgCodeList = new ArrayList<>(orgCodeSet);
        int fromIndex = 0;
        int toIndex = 1000;
        while(true){
            if (toIndex > orgCodeList.size()) {
                toIndex = orgCodeList.size();
            }
            List<String> subList = orgCodeList.subList(fromIndex, toIndex);
            orgCodeLists.add(subList);
            if(toIndex==orgCodeList.size()){
                break;
            }
            fromIndex = toIndex;
            toIndex = toIndex+1000;
        }

        List<AppropNoticeDataDetail> appropNoticeDataDetails = new ArrayList<>();
        List<AppropNoticeDataDetail> zgDataDetails = dealZgDataDetail(map, appropNoticePreviewDTO, sysUser,orgCodeSet,orgCodeLists);
        if (StringUtils.isNotEmpty(zgDataDetails)) {
            appropNoticeDataDetails.addAll(zgDataDetails);
        }
        //log.info("previewNew zgDataDetails:{}",JSON.toJSONString(zgDataDetails));
        List<AppropNoticeDataDetail> jmDataDetails = dealJmDataDetail(map, appropNoticePreviewDTO, sysUser,orgCodeSet,orgCodeLists);
        //List<AppropNoticeDataDetail> jmDataDetails = new ArrayList<>();
        if (StringUtils.isNotEmpty(jmDataDetails)) {
            if (StringUtils.isEmpty(appropNoticeDataDetails)) {
                appropNoticeDataDetails.addAll(jmDataDetails);
            } else {
                try {
                    for (AppropNoticeDataDetail detail : appropNoticeDataDetails) {
                        List<AppropNoticeDataDetail> filterJmDataDetail = jmDataDetails.stream().filter(x -> x.getOrgCode().equals(detail.getOrgCode())).collect(Collectors.toList());
                        if (StringUtils.isNotEmpty(filterJmDataDetail)) {
                            AppropNoticeDataDetail jmDataDetail = filterJmDataDetail.get(0);
                            Field[] declaredFields = detail.getClass().getDeclaredFields();
                            for (Field declaredField : declaredFields) {
                                declaredField.setAccessible(true);
                                Field field = jmDataDetail.getClass().getDeclaredField(declaredField.getName());
                                field.setAccessible(true);
                                Object val = field.get(jmDataDetail);
                                if(StringUtils.isNotNull(val)){
                                    declaredField.set(detail,field.get(jmDataDetail));
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new CustomException("居民数据明细赋值异常："+e.getMessage(),-1);
                }
            }
        }
        if (StringUtils.isNotEmpty(appropNoticeDataDetails)) {
            List<AppropNoticeDataDetail> originalDataDetails = BeanUtil.copyToList(appropNoticeDataDetails, AppropNoticeDataDetail.class);
            bean.setDataDetail(originalDataDetails);
            try {
                for (AppropNoticeDataDetail record : appropNoticeDataDetails) {
                    Field[] fields = record.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        Class<?> type = field.getType();
                        if(type == BigDecimal.class){
                            Object val = field.get(record);
                            if(StringUtils.isNotNull(val)){
                                field.set(record, NumberUtil.div(val.toString(), "10000").setScale(6,RoundingMode.HALF_UP));
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new CustomException("数据转换成万元异常："+e.getMessage(),-1);
            }
            bean.setConvertDataDetail(appropNoticeDataDetails);
        }
        return bean;
    }

    public void testFile(String fileId){
        FileInfo occurfileInfo = fileInfoService.getById(fileId);
        InputStream occurFile = FileUtils.downLoadFile(dow_path,occurfileInfo);
        List<AppropNoticeOccur> appropNoticeOccurs = readerFile(occurFile,occurfileInfo.getFileName(), AppropNoticeOccur.class,2);
        log.info("testFile occurFile:{}", JSON.toJSONString(appropNoticeOccurs));
    }

    /**
     * @param file
     * @param beanType
     * @return java.util.List<T>
     * @description //TODO  读取文件
     * @author wangxiao
     * @date 2024/5/21
     */
    private <T> List<T> readerFile(MultipartFile file, Class<T> beanType,int startRowIndex) {
        InputStream in = null;
        List<T> rows = new ArrayList<>();
        try {
            in = file.getInputStream();
            String filename = file.getOriginalFilename();
            String fileType = filename.substring(filename.lastIndexOf(".")+1);
            List<List<Object>> rowsTemp = new ArrayList<>();
            //构建对象读取
            if(fileType.equals("xls")){
                Excel03SaxReader reader = new Excel03SaxReader(createRowHandler(rowsTemp,startRowIndex));
                IOUtils.setByteArrayMaxOverride(400000000);
                reader.read(in, -1);
            }else{
                Excel07SaxReader reader = new Excel07SaxReader(createRowHandler(rowsTemp,startRowIndex));
                IOUtils.setByteArrayMaxOverride(400000000);
                reader.read(in, -1);
            }
            Field[] declaredFields = beanType.getDeclaredFields();
            if(StringUtils.isNotEmpty(rowsTemp)){
                for (List<Object> objects : rowsTemp) {
                    T t = beanType.newInstance();
                    int colIndex = 0;
                    for (Field field : declaredFields) {
                        Object val = null;
                        field.setAccessible(true);
                        Class<?> type = field.getType();
                        if (field.isAnnotationPresent(Alias.class)) {
                            Alias alias = field.getAnnotation(Alias.class);
                            if (alias != null) {
                                val = objects.get(colIndex);
                                if(type == BigDecimal.class){
                                    if(StringUtils.isNull(val) || StringUtils.isBlank(val.toString()) ){
                                        val = BigDecimal.ZERO.setScale(2,BigDecimal.ROUND_HALF_UP);
                                    }else{
                                        val = NumberUtil.toBigDecimal(val.toString()).setScale(2,BigDecimal.ROUND_HALF_UP);;
                                    }
                                }else{
                                    if(StringUtils.isNotNull(val) && StringUtils.isNotBlank(val.toString()) ){
                                        val = val.toString();
                                    }
                                }
                                colIndex++;
                            }
                        }else{
                            if(type == BigDecimal.class){
                                val = BigDecimal.ZERO.setScale(2,BigDecimal.ROUND_HALF_UP);
                            }
                        }
                        field.set(t,val);
                    }
                    rows.add(t);
                }
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

    /**
     * @param in
     * @param beanType
     * @return java.util.List<T>
     * @description //TODO  读取文件
     * @author wangxiao
     * @date 2024/5/21
     */
    private <T> List<T> readerFile(InputStream in,String filename, Class<T> beanType,int startRowIndex) {
//        InputStream in = null;
        List<T> rows = new ArrayList<>();
        try {
//            in = file.getInputStream();
            //String filename = file.getOriginalFilename();
            if(in==null)
                return rows;
            String fileType = filename.substring(filename.lastIndexOf(".")+1);
            List<List<Object>> rowsTemp = new ArrayList<>();
            //构建对象读取
            if(fileType.equals("xls")){
                Excel03SaxReader reader = new Excel03SaxReader(createRowHandler(rowsTemp,startRowIndex));
                IOUtils.setByteArrayMaxOverride(400000000);
                reader.read(in, -1);
            }else{
                Excel07SaxReader reader = new Excel07SaxReader(createRowHandler(rowsTemp,startRowIndex));
                IOUtils.setByteArrayMaxOverride(400000000);
                reader.read(in, -1);
            }
            Field[] declaredFields = beanType.getDeclaredFields();
            if(StringUtils.isNotEmpty(rowsTemp)){
                for (List<Object> objects : rowsTemp) {
                    T t = beanType.newInstance();
                    int colIndex = 0;
                    for (Field field : declaredFields) {
                        Object val = null;
                        field.setAccessible(true);
                        Class<?> type = field.getType();
                        if (field.isAnnotationPresent(Alias.class)) {
                            Alias alias = field.getAnnotation(Alias.class);
                            if (alias != null) {
                                val = objects.get(colIndex);
                                if(type == BigDecimal.class){
                                    if(StringUtils.isNull(val) || StringUtils.isBlank(val.toString()) ){
                                        val = BigDecimal.ZERO.setScale(2,BigDecimal.ROUND_HALF_UP);
                                    }else{
                                        val = NumberUtil.toBigDecimal(val.toString()).setScale(2,BigDecimal.ROUND_HALF_UP);;
                                    }
                                }else{
                                    if(StringUtils.isNotNull(val) && StringUtils.isNotBlank(val.toString()) ){
                                        val = val.toString();
                                    }
                                }
                                colIndex++;
                            }
                        }else{
                            if(type == BigDecimal.class){
                                val = BigDecimal.ZERO.setScale(2,BigDecimal.ROUND_HALF_UP);
                            }
                        }
                        field.set(t,val);
                    }
                    rows.add(t);
                }
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

    private RowHandler createRowHandler(List<List<Object>> rows,int startRowIndex) {
        return new RowHandler() {
            @Override
            public void handle(int sheetIndex, long rowIndex, List<Object> rowlist) {
                if (rowIndex == 0) {
                    return;
                }
                if(rowIndex >= startRowIndex && StringUtils.isNotEmpty(rowlist)){
//                    if(rowlist.get(0)==null||StringUtils.isEmpty(rowlist.get(0).toString())){
//
//                    } else if(!rowlist.stream().anyMatch(r->r!=null&&"合计".equals(r.toString()))){
//                        rows.add(rowlist);
//                    }
                    if(!rowlist.stream().anyMatch(r->r!=null&&sumStr.equals(r.toString()))){
                        rows.add(rowlist);
                    }
                }
            }
        };
    }


    /**
     * @param appropNoticePreviewDTO
     * @return void
     * @description //TODO  读取文件数据
     * @author wangxiao
     * @date 2024/5/21
     */
    private Map<String, Object> readFileData(MultipartFile occurFile,
                                             MultipartFile settleFile,
                                             MultipartFile jmdbbxsjzfFile,
                                             MultipartFile monthSettleFile,
                                             MultipartFile drgFile,
                                             AppropNoticePreviewDTO appropNoticePreviewDTO, SysUser sysUser) {
        List<AppropNoticeOccur> appropNoticeOccurs = readerFile(occurFile, AppropNoticeOccur.class,2);
        if (StringUtils.isNotEmpty(appropNoticeOccurs)) {
            appropNoticeOccurs.stream().forEach(x -> {
                x.setIsDel("0");
                x.setId(IdUtil.simpleUUID());
                x.setCreateUser(sysUser.getId());
                x.setCreateTime(new Date());
                x.setYear(appropNoticePreviewDTO.getYear());
                x.setMonth(appropNoticePreviewDTO.getMonth());
            });
        }
        List<AppropNoticeSettle> appropNoticeSettles = readerFile(settleFile, AppropNoticeSettle.class,2);
        if (StringUtils.isNotEmpty(appropNoticeSettles)) {
            appropNoticeSettles.stream().forEach(x -> {
                x.setIsDel("0");
                x.setId(IdUtil.simpleUUID());
                x.setCreateUser(sysUser.getId());
                x.setCreateTime(new Date());
                x.setYear(appropNoticePreviewDTO.getYear());
                x.setMonth(appropNoticePreviewDTO.getMonth());
            });
        }
        List<AppropNoticeJmdbbxsjzf> appropNoticeJmdbbxsjzfs = readerFile(jmdbbxsjzfFile, AppropNoticeJmdbbxsjzf.class,1);
        if (StringUtils.isNotEmpty(appropNoticeJmdbbxsjzfs)) {
            appropNoticeJmdbbxsjzfs.stream().forEach(x -> {
                x.setIsDel("0");
                x.setId(IdUtil.simpleUUID());
                x.setCreateUser(sysUser.getId());
                x.setCreateTime(new Date());
                x.setYear(appropNoticePreviewDTO.getYear());
                x.setMonth(appropNoticePreviewDTO.getMonth());
            });
        }
        List<AppropNoticeMonthSettle> appropNoticeMonthSettles = readerFile(monthSettleFile, AppropNoticeMonthSettle.class,4);
        if (StringUtils.isNotEmpty(appropNoticeMonthSettles)) {
            appropNoticeMonthSettles.stream().forEach(x -> {
                x.setIsDel("0");
                x.setId(IdUtil.simpleUUID());
                x.setCreateUser(sysUser.getId());
                x.setCreateTime(new Date());
                x.setYear(appropNoticePreviewDTO.getYear());
                x.setMonth(appropNoticePreviewDTO.getMonth());
            });
        }
        List<AppropNoticeDrg> appropNoticeDrgs = readerFile(drgFile, AppropNoticeDrg.class,4);
        if (StringUtils.isNotEmpty(appropNoticeDrgs)) {
            appropNoticeDrgs.stream().forEach(x -> {
                x.setIsDel("0");
                x.setId(IdUtil.simpleUUID());
                x.setCreateUser(sysUser.getId());
                x.setCreateTime(new Date());
                x.setYear(appropNoticePreviewDTO.getYear());
                x.setMonth(appropNoticePreviewDTO.getMonth());
            });
        }
        Map<String, Object> map = new HashMap<>();
        map.put("occurData", appropNoticeOccurs);
        map.put("settleData", appropNoticeSettles);
        map.put("jmdbbxsjzfData", appropNoticeJmdbbxsjzfs);
        map.put("monthSettleData", appropNoticeMonthSettles);
        map.put("drgData", appropNoticeDrgs);
        return map;
    }

    /**
     * @param appropNoticePreviewDTO
     * @return void
     * @description //TODO  读取文件数据
     * @author wangxiao
     * @date 2024/5/21
     */
    private Map<String, Object> readFileData(String occurFileId,
                                             String settleFileId,
                                             String jmdbbxsjzfFileId,
                                             String monthSettleFileId,
                                             String drgFileId,
                                             AppropNoticePreviewDTO appropNoticePreviewDTO, SysUser sysUser) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<FileInfo> occurfileInfos = fileInfoService.getFileInfoByBizId(occurFileId,DocumentTypeEnum.FSS.getCode());
            FileInfo occurfileInfo = CollectionUtils.isEmpty(occurfileInfos)?null:occurfileInfos.get(0);
            InputStream occurFile = FileUtils.downLoadFile(dow_path,occurfileInfo);
            //File file1 = new File("D:\\表一：发生数模板.xlsx");
            //InputStream occurFile = new FileInputStream(file1);
            List<AppropNoticeOccur> appropNoticeOccurs = readerFile(occurFile,occurfileInfo!=null?occurfileInfo.getFileName():"", AppropNoticeOccur.class,2);
            //log.info("readFileData appropNoticeOccurs:{}",JSON.toJSONString(appropNoticeOccurs));
            if (StringUtils.isNotEmpty(appropNoticeOccurs)) {
                appropNoticeOccurs.stream().forEach(x -> {
                    x.setIsDel("0");
                    x.setId(IdUtil.simpleUUID());
                    x.setCreateUser(sysUser.getId());
                    x.setCreateTime(new Date());
                    x.setYear(appropNoticePreviewDTO.getYear());
                    x.setMonth(appropNoticePreviewDTO.getMonth());
                });
            }
            List<FileInfo> settlefileInfos  = fileInfoService.getFileInfoByBizId(settleFileId,DocumentTypeEnum.YJS.getCode());
            FileInfo settlefileInfo = CollectionUtils.isEmpty(settlefileInfos)?null:settlefileInfos.get(0);
            InputStream settleFile = FileUtils.downLoadFile(dow_path,settlefileInfo);
            //File file2 = new File("D:\\表二：应结算模板.xlsx");
            //InputStream settleFile = new FileInputStream(file2);
            List<AppropNoticeSettle> appropNoticeSettles = readerFile(settleFile,settlefileInfo!=null?settlefileInfo.getFileName():"", AppropNoticeSettle.class,2);
            //log.info("readFileData appropNoticeSettles:{}",JSON.toJSONString(appropNoticeSettles));
            if (StringUtils.isNotEmpty(appropNoticeSettles)) {
                appropNoticeSettles.stream().forEach(x -> {
                    x.setIsDel("0");
                    x.setId(IdUtil.simpleUUID());
                    x.setCreateUser(sysUser.getId());
                    x.setCreateTime(new Date());
                    x.setYear(appropNoticePreviewDTO.getYear());
                    x.setMonth(appropNoticePreviewDTO.getMonth());
                });
            }
            List<FileInfo> jmdbbxsjzffileInfos  = fileInfoService.getFileInfoByBizId(jmdbbxsjzfFileId,DocumentTypeEnum.SJZF.getCode());
            FileInfo jmdbbxsjzffileInfo = CollectionUtils.isEmpty(jmdbbxsjzffileInfos)?null:jmdbbxsjzffileInfos.get(0);
            InputStream jmdbbxsjzfFile = FileUtils.downLoadFile(dow_path,jmdbbxsjzffileInfo);
            //File file3 = new File("D:\\表三：居民大病保险实际支付统计表模板.xlsx");
            //InputStream jmdbbxsjzfFile = new FileInputStream(file3);
            List<AppropNoticeJmdbbxsjzf> appropNoticeJmdbbxsjzfs = readerFile(jmdbbxsjzfFile,jmdbbxsjzffileInfo!=null?jmdbbxsjzffileInfo.getFileName():"", AppropNoticeJmdbbxsjzf.class,1);
            //log.info("readFileData appropNoticeJmdbbxsjzfs:{}",JSON.toJSONString(appropNoticeJmdbbxsjzfs));
            if (StringUtils.isNotEmpty(appropNoticeJmdbbxsjzfs)) {
                appropNoticeJmdbbxsjzfs.stream().forEach(x -> {
                    x.setIsDel("0");
                    x.setId(IdUtil.simpleUUID());
                    x.setCreateUser(sysUser.getId());
                    x.setCreateTime(new Date());
                    x.setYear(appropNoticePreviewDTO.getYear());
                    x.setMonth(appropNoticePreviewDTO.getMonth());
                });
            }
            List<FileInfo> monthSettlefileInfos  = fileInfoService.getFileInfoByBizId(monthSettleFileId,DocumentTypeEnum.MJS.getCode());
            FileInfo monthSettlefileInfo = CollectionUtils.isEmpty(monthSettlefileInfos)?null:monthSettlefileInfos.get(0);
            //File file4 = new File("D:\\表四：月结算表模板.xlsx");
            //InputStream monthSettleFile = new FileInputStream(file4);
            InputStream monthSettleFile = FileUtils.downLoadFile(dow_path,monthSettlefileInfo);
            List<AppropNoticeMonthSettle> appropNoticeMonthSettles = readerFile(monthSettleFile,monthSettlefileInfo!=null?monthSettlefileInfo.getFileName():"", AppropNoticeMonthSettle.class,4);
            //log.info("readFileData appropNoticeMonthSettles:{}",JSON.toJSONString(appropNoticeMonthSettles));
            if (StringUtils.isNotEmpty(appropNoticeMonthSettles)) {
                appropNoticeMonthSettles.stream().forEach(x -> {
                    x.setIsDel("0");
                    x.setId(IdUtil.simpleUUID());
                    x.setCreateUser(sysUser.getId());
                    x.setCreateTime(new Date());
                    x.setYear(appropNoticePreviewDTO.getYear());
                    x.setMonth(appropNoticePreviewDTO.getMonth());
                });
            }
            List<FileInfo> drgfileInfos  = fileInfoService.getFileInfoByBizId(drgFileId,DocumentTypeEnum.DRG.getCode());
            FileInfo drgfileInfo = CollectionUtils.isEmpty(drgfileInfos)?null:drgfileInfos.get(0);
            //File file5 = new File("D:\\表五：DRG表模板.xlsx");
            //InputStream drgFile = new FileInputStream(file5);
            InputStream drgFile = FileUtils.downLoadFile(dow_path,drgfileInfo);
            List<AppropNoticeDrg> appropNoticeDrgs = readerFile(drgFile,drgfileInfo!=null?drgfileInfo.getFileName():"", AppropNoticeDrg.class,4);
            //log.info("readFileData appropNoticeDrgs:{}",JSON.toJSONString(appropNoticeDrgs));
            if (StringUtils.isNotEmpty(appropNoticeDrgs)) {
                appropNoticeDrgs.stream().forEach(x -> {
                    x.setIsDel("0");
                    x.setId(IdUtil.simpleUUID());
                    x.setCreateUser(sysUser.getId());
                    x.setCreateTime(new Date());
                    x.setYear(appropNoticePreviewDTO.getYear());
                    x.setMonth(appropNoticePreviewDTO.getMonth());
                });
            }
            map.put("occurData", appropNoticeOccurs);
            map.put("settleData", appropNoticeSettles);
            map.put("jmdbbxsjzfData", appropNoticeJmdbbxsjzfs);
            map.put("monthSettleData", appropNoticeMonthSettles);
            map.put("drgData", appropNoticeDrgs);
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    /**
     * @param map
     * @param appropNoticePreviewDTO
     * @param sysUser
     * @return java.util.List<com.jsdc.ybpt.appropNotice.entity.AppropNoticeSummary>
     * @description //TODO  处理职工汇总数据
     * @author wangxiao
     * @date 2024/5/22
     */
    private List<AppropNoticeSummary> dealZgSummary(Map<String, Object> map, AppropNoticePreviewDTO appropNoticePreviewDTO, SysUser sysUser) {
        List<AppropNoticeSummary> appropNoticeSummaries = new ArrayList<>();
        //发生数
        List<AppropNoticeOccur> appropNoticeOccurs = (List<AppropNoticeOccur>) map.get("occurData");
        //应结算
        List<AppropNoticeSettle> appropNoticeSettles = (List<AppropNoticeSettle>) map.get("settleData");
        //居民大病保险实际支付
        List<AppropNoticeJmdbbxsjzf> appropNoticeJmdbbxsjzfs = (List<AppropNoticeJmdbbxsjzf>) map.get("jmdbbxsjzfData");
        //月结算
        List<AppropNoticeMonthSettle> appropNoticeMonthSettles = (List<AppropNoticeMonthSettle>) map.get("monthSettleData");
        //DRG
        List<AppropNoticeDrg> appropNoticeDrgs = (List<AppropNoticeDrg>) map.get("drgData");
        Integer currYear = appropNoticePreviewDTO.getYear();
        Integer currMonth = appropNoticePreviewDTO.getMonth();
        Set<String> tcqSet = new HashSet<>();
        if (StringUtils.isNotEmpty(appropNoticeOccurs)) {
            Set<String> tcq = appropNoticeOccurs.stream().filter(t->t.getJstcq()!=null).map(AppropNoticeOccur::getJstcq).collect(Collectors.toSet());
            if (StringUtils.isNotEmpty(tcq)) {
                tcqSet.addAll(tcq);
            }
        }
        if (StringUtils.isNotEmpty(appropNoticeSettles)) {
            Set<String> tcq = appropNoticeSettles.stream().filter(t->t.getTcq()!=null).map(AppropNoticeSettle::getTcq).collect(Collectors.toSet());
            if (StringUtils.isNotEmpty(tcq)) {
                tcqSet.addAll(tcq);
            }
        }
        if (StringUtils.isNotEmpty(appropNoticeJmdbbxsjzfs)) {
            Set<String> tcq = appropNoticeJmdbbxsjzfs.stream().filter(t->t.getTcq()!=null).map(AppropNoticeJmdbbxsjzf::getTcq).collect(Collectors.toSet());
            if (StringUtils.isNotEmpty(tcq)) {
                tcqSet.addAll(tcq);
            }
        }
        if (StringUtils.isNotEmpty(appropNoticeMonthSettles)) {
            Set<String> tcq = appropNoticeMonthSettles.stream().filter(t->t.getTcq()!=null).map(AppropNoticeMonthSettle::getTcq).collect(Collectors.toSet());
            if (StringUtils.isNotEmpty(tcq)) {
                tcqSet.addAll(tcq);
            }
        }
        if (StringUtils.isNotEmpty(appropNoticeDrgs)) {
            Set<String> tcq = appropNoticeDrgs.stream().filter(t->t.getTcq()!=null).map(AppropNoticeDrg::getTcq).collect(Collectors.toSet());
            if (StringUtils.isNotEmpty(tcq)) {
                tcqSet.addAll(tcq);
            }
        }

        //if (StringUtils.isNotEmpty(tcqSet)) {
        if (!CollectionUtils.isEmpty(tcqSet)) {
            //查询当前月是否有数据
            QueryWrapper<AppropNoticeSummary> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("count(*) as year");
            queryWrapper.eq("is_del","0");
            queryWrapper.eq("year", currYear);
            queryWrapper.eq("rylb", "1");
            queryWrapper.in("tcq", tcqSet);
            queryWrapper.eq("month", currMonth);
            List<AppropNoticeSummary> zgSummaryCurrentMonthList = appropNoticeSummaryService.list(queryWrapper);
            AppropNoticeSummary appropNoticeSummary = zgSummaryCurrentMonthList.get(0);
            if(appropNoticeSummary.getYear()>0){
                throw new CustomException("已存在职工汇总数据，请检查文件！");
            }
//            if(StringUtils.isNotEmpty(zgSummaryCurrentMonthList)){
//                String existTcqs = zgSummaryCurrentMonthList.stream().map(AppropNoticeSummary::getTcq).collect(Collectors.joining(","));
//                throw new CustomException("["+existTcqs+"]已存在职工汇总数据，请检查文件！");
//            }
            //查询上个月合计
            QueryWrapper<AppropNoticeSummary> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("is_del","0");
            queryWrapper2.eq("year", currYear);
            queryWrapper2.eq("rylb", "1");
            queryWrapper2.in("tcq", tcqSet);
            queryWrapper2.eq("month", currMonth - 1);
            List<AppropNoticeSummary> zgSummaryLastMonthList = appropNoticeSummaryService.list(queryWrapper2);
            List<SysDict> zgSettlePolicyList = sysDictService.list(new QueryWrapper<SysDict>().eq("dict_type", "zg_settle_policy"));
            for (String tcq : tcqSet) {
                AppropNoticeSummary zgSummary = new AppropNoticeSummary();
                zgSummary.setId(IdUtil.simpleUUID());
                zgSummary.setCreateTime(new Date());
                zgSummary.setCreateUser(sysUser.getId());
                zgSummary.setYear(currYear);
                zgSummary.setMonth(currMonth);
                zgSummary.setTcq(tcq);
                zgSummary.setRylb("1");
                BigDecimal fszfy = BigDecimal.ZERO;//发生总费用
                BigDecimal tcjjfsje = BigDecimal.ZERO;//统筹基金发生金额
                BigDecimal dbjjfsje = BigDecimal.ZERO;//大病基金发生金额
                BigDecimal gwyjjfsje = BigDecimal.ZERO;//公务员基金发生金额
                BigDecimal scjjfsje = BigDecimal.ZERO;//伤残基金发生金额
                BigDecimal jzjjfsje = BigDecimal.ZERO;//救助基金发生金额
                BigDecimal gzjjfsje = BigDecimal.ZERO;//个账基金发生金额
                BigDecimal dyXj = BigDecimal.ZERO;//现金
                if (StringUtils.isNotEmpty(appropNoticeOccurs)) {
                    List<AppropNoticeOccur> filterOccur = appropNoticeOccurs.stream().filter(x ->
                            x.getJstcq().equals(tcq) && (x.getXzlx().equals(InsutypeEnum.ZGJBYLBX.getInfo()) || x.getXzlx().equals(InsutypeEnum.SYBX.getInfo()))
                    ).collect(Collectors.toList());
                    if (StringUtils.isNotEmpty(filterOccur)) {
                        for (AppropNoticeOccur appropNoticeOccur : filterOccur) {
                            BigDecimal ylfze = appropNoticeOccur.getYlfze();
                            fszfy = NumberUtil.add(ylfze, fszfy);
                            BigDecimal tcjj = appropNoticeOccur.getTcjj();
                            BigDecimal syjj = appropNoticeOccur.getSyjj();
                            tcjjfsje = NumberUtil.add(tcjj, syjj, tcjjfsje);
                            BigDecimal zgdbjj = appropNoticeOccur.getZgdbjj();
                            dbjjfsje = NumberUtil.add(zgdbjj, dbjjfsje);
                            BigDecimal gwybzjj = appropNoticeOccur.getGwybzjj();
                            gwyjjfsje = NumberUtil.add(gwybzjj, gwyjjfsje);
                            BigDecimal cjjrbzjj = appropNoticeOccur.getCjjrbzjj();
                            scjjfsje = NumberUtil.add(cjjrbzjj, scjjfsje);
                            BigDecimal yljzjj = appropNoticeOccur.getYljzjj();
                            jzjjfsje = NumberUtil.add(yljzjj, jzjjfsje);
                            BigDecimal grzh = appropNoticeOccur.getGrzh();
                            BigDecimal gjzh = appropNoticeOccur.getGjzh();
                            gzjjfsje = NumberUtil.add(grzh, gjzh, gzjjfsje);
                            BigDecimal xj = appropNoticeOccur.getXj();
                            dyXj = NumberUtil.add(xj, dyXj);
                        }
                    }
                }
                zgSummary.setDyFszfy(fszfy.setScale(2,RoundingMode.HALF_UP));
                zgSummary.setDyTcjjfsje(tcjjfsje.setScale(2,RoundingMode.HALF_UP));
                zgSummary.setDyDbjjfsje(dbjjfsje.setScale(2,RoundingMode.HALF_UP));
                zgSummary.setDyGwyjjfsje(gwyjjfsje.setScale(2,RoundingMode.HALF_UP));
                zgSummary.setDyScjjfsje(scjjfsje.setScale(2,RoundingMode.HALF_UP));
                zgSummary.setDyJzjjfsje(jzjjfsje.setScale(2,RoundingMode.HALF_UP));
                zgSummary.setDyGzjjfsje(gzjjfsje.setScale(2,RoundingMode.HALF_UP));
                zgSummary.setDyXj(dyXj.setScale(2,RoundingMode.HALF_UP));
                BigDecimal jsxj = BigDecimal.ZERO;//结算小计
                BigDecimal tcjjjsje = BigDecimal.ZERO;//统筹基金结算金额
                BigDecimal dbjjjsje = BigDecimal.ZERO;//大病基金结算金额
                BigDecimal gwyjjjsje = BigDecimal.ZERO;//公务员基金结算金额
                BigDecimal scjjjsje = BigDecimal.ZERO;//伤残基金结算金额
                BigDecimal jzjjjsje = BigDecimal.ZERO;//救助基金结算金额
                BigDecimal gzjjjsje = BigDecimal.ZERO;//个账基金结算金额
                if (StringUtils.isNotEmpty(appropNoticeSettles)) {
                    if (StringUtils.isNotEmpty(zgSettlePolicyList)) {
                        List<String> settlePolicyNames = zgSettlePolicyList.stream().map(SysDict::getLabel).collect(Collectors.toList());
                        List<AppropNoticeSettle> filterSettle = appropNoticeSettles.stream().filter(x -> x.getTcq().equals(tcq) && settlePolicyNames.contains(x.getJszcmc())).collect(Collectors.toList());
                        if (StringUtils.isNotEmpty(filterSettle)) {
                            for (AppropNoticeSettle appropNoticeSettle : filterSettle) {
                                BigDecimal hj = appropNoticeSettle.getHj();
                                jsxj = NumberUtil.add(hj, jsxj);
                                BigDecimal zgtcjj = appropNoticeSettle.getZgtcjj();
                                BigDecimal syjj = appropNoticeSettle.getSyjj();
                                tcjjjsje = NumberUtil.add(zgtcjj, syjj, tcjjjsje);
                                BigDecimal zgdbjj = appropNoticeSettle.getZgdbjj();
                                dbjjjsje = NumberUtil.add(zgdbjj, dbjjjsje);
                                BigDecimal gwybzjj = appropNoticeSettle.getGwybzjj();
                                gwyjjjsje = NumberUtil.add(gwybzjj, gwyjjjsje);
                                BigDecimal cjjrbzjj = appropNoticeSettle.getCjjrbzjj();
                                scjjjsje = NumberUtil.add(cjjrbzjj, scjjjsje);
                                BigDecimal yljzjj = appropNoticeSettle.getYljzjj();
                                jzjjjsje = NumberUtil.add(yljzjj, jzjjjsje);
                                BigDecimal grzh = appropNoticeSettle.getGrzh();
                                BigDecimal gjzh = appropNoticeSettle.getGjzh();
                                gzjjjsje = NumberUtil.add(grzh, gjzh, gzjjjsje);
                            }
                        }
                    }
                }
                zgSummary.setDyJsxj(jsxj.setScale(2,RoundingMode.HALF_UP));
                zgSummary.setDyTcjjjsje(tcjjjsje.setScale(2,RoundingMode.HALF_UP));
                zgSummary.setDyDbjjjsje(dbjjjsje.setScale(2,RoundingMode.HALF_UP));
                zgSummary.setDyGwyjjjsje(gwyjjjsje.setScale(2,RoundingMode.HALF_UP));
                zgSummary.setDyScjjjsje(scjjjsje.setScale(2,RoundingMode.HALF_UP));
                zgSummary.setDyJzjjjsje(jzjjjsje.setScale(2,RoundingMode.HALF_UP));
                zgSummary.setDyGzjjjsje(gzjjjsje.setScale(2,RoundingMode.HALF_UP));
                BigDecimal jjbfxj = BigDecimal.ZERO;//基金拨付小计
                BigDecimal jjbfje = BigDecimal.ZERO;//基金拨付金额
                BigDecimal khbzj = BigDecimal.ZERO;//当月考核保证金
                BigDecimal kk = BigDecimal.ZERO;//当月扣款
                if (StringUtils.isNotEmpty(appropNoticeMonthSettles)) {
                    List<AppropNoticeMonthSettle> filterMonthSettle = appropNoticeMonthSettles.stream().filter(x -> x.getTcq().equals(tcq)).collect(Collectors.toList());
                    if (StringUtils.isNotEmpty(filterMonthSettle)) {
                        for (AppropNoticeMonthSettle appropNoticeMonthSettle : filterMonthSettle) {
                            BigDecimal zgjjbfje = appropNoticeMonthSettle.getZgjjbfje();
                            jjbfje = NumberUtil.add(zgjjbfje, jjbfje);
                            BigDecimal zgkhbzjlj = appropNoticeMonthSettle.getZgkhbzjlj();
                            khbzj = NumberUtil.add(zgkhbzjlj, khbzj);
                            BigDecimal zgbyzflj = appropNoticeMonthSettle.getZgbyzflj();
                            kk = NumberUtil.add(zgbyzflj, kk);
                        }
                    }
                }
                if (StringUtils.isNotEmpty(appropNoticeDrgs)) {
                    List<AppropNoticeDrg> filterDrg = appropNoticeDrgs.stream().filter(x -> x.getTcq().equals(tcq)).collect(Collectors.toList());
                    if (StringUtils.isNotEmpty(filterDrg)) {
                        for (AppropNoticeDrg appropNoticeDrg : filterDrg) {
                            BigDecimal zgjjbfje = appropNoticeDrg.getZgjjbfje();
                            jjbfje = NumberUtil.add(zgjjbfje, jjbfje);
                            BigDecimal zgbyzflj = appropNoticeDrg.getZgbyzflj();
                            kk = NumberUtil.add(zgbyzflj, kk);
                        }
                    }
                }
                jjbfxj = NumberUtil.sub(jjbfje, khbzj, kk);
                zgSummary.setDyJjbfxj(jjbfxj.setScale(2,RoundingMode.HALF_UP));
                zgSummary.setDyJjbfje(jjbfje.setScale(2,RoundingMode.HALF_UP));
                zgSummary.setDyKhbzj(khbzj.setScale(2,RoundingMode.HALF_UP));
                zgSummary.setDyKk(kk.setScale(2,RoundingMode.HALF_UP));
                //查询上个月合计
                AppropNoticeSummary zgSummaryLastMonth = null;
                if(StringUtils.isNotEmpty(zgSummaryLastMonthList)){
                    List<AppropNoticeSummary> collect = zgSummaryLastMonthList.stream().filter(x -> x.getTcq().equals(tcq)).collect(Collectors.toList());
                    if(StringUtils.isNotEmpty(collect)){
                        zgSummaryLastMonth = collect.get(0);
                    }
                }
                BigDecimal fszfylj = fszfy;//发生总费用累计
                BigDecimal tcjjfsjelj = tcjjfsje;//统筹基金发生金额累计
                BigDecimal dbjjfsjelj = dbjjfsje;//大病基金发生金额累计
                BigDecimal gwyjjfsjelj = gwyjjfsje;//公务员基金发生金额累计
                BigDecimal scjjfsjelj = scjjfsje;//伤残基金发生金额累计
                BigDecimal jzjjfsjelj = jzjjfsje;//救助基金发生金额累计
                BigDecimal gzjjfsjelj = gzjjfsje;//个账基金发生金额累计
                BigDecimal xjlj = dyXj;//现金累计
                BigDecimal jsxjlj = jsxj;//结算小计累计
                BigDecimal tcjjjsjelj = tcjjjsje;//统筹基金结算金额累计
                BigDecimal dbjjjsjelj = dbjjjsje;//大病基金结算金额累计
                BigDecimal gwyjjjsjelj = gwyjjjsje;//公务员基金结算金额累计
                BigDecimal scjjjsjelj = scjjjsje;//伤残基金结算金额累计
                BigDecimal jzjjjsjelj = jzjjjsje;//救助基金结算金额累计
                BigDecimal gzjjjsjelj = gzjjjsje;//个账基金结算金额累计
                BigDecimal jjbfxjlj = jjbfxj;//基金拨付小计累计
                BigDecimal jjbfjelj = jjbfje;//基金拨付金额累计
                BigDecimal khbzjlj = khbzj;//考核保证金累计
                BigDecimal kklj = kk;//扣款累计
                if (StringUtils.isNotNull(zgSummaryLastMonth)) {
                    BigDecimal fszfyljLastMonth = StringUtils.isNotNull(zgSummaryLastMonth.getLjFszfy())?zgSummaryLastMonth.getLjFszfy():BigDecimal.ZERO;
                    fszfylj = NumberUtil.add(fszfyljLastMonth, fszfy);
                    BigDecimal tcjjfsjeljLastMonth = StringUtils.isNotNull(zgSummaryLastMonth.getLjTcjjfsje())?zgSummaryLastMonth.getLjTcjjfsje():BigDecimal.ZERO;
                    tcjjfsjelj = NumberUtil.add(tcjjfsjeljLastMonth, tcjjfsje);
                    BigDecimal dbjjfsjeljLastMonth = StringUtils.isNotNull(zgSummaryLastMonth.getLjDbjjfsje())?zgSummaryLastMonth.getLjDbjjfsje():BigDecimal.ZERO;
                    dbjjfsjelj = NumberUtil.add(dbjjfsjeljLastMonth, dbjjfsje);
                    BigDecimal gwyjjfsjeljLastMonth = StringUtils.isNotNull(zgSummaryLastMonth.getLjGwyjjfsje())?zgSummaryLastMonth.getLjGwyjjfsje():BigDecimal.ZERO;
                    gwyjjfsjelj = NumberUtil.add(gwyjjfsjeljLastMonth, gwyjjfsje);
                    BigDecimal scjjfsjeljLastMonth = StringUtils.isNotNull(zgSummaryLastMonth.getLjScjjfsje())?zgSummaryLastMonth.getLjScjjfsje():BigDecimal.ZERO;
                    scjjfsjelj = NumberUtil.add(scjjfsjeljLastMonth, scjjfsje);
                    BigDecimal jzjjfsjeljLastMonth = StringUtils.isNotNull(zgSummaryLastMonth.getLjJzjjfsje())?zgSummaryLastMonth.getLjJzjjfsje():BigDecimal.ZERO;
                    jzjjfsjelj = NumberUtil.add(jzjjfsjeljLastMonth, jzjjfsje);
                    BigDecimal gzjjfsjeljLastMonth = StringUtils.isNotNull(zgSummaryLastMonth.getLjGzjjfsje())?zgSummaryLastMonth.getLjGzjjfsje():BigDecimal.ZERO;
                    gzjjfsjelj = NumberUtil.add(gzjjfsjeljLastMonth, gzjjfsje);
                    BigDecimal xjljLastMonth = StringUtils.isNotNull(zgSummaryLastMonth.getLjXj())?zgSummaryLastMonth.getLjXj():BigDecimal.ZERO;
                    xjlj = NumberUtil.add(xjljLastMonth, dyXj);
                    BigDecimal jsxjljLastMonth = StringUtils.isNotNull(zgSummaryLastMonth.getLjJsxj())?zgSummaryLastMonth.getLjJsxj():BigDecimal.ZERO;
                    jsxjlj = NumberUtil.add(jsxjljLastMonth, jsxj);
                    BigDecimal tcjjjsjeljLastMonth = StringUtils.isNotNull(zgSummaryLastMonth.getLjTcjjjsje())?zgSummaryLastMonth.getLjTcjjjsje():BigDecimal.ZERO;
                    tcjjjsjelj = NumberUtil.add(tcjjjsjeljLastMonth, tcjjjsje);
                    BigDecimal dbjjjsjeljLastMonth = StringUtils.isNotNull(zgSummaryLastMonth.getLjDbjjjsje())?zgSummaryLastMonth.getLjDbjjjsje():BigDecimal.ZERO;
                    dbjjjsjelj = NumberUtil.add(dbjjjsjeljLastMonth, dbjjjsje);
                    BigDecimal gwyjjjsjeljLastMonth = StringUtils.isNotNull(zgSummaryLastMonth.getLjGwyjjjsje())?zgSummaryLastMonth.getLjGwyjjjsje():BigDecimal.ZERO;
                    gwyjjjsjelj = NumberUtil.add(gwyjjjsjeljLastMonth, gwyjjjsje);
                    BigDecimal scjjjsjeljLastMonth = StringUtils.isNotNull(zgSummaryLastMonth.getLjScjjjsje())?zgSummaryLastMonth.getLjScjjjsje():BigDecimal.ZERO;
                    scjjjsjelj = NumberUtil.add(scjjjsjeljLastMonth, scjjjsje);
                    BigDecimal jzjjjsjeljLastMonth = StringUtils.isNotNull(zgSummaryLastMonth.getLjJzjjjsje())?zgSummaryLastMonth.getLjJzjjjsje():BigDecimal.ZERO;
                    jzjjjsjelj = NumberUtil.add(jzjjjsjeljLastMonth, jzjjjsje);
                    BigDecimal gzjjjsjeljLastMonth = StringUtils.isNotNull(zgSummaryLastMonth.getLjGzjjjsje())?zgSummaryLastMonth.getLjGzjjjsje():BigDecimal.ZERO;
                    gzjjjsjelj = NumberUtil.add(gzjjjsjeljLastMonth, gzjjjsje);
                    BigDecimal jjbfxjljLastMonth = StringUtils.isNotNull(zgSummaryLastMonth.getLjJjbfxj())?zgSummaryLastMonth.getLjJjbfxj():BigDecimal.ZERO;
                    jjbfxjlj = NumberUtil.add(jjbfxjljLastMonth, jjbfxj);
                    BigDecimal jjbfjeljLastMonth = StringUtils.isNotNull(zgSummaryLastMonth.getLjJjbfje())?zgSummaryLastMonth.getLjJjbfje():BigDecimal.ZERO;
                    jjbfjelj = NumberUtil.add(jjbfjeljLastMonth, jjbfje);
                    BigDecimal khbzjljLastMonth = StringUtils.isNotNull(zgSummaryLastMonth.getLjKhbzj())?zgSummaryLastMonth.getLjKhbzj():BigDecimal.ZERO;
                    khbzjlj = NumberUtil.add(khbzjljLastMonth, khbzj);
                    BigDecimal kkljLastMonth = StringUtils.isNotNull(zgSummaryLastMonth.getLjKk())?zgSummaryLastMonth.getLjKk():BigDecimal.ZERO;
                    kklj = NumberUtil.add(kkljLastMonth, kk);
                }
                zgSummary.setLjFszfy(fszfylj.setScale(2,RoundingMode.HALF_UP));
                zgSummary.setLjTcjjfsje(tcjjfsjelj.setScale(2,RoundingMode.HALF_UP));
                zgSummary.setLjDbjjfsje(dbjjfsjelj.setScale(2,RoundingMode.HALF_UP));
                zgSummary.setLjGwyjjfsje(gwyjjfsjelj.setScale(2,RoundingMode.HALF_UP));
                zgSummary.setLjScjjfsje(scjjfsjelj.setScale(2,RoundingMode.HALF_UP));
                zgSummary.setLjJzjjfsje(jzjjfsjelj.setScale(2,RoundingMode.HALF_UP));
                zgSummary.setLjGzjjfsje(gzjjfsjelj.setScale(2,RoundingMode.HALF_UP));
                zgSummary.setLjXj(xjlj.setScale(2,RoundingMode.HALF_UP));
                zgSummary.setLjJsxj(jsxjlj.setScale(2,RoundingMode.HALF_UP));
                zgSummary.setLjTcjjjsje(tcjjjsjelj.setScale(2,RoundingMode.HALF_UP));
                zgSummary.setLjDbjjjsje(dbjjjsjelj.setScale(2,RoundingMode.HALF_UP));
                zgSummary.setLjGwyjjjsje(gwyjjjsjelj.setScale(2,RoundingMode.HALF_UP));
                zgSummary.setLjScjjjsje(scjjjsjelj.setScale(2,RoundingMode.HALF_UP));
                zgSummary.setLjJzjjjsje(jzjjjsjelj.setScale(2,RoundingMode.HALF_UP));
                zgSummary.setLjGzjjjsje(gzjjjsjelj.setScale(2,RoundingMode.HALF_UP));
                zgSummary.setLjJjbfxj(jjbfxjlj.setScale(2,RoundingMode.HALF_UP));
                zgSummary.setLjJjbfje(jjbfjelj.setScale(2,RoundingMode.HALF_UP));
                zgSummary.setLjKhbzj(khbzjlj.setScale(2,RoundingMode.HALF_UP));
                zgSummary.setLjKk(kklj.setScale(2,RoundingMode.HALF_UP));
                appropNoticeSummaries.add(zgSummary);
            }
        }
        return appropNoticeSummaries;
    }

    /**
     * @param map
     * @param appropNoticePreviewDTO
     * @param sysUser
     * @return java.util.List<com.jsdc.ybpt.appropNotice.entity.AppropNoticeSummary>
     * @description //TODO  处理居民汇总数据
     * @author wangxiao
     * @date 2024/5/22
     */
    private List<AppropNoticeSummary> dealJmSummary(Map<String, Object> map, AppropNoticePreviewDTO appropNoticePreviewDTO, SysUser sysUser) {
        List<AppropNoticeSummary> appropNoticeSummaries = new ArrayList<>();
        //发生数
        List<AppropNoticeOccur> appropNoticeOccurs = (List<AppropNoticeOccur>) map.get("occurData");
        //应结算
        List<AppropNoticeSettle> appropNoticeSettles = (List<AppropNoticeSettle>) map.get("settleData");
        //居民大病保险实际支付
        List<AppropNoticeJmdbbxsjzf> appropNoticeJmdbbxsjzfs = (List<AppropNoticeJmdbbxsjzf>) map.get("jmdbbxsjzfData");
        //月结算
        List<AppropNoticeMonthSettle> appropNoticeMonthSettles = (List<AppropNoticeMonthSettle>) map.get("monthSettleData");
        //DRG
        List<AppropNoticeDrg> appropNoticeDrgs = (List<AppropNoticeDrg>) map.get("drgData");
        Integer currYear = appropNoticePreviewDTO.getYear();
        Integer currMonth = appropNoticePreviewDTO.getMonth();
        Set<String> tcqSet = new HashSet<>();
        if (StringUtils.isNotEmpty(appropNoticeOccurs)) {
            Set<String> tcq = appropNoticeOccurs.stream().filter(t->t.getJstcq()!=null).map(AppropNoticeOccur::getJstcq).collect(Collectors.toSet());
            if (StringUtils.isNotEmpty(tcq)) {
                tcqSet.addAll(tcq);
            }
        }
        if (StringUtils.isNotEmpty(appropNoticeSettles)) {
            Set<String> tcq = appropNoticeSettles.stream().filter(t->t.getTcq()!=null).map(AppropNoticeSettle::getTcq).collect(Collectors.toSet());
            if (StringUtils.isNotEmpty(tcq)) {
                tcqSet.addAll(tcq);
            }
        }
        if (StringUtils.isNotEmpty(appropNoticeJmdbbxsjzfs)) {
            Set<String> tcq = appropNoticeJmdbbxsjzfs.stream().filter(t->t.getTcq()!=null).map(AppropNoticeJmdbbxsjzf::getTcq).collect(Collectors.toSet());
            if (StringUtils.isNotEmpty(tcq)) {
                tcqSet.addAll(tcq);
            }
        }
        if (StringUtils.isNotEmpty(appropNoticeMonthSettles)) {
            Set<String> tcq = appropNoticeMonthSettles.stream().filter(t->t.getTcq()!=null).map(AppropNoticeMonthSettle::getTcq).collect(Collectors.toSet());
            if (StringUtils.isNotEmpty(tcq)) {
                tcqSet.addAll(tcq);
            }
        }
        if (StringUtils.isNotEmpty(appropNoticeDrgs)) {
            Set<String> tcq = appropNoticeDrgs.stream().filter(t->t.getTcq()!=null).map(AppropNoticeDrg::getTcq).collect(Collectors.toSet());
            if (StringUtils.isNotEmpty(tcq)) {
                tcqSet.addAll(tcq);
            }
        }
        if (!CollectionUtils.isEmpty(tcqSet)) {
            //查询当前月是否有数据
            QueryWrapper<AppropNoticeSummary> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("count(*) as year");
            queryWrapper.eq("is_del","0");
            queryWrapper.eq("year", currYear);
            queryWrapper.eq("rylb", "2");
            queryWrapper.in("tcq", tcqSet);
            queryWrapper.eq("month", currMonth);
            List<AppropNoticeSummary> jmSummaryCurrentMonthList = appropNoticeSummaryService.list(queryWrapper);
            AppropNoticeSummary appropNoticeSummary = jmSummaryCurrentMonthList.get(0);
            if(appropNoticeSummary.getYear()>0){
                throw new CustomException("已存在居民汇总数据，请检查文件！");
            }
//            if(StringUtils.isNotEmpty(jmSummaryCurrentMonthList)){
//                String existTcqs = jmSummaryCurrentMonthList.stream().map(AppropNoticeSummary::getTcq).collect(Collectors.joining(","));
//                throw new CustomException("["+existTcqs+"]已存在居民汇总数据，请检查文件！");
//            }
            //查询上个月合计
            QueryWrapper<AppropNoticeSummary> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("is_del","0");
            queryWrapper2.eq("year", currYear);
            queryWrapper2.eq("rylb", "2");
            queryWrapper2.in("tcq", tcqSet);
            queryWrapper2.eq("month", currMonth - 1);
            List<AppropNoticeSummary> jmSummaryLastMonthList = appropNoticeSummaryService.list(queryWrapper2);
            for (String tcq : tcqSet) {
                AppropNoticeSummary jmSummary = new AppropNoticeSummary();
                jmSummary.setId(IdUtil.simpleUUID());
                jmSummary.setCreateTime(new Date());
                jmSummary.setCreateUser(sysUser.getId());
                jmSummary.setYear(currYear);
                jmSummary.setMonth(currMonth);
                jmSummary.setTcq(tcq);
                jmSummary.setRylb("2");
                BigDecimal fszfy = BigDecimal.ZERO;//发生总费用
                BigDecimal tcjjfsje = BigDecimal.ZERO;//统筹基金发生金额
                BigDecimal dbjjfsje = BigDecimal.ZERO;//大病基金发生金额
                BigDecimal jzjjfsje = BigDecimal.ZERO;//救助基金发生金额
                BigDecimal gjzhfsje = BigDecimal.ZERO;//共济账户发生金额
                BigDecimal dyXj = BigDecimal.ZERO;//现金
                if (StringUtils.isNotEmpty(appropNoticeOccurs)) {
                    List<AppropNoticeOccur> filterOccur = appropNoticeOccurs.stream().filter(x ->
                            x.getJstcq().equals(tcq) && x.getXzlx().equals(InsutypeEnum.CXJMJBYLBX.getInfo())
                    ).collect(Collectors.toList());
                    if (StringUtils.isNotEmpty(filterOccur)) {
                        for (AppropNoticeOccur appropNoticeOccur : filterOccur) {
                            BigDecimal ylfze = appropNoticeOccur.getYlfze();
                            fszfy = NumberUtil.add(ylfze, fszfy);
                            BigDecimal tcjj = appropNoticeOccur.getTcjj();
                            tcjjfsje = NumberUtil.add(tcjj, tcjjfsje);
                            BigDecimal jmdbjj = appropNoticeOccur.getJmdbjj();
                            dbjjfsje = NumberUtil.add(jmdbjj, dbjjfsje);
                            BigDecimal yljzjj = appropNoticeOccur.getYljzjj();
                            jzjjfsje = NumberUtil.add(yljzjj, jzjjfsje);
                            BigDecimal gjzh = appropNoticeOccur.getGjzh();
                            gjzhfsje = NumberUtil.add(gjzh, gjzhfsje);
                            BigDecimal xj = appropNoticeOccur.getXj();
                            dyXj = NumberUtil.add(xj, dyXj);
                        }
                    }
                }
                jmSummary.setDyFszfy(fszfy.setScale(2,RoundingMode.HALF_UP));
                jmSummary.setDyTcjjfsje(tcjjfsje.setScale(2,RoundingMode.HALF_UP));
                jmSummary.setDyDbjjfsje(dbjjfsje.setScale(2,RoundingMode.HALF_UP));
                jmSummary.setDyJzjjfsje(jzjjfsje.setScale(2,RoundingMode.HALF_UP));
                jmSummary.setDyGjzhfsje(gjzhfsje.setScale(2,RoundingMode.HALF_UP));
                jmSummary.setDyXj(dyXj);
                BigDecimal jsxj = BigDecimal.ZERO;//结算小计
                BigDecimal tcjjjsje = BigDecimal.ZERO;//统筹基金结算金额
                BigDecimal dbjjjsje = BigDecimal.ZERO;//大病基金结算金额
                BigDecimal jzjjjsje = BigDecimal.ZERO;//救助基金结算金额
                BigDecimal gjzhjsje = BigDecimal.ZERO;//共济账户结算金额
                if (StringUtils.isNotEmpty(appropNoticeSettles)) {
                    List<SysDict> zgSettlePolicyList = sysDictService.list(new QueryWrapper<SysDict>().eq("dict_type", "jm_settle_policy"));
                    if (StringUtils.isNotEmpty(zgSettlePolicyList)) {
                        List<String> settlePolicyNames = zgSettlePolicyList.stream().map(SysDict::getLabel).collect(Collectors.toList());
                        List<AppropNoticeSettle> filterSettle = appropNoticeSettles.stream().filter(x -> x.getTcq().equals(tcq) && settlePolicyNames.contains(x.getJszcmc())).collect(Collectors.toList());
                        if (StringUtils.isNotEmpty(filterSettle)) {
                            for (AppropNoticeSettle appropNoticeSettle : filterSettle) {
                                BigDecimal hj = appropNoticeSettle.getHj();
                                jsxj = NumberUtil.add(hj, jsxj);
                                BigDecimal jmtcjj = appropNoticeSettle.getJmtcjj();
                                tcjjjsje = NumberUtil.add(jmtcjj, tcjjjsje);
                                BigDecimal jmdbjj = appropNoticeSettle.getJmdbjj();
                                dbjjjsje = NumberUtil.add(jmdbjj, dbjjjsje);
                                BigDecimal yljzjj = appropNoticeSettle.getYljzjj();
                                jzjjjsje = NumberUtil.add(yljzjj, jzjjjsje);
                                BigDecimal gjzh = appropNoticeSettle.getGjzh();
                                gjzhjsje = NumberUtil.add(gjzh, gjzhjsje);
                            }
                        }
                    }
                }
                jmSummary.setDyJsxj(jsxj.setScale(2,RoundingMode.HALF_UP));
                jmSummary.setDyTcjjjsje(tcjjjsje.setScale(2,RoundingMode.HALF_UP));
                jmSummary.setDyDbjjjsje(dbjjjsje.setScale(2,RoundingMode.HALF_UP));
                jmSummary.setDyJzjjjsje(jzjjjsje.setScale(2,RoundingMode.HALF_UP));
                jmSummary.setDyGjzhjsje(gjzhjsje.setScale(2,RoundingMode.HALF_UP));
                BigDecimal jjbfxj = BigDecimal.ZERO;//基金拨付小计
                BigDecimal jjbfje = BigDecimal.ZERO;//基金拨付金额
                BigDecimal dbsbbfje = BigDecimal.ZERO;//大病商保拨付金额
                BigDecimal khbzj = BigDecimal.ZERO;//当月考核保证金
                BigDecimal kk = BigDecimal.ZERO;//当月扣款
                if (StringUtils.isNotEmpty(appropNoticeJmdbbxsjzfs)) {
                    List<AppropNoticeJmdbbxsjzf> filterJmdbbxsjzf = appropNoticeJmdbbxsjzfs.stream().filter(x -> x.getTcq().equals(tcq)).collect(Collectors.toList());
                    if (StringUtils.isNotEmpty(filterJmdbbxsjzf)) {
                        for (AppropNoticeJmdbbxsjzf appropNoticeJmdbbxsjzf : filterJmdbbxsjzf) {
                            BigDecimal zfje = appropNoticeJmdbbxsjzf.getZfje();
                            dbsbbfje = NumberUtil.add(zfje, dbsbbfje);
                        }
                    }
                }
                if (StringUtils.isNotEmpty(appropNoticeMonthSettles)) {
                    List<AppropNoticeMonthSettle> filterMonthSettle = appropNoticeMonthSettles.stream().filter(x -> x.getTcq().equals(tcq)).collect(Collectors.toList());
                    if (StringUtils.isNotEmpty(filterMonthSettle)) {
                        for (AppropNoticeMonthSettle appropNoticeMonthSettle : filterMonthSettle) {
                            BigDecimal jmjjbfje = appropNoticeMonthSettle.getJmjjbfje();
                            jjbfje = NumberUtil.add(jmjjbfje, jjbfje);
                            BigDecimal jmkhbzjlj = appropNoticeMonthSettle.getJmkhbzjlj();
                            khbzj = NumberUtil.add(jmkhbzjlj, khbzj);
                            BigDecimal jmbyzflj = appropNoticeMonthSettle.getJmbyzflj();
                            kk = NumberUtil.add(jmbyzflj, kk);
                        }
                    }
                }
                if (StringUtils.isNotEmpty(appropNoticeDrgs)) {
                    List<AppropNoticeDrg> filterDrg = appropNoticeDrgs.stream().filter(x -> x.getTcq().equals(tcq)).collect(Collectors.toList());
                    if (StringUtils.isNotEmpty(filterDrg)) {
                        for (AppropNoticeDrg appropNoticeDrg : filterDrg) {
                            BigDecimal jmjjbfje = appropNoticeDrg.getJmjjbfje();
                            jjbfje = NumberUtil.add(jmjjbfje, jjbfje);
                            BigDecimal jmbyzflj = appropNoticeDrg.getJmbyzflj();
                            kk = NumberUtil.add(jmbyzflj, kk);
                        }
                    }
                }
                jjbfxj = NumberUtil.sub(NumberUtil.add(jjbfje,dbsbbfje), khbzj, kk);
                jmSummary.setDyDbsbbfje(dbsbbfje.setScale(2,RoundingMode.HALF_UP));
                jmSummary.setDyJjbfxj(jjbfxj.setScale(2,RoundingMode.HALF_UP));
                jmSummary.setDyJjbfje(jjbfje.setScale(2,RoundingMode.HALF_UP));
                jmSummary.setDyKhbzj(khbzj.setScale(2,RoundingMode.HALF_UP));
                jmSummary.setDyKk(kk.setScale(2,RoundingMode.HALF_UP));
                //查询上个月合计
                AppropNoticeSummary jmSummaryLastMonth = null;
                if(StringUtils.isNotEmpty(jmSummaryLastMonthList)){
                    List<AppropNoticeSummary> collect = jmSummaryLastMonthList.stream().filter(x -> x.getTcq().equals(tcq)).collect(Collectors.toList());
                    if(StringUtils.isNotEmpty(collect)){
                        jmSummaryLastMonth = collect.get(0);
                    }
                }
                BigDecimal fszfylj = fszfy;//发生总费用累计
                BigDecimal tcjjfsjelj = tcjjfsje;//统筹基金发生金额累计
                BigDecimal dbjjfsjelj = dbjjfsje;//大病基金发生金额累计
                BigDecimal jzjjfsjelj = jzjjfsje;//救助基金发生金额累计
                BigDecimal gjzhfsjelj = gjzhfsje;//共济账户发生金额累计
                BigDecimal xjlj = dyXj;//现金累计
                BigDecimal jsxjlj = jsxj;//结算小计累计
                BigDecimal tcjjjsjelj = tcjjjsje;//统筹基金结算金额累计
                BigDecimal dbjjjsjelj = dbjjjsje;//大病基金结算金额累计
                BigDecimal jzjjjsjelj = jzjjjsje;//救助基金结算金额累计
                BigDecimal gjzhjsjelj = gjzhjsje;//共济账户结算金额累计
                BigDecimal jjbfxjlj = jjbfxj;//基金拨付小计累计
                BigDecimal jjbfjelj = jjbfje;//基金拨付金额累计
                BigDecimal dbsbbfjelj = dbsbbfje;//大病商保拨付金额累计
                BigDecimal khbzjlj = khbzj;//考核保证金累计
                BigDecimal kklj = kk;//扣款累计
                if (StringUtils.isNotNull(jmSummaryLastMonth)) {
                    BigDecimal fszfyljLastMonth = StringUtils.isNotNull(jmSummaryLastMonth.getLjFszfy())?jmSummaryLastMonth.getLjFszfy():BigDecimal.ZERO;
                    fszfylj = NumberUtil.add(fszfyljLastMonth, fszfy);
                    BigDecimal tcjjfsjeljLastMonth = StringUtils.isNotNull(jmSummaryLastMonth.getLjTcjjfsje())?jmSummaryLastMonth.getLjTcjjfsje():BigDecimal.ZERO;
                    tcjjfsjelj = NumberUtil.add(tcjjfsjeljLastMonth, tcjjfsje);
                    BigDecimal dbjjfsjeljLastMonth = StringUtils.isNotNull(jmSummaryLastMonth.getLjDbjjfsje())?jmSummaryLastMonth.getLjDbjjfsje():BigDecimal.ZERO;
                    dbjjfsjelj = NumberUtil.add(dbjjfsjeljLastMonth, dbjjfsje);
                    BigDecimal jzjjfsjeljLastMonth = StringUtils.isNotNull(jmSummaryLastMonth.getLjJzjjfsje())?jmSummaryLastMonth.getLjJzjjfsje():BigDecimal.ZERO;
                    jzjjfsjelj = NumberUtil.add(jzjjfsjeljLastMonth, jzjjfsje);
                    BigDecimal gjzhfsjeljLastMonth = StringUtils.isNotNull(jmSummaryLastMonth.getLjGjzhfsje())?jmSummaryLastMonth.getLjGjzhfsje():BigDecimal.ZERO;
                    gjzhfsjelj = NumberUtil.add(gjzhfsjeljLastMonth, gjzhfsje);
                    BigDecimal xjljLastMonth = StringUtils.isNotNull(jmSummaryLastMonth.getLjXj())?jmSummaryLastMonth.getLjXj():BigDecimal.ZERO;
                    xjlj = NumberUtil.add(xjljLastMonth, dyXj);
                    BigDecimal jsxjljLastMonth = StringUtils.isNotNull(jmSummaryLastMonth.getLjJsxj())?jmSummaryLastMonth.getLjJsxj():BigDecimal.ZERO;
                    jsxjlj = NumberUtil.add(jsxjljLastMonth, jsxj);
                    BigDecimal tcjjjsjeljLastMonth = StringUtils.isNotNull(jmSummaryLastMonth.getLjTcjjjsje())?jmSummaryLastMonth.getLjTcjjjsje():BigDecimal.ZERO;
                    tcjjjsjelj = NumberUtil.add(tcjjjsjeljLastMonth, tcjjjsje);
                    BigDecimal dbjjjsjeljLastMonth = StringUtils.isNotNull(jmSummaryLastMonth.getLjDbjjjsje())?jmSummaryLastMonth.getLjDbjjjsje():BigDecimal.ZERO;
                    dbjjjsjelj = NumberUtil.add(dbjjjsjeljLastMonth, dbjjjsje);
                    BigDecimal jzjjjsjeljLastMonth = StringUtils.isNotNull(jmSummaryLastMonth.getLjJzjjjsje())?jmSummaryLastMonth.getLjJzjjjsje():BigDecimal.ZERO;
                    jzjjjsjelj = NumberUtil.add(jzjjjsjeljLastMonth, jzjjjsje);
                    BigDecimal gjzhjsjeljLastMonth = StringUtils.isNotNull(jmSummaryLastMonth.getLjGjzhjsje())?jmSummaryLastMonth.getLjGjzhjsje():BigDecimal.ZERO;
                    gjzhjsjelj = NumberUtil.add(gjzhjsjeljLastMonth, gjzhjsje);
                    BigDecimal jjbfxjljLastMonth = StringUtils.isNotNull(jmSummaryLastMonth.getLjJjbfxj())?jmSummaryLastMonth.getLjJjbfxj():BigDecimal.ZERO;
                    jjbfxjlj = NumberUtil.add(jjbfxjljLastMonth, jjbfxj);
                    BigDecimal jjbfjeljLastMonth = StringUtils.isNotNull(jmSummaryLastMonth.getLjJjbfje())?jmSummaryLastMonth.getLjJjbfje():BigDecimal.ZERO;
                    jjbfjelj = NumberUtil.add(jjbfjeljLastMonth, jjbfje);
                    BigDecimal dbsbbfjeljLastMonth = StringUtils.isNotNull(jmSummaryLastMonth.getLjDbsbbfje())?jmSummaryLastMonth.getLjDbsbbfje():BigDecimal.ZERO;
                    dbsbbfjelj = NumberUtil.add(dbsbbfjeljLastMonth, dbsbbfje);
                    BigDecimal khbzjljLastMonth = StringUtils.isNotNull(jmSummaryLastMonth.getLjKhbzj())?jmSummaryLastMonth.getLjKhbzj():BigDecimal.ZERO;
                    khbzjlj = NumberUtil.add(khbzjljLastMonth, khbzj);
                    BigDecimal fkkljLastMonth = StringUtils.isNotNull(jmSummaryLastMonth.getLjKk())?jmSummaryLastMonth.getLjKk():BigDecimal.ZERO;
                    kklj = NumberUtil.add(fkkljLastMonth, kk);
                }
                jmSummary.setLjFszfy(fszfylj.setScale(2,RoundingMode.HALF_UP));
                jmSummary.setLjTcjjfsje(tcjjfsjelj.setScale(2,RoundingMode.HALF_UP));
                jmSummary.setLjDbjjfsje(dbjjfsjelj.setScale(2,RoundingMode.HALF_UP));
                jmSummary.setLjJzjjfsje(jzjjfsjelj.setScale(2,RoundingMode.HALF_UP));
                jmSummary.setLjGjzhfsje(gjzhfsjelj.setScale(2,RoundingMode.HALF_UP));
                jmSummary.setLjXj(xjlj.setScale(2,RoundingMode.HALF_UP));
                jmSummary.setLjJsxj(jsxjlj.setScale(2,RoundingMode.HALF_UP));
                jmSummary.setLjTcjjjsje(tcjjjsjelj.setScale(2,RoundingMode.HALF_UP));
                jmSummary.setLjDbjjjsje(dbjjjsjelj.setScale(2,RoundingMode.HALF_UP));
                jmSummary.setLjJzjjjsje(jzjjjsjelj.setScale(2,RoundingMode.HALF_UP));
                jmSummary.setLjGjzhjsje(gjzhjsjelj.setScale(2,RoundingMode.HALF_UP));
                jmSummary.setLjJjbfxj(jjbfxjlj.setScale(2,RoundingMode.HALF_UP));
                jmSummary.setLjJjbfje(jjbfjelj.setScale(2,RoundingMode.HALF_UP));
                jmSummary.setLjDbsbbfje(dbsbbfjelj.setScale(2,RoundingMode.HALF_UP));
                jmSummary.setLjKhbzj(khbzjlj.setScale(2,RoundingMode.HALF_UP));
                jmSummary.setLjKk(kklj.setScale(2,RoundingMode.HALF_UP));
                appropNoticeSummaries.add(jmSummary);
            }
        }
        return appropNoticeSummaries;
    }

    /**
     * @param map
     * @param appropNoticePreviewDTO
     * @param sysUser
     * @return java.util.List<com.jsdc.ybpt.appropNotice.entity.AppropNoticeDataDetail>
     * @description //TODO  处理职工数据明细
     * @author wangxiao
     * @date 2024/5/22
     */
    private List<AppropNoticeDataDetail> dealZgDataDetail(Map<String, Object> map, AppropNoticePreviewDTO appropNoticePreviewDTO, SysUser sysUser,Set<String> orgCodeSet,List<List<String>> orgCodeLists) {

        List<AppropNoticeDataDetail> appropNoticeDataDetails = new ArrayList<>();
        //发生数
        List<AppropNoticeOccur> appropNoticeOccurs = (List<AppropNoticeOccur>) map.get("occurData");
        //应结算
        List<AppropNoticeSettle> appropNoticeSettles = (List<AppropNoticeSettle>) map.get("settleData");
        //居民大病保险实际支付
        List<AppropNoticeJmdbbxsjzf> appropNoticeJmdbbxsjzfs = (List<AppropNoticeJmdbbxsjzf>) map.get("jmdbbxsjzfData");
        //月结算
        List<AppropNoticeMonthSettle> appropNoticeMonthSettles = (List<AppropNoticeMonthSettle>) map.get("monthSettleData");
        //DRG
        List<AppropNoticeDrg> appropNoticeDrgs = (List<AppropNoticeDrg>) map.get("drgData");
        Integer currYear = appropNoticePreviewDTO.getYear();
        Integer currMonth = appropNoticePreviewDTO.getMonth();
//        Set<String> orgCodeSet = new HashSet<>();
//        if (StringUtils.isNotEmpty(appropNoticeOccurs)) {
//            Set<String> orgCode = appropNoticeOccurs.stream().map(AppropNoticeOccur::getOrgCode).collect(Collectors.toSet());
//            if (StringUtils.isNotEmpty(orgCode)) {
//                orgCodeSet.addAll(orgCode);
//            }
//        }
//        if (StringUtils.isNotEmpty(appropNoticeSettles)) {
//            Set<String> orgCode = appropNoticeSettles.stream().map(AppropNoticeSettle::getOrgCode).collect(Collectors.toSet());
//            if (StringUtils.isNotEmpty(orgCode)) {
//                orgCodeSet.addAll(orgCode);
//            }
//        }
//        if (StringUtils.isNotEmpty(appropNoticeJmdbbxsjzfs)) {
//            Set<String> orgCode = appropNoticeJmdbbxsjzfs.stream().map(AppropNoticeJmdbbxsjzf::getOrgCode).collect(Collectors.toSet());
//            if (StringUtils.isNotEmpty(orgCode)) {
//                orgCodeSet.addAll(orgCode);
//            }
//        }
//        if (StringUtils.isNotEmpty(appropNoticeMonthSettles)) {
//            Set<String> orgCode = appropNoticeMonthSettles.stream().map(AppropNoticeMonthSettle::getOrgCode).collect(Collectors.toSet());
//            if (StringUtils.isNotEmpty(orgCode)) {
//                orgCodeSet.addAll(orgCode);
//            }
//        }
//        if (StringUtils.isNotEmpty(appropNoticeDrgs)) {
//            Set<String> orgCode = appropNoticeDrgs.stream().map(AppropNoticeDrg::getOrgCode).collect(Collectors.toSet());
//            if (StringUtils.isNotEmpty(orgCode)) {
//                orgCodeSet.addAll(orgCode);
//            }
//        }
        //log.info("dealZgDataDetail orgCodeSet:{}",JSON.toJSONString(orgCodeSet));
        //if (StringUtils.isNotEmpty(orgCodeLists)) {
        if (!CollectionUtils.isEmpty(orgCodeSet)) {
//            List<List<String>> orgCodeLists = new ArrayList<>();
//            List<String> orgCodeList = new ArrayList<>(orgCodeSet);
//            int fromIndex = 0;
//            int toIndex = 1000;
//            while(true){
//                if (toIndex > orgCodeList.size()) {
//                    toIndex = orgCodeList.size();
//                }
//                List<String> subList = orgCodeList.subList(fromIndex, toIndex);
//                orgCodeLists.add(subList);
//                if(toIndex==orgCodeList.size()){
//                    break;
//                }
//                fromIndex = fromIndex+toIndex;
//                toIndex = toIndex+1000;
//            }
            //查询当前月是否有数据
            QueryWrapper<AppropNoticeDataDetail> queryWrapper = new QueryWrapper<>();
            //queryWrapper.select("count(*) as year");
            queryWrapper.select("nvl(wm_concat(ORG_CODE),'empty') as orgCode");
            queryWrapper.eq("is_del","0");
            queryWrapper.eq("year", currYear);
            queryWrapper.eq("month", currMonth);
            queryWrapper.and(wq->{
                int i = 0;
                for(List<String> orfCodes:orgCodeLists){
                    wq.in("org_code",orfCodes);
                    if(i==orgCodeLists.size())
                        break;
                    wq.or();
                }
            });
            queryWrapper.le("rownum",100);
            //queryWrapper.in("org_code", orgCodeSet);
            List<AppropNoticeDataDetail> zgDataDetailCurrMonth = appropNoticeDataDetailService.list(queryWrapper);
            AppropNoticeDataDetail zgDataDetail = zgDataDetailCurrMonth.get(0);
            if(!zgDataDetail.getOrgCode().equals("empty")){
                throw new CustomException("["+zgDataDetail.getOrgCode()+"]已存在明细数据，请检查文件！");
            }
//            if(zgDataDetail.getYear()>0){
//                throw new CustomException("已存在明细数据，请检查文件！");
//            }
//            if(StringUtils.isNotEmpty(zgDataDetailCurrMonth)){
//                String existOrgNames = zgDataDetailCurrMonth.stream().map(AppropNoticeDataDetail::getOrgName).collect(Collectors.joining(","));
//                throw new CustomException("["+existOrgNames+"]已存在明细数据，请检查文件！");
//            }
            //查询上个月合计
            QueryWrapper<AppropNoticeDataDetail> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("is_del","0");
            queryWrapper2.eq("year", currYear);
            queryWrapper2.eq("month", currMonth - 1);
            queryWrapper2.and(wq->{
                int i = 0;
                for(List<String> orfCodes:orgCodeLists){
                    wq.in("org_code",orfCodes);
                    if(i==orgCodeLists.size())
                        break;
                    wq.or();
                }
            });
            //queryWrapper2.in("org_code", orgCodeSet);
            List<AppropNoticeDataDetail> zgDataDetailLastMonthList = appropNoticeDataDetailService.list(queryWrapper2);

            Map<String,Map<String,String>> zgOccurMap = dealZgOccur(appropNoticeOccurs);
            Map<String,Map<String,String>> zgSettleMap = dealZgSettle(appropNoticeSettles);
            Map<String,Map<String,String>> zgMonthSettleMap = dealZgMonthSettle(appropNoticeMonthSettles);
            Map<String,Map<String,String>> zgDrgMap = dealZgDrg(appropNoticeDrgs);

            for (String orgCode : orgCodeSet) {
                AppropNoticeDataDetail detail = new AppropNoticeDataDetail();
                detail.setId(IdUtil.simpleUUID());
                detail.setCreateTime(new Date());
                detail.setCreateUser(sysUser.getId());
                detail.setYear(currYear);
                detail.setMonth(currMonth);
                String orgName = null;
                String tcq = null;
                String jb = null;
//                BigDecimal fszfy = BigDecimal.ZERO;//发生总费用
//                BigDecimal tcjjfsje = BigDecimal.ZERO;//统筹基金发生金额
//                BigDecimal dbjjfsje = BigDecimal.ZERO;//大病基金发生金额
//                BigDecimal gwyjjfsje = BigDecimal.ZERO;//公务员基金发生金额
//                BigDecimal scjjfsje = BigDecimal.ZERO;//伤残基金发生金额
//                BigDecimal jzjjfsje = BigDecimal.ZERO;//救助基金发生金额
//                BigDecimal gzjjfsje = BigDecimal.ZERO;//个账基金发生金额
//                BigDecimal dyXj = BigDecimal.ZERO;//现金
                Map<String,String> occurMap = zgOccurMap.get(orgCode);
                BigDecimal fszfy = occurMap!=null?new BigDecimal(MapUtil.getStr(occurMap,"fszfy")):BigDecimal.ZERO;//发生总费用
                BigDecimal tcjjfsje = occurMap!=null?new BigDecimal(MapUtil.getStr(occurMap,"tcjjfsje")):BigDecimal.ZERO;//统筹基金发生金额
                BigDecimal dbjjfsje = occurMap!=null?new BigDecimal(MapUtil.getStr(occurMap,"dbjjfsje")):BigDecimal.ZERO;//大病基金发生金额
                BigDecimal gwyjjfsje = occurMap!=null?new BigDecimal(MapUtil.getStr(occurMap,"gwyjjfsje")):BigDecimal.ZERO;//公务员基金发生金额
                BigDecimal scjjfsje = occurMap!=null?new BigDecimal(MapUtil.getStr(occurMap,"scjjfsje")):BigDecimal.ZERO;//伤残基金发生金额
                BigDecimal jzjjfsje = occurMap!=null?new BigDecimal(MapUtil.getStr(occurMap,"jzjjfsje")):BigDecimal.ZERO;//救助基金发生金额
                BigDecimal gzjjfsje = occurMap!=null?new BigDecimal(MapUtil.getStr(occurMap,"gzjjfsje")):BigDecimal.ZERO;//个账基金发生金额
                BigDecimal dyXj = occurMap!=null?new BigDecimal(MapUtil.getStr(occurMap,"dyXj")):BigDecimal.ZERO;//现金
                orgName = occurMap!=null?MapUtil.getStr(occurMap,"orgName"):null;
                tcq = occurMap!=null?MapUtil.getStr(occurMap,"tcq"):null;
//                if (StringUtils.isNotEmpty(appropNoticeOccurs)) {

//                    List<AppropNoticeOccur> filterOccur = appropNoticeOccurs.stream().filter(x ->
//                            x.getOrgCode().equals(orgCode) && (x.getXzlx().equals(InsutypeEnum.ZGJBYLBX.getInfo()) || x.getXzlx().equals(InsutypeEnum.SYBX.getInfo()))
//                    ).collect(Collectors.toList());
//                    if (StringUtils.isNotEmpty(filterOccur)) {
//                        orgName = filterOccur.get(0).getOrgName();
//                        for (AppropNoticeOccur appropNoticeOccur : filterOccur) {
//                            BigDecimal ylfze = StringUtils.isNull(appropNoticeOccur.getYlfze()) ? BigDecimal.ZERO : appropNoticeOccur.getYlfze();
//                            fszfy = NumberUtil.add(ylfze, fszfy);
//                            BigDecimal tcjj = StringUtils.isNull(appropNoticeOccur.getTcjj()) ? BigDecimal.ZERO : appropNoticeOccur.getTcjj();
//                            BigDecimal syjj = StringUtils.isNull(appropNoticeOccur.getSyjj()) ? BigDecimal.ZERO : appropNoticeOccur.getSyjj();
//                            tcjjfsje = NumberUtil.add(tcjj, syjj, tcjjfsje);
//                            BigDecimal zgdbjj = StringUtils.isNull(appropNoticeOccur.getZgdbjj()) ? BigDecimal.ZERO : appropNoticeOccur.getZgdbjj();
//                            dbjjfsje = NumberUtil.add(zgdbjj, dbjjfsje);
//                            BigDecimal gwybzjj = StringUtils.isNull(appropNoticeOccur.getGwybzjj()) ? BigDecimal.ZERO : appropNoticeOccur.getGwybzjj();
//                            gwyjjfsje = NumberUtil.add(gwybzjj, gwyjjfsje);
//                            BigDecimal cjjrbzjj = StringUtils.isNull(appropNoticeOccur.getCjjrbzjj()) ? BigDecimal.ZERO : appropNoticeOccur.getCjjrbzjj();
//                            scjjfsje = NumberUtil.add(cjjrbzjj, scjjfsje);
//                            BigDecimal yljzjj = StringUtils.isNull(appropNoticeOccur.getYljzjj()) ? BigDecimal.ZERO : appropNoticeOccur.getYljzjj();
//                            jzjjfsje = NumberUtil.add(yljzjj, jzjjfsje);
//                            BigDecimal grzh = StringUtils.isNull(appropNoticeOccur.getGrzh()) ? BigDecimal.ZERO : appropNoticeOccur.getGrzh();
//                            BigDecimal gjzh = StringUtils.isNull(appropNoticeOccur.getGjzh()) ? BigDecimal.ZERO : appropNoticeOccur.getGjzh();
//                            gzjjfsje = NumberUtil.add(grzh, gjzh, gzjjfsje);
//                            BigDecimal xj = StringUtils.isNull(appropNoticeOccur.getXj()) ? BigDecimal.ZERO : appropNoticeOccur.getXj();
//                            dyXj = NumberUtil.add(xj, dyXj);
//                        }
//                    }

//                }
                detail.setZgDyFszfy(fszfy.setScale(2,RoundingMode.HALF_UP));
                detail.setZgDyTcjjfsje(tcjjfsje.setScale(2,RoundingMode.HALF_UP));
                detail.setZgDyDbjjfsje(dbjjfsje.setScale(2,RoundingMode.HALF_UP));
                detail.setZgDyGwyjjfsje(gwyjjfsje.setScale(2,RoundingMode.HALF_UP));
                detail.setZgDyScjjfsje(scjjfsje.setScale(2,RoundingMode.HALF_UP));
                detail.setZgDyJzjjfsje(jzjjfsje.setScale(2,RoundingMode.HALF_UP));
                detail.setZgDyGzjjfsje(gzjjfsje.setScale(2,RoundingMode.HALF_UP));
                detail.setZgDyXj(dyXj.setScale(2,RoundingMode.HALF_UP));
//                BigDecimal jsxj = BigDecimal.ZERO;//结算小计
//                BigDecimal tcjjjsje = BigDecimal.ZERO;//统筹基金结算金额
//                BigDecimal dbjjjsje = BigDecimal.ZERO;//大病基金结算金额
//                BigDecimal gwyjjjsje = BigDecimal.ZERO;//公务员基金结算金额
//                BigDecimal scjjjsje = BigDecimal.ZERO;//伤残基金结算金额
//                BigDecimal jzjjjsje = BigDecimal.ZERO;//救助基金结算金额
//                BigDecimal gzjjjsje = BigDecimal.ZERO;//个账基金结算金额
                Map<String,String> settleMap = zgSettleMap.get(orgCode);
                BigDecimal jsxj = settleMap!=null?new BigDecimal(MapUtil.getStr(settleMap,"jsxj")):BigDecimal.ZERO;//结算小计
                BigDecimal tcjjjsje = settleMap!=null?new BigDecimal(MapUtil.getStr(settleMap,"tcjjjsje")):BigDecimal.ZERO;//统筹基金结算金额
                BigDecimal dbjjjsje = settleMap!=null?new BigDecimal(MapUtil.getStr(settleMap,"dbjjjsje")):BigDecimal.ZERO;//大病基金结算金额
                BigDecimal gwyjjjsje = settleMap!=null?new BigDecimal(MapUtil.getStr(settleMap,"gwyjjjsje")):BigDecimal.ZERO;//公务员基金结算金额
                BigDecimal scjjjsje = settleMap!=null?new BigDecimal(MapUtil.getStr(settleMap,"scjjjsje")):BigDecimal.ZERO;//伤残基金结算金额
                BigDecimal jzjjjsje = settleMap!=null?new BigDecimal(MapUtil.getStr(settleMap,"jzjjjsje")):BigDecimal.ZERO;//救助基金结算金额
                BigDecimal gzjjjsje = settleMap!=null?new BigDecimal(MapUtil.getStr(settleMap,"gzjjjsje")):BigDecimal.ZERO;//个账基金结算金额
                if (StringUtils.isEmpty(orgName)) {
                    orgName = settleMap!=null?MapUtil.getStr(settleMap,"orgName"):null;
                }
                if (StringUtils.isEmpty(tcq)) {
                    tcq = settleMap!=null?MapUtil.getStr(settleMap,"tcq"):null;
                }
//                if (StringUtils.isNotEmpty(appropNoticeSettles)) {
//                    if (StringUtils.isNotEmpty(zgSettlePolicyList)) {
//                        List<String> settlePolicyNames = zgSettlePolicyList.stream().map(SysDict::getLabel).collect(Collectors.toList());
//                        List<AppropNoticeSettle> filterSettle = appropNoticeSettles.stream().filter(x -> x.getOrgCode().equals(orgCode) && settlePolicyNames.contains(x.getJszcmc())).collect(Collectors.toList());
//                        if (StringUtils.isNotEmpty(filterSettle)) {
//                            if (StringUtils.isBlank(orgName)) {
//                                orgName = filterSettle.get(0).getOrgName();
//                            }
//                            if (StringUtils.isBlank(tcq)) {
//                                tcq = filterSettle.get(0).getTcq();
//                            }
//                            for (AppropNoticeSettle appropNoticeSettle : filterSettle) {
//                                BigDecimal hj = StringUtils.isNull(appropNoticeSettle.getHj()) ? BigDecimal.ZERO : appropNoticeSettle.getHj();
//                                jsxj = NumberUtil.add(hj, jsxj);
//                                BigDecimal zgtcjj = StringUtils.isNull(appropNoticeSettle.getZgtcjj()) ? BigDecimal.ZERO : appropNoticeSettle.getZgtcjj();
//                                BigDecimal syjj = StringUtils.isNull(appropNoticeSettle.getSyjj()) ? BigDecimal.ZERO : appropNoticeSettle.getSyjj();
//                                tcjjjsje = NumberUtil.add(zgtcjj, syjj, tcjjjsje);
//                                BigDecimal zgdbjj = StringUtils.isNull(appropNoticeSettle.getZgdbjj()) ? BigDecimal.ZERO : appropNoticeSettle.getZgdbjj();
//                                dbjjjsje = NumberUtil.add(zgdbjj, dbjjjsje);
//                                BigDecimal gwybzjj = StringUtils.isNull(appropNoticeSettle.getGwybzjj()) ? BigDecimal.ZERO : appropNoticeSettle.getGwybzjj();
//                                gwyjjjsje = NumberUtil.add(gwybzjj, gwyjjjsje);
//                                BigDecimal cjjrbzjj = StringUtils.isNull(appropNoticeSettle.getCjjrbzjj()) ? BigDecimal.ZERO : appropNoticeSettle.getCjjrbzjj();
//                                scjjjsje = NumberUtil.add(cjjrbzjj, scjjjsje);
//                                BigDecimal yljzjj = StringUtils.isNull(appropNoticeSettle.getYljzjj()) ? BigDecimal.ZERO : appropNoticeSettle.getYljzjj();
//                                jzjjjsje = NumberUtil.add(yljzjj, jzjjjsje);
//                                BigDecimal grzh = StringUtils.isNull(appropNoticeSettle.getGrzh()) ? BigDecimal.ZERO : appropNoticeSettle.getGrzh();
//                                BigDecimal gjzh = StringUtils.isNull(appropNoticeSettle.getGjzh()) ? BigDecimal.ZERO : appropNoticeSettle.getGjzh();
//                                gzjjjsje = NumberUtil.add(grzh, gjzh, gzjjjsje);
//                            }
//                        }
//                    }
//                }
                detail.setZgDyJsxj(jsxj.setScale(2,RoundingMode.HALF_UP));
                detail.setZgDyTcjjjsje(tcjjjsje.setScale(2,RoundingMode.HALF_UP));
                detail.setZgDyDbjjjsje(dbjjjsje.setScale(2,RoundingMode.HALF_UP));
                detail.setZgDyGwyjjjsje(gwyjjjsje.setScale(2,RoundingMode.HALF_UP));
                detail.setZgDyScjjjsje(scjjjsje.setScale(2,RoundingMode.HALF_UP));
                detail.setZgDyJzjjjsje(jzjjjsje.setScale(2,RoundingMode.HALF_UP));
                detail.setZgDyGzjjjsje(gzjjjsje.setScale(2,RoundingMode.HALF_UP));
//                BigDecimal jjbfxj = BigDecimal.ZERO;//基金拨付小计
//                BigDecimal jjbfje = BigDecimal.ZERO;//基金拨付金额
//                BigDecimal khbzj = BigDecimal.ZERO;//当月考核保证金
//                BigDecimal kk = BigDecimal.ZERO;//当月扣款
                Map<String,String> monthSettleMap = zgMonthSettleMap.get(orgCode);
                BigDecimal jjbfxj = monthSettleMap!=null?new BigDecimal(MapUtil.getStr(monthSettleMap,"jjbfxj")):BigDecimal.ZERO;//基金拨付小计
                BigDecimal jjbfje = monthSettleMap!=null?new BigDecimal(MapUtil.getStr(monthSettleMap,"jjbfje")):BigDecimal.ZERO;//基金拨付金额
                BigDecimal khbzj = monthSettleMap!=null?new BigDecimal(MapUtil.getStr(monthSettleMap,"khbzj")):BigDecimal.ZERO;//当月考核保证金
                BigDecimal kk = monthSettleMap!=null?new BigDecimal(MapUtil.getStr(monthSettleMap,"kk")):BigDecimal.ZERO;//当月扣款
                if (StringUtils.isEmpty(orgName)) {
                    orgName = monthSettleMap!=null?MapUtil.getStr(monthSettleMap,"orgName"):null;
                }
                if (StringUtils.isEmpty(tcq)) {
                    tcq = monthSettleMap!=null?MapUtil.getStr(monthSettleMap,"tcq"):null;
                }
                if (StringUtils.isEmpty(jb)) {
                    jb = monthSettleMap!=null?MapUtil.getStr(monthSettleMap,"jb"):null;
                }
//                if (StringUtils.isNotEmpty(appropNoticeMonthSettles)) {
//                    List<AppropNoticeMonthSettle> filterMonthSettle = appropNoticeMonthSettles.stream().filter(x -> x.getOrgCode().equals(orgCode)).collect(Collectors.toList());
//                    if (StringUtils.isNotEmpty(filterMonthSettle)) {
//                        if (StringUtils.isBlank(orgName)) {
//                            orgName = filterMonthSettle.get(0).getOrgName();
//                        }
//                        if (StringUtils.isBlank(tcq)) {
//                            tcq = filterMonthSettle.get(0).getTcq();
//                        }
//                        if (StringUtils.isBlank(jb)) {
//                            jb = filterMonthSettle.get(0).getJb();
//                        }
//                        for (AppropNoticeMonthSettle appropNoticeMonthSettle : filterMonthSettle) {
//                            BigDecimal zgjjbfje = StringUtils.isNull(appropNoticeMonthSettle.getZgjjbfje()) ? BigDecimal.ZERO : appropNoticeMonthSettle.getZgjjbfje();
//                            jjbfje = NumberUtil.add(zgjjbfje, jjbfje);
//                            BigDecimal zgkhbzjlj = StringUtils.isNull(appropNoticeMonthSettle.getZgkhbzjlj()) ? BigDecimal.ZERO : appropNoticeMonthSettle.getZgkhbzjlj();
//                            khbzj = NumberUtil.add(zgkhbzjlj, khbzj);
//                            BigDecimal zgbyzflj = StringUtils.isNull(appropNoticeMonthSettle.getZgbyzflj()) ? BigDecimal.ZERO : appropNoticeMonthSettle.getZgbyzflj();
//                            kk = NumberUtil.add(zgbyzflj, kk);
//                        }
//                    }
//                }

                Map<String,String> monthOrgMap = zgDrgMap.get(orgCode);
                if (StringUtils.isEmpty(orgName)) {
                    orgName = monthOrgMap!=null?MapUtil.getStr(monthOrgMap,"orgName"):null;
                }
                if (StringUtils.isEmpty(tcq)) {
                    tcq = monthOrgMap!=null?MapUtil.getStr(monthOrgMap,"tcq"):null;
                }
                if (StringUtils.isEmpty(jb)) {
                    jb = monthOrgMap!=null?MapUtil.getStr(monthOrgMap,"jb"):null;
                }
                jjbfje = NumberUtil.add(monthOrgMap!=null?new BigDecimal(MapUtil.getStr(monthOrgMap,"jjbfje")):BigDecimal.ZERO, jjbfje);
                kk = NumberUtil.add(monthOrgMap!=null?new BigDecimal(MapUtil.getStr(monthOrgMap,"kk")):BigDecimal.ZERO, kk);
//                if (StringUtils.isNotEmpty(appropNoticeDrgs)) {
//                    List<AppropNoticeDrg> filterDrg = appropNoticeDrgs.stream().filter(x -> x.getOrgCode().equals(orgCode)).collect(Collectors.toList());
//                    if (StringUtils.isNotEmpty(filterDrg)) {
//                        if (StringUtils.isBlank(orgName)) {
//                            orgName = filterDrg.get(0).getOrgName();
//                        }
//                        if (StringUtils.isBlank(tcq)) {
//                            tcq = filterDrg.get(0).getTcq();
//                        }
//                        if (StringUtils.isBlank(jb)) {
//                            jb = filterDrg.get(0).getJb();
//                        }
//                        for (AppropNoticeDrg appropNoticeDrg : filterDrg) {
//                            BigDecimal zgjjbfje = StringUtils.isNull(appropNoticeDrg.getZgjjbfje()) ? BigDecimal.ZERO : appropNoticeDrg.getZgjjbfje();
//                            jjbfje = NumberUtil.add(zgjjbfje, jjbfje);
//                            BigDecimal zgbyzflj = StringUtils.isNull(appropNoticeDrg.getZgbyzflj()) ? BigDecimal.ZERO : appropNoticeDrg.getZgbyzflj();
//                            kk = NumberUtil.add(zgbyzflj, kk);
//                        }
//                    }
//                    jjbfje = NumberUtil.add(monthOrgMap!=null?new BigDecimal(MapUtil.getStr(monthSettleMap,"jjbfje")):BigDecimal.ZERO, jjbfje);
//                    kk = NumberUtil.add(monthOrgMap!=null?new BigDecimal(MapUtil.getStr(monthSettleMap,"kk")):BigDecimal.ZERO, kk);
//                }
                jjbfxj = NumberUtil.sub(jjbfje, khbzj, kk);
                detail.setZgDyJjbfxj(jjbfxj.setScale(2,RoundingMode.HALF_UP));
                detail.setZgDyJjbfje(jjbfje.setScale(2,RoundingMode.HALF_UP));
                detail.setZgDyKhbzj(khbzj.setScale(2,RoundingMode.HALF_UP));
                detail.setZgDyKk(kk.setScale(2,RoundingMode.HALF_UP));
                detail.setOrgCode(orgCode);
                detail.setOrgName(orgName);
                detail.setTcq(tcq);
                detail.setJb(jb);
                //查询上个月
                AppropNoticeDataDetail zgDataDetailLastMonth = null;
                if(StringUtils.isNotEmpty(zgDataDetailLastMonthList)){
                    List<AppropNoticeDataDetail> collect = zgDataDetailLastMonthList.stream().filter(x -> x.getOrgCode().equals(orgCode)).collect(Collectors.toList());
                    if(StringUtils.isNotEmpty(collect)){
                        zgDataDetailLastMonth = collect.get(0);
                    }
                }
                BigDecimal fszfylj = fszfy;//发生总费用累计
                BigDecimal tcjjfsjelj = tcjjfsje;//统筹基金发生金额累计
                BigDecimal dbjjfsjelj = dbjjfsje;//大病基金发生金额累计
                BigDecimal gwyjjfsjelj = gwyjjfsje;//公务员基金发生金额累计
                BigDecimal scjjfsjelj = scjjfsje;//伤残基金发生金额累计
                BigDecimal jzjjfsjelj = jzjjfsje;//救助基金发生金额累计
                BigDecimal gzjjfsjelj = gzjjfsje;//个账基金发生金额累计
                BigDecimal xjlj = dyXj;//现金累计
                BigDecimal jsxjlj = jsxj;//结算小计累计
                BigDecimal tcjjjsjelj =  tcjjjsje;//统筹基金结算金额累计
                BigDecimal dbjjjsjelj = dbjjjsje;//大病基金结算金额累计
                BigDecimal gwyjjjsjelj = gwyjjjsje;//公务员基金结算金额累计
                BigDecimal scjjjsjelj = scjjjsje;//伤残基金结算金额累计
                BigDecimal jzjjjsjelj = jzjjjsje;//救助基金结算金额累计
                BigDecimal gzjjjsjelj = gzjjjsje;//个账基金结算金额累计
                BigDecimal jjbfxjlj = jjbfxj;//基金拨付小计累计
                BigDecimal jjbfjelj = jjbfje;//基金拨付金额累计
                BigDecimal khbzjlj = khbzj;//考核保证金累计
                BigDecimal kklj = kk;//扣款累计
                if (StringUtils.isNotNull(zgDataDetailLastMonth)) {
                    BigDecimal fszfyljLastMonth = StringUtils.isNotNull(zgDataDetailLastMonth.getZgLjFszfy())?zgDataDetailLastMonth.getZgLjFszfy():BigDecimal.ZERO;
                    fszfylj = NumberUtil.add(fszfyljLastMonth, fszfy);
                    BigDecimal tcjjfsjeljLastMonth = StringUtils.isNotNull(zgDataDetailLastMonth.getZgLjTcjjfsje())?zgDataDetailLastMonth.getZgLjTcjjfsje():BigDecimal.ZERO;
                    tcjjfsjelj = NumberUtil.add(tcjjfsjeljLastMonth, tcjjfsje);
                    BigDecimal dbjjfsjeljLastMonth = StringUtils.isNotNull(zgDataDetailLastMonth.getZgLjDbjjfsje())?zgDataDetailLastMonth.getZgLjDbjjfsje():BigDecimal.ZERO;
                    dbjjfsjelj = NumberUtil.add(dbjjfsjeljLastMonth, dbjjfsje);
                    BigDecimal gwyjjfsjeljLastMonth = StringUtils.isNotNull(zgDataDetailLastMonth.getZgLjGwyjjfsje())?zgDataDetailLastMonth.getZgLjGwyjjfsje():BigDecimal.ZERO;
                    gwyjjfsjelj = NumberUtil.add(gwyjjfsjeljLastMonth, gwyjjfsje);
                    BigDecimal scjjfsjeljLastMonth = StringUtils.isNotNull(zgDataDetailLastMonth.getZgLjScjjfsje())?zgDataDetailLastMonth.getZgLjScjjfsje():BigDecimal.ZERO;
                    scjjfsjelj = NumberUtil.add(scjjfsjeljLastMonth, scjjfsje);
                    BigDecimal jzjjfsjeljLastMonth = StringUtils.isNotNull(zgDataDetailLastMonth.getZgLjJzjjfsje())?zgDataDetailLastMonth.getZgLjJzjjfsje():BigDecimal.ZERO;
                    jzjjfsjelj = NumberUtil.add(jzjjfsjeljLastMonth, jzjjfsje);
                    BigDecimal gzjjfsjeljLastMonth = StringUtils.isNotNull(zgDataDetailLastMonth.getZgLjGzjjfsje())?zgDataDetailLastMonth.getZgLjGzjjfsje():BigDecimal.ZERO;
                    gzjjfsjelj = NumberUtil.add(gzjjfsjeljLastMonth, gzjjfsje);
                    BigDecimal xjljLastMonth = StringUtils.isNotNull(zgDataDetailLastMonth.getZgLjXj())?zgDataDetailLastMonth.getZgLjXj():BigDecimal.ZERO;
                    xjlj = NumberUtil.add(xjljLastMonth, dyXj);
                    BigDecimal jsxjljLastMonth = StringUtils.isNotNull(zgDataDetailLastMonth.getZgLjJsxj())?zgDataDetailLastMonth.getZgLjJsxj():BigDecimal.ZERO;
                    jsxjlj = NumberUtil.add(jsxjljLastMonth, jsxj);
                    BigDecimal tcjjjsjeljLastMonth = StringUtils.isNotNull(zgDataDetailLastMonth.getZgLjTcjjjsje())?zgDataDetailLastMonth.getZgLjTcjjjsje():BigDecimal.ZERO;
                    tcjjjsjelj = NumberUtil.add(tcjjjsjeljLastMonth, tcjjjsje);
                    BigDecimal dbjjjsjeljLastMonth = StringUtils.isNotNull(zgDataDetailLastMonth.getZgLjDbjjjsje())?zgDataDetailLastMonth.getZgLjDbjjjsje():BigDecimal.ZERO;
                    dbjjjsjelj = NumberUtil.add(dbjjjsjeljLastMonth, dbjjjsje);
                    BigDecimal gwyjjjsjeljLastMonth = StringUtils.isNotNull(zgDataDetailLastMonth.getZgLjGwyjjjsje())?zgDataDetailLastMonth.getZgLjGwyjjjsje():BigDecimal.ZERO;
                    gwyjjjsjelj = NumberUtil.add(gwyjjjsjeljLastMonth, gwyjjjsje);
                    BigDecimal scjjjsjeljLastMonth = StringUtils.isNotNull(zgDataDetailLastMonth.getZgLjScjjjsje())?zgDataDetailLastMonth.getZgLjScjjjsje():BigDecimal.ZERO;
                    scjjjsjelj = NumberUtil.add(scjjjsjeljLastMonth, scjjjsje);
                    BigDecimal jzjjjsjeljLastMonth = StringUtils.isNotNull(zgDataDetailLastMonth.getZgLjJzjjjsje())?zgDataDetailLastMonth.getZgLjJzjjjsje():BigDecimal.ZERO;
                    jzjjjsjelj = NumberUtil.add(jzjjjsjeljLastMonth, jzjjjsje);
                    BigDecimal gzjjjsjeljLastMonth = StringUtils.isNotNull(zgDataDetailLastMonth.getZgLjGzjjjsje())?zgDataDetailLastMonth.getZgLjGzjjjsje():BigDecimal.ZERO;
                    gzjjjsjelj = NumberUtil.add(gzjjjsjeljLastMonth, gzjjjsje);
                    BigDecimal jjbfxjljLastMonth = StringUtils.isNotNull(zgDataDetailLastMonth.getZgLjJjbfxj())?zgDataDetailLastMonth.getZgLjJjbfxj():BigDecimal.ZERO;
                    jjbfxjlj = NumberUtil.add(jjbfxjljLastMonth, jjbfxj);
                    BigDecimal jjbfjeljLastMonth = StringUtils.isNotNull(zgDataDetailLastMonth.getZgLjJjbfje())?zgDataDetailLastMonth.getZgLjJjbfje():BigDecimal.ZERO;
                    jjbfjelj = NumberUtil.add(jjbfjeljLastMonth, jjbfje);
                    BigDecimal khbzjljLastMonth = StringUtils.isNotNull(zgDataDetailLastMonth.getZgLjKhbzj())?zgDataDetailLastMonth.getZgLjKhbzj():BigDecimal.ZERO;
                    khbzjlj = NumberUtil.add(khbzjljLastMonth, khbzj);
                    BigDecimal kkljLastMonth = StringUtils.isNotNull(zgDataDetailLastMonth.getZgLjKk())?zgDataDetailLastMonth.getZgLjKk():BigDecimal.ZERO;
                    kklj = NumberUtil.add(kkljLastMonth, kk);
                }
                detail.setZgLjFszfy(fszfylj.setScale(2,RoundingMode.HALF_UP));
                detail.setZgLjTcjjfsje(tcjjfsjelj.setScale(2,RoundingMode.HALF_UP));
                detail.setZgLjDbjjfsje(dbjjfsjelj.setScale(2,RoundingMode.HALF_UP));
                detail.setZgLjGwyjjfsje(gwyjjfsjelj.setScale(2,RoundingMode.HALF_UP));
                detail.setZgLjScjjfsje(scjjfsjelj.setScale(2,RoundingMode.HALF_UP));
                detail.setZgLjJzjjfsje(jzjjfsjelj.setScale(2,RoundingMode.HALF_UP));
                detail.setZgLjGzjjfsje(gzjjfsjelj.setScale(2,RoundingMode.HALF_UP));
                detail.setZgLjXj(xjlj.setScale(2,RoundingMode.HALF_UP));
                detail.setZgLjJsxj(jsxjlj.setScale(2,RoundingMode.HALF_UP));
                detail.setZgLjTcjjjsje(tcjjjsjelj.setScale(2,RoundingMode.HALF_UP));
                detail.setZgLjDbjjjsje(dbjjjsjelj.setScale(2,RoundingMode.HALF_UP));
                detail.setZgLjGwyjjjsje(gwyjjjsjelj.setScale(2,RoundingMode.HALF_UP));
                detail.setZgLjScjjjsje(scjjjsjelj.setScale(2,RoundingMode.HALF_UP));
                detail.setZgLjJzjjjsje(jzjjjsjelj.setScale(2,RoundingMode.HALF_UP));
                detail.setZgLjGzjjjsje(gzjjjsjelj.setScale(2,RoundingMode.HALF_UP));
                detail.setZgLjJjbfxj(jjbfxjlj.setScale(2,RoundingMode.HALF_UP));
                detail.setZgLjJjbfje(jjbfjelj.setScale(2,RoundingMode.HALF_UP));
                detail.setZgLjKhbzj(khbzjlj.setScale(2,RoundingMode.HALF_UP));
                detail.setZgLjKk(kklj.setScale(2,RoundingMode.HALF_UP));
                appropNoticeDataDetails.add(detail);
            }
        }
        return appropNoticeDataDetails;
    }

    private Map<String,Map<String,String>> dealZgOccur(List<AppropNoticeOccur> appropNoticeOccurs) {
         List<AppropNoticeOccur> filterOccur = appropNoticeOccurs.stream().filter(x ->
                (x.getXzlx().equals(InsutypeEnum.ZGJBYLBX.getInfo()) || x.getXzlx().equals(InsutypeEnum.SYBX.getInfo()))
                        &&x.getOrgCode()!=null
                        &&StringUtils.isNotNull(x.getYlfze())
                        &&StringUtils.isNotNull(x.getTcjj())
                        &&StringUtils.isNotNull(x.getSyjj())
                        &&StringUtils.isNotNull(x.getZgdbjj())
                        &&StringUtils.isNotNull(x.getGwybzjj())
                        &&StringUtils.isNotNull(x.getCjjrbzjj())
                        &&StringUtils.isNotNull(x.getYljzjj())
                        &&StringUtils.isNotNull(x.getGrzh())
                        &&StringUtils.isNotNull(x.getGjzh())
                        &&StringUtils.isNotNull(x.getXj())
        ).collect(Collectors.toList());
        Map<String,List<AppropNoticeOccur>> map = filterOccur.stream().collect(Collectors.groupingBy(AppropNoticeOccur::getOrgCode));
        Map<String,Map<String,String>> result = new HashMap<>();
        map.forEach((orgCode,occurs)->{
            BigDecimal fszfy = BigDecimal.ZERO;//发生总费用
            BigDecimal tcjjfsje = BigDecimal.ZERO;//统筹基金发生金额
            BigDecimal dbjjfsje = BigDecimal.ZERO;//大病基金发生金额
            BigDecimal gwyjjfsje = BigDecimal.ZERO;//公务员基金发生金额
            BigDecimal scjjfsje = BigDecimal.ZERO;//伤残基金发生金额
            BigDecimal jzjjfsje = BigDecimal.ZERO;//救助基金发生金额
            BigDecimal gzjjfsje = BigDecimal.ZERO;//个账基金发生金额
            BigDecimal dyXj = BigDecimal.ZERO;//现金
            if (StringUtils.isNotEmpty(occurs)) {
                for (AppropNoticeOccur appropNoticeOccur : occurs) {
                    BigDecimal ylfze = StringUtils.isNull(appropNoticeOccur.getYlfze()) ? BigDecimal.ZERO : appropNoticeOccur.getYlfze();
                    fszfy = NumberUtil.add(ylfze, fszfy);
                    BigDecimal tcjj = StringUtils.isNull(appropNoticeOccur.getTcjj()) ? BigDecimal.ZERO : appropNoticeOccur.getTcjj();
                    BigDecimal syjj = StringUtils.isNull(appropNoticeOccur.getSyjj()) ? BigDecimal.ZERO : appropNoticeOccur.getSyjj();
                    tcjjfsje = NumberUtil.add(tcjj, syjj, tcjjfsje);
                    BigDecimal zgdbjj = StringUtils.isNull(appropNoticeOccur.getZgdbjj()) ? BigDecimal.ZERO : appropNoticeOccur.getZgdbjj();
                    dbjjfsje = NumberUtil.add(zgdbjj, dbjjfsje);
                    BigDecimal gwybzjj = StringUtils.isNull(appropNoticeOccur.getGwybzjj()) ? BigDecimal.ZERO : appropNoticeOccur.getGwybzjj();
                    gwyjjfsje = NumberUtil.add(gwybzjj, gwyjjfsje);
                    BigDecimal cjjrbzjj = StringUtils.isNull(appropNoticeOccur.getCjjrbzjj()) ? BigDecimal.ZERO : appropNoticeOccur.getCjjrbzjj();
                    scjjfsje = NumberUtil.add(cjjrbzjj, scjjfsje);
                    BigDecimal yljzjj = StringUtils.isNull(appropNoticeOccur.getYljzjj()) ? BigDecimal.ZERO : appropNoticeOccur.getYljzjj();
                    jzjjfsje = NumberUtil.add(yljzjj, jzjjfsje);
                    BigDecimal grzh = StringUtils.isNull(appropNoticeOccur.getGrzh()) ? BigDecimal.ZERO : appropNoticeOccur.getGrzh();
                    BigDecimal gjzh = StringUtils.isNull(appropNoticeOccur.getGjzh()) ? BigDecimal.ZERO : appropNoticeOccur.getGjzh();
                    gzjjfsje = NumberUtil.add(grzh, gjzh, gzjjfsje);
                    BigDecimal xj = StringUtils.isNull(appropNoticeOccur.getXj()) ? BigDecimal.ZERO : appropNoticeOccur.getXj();
                    dyXj = NumberUtil.add(xj, dyXj);
                }
            }
            Map<String,String> occurMap = new HashMap<>(16);
            occurMap.put("fszfy",fszfy.toString());
            occurMap.put("tcjjfsje",tcjjfsje.toString());
            occurMap.put("dbjjfsje",dbjjfsje.toString());
            occurMap.put("gwyjjfsje",gwyjjfsje.toString());
            occurMap.put("scjjfsje",scjjfsje.toString());
            occurMap.put("jzjjfsje",jzjjfsje.toString());
            occurMap.put("gzjjfsje",gzjjfsje.toString());
            occurMap.put("dyXj",dyXj.toString());
            occurMap.put("orgName",occurs.get(0).getOrgName());
            occurMap.put("tcq",occurs.get(0).getJstcq());
            result.put(orgCode,occurMap);
        });
        return result;
    }

    private Map<String,Map<String,String>> dealJmOccur(List<AppropNoticeOccur> appropNoticeOccurs) {
        List<AppropNoticeOccur> filterOccur = appropNoticeOccurs.stream().filter(x ->
                x.getXzlx().equals(InsutypeEnum.CXJMJBYLBX.getInfo())
                        &&x.getOrgCode()!=null
                        &&StringUtils.isNotNull(x.getYlfze())
                        &&StringUtils.isNotNull(x.getTcjj())
                        &&StringUtils.isNotNull(x.getJmdbjj())
                        &&StringUtils.isNotNull(x.getYljzjj())
                        &&StringUtils.isNotNull(x.getGjzh())
                        &&StringUtils.isNotNull(x.getXj())
        ).collect(Collectors.toList());
        Map<String,List<AppropNoticeOccur>> map = filterOccur.stream().collect(Collectors.groupingBy(AppropNoticeOccur::getOrgCode));
        Map<String,Map<String,String>> result = new HashMap<>();
        map.forEach((orgCode,occurs)->{
            BigDecimal fszfy = BigDecimal.ZERO;//发生总费用
            BigDecimal tcjjfsje = BigDecimal.ZERO;//统筹基金发生金额
            BigDecimal dbjjfsje = BigDecimal.ZERO;//大病基金发生金额
            BigDecimal jzjjfsje = BigDecimal.ZERO;//救助基金发生金额
            BigDecimal gjzhfsje = BigDecimal.ZERO;//共济账户发生金额
            BigDecimal dyXj = BigDecimal.ZERO;//现金
            if (StringUtils.isNotEmpty(occurs)) {
                for (AppropNoticeOccur appropNoticeOccur : occurs) {
                    BigDecimal ylfze = StringUtils.isNull(appropNoticeOccur.getYlfze()) ? BigDecimal.ZERO : appropNoticeOccur.getYlfze();
                    fszfy = NumberUtil.add(ylfze, fszfy);
                    BigDecimal tcjj = StringUtils.isNull(appropNoticeOccur.getTcjj()) ? BigDecimal.ZERO : appropNoticeOccur.getTcjj();
                    tcjjfsje = NumberUtil.add(tcjj, tcjjfsje);
                    BigDecimal jmdbjj = StringUtils.isNull(appropNoticeOccur.getJmdbjj()) ? BigDecimal.ZERO : appropNoticeOccur.getJmdbjj();
                    dbjjfsje = NumberUtil.add(jmdbjj, dbjjfsje);
                    BigDecimal yljzjj = StringUtils.isNull(appropNoticeOccur.getYljzjj()) ? BigDecimal.ZERO : appropNoticeOccur.getYljzjj();
                    jzjjfsje = NumberUtil.add(yljzjj, jzjjfsje);
                    BigDecimal gjzh = StringUtils.isNull(appropNoticeOccur.getGjzh()) ? BigDecimal.ZERO : appropNoticeOccur.getGjzh();
                    gjzhfsje = NumberUtil.add(gjzh, gjzhfsje);
                    BigDecimal xj = StringUtils.isNull(appropNoticeOccur.getXj()) ? BigDecimal.ZERO : appropNoticeOccur.getXj();
                    dyXj = NumberUtil.add(xj, dyXj);
                }
            }
            Map<String,String> occurMap = new HashMap<>(16);
            occurMap.put("fszfy",fszfy.toString());
            occurMap.put("tcjjfsje",tcjjfsje.toString());
            occurMap.put("dbjjfsje",dbjjfsje.toString());
            occurMap.put("jzjjfsje",jzjjfsje.toString());
            occurMap.put("gjzhfsje",gjzhfsje.toString());
            occurMap.put("dyXj",dyXj.toString());
            occurMap.put("orgName",occurs.get(0).getOrgName());
            occurMap.put("tcq",occurs.get(0).getJstcq());
            result.put(orgCode,occurMap);
        });
        return result;
    }

    private Map<String,Map<String,String>> dealZgSettle(List<AppropNoticeSettle> appropNoticeSettles) {
        List<SysDict> zgSettlePolicyList = sysDictService.list(new QueryWrapper<SysDict>().eq("dict_type", "zg_settle_policy"));
        List<String> settlePolicyNames = zgSettlePolicyList.stream().map(SysDict::getLabel).collect(Collectors.toList());
        List<AppropNoticeSettle> filterSettle = appropNoticeSettles.stream().filter(x -> settlePolicyNames.contains(x.getJszcmc())
                &&x.getOrgCode()!=null
                &&StringUtils.isNotNull(x.getHj())
                &&StringUtils.isNotNull(x.getZgtcjj())
                &&StringUtils.isNotNull(x.getSyjj())
                &&StringUtils.isNotNull(x.getZgdbjj())
                &&StringUtils.isNotNull(x.getGwybzjj())
                &&StringUtils.isNotNull(x.getCjjrbzjj())
                &&StringUtils.isNotNull(x.getYljzjj())
                &&StringUtils.isNotNull(x.getGrzh())
                &&StringUtils.isNotNull(x.getGjzh())
        ).collect(Collectors.toList());
        Map<String,List<AppropNoticeSettle>> map = filterSettle.stream().collect(Collectors.groupingBy(AppropNoticeSettle::getOrgCode));
        Map<String,Map<String,String>> result = new HashMap<>();
        map.forEach((orgCode,settles)->{
            BigDecimal jsxj = BigDecimal.ZERO;//结算小计
            BigDecimal tcjjjsje = BigDecimal.ZERO;//统筹基金结算金额
            BigDecimal dbjjjsje = BigDecimal.ZERO;//大病基金结算金额
            BigDecimal gwyjjjsje = BigDecimal.ZERO;//公务员基金结算金额
            BigDecimal scjjjsje = BigDecimal.ZERO;//伤残基金结算金额
            BigDecimal jzjjjsje = BigDecimal.ZERO;//救助基金结算金额
            BigDecimal gzjjjsje = BigDecimal.ZERO;//个账基金结算金额
            for (AppropNoticeSettle appropNoticeSettle : settles) {
                BigDecimal hj = StringUtils.isNull(appropNoticeSettle.getHj()) ? BigDecimal.ZERO : appropNoticeSettle.getHj();
                jsxj = NumberUtil.add(hj, jsxj);
                BigDecimal zgtcjj = StringUtils.isNull(appropNoticeSettle.getZgtcjj()) ? BigDecimal.ZERO : appropNoticeSettle.getZgtcjj();
                BigDecimal syjj = StringUtils.isNull(appropNoticeSettle.getSyjj()) ? BigDecimal.ZERO : appropNoticeSettle.getSyjj();
                tcjjjsje = NumberUtil.add(zgtcjj, syjj, tcjjjsje);
                BigDecimal zgdbjj = StringUtils.isNull(appropNoticeSettle.getZgdbjj()) ? BigDecimal.ZERO : appropNoticeSettle.getZgdbjj();
                dbjjjsje = NumberUtil.add(zgdbjj, dbjjjsje);
                BigDecimal gwybzjj = StringUtils.isNull(appropNoticeSettle.getGwybzjj()) ? BigDecimal.ZERO : appropNoticeSettle.getGwybzjj();
                gwyjjjsje = NumberUtil.add(gwybzjj, gwyjjjsje);
                BigDecimal cjjrbzjj = StringUtils.isNull(appropNoticeSettle.getCjjrbzjj()) ? BigDecimal.ZERO : appropNoticeSettle.getCjjrbzjj();
                scjjjsje = NumberUtil.add(cjjrbzjj, scjjjsje);
                BigDecimal yljzjj = StringUtils.isNull(appropNoticeSettle.getYljzjj()) ? BigDecimal.ZERO : appropNoticeSettle.getYljzjj();
                jzjjjsje = NumberUtil.add(yljzjj, jzjjjsje);
                BigDecimal grzh = StringUtils.isNull(appropNoticeSettle.getGrzh()) ? BigDecimal.ZERO : appropNoticeSettle.getGrzh();
                BigDecimal gjzh = StringUtils.isNull(appropNoticeSettle.getGjzh()) ? BigDecimal.ZERO : appropNoticeSettle.getGjzh();
                gzjjjsje = NumberUtil.add(grzh, gjzh, gzjjjsje);
            }
            Map<String,String> settleMap = new HashMap<>(16);
            settleMap.put("jsxj",jsxj.toString());
            settleMap.put("tcjjjsje",tcjjjsje.toString());
            settleMap.put("dbjjjsje",dbjjjsje.toString());
            settleMap.put("gwyjjjsje",gwyjjjsje.toString());
            settleMap.put("scjjjsje",scjjjsje.toString());
            settleMap.put("jzjjjsje",jzjjjsje.toString());
            settleMap.put("gzjjjsje",gzjjjsje.toString());
            settleMap.put("orgName",settles.get(0).getOrgName());
            settleMap.put("tcq",settles.get(0).getTcq());
            result.put(orgCode,settleMap);
        });
        return result;
    }

    private Map<String,Map<String,String>> dealJmSettle(List<AppropNoticeSettle> appropNoticeSettles) {
        List<SysDict> zgSettlePolicyList = sysDictService.list(new QueryWrapper<SysDict>().eq("dict_type", "jm_settle_policy"));
        List<String> settlePolicyNames = zgSettlePolicyList.stream().map(SysDict::getLabel).collect(Collectors.toList());
        List<AppropNoticeSettle> filterSettle = appropNoticeSettles.stream().filter(x -> settlePolicyNames.contains(x.getJszcmc())
                &&x.getOrgCode()!=null
                &&StringUtils.isNotNull(x.getHj())
                &&StringUtils.isNotNull(x.getJmtcjj())
                &&StringUtils.isNotNull(x.getJmdbjj())
                &&StringUtils.isNotNull(x.getYljzjj())
                &&StringUtils.isNotNull(x.getGjzh())
        ).collect(Collectors.toList());
        Map<String,List<AppropNoticeSettle>> map = filterSettle.stream().collect(Collectors.groupingBy(AppropNoticeSettle::getOrgCode));
        Map<String,Map<String,String>> result = new HashMap<>();
        map.forEach((orgCode,settles)->{
            BigDecimal jsxj = BigDecimal.ZERO;//结算小计
            BigDecimal tcjjjsje = BigDecimal.ZERO;//统筹基金结算金额
            BigDecimal dbjjjsje = BigDecimal.ZERO;//大病基金结算金额
            BigDecimal jzjjjsje = BigDecimal.ZERO;//救助基金结算金额
            BigDecimal gjzhjsje = BigDecimal.ZERO;//共济账户结算金额
            for (AppropNoticeSettle appropNoticeSettle : settles) {
                BigDecimal hj = StringUtils.isNull(appropNoticeSettle.getHj()) ? BigDecimal.ZERO : appropNoticeSettle.getHj();
                jsxj = NumberUtil.add(hj, jsxj);
                BigDecimal jmtcjj = StringUtils.isNull(appropNoticeSettle.getJmtcjj()) ? BigDecimal.ZERO : appropNoticeSettle.getJmtcjj();
                tcjjjsje = NumberUtil.add(jmtcjj, tcjjjsje);
                BigDecimal jmdbjj = StringUtils.isNull(appropNoticeSettle.getJmdbjj()) ? BigDecimal.ZERO : appropNoticeSettle.getJmdbjj();
                dbjjjsje = NumberUtil.add(jmdbjj, dbjjjsje);
                BigDecimal yljzjj = StringUtils.isNull(appropNoticeSettle.getYljzjj()) ? BigDecimal.ZERO : appropNoticeSettle.getYljzjj();
                jzjjjsje = NumberUtil.add(yljzjj, jzjjjsje);
                BigDecimal gjzh = StringUtils.isNull(appropNoticeSettle.getGjzh()) ? BigDecimal.ZERO : appropNoticeSettle.getGjzh();
                gjzhjsje = NumberUtil.add(gjzh, gjzhjsje);
            }
            Map<String,String> settleMap = new HashMap<>(16);
            settleMap.put("jsxj",jsxj.toString());
            settleMap.put("tcjjjsje",tcjjjsje.toString());
            settleMap.put("dbjjjsje",dbjjjsje.toString());
            settleMap.put("jzjjjsje",jzjjjsje.toString());
            settleMap.put("gjzhjsje",gjzhjsje.toString());
            settleMap.put("orgName",settles.get(0).getOrgName());
            settleMap.put("tcq",settles.get(0).getTcq());
            result.put(orgCode,settleMap);
        });
        return result;
    }

    private Map<String,Map<String,String>> dealZgMonthSettle(List<AppropNoticeMonthSettle> appropNoticeMonthSettles) {
        Map<String,List<AppropNoticeMonthSettle>> map = appropNoticeMonthSettles.stream().filter(x->x.getOrgCode()!=null
                &&StringUtils.isNotNull(x.getZgjjbfje())
                &&StringUtils.isNotNull(x.getZgkhbzjlj())
                &&StringUtils.isNotNull(x.getZgbyzflj())
        ).collect(Collectors.groupingBy(AppropNoticeMonthSettle::getOrgCode));
        Map<String,Map<String,String>> result = new HashMap<>();
        map.forEach((orgCode,monthSettles)->{

            BigDecimal jjbfxj = BigDecimal.ZERO;//基金拨付小计
            BigDecimal jjbfje = BigDecimal.ZERO;//基金拨付金额
            BigDecimal khbzj = BigDecimal.ZERO;//当月考核保证金
            BigDecimal kk = BigDecimal.ZERO;//当月扣款

            for (AppropNoticeMonthSettle appropNoticeMonthSettle : monthSettles) {
                BigDecimal zgjjbfje = StringUtils.isNull(appropNoticeMonthSettle.getZgjjbfje()) ? BigDecimal.ZERO : appropNoticeMonthSettle.getZgjjbfje();
                jjbfje = NumberUtil.add(zgjjbfje, jjbfje);
                BigDecimal zgkhbzjlj = StringUtils.isNull(appropNoticeMonthSettle.getZgkhbzjlj()) ? BigDecimal.ZERO : appropNoticeMonthSettle.getZgkhbzjlj();
                khbzj = NumberUtil.add(zgkhbzjlj, khbzj);
                BigDecimal zgbyzflj = StringUtils.isNull(appropNoticeMonthSettle.getZgbyzflj()) ? BigDecimal.ZERO : appropNoticeMonthSettle.getZgbyzflj();
                kk = NumberUtil.add(zgbyzflj, kk);
            }

            Map<String,String> monthSettleMap = new HashMap<>(16);
            monthSettleMap.put("jjbfxj",jjbfxj.toString());
            monthSettleMap.put("jjbfje",jjbfje.toString());
            monthSettleMap.put("khbzj",khbzj.toString());
            monthSettleMap.put("kk",kk.toString());
            monthSettleMap.put("orgName",monthSettles.get(0).getOrgName());
            monthSettleMap.put("tcq",monthSettles.get(0).getTcq());
            monthSettleMap.put("jb",monthSettles.get(0).getJb());
            result.put(orgCode,monthSettleMap);
        });
        return result;
    }

    private Map<String,Map<String,String>> dealJmdbbxsjzfs(List<AppropNoticeJmdbbxsjzf> appropNoticeJmdbbxsjzfs) {
        Map<String,List<AppropNoticeJmdbbxsjzf>> map = appropNoticeJmdbbxsjzfs.stream().filter(x->x.getOrgCode()!=null
                &&StringUtils.isNotNull(x.getZfje())
        ).collect(Collectors.groupingBy(AppropNoticeJmdbbxsjzf::getOrgCode));
        Map<String,Map<String,String>> result = new HashMap<>();
        map.forEach((orgCode,jmdbbxsjzfs)->{
            //BigDecimal jjbfxj = BigDecimal.ZERO;//基金拨付小计
            //BigDecimal jjbfje = BigDecimal.ZERO;//基金拨付金额
            BigDecimal dbsbbfje = BigDecimal.ZERO;//大病商保拨付金额
            //BigDecimal khbzj = BigDecimal.ZERO;//当月考核保证金
            //BigDecimal kk = BigDecimal.ZERO;//当月扣款
            for (AppropNoticeJmdbbxsjzf appropNoticeJmdbbxsjzf : jmdbbxsjzfs) {
                BigDecimal zfje = StringUtils.isNull(appropNoticeJmdbbxsjzf.getZfje()) ? BigDecimal.ZERO : appropNoticeJmdbbxsjzf.getZfje();
                dbsbbfje = NumberUtil.add(zfje, dbsbbfje);
            }
            Map<String,String> monthSettleMap = new HashMap<>(16);
            //monthSettleMap.put("jjbfxj",jjbfxj.toString());
            //monthSettleMap.put("jjbfje",jjbfje.toString());
            monthSettleMap.put("dbsbbfje",dbsbbfje.toString());
            //.put("khbzj",khbzj.toString());
            //monthSettleMap.put("kk",kk.toString());
            monthSettleMap.put("orgName",jmdbbxsjzfs.get(0).getOrgName());
            monthSettleMap.put("tcq",jmdbbxsjzfs.get(0).getTcq());
            result.put(orgCode,monthSettleMap);
        });
        return result;
    }

    private Map<String,Map<String,String>> dealJmMonthSettle(List<AppropNoticeMonthSettle> appropNoticeMonthSettles) {
        Map<String,List<AppropNoticeMonthSettle>> map = appropNoticeMonthSettles.stream().filter(x->x.getOrgCode()!=null
                &&StringUtils.isNotNull(x.getJmjjbfje())
                &&StringUtils.isNotNull(x.getJmkhbzjlj())
                &&StringUtils.isNotNull(x.getJmbyzflj())
        ).collect(Collectors.groupingBy(AppropNoticeMonthSettle::getOrgCode));
        Map<String,Map<String,String>> result = new HashMap<>();
        map.forEach((orgCode,monthSettles)->{

            BigDecimal jjbfje = BigDecimal.ZERO;//基金拨付金额
            BigDecimal khbzj = BigDecimal.ZERO;//当月考核保证金
            BigDecimal kk = BigDecimal.ZERO;//当月扣款

            for (AppropNoticeMonthSettle appropNoticeMonthSettle : monthSettles) {
                BigDecimal jmjjbfje = StringUtils.isNull(appropNoticeMonthSettle.getJmjjbfje()) ? BigDecimal.ZERO : appropNoticeMonthSettle.getJmjjbfje();
                jjbfje = NumberUtil.add(jmjjbfje, jjbfje);
                BigDecimal jmkhbzjlj = StringUtils.isNull(appropNoticeMonthSettle.getJmkhbzjlj()) ? BigDecimal.ZERO : appropNoticeMonthSettle.getJmkhbzjlj();
                khbzj = NumberUtil.add(jmkhbzjlj, khbzj);
                BigDecimal jmbyzflj = StringUtils.isNull(appropNoticeMonthSettle.getJmbyzflj()) ? BigDecimal.ZERO : appropNoticeMonthSettle.getJmbyzflj();
                kk = NumberUtil.add(jmbyzflj, kk);
            }
            Map<String,String> monthSettleMap = new HashMap<>(16);
            monthSettleMap.put("jjbfje",jjbfje.toString());
            monthSettleMap.put("khbzj",khbzj.toString());
            monthSettleMap.put("kk",kk.toString());
            monthSettleMap.put("orgName",monthSettles.get(0).getOrgName());
            monthSettleMap.put("tcq",monthSettles.get(0).getTcq());
            monthSettleMap.put("jb",monthSettles.get(0).getJb());
            result.put(orgCode,monthSettleMap);
        });
        return result;
    }
    private Map<String,Map<String,String>> dealZgDrg(List<AppropNoticeDrg> appropNoticeDrgs) {
        Map<String,List<AppropNoticeDrg>> map = appropNoticeDrgs.stream().filter(x->x.getOrgCode()!=null
                &&StringUtils.isNotNull(x.getZgjjbfje())
                &&StringUtils.isNotNull(x.getZgbyzflj())
        ).collect(Collectors.groupingBy(AppropNoticeDrg::getOrgCode));
        Map<String,Map<String,String>> result = new HashMap<>();
        map.forEach((orgCode,drgs)->{
            BigDecimal jjbfxj = BigDecimal.ZERO;//基金拨付小计
            BigDecimal jjbfje = BigDecimal.ZERO;//基金拨付金额
            BigDecimal khbzj = BigDecimal.ZERO;//当月考核保证金
            BigDecimal kk = BigDecimal.ZERO;//当月扣款
            for (AppropNoticeDrg appropNoticeDrg : drgs) {
                BigDecimal zgjjbfje = StringUtils.isNull(appropNoticeDrg.getZgjjbfje()) ? BigDecimal.ZERO : appropNoticeDrg.getZgjjbfje();
                jjbfje = NumberUtil.add(zgjjbfje, jjbfje);
                BigDecimal zgbyzflj = StringUtils.isNull(appropNoticeDrg.getZgbyzflj()) ? BigDecimal.ZERO : appropNoticeDrg.getZgbyzflj();
                kk = NumberUtil.add(zgbyzflj, kk);
            }
            Map<String,String> orgMap = new HashMap<>(16);
            orgMap.put("jjbfxj",jjbfxj.toString());
            orgMap.put("jjbfje",jjbfje.toString());
            orgMap.put("khbzj",khbzj.toString());
            orgMap.put("kk",kk.toString());
            orgMap.put("orgName",drgs.get(0).getOrgName());
            orgMap.put("tcq",drgs.get(0).getTcq());
            orgMap.put("jb",drgs.get(0).getJb());
            result.put(orgCode,orgMap);
        });
        return result;
    }

    private Map<String,Map<String,String>> dealJmDrg(List<AppropNoticeDrg> appropNoticeDrgs) {
        Map<String,List<AppropNoticeDrg>> map = appropNoticeDrgs.stream().filter(x->x.getOrgCode()!=null
                &&StringUtils.isNotNull(x.getJmjjbfje())
                &&StringUtils.isNotNull(x.getJmbyzflj())
        ).collect(Collectors.groupingBy(AppropNoticeDrg::getOrgCode));
        Map<String,Map<String,String>> result = new HashMap<>();
        map.forEach((orgCode,drgs)->{
            BigDecimal jjbfje = BigDecimal.ZERO;//基金拨付金额
            BigDecimal kk = BigDecimal.ZERO;//当月扣款
            for (AppropNoticeDrg appropNoticeDrg : drgs) {
                BigDecimal jmjjbfje = StringUtils.isNull(appropNoticeDrg.getJmjjbfje()) ? BigDecimal.ZERO : appropNoticeDrg.getJmjjbfje();
                jjbfje = NumberUtil.add(jmjjbfje, jjbfje);
                BigDecimal jmbyzflj = StringUtils.isNull(appropNoticeDrg.getJmbyzflj()) ? BigDecimal.ZERO : appropNoticeDrg.getJmbyzflj();
                kk = NumberUtil.add(jmbyzflj, kk);
            }
            Map<String,String> orgMap = new HashMap<>(16);
            orgMap.put("jjbfje",jjbfje.toString());
            orgMap.put("kk",kk.toString());
            orgMap.put("orgName",drgs.get(0).getOrgName());
            orgMap.put("tcq",drgs.get(0).getTcq());
            orgMap.put("jb",drgs.get(0).getJb());
            result.put(orgCode,orgMap);
        });
        return result;
    }

        /**
         * @param map
         * @param appropNoticePreviewDTO
         * @param sysUser
         * @return java.util.List<com.jsdc.ybpt.appropNotice.entity.AppropNoticeDataDetail>
         * @description //TODO  处理居民数据明细
         * @author wangxiao
         * @date 2024/5/22
         */
    private List<AppropNoticeDataDetail> dealJmDataDetail(Map<String, Object> map, AppropNoticePreviewDTO appropNoticePreviewDTO, SysUser sysUser,Set<String> orgCodeSet,List<List<String>> orgCodeLists) {
        List<AppropNoticeDataDetail> appropNoticeDataDetails = new ArrayList<>();
        //发生数
        List<AppropNoticeOccur> appropNoticeOccurs = (List<AppropNoticeOccur>) map.get("occurData");
        //应结算
        List<AppropNoticeSettle> appropNoticeSettles = (List<AppropNoticeSettle>) map.get("settleData");
        //居民大病保险实际支付
        List<AppropNoticeJmdbbxsjzf> appropNoticeJmdbbxsjzfs = (List<AppropNoticeJmdbbxsjzf>) map.get("jmdbbxsjzfData");
        //月结算
        List<AppropNoticeMonthSettle> appropNoticeMonthSettles = (List<AppropNoticeMonthSettle>) map.get("monthSettleData");
        //DRG
        List<AppropNoticeDrg> appropNoticeDrgs = (List<AppropNoticeDrg>) map.get("drgData");
        Integer currYear = appropNoticePreviewDTO.getYear();
        Integer currMonth = appropNoticePreviewDTO.getMonth();
//        Set<String> orgCodeSet = new HashSet<>();
//        if (StringUtils.isNotEmpty(appropNoticeOccurs)) {
//            Set<String> orgCode = appropNoticeOccurs.stream().map(AppropNoticeOccur::getOrgCode).collect(Collectors.toSet());
//            if (StringUtils.isNotEmpty(orgCode)) {
//                orgCodeSet.addAll(orgCode);
//            }
//        }
//        if (StringUtils.isNotEmpty(appropNoticeSettles)) {
//            Set<String> orgCode = appropNoticeSettles.stream().map(AppropNoticeSettle::getOrgCode).collect(Collectors.toSet());
//            if (StringUtils.isNotEmpty(orgCode)) {
//                orgCodeSet.addAll(orgCode);
//            }
//        }
//        if (StringUtils.isNotEmpty(appropNoticeJmdbbxsjzfs)) {
//            Set<String> orgCode = appropNoticeJmdbbxsjzfs.stream().map(AppropNoticeJmdbbxsjzf::getOrgCode).collect(Collectors.toSet());
//            if (StringUtils.isNotEmpty(orgCode)) {
//                orgCodeSet.addAll(orgCode);
//            }
//        }
//        if (StringUtils.isNotEmpty(appropNoticeMonthSettles)) {
//            Set<String> orgCode = appropNoticeMonthSettles.stream().map(AppropNoticeMonthSettle::getOrgCode).collect(Collectors.toSet());
//            if (StringUtils.isNotEmpty(orgCode)) {
//                orgCodeSet.addAll(orgCode);
//            }
//        }
//        if (StringUtils.isNotEmpty(appropNoticeDrgs)) {
//            Set<String> orgCode = appropNoticeDrgs.stream().map(AppropNoticeDrg::getOrgCode).collect(Collectors.toSet());
//            if (StringUtils.isNotEmpty(orgCode)) {
//                orgCodeSet.addAll(orgCode);
//            }
//        }
        //if (StringUtils.isNotEmpty(orgCodeLists)) {
        if(!CollectionUtils.isEmpty(orgCodeSet)) {
            //查询当前月是否有数据
            QueryWrapper<AppropNoticeDataDetail> queryWrapper = new QueryWrapper<>();
            //queryWrapper.select("count(*) as year");
            queryWrapper.select("nvl(wm_concat(ORG_CODE),'empty') as orgCode");
            queryWrapper.eq("is_del","0");
            queryWrapper.eq("year", currYear);
            queryWrapper.eq("month", currMonth);
            queryWrapper.and(wq->{
                int i = 0;
                for(List<String> orfCodes:orgCodeLists){
                    wq.in("org_code",orfCodes);
                    if(i==orgCodeLists.size())
                        break;
                    wq.or();
                }
            });
            //queryWrapper.in("org_code", orgCodeSet);
            queryWrapper.le("rownum",100);
            List<AppropNoticeDataDetail> jmDataDetailCurrMonth = appropNoticeDataDetailService.list(queryWrapper);
            AppropNoticeDataDetail jmDataDetail = jmDataDetailCurrMonth.get(0);
            if(!jmDataDetail.getOrgCode().equals("empty")){
                throw new CustomException("["+jmDataDetail.getOrgCode()+"]已存在明细数据，请检查文件！");
            }
//            if(jmDataDetail.getYear()>0){
//                throw new CustomException("已存在明细数据，请检查文件！");
//            }
//            if(StringUtils.isNotEmpty(jmDataDetailCurrMonth)){
//                String existOrgNames = jmDataDetailCurrMonth.stream().map(AppropNoticeDataDetail::getOrgName).collect(Collectors.joining(","));
//                throw new CustomException("["+existOrgNames+"]已存在明细数据，请检查文件！");
//            }
            //查询上个月合计
            QueryWrapper<AppropNoticeDataDetail> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("is_del","0");
            queryWrapper2.eq("year", currYear);
            queryWrapper2.eq("month", currMonth - 1);
            queryWrapper2.and(wq->{
                int i = 0;
                for(List<String> orfCodes:orgCodeLists){
                    wq.in("org_code",orfCodes);
                    if(i==orgCodeLists.size())
                        break;
                    wq.or();
                }
            });
            //queryWrapper2.in("org_code", orgCodeSet);
            List<AppropNoticeDataDetail> jmDataDetailLastMonthList = appropNoticeDataDetailService.list(queryWrapper2);
            //List<SysDict> zgSettlePolicyList = sysDictService.list(new QueryWrapper<SysDict>().eq("dict_type", "jm_settle_policy"));
            Map<String,Map<String,String>> jmOccurMap = dealJmOccur(appropNoticeOccurs);
            Map<String,Map<String,String>> jmSettleMap = dealJmSettle(appropNoticeSettles);
            Map<String,Map<String,String>> jmJmdbbxsjzfsMap = dealJmdbbxsjzfs(appropNoticeJmdbbxsjzfs);
            Map<String,Map<String,String>> jmMonthSettleMap = dealJmMonthSettle(appropNoticeMonthSettles);
            Map<String,Map<String,String>> jmDrgMap = dealJmDrg(appropNoticeDrgs);
            for (String orgCode : orgCodeSet) {
                AppropNoticeDataDetail detail = new AppropNoticeDataDetail();
                detail.setId(IdUtil.simpleUUID());
                detail.setCreateTime(new Date());
                detail.setCreateUser(sysUser.getId());
                detail.setYear(currYear);
                detail.setMonth(currMonth);
                detail.setOrgCode(orgCode);
                String orgName = null;
                String tcq = null;
                String jb = null;
//                BigDecimal fszfy = BigDecimal.ZERO;//发生总费用
//                BigDecimal tcjjfsje = BigDecimal.ZERO;//统筹基金发生金额
//                BigDecimal dbjjfsje = BigDecimal.ZERO;//大病基金发生金额
//                BigDecimal jzjjfsje = BigDecimal.ZERO;//救助基金发生金额
//                BigDecimal gjzhfsje = BigDecimal.ZERO;//共济账户发生金额
//                BigDecimal dyXj = BigDecimal.ZERO;//现金
                Map<String,String> occurMap = jmOccurMap.get(orgCode);
                BigDecimal fszfy = occurMap!=null?new BigDecimal(MapUtil.getStr(occurMap,"fszfy")):BigDecimal.ZERO;//发生总费用
                BigDecimal tcjjfsje = occurMap!=null?new BigDecimal(MapUtil.getStr(occurMap,"tcjjfsje")):BigDecimal.ZERO;//统筹基金发生金额
                BigDecimal dbjjfsje = occurMap!=null?new BigDecimal(MapUtil.getStr(occurMap,"dbjjfsje")):BigDecimal.ZERO;//大病基金发生金额
                BigDecimal jzjjfsje = occurMap!=null?new BigDecimal(MapUtil.getStr(occurMap,"jzjjfsje")):BigDecimal.ZERO;//救助基金发生金额
                BigDecimal gjzhfsje = occurMap!=null?new BigDecimal(MapUtil.getStr(occurMap,"gjzhfsje")):BigDecimal.ZERO;//共济账户发生金额
                BigDecimal dyXj = occurMap!=null?new BigDecimal(MapUtil.getStr(occurMap,"dyXj")):BigDecimal.ZERO;//现金
                orgName = occurMap!=null?MapUtil.getStr(occurMap,"orgName"):null;
                tcq = occurMap!=null?MapUtil.getStr(occurMap,"tcq"):null;
//                if (StringUtils.isNotEmpty(appropNoticeOccurs)) {
//                    List<AppropNoticeOccur> filterOccur = appropNoticeOccurs.stream().filter(x ->
//                            x.getOrgCode().equals(orgCode) && x.getXzlx().equals(InsutypeEnum.CXJMJBYLBX.getInfo())
//                    ).collect(Collectors.toList());
//                    if (StringUtils.isNotEmpty(filterOccur)) {
//                        if (StringUtils.isBlank(orgName)) {
//                            orgName = filterOccur.get(0).getOrgName();
//                        }
//                        for (AppropNoticeOccur appropNoticeOccur : filterOccur) {
//                            BigDecimal ylfze = StringUtils.isNull(appropNoticeOccur.getYlfze()) ? BigDecimal.ZERO : appropNoticeOccur.getYlfze();
//                            fszfy = NumberUtil.add(ylfze, fszfy);
//                            BigDecimal tcjj = StringUtils.isNull(appropNoticeOccur.getTcjj()) ? BigDecimal.ZERO : appropNoticeOccur.getTcjj();
//                            tcjjfsje = NumberUtil.add(tcjj, tcjjfsje);
//                            BigDecimal jmdbjj = StringUtils.isNull(appropNoticeOccur.getJmdbjj()) ? BigDecimal.ZERO : appropNoticeOccur.getJmdbjj();
//                            dbjjfsje = NumberUtil.add(jmdbjj, dbjjfsje);
//                            BigDecimal yljzjj = StringUtils.isNull(appropNoticeOccur.getYljzjj()) ? BigDecimal.ZERO : appropNoticeOccur.getYljzjj();
//                            jzjjfsje = NumberUtil.add(yljzjj, jzjjfsje);
//                            BigDecimal gjzh = StringUtils.isNull(appropNoticeOccur.getGjzh()) ? BigDecimal.ZERO : appropNoticeOccur.getGjzh();
//                            gjzhfsje = NumberUtil.add(gjzh, gjzhfsje);
//                            BigDecimal xj = StringUtils.isNull(appropNoticeOccur.getXj()) ? BigDecimal.ZERO : appropNoticeOccur.getXj();
//                            dyXj = NumberUtil.add(xj, dyXj);
//                        }
//                    }
//                }
                detail.setJmDyFszfy(fszfy.setScale(2,RoundingMode.HALF_UP));
                detail.setJmDyTcjjfsje(tcjjfsje.setScale(2,RoundingMode.HALF_UP));
                detail.setJmDyDbjjfsje(dbjjfsje.setScale(2,RoundingMode.HALF_UP));
                detail.setJmDyJzjjfsje(jzjjfsje.setScale(2,RoundingMode.HALF_UP));
                detail.setJmDyGjzhfsje(gjzhfsje.setScale(2,RoundingMode.HALF_UP));
                detail.setJmDyXj(dyXj.setScale(2,RoundingMode.HALF_UP));
//                BigDecimal jsxj = BigDecimal.ZERO;//结算小计
//                BigDecimal tcjjjsje = BigDecimal.ZERO;//统筹基金结算金额
//                BigDecimal dbjjjsje = BigDecimal.ZERO;//大病基金结算金额
//                BigDecimal jzjjjsje = BigDecimal.ZERO;//救助基金结算金额
//                BigDecimal gjzhjsje = BigDecimal.ZERO;//共济账户结算金额

                Map<String,String> settleMap = jmSettleMap.get(orgCode);
                BigDecimal jsxj = settleMap!=null?new BigDecimal(MapUtil.getStr(settleMap,"jsxj")):BigDecimal.ZERO;//结算小计
                BigDecimal tcjjjsje = settleMap!=null?new BigDecimal(MapUtil.getStr(settleMap,"tcjjjsje")):BigDecimal.ZERO;//结算小计;//统筹基金结算金额
                BigDecimal dbjjjsje = settleMap!=null?new BigDecimal(MapUtil.getStr(settleMap,"dbjjjsje")):BigDecimal.ZERO;//结算小计;//大病基金结算金额
                BigDecimal jzjjjsje = settleMap!=null?new BigDecimal(MapUtil.getStr(settleMap,"jzjjjsje")):BigDecimal.ZERO;//结算小计;//救助基金结算金额
                BigDecimal gjzhjsje = settleMap!=null?new BigDecimal(MapUtil.getStr(settleMap,"gjzhjsje")):BigDecimal.ZERO;//结算小计;//共济账户结算金额
                if (StringUtils.isEmpty(orgName)) {
                    orgName = settleMap!=null?MapUtil.getStr(settleMap,"orgName"):null;
                }
                if (StringUtils.isEmpty(tcq)) {
                    tcq = settleMap!=null?MapUtil.getStr(settleMap,"tcq"):null;
                }
//                if (StringUtils.isNotEmpty(appropNoticeSettles)) {
//                    if (StringUtils.isNotEmpty(zgSettlePolicyList)) {
//                        List<String> settlePolicyNames = zgSettlePolicyList.stream().map(SysDict::getLabel).collect(Collectors.toList());
//                        List<AppropNoticeSettle> filterSettle = appropNoticeSettles.stream().filter(x -> x.getOrgCode().equals(orgCode) && settlePolicyNames.contains(x.getJszcmc())).collect(Collectors.toList());
//                        if (StringUtils.isNotEmpty(filterSettle)) {
//                            if (StringUtils.isBlank(orgName)) {
//                                orgName = filterSettle.get(0).getOrgName();
//                            }
//                            if (StringUtils.isBlank(tcq)) {
//                                tcq = filterSettle.get(0).getTcq();
//                            }
//                            for (AppropNoticeSettle appropNoticeSettle : filterSettle) {
//                                BigDecimal hj = StringUtils.isNull(appropNoticeSettle.getHj()) ? BigDecimal.ZERO : appropNoticeSettle.getHj();
//                                jsxj = NumberUtil.add(hj, jsxj);
//                                BigDecimal jmtcjj = StringUtils.isNull(appropNoticeSettle.getJmtcjj()) ? BigDecimal.ZERO : appropNoticeSettle.getJmtcjj();
//                                tcjjjsje = NumberUtil.add(jmtcjj, tcjjjsje);
//                                BigDecimal jmdbjj = StringUtils.isNull(appropNoticeSettle.getJmdbjj()) ? BigDecimal.ZERO : appropNoticeSettle.getJmdbjj();
//                                dbjjjsje = NumberUtil.add(jmdbjj, dbjjjsje);
//                                BigDecimal yljzjj = StringUtils.isNull(appropNoticeSettle.getYljzjj()) ? BigDecimal.ZERO : appropNoticeSettle.getYljzjj();
//                                jzjjjsje = NumberUtil.add(yljzjj, jzjjjsje);
//                                BigDecimal gjzh = StringUtils.isNull(appropNoticeSettle.getGjzh()) ? BigDecimal.ZERO : appropNoticeSettle.getGjzh();
//                                gjzhjsje = NumberUtil.add(gjzh, gjzhjsje);
//                            }
//                        }
//                    }
//                }
                detail.setJmDyJsxj(jsxj.setScale(2,RoundingMode.HALF_UP));
                detail.setJmDyTcjjjsje(tcjjjsje.setScale(2,RoundingMode.HALF_UP));
                detail.setJmDyDbjjjsje(dbjjjsje.setScale(2,RoundingMode.HALF_UP));
                detail.setJmDyJzjjjsje(jzjjjsje.setScale(2,RoundingMode.HALF_UP));
                detail.setJmDyGjzhjsje(gjzhjsje.setScale(2,RoundingMode.HALF_UP));
//                BigDecimal jjbfxj = BigDecimal.ZERO;//基金拨付小计
//                BigDecimal jjbfje = BigDecimal.ZERO;//基金拨付金额
//                BigDecimal dbsbbfje = BigDecimal.ZERO;//大病商保拨付金额
//                BigDecimal khbzj = BigDecimal.ZERO;//当月考核保证金
//                BigDecimal kk = BigDecimal.ZERO;//当月扣款

                Map<String,String> dbbxsjzfsMap = jmJmdbbxsjzfsMap.get(orgCode);
                BigDecimal jjbfxj = BigDecimal.ZERO;//基金拨付小计
                BigDecimal dbsbbfje = dbbxsjzfsMap!=null?new BigDecimal(MapUtil.getStr(dbbxsjzfsMap,"dbsbbfje")):BigDecimal.ZERO;//大病商保拨付金额
                if (StringUtils.isEmpty(orgName)) {
                    orgName = dbbxsjzfsMap!=null?MapUtil.getStr(dbbxsjzfsMap,"orgName"):null;
                }
                if (StringUtils.isEmpty(tcq)) {
                    tcq = dbbxsjzfsMap!=null?MapUtil.getStr(dbbxsjzfsMap,"tcq"):null;
                }
//                if (StringUtils.isNotEmpty(appropNoticeJmdbbxsjzfs)) {
//                    List<AppropNoticeJmdbbxsjzf> filterJmdbbxsjzf = appropNoticeJmdbbxsjzfs.stream().filter(x -> x.getOrgCode().equals(orgCode)).collect(Collectors.toList());
//                    if (StringUtils.isNotEmpty(filterJmdbbxsjzf)) {
//                        if (StringUtils.isBlank(orgName)) {
//                            orgName = filterJmdbbxsjzf.get(0).getOrgName();
//                        }
//                        if (StringUtils.isBlank(tcq)) {
//                            tcq = filterJmdbbxsjzf.get(0).getTcq();
//                        }
//                        for (AppropNoticeJmdbbxsjzf appropNoticeJmdbbxsjzf : filterJmdbbxsjzf) {
//                            BigDecimal zfje = StringUtils.isNull(appropNoticeJmdbbxsjzf.getZfje()) ? BigDecimal.ZERO : appropNoticeJmdbbxsjzf.getZfje();
//                            dbsbbfje = NumberUtil.add(zfje, dbsbbfje);
//                        }
//                    }
//                }

                Map<String,String> monthSettleMap = jmMonthSettleMap.get(orgCode);
                BigDecimal jjbfje = monthSettleMap!=null?new BigDecimal(MapUtil.getStr(monthSettleMap,"jjbfje")):BigDecimal.ZERO;//基金拨付金额
                BigDecimal khbzj = monthSettleMap!=null?new BigDecimal(MapUtil.getStr(monthSettleMap,"khbzj")):BigDecimal.ZERO;//当月考核保证金
                BigDecimal kk = monthSettleMap!=null?new BigDecimal(MapUtil.getStr(monthSettleMap,"kk")):BigDecimal.ZERO;//当月扣款
                if (StringUtils.isEmpty(orgName)) {
                    orgName = monthSettleMap!=null?MapUtil.getStr(monthSettleMap,"orgName"):null;
                }
                if (StringUtils.isEmpty(tcq)) {
                    tcq = monthSettleMap!=null?MapUtil.getStr(monthSettleMap,"tcq ="):null;
                }
                if (StringUtils.isEmpty(jb)) {
                    jb = monthSettleMap!=null?MapUtil.getStr(monthSettleMap,"jb"):null;
                }
                //NumberUtil.add(monthSettleMap!=null?new BigDecimal(MapUtil.getStr(monthSettleMap,"jjbfje")):BigDecimal.ZERO, jjbfje);
                //NumberUtil.add(monthSettleMap!=null?new BigDecimal(MapUtil.getStr(monthSettleMap,"khbzj")):BigDecimal.ZERO, khbzj);
                //NumberUtil.add(monthSettleMap!=null?new BigDecimal(MapUtil.getStr(monthSettleMap,"kk")):BigDecimal.ZERO, kk);
//                if (StringUtils.isNotEmpty(appropNoticeMonthSettles)) {
//                    List<AppropNoticeMonthSettle> filterMonthSettle = appropNoticeMonthSettles.stream().filter(x -> x.getOrgCode().equals(orgCode)).collect(Collectors.toList());
//                    if (StringUtils.isNotEmpty(filterMonthSettle)) {
//                        if (StringUtils.isBlank(orgName)) {
//                            orgName = filterMonthSettle.get(0).getOrgName();
//                        }
//                        if (StringUtils.isBlank(tcq)) {
//                            tcq = filterMonthSettle.get(0).getTcq();
//                        }
//                        if (StringUtils.isBlank(jb)) {
//                            jb = filterMonthSettle.get(0).getJb();
//                        }
//                        for (AppropNoticeMonthSettle appropNoticeMonthSettle : filterMonthSettle) {
//                            BigDecimal jmjjbfje = StringUtils.isNull(appropNoticeMonthSettle.getJmjjbfje()) ? BigDecimal.ZERO : appropNoticeMonthSettle.getJmjjbfje();
//                            jjbfje = NumberUtil.add(jmjjbfje, jjbfje);
//                            BigDecimal jmkhbzjlj = StringUtils.isNull(appropNoticeMonthSettle.getJmkhbzjlj()) ? BigDecimal.ZERO : appropNoticeMonthSettle.getJmkhbzjlj();
//                            khbzj = NumberUtil.add(jmkhbzjlj, khbzj);
//                            BigDecimal jmbyzflj = StringUtils.isNull(appropNoticeMonthSettle.getJmbyzflj()) ? BigDecimal.ZERO : appropNoticeMonthSettle.getJmbyzflj();
//                            kk = NumberUtil.add(jmbyzflj, kk);
//                        }
//                    }
//                }
                Map<String,String> drgMap = jmDrgMap.get(orgCode);
                if (StringUtils.isEmpty(orgName)) {
                    orgName = drgMap!=null?MapUtil.getStr(drgMap,"orgName"):null;
                }
                if (StringUtils.isEmpty(tcq)) {
                    tcq = drgMap!=null?MapUtil.getStr(drgMap,"tcq"):null;
                }
                if (StringUtils.isEmpty(jb)) {
                    jb = drgMap!=null?MapUtil.getStr(drgMap,"jb"):null;
                }
                jjbfje = NumberUtil.add(drgMap!=null?new BigDecimal(MapUtil.getStr(drgMap,"jjbfje")):BigDecimal.ZERO, jjbfje);
                kk = NumberUtil.add(drgMap!=null?new BigDecimal(MapUtil.getStr(drgMap,"kk")):BigDecimal.ZERO, kk);
//                if (StringUtils.isNotEmpty(appropNoticeDrgs)) {
//                    List<AppropNoticeDrg> filterDrg = appropNoticeDrgs.stream().filter(x -> x.getOrgCode().equals(orgCode)).collect(Collectors.toList());
//                    if (StringUtils.isNotEmpty(filterDrg)) {
//                        if (StringUtils.isBlank(orgName)) {
//                            orgName = filterDrg.get(0).getOrgName();
//                        }
//                        if (StringUtils.isBlank(tcq)) {
//                            tcq = filterDrg.get(0).getTcq();
//                        }
//                        if (StringUtils.isBlank(jb)) {
//                            jb = filterDrg.get(0).getJb();
//                        }
//                        for (AppropNoticeDrg appropNoticeDrg : filterDrg) {
//                            BigDecimal jmjjbfje = StringUtils.isNull(appropNoticeDrg.getJmjjbfje()) ? BigDecimal.ZERO : appropNoticeDrg.getJmjjbfje();
//                            jjbfje = NumberUtil.add(jmjjbfje, jjbfje);
//                            BigDecimal jmbyzflj = StringUtils.isNull(appropNoticeDrg.getJmbyzflj()) ? BigDecimal.ZERO : appropNoticeDrg.getJmbyzflj();
//                            kk = NumberUtil.add(jmbyzflj, kk);
//                        }
//                    }
//                }
                jjbfxj = NumberUtil.sub(NumberUtil.add(jjbfje,dbsbbfje), khbzj, kk);
                detail.setJmDyDbsbbfje(dbsbbfje.setScale(2,RoundingMode.HALF_UP));
                detail.setJmDyJjbfxj(jjbfxj.setScale(2,RoundingMode.HALF_UP));
                detail.setJmDyJjbfje(jjbfje.setScale(2,RoundingMode.HALF_UP));
                detail.setJmDyKhbzj(khbzj.setScale(2,RoundingMode.HALF_UP));
                detail.setJmDyKk(kk.setScale(2,RoundingMode.HALF_UP));
                detail.setOrgCode(orgCode);
                detail.setOrgName(orgName);
                detail.setTcq(tcq);
                detail.setJb(jb);
                //查询上个月合计
                //查询上个月
                AppropNoticeDataDetail jmDataDetailLastMonth = null;
                if(StringUtils.isNotEmpty(jmDataDetailLastMonthList)){
                    List<AppropNoticeDataDetail> collect = jmDataDetailLastMonthList.stream().filter(x -> x.getOrgCode().equals(orgCode)).collect(Collectors.toList());
                    if(StringUtils.isNotEmpty(collect)){
                        jmDataDetailLastMonth = collect.get(0);
                    }
                }
                BigDecimal fszfylj = fszfy;//发生总费用累计
                BigDecimal tcjjfsjelj = tcjjfsje;//统筹基金发生金额累计
                BigDecimal dbjjfsjelj = dbjjfsje;//大病基金发生金额累计
                BigDecimal jzjjfsjelj = jzjjfsje;//救助基金发生金额累计
                BigDecimal gjzhfsjelj = gjzhfsje;//共济账户发生金额累计
                BigDecimal xjlj = dyXj;//现金累计
                BigDecimal jsxjlj = jsxj;//结算小计累计
                BigDecimal tcjjjsjelj = tcjjjsje;//统筹基金结算金额累计
                BigDecimal dbjjjsjelj = dbjjjsje;//大病基金结算金额累计
                BigDecimal jzjjjsjelj = jzjjjsje;//救助基金结算金额累计
                BigDecimal gjzhjsjelj = gjzhjsje;//共济账户结算金额累计
                BigDecimal jjbfxjlj = jjbfxj;//基金拨付小计累计
                BigDecimal jjbfjelj = jjbfje;//基金拨付金额累计
                BigDecimal dbsbbfjelj = dbsbbfje;//大病商保拨付金额累计
                BigDecimal khbzjlj = khbzj;//考核保证金累计
                BigDecimal kklj = kk;//扣款累计
                if (StringUtils.isNotNull(jmDataDetailLastMonth)) {
                    BigDecimal fszfyljLastMonth = StringUtils.isNull(jmDataDetailLastMonth.getJmLjFszfy()) ? BigDecimal.ZERO : jmDataDetailLastMonth.getJmLjFszfy();
                    fszfylj = NumberUtil.add(fszfyljLastMonth, fszfy);
                    BigDecimal tcjjfsjeljLastMonth = StringUtils.isNull(jmDataDetailLastMonth.getJmLjTcjjfsje()) ? BigDecimal.ZERO : jmDataDetailLastMonth.getJmLjTcjjfsje();
                    tcjjfsjelj = NumberUtil.add(tcjjfsjeljLastMonth, tcjjfsje);
                    BigDecimal dbjjfsjeljLastMonth = StringUtils.isNull(jmDataDetailLastMonth.getJmLjDbjjfsje()) ? BigDecimal.ZERO : jmDataDetailLastMonth.getJmLjDbjjfsje();
                    dbjjfsjelj = NumberUtil.add(dbjjfsjeljLastMonth, dbjjfsje);
                    BigDecimal jzjjfsjeljLastMonth = StringUtils.isNull(jmDataDetailLastMonth.getJmLjJzjjfsje()) ? BigDecimal.ZERO : jmDataDetailLastMonth.getJmLjJzjjfsje();
                    jzjjfsjelj = NumberUtil.add(jzjjfsjeljLastMonth, jzjjfsje);
                    BigDecimal gjzhfsjeljLastMonth = StringUtils.isNull(jmDataDetailLastMonth.getJmLjGjzhfsje()) ? BigDecimal.ZERO : jmDataDetailLastMonth.getJmLjGjzhfsje();
                    gjzhfsjelj = NumberUtil.add(gjzhfsjeljLastMonth, gjzhfsje);
                    BigDecimal xjljLastMonth = StringUtils.isNull(jmDataDetailLastMonth.getJmLjXj()) ? BigDecimal.ZERO : jmDataDetailLastMonth.getJmLjXj();
                    xjlj = NumberUtil.add(xjljLastMonth, dyXj);
                    BigDecimal jsxjljLastMonth = StringUtils.isNull(jmDataDetailLastMonth.getJmLjJsxj()) ? BigDecimal.ZERO : jmDataDetailLastMonth.getJmLjJsxj();
                    jsxjlj = NumberUtil.add(jsxjljLastMonth, jsxj);
                    BigDecimal tcjjjsjeljLastMonth = StringUtils.isNull(jmDataDetailLastMonth.getJmLjTcjjjsje()) ? BigDecimal.ZERO : jmDataDetailLastMonth.getJmLjTcjjjsje();
                    tcjjjsjelj = NumberUtil.add(tcjjjsjeljLastMonth, tcjjjsje);
                    BigDecimal dbjjjsjeljLastMonth = StringUtils.isNull(jmDataDetailLastMonth.getJmLjDbjjjsje()) ? BigDecimal.ZERO : jmDataDetailLastMonth.getJmLjDbjjjsje();
                    dbjjjsjelj = NumberUtil.add(dbjjjsjeljLastMonth, dbjjjsje);
                    BigDecimal jzjjjsjeljLastMonth = StringUtils.isNull(jmDataDetailLastMonth.getJmLjJzjjjsje()) ? BigDecimal.ZERO : jmDataDetailLastMonth.getJmLjJzjjjsje();
                    jzjjjsjelj = NumberUtil.add(jzjjjsjeljLastMonth, jzjjjsje);
                    BigDecimal gjzhjsjeljLastMonth = StringUtils.isNull(jmDataDetailLastMonth.getJmLjGjzhjsje()) ? BigDecimal.ZERO : jmDataDetailLastMonth.getJmLjGjzhjsje();
                    gjzhjsjelj = NumberUtil.add(gjzhjsjeljLastMonth, gjzhjsje);
                    BigDecimal jjbfxjljLastMonth = StringUtils.isNull(jmDataDetailLastMonth.getJmLjJjbfxj()) ? BigDecimal.ZERO : jmDataDetailLastMonth.getJmLjJjbfxj();
                    jjbfxjlj = NumberUtil.add(jjbfxjljLastMonth, jjbfxj);
                    BigDecimal jjbfjeljLastMonth = StringUtils.isNull(jmDataDetailLastMonth.getJmLjJjbfje()) ? BigDecimal.ZERO : jmDataDetailLastMonth.getJmLjJjbfje();
                    jjbfjelj = NumberUtil.add(jjbfjeljLastMonth, jjbfje);
                    BigDecimal dbsbbfjeljLastMonth = StringUtils.isNull(jmDataDetailLastMonth.getJmLjDbsbbfje()) ? BigDecimal.ZERO : jmDataDetailLastMonth.getJmLjDbsbbfje();
                    dbsbbfjelj = NumberUtil.add(dbsbbfjeljLastMonth, dbsbbfje);
                    BigDecimal khbzjljLastMonth = StringUtils.isNull(jmDataDetailLastMonth.getJmLjKhbzj()) ? BigDecimal.ZERO : jmDataDetailLastMonth.getJmLjKhbzj();
                    khbzjlj = NumberUtil.add(khbzjljLastMonth, khbzj);
                    BigDecimal kkljLastMonth = StringUtils.isNull(jmDataDetailLastMonth.getJmLjKk()) ? BigDecimal.ZERO : jmDataDetailLastMonth.getJmLjKk();
                    kklj = NumberUtil.add(kkljLastMonth, kk);
                }
                detail.setJmLjFszfy(fszfylj.setScale(2,RoundingMode.HALF_UP));
                detail.setJmLjTcjjfsje(tcjjfsjelj.setScale(2,RoundingMode.HALF_UP));
                detail.setJmLjDbjjfsje(dbjjfsjelj.setScale(2,RoundingMode.HALF_UP));
                detail.setJmLjJzjjfsje(jzjjfsjelj.setScale(2,RoundingMode.HALF_UP));
                detail.setJmLjGjzhfsje(gjzhfsjelj.setScale(2,RoundingMode.HALF_UP));
                detail.setJmLjXj(xjlj.setScale(2,RoundingMode.HALF_UP));
                detail.setJmLjJsxj(jsxjlj.setScale(2,RoundingMode.HALF_UP));
                detail.setJmLjTcjjjsje(tcjjjsjelj.setScale(2,RoundingMode.HALF_UP));
                detail.setJmLjDbjjjsje(dbjjjsjelj.setScale(2,RoundingMode.HALF_UP));
                detail.setJmLjJzjjjsje(jzjjjsjelj.setScale(2,RoundingMode.HALF_UP));
                detail.setJmLjGjzhjsje(gjzhjsjelj.setScale(2,RoundingMode.HALF_UP));
                detail.setJmLjJjbfxj(jjbfxjlj.setScale(2,RoundingMode.HALF_UP));
                detail.setJmLjJjbfje(jjbfjelj.setScale(2,RoundingMode.HALF_UP));
                detail.setJmLjDbsbbfje(dbsbbfjelj.setScale(2,RoundingMode.HALF_UP));
                detail.setJmLjKhbzj(khbzjlj.setScale(2,RoundingMode.HALF_UP));
                detail.setJmLjKk(kklj.setScale(2,RoundingMode.HALF_UP));
                appropNoticeDataDetails.add(detail);
            }
        }
        return appropNoticeDataDetails;
    }

    /**
     * @param appropNoticePreviewVO
     * @return com.jsdc.ybpt.vo.ResultInfo
     * @description //TODO  生成数据
     * @author wangxiao
     * @date 2024/5/23
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultInfo generate(AppropNoticePreviewVO appropNoticePreviewVO) {
//        MultipartFile occurFile,
//        MultipartFile settleFile,
//        MultipartFile jmdbbxsjzfFile,
//        MultipartFile monthSettleFile,
//        MultipartFile drgFile,
        SysUser sysUser = sysUserService.getUser();
        String tcq = "";
        if("1".equals(sysUser.getUser_type())){
            //市直单位
            AdministrativeUnit administrativeUnit = administrativeUnitService.getOne(Wrappers.<AdministrativeUnit>lambdaQuery()
                    .eq(AdministrativeUnit::getAdmdvs, sysUser.getOrg_code())
                    .eq(AdministrativeUnit::getIs_del, "0")
            );
            if(StringUtils.isNull(administrativeUnit)){
                throw new CustomException("机构信息不存在",-1);
            }
            tcq = administrativeUnit.getAdmdvs();
        }else{
            //定点医疗机构
            FixmedinsB fixmedinsB = fixmedinsBService.getOne(Wrappers.<FixmedinsB>lambdaQuery()
                    .eq(FixmedinsB::getFixmedins_code, sysUser.getOrg_code())
                    .eq(FixmedinsB::getIs_del, "0")
            );
            if(StringUtils.isNull(fixmedinsB)){
                throw new CustomException("机构信息不存在",-1);
            }
            tcq = fixmedinsB.getFix_blng_admdvs();
        }
        try {
            AppropNotice appropNotice = new AppropNotice();
            appropNotice.setYear(appropNoticePreviewVO.getYear());
            appropNotice.setId(IdUtil.simpleUUID());
            appropNotice.setCreateTime(new Date());
            appropNotice.setCreateUser(sysUser.getId());
            appropNotice.setYear(appropNoticePreviewVO.getYear());
            appropNotice.setMonth(appropNoticePreviewVO.getMonth());
            appropNotice.setTcq(tcq);
            appropNoticeMapper.insert(appropNotice);

            //当前版本未使用，先去掉
//            List<AppropNoticeOccur> occurData = appropNoticePreviewVO.getOccurData();
//            if (StringUtils.isNotEmpty(occurData)) {
//                occurData.stream().forEach(x -> x.setAppropNoticeId(appropNotice.getId()));
//                appropNoticeOccurService.saveBatch(occurData);
//            }
//            List<AppropNoticeSettle> settleData = appropNoticePreviewVO.getSettleData();
//            if (StringUtils.isNotEmpty(settleData)) {
//                settleData.stream().forEach(x -> x.setAppropNoticeId(appropNotice.getId()));
//                appropNoticeSettleService.saveBatch(settleData);
//            }
//            List<AppropNoticeJmdbbxsjzf> jmdbbxsjzfData = appropNoticePreviewVO.getJmdbbxsjzfData();
//            if (StringUtils.isNotEmpty(jmdbbxsjzfData)) {
//                jmdbbxsjzfData.stream().forEach(x -> x.setAppropNoticeId(appropNotice.getId()));
//                appropNoticeJmdbbxsjzfService.saveBatch(jmdbbxsjzfData);
//            }
//            List<AppropNoticeMonthSettle> monthSettleData = appropNoticePreviewVO.getMonthSettleData();
//            if (StringUtils.isNotEmpty(monthSettleData)) {
//                monthSettleData.stream().forEach(x -> x.setAppropNoticeId(appropNotice.getId()));
//                appropNoticeMonthSettleService.saveBatch(monthSettleData);
//            }
//            List<AppropNoticeDrg> drgData = appropNoticePreviewVO.getDrgData();
//            if (StringUtils.isNotEmpty(drgData)) {
//                drgData.stream().forEach(x -> x.setAppropNoticeId(appropNotice.getId()));
//                appropNoticeDrgService.saveBatch(drgData);
//            }


            List<AppropNoticeSummary> appropNoticeSummaries = new ArrayList<>();
            if (StringUtils.isNotEmpty(appropNoticePreviewVO.getZgSummaries())) {
                appropNoticeSummaries.addAll(appropNoticePreviewVO.getZgSummaries());
            }
            if (StringUtils.isNotEmpty(appropNoticePreviewVO.getJmSummaries())) {
                appropNoticeSummaries.addAll(appropNoticePreviewVO.getJmSummaries());
            }
            if (StringUtils.isNotEmpty(appropNoticeSummaries)) {
                appropNoticeSummaries.stream().forEach(x -> x.setAppropNoticeId(appropNotice.getId()));
                appropNoticeSummaryService.saveBatch(appropNoticeSummaries);
            }
            List<AppropNoticeDataDetail> dataDetail = appropNoticePreviewVO.getDataDetail();
            if (StringUtils.isNotEmpty(dataDetail)) {
                dataDetail.stream().forEach(x -> x.setAppropNoticeId(appropNotice.getId()));
                appropNoticeDataDetailService.saveBatch(dataDetail);
            }
//            FastDfsParams params = null;
//            params = new FastDfsParams("approp_notice/occurFile", occurFile, "28", appropNotice.getId());
//            params.setFileName(occurFile.getOriginalFilename());
//            fastDfsUtil.uploadFile(params);
//            params = new FastDfsParams("approp_notice/settleFile", settleFile, "29", appropNotice.getId());
//            params.setFileName(settleFile.getOriginalFilename());
//            fastDfsUtil.uploadFile(params);
//            params = new FastDfsParams("approp_notice/jmdbbxsjzfFile", jmdbbxsjzfFile, "30", appropNotice.getId());
//            params.setFileName(jmdbbxsjzfFile.getOriginalFilename());
//            fastDfsUtil.uploadFile(params);
//            params = new FastDfsParams("approp_notice/monthSettleFile", monthSettleFile, "31", appropNotice.getId());
//            params.setFileName(monthSettleFile.getOriginalFilename());
//            fastDfsUtil.uploadFile(params);
//            params = new FastDfsParams("approp_notice/drgFile", drgFile, "32", appropNotice.getId());
//            params.setFileName(drgFile.getOriginalFilename());
//            fastDfsUtil.uploadFile(params);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.error("生成数据异常：" + e.getMessage());
        }
        return ResultInfo.success();
    }

    /**
     * @param id
     * @return com.jsdc.ybpt.vo.ResultInfo
     * @description //TODO 发送数据
     * @author wangxiao
     * @date 2024/5/23
     */
    @Transactional
    public ResultInfo send(String id) {
        SysUser sysUser = sysUserService.getUser();
        AppropNotice appropNotice = new AppropNotice();
        appropNotice.setId(id);
        appropNotice.setUpdateTime(new Date());
        appropNotice.setUpdateUser(sysUser.getId());
        appropNotice.setSendTime(new Date());
        appropNotice.setStatus("1");
        appropNoticeMapper.updateById(appropNotice);
        List<AppropNoticeSend> appropNoticeSends = new ArrayList<>();
        List<AppropNoticeDataDetail> appropNoticeDataDetails = appropNoticeDataDetailService.list(new QueryWrapper<AppropNoticeDataDetail>().eq("approp_notice_id", id));
        if(StringUtils.isNotEmpty(appropNoticeDataDetails)){
            Set<String> orgCodeSet = appropNoticeDataDetails.stream().map(AppropNoticeDataDetail::getOrgCode).collect(Collectors.toSet());
            for (String orgCode : orgCodeSet) {
                AppropNoticeSend appropNoticeSend = new AppropNoticeSend();
                appropNoticeSend.setAppropNoticeId(id);
                appropNoticeSend.setId(IdUtil.simpleUUID());
                appropNoticeSend.setOrgCode(orgCode);
                appropNoticeSend.setIsDel("0");
                appropNoticeSend.setCreateUser(sysUser.getId());
                appropNoticeSend.setCreateTime(new Date());
                appropNoticeSends.add(appropNoticeSend);
            }
        }
        if(StringUtils.isNotEmpty(appropNoticeSends)){
            appropNoticeSendService.saveBatch(appropNoticeSends);
        }
        return ResultInfo.success();
    }

    /**
     * @param id
     * @return com.jsdc.ybpt.vo.ResultInfo
     * @description //TODO  删除数据
     * @author wangxiao
     * @date 2024/5/23
     */
    @Transactional
    public ResultInfo delete(String id) {
        SysUser sysUser = sysUserService.getUser();
        AppropNotice appropNotice = new AppropNotice();
        appropNotice.setId(id);
        appropNotice.setUpdateTime(new Date());
        appropNotice.setUpdateUser(sysUser.getId());
        appropNotice.setIsDel("1");
        appropNoticeMapper.updateById(appropNotice);
        appropNoticeOccurService.remove(new QueryWrapper<AppropNoticeOccur>().eq("approp_notice_id", id));
        appropNoticeSettleService.remove(new QueryWrapper<AppropNoticeSettle>().eq("approp_notice_id", id));
        appropNoticeJmdbbxsjzfService.remove(new QueryWrapper<AppropNoticeJmdbbxsjzf>().eq("approp_notice_id", id));
        appropNoticeMonthSettleService.remove(new QueryWrapper<AppropNoticeMonthSettle>().eq("approp_notice_id", id));
        appropNoticeDrgService.remove(new QueryWrapper<AppropNoticeDrg>().eq("approp_notice_id", id));
        appropNoticeSummaryService.remove(new QueryWrapper<AppropNoticeSummary>().eq("approp_notice_id", id));
        appropNoticeDataDetailService.remove(new QueryWrapper<AppropNoticeDataDetail>().eq("approp_notice_id", id));
        appropNoticeSummaryAnalyseService.remove(new QueryWrapper<AppropNoticeSummaryAnalyse>().eq("approp_notice_id", id));
        appropNoticeDetailAnalyseService.remove(    new QueryWrapper<AppropNoticeDetailAnalyse>().eq("approp_notice_id", id));
        appropNoticeSendService.remove(new QueryWrapper<AppropNoticeSend>().eq("approp_notice_id", id));
        return ResultInfo.success();
    }

    /**
     * @param appropNoticeViewSummaryDTO
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @description //TODO  查看汇总数据
     * @author wangxiao
     * @date 2024/5/23
     */
    public Map<String, Object> viewSummary(AppropNoticeViewSummaryDTO appropNoticeViewSummaryDTO) {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<AppropNoticeSummary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del","0");
        queryWrapper.eq("year", appropNoticeViewSummaryDTO.getYear());
        queryWrapper.eq("month", appropNoticeViewSummaryDTO.getMonth());
        if (!appropNoticeViewSummaryDTO.getTcq().equals(TcqEnum.SBJ.getCode())) {
            //统筹区不是市本级，查看自己上传的记录
            queryWrapper.eq("approp_notice_id", appropNoticeViewSummaryDTO.getAppropNoticeId());
        }
        List<AppropNoticeSummary> appropNoticeSummary = appropNoticeSummaryService.list(queryWrapper);
        List<AppropNoticeSummary> zgSummaries = new ArrayList<>();
        List<AppropNoticeSummary> jmSummaries = new ArrayList<>();
        if (StringUtils.isNotEmpty(appropNoticeSummary)) {
            List<AppropNoticeSummary> zgSummariesTemp = appropNoticeSummary.stream().filter(x -> x.getRylb().equals("1")).collect(Collectors.toList());
            List<AppropNoticeSummary> jmSummariesTemp = appropNoticeSummary.stream().filter(x -> x.getRylb().equals("2")).collect(Collectors.toList());
            AppropNoticeSummaryMergeDTO appropNoticeSummaryMergeDTO = new AppropNoticeSummaryMergeDTO();
            appropNoticeSummaryMergeDTO.setYear(appropNoticeViewSummaryDTO.getYear());
            appropNoticeSummaryMergeDTO.setMonth(appropNoticeViewSummaryDTO.getMonth());
            if (appropNoticeViewSummaryDTO.getTcq().equals(TcqEnum.SBJ.getCode())) {
                //统筹区是市本级，将贾汪和铜山合并到市本级
                List<String> tcqList = new ArrayList<>();
                tcqList.add(TcqEnum.SBJ.getInfo());
                tcqList.add(TcqEnum.TSQ.getInfo());
                tcqList.add(TcqEnum.JWQ.getInfo());
                appropNoticeSummaryMergeDTO.setTcqList(tcqList);
                List<AppropNoticeSummary> mergeSummaries = appropNoticeSummaryService.mergeData(appropNoticeSummaryMergeDTO);
                for (AppropNoticeSummary mergeData : mergeSummaries) {
                    mergeData.setTcq(TcqEnum.SBJ.getInfo() + "(含铜山、贾汪)");
                    if(mergeData.getRylb().equals("1")){
                        zgSummaries.add(mergeData);
                    }else{
                        jmSummaries.add(mergeData);
                    }
                }
                if(StringUtils.isNotEmpty(zgSummariesTemp)){
                    List<AppropNoticeSummary> notMergeZgSummaries = zgSummariesTemp.stream().filter(x -> !tcqList.contains(x.getTcq())).collect(Collectors.toList());
                    if(StringUtils.isNotEmpty(notMergeZgSummaries)){
                        zgSummaries.addAll(notMergeZgSummaries);
                    }
                }
                if(StringUtils.isNotEmpty(jmSummariesTemp)){
                    List<AppropNoticeSummary> notMergeJmSummaries = jmSummariesTemp.stream().filter(x -> !tcqList.contains(x.getTcq())).collect(Collectors.toList());
                    if(StringUtils.isNotEmpty(notMergeJmSummaries)){
                        jmSummaries.addAll(notMergeJmSummaries);
                    }
                }
                List<String> allTcqs = Arrays.stream(TcqEnum.values()).map(TcqEnum::getInfo).collect(Collectors.toList());
                appropNoticeSummaryMergeDTO.setTcqList(allTcqs);
                List<AppropNoticeSummary> totalSummaries = appropNoticeSummaryService.mergeData(appropNoticeSummaryMergeDTO);
                if(StringUtils.isNotEmpty(totalSummaries)){
                    for (AppropNoticeSummary mergeData : totalSummaries) {
                        mergeData.setTcq("合计");
                        if(mergeData.getRylb().equals("1")){
                            zgSummaries.add(mergeData);
                        }else{
                            jmSummaries.add(mergeData);
                        }
                    }
                }
            } else {
                zgSummaries.addAll(zgSummariesTemp);
                jmSummaries.addAll(jmSummariesTemp);
            }

        }
        try {
            if(StringUtils.isNotEmpty(zgSummaries)){
                for (AppropNoticeSummary zgSummary : zgSummaries) {
                    Field[] fields = zgSummary.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        Class<?> type = field.getType();
                        if(type == BigDecimal.class){
                            Object val = field.get(zgSummary);
                            if(StringUtils.isNotNull(val)){
                                field.set(zgSummary, NumberUtil.div(val.toString(), "10000").setScale(6,RoundingMode.HALF_UP));
                            }
                        }
                    }
                }
            }
            if(StringUtils.isNotEmpty(jmSummaries)){
                for (AppropNoticeSummary jmSummary : jmSummaries) {
                    Field[] fields = jmSummary.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        Class<?> type = field.getType();
                        if(type == BigDecimal.class){
                            Object val = field.get(jmSummary);
                            if(StringUtils.isNotNull(val)){
                                field.set(jmSummary, NumberUtil.div(val.toString(), "10000").setScale(6,RoundingMode.HALF_UP));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("数据转换成万元异常："+e.getMessage(),-1);
        }
        map.put("zgSummaries", zgSummaries);
        map.put("jmSummaries", jmSummaries);
        return map;
    }

    /**
     * @param appropNoticeViewDataDetailDTO
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.jsdc.ybpt.appropNotice.entity.AppropNoticeDataDetail>
     * @description //TODO  查看数据明细
     * @author wangxiao
     * @date 2024/5/23
     */
    public Page<AppropNoticeDataDetail> viewDataDetail(AppropNoticeViewDataDetailDTO appropNoticeViewDataDetailDTO) {
        Page<AppropNoticeDataDetail> page = new Page<>(appropNoticeViewDataDetailDTO.getPageNo(), appropNoticeViewDataDetailDTO.getPageSize());
        QueryWrapper<AppropNoticeDataDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        queryWrapper.eq("year", appropNoticeViewDataDetailDTO.getYear());
        queryWrapper.eq("month", appropNoticeViewDataDetailDTO.getMonth());
        SysUser sysUser = sysUserService.getUser();
        String orgCode = appropNoticeViewDataDetailDTO.getOrgCode();
        String orgName = appropNoticeViewDataDetailDTO.getOrgName();
        if("2".equals(sysUser.getUser_type()) || "3".equals(sysUser.getUser_type())){
            //定点医疗机构
            FixmedinsB fixmedinsB = fixmedinsBService.getOne(Wrappers.<FixmedinsB>lambdaQuery()
                    .eq(FixmedinsB::getFixmedins_code, sysUser.getOrg_code())
                    .eq(FixmedinsB::getIs_del, "0")
            );
            if(StringUtils.isNull(fixmedinsB)){
                throw new CustomException("机构信息不存在",-1);
            }
            orgCode = fixmedinsB.getFixmedins_code();
        }
        if (StringUtils.isNotBlank(orgCode)) {
            queryWrapper.like("org_code", orgCode);
        }
        if (StringUtils.isNotBlank(orgName)) {
            queryWrapper.like("org_name", orgName);
        }
        if (!appropNoticeViewDataDetailDTO.getTcq().equals(TcqEnum.SBJ.getCode())) {
            //统筹区不是市本级，查看自己统筹区
            queryWrapper.eq("approp_notice_id", appropNoticeViewDataDetailDTO.getAppropNoticeId());
        }
        Page<AppropNoticeDataDetail> appropNoticeDataDetailPage = appropNoticeDataDetailService.page(page, queryWrapper);
        try {
            List<AppropNoticeDataDetail> records = appropNoticeDataDetailPage.getRecords();
            if(StringUtils.isNotEmpty(records)){
                for (AppropNoticeDataDetail record : records) {
                    Field[] fields = record.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        Class<?> type = field.getType();
                        if(type == BigDecimal.class){
                            Object val = field.get(record);
                            if(StringUtils.isNotNull(val)){
                                field.set(record, NumberUtil.div(val.toString(), "10000").setScale(6,RoundingMode.HALF_UP));
                            }
                        }
                    }
                }
            }
            appropNoticeDataDetailPage.setRecords(records);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("数据转换成万元异常："+e.getMessage(),-1);
        }
        return appropNoticeDataDetailPage;
    }

    /**
     * @param appropNoticeViewSummaryDTO
     * @param response
     * @return void
     * @description //TODO  下载汇总数据
     * @author wangxiao
     * @date 2024/5/23
     */
    public void downloadSummary(AppropNoticeViewSummaryDTO appropNoticeViewSummaryDTO, HttpServletResponse response) {
        AppropNotice appropNotice = appropNoticeMapper.selectOne(new QueryWrapper<AppropNotice>()
                .eq("id", appropNoticeViewSummaryDTO.getAppropNoticeId()).eq("is_del", "0"));
        QueryWrapper<AppropNoticeSummary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("year", appropNoticeViewSummaryDTO.getYear());
        queryWrapper.eq("month", appropNoticeViewSummaryDTO.getMonth());
        queryWrapper.eq("is_del","0");
        if (!appropNotice.getTcq().equals(TcqEnum.SBJ.getCode())) {
            //统筹区不是市本级，查看自己统筹区
            queryWrapper.eq("approp_notice_id", appropNoticeViewSummaryDTO.getAppropNoticeId());
        }
        List<AppropNoticeSummary> appropNoticeSummary = appropNoticeSummaryService.list(queryWrapper);
        List<AppropNoticeSummary> zgSummaries = new ArrayList<>();
        List<AppropNoticeSummary> jmSummaries = new ArrayList<>();
        if (StringUtils.isNotEmpty(appropNoticeSummary)) {
            List<AppropNoticeSummary> zgSummariesTemp = appropNoticeSummary.stream().filter(x -> x.getRylb().equals("1")).collect(Collectors.toList());
            List<AppropNoticeSummary> jmSummariesTemp = appropNoticeSummary.stream().filter(x -> x.getRylb().equals("2")).collect(Collectors.toList());
            AppropNoticeSummaryMergeDTO appropNoticeSummaryMergeDTO = new AppropNoticeSummaryMergeDTO();
            appropNoticeSummaryMergeDTO.setYear(appropNoticeViewSummaryDTO.getYear());
            appropNoticeSummaryMergeDTO.setMonth(appropNoticeViewSummaryDTO.getMonth());
            if (appropNotice.getTcq().equals(TcqEnum.SBJ.getCode())) {
                //统筹区是市本级，将贾汪和铜山合并到市本级
                List<String> tcqList = new ArrayList<>();
                tcqList.add(TcqEnum.SBJ.getInfo());
                tcqList.add(TcqEnum.TSQ.getInfo());
                tcqList.add(TcqEnum.JWQ.getInfo());
                appropNoticeSummaryMergeDTO.setTcqList(tcqList);
                List<AppropNoticeSummary> mergeSummaries = appropNoticeSummaryService.mergeData(appropNoticeSummaryMergeDTO);
                for (AppropNoticeSummary mergeData : mergeSummaries) {
                    mergeData.setTcq(TcqEnum.SBJ.getInfo() + "(含铜山、贾汪)");
                    if(mergeData.getRylb().equals("1")){
                        zgSummaries.add(mergeData);
                    }else{
                        jmSummaries.add(mergeData);
                    }
                }
                if(StringUtils.isNotEmpty(zgSummariesTemp)){
                    List<AppropNoticeSummary> notMergeZgSummaries = zgSummariesTemp.stream().filter(x -> !tcqList.contains(x.getTcq())).collect(Collectors.toList());
                    if(StringUtils.isNotEmpty(notMergeZgSummaries)){
                        zgSummaries.addAll(notMergeZgSummaries);
                    }
                }
                if(StringUtils.isNotEmpty(jmSummariesTemp)){
                    List<AppropNoticeSummary> notMergeJmSummaries = jmSummariesTemp.stream().filter(x -> !tcqList.contains(x.getTcq())).collect(Collectors.toList());
                    if(StringUtils.isNotEmpty(notMergeJmSummaries)){
                        jmSummaries.addAll(notMergeJmSummaries);
                    }
                }
                List<String> allTcqs = Arrays.stream(TcqEnum.values()).map(TcqEnum::getInfo).collect(Collectors.toList());
                appropNoticeSummaryMergeDTO.setTcqList(allTcqs);
                List<AppropNoticeSummary> totalSummaries = appropNoticeSummaryService.mergeData(appropNoticeSummaryMergeDTO);
                if(StringUtils.isNotEmpty(totalSummaries)){
                    for (AppropNoticeSummary mergeData : totalSummaries) {
                        mergeData.setTcq("合计");
                        if(mergeData.getRylb().equals("1")){
                            zgSummaries.add(mergeData);
                        }else{
                            jmSummaries.add(mergeData);
                        }
                    }
                }
            } else {
                zgSummaries.addAll(zgSummariesTemp);
                jmSummaries.addAll(jmSummariesTemp);
            }

        }
        try {
            if(StringUtils.isNotEmpty(zgSummaries)){
                for (AppropNoticeSummary zgSummary : zgSummaries) {
                    Field[] fields = zgSummary.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        Class<?> type = field.getType();
                        if(type == BigDecimal.class){
                            Object val = field.get(zgSummary);
                            if(StringUtils.isNotNull(val)){
                                field.set(zgSummary, NumberUtil.div(val.toString(), "10000").setScale(6,RoundingMode.HALF_UP));
                            }
                        }
                    }
                }
            }
            if(StringUtils.isNotEmpty(jmSummaries)){
                for (AppropNoticeSummary jmSummary : jmSummaries) {
                    Field[] fields = jmSummary.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        Class<?> type = field.getType();
                        if(type == BigDecimal.class){
                            Object val = field.get(jmSummary);
                            if(StringUtils.isNotNull(val)){
                                field.set(jmSummary, NumberUtil.div(val.toString(), "10000").setScale(6,RoundingMode.HALF_UP));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("数据转换成万元异常："+e.getMessage(),-1);
        }
        ExcelWriter writer = ExcelUtil.getWriter(true);
        ServletOutputStream out = null;
        try {
            // URLEncoder.encode 防止中文乱码
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("汇总数据", "UTF-8") + ".xlsx");
            out = response.getOutputStream();
            writer.renameSheet("汇总数据");
            writer.getStyleSet().getCellStyleForNumber().setDataFormat((short) 0);
            Sheet sheet = writer.getSheet();
            //合并单元格
            String ljMonth = "";
            String currMonth = "";
            if (appropNoticeViewSummaryDTO.getMonth() == 1) {
                ljMonth = appropNoticeViewSummaryDTO.getYear() + "年1月累计";
            } else {
                ljMonth =appropNoticeViewSummaryDTO.getYear() + "年1-"+appropNoticeViewSummaryDTO.getMonth()+"月累计";
            }
            currMonth = appropNoticeViewSummaryDTO.getYear() + "年"+appropNoticeViewSummaryDTO.getMonth()+"月当月";
            writer.merge(0, 1, 0, 38, "职工医保基金拨付情况(单位：万元)", false);
            writer.merge(2, 2, 1, 19, ljMonth, true);
            writer.merge(2, 2, 20, 38, currMonth, false);
            Field[] zgFields = ZgAppropNoticeSummaryExcelVO.class.getDeclaredFields();
            for (int i = 0; i < zgFields.length; i++) {
                Field field = zgFields[i];
                if (field.isAnnotationPresent(Alias.class)) {
                    Alias alias = field.getAnnotation(Alias.class);
                    if (alias != null) {
                        if (i == 0) {
                            writer.merge(2, 4, 0, 0, "医疗机构归属地", false);
                        } else {
                            writer.merge(3, 4, i, i, alias.value(), false);
                        }
                        if(alias.value().equals("现金")){
                            sheet.setColumnWidth(i,alias.value().getBytes().length*400);
                        }else{
                            sheet.setColumnWidth(i,alias.value().getBytes().length*256);
                        }
                    }
                }
            }
            List<List<Object>> zgRows = new LinkedList<>();
            if (StringUtils.isNotEmpty(zgSummaries)) {
                for (AppropNoticeSummary zgSummary : zgSummaries) {
                    List<Object> row = new LinkedList<>();
                    for (Field field : zgFields) {
                        Field declaredField = zgSummary.getClass().getDeclaredField(field.getName());
                        declaredField.setAccessible(true);
                        Object val = declaredField.get(zgSummary);
                        row.add(val);
                    }
                    zgRows.add(row);
                }
            }
            int zgRowIndex = 5;
            if (StringUtils.isNotEmpty(zgRows)) {
                for (int i = 0; i < zgRows.size(); i++) {
                    List<Object> row = zgRows.get(i);
                    for (int j = 0; j < row.size(); j++) {
                        writer.writeCellValue(j,zgRowIndex + i,row.get(j));
                    }
                }
            }
            int jmRowIndex = 6;
            if(StringUtils.isNotEmpty(zgRows)){
                jmRowIndex += zgRows.size();
            }
            writer.merge(jmRowIndex, jmRowIndex + 1, 0, 32, "居民医保基金拨付情况(单位：万元)", false);
            writer.merge(jmRowIndex + 2, jmRowIndex + 2, 1, 16, ljMonth, true);
            writer.merge(jmRowIndex + 2, jmRowIndex + 2, 17, 32, currMonth, false);
            Field[] jmFields = JmAppropNoticeSummaryExcelVO.class.getDeclaredFields();
            for (int i = 0; i < jmFields.length; i++) {
                Field field = jmFields[i];
                if (field.isAnnotationPresent(Alias.class)) {
                    Alias alias = field.getAnnotation(Alias.class);
                    if (alias != null) {
                        if (i == 0) {
                            writer.merge(jmRowIndex + 2, jmRowIndex + 4, 0, 0, "医疗机构归属地", false);
                        } else {
                            writer.merge(jmRowIndex + 3, jmRowIndex + 4, i, i, alias.value(), false);
                        }
                        int columnWidth = sheet.getColumnWidth(i);
                        int newColumnWidth =  alias.value().getBytes().length*256;
                        if(columnWidth < newColumnWidth){
                            sheet.setColumnWidth(i,newColumnWidth);
                        }
                    }
                }
            }
            List<List<Object>> jmRows = new LinkedList<>();
            if (StringUtils.isNotEmpty(jmSummaries)) {
                for (AppropNoticeSummary jmSummary : jmSummaries) {
                    List<Object> row = new LinkedList<>();
                    for (Field field : jmFields) {
                        Field declaredField = jmSummary.getClass().getDeclaredField(field.getName());
                        declaredField.setAccessible(true);
                        Object val = declaredField.get(jmSummary);
                        row.add(val);
                    }
                    jmRows.add(row);
                }
            }
            jmRowIndex = jmRowIndex + 5;
            if (StringUtils.isNotEmpty(jmRows)) {
                for (int i = 0; i < jmRows.size(); i++) {
                    List<Object> row = jmRows.get(i);
                    for (int j = 0; j < row.size(); j++) {
                        writer.writeCellValue(j,jmRowIndex + i,row.get(j));
                    }
                }
            }
            writer.flush(out, true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭writer，释放内存
            writer.close();
            //关闭输出流
            IoUtil.close(out);
        }
    }

    /**
     * @param appropNoticeViewDataDetailDTO
     * @param response
     * @return void
     * @description //TODO  下载数据明细
     * @author wangxiao
     * @date 2024/5/24
     */
    public void downloadDataDetail(AppropNoticeViewDataDetailDTO appropNoticeViewDataDetailDTO, HttpServletResponse response) {
        AppropNotice appropNotice = appropNoticeMapper.selectOne(new QueryWrapper<AppropNotice>()
                .eq("id", appropNoticeViewDataDetailDTO.getAppropNoticeId()).eq("is_del", "0"));
        QueryWrapper<AppropNoticeDataDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        queryWrapper.eq("year", appropNoticeViewDataDetailDTO.getYear());
        queryWrapper.eq("month", appropNoticeViewDataDetailDTO.getMonth());
        SysUser sysUser = sysUserService.getUser();
        String orgCode = appropNoticeViewDataDetailDTO.getOrgCode();
        String orgName = appropNoticeViewDataDetailDTO.getOrgName();
        if("2".equals(sysUser.getUser_type()) || "3".equals(sysUser.getUser_type())){
            //定点医疗机构
            FixmedinsB fixmedinsB = fixmedinsBService.getOne(Wrappers.<FixmedinsB>lambdaQuery()
                    .eq(FixmedinsB::getFixmedins_code, sysUser.getOrg_code())
                    .eq(FixmedinsB::getIs_del, "0")
            );
            if(StringUtils.isNull(fixmedinsB)){
                throw new CustomException("机构信息不存在",-1);
            }
            orgCode = fixmedinsB.getFixmedins_code();
        }
        if (StringUtils.isNotBlank(orgCode)) {
            queryWrapper.like("org_code", orgCode);
        }
        if (StringUtils.isNotBlank(orgName)) {
            queryWrapper.like("org_name", orgName);
        }
        if (!appropNotice.getTcq().equals(TcqEnum.SBJ.getCode())) {
            //统筹区不是市本级，查看自己统筹区
            queryWrapper.eq("approp_notice_id", appropNoticeViewDataDetailDTO.getAppropNoticeId());
        }
        List<AppropNoticeDataDetail> list = appropNoticeDataDetailService.list(queryWrapper);
        try {
            if(StringUtils.isNotEmpty(list)){
                for (AppropNoticeDataDetail record : list) {
                    Field[] fields = record.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        Class<?> type = field.getType();
                        if(type == BigDecimal.class){
                            Object val = field.get(record);
                            if(StringUtils.isNotNull(val)){
                                field.set(record, NumberUtil.div(val.toString(), "10000").setScale(6,RoundingMode.HALF_UP));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("数据转换成万元异常："+e.getMessage(),-1);
        }
        ExcelWriter writer = ExcelUtil.getWriter(true);
        ServletOutputStream out = null;
        try {
            // URLEncoder.encode 防止中文乱码
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("数据明细", "UTF-8") + ".xlsx");
            out = response.getOutputStream();
            writer.renameSheet("数据明细");
            writer.getStyleSet().getCellStyleForNumber().setDataFormat((short) 0);
            Sheet sheet = writer.getSheet();
            //合并单元格
            String ljMonth = "";
            String currMonth = "";
            if (appropNoticeViewDataDetailDTO.getMonth() == 1) {
                ljMonth = appropNoticeViewDataDetailDTO.getYear() + "年1月累计";
            } else {
                ljMonth =appropNoticeViewDataDetailDTO.getYear() + "年1-"+appropNoticeViewDataDetailDTO.getMonth()+"月累计";
            }
            currMonth = appropNoticeViewDataDetailDTO.getYear() + "年"+appropNoticeViewDataDetailDTO.getMonth()+"月当月";
            writer.merge(0, 1, 0, 73, "各医药机构拨付明细", false);
            writer.merge(2, 3, 0, 3, "基本情况", false);
            writer.merge(2, 3, 4, 41, "职工医保（万元）", false);
            writer.merge(2, 3, 42, 73, "居民医保（万元）", false);
            writer.merge(4, 4, 4, 22, ljMonth, true);
            writer.merge(4, 4, 23, 41, currMonth, false);
            writer.merge(4, 4, 42, 57, ljMonth, true);
            writer.merge(4, 4, 58, 73, currMonth, false);
            Field[] fields = AppropNoticeDataDetailExcelVO.class.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                if (field.isAnnotationPresent(Alias.class)) {
                    Alias alias = field.getAnnotation(Alias.class);
                    if (alias != null) {
                        if (i <= 3) {
                            writer.merge(4, 6, i, i, alias.value(), false);
                        } else {
                            writer.merge(5, 6, i, i, alias.value(), false);
                        }
                        if(alias.value().equals("现金")){
                            sheet.setColumnWidth(i,alias.value().getBytes().length*400);
                        }else{
                            sheet.setColumnWidth(i,alias.value().getBytes().length*256);
                        }
                    }
                }
            }
            List<List<Object>> rows = new LinkedList<>();
            if (StringUtils.isNotEmpty(list)) {
                for (AppropNoticeDataDetail appropNoticeDataDetail : list) {
                    List<Object> row = new LinkedList<>();
                    for (Field field : fields) {
                        Field declaredField = appropNoticeDataDetail.getClass().getDeclaredField(field.getName());
                        declaredField.setAccessible(true);
                        Object val = declaredField.get(appropNoticeDataDetail);
                        row.add(val);
                    }
                    rows.add(row);
                }
            }
            int rowIndex = 7;
            if (StringUtils.isNotEmpty(rows)) {
                for (int i = 0; i < rows.size(); i++) {
                    List<Object> row = rows.get(i);
                    for (int j = 0; j < row.size(); j++) {
                        writer.writeCellValue(j,rowIndex + i,row.get(j));
                    }
                }
            }
            writer.flush(out, true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭writer，释放内存
            writer.close();
            //关闭输出流
            IoUtil.close(out);
        }
    }

    /**
     *
     * @description //TODO  生成拨付分析数据
     * @author wangxiao
     * @date 2024/5/24
     * @param generateApproAnalyseDTO
     * @return com.jsdc.ybpt.vo.ResultInfo
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultInfo generateApproAnalyse(GenerateApproAnalyseDTO generateApproAnalyseDTO) {
        AppropNotice notice = appropNoticeMapper.selectById(generateApproAnalyseDTO.getAppropNoticeId());
        if(notice==null||notice.getIsGenerAnaly().equals("1")) {
            return ResultInfo.error("分析数据已生成，请勿重复生成！");
        }
        List<SysDict> tcqList = sysDictService.list(new QueryWrapper<SysDict>().eq("dict_type", "admdvs-area"));
        QueryWrapper<AppropNotice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("year", generateApproAnalyseDTO.getYear());
        queryWrapper.eq("month", generateApproAnalyseDTO.getMonth());
        queryWrapper.eq("status", "1");
        queryWrapper.eq("is_del","0");
        List<AppropNotice> appropNotices = appropNoticeMapper.selectList(queryWrapper);
        if (StringUtils.isEmpty(appropNotices) || StringUtils.isEmpty(tcqList) || tcqList.size() != appropNotices.size()) {
            return ResultInfo.error("请确认当前全市拨付数据已发送，否则无法生成拨付分析！");
        }
        SysUser sysUser = sysUserService.getUser();
        try {
            for (AppropNotice appropNotice:appropNotices) {
                //生成汇总数据分析
                //List<AppropNoticeSummaryAnalyse> appropNoticeSummaryAnalyses = generateSummaryAnalyse(generateApproAnalyseDTO, sysUser);
                List<AppropNoticeSummaryAnalyse> appropNoticeSummaryAnalyses = generateSummaryAnalyse(appropNotice, sysUser);
                if(StringUtils.isNotEmpty(appropNoticeSummaryAnalyses)){
                    appropNoticeSummaryAnalyseService.saveBatch(appropNoticeSummaryAnalyses);
                }
                //生成明细数据分析
                //List<AppropNoticeDetailAnalyse> appropNoticeDetailAnalyses = generateDetailAnalyse(generateApproAnalyseDTO, sysUser);
                List<AppropNoticeDetailAnalyse> appropNoticeDetailAnalyses = generateDetailAnalyse(appropNotice, sysUser);
                if(StringUtils.isNotEmpty(appropNoticeDetailAnalyses)){
                    appropNoticeDetailAnalyseService.saveBatch(appropNoticeDetailAnalyses);
                }
                AppropNotice appropNoticeContent = new AppropNotice();
                appropNoticeContent.setId(appropNotice.getId());
                appropNoticeContent.setUpdateTime(new Date());
                appropNoticeContent.setUpdateUser(sysUser.getId());
                appropNoticeContent.setIsGenerAnaly("1");
                appropNoticeMapper.updateById(appropNoticeContent);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.error(e.getMessage());
        }
        return ResultInfo.success();
    }

    /**
     * @param generateApproAnalyseDTO
     * @param sysUser
     * @return java.util.List<com.jsdc.ybpt.appropNotice.entity.AppropNoticeSummaryAnalyse>
     * @description //TODO 生成汇总数据分析
     * @author wangxiao
     * @date 2024/5/24
     */
    private List<AppropNoticeSummaryAnalyse> generateSummaryAnalyse(GenerateApproAnalyseDTO generateApproAnalyseDTO, SysUser sysUser) {
       /* String appropNoticeId = generateApproAnalyseDTO.getAppropNoticeId();
        AppropNotice appropNotice = appropNoticeMapper.selectById(appropNoticeId);
        TcqEnum tcqEnum = TcqEnum.getEnumByCode(appropNotice.getTcq());*/
        List<AppropNoticeSummaryAnalyse> appropNoticeSummaryAnalyses = new ArrayList<>();
        Integer currYear = generateApproAnalyseDTO.getYear();
        Integer lastYear = currYear - 1;
        Integer currMonth = generateApproAnalyseDTO.getMonth();
        QueryWrapper<AppropNoticeSummary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id_del","0");
        queryWrapper.eq("year", currYear);
        queryWrapper.eq("month", currMonth);
//        queryWrapper.eq("tcq", tcqEnum.getInfo());
        List<AppropNoticeSummary> dqSummary = appropNoticeSummaryService.list(queryWrapper);
        QueryWrapper<AppropNoticeSummary> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("id_del","0");
        queryWrapper2.eq("year", lastYear);
        queryWrapper2.eq("month", currMonth);
//        queryWrapper2.eq("tcq", tcqEnum.getInfo());
        List<AppropNoticeSummary> tqSummary = appropNoticeSummaryService.list(queryWrapper2);
        AppropNoticeSummaryMergeDTO appropNoticeSummaryMergeDTO = new AppropNoticeSummaryMergeDTO();
        appropNoticeSummaryMergeDTO.setYear(currYear);
        appropNoticeSummaryMergeDTO.setMonth(currMonth);
        TcqEnum[] values = TcqEnum.values();
        List<String> tcqList = Arrays.stream(values).map(TcqEnum::getInfo).collect(Collectors.toList());
        appropNoticeSummaryMergeDTO.setTcqList(tcqList);
        List<AppropNoticeSummary> mergeData = appropNoticeSummaryService.mergeData(appropNoticeSummaryMergeDTO);
        for (AppropNoticeSummary dq : dqSummary) {
            String rylb = dq.getRylb();
            AppropNoticeSummaryAnalyse appropNoticeSummaryAnalyse = new AppropNoticeSummaryAnalyse();
            appropNoticeSummaryAnalyse.setId(IdUtil.simpleUUID());
            appropNoticeSummaryAnalyse.setAppropNoticeId(dq.getAppropNoticeId());
            appropNoticeSummaryAnalyse.setCreateTime(new Date());
            appropNoticeSummaryAnalyse.setCreateUser(sysUser.getId());
            appropNoticeSummaryAnalyse.setIsDel("0");
            appropNoticeSummaryAnalyse.setYear(currYear);
            appropNoticeSummaryAnalyse.setMonth(currMonth);
            appropNoticeSummaryAnalyse.setRylb(rylb);
            appropNoticeSummaryAnalyse.setTcq(dq.getTcq());
            appropNoticeSummaryAnalyse.setYlzfyDqljfse(dq.getLjFszfy());//医疗总费用-当期累计发生额
            List<AppropNoticeSummary> currRYlbMergeData = mergeData.stream().filter(x -> x.getRylb().equals(rylb)).collect(Collectors.toList());
            BigDecimal allLjfszfy = BigDecimal.ZERO;
            if (StringUtils.isNotEmpty(currRYlbMergeData)) {
                allLjfszfy = currRYlbMergeData.get(0).getLjFszfy();
            }
            if(allLjfszfy != BigDecimal.ZERO){
                BigDecimal ylzfybnzb = NumberUtil.mul(NumberUtil.div(dq.getLjFszfy(), allLjfszfy), 100);
                appropNoticeSummaryAnalyse.setYlzfyBnzb(NumberUtil.round(ylzfybnzb, 4));//医疗总费用-本年占比
            }
            appropNoticeSummaryAnalyse.setTcjjDqljfse(dq.getLjTcjjfsje());//统筹基金-当期累计发生额
            BigDecimal allLjTcjjfsje = BigDecimal.ZERO;
            if (StringUtils.isNotEmpty(currRYlbMergeData)) {
                allLjTcjjfsje = currRYlbMergeData.get(0).getLjTcjjfsje();
            }
            if(allLjTcjjfsje != BigDecimal.ZERO){
                BigDecimal tcjjbnzb = NumberUtil.mul(NumberUtil.div(dq.getLjTcjjfsje(), allLjTcjjfsje), 100);
                appropNoticeSummaryAnalyse.setTcjjBnzb(NumberUtil.round(tcjjbnzb, 4));//统筹基金-本年占比
            }
            appropNoticeSummaryAnalyse.setYjfje(dq.getLjJsxj());//应结付金额
            appropNoticeSummaryAnalyse.setYjfe(dq.getLjJjbfxj());//已结付额
            if (StringUtils.isNotEmpty(tqSummary)) {
                List<AppropNoticeSummary> tqs = tqSummary.stream().filter(x -> x.getRylb().equals(rylb)).collect(Collectors.toList());
                if (StringUtils.isNotEmpty(tqs)) {
                    AppropNoticeSummary tq = tqs.get(0);
                    appropNoticeSummaryAnalyse.setYlzfyTqljfse(tq.getLjFszfy());//医疗总费用-上年同期累计发生额
                    if(tq.getLjFszfy()!=BigDecimal.ZERO){
                        BigDecimal ylzfytb = NumberUtil.mul(NumberUtil.div(dq.getLjFszfy(), tq.getLjFszfy()), BigDecimal.valueOf(100));
                        appropNoticeSummaryAnalyse.setYlzfyTb(NumberUtil.round(ylzfytb, 4));//医疗总费用-同比
                    }
                    appropNoticeSummaryAnalyse.setTcjjTqljfse(tq.getLjTcjjfsje());//统筹基金-上年同期累计发生额
                    if(tq.getLjTcjjfsje()!=BigDecimal.ZERO){
                        BigDecimal tcjjtb = NumberUtil.mul(NumberUtil.div(dq.getLjTcjjfsje(), tq.getLjTcjjfsje()), 100);
                        appropNoticeSummaryAnalyse.setTcjjTb(NumberUtil.round(tcjjtb, 4));//统筹基金-同比
                    }
                }
            }
            appropNoticeSummaryAnalyses.add(appropNoticeSummaryAnalyse);
        }
        return appropNoticeSummaryAnalyses;
    }

    private List<AppropNoticeSummaryAnalyse> generateSummaryAnalyse(AppropNotice appropNotice, SysUser sysUser) {
        TcqEnum tcqEnum = TcqEnum.getEnumByCode(appropNotice.getTcq());
        List<AppropNoticeSummaryAnalyse> appropNoticeSummaryAnalyses = new ArrayList<>();
        Integer currYear = appropNotice.getYear();
        Integer lastYear = currYear - 1;
        Integer currMonth = appropNotice.getMonth();
        QueryWrapper<AppropNoticeSummary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del","0");
        queryWrapper.eq("year", currYear);
        queryWrapper.eq("month", currMonth);
        queryWrapper.eq("tcq", tcqEnum.getInfo());
        List<AppropNoticeSummary> dqSummary = appropNoticeSummaryService.list(queryWrapper);
        QueryWrapper<AppropNoticeSummary> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("is_del","0");
        queryWrapper2.eq("year", lastYear);
        queryWrapper2.eq("month", currMonth);
        queryWrapper2.eq("tcq", tcqEnum.getInfo());
        List<AppropNoticeSummary> tqSummary = appropNoticeSummaryService.list(queryWrapper2);
        AppropNoticeSummaryMergeDTO appropNoticeSummaryMergeDTO = new AppropNoticeSummaryMergeDTO();
        appropNoticeSummaryMergeDTO.setYear(currYear);
        appropNoticeSummaryMergeDTO.setMonth(currMonth);
        TcqEnum[] values = TcqEnum.values();
        List<String> tcqList = Arrays.stream(values).map(TcqEnum::getInfo).collect(Collectors.toList());
        appropNoticeSummaryMergeDTO.setTcqList(tcqList);
        List<AppropNoticeSummary> mergeData = appropNoticeSummaryService.mergeData(appropNoticeSummaryMergeDTO);
        for (AppropNoticeSummary dq : dqSummary) {
            String rylb = dq.getRylb();
            AppropNoticeSummaryAnalyse appropNoticeSummaryAnalyse = new AppropNoticeSummaryAnalyse();
            appropNoticeSummaryAnalyse.setId(IdUtil.simpleUUID());
            appropNoticeSummaryAnalyse.setAppropNoticeId(dq.getAppropNoticeId());
            appropNoticeSummaryAnalyse.setCreateTime(new Date());
            appropNoticeSummaryAnalyse.setCreateUser(sysUser.getId());
            appropNoticeSummaryAnalyse.setIsDel("0");
            appropNoticeSummaryAnalyse.setYear(currYear);
            appropNoticeSummaryAnalyse.setMonth(currMonth);
            appropNoticeSummaryAnalyse.setRylb(rylb);
            appropNoticeSummaryAnalyse.setTcq(dq.getTcq());
            appropNoticeSummaryAnalyse.setYlzfyDqljfse(dq.getLjFszfy());//医疗总费用-当期累计发生额
            List<AppropNoticeSummary> currRYlbMergeData = mergeData.stream().filter(x -> x.getRylb().equals(rylb)).collect(Collectors.toList());
            BigDecimal allLjfszfy = BigDecimal.ZERO;
            if (StringUtils.isNotEmpty(currRYlbMergeData)) {
                allLjfszfy = currRYlbMergeData.get(0).getLjFszfy();
            }
            if(allLjfszfy != BigDecimal.ZERO){
                BigDecimal ylzfybnzb = NumberUtil.mul(NumberUtil.div(dq.getLjFszfy(), allLjfszfy), 100);
                appropNoticeSummaryAnalyse.setYlzfyBnzb(NumberUtil.round(ylzfybnzb, 4));//医疗总费用-本年占比
            }
            appropNoticeSummaryAnalyse.setTcjjDqljfse(dq.getLjTcjjfsje());//统筹基金-当期累计发生额
            BigDecimal allLjTcjjfsje = BigDecimal.ZERO;
            if (StringUtils.isNotEmpty(currRYlbMergeData)) {
                allLjTcjjfsje = currRYlbMergeData.get(0).getLjTcjjfsje();
            }
            if(allLjTcjjfsje != BigDecimal.ZERO){
                BigDecimal tcjjbnzb = NumberUtil.mul(NumberUtil.div(dq.getLjTcjjfsje(), allLjTcjjfsje), 100);
                appropNoticeSummaryAnalyse.setTcjjBnzb(NumberUtil.round(tcjjbnzb, 4));//统筹基金-本年占比
            }
            appropNoticeSummaryAnalyse.setYjfje(dq.getLjJsxj());//应结付金额
            appropNoticeSummaryAnalyse.setYjfe(dq.getLjJjbfxj());//已结付额
            if (StringUtils.isNotEmpty(tqSummary)) {
                List<AppropNoticeSummary> tqs = tqSummary.stream().filter(x -> x.getRylb().equals(rylb)).collect(Collectors.toList());
                if (StringUtils.isNotEmpty(tqs)) {
                    AppropNoticeSummary tq = tqs.get(0);
                    appropNoticeSummaryAnalyse.setYlzfyTqljfse(tq.getLjFszfy());//医疗总费用-上年同期累计发生额
                    if(tq.getLjFszfy()!=BigDecimal.ZERO){
                        BigDecimal ylzfytb = NumberUtil.mul(NumberUtil.div(dq.getLjFszfy(), tq.getLjFszfy()), BigDecimal.valueOf(100));
                        appropNoticeSummaryAnalyse.setYlzfyTb(NumberUtil.round(ylzfytb, 4));//医疗总费用-同比
                    }
                    appropNoticeSummaryAnalyse.setTcjjTqljfse(tq.getLjTcjjfsje());//统筹基金-上年同期累计发生额
                    if(tq.getLjTcjjfsje()!=BigDecimal.ZERO){
                        BigDecimal tcjjtb = NumberUtil.mul(NumberUtil.div(dq.getLjTcjjfsje(), tq.getLjTcjjfsje()), 100);
                        appropNoticeSummaryAnalyse.setTcjjTb(NumberUtil.round(tcjjtb, 4));//统筹基金-同比
                    }
                }
            }
            appropNoticeSummaryAnalyses.add(appropNoticeSummaryAnalyse);
        }
        return appropNoticeSummaryAnalyses;
    }

    /**
     *
     * @description //TODO  生成数据明细分析
     * @author wangxiao
     * @date 2024/5/24
     * @param generateApproAnalyseDTO
     * @param sysUser
     * @return java.util.List<com.jsdc.ybpt.appropNotice.entity.AppropNoticeDetailAnalyse>
     */
    private List<AppropNoticeDetailAnalyse> generateDetailAnalyse(GenerateApproAnalyseDTO generateApproAnalyseDTO, SysUser sysUser) {
        List<AppropNoticeDetailAnalyse> appropNoticeDetailAnalyses = new ArrayList<>();
        Integer currYear = generateApproAnalyseDTO.getYear();
        Integer lastYear = currYear - 1;
        Integer currMonth = generateApproAnalyseDTO.getMonth();
        QueryWrapper<AppropNoticeDataDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del","0");
        queryWrapper.eq("year", currYear);
        queryWrapper.eq("month", currMonth);;
        List<AppropNoticeDataDetail> dqDataDetail = appropNoticeDataDetailService.list(queryWrapper);
        QueryWrapper<AppropNoticeDataDetail> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("is_del","0");
        queryWrapper2.eq("year", lastYear);
        queryWrapper2.eq("month", currMonth);
        List<AppropNoticeDataDetail> tqDataDetail = appropNoticeDataDetailService.list(queryWrapper2);
        AppropNoticeDataDetailMergeDTO appropNoticeDataDetailMergeDTO = new AppropNoticeDataDetailMergeDTO();
        appropNoticeDataDetailMergeDTO.setYear(currYear);
        appropNoticeDataDetailMergeDTO.setMonth(currMonth);
        AppropNoticeDataDetail mergeData = appropNoticeDataDetailService.mergeData(appropNoticeDataDetailMergeDTO);
        for (AppropNoticeDataDetail dq : dqDataDetail) {
            String orgCode = dq.getOrgCode();
            AppropNoticeDetailAnalyse appropNoticeDetailAnalyse = new AppropNoticeDetailAnalyse();
            appropNoticeDetailAnalyse.setId(IdUtil.simpleUUID());
            appropNoticeDetailAnalyse.setAppropNoticeId(dq.getAppropNoticeId());
            appropNoticeDetailAnalyse.setCreateTime(new Date());
            appropNoticeDetailAnalyse.setCreateUser(sysUser.getId());
            appropNoticeDetailAnalyse.setIsDel("0");
            appropNoticeDetailAnalyse.setYear(currYear);
            appropNoticeDetailAnalyse.setMonth(currMonth);
            appropNoticeDetailAnalyse.setOrgCode(dq.getOrgCode());
            appropNoticeDetailAnalyse.setOrgName(dq.getOrgName());
            appropNoticeDetailAnalyse.setTcq(dq.getTcq());
            appropNoticeDetailAnalyse.setJb(dq.getJb());
            appropNoticeDetailAnalyse.setZgYlzfyDqljfse(dq.getZgLjFszfy());//职工医疗总费用-当期累计发生额
            BigDecimal allZgLjfszfy = mergeData.getZgLjFszfy();
            if(allZgLjfszfy != BigDecimal.ZERO){
                BigDecimal zgylzfybnzb = NumberUtil.mul(NumberUtil.div(dq.getZgLjFszfy(), allZgLjfszfy), 100);
                appropNoticeDetailAnalyse.setZgYlzfyBnzb(NumberUtil.round(zgylzfybnzb, 4));//职工医疗总费用-本年占比
            }
            appropNoticeDetailAnalyse.setZgTcjjDqljfse(dq.getZgLjTcjjfsje());//职工统筹基金-当期累计发生额
            BigDecimal allZgLjTcjjfsje = mergeData.getZgLjTcjjfsje();
            if(allZgLjTcjjfsje != BigDecimal.ZERO){
                BigDecimal zgtcjjbnzb = NumberUtil.mul(NumberUtil.div(dq.getZgLjTcjjfsje(), allZgLjTcjjfsje), 100);
                appropNoticeDetailAnalyse.setZgTcjjBnzb(NumberUtil.round(zgtcjjbnzb, 4));//职工统筹基金-本年占比
            }
            appropNoticeDetailAnalyse.setZgYjfje(dq.getZgLjJsxj());//职工应结付金额
            appropNoticeDetailAnalyse.setZgYjfe(dq.getZgLjJjbfxj());//职工已结付额
            appropNoticeDetailAnalyse.setJmYlzfyDqljfse(dq.getJmLjFszfy());//居民医疗总费用-当期累计发生额
            BigDecimal allJmLjfszfy = mergeData.getJmLjFszfy();
            if(allJmLjfszfy != BigDecimal.ZERO){
                BigDecimal jmylzfybnzb = NumberUtil.mul(NumberUtil.div(dq.getJmLjFszfy(), allJmLjfszfy), 100);
                appropNoticeDetailAnalyse.setJmYlzfyBnzb(NumberUtil.round(jmylzfybnzb, 4));//居民医疗总费用-本年占比
            }
            appropNoticeDetailAnalyse.setJmTcjjDqljfse(dq.getJmLjTcjjfsje());//居民统筹基金-当期累计发生额
            BigDecimal allJmLjTcjjfsje = mergeData.getJmLjTcjjfsje();
            if(allJmLjTcjjfsje != BigDecimal.ZERO){
                BigDecimal jmtcjjbnzb = NumberUtil.mul(NumberUtil.div(dq.getJmLjTcjjfsje(), allJmLjTcjjfsje), 100);
                appropNoticeDetailAnalyse.setJmTcjjBnzb(NumberUtil.round(jmtcjjbnzb, 4));//居民统筹基金-本年占比
            }
            appropNoticeDetailAnalyse.setJmYjfje(dq.getJmLjJsxj());//居民应结付金额
            appropNoticeDetailAnalyse.setJmYjfe(dq.getJmLjJjbfxj());//居民已结付额
            if (StringUtils.isNotEmpty(tqDataDetail)) {
                List<AppropNoticeDataDetail> tqs = tqDataDetail.stream().filter(x -> x.getOrgCode().equals(orgCode)).collect(Collectors.toList());
                if (StringUtils.isNotEmpty(tqs)) {
                    AppropNoticeDataDetail tq = tqs.get(0);
                    appropNoticeDetailAnalyse.setZgYlzfyTqljfse(tq.getZgLjFszfy());//职工医疗总费用-上年同期累计发生额
                    if(tq.getZgLjFszfy() != BigDecimal.ZERO){
                        BigDecimal zgylzfytb = NumberUtil.mul(NumberUtil.div(dq.getZgLjFszfy(), tq.getZgLjFszfy()), 100);
                        appropNoticeDetailAnalyse.setZgYlzfyTb(NumberUtil.round(zgylzfytb, 4));//职工医疗总费用-同比
                    }
                    appropNoticeDetailAnalyse.setJmYlzfyTqljfse(tq.getJmLjFszfy());//居民医疗总费用-上年同期累计发生额
                    if(tq.getJmLjFszfy() != BigDecimal.ZERO){
                        BigDecimal jmylzfytb = NumberUtil.mul(NumberUtil.div(dq.getJmLjFszfy(), tq.getJmLjFszfy()), 100);
                        appropNoticeDetailAnalyse.setJmYlzfyTb(NumberUtil.round(jmylzfytb, 4));//居民医疗总费用-同比

                    }
                    appropNoticeDetailAnalyse.setZgTcjjTqljfse(tq.getZgLjTcjjfsje());//职工统筹基金-上年同期累计发生额
                    if(tq.getZgLjTcjjfsje() != BigDecimal.ZERO){
                        BigDecimal zgtcjjtb = NumberUtil.mul(NumberUtil.div(dq.getZgLjTcjjfsje(), tq.getZgLjTcjjfsje()), 100);
                        appropNoticeDetailAnalyse.setZgTcjjTb(NumberUtil.round(zgtcjjtb, 4));//职工统筹基金-同比
                    }
                    appropNoticeDetailAnalyse.setJmTcjjTqljfse(tq.getJmLjTcjjfsje());//居民统筹基金-上年同期累计发生额
                    if(tq.getJmLjTcjjfsje() != BigDecimal.ZERO){
                        BigDecimal jmtcjjtb = NumberUtil.mul(NumberUtil.div(dq.getJmLjTcjjfsje(), tq.getJmLjTcjjfsje()), 100);
                        appropNoticeDetailAnalyse.setJmTcjjTb(NumberUtil.round(jmtcjjtb, 4));//居民统筹基金-同比
                    }
                }
            }
            appropNoticeDetailAnalyses.add(appropNoticeDetailAnalyse);
        }
        return appropNoticeDetailAnalyses;
    }

    private List<AppropNoticeDetailAnalyse> generateDetailAnalyse(AppropNotice appropNotice, SysUser sysUser) {
//        String appropNoticeId = generateApproAnalyseDTO.getAppropNoticeId();
//        AppropNotice appropNotice = appropNoticeMapper.selectById(appropNoticeId);
        TcqEnum tcqEnum = TcqEnum.getEnumByCode(appropNotice.getTcq());
        List<AppropNoticeDetailAnalyse> appropNoticeDetailAnalyses = new ArrayList<>();
        Integer currYear = appropNotice.getYear();
        Integer lastYear = currYear - 1;
        Integer currMonth = appropNotice.getMonth();
        QueryWrapper<AppropNoticeDataDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del","0");
        queryWrapper.eq("year", currYear);
        queryWrapper.eq("month", currMonth);
        queryWrapper.eq("tcq", tcqEnum.getInfo());
        List<AppropNoticeDataDetail> dqDataDetail = appropNoticeDataDetailService.list(queryWrapper);
        QueryWrapper<AppropNoticeDataDetail> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("is_del","0");
        queryWrapper2.eq("year", lastYear);
        queryWrapper2.eq("month", currMonth);
        queryWrapper.eq("tcq", tcqEnum.getInfo());
        List<AppropNoticeDataDetail> tqDataDetail = appropNoticeDataDetailService.list(queryWrapper2);
        AppropNoticeDataDetailMergeDTO appropNoticeDataDetailMergeDTO = new AppropNoticeDataDetailMergeDTO();
        appropNoticeDataDetailMergeDTO.setYear(currYear);
        appropNoticeDataDetailMergeDTO.setMonth(currMonth);
        AppropNoticeDataDetail mergeData = appropNoticeDataDetailService.mergeData(appropNoticeDataDetailMergeDTO);
        for (AppropNoticeDataDetail dq : dqDataDetail) {
            String orgCode = dq.getOrgCode();
            AppropNoticeDetailAnalyse appropNoticeDetailAnalyse = new AppropNoticeDetailAnalyse();
            appropNoticeDetailAnalyse.setId(IdUtil.simpleUUID());
            appropNoticeDetailAnalyse.setAppropNoticeId(dq.getAppropNoticeId());
            appropNoticeDetailAnalyse.setCreateTime(new Date());
            appropNoticeDetailAnalyse.setCreateUser(sysUser.getId());
            appropNoticeDetailAnalyse.setIsDel("0");
            appropNoticeDetailAnalyse.setYear(currYear);
            appropNoticeDetailAnalyse.setMonth(currMonth);
            appropNoticeDetailAnalyse.setOrgCode(dq.getOrgCode());
            appropNoticeDetailAnalyse.setOrgName(dq.getOrgName());
            appropNoticeDetailAnalyse.setTcq(dq.getTcq());
            appropNoticeDetailAnalyse.setJb(dq.getJb());
            appropNoticeDetailAnalyse.setZgYlzfyDqljfse(dq.getZgLjFszfy());//职工医疗总费用-当期累计发生额
            BigDecimal allZgLjfszfy = mergeData.getZgLjFszfy();
            if(allZgLjfszfy != BigDecimal.ZERO){
                BigDecimal zgylzfybnzb = NumberUtil.mul(NumberUtil.div(dq.getZgLjFszfy(), allZgLjfszfy), 100);
                appropNoticeDetailAnalyse.setZgYlzfyBnzb(NumberUtil.round(zgylzfybnzb, 4));//职工医疗总费用-本年占比
            }
            appropNoticeDetailAnalyse.setZgTcjjDqljfse(dq.getZgLjTcjjfsje());//职工统筹基金-当期累计发生额
            BigDecimal allZgLjTcjjfsje = mergeData.getZgLjTcjjfsje();
            if(allZgLjTcjjfsje != BigDecimal.ZERO){
                BigDecimal zgtcjjbnzb = NumberUtil.mul(NumberUtil.div(dq.getZgLjTcjjfsje(), allZgLjTcjjfsje), 100);
                appropNoticeDetailAnalyse.setZgTcjjBnzb(NumberUtil.round(zgtcjjbnzb, 4));//职工统筹基金-本年占比
            }
            appropNoticeDetailAnalyse.setZgYjfje(dq.getZgLjJsxj());//职工应结付金额
            appropNoticeDetailAnalyse.setZgYjfe(dq.getZgLjJjbfxj());//职工已结付额
            appropNoticeDetailAnalyse.setJmYlzfyDqljfse(dq.getJmLjFszfy());//居民医疗总费用-当期累计发生额
            BigDecimal allJmLjfszfy = mergeData.getJmLjFszfy();
            if(allJmLjfszfy != BigDecimal.ZERO){
                BigDecimal jmylzfybnzb = NumberUtil.mul(NumberUtil.div(dq.getJmLjFszfy(), allJmLjfszfy), 100);
                appropNoticeDetailAnalyse.setJmYlzfyBnzb(NumberUtil.round(jmylzfybnzb, 4));//居民医疗总费用-本年占比
            }
            appropNoticeDetailAnalyse.setJmTcjjDqljfse(dq.getJmLjTcjjfsje());//居民统筹基金-当期累计发生额
            BigDecimal allJmLjTcjjfsje = mergeData.getJmLjTcjjfsje();
            if(allJmLjTcjjfsje != BigDecimal.ZERO){
                BigDecimal jmtcjjbnzb = NumberUtil.mul(NumberUtil.div(dq.getJmLjTcjjfsje(), allJmLjTcjjfsje), 100);
                appropNoticeDetailAnalyse.setJmTcjjBnzb(NumberUtil.round(jmtcjjbnzb, 4));//居民统筹基金-本年占比
            }
            appropNoticeDetailAnalyse.setJmYjfje(dq.getJmLjJsxj());//居民应结付金额
            appropNoticeDetailAnalyse.setJmYjfe(dq.getJmLjJjbfxj());//居民已结付额
            if (StringUtils.isNotEmpty(tqDataDetail)) {
                List<AppropNoticeDataDetail> tqs = tqDataDetail.stream().filter(x -> x.getOrgCode().equals(orgCode)).collect(Collectors.toList());
                if (StringUtils.isNotEmpty(tqs)) {
                    AppropNoticeDataDetail tq = tqs.get(0);
                    appropNoticeDetailAnalyse.setZgYlzfyTqljfse(tq.getZgLjFszfy());//职工医疗总费用-上年同期累计发生额
                    if(tq.getZgLjFszfy() != BigDecimal.ZERO){
                        BigDecimal zgylzfytb = NumberUtil.mul(NumberUtil.div(dq.getZgLjFszfy(), tq.getZgLjFszfy()), 100);
                        appropNoticeDetailAnalyse.setZgYlzfyTb(NumberUtil.round(zgylzfytb, 4));//职工医疗总费用-同比
                    }
                    appropNoticeDetailAnalyse.setJmYlzfyTqljfse(tq.getJmLjFszfy());//居民医疗总费用-上年同期累计发生额
                    if(tq.getJmLjFszfy() != BigDecimal.ZERO){
                        BigDecimal jmylzfytb = NumberUtil.mul(NumberUtil.div(dq.getJmLjFszfy(), tq.getJmLjFszfy()), 100);
                        appropNoticeDetailAnalyse.setJmYlzfyTb(NumberUtil.round(jmylzfytb, 4));//居民医疗总费用-同比

                    }
                    appropNoticeDetailAnalyse.setZgTcjjTqljfse(tq.getZgLjTcjjfsje());//职工统筹基金-上年同期累计发生额
                    if(tq.getZgLjTcjjfsje() != BigDecimal.ZERO){
                        BigDecimal zgtcjjtb = NumberUtil.mul(NumberUtil.div(dq.getZgLjTcjjfsje(), tq.getZgLjTcjjfsje()), 100);
                        appropNoticeDetailAnalyse.setZgTcjjTb(NumberUtil.round(zgtcjjtb, 4));//职工统筹基金-同比
                    }
                    appropNoticeDetailAnalyse.setJmTcjjTqljfse(tq.getJmLjTcjjfsje());//居民统筹基金-上年同期累计发生额
                    if(tq.getJmLjTcjjfsje() != BigDecimal.ZERO){
                        BigDecimal jmtcjjtb = NumberUtil.mul(NumberUtil.div(dq.getJmLjTcjjfsje(), tq.getJmLjTcjjfsje()), 100);
                        appropNoticeDetailAnalyse.setJmTcjjTb(NumberUtil.round(jmtcjjtb, 4));//居民统筹基金-同比
                    }
                }
            }
            appropNoticeDetailAnalyses.add(appropNoticeDetailAnalyse);
        }
        return appropNoticeDetailAnalyses;
    }

    /**
     *
     * @description //TODO  查看汇总数据分析
     * @author wangxiao
     * @date 2024/5/24
     * @param viewSummaryAnalyseDTO
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String, Object> viewSummaryAnalyse(ViewSummaryAnalyseDTO viewSummaryAnalyseDTO){
        Map<String, Object> map = new HashMap<>();
        String tcq = viewSummaryAnalyseDTO.getTcq();
        TcqEnum tcqEnum = TcqEnum.getEnumByCode(tcq);
        QueryWrapper<AppropNoticeSummaryAnalyse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("year", viewSummaryAnalyseDTO.getYear());
        queryWrapper.eq("month", viewSummaryAnalyseDTO.getMonth());
        queryWrapper.eq("is_del", "0");
        if (!tcqEnum.getCode().equals(TcqEnum.SBJ.getCode())) {
            //统筹区不是市本级，查看自己统筹区
            queryWrapper.eq("approp_notice_id", viewSummaryAnalyseDTO.getAppropNoticeId());
        }
        List<AppropNoticeSummaryAnalyse> zgSummaryAnalyses = new ArrayList<>();
        List<AppropNoticeSummaryAnalyse> jmSummaryAnalyses = new ArrayList<>();
        List<AppropNoticeSummaryAnalyse> appropNoticeSummaryAnalyses = appropNoticeSummaryAnalyseService.list(queryWrapper);
        if(StringUtils.isNotEmpty(appropNoticeSummaryAnalyses)){
            List<AppropNoticeSummaryAnalyse> zgSummaryAnalysesTemp = appropNoticeSummaryAnalyses.stream().filter(x -> x.getRylb().equals("1")).collect(Collectors.toList());
            List<AppropNoticeSummaryAnalyse> jmSummaryAnalysesTemp = appropNoticeSummaryAnalyses.stream().filter(x -> x.getRylb().equals("2")).collect(Collectors.toList());
            if (tcqEnum.getCode().equals(TcqEnum.SBJ.getCode())) {
                AppropNoticeSummaryMergeDTO appropNoticeSummaryMergeDTO = new AppropNoticeSummaryMergeDTO();
                appropNoticeSummaryMergeDTO.setYear(viewSummaryAnalyseDTO.getYear());
                appropNoticeSummaryMergeDTO.setMonth(viewSummaryAnalyseDTO.getMonth());
                TcqEnum[] values = TcqEnum.values();
                List<String> allTcqs = Arrays.stream(values).map(TcqEnum::getInfo).collect(Collectors.toList());
                appropNoticeSummaryMergeDTO.setTcqList(allTcqs);
                List<AppropNoticeSummary> mergeSummary = appropNoticeSummaryService.mergeData(appropNoticeSummaryMergeDTO);
                BigDecimal allLjfszfy = BigDecimal.ZERO;
                BigDecimal allLjTcjjfsje = BigDecimal.ZERO;
                //统筹区是市本级，将贾汪和铜山合并到市本级
                AppropNoticeSummaryAnalyseMergeDTO appropNoticeSummaryAnalyseMergeDTO = new AppropNoticeSummaryAnalyseMergeDTO();
                appropNoticeSummaryAnalyseMergeDTO.setYear(viewSummaryAnalyseDTO.getYear());
                appropNoticeSummaryAnalyseMergeDTO.setMonth(viewSummaryAnalyseDTO.getMonth());
                List<String> tcqList = new ArrayList<>();
                tcqList.add(TcqEnum.SBJ.getInfo());
                tcqList.add(TcqEnum.TSQ.getInfo());
                tcqList.add(TcqEnum.JWQ.getInfo());
                appropNoticeSummaryAnalyseMergeDTO.setTcqList(tcqList);
                List<AppropNoticeSummaryAnalyse> mergeDataList = appropNoticeSummaryAnalyseService.mergeData(appropNoticeSummaryAnalyseMergeDTO);
                for (AppropNoticeSummaryAnalyse mergeData : mergeDataList) {
                    mergeData.setTcq(TcqEnum.SBJ.getInfo() + "(含铜山、贾汪)");
                    //合并后重新计算同比和占比
                    if(mergeData.getYlzfyTqljfse()!=BigDecimal.ZERO){
                        BigDecimal ylzfytb = NumberUtil.mul(NumberUtil.div(mergeData.getYlzfyDqljfse(), mergeData.getYlzfyTqljfse()), BigDecimal.valueOf(100));
                        mergeData.setYlzfyTb(NumberUtil.round(ylzfytb, 4));//医疗总费用-同比
                    }
                    if(mergeData.getTcjjTqljfse()!=BigDecimal.ZERO){
                        BigDecimal tcjjtb = NumberUtil.mul(NumberUtil.div(mergeData.getTcjjDqljfse(), mergeData.getTcjjTqljfse()), 100);
                        mergeData.setTcjjTb(NumberUtil.round(tcjjtb, 4));//统筹基金-同比
                    }
                    List<AppropNoticeSummary> currRYlbMergeData = mergeSummary.stream().filter(x -> x.getRylb().equals(mergeData.getRylb())).collect(Collectors.toList());
                    if (StringUtils.isNotEmpty(currRYlbMergeData)) {
                        allLjfszfy = currRYlbMergeData.get(0).getLjFszfy();
                        allLjTcjjfsje = currRYlbMergeData.get(0).getLjTcjjfsje();
                    }
                    if(allLjfszfy != BigDecimal.ZERO){
                        BigDecimal ylzfybnzb = NumberUtil.mul(NumberUtil.div(mergeData.getYlzfyDqljfse(), allLjfszfy), 100);
                        mergeData.setYlzfyBnzb(NumberUtil.round(ylzfybnzb, 4));//医疗总费用-本年占比
                    }
                    if(allLjTcjjfsje != BigDecimal.ZERO){
                        BigDecimal tcjjbnzb = NumberUtil.mul(NumberUtil.div(mergeData.getTcjjDqljfse(), allLjTcjjfsje), 100);
                        mergeData.setTcjjBnzb(NumberUtil.round(tcjjbnzb, 4));//统筹基金-本年占比
                    }
                    if(mergeData.getRylb().equals("1")){
                        zgSummaryAnalyses.add(mergeData);
                    }else{
                        jmSummaryAnalyses.add(mergeData);
                    }
                }
                if(StringUtils.isNotEmpty(zgSummaryAnalysesTemp)){
                    List<AppropNoticeSummaryAnalyse> notMergeZgSummaryAnalyses = zgSummaryAnalysesTemp.stream().filter(x -> !tcqList.contains(x.getTcq())).collect(Collectors.toList());
                    if(StringUtils.isNotEmpty(notMergeZgSummaryAnalyses)){
                        zgSummaryAnalyses.addAll(notMergeZgSummaryAnalyses);
                    }
                }
                if(StringUtils.isNotEmpty(jmSummaryAnalysesTemp)){
                    List<AppropNoticeSummaryAnalyse> notMergeJmSummaryAnalyses = jmSummaryAnalysesTemp.stream().filter(x -> !tcqList.contains(x.getTcq())).collect(Collectors.toList());
                    if(StringUtils.isNotEmpty(notMergeJmSummaryAnalyses)){
                        jmSummaryAnalyses.addAll(notMergeJmSummaryAnalyses);
                    }
                }
                appropNoticeSummaryAnalyseMergeDTO.setTcqList(allTcqs);
                List<AppropNoticeSummaryAnalyse> totalSummaryAnalyses = appropNoticeSummaryAnalyseService.mergeData(appropNoticeSummaryAnalyseMergeDTO);
                if(StringUtils.isNotEmpty(totalSummaryAnalyses)){
                    for (AppropNoticeSummaryAnalyse mergeData : totalSummaryAnalyses) {
                        mergeData.setTcq("合计");
                        //合并后重新计算同比和占比
                        if(mergeData.getYlzfyTqljfse()!=BigDecimal.ZERO){
                            BigDecimal ylzfytb = NumberUtil.mul(NumberUtil.div(mergeData.getYlzfyDqljfse(), mergeData.getYlzfyTqljfse()), 100);
                            mergeData.setYlzfyTb(NumberUtil.round(ylzfytb, 4));//医疗总费用-同比
                        }
                        if(mergeData.getTcjjTqljfse()!=BigDecimal.ZERO){
                            BigDecimal tcjjtb = NumberUtil.mul(NumberUtil.div(mergeData.getTcjjDqljfse(), mergeData.getTcjjTqljfse()), 100);
                            mergeData.setTcjjTb(NumberUtil.round(tcjjtb, 4));//统筹基金-同比
                        }
                        List<AppropNoticeSummary> currRYlbMergeData = mergeSummary.stream().filter(x -> x.getRylb().equals(mergeData.getRylb())).collect(Collectors.toList());
                        if (StringUtils.isNotEmpty(currRYlbMergeData)) {
                            allLjfszfy = currRYlbMergeData.get(0).getLjFszfy();
                            allLjTcjjfsje = currRYlbMergeData.get(0).getLjTcjjfsje();
                        }
                        if(allLjfszfy != BigDecimal.ZERO){
                            BigDecimal ylzfybnzb = NumberUtil.mul(NumberUtil.div(mergeData.getYlzfyDqljfse(), allLjfszfy), 100);
                            mergeData.setYlzfyBnzb(NumberUtil.round(ylzfybnzb, 4));//医疗总费用-本年占比
                        }
                        if(allLjTcjjfsje != BigDecimal.ZERO){
                            BigDecimal tcjjbnzb = NumberUtil.mul(NumberUtil.div(mergeData.getTcjjDqljfse(), allLjTcjjfsje), 100);
                            mergeData.setTcjjBnzb(NumberUtil.round(tcjjbnzb, 4));//统筹基金-本年占比
                        }
                        if(mergeData.getRylb().equals("1")){
                            zgSummaryAnalyses.add(mergeData);
                        }else{
                            jmSummaryAnalyses.add(mergeData);
                        }
                    }
                }
            }else{
                zgSummaryAnalyses.addAll(zgSummaryAnalysesTemp);
                jmSummaryAnalyses.addAll(jmSummaryAnalysesTemp);
            }
        }
        try {
            if(StringUtils.isNotEmpty(zgSummaryAnalyses)){
                for (AppropNoticeSummaryAnalyse zgSummaryAnalyse : zgSummaryAnalyses) {
                    Field[] fields = zgSummaryAnalyse.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        Class<?> type = field.getType();
                        String name = field.getName();
                        if(type == BigDecimal.class && !name.equals("ylzfyTb") && !name.equals("ylzfyBnzb")
                                && !name.equals("tcjjTb") && !name.equals("tcjjBnzb")){
                            Object val = field.get(zgSummaryAnalyse);
                            if(StringUtils.isNotNull(val)){
                                field.set(zgSummaryAnalyse, NumberUtil.div(val.toString(), "10000").setScale(6,RoundingMode.HALF_UP));
                            }
                        }
                    }
                }
            }
            if(StringUtils.isNotEmpty(jmSummaryAnalyses)){
                for (AppropNoticeSummaryAnalyse jmSummaryAnalyse : jmSummaryAnalyses) {
                    Field[] fields = jmSummaryAnalyse.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        Class<?> type = field.getType();
                        String name = field.getName();
                        if(type == BigDecimal.class && !name.equals("ylzfyTb") && !name.equals("ylzfyBnzb")
                                && !name.equals("tcjjTb") && !name.equals("tcjjBnzb")){
                            Object val = field.get(jmSummaryAnalyse);
                            if(StringUtils.isNotNull(val)){
                                field.set(jmSummaryAnalyse, NumberUtil.div(val.toString(), "10000").setScale(6,RoundingMode.HALF_UP));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("数据转换成万元异常："+e.getMessage(),-1);
        }
        map.put("zgSummaryAnalyses", zgSummaryAnalyses);
        map.put("jmSummaryAnalyses", jmSummaryAnalyses);
        return map;
    }

    /**
     *
     * @description //TODO  查看数据明细分析
     * @author wangxiao
     * @date 2024/5/24
     * @param viewDetailAnalyseDTO
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.jsdc.ybpt.appropNotice.entity.AppropNoticeDetailAnalyse>
     */
    public Page<AppropNoticeDetailAnalyse> viewDetailAnalyse(ViewDetailAnalyseDTO viewDetailAnalyseDTO) {
        Page<AppropNoticeDetailAnalyse> page = new Page<>(viewDetailAnalyseDTO.getPageNo(), viewDetailAnalyseDTO.getPageSize());
        QueryWrapper<AppropNoticeDetailAnalyse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        queryWrapper.eq("year", viewDetailAnalyseDTO.getYear());
        queryWrapper.eq("month", viewDetailAnalyseDTO.getMonth());
        SysUser sysUser = sysUserService.getUser();
        String orgCode = viewDetailAnalyseDTO.getOrgCode();
        String orgName = viewDetailAnalyseDTO.getOrgName();
        if("2".equals(sysUser.getUser_type()) || "3".equals(sysUser.getUser_type())){
            //定点医疗机构
            FixmedinsB fixmedinsB = fixmedinsBService.getOne(Wrappers.<FixmedinsB>lambdaQuery()
                    .eq(FixmedinsB::getFixmedins_code, sysUser.getOrg_code())
                    .eq(FixmedinsB::getIs_del, "0")
            );
            if(StringUtils.isNull(fixmedinsB)){
                throw new CustomException("机构信息不存在",-1);
            }
            orgCode = fixmedinsB.getFixmedins_code();
        }
        if (StringUtils.isNotBlank(orgCode)) {
            queryWrapper.like("org_code", orgCode);
        }
        if (StringUtils.isNotBlank(orgName)) {
            queryWrapper.like("org_Name", orgName);
        }
        String tcq = viewDetailAnalyseDTO.getTcq();
        TcqEnum tcqEnum = TcqEnum.getEnumByCode(tcq);
        if (!tcqEnum.getCode().equals(TcqEnum.SBJ.getCode())) {
            //统筹区不是市本级，之查看自己机构的
            queryWrapper.eq("approp_notice_id", viewDetailAnalyseDTO.getAppropNoticeId());
        }
        Page<AppropNoticeDetailAnalyse> appropNoticeDetailAnalysePage = appropNoticeDetailAnalyseService.page(page, queryWrapper);
        try {
            List<AppropNoticeDetailAnalyse> records = appropNoticeDetailAnalysePage.getRecords();
            if(StringUtils.isNotEmpty(records)){
                for (AppropNoticeDetailAnalyse record : records) {
                    Field[] fields = record.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        Class<?> type = field.getType();
                        String name = field.getName();
                        if(type == BigDecimal.class && !name.equals("zgYlzfyTb") && !name.equals("zgYlzfyBnzb")
                                && !name.equals("zgTcjjTb") && !name.equals("zgTcjjBnzb")
                                && !name.equals("jmYlzfyTb") && !name.equals("jmYlzfyBnzb")
                                && !name.equals("jmTcjjTb") && !name.equals("jmTcjjBnzb")){
                            Object val = field.get(record);
                            if(StringUtils.isNotNull(val)){
                                field.set(record, NumberUtil.div(val.toString(), "10000").setScale(6,RoundingMode.HALF_UP));
                            }
                        }
                    }
                }
            }
            appropNoticeDetailAnalysePage.setRecords(records);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("数据转换成万元异常："+e.getMessage(),-1);
        }
        return appropNoticeDetailAnalysePage;
    }

    /**
     *
     * @description //TODO  下载汇总数据分析
     * @author wangxiao
     * @date 2024/5/27
     * @param viewSummaryAnalyseDTO
     * @param response
     * @return void
     */
    public void downloadSummaryAnalyse(ViewSummaryAnalyseDTO viewSummaryAnalyseDTO, HttpServletResponse response) {
        String tcq = viewSummaryAnalyseDTO.getTcq();
        TcqEnum tcqEnum = TcqEnum.getEnumByCode(tcq);
        QueryWrapper<AppropNoticeSummaryAnalyse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("year", viewSummaryAnalyseDTO.getYear());
        queryWrapper.eq("month", viewSummaryAnalyseDTO.getMonth());
        queryWrapper.eq("is_del","0");
        if (!tcqEnum.getCode().equals(TcqEnum.SBJ.getCode())) {
            //统筹区不是市本级，查看自己统筹区
            queryWrapper.eq("approp_notice_id", viewSummaryAnalyseDTO.getAppropNoticeId());
        }
        List<AppropNoticeSummaryAnalyse> zgSummaryAnalyses = new ArrayList<>();
        List<AppropNoticeSummaryAnalyse> jmSummaryAnalyses = new ArrayList<>();
        List<AppropNoticeSummaryAnalyse> appropNoticeSummaryAnalyses = appropNoticeSummaryAnalyseService.list(queryWrapper);
        if(StringUtils.isNotEmpty(appropNoticeSummaryAnalyses)){
            List<AppropNoticeSummaryAnalyse> zgSummaryAnalysesTemp = appropNoticeSummaryAnalyses.stream().filter(x -> x.getRylb().equals("1")).collect(Collectors.toList());
            List<AppropNoticeSummaryAnalyse> jmSummaryAnalysesTemp = appropNoticeSummaryAnalyses.stream().filter(x -> x.getRylb().equals("2")).collect(Collectors.toList());
            if (tcqEnum.getCode().equals(TcqEnum.SBJ.getCode())) {
                AppropNoticeSummaryMergeDTO appropNoticeSummaryMergeDTO = new AppropNoticeSummaryMergeDTO();
                appropNoticeSummaryMergeDTO.setYear(viewSummaryAnalyseDTO.getYear());
                appropNoticeSummaryMergeDTO.setMonth(viewSummaryAnalyseDTO.getMonth());
                TcqEnum[] values = TcqEnum.values();
                List<String> allTcqs = Arrays.stream(values).map(TcqEnum::getInfo).collect(Collectors.toList());
                appropNoticeSummaryMergeDTO.setTcqList(allTcqs);
                List<AppropNoticeSummary> mergeSummary = appropNoticeSummaryService.mergeData(appropNoticeSummaryMergeDTO);
                BigDecimal allLjfszfy = BigDecimal.ZERO;
                BigDecimal allLjTcjjfsje = BigDecimal.ZERO;
                //统筹区是市本级，将贾汪和铜山合并到市本级
                AppropNoticeSummaryAnalyseMergeDTO appropNoticeSummaryAnalyseMergeDTO = new AppropNoticeSummaryAnalyseMergeDTO();
                appropNoticeSummaryAnalyseMergeDTO.setYear(viewSummaryAnalyseDTO.getYear());
                appropNoticeSummaryAnalyseMergeDTO.setMonth(viewSummaryAnalyseDTO.getMonth());
                List<String> tcqList = new ArrayList<>();
                tcqList.add(TcqEnum.SBJ.getInfo());
                tcqList.add(TcqEnum.TSQ.getInfo());
                tcqList.add(TcqEnum.JWQ.getInfo());
                appropNoticeSummaryAnalyseMergeDTO.setTcqList(tcqList);
                List<AppropNoticeSummaryAnalyse> mergeDataList = appropNoticeSummaryAnalyseService.mergeData(appropNoticeSummaryAnalyseMergeDTO);
                for (AppropNoticeSummaryAnalyse mergeData : mergeDataList) {
                    mergeData.setTcq(TcqEnum.SBJ.getInfo() + "(含铜山、贾汪)");
                    //合并后重新计算同比和占比
                    if(mergeData.getYlzfyTqljfse()!=BigDecimal.ZERO){
                        BigDecimal ylzfytb = NumberUtil.mul(NumberUtil.div(mergeData.getYlzfyDqljfse(), mergeData.getYlzfyTqljfse()), 100);
                        mergeData.setYlzfyTb(NumberUtil.round(ylzfytb, 4));//医疗总费用-同比
                    }
                    if(mergeData.getTcjjTqljfse()!=BigDecimal.ZERO){
                        BigDecimal tcjjtb = NumberUtil.mul(NumberUtil.div(mergeData.getTcjjDqljfse(), mergeData.getTcjjTqljfse()), 100);
                        mergeData.setTcjjTb(NumberUtil.round(tcjjtb, 4));//统筹基金-同比
                    }
                    List<AppropNoticeSummary> currRYlbMergeData = mergeSummary.stream().filter(x -> x.getRylb().equals(mergeData.getRylb())).collect(Collectors.toList());
                    if (StringUtils.isNotEmpty(currRYlbMergeData)) {
                        allLjfszfy = currRYlbMergeData.get(0).getLjFszfy();
                        allLjTcjjfsje = currRYlbMergeData.get(0).getLjTcjjfsje();
                    }
                    if(allLjfszfy != BigDecimal.ZERO){
                        BigDecimal ylzfybnzb = NumberUtil.mul(NumberUtil.div(mergeData.getYlzfyDqljfse(), allLjfszfy), 100);
                        mergeData.setYlzfyBnzb(NumberUtil.round(ylzfybnzb, 4));//医疗总费用-本年占比
                    }
                    if(allLjTcjjfsje != BigDecimal.ZERO){
                        BigDecimal tcjjbnzb = NumberUtil.mul(NumberUtil.div(mergeData.getTcjjDqljfse(), allLjTcjjfsje), 100);
                        mergeData.setTcjjBnzb(NumberUtil.round(tcjjbnzb, 4));//统筹基金-本年占比
                    }
                    if(mergeData.getRylb().equals("1")){
                        zgSummaryAnalyses.add(mergeData);
                    }else{
                        jmSummaryAnalyses.add(mergeData);
                    }
                }
                if(StringUtils.isNotEmpty(zgSummaryAnalysesTemp)){
                    List<AppropNoticeSummaryAnalyse> notMergeZgSummaryAnalyses = zgSummaryAnalysesTemp.stream().filter(x -> !tcqList.contains(x.getTcq())).collect(Collectors.toList());
                    if(StringUtils.isNotEmpty(notMergeZgSummaryAnalyses)){
                        zgSummaryAnalyses.addAll(notMergeZgSummaryAnalyses);
                    }
                }
                if(StringUtils.isNotEmpty(jmSummaryAnalysesTemp)){
                    List<AppropNoticeSummaryAnalyse> notMergeJmSummaryAnalyses = jmSummaryAnalysesTemp.stream().filter(x -> !tcqList.contains(x.getTcq())).collect(Collectors.toList());
                    if(StringUtils.isNotEmpty(notMergeJmSummaryAnalyses)){
                        jmSummaryAnalyses.addAll(notMergeJmSummaryAnalyses);
                    }
                }
                appropNoticeSummaryAnalyseMergeDTO.setTcqList(allTcqs);
                List<AppropNoticeSummaryAnalyse> totalSummaryAnalyses = appropNoticeSummaryAnalyseService.mergeData(appropNoticeSummaryAnalyseMergeDTO);
                if(StringUtils.isNotEmpty(totalSummaryAnalyses)){
                    for (AppropNoticeSummaryAnalyse mergeData : totalSummaryAnalyses) {
                        mergeData.setTcq("合计");
                        //合并后重新计算同比和占比
                        if(mergeData.getYlzfyTqljfse()!=BigDecimal.ZERO){
                            BigDecimal ylzfytb = NumberUtil.mul(NumberUtil.div(mergeData.getYlzfyDqljfse(), mergeData.getYlzfyTqljfse()), BigDecimal.valueOf(100));
                            mergeData.setYlzfyTb(NumberUtil.round(ylzfytb, 4));//医疗总费用-同比
                        }
                        if(mergeData.getTcjjTqljfse()!=BigDecimal.ZERO){
                            BigDecimal tcjjtb = NumberUtil.mul(NumberUtil.div(mergeData.getTcjjDqljfse(), mergeData.getTcjjTqljfse()), 100);
                            mergeData.setTcjjTb(NumberUtil.round(tcjjtb, 4));//统筹基金-同比
                        }
                        List<AppropNoticeSummary> currRYlbMergeData = mergeSummary.stream().filter(x -> x.getRylb().equals(mergeData.getRylb())).collect(Collectors.toList());
                        if (StringUtils.isNotEmpty(currRYlbMergeData)) {
                            allLjfszfy = currRYlbMergeData.get(0).getLjFszfy();
                            allLjTcjjfsje = currRYlbMergeData.get(0).getLjTcjjfsje();
                        }
                        if(allLjfszfy != BigDecimal.ZERO){
                            BigDecimal ylzfybnzb = NumberUtil.mul(NumberUtil.div(mergeData.getYlzfyDqljfse(), allLjfszfy), 100);
                            mergeData.setYlzfyBnzb(NumberUtil.round(ylzfybnzb, 4));//医疗总费用-本年占比
                        }
                        if(allLjTcjjfsje != BigDecimal.ZERO){
                            BigDecimal tcjjbnzb = NumberUtil.mul(NumberUtil.div(mergeData.getTcjjDqljfse(), allLjTcjjfsje), 100);
                            mergeData.setTcjjBnzb(NumberUtil.round(tcjjbnzb, 4));//统筹基金-本年占比
                        }
                        if(mergeData.getRylb().equals("1")){
                            zgSummaryAnalyses.add(mergeData);
                        }else{
                            jmSummaryAnalyses.add(mergeData);
                        }
                    }
                }
            }else{
                zgSummaryAnalyses.addAll(zgSummaryAnalysesTemp);
                jmSummaryAnalyses.addAll(jmSummaryAnalysesTemp);
            }
        }
        try {
            if(StringUtils.isNotEmpty(zgSummaryAnalyses)){
                for (AppropNoticeSummaryAnalyse zgSummaryAnalyse : zgSummaryAnalyses) {
                    Field[] fields = zgSummaryAnalyse.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        Class<?> type = field.getType();
                        String name = field.getName();
                        if(type == BigDecimal.class && !name.equals("ylzfyTb") && !name.equals("ylzfyBnzb")
                                && !name.equals("tcjjTb") && !name.equals("tcjjBnzb")){
                            Object val = field.get(zgSummaryAnalyse);
                            if(StringUtils.isNotNull(val)){
                                field.set(zgSummaryAnalyse, NumberUtil.div(val.toString(), "10000").setScale(6,RoundingMode.HALF_UP));
                            }
                        }
                    }
                }
            }
            if(StringUtils.isNotEmpty(jmSummaryAnalyses)){
                for (AppropNoticeSummaryAnalyse jmSummaryAnalyse : jmSummaryAnalyses) {
                    Field[] fields = jmSummaryAnalyse.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        Class<?> type = field.getType();
                        String name = field.getName();
                        if(type == BigDecimal.class && !name.equals("ylzfyTb") && !name.equals("ylzfyBnzb")
                                && !name.equals("tcjjTb") && !name.equals("tcjjBnzb")){
                            Object val = field.get(jmSummaryAnalyse);
                            if(StringUtils.isNotNull(val)){
                                field.set(jmSummaryAnalyse, NumberUtil.div(val.toString(), "10000").setScale(6,RoundingMode.HALF_UP));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("数据转换成万元异常："+e.getMessage(),-1);
        }
        ExcelWriter writer = ExcelUtil.getWriter(true);
        ServletOutputStream out = null;
        try {
            // URLEncoder.encode 防止中文乱码
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("汇总数据分析", "UTF-8") + ".xlsx");
            out = response.getOutputStream();
            writer.getStyleSet().getCellStyleForNumber().setDataFormat((short) 0);
            writer.renameSheet("汇总数据分析");
            Sheet sheet = writer.getSheet();
            writer.merge(0, 0, 1, 10, "职工汇总数据分析(单位：万元)", false);
            writer.merge(1, 1, 1, 4, "医疗总费用", true);
            writer.merge(1, 1, 5, 8, "统筹基金", false);
            SummaryAnalyseExcelVO summaryAnalyseExcelVO = new SummaryAnalyseExcelVO();
            Field[] fields = summaryAnalyseExcelVO.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                if (field.isAnnotationPresent(Alias.class)) {
                    Alias alias = field.getAnnotation(Alias.class);
                    if (alias != null) {
                        if (i == 0) {
                            writer.merge(0, 2, 0, 0, alias.value(), false);
                        }else if(i == 9 || i==10){
                            writer.merge(1, 2, i, i, alias.value(), false);
                        } else {
                            writer.writeCellValue(i, 2, alias.value());
                        }
                        if(alias.value().equals("同比")){
                            sheet.setColumnWidth(i,alias.value().getBytes().length*350);
                        }else{
                            sheet.setColumnWidth(i,alias.value().getBytes().length*256);
                        }
                    }
                }
            }
            List<List<Object>> zgRows = new LinkedList<>();
            if (StringUtils.isNotEmpty(zgSummaryAnalyses)) {
                for (AppropNoticeSummaryAnalyse zgSummaryAnalyse : zgSummaryAnalyses) {
                    List<Object> row = new LinkedList<>();
                    for (Field field : fields) {
                        Field declaredField = zgSummaryAnalyse.getClass().getDeclaredField(field.getName());
                        declaredField.setAccessible(true);
                        Object val = declaredField.get(zgSummaryAnalyse);
                        if(field.getName().equals("ylzfyTb") || field.getName().equals("ylzfyBnzb")
                                || field.getName().equals("tcjjTb") || field.getName().equals("tcjjBnzb")){
                            if(StringUtils.isNotNull(val)){
                                val = val+"%";
                            }
                        }
                        row.add(val);
                    }
                    zgRows.add(row);
                }
            }
            int zgRowIndex = 3;
            if (StringUtils.isNotEmpty(zgRows)) {
                for (int i = 0; i < zgRows.size(); i++) {
                    List<Object> row = zgRows.get(i);
                    for (int j = 0; j < row.size(); j++) {
                        writer.writeCellValue(j,zgRowIndex + i,row.get(j));
                    }
                }
            }
            int jmRowIndex = 5;
            if(StringUtils.isNotEmpty(zgRows)){
                jmRowIndex += zgRows.size();
            }
            writer.merge(jmRowIndex, jmRowIndex, 1, 10, "居民汇总数据分析(单位：万元)", false);
            writer.merge(jmRowIndex+1, jmRowIndex+1, 1, 4, "医疗总费用", true);
            writer.merge(jmRowIndex+1, jmRowIndex+1, 5, 8, "统筹基金", false);
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                if (field.isAnnotationPresent(Alias.class)) {
                    Alias alias = field.getAnnotation(Alias.class);
                    if (alias != null) {
                        if (i == 0) {
                            writer.merge(jmRowIndex, jmRowIndex + 2, 0, 0, alias.value(), false);
                        }else if(i == 9 || i==10){
                            writer.merge(jmRowIndex + 1, jmRowIndex + 2, i, i, alias.value(), false);
                        } else {
                            writer.writeCellValue(i, jmRowIndex + 2, alias.value());
                        }
                        if(alias.value().equals("同比")){
                            sheet.setColumnWidth(i,alias.value().getBytes().length*350);
                        }else{
                            sheet.setColumnWidth(i,alias.value().getBytes().length*256);
                        }
                    }
                }
            }
            List<List<Object>> jmRows = new LinkedList<>();
            if (StringUtils.isNotEmpty(jmSummaryAnalyses)) {
                for (AppropNoticeSummaryAnalyse jmSummaryAnalyse : jmSummaryAnalyses) {
                    List<Object> row = new LinkedList<>();
                    for (Field field : fields) {
                        Field declaredField = jmSummaryAnalyse.getClass().getDeclaredField(field.getName());
                        declaredField.setAccessible(true);
                        Object val = declaredField.get(jmSummaryAnalyse);
                        if(field.getName().equals("ylzfyTb") || field.getName().equals("ylzfyBnzb")
                                || field.getName().equals("tcjjTb") || field.getName().equals("tcjjBnzb")){
                            if(StringUtils.isNotNull(val)){
                                val = val+"%";
                            }
                        }
                        row.add(val);
                    }
                    jmRows.add(row);
                }
            }
            jmRowIndex = jmRowIndex + 3;
            if (StringUtils.isNotEmpty(jmRows)) {
                for (int i = 0; i < jmRows.size(); i++) {
                    List<Object> row = jmRows.get(i);
                    for (int j = 0; j < row.size(); j++) {
                        writer.writeCellValue(j,jmRowIndex + i,row.get(j));
                    }
                }
            }
            writer.flush(out, true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭writer，释放内存
            writer.close();
            //关闭输出流
            IoUtil.close(out);
        }
    }

    /**
     *
     * @description //TODO  下载数据明细分析
     * @author wangxiao
     * @date 2024/5/27
     * @param viewDetailAnalyseDTO
     * @param response
     * @return void
     */
    public void downloadDetailAnalyse(ViewDetailAnalyseDTO viewDetailAnalyseDTO, HttpServletResponse response) {
        QueryWrapper<AppropNoticeDetailAnalyse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        queryWrapper.eq("year", viewDetailAnalyseDTO.getYear());
        queryWrapper.eq("month", viewDetailAnalyseDTO.getMonth());
        SysUser sysUser = sysUserService.getUser();
        String orgCode = viewDetailAnalyseDTO.getOrgCode();
        String orgName = viewDetailAnalyseDTO.getOrgName();
        if("2".equals(sysUser.getUser_type()) || "3".equals(sysUser.getUser_type())){
            //定点医疗机构
            FixmedinsB fixmedinsB = fixmedinsBService.getOne(Wrappers.<FixmedinsB>lambdaQuery()
                    .eq(FixmedinsB::getFixmedins_code, sysUser.getOrg_code())
                    .eq(FixmedinsB::getIs_del, "0")
            );
            if(StringUtils.isNull(fixmedinsB)){
                throw new CustomException("机构信息不存在",-1);
            }
            orgCode = fixmedinsB.getFixmedins_code();
        }
        if (StringUtils.isNotBlank(orgCode)) {
            queryWrapper.like("org_code", orgCode);
        }
        if (StringUtils.isNotBlank(orgName)) {
            queryWrapper.like("org_name", orgName);
        }
        String tcq = viewDetailAnalyseDTO.getTcq();
        TcqEnum tcqEnum = TcqEnum.getEnumByCode(tcq);
        if (!tcqEnum.getCode().equals(TcqEnum.SBJ.getCode())) {
            //统筹区不是市本级，之查看自己机构的
            queryWrapper.eq("approp_notice_id", viewDetailAnalyseDTO.getAppropNoticeId());
        }
        List<AppropNoticeDetailAnalyse> appropNoticeDetailAnalyses = appropNoticeDetailAnalyseService.list(queryWrapper);
        try {
            if(StringUtils.isNotEmpty(appropNoticeDetailAnalyses)){
                for (AppropNoticeDetailAnalyse appropNoticeDetailAnalyse : appropNoticeDetailAnalyses) {
                    Field[] fields = appropNoticeDetailAnalyse.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        Class<?> type = field.getType();
                        String name = field.getName();
                        if(type == BigDecimal.class && !name.equals("zgYlzfyTb") && !name.equals("zgYlzfyBnzb")
                                && !name.equals("zgTcjjTb") && !name.equals("zgTcjjBnzb")
                                && !name.equals("jmYlzfyTb") && !name.equals("jmYlzfyBnzb")
                                && !name.equals("jmTcjjTb") && !name.equals("jmTcjjBnzb")){
                            Object val = field.get(appropNoticeDetailAnalyse);
                            if(StringUtils.isNotNull(val)){
                                field.set(appropNoticeDetailAnalyse, NumberUtil.div(val.toString(), "10000").setScale(6,RoundingMode.HALF_UP));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("数据转换成万元异常："+e.getMessage(),-1);
        }
        ExcelWriter writer = ExcelUtil.getWriter(true);
        ServletOutputStream out = null;
        try {
            // URLEncoder.encode 防止中文乱码
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("数据明细分析", "UTF-8") + ".xlsx");
            out = response.getOutputStream();
            writer.getStyleSet().getCellStyleForNumber().setDataFormat((short) 0);
            writer.renameSheet("数据明细分析");
            Sheet sheet = writer.getSheet();
            writer.merge(0, 0, 4, 13, "职工(单位：万元)", false);
            writer.merge(0, 0, 14, 23, "居民(单位：万元)", false);
            writer.merge(1, 1, 4, 7, "医疗总费用", true);
            writer.merge(1, 1, 8, 11, "统筹基金", false);
            writer.merge(1, 1, 14, 17, "医疗总费用", true);
            writer.merge(1, 1, 18, 21, "统筹基金", false);
            DetailAnalyseExcelVO detailAnalyseExcelVO = new DetailAnalyseExcelVO();
            Field[] fields = detailAnalyseExcelVO.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                if (field.isAnnotationPresent(Alias.class)) {
                    Alias alias = field.getAnnotation(Alias.class);
                    if (alias != null) {
                        if (i <= 3) {
                            writer.merge(0, 2, i, i, alias.value(), false);
                        }else if(i == 12 || i==13 || i == 22 || i==23){
                            writer.merge(1, 2, i, i, alias.value(), false);
                        } else {
                            writer.writeCellValue(i, 2, alias.value());
                        }
                        if(alias.value().equals("同比")){
                            sheet.setColumnWidth(i,alias.value().getBytes().length*350);
                        }else{
                            sheet.setColumnWidth(i,alias.value().getBytes().length*256);
                        }
                    }
                }
            }
            List<List<Object>> rows = new LinkedList<>();
            if (StringUtils.isNotEmpty(appropNoticeDetailAnalyses)) {
                for (AppropNoticeDetailAnalyse appropNoticeDetailAnalysis : appropNoticeDetailAnalyses) {
                    List<Object> row = new LinkedList<>();
                    for (Field field : fields) {
                        Field declaredField = appropNoticeDetailAnalysis.getClass().getDeclaredField(field.getName());
                        declaredField.setAccessible(true);
                        Object val = declaredField.get(appropNoticeDetailAnalysis);
                        if(field.getName().equals("zgYlzfyTb") || field.getName().equals("zgYlzfyBnzb")
                            || field.getName().equals("zgTcjjTb") || field.getName().equals("zgTcjjBnzb")
                            || field.getName().equals("jmYlzfyTb") || field.getName().equals("jmYlzfyBnzb")
                                || field.getName().equals("jmTcjjTb") || field.getName().equals("jmTcjjBnzb")){
                            if(StringUtils.isNotNull(val)){
                                val = val+"%";
                            }
                        }
                        row.add(val);
                    }
                    rows.add(row);
                }
            }
            int rowIndex = 3;
            if (StringUtils.isNotEmpty(rows)) {
                for (int i = 0; i < rows.size(); i++) {
                    List<Object> row = rows.get(i);
                    for (int j = 0; j < row.size(); j++) {
                        writer.writeCellValue(j,rowIndex + i,row.get(j));
                    }
                }
            }
            writer.flush(out, true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭writer，释放内存
            writer.close();
            //关闭输出流
            IoUtil.close(out);
        }
    }

    /**
     * @description 处理往年数据
     * @author wangxiao
     * @date 2024/6/3
     * @param file
     * @return com.jsdc.ybpt.vo.ResultInfo
     */
    @Transactional
    public ResultInfo dealLastYearAnaselyData(MultipartFile file) {
        SysUser sysUser = sysUserService.getUser();
        List<LastYearDataVO> lastYearDataVOList = readerFile(file,LastYearDataVO.class,1);
        if(StringUtils.isNotEmpty(lastYearDataVOList)){
            List<AppropNoticeSummary> appropNoticeSummaries = generateLastYearSummary(lastYearDataVOList, sysUser);
            if(StringUtils.isNotEmpty(appropNoticeSummaries)){
                    appropNoticeSummaryService.saveBatch(appropNoticeSummaries);
            }
           List<AppropNoticeDataDetail> appropNoticeDataDetails = generateLastYeaDataDetail(lastYearDataVOList, sysUser);
            if(StringUtils.isNotEmpty(appropNoticeDataDetails)){
                appropNoticeDataDetailService.saveBatch(appropNoticeDataDetails);
            }
        }
        return ResultInfo.success();
    }

    /**
     * @description 生成往年汇总数据
     * @author wangxiao
     * @date 2024/6/3
     * @param lastYearDataVOList
     * @param sysUser
     * @return java.util.List<com.jsdc.ybpt.appropNotice.entity.AppropNoticeSummary>
     */
    private List<AppropNoticeSummary> generateLastYearSummary(List<LastYearDataVO> lastYearDataVOList,SysUser sysUser) {
        List<AppropNoticeSummary> appropNoticeSummaries = new ArrayList<>();
        Set<String> tcqSet = lastYearDataVOList.stream().map(LastYearDataVO::getTcq).collect(Collectors.toSet());
        if (StringUtils.isEmpty(tcqSet)) {
            throw new CustomException("请检查文件，医保区划不能为空",-1);
        }
        for (String tcq : tcqSet) {
            Set<String> jsqSet = lastYearDataVOList.stream().filter(x -> x.getTcq().equals(tcq)).map(LastYearDataVO::getJsq).collect(Collectors.toSet());
            List<LastYearDataVO> currTcqData = lastYearDataVOList.stream().filter(x -> x.getTcq().equals(tcq)).collect(Collectors.toList());
            if (StringUtils.isEmpty(jsqSet)) {
                throw new CustomException("请检查文件，结算期不能为空",-1);
            }
            for (String jsq : jsqSet) {
                Integer currYear = Integer.valueOf(jsq.substring(0, 4));
                Integer currMonth = Integer.valueOf(jsq.substring(4));
                try {
                    List<LastYearDataVO> currJsqData = currTcqData.stream().filter(x -> x.getJsq().equals(jsq)).collect(Collectors.toList());
                    if(StringUtils.isNotEmpty(currJsqData)){
                        //生成汇总数据
                        for (int j = 1; j <3; j++) {
                            AppropNoticeSummary summary = new AppropNoticeSummary();
                            summary.setId(IdUtil.simpleUUID());
                            summary.setCreateTime(new Date());
                            summary.setCreateUser(sysUser.getId());
                            summary.setYear(currYear);
                            summary.setMonth(currMonth);
                            summary.setTcq(tcq);
                            Field[] declaredFields = summary.getClass().getDeclaredFields();
                            for (Field field : declaredFields) {
                                field.setAccessible(true);
                                Class<?> type = field.getType();
                                if(type == BigDecimal.class){
                                    field.set(summary,BigDecimal.ZERO);
                                }
                            }
                            summary.setRylb(j+"");
                            BigDecimal ljFszfy = BigDecimal.ZERO;
                            BigDecimal ljTcjjfsje = BigDecimal.ZERO;
                            if(j==1){
                                //职工
                                List<LastYearDataVO> zgData = currJsqData.stream().filter(x ->x.getXzlx().equals(InsutypeEnum.ZGJBYLBX.getInfo()) || x.getXzlx().equals(InsutypeEnum.SYBX.getInfo()))
                                        .collect(Collectors.toList());
                                //当前月数据累计
                                if(StringUtils.isNotEmpty(zgData)){
                                    for (LastYearDataVO zgDatum : zgData) {
                                        ljFszfy = NumberUtil.add(ljFszfy,zgDatum.getYlfze());
                                        ljTcjjfsje = NumberUtil.add(ljTcjjfsje,zgDatum.getTcjj(),zgDatum.getSyjj());
                                    }
                                }
                            }else{
                                //居民
                                List<LastYearDataVO> jmData = currJsqData.stream().filter(x -> x.getXzlx().equals(InsutypeEnum.CXJMJBYLBX.getInfo()))
                                        .collect(Collectors.toList());
                                //当前月数据累计
                                if(StringUtils.isNotEmpty(jmData)){
                                    for (LastYearDataVO jmDatum : jmData) {
                                        ljFszfy = NumberUtil.add(ljFszfy,jmDatum.getYlfze());
                                        ljTcjjfsje = NumberUtil.add(ljTcjjfsje,jmDatum.getTcjj());
                                    }
                                }
                            }
                            //小于当前月的数据累计
                            if(currMonth > 1 && StringUtils.isNotEmpty(appropNoticeSummaries)){
                                List<AppropNoticeSummary> ltCurrMonthSummaries = appropNoticeSummaries.stream().filter(x -> x.getMonth() < currMonth
                                        && x.getTcq().equals(tcq) && x.getRylb().equals(summary.getRylb())).collect(Collectors.toList());
                                if(StringUtils.isNotEmpty(ltCurrMonthSummaries)){
                                    for (AppropNoticeSummary data : ltCurrMonthSummaries) {
                                        ljFszfy = NumberUtil.add(ljFszfy,data.getLjFszfy());
                                        ljTcjjfsje = NumberUtil.add(ljTcjjfsje,data.getLjTcjjfsje());
                                    }
                                }
                            }
                            summary.setLjFszfy(ljFszfy);
                            summary.setLjTcjjfsje(ljTcjjfsje);
                            appropNoticeSummaries.add(summary);
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return appropNoticeSummaries;
    }

    /**
     * @description 生成往年数据明细
     * @author wangxiao
     * @date 2024/6/3
     * @param lastYearDataVOList
     * @param sysUser
     * @return java.util.List<com.jsdc.ybpt.appropNotice.entity.AppropNoticeDataDetail>
     */
    private List<AppropNoticeDataDetail> generateLastYeaDataDetail(List<LastYearDataVO> lastYearDataVOList,SysUser sysUser) {
        List<AppropNoticeDataDetail> appropNoticeDataDetails = new ArrayList<>();
        Set<String> orgCodeSet = lastYearDataVOList.stream().map(LastYearDataVO::getOrgCode).collect(Collectors.toSet());
        if (StringUtils.isEmpty(orgCodeSet)) {
            throw new CustomException("请检查文件，机构编码不能为空",-1);
        }
        for (String orgCode : orgCodeSet) {
            Set<String> jsqSet = lastYearDataVOList.stream().filter(x -> x.getOrgCode().equals(orgCode)).map(LastYearDataVO::getJsq).collect(Collectors.toSet());
            List<LastYearDataVO> currOrgCodeData = lastYearDataVOList.stream().filter(x -> x.getOrgCode().equals(orgCode)).collect(Collectors.toList());
            if (StringUtils.isEmpty(jsqSet)) {
                throw new CustomException("请检查文件，结算期不能为空",-1);
            }
            for (String jsq : jsqSet) {
                Integer currYear = Integer.valueOf(jsq.substring(0, 4));
                Integer currMonth = Integer.valueOf(jsq.substring(4));
                try {
                    List<LastYearDataVO> currJsqData = currOrgCodeData.stream().filter(x -> x.getJsq().equals(jsq)).collect(Collectors.toList());
                    if(StringUtils.isNotEmpty(currJsqData)){
                        AppropNoticeDataDetail dataDetail = new AppropNoticeDataDetail();
                        dataDetail.setId(IdUtil.simpleUUID());
                        dataDetail.setCreateTime(new Date());
                        dataDetail.setCreateUser(sysUser.getId());
                        dataDetail.setYear(currYear);
                        dataDetail.setMonth(currMonth);
                        dataDetail.setOrgCode(orgCode);
                        dataDetail.setTcq(currJsqData.get(0).getTcq());
                        dataDetail.setOrgName(currJsqData.get(0).getOrgName());
                        dataDetail.setJb(currJsqData.get(0).getJb());
                        Field[] declaredFields = dataDetail.getClass().getDeclaredFields();
                        for (Field field : declaredFields) {
                            field.setAccessible(true);
                            Class<?> type = field.getType();
                            if(type == BigDecimal.class){
                                field.set(dataDetail,BigDecimal.ZERO);
                            }
                        }
                        BigDecimal zgLjFszfy = BigDecimal.ZERO;
                        BigDecimal zgljTcjjfsje = BigDecimal.ZERO;
                        BigDecimal jmLjFszfy = BigDecimal.ZERO;
                        BigDecimal jmljTcjjfsje = BigDecimal.ZERO;
                        //职工
                        List<LastYearDataVO> currJsqZgData = currJsqData.stream().filter(x -> x.getXzlx().equals(InsutypeEnum.ZGJBYLBX.getInfo()) || x.getXzlx().equals(InsutypeEnum.SYBX.getInfo()))
                                .collect(Collectors.toList());
                        if(StringUtils.isNotEmpty(currJsqZgData)){
                            for (LastYearDataVO zgDatum : currJsqZgData) {
                                zgLjFszfy = NumberUtil.add(zgLjFszfy,zgDatum.getYlfze());
                                zgljTcjjfsje = NumberUtil.add(zgljTcjjfsje,zgDatum.getTcjj(),zgDatum.getSyjj());
                            }
                        }
                        List<LastYearDataVO> currJsqJmData = currJsqData.stream().filter(x -> x.getXzlx().equals(InsutypeEnum.CXJMJBYLBX.getInfo()))
                                .collect(Collectors.toList());
                        if(StringUtils.isNotEmpty(currJsqJmData)){
                            for (LastYearDataVO jmDatum : currJsqJmData) {
                                jmLjFszfy = NumberUtil.add(jmLjFszfy,jmDatum.getYlfze());
                                jmljTcjjfsje = NumberUtil.add(jmljTcjjfsje,jmDatum.getTcjj());
                            }
                        }
                        //小于当前月的数据累计
                        if(currMonth >1 && StringUtils.isNotEmpty(appropNoticeDataDetails)){
                            List<AppropNoticeDataDetail> ltCurrMonthDataDetails = appropNoticeDataDetails.stream().filter(x -> x.getMonth() < currMonth && x.getOrgCode().equals(orgCode)).collect(Collectors.toList());
                            if(StringUtils.isNotEmpty(ltCurrMonthDataDetails)){
                                for (AppropNoticeDataDetail data : ltCurrMonthDataDetails) {
                                    zgLjFszfy = NumberUtil.add(zgLjFszfy,data.getZgLjFszfy());
                                    zgljTcjjfsje = NumberUtil.add(zgljTcjjfsje,data.getZgLjTcjjfsje());
                                    jmLjFszfy = NumberUtil.add(jmLjFszfy,data.getJmLjFszfy());
                                    jmljTcjjfsje = NumberUtil.add(jmljTcjjfsje,data.getJmLjTcjjfsje());
                                }
                            }
                        }
                        dataDetail.setZgLjFszfy(zgLjFszfy);
                        dataDetail.setZgLjTcjjfsje(zgljTcjjfsje);
                        dataDetail.setJmLjFszfy(jmLjFszfy);
                        dataDetail.setJmLjTcjjfsje(jmljTcjjfsje);
                        appropNoticeDataDetails.add(dataDetail);
                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return appropNoticeDataDetails;
    }

    public List<String> authList(){
        List<String> list = new ArrayList<>();
        SysUser sysUser = sysUserService.getUser();
        log.info("authList sysUser:{}", JSON.toJSONString(sysUser));
        List<SysUserRole> sysUserRoles = sysUserRoleService.list(new QueryWrapper<SysUserRole>().eq("user_id",sysUser.getId()));
        log.info("authList sysUserRoles:{}", JSON.toJSONString(sysUserRoles));
        List<String> roleIds = Optional.ofNullable(sysUserRoles).orElse(Lists.newArrayList()).stream().map(s->s.getRole_id()).collect(Collectors.toList());
        log.info("authList roleIds:{}", JSON.toJSONString(roleIds));
        if(CollectionUtils.isEmpty(roleIds))
            return list;
        List<SysRole> roles = sysRoleService.queryByRoleIds(roleIds);
        log.info("authList roles:{}", JSON.toJSONString(roleIds));
        roles.forEach(r->{
            if(AUTH_GENERATE.equals(r.getRole_symbol()))
                list.add(r.getRole_symbol());
        });
        return list;
    }
}


