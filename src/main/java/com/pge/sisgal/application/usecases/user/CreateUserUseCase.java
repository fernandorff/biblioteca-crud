package com.pge.sisgal.application.usecases.user;

import com.pge.sisgal.domain.entities.Role;
import com.pge.sisgal.domain.entities.User;
import com.pge.sisgal.domain.messages.UserValidationMessages;
import com.pge.sisgal.domain.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User execute(String name, String registration, String email, String password, String role) {
        Role userRole;
        try {
            userRole = Role.valueOf(role.toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(UserValidationMessages.invalidRole(role));
        }

        if (Boolean.TRUE.equals(userRepository.existsByRegistration(registration))) {
            throw new IllegalArgumentException(UserValidationMessages.userAlreadyCreatedWithSameRegistration(registration));
        }

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(name, registration, email, encodedPassword, userRole);
        return userRepository.save(user);
    }
}
