package com.javaprojects.restaurant.service.user;

import com.javaprojects.restaurant.model.user.ConfirmationToken;
import com.javaprojects.restaurant.model.user.RegistrationRequest;
import com.javaprojects.restaurant.model.user.User;
import com.javaprojects.restaurant.model.user.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final UserService userService;
    private final EmailSender emailSender;

    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator
                .test(request.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("Email is not valid!");
        }

        String token = userService.signUpUser(
                new User(
                        request.getUsername(),
                        request.getEmail(),
                        request.getPassword(),
                        request.getAddress(),
                        UserRole.USER
                )
        );

        String link = "http://localhost:8080/restaurant/registration/confirm?token=" + token;

        emailSender.send(
                request.getEmail(),
                "Confirm your email address",
                "Click the link to confirm your registration: " + link
        );

        return "Registration successful! Please check your email to confirm your account.";
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("Token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("Email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if(expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token expired");
        }
        confirmationTokenService.setConfirmedAt(token);
        userService.enableAppUser(
                confirmationToken.getUser().getEmail()
        );
        return "Confirmed";
    }
}