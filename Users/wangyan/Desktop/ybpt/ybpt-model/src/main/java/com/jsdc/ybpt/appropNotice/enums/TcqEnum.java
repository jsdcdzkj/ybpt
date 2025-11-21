package com.jsdc.ybpt.appropNotice.enums;

/**
 * @Description 统筹区枚举
 * @Author WangXiao
 * @Date 2024/5/23
 */
public enum TcqEnum {
    SBJ("320399","徐州市市本级"),
    TSQ("320312","铜山区"),
    FX("320321","丰县"),
    PX("320322","沛县"),
    SNX("320324","睢宁县"),
    XYS("320381","新沂市"),
    PZS("320382","邳州市"),
    JWQ("320305","贾汪区");

    private String code;
    private String info;

    TcqEnum(String code, String info) {
        this.code=code;
        this.info=info;
    }

    public static TcqEnum getEnumByCode(String code)
    {
        TcqEnum[] values = TcqEnum.values();
        for (TcqEnum value : values) {
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
