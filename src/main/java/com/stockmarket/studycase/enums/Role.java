package com.stockmarket.studycase.enums;
import com.stockmarket.studycase.enums.base.BaseEnum;

public enum Role implements BaseEnum{

    USER( 1,"USER"),
    ADMIN(2,"ADMIN");

    private final Integer value;
    private final String label;

    Role(Integer value, String label) {
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
