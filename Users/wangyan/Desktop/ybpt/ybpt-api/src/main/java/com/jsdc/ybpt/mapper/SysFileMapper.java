package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.ybpt.dao.CommonDao;
import com.jsdc.ybpt.dao.SysFileDao;
import com.jsdc.ybpt.model.SysFile;
import com.jsdc.ybpt.vo.NatDataDicAVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @ClassName SysUserMapper
 * @Description TODO
 * @Author xujian
 * @Date 2022/3/28 13:40
 * @Version 1.0
 */
@Mapper
public interface SysFileMapper extends BaseMapper<SysFile> {

    /**
     * 对外接口上传报告，根据ASSOCIATIONID 覆盖 上传 文件
     * */
    @SelectProvider(type= SysFileDao.class,method = "deleteRecords")
    String deleteRecords(String ASSOCIATIONID);

    @SelectProvider(type= SysFileDao.class,method = "selectByAssociationId")
    List<SysFile> selectByAssociationId(String ASSOCIATIONID);
}
