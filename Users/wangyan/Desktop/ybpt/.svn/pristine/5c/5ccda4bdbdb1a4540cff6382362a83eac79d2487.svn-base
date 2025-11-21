package com.jsdc.ybpt.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jsdc.ybpt.abnormal.CheckSuspicions;
import com.jsdc.ybpt.mapper.CheckSuspicionsMapper;
import com.jsdc.ybpt.mapper.CivilworkerInfoMapper;
import com.jsdc.ybpt.mapper.MedicalCareAbnormalMapper;
import com.jsdc.ybpt.mapper.SettleAbnormalMapper;
import com.jsdc.ybpt.mapper.notice.NoticeMapper;
import com.jsdc.ybpt.mapper.notice.NoticeToAccepterMapper;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_check.CivilworkerInfo;
import com.jsdc.ybpt.util.websocket.Websocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ExcuteService {
    @Autowired
    public SettleAbnormalMapper settleAbnormalMapper;


    @Autowired
    public MedicalCareAbnormalMapper medicalCareAbnormalMapper;

    @Autowired
    private CheckSuspicionsMapper checkSuspicionsMapper;

    @Autowired
    private NoticeToAccepterMapper noticeToAccepterMapper;

    @Autowired
    private NoticeMapper noticeMapper;


    @Autowired
    private Executor asyncServiceExecutor;


    @Autowired
    private Websocket websocket;

    @Async("asyncServiceExecutor")
    public void executeAsync(String sql) {
        settleAbnormalMapper.importDataSql(String.valueOf(sql));
    }


    @Async("asyncServiceExecutor")
    public void executeAsync2(String sql) {
        medicalCareAbnormalMapper.importDataSql(String.valueOf(sql));
    }

    @Async("asyncServiceExecutor")
    public void executeAsync3(String sql) {
        checkSuspicionsMapper.importDataSql(String.valueOf(sql));
    }

    public CompletableFuture<Void> executeAsyncCivil(String sql) {
        return CompletableFuture.runAsync(() -> {
                    noticeToAccepterMapper.importDataSql(String.valueOf(sql));
                }, asyncServiceExecutor);
    }

//    /**
//     * @Author: yc
//     * @Params: 用人单位查询下面的人员, 并插入NoticeToAccepter
//     * @Return:
//     * @Description：针对用人
//     * @Date ：2023/5/22 下午 4:08
//     * @Modified By：
//     */
//    public CompletableFuture<Void> insertCivilInfoToNoticeToAccepter(String sql) {
//        return CompletableFuture.runAsync(() -> {
//            noticeToAccepterMapper.importDataSql(sql);
//        }, asyncServiceExecutor);
//
//    }
}

