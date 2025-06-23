package com.pge.sisgal.infraestructure.persistence.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.pge.sisgal.TestUtils;
import com.pge.sisgal.domain.entities.User;
import com.pge.sisgal.infraestructure.persistence.entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserEntityMapperTest {

    private final UserEntityMapper userEntityMapper = new UserEntityMapper();

    @Test
    void toEntity_ShouldMapUserToUserEntity() {
        User user = TestUtils.createUser();

        UserEntity result = userEntityMapper.toEntity(user);

        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getRegistration(), result.getRegistration());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getPassword(), result.getPassword());
        assertEquals(user.getRole(), result.getRole());
    }

    @Test
    void toDomain_ShouldMapUserEntityToUser() {
        User user = TestUtils.createUser();
        UserEntity userEntity = UserEntity.builder()
            .id(user.getId())
            .name(user.getName())
            .registration(user.getRegistration())
            .email(user.getEmail())
            .password(user.getPassword())
            .role(user.getRole())
            .build();

        User result = userEntityMapper.toDomain(userEntity);

        assertEquals(userEntity.getId(), result.getId());
        assertEquals(userEntity.getName(), result.getName());
        assertEquals(userEntity.getRegistration(), result.getRegistration());
        assertEquals(userEntity.getEmail(), result.getEmail());
        assertEquals(userEntity.getPassword(), result.getPassword());
        assertEquals(userEntity.getRole(), result.getRole());
    }
}
