package com.stockmarket.studycase.service;

import com.stockmarket.studycase.entity.User;
import com.stockmarket.studycase.models.AuthenticationResponse;
import com.stockmarket.studycase.models.UserAddModel;

public interface AuthenticationService {
    AuthenticationResponse register(UserAddModel request);
    AuthenticationResponse authenticate(UserAddModel request);
    void resetPassword(String username, String newPassword);
}