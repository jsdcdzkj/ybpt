package com.jsdc.ybpt.controller;


import cn.dev33.satoken.temp.SaTempUtil;
import com.jsdc.ybpt.common.utils.ApiUtils;
import com.jsdc.ybpt.model.FixmedinsB;
import com.jsdc.ybpt.model_check.OrganizationInfo;
import com.jsdc.ybpt.service.*;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.util.exception.CustomException;
import com.jsdc.ybpt.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class ExternalApi {

    @Autowired
    private OrgReconciliationService orgReconciliationService;
    @Autowired
    private OrgReconciliationMonthService orgReconciliationMonthService;
    @Autowired
    private PersonSubscribeRecordService personSubscribeRecordService;
    @Autowired
    AutonomousMedicalService autonomousMedicalService;
    @Autowired
    OrganizationInfoService organizationInfoService;
    @Autowired
    private FixmedinsBService fixmedinsBService;



    /**
     * 日对账接口
     *
     * @param data
     * @return
     */
    @PostMapping("/day_reconciliation")
    public ResultInfo day_reconciliation(@RequestBody ReconciliationApiVo data) {
        if (StringUtils.isEmpty(data.getFixmedins_code())) {
            return ResultInfo.error("机构编码不得为空！");
        }
        if (StringUtils.isEmpty(data.getSettle_date())) {
            return ResultInfo.error("结算日期不得为空！");
        }
        if (data.getDay_details() != null && data.getDay_details().size() > 0) {
            return ResultInfo.error("对账明细不得为空！");
        }
        return orgReconciliationService.day_reconciliation(data);
    }

    /**
     * 月对账接口
     *
     * @param data
     * @return
     */
    @PostMapping("/month_reconciliation")
    public ResultInfo month_reconciliation(@RequestBody ReconciliationApiVo data) {
        if (StringUtils.isEmpty(data.getFixmedins_code())) {
            return ResultInfo.error("机构编码不得为空！");
        }
        if (StringUtils.isEmpty(data.getSettle_date())) {
            return ResultInfo.error("结算日期不得为空！");
        }
        if (data.getMonth_details() != null && data.getMonth_details().size() > 0) {
            return ResultInfo.error("对账明细不得为空！");
        }
        return orgReconciliationMonthService.month_reconciliation(data);
    }

//
//  /**
//   * 预约人数对外接口
//   * Author wzn
//   * Date 2022/6/9 16:36
//   */
//  @PostMapping("/reservationCount")
//  public ResultInfo reservationCount() {
//    ApiLoginInfo apiLoginInfo = ApiUtils.getApiLoginInfo();
//    String type = "'0','1','3'";
//    PeopleCount peopleCount = personSubscribeRecordService.reservationCount(apiLoginInfo.getOrgCode(), type);
//    return ResultInfo.success(peopleCount.getNum());
//  }
//
//  /**
//   * 体检人数对外接口
//   * Author wzn
//   * Date 2022/6/9 16:49
//   */
//  @PostMapping("/physicalExaminationCount")
//  public ResultInfo physicalExaminationCount() {
//    ApiLoginInfo apiLoginInfo = ApiUtils.getApiLoginInfo();
//    String type = "'1'";
//    PeopleCount peopleCount = personSubscribeRecordService.reservationCount(apiLoginInfo.getOrgCode(), type);
//    return ResultInfo.success(peopleCount.getNum());
//  }
//
//  /**
//   * 报告上传人数
//   * Author wzn
//   * Date 2022/6/9 16:50
//   */
//  @PostMapping("/uploadCount")
//  public ResultInfo uploadCount() {
//    ApiLoginInfo apiLoginInfo = ApiUtils.getApiLoginInfo();
//    String type = "'3'";
//    PeopleCount peopleCount = personSubscribeRecordService.reservationCount(apiLoginInfo.getOrgCode(), type);
//    return ResultInfo.success(peopleCount.getNum());
//  }

    /**
     * 获取接口调用token
     *
     * @param orgCode  机构编码
     * @param orgType  机构类型
     * @param authCode 授权code
     * @return
     */
    @PostMapping("/getTokenByLogin")
    public ResultInfo createToken(String orgCode, String orgType, String authCode) {
        if(null == orgCode || "".equals(orgCode) || null ==orgType||"".equals(orgType) || null == authCode || "".equals(authCode)){
            return ResultInfo.error("入参为空！");
        }
        //验证机构信息
        //验证成功之后生成token返回
        ApiLoginInfo apiLoginInfo = new ApiLoginInfo(orgCode, orgType, authCode);
        //通过orgType 和orgCode 获取机构信息，对比authCode是否一致，一致则返回token，否则返回错误信息
        //账号类型 1:行政管理单位 2:医疗机构 3:零售药店 4：用人单位 5：体检机构'
        FixmedinsB fixmedinsB = null;
        OrganizationInfo organizationInfo = null;
        if ("2".equals(orgType)) {
            fixmedinsB = new FixmedinsB();
            fixmedinsB.setFixmedins_code(orgCode);
            fixmedinsB.setAuthorizationCode(authCode);
            FixmedinsB fixmedinsB1 = fixmedinsBService.selectByFixmedinsCode(fixmedinsB);
            if (null == fixmedinsB1) {
                return ResultInfo.error("未授权,请联系鼎驰管理员！");
            }
        } else if ("5".equals(orgType)) {
            organizationInfo = new OrganizationInfo();
            organizationInfo.setMedical_insurance_num(orgCode);
            organizationInfo.setAuthorizationCode(authCode);
            OrganizationInfo organizationInfo1 = organizationInfoService.selectByOrgCode(organizationInfo);
            if (null == organizationInfo1) {
                return ResultInfo.error("未授权,请联系鼎驰管理员！");
            }
        } else {
            return ResultInfo.error("未授权,请联系鼎驰管理员！");
        }
        String token = SaTempUtil.createToken(apiLoginInfo, 60 * 60 * 24);
        return ResultInfo.success(token);
    }

    @PostMapping("/getToken")
    public ApiLoginInfo getToken() {
        return ApiUtils.getApiLoginInfo();
    }


}
