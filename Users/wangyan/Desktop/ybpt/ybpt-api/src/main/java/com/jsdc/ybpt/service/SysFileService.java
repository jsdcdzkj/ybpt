package com.jsdc.ybpt.service;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.FileInfoMapper;
import com.jsdc.ybpt.mapper.SysFileMapper;
import com.jsdc.ybpt.model.FileInfo;
import com.jsdc.ybpt.model.SysFile;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class SysFileService extends BaseService<SysFile> {
    @Autowired
    private SysFileMapper sysFileMapper ;

    @Autowired
    private FileInfoMapper fileInfoMapper;

//    @Value("${img_url}")
//    private String img_url;






    public List<Map<String,String>> picList(String id){
        List<FileInfo> fileInfoList = fileInfoMapper.selectList(Wrappers.<FileInfo>lambdaQuery()
                .eq(FileInfo::getBizId, id)
                .eq(FileInfo::getBizType, "7")
        );

        List<Map<String,String>> mapList = new ArrayList<>() ;
        if(CollectionUtils.isNotEmpty(fileInfoList)){
            for(FileInfo s: fileInfoList){
                Map<String,String> map = new HashMap<>() ;
                map.put("name",s.getFileName()) ;
//                map.put("url",img_url+s.getFileUrl()) ;
                mapList.add(map) ;
            }

        }
        return mapList ;
    }

    public boolean save(SysFile file){
        return saveOrUpdate(file);
    }





    public  ResultInfo  deleteFileSelectAssociationId(String AssociationId){
        return ResultInfo.success(sysFileMapper.deleteRecords(AssociationId));
    }

    public List<SysFile> selectByAssociationId(String AssociationId){
        List<SysFile> sysFile = sysFileMapper.selectByAssociationId(AssociationId);
        return sysFile;
    }
}
