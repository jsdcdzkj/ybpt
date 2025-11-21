package com.jsdc.ybpt.controller;

import cn.dev33.satoken.secure.SaSecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jsdc.ybpt.mapper.CivilworkerRecordMapper;
import com.jsdc.ybpt.model.FileInfo;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_check.Agreement;
import com.jsdc.ybpt.model_check.CivilworkerInfo;
import com.jsdc.ybpt.model_check.Notice;
import com.jsdc.ybpt.service.AgreementService;
import com.jsdc.ybpt.service.CivilworkerInfoService;
import com.jsdc.ybpt.service.DeptService;
import com.jsdc.ybpt.service.notice.NoticeService;
import com.jsdc.ybpt.util.DESUtil;
import com.jsdc.ybpt.util.IdCardNumberMethod;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.vo.ResultInfo;
import com.jsdc.ybpt.vo.notice.NoticeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sun.swing.SwingUtilities2;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private CivilworkerInfoService civilworkerInfoService;

    @Autowired
    private AgreementService agreementService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private CivilworkerRecordMapper civilworkerRecordMapper;

    @Autowired
    private NoticeService noticeService;

    @Value("${DESKEY}")
    private String desKey;

    /**
     * 登陆界面
     */
    @RequestMapping(value = "toLogin")
    public String index(Model model,
                        @RequestParam(required = true, name = "openid") String openId,
                        @RequestParam(required = true, name = "idcard") String cardId,
                        @RequestParam(required = false, name = "name") String name) throws Exception {
        String decryptedCardId = "";
        decryptedCardId = DESUtil.decryptDES(cardId, desKey);
        model.addAttribute("decryptedCardId", decryptedCardId);
        CivilworkerInfo autoLoginCi = this.civilworkerInfoService.checkAutoLogin(decryptedCardId);
        // ci不为空，则说明有资格，
        if (autoLoginCi != null) {
            if (StringUtils.isEmpty(autoLoginCi.getOpen_id())) {
                autoLoginCi.setOpen_id(openId);
                this.civilworkerInfoService.updateById(autoLoginCi);
            }
            if (StringUtils.isEmpty(autoLoginCi.getLogin_Name())) {
                return "toLogin";
            } else {
//                List<Notice> top2NoticeList = this.noticeService.getTop2NoticeForAccepter(decryptedCardId);
                Integer totalCount = this.noticeService.getTotalCount(decryptedCardId);
                model.addAttribute("totalCount", totalCount);
                return "applyPersonSubscribeRecord";
            }
        } else {
            return "toLogin";
        }
    }

    /**
     * @return author: yc
     * @param: [login_name, pwd]
     */
    @ResponseBody
    @PostMapping("login")
    public ResultInfo login(HttpSession session, @RequestBody CivilworkerInfo vo) {
        String certNo = (String) session.getAttribute("certNo");
        String openId = (String) session.getAttribute("openId");

        if (certNo == null) {
            return ResultInfo.error("缺少certNo");
        }
        if (openId == null) {
            return ResultInfo.error("缺少openId");
        }

        CivilworkerInfo civilworkerInfo = civilworkerInfoService.civilLoginH5(vo);
        if (civilworkerInfo != null) {
            if (civilworkerInfo.getCertno().equals(certNo)) {
                if (null == civilworkerInfo.getOpen_id()) {
                    civilworkerInfo.setOpen_id(openId);
                    civilworkerInfoService.updateById(civilworkerInfo);
                }
                session.setAttribute("civil", civilworkerInfo);
                return ResultInfo.success("登录成功");
            }
        }
        return ResultInfo.error("登录失败");
    }

    @RequestMapping("toRegister")
    public String toRegister(Model model, @RequestParam(name = "cardId") String cardId) {
        model.addAttribute("decryptedCardId", cardId);
        Agreement agreement = this.agreementService.getAgreement();
        if (null != agreement) {
            model.addAttribute("agreement", agreement.getContent());
        }
        return "register";
    }

    @ResponseBody
    @PostMapping("register")
    public ResultInfo register(@RequestBody CivilworkerInfo vo) {
        // 2.姓名不能为空
        // 4.根据身份证号和姓名查看该用户是否有体检资格
        // 5.如果用户名已存在则返回已经注册信息
        // 7.尝试更新openId
        // 8.尝试注册


        if (!IdCardNumberMethod.checkCardId(vo.getCertno())) {
            return ResultInfo.error("身份证号格式不正确");
        }

        if (StringUtils.isEmpty(vo.getCertno())) {
            return ResultInfo.error("身份证号不能为空");
        }

        CivilworkerInfo dbCivil = civilworkerInfoService.getQualifiedCivilWorkerInfo(vo);

        // 2.根据身份证号查看该用户是否有体检资格，
        if (dbCivil == null) {
            return ResultInfo.error("没有该用户，或者该用户没有体检资格");
        }

        // 3.如果用户名已存在则返回已经注册
        if ("registered".equals(dbCivil.getLogin_Name())) {
            return ResultInfo.error("该用户已注册,请勿重复注册");
        }

        if (!dbCivil.getName().equals(vo.getName())) {
            return ResultInfo.error("真实姓名与身份证不符");
        }

        // md5 加密密码
        dbCivil.setLogin_Name("registered");
        this.civilworkerInfoService.updateById(dbCivil);
        return ResultInfo.success("注册成功");
    }

    @PostMapping("logout")
    @ResponseBody
    public ResultInfo logout(HttpSession session) {
        session.invalidate();
        return ResultInfo.success("退出成功");
    }

    @GetMapping("userCenter")
    public String userCenter(Model model, @RequestParam(name = "cardId") String cardId) {
        model.addAttribute("decryptedCardId", cardId);
        return "userCenter";
    }


    @GetMapping("toUserModifyPwd")
    public String toUserModifyPwd() {
        return "userModifyPwd";
    }


    @PostMapping("modifyPassword")
    @ResponseBody
    public ResultInfo modifyPassword(HttpSession session, @RequestBody CivilworkerInfo vo) {
        CivilworkerInfo civil = (CivilworkerInfo) session.getAttribute("civil");
        if (civil == null) {
            return ResultInfo.error("请先登录");
        }

        if (StringUtils.isEmpty(vo.getPwd())) {
            return ResultInfo.error("原始密码不能为空");
        }

        if (StringUtils.isEmpty(vo.getNewPassword())) {
            return ResultInfo.error("新密码不能为空");
        }

        if (StringUtils.isEmpty(vo.getPasswordConfirm())) {
            return ResultInfo.error("确认密码不能为空");
        }

        if (!vo.getNewPassword().equals(vo.getPasswordConfirm())) {
            return ResultInfo.error("两次密码输入不一致");
        }

        if (!civil.getPwd().equals(SaSecureUtil.md5(vo.getPwd()))) {
            return ResultInfo.error("原始密码错误");
        }

        civil.setPwd(SaSecureUtil.md5(vo.getNewPassword()));
        if (this.civilworkerInfoService.updateById(civil)) {
            return ResultInfo.success("修改密码成功");
        }
        return ResultInfo.success("修改密码失败");
    }


    @GetMapping("notice.page")
    public String noticePage(@RequestParam(name = "cardId") String cardId, Model model) {
        List<NoticeVo> noticeVoList = this.noticeService.getListByCivilCertNo(cardId);
        model.addAttribute("noticeVoList", noticeVoList);
        model.addAttribute("decryptedCardId", cardId);
        return "message";
    }

    @GetMapping("getNoticeEntity")
    public String noticeEntity(@RequestParam String noticeId, @RequestParam String cardId, Model model) {
        NoticeVo entityById = this.noticeService.getEntityById(noticeId);
        SysUser sysUser = new SysUser();
        sysUser.setUsername(cardId);
        this.noticeService.setIsRead(noticeId, sysUser);

        model.addAttribute("notice", entityById);
        ArrayList<FileInfo> fileInfoList = entityById.getFileInfoList();
        model.addAttribute("fileInfoList", fileInfoList);
        model.addAttribute("decryptedCardId", cardId);
        return "messageView";
    }
}
