package com.codeup.crudspring.enums;

public enum StatusEnum {

    ACTIVE("Ativo"), INATIVE("Inativo");

    private String value;

    private StatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

}