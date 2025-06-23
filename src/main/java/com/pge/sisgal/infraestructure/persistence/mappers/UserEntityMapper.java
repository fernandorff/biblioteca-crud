package com.pge.sisgal.infraestructure.persistence.mappers;

import com.pge.sisgal.domain.entities.User;
import com.pge.sisgal.infraestructure.persistence.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserEntityMapper {

    public UserEntity toEntity(User user) {
        return UserEntity.builder()
            .id(user.getId())
            .name(user.getName())
            .registration(user.getRegistration())
            .email(user.getEmail())
            .password(user.getPassword())
            .role(user.getRole())
            .build();
    }

    public User toDomain(UserEntity entity) {
        User user = new User(
            entity.getName(),
            entity.getRegistration(),
            entity.getEmail(),
            entity.getPassword(),
            entity.getRole()
        );
        user.setId(entity.getId());
        return user;
    }
}
