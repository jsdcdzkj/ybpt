package com.jsdc.ybpt.service;

import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.util.IdUtil;
import cn.hutool.poi.excel.sax.Excel07SaxReader;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import com.jsdc.ybpt.abnormal.SettleErrorData;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.util.DateUtil;
import com.jsdc.ybpt.util.ListGroupUtil;
import com.jsdc.ybpt.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * (SettleErrorData)表服务接口
 *
 * @author wangYan
 * @since 2023-08-09
 */
@Service
public class SettleErrorDataService extends BaseService<SettleErrorData> {

    @Autowired
    private ExcuteService excuteService ;

    @Autowired
    private SysUserService sysUserService ;

    public List<SettleErrorData> importData(MultipartFile file) {
        SysUser sysUser = sysUserService.getUser();
        List<SettleErrorData> settleErrorDataList = new ArrayList<>() ;
        try {
            InputStream in = file.getInputStream();
            StringBuilder sb = null ;
            TimeInterval timer = cn.hutool.core.date.DateUtil.timer();
            //构建对象读取
            Excel07SaxReader reader = new Excel07SaxReader(createRowHandler(settleErrorDataList,sysUser.getUsername()));
            reader.read(in, 0);

            System.out.println("解析EXCEL用时："+timer.intervalRestart());
            List<List<SettleErrorData>> lists = ListGroupUtil.groupListByQuantity(settleErrorDataList,100) ;
            System.out.println("List分组用时："+timer.intervalRestart());
            for(int i = 0;i<lists.size();i++){
                sb = new StringBuilder() ;
                sb.append("INSERT  all into SETTLE_ERROR_DATA (ID, SERIAL_NUMBER, CREATEUSER, DRUG_NAME, FIXMEDINS_CODE, IF_UPLOAD, ORG_CODE, ORG_NAME, PRICE, QUANTITY, UPLOAD_NO, UPLOAD_TIME) VALUES  (") ;
                for(int k = 0;k<lists.get(i).size();k++){
                    if(k>0){
                        sb.append(" INTO SETTLE_ERROR_DATA (ID, SERIAL_NUMBER, CREATEUSER, DRUG_NAME, FIXMEDINS_CODE, IF_UPLOAD, ORG_CODE, ORG_NAME, PRICE, QUANTITY, UPLOAD_NO, UPLOAD_TIME) VALUES  (") ;
                    }
                    sb.append("'"+lists.get(i).get(k).getId()+"', ") ;
                    sb.append("'"+lists.get(i).get(k).getSerial_number()+"', ") ;
                    sb.append("'"+lists.get(i).get(k).getCreateUser()+"', ") ;
                    sb.append("'"+lists.get(i).get(k).getDrug_name()+"', ") ;
                    sb.append("'"+lists.get(i).get(k).getFixmedins_code()+"', ") ;
                    sb.append("'"+lists.get(i).get(k).getIf_upload()+"', ") ;
                    sb.append("'"+lists.get(i).get(k).getOrg_code()+"', ") ;
                    sb.append("'"+lists.get(i).get(k).getOrg_name()+"', ") ;
                    sb.append("'"+lists.get(i).get(k).getPrice()+"', ") ;
                    sb.append("'"+lists.get(i).get(k).getQuantity()+"', ") ;
                    sb.append("'"+lists.get(i).get(k).getUpload_no()+"', ") ;
                    sb.append("'"+lists.get(i).get(k).getUpload_time()+"') ") ;
                }
                sb.append("select 1 from dual") ;
                excuteService.executeAsync(sb.toString());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return settleErrorDataList ;
    }


    private RowHandler createRowHandler(List<SettleErrorData> list, String sysUserName) {
        return new RowHandler() {
            @Override
            public void handle(int sheetIndex, long rowIndex, List<Object> rowlist) {
                if(rowIndex == 0 ){
                    return;
                }
                SettleErrorData settleErrorData = null ;
                int s = rowlist.size();
                for(int i = 0;i<rowlist.size();i++){
                    settleErrorData = new SettleErrorData() ;
                    if(null != rowlist.get(0)){
                        settleErrorData.setSerial_number(rowlist.get(0).toString());
                    }else {
                        settleErrorData.setSerial_number("");
                    }
                    if(null != rowlist.get(1)){
                        settleErrorData.setOrg_code(rowlist.get(1).toString());
                    }else {
                        settleErrorData.setOrg_code("");
                    }
                    if(null != rowlist.get(2)){
                        settleErrorData.setOrg_name(rowlist.get(2).toString());
                    }else {
                        settleErrorData.setOrg_name("");
                    }
                    if(null != rowlist.get(3)){
                        settleErrorData.setQuantity(rowlist.get(3).toString());
                    }else {
                        settleErrorData.setQuantity("");
                    }
                    if(null != rowlist.get(4)){
                        settleErrorData.setPrice(rowlist.get(4).toString());
                    }else {
                        settleErrorData.setPrice("");
                    }
                    if(null != rowlist.get(5)){
                        settleErrorData.setFixmedins_code(rowlist.get(5).toString());
                    }else {
                        settleErrorData.setFixmedins_code("");
                    }
                    if(null != rowlist.get(6)){
                        settleErrorData.setDrug_name(rowlist.get(6).toString());
                    }else {
                        settleErrorData.setDrug_name("");
                    }

                    settleErrorData.setId(IdUtil.simpleUUID());
                    settleErrorData.setIf_upload("0");
                    settleErrorData.setUpload_no(DateUtil.getDateFormat(new Date(),"yyyyMMdd"));
                    settleErrorData.setUpload_time(DateUtil.getDateFormat(new Date(),"yyyy-MM-dd HH:mm:ss"));

                    settleErrorData.setCreateUser(sysUserName);
                }

                if(StringUtils.isNotEmpty(settleErrorData.getOrg_code())){
                    list.add(settleErrorData) ;
                }

            }
        };
    }


}

