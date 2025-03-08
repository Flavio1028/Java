package com.codeup.crudspring.enums;

import lombok.Getter;

@Getter
public enum StatusEnum {

    ACTIVE("Ativo"), INATIVE("Inativo");

    private final String value;

    private StatusEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}