package com.jsdc.ybpt.controller;

import com.jsdc.ybpt.model_check.EmpSubscribeRecord;
import com.jsdc.ybpt.service.EmpSubscribeRecordService;
import com.jsdc.ybpt.vo.EmpSubscribeRecordVo;
import com.jsdc.ybpt.vo.ResultInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author ：苹果
 * @Description：单位预约记录表
 * @Date ：2022/5/26 14:26
 * @Modified By：
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/empSubscribeRecord")
public class EmpSubscribeRecordController {

    private final EmpSubscribeRecordService empSubscribeRecordService;

    /**
     * @Description: 唯一查询 预约记录
     * @param: [id]
     * @return: com.jsdc.ybpt.vo.ResultInfo
     * @author: 苹果
     * @date: 2022/5/26
     * @time: 14:40
     */
    @PostMapping("/findEmpSubscribeRecordOne")
    public ResultInfo findEmpSubscribeRecordOne(String id) {
        return this.empSubscribeRecordService.findEmpSubscribeRecordOne(id);
    }

    /**
     * @Description: 条件查询 预约记录
     * @param: [time, empName, packName]
     * @return: com.jsdc.ybpt.vo.ResultInfo
     * @author: 苹果
     * @date: 2022/5/26
     * @time: 14:41
     */
    @PostMapping("/findEmpSubscribeRecord")
    public ResultInfo findEmpSubscribeRecord(@RequestBody EmpSubscribeRecord empSubscribeRecord) {
        return this.empSubscribeRecordService.findEmpSubscribeRecord(empSubscribeRecord.getCreate_time(), empSubscribeRecord.getUo_name(),
                empSubscribeRecord.getPack_name(), empSubscribeRecord.getPageNo(), empSubscribeRecord.getPageSize(), empSubscribeRecord.getUo_id());
    }

    /**
     * @Description: 添加预约记录
     * @param: [uid, pid, time, num]
     * @return: com.jsdc.ybpt.vo.ResultInfo
     * @author: 苹果
     * @date: 2022/5/26
     * @time: 14:41
     */
    @PostMapping("/saveEmpSubscribeRecord")
    public ResultInfo saveEmpSubscribeRecord(@RequestBody EmpSubscribeRecordVo vo) {
        return this.empSubscribeRecordService.saveEmpSubscribeRecord(vo);
    }

    /**
     * @Description: 预约撤销
     * @param: [id, uid, pid, time, num]
     * @return: com.jsdc.ybpt.vo.ResultInfo
     * @author: 苹果
     * @date: 2022/5/26
     * @time: 14:41
     */
    @PostMapping("/backoutSubscribe")
    public ResultInfo backoutSubscribe(String id) {
        if (StringUtils.isEmpty(id)) {
            return ResultInfo.error("参数验证错误");
        }
        return this.empSubscribeRecordService.backoutSubscribe(id);
    }

    /**
     * @Description: 人员列表
     * @param: [id]  ,[type]  0 全部  1  退休   2 在职
     * @return: com.jsdc.ybpt.vo.ResultInfo
     * @author: 苹果
     * @date: 2022/6/6
     * @time: 20:04
     */
    @GetMapping("/findCivilworkerNotIn")
    public ResultInfo findCivilworkerNotIn(String id, String year, String name,String type,Integer index,Integer size) {
        return this.empSubscribeRecordService.findCivilworkerNotIn(id, year, name,type,index,size);
    }

    /**
     * @Description: 已经添加过的人员
     * @param: [id]
     * @return: com.jsdc.ybpt.vo.ResultInfo
     * @author: 苹果
     * @date: 2022/6/8
     * @time: 19:57
     */
    @GetMapping("/findCivilworkerIn")
    public ResultInfo findCivilworkerIn(String id, String year, String name,Integer index,Integer size) {
        return this.empSubscribeRecordService.findCivilworkerIn(id, year, name,index,size);
    }

    /**
     * @Description: 批量预约导出
     * @param: [time, empName, packName, pageNo, pageSize, uoid, response]
     * @return: void
     * @author: 苹果
     * @date: 2022/6/15
     * @time: 16:35
     */
    @GetMapping("/excel")
    public void yyExport(String time, String empName, String packName, Integer pageNo, Integer pageSize, String uoid, HttpServletResponse response) throws IOException {
        this.empSubscribeRecordService.export(time, empName, packName, pageNo, pageSize, uoid, response);
    }

}
