package com.stockmarket.studycase.exception;

public class NotSendMailException extends RuntimeException {

    public NotSendMailException() {
        super("Parola belirleme e-postası gönderilemiyor lütfen tekrar deneyin");
    }
}
