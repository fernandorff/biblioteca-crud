package com.pge.sisgal.application.usecases.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.pge.sisgal.TestUtils;
import com.pge.sisgal.domain.entities.User;
import com.pge.sisgal.domain.messages.UserValidationMessages;
import com.pge.sisgal.domain.persistence.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FindUserByRegistrationUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private FindUserByRegistrationUseCase findUserByRegistrationUseCase;

    @Test
    void execute_ShouldReturnUser_WhenUserExists() {
        User expectedUser = TestUtils.createUser();
        when(userRepository.findByRegistration(expectedUser.getRegistration())).thenReturn(Optional.of(expectedUser));

        User result = findUserByRegistrationUseCase.execute(expectedUser.getRegistration());

        assertEquals(expectedUser.getId(), result.getId());
        assertEquals(expectedUser.getRegistration(), result.getRegistration());
        assertEquals(expectedUser.getRole(), result.getRole());
    }

    @Test
    void execute_ShouldThrowException_WhenUserNotFound() {
        String registration = TestUtils.createUser().getRegistration();
        when(userRepository.findByRegistration(registration)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> findUserByRegistrationUseCase.execute(registration));

        assertEquals(UserValidationMessages.userNotFoundByRegistration(registration), exception.getMessage());
    }
}
