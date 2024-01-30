package com.stockmarket.studycase.enums;

import com.stockmarket.studycase.enums.base.BaseEnum;

public enum KuponTuru implements BaseEnum {
    PAY_ALMA(1,"PAY_ALMA"),
    KAR_PAYI(2,"KAR_PAYI");

    private final Integer value;
    private final String label;

    KuponTuru(Integer value, String label) {
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