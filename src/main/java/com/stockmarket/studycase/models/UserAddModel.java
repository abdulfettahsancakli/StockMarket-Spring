package com.stockmarket.studycase.models;


import com.stockmarket.studycase.enums.Role;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserAddModel {

    @Nonnull
    private Integer id;

    @Nonnull
    private String firstName;

    @Nonnull
    private String lastName;

    @Nonnull
    private String username;

    @Nonnull
    private CharSequence password;

    @Nonnull
    private Role role;
}