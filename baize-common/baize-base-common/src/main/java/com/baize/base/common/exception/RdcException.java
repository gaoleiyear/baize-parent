package com.baize.base.common.exception;

import com.baize.base.common.code.BaseCodeInterface;
import lombok.Getter;

/**
 * @描述 :
 */
@Getter
public class RdcException extends AbstractRdcException {

    private Integer code;

    public RdcException() {
    }

    public RdcException(String message) {
        super(message);
    }

    public RdcException(BaseCodeInterface code, String message) {
        super(message);
        this.code = code.getCode();
    }

    public RdcException(BaseCodeInterface code) {
        super(code.getMessage());
        this.code = code.getCode();
    }

    public RdcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RdcException(Throwable cause) {
        super(cause);
    }

}
