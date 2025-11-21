package com.jsdc.ybpt.mapper.appropNotice;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.appropNotice.dto.AppropDocumentPageDTO;
import com.jsdc.ybpt.appropNotice.entity.AppropDocument;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 拨付通知(APPROP_DOCUMENT)数据库访问层
 *
 * @author hanch
 * @date 2024-07-15
 */
@Mapper
public interface AppropDocumentMapper extends BaseMapper<AppropDocument> {

    IPage<AppropDocument> pageQuery(Page<AppropDocument> page,@Param("appropDocumentPageDTO") AppropDocumentPageDTO appropDocumentPageDTO);


}
