package com.jsdc.ybpt.service;


import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.*;
import com.jsdc.ybpt.model.FileInfo;
import com.jsdc.ybpt.model.SysFile;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_check.*;
import com.jsdc.ybpt.util.FastDfs.FastDfsParams;
import com.jsdc.ybpt.util.FastDfs.FastDfsUtil;
import com.jsdc.ybpt.util.IdCardNumberMethod;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.vo.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.entity.ContentType;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author ：苹果
 * @Description：
 * @Date ：2022/5/26 15:42
 * @Modified By：
 */
@Service
@RequiredArgsConstructor
public class PersonSubscribeRecordService extends BaseService<PersonSubscribeRecord> {
    private static final Logger log = LoggerFactory.getLogger(PersonSubscribeRecordService.class);

    @Autowired
    private PersonSubscribeRecordMapper personSubscribeRecordMapper;
    @Autowired
    private EmpSubscribeRecordMapper empSubscribeRecordMapper;
    @Autowired
    private OrgSubscribeRulesMapper orgSubscribeRulesMapper;
    @Autowired
    private final SysFileMapper sysFileMapper;
    @Autowired
    private PhysExamConfigMapper physExamConfigMapper;
    @Autowired
    private SysFileService sysFileService;
    @Autowired
    private OrganizationInfoMapper organizationInfoMapper;
    @Autowired
    private PackInfoMapper packInfoMapper;

    @Value("${uploadPath}")
    private String uploadPath;

    @Value("${printPath}")
    private String printPath;

    @Value("${uploadtjreportPath}")
    private String uploadtjreportPath;

    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private FastDfsUtil fastDfsUtil;
    @Autowired
    private FileInfoMapper fileInfoMapper;

    public ResultInfo findPersonSubscribeRecordOne(String id) {
        return ResultInfo.success(this.personSubscribeRecordMapper.findPersonSubscribeRecordOne(id));
    }


    public ResultInfo findPersonSubscribeRecord(String esid, String cname, String year, String oname, String pname, String sched, Integer pageNo, Integer pageSize) {
        Page<PersonSubscribeRecord> page = new Page();
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        page.setRecords(this.personSubscribeRecordMapper.findPersonSubscribeRecord(esid, cname, year, oname, pname, sched,
                (pageNo - 1) * pageSize, pageNo * pageSize));
        page.setTotal(this.personSubscribeRecordMapper.findPersonSubscribeRecordCount(esid, cname, year, oname, pname, sched));
        return ResultInfo.success(page);
    }

    public ResultInfo savePersonSubscribeRecord(String eid, String cid, String year, String oid, String pid, String applyDate, String sched, String checkupTime,
                                                String uploadTime) {
        PersonSubscribeRecord personSubscribeRecord = new PersonSubscribeRecord();
        personSubscribeRecord.setApply_date(applyDate);
        personSubscribeRecord.setCheckup_time(checkupTime);
        personSubscribeRecord.setCivilworker_id(cid);
        personSubscribeRecord.setEmp_sub_id(eid);
        personSubscribeRecord.setOrg_id(oid);
        personSubscribeRecord.setPack_id(pid);
        personSubscribeRecord.setSched(sched);
        personSubscribeRecord.setUpload_time(uploadTime);
        personSubscribeRecord.setYear(year);
        return ResultInfo.success(this.personSubscribeRecordMapper.insert(personSubscribeRecord));
    }

    public ResultInfo updatePersonSubscribeRecord(String eid, String cid, String year, String oid, String pid, String applyDate, String sched, String checkupTime,
                                                  String uploadTime, String id) {
        PersonSubscribeRecord personSubscribeRecord = new PersonSubscribeRecord();
        personSubscribeRecord.setApply_date(applyDate);
        personSubscribeRecord.setCheckup_time(checkupTime);
        personSubscribeRecord.setCivilworker_id(cid);
        personSubscribeRecord.setEmp_sub_id(eid);
        personSubscribeRecord.setOrg_id(oid);
        personSubscribeRecord.setPack_id(pid);
        personSubscribeRecord.setSched(sched);
        personSubscribeRecord.setUpload_time(uploadTime);
        personSubscribeRecord.setYear(year);
        personSubscribeRecord.setId(id);
        return ResultInfo.success(this.personSubscribeRecordMapper.updateById(personSubscribeRecord));
    }


    /**
     * 预约人数统计接口
     * Author wzn
     * Date 2022/5/27 17:35
     */
    public PeopleCount tjCount() {
        SysUser sysUser = sysUserService.getUser();
        QueryWrapper<PersonSubscribeRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sched", "0").eq("is_del","0");
        if (!"1".equals(sysUser.getUser_type())) {
            queryWrapper.eq("org_id", sysUser.getOrg_code());
        }
        Long count = personSubscribeRecordMapper.selectCount(queryWrapper);
        PeopleCount peopleCount1 = new PeopleCount();
        peopleCount1.setIcon("user-edit");
        peopleCount1.setNum(count + "");
        peopleCount1.setTitle("待体检人数");
        peopleCount1.setLink("https://github.com/chuzhixin/vue-admin-beautiful");
        peopleCount1.setColor("#b37feb");
        return peopleCount1;
    }

    /**
     * 已体检人数统计
     * Author wzn
     * Date 2022/5/27 17:56
     */
    public PeopleCount getCount() {
        String orgCode = "";
        SysUser sysUser = sysUserService.getUser();
        if (!"1".equals(sysUser.getUser_type())) {
            orgCode = sysUser.getOrg_code();
        }
        Integer count = personSubscribeRecordMapper.getCount(orgCode);

        PeopleCount peopleCount1 = new PeopleCount();
        peopleCount1.setIcon("user-md");
        peopleCount1.setNum(count + "");
        peopleCount1.setTitle("体检人数");
        peopleCount1.setLink("");
        peopleCount1.setColor("#69c0ff");
        return peopleCount1;
    }

    /**
     * 对外接口--预约人数 013  |  1  已体检人数   3 报告上传人数
     * Author wzn
     * Date 2022/6/9 16:15
     */
    public PeopleCount reservationCount(String orgCode, String type) {
        Integer count = personSubscribeRecordMapper.reservationCount(orgCode, type);
        PeopleCount peopleCount1 = new PeopleCount();
        peopleCount1.setNum(count + "");
        return peopleCount1;
    }


    /**
     * 预约记录接口
     * Author wzn
     * Date 2022/5/31 9:22
     */
    public Page<PersonSubscribeRecordVo> selectList(PersonSubscribeRecordVo personSubscribeRecordVo) {
        Page<PersonSubscribeRecord> page = new Page<>(personSubscribeRecordVo.getPageNo(), personSubscribeRecordVo.getPageSize());
        SysUser sysUser = sysUserService.getUser();
        personSubscribeRecordVo.setOrg_code(sysUser.getOrg_code());
        if ("1".equals(sysUser.getUser_type())) {
            personSubscribeRecordVo.setOrg_code("");
        }else if("5".equals(sysUser.getUser_type())){//体检机构
            personSubscribeRecordVo.setOrg_code(sysUser.getOrg_code());
        }else if("4".equals(sysUser.getUser_type())){//用人单位 只能查询本单位的公务员预约记录
            personSubscribeRecordVo.setEmp_code(sysUser.getOrg_code());
            personSubscribeRecordVo.setOrg_code("");
        }
        personSubscribeRecordVo.setSysUser(sysUser);
        return personSubscribeRecordMapper.selectListAll(page, personSubscribeRecordVo);
    }

    /**
     * 为体检机构接口专用
     * @param personSubscribeRecordVo
     * @return
     */
    public Page<PersonSubscribeRecordVo> selectListAPI(PersonSubscribeRecordVo personSubscribeRecordVo) {
        Page<PersonSubscribeRecord> page = new Page<>(personSubscribeRecordVo.getPageNo(), personSubscribeRecordVo.getPageSize());
        return personSubscribeRecordMapper.selectListAll(page, personSubscribeRecordVo);
    }


    public ResultInfo findCiviWorkerByRid(String rid, String oid, String time,String code) {
        List<Map<String,String>> lists=this.personSubscribeRecordMapper.findCiviWorkerByRid(rid,oid,time,code);
        for (Map<String, String> list : lists) {
            list.put("SEX",IdCardNumberMethod.getSexFromIdCard(list.get("CERTNO"))+"");
            list.put("AGE",IdCardNumberMethod.getAgeForIdcard(list.get("CERTNO"))+"");
        }
        return ResultInfo.success(lists);
    }

    public ResultInfo findItemToMealByPid(String pid) {
        return ResultInfo.success(this.personSubscribeRecordMapper.findItemToMealByPid(pid));
    }

    /**
     * 公务员h5端预约记录查询,通过公务员id以及sched(可选)查询找预约记录
     *
     * @param: civilWorkerId required, sched required = false
     * @Author yc
     * @Date 2022/6/01 16:18
     */
    public List<PersonSubscribeRecord> findPersonSubscribeRecordByCivilWorker(String civilWorkerId, String type) {
        return this.personSubscribeRecordMapper.findPersonSubscribeRecordByCivilWorker(civilWorkerId, type);
    }

    public int updatePersonSubscribeRecordReview(PersonSubscribeRecord record) {
        return this.personSubscribeRecordMapper.updateById(record);
    }

    public PersonSubscribeRecord getReviewByPersonSubscribeRecordId(String id, String civilCertNo) {
        LambdaQueryWrapper<PersonSubscribeRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PersonSubscribeRecord::getId, id)
                .eq(PersonSubscribeRecord::getCivilworker_id, civilCertNo)
                .and(Wrapper -> Wrapper.eq(PersonSubscribeRecord::getSched, "3")
                        .or().eq(PersonSubscribeRecord::getSched, "1"));
        return this.personSubscribeRecordMapper.selectOne(queryWrapper);
    }


    /**
     * @return
     * @Description: 公务员端 个人申请撤回预约
     * @param: [civil, personSubscribeRecordId]
     * @author: yc
     * @date: 2022/6/7
     * @time: 16:47
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public synchronized ResultInfo backOutPersonSubscribeRecordPersonal(CivilworkerInfo civil, String personSubscribeRecordId) {
        // 1.根据(1)当前用户CivilWorkerInfo certNo以及(2)personSubscribeRecordId判断是否存在有sched=0的待体检记录
        // 2. 要撤销的psr必须为今年的
        // 3. 撤销不能当年不能超过医保制定的撤销次数
        // 4.并且撤销的时间为apply_date-3<当前日期,才可撤销，
        // 5.软删除psr
        // 6.对于emp -> 判断，如果emp是单位预约的，则人数减1; 如果emp是个人预约的，则软删除
        // 7.返回apply_date对应的预约人数

        // 1. 根据当前用户以及personSubscribeRecordId判断psr是否存在有sched=0的待体检记录
        LambdaQueryWrapper<PersonSubscribeRecord> psrQuery = new LambdaQueryWrapper<>();
        psrQuery.eq(PersonSubscribeRecord::getId, personSubscribeRecordId)
                .eq(PersonSubscribeRecord::getCivilworker_id, civil.getCertno())
                .and(Wrapper -> Wrapper.eq(PersonSubscribeRecord::getSched, "0").or().eq(PersonSubscribeRecord::getSched, "2"));

        PersonSubscribeRecord personSubscribeRecord = this.personSubscribeRecordMapper.selectOne(psrQuery);
        if (personSubscribeRecord == null) {
            return ResultInfo.error("此用户没有未体检的个人预约记录");
        }
        String applyDate = personSubscribeRecord.getApply_date();
        String applyDateOld = applyDate;
        applyDate += " 00:00:00";
        // 取出apply_date的year
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parsedApplyDate = LocalDateTime.parse(applyDate, dateTimeFormatter);
        int applyDateYear = parsedApplyDate.getYear();

        // 获取当前时间的年份
        LocalDateTime currentDateTime = LocalDateTime.now();
        int currentYear = currentDateTime.getYear();

        // 2. 要撤销的psr必须为今年的
        if (applyDateYear != currentYear) {
            return ResultInfo.error("申请撤销的个人预约记录必须为今年的");
        }

        // 3. 不能超过医保制定的撤销次数
        // 3.1 获取当前医保制定的撤销次数
        LambdaQueryWrapper<PhysExamConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PhysExamConfig::getYear, personSubscribeRecord.getYear());
        wrapper.eq(PhysExamConfig::getIs_del, "0");
        PhysExamConfig physExamConfig = this.physExamConfigMapper.selectOne(wrapper);
        // 3.2 获取此用户的撤销次数
        Integer count = this.personSubscribeRecordMapper.countBackOutTimesPersonally(personSubscribeRecord.getCivilworker_id(), personSubscribeRecord.getYear());
        if (physExamConfig.getCancel_num() <= count) {
            return ResultInfo.error("撤销失败, 医保局规定撤销次数最多" + physExamConfig.getCancel_num() + "次， "+ "您的撤销次数已达到上限");
        }

        DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
        String nowFormat1 = currentDateTime.format(dateTimeFormatter1);

        DateTimeFormatter dateTimeFormatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime nowFormat2 = LocalDateTime.parse(nowFormat1, dateTimeFormatter2);

        Duration between = Duration.between(nowFormat2, parsedApplyDate);
        if ("0".equals(personSubscribeRecord.getSched()) && between.toHours() <= 0 ) {
            return ResultInfo.error("撤销时间过晚.预约日期之前可以撤销");
        }


//        Duration between = Duration.between(currentDateTime, parsedApplyDate);

//        if (between.toDays() < 3) {
//            return ResultInfo.error("撤销失败，撤销时间过晚.最少提前3天申请撤销");
//        }

        // 预约当天之前可以撤销
//        if (between.toDays() < 1) {
//            return ResultInfo.error("撤销失败，撤销时间过晚.预约日期之前可以撤销");
//        }


        // 5.psr 软删除 is_del = 1
        personSubscribeRecord.setIs_del("1");

        // 并且设定sched == 4为 已撤销
        personSubscribeRecord.setSched("4");
        personSubscribeRecord.setUpdateTime(new Date());

        int effectPsr = this.personSubscribeRecordMapper.updateById(personSubscribeRecord);
        if (effectPsr != 1) {
            return ResultInfo.error("个人预约记录软删除失败");
        }

        // 6.判断emp; 6.1如果emp是个人预约的，则软删除; 6.2如果emp是单位预约的，则人数减1
        LambdaQueryWrapper<EmpSubscribeRecord> esrQueryWrapper = new LambdaQueryWrapper<>();
        esrQueryWrapper.eq(EmpSubscribeRecord::getId, personSubscribeRecord.getEmp_sub_id());
        EmpSubscribeRecord empSubscribeRecord = this.empSubscribeRecordMapper.selectOne(esrQueryWrapper);
        if (empSubscribeRecord == null) {
            return ResultInfo.error("没有此预约记录");
        }

        // 6.1如果emp是个人预约的，则软删除
        if (("1").equals(empSubscribeRecord.getIs_personal())) {
            empSubscribeRecord.setIs_del("1");
            empSubscribeRecord.updateById();
            // 6.2 如果emp是单位预约的，则人数减1
        } else if ("0".equals(empSubscribeRecord.getIs_personal())) {
            empSubscribeRecord.setSubscribe_num(empSubscribeRecord.getSubscribe_num() - 1);
            empSubscribeRecord.updateById();
        }

        // 6.返回apply_date对应的规则的预约人数-1
        LambdaQueryWrapper<OrgSubscribeRules> orgSubscribeRuleWrapper = new LambdaQueryWrapper<>();
        orgSubscribeRuleWrapper.eq(OrgSubscribeRules::getTime, applyDateOld);
        orgSubscribeRuleWrapper.eq(OrgSubscribeRules::getOrg_id, personSubscribeRecord.getOrg_id());
        OrgSubscribeRules orgSubscribeRules = this.orgSubscribeRulesMapper.selectOne(orgSubscribeRuleWrapper);
        int bookingPerson = Integer.parseInt(orgSubscribeRules.getBooking_person());
        bookingPerson = bookingPerson -1;
        if(bookingPerson <=0){
            bookingPerson = 0;
        }
        orgSubscribeRules.setBooking_person(String.valueOf(bookingPerson));
        orgSubscribeRules.updateById();
        return ResultInfo.success();
    }



    /**
     * @return
     * @Description: 公务员端 个人申请撤回预约
     * @param: [civil, personSubscribeRecordId]
     * @author: yc
     * @date: 2022/6/7
     * @time: 16:47
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public synchronized ResultInfo backOutPersonSubscribeRecordByAdmin(PersonSubscribeRecord psr) {
        psr.setIs_del("1");
        psr.setSched("4");
        psr.setUpdateTime(new Date());

        int effectPsr = this.personSubscribeRecordMapper.updateById(psr);
        if (effectPsr != 1) {
            return ResultInfo.error("个人预约记录软删除失败");
        }

        // 6.判断emp; 6.1如果emp是个人预约的，则软删除; 6.2如果emp是单位预约的，则人数减1
        LambdaQueryWrapper<EmpSubscribeRecord> esrQueryWrapper = new LambdaQueryWrapper<>();
        esrQueryWrapper.eq(EmpSubscribeRecord::getId, psr.getEmp_sub_id());
        EmpSubscribeRecord empSubscribeRecord = this.empSubscribeRecordMapper.selectOne(esrQueryWrapper);
        if (empSubscribeRecord == null) {
            return ResultInfo.error("没有此预约记录");
        }

        // 6.1如果emp是个人预约的，则软删除
        if (("1").equals(empSubscribeRecord.getIs_personal())) {
            empSubscribeRecord.setIs_del("1");
            empSubscribeRecord.updateById();
            // 6.2 如果emp是单位预约的，则人数减1
        } else if ("0".equals(empSubscribeRecord.getIs_personal())) {
            empSubscribeRecord.setSubscribe_num(empSubscribeRecord.getSubscribe_num() - 1);
            empSubscribeRecord.updateById();
        }

        String applyDate = psr.getApply_date();
        // 6.返回apply_date对应的规则的预约人数-1
        LambdaQueryWrapper<OrgSubscribeRules> orgSubscribeRuleWrapper = new LambdaQueryWrapper<>();
        orgSubscribeRuleWrapper.eq(OrgSubscribeRules::getTime, applyDate);
        orgSubscribeRuleWrapper.eq(OrgSubscribeRules::getOrg_id, psr.getOrg_id());
        OrgSubscribeRules orgSubscribeRules = this.orgSubscribeRulesMapper.selectOne(orgSubscribeRuleWrapper);
        int bookingPerson = Integer.parseInt(orgSubscribeRules.getBooking_person());
        orgSubscribeRules.setBooking_person(String.valueOf(--bookingPerson));
        orgSubscribeRules.updateById();
        return ResultInfo.success();
    }

    /**
     * 统计
     * 服务排行_组装数据
     */
    public List getRanking(String year) {
        //预约人数      sched:0+1+2+3
        //体检人数     sched:1
        //上传报告人数  sched:3
        int num = 0;
        List rankData = new ArrayList();
        List<PersonSubscribeRecordVo> getRanking = personSubscribeRecordMapper.getRanking(year);
        Map<String, List<PersonSubscribeRecordVo>> groupMap = getRanking.stream().collect(Collectors.groupingBy(PersonSubscribeRecordVo::getOrg_id));
        Iterator<Map.Entry<String, List<PersonSubscribeRecordVo>>> entries = groupMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, List<PersonSubscribeRecordVo>> entry = entries.next();
            List list = new ArrayList();
            int sched0 = 0;
            int sched1 = 0;
            int sched2 = 0;
            int sched3 = 0;
            for (int i = 0; i < entry.getValue().size(); i++) {
                String count = entry.getValue().get(i).getCount();
                String sched = entry.getValue().get(i).getSched();
                if ("0".equals(sched)) {
                    sched0 = NumberUtils.toInt(count);
                } else if ("1".equals(sched)) {
                    sched1 = NumberUtils.toInt(count);
                } else if ("2".equals(sched)) {
                    sched2 = NumberUtils.toInt(count);
                } else if ("3".equals(sched)) {
                    sched3 = NumberUtils.toInt(count);
                }
            }
            String orgName = personSubscribeRecordMapper.getOrgId(entry.getKey());
            if (orgName == null){
                orgName = "";
            }
            list.add(orgName);
            list.add(sched0 + sched1 + sched2 + sched3);
            list.add(sched1);
            list.add(sched3);

            rankData.add(list);

            num++;
            if (num >= 4) {
                break;
            }
        }
        return rankData;
    }
    /**
     *报表服务排名获取年份
     */
    // 获取年份列表
    public List<String> getYearList() {
        SysUser user = sysUserService.getUser();
        // 获取当前用户的org_code
        String org_code = user.getOrg_code();
        QueryWrapper<PersonSubscribeRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DISTINCT YEAR").eq("IS_DEL", "0").orderByDesc("YEAR");
        List<PersonSubscribeRecord> personSubscribeRecords = personSubscribeRecordMapper.selectList(queryWrapper);
        ArrayList<String> yearList = new ArrayList<>();
        personSubscribeRecords.forEach((personSubscribeRecord -> {
            yearList.add(personSubscribeRecord.getYear());
        }));
        return yearList;
    }


    /**
     * 体检确认
     *
     * @param id
     * @return
     */
    public ResultInfo conformById(String id) {
        PersonSubscribeRecord psr = personSubscribeRecordMapper.selectById(id);
        if (null != psr) {
            boolean flag = checkConformDate(psr.getApply_date() +" 00:00:00");
            if(!flag){
                return ResultInfo.error("预约日期4天内可确认");
            }
            if ("3".equals(psr.getSched())){
                return ResultInfo.error("已上传报告，请勿重复操作！");
            }
            psr.setSched("1");
            psr.setCheckup_time(sdf.format(new Date()));
            psr.updateById();
            return ResultInfo.success("体检信息已确认");
        }

        return ResultInfo.error("体检信息不存在");
    }

    /**
     * 医保确认结算
     *
     * @param id
     * @return
     */
    public ResultInfo jsConfirm(String id) {
        PersonSubscribeRecord psr = personSubscribeRecordMapper.selectById(id);
        if (null != psr) {
            if ("1".equals(psr.getIsSettlement())){
                return ResultInfo.error("已确认结算，请勿重复操作！");
            }
            psr.setIsSettlement("1");
            psr.setSettlementTime(new Date());//确认时间
            psr.updateById();
            return ResultInfo.success("体检信息已结算");
        }

        return ResultInfo.error("体检信息不存在");
    }

    /**
     * 在预约日期前4天之内可以确认
     * @param dateStr
     * @return
     */
    private boolean checkConformDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date applayDate = sdf.parse(dateStr);

            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);

            Date currentDate = cal.getTime();

            long sub = applayDate.getTime() -currentDate.getTime();

            if(sub < (86400000 * 4)){
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }


    /**
     * 体检确认
     *
     * @param id
     * @return
     */
    public ResultInfo conformByIdAPI(String id,ApiLoginInfo apiLoginInfo) {
        PersonSubscribeRecord psr = personSubscribeRecordMapper.selectById(id);
        if (null != psr) {
            boolean flag = checkConformDate(psr.getApply_date() +" 00:00:00");
            if(!flag){
                return ResultInfo.error("预约日期4天内可确认");
            }
            if (!psr.getOrg_id().equals(apiLoginInfo.getOrgCode())){
                return ResultInfo.error("体检信息不存在");
            }
            if ("3".equals(psr.getSched())){
                return ResultInfo.error("已上传报告，请勿重复操作！");
            }
            psr.setSched("1");
            psr.setCheckup_time(sdf.format(new Date()));
            psr.updateById();
            return ResultInfo.success("体检信息已确认");
        }

        return ResultInfo.error("体检信息不存在");
    }


    public void updateUploadFile(String id) {
        PersonSubscribeRecord psr = personSubscribeRecordMapper.selectById(id);

        if (null != psr) {
            psr.setSched("3");
            psr.setUpload_time(sdf.format(new Date()));
        }
        psr.updateById();
//        return ResultInfo.success();
    }

    public FileInfo getPhysicalExaminationReportByPsrId(String psrId) {
//        LambdaQueryWrapper<SysFile> sysFileLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        sysFileLambdaQueryWrapper.eq(SysFile::getAssociationId, psrId).eq(SysFile::getIs_del, "0");
//        sysFileLambdaQueryWrapper.orderByDesc(SysFile::getCreateTime);
//        sysFileLambdaQueryWrapper.orderByDesc(SysFile::getId);
//        List<SysFile> sysFiles = this.sysFileMapper.selectList(sysFileLambdaQueryWrapper);
//
//        return sysFiles.get(0);
        LambdaQueryWrapper<FileInfo> sysFileLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysFileLambdaQueryWrapper.eq(FileInfo::getBizId, psrId);
        sysFileLambdaQueryWrapper.orderByDesc(FileInfo::getUploadTime);
        sysFileLambdaQueryWrapper.orderByDesc(FileInfo::getId);
        List<FileInfo> sysFiles = fileInfoMapper.selectList(sysFileLambdaQueryWrapper);
        return sysFiles.get(0);
    }

    public FileInfo getFileServerExaminationReportByPsrId(String psrId) {
        LambdaQueryWrapper<FileInfo> sysFileLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysFileLambdaQueryWrapper.eq(FileInfo::getBizId, psrId);
        sysFileLambdaQueryWrapper.orderByDesc(FileInfo::getUploadTime);
        List<FileInfo> sysFiles = fileInfoMapper.selectList(sysFileLambdaQueryWrapper);
        return sysFiles.get(0);
    }


    /**
     * 修改为已上传报告
     * @param id
     * @return
     */
    public ResultInfo medicalOver (String id){
        return ResultInfo.success(personSubscribeRecordMapper.medicalOver(id));
    }

    public void yyExport(PersonSubscribeRecordVo personSubscribeRecordVo, HttpServletResponse response) throws Exception{
        SysUser sysUser = sysUserService.getUser();
        personSubscribeRecordVo.setOrg_code(sysUser.getOrg_code());
        if ("1".equals(sysUser.getUser_type())) {
            personSubscribeRecordVo.setOrg_code("");
        }else if("5".equals(sysUser.getUser_type())){//体检机构
            personSubscribeRecordVo.setOrg_id(sysUser.getOrg_code());
        }else if("4".equals(sysUser.getUser_type())){//用人单位 只能查询本单位的公务员预约记录
            personSubscribeRecordVo.setEmp_code(sysUser.getOrg_code());
            personSubscribeRecordVo.setOrg_code("");
        }

        personSubscribeRecordVo.setSysUser(sysUser);

        List<PersonSubscribeRecordVo> list=personSubscribeRecordMapper.yyExport(personSubscribeRecordVo);

        list.forEach(x->{
            switch (x.getSched()){
                case "0":
                    x.setSched("待体检");
                    break;
                case "1":
                    x.setSched("已体检");
                    break;
                case "2":
                    x.setSched("已过期");
                    break;
                case "3":
                    x.setSched("已上传报告");
                    break;
                default:
                    break;
            }
            if (StringUtils.isNotEmpty(x.getCertno())){
                x.setSex(IdCardNumberMethod.getSexFromIdCard(x.getCertno())==1?"男":"女");
            }
            x.setIsSettlement("0".equals(x.getIsSettlement())?"未结算":"已结算");
        });

        ExcelWriter writer = ExcelUtil.getWriter();

        writer.addHeaderAlias("name", "姓名");
        writer.addHeaderAlias("sex", "性别");
        writer.addHeaderAlias("certno", "证件号码");
        writer.addHeaderAlias("emp_name", "单位名称");
        writer.addHeaderAlias("year", "年份");
        writer.addHeaderAlias("org_name", "体检机构");
        writer.addHeaderAlias("pack_name", "套餐名称");
        writer.addHeaderAlias("apply_date", "预约时间");
        writer.addHeaderAlias("sched", "体检进度");
        writer.addHeaderAlias("phone", "手机号");
        writer.addHeaderAlias("isSettlement", "是否结算");
        writer.addHeaderAlias("settlementTime", "结算时间");
        writer.setOnlyAlias(true);
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


    /**
    *  未预约记录
    * Author wzn
    * Date 2022/6/25 14:44
    */
    public Page<CivilworkerInfo> noAppointment(PersonSubscribeRecordVo personSubscribeRecordVo) {
        Page<PersonSubscribeRecord> page = new Page<>(personSubscribeRecordVo.getPageNo(), personSubscribeRecordVo.getPageSize());
        SysUser sysUser = sysUserService.getUser();
        personSubscribeRecordVo.setOrg_code(sysUser.getOrg_code());
        if ("1".equals(sysUser.getUser_type())) {
            personSubscribeRecordVo.setOrg_code("");
        }else if("5".equals(sysUser.getUser_type())){//体检机构
            personSubscribeRecordVo.setOrg_code(sysUser.getOrg_code());
        }else if("4".equals(sysUser.getUser_type())){//用人单位 只能查询本单位的公务员预约记录
            personSubscribeRecordVo.setEmp_code(sysUser.getOrg_code());
            personSubscribeRecordVo.setOrg_code("");
        }
        return personSubscribeRecordMapper.noAppointment(page, personSubscribeRecordVo);
    }


    public PersonSubscribeRecord getOrgCode(String id){
        return personSubscribeRecordMapper.selectById(id);
    }


    public ResultInfo multipleCommentImageUpload(HttpServletRequest request, HttpServletResponse response,String id, String civilworker_id, String year,String orgCode) {
        response.setContentType("text/html;charset=utf-8");
        MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
        MultipartFile file = multipartRequest.getFile("file");
        if (file == null) {
            return ResultInfo.error("上传失败，文件为空");
        }
        if ((double) file.getSize() / 1048576 > 10){
            return ResultInfo.error("上传的文件不能大于10M");
        }
        List<PersonSubscribeRecord> personSubscribeRecords;
        List<String> ids=new ArrayList<>();
        if (Strings.isEmpty(id) ) {
            if (Strings.isEmpty(civilworker_id)){
                return ResultInfo.error("身份证为空");
            }
            if (Strings.isEmpty(year)){
                return ResultInfo.error("年份为空");
            }
            personSubscribeRecords=personSubscribeRecordMapper.
                    selectList(Wrappers.<PersonSubscribeRecord>lambdaQuery().
                            eq(PersonSubscribeRecord::getCivilworker_id,civilworker_id).
                            eq(PersonSubscribeRecord::getYear,year));
            if (personSubscribeRecords.isEmpty()){
                return ResultInfo.error("没有体检预约记录！");
            }
            personSubscribeRecords.forEach(x->{
                if ("1".equals(x.getSched())){
                    ids.add(x.getId());
                }
            });
            if (ids.size()==0){
                return ResultInfo.error("没有已完成的体检预约记录！");
            }
//            else if(ids.size()>1){
//                return ResultInfo.error("联系管理员！");
//            }
        } else{
            PersonSubscribeRecord personSubscribeRecord = personSubscribeRecordMapper.selectById(id);
            if(!"1".equals(personSubscribeRecord.getSched())&&!"3".equals(personSubscribeRecord.getSched())){
                return ResultInfo.error("没有已体检的记录！");
            }
        }
        /*
        //换成文件服务器这个就作废了
        List<SysFile> reservationRecords = sysFileService.selectByAssociationId(Strings.isEmpty(id)?ids.get(0):id);
        if (reservationRecords.size()>0){
            reservationRecords.forEach(reservationRecord->{
                reservationRecord.setIs_del("1");
                sysFileMapper.updateById(reservationRecord);
            });
        }*/

        try {
            String fileName = file.getOriginalFilename();

            int index = fileName.lastIndexOf(".");
            String fileSuffix = fileName.substring(index);
            String file_name = UUID.randomUUID().toString().replaceAll("-", "") + fileSuffix;
            String file_path = getResponsePath(id,civilworker_id,year,orgCode).getData().toString();
//            uploadFile(file.getBytes(), file_path, file_name);
//            SysFile sysFile = new SysFile();
//            Date time = new Date();
//            sysFile.setCreateTime(time);
//            sysFile.setCreateUser(orgCode);
//            sysFile.setOldFileName(fileName);
//            sysFile.setNewFileName(file_name);
//            sysFile.setAssociationId(Strings.isEmpty(id)?ids.get(0):id);
//            sysFile.setFilePath(file_path +"/"+file_name);
//            sysFile.setIs_del("0");
//            sysFile.insert();

            String path = file_path + "/" + file_name;

            MultipartFile  multipartFile = new MockMultipartFile(path, path, ContentType.APPLICATION_OCTET_STREAM.toString(), file.getInputStream());
            String fileServerPath = "uploadCivil" + file_path.replace("E:/upload", "");
            log.info("fileServerPath == " + fileServerPath);
            //params = new FastDfsParams(fileServerPath, multipartFile, "13", id);
            //公务员体检专用 上传到文件服务器
            FastDfsParams  params = uploadCivilFileServer(fileServerPath, multipartFile, Strings.isEmpty(id)?ids.get(0):id);
            params.setFileName(fileName);
            ResultInfo resultInfo = fastDfsUtil.uploadFile(params);
            FileInfo fileInfo = (FileInfo) resultInfo.getData();
            if(null != fileInfo){
                log.info(id +"==fileInfo == " + JSONObject.toJSONString(fileInfo));
            }

            updateUploadFile(Strings.isEmpty(id)?ids.get(0):id);
            medicalOver(Strings.isEmpty(id)?ids.get(0):id);

        } catch (IOException e) {
//            e.printStackTrace();
            log.error(e.toString());
            return ResultInfo.error("系统传输错误，请联系管理员！");
        } catch (Exception e) {
            log.error(e.toString());
            return ResultInfo.error("系统传输错误，请联系管理员！");
        }

        return ResultInfo.success();

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

    public  ResultInfo getResponsePath(String id,String civilworker_id, String year,String orgCode){

        Random num = new Random();
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        String file_path;
       if(Strings.isNotEmpty(id)){
           PersonSubscribeRecord psr = personSubscribeRecordMapper.selectById(id);
           file_path = uploadtjreportPath+
                   "/"+psr.getYear()+
                   "/"+orgCode+
                   "/"+psr.getCivilworker_id()+//身份证号
                   "/"+ (psr.getCivilworker_id()+"_"+time+num.nextInt());

       }else{

           file_path = uploadtjreportPath+
                   "/"+year+
                   "/"+orgCode+
                   "/"+civilworker_id+//身份证号
                   "/"+ (civilworker_id+"_"+time+num.nextInt());

       }
        if ("" == file_path && null == file_path){
            return ResultInfo.error("文件上传失败，id或者身份或者年份为空");
        }
        return ResultInfo.success(file_path);
    }

    public  ResultInfo getResponsePaths(String id){
        Random num = new Random();
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));

        PersonSubscribeRecord psr = personSubscribeRecordMapper.selectById(id);
        String file_path = uploadtjreportPath+
                "/"+psr.getYear()+
                "/"+psr.getOrg_id()+
                "/"+psr.getCivilworker_id()+//身份证号
                "/"+ (psr.getCivilworker_id()+"_"+time+num.nextInt());

        return ResultInfo.success(file_path);
    }

    public Long getValidPsrByPackId(String packId) {
        LambdaQueryWrapper<PersonSubscribeRecord> personSubscribeRecordWrapper = new LambdaQueryWrapper<>();
        personSubscribeRecordWrapper.eq(PersonSubscribeRecord::getPack_id, packId).eq(PersonSubscribeRecord::getIs_del, "0");
        personSubscribeRecordWrapper.ne(PersonSubscribeRecord::getSched, "4");
        return this.personSubscribeRecordMapper.selectCount(personSubscribeRecordWrapper);
    }


    public List<PhysicalRatioVo> adminUnitPhysicalRatioCheckedByOrg(String year, String empCode, String medicalInsuranceNum) {
        return this.personSubscribeRecordMapper.adminUnitPhysicalRatioCheckedByOrg(year, empCode, medicalInsuranceNum);
    }

    public List<String> getYearListPhysicalRatioCheckedByOrg(String medicalInsuranceNum) {
        return this.personSubscribeRecordMapper.yearListPhysicalRatioCheckedByOrg(medicalInsuranceNum);
    }

    public  List<EChat> eChat(){
        SysUser sysUser=sysUserService.getUser();
        List<EChat> lists=new ArrayList<>();
        List<EChat> eChatSCHED0=this.personSubscribeRecordMapper.eChatSCHED0(sysUser.getOrg_code());//已经预约的人数
        List<EChat> eChatSCHED1=this.personSubscribeRecordMapper.eChatSCHED1(sysUser.getOrg_code());//已经体检的人数
        List<Map<String,Object>> eChat1=this.personSubscribeRecordMapper.eChat1();
        for (Map<String, Object> echat1 : eChat1) {
            EChat eChats = new EChat();
            eChats.setToday(echat1.get("TODAY")+"");
           if (eChatSCHED0.size()>0){
               for (EChat chat : eChatSCHED0) {
                   if (echat1.get("TODAY").equals(chat.getToday())){
                       eChats.setCount(chat.getCount());
                   }else {
                       eChats.setCount("0");
                   }
               }
           }else {
               eChats.setCount("0");
           }
            if (eChatSCHED1.size()>0){
                for (EChat chat : eChatSCHED1) {
                    if (echat1.get("TODAY").equals(chat.getToday())){
                        eChats.setCount1(chat.getCount());
                    }else {
                        eChats.setCount1("0");
                    }
                }
            }else {
                eChats.setCount1("0");
            }
            lists.add(eChats);
        }
        return lists;
    }

    public List<Map<String, String>> findItemToMealByPackInfoId(String pid) {
         return this.personSubscribeRecordMapper.findItemToMealByPid(pid);
    }

    public Long countPsrNum(EmpSubscribeRecordVo vo) {
        List<String> tj = Arrays.asList("0","1","2","3");
        LambdaQueryWrapper<PersonSubscribeRecord> personSubscribeRecordQuery = new LambdaQueryWrapper<>();
        personSubscribeRecordQuery.eq(PersonSubscribeRecord::getCivilworker_id, vo.getCardId())
                .eq(PersonSubscribeRecord::getYear, vo.getYear())
                .eq(PersonSubscribeRecord::getIs_del, "0")
                .in(PersonSubscribeRecord::getSched, tj);
        return this.personSubscribeRecordMapper.selectCount(personSubscribeRecordQuery);
    }


    public  List<EChat> eChatYB(){
        List<EChat> lists=new ArrayList<>();
        List<EChat> eChatSCHED0=this.personSubscribeRecordMapper.eChatSCHEDYB0();//已经预约的人数
        List<EChat> eChatSCHED1=this.personSubscribeRecordMapper.eChatSCHEDYB1();//已经体检的人数
        List<Map<String,Object>> eChat1=this.personSubscribeRecordMapper.eChatYB1();
        for (Map<String, Object> echat1 : eChat1) {
            EChat eChats = new EChat();
            eChats.setToday(echat1.get("TODAY")+"");
            if (eChatSCHED0.size()>0){
                for (EChat chat : eChatSCHED0) {
                    if (echat1.get("TODAY").equals(chat.getToday())){
                        eChats.setCount(chat.getCount());
                    }
                }
            }else {
                eChats.setCount("0");
            }
            if (eChatSCHED1.size()>0){
                for (EChat chat : eChatSCHED1) {
                    if (echat1.get("TODAY").equals(chat.getToday())){
                        eChats.setCount1(chat.getCount());
                    }
                }
            }else {
                eChats.setCount1("0");
            }
            lists.add(eChats);
        }
        return lists;
    }

    public ResultInfo settlementMore(PersonSubscribeRecordVo personSubscribeRecordVo) {

        SysUser sysUser = sysUserService.getUser();
        personSubscribeRecordVo.setOrg_code(sysUser.getOrg_code());
        if ("1".equals(sysUser.getUser_type())) {
            personSubscribeRecordVo.setOrg_code("");
        }else if("5".equals(sysUser.getUser_type())){//体检机构
            personSubscribeRecordVo.setOrg_code(sysUser.getOrg_code());
        }else if("4".equals(sysUser.getUser_type())){//用人单位 只能查询本单位的公务员预约记录
            personSubscribeRecordVo.setEmp_code(sysUser.getOrg_code());
            personSubscribeRecordVo.setOrg_code("");
        }
        personSubscribeRecordVo.setSysUser(sysUser);

        //批量结算只结算未结算的
        personSubscribeRecordVo.setIsSettlement("0");

        List<PersonSubscribeRecordVo> recordVos=personSubscribeRecordMapper.settlementMore(personSubscribeRecordVo);

        recordVos.forEach(x->{
            //批量结算只结算未结算的
            if("0".equals(x.getIsSettlement())){
                PersonSubscribeRecord personSubscribeRecord=personSubscribeRecordMapper.selectById(x.getId());
                personSubscribeRecord.setIsSettlement("1");
                personSubscribeRecord.setSettlementTime(new Date());
                personSubscribeRecord.updateById();
            }
        });
        return ResultInfo.success();
    }

    /**
     * 上传体检报告统一
     *
     * @param fileServerPath 文件服务器路径
     * @param file           要上传的文件
     * @param id             业务id
     * @return
     */
    public FastDfsParams uploadCivilFileServer(String fileServerPath, MultipartFile file, String id) {
        return new FastDfsParams(fileServerPath, file, "13", id);
    }
}
