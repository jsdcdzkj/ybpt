package com.jsdc.ybpt.controller_appropNotice;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.appropNotice.dto.*;
import com.jsdc.ybpt.appropNotice.entity.AppropNoticeDataDetail;
import com.jsdc.ybpt.appropNotice.entity.AppropNoticeDetailAnalyse;
import com.jsdc.ybpt.appropNotice.vo.AppropNoticePreviewVO;
import com.jsdc.ybpt.service.appropNotice.AppropNoticeService;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Description 拨付通知控制层
 * @Author WangXiao
 * @Date 2024/5/21
 */
@RestController
@RequestMapping("/approNotice")
public class ApproNoticeController {

    @Resource
    private AppropNoticeService appropNoticeService;

    @PostMapping("/page")
    public ResultInfo getPage(@RequestBody AppropNoticePageDTO appropNoticePageDTO) {
        return ResultInfo.success(appropNoticeService.getPage(appropNoticePageDTO));
    }

    @PostMapping("/pageByOrgCode")
    public ResultInfo pageByOrgCode(@RequestBody AppropNoticePageDTO appropNoticePageDTO) {
        return ResultInfo.success(appropNoticeService.getPageByOrgCode(appropNoticePageDTO));
    }

    /**
     *
     * @description //TODO 上传校验
     * @author wangxiao
     * @date 2024/5/21
     * @param appropNoticeValidUploadDTO
     * @return com.jsdc.ybpt.vo.ResultInfo
     */
    @PostMapping("/validUpload")
    public ResultInfo validUpload(@RequestBody @Validated AppropNoticeValidUploadDTO appropNoticeValidUploadDTO) {
        return appropNoticeService.validUpload(appropNoticeValidUploadDTO);
    }

    @PostMapping("/uploadTemplate")
    public ResultInfo uploadTemplate(MultipartFile file) {
        return appropNoticeService.uploadTemplate(file);
    }

    @PostMapping("/getTempAddress")
    public ResultInfo getTempAddress() {
        return appropNoticeService.getTempAddress();
    }

    /**
     *
     * @description //TODO  上传数据预览
     * @author wangxiao
     * @date 2024/5/22
     * @param appropNoticePreviewDTO
     * @return com.jsdc.ybpt.vo.ResultInfo
     */
//    @PostMapping("/preview")
//    public ResultInfo preview(@RequestPart(value = "occurFile",required = true) MultipartFile occurFile,
//                              @RequestPart(value = "settleFile",required = true) MultipartFile settleFile,
//                              @RequestPart(value = "jmdbbxsjzfFile",required = true) MultipartFile jmdbbxsjzfFile,
//                              @RequestPart(value = "monthSettleFile",required = true) MultipartFile monthSettleFile,
//                              @RequestPart(value = "drgFile",required = true) MultipartFile drgFile,
//                              @RequestPart(value = "param",required = true) @Validated AppropNoticePreviewDTO appropNoticePreviewDTO) {
//        return ResultInfo.success(appropNoticeService.preview(occurFile,settleFile,jmdbbxsjzfFile,monthSettleFile,drgFile,appropNoticePreviewDTO));
//    }

    @PostMapping("/preview")
    public ResultInfo preview(@RequestBody @Validated AppropNoticePreviewDTO appropNoticePreviewDTO) {
        return ResultInfo.success(appropNoticeService.previewNew(appropNoticePreviewDTO));
    }

    @GetMapping("/testFile")
    public void testFile(String fileId) {
         appropNoticeService.testFile(fileId);
    }

    /**
     *
     * @description //TODO 生成数据
     * @author wangxiao
     * @date 2024/5/27
     * @param appropNoticePreviewVO
     * @return com.jsdc.ybpt.vo.ResultInfo
     */
//    @PostMapping("/generate")
//    public ResultInfo generate(@RequestPart(value = "occurFile",required = true) MultipartFile occurFile,
//                               @RequestPart(value = "settleFile",required = true) MultipartFile settleFile,
//                               @RequestPart(value = "jmdbbxsjzfFile",required = true) MultipartFile jmdbbxsjzfFile,
//                               @RequestPart(value = "monthSettleFile",required = true) MultipartFile monthSettleFile,
//                               @RequestPart(value = "drgFile",required = true) MultipartFile drgFile,
//                               @RequestPart(value = "param",required = true) @Validated AppropNoticePreviewVO appropNoticePreviewVO) {
//        return appropNoticeService.generate(occurFile,settleFile,jmdbbxsjzfFile,monthSettleFile,drgFile,appropNoticePreviewVO);
//    }

    @PostMapping("/generate")
    public ResultInfo generate(@RequestBody @Validated AppropNoticePreviewVO appropNoticePreviewVO) {
        return appropNoticeService.generate(appropNoticePreviewVO);
    }

    /**
     *
     * @description //TODO 发送数据
     * @author wangxiao
     * @date 2024/5/23
     * @param id
     * @return com.jsdc.ybpt.vo.ResultInfo
     */
    @GetMapping("/send")
    public ResultInfo send(@RequestParam String id) {
        return appropNoticeService.send(id);
    }

    /**
     *
     * @description //TODO  删除数据
     * @author wangxiao
     * @date 2024/5/23
     * @param id
     * @return com.jsdc.ybpt.vo.ResultInfo
     */
    @GetMapping("/delete")
    public ResultInfo delete(@RequestParam String id) {
        return appropNoticeService.delete(id);
    }

    /**
     *
     * @description //TODO  查看汇总数据
     * @author wangxiao
     * @date 2024/5/23
     * @param appropNoticeViewSummaryDTO
     * @return com.jsdc.ybpt.vo.ResultInfo
     */
    @PostMapping("/viewSummary")
    public ResultInfo viewSummary(@RequestBody @Validated AppropNoticeViewSummaryDTO appropNoticeViewSummaryDTO) {
        Map<String, Object> stringObjectMap = appropNoticeService.viewSummary(appropNoticeViewSummaryDTO);
        return ResultInfo.success(stringObjectMap);
    }

    /**
     *
     * @description //TODO  查看数据明细
     * @author wangxiao
     * @date 2024/5/23
     * @param appropNoticeViewDataDetailDTO
     * @return com.jsdc.ybpt.vo.ResultInfo
     */
    @PostMapping("/viewDataDetail")
    public ResultInfo viewDataDetail(@RequestBody @Validated AppropNoticeViewDataDetailDTO appropNoticeViewDataDetailDTO) {
        Page<AppropNoticeDataDetail> appropNoticeDataDetailPage = appropNoticeService.viewDataDetail(appropNoticeViewDataDetailDTO);
        return ResultInfo.success(appropNoticeDataDetailPage);
    }

    /**
     *
     * @description //TODO  下载汇总数据
     * @author wangxiao
     * @date 2024/5/23
     * @param appropNoticeViewSummaryDTO
     * @param response
     * @return com.jsdc.ybpt.vo.ResultInfo
     */
    @PostMapping("/downloadSummary")
    public void downloadSummary(@RequestBody @Validated AppropNoticeViewSummaryDTO appropNoticeViewSummaryDTO,HttpServletResponse response) {
        appropNoticeService.downloadSummary(appropNoticeViewSummaryDTO,response);
    }

    /**
     *
     * @description //TODO  下载数据明细
     * @author wangxiao
     * @date 2024/5/24
     * @param appropNoticeViewDataDetailDTO
     * @param response
     * @return com.jsdc.ybpt.vo.ResultInfo
     */
    @PostMapping("/downloadDataDetail")
    public void downloadDataDetail(@RequestBody @Validated AppropNoticeViewDataDetailDTO appropNoticeViewDataDetailDTO,HttpServletResponse response) {
        appropNoticeService.downloadDataDetail(appropNoticeViewDataDetailDTO,response);
    }

    /**
     *
     * @description //TODO  生成拨付分析数据
     * @author wangxiao
     * @date 2024/5/24
     * @param generateApproAnalyseDTO
     * @return com.jsdc.ybpt.vo.ResultInfo
     */
    @PostMapping("/generateApproAnalyse")
    public ResultInfo generateApproAnalyse(@RequestBody @Validated GenerateApproAnalyseDTO generateApproAnalyseDTO) {
        return appropNoticeService.generateApproAnalyse(generateApproAnalyseDTO);
    }

    /**
     *
     * @description //TODO  查看汇总数据分析
     * @author wangxiao
     * @date 2024/5/24
     * @param viewSummaryAnalyseDTO
     * @return com.jsdc.ybpt.vo.ResultInfo
     */
    @PostMapping("/viewSummaryAnalyse")
    public ResultInfo viewSummaryAnalyse(@RequestBody @Validated ViewSummaryAnalyseDTO viewSummaryAnalyseDTO) {
        return ResultInfo.success(appropNoticeService.viewSummaryAnalyse(viewSummaryAnalyseDTO));
    }

    /**
     *
     * @description //TODO  查看数据明细分析
     * @author wangxiao
     * @date 2024/5/27
     * @param viewDetailAnalyseDTO
     * @return com.jsdc.ybpt.vo.ResultInfo
     */
    @PostMapping("/viewDetailAnalyse")
    public ResultInfo viewDetailAnalyse(@RequestBody @Validated ViewDetailAnalyseDTO viewDetailAnalyseDTO) {
        Page<AppropNoticeDetailAnalyse> appropNoticeDetailAnalysePage = appropNoticeService.viewDetailAnalyse(viewDetailAnalyseDTO);
        return ResultInfo.success(appropNoticeDetailAnalysePage);
    }

    /**
     *
     * @description //TODO  下载汇总数据分析
     * @author wangxiao
     * @date 2024/5/27
     * @param viewSummaryAnalyseDTO
     * @param response
     * @return void
     */
    @PostMapping("/downloadSummaryAnalyse")
    public void downloadSummaryAnalyse(@RequestBody @Validated ViewSummaryAnalyseDTO viewSummaryAnalyseDTO,HttpServletResponse response) {
        appropNoticeService.downloadSummaryAnalyse(viewSummaryAnalyseDTO,response);
    }

    /**
     *
     * @description //TODO  下载数据明细分析
     * @author wangxiao
     * @date 2024/5/27
     * @param viewDetailAnalyseDTO
     * @param response
     * @return void
     */
    @PostMapping("/downloadDetailAnalyse")
    public void downloadDetailAnalyse(@RequestBody @Validated ViewDetailAnalyseDTO viewDetailAnalyseDTO,HttpServletResponse response) {
        appropNoticeService.downloadDetailAnalyse(viewDetailAnalyseDTO,response);
    }

    /**
     * @description 处理去年分析数据
     * @author wangxiao
     * @date 2024/5/30
     * @param file
     * @return com.jsdc.ybpt.vo.ResultInfo
     */
    @PostMapping("/dealLastYearAnaselyData")
    public ResultInfo dealLastYearAnaselyData(@RequestPart(value = "file",required = true) MultipartFile file) {
        return appropNoticeService.dealLastYearAnaselyData(file);
    }

    @GetMapping("/authList")
    public ResultInfo authList() {
        return ResultInfo.success(appropNoticeService.authList());
    }

}
