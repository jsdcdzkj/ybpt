package com.jsdc.ybpt.appropNotice.enums;

import lombok.Getter;

/**
 * @Description 参保险种枚举
 * @Author WangXiao
 * @Date 2024/5/21
 */
@Getter
public enum InsutypeEnum {

    ZGJBYLBX("310","职工基本医疗保险"),
    GWYYLBZ("320" , "公务员医疗补助"),
    DEYLFYBZ("330" , "大额医疗费用补助"),
    LXRYYLBZ("340" , "离休人员医疗保障"),
    CFJRYLBZ("350", "一至六级残废军人医疗补助"),
    LHJYLBZ("360", "老红军医疗保障"),
    QYBCYLBX("370", "企业补充医疗保险"),
    XXNCHZYL("380", "新型农村合作医疗"),
    CXJMJBYLBX("390", "城乡居民基本医疗保险(含长护险)"),
    CZJMJBYLBX("391", "城镇居民基本医疗保险"),
    CXJMDBYLBX("392", "城乡居民大病医疗保险"),
    QTTSRYYLBZ("399", "其他特殊人员医疗保障"),
    SYBX("510", "生育保险"),
    ZHX("410", "照护险"),
    CXJMBCYLBX("39903", "城乡居民补充医疗保险"),
    JGQLGRYLBX("39904", "建国前老工人医疗保险"),
    LMYLBZ("39901", "劳模医疗保障")
    ;

    private String code;
    private String info;

    InsutypeEnum(String code, String info) {
        this.code=code;
        this.info=info;
    }

    public static InsutypeEnum getEnumByCode(String code)
    {
        InsutypeEnum[] values = InsutypeEnum.values();
        for (InsutypeEnum value : values) {
            if(value.getCode().equals(code)){
                return value;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
