package com.pge.sisgal.application.usecases.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pge.sisgal.TestUtils;
import com.pge.sisgal.domain.entities.Role;
import com.pge.sisgal.domain.entities.User;
import com.pge.sisgal.domain.messages.UserValidationMessages;
import com.pge.sisgal.domain.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CreateUserUseCase createUserUseCase;

    @Test
    void execute_ShouldCreateUser_WhenValidParameters() {
        User expectedUser = TestUtils.createUser();
        when(userRepository.existsByRegistration(expectedUser.getRegistration())).thenReturn(false);
        when(passwordEncoder.encode(expectedUser.getPassword())).thenReturn(TestUtils.DEFAULT_USER_PASSWORD);
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);

        User result = createUserUseCase.execute(
            expectedUser.getName(),
            expectedUser.getRegistration(),
            expectedUser.getEmail(),
            expectedUser.getPassword(),
            expectedUser.getRole().name()
        );

        verify(passwordEncoder).encode(expectedUser.getPassword());
        verify(userRepository).existsByRegistration(expectedUser.getRegistration());
        verify(userRepository).save(any(User.class));
        assertEquals(expectedUser.getName(), result.getName());
        assertEquals(TestUtils.DEFAULT_USER_PASSWORD, result.getPassword());
    }

    @Test
    void execute_ShouldThrowException_WhenUserWithSameRegistrationExists() {
        User user = TestUtils.createUser();
        String name = user.getName();
        String registration = user.getRegistration();
        String email = user.getEmail();
        String password = user.getPassword();
        Role role = user.getRole();

        when(userRepository.existsByRegistration(registration)).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> createUserUseCase.execute(name, registration, email, password, role.name()));

        assertEquals(UserValidationMessages.userAlreadyCreatedWithSameRegistration(registration), exception.getMessage());
    }
}
