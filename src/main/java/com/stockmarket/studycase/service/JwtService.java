package com.stockmarket.studycase.service;
import com.stockmarket.studycase.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUsername(String token);
    boolean isValid(String token, UserDetails user);
    String generateToken(User user);
}