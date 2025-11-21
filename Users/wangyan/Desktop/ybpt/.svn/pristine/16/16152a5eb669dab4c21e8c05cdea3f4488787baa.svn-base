package com.jsdc.ybpt.service;


import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.FileGeneralDeliveryMapper;
import com.jsdc.ybpt.model.FileGeneralDelivery;
import com.jsdc.ybpt.model.FixmedinsB;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.util.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class FileGeneralDeliveryService extends BaseService<FileGeneralDelivery> {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private FileGeneralDeliveryMapper fileGeneralDeliveryMapper;

    @Value("${fastDfs_url}")
    private String fastDfs_url;

    @Autowired
    private FixmedinsBService fixmedinsBService ;

    /**
     * 手动匹配下发文件
     * Author wzn
     * Date 2023/6/3 11:36
     */
    public void updateFileGeneralDelivery(FileGeneralDelivery fileGeneralDelivery) {

        SysUser sysUser = sysUserService.getUser();
        //查是否已存在改机构
//        QueryWrapper<FileGeneralDelivery> queryWrapper = new QueryWrapper<>() ;
//        queryWrapper.eq("fixmedins_code",fileGeneralDelivery.getFixmedins_code()) ;
//        Long count = fileGeneralDeliveryMapper.selectCount(queryWrapper) ;
//        if(count != 0 ){
//            throw new CustomException("您选择的机构已关联文件,请重新选择！") ;
//        }
        fileGeneralDelivery.setUpdateUser(sysUser.getUsername());
        fileGeneralDelivery.setUpdateTime(new Date());
        fileGeneralDelivery.setStatus("0");
        fileGeneralDelivery.updateById();
    }


    /**
     * 文件下发列表
     * Author wzn
     * Date 2023/6/3 11:38
     */
    public Page<FileGeneralDelivery> fileGeneralDeliveryList(FileGeneralDelivery fileGeneralDelivery) {
        SysUser sysUser = sysUserService.getUser();
        Page<FileGeneralDelivery> page = new Page<>(fileGeneralDelivery.getPageNo(), fileGeneralDelivery.getPageSize());
        QueryWrapper<FileGeneralDelivery> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        if (!"".equals(fileGeneralDelivery.getFixmedins_code()) && null != fileGeneralDelivery.getFixmedins_code()) {
            queryWrapper.eq("fixmedins_code", fileGeneralDelivery.getFixmedins_code());
        }
        if (!"".equals(fileGeneralDelivery.getStatus()) && null != fileGeneralDelivery.getStatus()) {
            queryWrapper.eq("status", fileGeneralDelivery.getStatus());
        }
        if (!"".equals(fileGeneralDelivery.getFixmedins_name()) && null != fileGeneralDelivery.getFixmedins_name()) {
            queryWrapper.like("fixmedins_name", fileGeneralDelivery.getFixmedins_name());
        }
        if(!"1".equals(sysUser.getUser_type())){
            queryWrapper.eq("fixmedins_code",sysUser.getOrg_code()) ;
        }
        queryWrapper.orderByDesc("createTime") ;
        Page<FileGeneralDelivery> fileGeneralDeliveryPage = fileGeneralDeliveryMapper.selectPage(page, queryWrapper);
        return fileGeneralDeliveryPage;
    }


    /**
     * 新增
     * Author wzn
     * Date 2023/6/3 13:57
     */
    public void addFileGeneralDelivery(String filePath, String fileName) {
        String url = fastDfs_url + "/list_dir?dir=file/fileGeneralDelivery/" + filePath;
        SysUser sysUser = sysUserService.getUser();
        Map<String, String> params = new HashMap<>();
        String res = HttpRequest.post(url).formStr(params).timeout(10000).execute().body();
        JSONObject jsonObject = JSON.parseObject(res);
        if ("ok".equals(jsonObject.getString("status"))) {

            JSONArray jsonArray = new JSONArray();
            jsonArray = (JSONArray)jsonObject.get("data") ;
            FileGeneralDelivery fileGeneralDelivery = null ;
            if(null != jsonArray){
                for(int y = 0; y < jsonArray.size();y++){
                    fileGeneralDelivery = new FileGeneralDelivery() ;

                    //再将你取出来的list值重新赋值给JSONObject
                    JSONObject jsonObj = jsonArray.getJSONObject(y);

                    if("false".equals(jsonObj.getString("is_dir"))){
                        //然后直接用jsonObj.getString("属性名")就可以调取到你要的属性值
                        String org_code  = FileNameUtil.mainName(jsonObj.getString("name")) ;
//                        int e = org_code.indexOf("密钥信息");
                        FixmedinsB fixmedinsB = null ;
                            QueryWrapper<FixmedinsB> queryWrapper = new QueryWrapper<>() ;
//                            System.out.println(org_code.substring(0, org_code.indexOf("密钥信息")));
                            queryWrapper.eq("fixmedins_code",org_code) ;
                            queryWrapper.eq("is_del","0") ;
                             fixmedinsB =  fixmedinsBService.getOne(queryWrapper) ;
                            fileGeneralDelivery.setPath(jsonObj.getString("path")+"/"+jsonObj.getString("name"));
                            //匹配成功
                            if(null != fixmedinsB){
                                fileGeneralDelivery.setFixmedins_code(fixmedinsB.getFixmedins_code());
                                fileGeneralDelivery.setFixmedins_name(fixmedinsB.getFixmedins_name());
                                fileGeneralDelivery.setStatus("0");
//                                //删除旧记录
//                                QueryWrapper<FileGeneralDelivery> queryWrapper2 = new QueryWrapper<>() ;
//                                queryWrapper2.eq("fixmedins_code",fileGeneralDelivery.getFixmedins_code()) ;
//                                FileGeneralDelivery fileGeneralDelivery1 = fileGeneralDeliveryMapper.selectOne(queryWrapper2) ;
//                                //之前没有记录
//                                if(null == fileGeneralDelivery1){
////                                    fileGeneralDelivery1.deleteById() ;
//                                    fileGeneralDelivery.setCreateUser(sysUser.getUsername());
//                                    fileGeneralDelivery.setCreateTime(new Date());
//                                    fileGeneralDelivery.setIs_del("0");
//                                    fileGeneralDelivery.insert() ;
//                                }

                                fileGeneralDelivery.setFile_name(fileName);
                                fileGeneralDelivery.setCreateUser(sysUser.getUsername());
                                fileGeneralDelivery.setCreateTime(new Date());
                                fileGeneralDelivery.setIs_del("0");
                                fileGeneralDelivery.insert() ;

                            }else {
//                                //删除旧记录 匹配失败根据文件地址删除
//                                QueryWrapper<FileGeneralDelivery> queryWrapper2 = new QueryWrapper<>() ;
//                                queryWrapper2.eq("path",fileGeneralDelivery.getPath()) ;
//
//                                FileGeneralDelivery fileGeneralDelivery1 = fileGeneralDeliveryMapper.selectOne(queryWrapper2) ;
//                                //有记录，且匹配失败，删除再新增
//                                if(null != fileGeneralDelivery1 && "1".equals(fileGeneralDelivery1.getStatus())){
//                                    fileGeneralDelivery1.deleteById() ;
//                                    fileGeneralDelivery.setStatus("1");
//                                    fileGeneralDelivery.setCreateUser(sysUser.getUsername());
//                                    fileGeneralDelivery.setCreateTime(new Date());
//                                    fileGeneralDelivery.setIs_del("0");
//                                    fileGeneralDelivery.insert() ;
//                                }else if(null == fileGeneralDelivery1){//没记录 新增
//                                    fileGeneralDelivery.setStatus("1");
//                                    fileGeneralDelivery.setCreateUser(sysUser.getUsername());
//                                    fileGeneralDelivery.setCreateTime(new Date());
//                                    fileGeneralDelivery.setIs_del("0");
//                                    fileGeneralDelivery.insert() ;
//                                }
//                                //有记录，且匹配成功  不做操作

                                fileGeneralDelivery.setFile_name(fileName);
                                fileGeneralDelivery.setStatus("1");
                                fileGeneralDelivery.setCreateUser(sysUser.getUsername());
                                fileGeneralDelivery.setCreateTime(new Date());
                                fileGeneralDelivery.setIs_del("0");
                                fileGeneralDelivery.insert() ;

                            }







                    }

                }
            }

        }else {
            throw new CustomException("文件服务器请求异常！") ;
        }


    }


}
