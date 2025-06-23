package com.pge.sisgal.application.usecases.user;

import com.pge.sisgal.domain.entities.User;
import com.pge.sisgal.domain.messages.UserValidationMessages;
import com.pge.sisgal.domain.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FindUserByRegistrationUseCase {
    private final UserRepository userRepository;

    public User execute(String registration) {
        return userRepository.findByRegistration(registration)
            .orElseThrow(() -> new IllegalArgumentException(UserValidationMessages.userNotFoundByRegistration(registration)));
    }
}
