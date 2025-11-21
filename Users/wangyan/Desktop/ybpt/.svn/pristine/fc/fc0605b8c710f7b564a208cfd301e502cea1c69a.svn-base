package com.jsdc.ybpt.service.formula;

import cn.hutool.core.annotation.Alias;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsdc.ybpt.formula.domain.dto.CatalogPageDTO;
import com.jsdc.ybpt.formula.domain.entity.Catalog;
import com.jsdc.ybpt.mapper.formula.CatalogMapper;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.service.SysUserService;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.util.exception.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 制剂目录(Catalog)业务接口
 *
 * @author yc
 * @since 2024-05-14 11:24:46
 */
@Service
public class CatalogService extends ServiceImpl<CatalogMapper,Catalog> {

    @Resource
    private CatalogMapper catalogMapper;

    @Resource
    private SysUserService sysUserService ;

    /**
     *
     * @description //TODO  分页查询
     * @author wangxiao
     * @date 2024/5/14
     * @param catalogPageDTO
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.jsdc.ybpt.formula.domain.entity.Catalog>
     */
    public Page<Catalog> getPage(CatalogPageDTO catalogPageDTO) {
        Page<Catalog> page = new Page<>(catalogPageDTO.getPageNo(), catalogPageDTO.getPageSize());
        QueryWrapper<Catalog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        if (StringUtils.isNotBlank(catalogPageDTO.getNationalFormulaCode())) {
            queryWrapper.like("national_formula_code", catalogPageDTO.getNationalFormulaCode());
        }
        if (StringUtils.isNotBlank(catalogPageDTO.getFormulaName())) {
            queryWrapper.like("formula_name", catalogPageDTO.getFormulaName());
        }
        if (StringUtils.isNotBlank(catalogPageDTO.getRegisterCompanyName())) {
            queryWrapper.like("register_company_name", catalogPageDTO.getRegisterCompanyName());
        }
        Page<Catalog> catalogPage = catalogMapper.selectPage(page, queryWrapper);
        return catalogPage;
    }


    /**
     *
     * @description //TODO  导入数据
     * @author wangxiao
     * @date 2024/5/14
     * @param file
     * @return void
     */
    @Transactional
    public void importData(MultipartFile file) {
        SysUser sysUser = sysUserService.getUser();
        ExcelReader reader = null;
        InputStream in = null;
        try {
            in = file.getInputStream();
            reader = ExcelUtil.getReader(in);
            //指定标题行，从真正数据行读取
            List<Catalog> catalogList = reader.read(2,3,Catalog.class);
            if(StringUtils.isNotEmpty(catalogList)){
                for (Catalog catalog : catalogList) {
                    catalog.setIsDel("0");
                    catalog.setId(IdUtil.simpleUUID());
                    catalog.setCreateuser(sysUser.getId());
                    catalog.setCreatetime(new Date());
                }
                List<String> fileNationalFormulaCodes = catalogList.stream().map(Catalog::getNationalFormulaCode).collect(Collectors.toList());
                List<Catalog> dataBaseCatalogs = catalogMapper.selectList(new QueryWrapper<Catalog>());
                if(StringUtils.isNotEmpty(dataBaseCatalogs)){
                    //文件没有，数据库有的数据删除
                    List<Catalog> notExistCatalogs = dataBaseCatalogs.stream().filter(x -> !fileNationalFormulaCodes.contains(x.getNationalFormulaCode())).collect(Collectors.toList());
                    if(StringUtils.isNotEmpty(notExistCatalogs)){
                        List<String> delCatalogIds = notExistCatalogs.stream().map(Catalog::getId).collect(Collectors.toList());
                        if(delCatalogIds.size() >= 1000){
                            List<List<String>> split = CollectionUtil.split(delCatalogIds, 900);
                            for (List<String> strings : split) {
                                catalogMapper.deleteBatchIds(strings);
                            }
                        }else{
                            catalogMapper.deleteBatchIds(delCatalogIds);
                        }
                    }
                    //文件和数据库都有的更新
                    List<String> dataBaseNationalFormulaCodes = dataBaseCatalogs.stream().map(Catalog::getNationalFormulaCode).collect(Collectors.toList());
                    List<Catalog> updateCatalogs = catalogList.stream().filter(x -> dataBaseNationalFormulaCodes.contains(x.getNationalFormulaCode())).collect(Collectors.toList());
                    if(StringUtils.isNotEmpty(updateCatalogs)){
                        for (Catalog updateCatalog : updateCatalogs) {
                            List<Catalog> filter = dataBaseCatalogs.stream().filter(x -> StringUtils.isNotBlank(x.getNationalFormulaCode()) && x.getNationalFormulaCode().equals(updateCatalog.getNationalFormulaCode())).collect(Collectors.toList());
                            updateCatalog.setId(filter.get(0).getId());
                            updateCatalog.setUpdateuser(sysUser.getId());
                            updateCatalog.setUpdatetime(new Date());
                        }
                        this.updateBatchById(updateCatalogs);
                    }
                    //文件有，数据库没有的插入
                    List<Catalog> insertCatalogs = catalogList.stream().filter(x -> !dataBaseNationalFormulaCodes.contains(x.getNationalFormulaCode())).collect(Collectors.toList());
                    if(StringUtils.isNotEmpty(insertCatalogs)){
                        this.saveBatch(insertCatalogs);
                    }
                }else{
                    //数据库没有数据，全部插入
                    this.saveBatch(catalogList);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomException("导入数据不正确，请检查文档");
        }finally {
            reader.close();
            IoUtil.close(in);
        }
    }

    /**
     *
     * @description //TODO  导出数据
     * @author wangxiao
     * @date 2024/5/15
     * @param catalog
     * @param response
     * @return void
     */
    public void exportData(Catalog catalog, HttpServletResponse response) {
        ExcelWriter writer = null;
        ServletOutputStream out= null;
        try {
            writer = ExcelUtil.getWriter(true);
            QueryWrapper<Catalog> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("is_del", "0");
            if (StringUtils.isNotBlank(catalog.getNationalFormulaCode())) {
                    queryWrapper.like("national_formula_code", catalog.getNationalFormulaCode());
            }
            if (StringUtils.isNotBlank(catalog.getFormulaName())) {
                queryWrapper.like("formula_name", catalog.getFormulaName());
            }
            if (StringUtils.isNotBlank(catalog.getRegisterCompanyName())) {
                queryWrapper.like("register_company_name", catalog.getRegisterCompanyName());
            }
            List<Catalog> catalogList = catalogMapper.selectList(queryWrapper);
            if(StringUtils.isNotEmpty(catalogList)){
                catalogList.stream().forEach(x -> {
                    String selfPayRatio = x.getSelfPayRatio();
                    if(StringUtils.isNotBlank(selfPayRatio)){
                        BigDecimal mul = NumberUtil.mul(selfPayRatio, "100");
                        selfPayRatio = mul + "%";
                        x.setSelfPayRatio(selfPayRatio);
                    }
                });
            }
            Field[] fields = ReflectUtil.getFields(Catalog.class);
            for (Field field : fields) {
                Alias alias = field.getDeclaredAnnotation(Alias.class);
                if(StringUtils.isNotNull(alias)){
                    writer.addHeaderAlias(alias.value(),alias.value());
                }
            }
            writer.setOnlyAlias(true);
            writer.write(catalogList, true);

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode("制剂目录导出", "UTF-8") +".xlsx");
            out = response.getOutputStream();
            writer.flush(out, true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writer.close();
            IoUtil.close(out);
        }
    }



 
}

