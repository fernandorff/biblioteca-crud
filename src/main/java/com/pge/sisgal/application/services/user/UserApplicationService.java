package com.pge.sisgal.application.services.user;

import com.pge.sisgal.api.dtos.user.responses.UserResponse;

public interface UserApplicationService {
    UserResponse createUser(String name, String registration, String email, String password, String role); // Changed Role to String
    UserResponse getUserById(Long id);
    UserResponse getUserByRegistration(String registration);
}
