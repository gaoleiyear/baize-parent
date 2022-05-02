package com.baize.base.common.code;

import com.baize.base.common.enums.CodeConfigEnum;

public interface BaseCodeInterface {
    String getMessage();

    Integer getSelfCode();

    CodeConfigEnum getConfig();

    default Integer getCode() {
        return getConfig().getCodePrefix() + getSelfCode();
    }
}
