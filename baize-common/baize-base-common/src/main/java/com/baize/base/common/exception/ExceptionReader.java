package com.baize.base.common.exception;

/**
 * @what
 */
public class ExceptionReader {

    /**
     * read message
     *
     * @param e
     * @return
     */
    public static String readMessage(Throwable e) {
        return readMessage(e, 0);
    }

    /**
     * 获取异常信息
     *
     * @param e
     * @param deep
     * @return
     */
    private static String readMessage(Throwable e, int deep) {
        String message = e.getMessage();
        if (message == null) {
            Throwable throwable = e.getCause();
            if (throwable != null) {
                message = throwable.getMessage();
                if (message == null && deep < 100) {
                    message = readMessage(throwable, ++deep);
                }
            }
        }
        return message;
    }
}
