package com.jsdc.ybpt.service;

import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.sax.Excel07SaxReader;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.abnormal.SettleErrorData;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.eval_.EvalDesign;
import com.jsdc.ybpt.mapper.ExpenseDetailMapper;
import com.jsdc.ybpt.model.FileInfo;
import com.jsdc.ybpt.model.SysDict;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_check.Expense;
import com.jsdc.ybpt.model_check.ExpenseDetail;
import com.jsdc.ybpt.model_check.PersonSubscribeRecord;
import com.jsdc.ybpt.util.DateUtil;
import com.jsdc.ybpt.util.FastDfs.FastDfsParams;
import com.jsdc.ybpt.util.FastDfs.FastDfsUtil;
import com.jsdc.ybpt.util.IdCardNumberMethod;
import com.jsdc.ybpt.util.ListGroupUtil;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.util.exception.CustomException;
import com.jsdc.ybpt.vo.ExpenseDetailVo;
import com.jsdc.ybpt.vo.PersonSubscribeRecordVo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.apache.http.entity.ContentType;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author csx
 * @since 2024-06-11
 */
@Service
public class ExpenseDetailService extends BaseService<ExpenseDetail> {

    @Autowired
     FastDfsUtil fastDfsUtil;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    FileInfoService fileInfoService;



    public Page<ExpenseDetail> getPageList(ExpenseDetailVo detail){
        SysUser user = sysUserService.getUser();
        LambdaQueryWrapper<ExpenseDetail> wrapper=new LambdaQueryWrapper();
        wrapper.eq(StringUtils.isNotEmpty(detail.getYear()),ExpenseDetail::getYear,detail.getYear()).
                like(StringUtils.isNotEmpty(detail.getName()),ExpenseDetail::getName,detail.getName()).
                eq(ExpenseDetail::getIsexpense,"0").eq(user.getUser_type().equals("4"), ExpenseDetail::getCreateUser,user.getId());
        wrapper.eq(ExpenseDetail::getIsDel,0).orderByDesc(ExpenseDetail::getCreateTime);
        Page<ExpenseDetail> page = this.page(new Page<>(detail.getPageNo(), detail.getPageSize()), wrapper);
        return page;
    }

    public List<ExpenseDetail> getList(ExpenseDetail detail){
        LambdaQueryWrapper<ExpenseDetail> wrapper=new LambdaQueryWrapper();
        wrapper.eq(StringUtils.isNotEmpty(detail.getExpenseId()),ExpenseDetail::getExpenseId,detail.getExpenseId());
        wrapper.eq(ExpenseDetail::getIsDel,0);
        List<ExpenseDetail> details=this.list(wrapper);
        return details;
    }

    public  void  del(ExpenseDetail detail){
        ExpenseDetail expenseDetail = getById(detail.getId());
        expenseDetail.setIsDel("1");
        updateById(detail);
    }

    public ResultInfo saveOrUpdateDetail(ExpenseDetail detail) throws IOException {
        SysUser user = sysUserService.getUser();
        LambdaQueryWrapper<ExpenseDetail> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(ExpenseDetail::getYear,detail.getYear()).eq(ExpenseDetail::getIsDel,"0").eq(ExpenseDetail::getIsexpense,"0").
                eq(ExpenseDetail::getCreateUser,user.getId());
        List<ExpenseDetail> list = list(wrapper);
        if (list.size()>0){
            return ResultInfo.error("添加失败,本年度已经申请！");
        }
//        SysUser user = new SysUser();
//        user.setId("320312");
        detail.setId(IdUtil.simpleUUID());
        detail.setCreateUser(user.getId());
        detail.setIsDel("0");
        detail.setCreateTime(new Date());
        detail.setIsexpense("0");
        save(detail);
        detail.getFiles().setBizId(detail.getId());
        fileInfoService.updateById(detail.getFiles());
        return ResultInfo.success(detail);
    }

    public  ExpenseDetail getDetail(ExpenseDetail detail){
        ExpenseDetail expenseDetail = getById(detail.getId());
        return expenseDetail;
    }


    public void yyExport(ExpenseDetailVo detail, HttpServletResponse response) throws Exception{
        LambdaQueryWrapper<ExpenseDetail> wrapper=new LambdaQueryWrapper();
        wrapper.eq(!StringUtils.isEmpty(detail.getYear()),ExpenseDetail::getYear,detail.getYear()).like(!StringUtils.isEmpty(detail.getName()),ExpenseDetail::getName,detail.getName())
                .eq(!StringUtils.isEmpty(detail.getExpenseId()),ExpenseDetail::getExpenseId,detail.getExpenseId());
        wrapper.eq(ExpenseDetail::getIsDel,0);
        List<ExpenseDetail> list=this.list(wrapper);


        ExcelWriter writer = ExcelUtil.getBigWriter();
        if (!StringUtils.isEmpty(detail.getExpenseId())){
            writer.addHeaderAlias("name", "单位名称");
        }
        writer.addHeaderAlias("account", "财务帐户");
        writer.addHeaderAlias("bank", "开户银行");
        writer.addHeaderAlias("cardNo", "账号");
        writer.addHeaderAlias("year", "年份");
        writer.addHeaderAlias("linkman", "联系人");
        writer.addHeaderAlias("phone", "联系方式");
        writer.addHeaderAlias("phone", "手机号");
        if (!StringUtils.isEmpty(detail.getExpenseId())){
            writer.addHeaderAlias("admdvs", "所属区");
        }
        writer.setOnlyAlias(true);
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        //out为OutputStream，需要写出到的目标流
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=downLoad.xls");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        // 关闭writer，释放内存
        writer.close();
        //此处记得关闭输出Servlet流
        IoUtil.close(out);



    }

    public ResultInfo multipleCommentImageUpload(List<MultipartFile> files,String id) throws IOException {
        for (MultipartFile m : files) {
            //上传文件服务器
            FastDfsParams params = new FastDfsParams("civil/Expense", m, "28", id);
            params.setFileName(m.getOriginalFilename());
            ResultInfo resultInfo = fastDfsUtil.uploadFile(params);
            if (resultInfo.getCode() != 0) {
                throw new CustomException(resultInfo.getMsg());
            }
            return resultInfo;
        }
        return ResultInfo.success();
    }
}
