package com.stockmarket.studycase.controller;

import com.stockmarket.studycase.models.*;
import com.stockmarket.studycase.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Principal;

@RestController
public class AuthenticationController {


    @Autowired
    private final AuthenticationService authService;

    @Autowired
    private final SpringTemplateEngine myTemplateEngine;

    public AuthenticationController(AuthenticationService authService, SpringTemplateEngine myTemplateEngine) {
        this.authService = authService;
        this.myTemplateEngine = myTemplateEngine;
    }

    @PostMapping("/register")
    public ResponseEntity<RequestResponseModel> register( @RequestBody UserRegisterModel signUpRequest ) {

        return ResponseEntity.ok(authService.register(signUpRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<RequestResponseModel> login(@RequestBody UserLoginModel signInRequest) {
        return ResponseEntity.ok(authService.authenticate(signInRequest));
    }

    @PostMapping("/updateUser")
    public ResponseEntity<RequestResponseModel> updateUser(@RequestBody UserUpdateModel updateUserRequest) {
        RequestResponseModel response = authService.updateUser(updateUserRequest);
        HttpStatus status = HttpStatus.valueOf(response.getStatusCode());
        return ResponseEntity.status(status).body(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<RequestResponseModel> refreshToken(@RequestBody RefreshTokenModel refreshTokenRequest){
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }
    @PatchMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        authService.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/forgot-password")
    public ResponseEntity <String> forgotPassword(@RequestParam String email){
        return ResponseEntity.ok(authService.forgotPassword(email));
    }

    @GetMapping("/set-password")
    public String setPasswordPage(@RequestParam String email, Model model) {
        // E-posta adresini model'e ekle
        model.addAttribute("email", email);
        // Thymeleaf template'i işleyin
        Context context = new Context();
        context.setVariable("email", email);
        String htmlContent = myTemplateEngine.process("setPassword.html", context);

        return htmlContent;
    }

    @PostMapping("/set-password")
    public String setPassword(@RequestParam String email,@RequestParam String newPassword){
        String message = authService.setPassword(email, newPassword);
        try {
            // successPassword.html dosyasını oku
            ClassPathResource htmlFile = new ClassPathResource("templates/successPassword.html");
            String htmlContent = StreamUtils.copyToString(htmlFile.getInputStream(), StandardCharsets.UTF_8);

            // successPassword.html dosyasının içeriğini dön
            return htmlContent;
        } catch (IOException e) {
            e.printStackTrace();
            // Dosya okunamazsa veya bir hata olursa boş bir string dön
            return "";
        }
    }

}