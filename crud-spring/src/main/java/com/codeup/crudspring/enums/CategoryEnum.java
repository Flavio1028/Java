package com.codeup.crudspring.enums;

import lombok.Getter;

@Getter
public enum CategoryEnum {

    BACK_END("Back-end"), FRONT_END("Front-end");

    private final String value;

    private CategoryEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}
