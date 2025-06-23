package com.pge.sisgal.application.mappers;

import com.pge.sisgal.api.dtos.user.responses.UserResponse;
import com.pge.sisgal.domain.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserResponseMapper {

    public UserResponse toResponse(User user) {
        return UserResponse.builder()
            .id(user.getId())
            .name(user.getName())
            .registration(user.getRegistration())
            .email(user.getEmail())
            .role(user.getRole().name())
            .build();
    }

}
