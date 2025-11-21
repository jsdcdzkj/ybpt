package com.jsdc.ybpt.service.notice;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.mapper.FileInfoMapper;
import com.jsdc.ybpt.mapper.SysUserMapper;
import com.jsdc.ybpt.mapper.notice.NoticeMapper;
import com.jsdc.ybpt.mapper.notice.NoticeToAccepterMapper;
import com.jsdc.ybpt.model.FileInfo;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_check.CivilworkerInfo;
import com.jsdc.ybpt.model_check.Notice;
import com.jsdc.ybpt.model_check.NoticeToAccepter;
import com.jsdc.ybpt.service.ExcuteService;
import com.jsdc.ybpt.service.OrganizationInfoService;
import com.jsdc.ybpt.service.SysUserService;
import com.jsdc.ybpt.util.FastDfs.FastDfsParams;
import com.jsdc.ybpt.util.FastDfs.FastDfsUtil;
import com.jsdc.ybpt.util.ListGroupUtil;
import com.jsdc.ybpt.util.exception.CustomException;
import com.jsdc.ybpt.util.websocket.Websocket;
import com.jsdc.ybpt.vo.ResultInfo;
import com.jsdc.ybpt.vo.notice.NoticeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service

@Slf4j
public class NoticeService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private NoticeMapper mapper;

    @Autowired
    private NoticeToAccepterMapper noticeToAccepterMapper;

    @Autowired
    private FileInfoMapper fileInfoMapper;
    @Autowired
    private FastDfsUtil fastDfsUtil;

    @Autowired
    private OrganizationInfoService organizationInfoService;

    @Autowired
    private Websocket websocket;

    @Autowired
    private ExcuteService excuteService;

    public Page<Notice> getPageList(NoticeVo vo) {
        Page<Notice> page = new Page<>(vo.getPageNo(), vo.getPageSize());
        Page<Notice> noticePage = this.mapper.getPageList(page, vo);
        List<Notice> records = noticePage.getRecords();
        for (Notice record : records) {
            String range = record.getRange();
            String replace = "";
            replace = range.replace("t", "体检机构").replace("y", "用人单位").replace("j", "医疗机构").replace("d", "零售药店");
            record.setRange(replace);
            String content = record.getContent();
            if (record.getContent() != null && record.getContent().length() > 100) {
                record.setContent(content.substring(0, 99));
            }
        }
        return noticePage;
    }

    public Page<Notice> getPageList2(NoticeVo vo) {
        Page<Notice> page = new Page<>(vo.getPageNo(), vo.getPageSize());
        Page<Notice> noticePage = this.mapper.getPageList2(page, vo);
        List<Notice> records = noticePage.getRecords();
        for (Notice record : records) {
            String range = record.getRange();
            String replace = "";
            replace = range.replace("t", "体检机构").replace("y", "用人单位").replace("j", "医疗机构").replace("d", "零售药店");
            record.setRange(replace);
            String content = record.getContent();
            if (record.getContent() != null && record.getContent().length() > 100) {
                record.setContent(content.substring(0, 99));
            }
        }
        return noticePage;
    }


    public Page<NoticeVo> getPageListForAccepter(NoticeVo vo) {
        Page<NoticeVo> page = new Page<>(vo.getPageNo(), vo.getPageSize());
        Page<NoticeVo> noticePage = this.mapper.getPageListForAccepter(page, vo);
        return noticePage;
    }

    @Transactional
    public void edit(NoticeVo vo) {

        Notice notice = new Notice();
        notice.setId(vo.getId());
        notice.setTitle(vo.getTitle());
        notice.setContent(vo.getContent());
        notice.setRange(vo.getRange());
        this.mapper.updateById(notice);

        // 重新插入
        ArrayList<String> fileIdList = vo.getFileIdList();
        for (String id : fileIdList) {
            FileInfo fileInfo = new FileInfo();
            fileInfo.setBizId(vo.getId());
            fileInfo.setId(id);
            this.fileInfoMapper.updateById(fileInfo);
        }

        // 删除不再使用的附件
        ArrayList<String> delFileIdList = vo.getDelFileIdList();
        for (String id : delFileIdList) {
            FileInfo fileInfo = new FileInfo();
            fileInfo.setBizId(vo.getId());
            fileInfo.setId(id);
            this.fileInfoMapper.deleteById(fileInfo);
        }
    }

    public ResultInfo uploadFile(MultipartFile file) {
        FastDfsParams params = new FastDfsParams("notice/attachment", file, "10", null);
        return fastDfsUtil.uploadFile(params);

    }

    @Transactional
    public void save(NoticeVo vo) {
        SysUser user = this.sysUserService.getUser();
        Notice notice = new Notice();
        notice.setCreateUser(user.getId());
        notice.setRange(vo.getRange());
        notice.setTitle(vo.getTitle());
        notice.setContent(vo.getContent());
        notice.setCreateTime(new Date());
        notice.setIs_launch("0");
        notice.setIs_del("0");
        this.mapper.insert(notice);

        ArrayList<String> fileIdList = vo.getFileIdList();
        for (String id : fileIdList) {
            FileInfo fileInfo = new FileInfo();
            fileInfo.setBizId(notice.getId());
            fileInfo.setId(id);
            this.fileInfoMapper.updateById(fileInfo);
        }

        ArrayList<String> delFileIdList = vo.getDelFileIdList();
        for (String delFileId : delFileIdList) {
            FileInfo fileInfo = new FileInfo();
            fileInfo.setId(delFileId);
            this.fileInfoMapper.deleteById(fileInfo);
        }
    }

    public void delete(String id) {
        Notice notice = new Notice();
        notice.setId(id);
        notice.setIs_del("-1");
        this.mapper.updateById(notice);
    }

    public NoticeVo getEntityById(String id) {
        Notice notice = this.mapper.selectById(id);
        LambdaQueryWrapper<FileInfo> fileInfoWrapper = new LambdaQueryWrapper<>();
        fileInfoWrapper.eq(FileInfo::getBizId, notice.getId());
        List<FileInfo> fileInfoDBList = this.fileInfoMapper.selectList(fileInfoWrapper);
        NoticeVo noticeVo = new NoticeVo();
        noticeVo.setTitle(notice.getTitle());
        noticeVo.setContent(notice.getContent());
        noticeVo.setRange(notice.getRange());
        noticeVo.setLaunchTime(notice.getLaunchTime());

        if (StringUtils.hasText(notice.getRange())) {
            String range = notice.getRange();
            String[] split = range.split(",");
            for (String s : split) {
                noticeVo.getRangeList().add(s);
            }
        }


        ArrayList<FileInfo> fileInfoList = noticeVo.getFileInfoList();
        fileInfoList.addAll(fileInfoDBList);
        return noticeVo;
    }


    @Transactional
    public void launch(String id, SysUser sysUser) {
        String org_code = sysUser.getOrg_code();

        Notice notice = this.mapper.selectById(id);
        if (notice.getIs_launch().equals("1")) {
            throw new CustomException("该通知已发布");
        }

        // 保险起见再删除一次旧的NoticeToAccepter
        LambdaQueryWrapper<NoticeToAccepter> noticeToAccepterLambdaQueryWrapper = new LambdaQueryWrapper<>();
        noticeToAccepterLambdaQueryWrapper.eq(NoticeToAccepter::getNotice_id, id);
        this.noticeToAccepterMapper.delete(noticeToAccepterLambdaQueryWrapper);

        String range = notice.getRange();
        String[] split = range.split(",");
        ArrayList<SysUser> sysUserList = new ArrayList<>();
        for (String s : split) {
            switch (s) {
                // 体检机构
                case "t": {
                    List<SysUser> tjjgSysuserByMedicalInsuranceNumUserList = this.mapper.getTJJGSysuserByMedicalInsuranceNum(org_code);
                    sysUserList.addAll(tjjgSysuserByMedicalInsuranceNumUserList);
                    break;
                }
                // 用人单位
                case "y": {
                    List<SysUser> yrdwSysuserByParentOrgCodeUserList = this.mapper.getYRDWSysuserByParentOrgCode(org_code);
                    sysUserList.addAll(yrdwSysuserByParentOrgCodeUserList);
                    break;
                }
                case "j": {
                    QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>() ;
                    queryWrapper.eq("user_type","2") ;
                    queryWrapper.eq("is_del","0") ;
                    List<SysUser> sysUsers = sysUserMapper.selectList(queryWrapper) ;
                    sysUserList.addAll(sysUsers);
                    break;
                }
                case "d": {
                    QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>() ;
                    queryWrapper.eq("user_type","3") ;
                    queryWrapper.eq("is_del","0") ;
                    List<SysUser> sysUsers = sysUserMapper.selectList(queryWrapper) ;
                    sysUserList.addAll(sysUsers);
                    break;
                }
            }
        }

        notice.setIs_launch("1");
        notice.setLaunchTime(new Date());
        this.mapper.updateById(notice);

        ArrayList<NoticeToAccepter> ytNoticeToAccepterTempList = new ArrayList<>();
        int count = 0;
        for (SysUser sysUserAccepter : sysUserList) {
            NoticeToAccepter noticeToAccepter = new NoticeToAccepter();
            noticeToAccepter.setNotice_id(id);
            noticeToAccepter.setAccepter_code(sysUserAccepter.getUsername());
            noticeToAccepter.setIs_read("0");
            noticeToAccepter.setId(IdUtil.simpleUUID());
            ytNoticeToAccepterTempList.add(noticeToAccepter);
        }
        this.insertYTNoticeToAccepter(ytNoticeToAccepterTempList);


        if (range.contains("y")) {
            // 直接根据org_code 获取用人单位下面的公务员
            List<CivilworkerInfo> civilworkerInfoList = this.mapper.getCivilInfoListByEmpParentOrgCode(org_code);
            ArrayList<NoticeToAccepter> civilNoticeToAccepters = new ArrayList<>();
            for (CivilworkerInfo civilworkerInfo : civilworkerInfoList) {
                NoticeToAccepter noticeToAccepter = new NoticeToAccepter();
                noticeToAccepter.setNotice_id(notice.getId());
                noticeToAccepter.setAccepter_code(civilworkerInfo.getCertno());
                noticeToAccepter.setIs_read("0");
                noticeToAccepter.setId(IdUtil.simpleUUID());
                civilNoticeToAccepters.add(noticeToAccepter);
            }
            this.insertCivilNoticeToAccepter(civilNoticeToAccepters);
        }
    }

    public void insertYTNoticeToAccepter(List<NoticeToAccepter> noticeToAccepterTempList) {
        StringBuilder sb = null;
        List<List<NoticeToAccepter>> lists = ListGroupUtil.groupListByQuantity(noticeToAccepterTempList, 100);
        for (int i = 0; i < lists.size(); i++) {
            sb = new StringBuilder();
            sb.append("INSERT ALL ");
            for (int k = 0; k < lists.get(i).size(); k++) {
//                if (k > 0) {
//
//                }
                sb.append(" INTO NOTICE_TO_ACCEPTER (id,ACCEPTER_CODE,NOTICE_ID,IS_READ) VALUES  (");
                sb.append("'" + lists.get(i).get(k).getId() + "', ");
                sb.append("'" + lists.get(i).get(k).getAccepter_code() + "', ");
                sb.append("'" + lists.get(i).get(k).getNotice_id() + "', ");
                sb.append("'" + lists.get(i).get(k).getIs_read() + "') ");
            }
            sb.append("select 1 from dual");

            // 使用线程池异步执行, 异步任务执行后发送websocket通知
            log.debug("第" + i + "次,开始-插入时间:" + +System.currentTimeMillis());

            int finalI = i;
            excuteService.executeAsyncCivil(sb.toString()).thenRun(() -> {
                log.info("第" + finalI + "次,结束-插入时间:" + +System.currentTimeMillis());
                // 执行后续方法
                log.info("第" + finalI + "次,开始-发送websocket时间:" + +System.currentTimeMillis());
                this.websocket.sentBroadCast("我把你来梦");
                log.info("第" + finalI + "次,结束-发送websocket时间:" + +System.currentTimeMillis());
                System.out.println("ExecuteService: Completed");
            });
//            System.out.println(sb);
        }
    }

    public void insertCivilNoticeToAccepter(List<NoticeToAccepter> noticeToAccepterTempList) {
        StringBuilder sb = null;
        List<List<NoticeToAccepter>> lists = ListGroupUtil.groupListByQuantity(noticeToAccepterTempList, 100);
        for (int i = 0; i < lists.size(); i++) {
            sb = new StringBuilder();
            sb.append("INSERT ALL ");
            for (int k = 0; k < lists.get(i).size(); k++) {
//                if (k > 0) {
//
//                }
                sb.append(" INTO NOTICE_TO_ACCEPTER (id,ACCEPTER_CODE,NOTICE_ID,IS_READ) VALUES  (");
                sb.append("'" + lists.get(i).get(k).getId() + "', ");
                sb.append("'" + lists.get(i).get(k).getAccepter_code() + "', ");
                sb.append("'" + lists.get(i).get(k).getNotice_id() + "', ");
                sb.append("'" + lists.get(i).get(k).getIs_read() + "') ");
            }
            sb.append("select 1 from dual");

            // 使用线程池异步执行, 异步任务执行后发送websocket通知
            log.debug("civil第第" + i + "次,开始-插入时间:" + +System.currentTimeMillis());

            int finalI = i;
            excuteService.executeAsyncCivil(sb.toString()).thenRun(() -> {
                log.debug("civil第" + finalI + "次,结束-插入时间:" + +System.currentTimeMillis());
                // 执行后续方法
//                log.debug("civil第第" + finalI + "次,开始-发送websocket时间:" + +System.currentTimeMillis());
//                this.websocket.sentBroadCast("我把你来梦");
//                log.debug("civil第第" + finalI + "次,结束-发送websocket时间:" + +System.currentTimeMillis());
//                System.out.println("ExecuteService: Completed");
            });
        }
    }

    public void testWebSocket(String userName) {
        // 发送广播, 获取该客户的username, 如果username在noticeToAccepter中, 则把该notice发送给该客户端
//        Websocket.sentBroadCast("我把你来蒙");

    }


    public List<Notice> getTop2NoticeForAccepter(String accepterCode) {
        List<Notice> noticeListDB = this.mapper.getNoticeForAccepter(accepterCode);
        ArrayList<Notice> notices = new ArrayList<>();
        for (int i = 0; i < noticeListDB.size(); i++) {
            if (i > 1) {
                break;
            }
            notices.add(noticeListDB.get(i));
        }
        return notices;
    }

    public Page<Notice> getTop2NoticeForAccepterPage(Notice notice,String accepterCode) {
        Page<NoticeVo> page = new Page<>(notice.getPageNo(), notice.getPageSize());
        Page<Notice> noticeListDB = this.mapper.getNoticeForAccepterPage(page,notice,accepterCode);
        return noticeListDB;
    }

    public Integer getTotalCount(String accepterCode) {
        List<Notice> noticeListDB = this.mapper.getNoticeForAccepter(accepterCode);
        return noticeListDB.size();
    }

    public void recall(String id) {
        LambdaUpdateWrapper<Notice> wrapperUpdate = new LambdaUpdateWrapper<>();
        wrapperUpdate.eq(Notice::getId, id);
        wrapperUpdate.set(Notice::getIs_launch, "0");
//        wrapperUpdate.set(Notice::getLaunchTime, null);
        this.mapper.update(null, wrapperUpdate);


        LambdaQueryWrapper<NoticeToAccepter> noticeToAccepterLambdaQueryWrapper = new LambdaQueryWrapper<>();
        noticeToAccepterLambdaQueryWrapper.eq(NoticeToAccepter::getNotice_id, id);
        this.noticeToAccepterMapper.delete(noticeToAccepterLambdaQueryWrapper);
        //撤销通知
        this.websocket.sentBroadCast("撤销通知");
    }

    public void setIsRead(String noticeId, SysUser sysUser) {
        String username = sysUser.getUsername();

        LambdaQueryWrapper<NoticeToAccepter> noticeToAccepterLambdaQueryWrapper = new LambdaQueryWrapper<>();
        noticeToAccepterLambdaQueryWrapper.eq(NoticeToAccepter::getAccepter_code, username);
        noticeToAccepterLambdaQueryWrapper.eq(NoticeToAccepter::getNotice_id, noticeId);
        noticeToAccepterLambdaQueryWrapper.eq(NoticeToAccepter::getIs_read, "0");
        NoticeToAccepter noticeToAccepter = this.noticeToAccepterMapper.selectOne(noticeToAccepterLambdaQueryWrapper);
        if (noticeToAccepter != null) {
            noticeToAccepter.setIs_read("1");
            this.noticeToAccepterMapper.updateById(noticeToAccepter);
        }
    }

    public List<NoticeVo> getListByCivilCertNo(String cardId) {
        List<NoticeVo> noticeList = this.mapper.getListByCivilCertNo(cardId);
        return noticeList;
    }
}
