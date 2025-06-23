package com.pge.sisgal.domain.entities;

import com.pge.sisgal.domain.messages.UserValidationMessages;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Long id;
    private String name;
    private String registration;
    private String email;
    private String password;
    private Role role;

    public User(String name, String registration, String email, String password, Role role) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException(UserValidationMessages.NAME_REQUIRED);

        if (registration == null || registration.trim().isEmpty())
            throw new IllegalArgumentException(UserValidationMessages.REGISTRATION_REQUIRED);

        if (email == null || email.trim().isEmpty())
            throw new IllegalArgumentException(UserValidationMessages.EMAIL_REQUIRED);

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$"))
            throw new IllegalArgumentException(UserValidationMessages.EMAIL_INVALID);

        if (password == null || password.trim().isEmpty())
            throw new IllegalArgumentException(UserValidationMessages.PASSWORD_REQUIRED);

        if (role == null)
            throw new IllegalArgumentException(UserValidationMessages.ROLE_REQUIRED);

        this.name = name;
        this.registration = registration;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
