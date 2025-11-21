package com.jsdc.ybpt.service.agreementsignService;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jsdc.ybpt.agreementsignModel.NetTagFile;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.agreementsignMapper.NetTagFileMapper;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class NetTagFileService extends BaseService<NetTagFile> {
    @Autowired
    private NetTagFileMapper netTagFileMapper;

    @Value("${sort}")
    private String sort;
    @Value("${netTagNginxPath}")
    private String nginxPath;
    @Value("${signPdfPath}")
    private String fastDfs_downurl;
    public String getFile(String id) {
        NetTagFile netTagFile = netTagFileMapper.selectById(id);
        String path = netTagFile.getFilePath();
        //String tempPath = path.substring(9);
        //tempPath = nginxPath+tempPath;
        return fastDfs_downurl + path+"?download=0";
    }


    public List<Map<String, String>> picList(String id) {
        QueryWrapper<NetTagFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        queryWrapper.eq("is_del", "0");
        List<NetTagFile> NetTagFileList = netTagFileMapper.selectList(queryWrapper);
        List<Map<String, String>> mapList = new ArrayList<>();
        if (NetTagFileList.size() != 0 && NetTagFileList != null) {
            for (NetTagFile s : NetTagFileList) {
                Map<String, String> map = new HashMap<>();
                map.put("name", s.getOldFileName());
                map.put("url", sort + "/common/readImg?filePath=" + s.getNewFileName());
                mapList.add(map);
            }

        }
        return mapList;
    }

    public boolean save(NetTagFile file) {
        return saveOrUpdate(file);
    }


    public ResultInfo deleteFileSelectAssociationId(String AssociationId) {
        return ResultInfo.success(netTagFileMapper.deleteRecords(AssociationId));
    }
}
