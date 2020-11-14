package com.mmall.exception;

/**
 * @ClassName PerssionException
 * @Description TODO
 * @Author gaopeng
 * @Date 2020/11/12 22:21
 * @Version 1.0
 **/

public class PerssionException extends RuntimeException{
    public PerssionException() {
        super();
    }

    public PerssionException(String message) {
        super(message);
    }

    public PerssionException(String message, Throwable cause) {
        super(message, cause);
    }

    public PerssionException(Throwable cause) {
        super(cause);
    }

    protected PerssionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
