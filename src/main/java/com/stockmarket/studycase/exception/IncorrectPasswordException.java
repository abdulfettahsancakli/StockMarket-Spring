package com.stockmarket.studycase.exception;


public class IncorrectPasswordException extends RuntimeException {

    public IncorrectPasswordException() {
        super("Girilen parola yanlış");
    }
}