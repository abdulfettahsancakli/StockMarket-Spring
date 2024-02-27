package com.stockmarket.studycase.repository;


import com.stockmarket.studycase.entity.User;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);

    @Nonnull
    Optional<User> findById(Integer id);

    Optional<User> findByEmail(String email);
}
