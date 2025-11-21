package com.jsdc.ybpt.controller_eval;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.eval_.EvalDesign;
import com.jsdc.ybpt.eval_.EvalOrgTask;
import com.jsdc.ybpt.eval_.EvalOrgTaskVo;
import com.jsdc.ybpt.eval_.EvalTaskManage;
import com.jsdc.ybpt.service.eval.EvalDesignService;
import com.jsdc.ybpt.service.eval.EvalOrgDetailService;
import com.jsdc.ybpt.service.eval.EvalOrgTaskService;
import com.jsdc.ybpt.service.eval.EvalTaskManageService;
import com.jsdc.ybpt.vo.EvalDesignVo;
import com.jsdc.ybpt.vo.ResultInfo;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author ：苹果
 * @Description：考核设计表
 * @Date ：2023/11/16 13:39
 * @Modified By：
 */
@RestController
@RequestMapping("/evalDesign")
public class EvalDesignController {
    @Autowired
    private EvalDesignService designService;

    @Autowired
    private EvalOrgDetailService evalOrgDetailService ;

    @Autowired
    private EvalOrgTaskService evalOrgTaskService ;

    @PostMapping("pageList")
    public ResultInfo pageList(EvalDesignVo vo){
        return  ResultInfo.success(designService.pageList(vo));
    }

    @PostMapping("getEntity")
    public ResultInfo getEntity(String id){
        return  ResultInfo.success(designService.getEntity(id));
    }

    @PostMapping("del")
    public ResultInfo del(String id){
        return  designService.del(id);
    }

    @PostMapping("copyEntity")
    public ResultInfo copyEntity(String id){
        return  designService.copyEntity(id);
    }

    @PostMapping("saveOrUploadEval")
    public ResultInfo saveOrUploadEval(@RequestBody EvalDesignVo vo){
        return  designService.saveOrUploadEval(vo);
    }

    @PostMapping("getTaskEntity")
    public ResultInfo getTaskEntity(String id){
        return  designService.getTaskEntity(id);
    }




    /**
     *考核任务管理列表接口
     * Author wzn
     * Date 2023/11/20 10:45
     */
    @PostMapping("/selectList")
    public ResultInfo selectList(@RequestBody EvalTaskManage evalTaskManage){
        Page<EvalTaskManage> evalTaskManagePage = evalOrgDetailService.selectList(evalTaskManage) ;
        return ResultInfo.success(evalTaskManagePage);
    }



    /**
     *保证金状态修改接口
     * Author wzn
     * Date 2023/11/20 10:31
     */
    @PostMapping("/updateMoneyShow")
    public ResultInfo updateMoneyShow(@RequestBody EvalTaskManage evalTaskManage){
        evalOrgDetailService.updateMoneyShow(evalTaskManage) ;
        return ResultInfo.success();
    }

    /**
     *考核任务发布
     * Author wzn
     * Date 2023/11/20 13:52
     */
    @PostMapping("/publishTaskMange")
    public ResultInfo publishTaskMange(String id){
        evalOrgDetailService.publishTaskMange(id) ;
        return ResultInfo.success();
    }

    /**
     *机构上传接口
     * Author wzn
     * Date 2023/11/20 9:30
     */
    @RequestMapping("/uploadOrgFile")
    public ResultInfo uploadOrgFile(List<MultipartFile> file, String id){
        evalOrgDetailService.uploadOrgFile(file,id) ;
        return ResultInfo.success();
    }


    /**
    *保证金上传接口
    * Author wzn
    * Date 2023/12/12 9:50
    */
    @RequestMapping("/uploadMoneyFile")
    public ResultInfo uploadMoneyFile(List<MultipartFile> file, String id){
        evalOrgDetailService.uploadMoneyFile(file,id) ;
        return ResultInfo.success();
    }


    /**
     *获取保证金文件路径
     * Author wzn
     * Date 2023/11/23 14:28
     */
    @RequestMapping("/getFile")
    public ResultInfo getFile(String id){
        return ResultInfo.success(evalOrgDetailService.getFile(id));
    }


    /**
     *获取机构文件路径
     * Author wzn
     * Date 2023/12/12 11:43
     */
    @RequestMapping("/getFile2")
    public ResultInfo getFile2(String id){
        return ResultInfo.success(evalOrgDetailService.getFile2(id));
    }


    /**
     *考核任务删除
     * Author wzn
     * Date 2023/11/23 15:06
     */
    @RequestMapping("/delEvalTaskManage")
    public ResultInfo delEvalTaskManage(String id){
        evalOrgDetailService.del(id);
        return ResultInfo.success();
    }



    /**
    *复审结束 计算平均分和指标值
    * Author wzn
    * Date 2023/11/23 15:52
    */
    @RequestMapping("/calculateScore")
    public ResultInfo calculateScore(String id){
        evalOrgDetailService.calculateScore(id);
        return ResultInfo.success();
    }


    /**
    *结束初审接口
    * Author wzn
    * Date 2023/11/23 16:14
    */
    @RequestMapping("/updateStatus")
    public ResultInfo updateStatus(String id){
        evalOrgDetailService.updateStatus(id);
        return ResultInfo.success();
    }

    /**
     * 所有没有指标的机构
     * Author wzn
     * Date 2023/12/7 10:20
     */
    @RequestMapping("/zbList")
    public ResultInfo zbList(String id){
        return ResultInfo.success(evalOrgDetailService.zbList(id));
    }

    /**
    *计算完分数后 并且没有空的分数  则更改状态
    * Author wzn
    * Date 2023/11/29 16:49
    */
    @RequestMapping("/updatecsStatus")
    public ResultInfo updatecsStatus(String id){
        evalOrgDetailService.updatecsStatus(id);
        return ResultInfo.success();
    }



    @PostMapping("/uploadDesignFile")
    public ResultInfo uploadDesignFile(List<MultipartFile> file, String id,String year){
        return  ResultInfo.success(designService.uploadDesignFile(file,id,year));
    }

    @PostMapping("/getDesignFile")
    public ResultInfo getDesignFile(String id){
        return  ResultInfo.success(designService.getDesignFile(id));
    }

    /**
     *考核单下拉接口
     * Author wzn
     * Date 2023/11/23 18:44
     */
    @RequestMapping("/designList")
    public ResultInfo designList(@RequestBody  EvalDesign evalDesign){
        return ResultInfo.success(evalOrgDetailService.designList(evalDesign));
    }


    /**
     *机构下拉数据
     * Author wzn
     * Date 2023/11/24 14:06
     */
    @RequestMapping("/getMedinsDeptB")
    public ResultInfo getMedinsDeptB(){
        return ResultInfo.success(evalOrgDetailService.getMedinsDeptB());
    }


    /**
     *根据科室获取所有负责人接口
     * Author wzn
     * Date 2023/11/17 14:20
     */
    @RequestMapping("/getUserList")
    public ResultInfo getUserList(String deptCode){
        return ResultInfo.success(evalOrgDetailService.getUserList(deptCode));
    }


    /**
     *考核任务管理新增接口
     * Author wzn
     * Date 2023/11/17 15:07
     */
    @RequestMapping("/addTaskManage")
    public ResultInfo addTaskManage(@RequestBody EvalTaskManage evalTaskManage){
        evalOrgDetailService.addTaskManage(evalTaskManage) ;
        return ResultInfo.success();
    }


    /**
     *考核任务管理修改接口
     * Author wzn
     * Date 2023/11/17 15:07
     */
    @RequestMapping("/updTaskManage")
    public ResultInfo updTaskManage(@RequestBody EvalTaskManage evalTaskManage){
        evalOrgDetailService.updTaskManage(evalTaskManage) ;
        return ResultInfo.success();
    }


    /**
     *详情接口
     * Author wzn
     * Date 2023/11/25 10:24
     */
    @RequestMapping("/info")
    public ResultInfo info(String id){
        return ResultInfo.success(evalOrgDetailService.info(id));
    }

    /**
     *获取负责人接口
     * Author wzn
     * Date 2023/11/25 11:06
     */
    @RequestMapping("/getUser")
    public ResultInfo getUser(String id){
        return ResultInfo.success(evalOrgDetailService.getUser(id));
    }


    /**
     *计算保证金
     * Author wzn
     * Date 2023/11/25 15:05
     */
    @RequestMapping("/calculate")
    public ResultInfo calculate(String id){
        return ResultInfo.success(evalOrgDetailService.calculate(id));
    }


    /**
     *结束初审时  没有分数的机构列表
     * Author wzn
     * Date 2023/11/27 9:28
     */
    @RequestMapping("/noScore")
    public ResultInfo noScore(@RequestBody  EvalOrgTaskVo evalOrgTaskVo){
        return ResultInfo.success(evalOrgDetailService.noScore(evalOrgTaskVo));
    }


    /**
     *结束初审的时候最后算总分
     * Author wzn
     * Date 2023/12/6 15:54
     */
    @RequestMapping("/startFinish")
    public ResultInfo startFinish(String id){
        evalOrgDetailService.startFinish(id) ;
        return ResultInfo.success();
    }



    /**
     *历史考核列表接口
     * Author wzn
     * Date 2023/11/27 10:46
     */
    @RequestMapping("/getHisPageList")
    public ResultInfo getHisPageList(@RequestBody EvalOrgTask evalOrgTask){
        return ResultInfo.success(evalOrgTaskService.getHisPageList(evalOrgTask));
    }

    @RequestMapping("/updeById")
    public ResultInfo updeById(@RequestBody EvalOrgTask evalOrgTask){
        evalOrgTaskService.updeById(evalOrgTask) ;
        return ResultInfo.success();
    }


    /**
     *历史考核信息导出
     * Author wzn
     * Date 2023/11/28 10:14
     */
    @RequestMapping("/evalOrgTaskExport")
    public void evalOrgTaskExport(HttpServletResponse response,EvalOrgTask evalOrgTask){
        try {
            evalOrgTaskService.evalOrgTaskExport(response,evalOrgTask) ;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     *考核任务下拉数据接口
     * Author wzn
     * Date 2023/11/30 9:41
     */
    @RequestMapping("/taskManageDropDown")
    public ResultInfo taskManageDropDown(){
        return ResultInfo.success( evalOrgDetailService.taskManageDropDown());
    }


    /**
    *险种配置  批量导入
    * Author wzn
    * Date 2023/11/30 10:32
    */
    @RequestMapping("/uploadPzFile")
    public ResultInfo uploadPzFile(List<MultipartFile> file, String id){
        evalOrgDetailService.uploadPzFile(file,id) ;
        return ResultInfo.success();
    }


}
