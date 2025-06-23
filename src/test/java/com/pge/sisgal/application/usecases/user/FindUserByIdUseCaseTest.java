package com.pge.sisgal.application.usecases.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.pge.sisgal.TestUtils;
import com.pge.sisgal.domain.entities.User;
import com.pge.sisgal.domain.messages.UserValidationMessages;
import com.pge.sisgal.domain.persistence.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FindUserByIdUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private FindUserByIdUseCase findUserByIdUseCase;

    private User user;

    @BeforeEach
    void setUp() {
        user = TestUtils.createUser();
    }

    @Test
    void execute_ShouldReturnUser_WhenUserExists() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        User result = findUserByIdUseCase.execute(user.getId());

        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getRegistration(), result.getRegistration());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getRole(), result.getRole());
    }

    @Test
    void execute_ShouldThrowException_WhenUserNotFound() {
        Long id = user.getId();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> findUserByIdUseCase.execute(id));

        assertEquals(UserValidationMessages.userNotFoundById(id), exception.getMessage());
    }
}
