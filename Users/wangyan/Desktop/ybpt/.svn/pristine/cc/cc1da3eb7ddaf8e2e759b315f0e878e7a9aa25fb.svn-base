package com.jsdc.ybpt.controller;


import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.mapper.FixmedinsBMapper;
import com.jsdc.ybpt.model.FileInfo;
import com.jsdc.ybpt.model.FixmedinsB;
import com.jsdc.ybpt.model.SysDict;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.pur.PurStockout;
import com.jsdc.ybpt.pur.PurStockoutDetail;
import com.jsdc.ybpt.service.FileInfoService;
import com.jsdc.ybpt.service.SysDictService;
import com.jsdc.ybpt.service.SysUserService;
import com.jsdc.ybpt.service.pur.PurStockoutDetailService;
import com.jsdc.ybpt.service.pur.PurStockoutService;
import com.jsdc.ybpt.util.FastDfs.FastDfsParams;
import com.jsdc.ybpt.util.FastDfs.FastDfsUtil;
import com.jsdc.ybpt.vo.PurStockoutDetailVo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.*;

/**
 * (PurStockout)表控制层
 *
 * @author wangYan
 * @since 2023-04-03 15:29:29
 */
@RestController
@RequestMapping("purStockout")
public class PurStockoutController {
    /**
     * 服务对象
     */
    @Resource
    private PurStockoutService purStockoutService;
    @Autowired
    private PurStockoutDetailService purStockoutDetailService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private FixmedinsBMapper fixmedinsBMapper;
    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private FastDfsUtil fastDfsUtil;

    /**
     * 分页查询所有数据
     *
     * @param purStockout 查询实体
     * @return 所有数据
     */
    @RequestMapping("/getPage")
    public ResultInfo getPage(@RequestBody PurStockout purStockout) {

        Page<PurStockout> page = purStockoutService.getPaging(purStockout.getPageNo(), purStockout.getPageSize(), purStockout);
        return ResultInfo.success(page);
    }

    @RequestMapping("/export")
    public void export(HttpServletResponse response, PurStockout purStockout) throws Exception {
        List<PurStockoutDetailVo> list = purStockoutService.getList(purStockout);
        //查询统筹区
        HashMap<String, String> tcqMap = new HashMap();
        List<SysDict> tcq = sysDictService.list(new QueryWrapper<SysDict>().eq("dict_type", "ADMDVS").eq("is_del", "0"));
        tcq.forEach(x -> tcqMap.put(x.getValue(), x.getLabel()));
        for (int i = 0; i < list.size(); i++) {
            PurStockoutDetailVo x = list.get(i);
            x.setStockout_type_name("1".equals(x.getStockout_type()) ? "药品" : "耗材");
            x.setFix_blng_admdvs_name(tcqMap.get(x.getFix_blng_admdvs()));
        }
        // 通过工具类创建writer，默认创建xls格式
        BigExcelWriter writer = (BigExcelWriter) ExcelUtil.getBigWriter();
        writer.addHeaderAlias("org_name", "医药机构名称");
        writer.addHeaderAlias("org_code", "医药机构省平台编码");
//        writer.addHeaderAlias("codeList", "省阳光采购平台产品编码");
        writer.addHeaderAlias("fix_blng_admdvs_name", "统筹区");
        writer.addHeaderAlias("createUserName", "创建人");
        writer.addHeaderAlias("createTime", "创建时间");
        writer.addHeaderAlias("stockout_type_name", "缺货分类");
        writer.addHeaderAlias("name", "药品(耗材)名称");
        writer.addHeaderAlias("unit", "规格");
        writer.addHeaderAlias("code", "省阳光采购平台产品编码");
        writer.addHeaderAlias("start_date", "缺货开始采购日期");
        writer.addHeaderAlias("end_date", "缺货后最近一次采购订单日期");
        writer.addHeaderAlias("sub_quantity", "缺货订单提交次数");
        writer.addHeaderAlias("quantity", "数量");
        writer.addHeaderAlias("price", "金额");
        writer.addHeaderAlias("enterprise", "生产企业");
        writer.addHeaderAlias("company", "配送公司");
        writer.addHeaderAlias("note", "线索类型");
        //只导出定义字段
        writer.setOnlyAlias(true);
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        //out为OutputStream，需要写出到的目标流
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        // 关闭writer，释放内存
        writer.close();
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @RequestMapping("/selectOne")
    public ResultInfo selectOne(String id) {
        PurStockout x = this.purStockoutService.getById(id);
        List<PurStockoutDetail> details = purStockoutDetailService.list(Wrappers.<PurStockoutDetail>lambdaQuery().eq(PurStockoutDetail::getPur_stockout_id, id));
        x.setPurStockoutDetail(details);
        return ResultInfo.success(x);
    }

    /**
     * 新增数据
     *
     * @param purStockout 实体对象
     * @return 新增结果
     */
    @RequestMapping("/insert")
    public ResultInfo insert(@RequestBody PurStockout purStockout) {
        return ResultInfo.success(this.purStockoutService.save(purStockout));
    }

    /**
     * 修改数据
     *
     * @param purStockout 实体对象
     * @return 修改结果
     */
    @RequestMapping("/update")
    public ResultInfo update(@RequestBody PurStockout purStockout) {
        return ResultInfo.success(this.purStockoutService.updateById(purStockout));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @RequestMapping("/delete")
    public ResultInfo delete(@RequestParam("idList") List<Long> idList) {
        return ResultInfo.success(this.purStockoutService.removeByIds(idList));
    }

    /**
     * 新增修改数据
     *
     * @param purStockout 实体对象
     * @return 修改结果
     */
    @RequestMapping("/insertOrUpdate")
    public ResultInfo insertOrUpdate(@RequestPart(value = "purStockout", required = false) PurStockout purStockout, @RequestPart(value = "files", required = false) List<MultipartFile> files) {
        SysUser sysUser = sysUserService.getUser();
        StringJoiner joiner = new StringJoiner(", ");
        //String 下标 List 上传多文件内容
        Map<String, List<MultipartFile>> map = new HashMap();
        for (int i = 0; i < files.size(); i++) {
            MultipartFile x = files.get(i);
            String[] v = x.getOriginalFilename().split("&");
            List list;
            if (map.get(v[0]) != null) {
                list = map.get(v[0]);
                list.add(x);
            } else {
                list = new ArrayList();
                list.add(x);
            }
            map.put(v[0], list);
        }

        FixmedinsB fixmedinsB = fixmedinsBMapper.selectOne(Wrappers.<FixmedinsB>lambdaQuery().eq(FixmedinsB::getIs_del, "0").eq(FixmedinsB::getFixmedins_code, sysUser.getOrg_code()));
        if (purStockout.getId() == null) {
            String pur_stockout_id = IdUtil.simpleUUID();
            purStockout.setId(pur_stockout_id);
            purStockout.setFix_blng_admdvs(fixmedinsB.getFix_blng_admdvs());
            purStockout.setOrg_code(sysUser.getOrg_code());
            purStockout.setOrg_name(sysUser.getOrg_name());
            purStockout.setCreateUser(sysUser.getId());
            purStockout.setCreateUserName(sysUser.getUsername());
            purStockout.setCreateTime(new Date());
            purStockout.setIs_del("0");

            //详情
            for (int i = 0; i < purStockout.getPurStockoutDetail().size(); i++) {
                PurStockoutDetail x = purStockout.getPurStockoutDetail().get(i);
                x.setId(IdUtil.simpleUUID());
                x.setPur_stockout_id(pur_stockout_id);
                x.setStockout_type(purStockout.getStockout_type());
                x.setNote(purStockout.getClues());

                //绑定附件
                List<MultipartFile> list = map.get(String.valueOf(i));
                for (int j = 0; j < list.size(); j++) {
                    MultipartFile file = list.get(j);
                    FastDfsParams params = new FastDfsParams("price_declaration/pur", file, "9", x.getId());
                    fastDfsUtil.uploadFile(params);
                }
                joiner.add(x.getCode());
                x.insert();
            }

            //拼接 省阳光采购平台产品编码
            purStockout.setCodeList(joiner.toString());
        } else {
            purStockout.setUpdateUser(sysUser.getId());
            purStockout.setUpdateTime(new Date());

            //详情
            List<PurStockoutDetail> purStockoutDetails = purStockoutDetailService.list(Wrappers.<PurStockoutDetail>lambdaQuery().eq(PurStockoutDetail::getPur_stockout_id, purStockout.getId()));
            for (int i = 0; i < purStockoutDetails.size(); i++) {
                purStockoutDetails.get(i).deleteById();
            }
            for (int i = 0; i < purStockout.getPurStockoutDetail().size(); i++) {
                PurStockoutDetail x = purStockout.getPurStockoutDetail().get(i);
                x.setPur_stockout_id(purStockout.getId());
                x.setStockout_type(purStockout.getStockout_type());

                //绑定附件
                List<MultipartFile> list = map.get(i);
                for (int j = 0; j < list.size(); j++) {
                    MultipartFile file = list.get(i);
                    FastDfsParams params = new FastDfsParams("price_declaration/pur", file, "9", x.getId());
                    params.setFileName(file.getOriginalFilename().split("&")[1]);
                    fastDfsUtil.uploadFile(params);
                }

                joiner.add(x.getCode());
                x.insert();
            }

            //拼接 省阳光采购平台产品编码
            purStockout.setCodeList(joiner.toString());
        }
        return ResultInfo.success(this.purStockoutService.saveOrUpdate(purStockout));
    }

    @RequestMapping("/getFile/{id}")
    public ResultInfo getFile(@PathVariable Serializable id) {
        List<FileInfo> list = fileInfoService.list(Wrappers.<FileInfo>lambdaQuery()
                .eq(FileInfo::getBizType, "9")
                .eq(FileInfo::getBizId, id)
        );
        return ResultInfo.success(list);
    }


    /**
     * 上传文件
     */
    @RequestMapping("/uploadFile")
    @ResponseBody
    public ResultInfo uploadFile(@RequestParam(value = "fileInfoId", required = false) List<String> fileInfoId, @RequestParam(value = "file", required = false) List<MultipartFile> file) {
        //清除文件
        if (fileInfoId != null && fileInfoId.size() > 0) {
            for (int i = 0; i < fileInfoId.size(); i++) {
                String x = fileInfoId.get(i);
                FileInfo fileInfo = fileInfoService.getById(x);
                if (fileInfo != null) {
                    fastDfsUtil.deleteFile(fileInfo);
                }
            }
        }
        List<String> fileId = new ArrayList<>();
        List<String> fileName = new ArrayList<>();
        //上传文件服务器
        if (file != null && file.size() > 0) {
            for (int i = 0; i < file.size(); i++) {
                MultipartFile x = file.get(i);
                FastDfsParams params = new FastDfsParams("price_declaration/pur", x, "9", "");
                params.setFileName(x.getOriginalFilename());
                FileInfo fileInfo = (FileInfo) fastDfsUtil.uploadFile(params).getData();
                fileId.add(fileInfo.getId());
                fileName.add(fileInfo.getFileName());
            }
        }
        Map map = new HashMap();
        map.put("fileId", fileId);
        map.put("fileName", fileName);

        return ResultInfo.success(map);
    }
}

