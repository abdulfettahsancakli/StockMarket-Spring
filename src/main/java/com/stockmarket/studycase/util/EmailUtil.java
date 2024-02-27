package com.stockmarket.studycase.util;

import com.stockmarket.studycase.service.JwtUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender javaMailSender;

    private JwtUtils jwtUtils;

    public void sendSetPasswordEmail(String email) throws MessagingException, IOException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        // E-posta başlığı ve alıcı ayarları
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Sifre Yenileme Talebi");

        String userEmail = email;
        String setPasswordLink = "http://localhost:8080/set-password?email=" + email;
        String emailBody = getEmailTemplate("RefreshPassword.html");

        // Şifre yenileme bağlantısını HTML içindeki butona yerleştir
        emailBody = emailBody.replace("${setPasswordLink}", setPasswordLink);
        emailBody = emailBody.replace("${userEmail}",userEmail);

        mimeMessageHelper.setText(emailBody, true);

        javaMailSender.send(mimeMessage);

    }
    private String getEmailTemplate(String templateName) throws IOException {
        ClassPathResource resource = new ClassPathResource("templates/" + templateName);
        try (InputStream inputStream = resource.getInputStream()) {
            return StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        }
    }
}
