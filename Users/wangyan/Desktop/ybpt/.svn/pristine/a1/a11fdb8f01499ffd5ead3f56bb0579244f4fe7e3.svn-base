package com.jsdc.ybpt.controller;

import com.jsdc.ybpt.model_check.Expense;
import com.jsdc.ybpt.model_check.ExpenseDetail;
import com.jsdc.ybpt.service.ExpenseDetailService;
import com.jsdc.ybpt.service.ExpenseService;
import com.jsdc.ybpt.util.FastDfs.FastDfsParams;
import com.jsdc.ybpt.util.FastDfs.FastDfsUtil;
import com.jsdc.ybpt.vo.ExpenseDetailVo;
import com.jsdc.ybpt.vo.ExpenseVo;
import com.jsdc.ybpt.vo.ResultInfo;
import lombok.RequiredArgsConstructor;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author csx
 * @since 2024-06-11
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/expense")
public class ExpenseController {

    @Autowired
    ExpenseService expenseService;


    @RequestMapping("/saveExpense")
    public ResultInfo saveExpense(@RequestBody Expense expense) throws IOException {
        return expenseService.saveExpense(expense);
    }

    @RequestMapping("/getPageList")
    public ResultInfo getPageList(ExpenseVo detail){
        return  ResultInfo.success(expenseService.getPageList(detail));
    }


    @RequestMapping("/downLoad")
    public ResultInfo downLoad(ExpenseVo detail){
        return  ResultInfo.success(expenseService.downLoad(detail));
    }


    @RequestMapping("/upload")
    @ResponseBody
    public ResultInfo multipleCommentImageUpload(@RequestParam(value = "file", required = false) List<MultipartFile> files) throws IOException {
        return  expenseService.multipleCommentImageUpload(files);
    }


    @RequestMapping("/getDict")
    @ResponseBody
    public ResultInfo getDict(){
        return  expenseService.getDict();
    }

    @RequestMapping("/uploadFile")
    @ResponseBody
    public ResultInfo uploadFile(@RequestParam(value = "file", required = false) MultipartFile files){
        return ResultInfo.success(expenseService.importData(files));
    }

    @RequestMapping("/yyExport")
    @ResponseBody
    public String yyExport(ExpenseDetailVo detail, HttpServletResponse response) throws Exception {
       return expenseService.yyExport(detail,response);
    }

    @RequestMapping("/del")
    @ResponseBody
    public ResultInfo del(ExpenseVo vo){
        expenseService.del(vo);
        return ResultInfo.success();
    }

}
