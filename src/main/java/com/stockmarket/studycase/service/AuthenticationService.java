package com.stockmarket.studycase.service;


import com.stockmarket.studycase.entity.User;
import com.stockmarket.studycase.models.*;
import com.stockmarket.studycase.repository.UserRepository;
import com.stockmarket.studycase.util.EmailUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    @Autowired
    private EmailUtil emailUtil;

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
            user.setUsername(SignUpRequest.getUsername());
            user.setEmail(SignUpRequest.getEmail());
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

    public RequestResponseModel authenticate(UserLoginModel signInRequest) {
        RequestResponseModel response = new RequestResponseModel();
        String refreshToken = null;
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));
            var user = userRepository.findByUsername(signInRequest.getUsername()).orElseThrow();

            // Kullanıcı için mevcut tokenlar alınıyor
            String accessToken = user.getAccessToken();
            refreshToken = user.getRefreshToken();

            // Kullanıcı zaten oturum açmışsa, mevcut tokenları kullan
            // Eğer kullanıcıya ait geçerli bir erişim tokenı ve refresh tokenı varsa
            if (accessToken != null && refreshToken != null && jwtUtils.isTokenValid(accessToken, user)) {
                response.setStatusCode(200);
                response.setToken(accessToken);
                response.setRefreshToken(refreshToken);
                response.setMessage("Geçerli token ile oturumunuz aktif durumdadır.");
            } else {
                // Kullanıcı aktif oturum açmamışsa yeni tokenlar oluştur
                String newAccessToken = jwtUtils.generateToken(user);
                String newRefreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
                user.setAccessToken(newAccessToken );
                user.setRefreshToken(newRefreshToken);
                userRepository.save(user);
                response.setStatusCode(200);
                response.setToken(newAccessToken);
                response.setRefreshToken(newRefreshToken);
                response.setExpirationTime("24 Hours");
                response.setMessage("Successfully Signed In");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError("JWT kullanım süresi dolmuştur. \n Refresh Token ile yeni token oluşturunuz. Hata: " + e.getMessage());
            response.setMessage("Refresh Token: " + refreshToken);
        }
        return response;
    }



    /*public RequestResponseModel refreshToken(RefreshTokenModel refreshTokenReq) {
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
*/

    public RequestResponseModel refreshToken(RefreshTokenModel refreshTokenReq) {
        RequestResponseModel response = new RequestResponseModel();
        try {
            // Veritabanından kullanıcıyı bul
            String username = jwtUtils.extractUsername(refreshTokenReq.getToken());
            User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı: " + username));

            // Refresh token'ın geçerliliğini kontrol et
            if (!jwtUtils.isRefreshTokenValid(refreshTokenReq.getToken(), user)) {
                //throw new Exception("Geçersiz yenileme tokenı");
                // Geçersiz refresh token olduğunda yeni bir refresh token oluştur
                String newRefreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
                // Veritabanında refresh token alanını güncelle
                user.setRefreshToken(newRefreshToken);
            }

            // Yeni bir erişim tokenı oluştur
            String newAccessToken = jwtUtils.generateToken(user);

            // Veritabanındaki access token alanını güncelle
            user.setAccessToken(newAccessToken);

            // Veritabanında refresh token alanını güncelle
            user.setRefreshToken(refreshTokenReq.getToken());

            // Veritabanına değişiklikleri kaydet
            userRepository.save(user);

            // Response'a tokenları ve diğer bilgileri ekle
            response.setStatusCode(200);
            response.setToken(newAccessToken);
            response.setRefreshToken(refreshTokenReq.getToken()); // Mevcut refresh token'ı geri gönder
            response.setExpirationTime("24 Hours");
            response.setMessage("Başarılı bir şekilde token yenilendi");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
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

    public void resetPassword(String username, String newPassword) {
        // Kullanıcıyı kullanıcı adına göre bul
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        // Yeni şifreyi şifreleyerek kullanıcıya atayın
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }


    public String forgotPassword(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("User not found with this email: "+email));
       try{
           emailUtil.sendSetPasswordEmail(email);
       }
       catch (MessagingException e){
           throw new RuntimeException("Unable to send set password email please try again");
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
        return "Please check your email to set new password to your account";
    }

    public String setPassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("User not found with this email: "+email));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return "New password set successfully login with new password";
    }
}
