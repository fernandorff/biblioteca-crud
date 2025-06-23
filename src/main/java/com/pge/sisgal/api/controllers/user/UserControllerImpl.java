package com.pge.sisgal.api.controllers.user;

import com.pge.sisgal.api.dtos.user.requests.CreateUserRequest;
import com.pge.sisgal.api.dtos.user.responses.UserResponse;
import com.pge.sisgal.application.services.user.UserApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserApplicationService userApplicationService;

    @Override
    public ResponseEntity<UserResponse> createUser(CreateUserRequest request) {
        UserResponse response = userApplicationService.createUser(
            request.getName(),
            request.getRegistration(),
            request.getEmail(),
            request.getPassword(),
            request.getRole()
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UserResponse> getUserById(Long id) {
        UserResponse response = userApplicationService.getUserById(id);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<UserResponse> getUserByRegistration(String registration) {
        UserResponse response = userApplicationService.getUserByRegistration(registration);
        return ResponseEntity.ok(response);
    }
}
