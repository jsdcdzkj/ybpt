package com.jsdc.ybpt.service;

import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.util.IdUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.sax.Excel07SaxReader;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import com.jsdc.ybpt.abnormal.SettleAbnormal;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.SettleAbnormalMapper;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.util.DateUtil;
import com.jsdc.ybpt.util.ListGroupUtil;
import com.jsdc.ybpt.util.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SettleAbnormalService extends BaseService<SettleAbnormal> {
    @Autowired
    private SettleAbnormalMapper settleAbnormalMapper ;
    @Autowired
    private ExcuteService excuteService ;

    @Autowired
    private SysUserService sysUserService ;

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(SettleAbnormalService.class);

//
//    @Async("asyncServiceExecutor")
//    public void executeAsync(String sql){
//        settleAbnormalMapper.importDataSql(String.valueOf(sql)) ;
//    }


    /**
     * excel大文件导入  -医保
     * Author wzn
     * Date 2022/9/29 10:04
     */
    public List<SettleAbnormal> importData(MultipartFile file) {
        SysUser sysUser = sysUserService.getUser();
        List<SettleAbnormal> settleAbnormalList = new ArrayList<>() ;
        try {
            InputStream in = file.getInputStream();
            StringBuilder sb = null ;
            TimeInterval timer = cn.hutool.core.date.DateUtil.timer();
            //ExcelUtil快速读取
//            ExcelUtil.readBySax(in, 0, createRowHandler(settleAbnormalList));
            //构建对象读取
            Excel07SaxReader reader = new Excel07SaxReader(createRowHandler(settleAbnormalList,sysUser.getUsername()));
            reader.read(in, 0);

            System.out.println("解析EXCEL用时："+timer.intervalRestart());
            List<List<SettleAbnormal>> lists = ListGroupUtil.groupListByQuantity(settleAbnormalList,100) ;
            System.out.println("List分组用时："+timer.intervalRestart());
            for(int i = 0;i<lists.size();i++){
                 sb = new StringBuilder() ;
                sb.append("INSERT  all into SETTLE_ABNORMAL (id,RID,AREA,CARNO,CATALOGUE_CODE,CATALOGUE_TYPE,NAME,ORG_CODE,ORG_NAME,REASON_FOUR,REASON_ONE,REASON_THREE,REASON_TWO,SETTLE_TIME,SETTLE_TYPE,UPLOAD_NO,UPLOAD_TIME,IF_UPLOAD,MED_TYPE,INSURED_PERSONS_NO,MDTRT_ID,SETL_ID,PROJECT_NAME) VALUES  (") ;
                for(int k = 0;k<lists.get(i).size();k++){
                    if(k>0){
                       sb.append(" INTO SETTLE_ABNORMAL (id,RID,AREA,CARNO,CATALOGUE_CODE,CATALOGUE_TYPE,NAME,ORG_CODE,ORG_NAME,REASON_FOUR,REASON_ONE,REASON_THREE,REASON_TWO,SETTLE_TIME,SETTLE_TYPE,UPLOAD_NO,UPLOAD_TIME,IF_UPLOAD,MED_TYPE,INSURED_PERSONS_NO,MDTRT_ID,SETL_ID,PROJECT_NAME) VALUES  (") ;
                    }
                sb.append("'"+lists.get(i).get(k).getId()+"', ") ;
                sb.append("'"+lists.get(i).get(k).getRid()+"', ") ;
                sb.append("'"+lists.get(i).get(k).getArea()+"', ") ;
                sb.append("'"+lists.get(i).get(k).getCarno()+"', ") ;
                sb.append("'"+lists.get(i).get(k).getCatalogue_code()+"', ") ;
                sb.append("'"+lists.get(i).get(k).getCatalogue_type()+"', ") ;
                sb.append("'"+lists.get(i).get(k).getName()+"', ") ;
                sb.append("'"+lists.get(i).get(k).getOrg_code()+"', ") ;
                sb.append("'"+lists.get(i).get(k).getOrg_name()+"', ") ;
                sb.append("'"+lists.get(i).get(k).getReason_four()+"', ") ;
                sb.append("'"+lists.get(i).get(k).getReason_one()+"', ") ;
                sb.append("'"+lists.get(i).get(k).getReason_three()+"', ") ;
                sb.append("'"+lists.get(i).get(k).getReason_two()+"', ") ;
                sb.append("'"+lists.get(i).get(k).getSettle_time()+"', ") ;
                sb.append("'"+lists.get(i).get(k).getSettle_type()+"', ") ;
                sb.append("'"+lists.get(i).get(k).getUpload_no()+"', ") ;
                sb.append("'"+lists.get(i).get(k).getUpload_time()+"', ") ;
                sb.append("'"+lists.get(i).get(k).getIf_upload()+"', ") ;
                sb.append("'"+lists.get(i).get(k).getMed_type()+"', ") ;
                sb.append("'"+lists.get(i).get(k).getInsured_persons_no()+"', ") ;
                sb.append("'"+lists.get(i).get(k).getMdtrt_id()+"', ") ;
                sb.append("'"+lists.get(i).get(k).getSetl_id()+"', ") ;
                sb.append("'"+lists.get(i).get(k).getProject_name()+"') ") ;
                }
                sb.append("select 1 from dual") ;
                excuteService.executeAsync(sb.toString());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return settleAbnormalList ;
    }


    /**
    *医药机构大文件导入
    * Author wzn
    * Date 2022/9/29 15:06
    */
    public List<SettleAbnormal> importData2(MultipartFile file) {
        List<SettleAbnormal> settleAbnormalList = new ArrayList<>() ;
        try {
            InputStream in = file.getInputStream();
            ExcelUtil.readBySax(in, 0, createRowHandler2(settleAbnormalList));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return settleAbnormalList ;
    }

    private RowHandler createRowHandler(List<SettleAbnormal> list,String sysUserName) {
        return new RowHandler() {
            @Override
            public void handle(int sheetIndex, long rowIndex, List<Object> rowlist) {
                if(rowIndex == 0 ){
                    return;
                }
//                if(rowIndex > 1 ){
//                    sb.append("INTO SETTLE_ABNORMAL (RID,AREA,CARNO,CATALOGUE_CODE,CATALOGUE_TYPE,NAME,ORG_CODE,ORG_NAME,REASON_FOUR,REASON_ONE,REASON_THREE,REASON_TWO,SETTLE_TIME,SETTLE_TYPE,UPLOAD_NO,UPLOAD_TIME,IF_UPLOAD,MED_TYPE) VALUES  (") ;
//                }
                SettleAbnormal settleAbnormal = null ;
                int s = rowlist.size();
                for(int i = 0;i<rowlist.size();i++){
                   settleAbnormal = new SettleAbnormal() ;
                    if(null != rowlist.get(0)){
                        settleAbnormal.setRid(rowlist.get(0).toString());
                    }else {
                        settleAbnormal.setRid("");
                    }

                    if(null != rowlist.get(1)){
                        settleAbnormal.setOrg_code(rowlist.get(1).toString());

                    }else {
                        settleAbnormal.setOrg_code("");
                    }

                    if(null != rowlist.get(2)){
                        settleAbnormal.setOrg_name(rowlist.get(2).toString());

                    }else {
                        settleAbnormal.setOrg_name("");
                    }

                    if(null != rowlist.get(3)){
                        settleAbnormal.setArea(rowlist.get(3).toString());

                    }else {
                        settleAbnormal.setArea("");
                    }

                    if(null != rowlist.get(4)){
                        settleAbnormal.setCarno(rowlist.get(4).toString());
                    }else {
                        settleAbnormal.setCarno("");
                    }

                    if(null != rowlist.get(5)){
                        settleAbnormal.setName(rowlist.get(5).toString());
                    }else {
                        settleAbnormal.setName("");
                    }

                    if(null != rowlist.get(6)){
                        settleAbnormal.setSettle_time(rowlist.get(6).toString());
                    }else {
                        settleAbnormal.setSettle_time("");
                    }

                    if(null != rowlist.get(7)){
                        settleAbnormal.setSettle_type(rowlist.get(7).toString());
                    }else {
                        settleAbnormal.setSettle_type("");
                    }

                    if(null != rowlist.get(8)){
                        settleAbnormal.setMed_type(rowlist.get(8).toString());
                    }else {
                        settleAbnormal.setMed_type("");
                    }

                    if(null != rowlist.get(9)){
                        settleAbnormal.setCatalogue_type(rowlist.get(9).toString());
                    }else {
                        settleAbnormal.setCatalogue_type("");
                    }

                    if(null != rowlist.get(10)){
                        settleAbnormal.setCatalogue_code(rowlist.get(10).toString());
                    }else {
                        settleAbnormal.setCatalogue_code("");
                    }


                        if(s >12 &&null != rowlist.get(12)){
                        settleAbnormal.setReason_one((rowlist.get(12).toString()));
                    }else {
                            settleAbnormal.setReason_one("");
                        }

                    if(s >13 && null != rowlist.get(13)){
                        settleAbnormal.setReason_two((rowlist.get(13).toString()));
                    }else {
                        settleAbnormal.setReason_two("");
                    }

                        if(s >14 && null != rowlist.get(14)){
                            settleAbnormal.setReason_three((rowlist.get(14).toString()));
                        }else {
                            settleAbnormal.setReason_three("");
                        }

                    if(s >15 && null != rowlist.get(15)){
                        settleAbnormal.setReason_four((rowlist.get(15).toString()));
                    }else {
                        settleAbnormal.setReason_four("");
                    }

                    if(s >16 && null != rowlist.get(16)){
                        settleAbnormal.setReason_five((rowlist.get(16).toString()));
                    }else {
                        settleAbnormal.setReason_five("");
                    }

                    if(s >17 && null != rowlist.get(17)){
                        settleAbnormal.setInsured_persons_no((rowlist.get(17).toString()));
                    }else {
                        settleAbnormal.setInsured_persons_no("");
                    }

                    if(s >18 && null != rowlist.get(18)){
                        settleAbnormal.setMdtrt_id((rowlist.get(18).toString()));
                    }else {
                        settleAbnormal.setMdtrt_id("");
                    }

                    if(s >19 && null != rowlist.get(19)){
                        settleAbnormal.setSetl_id((rowlist.get(19).toString()));
                    }else {
                        settleAbnormal.setSetl_id("");
                    }


                    if(s >20 && null != rowlist.get(20)){
                        settleAbnormal.setProject_name((rowlist.get(20).toString()));
                    }else {
                        settleAbnormal.setProject_name("");
                    }

                    settleAbnormal.setId(IdUtil.simpleUUID());
                    settleAbnormal.setIf_upload("0");
                    settleAbnormal.setUpload_no(DateUtil.getDateFormat(new Date(),"yyyyMMdd"));
                    settleAbnormal.setUpload_time(DateUtil.getDateFormat(new Date(),"yyyy-MM-dd HH:mm:ss"));

                    settleAbnormal.setCreateUser(sysUserName);
                    settleAbnormal.setCreateTime(new Date());
                }

                if(StringUtils.isNotEmpty(settleAbnormal.getOrg_code())){
                    list.add(settleAbnormal) ;
                }

            }
        };
    }



    /**
    *机构修改后上传
    * Author wzn
    * Date 2022/9/29 12:22
    */
    private RowHandler createRowHandler2(List<SettleAbnormal> list) {
        return new RowHandler() {
            @Override
            public void handle(int sheetIndex, long rowIndex, List<Object> rowlist) {
                SettleAbnormal settleAbnormal = null ;
                if(rowIndex == 0 ){
                    return;
                }
                for(int i = 0;i<rowlist.size();i++){
                     settleAbnormal = new SettleAbnormal() ;
                    settleAbnormal.setId(rowlist.get(0).toString());

                    if(rowlist.size() >12 &&null != rowlist.get(12)){
                        settleAbnormal.setCatalogue_code_new(rowlist.get(12).toString());
                    }

                    if(rowlist.size() >17 &&null != rowlist.get(17)){
                        settleAbnormal.setReason_five((rowlist.get(17).toString()));
                    }

                }
                SettleAbnormal settleAbnormal1 = null ;
                if(!"".equals(settleAbnormal.getId())){
                     settleAbnormal1 =  settleAbnormal.selectById() ;
                    if(null != settleAbnormal1){
                        SettleAbnormal settleAbnormal2 = new SettleAbnormal() ;
                        settleAbnormal2.setId(settleAbnormal1.getId());
                        if(null != settleAbnormal.getCatalogue_code_new() && !"".equals(settleAbnormal1.getCatalogue_code_new())){
                            settleAbnormal2.setCatalogue_code_new(settleAbnormal.getCatalogue_code_new());
                        }
                        if(null != settleAbnormal.getReason_five() && !"".equals(settleAbnormal1.getReason_five())){
                            settleAbnormal2.setReason_five(settleAbnormal.getReason_five());
                        }
                        if(null != settleAbnormal2.getCatalogue_code_new() || null != settleAbnormal2.getReason_five()){
                            settleAbnormal2.setIf_upload("1");
                            settleAbnormal2.setUpdate_time(DateUtil.getDateFormat(new Date(),"yyyy-MM-dd HH:mm:ss"));
                            settleAbnormalMapper.updateById(settleAbnormal2) ;
                        }
                    }
                }


            }
        };
    }
}
