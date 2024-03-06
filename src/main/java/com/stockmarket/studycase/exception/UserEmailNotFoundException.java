package com.stockmarket.studycase.exception;

public class UserEmailNotFoundException extends RuntimeException {

    public UserEmailNotFoundException(String email) {
        super("Bu e-posta ile kullanıcı bulunamadı: " + email);
    }
}
