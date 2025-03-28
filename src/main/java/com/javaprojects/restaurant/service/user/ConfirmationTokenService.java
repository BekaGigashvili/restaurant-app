package com.javaprojects.restaurant.service.user;

import com.javaprojects.restaurant.model.user.ConfirmationToken;
import com.javaprojects.restaurant.model.user.User;
import com.javaprojects.restaurant.repository.user.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public Optional<ConfirmationToken> getToken(String token) {

        return confirmationTokenRepository.findByToken(token);
    }

    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }

    @Transactional
    public void setConfirmedAt(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository
                .findByToken(token)
                .orElseThrow(() -> new IllegalStateException("Token not found"));

        confirmationToken.setConfirmedAt(LocalDateTime.now());
    }
    public void revokeAllTokens(User user) {
        confirmationTokenRepository.deleteByUser(user);
    }
}