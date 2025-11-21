package com.jsdc.ybpt.service;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.*;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_check.*;
import com.jsdc.ybpt.model_query.PersonInfo;
import com.jsdc.ybpt.util.IdCardNumberMethod;
import com.jsdc.ybpt.vo.ResultInfo;
import lombok.RequiredArgsConstructor;
import org.apache.velocity.runtime.directive.Foreach;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import com.jsdc.ybpt.util.StringUtils;
/**
 * @Author ：苹果
 * @Description：异地就医
 * @Date ：2022/5/26 10:02
 * @Modified By：
 */
@Service
@RequiredArgsConstructor
public class RelocatedInfoService extends BaseService<RelocatedInfo> {

    private final RelocatedInfoMapper relocatedInfoMapper;

    private final CivilworkerInfoMapper civilworkerInfoMapper;

    private final EmployingInfoMapper employingInfoMapper;

    private final SysUserService sysUserService;

    private final AutonomousMedicalMapper autonomousMedicalMapper;

    private final PersonInfoMapper personInfoMapper;

    private final PersonSubscribeRecordMapper personSubscribeRecordMapper;

    private final IsConfigMapper isConfigMapper;

    private final CivilworkerInfoService civilworkerInfoService;

    public ResultInfo findRelocatedInfoOne(String id) {
        RelocatedInfo relocatedInfo = new RelocatedInfo();
        relocatedInfo.setId(id);
        return ResultInfo.success(relocatedInfoMapper.findRelocatedInfoOne(relocatedInfo));
    }

    public ResultInfo findRelocatedInfo(RelocatedInfo relocatedInfo) {
        SysUser user = this.sysUserService.getUser();
        if ("1".equals(user.getUser_type())) {
            List<RelocatedInfo> relocatedInfos = relocatedInfoMapper.FindRelocatedInfo(relocatedInfo.getName(), relocatedInfo.getNum(),
                    (relocatedInfo.getPageNo() - 1) * relocatedInfo.getPageSize(),
                    relocatedInfo.getPageNo() * relocatedInfo.getPageSize(), relocatedInfo.getEcode(), relocatedInfo.getAdministrative_unit());
            for (RelocatedInfo info : relocatedInfos) {
                info.setSEX(IdCardNumberMethod.getSexFromIdCard(info.getNum()) + "");
//                info.setAGE(IdCardNumberMethod.getAgeForIdcard(info.getNum()) + "");
                CivilworkerInfo civilworkerInfo=new CivilworkerInfo();
                civilworkerInfo.setCertno(info.getCivilworker_id());
                civilworkerInfo = civilworkerInfoService.getCivilworkerInfoByCardNo(civilworkerInfo);
                if (StringUtils.isNotNull(civilworkerInfo)){
                    info.setCardType(civilworkerInfo.getCardType());
                }else {
                    info.setCardType("1");
                }
            }
            Page<RelocatedInfo> page = new Page<>();
            page.setRecords(relocatedInfos);
            page.setTotal(relocatedInfoMapper.FindRelocatedInfoCount(relocatedInfo.getName(), relocatedInfo.getNum(),
                    relocatedInfo.getEcode(), relocatedInfo.getAdministrative_unit()));
            page.setSize(relocatedInfo.getPageSize());
            page.setCurrent(relocatedInfo.getPageNo());
            return ResultInfo.success(page);
        } else {
            LambdaQueryWrapper<EmployingInfo> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(EmployingInfo::getEmp_no, relocatedInfo.getAdministrative_unit());
            EmployingInfo employingInfo = this.employingInfoMapper.selectOne(wrapper);
            List<RelocatedInfo> relocatedInfos = relocatedInfoMapper.FindRelocatedInfo(relocatedInfo.getName(), relocatedInfo.getNum(),
                    (relocatedInfo.getPageNo() - 1) * relocatedInfo.getPageSize(),
                    relocatedInfo.getPageNo() * relocatedInfo.getPageSize(), relocatedInfo.getEcode(), null);
            for (RelocatedInfo info : relocatedInfos) {
                info.setSEX(IdCardNumberMethod.getSexFromIdCard(info.getNum()) + "");
//                info.setAGE(IdCardNumberMethod.getAgeForIdcard(info.getNum()) + "");
                CivilworkerInfo civilworkerInfo=new CivilworkerInfo();
                civilworkerInfo.setCertno(info.getCivilworker_id());
                civilworkerInfo = civilworkerInfoService.getCivilworkerInfoByCardNo(civilworkerInfo);
                if (StringUtils.isNotNull(civilworkerInfo)){
                    info.setCardType(civilworkerInfo.getCardType());
                }else {
                    info.setCardType("1");
                }
            }
            Page<RelocatedInfo> page = new Page<>();
            page.setRecords(relocatedInfos);
            page.setTotal(relocatedInfoMapper.FindRelocatedInfoCount(relocatedInfo.getName(), relocatedInfo.getNum(),
                    relocatedInfo.getEcode(), null));
            page.setSize(relocatedInfo.getPageSize());
            page.setCurrent(relocatedInfo.getPageNo());
            return ResultInfo.success(page);
        }

    }


    @Transactional(propagation = Propagation.REQUIRED)
    public ResultInfo saveRelocatedInfo(RelocatedInfo relocatedInfo, PersonInfo personInfo) {
        Integer count = this.autonomousMedicalMapper.findAutonomousMedicalCount(relocatedInfo.getOrg_code());
        if (count > 0) {
            return ResultInfo.error("该单位已申请自主体检");
        }
        IsConfig isConfig=isConfigMapper.selectById(1);
        if (isConfig.getIsOpen().equals("1")){
            return  ResultInfo.error("异地安置功能已经关闭");
        }
        LambdaQueryWrapper<PersonSubscribeRecord> query1Wrapper=new LambdaQueryWrapper<>();
        query1Wrapper.eq(PersonSubscribeRecord::getIs_del,"0");
        query1Wrapper.eq(PersonSubscribeRecord::getYear,relocatedInfo.getRelocated_year());
        query1Wrapper.ne(PersonSubscribeRecord::getSched,"4");
        query1Wrapper.eq(PersonSubscribeRecord::getCivilworker_id,relocatedInfo.getNum());
        long personCount=this.personSubscribeRecordMapper.selectCount(query1Wrapper);
        if (personCount>0){
            return ResultInfo.error("已经申请体检请取消后，在操作。");
        }
        SysUser user = this.sysUserService.getUser();
        LambdaQueryWrapper<CivilworkerInfo> wrapper = new LambdaQueryWrapper();
        if ("4".equals(user.getUser_type())) {
            wrapper.eq(CivilworkerInfo::getEmp_code, user.getOrg_code());
        }
        wrapper.eq(CivilworkerInfo::getCertno, relocatedInfo.getNum()).eq(CivilworkerInfo::getIs_del, "0");
        CivilworkerInfo civilworkerInfo = this.civilworkerInfoMapper.selectOne(wrapper);
        if (org.springframework.util.StringUtils.isEmpty(civilworkerInfo)) {
            return ResultInfo.error("身份证信息错误");
        }

        LambdaQueryWrapper<RelocatedInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RelocatedInfo::getCivilworker_id, relocatedInfo.getNum()).eq(RelocatedInfo::getIs_del, "0")
                .eq(RelocatedInfo::getRelocated_year, relocatedInfo.getRelocated_year());

        RelocatedInfo info = this.relocatedInfoMapper.selectOne(queryWrapper);
        if (!org.springframework.util.StringUtils.isEmpty(info)) {
            return ResultInfo.error("请勿重复添加");
        }
        //if ("1".equals(civilworkerInfo.getDeath_flag()) && "1".equals(civilworkerInfo.getQualifications()) &&
        //        "1".equals(civilworkerInfo.getOutside_flag()) && this.isRetr(personInfo.getRetr_type_code())) {

           //申请异地安置的前是异地就医或者离退休
        if ("1".equals(civilworkerInfo.getDeath_flag()) && "1".equals(civilworkerInfo.getQualifications())  && ( "1".equals(civilworkerInfo.getOutside_flag()) || this.isRetr(personInfo.getRetr_type_code()))) {
            relocatedInfo.setCivilworker_id(relocatedInfo.getNum());
            relocatedInfo.setIs_del("0");
            if ("1".equals(user.getUser_type())) {
                relocatedInfo.setAdministrative_unit(relocatedInfo.getAdministrative_unit());
            } else {
                LambdaQueryWrapper<EmployingInfo> wrapper1 = new LambdaQueryWrapper<>();
                wrapper1.eq(EmployingInfo::getEmp_no, relocatedInfo.getAdministrative_unit());
                EmployingInfo employingInfo = this.employingInfoMapper.selectOne(wrapper1);
                relocatedInfo.setAdministrative_unit(employingInfo.getParentOrgCode());
            }
            relocatedInfoMapper.insert(relocatedInfo);
            civilworkerInfo.setOutside_put("1");
            //异地安置 不通过setOutside_put判断，setQualifications
           // civilworkerInfo.setQualifications("0");
            this.civilworkerInfoMapper.updateById(civilworkerInfo);
            return ResultInfo.success();
        } else {
            return ResultInfo.error("不符合异地安置标准");
        }
    }

    public ResultInfo updateRelocatedInfo(RelocatedInfo relocatedInfo) {
        if (StringUtils.isNotEmpty(relocatedInfo.getNum())) {
            LambdaQueryWrapper<CivilworkerInfo> wrapper = new LambdaQueryWrapper();
            wrapper.eq(CivilworkerInfo::getCertno, relocatedInfo.getNum());
            CivilworkerInfo civilworkerInfo = this.civilworkerInfoMapper.selectOne(wrapper);
            relocatedInfo.setCivilworker_id(relocatedInfo.getNum());
        }
        relocatedInfoMapper.updateById(relocatedInfo);
        return ResultInfo.success();
    }


    public ResultInfo delRelocatedInfo(String id) {
        List<String> str = Arrays.asList(id.split(","));
        RelocatedInfo relocatedInfo = new RelocatedInfo();
        relocatedInfo.setIs_del("1");
        CivilworkerInfo civilworkerInfo = new CivilworkerInfo();
        civilworkerInfo.setOutside_put("0");
        civilworkerInfo.setQualifications("1");
        for (String s : str) {
            relocatedInfo.setId(s);
            relocatedInfoMapper.updateById(relocatedInfo);
            RelocatedInfo info = this.relocatedInfoMapper.selectById(s);
            LambdaQueryWrapper<CivilworkerInfo> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(CivilworkerInfo::getCertno, info.getCivilworker_id());
            this.civilworkerInfoMapper.update(civilworkerInfo, wrapper);
        }
        return ResultInfo.success();
    }

    public void export(RelocatedInfo relocatedInfo, HttpServletResponse response) throws IOException {
        SysUser user = this.sysUserService.getUser();
        List<RelocatedInfo> relocatedInfos;
        if ("1".equals(user.getUser_type())) {
            relocatedInfos = relocatedInfoMapper.FindRelocatedInfo(relocatedInfo.getName(), relocatedInfo.getNum(),
                    0,
                    10000, relocatedInfo.getEcode(), relocatedInfo.getAdministrative_unit());
            for (RelocatedInfo info : relocatedInfos) {
                info.setSEX(IdCardNumberMethod.getSexFromIdCard(info.getNum()) == 1 ? "男" : "女");
//                info.setAGE(IdCardNumberMethod.getAgeForIdcard(info.getNum()) + "");
            }
        } else {
            relocatedInfos = relocatedInfoMapper.FindRelocatedInfo(relocatedInfo.getName(), relocatedInfo.getNum(),
                    0,10000, user.getOrg_code(), null);
            for (RelocatedInfo info : relocatedInfos) {
                info.setSEX(IdCardNumberMethod.getSexFromIdCard(info.getNum()) == 1 ? "男" : "女");
//                info.setAGE(IdCardNumberMethod.getAgeForIdcard(info.getNum()) + "");
            }
        }
//        ExcelKit.$Export(EmpExport.class, response)
//                .downXlsx(this.empSubscribeRecordMapper.findEmpSubscribeRecordExport(time,empName, packName, pageNo, pageSize, uoid), false);
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getWriter();
        //自定义标题别名
        writer.addHeaderAlias("name", "姓名");
        writer.addHeaderAlias("SEX", "性别");
        writer.addHeaderAlias("AGE", "年龄");
        writer.addHeaderAlias("num", "身份证号");
        writer.addHeaderAlias("admdvs", "统筹区");
        writer.addHeaderAlias("medplace", "所属区");
        writer.addHeaderAlias("ename", "单位名称");
        writer.addHeaderAlias("ecode", "单位编码");
        writer.addHeaderAlias("account_bank", "开户行");
        writer.addHeaderAlias("account_no", "银行卡号");
        writer.addHeaderAlias("relocated_year", "安置年度");

// 默认的，未添加alias的属性也会写出，如果想只写出加了别名的字段，可以调用此方法排除之
        writer.setOnlyAlias(true);

// 合并单元格后的标题行，使用默认标题样式
        writer.merge(10, "异地安置人员信息");
// 一次性写出内容，使用默认样式，强制输出标题
        writer.write(relocatedInfos, true);
//out为OutputStream，需要写出到的目标流

//response为HttpServletResponse对象
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
//test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=RelocatedInfo.xlsx");
        ServletOutputStream out = response.getOutputStream();

        writer.flush(out, true);
// 关闭writer，释放内存
        writer.close();
//此处记得关闭输出Servlet流
        IoUtil.close(out);
    }

    @DS("reflowData")
    public PersonInfo findPerson(String cerNo) {
        PersonInfo personInfo = this.personInfoMapper.getPersonInfoByCarno(cerNo);
        return personInfo;
    }

    @DS("reflowData")
    public List<PersonInfo> findPersonList(List<String> cerNo) {
        String join = String.join("','", cerNo);
        List<PersonInfo> personInfo = this.personInfoMapper.getPersonInfoByCarnos(join);
        return personInfo;
    }

    @DS("reflowData")
    public PersonInfo findPersonOne(String cerNo) {
        PersonInfo personInfo = this.personInfoMapper.getPersonInfoByCarnoOne(cerNo);
        return personInfo;
    }


    private boolean isRetr(String type) {
        if ("2".equals(type) || "4".equals(type) || "5".equals(type) ||
                "6".equals(type) || "7".equals(type) ||
                "8".equals(type) || "99".equals(type)) {
            return true;
        } else {
            return false;
        }
    }

    public ResultInfo saveRelocatedInfoFlag(RelocatedInfo relocatedInfo) {
        Integer count = this.autonomousMedicalMapper.findAutonomousMedicalCount(relocatedInfo.getOrg_code());
        if (count > 0) {
            return ResultInfo.error("该单位已申请自主体检");
        }
        LambdaQueryWrapper<CivilworkerInfo> wrapper = new LambdaQueryWrapper();
        wrapper.eq(CivilworkerInfo::getCertno, relocatedInfo.getNum()).eq(CivilworkerInfo::getIs_del, "0");
        CivilworkerInfo civilworkerInfo = this.civilworkerInfoMapper.selectOne(wrapper);
        if (org.springframework.util.StringUtils.isEmpty(civilworkerInfo)) {
            return ResultInfo.error("身份证信息错误");
        }

        //添加的年份是否有重复
        LambdaQueryWrapper<RelocatedInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RelocatedInfo::getCivilworker_id, relocatedInfo.getNum()).eq(RelocatedInfo::getIs_del, "0");
        List<RelocatedInfo> infos = this.relocatedInfoMapper.selectList(queryWrapper);
        for(RelocatedInfo info:infos) {
            if (!org.springframework.util.StringUtils.isEmpty(info) && (relocatedInfo.getRelocated_year().equals(info.getRelocated_year()) && StringUtils.isNotEmpty(relocatedInfo.getRelocated_year()))) {
                return ResultInfo.error("请勿重复添加");
            }
        }
//        获取当前年份、⽉份和⽇期等。
// 获取当前年
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        LambdaQueryWrapper<PersonSubscribeRecord> psrwrapper = new LambdaQueryWrapper<>();
        psrwrapper.eq(PersonSubscribeRecord::getYear, year).eq(PersonSubscribeRecord::getCivilworker_id, relocatedInfo.getNum()).eq(PersonSubscribeRecord::getIs_del, "0");
        Long records = this.personSubscribeRecordMapper.selectCount(psrwrapper);
        if (records > 0) {
            return ResultInfo.error("人员已经参加体检预约");
        }
        if ("1".equals(civilworkerInfo.getDeath_flag()) && "1".equals(civilworkerInfo.getQualifications())) {
            relocatedInfo.setCivilworker_id(relocatedInfo.getNum());
            relocatedInfo.setIs_del("0");
            relocatedInfo.setAdministrative_unit(relocatedInfo.getAdministrative_unit());
            LambdaQueryWrapper<EmployingInfo> wrapper1 = new LambdaQueryWrapper<>();
            wrapper1.eq(EmployingInfo::getEmp_no, relocatedInfo.getAdministrative_unit());
            EmployingInfo employingInfo = this.employingInfoMapper.selectOne(wrapper1);
            if (!org.springframework.util.StringUtils.isEmpty(employingInfo)) {
                relocatedInfo.setAdministrative_unit(employingInfo.getParentOrgCode());
            }
            relocatedInfoMapper.insert(relocatedInfo);
            civilworkerInfo.setOutside_put("1");
            //异地安置人员根据年份排除
            //civilworkerInfo.setQualifications("1");
            this.civilworkerInfoMapper.updateById(civilworkerInfo);
            return ResultInfo.success();
        } else {
            return ResultInfo.error("人员状态异常或者没有体检资格");
        }
    }

    /**
     * 查询<code>year</code>体检年度所有异地安置人员
     * @param year
     * @return
     */
    public Set<String> findCivilworkerByRelocatedYear(String year) {
        Set<String> civilworkerCertNos = new HashSet<>();
        LambdaQueryWrapper<RelocatedInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RelocatedInfo::getIs_del, "0");
        if(StringUtils.isNotEmpty(year)){
            queryWrapper.eq(RelocatedInfo::getRelocated_year, year);
        }

        List<RelocatedInfo> infos = this.relocatedInfoMapper.selectList(queryWrapper);

        for (RelocatedInfo r: infos) {
            civilworkerCertNos.add(r.getCivilworker_id());
        }
        return  civilworkerCertNos;
    }
}
