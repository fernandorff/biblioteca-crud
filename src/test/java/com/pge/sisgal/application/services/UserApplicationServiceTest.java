package com.pge.sisgal.application.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pge.sisgal.TestUtils;
import com.pge.sisgal.api.dtos.user.responses.UserResponse;
import com.pge.sisgal.application.mappers.UserResponseMapper;
import com.pge.sisgal.application.services.user.UserApplicationServiceImpl;
import com.pge.sisgal.application.usecases.user.CreateUserUseCase;
import com.pge.sisgal.application.usecases.user.FindUserByIdUseCase;
import com.pge.sisgal.application.usecases.user.FindUserByRegistrationUseCase;
import com.pge.sisgal.domain.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserApplicationServiceTest {

    @Mock
    private CreateUserUseCase createUserUseCase;

    @Mock
    private FindUserByIdUseCase findUserByIdUseCase;

    @Mock
    private FindUserByRegistrationUseCase findUserByRegistrationUseCase;

    @Mock
    private UserResponseMapper userResponseMapper;

    @InjectMocks
    private UserApplicationServiceImpl userApplicationService;

    private User user;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        user = TestUtils.createUser();
        userResponse = TestUtils.createUserResponse();
    }

    @Test
    void createUser_ShouldReturnUserResponse_WhenValid() {
        when(createUserUseCase.execute(user.getName(), user.getRegistration(), user.getEmail(), user.getPassword(), user.getRole().name())).thenReturn(user);
        when(userResponseMapper.toResponse(user)).thenReturn(userResponse);

        UserResponse result = userApplicationService.createUser(user.getName(), user.getRegistration(), user.getEmail(), user.getPassword(), user.getRole().name());

        verify(createUserUseCase).execute(user.getName(), user.getRegistration(), user.getEmail(), user.getPassword(), user.getRole().name());
        verify(userResponseMapper).toResponse(user);
        assertEquals(userResponse, result);
    }

    @Test
    void getUserById_ShouldReturnUserResponse_WhenUserExists() {
        when(findUserByIdUseCase.execute(user.getId())).thenReturn(user);
        when(userResponseMapper.toResponse(user)).thenReturn(userResponse);

        UserResponse result = userApplicationService.getUserById(user.getId());

        verify(findUserByIdUseCase).execute(user.getId());
        verify(userResponseMapper).toResponse(user);
        assertEquals(userResponse, result);
    }

    @Test
    void getUserByRegistration_ShouldReturnUserResponse_WhenUserExists() {
        when(findUserByRegistrationUseCase.execute(user.getRegistration())).thenReturn(user);
        when(userResponseMapper.toResponse(user)).thenReturn(userResponse);

        UserResponse result = userApplicationService.getUserByRegistration(user.getRegistration());

        verify(findUserByRegistrationUseCase).execute(user.getRegistration());
        verify(userResponseMapper).toResponse(user);
        assertEquals(userResponse, result);
    }
}
