package com.pge.sisgal.api.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.pge.sisgal.TestUtils;
import com.pge.sisgal.api.dtos.user.responses.UserResponse;
import com.pge.sisgal.application.mappers.UserResponseMapper;
import com.pge.sisgal.domain.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserResponseMapperTest {

    private UserResponseMapper userResponseMapper;
    private User user;

    @BeforeEach
    void setUp() {
        userResponseMapper = new UserResponseMapper();
        user = TestUtils.createUser();
    }

    @Test
    void toResponse_ShouldMapUserToUserResponse_Correctly() {
        UserResponse result = userResponseMapper.toResponse(user);

        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getRegistration(), result.getRegistration());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getRole().name(), result.getRole());
    }
}
