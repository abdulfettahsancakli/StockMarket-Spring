package com.stockmarket.studycase.enums;

import com.stockmarket.studycase.enums.base.BaseEnum;

public enum IslemTipi implements BaseEnum {
    HISSE_SENEDI(1,"HISSE_SENEDI"),
    KAR_PAYI(2,"KAR_PAYI");

    private final Integer value;
    private final String label;

    IslemTipi(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    @Override
    public Integer getValue() {
        return null;
    }

    @Override
    public String getLabel() {
        return null;
    }
}
