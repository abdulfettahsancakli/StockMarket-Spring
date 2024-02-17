package com.stockmarket.studycase.exception;


public class TertipNotFoundException extends IllegalArgumentException{
    public TertipNotFoundException(String message){
        super(message);
    }
}