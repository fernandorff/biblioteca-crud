package com.pge.sisgal.application.services.user;

import com.pge.sisgal.api.dtos.user.responses.UserResponse;
import com.pge.sisgal.application.mappers.UserResponseMapper;
import com.pge.sisgal.application.usecases.user.CreateUserUseCase;
import com.pge.sisgal.application.usecases.user.FindUserByIdUseCase;
import com.pge.sisgal.application.usecases.user.FindUserByRegistrationUseCase;
import com.pge.sisgal.domain.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserApplicationServiceImpl implements UserApplicationService {

    private final CreateUserUseCase createUserUseCase;
    private final FindUserByIdUseCase findUserByIdUseCase;
    private final FindUserByRegistrationUseCase findUserByRegistrationUseCase;
    private final UserResponseMapper userResponseMapper;

    @Override
    public UserResponse createUser(String name, String registration, String email, String password, String role) {
        User user = createUserUseCase.execute(name, registration, email, password, role);
        return userResponseMapper.toResponse(user);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = findUserByIdUseCase.execute(id);
        return userResponseMapper.toResponse(user);
    }

    @Override
    public UserResponse getUserByRegistration(String registration) {
        User user = findUserByRegistrationUseCase.execute(registration);
        return userResponseMapper.toResponse(user);
    }
}
