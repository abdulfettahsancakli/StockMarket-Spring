package com.stockmarket.studycase.service.impl;


import com.stockmarket.studycase.entity.Token;
import com.stockmarket.studycase.entity.User;
import com.stockmarket.studycase.models.AuthenticationResponse;
import com.stockmarket.studycase.models.UserAddModel;
import com.stockmarket.studycase.repository.TokenRepository;
import com.stockmarket.studycase.repository.UserRepository;
import com.stockmarket.studycase.service.AuthenticationService;
import com.stockmarket.studycase.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public AuthenticationResponse register(UserAddModel request){
        // Kullanıcı adına göre mevcut bir kaydı kontrol et
        User existingUser = userRepository.findById(request.getId()).orElse(null);
        if (existingUser != null) {
            // Kullanıcı adı zaten mevcut, kayıt işlemini reddet
            return new AuthenticationResponse("","Bu kullanıcı adı zaten mevcut. Lütfen farklı bir kullanıcı adı seçin.");
        }
        User user = new User();
        user.setId(request.getId());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user = userRepository.save(user);

        String jwt = jwtService.generateToken(user);
        saveUserToken(jwt, user);
        return new AuthenticationResponse(jwt, "Kullanıcı kayıt işlemi başarıyla tamamlandı");
    }

    @Override
    public AuthenticationResponse authenticate(UserAddModel request){
        authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.generateToken(user);

        revokeAllTokenByUser(user);
        saveUserToken(token, user);

        return new AuthenticationResponse(token,"Kullanıcı sisteme başarıyla giriş yaptı.");
    }
    private void revokeAllTokenByUser(User user) {
        List<Token> validTokens = tokenRepository.findAllTokensByUser(user.getId());
        if(validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t-> {
            t.setLoggedOut(true);
        });

        tokenRepository.saveAll(validTokens);
    }

    private void saveUserToken(String jwt, User user) {
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }
    @Override
    public void resetPassword(String username, String newPassword) {
        // Kullanıcıyı kullanıcı adına göre bul
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        // Yeni şifreyi şifreleyerek kullanıcıya atayın
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}

