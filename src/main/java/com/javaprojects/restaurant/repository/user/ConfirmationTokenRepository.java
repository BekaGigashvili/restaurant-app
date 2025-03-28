package com.javaprojects.restaurant.repository.user;

import com.javaprojects.restaurant.model.user.ConfirmationToken;
import com.javaprojects.restaurant.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository
        extends JpaRepository<ConfirmationToken, Long> {

    Optional<ConfirmationToken> findByToken(String token);

    void deleteByUser(User user);
}