package com.jsdc.ybpt.service;


import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.FileDeliveryMapper;
import com.jsdc.ybpt.model.FileDelivery;
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
public class FileDeliveryService extends BaseService<FileDelivery> {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private FileDeliveryMapper fileDeliveryMapper;

    @Value("${fastDfs_url}")
    private String fastDfs_url;

    @Autowired
    private FixmedinsBService fixmedinsBService ;

    /**
     * 手动匹配下发文件
     * Author wzn
     * Date 2023/6/3 11:36
     */
    public void updateFileDelivery(FileDelivery fileDelivery) {

        SysUser sysUser = sysUserService.getUser();
        //查是否已存在改机构
        QueryWrapper<FileDelivery> queryWrapper = new QueryWrapper<>() ;
        queryWrapper.eq("fixmedins_code",fileDelivery.getFixmedins_code()) ;
        Long count = fileDeliveryMapper.selectCount(queryWrapper) ;
        if(count != 0 ){
            throw new CustomException("您选择的机构已关联文件,请重新选择！") ;
        }
        fileDelivery.setUpdateUser(sysUser.getUsername());
        fileDelivery.setUpdateTime(new Date());
        fileDelivery.setStatus("0");
        fileDelivery.updateById();
    }


    /**
     * 文件下发列表
     * Author wzn
     * Date 2023/6/3 11:38
     */
    public Page<FileDelivery> fileDeliveryList(FileDelivery fileDelivery) {
        SysUser sysUser = sysUserService.getUser();
        Page<FileDelivery> page = new Page<>(fileDelivery.getPageNo(), fileDelivery.getPageSize());
        QueryWrapper<FileDelivery> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        if (!"".equals(fileDelivery.getFixmedins_code()) && null != fileDelivery.getFixmedins_code()) {
            queryWrapper.eq("fixmedins_code", fileDelivery.getFixmedins_code());
        }
        if (!"".equals(fileDelivery.getStatus()) && null != fileDelivery.getStatus()) {
            queryWrapper.eq("status", fileDelivery.getStatus());
        }
        if (!"".equals(fileDelivery.getFixmedins_name()) && null != fileDelivery.getFixmedins_name()) {
            queryWrapper.like("fixmedins_name", fileDelivery.getFixmedins_name());
        }
        if(!"1".equals(sysUser.getUser_type())){
            queryWrapper.eq("fixmedins_code",sysUser.getOrg_code()) ;
        }
        queryWrapper.orderByDesc("createTime") ;
        Page<FileDelivery> fileDeliveryPage = fileDeliveryMapper.selectPage(page, queryWrapper);
        return fileDeliveryPage;
    }


    /**
     * 新增
     * Author wzn
     * Date 2023/6/3 13:57
     */
    public void addFileDelivery() {
        String url = fastDfs_url + "/list_dir?dir=file/fileDelivery";
        SysUser sysUser = sysUserService.getUser();
        Map<String, String> params = new HashMap<>();
        String res = HttpRequest.post(url).formStr(params).timeout(10000).execute().body();
        JSONObject jsonObject = JSON.parseObject(res);
        if ("ok".equals(jsonObject.getString("status"))) {

            JSONArray jsonArray = new JSONArray();
            jsonArray = (JSONArray)jsonObject.get("data") ;
            FileDelivery fileDelivery = null ;
            if(null != jsonArray){
                for(int y = 0; y < jsonArray.size();y++){
                    fileDelivery = new FileDelivery() ;

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
                            fileDelivery.setPath(jsonObj.getString("path")+"/"+jsonObj.getString("name"));
                            //匹配成功
                            if(null != fixmedinsB){
                                fileDelivery.setFixmedins_code(fixmedinsB.getFixmedins_code());
                                fileDelivery.setFixmedins_name(fixmedinsB.getFixmedins_name());
                                fileDelivery.setStatus("0");
                                //删除旧记录
                                QueryWrapper<FileDelivery> queryWrapper2 = new QueryWrapper<>() ;
                                queryWrapper2.eq("fixmedins_code",fileDelivery.getFixmedins_code()) ;
                                FileDelivery fileDelivery1 = fileDeliveryMapper.selectOne(queryWrapper2) ;
                                //之前没有记录
                                if(null == fileDelivery1){
//                                    fileDelivery1.deleteById() ;
                                    fileDelivery.setCreateUser(sysUser.getUsername());
                                    fileDelivery.setCreateTime(new Date());
                                    fileDelivery.setIs_del("0");
                                    fileDelivery.insert() ;
                                }
                                //之前有记录，不做操作

                            }else {
                                //删除旧记录 匹配失败根据文件地址删除
                                QueryWrapper<FileDelivery> queryWrapper2 = new QueryWrapper<>() ;
                                queryWrapper2.eq("path",fileDelivery.getPath()) ;

                                FileDelivery fileDelivery1 = fileDeliveryMapper.selectOne(queryWrapper2) ;
                                //有记录，且匹配失败，删除再新增
                                if(null != fileDelivery1 && "1".equals(fileDelivery1.getStatus())){
                                    fileDelivery1.deleteById() ;
                                    fileDelivery.setStatus("1");
                                    fileDelivery.setCreateUser(sysUser.getUsername());
                                    fileDelivery.setCreateTime(new Date());
                                    fileDelivery.setIs_del("0");
                                    fileDelivery.insert() ;
                                }else if(null == fileDelivery1){//没记录 新增
                                    fileDelivery.setStatus("1");
                                    fileDelivery.setCreateUser(sysUser.getUsername());
                                    fileDelivery.setCreateTime(new Date());
                                    fileDelivery.setIs_del("0");
                                    fileDelivery.insert() ;
                                }
                                //有记录，且匹配成功  不做操作

                            }







                    }

                }
            }

        }else {
            throw new CustomException("文件服务器请求异常！") ;
        }


    }


}
