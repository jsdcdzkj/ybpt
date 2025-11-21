package com.jsdc.ybpt.controller;

import com.alibaba.fastjson.JSONObject;
import com.jsdc.ybpt.model.FileInfo;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_check.ExpenseDetail;
import com.jsdc.ybpt.service.ExpenseDetailService;
import com.jsdc.ybpt.util.FastDfs.FastDfsParams;
import com.jsdc.ybpt.util.FastDfs.FastDfsUtil;
import com.jsdc.ybpt.vo.ExpenseDetailVo;
import com.jsdc.ybpt.vo.PersonSubscribeRecordVo;
import com.jsdc.ybpt.vo.ResultInfo;
import lombok.RequiredArgsConstructor;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
@RequestMapping("/expenseDetail")
public class ExpenseDetailController {

    @Autowired
     ExpenseDetailService expenseDetailService;

    @RequestMapping("/saveOrUpdate")
    public ResultInfo saveOrUpdate(@RequestBody ExpenseDetail detail) throws IOException {
        return  expenseDetailService.saveOrUpdateDetail(detail);
    }

    @RequestMapping("/del")
    public ResultInfo del(ExpenseDetail detail){
        expenseDetailService.del(detail);
        return  ResultInfo.success();
    }

    @RequestMapping("/getList")
    public ResultInfo getList(ExpenseDetail detail){
        return  ResultInfo.success(expenseDetailService.getList(detail));
    }

    @RequestMapping("/getPageList")
    public ResultInfo getPageList(ExpenseDetailVo detail){
        return  ResultInfo.success(expenseDetailService.getPageList(detail));
    }

    @RequestMapping("/getDetail")
    public ResultInfo getDetail(ExpenseDetail detail){
        return  ResultInfo.success(expenseDetailService.getDetail(detail));
    }

    @RequestMapping("/upload")
    @ResponseBody
    public ResultInfo multipleCommentImageUpload(@RequestParam(value = "file", required = false) List<MultipartFile> files,String id) throws IOException {
        return  expenseDetailService.multipleCommentImageUpload(files,id);
    }

    @PostMapping("/Export")
    public void yyExport(@RequestBody ExpenseDetailVo expenseDetailVo, HttpServletResponse response) throws Exception{
        expenseDetailService.yyExport(expenseDetailVo,response);
    }


}
