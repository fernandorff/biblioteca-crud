package com.pge.sisgal.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.pge.sisgal.TestUtils;
import com.pge.sisgal.domain.messages.UserValidationMessages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserTest {

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " "})
    void constructor_ShouldThrowException_WhenNameIsInvalid(String name) {
        User validUser = TestUtils.createUser();
        String registration = validUser.getRegistration();
        String email = validUser.getEmail();
        String password = validUser.getPassword();
        Role role = validUser.getRole();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> new User(name, registration, email, password, role));
        assertEquals(UserValidationMessages.NAME_REQUIRED, exception.getMessage());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " "})
    void constructor_ShouldThrowException_WhenRegistrationIsInvalid(String registration) {
        User validUser = TestUtils.createUser();
        String name = validUser.getName();
        String email = validUser.getEmail();
        String password = validUser.getPassword();
        Role role = validUser.getRole();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> new User(name, registration, email, password, role));
        assertEquals(UserValidationMessages.REGISTRATION_REQUIRED, exception.getMessage());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " "})
    void constructor_ShouldThrowException_WhenEmailIsEmpty(String email) {
        User validUser = TestUtils.createUser();
        String name = validUser.getName();
        String registration = validUser.getRegistration();
        String password = validUser.getPassword();
        Role role = validUser.getRole();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> new User(name, registration, email, password, role));
        assertEquals(UserValidationMessages.EMAIL_REQUIRED, exception.getMessage());
    }

    @Test
    void constructor_ShouldThrowException_WhenEmailIsInvalid() {
        User validUser = TestUtils.createUser();
        String name = validUser.getName();
        String registration = validUser.getRegistration();
        String password = validUser.getPassword();
        Role role = validUser.getRole();
        String invalidEmail = "invalid";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> new User(name, registration, invalidEmail, password, role));
        assertEquals(UserValidationMessages.EMAIL_INVALID, exception.getMessage());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " "})
    void constructor_ShouldThrowException_WhenPasswordIsInvalid(String password) {
        User validUser = TestUtils.createUser();
        String name = validUser.getName();
        String registration = validUser.getRegistration();
        String email = validUser.getEmail();
        Role role = validUser.getRole();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> new User(name, registration, email, password, role));
        assertEquals(UserValidationMessages.PASSWORD_REQUIRED, exception.getMessage());
    }

    @Test
    void constructor_ShouldThrowException_WhenRoleIsNull() {
        User validUser = TestUtils.createUser();
        String name = validUser.getName();
        String registration = validUser.getRegistration();
        String email = validUser.getEmail();
        String password = validUser.getPassword();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> new User(name, registration, email, password, null));
        assertEquals(UserValidationMessages.ROLE_REQUIRED, exception.getMessage());
    }
}
