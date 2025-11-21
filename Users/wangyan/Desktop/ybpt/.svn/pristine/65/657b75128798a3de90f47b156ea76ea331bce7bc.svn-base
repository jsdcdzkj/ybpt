package com.jsdc.ybpt.service;

import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;
import cn.hutool.poi.excel.sax.Excel07SaxReader;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.ExpenseDetailMapper;
import com.jsdc.ybpt.model.FileInfo;
import com.jsdc.ybpt.model.SysDict;
import com.jsdc.ybpt.model.SysFile;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_check.Expense;
import com.jsdc.ybpt.model_check.ExpenseDetail;
import com.jsdc.ybpt.util.FastDfs.FastDfsParams;
import com.jsdc.ybpt.util.FastDfs.FastDfsUtil;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.util.exception.CustomException;
import com.jsdc.ybpt.vo.ExpenseDetailVo;
import com.jsdc.ybpt.vo.ExpenseVo;
import com.jsdc.ybpt.vo.ResultInfo;
import com.microsoft.schemas.office.visio.x2012.main.CellType;
import org.apache.http.entity.ContentType;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import javax.swing.border.Border;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
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
public class ExpenseService extends BaseService<Expense> {
    @Autowired
     FastDfsUtil fastDfsUtil;
    @Value("${uploadtjreportPath}")
    private String uploadtjreportPath;

    @Autowired
    ExpenseDetailService expenseDetailService;
    @Autowired
    SysUserService sysUserService;
    @Autowired
     SysDictService sysDictService;
    @Autowired
     FileInfoService fileInfoService;


    public ResultInfo saveExpense(Expense bean) throws IOException {
        SysUser user = sysUserService.getUser();
        LambdaQueryWrapper<Expense> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(Expense::getYear,bean.getYear()).eq(Expense::getCreateUser,user.getId());
        List<Expense> list = list(wrapper);
        if (list.size()>0){
            return ResultInfo.error("添加失败,本年度已经申请！");
        }
//        SysUser user = new SysUser();
//        user.setOrg_code("320312");
        SysDict sysDict2 = new SysDict();
        sysDict2.setDict_type("ADMDVS");
        sysDict2.setValue(user.getOrg_code());
        SysDict sysDict3 = sysDictService.selectByValue(sysDict2);
        if (null != sysDict3) {
            bean.setAdmdvsName(sysDict3.getLabel());
        }
        bean.setId(IdUtil.simpleUUID());
        bean.setCreateTime(new Date());
        bean.setCreateUser(user.getId());
        bean.setAdmdvs(user.getOrg_code());
        save(bean);
        if (StringUtils.isNotEmpty(bean.getExpenseDetails())){
            Calendar cal=Calendar.getInstance();
            bean.getExpenseDetails().forEach(a->{
                Integer count=0;
                ExpenseDetail detail=a;
                cal.add(Calendar.SECOND,count);
                detail.setId(IdUtil.simpleUUID());
                detail.setCreateTime(cal.getTime());
                detail.setCreateUser(user.getId());
                detail.setAdmdvsId(user.getOrg_code());
                detail.setAdmdvs(bean.getAdmdvsName());
                detail.setIsDel("0");
                detail.setYear(bean.getYear());
                detail.setIsexpense("1");
                detail.setExpenseId(bean.getId());
                expenseDetailService.save(detail);
                count++;
            });
        }
        bean.getFiles().setBizId(bean.getId());
        fileInfoService.updateById(bean.getFiles());
        return ResultInfo.success(bean);
    }


    public Page<Expense> getPageList(ExpenseVo expense){
        SysUser user = sysUserService.getUser();
        LambdaQueryWrapper<Expense> wrapper=new LambdaQueryWrapper();
        wrapper.eq(StringUtils.isNotEmpty(expense.getYear()),Expense::getYear,expense.getYear()).
                eq(!user.getOrg_code().equals("320399"),Expense::getAdmdvs,user.getOrg_code()).
                eq(user.getUser_type().equals("4"),Expense::getCreateUser,user.getId()).orderByDesc(Expense::getCreateTime);
        Page<Expense> page = this.page(new Page<>(expense.getPageNo(), expense.getPageSize()), wrapper);
        page.getRecords().forEach(a->{
            if (StringUtils.isNotEmpty(a.getCreateUser())){
                SysUser byId = sysUserService.getById(a.getCreateUser());
                a.setCreateName(byId.getUsername());
            }
        });
        return page;
    }


    public FileInfo downLoad(ExpenseVo detail){
        QueryWrapper<FileInfo> queryWrapper = new QueryWrapper<>() ;
        queryWrapper.eq("bizType","28") ;
        queryWrapper.eq("bizId",detail.getId()) ;
        FileInfo fileInfo = fileInfoService.getOne(queryWrapper) ;
        return fileInfo;
    }

    public ResultInfo multipleCommentImageUpload(List<MultipartFile> files) throws IOException {
        for (MultipartFile m : files) {
            //上传文件服务器
            FastDfsParams params = new FastDfsParams("civil/Expense", m, "28", null);
            params.setFileName(m.getOriginalFilename());
            ResultInfo resultInfo = fastDfsUtil.uploadFile(params);
            if (resultInfo.getCode() != 0) {
                throw new CustomException(resultInfo.getMsg());
            }
            return resultInfo;
        }
        return ResultInfo.success();
    }

    public ResultInfo getDict(){
        SysUser user = sysUserService.getUser();
        SysDict sysDict2 = new SysDict();
        sysDict2.setDict_type("ADMDVS");
        sysDict2.setValue(user.getOrg_code());
        SysDict sysDict3 = sysDictService.selectByValue(sysDict2);
        return ResultInfo.success(sysDict3);
    }


    public void del(ExpenseVo vo){
        removeById(vo.getId());
    }



    public Expense importData(MultipartFile files) {
        SysUser sysUser = sysUserService.getUser();
        Expense expense = new Expense() ;
        SysDict sysDict2 = new SysDict();
        sysDict2.setDict_type("ADMDVS");
        sysDict2.setValue(sysUser.getOrg_code());
//        sysDict2.setValue("320399");
        SysDict sysDict3 = sysDictService.selectByValue(sysDict2);
        expense.setAdmdvs(sysDict3.getValue());
        expense.setAdmdvsName(sysDict3.getLabel());
        expense.setCreateTime(new Date());
        expense.setCreateUser(sysUser.getId());
//        expense.setCreateUser("1");
        try {
            boolean is03Excel = files.getOriginalFilename().matches("^.+\\.(?i)(xlsx)$") ? true : false;
            Workbook workbook = is03Excel ? new XSSFWorkbook(files.getInputStream()) : new HSSFWorkbook(files.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            sheet.getRow(1).getCell(5).setCellType(org.apache.poi.ss.usermodel.CellType.STRING);
            expense.setLinkman(sheet.getRow(1).getCell(5).getStringCellValue());
            sheet.getRow(1).getCell(7).setCellType(org.apache.poi.ss.usermodel.CellType.STRING);
            expense.setPhone(sheet.getRow(1).getCell(7).getStringCellValue());
            sheet.getRow(2).getCell(1).setCellType(org.apache.poi.ss.usermodel.CellType.STRING);
            expense.setBank(sheet.getRow(2).getCell(1).getStringCellValue());
            sheet.getRow(2).getCell(3).setCellType(org.apache.poi.ss.usermodel.CellType.STRING);
            expense.setCardNo(sheet.getRow(2).getCell(3).getStringCellValue());
            sheet.getRow(2).getCell(7).setCellType(org.apache.poi.ss.usermodel.CellType.STRING);
            expense.setAccount(sheet.getRow(2).getCell(7).getStringCellValue());
            int person = 0;
            List<ExpenseDetail> detailVos = new ArrayList<>() ;
            for (int i = 5; i <= sheet.getLastRowNum()-1; i++) {
                ExpenseDetail detail=new ExpenseDetail();
                Row row = sheet.getRow(i);

                Cell cell0 = row.getCell(1);
                cell0.setCellType(org.apache.poi.ss.usermodel.CellType.STRING);
                detail.setUnitId(cell0.getStringCellValue());

                Cell cell1 = row.getCell(3);
                cell1.setCellType(org.apache.poi.ss.usermodel.CellType.STRING);
                detail.setUnitName(cell1.getStringCellValue());
                Cell cell2 = row.getCell(7);
                cell2.setCellType(org.apache.poi.ss.usermodel.CellType.STRING);
                detail.setPersonNum(cell2.getStringCellValue());
                Cell cell3 = row.getCell(8);
                cell3.setCellType(org.apache.poi.ss.usermodel.CellType.STRING);
                detail.setRemark(cell3.getStringCellValue());
                person+=Integer.parseInt(org.springframework.util.StringUtils.isEmpty(detail.getPersonNum())?"0":detail.getPersonNum());
                detailVos.add(detail);
            }
            expense.setPersonNum(String.valueOf(person));
            expense.setMoney(String.valueOf(person*600));
            expense.setExpenseDetails(detailVos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return expense ;
    }


    public String yyExport(ExpenseDetailVo detail, HttpServletResponse response) throws Exception{
        Expense expense=getById(detail.getExpenseId());
        LambdaQueryWrapper<ExpenseDetail> wrapper=new LambdaQueryWrapper();
        wrapper.eq(!StringUtils.isEmpty(detail.getYear()),ExpenseDetail::getYear,detail.getYear()).like(!StringUtils.isEmpty(detail.getName()),ExpenseDetail::getName,detail.getName())
                .eq(!StringUtils.isEmpty(detail.getExpenseId()),ExpenseDetail::getExpenseId,detail.getExpenseId());
        wrapper.eq(ExpenseDetail::getIsDel,0);
        List<ExpenseDetail> list=expenseDetailService.list(wrapper);


        // 创建工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();// 内存中保留 1000 条数据，以免内存溢出，其余写入 硬盘   
        // 创建工作表
        HSSFSheet sheet = workbook.createSheet();
        //合并单元格
        CellRangeAddress callRangeAddress = new CellRangeAddress(0, 0, 0, 8);//起始行,结束行,起始列,结束列
        sheet.addMergedRegion(callRangeAddress);
        // 创建第1行
        Row row1 = sheet.createRow(0);
        //行高度
        row1.setHeightInPoints(50);
        //第一行表头
        Cell cell10 = row1.createCell(0);
        cell10.setCellValue(expense.getYear()+"年徐州市各区享受公务员医疗补助人员体检费用填报明细表");
        //样式
        CellStyle style = workbook.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        style.setAlignment(HorizontalAlignment.CENTER);//水平居中
        //字体
        Font font2 = workbook.createFont();
        font2.setFontHeightInPoints((short) 16);
        font2.setBold(true);//字体增粗
        style.setFont(font2);
        cell10.setCellStyle(style);
        // 创建第2行
        Row row2 = sheet.createRow(1);
        CellStyle style1 = workbook.createCellStyle();
        style1.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        style1.setAlignment(HorizontalAlignment.CENTER);//水平居中
        style1.setBorderBottom(BorderStyle.THIN);
        style1.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style1.setBorderLeft(BorderStyle.THIN);
        style1.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style1.setBorderTop(BorderStyle.THIN);
        style1.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style1.setBorderRight(BorderStyle.THIN);
        style1.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style1.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        style1.setAlignment(HorizontalAlignment.CENTER);//水平居中
        Font font3 = workbook.createFont();
        font3.setFontHeightInPoints((short) 14);
        font3.setBold(false);//字体增粗
        style1.setFont(font3);
        row2.setHeightInPoints(50);
        Cell cell21 = row2.createCell(0);
        cell21.setCellValue("所属区：");
        cell21.setCellStyle(style1);
        CellRangeAddress callRangeAddress22 = new CellRangeAddress(1, 1, 1, 3);//起始行,结束行,起始列,结束列
        sheet.addMergedRegion(callRangeAddress22);
        setBorderStyle(sheet,callRangeAddress22);
        Cell cell22 = row2.createCell(1);
        cell22.setCellValue(expense.getAdmdvsName());
        cell22.setCellStyle(style1);
        Cell cell23 = row2.createCell(4);
        cell23.setCellValue("联系人：");
        cell23.setCellStyle(style1);
        Cell cell24 = row2.createCell(5);
        cell24.setCellValue(expense.getLinkman());
        cell24.setCellStyle(style1);
        Cell cell25 = row2.createCell(6);
        cell25.setCellValue("联系方式：");
        cell25.setCellStyle(style1);
        CellRangeAddress callRangeAddress23 = new CellRangeAddress(1, 1, 7, 8);//起始行,结束行,起始列,结束列
        sheet.addMergedRegion(callRangeAddress23);
        setBorderStyle(sheet,callRangeAddress23);
        Cell cell26 = row2.createCell(7);
        cell26.setCellValue(expense.getPhone());
        cell26.setCellStyle(style1);

        // 创建第3行
        Row row3 = sheet.createRow(2);
        row3.setHeightInPoints(50);
        Cell cell31 = row3.createCell(0);
        cell31.setCellValue("开户银行：");
        cell31.setCellStyle(style1);
        Cell cell32 = row3.createCell(1);
        cell32.setCellValue(expense.getBank());
        cell32.setCellStyle(style1);
        Cell cell33 = row3.createCell(2);
        cell33.setCellValue("银行账号：");
        cell33.setCellStyle(style1);
        CellRangeAddress callRangeAddress33 = new CellRangeAddress(2, 2, 3, 5);//起始行,结束行,起始列,结束列
        sheet.addMergedRegion(callRangeAddress33);
        setBorderStyle(sheet,callRangeAddress33);
        Cell cell34 = row3.createCell(3);
        cell34.setCellValue(expense.getCardNo());
        cell34.setCellStyle(style1);
        Cell cell35 = row3.createCell(6);
        cell35.setCellValue("财政专户：");
        cell35.setCellStyle(style1);
        CellRangeAddress callRangeAddress34 = new CellRangeAddress(2, 2, 7, 8);//起始行,结束行,起始列,结束列
        sheet.addMergedRegion(callRangeAddress34);
        setBorderStyle(sheet,callRangeAddress34);
        Cell cell36 = row3.createCell(7);
        cell36.setCellValue(expense.getAccount());
        cell36.setCellStyle(style1);

        // 创建第4行
        Row row4 = sheet.createRow(3);
        row4.setHeightInPoints(60);
        CellRangeAddress callRangeAddress44 = new CellRangeAddress(3, 3, 0, 1);//起始行,结束行,起始列,结束列
        sheet.addMergedRegion(callRangeAddress44);
        setBorderStyle(sheet,callRangeAddress44);
        Cell cell41 = row4.createCell(0);
        cell41.setCellValue("所属区内各单位享受公务员医疗补助人员总数：");
        cell41.setCellStyle(style1);
        Cell cell42 = row4.createCell(2);
        cell42.setCellValue(expense.getPersonNum());
        cell42.setCellStyle(style1);
        CellRangeAddress callRangeAddress45 = new CellRangeAddress(3, 3, 3, 5);//起始行,结束行,起始列,结束列
        sheet.addMergedRegion(callRangeAddress45);
        setBorderStyle(sheet,callRangeAddress45);
        Cell cell43 = row4.createCell(3);
        cell43.setCellValue("总金额（所属区内各单位享受公务员医疗补助人员总数*600元/每人）：");
        cell43.setCellStyle(style1);
        CellRangeAddress callRangeAddress46 = new CellRangeAddress(3, 3, 6, 8);//起始行,结束行,起始列,结束列
        sheet.addMergedRegion(callRangeAddress46);
        setBorderStyle(sheet,callRangeAddress46);
        Cell cell44 = row4.createCell(6);
        cell44.setCellValue(expense.getMoney());
        cell44.setCellStyle(style1);
        // 创建第5行
        Row row5 = sheet.createRow(4);
        String title2 = "序号,下涉单位编码（缴纳社会保险费时生成的单位编码）,单位名称,单位人数,备注";
        String titles2[] = title2.split(",");
        for (int i = 0; i < 9; i++) {
            sheet.setColumnWidth(i, 5000);//调整 宽度
            switch (i){
                case 0:
                    Cell cell0 = row5.createCell(i );
                    cell0.setCellValue(titles2[0]);
                    cell0.setCellStyle(style1);
                    continue;
                case 1:
                    CellRangeAddress callRangeAddress57 = new CellRangeAddress(4, 4, 1, 2);//起始行,结束行,起始列,结束列
                    sheet.addMergedRegion(callRangeAddress57);
                    setBorderStyle(sheet,callRangeAddress57);
                    Cell cell1 = row5.createCell(i );
                    cell1.setCellValue(titles2[1]);
                    cell1.setCellStyle(style1);
                    continue;
                case 2:
                case 4:
                case 5:
                case 6:
                    continue;
                case 3:
                    CellRangeAddress callRangeAddress58 = new CellRangeAddress(4, 4, 3, 6);//起始行,结束行,起始列,结束列
                    sheet.addMergedRegion(callRangeAddress58);
                    setBorderStyle(sheet,callRangeAddress58);
                    Cell cell3 = row5.createCell(i );
                    cell3.setCellValue(titles2[2]);
                    cell3.setCellStyle(style1);
                    continue;
                case 7:
                    Cell cell7 = row5.createCell(i );
                    cell7.setCellValue(titles2[3]);
                    cell7.setCellStyle(style1);
                    continue;
                case 8:
                    Cell cell8 = row5.createCell(i );
                    cell8.setCellValue(titles2[4]);
                    cell8.setCellStyle(style1);
                    continue;
            }
        }

        //列表内容
        for (int i = 0; i < list.size(); i++) {
            sheet.setColumnWidth(i, 5000);//调整 宽度
            // 创建第4行及以后行数
            Row row = sheet.createRow(i+5);


            Cell cell0 = row.createCell(0);// 序列
            cell0.setCellValue(i + 1);
            cell0.setCellStyle(style1);
            CellRangeAddress callRangeAddress61 = new CellRangeAddress(i+5, i+5, 1, 2);//起始行,结束行,起始列,结束列
            sheet.addMergedRegion(callRangeAddress61);
            setBorderStyle(sheet,callRangeAddress61);
            //编码
            Cell cell1 = row.createCell(1);
            cell1.setCellValue(list.get(i).getUnitId());
            cell1.setCellStyle(style1);
            CellRangeAddress callRangeAddress62 = new CellRangeAddress(i+5, i+5, 3, 6);//起始行,结束行,起始列,结束列
            sheet.addMergedRegion(callRangeAddress62);
            setBorderStyle(sheet,callRangeAddress62);
            // 单位
            Cell cell2 = row.createCell(3);
            cell2.setCellValue(list.get(i).getUnitName());
            cell2.setCellStyle(style1);
            //	人数
            Cell cell3 = row.createCell(7);
            cell3.setCellValue(list.get(i).getPersonNum());
            cell3.setCellStyle(style1);
            //备注
            Cell cell4 = row.createCell(8);
            cell4.setCellValue(list.get(i).getRemark());
            cell4.setCellStyle(style1);
        }

        OutputStream outputStream = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel");
        try {
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("down.xls", "UTF-8"));
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        }catch (IOException e) {
            e.printStackTrace();
            return "1";//失败
        }

        return "2";//成功
    }

    private static void setBorderStyle(Sheet sheet, CellRangeAddress region) {
        // 合并单元格左边框样式
        RegionUtil.setBorderLeft(BorderStyle.THIN, region, sheet);
        RegionUtil.setLeftBorderColor(IndexedColors.BLACK.getIndex(), region, sheet);

        // 合并单元格上边框样式
        RegionUtil.setBorderTop(BorderStyle.THIN, region, sheet);
        RegionUtil.setTopBorderColor(IndexedColors.BLACK.getIndex(), region, sheet);

        // 合并单元格右边框样式
        RegionUtil.setBorderRight(BorderStyle.THIN, region, sheet);
        RegionUtil.setRightBorderColor(IndexedColors.BLACK.getIndex(), region, sheet);

        // 合并单元格下边框样式
        RegionUtil.setBorderBottom(BorderStyle.THIN, region, sheet);
        RegionUtil.setBottomBorderColor(IndexedColors.BLACK.getIndex(), region, sheet);
    }
}

