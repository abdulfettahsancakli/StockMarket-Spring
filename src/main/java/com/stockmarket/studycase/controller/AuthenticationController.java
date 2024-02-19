package com.stockmarket.studycase.controller;
import com.stockmarket.studycase.entity.User;
import com.stockmarket.studycase.models.AuthenticationResponse;
import com.stockmarket.studycase.models.ResetPasswordRequest;
import com.stockmarket.studycase.models.UserAddModel;
import com.stockmarket.studycase.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody UserAddModel request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody UserAddModel request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/password/reset")
    public ResponseEntity<String> resetPassword(
            @RequestBody ResetPasswordRequest request
    ) {
        authService.resetPassword(request.getUsername(), request.getNewPassword());
        return ResponseEntity.status(HttpStatus.OK).body("Password reset successful");
    }
}