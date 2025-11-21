package com.jsdc.ybpt.appropNotice.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum DocumentTypeEnum {

    FSS("28","发生数","approp_notice/occurFile", Arrays.asList("定点机构编码","定点机构名称","结算统筹区","参保统筹区","险种类型","医疗类别","人员类别",
            "生育类别","计划生育类别","本地政策参数","病种限额","证件号码","人次","人数","床日数","医疗费总额","全自费金额","超限价自费金额","先行自付金额","符合范围金额","统筹基金",
            "大额基金","公务员补助基金","职工大病基金","居民大病基金","医疗救助基金","一至六级残疾军人补助基金","个人账户","共济账户","现金","生育基金","医院垫付","倾斜救助","政府兜底",
            "离休保险基金","建档立卡大病基金","公卫补充基金"),1,1),

    YJS("29","应结算","approp_notice/settleFil", Arrays.asList("定点机构编码","定点机构名称","统筹区","结算政策名称","基金类别","职工统筹基金","居民统筹基金",
            "大额基金","公务员补助基金","职工大病基金","居民大病基金","医疗救助基金","一至六级残疾军人补助基金","个人账户","共济账户","生育基金","倾斜救助基金","离休保险基金","历年公务员账户",
            "公务员账户","地补基金","历年个账","其他账户","异地专用基金","合计"),1,1),

    SJZF("30","居民大病保险实际支付统计表","approp_notice/jmdbbxsjzfFile", Arrays.asList("机构编码","机构名称","支付金额","统筹区"),0,0),

    MJS("31","月结算表","approp_notice/monthSettleFile", Arrays.asList("基本情况","职工医保（元）","居民医保（元）","所属地区","医疗机构编码","医疗机构名称",
            "级别（医保收费等级）","本月","本月","基金拨付金额","预留考核保证金累计","不予支付累计","基金拨付金额","预留考核保证金累计","不予支付累计"),0,3),

    DRG("32","DRG表","approp_notice/drgFile", Arrays.asList("基本情况","职工医保（元）","居民医保（元）","所属地区","医疗机构编码","医疗机构名称","级别（医保收费等级）"
            ,"本月","本月","基金拨付金额","不予支付累计","基金拨付金额","不予支付累计"),
            0,3);

    private String code;
    private String info;
    private String filePath;
    private List<String> tableHeader;
    private int headStartRowIndex;
    private int headEndRowIndex;

    DocumentTypeEnum(String code,String info,String filePath,List<String> tableHeader,int headStartRowIndex,int headEndRowIndex) {
        this.code = code;
        this.info=info;
        this.filePath = filePath;
        this.tableHeader = tableHeader;
        this.headStartRowIndex = headStartRowIndex;
        this.headEndRowIndex = headEndRowIndex;
    }

    public static DocumentTypeEnum getEnumByCode(String code)
    {
        DocumentTypeEnum[] values = DocumentTypeEnum.values();
        for (DocumentTypeEnum value : values) {
            if(value.getCode().equals(code)){
                return value;
            }
        }
        return null;
    }
}
