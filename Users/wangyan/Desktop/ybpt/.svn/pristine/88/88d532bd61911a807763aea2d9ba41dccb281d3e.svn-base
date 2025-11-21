package com.jsdc.ybpt.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.mapper.CommonMapper;
import com.jsdc.ybpt.model.DiseaseMutex;
import com.jsdc.ybpt.service.DiseaseMutexService;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/diseaseMutex")
public class DiseaseMutexController {
    @Autowired
    private DiseaseMutexService diseaseMutexService;

    @Autowired
    private CommonMapper commonMapper ;


    /**
     * 查询病种列表
     * @param pageNo
     * @param pageSize
     * @param diseCode
     * @param diseName
     * @return
     */
    @RequestMapping("/selectDiseaseList")
    public ResultInfo selectDiseaseList(Integer pageNo,Integer pageSize,String diseCode, String diseName){
        ResultInfo resultInfo = diseaseMutexService.selectDisease(pageNo, pageSize, diseCode, diseName);
        return resultInfo;
    }

    @RequestMapping("/selectDiseaseMutexList")
    public ResultInfo selectDiseaseMutexList(@RequestBody DiseaseMutex diseaseMutex){
        QueryWrapper<DiseaseMutex> qw = new QueryWrapper();
        qw.eq("is_del","0");
        qw.eq("type","1");
        qw.orderByDesc("createTime") ;
        if(StrUtil.isNotEmpty(diseaseMutex.getDise_code())){
            qw.eq("dise_code",diseaseMutex.getDise_code());
        }
        if(StrUtil.isNotEmpty(diseaseMutex.getDise_name())){
            qw.like("dise_name",diseaseMutex.getDise_name());
        }
        Page<DiseaseMutex> page = new Page<>(diseaseMutex.getPageNo(), diseaseMutex.getPageSize());
        Page<DiseaseMutex> pageinfo = diseaseMutexService.page(page, qw);
        List<DiseaseMutex> diseaseMutexList = pageinfo.getRecords();
        //todo  等内网放开
//        List<NatDataDicAVo> maps = commonMapper.selectDicList("INSUTYPE");
//        Map<String,String> map = new HashMap<>() ;
//        for(NatDataDicAVo n:maps){
//            map.put(n.getNat_dic_val_code(),n.getNat_dic_val_name()) ;
//        }
//        if(CollectionUtils.isNotEmpty(diseaseMutexList)){
//            for(DiseaseMutex d:diseaseMutexList){
//                d.setInsured_type(map.get(d.getInsured_type()));
//                d.setInsured_mutex_type(map.get(d.getInsured_mutex_type()));
//            }
//        }


        return ResultInfo.success(pageinfo);
    }
    @RequestMapping("/saveDiseaseMutex")
    public ResultInfo saveDiseaseMutex(@RequestBody DiseaseMutex diseaseMutex){
        return diseaseMutexService.saveDiseaseMutex(diseaseMutex);
    }

    @RequestMapping("/delDiseaseMutex")
    public ResultInfo delDiseaseMutex(@RequestBody DiseaseMutex diseaseMutex){
        diseaseMutex.setIs_del("1");
        diseaseMutexService.updateById(diseaseMutex) ;
        return ResultInfo.success();
    }
}
