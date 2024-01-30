package com.stockmarket.studycase.enums;
import com.stockmarket.studycase.enums.base.BaseEnum;

public enum YatirimciTipi implements BaseEnum {

    GERCEK(1,"GERCEK"),
    TUZEL(2,"TUZEL");

    private final Integer value;
    private final String label;

    YatirimciTipi(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getLabel() {
        return label;
    }

}
