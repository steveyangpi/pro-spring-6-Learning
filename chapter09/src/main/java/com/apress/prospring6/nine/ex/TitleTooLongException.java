package com.apress.prospring6.nine.ex;

import java.io.Serial;

public class TitleTooLongException extends Exception{

    @Serial
    private static final long serialVersionUID = 42L;

    public TitleTooLongException(String message) {
        super(message);
    }

    public TitleTooLongException(String message, Throwable cause) {
        super(message, cause);
    }
}
