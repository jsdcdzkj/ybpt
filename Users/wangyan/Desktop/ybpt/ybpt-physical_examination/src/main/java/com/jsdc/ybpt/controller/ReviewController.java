package com.jsdc.ybpt.controller;

import com.jsdc.ybpt.model_check.CivilworkerInfo;
import com.jsdc.ybpt.model_check.PersonSubscribeRecord;
import com.jsdc.ybpt.service.EmpSubscribeRecordService;
import com.jsdc.ybpt.service.PersonSubscribeRecordService;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/review")
public class ReviewController {
    @Autowired
    private PersonSubscribeRecordService service;

    /**
     * @Description: 跳转到评价页面
     * @param: []
     * @return: java.lang.String
     * @date: 2022/6/2
     * @time: 14:49
     */
    @GetMapping(value = "/reviewPage")
    public String review(Model model,
                         @RequestParam(name = "psrId") String psrId,
                         @RequestParam(name="cardId") String cardId) {
        model.addAttribute("psrId", psrId);
        model.addAttribute("decryptedCardId", cardId);
        return "review";
    }

    /**
     * @Description: 获取评价
     * @param: [id]
     * @return: com.jsdc.ybpt.vo.ResultInfo
     * @author: yc
     */
    @RequestMapping("/getReviewByPsrId")
    @ResponseBody
    public ResultInfo getReviewById(@RequestParam(name = "cardId") String cardId, @RequestParam String id, Model model) {
            PersonSubscribeRecord personSubscribeRecord = this.service.getReviewByPersonSubscribeRecordId(id, cardId);
            if (personSubscribeRecord != null) {
                model.addAttribute("decryptedCardId", cardId);
                return ResultInfo.success(personSubscribeRecord);
            }
        return ResultInfo.error("没有此评论记录");
    }

    /**
     * @Description 保存评论
     * @param: [service_star, professional_star, service_status_star, react_star, service_label, service_review]
     * @return: com.jsdc.ybpt.vo.ResultInfo
     * @author: yc
     */
    @PostMapping("/save")
    @ResponseBody
    public ResultInfo save(@RequestBody PersonSubscribeRecord record) {
        if (this.service.updatePersonSubscribeRecordReview(record) == 1) {
            return ResultInfo.success("评论成功!");
        }
        return ResultInfo.error("评论失败!");
    }
}
