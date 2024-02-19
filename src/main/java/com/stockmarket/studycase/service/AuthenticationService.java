package com.stockmarket.studycase.service;


import com.stockmarket.studycase.entity.User;
import com.stockmarket.studycase.models.RefreshTokenModel;
import com.stockmarket.studycase.models.RequestResponseModel;
import com.stockmarket.studycase.models.UserLoginModel;
import com.stockmarket.studycase.models.UserRegisterModel;
import com.stockmarket.studycase.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private  JwtUtils jwtUtils;
    @Autowired
    private  AuthenticationManager authenticationManager;

    public RequestResponseModel register(UserRegisterModel SignUpRequest) {
        RequestResponseModel response = new RequestResponseModel();
        try {
            Optional<User> existingUser = userRepository.findById(SignUpRequest.getId());
            if (existingUser.isPresent()) {
                response.setStatusCode(400);
                response.setError("Bu kullanıcı zaten mevcut. Lütfen farklı bir kullanıcı adı seçin.");
                return response;
            }
            User user = new User();
            user.setId(SignUpRequest.getId());
            user.setFirstName(SignUpRequest.getFirstName());
            user.setLastName(SignUpRequest.getLastName());
            user.setUsername(SignUpRequest.getUsername());
            user.setPassword(passwordEncoder.encode(SignUpRequest.getPassword()));
            user.setRole(SignUpRequest.getRole());

            User savedUser =  userRepository.save(user);
            if(savedUser != null && savedUser.getId()>0){
                response.setUserInfo(savedUser);
                response.setMessage("User Saved Succesfully");
                response.setStatusCode(200);
            }
        }
        catch (Exception e){
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    public RequestResponseModel authenticate(UserLoginModel SignInRequest) {
        RequestResponseModel response = new RequestResponseModel();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(SignInRequest.getUsername(),SignInRequest.getPassword()));
            var user = userRepository.findByUsername(SignInRequest.getUsername()).orElseThrow();
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(),user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24 Hours");
            response.setMessage("Successfully Signed In");
        }
        catch (Exception e){
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    public RequestResponseModel refreshToken(RefreshTokenModel refreshTokenReq) {
        RequestResponseModel response = new RequestResponseModel();
        String name = jwtUtils.extractUsername(refreshTokenReq.getToken());
        User user = userRepository.findByUsername(name).orElseThrow();
        if(jwtUtils.isTokenValid(refreshTokenReq.getToken(),user))
        {
            var jwt = jwtUtils.generateToken(user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshTokenReq.getToken());
            response.setExpirationTime("24 Hour");
            response.setMessage("Succesfully Refreshed Token");
        }
        response.setStatusCode(500);
        return response;
    }

    public void resetPassword(String username, String newPassword) {
        // Kullanıcıyı kullanıcı adına göre bul
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        // Yeni şifreyi şifreleyerek kullanıcıya atayın
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}

