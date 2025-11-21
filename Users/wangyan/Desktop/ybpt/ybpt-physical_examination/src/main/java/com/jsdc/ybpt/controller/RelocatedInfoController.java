package com.jsdc.ybpt.controller;

import com.jsdc.ybpt.model_check.RelocatedInfo;
import com.jsdc.ybpt.model_query.PersonInfo;
import com.jsdc.ybpt.service.RelocatedInfoService;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.vo.ResultInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author ：苹果
 * @Description：异地就医
 * @Date ：2022/5/26 10:36
 * @Modified By：
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/relocatedInfo")
public class RelocatedInfoController {

    private final RelocatedInfoService relocatedInfoService;


    /**
     * @Description: 唯一查询    异地就医
     * @param: [id]
     * @return: com.jsdc.ybpt.vo.ResultInfo
     * @author: 苹果
     * @date: 2022/5/26
     * @time: 14:38
     */
    @GetMapping("findRelocatedInfoOne")
    public ResultInfo findRelocatedInfoOne(@RequestParam("id") String id) {
        return this.relocatedInfoService.findRelocatedInfoOne(id);
    }

    /**
     * @Description: 条件查询异地就医
     * @param: [id, cid, medplace, aBank, aNo]
     * @return: com.jsdc.ybpt.vo.ResultInfo
     * @author: 苹果
     * @date: 2022/5/26
     * @time: 14:38
     */
    @PostMapping("findRelocatedInfo")
    public ResultInfo findRelocatedInfo(@RequestBody RelocatedInfo relocatedInfo) {
        return this.relocatedInfoService.findRelocatedInfo(relocatedInfo);
    }

    /**
     * @Description: 添加  异地就医
     * @param: [relocatedInfo]
     * @return: com.jsdc.ybpt.vo.ResultInfo
     * @author: 苹果
     * @date: 2022/5/26
     * @time: 14:39
     */
    @PostMapping("saveRelocatedInfo")
    public ResultInfo saveRelocatedInfo(@RequestBody RelocatedInfo relocatedInfo) {
        if (StringUtils.isEmpty(relocatedInfo.getNum()) || StringUtils.isEmpty(relocatedInfo.getAccount_bank()) || StringUtils.isEmpty(relocatedInfo.getAccount_no()) || StringUtils.isEmpty(relocatedInfo.getMedplace())) {
            return ResultInfo.error("信息录入不完整");
        }
        if ("1".equals(relocatedInfo.getChecked())) {
            return this.relocatedInfoService.saveRelocatedInfoFlag(relocatedInfo);
        } else {
            PersonInfo personInfo = this.relocatedInfoService.findPersonOne(relocatedInfo.getNum());
            if (org.springframework.util.StringUtils.isEmpty(personInfo)) {
                return ResultInfo.error("查无此人");
            }
            return this.relocatedInfoService.saveRelocatedInfo(relocatedInfo, personInfo);
        }

    }

    /**
     * @Description: 修改  异地就医
     * @param: [relocatedInfo]
     * @return: com.jsdc.ybpt.vo.ResultInfo
     * @author: 苹果
     * @date: 2022/5/26
     * @time: 14:39
     */
    @PostMapping("updateRelocatedInfo")
    public ResultInfo updateRelocatedInfo(@RequestBody RelocatedInfo relocatedInfo) {
        return this.relocatedInfoService.updateRelocatedInfo(relocatedInfo);
    }

    /**
     * @Description: 删除
     * @param: [id]
     * @return: com.jsdc.ybpt.vo.ResultInfo
     * @author: 苹果
     * @date: 2022/5/27
     * @time: 15:57
     */
    @PostMapping("/delRelocatedInfo")
    public ResultInfo delRelocatedInfo(String ids) {
        System.out.println(ids);
        return this.relocatedInfoService.delRelocatedInfo(ids);
    }


    /**
     * @Description: 异地安置导出
     * @param: [relocatedInfo]
     * @return: com.jsdc.ybpt.vo.ResultInfo
     * @author: 苹果
     * @date: 2022/7/4
     * @time: 17:49
     */
    @RequestMapping("/export")
    public void export(RelocatedInfo relocatedInfo, HttpServletResponse response) throws IOException {
        this.relocatedInfoService.export(relocatedInfo, response);
    }
}
