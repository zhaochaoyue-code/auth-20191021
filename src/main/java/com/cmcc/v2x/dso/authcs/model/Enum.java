package com.cmcc.v2x.dso.authcs.model;

public enum Enum {
    // 枚举的属性字段正例
    DISABLED(0, "禁用"),
    ENABLED(1, "启用");

    public final int value;
    private final String description;

    Enum(int value, String description) {
        this.value = value;
        this.description = description;
    }
   /* private Enum(int value, String description) {
        this.value = value;
        this.description = description;
    }*/

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
