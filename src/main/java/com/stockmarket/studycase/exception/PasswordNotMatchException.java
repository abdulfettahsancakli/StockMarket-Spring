package com.stockmarket.studycase.exception;

public class PasswordNotMatchException extends RuntimeException {

    public PasswordNotMatchException() {
        super("Şifreler eşleşmiyor");
    }
}