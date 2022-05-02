package com.baize.base.common.exception;


/**
 * @描述 :  作为所有系统异常的公共类

 */
public class AbstractRdcException extends RuntimeException {

    public AbstractRdcException() {
        super();
    }

    public AbstractRdcException(String message) {
        super(message);
    }

    public AbstractRdcException(String message, Throwable cause) {
        super(message, cause);
    }

    public AbstractRdcException(Throwable cause) {
        super(cause);
    }

}

