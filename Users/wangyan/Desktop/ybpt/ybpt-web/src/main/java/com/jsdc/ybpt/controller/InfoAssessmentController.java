package com.jsdc.ybpt.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.infoAssessment.InfoAssessment;
import com.jsdc.ybpt.mapper.InfoAssessmentMapper;
import com.jsdc.ybpt.model.Bank;
import com.jsdc.ybpt.service.BankService;
import com.jsdc.ybpt.service.InfoAssessmentService;
import com.jsdc.ybpt.service.LoanApplicationService;
import com.jsdc.ybpt.service.SysDictService;
import com.jsdc.ybpt.util.exception.CustomException;
import com.jsdc.ybpt.vo.BankVo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@RequestMapping("/infoAssessment")
public class InfoAssessmentController {
    @Autowired
    private InfoAssessmentService infoAssessmentService;


    /**
     *
     *修改考核信息
     * @author wzn
     * @date 2024/10/28 10:50
     */
    @PostMapping("/updateInfoAssessment")
    public ResultInfo updateInfoAssessment(@RequestBody InfoAssessment infoAssessment) {
        infoAssessmentService.updateInfoAssessment(infoAssessment);
        return ResultInfo.success();
    }


    /**
     *
     *删除考核
     * @author wzn
     * @date 2024/10/28 11:27
     */
    @RequestMapping("/delInfoAssessment")
    public ResultInfo delInfoAssessment(String id) {
      infoAssessmentService.delInfoAssessment(id) ;
        return ResultInfo.success();
    }

    /**
    *列表
    * Author wzn
    * Date 2022/7/4 13:53
    */
    @PostMapping("/selectList")
    public ResultInfo selectList(@RequestBody InfoAssessment infoAssessment){
        Page<InfoAssessment> infoAssessmentPage = infoAssessmentService.selectList(infoAssessment) ;
        return ResultInfo.success(infoAssessmentPage);
    }


    /**
     *
     *文件导入
     * @author wzn
     * @date 2024/10/28 14:22
     */
    @RequestMapping("importData")
    public ResultInfo uploadFile(MultipartFile file) {
        infoAssessmentService.importData(file);
        return ResultInfo.success();
    }


    /**
     *
     *模板导出
     * @author wzn
     * @date 2024/10/28 15:47
     */
    @RequestMapping("down")
    public void down(@RequestBody InfoAssessment infoAssessment, HttpServletResponse response) {

         infoAssessmentService.down(infoAssessment, response);
    }


}
