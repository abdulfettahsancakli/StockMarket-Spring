package com.stockmarket.studycase.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.stockmarket.studycase.entity.User;
import com.stockmarket.studycase.enums.Role;
import lombok.Data;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestResponseModel {
    private int statusCode;
    private String username;
    private String firstName;
    private String lastName;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private Role role;
    private String password;
    private User userInfo;
}