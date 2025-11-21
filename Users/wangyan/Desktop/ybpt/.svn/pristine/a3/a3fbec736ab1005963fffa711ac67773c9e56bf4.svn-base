package com.jsdc.ybpt.mapper.agreementsignMapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.ybpt.agreementsignModel.NetTagFile;
import com.jsdc.ybpt.dao.agreementsignDao.NetTagFileDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

/**
 * @ClassName SysUserMapper
 * @Description TODO
 * @Author xujian
 * @Date 2022/3/28 13:40
 * @Version 1.0
 */
@Mapper
public interface NetTagFileMapper extends BaseMapper<NetTagFile> {

    /**
     * 对外接口上传报告，根据ASSOCIATIONID 覆盖 上传 文件
     * */
    @SelectProvider(type= NetTagFileDao.class,method = "deleteRecords")
    String deleteRecords(String ASSOCIATIONID);
}
