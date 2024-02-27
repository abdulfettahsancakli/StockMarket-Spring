package com.stockmarket.studycase.models;

import com.stockmarket.studycase.enums.Role;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserUpdateModel {

    @Nonnull
    private Integer id;

    @Nonnull
    private String username;

    @Nonnull
    private String password;

    @Nonnull
    private String email;

    @Nonnull
    private Role role;
}