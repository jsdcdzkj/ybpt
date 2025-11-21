package com.jsdc.ybpt.controller.notice;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_check.Notice;
import com.jsdc.ybpt.service.SysUserService;
import com.jsdc.ybpt.service.notice.NoticeService;
import com.jsdc.ybpt.util.InitNoticeVoRange;
import com.jsdc.ybpt.vo.ResultInfo;
import com.jsdc.ybpt.vo.notice.NoticeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private NoticeService service;

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("getPageList")
    public ResultInfo getPageList(@RequestBody NoticeVo vo) {
        SysUser user = this.sysUserService.getUser();
        vo.setCreateUser(user.getId());
        Page<Notice> noticePage = this.service.getPageList(vo);
        return ResultInfo.success(noticePage);
    }

    /**
    *医药机构通知列表
    * Author wzn
    * Date 2023/6/7 17:01
    */
    @RequestMapping("getPageList2")
    public ResultInfo getPageList2(@RequestBody NoticeVo vo) {
        SysUser user = this.sysUserService.getUser();
        vo.setCreateUser(user.getId());
        Page<Notice> noticePage = this.service.getPageList2(vo);
        return ResultInfo.success(noticePage);
    }


    @RequestMapping ("getPageListForAccepter")
    public ResultInfo getPageListForAccepter(@RequestBody NoticeVo vo) {
        SysUser user = this.sysUserService.getUser();
        vo.setAccepter_code(user.getUsername());
        Page<NoticeVo> noticePage = this.service.getPageListForAccepter(vo);
        return ResultInfo.success(noticePage);
    }


    @RequestMapping("getEntityById/{id}")
    public ResultInfo getEntityById(@PathVariable (name="id") String id) {
        NoticeVo vo = this.service.getEntityById(id);
        return ResultInfo.success(vo);
    }



    @RequestMapping("save")
    public ResultInfo save(@RequestBody NoticeVo vo) {
        if (vo.getRangeList().isEmpty()) {
            return ResultInfo.error("请选择通知范围");
        }
        InitNoticeVoRange.init(vo);
        this.service.save(vo);
        return ResultInfo.success("保存成功");
    }

    @RequestMapping("importData")
    public ResultInfo uploadFile(MultipartFile file) {

        return this.service.uploadFile(file);
    }

    @RequestMapping("delete/{id}")
    public ResultInfo delete(@PathVariable(name = "id") String id) {
        this.service.delete(id);
        return ResultInfo.success("删除成功");
    }

    @RequestMapping("edit")
    public ResultInfo edit(@RequestBody NoticeVo vo) {
        if (vo.getRangeList().isEmpty()) {
            return ResultInfo.error("请选择通知范围");
        }
        InitNoticeVoRange.init(vo);
        this.service.edit(vo);
        return ResultInfo.success("编辑成功");
    }

    @RequestMapping("launch/{id}")
    public ResultInfo launch(@PathVariable(name = "id") String id) {
        SysUser user = this.sysUserService.getUser();
        this.service.launch(id, user);
        return ResultInfo.success();
    }

    @RequestMapping("getTop2NoticeForAccepterAndTotalCount")
    public ResultInfo getTop2NoticeForAccepter() {
        SysUser user = this.sysUserService.getUser();
        List<Notice> top2NoticeList = this.service.getTop2NoticeForAccepter(user.getUsername());
        Integer totalCount = this.service.getTotalCount(user.getUsername());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("totalNoticeCount", totalCount);
        jsonObject.put("top2NoticeList", top2NoticeList);
        return ResultInfo.success(jsonObject);
    }

    @RequestMapping("getTop2NoticeForAccepterAndTotalCountPage")
    public ResultInfo getTop2NoticeForAccepterAndTotalCountPage(@RequestBody Notice notice) {
        SysUser user = this.sysUserService.getUser();
        Page<Notice> top2NoticeList = this.service.getTop2NoticeForAccepterPage(notice,user.getUsername());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("top2NoticeList", top2NoticeList);
        return ResultInfo.success(jsonObject);
    }

    @RequestMapping("testWebSocket")
    public ResultInfo testWebSocket(String username) {
        this.service.testWebSocket(username);
        return ResultInfo.success();
    }

    @RequestMapping("recall/{id}")
    public ResultInfo recall(@PathVariable(name = "id") String id) {
        this.service.recall(id);
        return ResultInfo.success();
    }

    /**
     * @Author: yc
     * @Params:
     * @Return:
     * @Description：点击详情后, 置为已读
     * @Date ：2023/5/23 上午 9:21
     * @Modified By：
     */
    @RequestMapping("setIsRead/{noticeId}")
    public ResultInfo setIsRead(@PathVariable(name = "noticeId") String noticeId) {
        SysUser user = this.sysUserService.getUser();
        this.service.setIsRead(noticeId, user);
        return ResultInfo.success();
    }
}
