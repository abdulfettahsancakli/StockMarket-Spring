package com.stockmarket.studycase.exception;

public class HissedarAlreadyExistsException extends RuntimeException{
    public HissedarAlreadyExistsException(String message){
        super(message);
    }
}