package com.jsdc.ybpt.mapper.notice;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.dao.notice.NoticeDao;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_check.CivilworkerInfo;
import com.jsdc.ybpt.model_check.Notice;
import com.jsdc.ybpt.vo.notice.NoticeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {
    @SelectProvider(type= NoticeDao.class,method = "getPageList")
    Page<Notice> getPageList(Page<Notice> page, NoticeVo vo);

    @SelectProvider(type= NoticeDao.class,method = "getPageList2")
    Page<Notice> getPageList2(Page<Notice> page, NoticeVo vo);


    @SelectProvider(type= NoticeDao.class,method = "getPageListForAccepter")
    Page<NoticeVo> getPageListForAccepter(Page<NoticeVo> page, NoticeVo vo);

    @SelectProvider(type= NoticeDao.class,method = "getNoticeForAccepter")
    List<Notice> getNoticeForAccepter(String accepterCode);

    @SelectProvider(type= NoticeDao.class,method = "getNoticeForAccepterPage")
    Page<Notice> getNoticeForAccepterPage(Page<NoticeVo> page,Notice notice,String accepterCode);



    @SelectProvider(type= NoticeDao.class,method = "getYRDWSysuserByParentOrgCode")
    List<SysUser> getYRDWSysuserByParentOrgCode(String accepterCode);

    @SelectProvider(type= NoticeDao.class,method = "getTJJGSysuserByMedicalInsuranceNum")
    List<SysUser> getTJJGSysuserByMedicalInsuranceNum(String accepterCode);

    @SelectProvider(type = NoticeDao.class, method = "getCivilInfoListByEmpParentOrgCode")
    List<CivilworkerInfo> getCivilInfoListByEmpParentOrgCode(String parentOrgCode);

    @SelectProvider(type = NoticeDao.class, method = "getListByCivilCertNo")
    List<NoticeVo> getListByCivilCertNo(String cardId);
}
