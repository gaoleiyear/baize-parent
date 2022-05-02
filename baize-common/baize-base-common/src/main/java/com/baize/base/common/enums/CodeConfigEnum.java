package com.baize.base.common.enums;

import java.io.Serializable;

/**
 * Code配置
 */
public enum CodeConfigEnum implements Serializable {

    API(0, "web api"),

    ;

    private Integer codePrefix;
    private String name;

    CodeConfigEnum(Integer codePrefix, String name) {
        this.codePrefix = codePrefix;
        this.name = name;
    }

    public Integer getCodePrefix() {
        return this.codePrefix;
    }

    public String getName() {
        return this.name;
    }

}
