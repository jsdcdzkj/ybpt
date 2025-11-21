package com.jsdc.ybpt.SchedulingTask;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.model.ReflAppyEvtC;
import com.jsdc.ybpt.service.ReflAppyEvtCService;
import com.jsdc.ybpt.vo.ReflAppyEvtCVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

//@Component
public class ReflAppyEvtCTask {
  @Autowired
  private ReflAppyEvtCService reflAppyEvtCService;


  /**
   * 根据未审核状态 查询回流库数据
   */
  @Scheduled(cron = "0 */5  * * * ?") //每五分钟执行一次
  public void queryAssociation() {
    ReflAppyEvtCVo vo = new ReflAppyEvtCVo();
    vo.setRCHK_FLAG("0");//未审核
    Page<ReflAppyEvtC> page = reflAppyEvtCService.getReflAppyEvtC(vo);
    if (page.getRecords() != null && page.getRecords().size() > 0) {
      for (ReflAppyEvtC t : page.getRecords()) {
        vo.setCERTNO(t.getCertno());
        ReflAppyEvtC s = reflAppyEvtCService.reflAppyEvtC(vo);
        if (s != null) {
          //更新本地
          BeanUtils.copyProperties(s, t);
        }
      }

    }
  }
}
