package com.jsdc.ybpt.SchedulingTask;

import com.jsdc.ybpt.mapper.RegistInfoMapper;
import com.jsdc.ybpt.service.OpspDiseListBService;
import com.jsdc.ybpt.service.SbWesternMedicineService;
import com.jsdc.ybpt.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
*门慢特登记定时任务
* Author wzn
* Date 2022/5/9 11:14
*/
@Component
public class OpspDiseTask {

    @Autowired
    private OpspDiseListBService opspDiseListBService;

    @Autowired
    private RegistInfoMapper registInfoMapper ;

    @Autowired
    private SbWesternMedicineService sbWesternMedicineService ;

    @Autowired
    private SysUserService sysUserService ;

    /**
    *门慢门特登记审核  定时任务
    * Author wzn
    * Date 2023/3/1 15:09
    */
    //* *0/5 * * * ?
//    @Scheduled(cron = "0 0/5 * * * ?")
//    public void queryAssociation() {
//        List<OpspDise> opspDiseList = opspDiseListBService.getList() ;
//        if(opspDiseList != null && opspDiseList.size()>0){
//            for(OpspDise o:opspDiseList){
//                QueryWrapper<RegistrationInformation> queryWrapper = new QueryWrapper<>() ;
//                queryWrapper.eq("opsp_id",o.getId()) ;
//                List<RegistrationInformation> registrationInformations = registInfoMapper.selectList(queryWrapper) ;
//                List<OpspDise> opspDises = opspDiseListBService.queryAssociation(o.getCertno(),registrationInformations.get(0).getOpsp_dise_code()) ;
//                if(opspDises != null && opspDises.size()>0){
//                    //更新本地
//                    o.setAssociationId(opspDises.get(0).getServ_matt_inst_id());
//                    o.setOpter_name(opspDises.get(0).getOpter_name());
//                    o.setOpt_time(opspDises.get(0).getOpt_time());
//                    opspDiseListBService.updateOpdise(o) ;
//                }
//            }
//
//        }
//    }



    /**
    *西药定时任务
    * Author wzn
    * Date 2023/3/2 9:12
    */
    @Scheduled(cron = "0 0 1 * * ? ")
    public void westTim() {
        sbWesternMedicineService.timing();
    }

    /**
     *西药定时任务
     * Author wzn
     * Date 2023/3/2 9:12
     */
    @Scheduled(cron = "0 0 1 * * ? ")
    public void chinTim() {
        sbWesternMedicineService.chinTiming();
    }


    /**
    *诊疗项目定时任务
    * Author wzn
    * Date 2023/3/2 14:03
    */
    @Scheduled(cron = "0 0 1 * * ? ")
    public void projectTim() {
        sbWesternMedicineService.projectTiming();
    }



    /**
    *统一门户定时
    * Author wzn
    * Date 2023/3/21 16:16
    */
    @Scheduled(cron = "0 0 2 * * ? ")
    public void synchronization() {
        sysUserService.synchronization();
    }



}
