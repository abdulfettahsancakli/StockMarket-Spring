package com.stockmarket.studycase.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockmarket.studycase.entity.Token;
import com.stockmarket.studycase.entity.User;
import com.stockmarket.studycase.enums.TokenType;
import com.stockmarket.studycase.exception.IncorrectPasswordException;
import com.stockmarket.studycase.exception.NotSendMailException;
import com.stockmarket.studycase.exception.PasswordNotMatchException;
import com.stockmarket.studycase.exception.UserEmailNotFoundException;
import com.stockmarket.studycase.models.*;
import com.stockmarket.studycase.repository.TokenRepository;
import com.stockmarket.studycase.repository.UserRepository;
import com.stockmarket.studycase.util.EmailUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Optional;
@Service
public class AuthenticationService {

    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private  JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private EmailUtil emailUtil;

    public RequestResponseModel register(UserRegisterModel signUpRequest) {
        try {
            Optional<User> existingUser = userRepository.findById(signUpRequest.getId());
            if (existingUser.isPresent()) {
                return RequestResponseModel.error("Bu id'ye ait kullanıcı zaten mevcut. Lütfen farklı bir kullanıcı adı seçin.");
            } else {
                User user = User.builder()
                        .id(signUpRequest.getId())
                        .username(signUpRequest.getUsername())
                        .email(signUpRequest.getEmail())
                        .password(passwordEncoder.encode(signUpRequest.getPassword()))
                        .role(signUpRequest.getRole())
                        .build();

                User savedUser = userRepository.save(user);
                String jwtToken = jwtUtils.generateToken(user);
                String refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
                saveUserToken(savedUser, jwtToken);

                return RequestResponseModel.success(savedUser, "Kullanıcı başarıyla kaydedildi", jwtToken, refreshToken);
            }
        } catch (Exception e) {
            return RequestResponseModel.error(e.getMessage());
        }
    }

    public RequestResponseModel authenticate(UserLoginModel signInRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.getUsername(),
                        signInRequest.getPassword()
                )
        );
        var user = userRepository.findByUsername(signInRequest.getUsername()).orElseThrow();

        var jwtToken = jwtUtils.generateToken(user);
        var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return RequestResponseModel.builder()
                .message("Sisteme başarıyla giriş yapıldı")
                .statusCode(200)
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
            token.setLoggedOut(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public RequestResponseModel updateUser(UserUpdateModel updateUserRequest) {
        RequestResponseModel response = new RequestResponseModel();
        try {
            // Kullanıcıyı kullanıcı adına göre bul
            User userToUpdate = userRepository.findByUsername(updateUserRequest.getUsername())
                    .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

            // Yeni bilgilerle güncelleyin
            userToUpdate.setId(updateUserRequest.getId());
            userToUpdate.setUsername(updateUserRequest.getUsername());
            userToUpdate.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));
            userToUpdate.setEmail(updateUserRequest.getEmail());
            userToUpdate.setRole(updateUserRequest.getRole());

            // Kullanıcıyı kaydedin
            User updatedUser = userRepository.save(userToUpdate);

            // Başarı mesajı ayarlayın
            response.setStatusCode(200);
            response.setMessage("Kullanıcı bilgileri başarıyla güncellendi");
            response.setUserInfo(updatedUser);
        } catch (Exception e) {

            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    public String forgotPassword(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()->new UserEmailNotFoundException(email));
       try{
           emailUtil.sendSetPasswordEmail(email);
       }
       catch (MessagingException | IOException e){
           throw new NotSendMailException();
       }
        return "Hesabınıza yeni şifre belirlemek için lütfen e-posta kutunuzu kontrol edin";
    }

    public String setPassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email).orElseThrow(()->new UserEmailNotFoundException(email));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return "Hesabınız için yeni şifre ile başarıyla giriş yapabilirsiniz";
    }

    public RequestResponseModel refreshToken(RefreshTokenModel refreshTokenReq) {
        RequestResponseModel response = new RequestResponseModel();
        String name = jwtUtils.extractUsername(refreshTokenReq.getToken());
        User user = userRepository.findByUsername(name).orElseThrow();
        if(jwtUtils.isTokenValid(refreshTokenReq.getToken(),user))
        {
            var accessToken = jwtUtils.generateToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, accessToken);
            response.setStatusCode(200);
            response.setToken(accessToken);
            response.setRefreshToken(refreshTokenReq.getToken());
            response.setExpirationTime("24 Hour");
            response.setMessage("Yenileme Tokenı Başarıyla oluşturuldu.");
        }
        response.setStatusCode(500);
        return response;
    }

    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // Kullacınının güncel parolasını kontrol et
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IncorrectPasswordException();
        }

        //Paraloların aynı olup olmadığını kontrol et
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new PasswordNotMatchException();
        }

        // parolayı güncelle
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // yeni parolayı kaydet
        userRepository.save(user);
    }
}
