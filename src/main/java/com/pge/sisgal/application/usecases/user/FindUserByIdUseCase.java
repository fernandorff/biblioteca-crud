package com.pge.sisgal.application.usecases.user;

import com.pge.sisgal.domain.entities.User;
import com.pge.sisgal.domain.messages.UserValidationMessages;
import com.pge.sisgal.domain.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FindUserByIdUseCase {
    private final UserRepository userRepository;

    public User execute(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException(UserValidationMessages.userNotFoundById(id)));
    }
}
