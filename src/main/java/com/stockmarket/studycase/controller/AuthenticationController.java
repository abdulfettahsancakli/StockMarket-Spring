package com.stockmarket.studycase.controller;
import com.stockmarket.studycase.models.*;
import com.stockmarket.studycase.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationService authService;

    public AuthenticationController
            (AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RequestResponseModel> register(
            @RequestBody UserRegisterModel signUpRequest
    ) {
        return ResponseEntity.ok(authService.register(signUpRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<RequestResponseModel> login(
            @RequestBody UserLoginModel signInRequest
    ) {
        return ResponseEntity.ok(authService.authenticate(signInRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<RequestResponseModel> refreshToken(@RequestBody RefreshTokenModel refreshTokenRequest){
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }

    @PostMapping("/password/reset")
    public ResponseEntity<String> resetPassword(
            @RequestBody ResetPasswordRequest request
    ) {
        authService.resetPassword(request.getUsername(), request.getNewPassword());
        return ResponseEntity.status(HttpStatus.OK).body("Password reset successful");
    }
}